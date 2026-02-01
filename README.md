# 爬虫服务项目

## 项目简介

本项目是一个基于 Spring Boot 的爬虫服务集合，包含多个模块，分别提供不同类型的爬取功能。

## 模块结构

- **crawl-parent**: 父模块，管理依赖版本
- **crawl-task**: 爬取任务后端服务，负责任务管理、调度和用户认证权限管理
- **crawl-common**: 通用爬取服务，提供静态网页爬取功能
- **crawl-dynamic**: 动态爬取服务，提供基于 Selenium 的动态网页爬取功能
- **crawl-frontend**: 前端服务，提供用户界面，包括任务管理、用户管理、角色管理和权限管理

## 技术栈

### 后端技术栈
- Java 17
- Spring Boot 3.5.10
- Maven 3.8+
- Spring Security (用户认证和权限管理)
- JWT (JSON Web Token)
- MyBatis Plus (ORM 框架)
- MySQL (数据库)
- Jsoup (静态网页解析)
- Apache HttpClient 5 (HTTP 请求)
- Selenium (动态网页爬取)
- Lombok (代码简化)

### 前端技术栈
- Vue 3
- Vite (构建工具)
- Pinia (状态管理)
- Axios (HTTP 客户端)
- Vue Router (路由管理)
- CSS3 (样式)

## 功能特性

### crawl-common 模块
- 静态网页爬取
- HTML 解析
- 链接提取
- 文本提取
- 标题提取

### crawl-dynamic 模块
- 动态网页爬取
- JavaScript 渲染支持
- 浏览器自动化操作

### crawl-task 模块
- 任务管理
- 任务调度
- 数据持久化
- **用户认证和权限管理**
  - 用户登录和注册
  - 角色管理
  - 权限管理
  - 基于角色的访问控制 (RBAC)
  - JWT 认证

### crawl-frontend 模块
- 响应式用户界面
- 任务管理功能
  - 任务列表
  - 创建任务
  - 编辑任务
  - 执行任务
- 用户管理功能
  - 用户列表
  - 创建用户
  - 编辑用户
- 角色管理功能
  - 角色列表
  - 创建角色
  - 编辑角色
- 权限管理功能
  - 权限列表
  - 创建权限
  - 编辑权限
- 用户认证功能
  - 登录页面
  - 注册页面
  - 导航栏和侧边栏

## 快速开始

### 环境要求

### 后端环境要求
- JDK 17 或更高版本
- Maven 3.8 或更高版本
- MySQL 8.0 或更高版本
- Chrome 浏览器 (用于动态爬取)

### 前端环境要求
- Node.js 18 或更高版本
- npm 9 或更高版本

### 安装与运行

#### 后端服务安装与运行

1. **克隆项目**

```bash
git clone <repository-url>
cd crawl
```

2. **配置数据库**

创建 MySQL 数据库 `crawl_task`，并确保数据库用户有足够的权限。

3. **构建项目**

```bash
mvn clean package
```

4. **运行后端服务**

分别运行各个后端模块的应用程序：

```bash
# 运行 crawl-task
java -jar crawl-task/target/crawl-task-1.0.0-SNAPSHOT.jar

# 运行 crawl-common
java -jar crawl-common/target/crawl-common-1.0.0-SNAPSHOT.jar

# 运行 crawl-dynamic
java -jar crawl-dynamic/target/crawl-dynamic-1.0.0-SNAPSHOT.jar
```

#### 前端服务安装与运行

1. **进入前端目录**

```bash
cd crawl-frontend
```

2. **安装依赖**

```bash
npm install
```

3. **运行前端服务**

```bash
npm run dev
```

4. **构建生产版本**

```bash
npm run build
```

## API 接口

### crawl-common 模块

- `GET /api/static-crawl/crawl?url=<url>`: 爬取指定 URL 的网页并提取信息
- `POST /api/static-crawl/extract-title`: 从 HTML 中提取标题
- `POST /api/static-crawl/extract-text`: 从 HTML 中提取文本
- `POST /api/static-crawl/extract-links`: 从 HTML 中提取链接

### crawl-dynamic 模块

- `GET /api/dynamic-crawl/crawl?url=<url>`: 动态爬取指定 URL 的网页
- `POST /api/dynamic-crawl/extract-title`: 从动态渲染的 HTML 中提取标题
- `POST /api/dynamic-crawl/extract-text`: 从动态渲染的 HTML 中提取文本
- `POST /api/dynamic-crawl/extract-links`: 从动态渲染的 HTML 中提取链接

### crawl-task 模块

#### 任务管理相关 API
- `GET /api/tasks`: 获取所有任务
- `GET /api/tasks/{id}`: 获取指定任务
- `POST /api/tasks`: 创建任务
- `PUT /api/tasks/{id}`: 更新任务
- `DELETE /api/tasks/{id}`: 删除任务
- `POST /api/tasks/{id}/execute`: 执行任务

