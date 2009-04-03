

function Test() {
}

Test.prototype.getParent = function(inst) {
	return inst.parent;
}

Test.prototype.getChild0 = function(inst) {
	return inst.children[0];
}

Test.prototype.getChild1 = function(kids) {
	return kids[0];
}

