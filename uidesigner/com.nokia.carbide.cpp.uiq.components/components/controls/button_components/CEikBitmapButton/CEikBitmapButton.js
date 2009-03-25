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

function CEikBitmapButton() {
}


////////////////////////////////////////////////////////////////////////////////
// IVisualAppearance

CEikBitmapButton.prototype.draw = function(instance, laf, graphics) {
	var arcbuttonwidth=5;
	var arcbuttonheight=5;
    var extraspacex = 8;
    var extraspacey = 8;
    var properties = instance.properties;	    

	var bounds = instance.getRenderingBounds();

    var boundsPoint = new Point(bounds.width, bounds.height);
    var rowsGap = laf.getInteger("RowLayoutManager.rowsGap",11);
    var ld = getLayoutData ( instance, "horizontalAlignment", "EQikLayoutHAlignInherit" );

	var img1Bounds = getImageBounds(instance, properties.image1);
	var img2Bounds = getImageBounds(instance, properties.image2);
    
    var aux1 = getImageSize(instance, "image1", laf);
    var aux2 = getImageSize(instance, "image2", laf);
	if(img1Bounds != null){
	    img1Bounds = new Rectangle(0,0,aux1.x,aux1.y);	
	}
	if(img2Bounds != null){
		img2Bounds = new Rectangle(0,0,aux2.x,aux2.y);		
	}
	
	setBackground( laf, instance, graphics, bounds );

	var emptywidth; 

	if( img2Bounds ) {
		if( img1Bounds == null ){
			renderImage(CEikBitmapButton.prototype, instance, laf, 
					graphics, (bounds.width - img2Bounds.width)/2,extraspacey/2, "image2", true);
		}else{
			switch(properties.layout) {
				case "EEikCmdButFirstRightSecondLeft":{
					renderImage(CEikBitmapButton.prototype, instance, laf, 
							graphics,3*bounds.width/4 - img1Bounds.width/2,(bounds.height-img1Bounds.height)/2, "image1", true);
					renderImage(CEikBitmapButton.prototype, instance, laf, 
							graphics, bounds.width/4 - img2Bounds.width/2,(bounds.height-img2Bounds.height)/2, "image2", true);													
				}break;
				case "EEikCmdButFirstLeftSecondRight":{
					renderImage(CEikBitmapButton.prototype, instance, laf, 
							graphics, bounds.width/4 - img1Bounds.width/2,(bounds.height-img1Bounds.height+extraspacey)/2, "image1", true);
					renderImage(CEikBitmapButton.prototype, instance, laf, 
							graphics,3*bounds.width/4 - img2Bounds.width/2,(bounds.height-img2Bounds.height+extraspacey)/2, "image2", true);																			
				}break;
				case "EEikCmdButFirstBottomSecondTop":{
					if(img1Bounds.width > img2Bounds.width){
						renderImage(CEikBitmapButton.prototype, instance, laf, 
								graphics, (bounds.width - img1Bounds.width)/2,img2Bounds.height+extraspacey/2, "image1", true);
						renderImage(CEikBitmapButton.prototype, instance, laf, 
								graphics,(bounds.width - img2Bounds.width)/2 ,extraspacey/2, "image2", true);						
					}else{
						renderImage(CEikBitmapButton.prototype, instance, laf, 
								graphics, (bounds.width - img1Bounds.width)/2,img2Bounds.height+extraspacey/2, "image1", true);
						renderImage(CEikBitmapButton.prototype, instance, laf, 
								graphics,(bounds.width - img2Bounds.width)/2 ,extraspacey/2, "image2", true);												
					}
				}break;
				case "EEikCmdButFirstTopSecondBottom":{
					if(img1Bounds.width > img2Bounds.width){
						renderImage(CEikBitmapButton.prototype, instance, laf, 
								graphics, (bounds.width - img1Bounds.width)/2,extraspacey/2, "image1", true);
						renderImage(CEikBitmapButton.prototype, instance, laf, 
								graphics,(bounds.width - img2Bounds.width)/2 ,img1Bounds.height+extraspacey/2, "image2", true);						
					}else{
						renderImage(CEikBitmapButton.prototype, instance, laf, 
								graphics, (bounds.width - img1Bounds.width)/2,extraspacey/2, "image1", true);
						renderImage(CEikBitmapButton.prototype, instance, laf, 
								graphics,(bounds.width - img2Bounds.width)/2 ,img1Bounds.height+extraspacey/2, "image2", true);												
					}
				}break;
			}
		}
	}else{
		if( img1Bounds ){
			renderImage(CEikBitmapButton.prototype, instance, laf, 
					graphics, (bounds.width - img1Bounds.width)/2,extraspacey/2, "image1", true);
		}		
	}    
	
}

