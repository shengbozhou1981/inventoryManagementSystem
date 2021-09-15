# Inventory Management API Document 

1. For security,API invocations must be authenticated. So each request will put the authentication info into the request when calling the API service and needs Header (terminalId and token) .
2. use https certificate call and all the requests visit API by Gateway (Port 8084) and then are transferred to relevant microservices.

![image-20210829094124277](D:\Study\学习资料\images\image-20210829094124277.png)



## Product APIs:

### 1. List all products:

GET  https://localhost:8084/product-service/product/list

Parameters:

```java

Header:
terminalId: 101
token: token101    
```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": [
        {
            "id": 1,
            "productName": "product101",
            "productId": 101,
            "upc": "SP101",
            "price": 1.50,
            "inventory": 174,
            "stockAlertThreshold": 10,
            "createTime": "2021-08-24T10:17:19",
            "updateTime": "2021-08-24T10:17:24"
        },
        {
            "id": 2,
            "productName": "product102",
            "productId": 102,
            "upc": "SP102",
            "price": 3.20,
            "inventory": 124,
            "stockAlertThreshold": 10,
            "createTime": "2021-08-23T18:18:03",
            "updateTime": "2021-08-23T18:18:09"
        }
    ]
}
```



### 2. List all products (Pagination):

GET   https://localhost:8084/product-service/product/listByPage/1/3

Parameters:

```java
Header:
terminalId: 101
token: token101  

page: 1
size: 3    
```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": {
        "content": [
            {
                "id": 1,
                "productName": "product101",
                "productId": 101,
                "upc": "SP101",
                "price": 1.50,
                "inventory": 174,
                "stockAlertThreshold": 10,
                "createTime": "2021-08-24T10:17:19",
                "updateTime": "2021-08-24T10:17:24"
            },
            {
                "id": 2,
                "productName": "product102",
                "productId": 102,
                "upc": "SP102",
                "price": 3.20,
                "inventory": 124,
                "stockAlertThreshold": 10,
                "createTime": "2021-08-23T18:18:03",
                "updateTime": "2021-08-23T18:18:09"
            },
            {
                "id": 3,
                "productName": "product103",
                "productId": 103,
                "upc": "SP103",
                "price": 5.20,
                "inventory": 192,
                "stockAlertThreshold": 10,
                "createTime": "2021-08-24T18:18:03",
                "updateTime": "2021-08-24T18:18:09"
            }
        ],
        "size": 3,
        "total": 6
    }
}
```





### 3. Find a product by its ID:

GET https://localhost:8084/product-service/product/findByProductId/104

Parameters:

```
Header:
terminalId: 101
token: token101  

product_id: 104
```

Return

```java
{
    "id": 4,
    "productName": "product104",
    "productId": 104,
    "upc": "SP104",
    "price": 2.90,
    "inventory": 165,
    "stockAlertThreshold": 10,
    "createTime": "2021-08-25T05:10:39",
    "updateTime": "2021-08-25T05:10:44"
}
```



### 4. Find a product by UPC number:

GET https://localhost:8084/product-service/product/findByUPC/SP102

Parameters:

```
Header:
terminalId: 101
token: token101  

upc: SP102
```

Return

```java
{
    "id": 2,
    "productName": "product102",
    "productId": 102,
    "upc": "SP102",
    "price": 3.20,
    "inventory": 124,
    "stockAlertThreshold": 10,
    "createTime": "2021-08-23T18:18:03",
    "updateTime": "2021-08-23T18:18:09"
}
```



### 5. Edit a product

PUT https://localhost:8084/product-service/product/update

Parameters:

```
Header:
terminalId: 101
token: token101  

Body:
{
    "id": 2,
    "productName": "product102",
    "productId": 102,
    "upc": "SP102",
    "price": 3.20,
    "inventory": 200

}
```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": {
        "id": 2,
        "productName": "product102",
        "productId": 102,
        "upc": "SP102",
        "price": 3.20,
        "inventory": 200,
        "stockAlertThreshold": null,
        "fromDate": null,
        "toDate": null,
        "discountPercentage": null,
        "createTime": null,
        "updateTime": null
    }
}

