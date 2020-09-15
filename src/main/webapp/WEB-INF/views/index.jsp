<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>人员信息管理系统</title>

	<link rel="stylesheet" type="text/css" href="/SIMS/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/SIMS/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="/SIMS/easyui/demo/demo.css">
	<script type="text/javascript" src="/SIMS/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="/SIMS/easyui/jquery.easyui.min.js"></script>
	
	<style type="text/css">
		 #DIV
	    {
	        position:absolute;right:8px;top:12px;        
	        font-size:12px;
	        line-height:105px;
	    }
		 #DIV a
	    {
	        text-decoration:none;        
	        font-weight:normal;
	        font-size:12px;
	        line-height:25px;
	        margin-left:3px;
	        margin-right:3px;
	        color:#FFFFFF;        
	    }
	    #DIV a:hover
	    {
	        text-decoration:underline;        
	    }
	    #center 
	    {
	    	text-align:center;
	    }
	</style>
	
	<script type="text/javascript">
		function changeStudentInfoPage(){
			$('#tabs').tabs('add',{
		        title:'学生信息展示',
		        content:'<iframe src="../jsp/studentInfo.jsp" width="100%" height="100%" style="border: 0px;"></iframe>',
		        closable:true
		    });
		}
	</script>
	
</head>
<body class="easyui-layout">
	
	<!-- north -->
    <div data-options="region:'north',split:true" style="height:100px;background:url(/SIMS/image/logo.png) no-repeat 0% 55% ;background-color:#707070" )>
	   		<div id="DIV" >
	   			<a href="#">首页 |</a> 
		   		<a href="#">待定 |</a>
		   		<a href="#">网站介绍 |</a>
		   		<a href="https://mrgeek-zrh.gitee.io/">MrGeek的个人博客 |</a>
		   		<a href="index.jsp">退出系统 </a>
	   		</div>
	</div>
	
	<!--south部分 -->
   <div id="center" data-options="region:'south',split:true" style="height:35px;">
		&copy; 学生信息管理系统-MrGeek
   </div>
    
    <!-- west -->
    <div data-options="region:'west',title:'系统功能栏',split:true" style="width:250px;">
   	   <ul id="tt" class="easyui-tree">
	        <li>
	    		<span>人员信息管理</span>
	    		<ul>
	    			<li>
	    				<span>学生信息管理</span>
	    				<ul>
							<li>
								<span>
									<a href="#" onclick="changeStudentInfoPage()">学生信息展示</a>
								</span>
							</li>
	    					<li>
	    						<span>File 12</span>
	    					</li>
	    					<li>
	    						<span>File 13</span>
	    					</li>
	    				</ul>
	    			</li>
	    			<li><span>File 2</span></li>
	    			<li><span>File 3</span></li>
	    		</ul>
	    	</li>
     	</ul>
    </div>
    
    <!-- center部分 -->
	   <div data-options="region:'center',title:'详细信息展示栏'" style="padding:5px;background:#eee;">
	   
	   		<div id="tabs" class="easyui-tabs" data-options="fit:true" style="width:500px;height:250px;">
		   		<!-- 欢迎页面 -->
		        <div id="welcome" title="首页" data-options="href:'../jsp/welcome.jsp',closable:false" style="padding:20px;display:none;"></div>
    		</div>
	   </div>
    
</body>
</html>