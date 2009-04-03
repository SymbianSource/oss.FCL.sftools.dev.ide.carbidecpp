var gCounter = 0;

function FooBar() {
	this.counter = ++gCounter;
}

FooBar.prototype.doFoo = function() {

	return this.counter;
}

FooBar.prototype.doBar = function() {

	return this.counter;
}

FooBar.prototype.propertyChanged = function(instance, propertyId) {
	if (propertyId == "foo")
		instance.properties.bar = instance.properties.foo;
}