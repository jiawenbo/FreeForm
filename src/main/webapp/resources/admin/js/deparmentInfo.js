$(function(){
	
	var table = $("#table").DataTable({
		'sPaginationType':'full_numbers',
		'processing':true,
		'pageLength':10,
		'autoWidth':true,
		'scrollX':true,
		'serverSide':true,
		'ajax':{
			'url':'deparment/get',
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
		{'data':'department','name':'department','title':'院系'},
		{'data':'major','name':'major','title':'专业'},
		{'data':null,'title':'操作','createdCell':function(cell, cellData, rowData, row, col){
			$(cell).html('<button class="btn btn-sm btn-success update">修改</button> <button class="btn btn-sm btn-danger delete">删除</button>');
		}}
		]
	});
	$("#add").on("click",function(){
		var data = {department:"",major:""};
		addOrUpdate("添加院系专业信息", tppl($("#updateTmpl").html(),data),"添加");
	});
	$("#table").on('click','.update',function(event){
		var button = $(this);
		var row = table.row(button.parents("tr")[0]);
		var d = row.data();
		var data = {department:d.department, major:d.major};
		addOrUpdate("修改院系专业信息",tppl($("#updateTmpl").html(),data),"更新",d.id);
	});
	$("#table").on('click','.delete',function(){
		var button = $(this);
		var row = table.row(button.parents("tr")[0]);
		var d = row.data();
		layer.confirm('你确定要删除该条记录吗？',{icon:0,title:'提示'},function(index){
			$("#table").uiAjax({
				msg:'正在删除...',
				url:'deparment/delete',
				type:'POST',
				data:{
					id:d.id
				},
				success:function(data){
					if(data.status==="ok"){
						layer.close(index);
						table.ajax.reload();
					}
					layer.msg(data.data,{icon:0,time:2000},function(){});
				},
				error:function(){
					layer.msg("网络加载失败",{icon:2,time:2000},function(){});
				}
			});
			
		});
	});
	
	
	function addOrUpdate(title,content,btn,id){
		layer.open({
			title:title,
			btn:[btn,"取消"],
		    type: 1,
		    area: ['500px', '500px'], //宽高
		    content: content,
		    yes:function(index, layero){
		    	var data = {};
		    	if(id){
		    		data.id = id;
		    	}
		    	data.department = $("#department").val();
		    	data.major = $("#major").val();
		    	$(".layui-layer-content").uiAjax({
		    		msg:'正在更新...',
		    		url:'deparment/update',
		    		type:'POST',
		    		data:data,
		    		success:function(data){
		    			if(data.status==="ok"){
		    				layer.close(index);
		    				table.ajax.reload();
		    			}
		    			layer.msg(data.data,{icon:0,time:2000},function(){});
		    		},
		    		error:function(){
		    			layer.msg("网络加载失败",{icon:2,time:2000},function(){});
		    		}
		    	});
		    }
		});
	}
	
	
	
	
	
});