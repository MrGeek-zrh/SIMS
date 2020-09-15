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
public class Student implements Serializable{

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
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", sex=" + sex + ", year=" + year + "]";
	}
	
}
