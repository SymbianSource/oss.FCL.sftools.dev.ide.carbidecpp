dcounter = 10
function draw(value)  {
	baseRet = Base.prototype.draw(value)
	old = dcounter
	dcounter += 10
	return baseRet + old * value;
	
}
function Derived() {
	Base.apply(this)
}
Derived.prototype = new Base;

Derived.prototype.draw = draw

