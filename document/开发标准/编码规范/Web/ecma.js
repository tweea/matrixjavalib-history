1、
var com_matrix_AjaxUtil = {
	readEntry : function(entryNode) {
		var node = {
			name : "",
			value : ""
		};
		if (entryNode.childNodes[0].childNodes.length > 0) {
			node.name = entryNode.childNodes[0].childNodes[0].nodeValue;
		}
		if (entryNode.childNodes[1].childNodes.length > 0) {
			node.value = entryNode.childNodes[1].childNodes[0].nodeValue;
		}
		return node;
	},
	readEntrySet : function(entrySetNode) {
		var result = new Object();
		result.subentries = new Array();
		for ( var i = 0; i < entrySetNode.childNodes.length; i++) {
			var entry = entrySetNode.childNodes[i];
			if (entry.nodeName == "entry") {
				var v = this.readEntry(entry);
				if (v.name == "") {
					result["unnamed" + i] = v.value;
				} else {
					result[v.name] = v.value;
				}
			} else if (entry.nodeName == "entry-set") {
				var entrySet = this.readEntrySet(entry);
				result.subentries.push(entrySet);
				if (entry.getAttribute("name")) {
					result[entry.getAttribute("name")] = entrySet;
				}
			}
		}
		return result;
	}
};

2、
function com_matrix_system_showMessage(item) {
	var myWindow = new com_matrix_DOM_window(window);
	item = $(item);
	item.show();
	com_matrix_CSS_setProperty(item, "top", myWindow.getScrollY() + myWindow.getInnerHeight() / 2
		- item.clientHeight / 2);
	com_matrix_CSS_setProperty(item, "left", myWindow.getScrollX() + myWindow.getInnerWidth() / 2 - item.clientWidth
		/ 2);
}

3、
var com_matrix_DOM_window = Class.create();
com_matrix_DOM_window.prototype = {
	initialize : function(win) {
		this._win = win;
	},
	getInnerWidth : function() {
		if (com_matrix_agent.isIE) {
			return this._win.document.body.clientWidth;
		} else {
			return this._win.innerWidth;
		}
	},
	getInnerHeight : function() {
		if (com_matrix_agent.isIE) {
			return this._win.document.body.clientHeight;
		} else {
			return this._win.innerHeight;
		}
	},
	getPageXOffset : function() {
		if (com_matrix_agent.isIE) {
			return this._win.document.body.scrollLeft;
		} else {
			return this._win.pageXOffset;
		}
	},
	getPageYOffset : function() {
		if (com_matrix_agent.isIE) {
			return this._win.document.body.scrollTop;
		} else {
			return this._win.pageYOffset;
		}
	},
	getScrollX : function() {
		if (com_matrix_agent.isIE) {
			return this._win.document.body.scrollLeft;
		} else {
			return this._win.scrollX;
		}
	},
	getScrollY : function() {
		if (com_matrix_agent.isIE) {
			return this._win.document.body.scrollTop;
		} else {
			return this._win.scrollY;
		}
	}
};
