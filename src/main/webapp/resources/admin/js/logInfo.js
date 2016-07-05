$(function(){
	var table = $("#table").DataTable({
		'sPaginationType':'full_numbers',
		'processing':true,
		'pageLength':10,
		'autoWidth':true,
		'scrollX':true,
		'serverSide':true,
		'ajax':{
			'url':'log/get',
			'type':'POST'
		},
		'language':{
					'sProcessing':'加载中...',
					'sSearch':'搜索',
					'lengthMenu':'每页 _MENU_ 条记录',
					'zeroRecords':'没有找到记录',
					'info':'第 _PAGE_ 页 （ 总共 _PAGES_ 页）',
					'infoEmpty':'无记录',
					'infoFiltered':'(从 _MAX_ 记录过滤)',
					'oPaginate':{
						'sFirst':'首页',
						'sPrevious':'上页',
						'sNext':'下页',
						'sLast':'末页'
					}
		},
		'columns':[
		{'data':'id','name':'id','title':'ID',createdCell:function(cell, cellData, rowData, row, col){
			$(cell).html(table.page.info().start+row+1);
		}},
		{'data':'operateTime','name':'operateTime','title':'操作时间'},
		{'data':'operateContent','name':'operateContent','title':'操作内容'}
		]
	});
	
});