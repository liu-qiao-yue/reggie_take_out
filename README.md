# 黑马程序-瑞吉外卖项目
后端网址：http://localhost:8080/backend/page/login/login.html

前端网址：http://localhost:8080/front/index.html

gitee: [reggie](https://gitee.com/zhuang_zhanrong/reggie_take_out/tree/master)

## 增加功能
1. 密码加密采取`PasswordEncoder`加密，通过调用`PasswordUtil.encodePassword`加密，`PasswordUtil.matches`比对新旧里面，不对加密密码解密
2. 调整业务代码位置，`controller`层仅为API入口，不处理逻辑，`service`层处理与DB的交互，纯业务逻辑（不涉及数据库操作）代码可以在`domain`层处理
3. 对于返回到前端的问题信息全部存放在`BizExceptionEnum`
4. 增加自定义注解`@MaskInfo`，对于`password`, `idNumber`等一些敏感信息进行隐藏，不对前端交互 
5. 自动填充中的ID可以通过使用`SpringSecurity`获得
6. 菜品管理中口味做法配置做成动态数据flavor和flavor_detail，通过`flavor`表和`flavor_detail`表进行关联，通过`flavor`表中的`flavorId`查询`flavor_detail`表中的口味做法信息
7. 前端页面菜品管理中口味配置 使用 `el-select` 
8. 添加用户时保证userName唯一可以优化前端验证 -- 待完成
9. 忘记密码功能，更新密码功能，六个月密码过期提示功能 -- 待完成
10. 添加接口文档 -- 进行中
11. 使用VUE3 + TS 重写前端页面 -- 待完成
12. 实现多数据源查询 -- 待完成
13. 添加发送消息功能 MQ? -- 待完成
14. 使用String Security + JWT -- 待完成