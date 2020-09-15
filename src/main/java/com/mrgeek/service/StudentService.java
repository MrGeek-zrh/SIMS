package com.mrgeek.service;

import java.util.List;
import java.util.Map;

import com.mrgeek.entity.Student;

/**
 * service层接口
* <p>Title: StudentService.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-13_16:53:58
* @version 1.0
 */
public interface StudentService {
	
	/**
	 * 向数据库中添加学生信息
	* <p>Title: addStudent<／p>
	* <p>Description: <／p>
	* @return int型，0：添加失败，1：添加成功
	* @
	 */
	public int addStudent(Student student) ;
	
	/**
	 * 根据id删除人员信息
	* <p>Title: deleteStudentById<／p>
	* <p>Description: <／p>
	* @return int型，0：添加失败，1：添加成功
	* @
	 */
	public int deleteStudentById(Student student) ;
	
	/**
	 * 根据id修改人员信息
	* <p>Title: updateStudentById<／p>
	* <p>Description: <／p>
	* @param student
	* @return int型，0：添加失败，1：添加成功
	* @
	 */
	public int updateStudentById(Student student) ;
	
	/**
	 * 根据id获取人员信息
	* <p>Title: findStudentById<／p>
	* <p>Description: <／p>
	* @param student
	* @return 指定人员信息的实体类
	* @
	 */
	public Student findStudentById(long id) ;
	
	/**
	 * 根据人员姓名获取人员信息
	* <p>Title: findStudentByName<／p>
	* <p>Description: <／p>
	* @param student
	* @return 指定人员信息的实体类
	* @
	 */
	public List<Student> findStudentByName(String name) ;
	
	/**
	 * 根据pageIndex 和 pageSize 获取指定页的数据
	* <p>Title: findStudentByPageIndexAndPageSize<／p>
	* <p>Description: <／p>
	* @return 包含有指定人员信息的List 集合
	* @
	 */
	public List<Student> findStudentByPageIndexAndPageSize(long pageIndex,long pageSize) ;
	
	/**
	 * 获取所有学生的信息
	* <p>Title: findAllStudent<／p>
	* <p>Description: <／p>
	* @return 包含有所有学生信息的List集合
	* @
	 */
	public List<Student> findAllStudent() ;
	
	/**
	 * 人员信息表格数据 处理
	* <p>Title: show<／p>
	* <p>Description: <／p>
	* @return key1:total value1:totalCount
	* 		  key2:rows  value2:list
	 */
	public Map<Object, Object>show(long pageIndex,long pageSize);
	
	public String search(String studentName);
	
}