```



### 6. subtract stock by product id:

PUT https://localhost:8084/product-service/product/subStock/102/20

Parameters:

```
Header:
terminalId: 101
token: token101  

product_id: 102
quantity: 20
```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": "substock product Id 102, success"
}
```



### 7. Subtract stock by UPC

PUT https://localhost:8084/product-service/product/subStockByUpc/SP102/2

Parameters:

```
Header:
terminalId: 101
token: token101  

UPC: 102
quantity: 2
```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": "subtract stock UPC SP106, success"
}
```

### 8. Check discount products

GET https://localhost:8084/product-service/discount/listDiscountProducts

Parameters:

```
Header:
terminalId: 101
token: token101  

```

Return

```java
[
    {
        "id": 2,
        "productId": "103",
        "upc": "SP103",
        "regularPrice": 5.20,
        "fromDate": 1629864000000,
        "toDate": 1632974400000,
        "discountPercentage": 0.2,
        "quantityRequirement": 2,
        "withProductId": null,
        "active": 1,
        "createTime": null,
        "updateTime": null,
        "updateBy": "Tony"
    },
    {
        "id": 3,
        "productId": "105",
        "upc": "SP105",
        "regularPrice": 20.10,
        "fromDate": 1629950400000,
        "toDate": 1632974400000,
        "discountPercentage": 0.2,
        "quantityRequirement": null,
        "withProductId": "106",
        "active": 1,
        "createTime": null,
        "updateTime": null,
        "updateBy": "Tony"
    },
    {
        "id": 4,
        "productId": "104",
        "upc": "SP104",
        "regularPrice": 2.90,
        "fromDate": 1630036800000,
        "toDate": 1632974400000,
        "discountPercentage": 0.3,
        "quantityRequirement": null,
        "withProductId": "102",
        "active": 1,
        "createTime": null,
        "updateTime": null,
        "updateBy": "Tony"
    }
]
```

### 9. Check discount product by productId

GET https://localhost:8084/product-service/discount/listByProductId/103

Parameters:

```
Header:
terminalId: 101
token: token101  

productId: 103
```

Return

```java
{
    "id": 2,
    "productId": "103",
    "upc": "SP103",
    "regularPrice": 5.20,
    "fromDate": 1629864000000,
    "toDate": 1632974400000,
    "discountPercentage": 0.2,
    "quantityRequirement": 2,
    "withProductId": null,
    "active": 1,
    "createTime":null,
    "updateTime": null,
    "updateBy": "Tony"
}
```

### 10. Stock alert list

GET https://localhost:8084/product-service/stockAlert/list

Parameters:

```
Header:
terminalId: 101
token: token101  

```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": [
        {
            "id": 1,
            "alertProduct": "product101",
            "alertProductUpc": "SP101",
            "alertCurrentStock": 9,
            "alertContent": "product inventory lower than the alert Threshold, UPC is :SP101",
            "createTime": null,
            "updateTime": null
        },
        {
            "id": 2,
            "alertProduct": "product101",
            "alertProductUpc": "SP101",
            "alertCurrentStock": 9,
            "alertContent": "product inventory lower than the alert Threshold, UPC is :SP101",
            "createTime": null,
            "updateTime": null
        },
        {
            "id": 3,
            "alertProduct": "product101",
            "alertProductUpc": "SP101",
            "alertCurrentStock": 9,
            "alertContent": "product inventory lower than the alert Threshold, UPC is :SP101",
             "createTime": null,
            "updateTime": null
        },
        {
            "id": 4,
            "alertProduct": "product101",
            "alertProductUpc": "SP101",
            "alertCurrentStock": 9,
            "alertContent": "product inventory lower than the alert Threshold, UPC is :SP101",
            "createTime": null,
            "updateTime": null
        }
    ]
}
```

### 10. Stock alert check from date

GET https://localhost:8084/product-service/stockAlert/listFromDate/2021-06-01

Parameters:

