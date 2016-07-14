/*
* @Author: Administrator
* @Date:   2016-07-13 16:13:59
* @Last Modified by:   Administrator
* @Last Modified time: 2016-07-13 16:47:31
*/

'use strict';
;(function(window, document, $, undefined){
	$.fn.validate = function(passfn){
		var _this = this;
		var allValidate = _this.find(".form-struct .form-item");
		var targetObj = {};
		for(var i = 0; i<allValidate.length; i++) {
			var input = $(allValidate[i]);
			var id  = input.attr("id");
			var pattern = input.attr("data-validate");
			var required = input.attr("required");
			var minLength = parseInt(input.attr("data-minLength"));
			var maxLength = parseInt(input.attr("data-maxLength"));
			if(!pattern&&!required&&!minLength&&!maxLength) continue;
			var t  = {
				dom:input,
				pattern:pattern,
				required:required,
				minLength: minLength,
				maxLength: maxLength,
				value:"",
				isPass:false
			};

			targetObj[name] = t;
		}

		var Util={
			isPass: function(name){
				var msg = "";
				var t = targetObj[name];
				if(t.required) {
					if(!t.value) {
						t.isPass = false;
						msg = "该字段必填";
						return msg;
					}
					if(t.minLength) {
						if(t.minLength>t.value.length) {
							t.isPass = false;
							msg = "该字段的长度不能小于"+t.minLength;
							return msg;
						}
					}

					if(t.maxLength) {
						if(t.maxLength<t.value.length) {
							t.isPass = false;
							msg = "该字段的长度不能长于"+t.maxLength;
							return msg;
						}
					}

					if(t.pattern) {
						var p = new RegExp(t.pattern);
						if(!p.test(t.value)) {
							t.isPass = false;
							msg = t.desc.pattern;
							return msg;
						}
					}
				}
				t.isPass = true;
				return msg;
			},
			isAllPass: function(){
				var result = true;
				for(var k in targetObj) {
					var t = targetObj[k];
					if(!t.isPass) {
						result = false;
					}
				}
				return result;
			}
		};

		allValidate.on("input propertychange", function(e){
			var t = $(this);
			var id = t.attr("name");
			targetObj[name].value = t.val();
			if(!Util.isPass(name)&&Util.isAllPass()) {
				passfn(true);
			} else {
				passfn(false);
			}
		});

		/*allValidate.on("blur", function(){
			var t = $(this);
			var name = t.attr("name");
			targetObj[name].value = t.val();
			var msg = "";
			if(msg = Util.isPass(name)) {
				verifn(msg);
			}
		});*/
	};
})(window, document, jQuery);
