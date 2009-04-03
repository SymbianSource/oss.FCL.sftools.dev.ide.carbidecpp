/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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
import com.nokia.sdt.utils.drawing.IFont;

/**
 * Interface for in-place label editing
 * Implementations can be for more than one label (e.g., CBA left and right labels)
 * Example javascript protoype implementing this interface:
 *	<blockquote><pre>
 *
 * function CDirectLabelEdit() {
 * }
 * 
 * CDirectLabelEdit.prototype.getPropertyPaths = function(instance) {
 * 
 * }
 * 
 * CDirectLabelEdit.prototype.getLabelBounds = function(instance, propertyPath, laf) {
 * 
 * }
 * 
 * CDirectLabelEdit.prototype.getLabelFont = function(instance, propertyPath, laf) {
 * 
 * }
 *	</pre></blockquote>
 */
public interface IDirectLabelEdit extends IDirectEdit {

	/**
	 * @param propertyPath the path of the property to edit
	 * @param laf provides parameters and SWT resources useful for rendering
	 * @return the <code>IFont</code> to use during edit
	 */
	IFont getLabelFont(String propertyPath, ILookAndFeel laf);
	
}
