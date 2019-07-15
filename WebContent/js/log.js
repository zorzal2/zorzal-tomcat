if(typeof Pragma == 'undefined')Pragma={};
if(!Pragma.log)Pragma.log={};

Pragma.log.Log = {
	dirtyView: false,
	messages: [],
	_window: null,
	raiseOnErrorsOnly: false,
	blindMode: false,
	_basePrint: function(message){
		this.messages.push('['+printTime(new Date())+'] '+message);
		this.dirtyView = true;
	},
	refreshView: function() {
		if(this.blindMode) return;
		var self=this;
		setTimeout(
			function() {
				self.show()
			}, 1);
	},
	print: function(message){
		this._basePrint(escapeHTML(message));
		if(!this.raiseOnErrorsOnly)this.refreshView();
	},
	printError: function(message){
		this._basePrint('<span style="color: red">'+escapeHTML(message)+'</span>');
		this.refreshView();
	},
	clear: function(){
		this.messages=[];
		this.dirtyView=true;
		this.refreshView();
	},
	show: function(){
		if(!this.dirtyView) return;
		if(!this._window || this._window.closed) {
			this._window = window.open("", "log", 'width=750,height=200,resizable=yes,scrollbars=yes') ;
		}
		this._window.document.write('<html><head><title>Log console</title></head><body><small>'+this.messages.join('<br/>')+'<'+'/small>'+
									((this.messages.length==0)?'':('<br/><a href="javascript: document.clearScreen()">Clear<'+'/a><'+'/body><'+'/html>')));
		this._window.document.close('<html><head><title>Log console</title></head><body><'+'/body><'+'/html>');
		var self = this;
		this._window.document.clearScreen = function() {
			//self._window.document.write()
			//self._window.document.close();
			//self.messages=[];
			self.clear();
		};
		this._window.scrollTo(0, 10000000);
		this._window.focus();
		this.dirtyView = false;
	}
}
Pragma.log.BlindLog = {
	refreshView: function() {},
	print: function(message){},
	printError: function(message){},
	clear: function(){},
	show: function(){}
}