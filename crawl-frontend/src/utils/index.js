// 日期格式化函数
export const formatDate = (date) => {
  if (!date) return ''
  const d = new Date(date)
  return d.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit'
  })
}

// 错误处理函数
export const handleError = (error) => {
  console.error('错误:', error)
  // 这里可以添加错误提示逻辑，如使用 Element Plus 的 Message 组件
  return error.message || '操作失败'
}

// 本地存储操作
export const storage = {
  // 设置存储
  set: (key, value) => {
    try {
      localStorage.setItem(key, JSON.stringify(value))
    } catch (error) {
      console.error('存储数据失败:', error)
    }
  },
  
  // 获取存储
  get: (key, defaultValue = null) => {
    try {
      const value = localStorage.getItem(key)
      return value ? JSON.parse(value) : defaultValue
    } catch (error) {
      console.error('获取数据失败:', error)
      return defaultValue
    }
  },
  
  // 删除存储
  remove: (key) => {
    try {
      localStorage.removeItem(key)
    } catch (error) {
      console.error('删除数据失败:', error)
    }
  },
  
  // 清空存储
  clear: () => {
    try {
      localStorage.clear()
    } catch (error) {
      console.error('清空数据失败:', error)
    }
  }
}

// 防抖函数
export const debounce = (func, wait) => {
  let timeout
  return function executedFunction(...args) {
    const later = () => {
      clearTimeout(timeout)
      func(...args)
    }
    clearTimeout(timeout)
    timeout = setTimeout(later, wait)
  }
}

// 节流函数
export const throttle = (func, limit) => {
  let inThrottle
  return function executedFunction(...args) {
    if (!inThrottle) {
      func.apply(this, args)
      inThrottle = true
      setTimeout(() => inThrottle = false, limit)
    }
  }
}

// 验证函数
export const validate = {
  // 验证用户名
  username: (username) => {
    return username && username.length >= 3 && username.length <= 20
  },
  
  // 验证密码
  password: (password) => {
    return password && password.length >= 6
  },
  
  // 验证邮箱
  email: (email) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    return emailRegex.test(email)
  },
  
  // 验证URL
  url: (url) => {
    try {
      new URL(url)
      return true
    } catch {
      return false
    }
  }
}

// 状态颜色映射
export const statusColors = {
  pending: 'warning',
  running: 'info',
  completed: 'success',
  failed: 'danger'
}

// 状态文本映射
export const statusText = {
  pending: '待执行',
  running: '执行中',
  completed: '已完成',
  failed: '执行失败'
}

// 爬取类型映射
export const crawlTypeText = {
  static: '静态爬取',
  dynamic: '动态爬取'
}