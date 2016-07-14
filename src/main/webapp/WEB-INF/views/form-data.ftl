<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<title>FreeForm</title>
	<link rel="stylesheet" href="${frontBase}/lib/normalize/normalize.css">
	<link rel="stylesheet" href="${frontBase}/lib/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${frontBase}/css/common.css">
	<link rel="stylesheet" href="${frontBase}/css/form-data.css">
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
				<div class="main col-md-10 col-md-offset-1 col-sm-10 col-sm-offset-1 col-xs-12">
					<table id="table" class="table table-bordered table-hover">
		             	<thead>
		             		<tr>
		             		</tr>
		             	</thead>
		                <tbody>
		                </tbody>
	              </table>
				</div>
			</div>
		</section>
		<script type="text/javascript" src="${frontBase}/lib/jquery/jquery.min.js"></script>
		<script type="text/javascript">
			$(function(){
				var h = location.href;
				var qPostion = h.indexOf("?");
				h = h.substr(qPostion+1);
				var arr = h.split("&");
				var formID = arr[0].split("=")[1];
				$.ajax({
					type:"GET",
					url:"lookdata",
					data:{
						id:formID
					}
				}).done(function(data){
					if(data.message === "ok") {
						var arr = JSON.parse(data.content);
						if(data.content.length==1) {
							arr = [arr];
						}
						if(arr.length>0) {
							for(var i=0; i<arr[0].length; i++) {
								$("#table").find("thead").find("tr").append("<td>"+arr[0][i][0]+"</td>");
							}
							var tbody = $("#table").find("tbody");
							for(var i=0; i<arr.length; i++) {
								var nowArr = arr[i];
								var tr = $("<tr></tr>");
								for(var j = 0; j<nowArr.length; j++) {
									var td = $("<td></td>");
									td.text(nowArr[j][1]);
									tr.append(td);
								}
								tbody.append(tr);
							}
						}
						
					} else {
						alert("获取数据失败");
					}
				}).fail(function(){
					alert("获取数据失败");
				});
			});
		</script>
    </body>
</html>