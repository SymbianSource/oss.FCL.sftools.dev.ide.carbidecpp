

function Test() {
}

Test.prototype.getEventBinding = function(id) {
	return events[id]
}
Test.prototype.getEventEventId = function(id) {
	return events[id].eventId
}
Test.prototype.getEventEventName = function(id) {
	return events[id].eventName
}
Test.prototype.getEventMethod = function(id) {
	return events[id].handlerName
}
Test.prototype.getEventSymbol = function(id) {
	return events[id].handlerSymbol
}

Test.prototype.getEventParamBinding = function(instance, id) {
	var events = instance.events
	return events[id]
}
Test.prototype.getEventParamEventId = function(instance, id) {
	var events = instance.events
	return events[id].eventId
}
Test.prototype.getEventParamEventName = function(instance, id) {
	var events = instance.events
	return events[id].eventName
}
Test.prototype.getEventParamMethod = function(instance, id) {
	var events = instance.events
	return events[id].handlerName
}
Test.prototype.getEventParamSymbol = function(instance, id) {
	var events = instance.events
	return events[id].handlerSymbol
}
