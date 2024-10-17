# 数据库配置

| 数据库           | 数据库名     |
|:--------------|:---------|
| employee      | 员工表      |
| category      | 菜品和套餐分类表 |
| dish          | 菜品表      |
| setmeal       | 套餐表      |
| setmeal_dish  | 套餐菜品关系表  |
| dish_flavor   | 菜品口味关系表  |
| user          | 用户表(c端)  |
| address_book  | 地址簿表     |
| shopping_cart | 购物车      |
| order         | 订单表      |
| order_detail  | 订单明细表    |

# 开发文档

## 后端接口信息
### 登录信息
#### 登录

- AIP
  > /employee/login
- method
  > POST
- 请求参数

> | 参数名      | 是否必须 | 类型   | 说明       |
> | :----------|:-------|:-----|:---------|
> | username | 是      | String | 员工账号   |
> | password | 是      | String | 员工密码 |

- 返回参数

> | 参数名        | 类型  | 说明      |
> |:-----------|:----|:--------|
> | id         | 是   | Long    | 员工ID |
> | username   | 是   | String  | 员工账号 |
> | name       | 是   | String  | 员工姓名 |
> | password   | 是   | String  | 密码     |
> | phone      | 是   | String  | 手机号   |
> | sex        | 是   | String  | 性别     |
> | idNumber   | 是   | String  | 身份证号 |
> | status     | 是   | Integer | 状态(0:禁用，1:启用) |
> | createTime | 是   | Date    | 创建时间 |
> | updateTime | 是   | Date    | 更新时间 |
> | createUser | 是   | Long    | 创建人   |
> | updateUser | 是   | Long    | 更新人   |

- 示例
    - request:
    > ```json
    > {
    >     "username": "admin",
    >     "password": "123456"
    > }
    > ```
    - response:
    >  ```json
    >  {
    >      "id": 1,
    >      "username": "admin",
    >      "name": "管理员",
    >      "password": "************************************************************",
    >      "phone": "13812312312",
    >      "sex": "1",
    >      "idNumber": "******************",
    >      "status": 1,
    >      "createTime": [2021,5,6,17,20,7],
    >      "updateTime": [2021,5,10,2,24,9],
    >      "createUser": "1",
    >      "updateUser": "1"
    >  }
    >  ```

#### 登出
- AIP
  > /employee/logout
- method
  > POST

### 员工管理
#### 新增员工

- AIP
  > /employee/login
- method
  > POST
- 请求参数
- 返回参数
- 示例

#### 编辑保存员工
- AIP
  > /employee
- method
  > PUT
- 请求参数
  > | 参数名 | 是否必须 | 类型 | 说明 |
  > | id | 是 | Long | 员工ID | 
  > | username | 是 | String | 员工账号 |
  > | name | 是 | String | 员工姓名 |
  > | password | 否 | String | 密码 |
  > | phone | 是 | String | 手机号 |
  > | sex | 是 | String | 性别 |
  > | idNumber | 是 | String | 身份证号 |
  > | status | 否 | Integer | 状态(0:禁用，1:启用) |
- 返回参数
  > | 参数名 | 类型  | 说明      |
  > | code | Integer | 返回标识符(0:成功,1:失败) |
  > | msg | String | 返回信息 |
  > | data | String | 返回数据 |
  > | map | Map | 返回补充数据 |
- 示例
    - request:
   > ```json
  >     {
  >         "id": "101054955225350145",
  >         "username": "456",
  >         "name": "456",
  >         "password": "************************************************************",
  >         "phone": "15050505050",
  >         "sex": "1",
  >         "idNumber": "******************",
  >         "status": 1,
  >         "createTime": [2024,10,8,19,30,13],
  >         "updateTime": [2024,10,8,19,30,13],
  >         "createUser": "1",
  >         "updateUser": "1"
  >     }
  > ```
  - response:
  > ```json
  > {
  >     "code": 1,
  >     "msg": null,
  >     "data": "update success.",
  >     "map": {}
  > }
  > ```

#### 员工列表
- AIP
  > /employee/page
- method
  > GET
- 请求参数
    > | 参数名 | 是否必须 | 类型   | 说明       |
    > |:------|:-------|:-----|:---------|
    > | name | 否      | String | 员工姓名 |
    > | page | 是      | Integer | 当前页码 |
    > | pageSize | 是      | Integer | 每页显示条数 |
