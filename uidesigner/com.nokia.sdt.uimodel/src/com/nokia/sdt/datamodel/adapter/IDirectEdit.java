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

package com.nokia.sdt.datamodel.adapter;

import com.nokia.sdt.displaymodel.ILookAndFeel;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Rectangle;

/**
 * Interface for in-place editing of properties.
 * Implementations can be for more than one label (e.g., CBA left and right labels)
 */
public interface IDirectEdit extends IModelAdapter {

	/**
	 * @return the paths of the properties to edit
	 * @see com.nokia.sdt.datamodel.util.ModelUtils#readProperty(EObject, String, boolean)
	 */
	String[] getPropertyPaths();
	
	/**
	 * @param propertyPath the path of the property to edit
	 * @param laf provides parameters and SWT resources useful for rendering
	 * @return the bounds encompassing the visual area of the property
	 */
	Rectangle getVisualBounds(String propertyPath, ILookAndFeel laf);
}
