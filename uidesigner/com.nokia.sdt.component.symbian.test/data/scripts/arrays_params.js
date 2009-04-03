

function ArrayTest() {
}

ArrayTest.prototype.getLength = function(properties, prop) {
	return properties[prop].length;
}


ArrayTest.prototype.setElementAt = function(properties, prop, idx, val) {
	properties[prop][idx] = val;
}

ArrayTest.prototype.getElementAt = function(properties, prop, idx) {
	return properties[prop][idx]
}

ArrayTest.prototype.setElementAtSub = function(properties, prop, idx, sub, val) {
	properties[prop][idx][sub] = val;
	
	// don't crash, even though "sub" isn't a real property
	properties[prop][idx].sub = val;
}

ArrayTest.prototype.getElementAtSub = function(properties, prop, idx, sub) {
	return properties[prop][idx][sub];
}

ArrayTest.prototype.resizeAndCheck = function(properties, prop, size) {
	properties[prop].length = size;
	return properties[prop].length;
}
