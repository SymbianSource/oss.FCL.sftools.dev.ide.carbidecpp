
// this test uses only globals

function Test() {
}

Test.prototype.getInstance = function() {
	return instance;
}

Test.prototype.getProperties = function() {
	return properties;
}

Test.prototype.getNamePlusFoo = function() {
	return properties["name"] + "foo";
}

Test.prototype.getSizeFormatted = function() {
	return properties["size"].x + "," + properties["size"].y;
}

