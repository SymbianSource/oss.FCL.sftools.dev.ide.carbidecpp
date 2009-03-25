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
include("../../../textLinesNumberLibrary.js")
include("../../../layoutDataLibrary.js")

function CEikTextButton() {
}


////////////////////////////////////////////////////////////////////////////////
// IVisualAppearance	

CEikTextButton.prototype.draw = function(instance, laf, graphics) {
	var arcbuttonwidth=5;
	var arcbuttonheight=5;
    var extraspacex = 8;
    var extraspacey = 8;
    var properties = instance.properties;	
    

    var text1;
    var text2;
	var bounds = instance.getRenderingBounds();

    var font = laf.getFont("NormalFont");	
	graphics.setFont(font);	
    var boundsPoint = new Point(bounds.width, bounds.height);
    var flags = getFlags(instance);	
    var ld = getLayoutData ( instance, "horizontalAlignment", "EQikLayoutHAlignInherit" );

    if(properties.text1==null){
    	text1 = "";
    }else{
    	text1 = properties.text1;
    }
    if(properties.text2==null){
    	text2 = "";
    }else{
    	text2 = properties.text2;
    }
    var text1Bounds = font.formattedStringExtent(text1, boundsPoint, flags,0);		
    var text2Bounds = font.formattedStringExtent(text2, boundsPoint, flags,0);		
		
	setBackground( laf, instance, graphics, bounds );

	var emptywidth; 
    var text1rect = new Rectangle(0,0,text1Bounds.x,text1Bounds.y);
    var text2rect = new Rectangle(0,0,text2Bounds.x,text2Bounds.y);

	if( text2!="" && text2.length!=0 && text2!=null ) {
		if( properties.text1 == null || properties.text1 == "" ){
			emptywidth	= bounds.width - text2Bounds.x;
			if(emptywidth < 0){
				emptywidth = 0;					
			}
			if( text2Bounds.x > bounds.width ){
				text2rect.width = bounds.width;
			}
			if(  ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill" ){
				text2rect.x = (bounds.width - text2rect.width)/2;
				text2rect.width = bounds.width;
			}

		}else{
			switch(properties.layout) {
				case "EEikCmdButFirstRightSecondLeft":{
					emptywidth	= bounds.width - (text2Bounds.x + text1Bounds.x + extraspacex);
					if(emptywidth < 0){
						emptywidth = 0;					
					}
					if( text1Bounds.x + text2Bounds.x + extraspacex > bounds.width ){
						text1rect.width = bounds.width*(text1Bounds.x + extraspacex/2)/(text1Bounds.x + text2Bounds.x + extraspacex);	
						text2rect.width = bounds.width*(text2Bounds.x + extraspacex/2)/(text1Bounds.x + text2Bounds.x + extraspacex);	
						text1rect.x = text2rect.width;
					}else{
						text1rect.x = bounds.width - text1Bounds.x;
					}
				}break;
				case "EEikCmdButFirstLeftSecondRight":{
					emptywidth	= bounds.width - (text2Bounds.x + text1Bounds.x + extraspacex);
					if(emptywidth < 0){
						emptywidth = 0;					
					}
					if( text1Bounds.x + text2Bounds.x + extraspacex > bounds.width ){
						text1rect.width = bounds.width*(text1Bounds.x + extraspacex/2)/(text1Bounds.x + text2Bounds.x + extraspacex);	
						text2rect.width = bounds.width*(text2Bounds.x + extraspacex/2)/(text1Bounds.x + text2Bounds.x + extraspacex);	
						text2rect.x = text1rect.width;
					}else{
						text2rect.x = bounds.width - text2Bounds.x;							
					}
				}break;
				case "EEikCmdButFirstBottomSecondTop":{
					if(text1Bounds.x > bounds.width){
						text1rect.width = bounds.width;							
					}
					if(text2Bounds.x > bounds.width){
						text2rect.width = bounds.width;							
					}
					text1rect.x=extraspacex/2;
					text2rect.x=extraspacex/2;
					if(  ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill" ){
						text1rect.x = (bounds.width - text1rect.width)/2;
						text2rect.x = (bounds.width - text2rect.width)/2;
					}
					text1rect.y = text2rect.height +2;
				}break;
				case "EEikCmdButFirstTopSecondBottom":{
					if(text1Bounds.x > bounds.width){
						text1rect.width = bounds.width;							
					}
					if(text2Bounds.x > bounds.width){
						text2rect.width = bounds.width;							
					}
					text1rect.x=extraspacex/2;
					text2rect.x=extraspacex/2;
					if(  ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill" ){
						text1rect.x = (bounds.width - text1rect.width)/2;
						text2rect.x = (bounds.width - text2rect.width)/2;
					}
					text2rect.y = text1rect.height +2;
				}break;
			}
			graphics.setForeground(laf.getColor("EEikColorButtonText"));
			graphics.drawFormattedString(text1, text1rect, flags);
		}
		graphics.setForeground(laf.getColor("EEikColorButtonText"));
		graphics.drawFormattedString(text2, text2rect, flags);
	}else{
		if( !(properties.text1 == null || properties.text1 == "") ){
			if(text1Bounds.x > bounds.width){
				text1rect.width = bounds.width;							
			}
			if(  ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill" ){
				text1rect.x = (bounds.width - text1rect.width)/2;
			}
			graphics.setForeground(laf.getColor("EEikColorButtonText"));
			graphics.drawFormattedString(text1, text1rect, flags);
		}		
	}    
}

