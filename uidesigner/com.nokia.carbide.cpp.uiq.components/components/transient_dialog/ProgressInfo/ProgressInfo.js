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
* START_USECASES: CU18 END_USECASES
*
*
*/


include("../../renderLibrary.js")
include("../../implLibrary.js")
include("../../layoutDataLibrary.js")

function ProgressInfo() {
}


////////////////////////////////////////////////////////////////////////////////
// IVisualAppearance

ProgressInfo.prototype.draw = function(instance, laf, graphics) {

	var properties = instance.properties;
	var renderingBounds = instance.getRenderingBounds();
	var width = renderingBounds.width-3;			
	var height; 
	var widthbar = (properties.value/properties.finalval)*width;

	if(properties.isVisible != null && properties.isVisible == false)
		   return;

	if(properties.height>=16){
		height = properties.height;
	}
	else{
		height = 16;
	}

	var font = laf.getFont("NormalFont");
	graphics.setFont(font);
	var flags = getFlags(instance);

	var rect = instance.getRenderingBounds();	

	rect.y=-4;
	graphics.drawRectangle(0,0,width,height);		
	if(widthbar>0){
		if(properties.dimmed){
			graphics.setBackground(laf.getColor("EEikColorProgressBarDimmed"));
		}
		else
			graphics.setBackground(laf.getColor("EEikColorProgressBar"));
		
		graphics.fillRectangle(0,0,widthbar,height);				
	}
	var text;
	if(properties.textType != "EEikProgressTextNone"){
		if(2*properties.value < properties.finalval ){
			graphics.setForeground(laf.getColor("EEikColorButtonText"));
		}else{
			graphics.setForeground(Colors.getColor(255, 255, 255));
		}		
	}
	if(properties.textType == "EEikProgressTextPercentage" && properties.splitsInBlock == 0){
		if(properties.value < properties.finalval){
			text= 100*properties.value/properties.finalval;
			text = text + " %";
		}
		else{
			text = "100 %";
		}
		graphics.drawFormattedString(text, rect, flags);
	}
	if(properties.textType == "EEikProgressTextFraction" && properties.splitsInBlock == 0){
		if(properties.value < properties.finalval){
			text = properties.value + " / " + properties.finalval;
		}
		else{
			text = properties.finalval + " / " + properties.finalval;
		}
		graphics.drawFormattedString(text, rect, flags);
	}
	
	if(properties.splitsInBlock > 0){
		graphics.setForeground(Colors.getColor(255, 255, 255));
		var segmentwidth=5;
		var covered = segmentwidth;
		while(covered < widthbar){
			graphics.drawLine(covered,0,covered,height-1);
			covered = covered + segmentwidth;
		}
	}
}

function getFlags(instance){
	var flags = 0;

	flags |= Font.ALIGN_CENTER;
	flags |= Font.VERTICAL_ALIGN_CENTER;		
	flags |= Font.WRAPPING_ENABLED;		
	flags |= Font.ANTIALIAS_OFF;

	return flags;
}


ProgressInfo.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	properties = instance.properties;
	
	var width = wHint;
	var height;
	if(properties.height>=16){
		height = properties.height+3;
	}
	else{
		height = 19;
	}
	
	var ld = getLayoutData ( instance, "horizontalAlignment", "EQikLayoutHAlignInherit" );
	if(wHint>= 231 && ld != "EQikLayoutHAlignInherit" && ld != "EQikLayoutHAlignFill"){
		width = .9*wHint;
	}		
	var bounds = new Point(width, height);
	return bounds;
}





	