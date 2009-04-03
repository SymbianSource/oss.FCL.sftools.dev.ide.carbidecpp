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

function CEikCommandButton() {
}

////////////////////////////////////////////////////////////////////////////////
// IVisualAppearance

CEikCommandButton.prototype.draw = function(instance, laf, graphics) {
	var arcbuttonwidth=5;
	var arcbuttonheight=5;
    var extraspacex = 8;
    var extraspacey = 6;
    var properties = instance.properties;	
    

    var text;
	var bounds = instance.getRenderingBounds();

    var font = laf.getFont("NormalFont");	
	graphics.setFont(font);	
    var boundsPoint = new Point(bounds.width, bounds.height);
    var flags = getFlags(instance);	
    var rowsGap = laf.getInteger("RowLayoutManager.rowsGap",11);
    var ld = getLayoutData ( instance, "horizontalAlignment", "EQikLayoutHAlignInherit" );

    if(properties.text==null){
    	text = "button";
    }else{
    	text = properties.text;
    }
    var textBounds = font.formattedStringExtent(text, boundsPoint, flags,rowsGap);		
	
	var imgBounds = getImageBounds(instance, properties.image);

    
    var aux = getImageSize(instance, "image", laf);
	if(imgBounds != null){
	    imgBounds = new Rectangle(0,0,aux.x,aux.y);	
	}
	
	setBackground( laf, instance, graphics, bounds );
	var imagerect=getImageSize(instance,"image",laf);

	var emptywidth; 
    var textrect = new Rectangle(0,0,textBounds.x,textBounds.y);

	if( imgBounds ) {
		if( properties.text == null || properties.text == "" ){
			emptywidth	= bounds.width - imgBounds.width;
			if(emptywidth < 0){
				emptywidth = 0;					
			}
			renderImage(CEikCommandButton.prototype, instance, laf, 
					graphics, emptywidth/2,3, "image", true);		
		}else{
			switch(properties.layout) {
				case "EEikCmdButTextRightPictureLeft":{
					emptywidth	= bounds.width - (imgBounds.width + textBounds.x + extraspacex);
					if(emptywidth < 0){
						emptywidth = 0;					
					}
					renderImage(CEikCommandButton.prototype, instance, laf, 
							graphics, emptywidth/4,3, "image", true);
					textrect.x = emptywidth/2 + imgBounds.width+2;
					textrect.height = bounds.height;
	
					if( textBounds.x > (bounds.width - imgBounds.width - extraspacex) || 
							ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill" ){
						textrect.width = bounds.width - imgBounds.width - extraspacex;	
					}
/*
					println("*********************content width: "+(textrect.width + extraspacex + imgBounds.width) + "***************************");
					println("*********************row width: "+bounds.width+"***************************");
*/					
				}break;
				case "EEikCmdButTextLeftPictureRight":{
					emptywidth	= bounds.width - (imgBounds.width + textBounds.x + extraspacex);
					if(emptywidth < 0){
						emptywidth = 0;					
					}
					textrect.x = 3;
					textrect.height = bounds.height;
	
					if( textBounds.x > (bounds.width - imgBounds.width - extraspacex) ){
						textrect.width = bounds.width - imgBounds.width - extraspacex;
						renderImage(CEikCommandButton.prototype, instance, laf, 
								graphics, textrect.width + 2,3, "image", true);
					}else{
						if(ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill"){
							renderImage(CEikCommandButton.prototype, instance, laf, 
									graphics, textBounds.x + 3*emptywidth/4 + 2,3, "image", true);													
						}
						else{
							renderImage(CEikCommandButton.prototype, instance, laf, 
									graphics, textrect.width + 2,3, "image", true);							
						}
					}	
				}break;
				case "EEikCmdButTextBottomPictureTop":{
					if(textBounds.x > imgBounds.width){
						if(textBounds.x > bounds.width){
							textrect.width = bounds.width;							
						}
					}else{
						if(imgBounds.width > bounds.width){
							textrect.width = bounds.width;							
						}						
					}
					textrect.x=extraspacex/2;
					if(  ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill" ){
						textrect.x = (bounds.width - textrect.width)/2;
					}
					textrect.y = imgBounds.height + extraspacey/3;
					renderImage(CEikCommandButton.prototype, instance, laf, 
							graphics, (bounds.width - imgBounds.width)/2,extraspacey/2, "image", true);					
				}break;
				case "EEikCmdButTextTopPictureBottom":{
					if(textBounds.x > imgBounds.width){
						if(textBounds.x > bounds.width){
							textrect.width = bounds.width;							
						}
					}else{
						if(imgBounds.width > bounds.width){
							textrect.width = bounds.width;							
						}						
					}
					textrect.x=extraspacex/3;
					if(  ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill" ){
						textrect.x = (bounds.width - textrect.width)/2;
					}
					textrect.y = extraspacey/2;
					renderImage(CEikCommandButton.prototype, instance, laf, 
							graphics, (bounds.width - imgBounds.width)/2,textrect.height+extraspacey/3, "image", true);					
				}break;
			}
			graphics.setForeground(laf.getColor("EEikColorButtonText"));
			if(textrect.width>0){
				graphics.drawFormattedString(text, textrect, flags);				
			}
		}
	}else{
		if( !(properties.text == null || properties.text == "") ){
			if(  ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill" ){
				textrect.x = (bounds.width - textBounds.x)/2;
			}
			if( bounds.width  < textBounds.x ){
				textrect.x = 0;
				textrect.width = bounds.width;
			}
			textrect.height = bounds.height;
			graphics.setForeground(laf.getColor("EEikColorButtonText"));
			graphics.drawFormattedString(text, textrect, flags);
		}		
	}    

}

