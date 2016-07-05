<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>教师选课系统 | 登录</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.5 -->
  <link rel="stylesheet" type="text/css" href="${adminBase}/bootstrap/css/bootstrap.min.css"/>
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="${adminBase}/AdminLTE2/css/AdminLTE.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <a><b>JEFF</b>Admin</a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg">教师管理系统欢迎你</p>

    <form id="login-form">
      <div class="form-group has-feedback">
        <input type="text" name="name" class="form-control" placeholder="账户">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" name="password" class="form-control" placeholder="密码">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        
        <!-- /.col -->
        <div class="col-xs-4">
          <button id="login-btn" type="button" class="btn btn-primary btn-block btn-flat">登录</button>
        </div>
        <!-- /.col -->
      </div>
    </form>

  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 2.1.4 -->
<script src="${adminBase}/js/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<!-- Bootstrap 3.3.5 -->
<script src="${adminBase}/bootstrap/js/bootstrap.min.js"></script>

<script type="text/javascript">

$(function(){
	
	$("#login-btn").on("click",function(){
		var name = $("#login-form input[name='name']").val();
		var password = $("#login-form input[name='password']").val();
		if(!name){
			alert("账户不能为空");
			return;
		}
		if(!password){
			alert("密码不能为空");
			return;
		}
		$.ajax({
			type:"POST",
			url:"login-indentify",
			data:{
				name:name,
				password:password
			},
			success:function(d){
				if(d.status!="error"){
					window.location.href="admin/teacherInfo";
				}
				else
				{
					alert(d.data);
				}
			},
			error:function(){
				alert("网络问题，请稍后重试");
			}
		});
	});
	
})

</script>

</body>
</html>
