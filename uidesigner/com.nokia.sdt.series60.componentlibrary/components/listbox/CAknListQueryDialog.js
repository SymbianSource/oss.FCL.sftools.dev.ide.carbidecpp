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
include("../messageLibrary.js")
include("../cba/cbaLibrary.js")
include("listboxLibrary.js")

function CAknListQueryDialog() {
	this.lafInfo = null;

	// the bounds of the dialog, rel. to instance
	this.dialogBounds = null;	
	// the bounds of the prompt part, rel. to dialogBounds
	this.promptBounds = null;
	// the bounds of the list box part, rel. to dialogBounds
	this.listBounds = null;
}


CAknListQueryDialog.prototype.ensureLafInfo = function(instance, laf) {
	if (isLafInfoDirty(this.lafInfo, instance, laf)) {
		
		// get rectangle, even if temporarily incorrect
		var listBounds;
		if (this.listBounds)
			listBounds = new Rectangle(this.listBounds.x, this.listBounds.y,
				this.listBounds.width, this.listBounds.height);
		else
			listBounds = new Rectangle(0, 0, 1, 1);
		
		//println("making laf info with " +listBounds);
		this.lafInfo = new ListBoxLafInfo(instance, laf);
	}
	return this.lafInfo;
}

CAknListQueryDialog.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;
	this.ensureLafInfo(instance, laf);  // ends up setting the bounds rects

	// first draw the CBA
	var leftText = lookupString("Ok");
	var rightText = lookupString("Cancel");
	var middleText = "";
	var r = laf.getRectangle("control.pane.bounds");
	var rect = new Rectangle(r.x, r.y, r.width, r.height);
	rect.x -= properties.location.x;
	rect.y -= properties.location.y;
	drawCBA(leftText, rightText, middleText, new Point(rect.x, rect.y), new Point(rect.width, rect.height), laf, graphics);

	graphics.setBackground(getBackgroundColor(instance, laf));
	graphics.fillRectangle(this.dialogBounds);

	// draw prompt
	var font = this.getLabelFont(instance, "text", laf);
	
	graphics.setForeground(laf.getColor("listitem.text"));
	var text = chooseScalableText(properties.text, font, this.promptBounds.width);
	graphics.setFont(font);
	graphics.drawFormattedString(text, this.promptBounds, 
		Font.OVERFLOW_ELLIPSIS,
		0);

	var lineRow = this.promptBounds.y + this.promptBounds.height;
	graphics.drawLine(this.dialogBounds.x, lineRow,
		this.dialogBounds.x + this.dialogBounds.width, lineRow);

	var lafInfo = this.ensureLafInfo(instance, laf);
	
	// if empty, draw No Data
	if (instance.children.length == 0) {
		var normalFont = laf.getFont("NormalFont");
		graphics.setForeground(laf.getColor("listitem.text"));
		graphics.setFont(normalFont);
		var str = lookupString("NoData");
		var extent = graphics.stringExtent(str);
		var r = (lafInfo.listBounds.height - extent.y - getFontHeight(normalFont)) / 2;
		var c = (lafInfo.listBounds.width - extent.x) / 2;
		graphics.drawString(str, c + lafInfo.listBounds.x , r + lafInfo.listBounds.y, true);
	} else {
		lafInfo.drawListBoxDecorations(graphics);
	}	
	
	// draw dialog
	graphics.setForeground(laf.getColor("EEikColorControlText"));
	var x = this.dialogBounds.x;
	var y = this.dialogBounds.y;
	var width = this.dialogBounds.width;
	var height = this.dialogBounds.height;
	graphics.drawRectangle(x, y, width, height);

	// shadows
	graphics.setForeground(laf.getColor("control.shadow.inner"))
	graphics.drawLine(x + 1, y + height + 1, x + width + 1, y + height + 1)
	graphics.drawLine(x + width + 1, y + 1, x + width + 1, y + height + 1)
	
	graphics.setForeground(laf.getColor("control.shadow.outer"))
	graphics.drawLine(x + 2, y + height + 2, x + width + 2, y + height + 2)
	graphics.drawLine(x + width + 2, y + 2, x + width + 2, y + height + 2)

}


