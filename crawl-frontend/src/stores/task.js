import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import axios from 'axios'

export const useTaskStore = defineStore('task', () => {
  // 状态
  const tasks = ref([])
  const currentTask = ref(null)
  const loading = ref(false)
  const error = ref('')

  // 计算属性
  const taskList = computed(() => tasks.value)
  const taskDetails = computed(() => currentTask.value)

  // 动作
  const fetchTasks = async () => {
    loading.value = true
    error.value = ''
    try {
      // 这里需要替换为实际的后端获取任务列表 API
      const response = await axios.get('http://localhost:8080/api/tasks')
      tasks.value = response.data
    } catch (err) {
      error.value = err.response?.data?.message || '获取任务列表失败'
      console.error('获取任务列表失败', err)
    } finally {
      loading.value = false
    }
  }

  const fetchTaskById = async (id) => {
    loading.value = true
    error.value = ''
    try {
      // 这里需要替换为实际的后端获取任务详情 API
      const response = await axios.get(`http://localhost:8080/api/tasks/${id}`)
      currentTask.value = response.data
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || '获取任务详情失败'
      console.error('获取任务详情失败', err)
      return null
    } finally {
      loading.value = false
    }
  }

  const createTask = async (taskData) => {
    loading.value = true
    error.value = ''
    try {
      // 这里需要替换为实际的后端创建任务 API
      const response = await axios.post('http://localhost:8080/api/tasks', taskData)
      tasks.value.push(response.data)
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || '创建任务失败'
      console.error('创建任务失败', err)
      return null
    } finally {
      loading.value = false
    }
  }

  const updateTask = async (id, taskData) => {
    loading.value = true
    error.value = ''
    try {
      // 这里需要替换为实际的后端更新任务 API
      const response = await axios.put(`http://localhost:8080/api/tasks/${id}`, taskData)
      const index = tasks.value.findIndex(task => task.id === id)
      if (index !== -1) {
        tasks.value[index] = response.data
      }
      if (currentTask.value && currentTask.value.id === id) {
        currentTask.value = response.data
      }
      return response.data
    } catch (err) {
      error.value = err.response?.data?.message || '更新任务失败'
      console.error('更新任务失败', err)
      return null
    } finally {
      loading.value = false
    }
  }

  const deleteTask = async (id) => {
    loading.value = true
    error.value = ''
    try {
      // 这里需要替换为实际的后端删除任务 API
      await axios.delete(`http://localhost:8080/api/tasks/${id}`)
      tasks.value = tasks.value.filter(task => task.id !== id)
      if (currentTask.value && currentTask.value.id === id) {
        currentTask.value = null
      }
      return true
    } catch (err) {
      error.value = err.response?.data?.message || '删除任务失败'
      console.error('删除任务失败', err)
      return false
    } finally {
      loading.value = false
    }
  }

  const executeTask = async (id) => {
    loading.value = true
    error.value = ''
    try {
      // 这里需要替换为实际的后端执行任务 API
      await axios.post(`http://localhost:8080/api/tasks/${id}/execute`)
      // 执行任务后刷新任务列表
      await fetchTasks()
      return true
    } catch (err) {
      error.value = err.response?.data?.message || '执行任务失败'
      console.error('执行任务失败', err)
      return false
    } finally {
      loading.value = false
    }
  }

  return {
    tasks,
    currentTask,
    loading,
    error,
    taskList,
    taskDetails,
    fetchTasks,
    fetchTaskById,
    createTask,
    updateTask,
    deleteTask,
    executeTask
  }
})