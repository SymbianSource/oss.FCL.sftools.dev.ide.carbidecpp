
// this test gets everything through strange accessors methods

function Test() {
}

Test.prototype.getInstance = function() {
	return instance;
}

Test.prototype.getProperties = function() {
	return instance != undefined ? instance.properties : undefined;
}

Test.prototype.getNamePlusFoo = function() {
	props = instance["properties"]
	return props["name"] + "foo";
}

Test.prototype.getSizeFormatted = function() {
	size = instance.properties.size
	return size.x + "," + size["y"];
}

