<template>
  <div class="permission-list">
    <h1>权限管理</h1>
    <div class="action-bar">
      <router-link to="/permissions/create" class="btn btn-primary">创建权限</router-link>
    </div>
    <table class="table table-striped">
      <thead>
        <tr>
          <th>ID</th>
          <th>权限名称</th>
          <th>权限键</th>
          <th>描述</th>
          <th>URL</th>
          <th>方法</th>
          <th>创建时间</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="permission in permissions" :key="permission.id">
          <td>{{ permission.id }}</td>
          <td>{{ permission.permissionName }}</td>
          <td>{{ permission.permissionKey }}</td>
          <td>{{ permission.description }}</td>
          <td>{{ permission.url }}</td>
          <td>{{ permission.method }}</td>
          <td>{{ formatDate(permission.createTime) }}</td>
          <td>
            <router-link :to="`/permissions/edit/${permission.id}`" class="btn btn-sm btn-warning">编辑</router-link>
            <button @click="deletePermission(permission.id)" class="btn btn-sm btn-danger">删除</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import apiService from '../../api'

const permissions = ref([])
const loading = ref(false)
const error = ref('')

const fetchPermissions = async () => {
  loading.value = true
  try {
    permissions.value = await apiService.permissions.getList()
  } catch (err) {
    error.value = err.message || '获取权限列表失败'
  } finally {
    loading.value = false
  }
}

const deletePermission = async (id) => {
  if (confirm('确定要删除这个权限吗？')) {
    try {
      await apiService.permissions.delete(id)
      // 重新获取权限列表
      await fetchPermissions()
    } catch (err) {
      error.value = err.message || '删除权限失败'
    }
  }
}

const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleString()
}

onMounted(() => {
  fetchPermissions()
})
</script>

<style scoped>
.permission-list {
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
}

.action-bar {
  margin-bottom: 20px;
}

.table {
  width: 100%;
  border-collapse: collapse;
}

.table th,
.table td {
  padding: 10px;
  border: 1px solid #ddd;
  text-align: left;
}

.table th {
  background-color: #f2f2f2;
}

.btn {
  padding: 5px 10px;
  margin-right: 5px;
  text-decoration: none;
  border: none;
  border-radius: 3px;
  cursor: pointer;
}

.btn-primary {
  background-color: #007bff;
  color: white;
}

.btn-warning {
  background-color: #ffc107;
  color: black;
}

.btn-danger {
  background-color: #dc3545;
  color: white;
}

.btn-sm {
  font-size: 12px;
  padding: 3px 8px;
}
</style>
