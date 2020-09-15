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
