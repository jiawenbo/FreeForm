/*
* @Author: Administrator
* @Date:   2016-07-12 14:57:15
* @Last Modified by:   Administrator
* @Last Modified time: 2016-07-12 15:49:25
*/

'use strict';
;(function(window, document, undefined){
	var  Resize = function(){
		var _t = this;
		this.eles = [];
		setInterval(function(){
			judge(_t.eles);
		},250);
		function judge(t){
			for(var i=0; i<t.length; i++) {
				var _t = $(t[i].ele);
				if(_t.length>0) {
					var w = _t.width(),
						h = _t.height();
					if(w!=_t.data("width")
						||h!=_t.data("height")){
						_t.data("width", w);
						_t.data("height", h);
						t[i].fn(_t.width(),_t.height(), _t);
					} 
				} else {
					t.splice(i, 1);
				}
			}
		}

		this.init = function(t) {
			t.data("width", t.width())
			 .data("height", t.height());
		}
	};

	Resize.prototype.addEle = function(ele, fn){
		var obj = {
			ele: ele,
			fn:fn
		};
		this.eles.push(obj);
		this.init(ele);
	};

	window.Resize = Resize;

})(window, document);