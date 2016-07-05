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
		        	教师选课管理
		      </h1>
		    </section>
		    <section class="content">
		    	<div class="row">
			        <div class="col-xs-12">
			          <div class="box">
			            <div class="box-header">
			              <h3 class="box-title">日志查看</h3>
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
	  <script src="${adminBase}/js/logInfo.js"></script>
  </body>
</html>
