

function QueryContainment() {
}


function doComponentCheck(instance, componentId) {
	return instance.componentId == componentId;
}

function doComponentCheckStatus(instance, componentId) {
	if (doComponentCheck(instance, componentId))
		return null;
		
	statusBuilder = newStatusBuilder();
	params = new Array(1);
	params[0] = componentId;
	statusBuilder.add(4, "Test {0}", params);
	return statusBuilder.createStatus("", null);
}

QueryContainment.prototype.canContainComponent = function(instance, otherComponent) {
	return doComponentCheckStatus(instance, otherComponent.id);
}

QueryContainment.prototype.canContainChild = function(instance, child) {
	return this.canContainComponent(instance.attributes, child.componentId);
}

QueryContainment.prototype.canRemoveChild = function(instance, child) {
	return doComponentCheck(instance, child.componentId);
}