CAknListQueryDialog.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null;
}

// IDirectLabelEdit
CAknListQueryDialog.prototype.getPropertyPaths = function(instance) {
	return new Array("text");
}

CAknListQueryDialog.prototype.getLabelBounds = function(instance, propertyPath, laf) {
	return this.promptBounds;
}

CAknListQueryDialog.prototype.getLabelFont = function(instance, propertyPath, laf) {
	return laf.getFont("NormalFont");
}

CAknListQueryDialog.prototype.layout = function(instance, laf) {
	// calculate our own bounds
	this.lafInfo = null;
	calculateQueryBounds(this, instance, laf, this.getLabelFont(instance, "text", laf));
	setBounds(instance, this.instanceBounds);

	// calculate list item bounds
	
	var listLafInfo = this.ensureLafInfo(instance, laf);
	var lafInfo = listLafInfo.getListBoxLafInfo();
	var width = this.listBounds.width;
	var height = this.listBounds.height;
	var row = 0;
	var rowHeight = lafInfo.rowHeight;
	var header = lafInfo.firstRow;
	var bottomRow = lafInfo.separatorRow;
	
	var x = this.listBounds.x;
	var y = this.listBounds.y;
	//println("list item width = " + width);
	for (var i in instance.children) {
		var child = instance.children[i];
		setBounds(child, new Rectangle(0, rowHeight * row, width, rowHeight));
		++row;
	}

}

// IScrollBoundsProvider

CAknListQueryDialog.prototype.getScrollBounds = function(instance, laf) {
	var listLafInfo = this.ensureLafInfo(instance, laf);
	var lafInfo = listLafInfo.getListBoxLafInfo();
	var x = this.listBounds.x;
	var y = this.listBounds.y;
	var width = this.listBounds.width;
	var height = this.listBounds.height;
	var scrollBounds = new Rectangle(x, y + lafInfo.firstRow, width, height);
	var displayable = lafInfo.maxDisplayableItems;
	scrollBounds.height = displayable * lafInfo.rowHeight + 1;

	return scrollBounds;
}


CAknListQueryDialog.prototype.propertyChanged = function(instance, property) {
	if (property == "text" || property == "style") {
		instance.forceLayout();
	}
}


///////////////////////////////////

// IQueryContainment

function canContainComponent(instance, component) {
	if (isListItemComponent(component))
		return null;
	
	return buildSimpleContainmentErrorStatus(
			lookupString("listBoxItemContainmentErr"), new Array( component.friendlyName ));			
}

CAknListQueryDialog.prototype.canContainComponent = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent);
}

CAknListQueryDialog.prototype.canContainChild = function(instance, child) {
	return canContainComponent(instance, child.component);
}

CAknListQueryDialog.prototype.canRemoveChild = function(instance) {
	return true;
}

CAknListQueryDialog.prototype.isValidComponentInPalette = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent) == null;
}


///////////////////////////////////

// IComponentValidator
CAknListQueryDialog.prototype.validate = function(instance) {
	if (!isTonePropertyAvailableInQuery() && instance.properties.tone != "ENoTone") {
		return [ createSimpleModelError(instance, "tone", 
			lookupString("UndefinedSDKPropertyValue"), 
			[ instance.name, "tone", instance.properties.tone ]) ];
	}
	return null;
}

CAknListQueryDialog.prototype.queryPropertyChange = function(instance, propertyPath, newValue) {
	if (propertyPath == "tone" && !isTonePropertyAvailableInQuery() && newValue != "ENoTone") {
		return formatString(lookupString("NoSDKPropertyValue"), [ "tone" ]);
	}
	return null;
}


