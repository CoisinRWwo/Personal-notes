## 数据表示

- 原码

- 反码

- 补码

- 移码

|      | 数值1     | 数值-1    | 1-1       |
| ---- | --------- | --------- | --------- |
| 原码 | 0000 0001 | 1000 0001 | 1000 0010 |
| 反码 | 0000 0001 | 1111 1110 | 1111 1111 |
| 补码 | 0000 0001 | 1111 1111 | 0000 0000 |
| 移码 | 1000 0001 | 0111 1111 | 1000 0000 |



### 浮点数运算

浮点数表示：**N = M*R^e**

其中M称为尾数，e是指数，R为基数。



例子：

1000  -->  1.0 * 10^3       119  -->  1.19 * 10^2

以上两个数相加，将低指数转为高指数：(1.0 * 10^3) + (0.119 * 10^3) = 1.119 * 10^3

注：要注意小数点左边的数字要保持在一位。



## 计算机结构

![image-20230410141421347](note.assets/image-20230410141421347.png)

### 结构分类 Flynn

![image-20230410141506148](note.assets/image-20230410141506148.png)



### CISC与RISC111

![image-20230410141552460](note.assets/image-20230410141552460.png)



## 流水线

### 概念

![image-20230410141646174](note.assets/image-20230410141646174.png)



### 流水线计算

- 流水线周期为执行时间最长的一段
- 流水线计算公式：
  - 一条指令执行时间 +（指令条数 - 1）* 流水线周期
  - 理论公式：(t1 + t1 + …… + tk) + (n-1) * 流水线周期
  - 实践公式：(k+n-1) * 流水线周期 (其中k指的是几部分操作)

![image-20230410142927048](note.assets/image-20230410142927048.png)

![image-20230410142936522](note.assets/image-20230410142936522.png)

理论公式：(2+2+1) + (100-1) * 2 = 203

实践公式：(3 + 100 - 1) * 2 = 204

注：在选项中有理论公式的答案优先选择，没有则选择实践公式



### 流水线吞吐率计算

![image-20230410143416308](note.assets/image-20230410143416308.png)

以上一案列的计算结果进行计算：

TP = 100 / 203

最大吞吐率：1/2



### 流水线加速比

![image-20230410143631712](note.assets/image-20230410143631712.png)

以之前的流水线计算结果进行计算：

**S = (2+2+1)*100 / 203**



### 流水线的效率

![image-20230410143843568](note.assets/image-20230410143843568.png)

**E = (t + t + t + 3t) * 4 / 15t * 4 = 24t / 60t**



## 层次存储结构

![image-20230410144247171](note.assets/image-20230410144247171.png)

其中cache按k为单位，是1024k

内存为G或M，也是1024	



### Cache 概念

![image-20230410144401002](note.assets/image-20230410144401002.png)

假设Cache命中率为95%，Cache的周期时间为1ns，主存储器周期时间为1ms = 1000ns

平均周期则为：**1ns * 95% + (1 - 95%) * 1000ns = 50.95ns**



### 局部性原理

- 时间局部性
- 空间局部性
- 工作集原理：工作集是进程运行时被频繁访问的页面集合

时间局部性例：

```
int i,s=0;
for(i=1;i<1000;i++)
	for(j=1; j<1000:j++)
		s+=j;
printf("结果为:%d", s)
即刚访问完的数据再次访问
```

空间局部性以数组为例：不停的对刚创建进行初始化的时候



### 主存 - 分类

![image-20230410145130166](note.assets/image-20230410145130166.png)



### 主存 - 编址

![image-20230410145216040](note.assets/image-20230410145216040.png)

例子：

![image-20230410145224721](note.assets/image-20230410145224721.png)

先将C7FFFH补1，就变成C8000H。

然后C8000H-AC000H,得1C000H。

将结果转换为十进制，得114688，去除1024，得112K个地址单元。

内存块是 112 * 16 。

所以是（112K * 16） / （28 * 16 * x），得4 



