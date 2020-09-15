<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

	<title>人员信息展示列表</title>
	
	<link rel="stylesheet" type="text/css" href="/SIMS/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/SIMS/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="/SIMS/easyui/demo/demo.css">
	<script type="text/javascript" src="/SIMS/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="/SIMS/easyui/jquery.easyui.min.js"></script>	

</head>
<body>
    <!-- 工具栏 -->
	<div id="tb">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addRow()">添加</a>
		
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteRow()">删除</a>
		
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateRow()">修改</a>
		
		<a href="javaScript:void(0)" class="easyui-linkbutton" onclick="saveUpdate()" data-options="iconCls:'icon-save',plain:true">保存</a>
		
		<!-- 搜索栏 -->
		<input id="ss" class="easyui-searchbox" style="width:300px" data-options="searcher:qq,prompt:'请输入你想查询的学生的姓名',menu:'#mm'"></input>
		<div id="mm" style="width:120px">
		    <div data-options="name:'all',iconCls:'icon-ok'"></div>
		    <div data-options="name:'sports'"></div>
		</div>
	</div>
	
	<!-- 表格 -->
    <table id="dg"></table>
    
    <!-- 窗体 -->
    <div id="win" class="easyui-window" title="添加学生信息" style="width:600px;height:470px;"
        data-options="iconCls:'icon-save',modal:true">
		<div  style="width:100%; border: 0px red solid; " >
			<div style="padding:35px 1px 26px 161px">
			    <form id="ff" action="/SIMS/StudentInfo_CURD/add" method="post" >
			    	<table cellpadding="3">
			    		<tr>
			    			<td>姓名:</td>
			    			<td><input class="easyui-textbox" type="text" name="name" 
			    			data-options="required:true"></input></td>
			    		</tr>
			    		<tr>
			    			<td>性别:</td>
			    			<td><input class="easyui-textbox" type="text" name="sex" 
			    			data-options="required:true"></input></td>
			    		</tr>
			    		<tr>
			    			<td>年龄:</td>
			    			<td><input class="easyui-textbox" type="text" name="year" ></input></td>
			    		</tr>
			    	</table>
				</form>
			    <div style="text-align:center;padding:17px 155px 19px 32px">
			    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">添加</a>
			    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">取消</a>
			    </div>
			</div>
		</div>
			
    </div>
	
	<!-- start -->
	<script type="text/javascript">
		$(function (){
			$('#dg').datagrid({
		        url:'../student/show',
		        fit:true,
		        striped:true,
		        singleSelect:true,
		        checkOnSelect:true,
		        pagination:true,
		        pageNumber:1,
				pageSize:10,
		        columns:[[
		        	{checkbox:true,field:''},
		    		{field:'id',title:'编号',width:100},
		    		{field:'name',title:'姓名',width:100},
		    		{field:'sex',title:'性别',width:100,},
		    		{field:'year',title:'年龄',width:100,editor : 'textbox'},
		        ]],
	        	toolbar: '#tb'
		    });	
			
			//页面加载完成，关闭用于CURD的窗体
			$("#win").window("close");
		});
	</script>	
<!-- 	end -->

<!-- 	搜索功能 -->
<!-- 	start -->
	<script type="text/javascript">
		function qq(studentName){
			$('#dg').datagrid({
		        url:'StudentInfo_CURD?task=search&studentName='+studentName,
		        fit:true,
		        striped:true,
		        singleSelect:true,
		        checkOnSelect:true,
		        pagination:true,
		        pageNumber:1,
				pageSize:10,
		        columns:[[
		        	{checkbox:true,field:''},
		    		{field:'id',title:'编号',width:100},
		    		{field:'name',title:'姓名',width:100},
		    		{field:'num',title:'学号',width:100,align:'right'},
		    		{field:'password',title:'密码',width:100,editor : 'textbox'},
		    		{field:'score',title:'学分',width:100,editor : 'textbox'},
		    		{field:'courseID',title:'所选课程id',width:100,editor : 'textbox'},
		    		{field:'classID',title:'所在班级id',width:100,editor : 'textbox'},
		        ]],
	        	toolbar: '#tb'
		    });	
		
		//页面加载完成，关闭用于CURD的窗体
		$("#win").window("close");
	}
	</script>
	
	<!-- 	添加功能 -->
