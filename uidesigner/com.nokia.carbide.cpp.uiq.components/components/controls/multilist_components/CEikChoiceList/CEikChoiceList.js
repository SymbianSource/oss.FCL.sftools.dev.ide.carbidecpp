/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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
* START_USECASES: CU10 END_USECASES
*
*
*/

include("../../../containerLibrary.js")
include("../../../renderLibrary.js")


function CEikChoiceList() {
}

////////////////////////////////////////////////////////////////////////////////
//IQueryContainment

CEikChoiceList.prototype.canContainComponent = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent);
}


CEikChoiceList.prototype.canContainChild = function(instance, child) {
	return canContainComponent(instance, child.component);
}


CEikChoiceList.prototype.canRemoveChild = function(instance, child) {
	if (instance.children.length == 1 ) {
		return null;
	}else{
		return true;
	}
		
}


CEikChoiceList.prototype.isValidComponentInPalette = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent) == null;
}


function canContainComponent(instance, component) {
	if (isChoiceListItemComponent(component)) 
		{
		if (instance.children.length == 1 ) {
			instance.properties.activeItem = instance.children[0].properties.name;
		} 
		return null;
		} 
	else {
		return buildSimpleContainmentErrorStatus(
			lookupString("generalContainmentError"), new Array( component.friendlyName, instance.name ));
		}			
}


function isChoiceListItemComponent(component) {
	return component.id == "com.nokia.carbide.uiq.ChoiceListItem";
}

////////////////////////////////////////////////////////////////////////////////
//IVisualAppearance


CEikChoiceList.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;
	
	var font = laf.getFont("NormalFont");
	graphics.setFont(font);
	var flags = getFlags(instance);

	text = "";
	
	var refInstance = lookupInstanceByName(instance.properties.activeItem);
	var referenceNumber = instance.name.slice(instance.name.indexOf("Ref")+3 -instance.name.length);
	var ccollectionInstance = lookupInstanceByName(instance.name.replace("Ref"+referenceNumber,""));
	

	if(properties.isVisible != null && properties.isVisible == false)
	      return;
	
	if (refInstance != null) text = refInstance.properties.label;
	var rect = instance.getRenderingBounds();	
	if(properties.arrowVisible && ccollectionInstance.children.length > 1){
		rect.width -= 25;
	}
	if(rect)
		{
		rect.x=3;
		rect.y=0;
		}

	graphics.setForeground(laf.getColor("EEikColorButtonText"));
	if(text){
		graphics.drawFormattedString(text, rect, flags);
	}
	if(properties.arrowVisible && ccollectionInstance.children.length > 1){
		rect.width += 25;
	}
	if(properties.arrowVisible && ccollectionInstance.children.length > 1){
		polygon = [rect.width-17, 9, rect.width-10, 9,rect.width-10, 14,rect.width-5, 14, rect.width-13, 22,rect.width-22, 14,rect.width-17, 14];
		graphics.setBackground(Colors.getColor(255, 255, 255));
		graphics.fillPolygon(polygon);
		graphics.drawPolygon(polygon);
	}
}


CEikChoiceList.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	   var width = wHint > 0? wHint : 60;
	   var height = hHint > 0? hHint : 24;
	   return new Point(width, height);
}



function getFlags(instance){
	var properties = instance.properties;
	var flags = 0;
	
	flags |= Font.ALIGN_LEFT;
	flags |= Font.VERTICAL_ALIGN_CENTER;
	flags |= Font.ANTIALIAS_OFF;
	flags |= Font.OVERFLOW_ELLIPSIS;

	return flags;
}

////////////////////////////////////////////////////////////////////////////////
//IComponentValidator

CEikChoiceList.prototype.validate = function(instance) {
	if (instance != null || instance != "") {
		var properties = instance.properties;
		if ( instance.isInstanceOf("com.nokia.carbide.uiq.CEikChoiceList")&&(properties.activeItem == "" || properties.activeItem == null) ) {
			var modelMessage = newModelMessage(IStatus.WARNING,formatString(lookupString("validate.flags"),
								[instance.name ]),instance, "flags", null);
			return [ modelMessage ];					
		}
		if ( instance.isInstanceOf("com.nokia.carbide.uiq.CEikChoiceList")&&(properties.incrementalMatching == true && properties.inhibitPopout == true) ) {
			var modelMessage = newModelMessage(IStatus.WARNING,formatString(lookupString("validateIncrementalMatching"),
								[instance.name ]),instance, "flags", null);
			return [ modelMessage ];					
		}
	}
	return null;
}

CEikChoiceList.prototype.queryPropertyChange = function(instance, propertyId, newValue) {
	return null;
}

////////////////////////////////////////////////////////////////////////////////
//IPropertyListener

CEikChoiceList.prototype.propertyChanged = function(instance, property) {
	if (property == "activeItem" || property == "label"){
		instance.forceRedraw();
	}
}



