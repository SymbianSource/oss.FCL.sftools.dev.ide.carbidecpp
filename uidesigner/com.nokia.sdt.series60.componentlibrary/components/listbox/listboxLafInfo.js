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


/**
 *	Most of the rendering information is in the styleMap variable
 *	in listboxLibrary.js.  The specific column/cell sizes are in
 *	the LAF as rectangles "listitem.<style>.<property>".
 */
 
include("../renderLibrary.js")

Rectangle = Packages.org.eclipse.swt.graphics.Rectangle;
ImageUtils = Packages.com.nokia.sdt.utils.ImageUtils;

function isLafInfoDirty(lafInfo, instance, laf) {
	var screen = laf.getDimension("screen.size");
	//println("screen is " + screen + " vs: " + (lafInfo != null ? lafInfo.screen : null));
	return (lafInfo == null 
		|| lafInfo.laf != laf 
		|| lafInfo.screen == null
		|| lafInfo.screen.x != screen.x
		|| lafInfo.screen.y != screen.y
		|| lafInfo.listInstance != instance);
}

////////////////////////////////////////////

function cloneObject(what) {
	var into = new Object();
    for (i in what) {
     	into[i] = what[i];
    }
    return into;
}

function dump(what) {
	var str = "[";
	for (i in what) {
		str += i + ":" +what[i]+"; ";
	}
	str += "]";
	println(str);
}


/////////////////////


//	get prompt bounds, given the dialog width
function calculateQueryPromptBounds (holder, instance, laf, width) {
	var font = laf.getFont("AnnotationFont");
	var fontHeight = getFontHeight(font);
	// padding inside dialog
	var padding = laf.getInteger("note.padding", 2);
	rect = new Rectangle(padding*3, padding, width - padding*6, fontHeight + padding*2);
	holder.promptBounds = rect;
	//println("prompt bounds = "+ rect);
	return rect;
}

//	get list bounds, given the list width
function calculateQueryListBounds(holder, instance, laf, width, height) {

	var listBoxLafInfo = new ListBoxLafInfo(instance, laf);

	var rowHeight = listBoxLafInfo.rowHeight;

	// padding inside dialog
	var padding = laf.getInteger("note.padding", 2);
	
	var rows = Math.floor((height - padding*2) / rowHeight);
	
	// if no kids, draw "No Data", else pack 
	if ( instance.children.length > 0 ) {
		var maxRows = laf.getInteger("list.query.maxRows." + listBoxLafInfo.lafData.height, 3);
		rows = Math.min(instance.children.length, maxRows);
	}
	else
		rows /= 2;
		
	// update bounds
	var rect = new Rectangle(padding, 0, 
						width - padding*2, rows * rowHeight);
	//println("list bounds = " + rect);
	holder.listBounds = rect;
}


//	Calculate bounds of all the parts of the dialog, including the instance
function calculateQueryBounds(holder, instance, laf) {
	var rect;
	
	var d = laf.getDimension("screen.size");
	// padding outside dialog
	var padding = laf.getInteger("note.padding", 2);

	var cbaRect = laf.getRectangle("control.pane.bounds");
	var portrait = laf.getBoolean("is.portrait", true);

	var width = Math.min(d.x, d.y) - padding*4;	// shadow room
	calculateQueryPromptBounds(holder, instance, laf, width);
	
	var availHeight;
	if (portrait) {
		availHeight = d.y - cbaRect.height - holder.promptBounds.height;
	}
	else {
		var sb1Rect = laf.getRectangle("status.bar1.bounds");
		var sb2Rect = laf.getRectangle("status.bar2.bounds");
		availHeight = d.y - sb1Rect.height - sb2Rect.height - holder.promptBounds.height;
	}
		
	calculateQueryListBounds(holder, instance, laf, width, availHeight);

	holder.listBounds.y += holder.promptBounds.y + holder.promptBounds.height;

	var height = holder.promptBounds.y + holder.promptBounds.height + holder.listBounds.height;
	
	var rect;
	var bounds;
	if (portrait) {
		var contentRect = laf.getRectangle("content.pane.bounds");
		rect = new Rectangle(contentRect.x + (contentRect.width - width) / 2, 0, width, height);
		bounds = new Rectangle(0, d.y - height - cbaRect.height - 3 - padding, 
										d.x, height + cbaRect.height + 3 + padding);
	}
	else {
		rect = new Rectangle(0, (d.y - height) / 2, width, height);
		// This flag denotes 3.1+, center the dialog in landscape
		var dlgWidth = laf.getBoolean("decorations", true) ? width : (d.x + width) / 2; 
		bounds = new Rectangle(d.x - dlgWidth - 3, 0, dlgWidth + 3, d.y);
	}
	
	holder.dialogBounds = rect;	
	
	holder.listBounds.x += holder.dialogBounds.x;
	holder.listBounds.y += holder.dialogBounds.y;
	holder.promptBounds.x += holder.dialogBounds.x;
	holder.promptBounds.y += holder.dialogBounds.y;
	
	holder.instanceBounds = bounds;
	
	//println("dialog bounds = " + rect);
	//println("query bounds = " + bounds);
	
}

