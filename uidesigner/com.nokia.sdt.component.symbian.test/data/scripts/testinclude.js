
include("library.js")

function Test() {
}

Test.prototype.getGlobal = function() {
	return libfunc();
}

