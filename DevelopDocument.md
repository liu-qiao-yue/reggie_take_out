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

| 参数名      | 是否必须 | 类型     | 说明   |
|:---------|:-----|:-------|:-----|
| username | 是    | String | 员工账号 |
| password | 是    | String | 员工密码 |

- 返回参数

| 参数名        | 类型  | 说明      |
|:-----------|:----|:--------|
| id         | 是   | Long    | 员工ID |
| username   | 是   | String  | 员工账号 |
| name       | 是   | String  | 员工姓名 |
| password   | 是   | String  | 密码     |
| phone      | 是   | String  | 手机号   |
| sex        | 是   | String  | 性别     |
| idNumber   | 是   | String  | 身份证号 |
| status     | 是   | Integer | 状态(0:禁用，1:启用) |
| createTime | 是   | Date    | 创建时间 |
| updateTime | 是   | Date    | 更新时间 |
| createUser | 是   | Long    | 创建人   |
| updateUser | 是   | Long    | 更新人   |

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

| 参数名      | 是否必须 | 类型      | 说明            |
|:---------|:-----|:--------|:--------------|
| id       | 是    | Long    | 员工ID          | 
| username | 是    | String  | 员工账号          |
| name     | 是    | String  | 员工姓名          |
| password | 否    | String  | 密码            |
| phone    | 是    | String  | 手机号           |
| sex      | 是    | String  | 性别            |
| idNumber | 是    | String  | 身份证号          |
| status   | 否    | Integer | 状态(0:禁用，1:启用) |

- 返回参数

| 参数名  | 类型      | 说明               |
|:-----|:--------|:-----------------|
| code | Integer | 返回标识符(0:成功,1:失败) |
| msg  | String  | 返回信息             |
| data | String  | 返回数据             |
| map  | Map     | 返回补充数据           |

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

| 参数名      | 是否必须 | 类型      | 说明     |
|:---------|:-----|:--------|:-------|
| name     | 否    | String  | 员工姓名   |
| page     | 是    | Integer | 当前页码   |
| pageSize | 是    | Integer | 每页显示条数 |

- 返回参数

| 参数名                         | 类型      | 说明   |
|:----------------------------|:--------|:-----|
| total                       | Integer | 总记录数 |
| pages                       | Integer | 总页数  |
| records:Employee.id         | Long    | id   |
| records:Employee.username   | String  | 用户名  |
| records:Employee.sex        | String  | 性别   |
| records:Employee.status     | Integer | 状态   |
| records:Employee.createTime | Date    | 创建时间 |
| records:Employee.updateTime | Date    | 更新时间 |
| records:Employee.createUser | Long    | 创建人  |
| records:Employee.updateUser | Long    | 更新人  |
| records:Employee.name       | String  | 员工姓名 |
| records:Employee.idNumber   | String  | 身份证号 |
| records:Employee.phone      | String  | 手机号  |
| records:Employee.password   | String  | 密码   |
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

| 参数名    | 是否必须 | 类型      | 说明            |
|:-------|:-----|:--------|:--------------|
| id     | 是    | Long    | 员工ID          |
| status | 是    | Integer | 状态(0:禁用，1:启用) |
- 返回参数

| 参数名  | 类型      | 说明               |
|:-----|:--------|:-----------------|
| code | Integer | 返回标识符(0:成功,1:失败) |
| msg  | String  | 返回信息             |
| data | String  | 返回数据             |
| map  | Map     | 返回补充数据           |
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

| 参数名 | 是否必须 | 类型   | 说明   |
|:----|:-----|:-----|:-----|
| id  | 是    | Long | 员工ID |
- 返回参数

| 参数名                 | 类型      | 说明            |
|:--------------------|:--------|:--------------|
| Employee.id         | Long    | id            |
| Employee.username   | String  | 用户名           |
| Employee.name       | String  | 员工姓名          |
| Employee.password   | String  | 密码            |
| Employee.phone      | String  | 手机号           |
| Employee.sex        | String  | 性别            |
| Employee.idNumber   | String  | 身份证号          |
| Employee.status     | Integer | 状态(0:禁用，1:启用) |
| Employee.createTime | Date    | 创建时间          |
| Employee.updateTime | Date    | 更新时间          |
| Employee.createUser | Long    | 创建人           |
| Employee.updateUser | Long    | 更新人           |
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

