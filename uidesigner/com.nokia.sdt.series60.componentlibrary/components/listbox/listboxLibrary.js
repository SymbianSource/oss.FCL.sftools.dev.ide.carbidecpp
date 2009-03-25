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


include("../implLibrary.js")
include("../srcgenLibrary.js")

listboxLibraryStrings = getLocalizedStrings("listboxLibrary");

function fontForType(listboxProperties, laf) {
	// in the future, this needs to switch on properties.type and column
	return laf.getFont("NormalFont");
}

function isListBox(instance) {
	return instance.isInstanceOf("com.nokia.sdt.series60.CAknListBox");
}

function isQueryDialog(instance) {
	return instance.isInstanceOf("com.nokia.sdt.series60.CAknListQueryDialog")
	|| instance.isInstanceOf("com.nokia.sdt.series60.CAknListMultiQueryDialog");
}

function isListItemComponent(component) {
	return component.id == "com.nokia.sdt.series60.ListBoxItem";
}

function getListBoxItems(listboxInstance) {
	var kids = listboxInstance.children;
	return kids;
	/*
	var items = [];
	var idx = 0;
	for (var i in kids) {
		if (isListItemComponent(kids[i].component))
			items[idx++] = kids[i];
	}
	return items;
	*/
}

/*
function getListBoxSearchField(listboxInstance) {
	return findImmediateChildByComponentID(listboxInstance.children,
		"com.nokia.sdt.series60.CAknSearchField");			
}
*/

////////////////////

// source gen helpers

