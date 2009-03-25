function CImplementationDelegate() {
}

CImplementationDelegate.prototype.getDelegates = function(instance, interfaceTypeName) {
	if (interfaceTypeName == "com.nokia.sdt.datamodel.adapter.ILayout") {
		return [ lookupInstanceByName("delegate") ];
	}
	
	return null;
}

CImplementationDelegate.prototype.getDelegateInterfaceNames = function(instance) {
	return [ "com.nokia.sdt.datamodel.adapter.ILayout" ];
}
