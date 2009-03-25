
function Test() {
}


Test.prototype.getName = function(inst) {
	return inst.properties["name"];
}

Test.prototype.setName = function(inst, str) {
	inst.properties["name"] = "_" + str;
}

Test.prototype.getSizeFormatted = function(props) {
	return props["size"].x + "," + props["size"].y;
}

Test.prototype.setSize = function(props, x,y) {
	props["size"]["x"] = x;
	props["size"]["y"] = y;
}
