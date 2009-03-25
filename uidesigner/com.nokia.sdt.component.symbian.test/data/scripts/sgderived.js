
function Derived() {
	Base.apply(this)
}

Derived.prototype = new Base()

Derived.prototype.act = function() {
	return "Derived-Act-"+form + "/" + this.shared()
}
