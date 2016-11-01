<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<title>FreeForm</title>
	<link rel="stylesheet" href="${frontBase}/lib/normalize/normalize.css">
	<link rel="stylesheet" href="${frontBase}/lib/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${frontBase}/css/common.css">
	<link rel="stylesheet" href="${frontBase}/css/person-center.css">
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
					<div class="avatar-block">
						
						<div class="avatar-inner">
							<form id="upload-form" method="POST" enctype="multipart/form-data">
								<input id="upload" name="image" type="file" accept="image/*" style="position:absolute; width: 100%; height: 100%; opacity: 0">
							</form>
							<img id="targetImg" src="${(user.headPic)!"${frontBase}/assets/img/avatar.jpg"}" style="background: url(${frontBase}/assets/img/avatar.jpg) no-repeat; background-size: 100% 100%;">
						</div>
						
					</div>
					<div class="input-block">
						<div class="input-item">
							<label for="mail">邮箱</label>
							<div class="input-inner"><input name="mail" value="${(user.mail)!"非法用户"}" type="email" disabled></div>
						</div>
						<div class="input-item">
							<label for="password">密码</label>
							<div class="input-inner"><input name="password"id="password" value="${(user.password)!"password"}" type="password"></div>
						</div>
						<div class="input-item save-item">
							<div class="btn btn-info save-btn">保存</div>
						</div>
					</div>
				</div>
			</div>
    	</section>
    	<script type="text/javascript" src="${frontBase}/lib/jquery/jquery.js"></script>
    	<script src="${frontBase}/js/upload.js"></script>
    	<script type="text/javascript">
    		$(function(){
    			var readConfig = {
					// 上传格式
					fileFormat: ['image/gif','image/png','image/jpg','image/jpeg'],
					// 最小文件大小
					minFileSize: 102400,
					// 最大文件大小
					maxFileSize: 20971520,
					// 读取显示区域
					image: $("#targetImg")[0],
					start: function(){
						console.log("开始上传");
					},
					success: function(){
						console.log("上传成功");
					},
					error: function(){
						console.log("上传失败");
					}
				};
				var uploadConfig = {
					url:"upload",
					start: function(){
						console.log("正在上传");
					},
					success: function(data, img){
						targetImg.src = data.url;
					},
					error: function(){
						
					}
				};
    		
    			var settings = {
					event: "click"
				};
				upload($("#upload")[0], readConfig, uploadConfig, settings);
				
    			//$("#upload").on("change", function(e){
    			//	$('#upload-form').ajaxSubmit({
    			//		url:"upload",
    			//		success:function(data) {
    			//			targetImg.src = data.url;
    			//		}
    			//	});
    			//});
    			$(".save-btn").on('click', function(e){
    				var obj = {};
    				obj.HeadPic = $("#targetImg").attr("src");
    				obj.password = $("#password").val();
    				if(!obj.password) {
    					alert("密码不能为空");
    					return;
    				}
    				if(!/^(?!([a-zA-Z]+|\d+)$)[a-zA-Z\d]{6,20}$/.test(obj.password)) {
    					alert("密码格式不对，必须同时包含字母和数字，且长度为6到20");
    					return;
    				}
    				$.ajax({
    					url:'user/save',
    					type:'POST',
    					data: obj,
    					success:function(data){
    						var r = data.message;
	    					if(r==="ok") {
	    						alert("保存成功");
	    					} else {
	    						alert("保存失败");
	    					}
    					},
    					error: function(){
    						alert("保存失败");
    					}
    				});
    			});
    		});
    	</script>
    	
    	
	</body>
</html>