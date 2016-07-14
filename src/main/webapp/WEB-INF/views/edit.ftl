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
	<link rel="stylesheet" href="${frontBase}/css/edit.css">
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
				<div class="main col-md-10 col-md-offset-1 col-sm-12 col-xs-12">
					<div class="main-top">
						<div class="save-date pull-left"> <span class="date">暂未保存</span></div>
						<div class="btns pull-right">
							<div class="btn btn-warning glyphicon glyphicon-eye-open"
								 id="preview-btn"></div>
							<div id="publish-btn" class="btn btn-info">发布</div>
							<div id="save-form-btn" class="btn btn-info">保存</div>
						</div>
					</div>
					<div class="border"></div>
					<div class="main-bottom">
						<div class="components">
							<div class="c-title">组件区</div>
							<div class="c-content clearfix">
								<div class="component" draggable="true" id="OrdinaryInputComp">
									<span class="glyphicon glyphicon-info-sign"> 单行文本框</span>
								</div>
								<div class="component" draggable="true" id="TextAreaComp">
									<span class="glyphicon glyphicon-align-justify"> 多行文本框</span>
								</div>
								<div class="component" draggable="true" id="RadioInputComp">
									<span class="glyphicon glyphicon-record"> 单选框</span>
								</div>
								<div class="component" draggable="true" id="CheckboxInputComp">
									<span class="glyphicon glyphicon-ok-sign"> 多选框</span>
								</div>
								<div class="component" draggable="true" id="SelectInputComp">
									<span class="glyphicon glyphicon-chevron-down"> 下拉框</span>
								</div>
								<div class="component" draggable="true" id="ImageEleComp">
									<span class="glyphicon glyphicon-picture"> 图片</span>
								</div>
								<div class="component" draggable="true" id="UploadInputComp">
									<span class="glyphicon glyphicon-open"> 上传文件</span>
								</div>
								<div class="component" draggable="true" id="HTMLComp">
									<span class="glyphicon glyphicon-text-color"> HTML</span>
								</div>
							</div>
						</div>
						<div class="design-area">
						</div>
						<div class="properties">
							<div class="c-title">属性</div>
							<div class="c-content properties-panel">
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>

		<div class="modal fade" id="preview" tabindex="-1" role="dialog" aria-labelledby="previewLabel">
			  <div class="modal-dialog modal-lg" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="previewLabel">预览</h4>
			      </div>
			      <div class="modal-body ">
						<div class="previewArea row">
							<div class="form-struct col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">
								<div class="createArea"></div>
								<div class="submitArea"><div class="btn btn-info">提交</div></div>
							</div>
						</div>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			      </div>
			    </div>
			  </div>
		</div>




		<script type="text/html" id="OrdinaryInput">
			<div id="ordinary-input-[:=id:]">
			<div class="custom-part">
				<div class="form-group">
					<label class="control-label">标签</label>
					<div>
						<input type="text" value="[:=label:]" class="form-control ordinary-input-label">
					</div>
				</div>
				<div class="form-group">
					<label for="label" class="control-label">类型</label>
					<select class="form-control ordinary-input-select">
					   [:for(var i=0; i<typeOption.length; i++){:]
					   	[:if(typeOption[i].type===type){:]
					   		<option selected value="[:=typeOption[i].type:]">[:=typeOption[i].text:]</option>
					   	[:} else {:]
					   		 <option value="[:=typeOption[i].type:]">[:=typeOption[i].text:]</option>
					   	[:}:]
					   [:}:]
					</select>
				</div>
				<div class="form-group">
					<div class="checkbox">
					  <label>
					    <input class="ordinary-input-checkbox" type="checkbox" [:if(required){:]checked[:}:]>
					    必选
					  </label>
					</div>
				</div>

				<div class="form-group">
					<label for="label" class="control-label">最短长度</label>
					<div>
						<input class="form-control ordinary-input-minLength" type="text" value="[:=minLength:]" class="">
					</div>
				</div>
				<div class="form-group">
					<label for="label" class="control-label">最长长度</label>
					<div>
						<input class="form-control ordinary-input-maxLength" type="text" value="[:=maxLength:]">
					</div>
				</div>
				[:if(type==="textarea") {:]
				<div class="form-group">
					<label for="label" class="control-label">显示行数</label>
					<div>
						<input class="form-control ordinary-input-rows" type="text" value="[:=rows:]">
					</div>
				</div>
				[:}:]
				<div class="form-group">
					<label for="label" class="control-label">placeholder</label>
					<div>
						<input type="text" value="[:=placeholder:]" class="form-control ordinary-input-placeholder">
					</div>
				</div>
			</div>
			<div class="advance-part">
				<div class="c-title">高级</div>
				<div class="form-group">
					<label for="label" class="control-label">格式限制</label>
					<div>
						<input type="text" class="form-control ordinary-input-validate">
					</div>
				</div>
			</div>
			</div>
		</script>
		<script type="text/html" id="TextArea">
			<div id="ordinary-input-[:=id:]">
			<div class="custom-part">
				<div class="form-group">
					<label class="control-label">标签</label>
					<div>
						<input type="text" value="[:=label:]" class="form-control ordinary-input-label">
					</div>
				</div>
				<div class="form-group">
					<label for="label" class="control-label">类型</label>
					<select class="form-control ordinary-input-select">
					   [:for(var i=0; i<typeOption.length; i++){:]
					   	[:if(typeOption[i].type===type){:]
					   		<option selected value="[:=typeOption[i].type:]">[:=typeOption[i].text:]</option>
					   	[:} else {:]
					   		 <option value="[:=typeOption[i].type:]">[:=typeOption[i].text:]</option>
					   	[:}:]
					   [:}:]
					</select>
				</div>
				<div class="form-group">
					<div class="checkbox">
					  <label>
					    <input class="ordinary-input-checkbox" type="checkbox" [:if(required){:]checked[:}:]>
					    必选
					  </label>
					</div>
				</div>

				<div class="form-group">
					<label for="label" class="control-label">最短长度</label>
					<div>
						<input class="form-control ordinary-input-minLength" type="text" value="[:=minLength:]" class="">
					</div>
				</div>
				<div class="form-group">
					<label for="label" class="control-label">最长长度</label>
					<div>
						<input class="form-control ordinary-input-maxLength" type="text" value="[:=maxLength:]">
					</div>
				</div>
				<div class="form-group">
					<label for="label" class="control-label">显示行数</label>
					<div>
						<input class="form-control ordinary-input-rows" type="text" value="[:=rows:]">
					</div>
				</div>
				<div class="form-group">
					<label for="label" class="control-label">placeholder</label>
					<div>
						<input type="text" value="[:=placeholder:]" class="form-control ordinary-input-placeholder">
					</div>
				</div>
			</div>
			<div class="advance-part">
				<div class="c-title">高级</div>
				<div class="form-group">
					<label for="label" class="control-label">格式限制</label>
					<div>
						<input type="text" class="form-control ordinary-input-validate">
					</div>
				</div>
			</div>
			</div>
		</script>
		<script type="text/html" id="RadioInput">
			<div class="custom-part">
				<div class="form-group">
					<label class="control-label">标签</label>
					<div>
						<input type="text" value="[:=label:]" class="form-control radio-input-label">
					</div>
				</div>
				<div class="form-group">
					<div class="checkbox">
					  <label>
					    <input class="radio-input-required" type="checkbox" [:if(required){:]checked[:}:]>
					    必选
					  </label>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label">选项</label>
					<div class="form-horizontal">
						[:for(var i=0; i<values.length; i++){:]
						<div class="form-group">
							<label class="control-label col-sm-1">
								<input name="add-radio" class="radio-input-checked" type="radio" [:if(values[i].checked){:]checked[:}:]>
							</label>
							<div class="col-sm-7">
								<input class="form-control radio-input-option" value="[:=values[i].value:]" type="text">
							</div>
							<span class="add-option-btn glyphicon glyphicon-plus bg-primary"></span>
							<span class="delete-option-btn glyphicon glyphicon-minus bg-danger"></span>
						</div>
						[:}:]
					</div>
				</div>
			</div>
		</script>

		<script type="text/html" id="CheckboxInput">
			<div class="custom-part">
				<div class="form-group">
					<label class="control-label">标签</label>
					<div>
						<input type="text" value="[:=label:]" class="form-control checkbox-input-label">
					</div>
				</div>
				<div class="form-group">
					<div class="checkbox">
					  <label>
					    <input class="checkbox-input-required" type="checkbox" [:if(required){:]checked[:}:]>
					    必选
					  </label>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label">选项</label>
					<div class="form-horizontal">
						[:for(var i=0; i<values.length; i++){:]
						<div class="form-group">
							<label class="control-label col-sm-1">
								<input name="add-radio" class="checkbox-input-checked" type="checkbox" [:if(values[i].checked){:]checked[:}:]>
							</label>
							<div class="col-sm-7">
								<input class="form-control checkbox-input-option" value="[:=values[i].value:]" type="text">
							</div>
							<span class="add-option-btn glyphicon glyphicon-plus bg-primary"></span>
							<span class="delete-option-btn glyphicon glyphicon-minus bg-danger"></span>
						</div>
						[:}:]
					</div>
				</div>
			</div>
		</script>

		<script type="text/html" id="SelectInput">
			<div class="custom-part">
				<div class="form-group">
					<label class="control-label">标签</label>
					<div>
						<input type="text" value="[:=label:]" class="form-control select-input-label">
					</div>
				</div>
				<div class="form-group">
					<div class="checkbox">
					  <label>
					    <input class="select-input-required" type="checkbox" [:if(required){:]checked[:}:]>
					    必选
					  </label>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label">选项</label>
					<div class="form-horizontal">
						[:for(var i=0; i<values.length; i++){:]
						<div class="form-group">
							<label class="control-label col-sm-1">
								<input name="add-radio" class="select-input-checked" type="radio" [:if(values[i].checked){:]checked[:}:]>
							</label>
							<div class="col-sm-7">
								<input class="form-control select-input-option" value="[:=values[i].value:]" type="text">
							</div>
							<span class="add-option-btn glyphicon glyphicon-plus bg-primary"></span>
							<span class="delete-option-btn glyphicon glyphicon-minus bg-danger"></span>
						</div>
						[:}:]
					</div>
				</div>
			</div>
		</script>

		<script id="ImageEle" type="text/html">
			<div class="custom-part">
				<label for="label" class="control-label">上传图片</label>
				<div>
					<input type="file" class="upload-image" accept="image/*">
				</div>
			</div>

		</script>
		<script id="HTML" type="text/html">
			<div class="custom-part">
				<label for="label" class="control-label">HTML内容</label>
				<div>
					<textarea class="form-control html-content">[:=content:]</textarea>
				</div>
			</div>
		</script>
		
		<script id="UploadInput" type="text/html">
			<div class="custom-part">
				<div class="form-group">
					<label class="control-label">标签</label>
					<div>
						<input type="text" value="[:=label:]" class="form-control upload-input-label">
					</div>
				</div>
				<div class="form-group">
					<div class="checkbox">
					  <label>
					    <input class="upload-input-required" type="checkbox" [:if(required){:]checked[:}:]>
					    必选
					  </label>
					</div>
				</div>
			</div>
		</script>

		<script id="Form" type="text/html">
			<div class="custom-part">
				<div class="form-group">
					<label for="label" class="control-label">表单名称</label>
					<div>
						<input type="text" value="[:=name:]" class="form-control form-name">
					</div>
				</div>
			</div>
		</script>
		<script type="text/javascript" src="${frontBase}/lib/jquery/jquery.js"></script>
		<script type="text/javascript" src="${frontBase}/lib/bootstrap/js/bootstrap.js"></script>
		<script type="text/javascript" src="${frontBase}/lib/mCustomerScrollbar/js/jquery.mCustomScrollbar.concat.min.js"></script>
		<script type="text/javascript" src="${frontBase}/js/freeform-engine.js"></script>
		<script type="text/javascript" src="${frontBase}/js/tppl.js"></script>
		<script type="text/javascript" src="${frontBase}/js/jquery.resize.js"></script>
		<script type="text/javascript" src="${frontBase}/js/edit.js"></script>
    </body>
</html>