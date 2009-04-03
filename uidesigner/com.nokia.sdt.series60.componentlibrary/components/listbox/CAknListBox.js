/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of the License "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
* Contributors:
*
* Description: 
*
*/


include("../containers/containerLibrary.js")
include("../renderLibrary.js")
include("listboxLibrary.js")

function CAknListBox() {
	this.lafInfo = null;
}

CAknListBox.prototype.ensureLafInfo = function(instance, laf) {
	if (isLafInfoDirty(this.lafInfo, instance, laf)) {
		this.lafInfo = new ListBoxLafInfo(instance, laf);
	}
	return this.lafInfo;
}

// IVisualAppearance

CAknListBox.prototype.draw = function(instance, laf, graphics) {
	var lafInfo = this.ensureLafInfo(instance, laf);

	if (instance.children.length == 0) {
		graphics.setBackground(getBackgroundColor(instance, laf));
		DialogUtils = getPluginClass("com.nokia.sdt.series60.componentlibrary", 
						"com.nokia.sdt.series60.component.DialogUtils");
		DialogUtils.drawDialogNoData(graphics, laf);

		if (listBoxHasSearchField(instance)) 
			lafInfo.drawSearchField(graphics);

		return;	
	}

	var properties = instance.properties;	
	var width = properties.size.width;
	var height = properties.size.height;

	graphics.setBackground(getBackgroundColor(instance, laf));
	graphics.fillRectangle(new Rectangle(0, 0, width, height));

	
	lafInfo.drawListBoxDecorations(graphics);
	
	if (listBoxHasSearchField(instance)) 
		lafInfo.drawSearchField(graphics);
}

CAknListBox.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null; // needs implementation	
}

///////////////////////////////////////////////////////////

// ILayout

CAknListBox.prototype.layout = function(instance, laf) {
	var listLafInfo = this.ensureLafInfo(instance, laf);
	listLafInfo.lafInfo = null;
	var width = instance.properties.size.width;
	var height = instance.properties.size.height;
	var row = 0;
	var lafInfo = listLafInfo.getListBoxLafInfo();
	var rowHeight = lafInfo.rowHeight;
	var displayable = listBoxHasSearchField(instance) ? lafInfo.maxDisplayableItems - 1 : lafInfo.maxDisplayableItems;
	var header = (instance.children.length > displayable) ? 0 : lafInfo.firstRow;
	var bottomRow = lafInfo.separatorRow;
	if (lafInfo.searchFieldRow)
		bottomRow = Math.min(lafInfo.searchFieldRow, bottomRow);
	for (var i in instance.children) {
		var child = instance.children[i];
		var top = header + rowHeight * row;
		setBounds(child, new Rectangle(0, top, width, rowHeight));
		++row;
	}
}

///////////////////////////////////////////////////////////

// IQueryContainment

function canContainComponent(instance, component) {
	if (isListItemComponent(component))
		return null;
	
	return buildSimpleContainmentErrorStatus(
			lookupString("listBoxItemContainmentErr"), new Array( component.friendlyName ));			
}

CAknListBox.prototype.canContainComponent = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent);
}

CAknListBox.prototype.canContainChild = function(instance, child) {
	return canContainComponent(instance, child.component);
}

CAknListBox.prototype.canRemoveChild = function(instance) {
	return true;
}

CAknListBox.prototype.isValidComponentInPalette = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent) == null;
}


///////////////////////////////////

// IComponentInstancePropertyListener

CAknListBox.prototype.propertyChanged = function(instance, propertyId) {
	if (propertyId == "style") {
		var kids = getListBoxItems(instance);
		for (var i in kids) {
			kids[i].updatePropertySource();
		}
		
		// resize array of columns		
		instance.properties.alignmentInfo.length = getListBoxColumnCount(instance);
		this.lafInfo = null;
	}
	else if (propertyId == "hasSearchField") {
		this.lafInfo = null;
		instance.forceRedraw();
	}
	
	// we also listen to "type" changing to "EAknListboxMarkableList"
	// in the set value command extender
}