- 返回参数
    > | 参数名 | 类型  | 说明      |
    > |:------|:----|:------|
    > | total | Integer | 总记录数 |
    > | pages | Integer | 总页数 |
    > | records:Employee.id | Long | id |
    > | records:Employee.username | String | 用户名 |
    > | records:Employee.sex | String | 性别 |
    > | records:Employee.status | Integer | 状态 |
    > | records:Employee.createTime | Date | 创建时间 |
    > | records:Employee.updateTime | Date | 更新时间 |
    > | records:Employee.createUser | Long | 创建人 |
    > | records:Employee.updateUser | Long | 更新人 |
    > | records:Employee.name | String | 员工姓名 |
    > | records:Employee.idNumber | String | 身份证号 |
    > | records:Employee.phone | String | 手机号 |
    > | records:Employee.password | String | 密码 |
- 示例
  - response:
      > ```json
      > {
      >     "code": 1,
      >     "msg": null,
      >     "data": {
      >         "records": [
      >             {
      >                 "id": "1",
      >                 "username": "admin",
      >                 "name": "管理员",
      >                 "password": "************************************************************",
      >                 "phone": "13812312312",
      >                 "sex": "1",
      >                 "idNumber": "******************",
      >                 "status": 1,
      >                 "createTime": [2021,5,6,17,20,7],
      >                 "updateTime": [2021,5,10,2,24,9],
      >                 "createUser": "1",
      >                 "updateUser": "1"
      >             }
      >         ],
      >         "total": "1",
      >         "size": "10",
      >         "current": "1",
      >         "orders": [],
      >         "optimizeCountSql": true,
      >         "hitCount": false,
      >         "countId": null,
      >         "maxLimit": null,
      >         "searchCount": true,
      >         "pages": "1"
      >     },
      >     "map": {}
      > }
      > ```

#### 启用禁用员工
- AIP
  > /employee
- method
  > PUT
- 请求参数
  > | 参数名 | 是否必须 | 类型 | 说明 |
  > |:------|:-------|:----|:----|
  > | id | 是 | Long | 员工ID |
  > | status | 是 | Integer | 状态(0:禁用，1:启用) |
- 返回参数
  > | 参数名 | 类型  | 说明      |
  > |:------|:----|:------|
  > | code | Integer | 返回标识符(0:成功,1:失败) |
  > | msg | String | 返回信息 |
  > | data | String | 返回数据 |
  > | map | Map | 返回补充数据 |
- 示例
  - request:
  > ```json
  > {
  >     "id": "101054955225350144",
  >     "status": 0
  > }
  > ```
  - response:
  > ```json
  > {
  >     "code": 1,
  >     "msg": null,
  >     "data": "update success.",
  >     "map": {}
  > }
  > ```

#### 编辑获取员工信息
- AIP
  > /employee/{id}
- method
  > GET
- 请求参数
  > | 参数名 | 是否必须 | 类型 | 说明 |
  > |:------|:-------|:----|:----|
  > | id | 是 | Long | 员工ID |
- 返回参数
  > | 参数名 | 类型  | 说明      |
  > |:------|:----|:------|
  > | Employee.id | Long | id |
  > | Employee.username | String | 用户名 |
  > | Employee.name | String | 员工姓名 |
  > | Employee.password | String | 密码 |
  > | Employee.phone | String | 手机号 |
  > | Employee.sex | String | 性别 |
  > | Employee.idNumber | String | 身份证号 |
  > | Employee.status | Integer | 状态(0:禁用，1:启用) |
  > | Employee.createTime | Date | 创建时间 |
  > | Employee.updateTime | Date | 更新时间 |
  > | Employee.createUser | Long | 创建人 |
  > | Employee.updateUser | Long | 更新人 |
- 示例
  - response:
  > ```json
  > {
  >     "code": 1,
  >     "msg": null,
  >     "data": {
  >         "id": "101054955225350144",
  >         "username": "123updateaccount",
  >         "name": "updatename",
  >         "password": "************************************************************",
  >         "phone": "15050505050",
  >         "sex": "0",
  >         "idNumber": "******************",
  >         "status": 1,
  >         "createTime": [2024,10,8,19,30,13],
  >         "updateTime": [2024,10,8,19,30,13],
  >         "createUser": "1",
  >         "updateUser": "1"
  >     },
  >     "map": {}
  > }
  > ```

### 分类管理
#### 新增菜品分类
- AIP
  > /category
- method
  > POST
- 请求参数
  > | 参数名 | 是否必须 | 类型 | 说明 |
  > |:------|:-------|:----|:----|
  > | name | 是 | String | 分类名称 |
  > | type | 是 | Integer | 分类类型(1:菜品分类，2:套餐分类) |
  > | sort | 是 | Integer | 排序 |
- 返回参数
- 示例
  - request:
  > ```json
  >  {
  >      "name": "2121",
  >      "type": "1",
  >      "sort": "2"
  >  }
  >```
  - response:

#### 分类管理列表

### 菜单管理

### 套餐管理

### 订单明细

## 前端接口信息