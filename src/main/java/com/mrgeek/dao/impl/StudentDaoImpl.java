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
