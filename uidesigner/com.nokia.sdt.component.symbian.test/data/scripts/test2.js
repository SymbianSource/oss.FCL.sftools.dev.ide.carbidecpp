
// this test gets everything through instance

function Test() {
}

Test.prototype.getInstance = function() {
	return instance;
}

Test.prototype.getProperties = function() {
	return instance.properties;
}

Test.prototype.getNamePlusFoo = function() {
//	java.lang.System.out.println( instance.properties.size)
	return instance.properties.name + "foo";
}

Test.prototype.getSizeFormatted = function() {
	return instance.properties.size.x + "," + instance.properties.size.y;
}

