/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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


include("containerLibrary.js")

/**
 *	Get the layout data.
 *
 *  If Layout Data is different than "Inherit", then the layout data is returned,
 *	if not, iterate over all parent relationship.
 *
 *	@param instance the instance where look in the layout data.
 *	@param propertyPath the name of the property to analize.
 *	@param propertyDefaultValue the default value of propertyPath which will 
 *	determine if the parent layout data should be reviewed or not.
 *	@return the layout data.
 */
function getLayoutData ( instance, propertyPath, propertyDefaultValue ) {

	var container = "com.nokia.carbide.uiq.CQikContainer";
	var sbb = "com.nokia.carbide.uiq.SystemBuildingBlock";
	var slot = "com.nokia.carbide.uiq.ItemSlot";
	var viewPage = "com.nokia.carbide.uiq.ViewPage";
	var containersGroup = "com.nokia.carbide.uiq.ContainersGroup";

	var parentInstance = instance.parent;
	var parentID = instance.parent.component.id;
	var properties = instance.properties;
	var layoutManagerInstance = null; 
	var layoutData = null;

	while ((parentInstance != null) && (parentID != viewPage) && (parentID != containersGroup)) {
		if (parentID == container) {
			//println("**************** parent: container");
			layoutManagerInstance = findImmediateChildByAttributeValue( parentInstance.children, "is-layout-manager", "true");
			if ( layoutManagerInstance == null ) {
				return propertyDefaultValue;
			}
			layoutData = instance.properties.layoutData[propertyPath];
			if ( layoutData != propertyDefaultValue ) {
				return layoutData
			}
			return getLayoutData ( parentInstance, propertyPath, propertyDefaultValue );
		}
		if (parentID == slot) {
			//println("**************** parent: slot");
			return getLayoutData ( parentInstance, propertyPath, propertyDefaultValue );
		}
		if (parentID == sbb) {
			//println("**************** parent: SBB");
			layoutManagerInstance = findImmediateChildByAttributeValue( parentInstance.parent.children, "is-layout-manager", "true");
			if ( layoutManagerInstance == null ) {
				return propertyDefaultValue;
			}
			layoutData = parentInstance.properties.layoutData[propertyPath];
			if ( layoutData != propertyDefaultValue ) {
				return layoutData
			}
			return getLayoutData ( parentInstance, propertyPath, propertyDefaultValue );
		}
		return propertyDefaultValue;
	}

	return getLayoutManagerLayoutData( instance.children, propertyPath, propertyDefaultValue );
}


/**
 *	Get the layout data of LayoutManager.
 *
 *  If Layout Manager is null, then the default value of the property will be
 *	returned.
 *
 *	@param children the children where look in the layout data.
 *	@param propertyPath the name of the property to analize.
 *	@param propertyDefaultValue the default value of propertyPath.
 *	@return the layout data of LayoutManager or propertyDefaultValue if 
 *	LayoutManager is null .
 */
function getLayoutManagerLayoutData( children, propertyPath, propertyDefaultValue ) {
	var layoutManager = findImmediateChildByAttributeValue( children, "is-layout-manager", "true");
	if (layoutManager != null) {
		//println("**************** sister: layout Manager");
		return layoutManager.properties.defaultLayoutData[propertyPath];
	}
	return propertyDefaultValue;
}