## 基础功能及规则
所有角色用户登录操作
学生默认账号密码均为学号
为保证安全，登录时检测到账号密码相同会要求修改密码，可忽略
点击左上角个人姓名，退出/修改密码
用户身份角色加密，无法篡改；基于解密的身份完成用户独有操作
密码加密保存请放心
未登录跳转登录页面，不再单写角色加载，全局守卫控制角色下路由路径
## 学生
未开始，弹出开始时间说明，且无法加载教师列表，更无法选择教师
开始后未选择，加载教师列表，数量已满教师无法选择操作；后端保证并发操作的数据完整性； 选择请求逻辑，处理，学生不存在/教师不存在/教师数量已满(独占)/重复选择/开始前提交选择等逻辑判断

开始后已选择，选择后不可更改；不再加载教师列表，确保无法再次选择，同时避免学生反复查看
后端对选择请求验证，拒绝已选学生再次发出的选择请求
## 教师
查看已选自己学生；查看所有未选择学生；导出学生指导教师excel表格
## 管理员
独立角色，无教师操作权限
设置开始时间；导入学生名单；添加教师(姓名/账号/数量)；重置学生/教师密码
## 技术栈
springboot/spring-data-jdbc/mysql
####
springboot框架下的springmvc拦截器
####
springboot框架下的spring aop
## 详细说明
对请求的拦截我使用了springmvc拦截器，非登录状态下除了登录请求可以放行，其他请求都会被拦截；登录成功后会将登录信息通过JWT加密（确保传输过程中不会被篡改）保存到请求响应的头部中，以供后续请求或者前端使用。
####
登录状态下所有的请求还是会被拦截下来，先进行JWT解密，将解密信息塞到请求中，后续就可以直接get，原因是要对后续请求进行权限验证。由于写之前没考虑清楚，
对教师端的权限验证是每一个功能都进行一遍验证，导致代码冗余，所以后来对管理员端采用了aop统一管理，只要是管理员端的功能
都会通过aop进行一次权限判断
####
最后是时间处理也采用了aop统一管理，开启aop后，管理员首先就要设置开始时间和结束时间，
在这段时间之外，学生端的所有功能都是被禁止的
## 修改
1. 对于controller层来说，完全可以不用抛异常，而是直接返回ResultVo的视图，如果有各种异常，完全可以在service层进行控制
2. 对于controller层的权限验证来说，由于拦截器的存在，它就是多余的，根本就不用在controller层再次进行权限验证
3. 对于使用了@Slf4j注解没有输出的问题，注意application.yml文件中logging:
   level:root:warn
4. 对登录成功后的视图输出修改了一下，更直观的看出各个用户的状况
5. 注意@RequestParam和@PathVariable的使用区别
6. 悲观锁来说还是有很大的效率问题，可以改善。update语句在更新某个列时，会进行加锁，以确保其他事务在同一时间不会对该行进行读取或修改，所以就不用加for update悲观锁，而是直接使用update语句。
7. 对于请求命名已经全部修改为rest风格
