
// this test uses only globals

function Test() {
}


Test.prototype.getName = function() {
	return properties["name"];
}

Test.prototype.setName = function(str) {
	properties["name"] = "_" + str;
}

Test.prototype.getSizeFormatted = function() {
	return properties["size"].x + "," + properties["size"].y;
}

Test.prototype.setSize = function(x,y) {
	properties["size"]["x"] = x;
	properties["size"]["y"] = y;
}
