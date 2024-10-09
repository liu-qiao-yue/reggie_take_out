# 黑马程序-瑞吉外卖项目

## 增加功能
1. 密码加密采取`PasswordEncoder`加密，通过调用`PasswordUtil.encodePassword`加密，`PasswordUtil.matches`比对新旧里面，不对加密密码解密
2. 调整业务代码位置，`controller`层仅为API入口，不处理逻辑，`service`层处理与DB的交互，纯业务逻辑（不涉及数据库操作）代码可以在`domain`层处理
3. 对于返回到前端的问题信息全部存放在`MessageInfo`
4. 增加自定义注解@MaskInfo，对于password, idNumber等一些敏感信息进行隐藏，不对前端交互
5. 解决前端ID失真的问题，将Long转为String，但是这回导致`Page`中的`total`返回到前端是String，前端报错，因此需要在前端加上`Number()`
6. 忘记密码功能，更新密码功能，六个月密码过期提示功能
7. 使用