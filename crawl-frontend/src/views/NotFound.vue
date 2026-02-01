<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const goHome = () => {
  router.push('/')
}
</script>

<template>
  <div class="not-found">
    <div class="error-container">
      <h1 class="error-code">404</h1>
      <h2 class="error-message">页面未找到</h2>
      <p class="error-description">
        您访问的页面不存在或已被删除
      </p>
      <el-button type="primary" @click="goHome">返回首页</el-button>
    </div>
  </div>
</template>

<style scoped>
.not-found {
  min-height: 80vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f5f7fa;
}

.error-container {
  text-align: center;
  padding: 60px 40px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  max-width: 400px;
  width: 100%;
}

.error-code {
  font-size: 72px;
  font-weight: 700;
  color: #1890ff;
  margin-bottom: 20px;
}

.error-message {
  font-size: 24px;
  font-weight: 600;
  margin-bottom: 16px;
  color: #333;
}

.error-description {
  font-size: 16px;
  color: #666;
  margin-bottom: 32px;
  line-height: 1.5;
}
</style>