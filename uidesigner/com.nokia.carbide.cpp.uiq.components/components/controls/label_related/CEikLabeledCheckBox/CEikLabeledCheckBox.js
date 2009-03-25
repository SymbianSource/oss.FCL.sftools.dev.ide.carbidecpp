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
* START_USECASES: CU14 END_USECASES
*
*
*/



include("../../../renderLibrary.js")
include("../../../implLibrary.js")
include("../../../layoutDataLibrary.js")

function CEikLabeledCheckBox() {
}


////////////////////////////////////////////////////////////////////////////////
// IVisualAppearance

CEikLabeledCheckBox.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;
	var sqsidelenght = 12;
	var extraspace = 4;
	var text = "";
	   
    if(properties.text!=null){
	    text = properties.text;   		   
    }
	
	var rect = instance.getRenderingBounds();	
	var font = laf.getFont("NormalFont");
	graphics.setFont(font);
	
	var bounds = new Point(rect.width, rect.height);
	var flags = getFlags(instance);	
	var rowsGap = laf.getInteger("RowLayoutManager.rowsGap",11);
	var ld = getLayoutData ( instance, "horizontalAlignment", "EQikLayoutHAlignInherit" );
	
	var newBounds = font.formattedStringExtent(text, bounds, flags,rowsGap);		
	rect.width-=sqsidelenght+extraspace;	
	
	if(properties.isVisible != null && properties.isVisible == false)
	      return;
	
	if(rect){
		if(properties.textOnRight){
			rect.x=sqsidelenght+extraspace;
		}
	}
	
	graphics.setForeground(laf.getColor("EEikColorButtonText"));
	// pixelGapBetweenLines is not rendered due to the Emulator doesn't show it
	var pixelGapBetweenLines = rowsGap;
	
	rect.height += rowsGap;
	
	if(text!=""){
		graphics.drawFormattedString(text, rect, flags, pixelGapBetweenLines);
	}
	graphics.setBackground(laf.getColor("EEikColorControlBackground"));
	if(properties.textOnRight)
		{
		graphics.fillRectangle(3,3,sqsidelenght,sqsidelenght);
		graphics.drawRectangle(3,3,sqsidelenght,sqsidelenght);
			if(properties.state=="ESet"){
				graphics.setLineWidth(3);
				graphics.drawLine(4,10,8,15);
				graphics.drawLine(8,15,15,5);
			}
		}
	else
		{
		graphics.fillRectangle(rect.width+5,3,sqsidelenght,sqsidelenght);
		graphics.drawRectangle(rect.width+5,3,sqsidelenght,sqsidelenght);
			if(properties.state=="ESet"){
			graphics.setLineWidth(3);
			graphics.drawLine(rect.width+6,10,rect.width+10,15);
			graphics.drawLine(rect.width+10,15,rect.width+17,5);
		}
	}
}


CEikLabeledCheckBox.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return getPreferredSize(instance, laf, wHint, hHint);
}


function getPreferredSize(instance, laf, wHint, hHint) {
	   var width = wHint > 0? wHint : 60;
	   var height = hHint > 0? hHint : 24;
	   var properties = instance.properties;	
	   var text = "";
	   var sqsidelenght = 12;
	   var extraspace = 4;
	   
	   if(properties.text!=null){
		   text = properties.text;   		   
	   }
	   
	   var font = laf.getFont("NormalFont");	
	   var bounds = new Point(width, height);
	   var flags = getFlags(instance);	
	   var rowsGap = laf.getInteger("RowLayoutManager.rowsGap",11);
	   var ld = getLayoutData ( instance, "horizontalAlignment", "EQikLayoutHAlignInherit" );

	   var newBounds = font.formattedStringExtent(text, bounds, flags,rowsGap);		
	   if(newBounds.x > width - sqsidelenght - extraspace || ld == "EQikLayoutHAlignInherit" ){
		   return new Point(width, height);		   
	   }else{
		   return new Point(newBounds.x + sqsidelenght + extraspace, height);		   
	   }
}

function getFlags(instance){
	var properties = instance.properties;
	var flags = 0;
	
	flags |= Font.VERTICAL_ALIGN_TOP;
	if(properties.textOnRight)
		flags |= Font.ALIGN_LEFT;
	else
		flags |= Font.ALIGN_RIGHT;
	flags |= Font.WRAPPING_ENABLED;	
	
	flags |= Font.ANTIALIAS_OFF;
	
	return flags;
}

////////////////////////////////////////////////////////////////////////////////
// IDirectLabelEdit

setupCommonDirectLabelEditing(CEikLabeledCheckBox.prototype, 
	"text", 
	areaWithParentWidth,
	function(instance, laf) { return laf.getFont("NormalFont"); } 
	)