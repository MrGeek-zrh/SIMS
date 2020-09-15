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
