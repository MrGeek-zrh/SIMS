# 0. 项目简单说明

## 功能要求

+ 在页面上对人员信息进行CURD

# 1. 技术选型

+ 项目管理
  + maven
+ 数据库
  + MySQL
+ 开发语言
  + java
+ Dao层
  + JDBC+spring
+ Service层
  + spring
+ Controller层
  + springMVC
+ 前台
  + EasyUI for jquery
  + jsp
+ Web 服务器
  + tomcat
+ 版本控制
  + git

# 2. 开发工具和环境

+ eclipse 2020
+ jdk 1.8
+ linux minit 20.0
+ maven  3.6.3
+ tomcat 8
+ mysql 5.5
+ git
+ 部署环境
  + 阿里云 Ubuntu16.0

# 3. 后台系统搭建

## 3.1 创建一个maven项目

+ 项目名：SIMS
+ 打包方式：war 包

### 注意：

+ 创建完maven项目之后，可能会报错。
+ 原因：
  + 由于打包方式为war 包，所以项目中必须要有web.xml 文件。而eclipse 在创建maven项目时，不会帮我们自动创建web.xml文件，所以需要我们自己手动创建web.xml 文件
    + 在webapp 下，创建一个WEB-INF文件夹，然后在WEB-INF文件夹中创建web.xml文件

## 3.2 配置开发环境

+ 详情参考以前写的文章

### 3.2.1 配置tomcat 

### 3.2.2 指定JDK版本

### 3.2.3 指定maven版本

## 3.3 设计数据库，并导入数据

### 3.3.1 设计数据库

+ 创建数据库时必须指定编码为UTF-8

#### 3.3.1.1 人员表的结构

