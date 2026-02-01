<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useTaskStore } from '../../stores/task'
import { ElMessage, ElMessageBox } from 'element-plus'
import { formatDate, statusText, statusColors, crawlTypeText } from '../../utils'

const router = useRouter()
const taskStore = useTaskStore()

// 加载状态
const loading = ref(false)

// 任务列表
const tasks = computed(() => taskStore.taskList)

// 初始加载
onMounted(() => {
  fetchTasks()
})

// 获取任务列表
const fetchTasks = async () => {
  loading.value = true
  await taskStore.fetchTasks()
  loading.value = false
}

// 创建任务
const createTask = () => {
  router.push('/tasks/create')
}

// 编辑任务
const editTask = (id) => {
  router.push(`/tasks/edit/${id}`)
}

// 删除任务
const deleteTask = (id, taskName) => {
  ElMessageBox.confirm(
    `确定要删除任务「${taskName}」吗？`,
    '删除确认',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    const success = await taskStore.deleteTask(id)
    if (success) {
      ElMessage.success('删除成功')
      await fetchTasks()
    } else {
      ElMessage.error('删除失败')
    }
  }).catch(() => {
    // 取消删除
  })
}

// 执行任务
const executeTask = async (id, taskName) => {
  try {
    const success = await taskStore.executeTask(id)
    if (success) {
      ElMessage.success('任务执行已启动')
      await fetchTasks()
    } else {
      ElMessage.error('任务执行失败')
    }
  } catch (error) {
    ElMessage.error('任务执行失败：' + error.message)
  }
}

// 获取状态颜色
const getStatusColor = (status) => {
  return statusColors[status] || 'info'
}

// 获取状态文本
const getStatusText = (status) => {
  return statusText[status] || status
}

// 获取爬取类型文本
const getCrawlTypeText = (type) => {
  return crawlTypeText[type] || type
}
</script>

<template>
  <div class="task-list">
    <!-- 页面标题和操作按钮 -->
    <div class="task-header">
      <h2 class="task-title">爬虫任务管理</h2>
      <el-button type="primary" @click="createTask">创建任务</el-button>
    </div>
    
    <!-- 刷新按钮 -->
    <div class="task-actions">
      <el-button @click="fetchTasks" :loading="loading">
        <el-icon><i class="el-icon-refresh"></i></el-icon>
        刷新列表
      </el-button>
    </div>
    
    <!-- 任务表格 -->
    <el-table
      :data="tasks"
      style="width: 100%"
      border
      stripe
      :loading="loading"
    >
      <el-table-column prop="id" label="任务ID" width="80" />
      <el-table-column prop="taskName" label="任务名称" min-width="180" />
      <el-table-column prop="url" label="目标URL" min-width="250" show-overflow-tooltip />
      <el-table-column prop="crawlType" label="爬取类型" width="120">
        <template #default="scope">
          <el-tag size="small">{{ getCrawlTypeText(scope.row.crawlType) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="120">
        <template #default="scope">
          <el-tag :type="getStatusColor(scope.row.status)" size="small">
            {{ getStatusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180">
        <template #default="scope">
          {{ formatDate(scope.row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column prop="endTime" label="完成时间" width="180">
        <template #default="scope">
          {{ formatDate(scope.row.endTime) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="scope">
          <div class="btn-group">
            <el-button
              type="primary"
              size="small"
              @click="executeTask(scope.row.id, scope.row.taskName)"
              :disabled="scope.row.status === 'running'"
            >
              执行
            </el-button>
            <el-button
              type="success"
              size="small"
              @click="editTask(scope.row.id)"
            >
              编辑
            </el-button>
            <el-button
              type="danger"
              size="small"
              @click="deleteTask(scope.row.id, scope.row.taskName)"
            >
              删除
            </el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    
    <!-- 空状态 -->
    <div v-if="tasks.length === 0 && !loading" class="empty-state">
      <el-empty description="暂无任务" />
      <el-button type="primary" @click="createTask" style="margin-top: 20px">创建第一个任务</el-button>
    </div>
  </div>
</template>

<style scoped>
.task-actions {
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