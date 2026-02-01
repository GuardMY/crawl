# 爬虫服务项目

## 项目简介

本项目是一个基于 Spring Boot 的爬虫服务集合，包含多个模块，分别提供不同类型的爬取功能。

## 模块结构

- **crawl-parent**: 父模块，管理依赖版本
- **crawl-task**: 爬取任务后端服务，负责任务管理和调度
- **crawl-common**: 通用爬取服务，提供静态网页爬取功能
- **crawl-dynamic**: 动态爬取服务，提供基于 Selenium 的动态网页爬取功能

## 技术栈

- Java 17
- Spring Boot 3.5.10
- Maven 3.8+
- Jsoup (静态网页解析)
- Apache HttpClient 5 (HTTP 请求)
- Selenium (动态网页爬取)
- Lombok (代码简化)

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

## 快速开始

### 环境要求

- JDK 17 或更高版本
- Maven 3.8 或更高版本
- Chrome 浏览器 (用于动态爬取)

### 安装与运行

1. **克隆项目**

```bash
git clone <repository-url>
cd crawl
```

2. **构建项目**

```bash
mvn clean package
```

3. **运行服务**

分别运行各个模块的应用程序：

```bash
# 运行 crawl-task
java -jar crawl-task/target/crawl-task-1.0.0-SNAPSHOT.jar

# 运行 crawl-common
java -jar crawl-common/target/crawl-common-1.0.0-SNAPSHOT.jar

# 运行 crawl-dynamic
java -jar crawl-dynamic/target/crawl-dynamic-1.0.0-SNAPSHOT.jar
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

## 配置说明

### 动态爬取配置

在 `crawl-dynamic` 模块中，可根据需要修改 Selenium 相关配置：

- ChromeDriver 路径
- 浏览器启动参数
- 页面加载超时设置

## 项目结构

```
crawl/
├── crawl-parent/         # 父模块
├── crawl-task/           # 任务管理模块
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