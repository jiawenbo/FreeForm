<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<title>FreeForm</title>
	<link rel="stylesheet" href="${frontBase}/lib/normalize/normalize.css">
	<link rel="stylesheet" href="${frontBase}/lib/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${frontBase}/lib/mCustomerScrollbar/css/jquery.mCustomScrollbar.min.css">
	<link rel="stylesheet" href="${frontBase}/css/free-form.css">
	<link rel="stylesheet" href="${frontBase}/css/common.css">
</head>
    <body>
    	<div class="container">
    		<div class="row">
    				<div class="form-struct col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">
						<div class="createArea"></div>
						<div class="submitArea"><div class="btn btn-info" id="submit">提交</div></div>
					</div>
    		</div>
    	</div>
		<script type="text/javascript" src="${frontBase}/lib/jquery/jquery.js"></script>
		<script type="text/javascript" src="${frontBase}/lib/bootstrap/js/bootstrap.js"></script>
		<script type="text/javascript" src="${frontBase}/js/freeform-engine.js"></script>
		<script>
		
		$(function(){
			var h = location.href;
			var qPostion = h.indexOf("?");
			h = h.substr(qPostion+1);
			var arr = h.split("&");
			var formID = arr[0].split("=")[1];
			var formObj = null;
			var designArea = $(".createArea");
			$.ajax({
			type:"POST", 
			url:"getformcontent",
			data:{formId: formID}})
			.done(function(data){
				if(data.content) {
					formObj = JSON.parse(data.content);
				}
				new Form(formObj, "product", designArea);
			})
			.fail(function(){
				alert("加载表单数据失败，请重新获取");
			});
			
			$("#submit").on("click",function(){
				var obj = [];
				$(".form-item").each(function(){
				    var _t = $(this);
				    var inputs = _t.find("input");
				    if(!inputs) {
				    	inputs = _t.find("textarea");
				    	if(!inputs) {
				    		inputs = _t.find("select");
				    	}
				    }
					var type = inputs.attr("type");
					if(!type) {
						type = "select";
					}
					switch(type) {
						case "radio":
							var o = [];
							var name = _t.find("input").attr("name");
							var label = $(_t.children()[0]).text();
							var value = _t.find("input[name='"+name+"']:checked").next().text();
							o.push(label,value);
							obj.push(o);
							break;
						case "select":
							var o = [];
							var label =  $(_t.children()[0]).text();
							var select = $(_t.children()[1]).val();
							o.push(label,select);
							obj.push(o);
							break;
						case "checkbox":
							var o = [];
							var label = $(_t.children()[0]).text();
							var checked = [];
							_t.find("input:checked").each(function(){
								checked.push($(this).next().text());
							});
							o.push(label,checked);
							obj.push(o);
							break;
						case "textarea":
							var o = [];
							var label = $(_t.children()[0]).text();
							var value = inputs.val();
							o.push(label,value);
							obj.push(o);
							break;
						case "text":
							var o = [];
							var label = $(_t.children()[0]).text();
							var value = inputs.val();
							o.push(label,value);
							obj.push(o);
							break;
						case "password":
							var o = [];
							var label = $(_t.children()[0]).text();
							var value = inputs.val();
							o.push(label,value);
							obj.push(o);
							break;
						case "email":
							var o = [];
							var label = $(_t.children()[0]).text();
							var value = inputs.val();
							o.push(label,value);
							obj.push(o);
							break;
						case "tel":
							var o = [];
							var label = $(_t.children()[0]).text();
							var value = inputs.val();
							o.push(label,value);
							obj.push(o);
							break;
						case "number":
							var o = [];
							var label = $(_t.children()[0]).text();
							var value = inputs.val();
							o.push(label,value);
							obj.push(o);
							break;
						case "time":
							var o = [];
							var label = $(_t.children()[0]).text();
							var value = inputs.val();
							o.push(label,value);
							obj.push(o);
							break;
						case "date":
							var o = [];
							var label = $(_t.children()[0]).text();
							var value = inputs.val();
							o.push(label,value);
							obj.push(o);
							break;
						case "url":
							var o = [];
							var label = $(_t.children()[0]).text();
							var value = inputs.val();
							o.push(label,value);
							obj.push(o);
							break;
							
							
					}
				});
				
				$.ajax({
						type:"POST", 
						url:"submitdata", 
						data:{
							id:formID,
							data:JSON.stringify(obj)
						}
					}).done(function(data){
						if(data.message=="ok") {
							$(".form-struct").html("<div class='submit-ok'>提交成功</div>");
						} else {
							alert("提交失败");
						}
					}).fail(function(){
						alert("提交失败");
					});
								
			});
			
		});
		
		
		</script>
		
    </body>
</html>