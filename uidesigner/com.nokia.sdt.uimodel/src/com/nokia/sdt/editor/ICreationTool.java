/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.sdt.editor;

import com.nokia.sdt.component.adapter.IInfoItems;
import com.nokia.sdt.datamodel.IDesignerDataModel;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * The interface for a single entry in the palette.
 * It may be adaptable to {@link IInfoItems} for the optional palette info widget support
 * It should be adaptable to IComponent for containment query to work correctly.
 */
public interface ICreationTool extends IAdaptable {
	
	/**
	 * @return String used as the label of the tool entry in the palette
	 */
	String getLabel();
	
	/**
	 * @return String used as the drawer label containing the tool entry in the palette
	 */
	String getCategory();
	
	/**
	 * @return String used as the description (or tooltip) of the entry in the palette
	 */
	String getDescription();
	
	/**
	 * @return ImageDescriptor for the small icon (16X16 pixels) of the entry in the palette
	 */
	ImageDescriptor getIcon16();
	
	/**
	 * @return ImageDescriptor for the large icon (24X24 pixels) of the entry in the palette
	 */
	ImageDescriptor getIcon24();
	
	/**
	 * @param dataModel the IDesignerDataModel
	 * @return EObject a new object created when the tool is used to create a new item
	 */
	EObject createNewObject(IDesignerDataModel dataModel);
	
	/**
	 * @param parent the object in the data model to which the new object is added
	 */
	void addNotify(EObject parent);
}