function getRowHeight(laf, queryDialog, heightSpec, defVal) {
	var lafString = "list.";
	if (queryDialog)
		lafString += "query."
	lafString += "row.height.";
	lafString += heightSpec;

	//println("lafString="+lafString);
	var rowHeight = laf.getInteger(lafString, defVal);
	//println("laf="+laf);
	//println("rowHeight="+rowHeight);
	return rowHeight;
}


/////////////////////

function ListBoxLafInfo(listInstance, laf) {
	this.listInstance = listInstance;
	this.inListBox = isListBox(listInstance);
	this.inQueryDialog = isQueryDialog(listInstance);
	this.isFormattedCellListBox = isFormattedCellListBox(listInstance);
		
	this.style = styleMap[listInstance.properties.style];
	if (this.style == null) {
		error("Unexpected style: " + listInstance.properties.style + " in " + listInstance.name);
	}
	this.lafData = this.style.lafData;
	this.cellInfo = this.style.cellInfo;
	this.properties = this.style.properties;
	this.laf = laf;
	
	this.screen = this.laf.getDimension("screen.size");
	this.rowHeight = getRowHeight(this.laf, this.inQueryDialog, this.lafData.height, 22);
	
	// calculate lazily
	this.listBounds = null;
	this.lafInfo = null;
	this.boundsInfo = null;
}

/**
 *	Get generic cell bounds for property
 */
ListBoxLafInfo.prototype.getListItemCellBounds = function(property) {
	this.ensureListBounds();
	
	var rectKey;
	if (property == "markIcon")
		rectKey = "listitem.any.markIcon";
	else 
		rectKey = "listitem." + this.listInstance.properties.style + "." + property;
	var rect = this.laf.getRectangle(rectKey);
	if (rect == null) {
		println("ERROR: unknown LAF rect " + rectKey);
//		(new java.lang.Exception()).printStackTrace();
		return null;
	}
	return this.scaleRectToLayout(rect, this.listBounds);
}

ListBoxLafInfo.prototype.scaleRectToLayout = function(rect, parentBounds) {
	
	// get multipliers for rect -> coord transform
	var xOffsMult, yOffsMult;
	var xSizeMult, ySizeMult;
/*
* with the introduction of a different qvga landscape, 
* it became clear that each laf needed its own copy of these values,
* and that simple scaling would not be sufficent.
	if (this.screen.x == 176) {
		xOffsMult = 1.0;
		yOffsMult = 1.0;
		xSizeMult = 1.0;
		ySizeMult = 1.0;
	}
	else if (this.screen.x == 208) {
		xOffsMult = 1.0;
		yOffsMult = 1.0;
		xSizeMult = 1.0;
		ySizeMult = 1.0;
	}
	else if (this.screen.x == 240) {
		// QVGA Portrait
		xOffsMult = 1.36;
		yOffsMult = 1.5;
		xSizeMult = 1.36;
		ySizeMult = 1.5;
	}
	else if (this.screen.x == 320) {
		// QVGA Landscape is funny, at least in S60 3.0 200548
		xOffsMult = 1.5;
		yOffsMult = 1.0;
		xSizeMult = 1.5;
		ySizeMult = 1.0;
	}
	else if (this.screen.x == 352) {
		xOffsMult = 2.0;
		yOffsMult = 2.0;
		xSizeMult = 2.0;
		ySizeMult = 2.0;
	}
	else if (this.screen.x == 416) {
		xOffsMult = 2.0;
		yOffsMult = 2.0;
		xSizeMult = 2.0;
		ySizeMult = 2.0;
	}
	else {
		println("ERROR: Unknown resolution: " + this.screen);
		xOffsMult = 1.0;
		yOffsMult = 1.0;
	}
	//println("xMult: " +xOffsMult+", yMult:" + yOffsMult);
*/
	xOffsMult = 1.0;
	yOffsMult = 1.0;
	xSizeMult = 1.0;
	ySizeMult = 1.0;
	
	var x = rect.x * xOffsMult;
	var y = rect.y * yOffsMult;
	
	//print("putting bounds " + rect + " in " + parentBounds);
	var width = rect.width < 0 
		? parentBounds.width + (rect.width * xSizeMult) - x
		: rect.width * xSizeMult;
	var height = rect.height < 0 
		? parentBounds.height + rect.height * ySizeMult - y
		: rect.height * ySizeMult;
	
	var bounds = new Rectangle(x, y, width, height);
	//println("got " + bounds);
	return bounds;
}


