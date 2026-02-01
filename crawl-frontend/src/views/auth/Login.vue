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
  password: ''
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
  ]
}

// 表单引用
const formRef = ref(null)

// 加载状态
const loading = ref(false)

// 登录方法
const login = async () => {
  // 表单验证
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        const success = await userStore.login(form.username, form.password)
        if (success) {
          ElMessage.success('登录成功')
          router.push('/tasks')
        } else {
          ElMessage.error(userStore.error || '登录失败')
        }
      } catch (error) {
        ElMessage.error('登录失败，请检查网络连接')
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<template>
  <div class="login-container">
    <h2 class="login-title">用户登录</h2>
    
    <el-form
      ref="formRef"
      :model="form"
      :rules="rules"
      label-width="80px"
      class="login-form"
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
      
      <el-form-item>
        <el-button
          type="primary"
          @click="login"
          :loading="loading"
          style="width: 100%"
        >
          登录
        </el-button>
      </el-form-item>
      
      <div class="form-footer">
        <span>还没有账号？</span>
        <el-button type="text" @click="router.push('/register')">立即注册</el-button>
      </div>
    </el-form>
  </div>
</template>

<style scoped>
.login-form {
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