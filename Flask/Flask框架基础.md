# 创建Flask基本框架



框架文件夹有三个文件，分别为：static、templates、app.py

templates文件夹下应放HTML文件

而app.py文件则是主要的运行文件

![image-20211211183059807](Flask框架基础.assets/image-20211211183059807.png)



---



# 原框架



```python
import json

from flask import Flask

# 使用Flask创建一个app，并且传递一个__name__参数
app = Flask(__name__)

# 设置@app.route:设置访问的url，这里是设置一个根路径
@app.route('/')
def index():  # put application's code here
    return 'Hello World!'


if __name__ == '__main__':
    app.run()
```



---



# JinJa2语法的两种简单写法



```jinja2
//判断语句
{% if age > 18 %}
    <div>真是大帅哥</div>
{% elif age < 18 %}
    <div>是小帅哥</div>
{% endif %}

//循环语句
{% for book in books %}
    <div>{{ book }}</div>
{% endfor %}

```



---



# SQLAlchemy与Flask结合使用



## 连接数据库



第一种方式（使用传参的形式编写）

```python
# 数据库的配置变量
HOSTNAME = '192.168.23.36'
POST = '3306'
DATABASE = 'takeout'
USERNAME = 'root'
PASSWORD = '123456'
DB_URI = 'mysql+pymysql://{}:{}@{}:{}/{}?charset=utf8'.format(USERNAME,PASSWORD,HOSTNAME,POST,DATABASE)
app.config['SQLALCHEMY_DATABASE_URI'] = DB_URI
```

第二种方式（直接进行连接）

```python
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root:123456@192.168.23.36:3306/takeout'
```



 连接数据库以后还需要配置参数,如果不配置参数，会报错

```python
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = True
```



## 定义数据库模型



再使用SQLAlchemt之前需要导包

```python
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy import *
```

再去定义模型

```python
db = SQLAlchemy(app)
```



数据表的定义

```python
class Article(db.Model):
    __tablename__ = "store_operation_data"
    id = db.Column(db.Integer,primary_key=True)
    restaurant_inside_id = db.Column(db.Integer)
    推单数9 = db.Column(db.Integer)

    #author_id = db.Column(db.Integer,db.ForeignKey("store_basic_informations.id"))

class store_basic_informations(db.Model):
    __tablename__ = "store_basic_informations"
    id = db.Column(db.Integer,primary_key=True)
    location = db.Column(db.String(50))
    latitude = db.Column(db.Float)
    longitude = db.Column(db.Float)
```

此处需要注意的是：表名不能有错误，定义的字段类型需注意，不需要的字段不用去定义



## 查询数据



几个简单的查询数据的写法

```sqlite
@app.route('/articl')
def article_view():

		result = db.session.query(Article.restaurant_inside_id,Article.推单数9).all()

		result = db.session.query(platform_rate.province,func.sum(platform_rate.accommodation_order).label('sumA'))
         .group_by(platform_rate.province)
         .order_by(desc('sumA')).all()
         
		result = db.session 	   		.query(store_basic_informations.location,func.avg(store_basic_informations.latitude),func.avg(store_basic_informations.longitude)).group_by(store_basic_informations.location).all()

		print(result) //输出数据
```



## 按格式输出数据

```python
a = article_view()
print(a)
i = 0

while i <= 11:
   print("==省份：%s =订单数量：%d个==" % (a[i][0], a[i][1]))
   i += 1
```

