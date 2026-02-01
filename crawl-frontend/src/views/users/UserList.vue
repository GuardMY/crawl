<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { formatDate } from '../../utils'

const router = useRouter()

// 模拟用户数据，实际项目中应该从后端 API 获取
const users = ref([
  {
    id: 1,
    username: 'admin',
    email: 'admin@example.com',
    role: 'admin',
    createTime: new Date('2024-01-01 10:00:00')
  },
  {
    id: 2,
    username: 'user1',
    email: 'user1@example.com',
    role: 'user',
    createTime: new Date('2024-01-02 11:00:00')
  }
])

// 加载状态
const loading = ref(false)

// 获取用户列表
const fetchUsers = async () => {
  loading.value = true
  // 实际项目中应该调用 API 获取用户列表
  // 这里使用模拟数据
  setTimeout(() => {
    loading.value = false
  }, 500)
}

// 初始加载
onMounted(() => {
  fetchUsers()
})

// 创建用户
const createUser = () => {
  router.push('/users/create')
}

// 编辑用户
const editUser = (id) => {
  router.push(`/users/edit/${id}`)
}

// 删除用户
const deleteUser = (id, username) => {
  ElMessageBox.confirm(
    `确定要删除用户「${username}」吗？`,
    '删除确认',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(() => {
    // 实际项目中应该调用 API 删除用户
    // 这里使用模拟数据
    const index = users.value.findIndex(user => user.id === id)
    if (index !== -1) {
      users.value.splice(index, 1)
      ElMessage.success('删除成功')
    } else {
      ElMessage.error('删除失败')
    }
  }).catch(() => {
    // 取消删除
  })
}
</script>

<template>
  <div class="user-list">
    <!-- 页面标题和操作按钮 -->
    <div class="user-header">
      <h2 class="user-title">用户管理</h2>
      <el-button type="primary" @click="createUser">创建用户</el-button>
    </div>
    
    <!-- 刷新按钮 -->
    <div class="user-actions">
      <el-button @click="fetchUsers" :loading="loading">
        <el-icon><i class="el-icon-refresh"></i></el-icon>
        刷新列表
      </el-button>
    </div>
    
    <!-- 用户表格 -->
    <el-table
      :data="users"
      style="width: 100%"
      border
      stripe
      :loading="loading"
    >
      <el-table-column prop="id" label="用户ID" width="80" />
      <el-table-column prop="username" label="用户名" min-width="150" />
      <el-table-column prop="email" label="邮箱" min-width="200" show-overflow-tooltip />
      <el-table-column prop="role" label="角色" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.role === 'admin' ? 'danger' : 'success'" size="small">
            {{ scope.row.role === 'admin' ? '管理员' : '普通用户' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180">
        <template #default="scope">
          {{ formatDate(scope.row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right">
        <template #default="scope">
          <div class="btn-group">
            <el-button
              type="success"
              size="small"
              @click="editUser(scope.row.id)"
            >
              编辑
            </el-button>
            <el-button
              type="danger"
              size="small"
              @click="deleteUser(scope.row.id, scope.row.username)"
              :disabled="scope.row.username === 'admin'"
            >
              删除
            </el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    
    <!-- 空状态 -->
    <div v-if="users.length === 0 && !loading" class="empty-state">
      <el-empty description="暂无用户" />
      <el-button type="primary" @click="createUser" style="margin-top: 20px">创建第一个用户</el-button>
    </div>
  </div>
</template>

<style scoped>
.user-list {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.09);
  padding: 20px;
}

.user-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.user-title {
  font-size: 18px;
  font-weight: 600;
}

.user-actions {
  margin-bottom: 16px;
}

.empty-state {
  text-align: center;
  padding: 60px 0;
}

.btn-group {
  display: flex;
  gap: 8px;
}
</style>