# myblog



博客展示地址:http://112.74.51.45/



1.博客是一个前后端分离的项目，主要运用到的技术有SpringBoot + Vue +  shiro+jwt + Redis + MyBatisPlus。

前端主要使用element-ui实现搭建，使用axios进行接口通信，使用JSON进行数据交换。登陆功能主要由shiro构建，通过前端传来的数据进行校验，生成javaWebToken返回给前端，作为通行证。



2.运行可以通过docker-compose一键部署到远程服务器，也可以下载到本地，将springboot项目中的激活配置文件改为dev，vue模块中的request包下的baseurl改为localhost即可。

```bash
第一步：npm install #设置环境
第二步: npm run serve #启动vue项目
```



3.效果展示

主界面

![image-20210515224415993](C:\Users\zjm2000\AppData\Roaming\Typora\typora-user-images\image-20210515224415993.png)



登陆界面

![image-20210515224458379](C:\Users\zjm2000\AppData\Roaming\Typora\typora-user-images\image-20210515224458379.png)

登陆后可对文章进行修改和编辑

![image-20210515224522357](C:\Users\zjm2000\AppData\Roaming\Typora\typora-user-images\image-20210515224522357.png)

编辑界面内嵌了MarkDown编辑器，编辑更加方便

![image-20210515224608577](C:\Users\zjm2000\AppData\Roaming\Typora\typora-user-images\image-20210515224608577.png)



4.更多功能，如登陆和评论模块正在开发中，敬请期待....