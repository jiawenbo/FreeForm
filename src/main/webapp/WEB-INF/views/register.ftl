<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<title>FreeForm</title>
	<link rel="stylesheet" href="${frontBase}/lib/normalize/normalize.css">
	<link rel="stylesheet" href="${frontBase}/lib/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${frontBase}/css/common.css">
	<link rel="stylesheet" href="${frontBase}/css/register.css">
</head>
    <body>
    	<div class="register-container">
				<div class="panel panel-default register-panel">
					<div class="input-item">
						<label for="email">邮箱</label>
						<div class="input-inner">
							<input type="email" class="lg-input" id="lgemail" placeholder="输入邮箱">
						</div>
					</div>
					<div class="input-item">
						<label for="password">密码</label>
						<div class="input-inner">
							<input type="password" class="lg-input" id="lgpwd" placeholder="输入6-20位密码">
						</div>
					</div>
					<div class="input-item">
						<label for="password">确认密码</label>
						<div class="input-inner">
							<input type="password" class="lg-input" id="relgpwd" placeholder="输入6-20位密码">
						</div>
					</div>
					<div class="input-item">
						<button class="btn btn-info register-btn">注册</button>
					</div>
				</div>
    	</div>
    	<script type="text/javascript" src="${frontBase}/lib/jquery/jquery.js"></script>
    	<script type="text/javascript">
    		$(function(){
    			$(".register-btn").on('click', function(){
    				var obj = {
    					mail: $("#lgemail").val(),
    					password: $("#lgpwd").val(),
    					repassword: $("#relgpwd").val()
    				};

    				if(obj.password!=obj.repassword) {
    					alert("两次密码输入不一致");
    					return;
    				}

    				if(!obj.mail) {
    					alert("email不能为空");
    					return;
    				}
    				if(!/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(obj.mail)) {
    					alert("email格式错误");
    					return;
    				}
    				if(!obj.password) {
    					alert("密码不能为空");
    					return;
    				}
    				if(!/^(?!([a-zA-Z]+|\d+)$)[a-zA-Z\d]{6,20}$/.test(obj.password)) {
    					alert("密码格式错误，必须同时包含字母和数字");
    					return;
    				}
    				$.ajax({
    					url:"register/identify",
    					type:"post",
    					data:obj})
    				.done(function(data){
    					var r = data.message;
    					if(r==="ok") {
    						alert("注册成功！");
    						window.location = "createform";
    					}
    					else alert(r);
    				}).fail(function(){
    					alert("网络出错");
    				});
    			});
    		});
    	</script>
    </body>
</html>