ListBoxLafInfo.prototype.ensureListBounds = function() {
		var listBounds;
		if (this.inListBox) {
			var p = this.listInstance.properties;
			listBounds = new Rectangle(p.location.x, p.location.y,
				p.size.width, p.size.height);
		} else if (this.inQueryDialog) {
			var holder = new Object();
			calculateQueryBounds(holder, this.listInstance, this.laf);
			listBounds = holder.listBounds;
		}
		//println("storing listBounds = "+listBounds);
		this.listBounds = listBounds;
}

/**
 *	Get look and feel info for the list box, updated for the current
 *	screen size.
 */
ListBoxLafInfo.prototype.getListBoxLafInfo = function() {
	if (this.lafInfo == null) {
		this.ensureListBounds();
		
		var padding = this.inListBox ? this.laf.getInteger("list.padding", 8) : 0;
		var lafInfo = new Object();
		
		var separatorBefore;
		// nothing uses this override yet
		if (!("separatorBefore" in this.lafData))
			separatorBefore = "text";
		else
			separatorBefore = this.lafData.separatorBefore;
			
		// get the separator column
		lafInfo.separatorColumn = null;
		if (separatorBefore != null) {
			var textRect = this.getListItemCellBounds(separatorBefore);
			if (textRect != null)
				lafInfo.separatorColumn = textRect.x - padding;
		}
		
		//println("props 0 = " +this.properties[0]+"; separatorBefore = " + separatorBefore);
		lafInfo.isInitialSeparatorColumn = (this.properties[0] == separatorBefore);

		//lafInfo.searchField = getListboxSearchField(this.listInstance);
		
		lafInfo.firstRow = padding / 2;
		lafInfo.rowHeight = getRowHeight(this.laf, this.inQueryDialog, this.lafData.height, 22);
		lafInfo.maxDisplayableItems = Math.floor(this.listBounds.height / lafInfo.rowHeight);

		//if (listBoxHasSearchField(this.listInstance)) {
		//	lafInfo.maxDisplayableItems--;
		//}
		
		lafInfo.separatorRow = lafInfo.maxDisplayableItems * lafInfo.rowHeight + padding;
			
		if (listBoxHasSearchField(this.listInstance)) {
			var font = this.laf.getFont("NormalFont");

			var sfBounds = this.isFormattedCellListBox ?
				this.laf.getRectangle("listbox.searchfield.bounds.popup") :
				this.laf.getRectangle("listbox.searchfield.bounds.search");
			sfBounds = this.scaleRectToLayout(sfBounds, this.listBounds);

			lafInfo.searchFieldRow = this.listBounds.y 
				+ this.listBounds.height 
				- sfBounds.height;
			/*	do this adjustment in CAknListBox.layout
			//println("sep="+lafInfo.separatorRow+", sfRow="+lafInfo.searchFieldRow);
			while (lafInfo.separatorRow > lafInfo.searchFieldRow) {
				lafInfo.maxDisplayableItems--;
				lafInfo.separatorRow -= lafInfo.rowHeight;
				//println("sep="+lafInfo.separatorRow+", sfRow="+lafInfo.searchFieldRow);
			}
			*/
		}
		
		//dump(lafInfo);

		lafInfo.inset = this.laf.getInteger("list.padding", 8) / 2;
				
		this.lafInfo = lafInfo;
		this.boundsInfo = null;
	}
		
	return this.lafInfo;
}

