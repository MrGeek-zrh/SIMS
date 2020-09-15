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
