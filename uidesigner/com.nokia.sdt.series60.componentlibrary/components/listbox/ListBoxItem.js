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


include("../renderLibrary.js")
include("listboxLibrary.js")

function ListBoxItem() {
	this.lafInfo = null;
}

ListBoxItem.prototype.ensureLafInfo = function(instance, laf) {
	if (isLafInfoDirty(this.lafInfo, instance, laf)) {
		this.lafInfo = new ListBoxLafInfo(instance.parent, laf);
	}
	return this.lafInfo;
}


function getListItemIndex(listInstance, itemInstance) {
	for (var i in listInstance.children) {
		if (listInstance.children[i] == itemInstance)
			return i;
	}
	return 0;
}

ListBoxItem.prototype.draw = function(instance, laf, graphics) {
	var listLafInfo = this.ensureLafInfo(instance, laf);
	
	//var visibleIndex = getListItemIndex(instance.parent, instance) - 0;

	// Don't draw any items below the horizontal line 
	// at the bottom of the list
	//if (visibleIndex < 0 || visibleIndex >= listLafInfo.getListBoxLafInfo().maxDisplayableItems)
	//	return;
		
	var properties = instance.properties;
	
	graphics.setBackground(getBackgroundColor(instance.parent, laf));
	
	listLafInfo.drawListItem(ListBoxItem.prototype, instance, graphics);	
}

ListBoxItem.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null; 	
}

///////////////////////////////////////////////////////////

// IPropertyExtenders

	// Return instances that may provide extension properties
	// The target instance parameter is the instance to receive the
	// additional properties
ListBoxItem.prototype.getPropertyExtenders = function(instance, targetInstance) {
	this.lafInfo = null;
	
	if (instance == targetInstance) {
		return [instance];
	}
	return null;
}
	
ListBoxItem.prototype.getExtensionSetNames = function(instance, targetInstance) {
	if (!isListBox(instance.parent) && !isQueryDialog(instance.parent))
		return null;  // happens on clipboard
	var style = instance.parent.properties.style;
	//println("incoming style = " + style);
	if (styleAliases[style]) {
		style = styleAliases[style];
		//println("aliases to " + style);
	}
	return [ style ];
}

////////////////////////////////////////////////////////////

// IComponentValidator

	// Return collection of IModelMessage or null for no errors
ListBoxItem.prototype.validate = function(instance) {
	return validateListItemData(instance);
}

	// Query validity of a property change.  Return null for success
	// or an error string.
ListBoxItem.prototype.queryPropertyChange = function(instance, propertyPath, newValue) {
	return validateListItemPropertyChange(instance, propertyPath, newValue);
}

/////////////////////////////////////////////////////////////

	// implementation for IDirectImageEdit
ListBoxItem.prototype.getImagePropertyPaths = function(instance) {
	return getListItemImageProperies(instance);
}

	// implementation for IDirectImageEdit
ListBoxItem.prototype.getImageBounds = function(instance, propertyPath, laf) {
	var listLafInfo = this.ensureLafInfo(instance, laf);
	return listLafInfo.getListItemBounds(instance, propertyPath);
}

//////////////////////////////////////////////////////////////

	// implementation for IImagePropertyRenderingInfo
ListBoxItem.prototype.getViewableSize = function(instance, propertyId, laf) {
	var listLafInfo = this.ensureLafInfo(instance, laf);
	var bounds = listLafInfo.getListItemBounds(propertyId);
	//println("bounds for " + propertyId + " = " + bounds);
	return new Point(bounds.width, bounds.height);
}

	// implementation for IImagePropertyRenderingInfo
ListBoxItem.prototype.isScaling = function(instance, propertyId, laf)  {
	return isScalingIcons();
}

	// implementation for IImagePropertyRenderingInfo
ListBoxItem.prototype.getAlignmentWeights = function(instance, propertyId, laf) {
	var version = getComponentVersions();
	if (version.getMajor() >= 3 || version.getMinor() >= 8)
		return new Point(ImageUtils.ALIGN_CENTER, ImageUtils.ALIGN_CENTER);
	else
		return new Point(ImageUtils.ALIGN_CENTER, ImageUtils.ALIGN_CENTER);
}

	// implementation for IImagePropertyRenderingInfo
ListBoxItem.prototype.isPreservingAspectRatio = function(instance, propertyId, laf) {
	return true;
}

////////////////////////////////////////////////////

	// IDirectLabelEdit
ListBoxItem.prototype.getPropertyPaths = function(instance) {
	return getListItemLabelProperties(instance);
}

ListBoxItem.prototype.getLabelBounds = function(instance, propertyPath, laf) {
	var listLafInfo = this.ensureLafInfo(instance, laf);
	var bounds = listLafInfo.getListItemBounds(propertyPath);
	//println("label bounds for " + propertyPath + " = " +bounds);
	return new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height);
}

ListBoxItem.prototype.getLabelFont = function(instance, propertyPath, laf) {
	var listLafInfo = this.ensureLafInfo(instance, laf);
	var cellInfo = listLafInfo.getListItemCellInfo(propertyPath);
	return laf.getFont(cellInfo.font);
}

