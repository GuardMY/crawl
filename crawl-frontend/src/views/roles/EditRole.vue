<template>
  <div class="edit-role">
    <h1>编辑角色</h1>
    <form @submit.prevent="updateRole">
      <div class="form-group">
        <label for="roleName">角色名称</label>
        <input type="text" id="roleName" v-model="roleForm.roleName" class="form-control" required>
      </div>
      <div class="form-group">
        <label for="description">描述</label>
        <textarea id="description" v-model="roleForm.description" class="form-control" rows="3"></textarea>
      </div>
      <div class="action-buttons">
        <button type="submit" class="btn btn-primary" :disabled="loading">保存</button>
        <router-link to="/roles" class="btn btn-secondary">取消</router-link>
      </div>
      <div v-if="error" class="alert alert-danger">{{ error }}</div>
    </form>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import apiService from '../../api'

const router = useRouter()
const route = useRoute()
const roleId = route.params.id
const roleForm = ref({
  roleName: '',
  description: ''
})
const loading = ref(false)
const error = ref('')

const fetchRole = async () => {
  loading.value = true
  try {
    const role = await apiService.roles.getById(roleId)
    roleForm.value = {
      roleName: role.roleName,
      description: role.description
    }
  } catch (err) {
    error.value = err.message || '获取角色信息失败'
  } finally {
    loading.value = false
  }
}

const updateRole = async () => {
  loading.value = true
  error.value = ''
  try {
    await apiService.roles.update(roleId, roleForm.value)
    // 跳转到角色列表页面
    router.push('/roles')
  } catch (err) {
    error.value = err.message || '更新角色失败'
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchRole()
})
</script>

<style scoped>
.edit-role {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.form-control {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.action-buttons {
  margin-top: 20px;
}

.btn {
  padding: 8px 16px;
  margin-right: 10px;
  text-decoration: none;
  border: none;
  border-radius: 3px;
  cursor: pointer;
}

.btn-primary {
  background-color: #007bff;
  color: white;
}

.btn-secondary {
  background-color: #6c757d;
  color: white;
}

.alert {
  margin-top: 15px;
  padding: 10px;
  border-radius: 4px;
}

.alert-danger {
  background-color: #f8d7da;
  color: #721c24;
  border: 1px solid #f5c6cb;
}
</style>
