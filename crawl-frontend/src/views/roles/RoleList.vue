<template>
  <div class="role-list">
    <h1>角色管理</h1>
    <div class="action-bar">
      <router-link to="/roles/create" class="btn btn-primary">创建角色</router-link>
    </div>
    <table class="table table-striped">
      <thead>
        <tr>
          <th>ID</th>
          <th>角色名称</th>
          <th>描述</th>
          <th>创建时间</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="role in roles" :key="role.id">
          <td>{{ role.id }}</td>
          <td>{{ role.roleName }}</td>
          <td>{{ role.description }}</td>
          <td>{{ formatDate(role.createTime) }}</td>
          <td>
            <router-link :to="`/roles/edit/${role.id}`" class="btn btn-sm btn-warning">编辑</router-link>
            <button @click="deleteRole(role.id)" class="btn btn-sm btn-danger">删除</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import apiService from '../../api'
import { useUserStore } from '../../stores/user'

const router = useRouter()
const userStore = useUserStore()
const roles = ref([])
const loading = ref(false)
const error = ref('')

const fetchRoles = async () => {
  loading.value = true
  try {
    roles.value = await apiService.roles.getList()
  } catch (err) {
    error.value = err.message || '获取角色列表失败'
  } finally {
    loading.value = false
  }
}

const deleteRole = async (id) => {
  if (confirm('确定要删除这个角色吗？')) {
    try {
      await apiService.roles.delete(id)
      // 重新获取角色列表
      await fetchRoles()
    } catch (err) {
      error.value = err.message || '删除角色失败'
    }
  }
}

const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleString()
}

onMounted(() => {
  fetchRoles()
})
</script>

<style scoped>
.role-list {
  max-width: 800px;
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
