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

include("../../containerLibrary.js")
include("../../implLibrary.js")  

function ViewPage() {
}

////////////////////////////////////////////////////////////////////////////////
// IPropertyListener

ViewPage.prototype.propertyChanged = function(instance, property) {
	if (property != "size" && property != "location"){
		instance.parent.forceRedraw();
	}
}

////////////////////////////////////////////////////////////////////////////////
// IQueryContainment

ViewPage.prototype.canContainComponent = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent);
}


ViewPage.prototype.canContainChild = function(instance, child) {
	return canContainComponent(instance, child.component);
}


ViewPage.prototype.canRemoveChild = function(instance, child) {
	return false;
}


ViewPage.prototype.isValidComponentInPalette = function(instance, otherComponent) {
	return canContainComponent(instance, otherComponent) == null;
}


function canContainComponent(instance, component) {
	if (isCQikContainerComponent(component)){
		if (instance.children.length < 1)
			return null;
		else
			return buildSimpleContainmentErrorStatus(
				lookupString("iQueryContaimentError2"), new Array( component.friendlyName ));
	} else {
		return buildSimpleContainmentErrorStatus(
			lookupString("iQueryContaimentError"), new Array( component.friendlyName ));			
	}
}


function isCQikContainerComponent(instance) {
	return instance.attributes["is-qikcontainer"] == "true";
}

////////////////////////////////////////////////////////////////////////////////
// IComponentValidator

ViewPage.prototype.validate = function(instance) {
	return validateTabCaptionLength(instance, "tabCaption", instance.properties.text);
}

ViewPage.prototype.queryPropertyChange = function(instance, propertyPath, newValue) {
	var aux = validateIsSimplePageView(instance, propertyPath, newValue);
	if(aux != null){
		return aux;
	}else{
		return validateTabCaptionLength(instance, propertyPath, newValue);
	}
}

function validateIsSimplePageView(instance, propertyPath, newValue){
	var viewinstanceproperties;
	var issimpleview = false;
	if (instance.rootContainer.isInstanceOf("com.nokia.carbide.uiq.CQikView")) {
		viewinstanceproperties = instance.rootContainer.properties;
	}

	if(viewinstanceproperties.type == "singlePage"){
		issimpleview = true;
	}
	if ( (propertyPath == "tabCaption" || propertyPath == "tabImage") && issimpleview ) {
		return [createSimpleModelError(instance, 
				propertyPath,
				lookupString("isSimpleViewValidationMessage"),
				instance.name )
			   ];
	}
	return null;
	
}

function validateTabCaptionLength (instance, propertyPath, newValue) {
	if (propertyPath == "tabCaption") {
		if (newValue == "") {
			return [createSimpleModelError(instance, 
					propertyPath,
					lookupString("tabCaptionValidationError"),
					instance.name )
				   ];
		}
	}
	return null;
}


////////////////////////////////////////////////////////////////////////////////
//IDirectLabelEdit

function getPageTitleBounds(laf) {
	var pageTitleBounds = laf.getRectangle("ViewPage.bounds");
	return new Rectangle (pageTitleBounds.x,
							  pageTitleBounds.y,
							  pageTitleBounds.width,
							  laf.getInteger("tab.total.height",25));
}

setupCommonDirectLabelEditing(ViewPage.prototype, 
	"tabCaption", 
	function (instance, laf, propertyId) {
		return getPageTitleBounds(laf);
	},
	function(instance, laf) { return laf.getFont("ArialNarrowFont"); } 
	)
