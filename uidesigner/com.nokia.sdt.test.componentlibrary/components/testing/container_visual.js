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

/*
 * Visual static
 *
 * globals available are:
 *
 *		instance (WrappedInstance)
 *		properties (WrappedProperties) 
 *			properties["name"] or properties.name retrieves values
 *		parent (WrappedInstance)
 *		children (WrappedInstance[])
 *		
 * Rendering globals:
 *		graphics (wrapped SWT GC)
 *		Colors (object from which getColor(r,g,b) is available)
 *		Fonts (object from which getFont("path") is available)
 *		Images (object from which newImage(device,w,h) is available)
 */

function Visual() {
}

Visual.prototype.draw = function(instance, laf, gc) {

	var width = instance.properties["size"].width;
	var height = instance.properties.size["height"]
	var r = new Rectangle(0, 0, width/2, height/2)
	
//	gc.setForeground(Colors.getColor(48, 64, 64))
//	gc.setLineWidth(2)
//	gc.drawRectangle(r)			
}

Visual.prototype.getPreferredSize = function(wHint, hHint) {
	return null; // needs implementation	
}