## 磁盘结构与参数

![image-20230412191652581](note.assets/image-20230412191652581.png)

![image-20230412191831914](note.assets/image-20230412191831914.png)

![image-20230412192411250](note.assets/image-20230412192411250.png)

处理11个记录得最长时间为366ms

![image-20230412192715380](note.assets/image-20230412192715380.png)

最短则为66ms



## 总线

![image-20230412192827099](note.assets/image-20230412192827099.png)



## 系统可靠性分析 

### 串联系统与并联系统

![image-20230412193157414](note.assets/image-20230412193157414.png)

![image-20230412193404023](note.assets/image-20230412193404023.png)



### 模冗余系统与混合系统

![image-20230412193558391](note.assets/image-20230412193558391.png)

![image-20230412193651514](note.assets/image-20230412193651514.png)



## 差错控制- CRC与海明校验码

![image-20230412193722349](note.assets/image-20230412193722349.png)

![image-20230412194641696](note.assets/image-20230412194641696.png)

![image-20230412194948963](note.assets/image-20230412194948963.png)

![image-20230412195333994](note.assets/image-20230412195333994.png)

![image-20230412195530622](note.assets/image-20230412195530622.png)



## 操作系统

### 概述

![image-20230412195614486](note.assets/image-20230412195614486.png)

![image-20230412195827033](note.assets/image-20230412195827033.png)



### 进程管理 

#### 进程的状态

![image-20230412195934265](note.assets/image-20230412195934265.png)



#### 前趋图

![image-20230412200439182](note.assets/image-20230412200439182.png)

 

#### 进程的同步与进制

![image-20230412200652800](note.assets/image-20230412200652800.png)

![image-20230412200947221](note.assets/image-20230412200947221.png)



#### PV操作

![image-20230412201154339](note.assets/image-20230412201154339.png)

**P操作是申请资源，S减1小于0说明S小于1，证明当前没有可申请的资源，所以这个进程要进入阻塞状态，反之就是有可使用的资源，可继续执行**

**s就像是临界资源数量，为了防止随机执行导致进程队列满，当执行v操作释放资源的时候，需要看当前进程队列中是否有进程在排队等候，所以小于等于0的时候直接调度进程从S<0语句下面执行**

![image-20230412201557484](note.assets/image-20230412201557484.png)

![image-20230412202043679](note.assets/image-20230412202043679.png)

例：

![image-20230412202212960](note.assets/image-20230412202212960.png)

![image-20230412202726251](note.assets/image-20230412202726251.png)

![image-20230412203322240](note.assets/image-20230412203322240.png)



#### 死锁问题

![image-20230412203505425](note.assets/image-20230412203505425.png)

![image-20230412203737323](note.assets/image-20230412203737323.png)

![image-20230415085508771](note.assets/image-20230415085508771.png)



#### 银行家算法

![image-20230415085559820](note.assets/image-20230415085559820.png)

![image-20230415085629638](note.assets/image-20230415085629638.png)

![image-20230415085900592](note.assets/image-20230415085900592.png)

![image-20230415090126719](note.assets/image-20230415090126719.png)



### 存储管理

![image-20230415090417026](note.assets/image-20230415090417026.png)



#### 页式存储组织

![image-20230415091335027](note.assets/image-20230415091335027.png)

![image-20230415092309636](note.assets/image-20230415092309636.png)



#### 段式存储组织

![image-20230415092401720](note.assets/image-20230415092401720.png)



#### 段页式存储组织

![image-20230415092618122](note.assets/image-20230415092618122.png)



#### 快表

快表是一块小容量的相联存储器(Associative Memory) ，由高速缓存器组成，速度快，并且可以从硬件上保证按内容并行查找，- 般用来存放当前访问最频繁的少数活动页面的页号。



#### 页面置换算法

![image-20230415132253155](note.assets/image-20230415132253155.png)

![image-20230415133807776](note.assets/image-20230415133807776.png)

