
function PropertyExtenders() {
}

	// Return instances that may provide extension properties
	// The target instance parameter is the instance to receive the
	// additional properties
PropertyExtenders.prototype.getPropertyExtenders = function(instance, targetInstance) {
	if (targetInstance.parent == instance)
		return [instance];

	return null;
}
	
PropertyExtenders.prototype.getExtensionSetNames = function(instance, targetInstance) {
	if (targetInstance.parent != instance)
		return null;
	var val = instance.properties.type;
	if (val == "EElement1__")
		return [ "element1" ];
	if (val == "EElement_1_")
		return [ "element2" ];
	if (val == "EElement__1")
		return [ "element3" ];
	if (val == "EElement1_1")
		return [ "element1", "element3" ];
	if (val == "EElement11_")
		return [ "element1", "element2" ];
	if (val == "EElement_11")
		return [ "element2", "element3" ];
	if (val == "EElement111")
		return [ "element1", "element2", "element3" ];
	return null;
}

