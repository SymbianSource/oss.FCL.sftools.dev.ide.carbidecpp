/*		
 *		graphics (wrapped SWT GC)
 *		Colors (object from which getColor(r,g,b) is available)
 *		Fonts (object from which getFont("path") is available)
 *		Images (object from which newImage(device,w,h) is available)
 */
function CBAVisual() {
}

CBAVisual.prototype.draw = function() {
	fontFile = "Alb17.14.ttf";

	graphics.setFont(Fonts.getGlobalFont("data/s60/fonts/"+fontFile));
	graphics.setBackground(Colors.getColor(0, 0, 0))

	// get bounding rect
	var rect = new Rectangle(5, 0,
		properties.size.width - 10, properties.size.height)
		
	graphics.drawFormattedString(properties.CBA.leftText, rect, Font.ALIGN_LEFT, 0);
	
	graphics.drawFormattedString(properties.CBA.rightText, rect, Font.ALIGN_RIGHT, 0);
}

CBAVisual.prototype.getPreferredSize = function(wHint, hHint) {
	return null; // needs implementation	
}

