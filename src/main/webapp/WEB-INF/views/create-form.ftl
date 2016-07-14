<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
		<title>FreeForm</title>
		<link rel="stylesheet" href="${frontBase}/lib/normalize/normalize.css">
		<link rel="stylesheet" href="${frontBase}/lib/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" href="${frontBase}/css/common.css">
		<link rel="stylesheet" href="${frontBase}/css/create-form.css">
	</head>
    <body>
    	<header class="navb">
    		<div class="logo">
    			FreeForm
    		</div>
    		<a href="center" class="avatar">
    			<img src="${frontBase}/assets/img/avatar.jpg">
    		</a>
    	</header>
    	<section class="content">
    		<div class="row">
				<div class="main-content col-md-10 col-md-offset-1 col-sm-10 col-sm-offset-1 col-xs-12">
					<div class="btn-block">
						<div class="btn btn-info create-form">创建表单</div>
					</div>
					<div class="list-block">
						<table class="table" style="text-align:center;">
							<tbody class="list-table"></tbody>
						</table>
					</div>
				</div>
			</div>
    	</section>
    	
    	<script type="text/html" id="form-item">
    		[:for(var i=0; i<list.length; i++){:]
	    		<tr id="[:=list[i].id:]">
					<td class="form-name">[:=list[i].name:]</td>
					<td>
						<a data-toggle="tooltip" href="edit?id=[:=list[i].id:]" data-placement="top" title="编辑" class="edit-item glyphicon glyphicon-edit"></a>
						<a data-toggle="tooltip" href="formdata?id=[:=list[i].id:]" data-placement="top" title="表单数据"  class="edit-item glyphicon glyphicon-signal"></a>
						<a data-toggle="tooltip" id="delete-form" data-placement="top" title="删除"  class="edit-item glyphicon glyphicon-trash"></a>
					</td>
				</tr>
    		[:}:]
    		
    	</script>
    	
    	<script type="text/javascript" src="${frontBase}/lib/jquery/jquery.min.js"></script>
    	<script type="text/javascript" src="${frontBase}/lib/bootstrap/js/bootstrap.min.js"></script>
    	<script type="text/javascript" src="${frontBase}/js/tppl.js"></script>
    	<script type="text/javascript">
    		$(function(){
    			$.get("getFormlist").done(function(data){
    				if(data.list&&data.list.length>0) {
    					var str = tppl($("#form-item").html(), data);
    					$(".list-table").html(str);
    				} else {
    					$(".list-table").html("还未开始创建表单");
    				}
    			});
    			$("[data-toggle='tooltip']").tooltip();
    			
    			$(document).on('click', '#delete-form', function(){
    				var p = $(this).parents()[1];
    				var id = $(p).attr("id");
    				$.ajax({url:"deleteForm", type:"POST", data:{id:id}})
    				.done(function(data){
    					if(data.message=="ok") {
    						p.remove();
    						alert("删除成功");
    					} else {
    						alert("删除失败");
    					}
    				}).fail(function(){
    					alert("删除失败");
    				});
    			});
    			
				$(document).on("click",".create-form", function(){
    				$.get("createForm")
	    				.done(function(data){
	    					if(data.message=="ok") {
	    						window.location="edit?="+data.id;
	    					} else {
	    						alert("生成失败");
	    					}
	    				})
	    				.fail(function(){
	    					alert("生成失败");
	    				});
    			});    			
    		});
    	</script>
    </body>
</html>