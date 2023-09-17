## 介绍[flask](https://so.csdn.net/so/search?q=flask&spm=1001.2101.3001.7020)-migrate：



flask-migrate，一个用来做[数据迁移](https://so.csdn.net/so/search?q=数据迁移&spm=1001.2101.3001.7020)的falsk扩展，一般都是结合flask-sqlalchemy使用



一般我们修改数据库通常不会直接手动修改，这样效率不仅慢，而且也需要对整体结构清晰，正常情况是修改ORM对应的模型，然后再把模型映射到数据库中。

它是基于Alembic进行的一个封装，并集成到Flask中，而所有的迁移操作其实都是Alembic做的，他能跟踪模型的变化，并将变化映射到数据库中。





## 1、Migrate在app.py文件的使用：

```python
# app.py

from flask_script import Manager
from flask_migrate import Migrate, MigrateCommand
from flask_sqlalchemy import SQLAlchemy
from flask import Flask

app = Flask(__name__)

# 创建数据库sqlalchemy工具对象
db = SQLAlchemy(app)

# db被放到另外一个文件中时，可以使用这个方式连接绑定数据库
# db.init_app(app)  

# 创建数据库迁移工具对象
migrate = Migrate(app=app,db=db)

#让python支持命令行工作
manager = Manager(app)

# 向manager对象中添加数据库操作命令
manger.add_command('db', MigrateCommand)

if __name__ == '__main__':
    manger.run() 
    # 终端运行 python app.py runserver
    # 或 python app.py runserver -h 0.0.0.0 -p 端口

```

```python
from flask import Flask, session, g
import config
from exts import db, mail
from models import UserModel
from blueprints.qa import bp as qa_bp
from blueprints.auth import bp as auth_bp
from flask_migrate import Migrate

app = Flask(__name__)

# 绑定配置文件
app.config.from_object(config)
db.init_app(app)
mail.init_app(app)

# 数据迁移
migrate = Migrate(app, db)

# 绑定蓝图
app.register_blueprint(qa_bp)
app.register_blueprint(auth_bp)


# 钩子函数
@app.before_request
def my_before_request():
    user_id = session.get("user_id")
    if user_id:
        user = UserModel.query.get(user_id)
        setattr(g, "user", user)
    else:
        setattr(g, "user", None)


@app.context_processor
def my_context_processor():
    return {"user": g.user}


if __name__ == '__main__':
    app.run()

```



## 2、创建数据库模型类（即MTV中的Model）

```python
# model.py

from datetime import datetime
from flask_sqlalchemy import SQLAlchemy
db = SQLAlchemy()  # 这句话其实可以放到公共文件中存放调用，比如ext文件下的__init__.py文件

# 建表
class User(db.Model): # 必须继承Model
	__tablename__ = 'user' 
	# 表名，不写是默认也是user，
	# 表名规则是除了第1个字母，其余大写字母前面都会加上“_”
	# 例如UserName，最终生成的表名是：user_name

    # db.Cloumn(SQLAlchemy().类型，约束)  映射表中的列
    id = db.Column(db.Integer, primary_key=True, autoincrement=True, comment='主键id')
	# 这里解释下id，int类型，设置主键，自动递增，备注comment
	
    username = db.Column(db.String(15), nullable=False)
    phone = db.Column(db.String(11),unique=True)
    email = db.Column(db.String(20))
    create_at = db.Column(db.DateTime, default=datetime.now)

    def __str__(self):
        return self.username

```

```python
from datetime import datetime

from exts import db


class UserModel(db.Model):
    __tablename__ = "user"
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    username = db.Column(db.String(100), nullable=False)
    password = db.Column(db.String(200), nullable=False)
    email = db.Column(db.String(100), nullable=False, unique=True)
    join_time = db.Column(db.DateTime, default=datetime.now)


class EmailCaptchaModel(db.Model):
    __tablename__ = "email_captcha"
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    email = db.Column(db.String(100), nullable=False)
    captcha = db.Column(db.String(100), nullable=False)


class QuestionModel(db.Model):
    __tablename__ = "question"
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    title = db.Column(db.String(100), nullable=False)
    content = db.Column(db.Text, nullable=False)
    create_Time = db.Column(db.DateTime, default=datetime.now)

    author_id = db.Column(db.Integer, db.ForeignKey("user.id"))
    author = db.relationship(UserModel, backref="questions")

```

其实数据库类型还有很多，可查看sqlalchemy官方文档：

https://docs.sqlalchemy.org/en/13/core/type_basics.html#generic-types

![img](./Flask_migrate%E4%BD%BF%E7%94%A8.assets/fab15ffaa98541768d64300d7ea9d181.png)



## 3、对数据库的连接

配置数据库的连接路径格式,(以MySQL为例)：

```python
# setting.py

def Config:
 	# mysql+pymysql://user:password@IP地址:port/数据库
	SQLALCHEMY_DATABASE_URI = 'mysql+pymysql://root:123456@127.0.0.1:3306/db'
	
	# 如果设置成 True (默认情况)，Flask-SQLAlchemy 将会追踪对象的修改并且发送信号。这需要额外的内存， 如果不必要的可以禁用它。
	SQLALCHEMY_TRACK_MODIFICATIONS = False

```

```python
# 数据库配置
HOSTNAME = "localhost"
PORT = 3306
USERNAME = "root"
PASSWORD = "000000"
DATABASE = "zhiliaooa"
DB_URI = f"mysql+pymysql://{USERNAME}:{PASSWORD}@{HOSTNAME}:{PORT}/{DATABASE}?charset=utf8"
SQLALCHEMY_DATABASE_URI = DB_URI
```



#### 4、最终的app.py文件

```python
# app.py

from flask import Flask
from flask_script import Manager
from flask_migrate import Migrate, MigrateCommand
from flask_sqlalchemy import SQLAlchemy

from model import User  # 导入模型中的User表
# (重点，不导入模型表虽然不会报错，但是执行命令后，数据库不会建表)

from setting import Config # 导入数据库连接配置

app = Flask(__name__)

# 导入配置信息
app.config.from_object(Config )


# 创建数据库sqlalchemy工具对象
db = SQLAlchemy()

# 创建数据库迁移工具对象
migrate = Migrate(app=app,db=db)

#让python支持命令行工作
manager = Manager(app)

# 向manager对象中添加数据库操作命令
manger.add_command('db', MigrateCommand)

'''
MigrateCommand是flask-migrate集成的一个命令。
因此想要添加到脚本命令中，需要采用managre.add_command('db', MigrateCommand)的方式.
以后运行python app.py db xxx的命令，其实就是执行MigrateCommand。
'''

if __name__ == '__main__':
    manger.run()

```



#### 5、使用命令操作数据库（[数据库迁移](https://so.csdn.net/so/search?q=数据库迁移&spm=1001.2101.3001.7020)同步:）



**第1步：init 初始化一个迁移文件夹**

产生一个 migrations文件夹，这个文件作用：主要记录版本号 。

一个项目只需要init一次

```bash
>>>flask db init 

# 数据库下会生成一个表(alembic_version,主要记录的是版本文件
# 降级也是降为上一个版本：downgrade)
```



**第2步：迁移**

自动产生一个版本文件（里面是对数据库的操作：upgrade；

比如增加了什么字段、建的什么表，删除了什么类型等）

```bash
>>> flask db migrate
```





**第3步：同步**

生成数据库的表，将版本号的内容同步到数据表中(alembic_version)

最后这个才是将映射文件真正映射到数据库中：

```bash
>>> flask db upgrade
```



**降级还原时使用下面的，否则不用**

```bash
>>>python app.py db downgrade {{版本号}}  # 版本号选填
```



总结：

只要对模型（数据表）进行了修改（比如：**修改、删除了字段等**），就需要不断进行“迁移”、“同步”操作

**前提**：py文件的版本号-必须与-数据库表(alembic_version)中的版本号一致