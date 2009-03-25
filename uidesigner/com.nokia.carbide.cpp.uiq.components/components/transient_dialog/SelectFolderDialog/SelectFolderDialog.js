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
* START_USECASES: CU20 END_USECASES
*
*
*/


include("../../implLibrary.js")  //for setupCommonDirectLabelEditing function
include("../../renderLibrary.js")//for getBackgroundColor function

function SelectFolderDialog() {
}

////////////////////////////////////////////////////////////////////////////////
// IVisualAppearance

SelectFolderDialog.prototype.draw = function(instance, laf, graphics) {
		var properties = instance.properties;
		var image = laf.getImage("select.folder.dialog");	
		if (image != null) {
			graphics.drawImage(image, 0 , 0);
		}
		var flags = Font.ALIGN_LEFT;	
	    flags |= Font.ANTIALIAS_OFF;
		var font = laf.getFont("ArialNarrowFont");
		graphics.setFont(font);

		var rect = new Rectangle (laf.getInteger("select.folder.dialog.text.bound.x",55),
								  laf.getInteger("select.folder.dialog.text.bound.y",18),
								  laf.getInteger("select.folder.dialog.text.width",184),
								  laf.getInteger("select.folder.dialog.text.height",30));
		
		var appUi = getRootModelInstanceOfType("com.nokia.carbide.uiq.ApplicationUI");

		if (appUi != null && appUi.properties.caption != "") { 
			graphics.drawFormattedString(appUi.properties.caption, rect, flags, 0);	
		} else {
			graphics.drawFormattedString(getProjectName(), rect, flags, 0);	
		}		
}

SelectFolderDialog.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return laf.getPoint("screen.size");
}

////////////////////////////////////////////////////////////////////////////////
//ILayout

SelectFolderDialog.prototype.layout = function(instance, laf) {	
	var rect = laf.getRectangle("select.folder.dialog.bounds");
	instance.setLayoutBounds(rect);
}

////////////////////////////////////////////////////////////////////////////////
//IComponentValidator

SelectFolderDialog.prototype.validate = function(instance) {
	properties = instance.properties;
	if (instance != null || instance != "") {
		if ( properties.defaultFolderDescription.driveLetter.length !=1 ||
				!((properties.defaultFolderDescription.driveLetter.toLowerCase().charAt(0)>='a') 
			   && (properties.defaultFolderDescription.driveLetter.toLowerCase().charAt(0)<='z')) ){
			return [createSimpleModelError(instance, 
					"defaultFolderDescription.driveLetter",
					lookupString("validate.driveLetter"),
					instance.name )];												
		}				
	}
	return null;
}

SelectFolderDialog.prototype.queryPropertyChange = function(instance, propertyId, newValue) {
	return null;
}
