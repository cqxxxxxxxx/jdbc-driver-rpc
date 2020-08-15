# jdbc-driver-rpc

自实现的jdbc驱动，通过http执行sql并解析返回的json数据。

用于集成进mysql等框架

#### todo
1. 如何集成认证机制
2. 缓存处理（列名之类缓存，提高性能；结果缓存？）
3. 开放部分扩展点，通过拦截器或者spi机制扩展出去
4. 实现lb的connection（参考mysql的jdbc driver 或者其他的）

#### 特点 feature
1. 支持自定义请求，响应 Serializable和Deserializable 
类似redis的自定义序列化反序列化器

2. 支持自定义的Request与Response结构，只需要实现对应的接口
同时需要实现IRequestFactory接口，通过工厂方法构建request
同时需要实现Response的toRows接口


#### 其他说明
1. 暂不支持基于username password自动认证功能， 需要自己额外先认证通过获取token后处理
2. 暂时只做了http的rpc实现

### 整个流程梳理

1. class.forName 加载driver, 内部通过driverManager注册该driver到manager中
2. driver.getConnect 方法把相关连接信息，properties传进来
3. 基于连接信息，properties构建ConnectionInfo作为连接的上下文信息
4. 构建connectionInfo时候解析出需要的RpcClientType，connectionType，requestFactory
5. 基于connectionInfo的信息构建当前连接需要使用的RpcClient,connection,requestFactory
...