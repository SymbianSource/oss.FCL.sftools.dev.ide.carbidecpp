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


include("cbaLibrary.js")

function CBAVisual() {
}

CBAVisual.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;
	var middleText = getMiddleText(instance, laf);
	var leftText = getLeftText(instance);
	var rightText = getRightText(instance);
	if (middleText == leftText && middleText != ""){
		middleText = ".";
	}
	drawCBA(leftText, rightText, middleText,
		new Point(0, 0), new Point(properties.size.width, properties.size.height), laf, graphics);
}

function isRestricted(instance) {
	return instance.component.id == "com.nokia.sdt.series60.CBARestricted";
}

function isCustom(info, instance) {
	if (isRestricted(instance)){
		return false;
	}
	
	if (info.editableValue == "com.nokia.sdt.series60.CBA.Type.CUSTOM") {
		return true;
	}
	
	return false;
}

function getLeftText(instance) {
	var info = instance.properties.info;
	if (isCustom(info, instance)) {
			return info.leftText;
	}
	else if (isRestricted(instance)) {
		if ((info == "R_AVKON_SOFTKEYS_OPTIONS_BACK") || (info == "R_AVKON_SOFTKEYS_OPTIONS_EXIT"))
			return lookupString("text_softkey_option");
		else if (info == "R_AVKON_SOFTKEYS_OK_BACK")
			return lookupString("text_softkey_ok");
		else if (info == "R_AVKON_SOFTKEYS_BACK")
			return "";
	}
	else {
		return lookupString("text_" + info.leftId);
	}
}

function getRightText(instance) {
	var info = instance.properties.info;
	if (isCustom(info, instance)) {
		return info.rightText;
	}
	else if (isRestricted(instance)) {
		if ((info == "R_AVKON_SOFTKEYS_OPTIONS_BACK") || (info == "R_AVKON_SOFTKEYS_OK_BACK") || (info == "R_AVKON_SOFTKEYS_BACK"))
			return lookupString("text_softkey_back");
		else if (info == "R_AVKON_SOFTKEYS_OPTIONS_EXIT")
			return lookupString("text_softkey_exit");
	}
	else {
		if (info.type == "R_AVKON_SOFTKEYS_EXIT") // treat this one special because it uses the "back" id
			return lookupString("text_EAknSoftkeyExit");
			
		return lookupString("text_" + info.rightId);
	}
}

function getMiddleText(instance, laf) {

	if (isRestricted(instance)){
		return "";
	}
	
	if (hasMSKSupport() && laf.getBoolean("draw.msk", false)){
		info = instance.properties.info;
		if (info.middleId == "EAknSoftkeyContextOptions") {
			return "_";  // sub menu of left soft key, will use icon
		}
		else if (isCustom(info, instance))
			return info.middleText;
		else
			return lookupString("text_" + info.middleId);
	} else {
		return "";
	}
}


CBAVisual.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return null; // needs implementation	
}

CBAVisual.prototype.getPropertyPaths = function(instance) {
	if (hasMSKSupport()){
		return new Array("info.leftText", "info.rightText", "info.middleText");
	} else {
		return new Array("info.leftText", "info.rightText");
	}
}

CBAVisual.prototype.getLabelBounds = function(instance, propertyPath, laf) {
	var properties = instance.properties;
	var margin = laf.getInteger("control.pane.text.margin", 5);
	var width = properties.size.width - (2*margin);
	var height = properties.size.height;
	if (laf.getBoolean("is.portrait", false)) {
		var numButtons = 2;
		if (hasMSKSupport()){
			numButtons = 3;
		}
		if (propertyPath.equals("info.leftText")) {
			return new Rectangle(margin, 0, width / numButtons, height);
		} 
		else if (hasMSKSupport() && propertyPath.equals("info.middleText")) {
			return new Rectangle(margin + (width / numButtons), 0, width / numButtons, height);
		}
		else {
			return new Rectangle(margin + (width / 2), 0, width / 2, height);
		}
	}
	else {
		// landscape doesn't support MSK
		var font = laf.getFont("control.pane.font");
		var fontHeight = font.getHeight();
		var sbar2Bounds = laf.getRectangle("status.bar2.bounds");
		var rect = new Rectangle(0, 1, width-margin, fontHeight);
		if (propertyPath.equals("info.leftText")) {
			rect.y = height - sbar2Bounds.height + 1;
			rect.height = sbar2Bounds.height;
		}

		return rect;
	}
	
	return new Rectangle(0, 0, 0, 0);
}

CBAVisual.prototype.getLabelFont = function(instance, propertyPath, laf) {
	return laf.getFont("control.pane.font");
}

function hasMSKSupport(){
	var version = getComponentVersions();
	if (version.getMajor() > 3 || (version.getMajor() == 3 && version.getMinor() >= 2)){
		return true;
	}
	
	return false;
}
