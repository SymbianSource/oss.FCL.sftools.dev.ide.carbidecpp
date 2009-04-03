
function PropertyExtenders() {
}

	// Return instances that may provide extension properties
	// The target instance parameter is the instance to receive the
	// additional properties
PropertyExtenders.prototype.getPropertyExtenders = function(instance, targetInstance) {
	if (instance == targetInstance)
		return [instance.parent];

	return null;
}
	
PropertyExtenders.prototype.getExtensionSetNames = function(instance, targetInstance) {
	return null;
}

