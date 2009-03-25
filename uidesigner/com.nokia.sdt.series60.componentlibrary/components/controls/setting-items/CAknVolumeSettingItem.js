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


include("../../embeddedControlImplLibrary.js")

function CAknVolumeSettingItem() {
}

CAknVolumeSettingItem.prototype.drawContent = function(instance, laf, graphics, rect) {
	var properties = instance.properties;
	
	var barSize = laf.getInteger("volume.bar.width", 4);
	var barSpace = barSize + 2;
	var hpadding = rect.width - barSpace * 10;
	var vpadding = laf.getInteger("volume.padding.height", 4);
	var width = rect.width - hpadding;
	var height = rect.height - vpadding;
	
	var onColor = laf.getColor("volume.settingitem.onColor");
	var offColor = laf.getColor("volume.settingitem.offColor");
	
	for (var v = 0; v < 10; v++) {
		if (v < properties.value)
			graphics.setBackground(onColor);
		else
			graphics.setBackground(offColor);
			
		var x = Math.floor(hpadding/2 + v * barSpace);
		var barHeight = Math.floor((v + 1) * height / 10);
		var y = height - barHeight + vpadding/2;
		
		graphics.fillRoundRectangle(x, y, barSize, barHeight, barSize / 2 , barSize / 2);
	}
}

CAknVolumeSettingItem.prototype.getContentSize = function(instance, laf, size) {
	return size;
}

setupEmbeddedRendering(CAknVolumeSettingItem.prototype);

setupCommonEmbeddedDirectLabelEditing(CAknVolumeSettingItem.prototype, "value",
	null, null);