![image-20230415133815420](note.assets/image-20230415133815420.png)

![image-20230415134100178](note.assets/image-20230415134100178.png)

指令只会一次，数据则是两次



#### 索引文件结构

![image-20230415152020070](note.assets/image-20230415152020070.png)

![image-20230415152845557](note.assets/image-20230415152845557.png)



#### 文件和树型目录结构

![image-20230415153116602](note.assets/image-20230415153116602.png)





#### 空闲存储空间的管理

![image-20230416143518683](note.assets/image-20230416143518683.png)

![image-20230416144007315](note.assets/image-20230416144007315.png)

![image-20230416143946074](note.assets/image-20230416143946074.png)

字从1开始算，位置从0开始算



#### 数据传输控制方式

![image-20230416151850451](note.assets/image-20230416151850451.png)

**主要前三种**



### 设备管理

#### 虚设备与SPOOLING技术

![image-20230416152115586](note.assets/image-20230416152115586.png)



### 微内核操作系统

![image-20230416152504537](note.assets/image-20230416152504537.png)

![image-20230416152622820](note.assets/image-20230416152622820.png)



## 数据库

### 三级模式-两级映射

![image-20230416155519508](note.assets/image-20230416155519508.png)

![image-20230416155525832](note.assets/image-20230416155525832.png)



### 数据库设计过程

![image-20230416155703623](note.assets/image-20230416155703623.png)



### ER模型

![image-20230416160721000](note.assets/image-20230416160721000.png)

![image-20230416162006229](note.assets/image-20230416162006229.png)

![image-20230416162150833](note.assets/image-20230416162150833.png)



### 关系代数

![image-20230416162724151](note.assets/image-20230416162724151.png)

![image-20230416163011624](note.assets/image-20230416163011624.png)

![image-20230416163022445](note.assets/image-20230416163022445.png)



### 规范化理论

#### 函数依赖

![image-20230416163256319](note.assets/image-20230416163256319.png)



#### 价值与用途

![image-20230416164532016](note.assets/image-20230416164532016.png)



#### 键

![image-20230416164901410](note.assets/image-20230416164901410.png)

![image-20230416165425695](note.assets/image-20230416165425695.png)

![image-20230416170150800](note.assets/image-20230416170150800.png)



#### 范式

![image-20230416170246147](note.assets/image-20230416170246147.png)



#### 第一范式

![image-20230416170748832](note.assets/image-20230416170748832.png)



#### 第二范式

![image-20230416170815015](note.assets/image-20230416170815015.png)



#### 第三范式

![image-20230416171106050](note.assets/image-20230416171106050.png)



#### BC范式

![image-20230416171318743](note.assets/image-20230416171318743.png)



#### 例题

![image-20230416172430309](note.assets/image-20230416172430309.png)



#### 模式分解

![image-20230416172456939](note.assets/image-20230416172456939.png)

![image-20230416172945613](note.assets/image-20230416172945613.png)

![image-20230416173341231](note.assets/image-20230416173341231.png)

![image-20230416173424552](note.assets/image-20230416173424552.png)



### 并发控制

![image-20230416173743160](note.assets/image-20230416173743160.png)



#### 存在的问题示例

![image-20230416200419307](note.assets/image-20230416200419307.png)



#### 封锁协议

![image-20230416200616272](note.assets/image-20230416200616272.png)



#### 数据库的完整性约束

* 实体完整性约束
* 参照完整性约束
* 用户自定义完整性约束
* 触发器



#### 数据库安全

