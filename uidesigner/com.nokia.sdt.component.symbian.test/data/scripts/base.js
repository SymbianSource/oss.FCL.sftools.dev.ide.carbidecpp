counter = 4
function draw(value)  {
	//java.lang.System.out.println("counter="+counter);
	return counter++ * -value;
	
}
function Base() {
}

Base.prototype.draw = draw

Base.prototype.layout = function(lay) {
}
