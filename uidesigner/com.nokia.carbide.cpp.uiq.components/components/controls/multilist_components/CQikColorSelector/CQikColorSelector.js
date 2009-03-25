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
* START_USECASES: CU12 END_USECASES
*
*
*/



include("../../../renderLibrary.js")
include("../../../implLibrary.js")
include("../../../containerLibrary.js")
include("../../../layoutDataLibrary.js")

function CQikColorSelector() {
}


////////////////////////////////////////////////////////////////////////////////
// IVisualAppearance

CQikColorSelector.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties;
	
	var font = laf.getFont("NormalFont");
	graphics.setFont(font);
	var flags = getFlags(instance);
	var sqside = 14;
	var extraspace = 0;
	var rect = instance.getRenderingBounds();	
	rect.width-= sqside + 4;
	var bounds = new Point(rect.width, rect.height);
	var labelBounds;
	var largestLabel = getLargestLabel(instance);
//	rect.x=3;
	if(properties.isVisible != null && properties.isVisible == false)
	      return;

	//Gap between rows in Layout Manager.
	var rowsGap = laf.getInteger("RowLayoutManager.rowsGap",11);

	// pixelGapBetweenLines is not rendered due to the Emulator doesn't show it
	var pixelGapBetweenLines = rowsGap;

	rect.height += rowsGap;
	

	if(properties.showText){
		if(properties.paletteEnum=="Color4" || properties.paletteEnum=="Color16" || properties.paletteEnum=="Gray4"){
			graphics.setBackground(Colors.getColor(0, 0, 0));
		    label = "Black";
		    labelBounds = font.formattedStringExtent(largestLabel, bounds, flags,rowsGap);
		    if(labelBounds.x <= rect.width){
			    graphics.drawFormattedString(label, rect, flags, pixelGapBetweenLines);
				if(labelBounds.x < rect.width  ){
					graphics.fillRectangle(labelBounds.x + extraspace,2,sqside,sqside);
					graphics.drawRectangle(labelBounds.x + extraspace,2,sqside,sqside);			
				}else{
					graphics.fillRectangle(rect.width + extraspace,2,sqside,sqside);
					graphics.drawRectangle(rect.width + extraspace,2,sqside,sqside);				
				}
		    }else{
		    	rect.width += sqside + 4;
			    graphics.drawFormattedString(label, rect, flags, pixelGapBetweenLines);		    	
		    }
		}
		if(properties.paletteEnum=="Gray16"){
			graphics.setBackground(Colors.getColor(0, 0, 0));
			label = "0";
		    labelBounds = font.formattedStringExtent(largestLabel, bounds, flags,rowsGap);
		    if(labelBounds.x <= rect.width){
				graphics.drawFormattedString(label, rect, flags, pixelGapBetweenLines);
				if(labelBounds.x < rect.width  ){
					graphics.fillRectangle(labelBounds.x + extraspace,2,sqside,sqside);
					graphics.drawRectangle(labelBounds.x + extraspace,2,sqside,sqside);			
				}else{
					graphics.fillRectangle(rect.width + extraspace,2,sqside,sqside);
					graphics.drawRectangle(rect.width + extraspace,2,sqside,sqside);
				}
		    }else{
		    	rect.width += sqside + 4;
				graphics.drawFormattedString(label, rect, flags, pixelGapBetweenLines);		    	
		    }
		}
		if(properties.paletteEnum=="Custom" && properties.palette[0]!=null){
			var customcolor = colorFromString(laf,properties.palette[0].rgb.red+","+
					properties.palette[0].rgb.green+","+properties.palette[0].rgb.blue);
			graphics.setBackground(customcolor);
			label = "";
		    labelBounds = font.formattedStringExtent(largestLabel, bounds, flags,rowsGap);
		    if(labelBounds.x <= rect.width){
				if(properties.palette[0].label != ""){
					label = properties.palette[0].label;
					graphics.drawFormattedString(properties.palette[0].label, rect, flags, pixelGapBetweenLines);
				}
				if(labelBounds.x < rect.width  ){
					graphics.fillRectangle(labelBounds.x + extraspace,2,sqside,sqside);
					graphics.drawRectangle(labelBounds.x + extraspace,2,sqside,sqside);			
				}else{
					graphics.fillRectangle(rect.width + extraspace,2,sqside,sqside);
					graphics.drawRectangle(rect.width + extraspace,2,sqside,sqside);				
				}		    	
		    }else{
		    	rect.width += sqside + 4;
				if(properties.palette[0].label != ""){
					label = properties.palette[0].label;
					graphics.drawFormattedString(properties.palette[0].label, rect, flags, pixelGapBetweenLines);
				}
		    }
		}
	}else{
		if(properties.paletteEnum=="Custom"){
			if(properties.palette.length != 0){
				var customcolor = colorFromString(laf,properties.palette[0].rgb.red+","+
						properties.palette[0].rgb.green+","+properties.palette[0].rgb.blue);
				graphics.setBackground(customcolor);
				graphics.fillRectangle(2,2,sqside,sqside);
				graphics.drawRectangle(2,2,sqside,sqside);
			}
		}else{
			graphics.setBackground(Colors.getColor(0, 0, 0));
			graphics.fillRectangle(2,2,sqside,sqside);
			graphics.drawRectangle(2,2,sqside,sqside);
		}
	}
}


