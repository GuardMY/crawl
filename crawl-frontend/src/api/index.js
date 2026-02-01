import axios from 'axios'

// 创建 axios 实例
const api = axios.create({
  baseURL: 'http://localhost:8080/api', // 后端 API 基础路径
  timeout: 10000, // 请求超时时间
  headers: {
    'Content-Type': 'application/json'
  }
})

// 请求拦截器
api.interceptors.request.use(
  config => {
    // 从本地存储中获取 token
    const token = localStorage.getItem('token')
    // 如果有 token，添加到请求头
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => {
    // 处理请求错误
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
api.interceptors.response.use(
  response => {
    // 直接返回响应数据
    return response.data
  },
  error => {
    // 处理响应错误
    console.error('响应错误:', error)
    
    // 处理 401 未授权错误
    if (error.response && error.response.status === 401) {
      // 清除本地存储的 token
      localStorage.removeItem('token')
      // 跳转到登录页面
      window.location.href = '/login'
    }
    
    // 提取错误信息
    const errorMessage = error.response?.data?.message || '请求失败'
    return Promise.reject(new Error(errorMessage))
  }
)

// API 接口
const apiService = {
  // 用户认证相关
  auth: {
    login: (credentials) => api.post('/auth/login', credentials),
    register: (userData) => api.post('/auth/register', userData),
    getUserInfo: () => api.get('/auth/userinfo'),
    logout: () => api.post('/auth/logout')
  },
  
  // 任务管理相关
  tasks: {
    getList: () => api.get('/tasks'),
    getById: (id) => api.get(`/tasks/${id}`),
    create: (taskData) => api.post('/tasks', taskData),
    update: (id, taskData) => api.put(`/tasks/${id}`, taskData),
    delete: (id) => api.delete(`/tasks/${id}`),
    execute: (id) => api.post(`/tasks/${id}/execute`)
  },
  
  // 用户管理相关
  users: {
    getList: () => api.get('/users'),
    getById: (id) => api.get(`/users/${id}`),
    create: (userData) => api.post('/users', userData),
    update: (id, userData) => api.put(`/users/${id}`, userData),
    delete: (id) => api.delete(`/users/${id}`)
  },
  
  // 角色管理相关
  roles: {
    getList: () => api.get('/roles'),
    getById: (id) => api.get(`/roles/${id}`),
    create: (roleData) => api.post('/roles', roleData),
    update: (id, roleData) => api.put(`/roles/${id}`, roleData),
    delete: (id) => api.delete(`/roles/${id}`),
    getRolesByUserId: (userId) => api.get(`/roles/user/${userId}`)
  },
  
  // 权限管理相关
  permissions: {
    getList: () => api.get('/permissions'),
    getById: (id) => api.get(`/permissions/${id}`),
    create: (permissionData) => api.post('/permissions', permissionData),
    update: (id, permissionData) => api.put(`/permissions/${id}`, permissionData),
    delete: (id) => api.delete(`/permissions/${id}`),
    getPermissionsByRoleId: (roleId) => api.get(`/permissions/role/${roleId}`),
    getPermissionsByUserId: (userId) => api.get(`/permissions/user/${userId}`),
    assignToRole: (roleId, permissionId) => api.post(`/permissions/assign`, { roleId, permissionId }),
    removeFromRole: (roleId, permissionId) => api.post(`/permissions/remove`, { roleId, permissionId }),
    checkPermission: (userId, permissionKey) => api.get(`/permissions/check`, { params: { userId, permissionKey } })
  }
}

export default apiService