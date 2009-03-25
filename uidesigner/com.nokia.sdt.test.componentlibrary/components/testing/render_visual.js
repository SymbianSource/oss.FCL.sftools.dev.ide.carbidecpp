/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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

/*
 * Render test
 *
 * globals available are:
 *
 *		instance (WrappedInstance)
 *		parent (WrappedInstance)
 *		children (WrappedInstance[])
 *		
 * rendering globals:
 *		graphics (wrapped SWT GC)
 *		Colors (object from which getColor(r,g,b) is available)
 *		Fonts (object from which getFont("path") is available)
 *		Images (object from which newImage(device,w,h) is available)
 */

include("library.js")

function Visual() {
}

function detailedDraw(string, x, y) {
	var extent = graphics.stringExtent(string)
	var metrics = graphics.getFontMetrics()
	var orig = graphics.getForeground()
	
	var hy = y + metrics.getHeight() 

	var origbk = graphics.getBackground()
	graphics.setBackground(Colors.getColor(192,192,192))
	graphics.fillRectangle(x, hy-metrics.getAscent(), extent.x, metrics.getAscent()+metrics.getDescent()+metrics.getLeading())
	graphics.setBackground(origbk)
	
		
	java.lang.System.out.println("height="+metrics.getHeight()+
		" descent="+metrics.getDescent()+
		" ascent="+metrics.getAscent())
	graphics.setLineDash(new Array(1,1))
	
	// baseline (green)
	graphics.setForeground(Colors.getColor(0, 255, 0))
	graphics.drawLine(x, hy, x+extent.x, hy)

	// descent (cyan)
	graphics.setForeground(Colors.getColor(0, 255, 255))
	graphics.drawLine(x+1, hy+metrics.getDescent(), x+extent.x, hy+metrics.getDescent())

	// ascent (yellow)
	graphics.setForeground(Colors.getColor(255, 255, 0))
	graphics.drawLine(x, hy-metrics.getAscent(), x+extent.x, hy-metrics.getAscent())

	// height (red)
	graphics.setForeground(Colors.getColor(255, 0, 0))
	graphics.drawLine(x+1, y, x+extent.x, y)

	graphics.setLineDash(null)

	graphics.setForeground(orig)
	graphics.drawString(string, x, y)	
	
	
	return extent.y
}

Visual.prototype.getPattern = function(type) {
	if (this.image3 == null) {
		this.image3 = Images.newImage(graphics.getDevice(), 3, 3)
	}
	if (this.image4 == null) {
		this.image4 = Images.newImage(graphics.getDevice(), 4, 4)
	}
	
	type = Math.floor(type)
	var img;
	if (type != 8.0 && type != 3.0 && type != 5.0)
		img = this.image3
	else
		img = this.image4
		
	var gc = new GC(graphics.getDevice(), img)

	gc.setBackground(Colors.getColor(255, 255, 255))
	gc.fillRectangle(0, 0, 4, 4);
	gc.setBackground(Colors.getColor(255, 255,255))
	gc.setForeground(Colors.getColor(0, 0, 0))
	
	switch (type) {
	case 1: // solid
	default:
		gc.setBackground(Colors.getColor(0, 0, 0))
		gc.fillRectangle(0, 0, 5, 5); 
		break;
	case 3: // vertical hatch
		gc.drawLine(1, 0, 1, 3); 
		gc.drawLine(3, 0, 3, 3); 
		break;
	case 4: // forward diagonal hatch
		gc.drawLine(0, 2, 2, 0); 
		break;
	case 5: // horizontal hatch
		gc.drawLine(0, 1, 3, 1);
		gc.drawLine(0, 3, 3, 3);
		break;
	case 6: // rearward diagonal hatch
		gc.drawLine(0, 0, 2, 2);
		break;
	case 7: // square cross hatch
		gc.drawLine(1, 0, 1, 3);
		gc.drawLine(0, 1, 3, 1);
		break;
	case 8: // diamond cross hatch
		gc.drawLine(0, 0, 3, 3); 
		gc.drawLine(2, 0, 0, 2);
		break;
	}
	
	gc.dispose()
	
	return new Pattern(graphics.getDevice(), img)
}

