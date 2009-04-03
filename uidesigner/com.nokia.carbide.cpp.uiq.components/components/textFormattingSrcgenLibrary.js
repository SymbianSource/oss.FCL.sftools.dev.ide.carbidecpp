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


/**
 *	Create a contribution that sets up a CharFormat and CharFormatMask for use
 *  by CEikGlobalText and subcomponents (CEikRichText).
 *	@param contribs the contributions to append to
 *	@param instance instance of the component assumed to have the correct properties.
 *	@param location location to write to
 *  @param phase phase to write to
 *	@return true iff generated the contribution (if some property not default).
 */
function createCharFormatStructs(contribs, instance, location, phase) {
	var generated = false;
	var properties = instance.properties;
	var contribText = "\tTCharFormatMask charFormatMask;\n\tTCharFormat charFormat;\n";
	
	if (properties.charFormat.textColor != "") {
		contribText += "\tcharFormatMask.SetAttrib( EAttColor );\n";
		contribText += "\tcharFormat.iFontPresentation.iTextColor = TLogicalRgb( ";
		contribText += this.getRgb(properties.charFormat.textColor);
		contribText += " );\n";
		generated = true;
	}

	if (properties.charFormat.highlightColor != "") {
		contribText += "\tcharFormatMask.SetAttrib( EAttFontHighlightColor );\n";
		contribText += "\tcharFormat.iFontPresentation.iHighlightColor = TLogicalRgb( ";
		contribText += this.getRgb(properties.charFormat.highlightColor);
		contribText += " );\n";
		generated = true;
	}
	
	if (properties.charFormat.highlightStyle != "EFontHighlightNone") {
		contribText += "\tcharFormatMask.SetAttrib( EAttFontHighlightStyle );\n";
		contribText += "\tcharFormat.iFontPresentation.iHighlightStyle = ";
		contribText += "\tTFontPresentation::";
		contribText += properties.charFormat.highlightStyle;
		contribText += ";\n";
		generated = true;
	}

	if (properties.charFormat.strikethrough) {
		contribText += "\tcharFormatMask.SetAttrib( EAttFontStrikethrough );\n";
		contribText += "\tcharFormat.iFontPresentation.iStrikethrough = EStrikethroughOn;\n";
		generated = true;
	}
	
	if (properties.charFormat.underline) {
		contribText += "\tcharFormatMask.SetAttrib( EAttFontUnderline );\n";
		contribText += "\tcharFormat.iFontPresentation.iUnderline = EUnderlineOn;\n";
		generated = true;
	}
	
	if (properties.charFormat.italics) {
		contribText += "\tcharFormatMask.SetAttrib( EAttFontPosture );\n";
		contribText += "\tcharFormat.iFontSpec.iFontStyle.SetPosture( EPostureItalic );\n";
		generated = true;
	}
	
	if (properties.charFormat.bold) {
		contribText += "\tcharFormatMask.SetAttrib( EAttFontStrokeWeight );\n";
		contribText += "\tcharFormat.iFontSpec.iFontStyle.SetStrokeWeight( EStrokeWeightBold );\n";
		generated = true;
	}
	
	if (properties.charFormat.hiddenText) {
		contribText += "\tcharFormatMask.SetAttrib( EAttFontHiddenText );\n";
		contribText += "\tcharFormat.iFontPresentation.iHiddenText = ETrue;\n";
		generated = true;
	}
	
	if (properties.charFormat.pictureAlignment != "EAlignBaseLine") {
		contribText += "\tcharFormatMask.SetAttrib( EAttFontPictureAlignment );\n";
		contribText += "\tcharFormat.iFontPresentation.iPictureAlignment = ";
		contribText += "\tTFontPresentation::";
		contribText += properties.charFormat.pictureAlignment;
		contribText += ";\n";
		generated = true;
	}
	
	if (generated) {
		if (instance.component.id == "com.nokia.carbide.uiq.CEikRichText") {
			contribText += "\tCRichText* richtxt = " + instance.memberName + "->RichText();\n";
			contribText += "\trichtxt->ApplyCharFormatL( charFormat, charFormatMask, 0, richtxt->DocumentLength() );\n";
		} else {
			contribText += "\t" + instance.memberName + "->ApplyCharFormatL( charFormat, charFormatMask );\n";
		}
	}

	if (generated) {
		var contrib = null; 
		if (location != null) {
			contrib = Engine.createContributionForLocation(location);
		} else {
			contrib = Engine.createContributionForPhase(phase);
		}
		var opening = "\nif ( " + instance.memberName + " != NULL )\n";
		opening += "\t{\n";
		var closing = "\t}\n"
		contribText = opening + contribText + closing;
		contrib.setText(contribText);
		contribs.add(contrib);
	}
	
	return generated;		
}

/**
 *	Create a contribution that sets up a CharFormat and CharFormatMask for use
 *  by CEikGlobalText and subcomponents (CEikRichText).
 *	@param contribs the contributions to append to
 *	@param instance instance of the component assumed to have the correct properties.
 *  @param phase phase to write to
 *	@return true if generated the contribution (if some property not default).
 */
function createCharFormatStructsForPhase(contribs, instance, phase) {
	var generated = createCharFormatStructs(contribs, instance, null, phase);
	return generated;
}


function getRgb(colorProperty) {
	if (colorProperty.indexOf(',') > 0)
		return "TRgb( " + colorProperty + " )";
	else
		return "iEikonEnv->Color( ( TLogicalColor ) " + colorProperty + " )";
}	
