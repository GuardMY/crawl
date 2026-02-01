<template>
  <div class="edit-permission">
    <h1>编辑权限</h1>
    <form @submit.prevent="updatePermission">
      <div class="form-group">
        <label for="permissionName">权限名称</label>
        <input type="text" id="permissionName" v-model="permissionForm.permissionName" class="form-control" required>
      </div>
      <div class="form-group">
        <label for="permissionKey">权限键</label>
        <input type="text" id="permissionKey" v-model="permissionForm.permissionKey" class="form-control" required>
      </div>
      <div class="form-group">
        <label for="description">描述</label>
        <textarea id="description" v-model="permissionForm.description" class="form-control" rows="3"></textarea>
      </div>
      <div class="form-group">
        <label for="url">URL</label>
        <input type="text" id="url" v-model="permissionForm.url" class="form-control">
      </div>
      <div class="form-group">
        <label for="method">方法</label>
        <select id="method" v-model="permissionForm.method" class="form-control">
          <option value="GET">GET</option>
          <option value="POST">POST</option>
          <option value="PUT">PUT</option>
          <option value="DELETE">DELETE</option>
          <option value="PATCH">PATCH</option>
        </select>
      </div>
      <div class="action-buttons">
        <button type="submit" class="btn btn-primary" :disabled="loading">保存</button>
        <router-link to="/permissions" class="btn btn-secondary">取消</router-link>
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
const permissionId = route.params.id
const permissionForm = ref({
  permissionName: '',
  permissionKey: '',
  description: '',
  url: '',
  method: 'GET'
})
const loading = ref(false)
const error = ref('')

const fetchPermission = async () => {
  loading.value = true
  try {
    const permission = await apiService.permissions.getById(permissionId)
    permissionForm.value = {
      permissionName: permission.permissionName,
      permissionKey: permission.permissionKey,
      description: permission.description,
      url: permission.url,
      method: permission.method
    }
  } catch (err) {
    error.value = err.message || '获取权限信息失败'
  } finally {
    loading.value = false
  }
}

const updatePermission = async () => {
  loading.value = true
  error.value = ''
  try {
    await apiService.permissions.update(permissionId, permissionForm.value)
    // 跳转到权限列表页面
    router.push('/permissions')
  } catch (err) {
    error.value = err.message || '更新权限失败'
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchPermission()
})
</script>

<style scoped>
.edit-permission {
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