CQikColorSelector.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	return getPreferredSize(instance, laf, wHint, hHint);
}


function getPreferredSize(instance, laf, wHint, hHint) {

	var width=0;
	var height=0;
	var properties = instance.properties;
	var sqside = 14;
	var extraspace = 4;
	
	var	font = laf.getFont("NormalFont");	
				
	var flags = getFlags(instance);	

	if (wHint >= 0)
		width = wHint;
	if (hHint >= 0)
		height = hHint;

	var bounds = new Point(width, height);
	var font = laf.getFont("NormalFont");	


	//Gap between rows in Layout Manager
	var rowsGap = laf.getInteger("RowLayoutManager.rowsGap",11);
	var ld = getLayoutData ( instance, "horizontalAlignment", "EQikLayoutHAlignInherit" );

	// pixelGapBetweenLines is not rendered due to the Emulator doesn't show it
	var pixelGapBetweenLines = rowsGap; 
	var largestLabel = getLargestLabel(instance);
	var newBounds = font.formattedStringExtent(largestLabel, bounds, flags,pixelGapBetweenLines);		
	
	if(properties.showText){
		if(newBounds.x > width){
			return new Point (width + sqside + extraspace, height); 		
		}else{
			if( ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill"){
				return new Point (width, height); 									
			}else{
				return new Point (newBounds.x + sqside + extraspace, height); 									
			}
		}
	}else{
		if( ld == "EQikLayoutHAlignFill"){
			return new Point (width, height); 									
		}else{
			return new Point (sqside + extraspace, height); 
		}
	}
}

function getLargestLabel(instance){
	var properties = instance.properties;
	var largestLabel = "";
	switch(properties.paletteEnum) {
		case "Color4":{
			largestLabel = "Light grey";
		}break;
		case "Gray4":{
			largestLabel = "Light grey";
		}break;
		case "Color16":{
			largestLabel = "Dark Magenta";
		}break;
		case "Gray16":{
			largestLabel = "15";
		}break;
		case "Custom":{
			if(properties.palette!=null){
				for (var i=0; i<properties.palette.length; i++) {
					if(properties.palette[i].label.length > largestLabel.length){
						largestLabel = properties.palette[i].label;
					}
				}
			}
		}break;
	}
	return largestLabel;
}

function getFlags(instance){
	var properties = instance.properties;
	var flags = 0;
	
	flags |= Font.VERTICAL_ALIGN_TOP;
	flags |= Font.ALIGN_LEFT;
	flags |= Font.ANTIALIAS_OFF;
	flags |= Font.OVERFLOW_ELLIPSIS;

	return flags;
}

///////////////////////////////////////////////////////////////////////////////////////
//IPropertyListener

CQikColorSelector.prototype.propertyChanged = function(instance, property) {
	properties = instance.properties;
	var aux;
	
	if(property=="rgbChanged"){
		instance.forceRedraw();			
	}
	
	if(property=="labelChanged"){
		instance.forceRedraw();			
	}

	if(property=="largestLabelChanged"){
		instance.forceRedraw();			
	}

	
	if (property!="rgbChanged" && property != "size" && property != "location"){
		if (properties.palette != null) {
			if (properties.palette[0] != null) {									 
				aux = properties.palette[0].rgb.red + "," +
							properties.palette[0].rgb.green + "," + properties.palette[0].rgb.blue;
				if(aux != properties.rgbChanged){
					properties.rgbChanged = aux;
				}
			}
		}
	}

	if (property!="labelChanged" && property != "size" && property != "location"){
		if (properties.palette != null) {
			if (properties.palette[0] != null) {									 
				aux = properties.palette[0].label;
				if(aux != properties.labelChanged){
					properties.labelChanged = aux;
				}
			}
		}
	}

	if (property!="largestLabelChanged" && property != "size" && property != "location"){
		if (properties.palette != null) {
			if (properties.palette[0] != null) {									 
				aux = getLargestLabel(instance);
				if(aux != properties.largestLabelChanged){
					properties.largestLabelChanged = aux;
				}
			}
		}
	}

}

////////////////////////////////////////////////////////////////////////////////
//IComponentValidator

CQikColorSelector.prototype.validate = function(instance) {
	if (instance != null || instance != "") {
		if (instance.isInstanceOf("com.nokia.carbide.uiq.CQikColorSelector")&& instance.properties.paletteEnum=="Custom" && instance.properties.palette.length == 0 ){
			return [createSimpleModelError(instance, 
					"paletteEnum",
					lookupString("validate.customPalette1"),
					instance.name )
				   ];
		}
		if (instance.isInstanceOf("com.nokia.carbide.uiq.CQikColorSelector")&& instance.properties.paletteEnum=="Custom" && instance.properties.palette.length > 16 ){
			return [createSimpleModelError(instance, 
					"paletteEnum",
					lookupString("validate.customPalette2"),
					instance.name )
				   ];
		}
	}
	return null;
}

CQikColorSelector.prototype.queryPropertyChange = function(instance, propertyId, newValue) {
	return null;
}

