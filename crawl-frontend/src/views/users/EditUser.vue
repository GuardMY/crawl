<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { validate } from '../../utils'

const router = useRouter()
const route = useRoute()

// 用户 ID
const userId = ref(route.params.id)

// 表单数据
const form = reactive({
  username: '',
  password: '',
  confirmPassword: '',
  email: '',
  role: 'user'
})

// 表单验证规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在 3 到 20 之间', trigger: 'blur' }
  ],
  password: [
    { min: 6, message: '密码长度至少 6 位', trigger: 'blur' }
  ],
  confirmPassword: [
    {
      validator: (rule, value, callback) => {
        if (value && value !== form.password) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入有效的邮箱地址', trigger: 'blur' }
  ],
  role: [
    { required: true, message: '请选择角色', trigger: 'change' }
  ]
}

// 表单引用
const formRef = ref(null)

// 加载状态
const loading = ref(false)
const userLoading = ref(true)

// 初始加载
onMounted(() => {
  loadUserDetail()
})

// 加载用户详情
const loadUserDetail = async () => {
  userLoading.value = true
  try {
    // 实际项目中应该调用 API 获取用户详情
    // 这里使用模拟数据
    setTimeout(() => {
      // 模拟用户数据
      form.username = 'user1'
      form.email = 'user1@example.com'
      form.role = 'user'
      userLoading.value = false
    }, 500)
  } catch (error) {
    ElMessage.error('加载用户失败')
    router.push('/users')
  }
}

// 提交表单
const submitForm = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        // 实际项目中应该调用 API 更新用户
        // 这里使用模拟数据
        setTimeout(() => {
          ElMessage.success('更新成功')
          router.push('/users')
          loading.value = false
        }, 500)
      } catch (error) {
        ElMessage.error('更新失败：' + error.message)
        loading.value = false
      }
    }
  })
}

// 取消
const cancel = () => {
  router.push('/users')
}
</script>

<template>
  <div class="user-edit">
    <h2 class="page-title">编辑用户</h2>
    
    <el-form
      v-if="!userLoading"
      ref="formRef"
      :model="form"
      :rules="rules"
      label-width="120px"
      class="edit-form"
    >
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" placeholder="请输入用户名" />
      </el-form-item>
      
      <el-form-item label="密码" prop="password">
        <el-input
          v-model="form.password"
          type="password"
          placeholder="请输入密码（留空表示不修改）"
          show-password
        />
      </el-form-item>
      
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input
          v-model="form.confirmPassword"
          type="password"
          placeholder="请确认密码"
          show-password
        />
      </el-form-item>
      
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" placeholder="请输入邮箱" />
      </el-form-item>
      
      <el-form-item label="角色" prop="role">
        <el-radio-group v-model="form.role">
          <el-radio label="user">普通用户</el-radio>
          <el-radio label="admin">管理员</el-radio>
        </el-radio-group>
      </el-form-item>
      
      <el-form-item>
        <el-button type="primary" @click="submitForm" :loading="loading">
          保存修改
        </el-button>
        <el-button @click="cancel">取消</el-button>
      </el-form-item>
    </el-form>
    
    <!-- 加载中 -->
    <div v-else class="loading-state">
      <el-loading v-loading="userLoading" element-loading-text="加载用户中..." />
    </div>
  </div>
</template>

<style scoped>
.user-edit {
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

.edit-form {
  max-width: 600px;
}

.loading-state {
  height: 400px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>