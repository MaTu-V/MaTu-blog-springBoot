# 码途博客后端
> 2019/10/16完成数据库表设计
  >> 完成基础环境搭建
  1. 项目结构->聚合项目工程（便于后续拓展）。
  2. 配置各项依赖。
   
      | 文件名称 | 意义 | 依赖关系 | 打包方式 |
      | -------- | -------- | -------- | --------|
      | *mt-blog* | 项目父工程 | 依赖springBoot父工程 | pom |
      | *mt-commons* | 子工程(存放公共依赖) | 依赖mt-blog父工程 | jar |
      | *mt-pojo* | 子工程(存放对应实体类) | 依赖mt-blog、mt-commons父工程 | jar |
      | *mt-utils* | 项目父工程(工具类) | 依赖mt-blog、mt-commons父工程 | jar |
      | *mt-mapper* | 子工程(数据访问层) | 依赖mt-blog、mt-pojo父工程 | jar |
      | *mt-service* | 子工程(业务逻辑层) | 依赖mt-blog、mt-mapper、mt-utils父工程 | jar |
      | *mt-web* | 子工程(视图解析层) | 依赖mt-blog、mt-service、mt-utils父工程 | jar |
      | *images* | 存放运行截图 | 无任何依赖 | 无 |
    
   3. 完成实体类Pojo建立。
   4. 项目工程图片
       + ![这是搭建的聚合项目工程目录](https://raw.githubusercontent.com/MaTu-V/MaTu-blog-springBoot/master/images/screenshot.png) 
> 2019/10/17 完成友链功能
  1. 完成友链增删改查 具体见 `LinkController`、`LinkService`、`LinkMapper`。
  2. 项目测试,书写`README.md`。
> 2019/10/19 完成分类功能、标签功能、用户背景功能
  1. 完成分类增删改查 具体见 `CategoryController`,`CategoryService`,`CategoryMapper`。
  2. 完成标签增删改查 具体见 `LabelController`,`LabelService`,`LabelMapper`。
  3. 完成用户背景增删改查 具体见 `UserBackController`,`UserBackService`,`UserBackMapper`。
> 2019/12/24
  1. 完成  
> 2020/2/15
  2. 阿里云服务器识别未通过,暂时无法部署.  