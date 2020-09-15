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
