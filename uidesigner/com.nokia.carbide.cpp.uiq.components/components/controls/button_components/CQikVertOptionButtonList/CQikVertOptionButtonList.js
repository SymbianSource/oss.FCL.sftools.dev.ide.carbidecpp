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
* START_USECASES: CU17 END_USECASES
*
*
*/


include("../../../renderLibrary.js")
include("../../../implLibrary.js")
include("../../../layoutDataLibrary.js")

function CQikVertOptionButtonList() {
}


////////////////////////////////////////////////////////////////////////////////
// IVisualAppearance

CQikVertOptionButtonList.prototype.draw = function(instance, laf, graphics) {
	draw(instance, laf, graphics);
}

function draw(instance, laf, graphics) {
	var properties = instance.properties;	
	var flags = getFlags(instance);
	var font = laf.getFont("NormalFont");
	graphics.setFont(font);	
	text=properties.labeledOptions[0];	
	var rect = instance.getRenderingBounds();
	graphics.setForeground(laf.getColor("EEikColorButtonText"));
	if(text)
		graphics.drawFormattedString(text, rect, flags);	
}

CQikVertOptionButtonList.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	   var width = wHint > 0? wHint : 60;
	   var height = hHint > 0? hHint : 24;
	   var properties = instance.properties;	
	   var text = "";
	   if(properties.labeledOptions!=null){
		   if(properties.labeledOptions[0]!=null){
			   text = properties.labeledOptions[0];   
		   }
	   }
	   
	   var font = laf.getFont("NormalFont");	
	   var bounds = new Point(width, height);
	   var flags = getFlags(instance);	
	   var rowsGap = laf.getInteger("RowLayoutManager.rowsGap",11);
	   var ld = getLayoutData ( instance, "horizontalAlignment", "EQikLayoutHAlignInherit" );

	   var newBounds = font.formattedStringExtent(text, bounds, flags,rowsGap);		
	   if(newBounds.x > width || ld == "EQikLayoutHAlignInherit" ){
		   return new Point(width, height);		   
	   }else{
		   return new Point(newBounds.x, height);		   
	   }

}



function getFlags(instance){
	var properties = instance.properties;
	var flags = 0;
	var ld = getLayoutData ( instance, "horizontalAlignment", "EQikLayoutHAlignInherit" );
	
	if(ld == "EQikLayoutHAlignLeft" || ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill"){
		flags |= Font.ALIGN_LEFT;		
	}
	if(ld == "EQikLayoutHAlignRight"){
		flags |= Font.ALIGN_RIGHT;		
	}
	if(ld == "EQikLayoutHAlignCenter"){
		flags |= Font.ALIGN_CENTER;		
	}
	if(ld == "EQikLayoutHAlignRight"){
		flags |= Font.ALIGN_RIGHT;		
	}
	flags |= Font.VERTICAL_ALIGN_CENTER;
	flags |= Font.ANTIALIAS_OFF;
	flags |= Font.OVERFLOW_ELLIPSIS;

	return flags;
}