| 参数名  | 是否必须 | 类型      | 说明                  |
|:-----|:-----|:--------|:--------------------|
| name | 是    | String  | 分类名称                |
| type | 是    | Integer | 分类类型(1:菜品分类，2:套餐分类) |
| sort | 是    | Integer | 排序                  |
- 返回参数

| 参数名  | 类型      | 说明               |
|:-----|:--------|:-----------------|
| code | Integer | 返回标识符(0:成功,1:失败) |
| msg  | String  | 返回信息             |
| data | String  | 返回数据             |
| map  | Map     | 返回补充数据           |

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
  > ```json
  > {
  >     "code": 1,
  >     "msg": null,
  >     "data": true,
  >     "map": {}
  > }
  >```
#### 分类管理列表
- AIP
  > /category/page
- method
  > GET
- 请求参数

| 参数名      | 是否必须 | 类型      |
|:---------|:-----|:--------|
| page     | 是    | Integer |
| pageSize | 是    | Integer |
- 返回参数

| 参数名                | 类型      | 说明                  |
|:-------------------|:--------|:--------------------|
| code               | Integer | 返回标识符(0:成功,1:失败)    |
| records            | List    | 分类列表                |
| total              | Long    | 总记录数                |
| size               | Integer | 每页显示条数              |
| current            | Integer | 当前页                 |
| code:Category.id   | Long    | 分类ID                |
| code:Category.type | Integer | 分类类型(1:菜品分类，2:套餐分类) |
| code:Category.name | String  | 分类名称                |
| code:Category.sort | Integer | 排序                  |

- 示例
  - response:
  > ```json
  > {
  >     "code": 1,
  >     "msg": null,
  >     "data": {
  >         "records": [
  >             {
  >                 "id": "1413384954989060097",
  >                 "type": 1,
  >                 "name": "主食",
  >                 "sort": 12,
  >                 "createTime": "2021-07-09 14:30:07",
  >                 "updateTime": "2021-07-09 14:39:19",
  >                 "createUser": "1",
  >                 "updateUser": "1",
  >                 "isDeleted": 0
  >             }
  >         ],
  >         "total": 11,
  >         "size": 10,
  >         "current": 2,
  >         "orders": [],
  >         "optimizeCountSql": true,
  >         "hitCount": false,
  >         "countId": null,
  >         "maxLimit": null,
  >         "searchCount": true,
  >         "pages": 2
  >     },
  >     "map": {}
  > }
  >```
#### 修改菜品分类
- AIP
  > /category
- method
  > PUT
- 请求参数

| 参数名  | 是否必须 | 类型      | 说明                  |
|:-----|:-----|:--------|:--------------------|
| id   | 是    | Long    | 分类ID                |
| name | 是    | String  | 分类名称                |
| sort | 是    | Integer | 排序                  |

- 返回参数

| 参数名  | 类型      | 说明               |
|:-----|:--------|:-----------------|
| code | Integer | 返回标识符(0:成功,1:失败) |
| msg  | String  | 返回信息             |
| data | String  | 返回数据             |
| map  | Map     | 返回补充数据           |

- 示例
  - request:
  > ```json
  >  {
  >      "name": "2121",
  >      "id": "1847196037989793794",
  >      "sort": "2"
  >  }
  >```
  - response:
  > ```json
  > {
  >     "code": 1,
  >     "msg": null,
  >     "data": true,
  >     "map": {}
  > }
  >```
#### 删除菜品分类
- AIP
  > /category/{id}
- method
  > DELETE
- 返回参数

| 参数名  | 类型      | 说明               |
|:-----|:--------|:-----------------|
| code | Integer | 返回标识符(0:成功,1:失败) |
| msg  | String  | 返回信息             |
| data | String  | 返回数据             |
| map  | Map     | 返回补充数据           |