<!-- start -->
	<script type="text/javascript">
		function addRow(){
			$('#win').window('open');
		}
	</script>
	<script type="text/javascript">
		function submitForm(){
			$.messager.confirm('提示信息', '确认添加？',function(r){
				if(r){
					$('#ff').form('submit', {
				        success: function(msg){
				    		if(msg=='error1'){
				    			$.messager.alert('提示信息', '添加用户失败!,该用户已存在');
				    		}else if(msg=='error2'){
				    			$.messager.alert('提示信息', '添加用户失败!,亲联系管理员');
				    		}else{
				    			$.messager.alert('提示信息', '添加用户成功!');
				    			$("#dg").datagrid("reload");
								// 关闭窗口
								$("#win").window("close");
				    		}
				        }
				    });
				}
			});
		}
		
		function clearForm(){
			$.messager.confirm('提示信息', '确认取消添加？',function(r){
				if(r){
					// 关闭窗口
					$("#win").window("close");
				}
			});
			
		}
	</script>
<!-- 	end -->

<!-- 删除功能 -->
<!-- start -->
	<script type="text/javascript">
		function deleteRow(){
			$.messager.confirm('提示信息', '确认要删除学生信息吗？',function(r){
				if(r){
					//获取被选中的行
					var row = $("#dg").datagrid("getSelected");
					if(row==null){
						$.messager.alert('提示信息', '请先选择想要删除的数据!');
					}else{
						//获取该行的编号
						var index = row.id;
						alert(index);
						//将编号传递给StudentInfo_CURD，进行删除处理
						$.post("/SIMS/StudentInfo_CURD/delete", {
							"index" : index
							}, function(msg) {
								if(msg=='success'){
									// 更新数据
									$.messager.alert('提示信息', '删除成功!');
									$("#dg").datagrid("reload");
								}
								else{
									$.messager.alert('提示信息', '添加用户失败!');
								}
							}
						)
					}
				}
			});
		}
	</script>
<!-- end -->


<!-- 修改功能 -->
<!-- start -->
	<script type="text/javascript">
		var index=-1;
		function updateRow(){
			// 获取被勾选的行
			var row = $("#dg").datagrid("getSelected");
			if (row != null) {
				// 先关闭编辑状态
				$("#dg").datagrid("endEdit", index);
				// 通过选中的行获取对应的行号
				index = $("#dg").datagrid("getRowIndex", row);
				// 让某行进入编辑状态
				$("#dg").datagrid("beginEdit", index);
			} else {
				$.messager.alert('修改失败', '请勾选要修改的行号!');
			}
		}
	</script>
<!-- end -->


<!-- 保存功能 和修改相关联-->
	<script type="text/javascript">
		function saveUpdate() {
			var row = $("#dg").datagrid("getSelected");
			var index = $("#dg").datagrid("getRowIndex", row);
			if (row != null) {
				// 关闭编辑状态
				$("#dg").datagrid("endEdit", index);
				// 提交更新数据
				$.post("/SIMS/StudentInfo_CURD/update", {
					"id" : row.id,
					"name" : row.name,
					"sex" : row.sex,
					"year" : row.year
				}, function(msg) {
					if(msg=='success'){
						// 更新数据
						$.messager.alert('提示信息', '保存成功!');
						$("#dg").datagrid("reload");
					}
					else{
						$.messager.alert('提示信息', '保存失败!');
					}
				});
			} else {
				$.messager.alert('保存失败', '请先修改数据!');
			}
		}
	</script>
	
	
	<!-- 	搜索功能 -->
<!-- 	start -->
	<script type="text/javascript">
		function qq(studentName){
			$('#dg').datagrid({
		        url:'/SIMS/StudentInfo_CURD/search/'+studentName,
		        fit:true,
		        striped:true,
		        singleSelect:true,
		        checkOnSelect:true,
		        pagination:true,
		        pageNumber:1,
				pageSize:10,
		        columns:[[
		        	{checkbox:true,field:''},
		    		{field:'id',title:'编号',width:100},
		    		{field:'name',title:'姓名',width:100},
		    		{field:'sex',title:'性别',width:100},
		    		{field:'year',title:'年龄',width:100,editor : 'textbox'},
		        ]],
	        	toolbar: '#tb'
		    });	
		
		//页面加载完成，关闭用于CURD的窗体
		$("#win").window("close");
	}
	</script>
<!-- 	end -->
	
</body>
</html>