function setBackground( laf, instance, graphics, rect ) {

	var properties = instance.properties;

	var image;
	if ( ( properties.state == "ESet" ) || ( properties.behavior == "StaysSet" )) {
		image = laf.getImage("textButton.set.background");
		if ( image != null) {
			drawImage(instance, graphics, image, rect, false);
		}
	} else {
		image = laf.getImage("textButton.noSet.background");
		if ( image != null) {
			drawImage(instance, graphics, image, rect, false);
		}
	}
	if ( properties.defaultb ) graphics.setForeground( Colors.getColor( 255, 255, 255 ) );

}

function getFlags(instance,text){
	var properties = instance.properties;
	var flags ;
	flags |= Font.OVERFLOW_ELLIPSIS;
	flags |= Font.ANTIALIAS_OFF;
	flags |= Font.ALIGN_LEFT;
	flags |= Font.VERTICAL_ALIGN_CENTER;		
	return flags;
}


CEikTextButton.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {

	var width = wHint > 0? wHint : 60;
    var height = hHint > 0? hHint : 24;
    var properties = instance.properties;	

    var text1;
    var text2; 

    var font = laf.getFont("NormalFont");	
    var bounds = new Point(width, height);
    var flags = getFlags(instance);	
    var ld = getLayoutData ( instance, "horizontalAlignment", "EQikLayoutHAlignInherit" );
    var extraspacex = 8;
    var extraspacey = 8;
    
    if(properties.text1==null){
    	text1 = "text 1";
    }else{
    	text1 = properties.text1;
    }
    var text1Bounds = font.formattedStringExtent(text1, bounds, flags,0);		
    if(properties.text2==null){
    	text2 = "";
    }else{
    	text2 = properties.text2;
    }
    var text2Bounds = font.formattedStringExtent(text2, bounds, flags,0);		
	if (text2!="" && text2.length!=0 && text2!=null) {
		if(text1=="" || text1.length==0 || text1==null){
			height = text2Bounds.y + extraspacey;
			if(!(ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill")){
				width = text2Bounds.x + extraspacex;				
			}
		}else{
			if ( properties.layout == "EEikCmdButFirstRightSecondLeft" || 
					properties.layout == "EEikCmdButFirstLeftSecondRight" ){
				if(text2Bounds.y + extraspacey >= height){
					height = text2Bounds.y;					
				}
				if( !(text1Bounds.x > width - text2Bounds.x - extraspacex || ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill") ){
					width = text2Bounds.x + text1Bounds.x + extraspacex;					
				}
			}else{
				height = text2Bounds.y + text1Bounds.y + extraspacey;
				if(!(ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill" || text1Bounds.x > width)){
					if(text2Bounds.x > text1Bounds.x){
						width = text2Bounds.x + extraspacex;
					}
					else{
						width = text1Bounds.x + extraspacex;					
					}
				}
			}
		}
	}else{
		if( !(text1Bounds.x > width || ld == "EQikLayoutHAlignInherit" || ld == "EQikLayoutHAlignFill") ){
			width = text1Bounds.x + extraspacex;			
		}
		height = text1Bounds.y + extraspacey;	
		if(text1=="" || text1.length==0 || text1==null){
			height = extraspacey;
		}		
	} 
	return new Point(width, height);
}


////////////////////////////////////////////////////////////////////////////////
//IDirectLabelEdit

setupDirectLabelEditingTextButton(CEikTextButton.prototype, 
		"text1", "text2",
		null,
		function(instance, laf) { return laf.getFont("NormalFont"); } 
		)

function setupDirectLabelEditingTextButton(prototype, text1, text2, areafunction, fontfunction) {

	prototype.getPropertyPaths = function(instance) {
		return new Array (text1, text2);
	}

	prototype.getLabelBounds = function(instance, propertyPath, laf) {

		var properties=instance.properties;
		var layoutBounds = instance.getLayoutBounds();
		var width = layoutBounds.width-5;					
		var height = 48;
		
		if( (propertyPath=="text1" && properties.layout=="EEikCmdButFirstRightSecondLeft") ||
			(propertyPath=="text2" && properties.layout=="EEikCmdButFirstLeftSecondRight")	)
			{
			height=24;
			return new Rectangle(width/2,3,width/2-6,height-6);
			}
		if( (propertyPath=="text2" && properties.layout=="EEikCmdButFirstRightSecondLeft") ||
			(propertyPath=="text1" && properties.layout=="EEikCmdButFirstLeftSecondRight")	)
			{
			height=24;
			return new Rectangle(3,3,width/2-6,height-6);
			}
		if( (propertyPath=="text1" && properties.layout=="EEikCmdButFirstTopSecondBottom") ||
			(propertyPath=="text2" && properties.layout=="EEikCmdButFirstBottomSecondTop")	)
			return new Rectangle(3,3,width-6,height/2-6);
		if( (propertyPath=="text2" && properties.layout=="EEikCmdButFirstTopSecondBottom") ||
			(propertyPath=="text1" && properties.layout=="EEikCmdButFirstBottomSecondTop")	)
			return new Rectangle(3,24,width-6,height/2-6);	
	}

	prototype.getLabelFont = function(instance, propertyPath, laf) {
		if (fontfunction)
			return fontfunction(instance, laf);
		return null;
	}

}	

////////////////////////////////////////////////////////////////////////////////
//IComponentValidator

CEikTextButton.prototype.validate = function(instance) {
	if (instance != null || instance != "") {
		if (!instance.isInstanceOf("com.nokia.carbide.uiq.CEikTextButton"))
			return null;
		var properties = instance.properties;
		if (properties.text1== "" && properties.text2 == "") {
			return [createSimpleModelError(instance, 
									"text",
									formatString(lookupString("blankTextError"),
									[instance.name ]),								
									null )
								   ];	
		}
	}
	return null;
}

CEikTextButton.prototype.queryPropertyChange = function(instance, propertyId, newValue) {
	return null;
}	