- 示例
  - response:
  > ```json
  > {
  >     "code": 1,
  >     "msg": null,
  >     "data": true,
  >     "map": {}
  > }
  >```

### 菜单管理
#### 新增保存菜品分类
- AIP
  > /dish
- method
  > POST
- 请求参数

| 参数名                           | 是否必须 | 类型      | 说明   |
|:------------------------------|:-----|:--------|:-----|
| name                          | 是    | String  | 分类名称 |
| price                         | 是    | Integer | 价格   |
| code                          | 是    | String  | 唯一编码 |
| image                         | 是    | String  | 图片   |
| description                   | 是    | String  | 描述   |
| status                        | 是    | Integer | 状态   |
| categoryId                    | 是    | Long    | 分类ID |
| flavors:DishFlavor.name       | 是    | String  | 口味名称 |
| flavors:DishFlavor.value      | 是    | String  | 口味值  |
| flavors:DishFlavor.showOption | 是    | Boolean | 是否展示 |

- 返回参数

| 参数名  | 类型      | 说明               |
|:-----|:--------|:-----------------|
| code | Integer | 返回标识符(0:成功,1:失败) |
| msg  | String  | 返回信息             |
| data | String  | 返回数据             |
| map  | Map     | 返回补充数据           |

- 示例
  - request:
  > ```json
  > {
  >     "name": "啵啵奶茶",
  >     "price": 1500,
  >     "code": "",
  >     "image": "7b838807-fcd3-48c9-a76c-4c58d86e9b33.png",
  >     "description": "啵啵奶茶好喝无比",
  >     "status": 1,
  >     "categoryId": "1413341197421846529",
  >     "flavors": [
  >         {
  >             "name": "温度",
  >             "value": "[\"常温\"]",
  >             "showOption": false
  >         }
  >     ]
  > }
  >```
  - response:
  > ```json
  > {
  >     "code": 1,
  >     "msg": null,
  >     "data": true,
  >     "map": {}
  > }
  >```
#### 获取菜品详细信息
- AIP
  > /dish/{id}
- method
  > GET
- 返回参数

| 参数名                   | 类型      | 说明               |
|:----------------------|:--------|:-----------------|
| code                  | Integer | 返回标识符(0:成功,1:失败) |
| data                  | Dish    | 菜品信息             |
| code:Dish.id          | Long    | 菜品ID             |
| code:Dish.categoryId  | Long    | 分类ID             |
| code:Dish.name        | String  | 菜品名称             |
| code:Dish.price       | Integer | 价格(分)            |
| code:Dish.code        | String  | 唯一编码             |
| code:Dish.image       | String  | 图片               |
| code:Dish.description | String  | 描述               |
| code:Dish.status      | Integer | 状态(0:停售,1:起售)    |
| code:Dish.sort        | Integer | 排序               |
| code:Dish.createTime  | Date    | 创建时间             |
| code:Dish.updateTime  | Date    | 更新时间             |
| code:Dish.createUser  | Long    | 创建人ID            |
| code:Dish.updateUser  | Long    | 更新人ID            |
| code:Dish.isDeleted   | Integer | 是否删除             |

- 示例
  - response:
  > ```json
  > {
  >     "code": 1,
  >     "msg": null,
  >     "data": {
  >         "id": "1413384757047271425",
  >         "name": "王老吉",
  >         "categoryId": "1413341197421846529",
  >         "price": 500,
  >         "code": "",
  >         "image": "00874a5e-0df2-446b-8f69-a30eb7d88ee8.png",
  >         "description": "",
  >         "status": 1,
  >         "sort": 0,
  >         "createTime": "2021-07-09 14:29:20",
  >         "updateTime": "2021-07-12 09:09:16",
  >         "createUser": "1",
  >         "updateUser": "1",
  >         "isDeleted": 0
  >     },
  >     "map": {}
  > }
  >```
#### 菜品管理列表
- AIP
  > /dish/page
- method
  > GET
- 返回参数

