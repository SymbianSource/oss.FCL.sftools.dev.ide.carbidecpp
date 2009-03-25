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
*
*/


function getTextBounds (lines, width, height, flags, font, pixelGapBetweenLines) {

	var newBounds;
	var textHeight = 0;
	var textWidth = 0;

	for (var i = 0; i < lines.length; i++ ) {
		newBounds = font.formattedStringExtent(lines[i], 
						new Point (width, height),
						flags, pixelGapBetweenLines);
		textHeight += newBounds.y;	
		if ( newBounds.x > textWidth) {
			textWidth = newBounds.x;
		}
	}
	return new Point (textWidth, textHeight );
}

function getMaxLinesAllowedByParent(instance) {
	var parent = instance.parent;
	if (parent != null && isCQikContainer(parent)) {
		return getMaxLinesAllowedInCQikContainer (instance);
	} else if (parent != null && isItemSlot(parent)) {
		var grandParent = parent.parent;
		if  (grandParent != null && isSystemBuildingBlock(grandParent)) {
			return getMaxLinesAllowedInSlot(grandParent.properties.type, parent.properties.slotId);
		}
	}
	return 1;
}

function getMaxLinesAllowedInCQikContainer (instance) {
	var maxLinesNumber = 2147483647;
	if (instance != null && isCEikLabel(instance)) {
		return maxLinesNumber;
	} 
	return 1;
}

function isCEikLabel(instance) {
	return (instance.component.id).substring(0, 32) == "com.nokia.carbide.uiq.CEikLabel_";
}

function isCQikContainer(instance) {
	return instance.component.id == "com.nokia.carbide.uiq.CQikContainer";
}

function isItemSlot(instance) {
	return instance.component.id == "com.nokia.carbide.uiq.ItemSlot";
}

function isSystemBuildingBlock(instance) {
	return instance.component.id == "com.nokia.carbide.uiq.SystemBuildingBlock";
}

function getMaxLinesAllowedInSlot(typeOfSBB, slotId) {
	//println("***getMaxLinesAllowedInSlot");
	var maxLinesNumber = 2147483647;
	
	switch (typeOfSBB) {
		case "EQikCtOnelineBuildingBlock":
		case "EQikCtIconOnelineBuildingBlock":
		case "EQikCtOnelineIconBuildingBlock":
		case "EQikCtIconOnelineIconBuildingBlock":
		case "EQikCtMediumThumbnailDoubleOnelineBuildingBlock":
		case "EQikCtCaptionedOnelineBuildingBlock":
		case "EQikCtIconCaptionedOnelineBuildingBlock":
		case "EQikCtIconIconOnelineBuildingBlock":
		case "EQikCtHalflineHalflineBuildingBlock":
		case "EQikCtCaptionedHalflineBuildingBlock":
			return 1;
		case "EQikCtTwolineBuildingBlock":
		case "EQikCtIconTwolineBuildingBlock":
		case "EQikCtTwolineIconBuildingBlock":
		case "EQikCtIconTwolineIconBuildingBlock":
			return 2;
		case "EQikCtLargeThumbnailThreelineBuildingBlock":
			return 3;
		case "EQikCtManylinesBuildingBlock":
			return maxLinesNumber;
		case "EQikCtCaptionedTwolineBuildingBlock":
		case "EQikCtIconCaptionedTwolineBuildingBlock":
			return evaluateSlot(slotId, 1, 2);
	}
	
	return 1;
}

function evaluateSlot(slotId, valueForItemSlot1, valueForItemSlot2) {
//	println("***slotId: " + slotId);
	if (slotId  == "EQikItemSlot1") {
//		println("***valueForItemSlot1: " + valueForItemSlot1);
		return valueForItemSlot1;
	} else {
		return valueForItemSlot2;
	}
}