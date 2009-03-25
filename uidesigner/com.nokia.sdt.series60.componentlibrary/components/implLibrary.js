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


// This file has routines which initialize component implementations
// according to patterns.

include ("messageLibrary.js")

implLibraryStrings = getLocalizedStrings("implLibrary");

/**
 *	Set up direct label editing implementation for a component with
 *	one editable label
 *	@param prototype the impl prototype to update
 *	@param property the name of the edited property
 *	@param areafunction a function, taking an instance and laf, which returns the
 *		editable area of the label (or null).  If null, the default behavior is
 *	to use the entire rendered area.
 *	@param fontfunction a function, taking an instance and laf, which returns the
 *		IFont to edit with (or null).  If null, the default behavior is to return
 *		null, indicating a default system font.
 */
function setupCommonDirectLabelEditing(prototype, property, areafunction, fontfunction) {

	prototype.getPropertyPaths = function(instance) {
		return [ property ];
	}

	prototype.getLabelBounds = function(instance, propertyPath, laf) {
		if (areafunction)
			return areafunction(instance, laf);
		var size = instance.properties.size;
	    return new Rectangle(0, 0, size.width, size.height);
	}

	prototype.getLabelFont = function(instance, propertyPath, laf) {
		if (fontfunction)
			return fontfunction(instance, laf)
		return null;
	}

}


//	Set up direct image editing implementation for a component
//	with one image property.  
//
//	@param prototype the impl prototype to update
//	@param property the name of the edited property
//	@param areafunction a function, taking an instance, laf, and propertyPath, or null.
//		If not null, the function which returns a rectangle for the clickable area 
//		of the rendered area of the component to respond to.
//		If null, the default behavior is to use the entire rendered area.

function setupCommonDirectImageEditing(prototype, property, areafunction, sizefunction, isScalingFunction) {

	// implementation for IDirectImageEdit
	prototype.getImagePropertyPaths = function(instance) {
		return [ property ];
	}

	// implementation for IDirectImageEdit
	prototype.getImageBounds = function(instance, propertyPath, laf) {
		//println( "image: " + instance.properties[propertyPath].bmpfile + "/" 
		//		+instance.properties[propertyPath].bmpid + "/" 
		//		+instance.properties[propertyPath].bmpmask );
				
		if (areafunction) 
			return areafunction(instance, laf, property);
		else {
			var size = instance.properties.size;
		    return new Rectangle(0, 0, size.width, size.height);
		}
	}
}

/**	Create a simple IModelMessage that refers to an error on a property. */
function createSimpleModelError(instance, propertyId, messageFormat, argList) {
	var modelMessage = newModelMessage(IStatus.ERROR, 
			formatString(messageFormat, argList), 
			instance, propertyId, null);
	return modelMessage;
}

/**
 *	Set up common IComponentValidator script for an editor that has
 *	minimum, maximum, and current value properties.
 *	@param prototype to modify
 *	@param noun localized string for the unit being edited (i.e. "duration")
 *	@param nouns localized string for the plural unit being edited (i.e. "durations")
 *	@param minProperty, maxProperty, valueProperty properties to check
 *	@param converter a function taking a property value used to convert a property to a comparable value;
 *		if null, the property is treated as an integer
 */
function setupCommonRangeCheckingValidation(prototype, noun, nouns,
		minProperty, maxProperty, valueProperty, converter) {

	if (converter == null)
		converter = function(value) { return value; };

			// note that laf will be null if a display model was not created
	prototype.validate = function(instance, laf) {
		var properties = instance.properties;

		var min = converter(properties[minProperty]);
		var max = converter(properties[maxProperty]);
		var value = converter(properties[valueProperty]);

		var messages = new java.util.ArrayList();
		
		if (min > max) {
			messages.add(createSimpleModelError(instance, 
				minProperty, 
				implLibraryStrings.getString("minMaxValueError"), 
				[ noun, nouns, instance.name, min, max ]));
		} 
		if (value < min || value > max) {
			messages.add(createSimpleModelError(instance, 
				valueProperty, 
				implLibraryStrings.getString("valueRangeError"), 
				[ noun, nouns, instance.name, min, max, value ]));
		}	
		return messages;
	}
	
		// note that laf will be null if a display model was not created
	prototype.queryPropertyChange = function(instance, propertyPath,
					newVal, laf) {
					
		var properties = instance.properties;
		var message = null;

		newValue = converter(newVal);
		if (propertyPath == minProperty) {
			if (newValue > converter(properties[valueProperty]) ||
				newValue >= converter(properties[maxProperty])) {
				message = formatString(implLibraryStrings.getString("minValueConstraint"), noun, nouns );
			}
		}
		else if (propertyPath == valueProperty) {
			if (newValue < converter(properties[minProperty]) ||
			    newValue > converter(properties[maxProperty])) {
				message = formatString(implLibraryStrings.getString("valueConstraint"), noun, nouns );
			}
		}
		else if (propertyPath == maxProperty) {
			if (newValue <= converter(properties[minProperty]) ||
				newValue < converter(properties[valueProperty]))
				message = formatString(implLibraryStrings.getString("maxValueConstraint"), noun, nouns );
		}
	
		return message;		
	}

}