styleMap = 
	{
	// yup, columnCount == properties.length
	"CAknSingleStyleListBox" : 
		{
		"properties" : [ "text" ],
		"stringFormat" : [ "\\t%S" ],
		"columnCount" : 1,
		"basis" : "eikon",
		"lafData": {
				"height" : "single",
			},
		"cellInfo" : [
			{
				"type" : "text",
				"font" : "NormalFont",
				"alignment" : "ELeft",
			}
		]
		
		},
	"CAknSingleGraphicStyleListBox" :
		{
		"properties" : [ "image", "text" ],
		"stringFormat" : [ "%d\\t%S" ],
		"columnCount" : 2,
		"basis" : "eikon",
		"lafData": {
				"height" : "single",
			},
		"cellInfo" : [
			{ 
				"type" : "image",
				"size" : "small",
				"alignment" : "ERight",
			},
			{
				"type" : "text",
				"font" : "NormalFont",
				"alignment" : "ELeft",
			}
		]
		},
		
	// For multi-selection query dialogs, you can't configure the image.
	// It is devoted to the selection checkbox box.
	"CAknSinglePopupMenuGraphicStyleListBoxMulti" :
		{
		"properties" : [ "text" ],
		"stringFormat" : [ "%S\\t" ],
		"columnCount" : 1,
		"basis" : "eikon",
		"lafData": {
				"height" : "single",
			},
		"cellInfo" : [
			{
				"type" : "text",
				"font" : "NormalFont",
				"alignment" : "ELeft",
			}
		]
		},
		
	"CAknSingleNumberStyleListBox" :
		{
		"properties" : [ "number", "text" ],
		"stringFormat" : [ "%d\\t%S" ],
		"columnCount" : 2,
		"basis" : "eikon",
		"lafData": {
				"height" : "single",
			},
		"cellInfo" : [
			{ 
				"type" : "text",
				"font" : "AnnotationFont",
				"alignment" : "ERight",
			},
			{
				"type" : "text",
				"font" : "NormalFont",
				"alignment" : "ELeft",
			}
		]
		},
		
	"CAknSingleHeadingStyleListBox" :
		{
		"properties" : [ "headingText", "text" ],
		"stringFormat" : [ "%S\\t%S" ],
		"columnCount" : 2,
		"basis" : "eikon",
		"lafData": {
				"height" : "single",
			},
		"cellInfo" : [
			{ 
				"type" : "text",
				"font" : "DenseFont",
				"alignment" : "ERight",
			},
			{
				"type" : "text",
				"font" : "NormalFont",
				"alignment" : "ELeft",
			}
		]
		
		},

	"CAknSingleGraphicHeadingStyleListBox" :
		{
		"properties" : [ "image", "headingText", "text" ],
		"stringFormat" : [ "%d\\t%S\\t%S" ],
		"columnCount" : 3,
		"basis" : "eikon",
		"lafData": {
				"height" : "single",
			},
		"cellInfo" : [
			{
				"type" : "image",
				"alignment" : "ECenter",
			},
			{ 
				"type" : "text",
				"font" : "DenseFont",
				"alignment" : "ERight",
			},
			{
				"type" : "text",
				"font" : "NormalFont",
				"alignment" : "ELeft",
			}
		]
		
		},

	// For multi-selection query dialogs, you can't configure the image.
	// It is devoted to the selection checkbox box.
	"CAknSingleGraphicHeadingPopupMenuStyleListBoxMulti" :
		{
		"properties" : [ "headingText", "text" ],
		"stringFormat" : [ "\\t%S\\t%S" ],
		"columnCount" : 2,
		"basis" : "eikon",
		"lafData": {
				"height" : "single",
			},
		"cellInfo" : [
			{ 
				"type" : "text",
				"font" : "DenseFont",
				"alignment" : "ERight",
			},
			{
				"type" : "text",
				"font" : "NormalFont",
				"alignment" : "ELeft",
			}
		]
		
		},

	"CAknSingleLargeStyleListBox" :
		{
		"properties" : [ "image", "text" ],
		"stringFormat" : [ "%d\\t%S" ],
		"columnCount" : 2,
		"basis" : "eikon",
		"lafData": {
				"height" : "single.large",
			},
		"cellInfo" : [
			{
				"type" : "image",
				"alignment" : "ECenter",
			},
			{ 
				"type" : "text",
				"font" : "NormalFont",
				"alignment" : "ELeft",
			},
		]
		
		},
	"CAknSingleNumberHeadingStyleListBox" :
		{
		"properties" : [ "number", "headingText", "text" ],
		"stringFormat" : [ "%d\\t%S\\t%S" ],
		"columnCount" : 3,
		"basis" : "eikon",
		"lafData": {
				"height" : "single",
			},
		"cellInfo" : [
			{
				"type" : "text",
				"font" : "DenseFont",
				"alignment" : "ERight",
			},
			{
				"type" : "text",
				"font" : "DenseFont",
				"alignment" : "ELeft",
			},
			{
				"type" : "text",
				"font" : "NormalFont",
				"alignment" : "ELeft",
			}
		]
		},
	"CAknDoubleStyleListBox" :
		{
		"properties" : [ "text", "secondaryText" ],
		"stringFormat" : [ "\\t%S\\t%S" ],
		"columnCount" : 2,
		"basis" : "avkon",
		"lafData": {
				"height" : "double",
			},
		"cellInfo" : [
			{
				"type" : "text",
				"font" : "NormalFont",
				"alignment" : "ELeft",
			},
			{
				"type" : "text",
				"font" : "DenseFont",
				"alignment" : "ELeft",
			}
		]
		
		},
	"CAknDoubleStyle2ListBox" :
		{
		"properties" : [ "text", "secondaryText" ],
		"stringFormat" : [ "\\t%S\\t%S" ],
		"columnCount" : 2,
		"basis" : "avkon",
		"lafData": {
				"height" : "double",
			},
		"cellInfo" : [
			{
				"type" : "text",
				"font" : "NormalFont",
				"alignment" : "ELeft",
			},
			{
				"type" : "text",
				"font" : "NormalFont",
				"alignment" : "ELeft",
			}
		]
		},
	"CAknDoubleNumberStyleListBox" :
		{
		"properties" : [ "number", "text", "secondaryText" ],
		"stringFormat" : [ "%d\\t%S\\t%S" ],
		"columnCount" : 3,
		"basis" : "avkon",
		"lafData": {
				"height" : "double",
			},
		"cellInfo" : [
			{
				"type" : "text",
				"font" : "AnnotationFont",
				"alignment" : "ECenter",
			},
			{
				"type" : "text",
				"font" : "NormalFont",
				"alignment" : "ELeft",
			},
			{
				"type" : "text",
				"font" : "DenseFont",
				"alignment" : "ELeft",
			}
		]
		},
	"CAknDoubleTimeStyleListBox" :
		{
		"properties" : [ "time1Text", "time2Text", "text", "secondaryText" ],
		"stringFormat" : [ "%S\\t%S\\t%S\\t%S" ],
		"columnCount" : 4,
		"basis" : "avkon",
		"lafData": {
				"height" : "double",
			},
		"cellInfo" : [
			{
				"type" : "text",
				"font" : "TimeFont",
				"alignment" : "ERight",
			},
			{
				"type" : "text",
				"font" : "DenseFont",
				"alignment" : "ERight",
			},
			{
				"type" : "text",
				"font" : "NormalFont",
				"alignment" : "ELeft",
			},
			{
				"type" : "text",
				"font" : "DenseFont",
				"alignment" : "ELeft",
			}
		]
		},
	"CAknDoubleLargeStyleListBox" :
		{
		"properties" : [ "image", "text", "secondaryText" ],
		"stringFormat" : [ "%d\\t%S\\t%S" ],
		"columnCount" : 3,
		"basis" : "avkon",
		"lafData": {
				"height" : "double",
			},
		"cellInfo" : [
			{
				"type" : "image",
				"alignment" : "ECenter",
			},
			{
				"type" : "text",
				"font" : "NormalFont",
				"alignment" : "ELeft",
			},
			{
				"type" : "text",
				"font" : "DenseFont",
				"alignment" : "ELeft",
			}
		]
		},
	};

