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
