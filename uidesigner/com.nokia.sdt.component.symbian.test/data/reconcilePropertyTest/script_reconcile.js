var kZero = "Zero";
var kOne = "One";
var kTwo = "Two";
var kThree = "Three";
var kMany = "Many";


function Reconcile() {
}

Reconcile.prototype.createDisplayValue = function(instance, propertyTypeName, propertyValue) {
	if (!propertyTypeName.equals("compoundProperty")) {
		java.lang.System.out.println("createDisplayValue() Wrong property type: "+propertyTypeName+"; expected \"compoundProperty\"");
		return null;
	}
		
	var sum = propertyValue.first + propertyValue.second;
	if (sum == 0)
		return kZero;
	if (sum == 1)
		return kOne;
	if (sum == 2)
		return kTwo;
	if (sum == 3)
		return kThree;

	return kMany;
}
	
Reconcile.prototype.isDisplayValueEditable = function(instance, propertyTypeName) {
	if (!propertyTypeName.equals("compoundProperty")) {
		java.lang.System.out.println("isDisplayValueEditable() Wrong property type: "+propertyTypeName+"; expected \"compoundProperty\"");
		return true;
	}
		
	return true;
}
	
Reconcile.prototype.applyDisplayValue = function(instance, propertyTypeName, displayValue, propertyValue) {
	if (!propertyTypeName.equals("compoundProperty")) {
		java.lang.System.out.println("applyDisplayValue() Wrong property type: "+propertyTypeName+"; expected \"compoundProperty\"");
		return;
	}
		
	if (displayValue.equals(kZero)) {
		propertyValue.first = 0;
		propertyValue.second = 0;
	}
	if (displayValue.equals(kOne)) {
		propertyValue.first = 0;
		propertyValue.second = 1;
	}
	if (displayValue.equals(kTwo)) {
		propertyValue.first = 1;
		propertyValue.second = 1;
	}
	if (displayValue.equals(kThree)) {
		propertyValue.first = 1;
		propertyValue.second = 2;
	}
	if (displayValue.equals(kMany)) {
		propertyValue.first = 2;
		propertyValue.second = 2;
	}
}

