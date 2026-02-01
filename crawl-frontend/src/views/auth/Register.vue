<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../../stores/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

// 表单数据
const form = reactive({
  username: '',
  password: '',
  confirmPassword: '',
  email: ''
})

// 表单验证规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在 3 到 20 之间', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度至少 6 位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== form.password) {
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
  ]
}

// 表单引用
const formRef = ref(null)

// 加载状态
const loading = ref(false)

// 注册方法
const register = async () => {
  // 表单验证
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        const success = await userStore.register({
          username: form.username,
          password: form.password,
          email: form.email
        })
        if (success) {
          ElMessage.success('注册成功，请登录')
          router.push('/login')
        } else {
          ElMessage.error(userStore.error || '注册失败')
        }
      } catch (error) {
        ElMessage.error('注册失败，请检查网络连接')
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<template>
  <div class="register-container">
    <h2 class="register-title">用户注册</h2>
    
    <el-form
      ref="formRef"
      :model="form"
      :rules="rules"
      label-width="80px"
      class="register-form"
    >
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" placeholder="请输入用户名" />
      </el-form-item>
      
      <el-form-item label="密码" prop="password">
        <el-input
          v-model="form.password"
          type="password"
          placeholder="请输入密码"
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
      
      <el-form-item>
        <el-button
          type="primary"
          @click="register"
          :loading="loading"
          style="width: 100%"
        >
          注册
        </el-button>
      </el-form-item>
      
      <div class="form-footer">
        <span>已有账号？</span>
        <el-button type="text" @click="router.push('/login')">立即登录</el-button>
      </div>
    </el-form>
  </div>
</template>

<style scoped>
.register-form {
  margin-top: 20px;
}

.form-footer {
  margin-top: 20px;
  text-align: center;
  font-size: 14px;
}

.form-footer span {
  margin-right: 8px;
}
</style>