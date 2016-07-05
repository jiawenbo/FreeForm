;(function($,document,window,KindEditor){
		//初始化KindEditor
		KindEditor.ready(function(K){
			var options = {
				items:['source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen',
        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
        'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
        'anchor', 'link', 'unlink']
				}
			window.editor = K.create("#editor",options);
		});
		
			var dataInfo = {};
			var validate = {};
			$.fn.dataGrid = function(options){
				return this.each(function(){
					var t = $(this);
					var columnsOption = options.columns;
					var targetArr = [];
					for(var item in columnsOption){
						var temp = columnsOption[item];
						var classname = temp.className;
						var field = temp.data;
						if(temp.validate){
							validate[temp.data]=temp.validate;
						}
						targetArr.push(parseInt(item));
						if(classname=='cell-img'){
							temp.createdCell=function(cell, cellData, rowData, row, col){
								$(cell).attr('data-field', columnsOption[col].data);
								if(cellData=="null"){
									cellData="admin/img/user2-160x160.jpg";
								}
								$(cell).attr('data-edit',columnsOption[col].isEdit);
								$(cell).html('<div class="cell-img-wrapper"><img class="user-img" src="'+cellData+'" alt /><input type="file" name="image" class="img-file-upload" formenctype="multipart/form-data" multiple /><div class="img-file-progress"></div></div>');
							}
						}
						else if(classname=='cell-tag'){
							dataInfo[temp.data]={};
							if(!temp.hasOwnProperty("tag_list")&&temp.hasOwnProperty('tag_url')){
								$.ajax({
									type:"get",
									url:temp.tag_url,
									async:false,
									success:function(d){
										for(var temp in d.data){
											dataInfo[temp.data][d.data[temp]]=d.data[temp];
										}
									},
									error:function(){
										alert("请检查你的网络！");
									}
								});
							}
							else
							{
								if(temp.tag_map){
									for(var d in temp.tag_list){
										dataInfo[temp.data][temp.tag_list[d]]=temp.tag_map[d];
									}
								}
								else
								{
									for(var d in temp.tag_list){
										dataInfo[temp.data][temp.tag_list[d]]=temp.tag_list[d];
									}
								}
								
							}
							temp.createdCell=function(cell, cellData, rowData, row, col){
								if(cellData=="null"){
									cellData="";
								}
								for(var i=0; i<temp.tag_list.length;i++){
									if(cellData==temp.tag_list[i]){
										cellData=temp.tag_map[i];
									}
								}
								$(cell).attr('data-edit',columnsOption[col].isEdit);
								$(cell).attr('data-field', columnsOption[col].data);
								$(cell).html('<span class="label label-success tag-content">'+cellData+'</span>');
							}
						}
						else if(classname=='cell-video'){
							temp.createdCell=function(cell, cellData, rowData, row, col){
								if(cellData=="null"){
									cellData="";
								}
								$(cell).attr('data-edit',columnsOption[col].isEdit);
								$(cell).attr('data-field', columnsOption[col].data);
								$(cell).html('<div class="cell-video-wrapper"><img src="'+cellData.poster+'" alt="播放" /><input type="file" name="video" class="video-file-upload" formenctype="multipart/form-data" multiple /><div class="video-file-progress"></div><div class="video-play"><i class="fa  fa-play-circle"></i></div></div>');
								$(cell).attr("data-poster",cellData.poster).attr("data-url",cellData.url);
								$(cell).find(".video-play").on('click',function(){
								var modal_body = $("#video").find(".modal-body");
									modal_body.html(_createVideo($(cell).attr("data-poster"),$(cell).attr("data-url")));
									$("#video").on('shown.bs.modal',function(event){
										$(this).find("#video-play")[0].width=modal_body.width();
									})
									.on('hide.bs.modal',function(event){
										modal_body.html("");
									})
									$("#video").modal("show");
								});
							}
						}
						
						else if(classname=='cell-content'){
							temp.createdCell=function(cell, cellData, rowData, row, col){
								if(cellData=="null"){
									cellData="";
								}
								$(cell).attr('data-edit',columnsOption[col].isEdit);
								$(cell).attr('data-field', columnsOption[col].data);
								$(cell).html("<div class='cell-content-wrapper'></div>");
								$(cell).find(".cell-content-wrapper").html(cellData);
							}
						}
						else if(classname=='cell-date-range'){
							
							temp.createdCell=function(cell, cellData, rowData, row, col){
								if(cellData=="null"){
									cellData="";
								}
								$(cell).attr('data-edit',columnsOption[col].isEdit);
								$(cell).attr('data-field', columnsOption[col].data);
								$(cell).text(cellData[0]+"--"+cellData[1]);
							}
						}
						else{
							
							temp.createdCell=function(cell,cellData,rowData,row,col){
								if(cellData=="null"){
									cellData="";
								}
								$(cell).attr('data-edit',columnsOption[col].isEdit);
								$(cell).text(cellData);
								$(cell).attr('data-field', columnsOption[col].data);
							}
						}
						
						
					}
					columnsOption.push({'data':null});
					
					//初始化表格
					t.on('init.dt',function(){
						t.find('thead th').html("");
						//该列表默认显示1行，多出部分予以隐藏，可以用render属性
						var cellTexts = $(this).find('.cell-flex-text');
						if(cellTexts.length>0){
							for(var i=1; i<cellTexts.length; i++){
								var temp = $(cellTexts[i]);
								_createFlexibleCell(temp,temp.text());
							}
						}
					});
					
					var datatable = t.DataTable({
						'sPaginationType':'full_numbers',
						'processing':true,
						'scrollX':true,
						'pageLength':10,
						'serverSide':options.hasOwnProperty("serverSide")?options.serverSide:false,
						'ajax':options.hasOwnProperty("ajax")?options.ajax:null,
						'data':options.hasOwnProperty("data")?options.data:null,
						'columnDefs':[{
							'targets':targetArr,
							'defaultContent':'null'
							},
							{
							'targets':-1,
							'data':null,
							'width':'10%',
							'title':'操作',
							'searchable':false,
							'type':'html',
							'orderable':false,
							'className':'action',
							'defaultContent':'<button type="button" class="btn btn-sm bg-green edit-btn"><i class="fa fa-pencil"></i></button> <button type="button" class="btn btn-sm bg-green delete-btn"><i class="fa fa-close"></i></button>'
						}],
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
						'columns':columnsOption
					});
					
					
					
					
					
					
					//定义几个特殊的的类型（ID，Date，Img,Video,Content,Tag,Uneditable）
			//click edit-btn to edit data
			$("table tbody").on('click','.edit-btn',function(){
				//get the current row
				var tr  =$(this).parents('tr');
				//var row = table.row(tr);
				var tds = tr.children('td');
				
				for(var i=0; i<tds.length; i++){
					var temp = $(tds[i]);
					if(!temp.hasClass('action')&&!temp.hasClass('cell-id')&&!temp.hasClass('uneditable')&&temp.attr('data-edit')!="false"){
						if(temp.hasClass('cell-img')){
							temp.children("div").addClass('upload-img');
							temp.find('.img-file-upload').show();
							
							temp.children('.upload-img').fileupload({
								url:options.hasOwnProperty("uploadImage")?options.uploadImage:'',
								dataType:'json',
								autoUpload:true,
								maxFileSize:500000000,
								acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
							})
							.on('fileuploadprogressall', function (e, data) {
        					var progress = parseInt(data.loaded / data.total * 100, 10);
        					$(this).find(".img-file-progress").show();
									$(this).find(".img-file-progress").css('width',progress+'%');	
   						})
							.on('fileuploadfail',function(e,data){
								$(this).find(".img-file-progress").hide();
								alert('loadfail');
							})
							.on('fileuploadprocessalways',function(e,data){
								var file = data.files[data.index];
								if(file.error){
									alert("请选择正确格式的图片");
								}
							})
							.on('fileuploaddone',function(e,data){
									console.log(data);
									var p = $(this).find(".img-file-progress");
									var img = $(this).find('img').attr('src',encodeURI(data._response.result.url));
									p.html("<i class='fa fa-check'></i>");
									setTimeout(function(){
										p.html("");
										p.hide(500);
									},1500);
										
	
							});
						}
						else if(temp.hasClass('cell-video')){
							temp.find(".video-play").hide();
							temp.children("div").addClass("upload-video").children(".video-file-upload").show();
							temp.children("div").fileupload({
								url:options.hasOwnProperty("uploadVideo")?options.uploadVideo:'',
								dataType:'json',
								autoUpload:true,
								maxFileSize:500000000,
								acceptFileTypes: /(\.|\/)(mp4)$/i,
							})
							.on('fileuploadprogressall', function (e, data) {
        					var progress = parseInt(data.loaded / data.total * 100, 10);
        					$(this).find(".video-file-progress")
        					.show()
        					.css('width',progress+'%');
   						})
							.on('fileuploadfail',function(e,data){
								$(this).find(".video-file-progress").hide();
								alert('loadfail');
							})
							.on('fileuploadprocessalways',function(e,data){
								var file = data.files[data.index];
								if(file.error){
									alert("请选择正确格式的视频,目前只支持mp4");
								}
							})
							.on('fileuploaddone',function(e,data){
									console.log(data);
									var p = $(this).find(".video-file-progress");
									var response = data._response.result;
									var img = $(this).find('img').attr('src',encodeURI(response.poster));
									$(this).parent().attr("data-poster",response.poster);
									$(this).parent().attr("data-url",response.url);
									p.html("<i class='fa fa-check'></i>");
									setTimeout(function(){
										p.html("");
										p.hide(500);
									},1500);
	
							});
						}
						else if(temp.hasClass('cell-date')){
							var ip=$("<input type='text' class='date-input' readonly />");
							ip.datepicker({format:'yyyy.mm.dd',language: "zh-CN",zIndexOffset:'2000'});
							ip.css('width',temp.width());
							ip.val(temp.text());
							temp.html(ip);
							
						}
						else if(temp.hasClass('cell-date-range')){
							var dateArr = temp.text().split('--');
							var startEle = $('<input type="text" class="date-input-start-time" readonly />');
							startEle.val(dateArr[0]);
							var endEle = $('<input type="text" class="date-input-end-time" readonly />');
							endEle.val(dateArr[1]);
							var wrap = $('<div class="input-daterange"></div>');
							wrap.append(startEle);
							wrap.append(endEle);
							wrap.datepicker({format:'yyyy.mm.dd',language: "zh-CN",zIndexOffset:'2000'});
							temp.html("");
							temp.append(wrap);
							
						}
						else if(temp.hasClass('cell-tag')){
							//get tag list from server
							var p = temp.attr('data-field');
							var selectList=_createSelect(temp.children('.tag-content').text(),dataInfo.hasOwnProperty(p)?dataInfo[p]:{});
							temp.html(selectList);
						}
						else if(temp.hasClass('cell-content')){
							var cellContent = temp.find(".cell-content-wrapper");
							cellContent.css('width',temp.width());
							temp.addClass("content-active");
							cellContent.addClass('active');
							temp.bind('click',function(){
								$(".ke-container").addClass("active");
								$("#content").modal("show");
								window.editor.html(cellContent.html());
							});
							$("#edit-save").bind('click',function(){
								cellContent.html(window.editor.html());
								$("#content").modal("hide");
							});
							
						}
						else if(temp.hasClass('cell-flex-text'))
						{
							//create input
							temp.unbind('click');
							var ip=$("<input type='text' class='text-input' />");
							ip.css('width',temp.width());
							if(temp.find('.cell-text-etc').length>0){
								ip.val(temp[0].childNodes[0].nodeValue+temp.find('.cell-text-other').text());
							}
							else
							{
								ip.val(temp.text());
							}
							temp.html(ip);
						}
						else
						{
							var ip=$("<input type='text' class='text-input' />");
							ip.css('width',temp.width());
							ip.val(temp.text());
							temp.html(ip);
						}
						
					}
					$(this).removeClass('edit-btn');
					$(this).addClass('save-btn');
					$(this).children().removeClass('fa-pencil');
					$(this).children().addClass('fa-floppy-o');
				}
				
			});
	
			//click savebtn
			$("table tbody").on('click','.save-btn',function(){
				//get the current row
				var tr  =$(this).parents('tr');
				var tds = tr.children('td');
				var data={};
				for(var i=0; i<tds.length; i++){
					var temp = $(tds[i]);
					if(!temp.hasClass('action')&&!temp.hasClass('cell-id')&&!temp.hasClass('uneditable')&&temp.attr('data-edit')!="false"){
						var dataField = temp.attr('data-field');
						var valid = validate[dataField];
						if(temp.hasClass('cell-img')){
							var imgData = temp.find('img').attr('src');
							if(valid){
								if(!valid.canEmpty&&!imgData){
									alert(columnsOption[i].title+"必须上传");
									return;
								}
							}
						}
						else if(temp.hasClass('cell-video')){
							var videoData = {'url':temp.attr('data-url'),'poster':temp.attr('poster')};
							if(valid){
								if(!valid.canEmpty&&!videoData){
									alert(columnsOption[i].title+"必须上传");
									return;
								}
							}
						}
						else if(temp.hasClass('cell-date')){
							
							var date_input = temp.children('.date-input');
							var dateValue = date_input.val();
							if(valid){
								if(!valid.canEmpty&&!dateValue){
									alert(columnsOption[i].title+"不能为空");
									return;
								}
								if(valid.regex){
									var pattern = new RegExp(valid.regex);
									if(!pattern.test(dateValue)){
										alert(valid.tip);
										return;
									}
								}
							}
							
						}
						else if(temp.hasClass('cell-date-range')){
							var start_time = temp.find('.date-input-start-time').val();
							var end_time = temp.find('.date-input-end-time').val();
							if(valid){
								if(!valid.canEmpty&&(!start_time||!end_time)){
									alert(columnsOption[i].title+"不能为空");
									return;
								}
								if(valid.regex){
									var pattern = new RegExp(valid.regex);
									if(!pattern.test(start_time)||!pattern.test(end_time)){
										alert(valid.tip);
										return;
									}
								}
							}
						}
						else if(temp.hasClass('cell-tag')){
							
						}
						else if(temp.hasClass('cell-content')){
							var cell_content_wrapper = temp.find(".cell-content-wrapper");
							var contentData = cell_content_wrapper.html();
							if(valid){
								if(!valid.canEmpty&&!contentData){
									alert(columnsOption[i].title+"不能为空");
									return;
								}
								if(valid.regex){
									var pattern = new RegExp(valid.regex);
									if(!pattern.test(contentData)){
										alert(valid.tip);
										return;
									}
								}
							}
						}
						else if(temp.hasClass('cell-flex-text')){
							var tv = temp.children('.text-input').val();
							if(valid){
								if(!valid.canEmpty&&!tv){
									alert(columnsOption[i].title+"不能为空");
									return;
								}
								if(valid.regex){
									var pattern = new RegExp(valid.regex);
									if(!pattern.test(tv)){
										alert(valid.tip);
										return;
									}
								}
							}
							
						}
						else
						{
							var tv = temp.children('.text-input').val();
							if(valid){
								if(!valid.canEmpty&&!tv){
									alert(columnsOption[i].title+"不能为空");
									return;
								}
								if(valid.regex){
									var pattern = new RegExp(valid.regex);
									if(!pattern.test(tv)){
										alert(valid.tip);
										return;
									}
								}
							}
						}
						
					}
					
				}
				
				
				for(var i=0; i<tds.length; i++){
					var temp = $(tds[i]);
					if(!temp.hasClass('action')&&!temp.hasClass('cell-id')&&!temp.hasClass('uneditable')){
						var dataField = temp.attr('data-field');
						if(temp.hasClass('cell-img')){
							data[dataField] = temp.find('img').attr('src');
							temp.find('.img-file-upload').hide();
							temp.children('div').removeClass('upload-img');
						}
						else if(temp.hasClass('cell-video')){
							data[dataField] = {'url':temp.attr('data-url'),'poster':temp.attr('poster')};
							temp.children("div").removeClass("upload-video")
							.find(".video-file-upload").hide()
							.siblings(".video-play").show();
							
						}
						else if(temp.hasClass('cell-date')){
							
							var date_input = temp.children('.date-input');
							var dateValue = date_input.val();
							date_input.remove();
							data[dataField] = dateValue;
							temp.text(dateValue);
							
						}
						else if(temp.hasClass('cell-date-range')){
							var start_time = temp.find('.date-input-start-time').val();
							var end_time = temp.find('.date-input-end-time').val();
							data[dataField] = [start_time,end_time];
							temp.text(start_time+"--"+end_time);
						}
						else if(temp.hasClass('cell-tag')){
							var nowSelect = temp.children('.tag-select option:selected');
							var selected=nowSelect.attr("value");
							var showSelect = nowSelect.text();
							data[dataField] = selected;
							temp.html(_createTag(showSelect));
						}
						else if(temp.hasClass('cell-content')){
							var cell_content_wrapper = temp.find(".cell-content-wrapper");
							cell_content_wrapper.removeClass("active");
							temp.removeClass("content-active");
							data[dataField] = cell_content_wrapper.html();
							$("#edit-save").unbind("click");
							temp.unbind('click');
						}
						else if(temp.hasClass('cell-flex-text')){
							var tv = temp.children('.text-input').val();
							data[dataField] = tv;
							temp.children('.text-input').remove();
							_createFlexibleCell(temp,tv);
							
						}
						else if(temp.attr('data-edit')=="false"){
							data[dataField] = temp.text();
						}
						else
						{
							var tv = temp.children('.text-input').val();
							data[dataField] = tv;
							temp.children('.text-input').remove();
							temp.text(tv);
						}
						
					}
					
				}
				
				
				
				
				
				var row = datatable.row($(this).parents("tr")[0]);
				if(row.data()){
					data['id'] = row.data().id;
				}
				
				options.saveAjax.success=function(){
					datatable.ajax.reload();
					alert("保存成功");
				}
				options.saveAjax.error=function(){
					datatable.ajax.reload();
					alert("网络问题!");
				}
				options.saveAjax.data=data;
				$.ajax(options.saveAjax);
				$(this).removeClass('save-btn');
				$(this).addClass('edit-btn');
				$(this).children().removeClass('fa-floppy-o');
				$(this).children().addClass('fa-pencil');
			});
			
			
			
			t.find("tbody").on('click','.delete-btn',function(){
				var row = datatable.row($(this).parents("tr")[0]);
				var index = row.index();
				if(!confirm("真的要删除记录吗？")){
					return;
				}
				var a = options.deleteAjax;
				if(!a.hasOwnProperty("data")){
					a.data={};
				}
				a.data.id=row.data().id;
				a.success=function(){
					datatable.row(index).remove().draw();
					datatable.ajax.reload();
					alert("删除成功！");
				};
				a.error=function(){
					datatable.ajax.reload();
					alert("网络问题!");
				}
				$.ajax(a);
				
			});
			
			t.find("tbody").on('click','.cancel',function(){
				$(this).parents("tr").remove();
			});
			
			
			t.find("tbody").on('click','.search-btn',function(){
				$(".search-row input").each(function(index){
					var value = $(this).val();
					if(!value){
						value="";
					}
					datatable.column(index).search(value);
				});
				datatable.draw();
			});
			
			//简写的search，因为给用户呈现的数据与实际的数据实际上是不一样的，所以有些数据类型还是要更改，才能传到后台去搜索
			$("#search").on('click',function(){
				if(!$(t.find("tbody tr").eq(0)).hasClass("search-row")){
					var rowNode = datatable.row.add(["1","1","1","1","1","1","1","1","1"]).node();
					t.find("tbody").prepend(rowNode);
					$(rowNode).addClass("search-row");
					$(rowNode).children().each(function(){
						var cell = $(this);
						if(!cell.hasClass("cell-img")&&!cell.hasClass("cell-video")&&!cell.hasClass("action")){
							cell.html("<input type='text' class='search-input' />");
						}
						if(cell.hasClass("action")){
							cell.html('<button type="button" class="btn btn-sm bg-green search-btn"><i class="fa fa-search"></i></button> <button type="button" class="btn btn-sm bg-green cancel"><i class="fa fa-close"></i></button>');
						}
					});
				}
			});
			
			
			
			//添加记录
			$("#add-row").on('click',function(){
				var rowNode = datatable.row.add(["1","1","1","1","1","1","1","1","1"]).node();
				t.find("tbody").prepend(rowNode);
				var oneRow = $(rowNode);
				oneRow.find(".delete-btn").removeClass("delete-btn").addClass("cancel");
				oneRow.find('.edit-btn').trigger('click');
				
			});
			
			
		});
				
			};
			
			
			//将像素值转换为整数值
		function deletePx(str){
				return parseInt(str.substr(0,str.length-2));
		}
		
		
		//生成video标签
		function _createVideo(poster,video_url){
			return '<video id="video-play" controls preload="auto" height="400"'
  								+'poster="'+poster+'">'
  								+'<source src="'+video_url+'" type="video/mp4" />'
    							+'<p class="vjs-no-js">'
      						+'To view this video please enable JavaScript, and consider upgrading to a web browser that'
      						+'<a href="http://videojs.com/html5-video-support/" target="_blank">supports HTML5 video</a>'
   							 +'</p>'
  						+'</video>'
		}
		
		
		//生成select元素
		function _createSelect(name,op){
			var lis="<select class='tag-select'>";
			var nowValue="";
			for(var i in op){
				if(op[i]=name){
					nowValue = i;
				}
				lis+='<option value="'+i+'">'+op[i]+'</option>';
			}
			lis+='</select>';
			$(lis).val(i);
			return lis;
		}
		
		//创建标签
		function _createTag(name){
			return '<span class="label label-success tag-content">'+name+'</span>';
		}	
			//创建灵活的单元格，字数过多显示...点击显示全部内容
			function _createFlexibleCell(temp,text){
				var etc='<span class="cell-text-etc">...</span>';
				var width = temp.width();
				var fontSize = deletePx(temp.css('font-size'));
				var fontNum = parseInt(width/fontSize);
				var str = text.replace('x','l');
				str = str.replace(/[\u4E00-\u9FA5\uF900-\uFA2D]/g,'xx');
				if(str.length>fontNum*2){
					var mainstr = str.substr(0,fontNum*2);
					//获取中文字体数目
					var chineseArray = mainstr.match(/xx/g);
					var chineseNum = 0;
					if(chineseArray){
						chineseNum=chineseArray.length;
					}
					//其他字符数目
					var otherNum = mainstr.length-chineseNum*2;
					var mainEle = text.substr(0,chineseNum-1+otherNum)+etc;
					var otherEle = '<span class="cell-text-other cell-text-hide">'+text.substring(chineseNum-2+otherNum)+'</span>';
					temp.html(mainEle+otherEle);
					temp.bind('click',function(){
						$(this).children('.cell-text-etc').toggleClass('cell-text-hide');
						$(this).children('.cell-text-other').toggleClass('cell-text-hide');
					});
				}
				else
				{
					temp.text(text);
				}
				
			}
			
			
			
})($,document,window,KindEditor)
		
		
		
		
		
		