// these styles are functionally identical (used for property sets too)

styleAliases = {
"CAknSinglePopupMenuStyleListBox" : "CAknSingleStyleListBox",
"CAknSingleGraphicPopupMenuStyleListBox" : "CAknSingleGraphicStyleListBox",
"CAknSingleGraphicPopupMenuStyleListBoxMulti" : "CAknSingleStyleListBox",
"CAknSingleHeadingPopupMenuStyleListBox" : "CAknSingleHeadingStyleListBox",
"CAknSingleGraphicHeadingPopupMenuStyleListBox" : "CAknSingleGraphicHeadingStyleListBox",
"CAknSingleGraphicHeadingPopupMenuStyleListBoxMulti" : "CAknSingleHeadingStyleListBox",
"CAknDoublePopupMenuStyleListBox" : "CAknDoubleStyleListBox",
"CAknDoubleLargeGraphicPopupMenuStyleListBox" : "CAknDoubleLargeStyleListBox",
"CAknDouble2PopupMenuStyleListBox" : "CAknDoubleStyle2ListBox",

}

// fill in any gaps
for ( var i in styleAliases ) {
	if (!styleMap[i])
		styleMap[i] = styleMap[ styleAliases[i] ];
}

propertyToTypeMap = 
	{
	"text":	
		{
		"cppArg" : "aMainText",
		"type" : "string"
		},
	"secondaryText":	
		{
		"cppArg" : "aSecondaryText",
		"type" : "string"
		},
	"headingText":
		{
		"cppArg" : "aHeadingText",
		"type" : "string"
		},
	"number":
		{
		"cppArg" : "aNumber",
		"type" : "number"
		},
	"image":
		{
		// the icon index inside the icon array
		"cppArg" : "aIconIndex",
		"type" : "image"
		},
	"time1Text":
		{
		"cppArg" : "aTime1Text",
		"type" : "string"
		},
	"time2Text":
		{
		"cppArg" : "aTime2Text",
		"type" : "string"
		}
	}
	
typeToCppTypeMap = 
	{
	"string" : "const TDesC&",
	"number" : "TInt",
	"image"  : "TInt"
	};
	
// Get the function signature for format arguments (inside arg list)
function createParameterizedItemLSignature(listInstance, indentString) {
	var props = styleMap[listInstance.properties.style]["properties"];
	var proto = "";
	var first = true;
	for (var i in props) {
		if (!first)
			proto += ",";
		else
			first = false;
		proto += "\n" + indentString;
		var prop = props[i];
		var type = propertyToTypeMap[prop];
		proto += typeToCppTypeMap[type.type] + " " + type.cppArg;
	}
	return proto;
}

/**
 *	We only support one numeric column, either the number or the 
 *	image index.  Get that property, or null if none.
 */
function getParameterizedItemLNumericColumnProperty(listInstance) {
	var props = styleMap[listInstance.properties.style].properties;
	for (var i in props) {
		var prop = props[i];
		if (prop == "image" || prop == "number")
			return prop;
	}
	return null;
}