```
Header:
terminalId: 101
token: token101  

date: 2021-06-01
```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": [
        {
            "id": 4,
            "alertProduct": "product101",
            "alertProductUpc": "SP101",
            "alertCurrentStock": 9,
            "alertContent": "product inventory lower than the alert Threshold, UPC is :SP101",
            "createTime": null,
            "updateTime": null
        },
        {
            "id": 3,
            "alertProduct": "product101",
            "alertProductUpc": "SP101",
            "alertCurrentStock": 9,
            "alertContent": "product inventory lower than the alert Threshold, UPC is :SP101",
            "createTime": null,
            "updateTime": null
        }
    ]
}
```

### 11. Stock alert check by date

GET https://localhost:8084/product-service/stockAlert/listByDate/2021-08-28

Parameters:

```
Header:
terminalId: 101
token: token101  

date: 2021-08-28
```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": [
        {
            "id": 4,
            "alertProduct": "product101",
            "alertProductUpc": "SP101",
            "alertCurrentStock": 9,
            "alertContent": "product inventory lower than the alert Threshold, UPC is :SP101",
            "createTime": null,
            "updateTime": null
        }
    ]
}
```



## Cashier APIs:

### 1. List all cashier terminals

GET https://localhost:8084/cashier-service/cashier/list

Parameters:

```
Header:
terminalId: 101
token: token101  
```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": [
        {
            "id": 1,
            "terminalId": 101,
            "token": "token101",
            "createTime": "2021-08-25T17:32:23",
            "updateTime": "2021-08-25T17:32:27"
        },
        {
            "id": 2,
            "terminalId": 102,
            "token": "token102",
            "createTime": "2021-08-25T17:32:29",
            "updateTime": "2021-08-25T17:32:31"
        },
        {
            "id": 3,
            "terminalId": 103,
            "token": "token103",
            "createTime": "2021-08-25T17:32:34",
            "updateTime": "2021-08-25T17:32:40"
        }
    ]
```

### 2. Find a cashier terminal by ID

GET https://localhost:8084/cashier-service/cashier/findById/103

Parameters:

```
Header:
terminalId: 101
token: token101  

terminal_id: 103
```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": {
        "id": 3,
        "terminalId": 103,
        "token": "token103",
        "createTime": "2021-08-25T17:32:34",
        "updateTime": "2021-08-25T17:32:40"
    }
}
```

### 3. Create a cashier terminal

POST https://localhost:8084/cashier-service/cashier/create/108/token108

Parameters:

```
Header:
terminalId: 101
token: token101  

terminal_id: 108
token: token108
```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": "create cashier successfully"
}
```

![image-20210827095116104](D:\Study\学习资料\images\image-20210827095116104.png)





---



## Order APIs:

### 1. List all orders

GET  https://localhost:8084/order-service/orderHead/list

Parameters:

```
Header:
terminalId: 101
token: token101  

```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": [
        {
            "orderId": "e9c8cecab86d4bfd9c316cb66afe1d4d",
            "customerName": "tony",
            "terminalId": 13,
            "orderAmount": 30.4,
            "orderStatus": "new",
            "paymentMethod": "credit card",
            "payStatus": "pending",
            "createTime": null,
            "updateTime": null
        },
        {
            "orderId": "e5fb7d4c064b1418af607babfe19a790",
            "customerName": "tony",
            "terminalId": 13,
            "orderAmount": 30.4,
            "orderStatus": "new",
            "paymentMethod": "credit card",
            "payStatus": "pending",
            "createTime": null,
            "updateTime": null
        }
    ]
}
```

### 2. List all orders with order details

GET  https://localhost:8084/order-service/orderHead/orderListWithOrderDetail

Parameters:

```
Header:
terminalId: 101
token: token101  

```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": [
        {
            "orderId": "6b50af9223b6472217dadd7f60b25192",
            "customerName": "Michael",
            "terminalId": 101,
            "orderAmount": 243.515,
            "orderStatus": "completed",
            "paymentMethod": "credit card",
            "payStatus": "paid",
            "createTime": null,
            "updateTime": null,
            "orderDetailList": [
                {
                    "detailId": "c133e4e7cbbaed4223cf4e38f2b4c473",
                    "orderId": "6b50af9223b6472217dadd7f60b25192",
                    "productId": 105,
                    "productName": "product105",
                    "upc": "SP105",
                    "price": 20.1,
                    "amount": 5,
                    "createTime": null,
                    "updateTime": null
                }
            ]
        },
        {
            "orderId": "6e88b7bea2fa32d12c8e79e557744a92",
            "customerName": "Michael",
            "terminalId": 101,
            "orderAmount": 243.515,
            "orderStatus": "pending",
            "paymentMethod": "credit card",
            "payStatus": "pending",
            "createTime": null,
            "updateTime": null,
            "orderDetailList": [
                {
                    "detailId": "f4097d9fe79fe2f4be210b45b848a6c1",
                    "orderId": "6e88b7bea2fa32d12c8e79e557744a92",
                    "productId": 105,
                    "productName": "product105",
                    "upc": "SP105",
                    "price": 20.1,
                    "amount": 5,
                    "createTime": null,
                    "updateTime": null
                },
                {
                    "detailId": "eadf02aa0992e2fbb221689fde875966",
                    "orderId": "6e88b7bea2fa32d12c8e79e557744a92",
                    "productId": 106,
                    "productName": "product106",
                    "upc": "SP106",
                    "price": 11.5,
                    "amount": 10,
                    "createTime": null,
                    "updateTime": null
                }
            ]
        }
    ]
}
```

### 3. List all orders (Pagination)

GET https://localhost:8084/order-service/orderHead/orderListByPage/1/2

Parameters:

```
Header:
terminalId: 101
token: token101  

page: 1
size: 2
```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": {
        "content": [
            {
                "orderId": "8c81e09be5e39158dae3147caaa907b2",
                "customerName": "WANG",
                "terminalId": 14,
                "orderAmount": 243.515,
                "orderStatus": "pending",
                "paymentMethod": "credit card",
                "payStatus": "completed",
                "createTime": null,
                "updateTime": null
            },
            {
                "orderId": "50d332706c0c3cdcc22b8d2e951d71fb",
                "customerName": "ZHOU",
                "terminalId": 14,
                "orderAmount": 18.08,
                "orderStatus": "new",
                "paymentMethod": "credit card",
                "payStatus": "pending",
                "createTime": null,
                "updateTime": null
            }
        ],
        "total": 7,
        "size": 2
    }
}
```

### 4. Find all products purchased and quantities of each product in an order

GET https://localhost:8084/order-service/orderDetail/orderDetailByOrderId/8c81e09be5e39158dae3147caaa907b2

Parameters:

```
Header:
terminalId: 101
token: token101  

order_id: 8c81e09be5e39158dae3147caaa907b2
```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": [
        {
            "detailId": "9808fcd4426b39110c1565a07cdfa26d",
            "orderId": "8c81e09be5e39158dae3147caaa907b2",
            "productId": 105,
            "productName": "product105",
            "upc": "SP105",
            "price": 20.1,
            "amount": 5,
            "createTime": null,
            "updateTime": null
        },
        {
            "detailId": "9cf8a62e0ab535e01f7fc9ed779a9bd9",
            "orderId": "8c81e09be5e39158dae3147caaa907b2",
            "productId": 106,
            "productName": "product106",
            "upc": "SP106",
            "price": 11.5,
            "amount": 10,
            "createTime": null,
            "updateTime": null
        }
    ]
}
```

### 5. Add a product to an order by UPC and quantity

PUT https://localhost:8084/order-service/orderHead/addProductToOrder

Parameters:

```
Header:
terminalId: 101
token: token101  

Body:
{
    "customerName": "WANG",
    "terminalId": 14,
    "orderId": "8c81e09be5e39158dae3147caaa907b2",
    "items": [
    {
        "upc": "SP103",
        "amount": 5
    },
      {
        "upc": "SP104",
        "amount": 10
    }
    ]
}
```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": "add product to order 8c81e09be5e39158dae3147caaa907b2, success"
}
```

### 6. Find an order by ID/Get total price of an order

