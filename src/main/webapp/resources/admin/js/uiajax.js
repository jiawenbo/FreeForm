;(function(document,window,$){
	var constant = {
		dom:function(str){
			return '<div class="loading">'+
'			<div class="loading-inner">'+
'				<span class="inner-main">'+
'					<img src="/tcl/resources/admin/img/loading.gif" />'+
'				</span>'+
'				<br />'+
'				<span class="inner-text">'+str+'</span>'+
'			</div>'+
'		</div>';
		},
		str:"加载中.."
	};
	$.fn.uiAjax=function(config){
		return this.each(function(){
			var t = $(this);
			var tPos = t.css('position');
			if(tPos!=='relative'||tPos!=='absolute'||tPos!=='fixed')
				t.css('position','relative');
			var loading = $(constant.dom(config.msg||constant.str)).appendTo(t);
			var success=config.success||function(){};
			var error = config.error||function(){};
			config.success=function(data){
				success(data);
				loading.remove();
			};
			config.error=function(){
				error();
				loading.remove();
			}
			$.ajax(config);
		});
	}
})(document,window,$);