/**
 *	Get doxygen-style comment for numeric/image column parameter
 */
function getParameterizedItemLNumericColumnComment(listInstance) {
	var prop = getParameterizedItemLNumericColumnProperty(listInstance);
	if (prop == "number")
		return "@param aNumber the number value";
	else if (prop == "image") 
		return "@param aIconIndex the index in the icon array, or -1";
	else
		return "";
}

/**
 *	Get the parameter declaration for the numeric/image column parameter
 */
function getParameterizedItemLNumericColumnParameterName(listInstance) {
	var prop = getParameterizedItemLNumericColumnProperty(listInstance);
	if (prop == "number")
		return "aNumber";
	else if (prop == "image") 
		return "aIconIndex";
	else
		return null;

}

/**
 *	Get the parameter declaration for the numeric/image column parameter
 */
function getParameterizedItemLNumericColumnParameter(listInstance, inMacro) {
	var paramName = getParameterizedItemLNumericColumnParameterName(listInstance);
	if (paramName != null) {
		if (!inMacro)
			return ", TInt " + paramName;
		else
			return "TInt " + paramName;
	} else
		return "";
}

function getListItemStringFormat(listInstance) {
	// the string format may have a leading tab for list box but not for query dialog
	var format = "" + styleMap[listInstance.properties.style].stringFormat;
	if (isQueryDialog(listInstance) && format.substring(0,2) == "\\t")
		format = format.substring(2);
	return format;
		
}

/**
 *	Return the argument text to ParameterizedItemL().
 */
function getParameterizedItemLArguments(listInstance, arrayVar, numericParameterName) {
    var els = ""; 
	var props = styleMap[listInstance.properties.style].properties;
	var first = true;
	var idx = 0;
	var onLine = 0;
	for (var i in props) {
		var prop = props[i];
		var type = propertyToTypeMap[prop];
		
		if (!first) {
			els += ", ";
			if (onLine >= 2)  {
				els += "\n\t\t";
				onLine = 0;
			}
		}
		else
			first = false;

		if (type.type == "string") {
			els += "( *array ) [ " + idx + " ]";
			idx++;
		} else if (type.type == "number") {
			els += numericParameterName;
		} else if (type.type == "image") {
			// this has an icon index
			els += numericParameterName;
		}
		onLine++;
	} 
	return els;
}

/**
 *	Get format arguments to the TBuf.listString() call
 */
function getParameterizedItemLFormatArguments(listInstance) {
	var args = "";
	var props = styleMap[listInstance.properties.style].properties;
	var first = true;
	for (var i in props) {
		var prop = props[i];
		var type = propertyToTypeMap[prop];
		if (!first)
			args += ", ";
		else
			first = false;
		if (type.type == "string")
			args += "&" + type.cppArg;
		else
			args += type.cppArg;
	}
	return args;
}

//////////////////////////////////////////////

/**
 *	Create a table of list item images.  
 *	These contain the implicitly referenced images,
 *	including the mark icon.
 */
function setupListItemImageInfo(listInstance, listBoxImageInfo) {
	// add the markable list icon
	if ("markIcon" in listInstance.properties
	 	&& listInstance.properties.type == "EAknListBoxMarkableList" ) {
		if (isImagePropertySet(listInstance.properties.markIcon)) {
			listBoxImageInfo.registerImageProperty(listInstance.properties["markIcon"]);
		} else {
			listBoxImageInfo.registerAvkonSystemImage("qgn_indi_marked_add", "qgn_indi_marked_add_mask");
		}
	}
	
	var kids = getListBoxItems(listInstance);
	
	for (var i in kids) {
		var kid = kids[i];

		if (kid.properties["image"]) {
			listBoxImageInfo.registerImageProperty(kid.properties["image"]);
		}
	}
}

/**
 *	Create a table of extra list item images.  
 *	
 */
function setupExtraListImages(listInstance, listBoxImageInfo) {
	var images = listInstance.properties.extraImages;
	
	for (var i in images) {
		listBoxImageInfo.registerImageProperty(images[i]);
	}

}

function generateImageListEnums(listInstance, listBoxImageInfo) {

}

