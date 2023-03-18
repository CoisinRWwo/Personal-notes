## JDBC访问数据库与执行操作

### **连接数据库**

```java
public static Connection getConnnection() throws Exception {
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");

        Properties properties = new Properties();
        properties.load(resourceAsStream);

        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");

        //2.加载驱动
        Class.forName(driverClass);

        //3.获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }
```

---

### **资源的关闭**

```java
 public static void closeResource(Connection connection, PreparedStatement preparedStatement){
        //资源的关闭
        try {
            if (preparedStatement != null)
                preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //关闭资源的操作
    public static void closeResource(Connection connection, PreparedStatement preparedStatement , ResultSet rs){
        try {
            if (preparedStatement != null)
                preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (rs != null)
                rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
```

---

### 向表中添加数据

```java
@Test
    public void TestInsert() throws SQLException {
        Connection connection = null;
        java.sql.PreparedStatement preparedStatement = null;
        try {
            InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");

            Properties properties = new Properties();
            properties.load(resourceAsStream);

            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            String url = properties.getProperty("url");
            String driverClass = properties.getProperty("driverClass");

            //2.加载驱动
            Class.forName(driverClass);

            //3.获取连接
            connection = DriverManager.getConnection(url, user, password);

            //4.预编译sql语句，返回实例
            String sql = "insert into cunstomers(name,email,birth)values(?,?,?)";//占位符
            preparedStatement = connection.prepareStatement(sql);

            //5.填充占位符
            preparedStatement.setString(1,"吴亦凡");
            preparedStatement.setString(2,"wuyifan@gmail.com");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parse = simpleDateFormat.parse("1000-01-01");
            preparedStatement.setDate(3,new Date(parse.getTime()));

            //6.执行操作
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //资源的关闭
            try {
                if (preparedStatement != null)
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if (connection != null)
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }
```

---

### **修改表操作**

```java
public void testUpdate() {
        Connection connnection = null;
        PreparedStatement preparedStatement = null;

        try {
            //1.获取数据库的连接
            connnection = JDBCUtils.getConnnection();

            //2.预编译sql语句，返回PrepareStatment的实列
            String sql = "update customers set name = ? where id = ?";
            preparedStatement = connnection.prepareStatement(sql);

            //3.填充占位符
            preparedStatement.setString(1,"mozhate");
            preparedStatement.setInt(2,18);

            //4.执行
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.资源的关闭
            JDBCUtils.closeResource(connnection,preparedStatement);
        }
    }
```

---



### **通用的增删改操作**

```java
public i update(String sql,Object ...args) {
        Connection connnection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.获取数据库的连接
            connnection = JDBCUtils.getConnnection();
            //2.预编译sql语句，返回PrepareStatment的实列
            preparedStatement = connnection.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0 ; i < args.length ; i++){
                preparedStatement.setObject(i + 1, args[i]);
            }
            //4.执行
            //preparedStatement.execute();
            //如果执行的是查询操作，有返回结果，则此方法返回true；
            //如果执行的增删改操作，没有返回结果，则此方法返回false；
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtils.closeResource(connnection,preparedStatement);
        }
  return 0 ;
    }
```

---

### **查询操作**

```java
 public void testQueryt1(){
        Connection connnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connnection = JDBCUtils.getConnnection();

            String sql = "select id,name,email,birth from customers where id = ?";
            preparedStatement = connnection.prepareStatement(sql);
            preparedStatement.setObject(1,1);

            //执行,并返回结果集
            resultSet = preparedStatement.executeQuery();

            //处理结果集
            if (resultSet.next()){
                //判断结果集的下一条是否有数据，如果有数据返回true，并指向下一条
                //获取当前这条数据的各个字段值
                int anInt = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date date = resultSet.getDate(4);

                //将数据封装为一个对象
                Customer customer = new Customer(anInt, name, email, date);
                System.out.println(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            JDBCUtils.closeResource(connnection,preparedStatement,resultSet);
        }
    }
```

---

### 通用的查询操作

```java
public Customer queryForCustomers(String sql , Object...args) throws Exception {
        Connection connnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connnection = JDBCUtils.getConnnection();

            preparedStatement = connnection.prepareStatement(sql);

            for (int i = 0 ; i < args.length ; i++){
                preparedStatement.setObject(i+1,args[i]);
            }

            resultSet = preparedStatement.executeQuery();

            //获取结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            if (resultSet.next()){
                Customer customer = new Customer();
                //处理结果集的一行数据中的每一个列
                for (int i = 0 ; i < columnCount ; i++){
                    //获取列值
                    Object object = resultSet.getObject(i + 1);

                    //获取每个列的列名
                    String columnName = metaData.getColumnName(i + 1);

                    //给cust对象指定的某个对象，赋值给value，通过反射
                    Field declaredField = Customer.class.getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    declaredField.set(customer,object);
                }
                return customer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connnection,preparedStatement,resultSet);
        }

        return null;

    }
```

---

### 带泛型的通用查询操作

```java
 public<T> T orderForQuery(Class<T> clazz , String sql , Object...args) throws Exception {
        Connection connnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connnection = JDBCUtils.getConnnection();

            preparedStatement = connnection.prepareStatement(sql);

            for (int i = 0 ; i < args.length ; i++){
                preparedStatement.setObject(i+1,args[i]);
            }
            //执行，获取结果集
            resultSet = preparedStatement.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //获取列数
            int columnCount = metaData.getColumnCount();
            if (resultSet.next()){
                T t = clazz.newInstance();

                for (int i = 0 ; i < columnCount ; i++){
                    //获取每个列的列值：ResultSet
                    Object object = resultSet.getObject(i + 1);
                    //获取每个列的列名（getColumnName）：通过ResultSetMetaData
                    //获取每个列的列名（getColumnLabel）
                    String columnLabel = metaData.getColumnLabel(i + 1);

                    //通过反射，将对象指定名columnName的属性赋值为指定的值columnValue
                    Field declaredField = Order.class.getDeclaredField(columnLabel);
                    declaredField.setAccessible(true);
                    declaredField.set(t,object);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connnection,preparedStatement,resultSet);
        }
        return null;
    }
```

---

### 查询多条数据

```java
public <T> List<T> getForList(Class<T> clazz , String sql , Object...args){
        Connection connnection = null;
        java.sql.PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connnection = JDBCUtils.getConnnection();

            preparedStatement = connnection.prepareStatement(sql);

            for (int i = 0 ; i < args.length ; i++){
                preparedStatement.setObject(i+1,args[i]);
            }

            resultSet = preparedStatement.executeQuery();

            //获取结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            //创建集合对象
            ArrayList<T> objects = new ArrayList<T>();
            while (resultSet.next()){
                T t = clazz.newInstance();
                //处理结果集的一行数据中的每一个列
                for (int i = 0 ; i < columnCount ; i++){
                    //获取列值
                    Object object = resultSet.getObject(i + 1);

                    //获取每个列的列名
                    String columnName = metaData.getColumnName(i + 1);

                    //给cust对象指定的某个对象，赋值给value，通过反射
                    Field declaredField = Customer.class.getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    declaredField.set(t,object);
                }
                objects.add(t);
            }
            return objects;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connnection,preparedStatement,resultSet);
        }

        return null;
    }
```

