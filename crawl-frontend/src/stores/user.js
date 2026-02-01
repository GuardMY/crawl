import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import apiService from '../api'

export const useUserStore = defineStore('user', () => {
  // 状态
  const user = ref(null)
  const token = ref(localStorage.getItem('token') || '')
  const permissions = ref([])
  const roles = ref([])
  const loading = ref(false)
  const error = ref('')

  // 计算属性
  const isLoggedIn = computed(() => !!token.value)
  const userInfo = computed(() => user.value)
  const hasPermission = computed(() => (permissionKey) => {
    return permissions.value.some(p => p.permissionKey === permissionKey)
  })

  // 动作
  const login = async (username, password) => {
    loading.value = true
    error.value = ''
    try {
      const response = await apiService.auth.login({ username, password })
      
      token.value = response.token
      user.value = response.user
      localStorage.setItem('token', token.value)
      
      // 获取用户权限
      await fetchUserPermissions()
      await fetchUserRoles()
      
      return true
    } catch (err) {
      error.value = err.message || '登录失败'
      return false
    } finally {
      loading.value = false
    }
  }

  const register = async (userData) => {
    loading.value = true
    error.value = ''
    try {
      await apiService.auth.register(userData)
      return true
    } catch (err) {
      error.value = err.message || '注册失败'
      return false
    } finally {
      loading.value = false
    }
  }

  const logout = () => {
    user.value = null
    token.value = ''
    permissions.value = []
    roles.value = []
    localStorage.removeItem('token')
  }

  const getUserInfo = async () => {
    if (!token.value) return
    
    loading.value = true
    try {
      const response = await apiService.auth.getUserInfo()
      user.value = response
    } catch (err) {
      console.error('获取用户信息失败', err)
      logout()
    } finally {
      loading.value = false
    }
  }

  const fetchUserPermissions = async () => {
    if (!token.value || !user.value) return
    
    try {
      const response = await apiService.permissions.getPermissionsByUserId(user.value.id)
      permissions.value = response
    } catch (err) {
      console.error('获取用户权限失败', err)
    }
  }

  const fetchUserRoles = async () => {
    if (!token.value || !user.value) return
    
    try {
      const response = await apiService.roles.getRolesByUserId(user.value.id)
      roles.value = response
    } catch (err) {
      console.error('获取用户角色失败', err)
    }
  }

  // 初始化时检查 token
  if (token.value) {
    getUserInfo()
    fetchUserPermissions()
    fetchUserRoles()
  }

  return {
    user,
    token,
    permissions,
    roles,
    loading,
    error,
    isLoggedIn,
    userInfo,
    hasPermission,
    login,
    register,
    logout,
    getUserInfo,
    fetchUserPermissions,
    fetchUserRoles
  }
})