
function ComponentValidator() {
}

ComponentValidator.prototype.validate = function(instance, laf) {
	return null;
}

ComponentValidator.prototype.queryPropertyChange = function(instance, propertyPath, newValue, laf) {
	var result = null;
	if (propertyPath == "always")
		result = null;
	else if (propertyPath == "never")
		result = "not allowed";
	else if (propertyPath == "notnull") {
		if (newValue == null)
			result = "not null";
	}
	else if (propertyPath == "compound.oddonly") {
		if ((newValue & 1)==0)
			result = "odd only";
	}
	else 
		result = "unknown";
	return result;
}
	