/** 
 *	Get generic bounds for any list item.
 *	@return array of rectangles indexed by cell
 */
ListBoxLafInfo.prototype.getListItemBoundsInfo = function() {
	if (this.boundsInfo == null) {
		var boundsInfo = [];
		
		for (var i in this.properties) {	
			var bounds = this.getListItemCellBounds(this.properties[i]);
			boundsInfo[i] = bounds;
		}
		//println(boundsInfo);
		this.boundsInfo = boundsInfo;
	}
	return this.boundsInfo;
}

Point = Packages.org.eclipse.swt.graphics.Point;

ListBoxLafInfo.prototype.getCellAlignedTextOffset = function(cellBounds, textBounds, alignment) {
	//println("aligning " + textBounds + " in " + cellBounds + " at " + alignment);
	var x,y;
	if (alignment == "ELeft") {
		x = cellBounds.x;
		y = cellBounds.y;
	} else if (alignment == "ECenter") {
		x = cellBounds.x + (cellBounds.width - textBounds.x) / 2;
		y = cellBounds.y;
	} else /* ERight */ {
		x = cellBounds.x + cellBounds.width - textBounds.x;
		y = cellBounds.y;
	}
	if (x < cellBounds.x)
		x = cellBounds.x;
	if (y < cellBounds.y)
		y = cellBounds.y;
	//println(" --> "+x+","+y);
	return new Point(x, y);
}

/**
 *	Get rectangle for the mark icon, available if the list is markable.
 */
ListBoxLafInfo.prototype.getMarkIconBounds = function() {
	if (this.listInstance.properties.type == "EAknListBoxMarkableList") {
		var rect;
		this.ensureListBounds();
		rect = this.laf.getRectangle("listitem.any.markIcon");
		rect = this.scaleRectToLayout(rect, this.listBounds);
		//rect.x = this.listBounds.width - rect.width * (1.333);
		rect.height = rect.width;
		//println(rect);
		return rect;
	}
	return null;
}

///////////////////////////
// implementation for IImagePropertyRenderingInfo -- only for mark icon!

ListBoxLafInfo.prototype.getViewableSize = function(instance, propertyId, laf) {
	var lafInfo = new ListBoxLafInfo(instance, laf);
	this.ensureListBounds();
	var rect = new Rectangle(0, 0, 12, 12);
	rect = lafInfo.scaleRectToLayout(rect, this.listBounds);
	var sz = new Point(rect.width, rect.height);
	//println(sz);
	return sz;
}

	// implementation for IImagePropertyRenderingInfo
ListBoxLafInfo.prototype.isScaling = function(instance, propertyId, laf)  {
	return isScalingIcons();
}

	// implementation for IImagePropertyRenderingInfo
ListBoxLafInfo.prototype.getAlignmentWeights = function(instance, propertyId, laf) {
	var version = getComponentVersions();
	if (version.getMajor() >= 3 || version.getMinor() >= 8)
		return new Point(ImageUtils.ALIGN_CENTER, ImageUtils.ALIGN_CENTER);
	else
		return new Point(ImageUtils.ALIGN_CENTER, ImageUtils.ALIGN_CENTER);
}

	// implementation for IImagePropertyRenderingInfo
ListBoxLafInfo.prototype.isPreservingAspectRatio = function(instance, propertyId, laf) {
	return true;
}

/////////////////////////////////

/**
 *	Draw a list item.
 *	@param itemPrototype the prototype for IImagePropertyRenderingInfo
 */