GET https://localhost:8084/order-service/orderHead/findByOrderId/50d332706c0c3cdcc22b8d2e951d71fb

Parameters:

```
Header:
terminalId: 101
token: token101  

order_id: 50d332706c0c3cdcc22b8d2e951d71fb
```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": {
        "orderId": "50d332706c0c3cdcc22b8d2e951d71fb",
        "customerName": "ZHOU",
        "terminalId": 14,
        "orderAmount": 18.08,
        "orderStatus": "new",
        "paymentMethod": "credit card",
        "payStatus": "pending",
        "createTime": null,
        "updateTime": null,
        "orderDetailList": [
            {
                "detailId": "db310054ac86b3af1541e5842b47fbeb",
                "orderId": "50d332706c0c3cdcc22b8d2e951d71fb",
                "productId": 102,
                "productName": "product102",
                "upc": "SP102",
                "price": 3.2,
                "amount": 5,
                "createTime": null,
                "updateTime": null
            }
        ]
    }
}
```

### 7. Create an order

POST https://localhost:8084/order-service/orderHead/create

Parameters:

```
Header:
terminalId: 101
token: token101  

Body:
{
    "customerName": "Michael",
    "terminalId": 101,

    "orderStatus": "new",
    "paymentMethod": "credit card",
    "payStatus": "",
    "items": [
    {
        "productId": 105,
        "amount": 5
    },
      {
        "productId": 106,
        "amount": 10
    }
    ]
}
```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": {
        "orderId": "6e88b7bea2fa32d12c8e79e557744a92"
    }
}
```

### 8. Close an order when payments are made in full

PUT https://localhost:8084/order-service/orderHead/updateOrderStatus/6b50af9223b6472217dadd7f60b25192

Parameters:

```
Header:
terminalId: 101
token: token101  

order_id: 6b50af9223b6472217dadd7f60b25192
```

Return

If not paid yet, will response

```java
{
    "code": 200,
    "msg": "success",
    "data": "payment not done, please pay the order first"
}
```

If paid in full, will response

```java
{
    "code": 200,
    "msg": "success",
    "data": "payments are made in full, close order 6b50af9223b6472217dadd7f60b25192 successfully"
}
```





### 9. sales statistics of past xx days

GET https://localhost:8084/order-service/orderDetail/salesStatistic/7

Parameters:

```
Header:
terminalId: 101
token: token101  

past_days: 7
```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": [
        {
            "upc": "SP103",
            "productId": 103,
            "productName": "product103",
            "price": 5.2,
            "totalSales": 49
        },
        {
            "upc": "SP104",
            "productId": 104,
            "productName": "product104",
            "price": 2.9,
            "totalSales": 35
        },
        {
            "upc": "SP101",
            "productId": 101,
            "productName": "product101",
            "price": 1.5,
            "totalSales": 30
        },
        {
            "upc": "SP106",
            "productId": 106,
            "productName": "product106",
            "price": 11.5,
            "totalSales": 20
        },
        {
            "upc": "SP102",
            "productId": 102,
            "productName": "product102",
            "price": 3.2,
            "totalSales": 16
        },
        {
            "upc": "SP105",
            "productId": 105,
            "productName": "product105",
            "price": 20.1,
            "totalSales": 15
        }
    ]
}
```

And the report will be saved into the sales_report table in database for further check.

![image-20210827123623458](D:\Study\学习资料\images\image-20210827123623458.png)

### 10. Recall check by UPC

GET https://localhost:8084/order-service/orderHead/recallCheck/SP104

Parameters:

```
Header:
terminalId: 101
token: token101  

