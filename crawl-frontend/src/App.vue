<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from './stores/user'

const router = useRouter()
const userStore = useUserStore()

const isLoggedIn = computed(() => userStore.isLoggedIn)
const userInfo = computed(() => userStore.userInfo)

const logout = () => {
  userStore.logout()
  router.push('/login')
}
</script>

<template>
  <div class="app-container">
    <!-- 登录页面不需要布局 -->
    <template v-if="!isLoggedIn && (router.currentRoute.name === 'Login' || router.currentRoute.name === 'Register')">
      <router-view />
    </template>
    
    <!-- 其他页面需要布局 -->
    <template v-else>
      <!-- 顶部导航栏 -->
      <header class="app-header">
        <div class="header-left">
          <h1 class="app-title">爬虫任务管理系统</h1>
        </div>
        <div class="header-right">
          <template v-if="isLoggedIn">
            <span class="user-info">欢迎，{{ userInfo?.username || '用户' }}</span>
            <el-button type="primary" @click="logout">退出登录</el-button>
          </template>
          <template v-else>
            <el-button type="primary" @click="router.push('/login')">登录</el-button>
          </template>
        </div>
      </header>
      
      <!-- 主内容区 -->
      <main class="app-main">
        <!-- 侧边栏 -->
        <aside class="app-sidebar">
          <nav class="sidebar-nav">
            <el-menu
              :default-active="router.currentRoute.path"
              class="el-menu-vertical-demo"
              router
            >
              <el-menu-item index="/tasks">
                <el-icon><i class="el-icon-document"></i></el-icon>
                <span>任务管理</span>
              </el-menu-item>
              <el-menu-item index="/users">
                <el-icon><i class="el-icon-user"></i></el-icon>
                <span>用户管理</span>
              </el-menu-item>
            </el-menu>
          </nav>
        </aside>
        
        <!-- 内容区 -->
        <section class="app-content">
          <router-view />
        </section>
      </main>
    </template>
  </div>
</template>

<style>
/* 全局样式重置 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'PingFang SC', 'Helvetica Neue', Arial, sans-serif;
  font-size: 14px;
  line-height: 1.5;
  color: #333;
  background-color: #f5f7fa;
}

/* 应用容器 */
.app-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

/* 头部导航栏 */
.app-header {
  height: 60px;
  background-color: #1890ff;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  position: sticky;
  top: 0;
  z-index: 100;
}

.app-title {
  font-size: 18px;
  font-weight: 600;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.user-info {
  font-size: 14px;
}

/* 主内容区 */
.app-main {
  flex: 1;
  display: flex;
  min-height: 0;
}

/* 侧边栏 */
.app-sidebar {
  width: 200px;
  background-color: #fff;
  border-right: 1px solid #e8e8e8;
  flex-shrink: 0;
}

.sidebar-nav {
  padding: 16px 0;
}

/* 内容区 */
.app-content {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
}

/* 登录/注册页面样式 */
.login-container,
.register-container {
  max-width: 400px;
  margin: 100px auto;
  padding: 32px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.login-title,
.register-title {
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 24px;
  text-align: center;
  color: #1890ff;
}

/* 表单样式 */
.form-item {
  margin-bottom: 20px;
}

.form-label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
}

/* 任务管理页面样式 */
.task-list {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.09);
  padding: 20px;
}

.task-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.task-title {
  font-size: 18px;
  font-weight: 600;
}

/* 表格样式 */
.el-table {
  margin-top: 20px;
}

/* 按钮样式 */
.btn-group {
  display: flex;
  gap: 8px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .app-sidebar {
    width: 180px;
  }
  
  .app-content {
    padding: 16px;
  }
  
  .login-container,
  .register-container {
    margin: 50px auto;
    padding: 24px;
  }
}
</style>
