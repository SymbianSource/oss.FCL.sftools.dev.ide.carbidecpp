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



function drawTitleText(instance, laf, graphics, titleText) {
	var properties = instance.properties;

	var textColor = laf.getColor("status.title.color");
   if (textColor != null)
        graphics.setForeground(textColor);
	var titleBounds = new Rectangle(0, 0, properties.size.width, properties.size.height)
		
	if (laf.getBoolean("is.portrait", true)) {
        var bgColor = laf.getColor("screen.background");
        if (bgColor != null)
            graphics.setBackground(bgColor);

		var font = laf.getFont("status.TitleFont");
		titleText = chooseScalableText(titleText, font, titleBounds.width);
	
		var oneLineExtent = font.stringExtent(titleText);
		if (oneLineExtent.x <= titleBounds.width) {
			graphics.setFont(font);
            titleBounds.y += (titleBounds.height - oneLineExtent.y) / 2;
			graphics.drawFormattedString(titleText, titleBounds, 
					Font.OVERFLOW_IGNORE|Font.DRAW_TRANSPARENT);
		}
		else {
			font = laf.getFont("HalfTitleFont");
			oneLineExtent = font.stringExtent(titleText);
			graphics.setFont(font);
			graphics.drawFormattedString(titleText, titleBounds, 
					Font.OVERFLOW_IGNORE|Font.DRAW_TRANSPARENT|Font.WRAPPING_ENABLED);
		}
	}
	else { // landscape, title is always one line
        var bgColor = laf.getColor("status.bar.color");
        if (bgColor != null)
            graphics.setBackground(bgColor);

        var font = laf.getFont("status.TitleFont");
        titleText = chooseScalableText(titleText, font, titleBounds.width);
        	
		var oneLineExtent = font.stringExtent(titleText);
		graphics.setFont(font);
        var y = (titleBounds.height - oneLineExtent.y) / 2;
		graphics.drawFormattedString(titleText, 
				new Rectangle(0, y, titleBounds.width, titleBounds.height), 
				Font.OVERFLOW_ELLIPSIS|Font.DRAW_TRANSPARENT);
	}

}
