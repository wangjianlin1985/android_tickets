# android_tickets
安卓Android火车票查询预订系统毕业源码案例设计

系统开发环境: Windows + Myclipse(服务器端) + Eclipse(手机客户端) + mysql数据库

服务器也可以用Eclipse或者idea等工具，客户端也可以采用android studio工具！

系统客户端和服务器端架构技术: 界面层，业务逻辑层，数据层3层分离技术，MVC设计思想！

服务器和客户端数据通信格式：json格式,采用servlet方式

【服务器端采用SSH框架，请自己启动tomcat服务器，hibernate会自动生成数据库表的哈！】

hibernate生成数据库表后，只需要在admin管理员表中加个测试账号密码就可以登录后台了哈！

下面是数据库的字段说明：

用户信息: 用户名,密码,姓名,性别,出生日期,身份证,籍贯,账户余额,照片,家庭地址
站点信息: 记录编号,站点名称,联系人,联系电话,邮编,通讯地址
车次信息: 记录编号,车次,始发站,终到站,开车日期,席别,票价,总座位数,剩余座位数,开车时间,终到时间,历时
座位席别: 记录编号,席别名称
订单信息: 记录编号,用户,车次信息,车次,始发站,终到站,开车日期,席别,座位信息,总票价,开车时间,终到时间
留言信息: 记录编号,留言标题,留言内容,留言人,留言时间
新闻公告: 记录编号,标题,新闻内容,发布日期
充值信息: 记录编号,充值用户,充值金额,充值时间
