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
		        	院系信息管理
		      </h1>
		    </section>
		    <section class="content">
		    	<div class="row">
			        <div class="col-xs-12">
			          <div class="box">
			            <div class="box-header">
			              <h3 class="box-title">院系信息管理</h3>
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
	  <script src="${adminBase}/js/deparmentInfo.js"></script>
	  <script id="updateTmpl" type="text/html">
		<form id="form">
			<div class="form-group">
	   			<label for="name">院系</label>
	   			<input type="text" name="department" class="form-control" id="department" value="[:=department:]" />
	   		</div>
	   		<div class="form-group">
	   			<label for="rank">专业</label>
	   			<input type="text" name="major" class="form-control" id="major" value="[:=major:]" />
	   		</div>
	    </form>
	  </script>
  </body>
</html>
