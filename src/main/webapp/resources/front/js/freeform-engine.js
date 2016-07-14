/*
* @Author: Administrator
* @Date:   2016-07-01 19:52:18
* @Last Modified by:   Administrator
* @Last Modified time: 2016-07-14 08:34:45
*/

'use strict';
	var freeform = {};
	freeform.Utils = {
		// 合并数组
		merge: function(target, arr, isUnique){
			if(isUnique) {
				for(var i=0; i<arr.length; i++) {
					var t = arr[i];
					var isSame = false;
					for(var j=0; j<target.length; j++) {
						if(t==target[j]) {
							isSame = true;
							break;
						}
					}
					if(!isSame) {
						target.push(t);
					}
				}
			} else {
				Array.prototype.push.apply(target, arr);
			}
		},
		insert: function(arr, value, position){
			if(position===-1) {
				position = arr.length-1;
			}
			arr.splice(position+1, 0, value);
		},
		delete: function(arr, value){
			var index = arr.indexOf(value);
			if(index>-1) {
				arr.splice(index, 1);
			}
		},
		before: function(arr, now, target){
			var nowIndex = arr.indexOf(now);
			if(nowIndex>-1) {
				arr.splice(nowIndex, 1);
			}
			var targetIndex = arr.indexOf(target);
			arr.splice(targetIndex, 0, now);
		},
		after:function(arr,now, target){
			var nowIndex = arr.indexOf(now);
			if(nowIndex>-1) {
				arr.splice(nowIndex, 1);
			}
			var targetIndex = arr.indexOf(target);
			arr.splice(targetIndex+1, 0, now);
		},

		// 得到唯一标识，时间戳+三位随机数字
		getIdentity: function(){
			return new Date().getTime()+""+parseInt((Math.random()*900+100));
		},
		// 得到时间戳
		getTimestamp: function(){
			return new Date().getTime();
		},
		//将jquery对象转换为html字符串
		jQueryObjectToHtmlStr:function(j){
			var obj = j.clone();
			obj.removeAttr("draggable");
			obj.children('.dev-struct').remove();
			return $("<div></div>").append(obj).html();
		},
		isFunction: function(fn){
			return Object.prototype.toString.call(fn)=== '[object Function]';
		}
	};


	// 基本元素
	function Element(){
		this.dom = "";
		this.nameDom = "";
		this.contentDom = "";
		this.label = "标签";
		this.type = "element";
		this.css = "";
		this.id = "";
		this.addId = function(id){
			if(!id) {
				this.id = freeform.Utils.getIdentity();
			} else {
				this.id = id;
			}
			this.dom.attr("id", this.id);
		};
		this.init = function(id){
			this.addId(id);
			this.dom.append(this.nameDom).append(this.contentDom);
		};
	}
	Element.prototype.getId = function(){
		return this.id;
	};

	Element.prototype.setCss = function(){

	};
	Element.prototype.getDom = function(){
		return this.dom;
	};
	Element.prototype.clone = function(){
		var cloneObj = $.extend(true, {}, this);
		cloneObj.dom = cloneObj.dom.clone();
		cloneObj.dom.html("");
		cloneObj.nameDom = cloneObj.nameDom.clone();
		cloneObj.contentDom = cloneObj.contentDom.clone();
		if(cloneObj.initValues) {
			cloneObj.contentDom.html("");
			cloneObj.initValues();
		}
		cloneObj.init();
		return cloneObj;
	};


	Element.prototype.toString = function(){
		return freeform.Utils.jQueryObjectToHtmlStr(this.dom);
	};

	// 普通输入框
	function OrdinaryInput(obj) {
		this.className = "OrdinaryInput";
		this.typeOption = [{
				text:"文本",
				type:"text",
				validate:''
			},{
				text:"数字",
				type:"number",
				validate:'^0|[1-9]\d*$'
			},{
				text:"密码",
				type:"password",
				validate:''
			},{
				text:"手机号码",
				type:"tel",
				validate:'^1[3456789]\d{9}'
			},{
				text:"电子邮箱",
				type:"email",
				validate:'^\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}$'
			},{
				text:"时间（时分秒）",
				type:"time",
				validate:'',
			},{
				text:"日期（年月日）",
				type:"date",
				validate:''
			},{
				text:"链接",
				type:'url',
				validate:'^((https|http|ftp|rtsp|mms)?:\/\/)[^\s]+'
			}];
		this.addProps = function(){
			this.contentDom.attr("type", this.type)
						.attr("name", this.name)
						.attr("placeholder", this.placeholder);
			this.dom.attr("data-validate", this.validate)
					.attr("data-minLength", this.minLength)
					.attr("data-maxLength", this.maxLength);
		};
		this.domTmpl = "<div class='form-item'></div>";
		this.nameTmpl = "<label></label>";
		this.contentTmpl = "<input class='form-control'>";

		if(!obj) {
			obj = {};
			obj.label = "文本框";
			obj.type = "text";
			obj.name = "文本框";
			obj.validate = "";
			obj.placeholder = "文本框";
			obj.minLength = "0";
			obj.maxLength = "100";
			obj.required = false;
		}
		this.initProperties(obj);
	}

	OrdinaryInput.prototype = new Element();

	OrdinaryInput.prototype.initProperties = function(obj){
		this.label = obj.label;
		this.type = obj.type;
		this.name = obj.name;
		this.validate = obj.validate;
		this.placeholder = obj.placeholder;
		this.minLength = obj.minLength;
		this.maxLength = obj.maxLength;
		this.required = obj.required;
		this.dom = $(this.domTmpl);
		if(obj.required) this.dom.attr("required", "required");
		this.nameDom = $(this.nameTmpl).text(this.name);
		this.contentDom = $(this.contentTmpl);
		this.addProps();
		this.init(obj.id);

	};

	OrdinaryInput.prototype.setLabel = function(label){
		this.setName(label);
	};
	OrdinaryInput.prototype.setName = function(name) {
		this.name = name;
		this.label = name;
		this.nameDom.text(name);
		this.contentDom.attr("name", name);
	};
	OrdinaryInput.prototype.setType = function(type){
		this.type = type;
		this.contentDom.attr("type", type);
	};
	OrdinaryInput.prototype.setMinLength = function(num) {
		this.minLength = num;
		this.dom.attr("data-minLength", num);
	};
	OrdinaryInput.prototype.setMaxLength = function(num) {
		this.maxLength = num;
		this.dom.attr("data-maxLength", num);
	};
	OrdinaryInput.prototype.setPlaceHolder = function(text) {
		this.placeholder = text;
		this.contentDom.attr("placeholder", text);
	};
	OrdinaryInput.prototype.setRequired = function(required) {
		this.required = required;
		if(required) {
			this.dom.attr("required","required");
		} else {
			this.dom.removeAttr("required");
		}
	};
	OrdinaryInput.prototype.setValidate = function(pattern) {
		this.validate = pattern;
		this.dom.attr("data-validate", pattern);
	}


	OrdinaryInput.prototype.echoJson = function(){
		var obj = {};
		obj.id = this.id;
		obj.label = this.label;
		obj.className = this.className;
		obj.type = this.type;
		obj.name = this.name;
		obj.validate = this.validate;
		obj.placeholder = this.placeholder;
		obj.minLength = this.minLength;
		obj.maxLength = this.maxLength;
		obj.required = this.required;
		return obj;
	};

	OrdinaryInput.prototype.createObj = function(obj){
		obj.prototype = OrdinaryInput.prototype;
		obj.dom = $(obj.dom);
		obj.nameDom = $(obj.nameDom);
		obj.contentDom = $(obj.contentDom);
	};


	// 图片
	function ImageEle(obj) {
		this.className = "ImageEle";
		this.nameTmpl = "<span></span>";
		this.contentTmpl = "<img class='img-thumbnail'>";
		this.domTmpl = "<div class='form-item'></div>";
		if(!obj) {
			obj = {};
			obj.src = "";
		}
		this.initProperties(obj);
	}
	ImageEle.prototype = new Element();
	ImageEle.prototype.initProperties = function(obj){
		this.src = obj.src;
		this.dom = $(this.domTmpl);
		this.nameDom = $(this.nameTmpl);
		this.contentDom = $(this.contentTmpl)
						.attr("src", this.src);
		this.init(obj.id);
	};

	ImageEle.prototype.setSrc = function(src){
		this.src = src;
		this.contentDom.attr("src", src);
	};

	ImageEle.prototype.echoJson = function() {
		var obj = {};
		obj.className = this.className;
		obj.src = this.src;
		obj.id = this.id;
		return obj;
	}


	function GroupInput() {
		this.label = "组输入框";
		this.type = "groupinput";
		this.name = "组输入框";
		this.required = false;
		this.itemFn = function(){};
		this.cleanChecked = function(){
			for(var i=0; i<this.values.length; i++) {
				var t = this.values[i];
				t.checked = false;
			}
		};
		this.initValuesObj = function(){
			var result = [];
			for(var i=0; i<3; i++) {
				result.push({
					dom:"",
					value:"选项"+(i+1),
					checked: false
				});
			}

			return result;
		};
		this.initValues = function(){
			for(var i=0; i<this.values.length; i++) {
				this.contentDom.append(this.values[i].dom = 
										$(this.itemFn(this.values[i].value)));
			}
		};
	}

	GroupInput.prototype = new Element();

	GroupInput.prototype.echoJson = function(){
		var obj = {};
		obj.label = this.label;
		obj.className = this.className;
		obj.id = this.id;
		obj.name = this.name;
		obj.values = [];
		for(var i=0; i<this.values.length; i++) {
			var t = $.extend({},this.values[i]);
			t.dom = "";
			obj.values.push(t);
		} 
		obj.required = this.required;
		return obj;
	};

	GroupInput.prototype.setName = function(label) {
		this.label = label;
		this.nameDom.text(label); 
	};
	GroupInput.prototype.setRequired = function(required) {
		this.required = required;
		this.dom.attr("required", required);
	};
	GroupInput.prototype.addInput = function(position, value){
		var c = this.values[position].dom.clone();
		c.prop("checked", false);
		var obj = {
			dom: c,
			value: value,
			checked:false
		};
		freeform.Utils.insert(this.values, obj, position);
		$(this.contentDom.children()[position]).after(c);
	};

	GroupInput.prototype.deleteInput = function(position){
		this.values.splice(position, 1);
		$(this.contentDom.children()[position]).remove();
	};

	GroupInput.prototype.updateInput = function(num, value) {
		this.values[num].value = value;
		this.values[num].dom.find("span").text(value);
	};

	// 单选框
	function RadioInput(obj) {
		this.className = "RadioInput";
		this.type = "radio";
		this.domTmpl = "<div class=\"form-item\"></div>";
		this.nameTmpl = "<label></label>";
		this.contentTmpl = "<div></div>";
		this.itemFn = function(value){
			return "<div class='radio'><label><input name=\""
					+this.name+"\" type=\""
					+this.type+"\"><span>"
					+value+"<span></label></div>"
		};

		if(!obj) {
			obj = {};
			obj.label = "单选按钮";
			obj.name = "radio-"+freeform.Utils.getTimestamp();
			obj.values = this.initValuesObj();
			obj.required = false;
		}

		this.initProperties(obj);
	}
	RadioInput.prototype = new GroupInput();


	RadioInput.prototype.initProperties = function(obj) {
		this.label = obj.label;
		this.name = obj.name;
		this.values = obj.values;
		this.required = obj.required;
		this.dom = $(this.domTmpl);
		if(obj.required) this.dom.attr("required", "required");
		this.nameDom = $(this.nameTmpl).text(this.label);
		this.contentDom = $(this.contentTmpl);
		for(var i=0; i<this.values.length; i++) {
			this.contentDom.append(this.values[i].dom = 
									$(this.itemFn(this.values[i].value)));
		}
		this.init(obj.id);
	}

	RadioInput.prototype.setChecked = function(checked) {
		this.cleanChecked();
		this.values[checked].checked = true;
		this.contentDom.find("input")[checked].checked = true;
	};

	// 多选框
	function CheckboxInput(obj) {
		// 固定部分
		this.className = "CheckboxInput";
		this.type = "checkbox";
		this.domTmpl = "<div class=\"form-item\"></div>";
		this.nameTmpl = "<label></label>";
		this.contentTmpl = "<div></div>";
		this.itemFn = function(value){
			return "<div class='checkbox'><label><input name=\""
					+this.name+"\" type=\""
					+this.type+"\"><span>"
					+value+"<span></label></div>"
		};

		if(!obj) {
			obj = {};
			obj.label = "多选按钮";
			obj.name = "多选按钮";
			obj.required = false;
			obj.values = this.initValuesObj();
		}
		this.initProperties(obj);
	}

	CheckboxInput.prototype = new GroupInput();

	CheckboxInput.prototype.initProperties = function(obj){
		this.label = obj.label;
		this.name = obj.name;
		this.values = obj.values;
		this.required = obj.required;
		this.dom = $(this.domTmpl);
		if(obj.required) this.dom.attr("required", "required");
		this.nameDom = $(this.nameTmpl)
						.text(this.label);
		this.contentDom = $(this.contentTmpl);
		for(var i=0; i<this.values.length; i++) {
			this.contentDom.append(this.values[i].dom = 
					$(this.itemFn(this.values[i].value)));
		}
		this.init(obj.id);
	};

	CheckboxInput.prototype.addChecked = function(index, value) {
		this.values[index].checked = value;
		this.values[index].dom.find("input:checkbox").prop("checked",value);
	};

	// 下拉框
	function SelectInput(obj) {
		// 固定部分
		this.className = "SelectInput";
		this.type = "select";
		this.domTmpl = "<div class='form-item'></div>";
		this.nameTmpl = "<label></label>";
		this.contentTmpl = "<select class=\"form-control\"></select>";
		this.itemFn = function(value){
			return "<option value=\""+value+"\">"+value+"</option>"
		};

		if(!obj) {
			obj = {};
			obj.label = "下拉框";
			obj.name = "下拉框";
			obj.required = false;
			obj.values = this.initValuesObj();
		}

		this.initProperties(obj);
	}

	SelectInput.prototype = new GroupInput();

	SelectInput.prototype.initProperties = function(obj){
		this.label = obj.label;
		this.name = obj.name;
		this.values = obj.values;
		this.required = obj.required;
		this.dom = $(this.domTmpl);
		if(obj.required) this.dom.attr("required", "required");
		this.nameDom = $(this.nameTmpl).text(this.label);
		this.contentDom = $(this.contentTmpl);
		for(var i=0; i<this.values.length; i++) {
			this.contentDom.append(
				this.values[i].dom = 
					$(this.itemFn(this.values[i].value)));
		}
		this.init(obj.id);
	};

	SelectInput.prototype.setSelected = function(item){
		if(typeof item === "number") {
			this.cleanChecked();
			this.values[item].checked = true;
			this.values[item].dom.prop("selected", true);
		}
	};

	SelectInput.prototype.updateInput = function(num, value) {
		this.values[num].value = value;
		this.values[num].dom.text(value);
	};

	// 上传组件
	function UploadInput (obj) {
		// 固定部分
		this.type = "file";
		this.className = "UploadInput";
		this.domTmpl = "<div class='form-item'></div>";
		this.nameTmpl = "<label></label>";
		this.contentTmpl = "<input>";

		if(!obj) {
			obj = {};
			obj.label = "上传文件";
			obj.required = false;
		}
		this.initProperties(obj);
	}

	UploadInput.prototype = new Element();

	UploadInput.prototype.echoJson = function(){
		var obj = {};
		obj.label = this.label;
		obj.className = this.className;
		obj.id = this.id;
		obj.name = this.name;
		obj.required = this.required;
		return obj;
	};

	UploadInput.prototype.initProperties = function(obj){
		this.label = obj.label;
		this.name = obj.name;
		this.accept = obj.accept;
		this.required = obj.required;
		this.dom = $(this.domTmpl);
		if(obj.required) this.dom.attr("required", "required");
		this.nameDom = $(this.nameTmpl).text(this.label);
		this.contentDom = $(this.contentTmpl)
						.attr("type", this.type)
						.attr("name", this.name);
		this.init(obj.id);
	};

	UploadInput.prototype.setName = function(label){
		this.label = label;
		this.nameDom.text(label);
	};

	UploadInput.prototype.setRequired = function(required) {
		this.required = required;
		if(this.required) {
			this.dom.attr("required", "required");
		} else {
			this.dom.removeAttr("required");
		}

	};
	UploadInput.prototype.setAccept = function(suffix){
		freeform.Utils.merge(this.accept, [suffix], true);
	};

	function HTML(obj) {
		// 固定部分
		this.className = "HTML";
		this.type = "html";
		this.domTmpl = "<div class='form-item'></div>";
		this.nameTmpl = "<span></span>";
		this.contentTmpl = "<p></p>";

		if(!obj) {
			obj = {};
			obj.content = "HTML内容";
		}
		this.initProperties(obj);
	}
	HTML.prototype = new Element();
	HTML.prototype.echoJson = function(){
		var obj = {};
		obj.className = this.className;
		obj.content = this.content;
		return obj;
	};
	HTML.prototype.setContent = function(html) {
		this.content = html;
		this.contentDom.html(html);
	};

	HTML.prototype.initProperties = function(obj){
		this.content = obj.content;
		this.dom = $(this.domTmpl);
		if(obj.required) this.dom.attr("required", "required");
		this.nameDom = $(this.nameTmpl);
		this.contentDom = $(this.contentTmpl).html(this.content);
		this.init(obj.id);
	};

	function TextArea(obj) {
		this.className = "TextArea";
		this.nameTmpl = "<label></label>";
		this.contentTmpl = "<textarea class='form-control'></textarea>";
		this.domTmpl = "<div class='form-item'></div>";
		this.type = "textarea";
		if(!obj) {
			obj = {};
			obj.label = "多行输入框";
			obj.placeholder = "请输入";
			obj.rows = 3;
			obj.cols = 20;
			obj.required = false;
		}
		this.initProperties(obj);
	}

	TextArea.prototype = new OrdinaryInput();

	TextArea.prototype.echoJson = function(){
		var obj = {};
		obj.label = this.label;
		obj.className = this.className;
		obj.id = this.id;
		obj.placeholder = this.placeholder;
		obj.rows = this.rows;
		obj.required = this.required;
		return obj;
	};

	TextArea.prototype.initProperties = function(obj){
		this.label = obj.label;
		this.placeholder = obj.placeholder;
		this.rows = obj.rows;
		this.cols = obj.cols;
		this.required = obj.required;
		this.nameDom = $(this.nameTmpl).text(this.label);
		this.contentDom = $(this.contentTmpl)
			.attr("rows", obj.rows)
			.attr("cols", obj.cols);
		this.addProps();
		this.dom = $(this.domTmpl);
		if(obj.required) this.dom.attr("required", "required");
		this.init(obj.id);
	};

	TextArea.prototype.setRows = function(rows){
		this.rows = rows;
		this.contentDom.attr("rows", rows);
	};

	function Form(obj,pattern,area) {
		// 固定部分
		this.domTmpl = "<form class='freeform'></form>";
		this.area = area;
		this.nameTmpl = "<div class='form-name'><h3></h3></div>";
		this.contentTmpl = "<div class='form-content'><div class='content-empty'>内容为空</div></div>";
		this.className = "Form";
		this.pattern = pattern;
		if(!obj) {
			obj = {};
			obj.name = "表单名称";
			obj.css = "";
			obj.content = {};
			obj.index = [];
		}
		// 参数化部分
		this.init(obj);
	}

	// 初始化dom
	Form.prototype.init = function(obj){
		var _t = this;
		this.name = obj.name;
		this.css = obj.css;
		this.content = obj.content;
		this.index = obj.index;
		this.nameDom = $(this.nameTmpl).find("h3").text(this.name);
		this.contentDom = $(this.contentTmpl);
		this.dom = $(this.domTmpl).attr("id",this.id).attr("method","POST");
		// 初始化添加表单名
		this.dom.append(this.nameDom);
		this.dom.append(this.contentDom);
		this.area.html(this.dom);
		// 初始化添加内容框架
		for(var i=0; i<this.index.length; i++) {
			var id = this.index[i];
			var target = this.content[id];
			switch(target.className){
				case "OrdinaryInput":
					_t.addElement(new OrdinaryInput(target));
					break;
				case "SelectInput":
					_t.addElement(new SelectInput(target));
					break;
				case "RadioInput":
					_t.addElement(new RadioInput(target));
					break;
				case "CheckboxInput":
					_t.addElement(new CheckboxInput(target));
					break;
				case "TextArea":
					_t.addElement(new TextArea(target));
					break;
				case "ImgEle":
					_t.addElement(new ImageEle(target));
					break;
				case "UploadInput":
					_t.addElement(new UploadInput(target));
					break;
				case "HTML":
					_t.addElement(new HTML(target));
					break;
				default:
					break;
			}
		}
	};

	Form.prototype.toString = function(){
		var obj = this.dom.clone();
		obj.find(".content-empty").remove();
		obj.find(".dev-struct").remove();
		obj.find(".form-item").removeAttr("draggable");
		return $("<div></div>").append(obj).html();
	};

	Form.prototype.getContent = function(){
		return this.content;
	};
	Form.prototype.getDom = function(){
		return this.dom;
	};

	Form.prototype.setName = function(name){
		this.name = name;
		this.nameDom.text(name);
	};

	Form.prototype.insertBefore = function(id, targetid){
		freeform.Utils.before(this.index, id, targetid);
	};

	Form.prototype.insertAfter = function(id, targetid){
		freeform.Utils.after(this.index, id, targetid);
	};

	Form.prototype.deleteElement = function(id){
		freeform.Utils.delete(this.index, id);
		this.content[id].dom.remove();
		delete this.content[id];
		if(this.index.length==0) {
			$(".content-empty").show();
		}
	};

	Form.prototype.cloneElement = function(id) {
		var target =  this.content[id];
		var cloneObj = target.clone();
		this.insertAfter(cloneObj.id, id);
		this.content[cloneObj.id] = cloneObj;
		this.contentDom.find("#"+id).after(cloneObj.getDom());
		if(this.pattern === "dev") {
			var h = cloneObj.dom.height();
			cloneObj.dom.attr("draggable", "true");
			var devStrut = $("<div class='dev-struct'><div class='operate'>"
				+"<div class='add'>+</div><div class='delete'>-</div>"
				+"</div></div>")
				.css({
					"height": (h*1.2)+"px",
					"margin-top":-(h*0.1)+"px"
				});
			cloneObj.dom.append(devStrut);
		}
		return cloneObj;
	};

	Form.prototype.addElement = function(ele, id){
		if(ele instanceof Element) {
			this.content[ele.id] = ele;
			if(id) {
				this.insertAfter(ele.id, id);
				this.contentDom.find("#"+id).after(ele.getDom());
			} else {
				if(this.index.indexOf(ele.id)<0) {
					this.index.push(ele.id);
				}
				this.contentDom.append(ele.getDom());
			}
			if(this.pattern === "dev") {
				this.resize = new Resize();
				var h = ele.dom.height();
				ele.dom.attr("draggable", "true");
				var devStrut = $("<div class='dev-struct'><div class='operate'>"
					+"<div class='add'>+</div><div class='delete'>-</div>"
					+"</div></div>")
					.css({
						"height": (h*1.2)+"px",
						"margin-top":-(h*0.1)+"px"
					});
				ele.dom.append(devStrut);
				this.resize.addEle(ele.getDom(), function(w, h, t){
					t.find(".dev-struct").css({
						"height": (h+16)+"px",
						"margin-top":"-8px"
					});
				});
			}
			if($(".content-empty").length>=0) {
				$(".content-empty").hide();
			}
		}
	};

	Form.prototype.echoJson = function(){
		var obj = {};
		obj.index = this.index;
		obj.className = this.className;
		obj.content = {};
		obj.name = this.name;
		obj.css = this.css;
		for(var k in this.content) {
			obj.content[k] = this.content[k].echoJson();
		}
		return obj;
	};

	Form.prototype.createHtml = function(){
		return this.toString();
	};