| 参数名                   | 类型      | 说明               |
|:----------------------|:--------|:-----------------|
| code                  | Integer | 返回标识符(0:成功,1:失败) |
| records               | List    | 菜品列表             |
| total                 | Long    | 总记录数             |
| size                  | Integer | 每页显示条数           |
| current               | Integer | 当前页              |
| code:Dish.id          | Long    | 菜品ID             |
| code:Dish.categoryId  | Long    | 分类ID             |
| code:Dish.name        | String  | 菜品名称             |
| code:Dish.price       | Integer | 价格(分)            |
| code:Dish.code        | String  | 唯一编码             |
| code:Dish.image       | String  | 图片               |
| code:Dish.description | String  | 描述               |
| code:Dish.status      | Integer | 状态(0:停售,1:起售)    |
| code:Dish.sort        | Integer | 排序               |

- 示例
  - response:
  > ```json
  > {
  >     "code": 1,
  >     "msg": null,
  >     "data": {
  >         "records": [
  >             {
  >                 "id": "1397850851245600769",
  >                 "name": "霸王别姬",
  >                 "categoryId": "1397844263642378242",
  >                 "price": 12800,
  >                 "code": "123412341234",
  >                 "image": "057dd338-e487-4bbc-a74c-0384c44a9ca3.jpg",
  >                 "description": "还有什么比霸王别姬更美味的呢？",
  >                 "status": 1,
  >                 "sort": 0,
  >                 "createTime": "2021-05-27 09:43:08",
  >                 "updateTime": "2021-05-27 09:43:08",
  >                 "createUser": "1",
  >                 "updateUser": "1",
  >                 "isDeleted": 0
  >             }
  >         ],
  >         "total": 21,
  >         "size": 10,
  >         "current": 3,
  >         "orders": [],
  >         "optimizeCountSql": true,
  >         "hitCount": false,
  >         "countId": null,
  >         "maxLimit": null,
  >         "searchCount": true,
  >         "pages": 3
  >     },
  >     "map": {}
  > }
  >```
#### 获取菜品分类
- AIP
  > category/list
- method
  > GET
- 返回参数

| 参数名                | 类型      | 说明               |
|:-------------------|:--------|:-----------------|
| code               | Integer | 返回标识符(0:成功,1:失败) |
| data               | List    | 菜品分类列表           |
| map                | Map     | 返回补充数据           |
| code:Category.id   | Long    | 分类ID             |
| code:Category.name | String  | 分类名称             |
| code:Category.sort | Integer | 排序               |

- 示例
  - response:
  > ```json
  > {
  >     "code": 1,
  >     "msg": null,
  >     "data": [
  >         {
  >             "id": "1397844263642378242",
  >             "type": 1,
  >             "name": "湘菜",
  >             "sort": 1,
  >             "createTime": "2021-05-27 09:16:58",
  >             "updateTime": "2021-07-15 20:25:23",
  >             "createUser": "1",
  >             "updateUser": "1",
  >             "isDeleted": 0
  >         }
  >     ],
  >     "map": {}
  > }
  >```
#### 编辑保存菜品信息  todo
- AIP
- method
- 请求参数
- 返回参数
- 示例
#### 启用/停售/批量
- AIP
  > status/{status}?ids=1,2,3
- method
  > POST
- 请求参数

| 参数名    | 类型      | 说明            |
|:-------|:--------|:--------------|
| status | Integer | 状态(0:停售,1:起售) |
| ids    | String  | 菜品ID，多个以逗号分隔  |

- 返回参数

| 参数名  | 类型      | 说明               |
|:-----|:--------|:-----------------|
| code | Integer | 返回标识符(0:成功,1:失败) |
| msg  | String  | 返回信息             |
| map  | Map     | 返回补充数据           |
| data | boolean | 返回数据             |
#### 上传图片
- AIP
  > common/upload
- method
  > POST
- 返回参数
  > fileName:文件名
#### 下载图片
- AIP
  > common/access?name=xxx.jpg
- method
  > POST
### 套餐管理

### 订单明细

## 前端接口信息