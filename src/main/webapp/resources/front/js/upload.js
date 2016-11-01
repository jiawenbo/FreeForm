/*
* @Author: Administrator
* @Date:   2016-06-28 11:46:07
* @Last Modified by:   Administrator
* @Last Modified time: 2016-06-30 12:46:15
*/

/*
* 使用mutipart/form-data
* 完整的配置
    upload(input,{
    	// 上传格式
		fileFormat: ['image/gif','image/png','image/jpg','image/jpeg'],
		// 最小文件大小
		minFileSize: 100000,
		// 最大文件大小
		maxFileSize: 20000000,
		// 读取显示区域
		image: image,
		// 开始读取
		start: function(){},
		// 读取中
		progress: function(){},
		// 成功读取
		success: function(){},
		// 读取失败
		error: function(){}
	},{
		// 上传按钮
		uploadBtn: btn,
		// 上传的地址
		url: url,
		// 请求类型
		type: "POST",
		// 开始上传触发
		start: function(){},
		progress: function(){},
		success: function(){},
		error: function(){}
	}, {
		alert: alert,
		event: "click"
	});
*
*/
'use strict';
(function(document,window){
	if (XMLHttpRequest.prototype.sendAsBinary) return;
	XMLHttpRequest.prototype.sendAsBinary = function(datastr) {
	    function byteValue(x) {
	        return x.charCodeAt(0) & 0xff;
	    }
	    var ords = Array.prototype.map.call(datastr, byteValue);
	    var ui8a = new Uint8Array(ords);
	    this.send(ui8a.buffer);
	}
	var upload = function(input,readConfig,uploadConfig, settings){
		var touchEvent = 'touchstart';
		var messageFn = window.alert;
		var Dom = {
			wrap: function(wrap, bewrap){
				var bw  = typeof wrap === "string"
					?document.querySelector(bewrap)
					:bewrap;
				var parent = bw.parentNode;
				var w = typeof wrap === "string"
					?document.createElement(wrap)
					:wrap;
				parent.insertBefore(w, bw);
				w.appendChild(bw);
			}
		};
		var defaultOptions={
			type:"POST",
			maxFileSize:1048576,
		};

		if(uploadConfig) {
			if(uploadConfig.alert||uploadConfig.settings) {
				settings = uploadConfig;
				uploadConfig = null;
			}
		}

		if(settings) {
			if(settings.alert) {
				messageFn = settings.alert;
			}
			if(settings.event) {
				touchEvent = settings.event;
			}
		}

		//var form = document.createElement("form");
		//form.enctype = "multipart/form-data";
		//form.encoding = "multipart/form-data";
		//form.method = "POST";
		//Dom.wrap(form, input);
		input.addEventListener('change',function(e){
			changeEvent(input);
		});

		function buildMultiPartFileData(file, fileData, boundary){
			var dash = '--',
				crlf = '\r\n',
				str = '';
			str += (dash+boundary+crlf);
			str += "Content-Disposition: form-data; name=image";
			str += "; filename=\"" + file.name + "\"";
			str += crlf;
			str += "Content-Type: application/octet-stream";
			str += crlf+crlf;
			str += fileData;
			str += crlf;
			str += (dash+boundary+dash+crlf);
			return str;
		}



		//当前插件只支持单个文件上传
		function changeEvent(input){
			var file = input.files[0];
			if(!file){
				return;
			}
			if(readConfig.fileFormat){
				var hasType=false;
				var format = [];
				for(var i=0; i<readConfig.fileFormat.length; i++){
					var item = readConfig.fileFormat[i];
					format.push(item.split("/")[1]);
					if(file.type==item){
						hasType=true;
						break;
					}
				}
				if(!hasType){
					input.value="";
					messageFn("文件的格式支持"+format.join(","));
					return;
				}
			}

			if(readConfig.minFileSize) {
				if(file.size<readConfig.minFileSize){
					messageFn("文件大小不能小于"+readConfig.minFileSize/1024+"k");
					return;
				}
			}
			var maxFileSize = defaultOptions.maxFileSize;
			if(readConfig.maxFileSize){
				 maxFileSize = readConfig.maxFileSize;
			}

			if(maxFileSize<file.size){
				input.value="";
				messageFn("文件大小不能超过"+readConfig.maxFileSize/1024+"k");
				return;
			}

			if(readConfig.image){
				fileReader(readConfig.image, file);
			} 

		}
		
		function uploadFile(input,file, img, e){
			var xhr = new XMLHttpRequest(),
			boundary = "------multipartformboundary" + (new Date).getTime(),
			upload = xhr.upload,
			data = buildMultiPartFileData(file, e.target.result, boundary);

			xhr.addEventListener('readystatechange', function(event){
				if(xhr.readyState === XMLHttpRequest.OPENED) {
					uploadConfig.start(event, img);
				}
				if(xhr.readyState === XMLHttpRequest.DONE) {
					if((xhr.status >= 200 && xhr.status < 300) || xhr.status == 304) {
						uploadConfig.success(xhr.responseText, img);
					} else {
						uploadConfig.error(event, img);
					}
				}

			});
			//上传过程中
			upload.addEventListener('progress',function(event){
				if(uploadConfig.progress){
					uploadConfig.progress(event, img);
				}
			},false);
			xhr.open(uploadConfig.type?
						uploadConfig.type:
						defaultOptions.type,
						uploadConfig.url, true);
			xhr.setRequestHeader('Content-Type', 'multipart/form-data; boundary='+boundary);
			xhr.sendAsBinary(data);
		}
		
		
		function fileReader(img,file){
			if(window.FileReader){
				var reader = new FileReader();

				reader.onloadstart = function(e){
					if(readConfig.start) {
						readConfig.start(e, img);
					}
				};
				
				reader.onload = function(e){
					if(readConfig.success) {
						readConfig.success(e, img);
						if(uploadConfig){
							// 如果存在上传按钮
							if(uploadConfig.uploadBtn) {
								uploadConfig.uploadBtn.addEventListener(touchEvent,function(){
									uploadFile(input, file, readConfig.image, e);
								});
							} else {
								uploadFile(input, file, readConfig.image, e);
							}
							
						}
					}
				};

				reader.onprogress = function(e){
					if(readConfig.progress) {
						readConfig.progress(e, img);
					}
				};

				reader.onerror = function(e){
					if(readConfig.error) {
						readConfig.error(e, img);
					}
				}

				
				reader.readAsBinaryString(file);
			} else {
				messageFn("该浏览器不支持文件读取功能");
			}
		}
		
	};
	window.upload = upload;
	
})(document,window);
