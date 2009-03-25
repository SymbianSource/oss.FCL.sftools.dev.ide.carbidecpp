
function Base() {

}

Base.prototype = new Object()

Base.prototype.shared = function() {
	return "Base-Shared-"+form;
}

Base.prototype.act = function() {
	return "Base-Act-"+form;
}