#### 用户认证相关 API
- `POST /api/auth/login`: 用户登录
- `POST /api/auth/register`: 用户注册
- `GET /api/auth/userinfo`: 获取当前用户信息

#### 角色管理相关 API
- `GET /api/roles`: 获取所有角色
- `GET /api/roles/{id}`: 获取指定角色
- `POST /api/roles`: 创建角色
- `PUT /api/roles/{id}`: 更新角色
- `DELETE /api/roles/{id}`: 删除角色
- `GET /api/roles/user/{userId}`: 获取指定用户的角色

#### 权限管理相关 API
- `GET /api/permissions`: 获取所有权限
- `GET /api/permissions/{id}`: 获取指定权限
- `POST /api/permissions`: 创建权限
- `PUT /api/permissions/{id}`: 更新权限
- `DELETE /api/permissions/{id}`: 删除权限
- `GET /api/permissions/role/{roleId}`: 获取指定角色的权限
- `GET /api/permissions/user/{userId}`: 获取指定用户的权限
- `POST /api/permissions/assign`: 为角色分配权限
- `POST /api/permissions/remove`: 从角色中移除权限
- `GET /api/permissions/check`: 检查用户是否有指定权限

## 配置说明

### 数据库配置

在 `crawl-task` 模块的 `application.yml` 文件中配置数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/crawl_task?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    driverClassName: com.mysql.cj.jdbc.Driver
    username: root
    password: 123456
```

### 动态爬取配置

在 `crawl-dynamic` 模块中，可根据需要修改 Selenium 相关配置：

- ChromeDriver 路径
- 浏览器启动参数
- 页面加载超时设置

### 用户认证配置

在 `crawl-task` 模块的 `JwtUtils.java` 文件中配置 JWT 密钥：

```java
private static final String SECRET_KEY = "your-secret-key-here-change-in-production";
```

### 前端配置

在 `crawl-frontend` 模块的 `.env` 文件中配置后端 API 地址：

```
VITE_API_BASE_URL=http://localhost:8080/api
```

## 项目结构

```
crawl/
├── crawl-parent/         # 父模块
├── crawl-task/           # 任务管理模块
│   └── src/main/java/com/crawl/task/
│       ├── client/       # 客户端
│       ├── config/       # 配置
│       ├── controller/   # 控制器
│       ├── entity/       # 实体类
│       ├── mapper/        # Mapper 接口
│       ├── service/      # 服务层
│       │   └── impl/     # 服务实现
│       ├── utils/         # 工具类
│       └── CrawlTaskApplication.java  # 应用入口
├── crawl-common/         # 通用爬取模块
│   └── src/main/java/com/crawl/common/
│       ├── controller/   # 控制器
│       ├── service/      # 服务层
│       ├── util/         # 工具类
│       └── CrawlCommonApplication.java  # 应用入口
├── crawl-dynamic/        # 动态爬取模块
│   └── src/main/java/com/crawl/dynamic/
│       ├── controller/   # 控制器
│       ├── service/      # 服务层
│       ├── util/         # 工具类
│       └── CrawlDynamicApplication.java  # 应用入口
├── crawl-frontend/       # 前端服务模块
│   ├── public/           # 静态资源
│   ├── src/              # 源代码
│   │   ├── api/          # API 调用
│   │   ├── assets/       # 静态资源
│   │   ├── components/   # 组件
│   │   ├── router/       # 路由
│   │   ├── stores/       # 状态管理
│   │   ├── utils/        # 工具类
│   │   ├── views/        # 页面
│   │   │   ├── auth/      # 认证相关页面
│   │   │   ├── tasks/     # 任务管理页面
│   │   │   ├── users/     # 用户管理页面
│   │   │   ├── roles/     # 角色管理页面
│   │   │   └── permissions/ # 权限管理页面
│   │   ├── App.vue        # 根组件
│   │   ├── main.js        # 入口文件
│   │   └── style.css      # 全局样式
│   ├── .gitignore
│   ├── README.md
│   ├── index.html
│   ├── package-lock.json
│   ├── package.json
│   └── vite.config.js
└── README.md             # 项目说明文档
```

## 开发指南

### 添加新功能

1. 在对应模块中创建新的类或修改现有类
2. 遵循项目的代码风格和命名规范
3. 添加适当的注释
4. 运行测试确保功能正常

### 测试

```bash
mvn test
```

## 注意事项

- 请遵守网站的 robots.txt 规则
- 合理控制爬取频率，避免对目标网站造成压力
- 仅用于合法用途，遵守相关法律法规

## 许可证

本项目采用 MIT 许可证。

## 联系方式

如有问题或建议，欢迎联系项目维护者。