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


include("../implLibrary.js")
include("../renderLibrary.js")
include("../srcgenLibrary.js")

function NaviVolumeVisual() {
}

function getNaviSize(instance, laf) {
	var properties = instance.properties;
	return new Point(properties.size.width, properties.size.height);
}

function getIconRect(instance, laf) {

	var iconRect = (0,0,10,10);
	return iconRect;
}

function volumeImage(instance, laf) {
		var properties = instance.properties;
		var icon = laf.getImage("volume_speaker_indicator"); // default
		
		switch (properties.volumeType) {
			case "R_AVKON_NAVI_PANE_VOLUME_INDICATOR":
				icon = laf.getImage("volume_speaker_indicator"); 
				break;
			case "R_AVKON_NAVI_PANE_RECORDER_VOLUME_INDICATOR":
				icon = laf.getImage("volume_mic_indicator"); 
				break;
			case "R_AVKON_NAVI_PANE_EARPIECE_VOLUME_INDICATOR":
				icon = laf.getImage("volume_earpiece_indicator");
				break; 
			}
		return icon;
}

NaviVolumeVisual.prototype.draw = function(instance, laf, graphics) {

	var icon = volumeImage(instance, laf);
	var iconRect = getIconRect(instance, laf);
	var size = getNaviSize(instance, laf);
	var rect = new Rectangle(0, 0, size.x, size.y);
	
	var rectIcon = new Rectangle(0, 0, size.y, size.y);
	var rectBar = new Rectangle(0, 0, (size.x - rectIcon.width), size.y);
	var doBlend = false;
	if (icon != null) {
		var imageData = icon.getImageData();
		graphics.setBackground(getBackgroundColor(instance, laf));
		var screenBackground = laf.getColor("screen.background");	
		graphics.setBackground(screenBackground);
		drawImage(instance, graphics, icon, rectIcon, doBlend);
	}
	
	//println(graphics.getBackground());
	
	drawBar (instance, laf, graphics, rectBar);
}


function drawBar(instance, laf, graphics, rect) {
	var properties = instance.properties;
	
	var arcWidth = laf.getInteger("naviVolume.bar.arcWidth", 2);
	var arcHeight = laf.getInteger("naviVolume.bar.arcHeight", 2);
	var barSize = laf.getInteger("naviVolume.bar.width", 4);
	var space = laf.getInteger ("naviVolume.bar.space", 1);
	
	var barSpace = barSize + space;
	var hpadding = rect.width - barSpace * 10 +40;
	var vpadding = laf.getInteger("volume.padding.height", 4)+2;
	var width = rect.width - hpadding;
	var height = rect.height - vpadding;
	
	var onColor = laf.getColor("volume.settingitem.onColor");
	var offColor = laf.getColor("volume.settingitem.offColor");
	
	for (var v = 0; v < 10; v++) {
		if (v <= properties.value)
			graphics.setBackground(onColor);
		else
			graphics.setBackground(offColor);
			
		var x = Math.floor(hpadding/2 + v * barSpace);
		var barHeight = Math.floor((v + 1) * height / 10)+2;
		var y = (height - barHeight + vpadding/2);
		
		graphics.fillRoundRectangle(x, y, barSize, barHeight, arcWidth, arcHeight);	
		graphics.setForeground(laf.getColor("navi.volume.shade"));
		graphics.drawRoundRectangle(x, y, barSize, barHeight, arcWidth, arcHeight);
	
	}
}