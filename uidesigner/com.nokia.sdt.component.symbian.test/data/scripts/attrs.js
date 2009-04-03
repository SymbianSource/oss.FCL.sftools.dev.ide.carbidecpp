

function Test() {
}

Test.prototype.getAttr = function(key) {
	return attributes[key]
}

Test.prototype.getAttrParam = function(attrs, key) {
	return attrs[key]
}
