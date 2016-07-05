<!DOCTYPE html>
<html>
   <head>
	<title>教师管理系统</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <#include "templates/css.ftl">
  </head>
  <body class="hold-transition skin-blue sidebar-mini">
	  <div class="wrapper">
	  	<#include "templates/head.ftl">
	  	<div class="content-wrapper">
	  		<section class="content-header">
		      <h1>
		        	教师信息管理
		      </h1>
		    </section>
		    <section class="content">
		    	<div class="row">
			        <div class="col-xs-12">
			          <div class="box">
			            <div class="box-header">
			              <h3 class="box-title">教师信息管理</h3>
			              <div class="pull-right">
			              		<button class="btn btn-sm bg-green" id="add">
			              			<i class="fa fa-plus"></i> 添加
			              		</button>
			              </div>
			            </div>
			            <!-- start box-header -->
			            <div class="box-body">
			              <table id="table" class="table table-bordered table-hover">
			                <thead>
			                </thead>
			                <tbody>
			                </tbody>
			              </table>
			            </div>
			            <!-- end box-body -->
			          </div>
			          <!-- end box -->
			        </div>
			        <!-- end col -->
			      </div>	
        	</section>
	  	</div>
	  </div>
	  <#include "templates/jslib.ftl">
	  <script src="${adminBase}/js/teacherInfo.js"></script>
	  <script id="updateTmpl" type="text/html">
		<form id="form">
			<div class="form-group">
	   			<label for="name">教师名称</label>
	   			<input type="text" name="name" class="form-control" id="name" value="[:=name:]" />
	   		</div>
	   		<div class="form-group">
	   			<label for="password">密码</label>
	   			<input type="text" name="password" class="form-control" id="password" value="[:=password:]" />
	   		</div>
	   		<div class="form-group">
	   			<label for="rank">职称</label>
	   			<select id="rankList">
	   				[:if(rank==0){:]
	   				<option value="0" selected="selected">教师</option>
	   				[:}else{:]
	   				<option value="0">教师</option>
	   				[:}:]
	   				[:if(rank==1){:]
	   				<option value="1" selected="selected">讲师</option>
	   				[:}else{:]
	   				<option value="1">讲师</option>
	   				[:}:]
	   				[:if(rank==2){:]
	   				<option value="2" selected="selected">副教授</option>
	   				[:}else{:]
	   				<option value="2">副教授</option>
	   				[:}:]
	   				[:if(rank==3){:]
	   				<option value="3" selected="selected">教授</option>
	   				[:}else{:]
	   				<option value="3">教授</option>
	   				[:}:]
				</select>
				<div class="form-group">
	   				<label for="department">院系信息</label>
	   				<select id="deparment" class="span2">
	   					[:for(var i=0; i<department.length; i++){:]
	   						[:if(nowDepartment===department[i].id){:]
	   						<option value="[:=department[i].id:]" selected="selected">[:=department[i].department:]--[:=department[i].major:]</option>
	   						[:}else{:]
	   						<option value="[:=department[i].id:]">[:=department[i].department:]--[:=department[i].major:]</option>
	   						[:}:]
	   					[:}:]
	   				</select>
	   			</div>
	   		</div>
	    </form>
	  </script>
  </body>
</html>
