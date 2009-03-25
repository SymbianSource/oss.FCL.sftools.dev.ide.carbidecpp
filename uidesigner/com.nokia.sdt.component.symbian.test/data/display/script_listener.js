

function Listener() {
}

Listener.prototype.childAdded = function(parent, child, laf) {
	if (child.componentId == "com.nokia.examples.scriptComp")
		child.properties.test = "added";
}

Listener.prototype.childRemoved = function(parent, child, laf) {
	parent.properties.test = "removed";
}

Listener.prototype.childrenReordered = function(parent, laf) {
	parent.properties.test = "reordered";
}

