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


function findImmediateChildByComponentId(children, componentID) {
    var result = null;
	for (var i in children) {
		var child = children[i];
		if (child.component != null && child.component.isOfType(componentID)) {
			result = child;
			break;
		}
	}
	return result;
}

function isRestricted(instance) {
	return instance.component.id == "com.nokia.sdt.series60.CBARestricted";
}

function findCbaLeftId(children) {
	var cba = findImmediateChildByComponentId(children, "com.nokia.sdt.series60.CBABase");
	if (cba == null) return null;
	return getCbaLeftId(cba);
}

function findCbaMiddleId(children) {
	var cba = findImmediateChildByComponentId(children, "com.nokia.sdt.series60.CBABase");
	if (cba == null) return null;
	return getCbaMiddleId(cba);
}


function getCbaLeftId(cba) {
	var info = cba.properties.info;
	if (isRestricted(cba)) {
		if ((info == "R_AVKON_SOFTKEYS_OPTIONS_BACK") || (info == "R_AVKON_SOFTKEYS_OPTIONS_EXIT"))
			return "EAknSoftkeyOptions";
		else if (info == "R_AVKON_SOFTKEYS_OK_BACK")
			return "EAknSoftkeyOk";
	}
	else {
		// need to check, since CBA may not be emitted if not custom (thus no enumerator registered)
		//println("cba:"+cba.properties.info.editableValue);
		if (cba.properties.info.editableValue != "com.nokia.sdt.series60.CBA.Type.CUSTOM")
			return cba.properties.info.leftId;
		else
			return Engine.findBuiltinOrGeneratedEnumeratorForAlgorithm(cba, "leftId", "com.nokia.sdt.component.symbian.NAME_ALG_COMMANDS");
	}
}		

function getCbaMiddleId(cba) {
	var info = cba.properties.info;
	// TODO: Need to revisit this code for MSK
	if (isRestricted(cba)) {
		if ((info == "R_AVKON_SOFTKEYS_OPTIONS_BACK") || (info == "R_AVKON_SOFTKEYS_OPTIONS_EXIT"))
			return "EAknSoftkeyOptions";
		else if (info == "R_AVKON_SOFTKEYS_OK_BACK")
			return "EAknSoftkeyOk";
	}
	else {
		// need to check, since CBA may not be emitted if not custom (thus no enumerator registered)
		//println("cba:"+cba.properties.info.editableValue);
		if (cba.properties.info.editableValue != "com.nokia.sdt.series60.CBA.Type.CUSTOM")
			return cba.properties.info.middleId;
		else
			return Engine.findBuiltinOrGeneratedEnumeratorForAlgorithm(cba, "middleId", "com.nokia.sdt.component.symbian.NAME_ALG_COMMANDS");
	}
}		

function findCbaRightId(children) {
	var cba = findImmediateChildByComponentId(children, "com.nokia.sdt.series60.CBABase");
	if (cba == null) return null;
	return getCbaRightId(cba);
}	

function getCbaRightId(cba) {
	var info = cba.properties.info;
	if (isRestricted(cba)) {
		if ((info == "R_AVKON_SOFTKEYS_OPTIONS_BACK") || (info == "R_AVKON_SOFTKEYS_OK_BACK") || (info == "R_AVKON_SOFTKEYS_BACK"))
			return "EAknSoftkeyBack";
		else if (info == "R_AVKON_SOFTKEYS_OPTIONS_EXIT")
			return "EAknSoftkeyExit";
	}
	else {
		// need to check, since CBA may not be emitted if not custom (thus no enumerator registered)
		if (cba.properties.info.editableValue != "com.nokia.sdt.series60.CBA.Type.CUSTOM")
			return cba.properties.info.rightId;
		else
			return Engine.findBuiltinOrGeneratedEnumeratorForAlgorithm(cba, "rightId", "com.nokia.sdt.component.symbian.NAME_ALG_COMMANDS");
	}
}

function drawCBA(leftText, rightText, middleText, location, size, laf, graphics) {
	var x = location.x;
	var y = location.y;
	var width = size.x;
	var height = size.y;

	var backColor = laf.getColor("control.pane.background");
	graphics.setBackground(backColor);

	var font = laf.getFont("control.pane.font");
		
	graphics.setFont(font);
	graphics.setForeground(laf.getColor("control.pane.text"));
	
	var margin = laf.getInteger("control.pane.text.margin", 5);
	var extent = font.stringExtent(leftText + "/" + rightText)

	if (laf.getBoolean("is.portrait", true)) {
		var fontOffset = (height - extent.y) / 2

		var rect = new Rectangle(x + margin, y + fontOffset, 
				width - (2*margin), height - fontOffset);

		graphics.fillRectangle(x, y, width, height);

		leftText = chooseScalableText(leftText, font, rect.width);
		graphics.drawFormattedString(leftText, rect, Font.ALIGN_LEFT, 0);
		rightText = chooseScalableText(rightText, font, rect.width);
		graphics.drawFormattedString(rightText, rect, Font.ALIGN_RIGHT, 0);
		middleText = chooseScalableText(middleText, font, rect.width);
		if (middleText == "_" || middleText == "."){
			var imageMSK;
			if (middleText == "_") {
				imageMSK = laf.getImage("msk.context.options.icon");
			} else {
				imageMSK = laf.getImage("msk.left.key.icon");
			}
			
			if (imageMSK != null) {
				var imageData = imageMSK.getImageData();
				graphics.drawImage(imageMSK, 0, 0, imageData.width, imageData.height,
				width/2-imageData.width/2, height/2-imageData.height/2, imageData.width, imageData.height);

			} else {
				graphics.drawFormattedString("NO IMAGE", rect, Font.ALIGN_CENTER, 0);
			}
		}  else {
			graphics.drawFormattedString(middleText, rect, Font.ALIGN_CENTER, 0);
		}
	}
	else {
		// middle soft key text is not displayed in landscape
		var fontHeight = font.getHeight();
		var sbar1Bounds = laf.getRectangle("status.bar1.bounds");
		var sbar2Bounds = laf.getRectangle("status.bar2.bounds");
		var gradientStart = laf.getColor("control.pane.gradient.start");
		var gradientEnd = laf.getColor("control.pane.gradient.end");
		graphics.setBackground(gradientEnd);

		var alignCenter = laf.getBoolean("cba.align.center", false);
		var flags = alignCenter ? Font.ALIGN_CENTER : Font.ALIGN_RIGHT;
		
		var fontOffset = (sbar1Bounds.height - extent.y) / 2

		var rect = new Rectangle(x, y + fontOffset, width-margin, height-fontOffset);
		graphics.setForeground(gradientStart);
		graphics.fillGradientRectangle(x, y + sbar1Bounds.y, width, sbar1Bounds.height, false);
		rightText = chooseScalableText(rightText, font, rect.width);
		graphics.setForeground(laf.getColor("control.pane.text"));
		graphics.drawFormattedString(rightText, rect, flags, 0);
		
		rect.y = y + height - sbar1Bounds.height + fontOffset;
		rect.height = sbar1Bounds.height - fontOffset;

		graphics.setForeground(gradientStart);
		graphics.fillGradientRectangle(x, y + sbar2Bounds.y, width, sbar2Bounds.height, false);
		leftText = chooseScalableText(leftText, font, rect.width);
		graphics.setForeground(laf.getColor("control.pane.text"));
		graphics.drawFormattedString(leftText, rect, flags, 0);
	}
}

