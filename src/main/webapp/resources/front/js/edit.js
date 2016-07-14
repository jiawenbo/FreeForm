/*
* @Author: Administrator
* @Date:   2016-07-13 12:06:31
* @Last Modified by:   Administrator
* @Last Modified time: 2016-07-13 23:13:22
*/

'use strict';
		$(function(){

				function fileReader(file,fn){
					if(window.FileReader){
						var reader = new FileReader();

						reader.onload = function(e){
							fn(e.target.result);
						};
						
						reader.readAsDataURL(file);
					} else {
						alert("该浏览器不支持文件读取功能");
					}
				}
				var designArea = $(".design-area").mCustomScrollbar({
					theme:"minimal-dark"
				}).find(".mCSB_container");
				var propertyArea = $(".properties").mCustomScrollbar({
					theme:"minimal-dark"
				}).find(".c-content");
				var h = location.href;
				var qPostion = h.indexOf("?");
				var h = h.substr(qPostion+1);
				var arr = h.split("&");
				var formID = arr[0].split("=")[1];
				var formObj = null;
				var FreeForm = function(){
					// 当前活跃的ID
					var all = {
						form: new Form(formObj, "dev", designArea),
						activeId: null,
						resize: new Resize()
						
					};
					//设置formUI
					// 组件区
					var components = {
						class:".component",
						OrdinaryInputComp:[$("#OrdinaryInputComp"), OrdinaryInput],
						TextAreaComp: [$("#TextAreaComp"), TextArea],
						RadioInputComp: [$("#RadioInputComp"), RadioInput],
						CheckboxInputComp: [$("#CheckboxInputComp"), CheckboxInput],
						SelectInputComp: [$("#SelectInputComp"), SelectInput],
						ImageEleComp: [$("#ImageEleComp"), ImageEle],
						UploadInputComp: [$("#UploadInputComp"), UploadInput],
						HTMLComp: [$("#HTMLComp"), HTML]
					};
					// 属性区
					var properties = {
						panel:".properties-panel",
						form:{
							// 解释:批量绑定属性面板中的输入，分别代表[类名, 监听事件, 执行的方法, 输入类型]
							name:[".form-name", "input", "setName", "input"]
						},
						ordinaryInput: {
							label:[".ordinary-input-label","input","setName","input"],
							select:[".ordinary-input-select","change","setType","select"],
							checkbox: [".ordinary-input-checkbox","change","setRequired","checkbox"],
							minLength: [".ordinary-input-minLength","input","setMinLength","input"],
							maxLength: [".ordinary-input-maxLength","input","setMaxLength","input"],
							placeholder:[".ordinary-input-placeholder","input","setPlaceHolder","input"],
							validate:[".ordinary-input-validate","input","setValidate","input"],
							rows:[".ordinary-input-rows","input","setRows","input"]
						},
						radioInput: {
							label: [".radio-input-label", "input", "setName", "input"],
							required:[".radio-input-required", "change", "setRequired", "checkbox"],
							options:[".radio-input-option", "input", "updateInput", "radioOptionGroup"],
							checked: [".radio-input-checked", "click", "setChecked", "radioCheckedGroup"]
						},
						checkboxInput: {
							label: [".checkbox-input-label", "input", "setName", "input"],
							required:[".checkbox-input-required", "change", "setRequired", "checkbox"],
							options:[".checkbox-input-option", "input", "updateInput", "checkboxOptionGroup"],
							checked: [".checkbox-input-checked", "click", "addChecked", "checkboxCheckedGroup"]
						},
						selectInput: {
							label:[".select-input-label", "input", "setName", "input"],
							required:[".select-input-required", "change", "setRequired", "checkbox"],
							options:[".select-input-option", "input", "updateInput", "selectOptionGroup"],
							checked: [".select-input-checked", "click", "setSelected", "selectCheckedGroup"]
						},
						html: {
							"content":[".html-content", "input", "setContent", "textarea"]
						},
						uploadInput: {
							label:[".upload-input-label", "input", "setName", "input"],
							required:[".upload-input-required", "change", "setRequired", "checkbox"]
						}
					};
					// 设计区
					var design = {
						devStruct: ".dev-struct"
					};
					var Util = {
						// 找到form当中的元素，找到对应的属性模板进行渲染
						renderProperties: function(id){
							var nowEle = id?all.form.getContent()[id]:all.form;
							propertyArea.html(
								tppl($("#"+nowEle.className).html(),nowEle));
						},
						// 防止属性区和组件区冒泡
						stopPropagation: function(){
								$(document).on('click', properties.panel, function(e){
									e.stopPropagation();
								});
								$(document).on('click', components.class, function(e){
									e.stopPropagation();
							});
						},

						judgeInput: function(item, obj, t){
							switch(item[3]){
								case "checkbox":
									obj[item[2]](t.prop("checked"));
									break;
								case "input":
									obj[item[2]](t.val());
									break;
								case "select":
									obj[item[2]](t.val());
									break;
								case "radioOptionGroup":
									obj[item[2]]($(t.parents()[1]).index(), t.val());
									break;
								case "radioCheckedGroup":
									obj[item[2]]($(t.parents()[1]).index());
									break;
								case "checkboxOptionGroup":
									obj[item[2]]($(t.parents()[1]).index(), t.val());
									break;
								case "checkboxCheckedGroup":
									obj[item[2]]($(t.parents()[1]).index(), t.prop("checked"));
									break;
								case "selectOptionGroup":
									obj[item[2]]($(t.parents()[1]).index(), t.val());
									break;
								case "selectCheckedGroup":
									obj[item[2]]($(t.parents()[1]).index());
									break;
								case "textarea":
									obj[item[2]](t.val());
									break;
								default:
									break;
							}
						},
						addElement: function(t, id){
							//反射执行方法
							var ele = new t[1]();
							if(id) {
								all.form.addElement(ele, id);
							} else {
								all.form.addElement(ele);
							}
							
							$(design.devStruct).removeClass("active");
							var dev = ele.getDom().find(".dev-struct").addClass("active");
							/*all.resize.addEle(ele.getDom(), function(w, h){
								dev.css({
									"height": (h+16)+"px",
									"margin-top":"-8px"
								});
							});*/
							all.activeId = ele.getId();
							Util.renderProperties(all.activeId);
						}

					};
					var bindEvent = function(){

						// 防止冒泡
						Util.stopPropagation();

						// 为组件绑定方法
						for(var k in components) {
							if(k!=="class") {
								var t = components[k];
								(function(t){
									t[0].on('click',function(){
										Util.addElement(t);
									});
								})(t);
							}
						}
						// 为属性面板绑定方法
						for(var k in properties) {
							var temp = properties[k];
							if(k!=="panel") {
								for(var x in temp) {
									var temp2 = temp[x];
									(function(document, temp2, all, k, Util){
										$(document).on(temp2[1], temp2[0], function(){
											var obj = all.form.getContent()[all.activeId];
											if(k === "form") obj = all.form;
											Util.judgeInput(temp2, obj, $(this));
											
										});
									})(document, temp2, all, k, Util);
								}
							}
						}
						// 为document添加监听，消除活跃的元素
						$(document).on('click', function(){
							$(".dev-struct").removeClass("active");
							all.activeId = null;
							Util.renderProperties(all.activeId);
						});
						// 为设计区的组件添加监听
						$(document).on('click', design.devStruct, function(e){
							e.stopPropagation();
							$(design.devStruct).removeClass("active");
							$(this).addClass("active");
							var p = $(this).parent();
							var t = p.attr("id");
							if(t!==all.activeId) {
								all.activeId = t;
								Util.renderProperties(t);
							}
						});

						// 设置拖动效果
						var dragObj = null;
						var comp = null;
						var startY = 0;
						$(document).on('dragstart',".form-item", function(e){
							dragObj = $(this);
							startY = e.pageY;
						});

						$(document).on('dragend',".form-item", function(e){
							console.log("end");
							startY = 0;
							dragObj = null;
						});

						$(document).on('dragenter',".form-item", function(e){
							e.preventDefault();
							$(e.target).addClass("drag-enter");
						});

						$(document).on('dragover',".form-item", function(e){
							e.preventDefault();
						});

						$(document).on('dragleave',".form-item", function(e){
							e.preventDefault();
							$(e.target).removeClass("drag-enter");
						});

						$(document).on('drop','.form-item', function(e){
							e.preventDefault();
							var id = $(this).attr("id");
							var dragId = null;
							if(dragObj) {
								dragId = dragObj.attr("id");
							}
							if(id!==dragId&&dragId!=null){
								if(e.pageY<startY){
									$(this).before(dragObj);
									all.form.insertBefore(dragId, id);
								}
								else {
									$(this).after(dragObj);
									all.form.insertAfter(dragId, id);
								}
							}
							if(comp) {
								var compId = comp.attr("id");
								Util.addElement(components[compId], id);
							}

							$(e.target).removeClass("drag-enter");
						});
						// 左侧组件拖动函数
						$(document).on('dragstart', '.component', function(e) {
							comp = $(this);
						});

						$(document).on('dragend', '.component', function(e) {
							comp = null;
						});

						$(document).on('dragover', '.form-content', function(e){
							e.preventDefault();
						});
						$(document).on('drop', '.form-content', function(e){
							e.preventDefault();
						});

						// 添加选项监听
						$(document).on('click', '.add-option-btn', function(){
							var _t = $(this);
							var _p = _t.parent();
							all.form.getContent()[all.activeId]
									.addInput(
										_p.index(), 
										_t.siblings(".col-sm-7").find("input").val());
							// 为面板添加
							var c = _p.clone();
							c.prop("checked", false);
							_p.after(c);
						});
						$(document).on('click', '.delete-option-btn', function(){
							var _t = $(this);
							var _p = _t.parent();
							if(_p.parent().children().length>=2) {
								all.form.getContent()[all.activeId].deleteInput(_p.index());
								// 删除该元素
								_p.remove();
							} else {
								alert("不能少于一个选项");
							}
							
						});

						$(document).on('change', '.upload-image', function(){
							fileReader($(this).prop("files")[0],function(r){
								all.form.getContent()[all.activeId].setSrc(r);
							});
						});



						$(document).on('click', '.add', function(){
							var nowId = $($(this).parents()[2]).attr("id");
							var ele = all.form.cloneElement(nowId);
							var dev = ele.getDom().find(".dev-struct");
							all.resize.addEle(ele.getDom(), function(w, h){
								dev.css({
									"height": (h+16)+"px",
									"margin-top":"-8px"
								});
							});
						});

						$(document).on('click', '.delete', function(e){
							e.stopPropagation();
							var _t = $(this);
							var nowId = $(_t.parents()[2]).attr("id");
							if(nowId===all.activeId) {
								all.activeId = null;
								Util.renderProperties(all.activeId);
							}
							all.form.deleteElement(nowId);
						});

						$(document).on('click', '#preview-btn', function(){
							$(".createArea").html(all.form.createHtml());
							$("#preview").modal();
						});
						
						$("#save-form-btn").on('click', function(){
							var d = JSON.stringify(all.form.echoJson());
							$.ajax({
								url:"saveform", 
								type:"POST", 
								data:{
									data: d,
									id: formID,
									name: all.form.name
								}
							})
							.done(function(data){
								if(data.message!=="ok")
									alert("保存失败");
								else {
									var date = new Date();
									$(".date").html("最后保存于"+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds());
								};
							})
							.fail(function(){
								alert("保存失败");
							});
							
						});
						
						$("#publish-btn").on('click', function(){
							$.ajax({
								url:"publish",
								type:"POST",
								data: {
									id:formID
								}
							}).done(function(data){
								if(data.message=="ok") {
									prompt("生成的链接为", data.url);
								} else{
									alert("发布失败");
								}
							}).fail(function(){
								alert("发布失败");
							});
						});
						
					};
					return {
						init: function(){
							bindEvent();
							Util.renderProperties(all.activeId);
						}
					}; 
				};
				
				$.ajax({
					type:"POST", 
					url:"getformcontent",
					data:{formId: formID}})
					.done(function(data){
						if(data.content) {
							formObj = JSON.parse(data.content);
						}
						FreeForm().init();
					})
					.fail(function(){
						alert("加载表单数据失败，请重新获取");
					});
				
				
			});

