<inline scope="file"><![CDATA[
function getCurrentConfig(instance) {
	// find dialog instance
	var dlg = instance.parent;
	while (!dlg.isInstanceOf("com.nokia.carbide.uiq.CQikSimpleDialog"))
		dlg = dlg.parent;
	// find dialog configurations group
	var dlgConfigsGroup = dlg.findChildOfType("com.nokia.carbide.uiq.DialogConfigurationsGroup");
	for (var i in dlgConfigsGroup.children) {
		var dlgConfig = dlgConfigsGroup.children[i];
		if (dlgConfig.properties.viewOrContainer == instance.name)
			return dlgConfig.properties.uiconfig;
	}
	return null;
}
]]></inline>
<inline><![CDATA[
if (isWithinSimpleDialog(instance)) {
	var allChildContribs = Engine.generateChildContributions(form);
	
	var initControlContribs = Engine.getContributionsForPhase(allChildContribs, "Initialize");
	var initPropertiesContribs = Engine.getContributionsForPhase(allChildContribs, "InitializeControlHandlers");
	
	allChildContribs.removeAll(initControlContribs);
	allChildContribs.removeAll(initPropertiesContribs);
	
	for (var i in instance.children) {
		var child = instance.children[i];
		var childContribs = Engine.generateChildContributions(child, form);
		childContribs.removeAll(Engine.generateChildContributions(child, form));
		childContribs.retainAll(Engine.getContributionsForPhase(childContribs, "Initialize"));
		if (Engine.formMatches(form, ["InstanceGen"])) {
			if (this.isRegistered(child) == true) {
				//println(child + " is registered, removing Initialize contribs");
				var toRemove = Engine.getContributionsForPhase(childContribs, "Initialize");
				//println("toRemove.size before=" + toRemove.size());
				//println("ChildContribs.size before=" + childContribs.size());
				childContribs.removeAll(toRemove);
				//println("ChildContribs.size after=" + childContribs.size());
				//for (var iterator = childContribs.iterator(); iterator.hasNext();) {
					//var myContrib = iterator.next();
					//println("     " + myContrib);
				//}
			} else {
				//println(child + " is not registered, registering child");
				this.register(child);
			}
		}
		contribs.addAll(childContribs);
	}
	
	var curConfig = getCurrentConfig(instance);
	var contrib;
	
//poc 091107 uncomment ini..
	if (curConfig != null) {
		contrib = Engine.createContributionForPhase("InitializeControlHandlers");
		contrib.setText("if ( CQUiConfigClient::Static().CurrentConfig() == " + curConfig + " )\n");
		contribs.add(contrib);
		contrib = Engine.createContributionForPhase("InitializeControlHandlers");
		contrib.setText("{\n");
		contrib.indentAdjust(1);
		contribs.add(contrib);
	}

//poc 091107 uncomment fin..
		
	for (var iterator = initPropertiesContribs.iterator(); iterator.hasNext();) {
		contrib = iterator.next();
		if (curConfig != null)
			contrib.indentAdjust(1);
		contribs.add(contrib);
	}

//poc 091107 uncomment ini..
	if (curConfig != null) {
		contrib = Engine.createContributionForPhase("InitializeControlHandlers");
		contrib.setText("}\n");
		contrib.indentAdjust(1);
		contribs.add(contrib);
	}
//poc 091107 uncomment fin..
		
	contribs.addAll(allChildContribs);
}
]]></inline>