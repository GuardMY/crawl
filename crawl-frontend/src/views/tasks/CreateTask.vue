<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useTaskStore } from '../../stores/task'
import { ElMessage } from 'element-plus'
import { validate } from '../../utils'

const router = useRouter()
const taskStore = useTaskStore()

// 表单数据
const form = reactive({
  taskName: '',
  url: '',
  crawlType: 'static' // 默认静态爬取
})

// 表单验证规则
const rules = {
  taskName: [
    { required: true, message: '请输入任务名称', trigger: 'blur' },
    { min: 2, max: 50, message: '任务名称长度在 2 到 50 之间', trigger: 'blur' }
  ],
  url: [
    { required: true, message: '请输入目标 URL', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (!validate.url(value)) {
          callback(new Error('请输入有效的 URL 地址'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ],
  crawlType: [
    { required: true, message: '请选择爬取类型', trigger: 'change' }
  ]
}

// 表单引用
const formRef = ref(null)

// 加载状态
const loading = ref(false)

// 提交表单
const submitForm = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        const task = await taskStore.createTask(form)
        if (task) {
          ElMessage.success('创建成功')
          router.push('/tasks')
        } else {
          ElMessage.error('创建失败')
        }
      } catch (error) {
        ElMessage.error('创建失败：' + error.message)
      } finally {
        loading.value = false
      }
    }
  })
}

// 取消
const cancel = () => {
  router.push('/tasks')
}
</script>

<template>
  <div class="task-create">
    <h2 class="page-title">创建爬虫任务</h2>
    
    <el-form
      ref="formRef"
      :model="form"
      :rules="rules"
      label-width="120px"
      class="create-form"
    >
      <el-form-item label="任务名称" prop="taskName">
        <el-input v-model="form.taskName" placeholder="请输入任务名称" />
      </el-form-item>
      
      <el-form-item label="目标 URL" prop="url">
        <el-input v-model="form.url" placeholder="请输入目标 URL" />
      </el-form-item>
      
      <el-form-item label="爬取类型" prop="crawlType">
        <el-radio-group v-model="form.crawlType">
          <el-radio label="static">静态爬取</el-radio>
          <el-radio label="dynamic">动态爬取</el-radio>
        </el-radio-group>
      </el-form-item>
      
      <el-form-item>
        <el-button type="primary" @click="submitForm" :loading="loading">
          创建任务
        </el-button>
        <el-button @click="cancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped>
.task-create {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.09);
  padding: 30px;
}

.page-title {
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 24px;
  color: #1890ff;
}

.create-form {
  max-width: 600px;
}
</style>