![](https://gitee.com/mrgeek-zrh/blogimage/raw/master/img/image-20200912140325487.png)

#### 3.3.1.2 具体的sql语句

```mysql
# 创建数据库
create database studentInfo DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

# 创建表
create table student(
    id int not null auto_increment primary key,
    name varchar(32),
    sex varchar(8),
    year int
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;
```

### 3.3.2 导入数据

+ 数据放在后面的JDBC 操作中导入

## 3.4 根据表的结构创建实体类

+ student.java

``` java
package com.mrgeek.entity;

import java.io.Serializable;

/**
 * 学生实体类
* <p>Title: student.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-12_17:45:39
* @version 1.0
 */
public class student implements Serializable{

	Long id;	//人员id
	String name;//人员姓名
	String sex;	//人员性别
	Long year;	//人员年龄
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Long getYear() {
		return year;
	}
	public void setYear(Long year) {
		this.year = year;
	}
	
}

```

## 3.5 JDBC操作

### 3.5.1 导入相关jar包

> <!-- mysql相关 -->
> 	<dependency>
> 	    <groupId>mysql</groupId>
> 	    <artifactId>mysql-connector-java</artifactId>
> 	    <version>5.1.47</version>
> 	</dependency>
> 	<dependency>
> 	    <groupId>commons-dbutils</groupId>
> 	    <artifactId>commons-dbutils</artifactId>
> 	    <version>1.6</version>
> 	</dependency>

### 3.5.2 编写JDBCUtil

``` java
package com.mrgeek.utils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * 
* <p>Title: JDBCUtil.java<／p>
* <p>Description: JDBC工具类<／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-12_16:44:11
* @version 1.0
 */
public class JDBCUtil {
	
	/**
	 * 和数据库建立连接
	* <p>Title: getConnection<／p>
	* <p>Description: <／p>
	* @return Connection 连接
	* @throws ClassNotFoundException
	* @throws SQLException
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		
		//1.加载驱动
		//确定链接的数据库的类型
		Class.forName("com.mysql.jdbc.Driver");
		//确定请求路径
		String url = "jdbc:mysql://localhost:3306/studentInfo?characterEncoding=UTF-8";
		//确定用户名
		String user = "root";
		//确定密码
		String password = "保密";
		
		//2.建立连接
		Connection connection = (Connection) DriverManager.getConnection(url,user,password);
		return connection;
		
	}
	
	/**
	 * 关闭连接数据库建立的连接通道
	* <p>Title: closeConnection<／p>
	* <p>Description: <／p>
	* @param resultSet 执行sql语句返回的结果
	* @param statement Statement 对象
	* @param connection 和数据库建立的链接
	 * @throws Exception 
	 */
	public static void closeJDBC(ResultSet resultSet,Statement statement,Connection connection) throws Exception {
		
		resultSet.close();
		statement.close();
		connection.close();
	}
	
	/**
	 * 关闭数据库连接
	* <p>Title: closeJDBC<／p>
	* <p>Description: <／p>
	* @param statement
	* @param connection
	* @throws SQLException
	 */
	public static void closeJDBC(Statement statement,Connection connection) throws SQLException {
		
		statement.close();
		connection.close();
	}

}
```

### 3.5.3 利用JDBC 向数据库中添加数据

```java
package com.mrgeek.addInfo;


import org.junit.Test;

import com.mrgeek.dao.StudentDao;
import com.mrgeek.dao.impl.StudentDaoImpl;
import com.mrgeek.entity.Student;

/**
 * 该类的主要目的是向数据库中添加数据
* <p>Title: addInfo.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-12_20:39:58
* @version 1.0
 */
public class addInfo {
	@Test
	public void InsertInfo() throws Exception  {
		// TODO Auto-generated method stub
		//调用Dao层的addInfo方法，向数据库中添加数据
		StudentDao studentDao = new StudentDaoImpl();
		
		String[] name1 = {"张","李","王","钱","赵","孙","杜","林","袁","何","杨","朱","刘","德","韩","习","大","小"}; 
		String[] name2 = {"张","李","王","钱","赵","孙","杜","林","袁","何","杨","朱","刘","德","看","吉","汗","韩"};
		String[] sex = {"男","女"};
		
		Student student;
		String randName;
		for (int i = 0; i < 1000; i++) {
			int j = (int)(Math.random()*17);
			int m = (int)(Math.random()*17);
			int k = (int)(Math.random()*1);
			long year = (int)(Math.random()*1000);
			student = new Student();
			
			randName = name1[j]+name2[m]+year;
			student.setName(randName);
			student.setSex(sex[k]);
			student.setYear(year);
			System.out.println(student);
			studentDao.addStudent(student);
		}
		
	}

}
```

## 3.6 配置spring

### 3.6.1 导入spring 所需jar包

> <!-- spring相关jar包 -->
> 	<dependency>
> 	    <groupId>org.springframework</groupId>
> 	    <artifactId>spring-beans</artifactId>
> 	    <version>4.1.6.RELEASE</version>
> 	</dependency>
> 	<dependency>
> 	    <groupId>org.springframework</groupId>
> 	    <artifactId>spring-context</artifactId>
> 	    <version>4.1.6.RELEASE</version>
> 	</dependency>
> 	<dependency>
> 	    <groupId>org.springframework</groupId>
> 	    <artifactId>spring-core</artifactId>
> 	    <version>4.1.6.RELEASE</version>
> 	</dependency>
> 	<dependency>
> 	    <groupId>org.springframework</groupId>
> 	    <artifactId>spring-expression</artifactId>
> 	    <version>4.1.6.RELEASE</version>
> 	</dependency>
> 	<dependency>
> 	    <groupId>commons-logging</groupId>
> 	    <artifactId>commons-logging</artifactId>
> 	    <version>1.1.3</version>
> 	</dependency>
>
> 	<dependency>
> 	    <groupId>org.springframework</groupId>
> 	    <artifactId>spring-web</artifactId>
> 	    <version>4.1.6.RELEASE</version>
> 	</dependency>

### 3.6.2 配置applicationContext.xml 文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.2.xsd
  	 http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.2.xsd
 	 ">

   	<!-- 配置包扫描器 -->
   	<context:component-scan base-package="com.mrgeek.dao"/>
   	<context:component-scan base-package="com.mrgeek.service" />
   	
</beans>
```

### 3.6.3 测试spring 是否正确配置

+ 可以正常获取到StudentDao的对象，就说明配置成功

#### 3.6.3.1 导入测试spring 所需jar包

> <!-- spring 测试所需jar 包 -->					
> 	<dependency>
> 	    <groupId>org.springframework</groupId>
> 	    <artifactId>spring-test</artifactId>
> 	    <version>4.1.6.RELEASE</version>
> 	    <scope>test</scope>
> 	</dependency>

#### 3.6.3.2 具体代码

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/*.xml")
public class StudentDaoTest {
	
	@Autowired
	private StudentDao studentDao;
	
	@Test
	public void testdeleteStudent() throws Exception {
		
		Student student = new Student();
		student.setId((long) 1000);
		
		int count = studentDao.deleteStudentById(student);
		System.out.println(count);
		
	}
}
```

#### 3.6.3.3 在web.xml 中配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
  <display-name>SIMS</display-name>
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.htm</welcome-file>
    <welcome-file>index.jsp</welcome-file>
  </welcome-file-list>
 
  <!-- 配置spring的监听器 -->
  	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:spring/applicationContext.xml</param-value>
	</context-param>
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
 </web-app>
```



## 3.7 编写DAO层

+ StudentDaoImpl.java
+ StudentDao.java 是一个接口，这里就不展示代码了，展示他的实现类

```java
package com.mrgeek.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mrgeek.dao.StudentDao;
import com.mrgeek.entity.Student;
import com.mrgeek.utils.JDBCUtil;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

@Repository
/**
 * 实现类
* <p>Title: StudentDaoImpl.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-12_17:40:20
* @version 1.0
 */
public class StudentDaoImpl implements StudentDao {

	/*
	 * 向数据库中添加数据
	 */
	public int addStudent(Student student) throws Exception {
		
		int count = 0;
		
		//和数据库建立连接
		Connection connection = JDBCUtil.getConnection();
		
		//编写sql语句
		String sql = "insert into student values(null,'"+student.getName()+"','"+student.getSex()+"',"+student.getYear()+")";
		
		//获取Statement 对象
		Statement statement = (Statement) connection.createStatement();
		
		//调用executeUpdate 方法，执行sql语句
		count = statement.executeUpdate(sql);
		
		//关流
		JDBCUtil.closeJDBC(statement, connection);
		
		return count;
	}

	public int deleteStudentById(Student student) throws Exception {
		
		int count = 0;
		
		//和数据库建立连接
		Connection connection = JDBCUtil.getConnection();
		
		//编写sql语句
		String sql = "delete from student where id="+student.getId();
		
		//获取Statement 对象
		Statement statement = (Statement) connection.createStatement();
		
		//调用executeUpdate 方法，执行sql语句
		count = statement.executeUpdate(sql);
		
		//关流
		JDBCUtil.closeJDBC(statement, connection);
		
		return count;
	}

	public int updateStudentById(Student student) throws Exception {
		int count = 0;
		
		//和数据库建立连接
		Connection connection = JDBCUtil.getConnection();
		
		//编写sql语句
		String sql = "update student set name='"+student.getName()+"',sex='"+student.getSex()+"',year="+student.getYear()+" where id="+student.getId();
		
		//获取Statement 对象
		Statement statement = (Statement) connection.createStatement();
		
		//调用executeUpdate 方法，执行sql语句
		count = statement.executeUpdate(sql);
		
		//关流
		JDBCUtil.closeJDBC(statement, connection);
		
		return count;
	}

	public Student findStudentById(long id) throws Exception {

		ResultSet set ;
		
		//和数据库建立连接
		Connection connection = JDBCUtil.getConnection();
		
		//编写sql语句
		String sql = "select * from student where id="+id;
		
		//获取Statement 对象
		Statement statement = (Statement) connection.createStatement();
		
		Student student1 = new Student();
		//调用executeUpdate 方法，执行sql语句
		set = statement.executeQuery(sql);
		while (set.next()) {
			//索引从1开始
			student1.setId(set.getLong(1));
			student1.setName(set.getString(2));
			student1.setSex(set.getString(3));
			student1.setYear(set.getLong(4));
		}
		return student1;
	}

	public List<Student> findStudentByName(String name) throws Exception {
		ResultSet set ;
		
		//和数据库建立连接
		Connection connection = JDBCUtil.getConnection();
		
		//编写sql语句
		String sql = "select * from student where name='"+name+"'";
		
		//获取Statement 对象
		Statement statement = (Statement) connection.createStatement();
		
		Student student1 ;
		List<Student>list = new ArrayList<Student>();
		//调用executeUpdate 方法，执行sql语句
		set = statement.executeQuery(sql);
		while (set.next()) {
			student1 = new Student();
			//索引从1开始
			student1.setId(set.getLong(1));
			student1.setName(set.getString(2));
			student1.setSex(set.getString(3));
			student1.setYear(set.getLong(4));
			list.add(student1);
		}
		return list;
	}

	public List<Student> findStudentByPageIndexAndPageSize(long pageIndex,long pageSize) throws Exception {
		ResultSet set ;
		
		//和数据库建立连接
		Connection connection = JDBCUtil.getConnection();
		
		long start = (pageIndex-1)*pageSize;
		//编写sql语句
		String sql = "select * from student limit "+start+","+pageSize;
		
		//获取Statement 对象
		Statement statement = (Statement) connection.createStatement();
		
		Student student1 ;
		List<Student>list = new ArrayList<Student>();
		//调用executeUpdate 方法，执行sql语句
		set = statement.executeQuery(sql);
		while (set.next()) {
			student1 = new Student();
			//索引从1开始
			student1.setId(set.getLong(1));
			student1.setName(set.getString(2));
			student1.setSex(set.getString(3));
			student1.setYear(set.getLong(4));
			list.add(student1);
		}
		return list;
	}

	public List<Student> findAllStudent() throws Exception {
		ResultSet set ;
		
		//和数据库建立连接
		Connection connection = JDBCUtil.getConnection();
		
		//编写sql语句
		String sql = "select * from student ";
		
		//获取Statement 对象
		Statement statement = (Statement) connection.createStatement();
		
		Student student1 ;
		List<Student>list = new ArrayList<Student>();
		//调用executeUpdate 方法，执行sql语句
		set = statement.executeQuery(sql);
		while (set.next()) {
			student1 = new Student();
			//索引从1开始
			student1.setId(set.getLong(1));
			student1.setName(set.getString(2));
			student1.setSex(set.getString(3));
			student1.setYear(set.getLong(4));
			list.add(student1);
		}
		return list;
	}

}

```

## 3.8 编写junit测试类

### 3.8.1 导入jar包

> <!-- junit测试相关 -->
> 	<dependency>
> 	    <groupId>junit</groupId>
> 	    <artifactId>junit</artifactId>
> 	    <version>4.12</version>
> 	    <scope>test</scope>
> 	</dependency>
> 	<dependency>
> 	    <groupId>org.hamcrest</groupId>
> 	    <artifactId>hamcrest-core</artifactId>
> 	    <version>1.3</version>
> 	    <scope>test</scope>
> 	</dependency>

### 3.8.2 编写junit测试

```java
package com.mrgeek.StudentDaoTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mrgeek.dao.StudentDao;
import com.mrgeek.entity.Student;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/*.xml")
public class StudentDaoTest {
	
	@Autowired
	private StudentDao studentDao;
	
	@Test
	public void testdeleteStudent() throws Exception {
		
		Student student = new Student();
		student.setId((long) 1000);
		
		int count = studentDao.deleteStudentById(student);
		System.out.println(count);
		
	}
	
	@Test
	public void testupdateStudent() throws Exception {
		
		Student student = new Student();
		student.setId((long) 999);
		student.setName("杨杨574");
		student.setSex("女");
		student.setYear((long) 850);
		
		int count = studentDao.updateStudentById(student);
		System.out.println(count);
		
	}
	
	@Test
	public void testfindStudentById() throws Exception {
		
		Student student = studentDao.findStudentById(999);
		System.out.println(student);
	}
	
	@Test
	public void testfindStudentByName() throws Exception {
		
		List<Student>list = studentDao.findStudentByName("杨杨574");
		for (Student student : list) {
			System.out.println(student);
		}
	}
	
	@Test
	public void testfindStudentByPageIndexAndPageSize() throws Exception {
		
		long pageIndex = 1,pageSize = 20;
		List<Student> list = studentDao.findStudentByPageIndexAndPageSize(pageIndex, pageSize);
		for (Student student : list) {
			System.out.println(student);
		}
	}
	
	@Test
	public void testfindAllStudent() throws Exception {
		
		List<Student>list = studentDao.findAllStudent();
		for (Student student : list) {
			System.out.println(student);
		}
	}

}

```

## 3.9 编写Service 层

+ service 的主要功能是
  + 处理业务逻辑
  + 处理DAO层抛出的异常

### 3.9.1 配置log4j 用于记录异常

#### 3.9.1.1 导入log4j 所需jar包

> <!-- log4j相关jar包 -->
> 	<dependency>
> 	    <groupId>log4j</groupId>
> 	    <artifactId>log4j</artifactId>
> 	    <version>1.2.17</version>
> 	</dependency>

#### 3.9.1.2 在web.xml 中配置log4j,用spring 管理log4j

> <!-- 加载log4j的配置文件log4j.properties -->
>  <context-param>
>      <param-name>log4jConfigLocation</param-name>
>      <param-value>classpath:log4j.properties</param-value>
>  </context-param>
>
> <!-- 设定刷新日志配置文件的时间间隔，这里设置为10s -->
> <context-param>
>  <param-name>log4jRefreshInterval</param-name>
>  <param-value>10000</param-value>
> </context-param>
>
> <!-- 加载Spring框架中的log4j监听器Log4jConfigListener -->
> <listener>
>  <listener-class>org.springframework.web.util.Log4jConfigListener</listener-class>
> </listener>

#### 3.9.1.3 编写properties 文件

> ### set log levels ###
>
> log4j.rootLogger = INFO, D, E
>
> log4j.appender.D = org.apache.log4j.RollingFileAppender
> log4j.appender.D.File =${scheduleProject}WEB-INF/logs/debug.log
> log4j.appender.D.Append = true
> log4j.appender.D.Threshold = DEBUG
> log4j.appender.D.MaxFileSize = 50000KB
> log4j.appender.D.layout = org.apache.log4j.PatternLayout
> log4j.appender.D.layout.ConversionPattern = %-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n
>
> log4j.appender.E = org.apache.log4j.RollingFileAppender
> log4j.appender.E.File = ${scheduleProject}WEB-INF/logs/error.log
> log4j.appender.E.Append = true
> log4j.appender.E.Threshold = ERROR
> log4j.appender.E.MaxFileSize = 50000KB
> log4j.appender.E.layout = org.apache.log4j.PatternLayout
> log4j.appender.E.layout.ConversionPattern =%-d{yyyy-MM-dd HH\:mm\:ss}  [ %l\:%c\:%t\:%r ] - [ %p ]  %m%n

#### 3.9.1.4 在代码中调用相关函数进行日志记录

### 3.9.2 编写service层代码

```java
package com.mrgeek.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrgeek.dao.StudentDao;
import com.mrgeek.entity.Student;
import com.mrgeek.service.StudentService;

import net.sf.json.JSONObject;

/**
 * service层接口实现类
* <p>Title: StudentServiceImpl.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-13_17:26:15
* @version 1.0
 */
@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentDao studentDao;
	
	private Logger logger = Logger.getLogger(StudentServiceImpl.class);

	/**
	 * @return -1:该用户已经存在 0:添加失败，联系管理员 1：添加成功
	 */
	public int addStudent(Student student) {
		
		int count = 0;
		try {
			List<Student>list = studentDao.findStudentByName(student.getName());
			//判断list不为空的方法：isEmpty 和 ==null 不同
			if (!list.isEmpty()&&list!=null) {
				count = -1;
			}else {
				count = studentDao.addStudent(student);
			}
		} catch (Exception e) {
			logger.error("[错误]StudentServiceImpl的addStudent方法报错,原因 : " + e.getMessage());
			e.printStackTrace();
		}
		return count;
	}

	public int deleteStudentById(Student student) {
		int count = 0;
		try {
			count = studentDao.deleteStudentById(student);
		} catch (Exception e) {
			logger.error("[错误]StudentServiceImpl的deleteStudentById方法报错,原因 : " + e.getMessage());
			e.printStackTrace();
		}
		return count;
	}

	public int updateStudentById(Student student) {
		int count = 0;
		try {
			count = studentDao.updateStudentById(student);
		} catch (Exception e) {
			logger.error("[错误]StudentServiceImpl的updateStudentById方法报错,原因 : " + e.getMessage());
			e.printStackTrace();
		}
		return count;
	}

	public Student findStudentById(long id) {
		Student student = null;
		try {
			student = studentDao.findStudentById(id);
		} catch (Exception e) {
			logger.error("[错误]StudentServiceImpl的findStudentById方法报错,原因 : " + e.getMessage());
			e.printStackTrace();
		}
		return student;
	}

	public List<Student> findStudentByName(String name) {
		List<Student>list = null;
		try {
			list = studentDao.findStudentByName(name);
		} catch (Exception e) {
			logger.error("[错误]StudentServiceImpl的findStudentByName方法报错,原因 : " + e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	public List<Student> findStudentByPageIndexAndPageSize(long pageIndex, long pageSize) {
		List<Student>list = null;
		try {
			list = studentDao.findStudentByPageIndexAndPageSize(pageIndex, pageSize);
		} catch (Exception e) {
			logger.error("[错误]StudentServiceImpl的findStudentByPageIndexAndPageSize方法报错,原因 : " + e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	public List<Student> findAllStudent() {
		List<Student>list = null;
		try {
			list = studentDao.findAllStudent();
		} catch (Exception e) {
			logger.error("[错误]StudentServiceImpl的findAllStudent方法报错,原因 : " + e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	
	public Map<Object, Object>show(long pageIndex,long pageSize){
		Map<Object, Object>map = new HashMap<Object, Object>();
		
		int total=0;
		List<Student>rows = new ArrayList<>();
		try {
			total=studentDao.findAllStudent().size();
			rows = studentDao.findStudentByPageIndexAndPageSize(pageIndex, pageSize);
			map.put("total", total);
			map.put("rows", rows);
		} catch (Exception e) {
			logger.error("[错误]StudentServiceImpl的show方法报错,原因 : " + e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public String search(String studentName) {
		Map<Object, Object>map = new HashMap<Object, Object>();
		
		int total=0;
		List<Student> rows=null;
		try {
			total = studentDao.findStudentByName(studentName).size();
			rows = studentDao.findStudentByName(studentName);
		} catch (Exception e) {
			logger.error("[错误]StudentServiceImpl的search方法报错,原因 : " + e.getMessage());
			e.printStackTrace();
		}
		
		map.put("total", total);
		map.put("rows", rows);
		
		JSONObject mapObject = JSONObject.fromObject(map);
		
		return mapObject.toString();
	}

}

```

## 3.10 编写controller 和 前端页面

### 3.10.1 引入springMVC

#### 3.10.1.1 导入相关jar包

> <!-- springMVC -->
> 	<dependency>
> 	    <groupId>org.springframework</groupId>
> 	    <artifactId>spring-webmvc</artifactId>
> 	    <version>4.1.6.RELEASE</version>
> 	</dependency>

#### 3.10.1.2 编写springmvc.xml 文件

> <?xml version="1.0" encoding="UTF-8"?>
> <beans 
> 	xmlns="http://www.springframework.org/schema/beans"
> 	xmlns:context="http://www.springframework.org/schema/context"
>  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
> 	xmlns:mvc="http://www.springframework.org/schema/mvc"
>  xsi:schemaLocation="
>  	http://www.springframework.org/schema/beans
>      http://www.springframework.org/schema/beans/spring-beans.xsd
>      http://www.springframework.org/schema/context
>      http://www.springframework.org/schema/context/spring-context.xsd
>      http://www.springframework.org/schema/mvc
>      http://www.springframework.org/schema/mvc/spring-mvc.xsd
>  ">
>
> <!-- 加载bean的包扫描器 -->
> <context:component-scan base-package="com.mrgeek.controller" />
> <!-- 开启springMVC的注解功能 -->
> <mvc:annotation-driven />
>
> <!-- 配置视图解析器 -->
> <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
> 	<property name="prefix" value="/WEB-INF/views/"></property>
> 	<property name="suffix" value=".jsp"></property>
> </bean>
>
> <!-- 设定项目的静态资源位置 防止静态资源也被拦截 -->
> <mvc:resources location="/easyui/" mapping="/easyui/**" /> 
> <mvc:resources location="/WEB-INF/image/" mapping="/image/**" /> 
> <!-- <mvc:resources location="/WEB-INF/js/" mapping="/js/**" />
> <mvc:resources location="/WEB-INF/fonts/" mapping="/fonts/**" /> -->
>
> </beans>

#### 3.10.1.3 在web.xml中配置

> <!-- servlet 前端控制器 -->
> 	<servlet>
> 		<servlet-name>dispatcherServlet</servlet-name>
> 		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
> 		<init-param>
> 			<param-name>contextConfigLocation</param-name>
> 			<param-value>classpath:springmvc/springmvc.xml</param-value>
> 		</init-param>
> 		<load-on-startup>1</load-on-startup>
> 	</servlet>
> 	<servlet-mapping>
> 		<servlet-name>dispatcherServlet</servlet-name>
> 		<url-pattern>/</url-pattern>
> 	</servlet-mapping>

### 3.10.2 在web.xml 中配置过滤器，防止中文乱码

> <filter>
> 		<filter-name>CharacterEncodingFilter</filter-name>
> 		<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
>
> ​	<init-param>
> ​		<param-name>encoding</param-name>
> ​		<param-value>UTF-8</param-value>
> ​	</init-param>
> ​	<init-param>
> ​		<param-name>forceEncoding</param-name>
> ​		<param-value>true</param-value>
> ​	</init-param>
> </filter>
>
> <filter-mapping>
> 	<filter-name>CharacterEncodingFilter</filter-name>
> 	<url-pattern>/*</url-pattern>
> </filter-mapping>

### 3.10.3 构建前端页面

#### 3.10.3.1 导入jar包

> <!-- JSP相关 -->
> 	<dependency>
> 		<groupId>jstl</groupId>
> 		<artifactId>jstl</artifactId>
> 		<version>1.2</version>
> 	</dependency>
> 	<dependency>
> 		<groupId>javax.servlet</groupId>
> 		<artifactId>servlet-api</artifactId>
> 		<version>2.5</version>
> 		<scope>provided</scope>
> 	</dependency>
> 	<dependency>
> 		<groupId>javax.servlet</groupId>
> 		<artifactId>jsp-api</artifactId>
> 		<version>2.0</version>
> 		<scope>provided</scope>
> 	</dependency>

#### 3.10.3.2编写jsp文件

##### 1.系统对外系统访问权限的默认界面

+ index.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	response.sendRedirect("system/index");
%>
```

##### 2. 真正的系统主页面

+ index.jsp

``` jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>人员信息管理系统</title>

	<link rel="stylesheet" type="text/css" href="/SIMS/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/SIMS/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="/SIMS/easyui/demo/demo.css">
	<script type="text/javascript" src="/SIMS/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="/SIMS/easyui/jquery.easyui.min.js"></script>
	
	<style type="text/css">
		 #DIV
	    {
	        position:absolute;right:8px;top:12px;        
	        font-size:12px;
	        line-height:105px;
	    }
		 #DIV a
	    {
	        text-decoration:none;        
	        font-weight:normal;
	        font-size:12px;
	        line-height:25px;
	        margin-left:3px;
	        margin-right:3px;
	        color:#FFFFFF;        
	    }
	    #DIV a:hover
	    {
	        text-decoration:underline;        
	    }
	    #center 
	    {
	    	text-align:center;
	    }
	</style>
	
	<script type="text/javascript">
		function changeStudentInfoPage(){
			$('#tabs').tabs('add',{
		        title:'学生信息展示',
		        content:'<iframe src="../jsp/studentInfo.jsp" width="100%" height="100%" style="border: 0px;"></iframe>',
		        closable:true
		    });
		}
	</script>
	
</head>
<body class="easyui-layout">
	
	<!-- north -->
    <div data-options="region:'north',split:true" style="height:100px;background:url(/SIMS/image/logo.png) no-repeat 0% 55% ;background-color:#707070" )>
	   		<div id="DIV" >
	   			<a href="#">首页 |</a> 
		   		<a href="#">待定 |</a>
		   		<a href="#">网站介绍 |</a>
		   		<a href="https://mrgeek-zrh.gitee.io/">MrGeek的个人博客 |</a>
		   		<a href="index.jsp">退出系统 </a>
	   		</div>
	</div>
	
	<!--south部分 -->
   <div id="center" data-options="region:'south',split:true" style="height:35px;">
		&copy; 学生信息管理系统-MrGeek
   </div>
    
    <!-- west -->
    <div data-options="region:'west',title:'系统功能栏',split:true" style="width:250px;">
   	   <ul id="tt" class="easyui-tree">
	        <li>
	    		<span>人员信息管理</span>
	    		<ul>
	    			<li>
	    				<span>学生信息管理</span>
	    				<ul>
							<li>
								<span>
									<a href="#" onclick="changeStudentInfoPage()">学生信息展示</a>
								</span>
							</li>
	    					<li>
	    						<span>File 12</span>
	    					</li>
	    					<li>
	    						<span>File 13</span>
	    					</li>
	    				</ul>
	    			</li>
	    			<li><span>File 2</span></li>
	    			<li><span>File 3</span></li>
	    		</ul>
	    	</li>
     	</ul>
    </div>
    
    <!-- center部分 -->
	   <div data-options="region:'center',title:'详细信息展示栏'" style="padding:5px;background:#eee;">
	   
	   		<div id="tabs" class="easyui-tabs" data-options="fit:true" style="width:500px;height:250px;">
		   		<!-- 欢迎页面 -->
		        <div id="welcome" title="首页" data-options="href:'../jsp/welcome.jsp',closable:false" style="padding:20px;display:none;"></div>
    		</div>
	   </div>
    
</body>
</html>
```

##### 3. 系统欢迎页面

+ welcome.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>欢迎页面</title>
</head>
<body>
<div title="欢迎使用" style="padding:20px;overflow:hidden; color:red; " >
	<p style="font-size: 50px; line-height: 10px; height: 5px;">${admin.username}</p>
	<p style="font-size: 25px; line-height: 10px; height: 10px;">欢迎使用ssm框架管理系统</p>
  	<p>开发人员：【MrGeek】</p>
  	<p>开发周期：2020/09/13 ---2020/09/14 </p>
  	
  	<hr />
  	<h2>项目运行环境</h2>
  	<p>开发环境：linux minit</p>
	<p>开发工具：Eclipse 2020 marton</p>
	<p>Java版本：JDK 1.8</p>
	<p>服务器：tomcat 8.5</p>
	<p>数据库：MySQL 5.5</p>
	<p>系统采用技术： JSP+MySQL+JDBC+spring+springMVC+EasyUI+jQuery</p>
</div>
</body>
</html>
```

##### 4. 人员信息展示页面

+ studentInfo.jsp

``` jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

	<title>人员信息展示列表</title>
	
	<link rel="stylesheet" type="text/css" href="/SIMS/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/SIMS/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="/SIMS/easyui/demo/demo.css">
	<script type="text/javascript" src="/SIMS/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="/SIMS/easyui/jquery.easyui.min.js"></script>	

</head>
<body>
    <!-- 工具栏 -->
	<div id="tb">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addRow()">添加</a>
		
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteRow()">删除</a>
		
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateRow()">修改</a>
		
		<a href="javaScript:void(0)" class="easyui-linkbutton" onclick="saveUpdate()" data-options="iconCls:'icon-save',plain:true">保存</a>
		
		<!-- 搜索栏 -->
		<input id="ss" class="easyui-searchbox" style="width:300px" data-options="searcher:qq,prompt:'请输入你想查询的学生的姓名',menu:'#mm'"></input>
		<div id="mm" style="width:120px">
		    <div data-options="name:'all',iconCls:'icon-ok'"></div>
		    <div data-options="name:'sports'"></div>
		</div>
	</div>
	
	<!-- 表格 -->
    <table id="dg"></table>
    
    <!-- 窗体 -->
    <div id="win" class="easyui-window" title="添加学生信息" style="width:600px;height:470px;"
        data-options="iconCls:'icon-save',modal:true">
		<div  style="width:100%; border: 0px red solid; " >
			<div style="padding:35px 1px 26px 161px">
			    <form id="ff" action="/SIMS/StudentInfo_CURD/add" method="post" >
			    	<table cellpadding="3">
			    		<tr>
			    			<td>姓名:</td>
			    			<td><input class="easyui-textbox" type="text" name="name" 
			    			data-options="required:true"></input></td>
			    		</tr>
			    		<tr>
			    			<td>性别:</td>
			    			<td><input class="easyui-textbox" type="text" name="sex" 
			    			data-options="required:true"></input></td>
			    		</tr>
			    		<tr>
			    			<td>年龄:</td>
			    			<td><input class="easyui-textbox" type="text" name="year" ></input></td>
			    		</tr>
			    	</table>
				</form>
			    <div style="text-align:center;padding:17px 155px 19px 32px">
			    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">添加</a>
			    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">取消</a>
			    </div>
			</div>
		</div>
			
    </div>
	
	<!-- start -->
	<script type="text/javascript">
		$(function (){
			$('#dg').datagrid({
		        url:'../student/show',
		        fit:true,
		        striped:true,
		        singleSelect:true,
		        checkOnSelect:true,
		        pagination:true,
		        pageNumber:1,
				pageSize:10,
		        columns:[[
		        	{checkbox:true,field:''},
		    		{field:'id',title:'编号',width:100},
		    		{field:'name',title:'姓名',width:100},
		    		{field:'sex',title:'性别',width:100,},
		    		{field:'year',title:'年龄',width:100,editor : 'textbox'},
		        ]],
	        	toolbar: '#tb'
		    });	
			
			//页面加载完成，关闭用于CURD的窗体
			$("#win").window("close");
		});
	</script>	
<!-- 	end -->

<!-- 	搜索功能 -->
<!-- 	start -->
	<script type="text/javascript">
		function qq(studentName){
			$('#dg').datagrid({
		        url:'StudentInfo_CURD?task=search&studentName='+studentName,
		        fit:true,
		        striped:true,
		        singleSelect:true,
		        checkOnSelect:true,
		        pagination:true,
		        pageNumber:1,
				pageSize:10,
		        columns:[[
		        	{checkbox:true,field:''},
		    		{field:'id',title:'编号',width:100},
		    		{field:'name',title:'姓名',width:100},
		    		{field:'num',title:'学号',width:100,align:'right'},
		    		{field:'password',title:'密码',width:100,editor : 'textbox'},
		    		{field:'score',title:'学分',width:100,editor : 'textbox'},
		    		{field:'courseID',title:'所选课程id',width:100,editor : 'textbox'},
		    		{field:'classID',title:'所在班级id',width:100,editor : 'textbox'},
		        ]],
	        	toolbar: '#tb'
		    });	
		
		//页面加载完成，关闭用于CURD的窗体
		$("#win").window("close");
	}
	</script>
	
	<!-- 	添加功能 -->
<!-- start -->
	<script type="text/javascript">
		function addRow(){
			$('#win').window('open');
		}
	</script>
	<script type="text/javascript">
		function submitForm(){
			$.messager.confirm('提示信息', '确认添加？',function(r){
				if(r){
					$('#ff').form('submit', {
				        success: function(msg){
				    		if(msg=='error1'){
				    			$.messager.alert('提示信息', '添加用户失败!,该用户已存在');
				    		}else if(msg=='error2'){
				    			$.messager.alert('提示信息', '添加用户失败!,亲联系管理员');
				    		}else{
				    			$.messager.alert('提示信息', '添加用户成功!');
				    			$("#dg").datagrid("reload");
								// 关闭窗口
								$("#win").window("close");
				    		}
				        }
				    });
				}
			});
		}
		
		function clearForm(){
			$.messager.confirm('提示信息', '确认取消添加？',function(r){
				if(r){
					// 关闭窗口
					$("#win").window("close");
				}
			});
			
		}
	</script>
<!-- 	end -->

<!-- 删除功能 -->
<!-- start -->
	<script type="text/javascript">
		function deleteRow(){
			$.messager.confirm('提示信息', '确认要删除学生信息吗？',function(r){
				if(r){
					//获取被选中的行
					var row = $("#dg").datagrid("getSelected");
					if(row==null){
						$.messager.alert('提示信息', '请先选择想要删除的数据!');
					}else{
						//获取该行的编号
						var index = row.id;
						alert(index);
						//将编号传递给StudentInfo_CURD，进行删除处理
						$.post("/SIMS/StudentInfo_CURD/delete", {
							"index" : index
							}, function(msg) {
								if(msg=='success'){
									// 更新数据
									$.messager.alert('提示信息', '删除成功!');
									$("#dg").datagrid("reload");
								}
								else{
									$.messager.alert('提示信息', '添加用户失败!');
								}
							}
						)
					}
				}
			});
		}
	</script>
<!-- end -->


<!-- 修改功能 -->
<!-- start -->
	<script type="text/javascript">
		var index=-1;
		function updateRow(){
			// 获取被勾选的行
			var row = $("#dg").datagrid("getSelected");
			if (row != null) {
				// 先关闭编辑状态
				$("#dg").datagrid("endEdit", index);
				// 通过选中的行获取对应的行号
				index = $("#dg").datagrid("getRowIndex", row);
				// 让某行进入编辑状态
				$("#dg").datagrid("beginEdit", index);
			} else {
				$.messager.alert('修改失败', '请勾选要修改的行号!');
			}
		}
	</script>
<!-- end -->


<!-- 保存功能 和修改相关联-->
	<script type="text/javascript">
		function saveUpdate() {
			var row = $("#dg").datagrid("getSelected");
			var index = $("#dg").datagrid("getRowIndex", row);
			if (row != null) {
				// 关闭编辑状态
				$("#dg").datagrid("endEdit", index);
				// 提交更新数据
				$.post("/SIMS/StudentInfo_CURD/update", {
					"id" : row.id,
					"name" : row.name,
					"sex" : row.sex,
					"year" : row.year
				}, function(msg) {
					if(msg=='success'){
						// 更新数据
						$.messager.alert('提示信息', '保存成功!');
						$("#dg").datagrid("reload");
					}
					else{
						$.messager.alert('提示信息', '保存失败!');
					}
				});
			} else {
				$.messager.alert('保存失败', '请先修改数据!');
			}
		}
	</script>
	
	
	<!-- 	搜索功能 -->
<!-- 	start -->
	<script type="text/javascript">
		function qq(studentName){
			$('#dg').datagrid({
		        url:'/SIMS/StudentInfo_CURD/search/'+studentName,
		        fit:true,
		        striped:true,
		        singleSelect:true,
		        checkOnSelect:true,
		        pagination:true,
		        pageNumber:1,
				pageSize:10,
		        columns:[[
		        	{checkbox:true,field:''},
		    		{field:'id',title:'编号',width:100},
		    		{field:'name',title:'姓名',width:100},
		    		{field:'sex',title:'性别',width:100},
		    		{field:'year',title:'年龄',width:100,editor : 'textbox'},
		        ]],
	        	toolbar: '#tb'
		    });	
		
		//页面加载完成，关闭用于CURD的窗体
		$("#win").window("close");
	}
	</script>
<!-- 	end -->
	
</body>
</html>
```

### 3.10 .4 在web.xml 中配置studentInfo.jsp 和 welcome.jsp 文件的映射路径

``` xml
<!-- 配置jsp文件的请求映射路径 -->
	<!-- 欢迎页面映射路径 -->
	<servlet>  
	    <servlet-name>mapping1</servlet-name>  
	    <jsp-file>/WEB-INF/views/welcome.jsp</jsp-file>
	</servlet>  
	<servlet-mapping>  
	    <servlet-name>mapping1</servlet-name>  
	    <url-pattern>/jsp/welcome.jsp</url-pattern>  
	</servlet-mapping>
	<!-- studentInfo.jsp页面映射路径 -->
	<servlet>  
	    <servlet-name>mapping2</servlet-name>  
	    <jsp-file>/WEB-INF/views/studentInfo.jsp</jsp-file>
	</servlet>  
	<servlet-mapping>  
	    <servlet-name>mapping2</servlet-name>  
	    <url-pattern>/jsp/studentInfo.jsp</url-pattern>  
	</servlet-mapping>  
```

### 3.10.5 编写对应的controller层代码

+ Jump.java

``` java
package com.mrgeek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用于跳转页面的类
* <p>Title: Jump.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-13_21:45:49
* @version 1.0
 */
@Controller
@RequestMapping("/system")
public class Jump {

	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
}
```

+ StudentController.java

``` java
package com.mrgeek.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrgeek.dao.StudentDao;
import com.mrgeek.service.StudentService;

import net.sf.json.JSONObject;

/**
 * 人员信息管理类
* <p>Title: StudentController.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-14_15:14:28
* @version 1.0
 */
@Controller
@RequestMapping(value = "/student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;

	@RequestMapping(value = "/show",method = RequestMethod.POST,produces = "text/plain;charset=utf-8")
	@ResponseBody
	//变量名必须为rows 和 page，才能自动获取到页面传来的变量的值 
	// rows 对应pageSize
	// page 对应pageIndex
	public String show (@RequestParam(defaultValue = "1") long page,@RequestParam(defaultValue = "15") long rows){
		
		Map<Object, Object>map = studentService.show(page, rows);
		JSONObject mapObject = JSONObject.fromObject(map);
		
		return mapObject.toString();
	}
	
}
```

+ StudentInfo_CURD.java

``` java
package com.mrgeek.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrgeek.entity.Student;
import com.mrgeek.service.StudentService;

import net.sf.json.JSONObject;

/**
 * 对人员信息进行CURD操作
* <p>Title: StudentInfo_CURD.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-15_00:02:16
* @version 1.0
 */
@Controller
@RequestMapping("/StudentInfo_CURD")
public class StudentInfo_CURD {
	
	@Autowired
	private StudentService studentService;
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public String add(Student student) {
		int count = studentService.addStudent(student);
		if (count == -1) {
			return "error1";
		}else if (count == 0) {
			return "error2";
		}{
			return "success";
		}
	}

	
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public String delete(long index) {
		Student student = new Student();
		student.setId(index);
		int count = studentService.deleteStudentById(student);
		return count>0 ? "success" : "error";
	}
	
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	@ResponseBody
	public String update(Student student) {
		int count = studentService.updateStudentById(student);
		if (count>0) {
			return "success";
		}else {
			return "error";
		}
	}
	
	@RequestMapping(value = "/search/{studentName}",method = RequestMethod.POST,produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String search(@PathVariable("studentName") String studentName) {
		return studentService.search(studentName);
	}
	
}

```

## 大功告成！！！

### 附上pom.xml 的完整代码

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.mrgeek</groupId>
  <artifactId>SIMS</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>war</packaging>
  
  <build>
  	<plugins>
  		<plugin>
  			<groupId>org.apache.maven.plugins</groupId>
  			<artifactId>maven-compiler-plugin</artifactId>
  			<configuration>
  				<source>1.8</source>
  				<target>1.8</target>
  				<encoding>UTF-8</encoding>
  			</configuration>
  		</plugin>
  		  <plugin>
	          <groupId>org.apache.tomcat.maven</groupId>
	          <artifactId>tomcat8-maven-plugin</artifactId>
	          <version>3.0-r1655215</version>
        </plugin>
  	</plugins>
  </build>
   <!--配置Tomcat8仓库-->
 <pluginRepositories>
     <pluginRepository>
         <id>alfresco-public</id>
         <url>https://artifacts.alfresco.com/nexus/content/groups/public</url>
     </pluginRepository>
     <pluginRepository>
         <id>alfresco-public-snapshots</id>
         <url>https://artifacts.alfresco.com/nexus/content/groups/public-snapshots</url>
         <snapshots>
             <enabled>true</enabled>
             <updatePolicy>daily</updatePolicy>
         </snapshots>
     </pluginRepository>
 </pluginRepositories>

  
  
  <dependencies>
  
  <!-- mysql相关 -->
	<dependency>
	    <groupId>mysql</groupId>
	    <artifactId>mysql-connector-java</artifactId>
	    <version>5.1.47</version>
	</dependency>
	<dependency>
	    <groupId>commons-dbutils</groupId>
	    <artifactId>commons-dbutils</artifactId>
	    <version>1.6</version>
	</dependency>
	
	<!-- junit测试相关 -->
	<dependency>
	    <groupId>junit</groupId>
	    <artifactId>junit</artifactId>
	    <version>4.12</version>
	    <scope>test</scope>
	</dependency>
	<dependency>
	    <groupId>org.hamcrest</groupId>
	    <artifactId>hamcrest-core</artifactId>
	    <version>1.3</version>
	    <scope>test</scope>
	</dependency>
		
	<!-- spring相关jar包 -->
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-beans</artifactId>
	    <version>4.1.6.RELEASE</version>
	</dependency>
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-context</artifactId>
	    <version>4.1.6.RELEASE</version>
	</dependency>
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-core</artifactId>
	    <version>4.1.6.RELEASE</version>
	</dependency>
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-expression</artifactId>
	    <version>4.1.6.RELEASE</version>
	</dependency>
	<dependency>
	    <groupId>commons-logging</groupId>
	    <artifactId>commons-logging</artifactId>
	    <version>1.1.3</version>
	</dependency>
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-web</artifactId>
	    <version>4.1.6.RELEASE</version>
	</dependency>
	
	<!-- spring 测试所需jar 包 -->					
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-test</artifactId>
	    <version>4.1.6.RELEASE</version>
	    <scope>test</scope>
	</dependency>
		
	<!-- log4j相关jar包 -->
	<dependency>
	    <groupId>log4j</groupId>
	    <artifactId>log4j</artifactId>
	    <version>1.2.17</version>
	</dependency>
	
	<!-- springMVC -->
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-webmvc</artifactId>
	    <version>4.1.6.RELEASE</version>
	</dependency>
	
	<!-- JSP相关 -->
	<dependency>
		<groupId>jstl</groupId>
		<artifactId>jstl</artifactId>
		<version>1.2</version>
	</dependency>
	<dependency>
		<groupId>javax.servlet</groupId>
		<artifactId>servlet-api</artifactId>
		<version>2.5</version>
		<scope>provided</scope>
	</dependency>
	<dependency>
		<groupId>javax.servlet</groupId>
		<artifactId>jsp-api</artifactId>
		<version>2.0</version>
		<scope>provided</scope>
	</dependency>
	
	<!-- json 与字符串互转所需jar包 -->
	<dependency>
	    <groupId>commons-beanutils</groupId>
	    <artifactId>commons-beanutils</artifactId>
	    <version>1.8.0</version>
	</dependency>
	<dependency>
	    <groupId>commons-collections</groupId>
	    <artifactId>commons-collections</artifactId>
	    <version>3.2.1</version>
	</dependency>
	<dependency>
	    <groupId>commons-lang</groupId>
	    <artifactId>commons-lang</artifactId>
	    <version>2.5</version>
	</dependency>
	<dependency>
	    <groupId>net.sf.ezmorph</groupId>
	    <artifactId>ezmorph</artifactId>
	    <version>1.0.6</version>
	</dependency>
	<dependency>
	    <groupId>net.sf.json-lib</groupId>
	    <artifactId>json-lib</artifactId>
	    <version>2.4</version>
	</dependency> 	
  </dependencies>
</project>
```

