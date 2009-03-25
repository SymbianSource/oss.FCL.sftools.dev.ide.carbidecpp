

function ArrayTest() {
}

ArrayTest.prototype.getLength = function(prop) {
	return properties[prop].length;
}


ArrayTest.prototype.setElementAt = function(prop, idx, val) {
	properties[prop][idx] = val;
}

ArrayTest.prototype.getElementAt = function(prop, idx) {
	return properties[prop][idx]
}

ArrayTest.prototype.setElementAtSub = function(prop, idx, sub, val) {
	properties[prop][idx][sub] = val;
	
	// don't crash, even though "sub" isn't a real property
	properties[prop][idx].sub = val;
}

ArrayTest.prototype.getElementAtSub = function(prop, idx, sub) {
	return properties[prop][idx][sub];
}

ArrayTest.prototype.resizeAndCheck = function(prop, idx, size) {
	properties[prop].length = size;
	return properties[prop].length;
}