ListBoxLafInfo.prototype.drawListItem = function(itemPrototype, itemInstance, graphics) {
	var boundsInfo = this.getListItemBoundsInfo();
	//dump(boundsInfo);

	var markIconRect = this.getMarkIconBounds();
	if (markIconRect != null) {
		if (isImagePropertySet(this.listInstance.properties.markIcon)) {
			renderImage(ListBoxLafInfo.prototype, this.listInstance, this.laf, graphics, 
					markIconRect.x, markIconRect.y, "markIcon", true);
		} else {
			var markIcon = this.laf.getImage("listbox.mark.icon");
			if (markIcon) {
				var iconBounds = markIcon.getBounds();
				var scaledSize = getSizeScaledToSize(new Point(iconBounds.width, iconBounds.height), 
					new Point(markIconRect.width, markIconRect.height), true);
				drawImage(itemInstance, graphics, markIcon, markIconRect, true);
			}
		}
	}
	
	var textColor = this.laf.getColor("listitem.text");
	graphics.setForeground(textColor);

	for (var i in this.properties) {
		var prop = this.properties[i];
		var bounds = boundsInfo[i];
		
		//graphics.drawRectangle(bounds);
		
		var cellInfo = this.cellInfo[i];
		if (cellInfo.type == "text") {
	
			var font = this.laf.getFont(cellInfo.font);
			graphics.setFont(font);
			//println("font  for " + cellInfo.font + " is " + font);
			var text = chooseScalableText(itemInstance.properties[prop], font, bounds.width);
			var textHeight = Math.max(getFontHeight(font), bounds.height);
			var textBounds = graphics.formattedStringExtent(text, 
					new Point(bounds.width, textHeight), 0);

			var alignment = "Default";
			if ("alignmentInfo" in this.listInstance.properties)
				alignment = this.listInstance.properties.alignmentInfo[i];
			if (alignment == "Default" || alignment == null || alignment == undefined)
				alignment = this.cellInfo[i].alignment;
			
			var textOffset = this.getCellAlignedTextOffset(bounds, textBounds, alignment);
			//println("textOffset = " + textOffset);
			
			graphics.setFont(font);
			graphics.drawFormattedString(text, 
				new Rectangle(textOffset.x, textOffset.y, 
						bounds.width, bounds.height),
				Font.OVERFLOW_ELLIPSIS);
				
		} else if (cellInfo.type == "image") {
			renderImage(itemPrototype, itemInstance, this.laf, graphics, 
				bounds.x, bounds.y, "image", true);
			
		}
	}

}

ListBoxLafInfo.prototype.getListItemBounds = function(propertyId) {
	var boundsInfo = this.getListItemBoundsInfo();
	for (var i in this.properties) {
		if (this.properties[i] == propertyId)
			return boundsInfo[i];
	}
	return null;
}

/**
 *	Get cell info for property
 */
ListBoxLafInfo.prototype.getListItemCellInfo = function(propertyId) {
	for (var i in this.properties) {
		if (this.properties[i] == propertyId)
			return this.cellInfo[i];
	}
	return null;
}

ListBoxLafInfo.prototype.drawListBoxDecorations = function(graphics) {
	var lafInfo = this.getListBoxLafInfo();
	this.ensureListBounds();
	var width = this.listBounds.width;
	
	var maxDisplayed = this.maxDisplayableItems;

	var bottom = lafInfo.separatorRow;
	
	var version = getComponentVersions();
	// no decorations on 3.1+
	if (!this.laf.getBoolean("decorations", true))
		return;
		
	if (version.getMajor() >= 3 || version.getMinor() >= 8) {
		if (this.screen.x > 208) {
			graphics.setLineWidth(2);
			graphics.setLineDash([ 2 ]);
		}
		else {
			graphics.setLineWidth(1);
			graphics.setLineDash([ 1 ]);
		}
	} else {
		graphics.setLineDash(null);
		graphics.setLineWidth(1);
	}
	graphics.setForeground(this.laf.getColor("EEikColorControlText"));
	
	var col = lafInfo.separatorColumn;
	var drawSeparators = (col != null);
	var drawSeparatorRow = !listBoxHasSearchField(this.listInstance) && this.inListBox;
	if (this.inQueryDialog && this.lafInfo.isInitialSeparatorColumn)
		drawSeparators = false;
		
	if (drawSeparators) {
		//println("Separator at " + col);
		graphics.drawLine(/*this.listBounds.x +*/ col, this.listBounds.y, 
			/*this.listBounds.x +*/ col, this.listBounds.y + bottom);
		if (drawSeparatorRow)
			graphics.drawLine(/*this.listBounds.x +*/ col, this.listBounds.y + bottom, 
					/*this.listBounds.x +*/ width, this.listBounds.y + bottom);
	}

	// clean up
	graphics.setLineDash(null);
	graphics.setLineWidth(1);

}

