package com.mrgeek.dao;

import java.util.List;

import com.mrgeek.entity.Student;

/**
 * 
* <p>Title: StudentDao.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-12_17:35:56
* @version 1.0
 */
public interface StudentDao{

	/**
	 * 向数据库中添加学生信息
	* <p>Title: addStudent<／p>
	* <p>Description: <／p>
	* @return int型，0：添加失败，1：添加成功
	* @throws Exception
	 */
	public int addStudent(Student student) throws Exception;
	
	/**
	 * 根据id删除人员信息
	* <p>Title: deleteStudentById<／p>
	* <p>Description: <／p>
	* @return int型，0：添加失败，1：添加成功
	* @throws Exception
	 */
	public int deleteStudentById(Student student) throws Exception;
	
	/**
	 * 根据id修改人员信息
	* <p>Title: updateStudentById<／p>
	* <p>Description: <／p>
	* @param student
	* @return int型，0：添加失败，1：添加成功
	* @throws Exception
	 */
	public int updateStudentById(Student student) throws Exception;
	
	/**
	 * 根据id获取人员信息
	* <p>Title: findStudentById<／p>
	* <p>Description: <／p>
	* @param student
	* @return 指定人员信息的实体类
	* @throws Exception
	 */
	public Student findStudentById(long id) throws Exception;
	
	/**
	 * 根据人员姓名获取人员信息
	* <p>Title: findStudentByName<／p>
	* <p>Description: <／p>
	* @param student
	* @return 指定人员信息的实体类
	* @throws Exception
	 */
	public List<Student> findStudentByName(String name) throws Exception;
	
	/**
	 * 根据pageIndex 和 pageSize 获取指定页的数据
	* <p>Title: findStudentByPageIndexAndPageSize<／p>
	* <p>Description: <／p>
	* @return 包含有指定人员信息的List 集合
	* @throws Exception
	 */
	public List<Student> findStudentByPageIndexAndPageSize(long pageIndex,long pageSize) throws Exception;
	
	/**
	 * 获取所有学生的信息
	* <p>Title: findAllStudent<／p>
	* <p>Description: <／p>
	* @return 包含有所有学生信息的List集合
	* @throws Exception
	 */
	public List<Student> findAllStudent() throws Exception;
	
}
