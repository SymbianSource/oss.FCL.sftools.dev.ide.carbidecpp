
include("lib/libraryincl.js")

function Test() {
}

Test.prototype.getGlobal = function() {
	return libfunc();
}