ListBoxLafInfo.prototype.getSearchFieldBounds = function() {
	var lafInfo = this.getListBoxLafInfo();
	this.ensureListBounds();
	var width = this.listBounds.width;
	var listHeight = this.listBounds.height;

	var bottom = lafInfo.searchFieldRow;
	var height = listHeight - bottom;
	
	return new Rectangle(0, listHeight - height, width, height);
}

ListBoxLafInfo.prototype.drawSearchField = function(graphics) {
	var rect = this.getSearchFieldBounds();
	//println("drawSearchField rect="+rect);
	var lafInfo = this.getListBoxLafInfo();
	var drawPopup = this.isFormattedCellListBox;
	
	rect.x += lafInfo.inset;
	rect.width -= lafInfo.inset * 3;
	rect.height -= lafInfo.inset;

	var iconRect = this.laf.getRectangle("listbox.searchfield.icon");
	iconRect = this.scaleRectToLayout(iconRect, rect);

	var icon = this.laf.getImage("listbox.find.icon");

	var inputRect = this.laf.getRectangle("listbox.searchfield.input");
	inputRect = this.scaleRectToLayout(inputRect, rect);

	iconRect.x += rect.x;
	iconRect.y += rect.y;
	inputRect.x += rect.x;
	inputRect.y += rect.y;
	
	graphics.setBackground(this.laf.getColor("EEikColorControlBackground"));
	graphics.fillRectangle(rect);

	if (drawPopup) {
		graphics.setForeground(this.laf.getColor("EEikColorControlText"));
		graphics.drawRectangle(rect);

		graphics.setForeground(this.laf.getColor("control.shadow.inner"));
	
		rect.x++;
		rect.y++;
		graphics.drawLine(rect.x + rect.width, rect.y, rect.x + rect.width, rect.y + rect.height);
		graphics.drawLine(rect.x, rect.y + rect.height, rect.x + rect.width, rect.y + rect.height);
	
		rect.x++;
		rect.y++;
		graphics.drawLine(rect.x + rect.width, rect.y, rect.x + rect.width, rect.y + rect.height);
		graphics.drawLine(rect.x, rect.y + rect.height, rect.x + rect.width, rect.y + rect.height);
	}
	////////
	
	// input rect
	if (drawPopup) {
		inputRect.x += lafInfo.inset; 
		inputRect.width -= lafInfo.inset*2;
	}
	inputRect.y += lafInfo.inset;
	inputRect.height -= lafInfo.inset*2;

	graphics.setForeground(this.laf.getColor("EEikColorControlText"));
	graphics.drawRectangle(inputRect);

	// shadow
	inputRect.x--; 
	inputRect.y--;
	inputRect.width++;
	inputRect.height++;

	graphics.setForeground(this.laf.getColor("control.shadow.inner"));
	graphics.drawLine(inputRect.x, inputRect.y, inputRect.x + inputRect.width, inputRect.y);
	graphics.drawLine(inputRect.x, inputRect.y, inputRect.x, inputRect.y + inputRect.height);
	
	/////////
	
	// icon
	if (icon) {
		if (drawPopup) {
			iconRect.x += lafInfo.inset; 
		}
		iconRect.y += lafInfo.inset;
		var iconBounds = icon.getBounds();
		var scaledSize = getSizeScaledToSize(new Point(iconBounds.width, iconBounds.height), 
			new Point(iconRect.width, iconRect.height), true);
		//println("iconRect="+iconRect+", iconBounds="+iconBounds+", scaledSize="+scaledSize);
		var newRect = new Rectangle(
			iconRect.x + (iconRect.width - scaledSize.x) / 2, 
			iconRect.y + (iconRect.height - scaledSize.y) / 2, 
			scaledSize.x, scaledSize.y);
		drawImage(this.listInstance, graphics, icon, newRect, true);
		/*
		graphics.drawImage(icon, 
			0, 0, iconBounds.width, iconBounds.height,
			);
		*/
	}
}
	