|      措施      |                             说明                             |
| :------------: | :----------------------------------------------------------: |
| 用户标识和鉴定 | 最外层的安全保护措施，可以使用用户帐户、口令及随机数检验等方式 |
|    存取控制    | 对用户进行授权，包括操作类型 (如查找、插入、删除、修改等动作）和数据对象(主要是数据范围)的权限。 |
| 密码存储和传输 |                   对远程终端信息用密码传输                   |
|   视图的保护   |                        对视图进行授权                        |
|      审计      | 使用一个专用文件或数据库，自动将用户对数据库的所有操作记录下来 |



#### 数据备份

![image-20230416201806557](note.assets/image-20230416201806557.png)

![image-20230416202146865](note.assets/image-20230416202146865.png)



#### 数据库故障与恢复

![image-20230416202557331](note.assets/image-20230416202557331.png)



### 数据仓库与数据挖掘

![image-20230416202656179](note.assets/image-20230416202656179.png)

![image-20230416203449290](note.assets/image-20230416203449290.png)



### 反规范化

![image-20230416203519410](note.assets/image-20230416203519410.png)



### 大数据

![image-20230416203859442](note.assets/image-20230416203859442.png)

![image-20230416204114751](note.assets/image-20230416204114751.png)



## 计算机网络

### OSI/RM七层模型

![image-20230422141009245](note.assets/image-20230422141009245.png)

![image-20230422142406004](note.assets/image-20230422142406004.png)



### 网络技术标准与协议

![image-20230422142442846](note.assets/image-20230422142442846.png)



#### TCP协议

![image-20230422143105321](note.assets/image-20230422143105321.png)



#### DHCP协议

![image-20230422143337507](note.assets/image-20230422143337507.png)



#### DNS协议

![image-20230422143804089](note.assets/image-20230422143804089.png)

![image-20230422144140636](note.assets/image-20230422144140636.png)

![image-20230422144406644](note.assets/image-20230422144406644.png)



## 计算机网络的分类

### 拓扑结构

![image-20230422144456673](note.assets/image-20230422144456673.png)



### 网络规划与设计

![image-20230422144903242](note.assets/image-20230422144903242.png)



### 逻辑网络设计

![image-20230422145321643](note.assets/image-20230422145321643.png)



### 分层设计

![image-20230422145353827](note.assets/image-20230422145353827.png)



### IP地址

![image-20230422145950222](note.assets/image-20230422145950222.png)

 



### 子网划分

![image-20230422150444022](note.assets/image-20230422150444022.png)

![image-20230422151706710](note.assets/image-20230422151706710.png)

![image-20230422151719394](note.assets/image-20230422151719394.png)



### 无分类编制

![image-20230422151805318](note.assets/image-20230422151805318.png)

![image-20230422152010766](note.assets/image-20230422152010766.png)

c



### 特殊含义的IP地址

![image-20230422152046663](note.assets/image-20230422152046663.png)



### 计算机网络与信息安全

#### HTML

![image-20230422152646843](note.assets/image-20230422152646843.png)



### 无线网

![image-20230422152838510](note.assets/image-20230422152838510.png)



### 网络接入技术

![image-20230422153006800](note.assets/image-20230422153006800.png)



### IPV6

![image-20230422154817054](note.assets/image-20230422154817054.png)



## 信息安全系统属性

![image-20230422155252989](note.assets/image-20230422155252989.png)



### 对称加密技术

![image-20230422155740521](note.assets/image-20230422155740521.png)



### 非对称加密技术

![image-20230422155804327](note.assets/image-20230422155804327.png)



### 信息摘要

![image-20230422195439978](note.assets/image-20230422195439978.png)



### 数字签名

![image-20230422200146922](note.assets/image-20230422200146922.png)



### 数字信封与PGP

![image-20230422203539822](note.assets/image-20230422203539822.png)

![image-20230422204357568](note.assets/image-20230422204357568.png)





## 系统安全分析与设计

### 网络安全 - 各个网络层次的安全保障

![image-20230422204613076](note.assets/image-20230422204613076.png)



### 网络威胁与攻击

![image-20230422213353354](note.assets/image-20230422213353354.png)

![image-20230422214010296](note.assets/image-20230422214010296.png)



### 防火墙

![image-20230422214052709](note.assets/image-20230422214052709.png)





## 数据结构与算法基础

![image-20230502124556541](note.assets/image-20230502124556541.png)



### 数组

![image-20230502124631708](note.assets/image-20230502124631708.png)

![image-20230502125210112](note.assets/image-20230502125210112.png)



### 稀疏矩阵

![image-20230502125243065](note.assets/image-20230502125243065.png)

![image-20230502125728482](note.assets/image-20230502125728482.png)

再将A[1,1]代入，得到A[3]即为正确答案



## 数据结构的定义

![image-20230502125849080](note.assets/image-20230502125849080.png)



### 线性表的定义

![image-20230502130013372](note.assets/image-20230502130013372.png)



#### 线性表

![image-20230502130026531](note.assets/image-20230502130026531.png)

![image-20230502130202665](note.assets/image-20230502130202665.png)



### 顺序存储与链式存储对比

![image-20230502130449542](note.assets/image-20230502130449542.png)



### 队列与栈

![image-20230502130857373](note.assets/image-20230502130857373.png)

![image-20230502131704786](note.assets/image-20230502131704786.png)



### 广义表

![image-20230502132128297](note.assets/image-20230502132128297.png)

深度为重度，如果（b，c）换位（b1，b2）则为三重

**表头为第一个元素，表位则是除表头以外所有元素**

![image-20230502132351458](note.assets/image-20230502132351458.png)

![image-20230502132400591](note.assets/image-20230502132400591.png)



### 数与二叉树

![image-20230502132420406](note.assets/image-20230502132420406.png)

![image-20230502132720559](note.assets/image-20230502132720559.png)



### 二叉树的遍历

![image-20230502133619581](note.assets/image-20230502133619581.png)

前序遍历：12457836

中序遍历：42785136（左中右）

后序遍历：48752631（左右中）



### 反向构造二叉树

![image-20230502134357372](note.assets/image-20230502134357372.png)



### 树转二叉树

![image-20230502134714400](note.assets/image-20230502134714400.png)



### 查找二叉树

又叫“**排序二叉树**”

![image-20230502134949183](note.assets/image-20230502134949183.png)



### 最优二叉树

又称“**哈夫曼树**”

![image-20230502135430933](note.assets/image-20230502135430933.png)

![image-20230502135541568](note.assets/image-20230502135541568.png)

![image-20230502135752278](note.assets/image-20230502135752278.png)



### 线索二叉树

![image-20230502135858084](note.assets/image-20230502135858084.png)

![image-20230502140116150](note.assets/image-20230502140116150.png)



### 平衡二叉树

![image-20230502140151225](note.assets/image-20230502140151225.png)

![image-20230502140407979](note.assets/image-20230502140407979.png)



### 图-基本概念

![image-20230502173609995](note.assets/image-20230502173609995.png)



### 图的存储-邻接矩阵

![image-20230502173725104](note.assets/image-20230502173725104.png)



### 图的存储-邻接表

![image-20230502173807708](note.assets/image-20230502173807708.png)



### 图的遍历

![image-20230502173902866](note.assets/image-20230502173902866.png)



### 图-拓扑排序

![image-20230502174042234](note.assets/image-20230502174042234.png)



### 图的最小生成树

#### 普里姆算法

![image-20230502174508667](note.assets/image-20230502174508667.png)



#### 克鲁斯卡尔算法

![image-20230502174601317](note.assets/image-20230502174601317.png)



### 算法的特性

![image-20230502174619684](note.assets/image-20230502174619684.png)



### 算法的复杂度

![image-20230502174800375](note.assets/image-20230502174800375.png)

赋值语句为O(1)，单循环为O(n)，嵌套循环为O(n^2)

树的查询为O(log2n)



### 查找-顺序查找

![image-20230502175303635](note.assets/image-20230502175303635.png)



### 查找-二分查找

![image-20230502175451200](note.assets/image-20230502175451200.png)

![image-20230502175831973](note.assets/image-20230502175831973.png)

![image-20230502175836999](note.assets/image-20230502175836999.png)



### 查找-散列表

![image-20230502175913557](note.assets/image-20230502175913557.png)

![image-20230502180059807](note.assets/image-20230502180059807.png)

线性探测法：往后占位



### 排序

![image-20230502180313054](note.assets/image-20230502180313054.png)



#### 直接插入排序

![image-20230502180656864](note.assets/image-20230502180656864.png)



#### 希尔排序

![image-20230502180931732](note.assets/image-20230502180931732.png)



#### 直接选择排序

![image-20230502181050874](note.assets/image-20230502181050874.png)



#### 堆排序

![image-20230502181251949](note.assets/image-20230502181251949.png)

![image-20230502181256041](note.assets/image-20230502181256041.png)

![image-20230502181621138](note.assets/image-20230502181621138.png)

![image-20230502181845689](note.assets/image-20230502181845689.png)



#### 冒泡排序

![image-20230502181921655](note.assets/image-20230502181921655.png)



#### 快速排序

![image-20230502182250965](note.assets/image-20230502182250965.png)



#### 归并排序

![image-20230502182516092](note.assets/image-20230502182516092.png)



#### 基数排序

![image-20230502182948156](note.assets/image-20230502182948156.png)



#### 时间复杂度与空间复杂度

![image-20230502183014997](note.assets/image-20230502183014997.png)



## 程序设计语言与语言处理程序基础

![image-20230502191055581](note.assets/image-20230502191055581.png)



### 编译过程

![image-20230502191140064](note.assets/image-20230502191140064.png)



### 文法定义

![image-20230502191728561](note.assets/image-20230502191728561.png)

![image-20230502191801530](note.assets/image-20230502191801530.png)

#### 语法推导树

![image-20230502192017842](note.assets/image-20230502192017842.png)



### 有限自动机与正规式

![image-20230502193242431](note.assets/image-20230502193242431.png)

![](note.assets/image-20230502193303022.png)

![image-20230502193522206](note.assets/image-20230502193522206.png)

![image-20230502193707442](note.assets/image-20230502193707442.png)



### 表达式

![image-20230502193838132](note.assets/image-20230502193838132.png)

![image-20230502193925006](note.assets/image-20230502193925006.png)



### 函数调用-传值与传址

![image-20230502194033551](note.assets/image-20230502194033551.png)![image-20230502194212459](note.assets/image-20230502194212459.png)



### 各种程序语言的特点

![image-20230502194302763](note.assets/image-20230502194302763.png)



## 法律法规

![image-20230502194411770](note.assets/image-20230502194411770.png)



### 保护期限

![image-20230502194742700](note.assets/image-20230502194742700.png)



### 知识产权人确定

![image-20230502200235143](note.assets/image-20230502200235143.png)



![image-20230502200522655](note.assets/image-20230502200522655.png)



### 侵权判定

![image-20230502200858558](note.assets/image-20230502200858558.png)

![image-20230502201143864](note.assets/image-20230502201143864.png)



### 标准化基础知识 - 标准的分类

![image-20230502201520098](note.assets/image-20230502201520098.png)



### 标准的编号

![image-20230502201642558](note.assets/image-20230502201642558.png)



## 多媒体基础

![image-20230502201948218](note.assets/image-20230502201948218.png)



### 音频相关概念

![image-20230502202339953](note.assets/image-20230502202339953.png)



### 图像相关概念

![image-20230502202431988](note.assets/image-20230502202431988.png)

![image-20230502202510495](note.assets/image-20230502202510495.png)

![image-20230502202710847](note.assets/image-20230502202710847.png)



### 媒体的种类

![image-20230502202742701](note.assets/image-20230502202742701.png)



### 多媒体相关计算问题

![image-20230502203253527](note.assets/image-20230502203253527.png)

![image-20230502203731025](note.assets/image-20230502203731025.png)



### 常见多媒体标准

![image-20230502203850885](note.assets/image-20230502203850885.png)



### 数据压缩基础

![image-20230502204112018](note.assets/image-20230502204112018.png)



### 有损压缩与无损压缩

![image-20230502204413027](note.assets/image-20230502204413027.png)



## 软件开发模型

![image-20230502204627757](note.assets/image-20230502204627757.png)



### 瀑布模型

![image-20230502204805109](note.assets/image-20230502204805109.png)



### 其他经典模型

![image-20230502204949578](note.assets/image-20230502204949578.png)



### 螺旋模型

![image-20230502205604948](note.assets/image-20230502205604948.png)



### V模型

![image-20230502205728594](note.assets/image-20230502205728594.png)



### 喷泉模型

![image-20230502210007605](note.assets/image-20230502210007605.png)

面向对象的开发



![image-20230502210039424](note.assets/image-20230502210039424.png)



### 构建组装模型（CBSD）

![image-20230502210148380](note.assets/image-20230502210148380.png)



### 统一过程（UP、RUP）

![image-20230502210401876](note.assets/image-20230502210401876.png)



### 敏捷开发方法

![image-20230502210821443](note.assets/image-20230502210821443.png)



### 信息系统开发方法

![image-20230502211356245](note.assets/image-20230502211356245.png)



### 需求分类与需求获取

![image-20230502211923587](note.assets/image-20230502211923587.png)



### 结构化设计

![image-20230502212430742](note.assets/image-20230502212430742.png)



#### 内聚和耦合

![image-20230502212644394](note.assets/image-20230502212644394.png)



#### 系统模块/模块结构

![image-20230502212913106](note.assets/image-20230502212913106.png)





### 软件测试

#### 测试原则与类型

![image-20230502212943663](note.assets/image-20230502212943663.png)



#### 测试用例设计

![image-20230502214131522](note.assets/image-20230502214131522.png)



#### 测试阶段

![image-20230503085548382](note.assets/image-20230503085548382.png)



#### McCabe复杂度

![image-20230503090411774](note.assets/image-20230503090411774.png)



#### 系统运行与维护

![image-20230503090431900](note.assets/image-20230503090431900.png)



### 软件过程改进 - CMMI

![image-20230503091218844](note.assets/image-20230503091218844.png)



### 项目管理

![image-20230503091743120](note.assets/image-20230503091743120.png)

![image-20230503092145109](note.assets/image-20230503092145109.png)

![image-20230503092207254](note.assets/image-20230503092207254.png)



## 面向对象设计

### 需求开发

#### 需求分析 - OOA

![image-20230503131016122](note.assets/image-20230503131016122.png)



### 设计原则

![image-20230503131426496](note.assets/image-20230503131426496.png)



### UML

![image-20230503132419136](note.assets/image-20230503132419136.png)



### 设计模式的概念

![image-20230503132435965](note.assets/image-20230503132435965.png)



### 设计模式的分类

![image-20230503132826702](note.assets/image-20230503132826702.png)



#### 创建型模式

![image-20230503132915987](note.assets/image-20230503132915987.png)



#### 结构型模式

![image-20230503133237935](note.assets/image-20230503133237935.png)



#### 行为型模式

![image-20230503133706980](note.assets/image-20230503133706980.png)

![image-20230503134303767](note.assets/image-20230503134303767.png)



## 数据流图

![image-20230503134638995](note.assets/image-20230503134638995.png)



### 基本概念

![image-20230503134747048](note.assets/image-20230503134747048.png)

![image-20230503135410360](note.assets/image-20230503135410360.png)



### 数据字典

![image-20230503135501133](note.assets/image-20230503135501133.png)

![image-20230503135745782](note.assets/image-20230503135745782.png)



### 数据流图平衡原则

![image-20230503135807056](note.assets/image-20230503135807056.png)

![image-20230503140127494](note.assets/image-20230503140127494.png)



### 答题技巧

![image-20230503140554935](note.assets/image-20230503140554935.png)



### 分析1

![image-20230503141125458](note.assets/image-20230503141125458.png)

![image-20230503141224141](note.assets/image-20230503141224141.png)

![image-20230503141602951](note.assets/image-20230503141602951.png)

![image-20230503141623375](note.assets/image-20230503141623375.png)

1、奇迹

2、黑洞

3、数据流命名问题

4、输入流加工不能产出输出流



### 分析2

![image-20230503142347322](note.assets/image-20230503142347322.png)

![image-20230503142202686](note.assets/image-20230503142202686.png)

![image-20230503142336283](note.assets/image-20230503142336283.png)

![image-20230503142607783](note.assets/image-20230503142607783.png)



## 数据设计



### 设计过程

![image-20230503142738061](note.assets/image-20230503142738061.png)



### ER模型

![image-20230503142920094](note.assets/image-20230503142920094.png)



### ER图向关系模型的转换

![image-20230503143104086](note.assets/image-20230503143104086.png)



### 分析1

![image-20230503143337554](note.assets/image-20230503143337554.png)

![image-20230503143449325](note.assets/image-20230503143449325.png)

![image-20230503144016975](note.assets/image-20230503144016975.png)



### 分析2

![image-20230503144533190](note.assets/image-20230503144533190.png)

![image-20230503144550307](note.assets/image-20230503144550307.png)

![image-20230503144554939](note.assets/image-20230503144554939.png)

![image-20230503144559576](note.assets/image-20230503144559576.png)

![image-20230503144611034](note.assets/image-20230503144611034.png)

![image-20230503144614428](note.assets/image-20230503144614428.png)



## UML建模



### 用例图

![image-20230503150731723](note.assets/image-20230503150731723.png)



### 类图与对象图

![image-20230503151107649](note.assets/image-20230503151107649.png)

![image-20230503151229206](note.assets/image-20230503151229206.png)

![image-20230503151330906](note.assets/image-20230503151330906.png)



### 顺序图

![image-20230503151338963](note.assets/image-20230503151338963.png)



### 活动图

![](note.assets/image-20230503151537705.png)

![image-20230503151657917](note.assets/image-20230503151657917.png)



### 状态图

![image-20230503151750337](note.assets/image-20230503151750337.png)



### 通信图

![image-20230503151917561](note.assets/image-20230503151917561.png)



### 分析1

![image-20230503152117789](note.assets/image-20230503152117789.png)

![image-20230503152130248](note.assets/image-20230503152130248.png)

![image-20230503152133197](note.assets/image-20230503152133197.png)

![image-20230503152137452](note.assets/image-20230503152137452.png)

![image-20230503165354964](note.assets/image-20230503165354964.png)



### 分析2

![image-20230503165849168](note.assets/image-20230503165849168.png)

![image-20230503165943539](note.assets/image-20230503165943539.png)

![image-20230503165953008](note.assets/image-20230503165953008.png)

![image-20230503170402675](note.assets/image-20230503170402675.png)

![image-20230503170437952](note.assets/image-20230503170437952.png)

状态模式



## 数据结构及算法应用

![image-20230503170915115](note.assets/image-20230503170915115.png)



### 分治法

![image-20230503171101268](note.assets/image-20230503171101268.png)



#### 递归技术

![image-20230503171237990](note.assets/image-20230503171237990.png)



#### 二分法查找

![image-20230503171443497](note.assets/image-20230503171443497.png)



### 回溯法

![image-20230503184523562](note.assets/image-20230503184523562.png)



### 贪心法

![image-20230503184724481](note.assets/image-20230503184724481.png)



### 动态规划法

![image-20230503185100656](note.assets/image-20230503185100656.png)



### 分析1

![image-20230503185922992](note.assets/image-20230503185922992.png)

![image-20230503191351241](note.assets/image-20230503191351241.png)

![image-20230503190918409](note.assets/image-20230503190918409.png)



### 分析2

![image-20230503191016956](note.assets/image-20230503191016956.png)

![image-20230503191000987](note.assets/image-20230503191000987.png)



## 面向对象程序设计

![image-20230503193406011](note.assets/image-20230503193406011.png)