function setBackground( laf, instance, graphics, rect ) {

	var properties = instance.properties;

	/* Emulator's commandButton image is defined as background */
	if ( !properties.displayPictureOnly ) {
		var image;
		if ( ( properties.state == "ESet" ) || ( properties.behavior == "StaysSet" )) {
			image = laf.getImage("commandButton.set.background");
			if ( image != null) {
				drawImage(instance, graphics, image, rect, false);
			}
		} else {
			image = laf.getImage("commandButton.noSet.background");
			if ( image != null) {
				drawImage(instance, graphics, image, rect, false);
			}
		}
	}
	if ( properties.defaultb ) graphics.setForeground( Colors.getColor( 255, 255, 255 ) );

}

function getFlags(instance){

	var properties = instance.properties;
	var flags ;
	flags |= Font.OVERFLOW_ELLIPSIS;
	flags |= Font.ANTIALIAS_OFF;
	flags |= Font.ALIGN_LEFT;
	flags |= Font.VERTICAL_ALIGN_CENTER;		
	return flags;
}


CEikCommandButton.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	
    var width = wHint > 0? wHint : 60;
    var height = hHint > 0? hHint : 24;
    var properties = instance.properties;	
    

    var text;

    var font = laf.getFont("NormalFont");	
    var bounds = new Point(width, height);
    var flags = getFlags(instance);	
    var rowsGap = laf.getInteger("RowLayoutManager.rowsGap",11);
    var ld = getLayoutData ( instance, "horizontalAlignment", "EQikLayoutHAlignInherit" );
    var extraspacex = 8;
    var extraspacey = 6;
    
    if(properties.text==null){
    	text = "button";
    }else{
    	text = properties.text;
    }
    var textBounds = font.formattedStringExtent(text, bounds, flags,rowsGap);		
	
	var imgBounds = getImageBounds(instance, properties.image);
	
	if (imgBounds != null) {
		if(text=="" || text.length==0 || text==null){
			height = imgBounds.height + extraspacey;
			if(!(ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill")){
				width = imgBounds.width + extraspacex;				
			}
		}else{
			if ( properties.layout == "EEikCmdButTextRightPictureLeft" || 
					properties.layout == "EEikCmdButTextLeftPictureRight" ){
				if(imgBounds.height + extraspacey >= height){
					height = imgBounds.height+extraspacey;					
				}
				if( !(textBounds.x > width - imgBounds.width - extraspacex || ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill") ){
					width = imgBounds.width + textBounds.x + extraspacex;					
				}
			}else{
				height = imgBounds.height + textBounds.y + extraspacey;
				if(!(ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill" || textBounds.x > width)){
					if(imgBounds.width > textBounds.x){
						width = imgBounds.width + extraspacex;
					}
					else{
						width = textBounds.x + extraspacex;					
					}
				}
			}
		}
	}else{
		if( !(textBounds.x > width || ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill") ){
			width = textBounds.x + extraspacex;					
		}
		if(text=="" || text.length==0 || text==null){
			height = extraspacey;
		}		
	} 
	
	return new Point(width, height);
}
//////////////////////////////////////////////////////////////////////////////////////////////////////
//IImageProperytInfo

function getImageSize(instance, propertyId, laf) {
	var properties = instance.properties;
	var imbounds=getImageBounds(instance,properties.image);
	var bounds = instance.getRenderingBounds();
    var boundsPoint = new Point(bounds.width, bounds.height);
    var flags = getFlags(instance);	
    var rowsGap = laf.getInteger("RowLayoutManager.rowsGap",11);
	var text = properties.text;
    var font = laf.getFont("NormalFont");	
	var textBounds = font.formattedStringExtent(text, boundsPoint, flags,rowsGap);
	var widthFactor = 1;
	var extraspacex = 8;

	switch(properties.layout) {
		case "EEikCmdButTextRightPictureLeft":{
			if(imbounds!=null){
				if( textBounds.x > bounds.width - imbounds.width - extraspacex){
					widthFactor=imbounds.width/(imbounds.width + textBounds.x + extraspacex);
				}
			}			
		}break;
		case "EEikCmdButTextLeftPictureRight":{
			if(imbounds!=null){
				if( textBounds.x > bounds.width - imbounds.width - extraspacex){
					widthFactor=imbounds.width/(imbounds.width + textBounds.x + extraspacex);
				}
			}			
		}break;
		case "EEikCmdButTextBottomPictureTop":{
			if(imbounds!=null){
				if( imbounds.width + extraspacex > bounds.width ){
					widthFactor=bounds.width/(imbounds.width + extraspacex);
				}
			}			
		}break;
		case "EEikCmdButTextTopPictureBottom":{
			if(imbounds!=null){
				if( imbounds.width + extraspacex > bounds.width ){
					widthFactor=bounds.width/(imbounds.width + extraspacex);
				}
			}			
		}break;
	}
	
	if(propertyId=="image"){
		if (imbounds==null){
			return new Point (18, 18);
		}else {
			return new Point(widthFactor*imbounds.width,imbounds.height);
		}
	}
	return null;
}

CEikCommandButton.prototype.getViewableSize = function(instance, propertyId, laf) {
	return getImageSize(instance, propertyId, laf);
}


CEikCommandButton.prototype.getAlignmentWeights = function(instance, propertyId, laf) {
	if (propertyId == "image") 
		{
		return new Point(ImageUtils.ALIGN_CENTER_OR_LEFT, ImageUtils.ALIGN_CENTER_OR_TOP);	
		}
	return null;
}

CEikCommandButton.prototype.isScaling = function(instance, propertyId, laf) {
	return true;
}

CEikCommandButton.prototype.isPreservingAspectRatio = function(instance, propertyId, laf) {
	return false;
}
////////////////////////////////////////////////////////////////////////////////
//IDirectLabelEdit

setupCommonDirectLabelEditing(CEikCommandButton.prototype, 
	"text", 
	areaWithParentWidth,
	function(instance, laf) { return laf.getFont("NormalFont"); } 
	)

///////////////////////////////////////////////////////////////////////////////
//IDirectImageEdit
	
setupCommonDirectImageEditing(CEikCommandButton.prototype,"image", 
	null  	// areafunction
);