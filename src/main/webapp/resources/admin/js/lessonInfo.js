$(function(){
	
	var table = $("#table").DataTable({
		'sPaginationType':'full_numbers',
		'processing':true,
		'pageLength':10,
		'autoWidth':true,
		'scrollX':true,
		'serverSide':true,
		'ajax':{
			'url':'lesson/get',
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
		{'data':'lesson.id','name':'id','title':'ID',createdCell:function(cell, cellData, rowData, row, col){
			$(cell).html(table.page.info().start+row+1);
		}},
		{'data':'lesson.name','name':'name','title':'课程名称'},
		{'data':'lesson.rank','name':'rank','title':'课程要求职称','render':function(data,type,full,meta){
			if(data){
				var arr = data.split(",");
				var str = "";
				for(var i=0; i<arr.length; i++){
					var temp = arr[i];
					if(temp==="0"){
						temp="教师";
					}
					if(temp==="1"){
						temp="讲师";
					}
					if(temp=="2"){
						temp="副教授";
					}
					if(temp=="3"){
						temp="教授"
					}
					str+=temp;
					if(i<arr.length-1){
						str+=" 或 ";
					}
				}
				return str;
			}
			return "";
		}},
		{'data':'deparment','name':'','title':'院系','render':function(data,type,full,meta){
			if(data&&data.department){
				return data.department;
			}
			return "";
		}},
		{'data':'deparment','name':'','title':'专业','render':function(data,type,full,meta){
			if(data&&data.major){
				return data.major;
			}
			return "";
		}},
		{'data':null,'title':'操作','createdCell':function(cell, cellData, rowData, row, col){
			$(cell).html('<button class="btn btn-sm btn-success update">修改</button> <button class="btn btn-sm btn-danger delete">删除</button>');
		}}
		]
	});
	$("#add").on("click",function(){
		var departmentList = getDepartmentList();
		var data = {name:"",rank:"",department:departmentList?departmentList:[],nowDepartment:null};
		addOrUpdate("添加课程", tppl($("#updateTmpl").html(),data),"添加");
	});
	$("#table").on('click','.update',function(event){
		var button = $(this);
		var row = table.row(button.parents("tr")[0]);
		var d = row.data();
		var departmentList = getDepartmentList();
		var data = {name:d.lesson.name,rank:d.lesson.rank,department:departmentList?departmentList:[],nowDepartment:d.deparment.id};
		addOrUpdate("修改课程信息",tppl($("#updateTmpl").html(),data),"更新",d.lesson.id);
	});
	$("#table").on('click','.delete',function(){
		var button = $(this);
		var row = table.row(button.parents("tr")[0]);
		var d = row.data();
		layer.confirm('你确定要删除该条记录吗？',{icon:0,title:'提示'},function(index){
			$("#table").uiAjax({
				msg:'正在删除...',
				url:'lesson/delete',
				type:'POST',
				data:{
					id:d.lesson.id
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
	function getDepartmentList(){
		var data;
		$.ajax({
			url:'deparment/list',
			type:'GET',
			async:false,
			success:function(d){
				data = d.data;
			}
		});
		return data;
	}
	
	
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
		    	data.name = $("#name").val();
		    	data.rank = $("#rank").val();
		    	data.department = $("#deparment").val();
		    	$(".layui-layer-content").uiAjax({
		    		msg:'正在更新...',
		    		url:'lesson/update',
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