function setBackground( laf, instance, graphics, rect ) {

	var properties = instance.properties;

	/* Emulator's bitmapButton image is defined as background */
// Emulator doesn't apply displayPictureOnly
//	if ( !properties.displayPictureOnly ) { 
		var image;
		if ( ( properties.state == "ESet" ) || ( properties.behavior == "StaysSet" )) {
			image = laf.getImage("bitmapButton.set.background");
			if ( image != null) {
				drawImage(instance, graphics, image, rect, false);
			}
		} else {
			image = laf.getImage("bitmapButton.noSet.background");
			if ( image != null) {
				drawImage(instance, graphics, image, rect, false);
			}
		}
//	}
	if ( properties.defaultb ) graphics.setForeground( Colors.getColor( 255, 255, 255 ) );

}

CEikBitmapButton.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
    var width = wHint > 0? wHint : 60;
    var height = hHint > 0? hHint : 24;
    var properties = instance.properties;	  
  
    var bounds = new Point(width, height);
    var rowsGap = laf.getInteger("RowLayoutManager.rowsGap",11);
    var ld = getLayoutData ( instance, "horizontalAlignment", "EQikLayoutHAlignInherit" );
    var extraspacex = 8;
    var extraspacey = 8;
    
	var img1Bounds = getImageBounds(instance, properties.image1);
	var img2Bounds = getImageBounds(instance, properties.image2);
	
	if (img2Bounds) {
		if(img1Bounds == null){
			height = img2Bounds.height + extraspacey;
			if(!(ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill")){
				width = img2Bounds.width + extraspacex;				
			}
		}else{
			if ( properties.layout == "EEikCmdButFirstRightSecondLeft" || 
					properties.layout == "EEikCmdButFirstLeftSecondRight" ){
				if(img1Bounds.height > img2Bounds.height){
					height = img1Bounds.height+extraspacey;					
				}else{
					height = img2Bounds.height+extraspacey;										
				}
				if( !(ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill") ){
					width = img1Bounds.width + img2Bounds.width + extraspacex;					
				}
			}else{
				height = img1Bounds.height + img2Bounds.height + extraspacey;
				if(!(ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill")){
					if(img1Bounds.width > img2Bounds.width){
						width = img1Bounds.width + extraspacex;
					}else{
						width = img2Bounds.width + extraspacex;					
					}
				}
			}
		}
	}else{
		if( !(ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill") ){
			if (img1Bounds != null) {
				width = img1Bounds.width + extraspacex;
			}					
		}
		if(img1Bounds == null){
			height = extraspacey;
		}else{
			height = img1Bounds.height + extraspacey;			
		}		
	} 	
	return new Point(width, height);	
}


//////////////////////////////////////////////////////////////////////////////////////////////////////
//IImageProperytInfo

function getImageSize(instance, propertyId, laf) {
	var properties = instance.properties;
	var bounds = instance.getRenderingBounds();
    var extraspacex = 8;
    var extraspacey = 8;
    var width=18;
    var height=18;
    var widthFactor = 1;
    var heightFactor = 1;

	imbounds1=getImageBounds(instance,instance.properties.image1);
	imbounds2=getImageBounds(instance,instance.properties.image2);
	
	if(propertyId=="image1"){		
		if(imbounds1==null){
			return new Point (width, height);
		}else{
			if(properties.layout == "EEikCmdButFirstRightSecondLeft" ||
			   properties.layout == "EEikCmdButFirstLeftSecondRight"){
				if(imbounds2==null){
					widthFactor = (bounds.width - extraspacex)/imbounds1.width;
					heightFactor = (bounds.height - extraspacey)/imbounds1.height;
				}else{
					widthFactor = (bounds.width - extraspacex)/(imbounds1.width+imbounds2.width);
					if(imbounds1.height >= imbounds2.height && imbounds1.height > bounds.height - extraspacey){
						heightFactor = (bounds.height - extraspacey)/imbounds1.height;											
					}
					if(imbounds2.height > imbounds1.height && imbounds2.height > bounds.height - extraspacey){
						heightFactor = (bounds.height - extraspacey)/imbounds2.height;											
					}
				}				
			}else{
				if(imbounds2==null){
					widthFactor = (bounds.width - extraspacex)/imbounds1.width;
					heightFactor = (bounds.height - extraspacey)/imbounds1.height;
				}else{
					heightFactor = (bounds.height - extraspacey)/(imbounds1.height+imbounds2.height);
					if(imbounds1.width >= imbounds2.width && imbounds1.width > bounds.width - extraspacex){
						widthFactor = (bounds.width - extraspacex)/imbounds1.width;											
					}
					if(imbounds2.width > imbounds1.width && imbounds2.width > bounds.width - extraspacex){
						widthFactor = (bounds.width - extraspacex)/imbounds2.width;											
					}
				}								
			}
			if(widthFactor>1){
				widthFactor=1;
			}
			if(heightFactor>1){
				heightFactor=1;
			}
			return new Point(widthFactor*imbounds1.width,imbounds1.height);
		}
	}
	if(propertyId=="image2"){
		if(imbounds2==null){
			return new Point (width, height);
		}else{
			if(properties.layout == "EEikCmdButFirstRightSecondLeft" ||
			   properties.layout == "EEikCmdButFirstLeftSecondRight"){
				if(imbounds1==null){
					widthFactor = (bounds.width - extraspacex)/imbounds2.width;
					heightFactor = (bounds.height - extraspacey)/imbounds2.height;
				}else{
					widthFactor = (bounds.width - extraspacex)/(imbounds1.width+imbounds2.width);
					if(imbounds1.height > imbounds2.height && imbounds1.height > bounds.height - extraspacey){
						heightFactor = (bounds.height - extraspacey)/imbounds1.height;											
					}
					if(imbounds2.height >= imbounds1.height && imbounds2.height > bounds.height - extraspacey){
						heightFactor = (bounds.height - extraspacey)/imbounds2.height;											
					}
				}				
			}else{
				if(imbounds1==null){
					widthFactor = (bounds.width - extraspacex)/imbounds2.width;
					heightFactor = (bounds.height - extraspacey)/imbounds2.height;
				}else{
					heightFactor = (bounds.height - extraspacey)/(imbounds1.height+imbounds2.height);
					if(imbounds1.width > imbounds2.width && imbounds1.width > bounds.width - extraspacex){
						widthFactor = (bounds.width - extraspacex)/imbounds1.width;											
					}
					if(imbounds2.width >= imbounds1.width && imbounds2.width > bounds.width - extraspacex){
						widthFactor = (bounds.width - extraspacex)/imbounds2.width;											
					}
				}								
			}
			if(widthFactor>1){
				widthFactor=1;
			}
			if(heightFactor>1){
				heightFactor=1;
			}
			return new Point(widthFactor*imbounds2.width,imbounds2.height);
		}
	}
	return null;
}

CEikBitmapButton.prototype.getViewableSize = function(instance, propertyId, laf) {
	return getImageSize(instance, propertyId, laf);
}


CEikBitmapButton.prototype.getAlignmentWeights = function(instance, propertyId, laf) {
	if (propertyId == "image1" || propertyId == "image2") 
		{
		return new Point(ImageUtils.ALIGN_CENTER_OR_LEFT, ImageUtils.ALIGN_CENTER_OR_TOP);	
		}
	return null;
}

CEikBitmapButton.prototype.isScaling = function(instance, propertyId, laf) {
	return true;
}

CEikBitmapButton.prototype.isPreservingAspectRatio = function(instance, propertyId, laf) {
	return false;
}


///////////////////////////////////////////////////////////////////////////////
//IDirectImageEdit
	
setupCommonDirectImageEditing(CEikBitmapButton.prototype,"image2", 
	null  	// areafunction
);

setupCommonDirectImageEditing(CEikBitmapButton.prototype,"image1", 
		null  	// areafunction
);


////////////////////////////////////////////////////////////////////////////////
//IComponentValidator

CEikBitmapButton.prototype.validate = function(instance) {
	if (instance != null || instance != "") {
		if (!instance.isInstanceOf("com.nokia.carbide.uiq.CEikBitmapButton"))
			return null;
		var properties = instance.properties;
		if (properties.image1.bmpfile == "" && properties.image2.bmpfile == "") {
			var modelMessage = newModelMessage(IStatus.WARNING,formatString(lookupString("validate.flags"),
								[instance.name ]),instance, "flags", null);
			return [ modelMessage ];
					
		}
	}
	return null;
}

CEikBitmapButton.prototype.queryPropertyChange = function(instance, propertyId, newValue) {
	return null;
}	