import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../stores/user'

// 路由配置
const routes = [
  {
    path: '/',
    redirect: '/tasks'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/auth/Login.vue'),
    meta: { requiresAuth: false }
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/auth/Register.vue'),
    meta: { requiresAuth: false }
  },
  {
    path: '/tasks',
    name: 'Tasks',
    component: () => import('../views/tasks/TaskList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/tasks/create',
    name: 'CreateTask',
    component: () => import('../views/tasks/CreateTask.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/tasks/edit/:id',
    name: 'EditTask',
    component: () => import('../views/tasks/EditTask.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/users',
    name: 'Users',
    component: () => import('../views/users/UserList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/users/create',
    name: 'CreateUser',
    component: () => import('../views/users/CreateUser.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/users/edit/:id',
    name: 'EditUser',
    component: () => import('../views/users/EditUser.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/roles',
    name: 'Roles',
    component: () => import('../views/roles/RoleList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/roles/create',
    name: 'CreateRole',
    component: () => import('../views/roles/CreateRole.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/roles/edit/:id',
    name: 'EditRole',
    component: () => import('../views/roles/EditRole.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/permissions',
    name: 'Permissions',
    component: () => import('../views/permissions/PermissionList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/permissions/create',
    name: 'CreatePermission',
    component: () => import('../views/permissions/CreatePermission.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/permissions/edit/:id',
    name: 'EditPermission',
    component: () => import('../views/permissions/EditPermission.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('../views/NotFound.vue'),
    meta: { requiresAuth: false }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth)
  
  if (requiresAuth && !userStore.isLoggedIn) {
    next({ name: 'Login' })
  } else {
    next()
  }
})

export default router