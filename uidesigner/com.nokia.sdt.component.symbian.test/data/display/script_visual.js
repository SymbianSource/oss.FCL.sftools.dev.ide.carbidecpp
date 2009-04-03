
function Visual() {
}

Visual.prototype.draw = function(instance, laf, graphics) {

	graphics.setBackground(Colors.getColor(255, 0, 0))

}

Visual.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return new Point(wHint + 10, hHint + 20);	
}

Visual.prototype.getPropertyPaths = function(instance) {
	return new Array("foo")
}

Visual.prototype.getLabelBounds = function(instance, propertyPath, laf) {
	return new Rectangle(0, 0, 10, 10)
}

Visual.prototype.getLabelFont = function(instance, propertyPath, laf) {
	return null;
}

