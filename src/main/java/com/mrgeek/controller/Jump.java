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