function validateListItemImage(itemInstance, propertyPath, image) {
	//println("validating image " + itemInstance + " / " + propertyPath + " = " +image.bmpfile);
	var error = null;
	if (!isImagePropertySet(image)) {
		error = createSimpleModelError(itemInstance, propertyPath, 
			listboxLibraryStrings.getString("ListBoxLibrary.ImageRequired"), 
			[ itemInstance.name ]);
	}
	//println("message: " + (error!= null?error.getMessage():"<null>"));
	return error;
}

/**
 *	Validate the contents of a list item, for use by IComponentValidator
 */
function validateListItemData(itemInstance) {
	//println("validate data...");
	var errors = null;
	var listInstance = itemInstance.parent;
	var props = styleMap[listInstance.properties.style].properties;
	for (var i in props) {
		var prop = props[i];
		var type = propertyToTypeMap[prop];
		var error = null;
		
		if (type.type == "image") {
			var image = itemInstance.properties[prop];
			error = validateListItemImage(itemInstance, prop, image);
		}
		
		if (error != null) {
			if (errors == null)
				errors = new java.util.ArrayList();
			errors.add(error);
		}
	}
	return errors;

}

/**
 *	Validate a change to a list item property, for use by
 *	IComponentValidator
 */
function validateListItemPropertyChange(itemInstance, propertyPath, newValue) {
	//println("property change...");
	var error = null;
	if (propertyPath == "image") {
		var errorMsg = validateListItemImage(itemInstance, propertyPath, newValue);
		if (errorMsg != null)
			error = errorMsg.getMessage();
	}
	//println("message: " + error);
	return error;
}

/**
 *	Get the effective number of columns, according to S60
 */
function getListBoxColumnCount(listInstance) {
	return styleMap[listInstance.properties.style].columnCount;
}

/**
 *	Set up the settings for the columns
 */
function generateOneColumnSetup(contribs, phase, listInstance, columnNumber, 
		columnAlignment, alignmentFormat) {
	if (columnAlignment != "Default") {
		addFormattedContrib(contribs, phase, null,  0,
			alignmentFormat,
			[ listInstance.memberName, columnNumber, 
				"CGraphicsContext::" + columnAlignment ]);
	}
}

/**
 *	Set up the settings for the columns
 */
function generateColumnSetup(contribs, phase, listInstance) {
	var basis = styleMap[listInstance.properties.style].basis;
	var columns = styleMap[listInstance.properties.style].columnCount;
	var alignmentFormat;
	if (basis == "eikon")  {
		alignmentFormat = "{0}->ItemDrawer()->ColumnData()->SetColumnAlignmentL(\n\t\t{1}, {2} );\n";
	} else if (basis == "avkon") {
		alignmentFormat = "{0}->ItemDrawer()->FormattedCellData()->SetSubCellAlignmentL(\n\t\t{1}, {2} );\n";
	}
	
	for (var i = 0 ; i < columns; i++) {
		generateOneColumnSetup(contribs, phase, 
			listInstance, i, listInstance.properties.alignmentInfo[i], 
			alignmentFormat);
	}
}

function isFormattedCellListBox(listInstance) {
	var basis = styleMap[listInstance.properties.style].basis;
	return (basis == "avkon");
}

function getListItemImageProperies(itemInstance) {
	var listInstance = itemInstance.parent;
	var properties = styleMap[listInstance.properties.style].properties;
	var cellInfo = styleMap[listInstance.properties.style].cellInfo;
	var images = [];
	for (var i in properties) {
		if (cellInfo[i].type == "image")
			images[images.length] = properties[i];
	}
	return images;
}

function getListItemLabelProperties(itemInstance) {
	var listInstance = itemInstance.parent;
	var properties = styleMap[listInstance.properties.style].properties;
	var cellInfo = styleMap[listInstance.properties.style].cellInfo;
	var labels = [];
	for (var i in properties) {
		if (cellInfo[i].type == "text")
			labels[labels.length] = properties[i];
	}
	return labels;

}

function listBoxHasSearchField(listInstance) {
	return listInstance.properties.hasSearchField;
}

function getListBoxSearchFieldName(listInstance) {
	return listInstance.memberName + "SearchField";
}

////////////////////

function isTonePropertyAvailableInQuery() {
	var version = getComponentVersions();
	if (version.major == 2 && version.minor < 1) {
		return false;
	}
	return true;
}

include("listboxLafInfo.js")


