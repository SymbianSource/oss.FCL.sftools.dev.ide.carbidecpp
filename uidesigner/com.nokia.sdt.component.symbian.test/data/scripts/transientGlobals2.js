

function Proto () {
	this.counter = 1;
	this.globalString = "InstanceVar";
}

Proto.prototype = new Object()

function extra() {
	anotherglobal = 3;
}

Proto.prototype.myfunc = function() {
	var oo = inst;
	
	myglobal = 2;
	extra();
	
	this.foo = 3;
	
	return this.globalString + (this.counter++);
}
