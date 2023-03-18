连接mysql数据库时后台会出现这样的提示：

Establishing SSL connection without server’s identity verification is not recommended. According to MySQL 5.5.45+,
5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn’t set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to ‘false’. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.

原因是MySQL在高版本需要指明是否进行SSL连接。


```tex
  SSL协议提供服务主要： 		
       1）认证用户服务器，确保数据发送到正确的服务器； 　　 .
       2）加密数据，防止数据传输途中被窃取使用；
       3）维护数据完整性，验证数据在传输过程中是否丢失；

   当前支持SSL协议两层：
   	 	SSL记录协议（SSL Record Protocol）：建立靠传输协议（TCP）高层协议提供数据封装、压缩、加密等基本功能支持
	    SSL握手协议（SSL Handshake Protocol）：建立SSL记录协议用于实际数据传输始前通讯双进行身份认证、协商加密
	    算法、 交换加密密钥等。
```

**您需要通过设置useSSL = false显式禁用SSL，或者设置useSSL = true并为服务器证书验证提供信任库。**



连接池配置一些参数

| 参数名称              | 参数说明                                                     |
| :-------------------- | :----------------------------------------------------------- |
| user                  | 数据库用户名（用于连接数据库）                               |
| password              | 用户密码（用于连接数据库）                                   |
| useUnicode            | 是否使用Unicode字符集，如果参数characterEncoding设置为gb2312或gbk，本参数值必须设置为true |
| characterEncoding     | 当useUnicode设置为true时，指定字符编码。比如可设置为gb2312或gbk |
| autoReconnect         | 当数据库连接异常中断时，是否自动重新连接？                   |
| autoReconnectForPools | 是否使用针对数据库连接池的重连策略                           |
| failOverReadOnly      | 自动重连成功后，连接是否设置为只读？                         |
| maxReconnects         | autoReconnect设置为true时，重试连接的次数                    |
| initialTimeout        | autoReconnect设置为true时，两次重连之间的时间间隔，单位：秒  |
| connectTimeout        | 和数据库服务器建立socket连接时的超时，单位：毫秒。 0表示永不超时，适用于JDK 1.4及更高版本 |
| socketTimeout         | socket操作（读写）超时，单位：毫秒。 0表示永不超时           |