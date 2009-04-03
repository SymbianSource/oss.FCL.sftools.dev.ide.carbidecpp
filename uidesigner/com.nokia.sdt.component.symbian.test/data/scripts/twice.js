
var gCounter = 23

function Base() {
	this.counter = gCounter
	gCounter *= 2
}

Base.prototype.fiddle = function() { return this.counter; }
Base.prototype.faddle = function() { return this.counter; }