////////////////////////////////////

// IInitializer

CAknListBox.prototype.initialize = function(instance, isConfigured) {
	// setup array of columns		
	instance.properties.alignmentInfo.length = getListBoxColumnCount(instance);
}

////////////////////////////////////

// IComponentValidator

CAknListBox.prototype.validate = function(instance) {
	var messages = null;
	/*
	var properties = instance.properties;
	if (properties.type == "EAknListBoxMarkableList") {
		if (!isImagePropertySet(properties.markIcon)) {
			var message = createSimpleModelError(instance, 
				"markIcon",
				lookupString("ListBoxValidate.NoMarkIconError"),
				[ instance.name ] );
			messages = new java.util.ArrayList();
			messages.add(message);
		}
	}
	*/
	return messages;
}

CAknListBox.prototype.queryPropertyChange = function(instance, propertyId, newValue) {
	// allow anything -- only check at save time
	return null;
}

////////////////////////////////////

// IDirectImageEdit

CAknListBox.prototype.getPropertyPaths = function(instance) {
	return null;
}

CAknListBox.prototype.getVisualBounds = function(instance, propertyPath, laf) {
	return null;
}


// IImagePropertyRenderingInfo

CAknListBox.prototype.getViewableSize = function(instance, propertyId, laf) {
	var lafInfo = this.ensureLafInfo(instance, laf);
	if (propertyId.startsWith("extraImages"))
		propertyId = "image";
	var rect = lafInfo.getListItemCellBounds(propertyId);
	if (rect == null)
		return null;
	return new Point(rect.width, rect.height);
}

CAknListBox.prototype.isScaling = function(instance, propertyId, laf) {
	return isScalingIcons();
}

CAknListBox.prototype.getAlignmentWeights = function(instance, propertyId, laf) {
	var version = getComponentVersions();
	if (version.getMajor() >= 3 || version.getMinor() >= 8)
		return new Point(ImageUtils.ALIGN_CENTER_OR_LEFT, ImageUtils.ALIGN_CENTER_OR_TOP);
	else
		return new Point(ImageUtils.ALIGN_LEFT, ImageUtils.ALIGN_TOP);
}

CAknListBox.prototype.isPreservingAspectRatio = function(instance, propertyId, laf) {
	return true;
}

// IScrollBoundsProvider

CAknListBox.prototype.getScrollBounds = function(instance, laf) {
	var listLafInfo = this.ensureLafInfo(instance, laf);
	var lafInfo = listLafInfo.getListBoxLafInfo();
	var b = listLafInfo.listBounds;
	var scrollBounds = new Rectangle(0, lafInfo.firstRow, b.width, b.height);
	var displayable = listBoxHasSearchField(instance) ? lafInfo.maxDisplayableItems - 1 : lafInfo.maxDisplayableItems;
	scrollBounds.height = displayable * lafInfo.rowHeight + 3;

	return scrollBounds;
}

//////////////////////////////////////////
// IComponentEventInfo
//////////////////////////////////////////
CAknListBox.prototype.getEventGroups = function(instance) {
	// Get the proper events depending on the SDK.
	version = getComponentVersions();
	if (version.major >= 5) {
		// Add touch events
		return ["CCoeControl", "CCoeControlTouch"];
	}
	else {
		// sdks prior to touch
		return ["CCoeControl"];
	}
}


//////////////////////////////////////////
// IPropertyExtenders
//////////////////////////////////////////
// Return instances that may provide extension properties
// The target instance parameter is the instance to receive the
// additional properties
CAknListBox.prototype.getPropertyExtenders = function(instance, targetInstance) {
	version = getComponentVersions();
	if (version.major >= 5) {
		return [instance];
	}
	return null;
}
	
CAknListBox.prototype.getExtensionSetNames = function(instance, targetInstance) {
	if (instance == targetInstance){
		return [ "touchUI" ];
	}
	return null;
}

