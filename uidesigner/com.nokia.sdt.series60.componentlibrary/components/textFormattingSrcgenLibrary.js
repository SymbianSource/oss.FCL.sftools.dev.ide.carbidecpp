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
 *	@return true iff generated the contribution (if some property not default).
 */
function createCharFormatStructs(contribs, instance, location) {
	var generated = false;
	var properties = instance.properties;
	var contribText = "TCharFormatMask charFormatMask;\nTCharFormat charFormat;\n";
	
	if (properties.charFormat.textColor != "") {
		contribText += "charFormatMask.SetAttrib( EAttColor );\n";
		contribText += "charFormat.iFontPresentation.iTextColor = TLogicalRgb( ";
		contribText += this.getRgb(properties.charFormat.textColor);
		contribText += " );\n";
		generated = true;
	}

	if (properties.charFormat.highlightColor != "") {
		contribText += "charFormatMask.SetAttrib( EAttFontHighlightColor );\n";
		contribText += "charFormat.iFontPresentation.iHighlightColor = TLogicalRgb( ";
		contribText += this.getRgb(properties.charFormat.highlightColor);
		contribText += " );\n";
		generated = true;
	}
	
	if (properties.charFormat.highlightStyle != "EFontHighlightNone") {
		contribText += "charFormatMask.SetAttrib( EAttFontHighlightStyle );\n";
		contribText += "charFormat.iFontPresentation.iHighlightStyle = ";
		contribText += "TFontPresentation::";
		contribText += properties.charFormat.highlightStyle;
		contribText += ";\n";
		generated = true;
	}

	if (properties.charFormat.strikethrough) {
		contribText += "charFormatMask.SetAttrib( EAttFontStrikethrough );\n";
		contribText += "charFormat.iFontPresentation.iStrikethrough = EStrikethroughOn;\n";
		generated = true;
	}
	
	if (properties.charFormat.underline) {
		contribText += "charFormatMask.SetAttrib( EAttFontUnderline );\n";
		contribText += "charFormat.iFontPresentation.iUnderline = EUnderlineOn;\n";
		generated = true;
	}
	
	if (properties.charFormat.italics) {
		contribText += "charFormatMask.SetAttrib( EAttFontPosture );\n";
		contribText += "charFormat.iFontSpec.iFontStyle.SetPosture( EPostureItalic );\n";
		generated = true;
	}
	
	if (properties.charFormat.bold) {
		contribText += "charFormatMask.SetAttrib( EAttFontStrokeWeight );\n";
		contribText += "charFormat.iFontSpec.iFontStyle.SetStrokeWeight( EStrokeWeightBold );\n";
		generated = true;
	}
	
	if (properties.charFormat.hiddenText) {
		contribText += "charFormatMask.SetAttrib( EAttFontHiddenText );\n";
		contribText += "charFormat.iFontPresentation.iHiddenText = ETrue;\n";
		generated = true;
	}
	
	if (properties.charFormat.pictureAlignment != "EAlignBaseLine") {
		contribText += "charFormatMask.SetAttrib( EAttFontPictureAlignment );\n";
		contribText += "charFormat.iFontPresentation.iPictureAlignment = ";
		contribText += "TFontPresentation::";
		contribText += properties.charFormat.pictureAlignment;
		contribText += ";\n";
		generated = true;
	}

	if (generated) {
		var contrib = Engine.createContributionForLocation(location);
		contrib.setText(contribText);
		contribs.add(contrib);
	}
	
	return generated;		
}

function getRgb(colorProperty) {
	if (colorProperty.indexOf(',') > 0)
		return "TRgb( " + colorProperty + " )";
	else
		return "iEikonEnv->Color( ( TLogicalColor ) " + colorProperty + " )";
}	