upc: SP104
```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": [
        {
            "orderId": "cef40fc5e54045e12337f7d9348bb383",
            "customerName": "tony",
            "terminalId": 13,
            "orderAmount": 51.0,
            "orderStatus": "new",
            "paymentMethod": "credit card",
            "payStatus": "pending",
            "createTime": null,
            "updateTime": null,
            "orderDetailList": [
                {
                    "detailId": "df724ac8987f5aa1b8bcf6a3ced45a6d",
                    "orderId": "cef40fc5e54045e12337f7d9348bb383",
                    "productId": 101,
                    "productName": "product101",
                    "upc": "SP101",
                    "price": 1.5,
                    "amount": 5,
                    "createTime": null,
                    "updateTime": null
                },
                {
                    "detailId": "4223184b5db83e2ea9fb4323da95cdd7",
                    "orderId": "cef40fc5e54045e12337f7d9348bb383",
                    "productId": 104,
                    "productName": "product104",
                    "upc": "SP104",
                    "price": 2.9,
                    "amount": 5,
                    "createTime": null,
                    "updateTime": null
                },
                {
                    "detailId": "1bbf6b2efce6dcd725dd131e2911a08b",
                    "orderId": "cef40fc5e54045e12337f7d9348bb383",
                    "productId": 104,
                    "productName": "product104",
                    "upc": "SP104",
                    "price": 2.9,
                    "amount": 5,
                    "createTime": null,
                    "updateTime": null
                }
            ]
        },
        {
            "orderId": "024b585af40cec2399bb0ae428d8c512",
            "customerName": "YY",
            "terminalId": 14,
            "orderAmount": 22.0,
            "orderStatus": "new",
            "paymentMethod": "credit card",
            "payStatus": "pending",
            "createTime": null,
            "updateTime": null,
            "orderDetailList": [
                {
                    "detailId": "d8b3a1f1c2cc1576d5e0c9ba43f21cd2",
                    "orderId": "024b585af40cec2399bb0ae428d8c512",
                    "productId": 101,
                    "productName": "product101",
                    "upc": "SP101",
                    "price": 1.5,
                    "amount": 5,
                     "createTime": null,
                    "updateTime": null
                },
                {
                    "detailId": "cf3389578d6e19f67c79af99a35c04e6",
                    "orderId": "024b585af40cec2399bb0ae428d8c512",
                    "productId": 104,
                    "productName": "product104",
                    "upc": "SP104",
                    "price": 2.9,
                    "amount": 5,
                    "createTime": null,
                    "updateTime": null
                }
            ]
        }
   }    
```

----



## Payment APIs:

### 1. List all payments

GET https://localhost:8084/order-service/payment/list

Parameters:

```
Header:
terminalId: 101
token: token101  
```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": [
        {
            "id": 1,
            "orderId": "8c81e09be5e39158dae3147caaa907b2",
            "orderAmount": 243.515,
            "paymentMethod": "credit card",
            "paymentStatus": "paid",
            "createTime": null,
            "updateTime": null
        },
        {
            "id": 2,
            "orderId": "6b50af9223b6472217dadd7f60b25192",
            "orderAmount": 243.515,
            "paymentMethod": "credit card",
            "paymentStatus": "paid",
            "createTime": null,
            "updateTime": null
        },
        {
            "id": 3,
            "orderId": "6e88b7bea2fa32d12c8e79e557744a92",
            "orderAmount": 243.515,
            "paymentMethod": "credit card",
            "paymentStatus": "pending",
            "createTime": null,
            "updateTime": null
        }
    ]
}
```

### 2. List all payments made for an order

GET https://localhost:8084/order-service/payment/listByOrderId/6e88b7bea2fa32d12c8e79e557744a92

Parameters:

```
Header:
terminalId: 101
token: token101  

order_id: 6e88b7bea2fa32d12c8e79e557744a92
```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": [
        {
            "id": 3,
            "orderId": "6e88b7bea2fa32d12c8e79e557744a92",
            "orderAmount": 243.515,
            "paymentMethod": "credit card",
            "paymentStatus": "pending",
            "createTime": null,
            "updateTime": null
        }
    ]
}
```

### 3. Update payment status to paid by orderId

PUT https://localhost:8084/order-service/payment/paymentStatusUpdate/6e88b7bea2fa32d12c8e79e557744a92/paid

Parameters:

```
Header:
terminalId: 101

order_id: 6e88b7bea2fa32d12c8e79e557744a92
status: paid
```

Return

```java
{
    "code": 200,
    "msg": "success",
    "data": "payment status updates to paid"
}
```