Visual.prototype.draw = function(instance, laf, graphics) {
	var properties = instance.properties
	var width = properties["size"].width;
	var height = properties.size["height"]

	var base = "data/s60"
	//var base = "c:/downloads"
	var thickness;
	
	var r,p,x,y,size,border;
	
	switch (properties.appearance) {
	case 0:
	default:
		thickness = 1;
		graphics.setLineWidth(thickness)
		graphics.setForeground(getBlue())
		graphics.setBackground(Colors.getColor(255, 255, 255))
		
		graphics.fillGradientRectangle(0, 0, properties.size.width, properties.size.height, true)
		break;
	case 1:
		thickness = 2;
		graphics.setLineWidth(thickness)
	
		r = new Rectangle(width/2, height/2, width/2-4, height/2-4)
		
		graphics.setForeground(Colors.getColor(48, 64, 64))
		graphics.setLineWidth(2)
		graphics.drawRectangle(r)			
		break;
	case 2:
		r = new Rectangle(0, 0, width, height)
		
		graphics.setForeground(Colors.getColor(0, 0, 64))
		graphics.setLineWidth(2)
		graphics.drawRectangle(r)			
		break;
	case 3:
		// ensure Path is available
		p = new Path(graphics.getDevice())
		p.moveTo(width/2, 0)
		p.lineTo(0, height)
		p.lineTo(width, height/2)
		graphics.setClipping(p);
	
		graphics.setForeground(Colors.getColor(128, 128, 0))
		graphics.setBackground(Colors.getColor(128, 128, 0))
		graphics.setAlpha(64)	// note: no effect yet
		graphics.setLineWidth(1)
		
		size = properties.value
		if (size <= 0)
			size = 1
		
		for (y=0; y<height; y+=size)
			for (x=size*(((y/size)&1)^1); x<width; x+=size*2)
				graphics.fillRectangle(x, y, size, size)
				
		graphics.setForeground(Colors.getColor(0, 128, 128))
		graphics.setBackground(Colors.getColor(0, 128, 128))
		graphics.setAlpha(128)	// note: no effect yet
		for (y=0; y<height; y+=size)
			for (x=size*((y/size)&1); x<width; x+=size*2)
				graphics.fillRectangle(x, y, size, size)	
		break;
		
	case 4:
		r = new Rectangle(0, 0, width, height)
		graphics.setLineWidth(1)
		graphics.drawRectangle(r)
		
		r = new Rectangle(4, height/2, width-8, height/2-4)
	
		graphics.drawRectangle(r)
		graphics.setFont(Fonts.getGlobalFont(base+"/fonts/Alp17.14.ttf", 14))
		
		graphics.setForeground(Colors.getColor(0, 0, 0))
		//detailedDraw("Hello", r.x, r.y)
		graphics.drawFormattedString("\"Hello Sedona!\" says a wrapped box.\nFoo,\nbar.",
			r,
			Font.WRAPPING_ENABLED+Font.DRAW_TRANSPARENT+Font.ALIGN_CENTER);	
		break;
		
	case 5:
		graphics.setFont(Fonts.getGlobalFont(base+"/fonts/Alb13.10.ttf", 10))
		h = graphics.getFontMetrics().getHeight()
		r = new Rectangle(4, height/4, width-8, height/4*3-8)
		graphics.drawFormattedString("First", r, 0)
		r.y += h
		graphics.drawFormattedString("Second", r, Font.OPTIONS_STRIKETHROUGH)
		r.y += h
		graphics.drawFormattedString("Third", r, Font.OPTIONS_UNDERLINE)
		break;
		
	case 6:
		r = 32
		str="!xpEdant \u011E \u52a3\u554f\u6C55"
		graphics.setFont(Fonts.getGlobalFont(base+"/fonts/Alb19.15.ttf", 15))
		r += detailedDraw(str, 4, r)
		graphics.setFont(Fonts.getGlobalFont(base+"/fonts/Alp17.14.ttf", 14))
		r += detailedDraw(str, 4, r)
		graphics.setFont(Fonts.getGlobalFont(base+"/fonts/Alpi17.14.ttf", 14))
		r += detailedDraw(str, 4, r)
		graphics.setFont(Fonts.getGlobalFont(base+"/fonts/Albi17b.14.ttf", 14))
		r += detailedDraw(str, 4, r)
		graphics.setFont(Fonts.getGlobalFont(base+"/fonts/Alb12.9.ttf", 9))
		r += detailedDraw(str, 4, r)
		graphics.setFont(Fonts.getGlobalFont(base+"/fonts/Alb17.14.ttf", 14))
		r += detailedDraw(str, 4, r)
		graphics.setFont(Fonts.getGlobalFont(base+"/fonts/Aco21.16.ttf", 16))
		r += detailedDraw("|=+-\u00d7\u00f7", 4, r)
		graphics.setFont(Fonts.getGlobalFont(base+"/fonts/JapanPlain12.12.ttf", 12))
		r += detailedDraw(str, 4, r)
		graphics.setFont(Fonts.getGlobalFont(base+"/fonts/JapanPlain16.17.ttf", 17))
		r += detailedDraw(str, 4, r)
		//graphics.setFont(Fonts.getGlobalFont("c:/winnt/fonts/Arial.ttf", 12))
		//r += detailedDraw(str, 4, r)
		//graphics.setFont(Fonts.getGlobalFont("c:/winnt/fonts/ArialUni.ttf", 12))
		//r += detailedDraw(str, 4, r)
		break;

	case 7:
		graphics.setBackground(Colors.getColor(64,160,160))
		graphics.fillRectangle(0, 0, width, height)

		border = width/8
		// ensure Pattern works
		p = new Path(graphics.getDevice())
		p.moveTo(border, height/2)
		p.quadTo(border, border, width/2-border, border*2)
		p.quadTo(width-border, border, width-border, height/2-border)
		p.quadTo(width-border, height-border, width/2-border, height-border)
		p.quadTo(border, height-border, border, height/2-border)
		graphics.setClipping(p);
	
		pattern = this.getPattern(properties.value)
		graphics.setBackgroundPattern(pattern)
		graphics.setForegroundPattern(pattern)
		
//		graphics.setBackground(Colors.getColor(255, 128, 64))
		graphics.setForeground(Colors.getColor(0, 0, 0))
		graphics.fillRectangle(0, 0, width, height)
		
		break;
		

	}

	// draw a label on top identifying this	
	full = new Rectangle(0, 0, width, height)
	graphics.setClipping(full)
	graphics.setForeground(Colors.getColor(0, 0, 128))
	graphics.setBackground(Colors.getColor(128, 128, 128))
	//java.lang.System.out.println("1")
	graphics.setFont(Fonts.getGlobalFont(base+"/fonts/Alpi12.9.ttf", 9))
	
	s = '(name='+properties.name+'; appearance='+properties.appearance+')';
	r = graphics.formattedStringExtent(s, full, Font.WRAPPING_ENABLED)
	//java.lang.System.out.println("r="+r)
	graphics.drawFormattedString(s, r,
		Font.WRAPPING_ENABLED+Font.DRAW_OPAQUE);
}

Visual.prototype.getPreferredSize = function(wHint, hHint) {
	return null; // needs implementation	
}

