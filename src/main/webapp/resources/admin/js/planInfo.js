$(function(){
	
	var table = $("#table").DataTable({
		'sPaginationType':'full_numbers',
		'processing':true,
		'pageLength':10,
		'autoWidth':true,
		'scrollX':true,
		'serverSide':true,
		'ajax':{
			'url':'plan/get',
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
		{'data':'plan.id','name':'id','title':'ID',createdCell:function(cell, cellData, rowData, row, col){
			$(cell).html(table.page.info().start+row+1);
		}},
		{'data':'plan.chooseTime','name':'','title':'选课时间'},
		{'data':'teacher.name','name':'','title':'教师姓名'},
		{'data':'lesson.name','name':'','title':'已选课程'},
		{'data':null,'title':'操作','createdCell':function(cell, cellData, rowData, row, col){
			$(cell).html('<button class="btn btn-sm btn-danger delete">删除</button>');
		}}
		]
	});
	$("#add").on("click",function(){
		var data = {};
		addOrUpdate("可选课程","我已选好");
	});
	$("#table").on('click','.delete',function(){
		var button = $(this);
		var row = table.row(button.parents("tr")[0]);
		var d = row.data();
		layer.confirm('你确定要删除该条记录吗？',{icon:0,title:'提示'},function(index){
			$("#table").uiAjax({
				msg:'正在删除...',
				url:'plan/delete',
				type:'POST',
				data:{
					id:d.plan.id
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
	
	
	function addOrUpdate(title,btn){
		layer.open({
			title:title,
			btn:[btn,"取消"],
		    type: 1,
		    area: ['500px', '500px'], //宽高
		    content: "",
		    success:function(layero, index){
		    	var content = $(layero).children(".layui-layer-content").uiAjax({
		    		msg:'正在拉取课程列表...',
		    		url:'lesson/list',
		    		type:'POST',
		    		success:function(data){
		    			if(data.status=="ok"){
		    				var list = data.data;
		    				content.html(tppl($("#updateTmpl").html(),{list:data.data}));
		    				return;
		    			}
		    			layer.msg(data.data,{icon:0,time:2000},function(){});
		    		}
		    	});
		    },
		    yes:function(index, layero){
		    	var data = {};
		    	if($("#form").length===0){
		    		layer.close(index);
		    		return;
		    	}
		    	data.id = $("input[name='lesson']:checked").val();
		    	$(".layui-layer-content").uiAjax({
		    		msg:'正在选课...',
		    		url:'plan/choose',
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