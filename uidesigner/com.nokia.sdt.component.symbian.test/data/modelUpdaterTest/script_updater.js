function CModelUpdater() {
}

CModelUpdater.prototype.updateModel = function(instance, dataModel) {
	var newObject = dataModel.createNewComponentInstance(instance.parent.component);
	var command = dataModel.createAddNewComponentInstanceCommand(instance.EObject, newObject, 0);
	if (command.canExecute()) {
		command.execute();
	}
}

