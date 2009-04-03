

function Layout() {
}

Layout.prototype.layout = function(instance, laf) {
	var existingLaf = findExistingLookAndFeel(instance);
	// this gets called creating the display model, before accessible from data model
	if (existingLaf != null && existingLaf != laf)
		throw new java.lang.IllegalArgumentException("findExistingLookAndFeel failed");


	var children = instance.children;
	var properties = instance.properties;
	
	var childProperties = children[0].properties;
	childProperties.location.x = properties.location.x;
	childProperties.location.y = properties.location.y + 25;
	var prefSize = children[0].getPreferredSize(properties.size.width, properties.size.height - 25);
	childProperties.size.width = prefSize.x;
	childProperties.size.height = prefSize.y;
}

Layout.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return new Point(wHint, hHint);
}

