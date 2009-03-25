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
* START_USECASES: CU13 END_USECASES
*
*
*/

include("../../implLibrary.js")
include("../../renderLibrary.js")

function ListBoxItem() {
}

////////////////////////////////////////////////////////////////////////////////
// IPropertyExtenders

ListBoxItem.prototype.getPropertyExtenders = function(instance, targetInstance) {
	if (instance == targetInstance) {
		return [ instance ];
	}
	return null;
}

/*
 * The union of the slots required by each layout determine the set of extension properties exposed
 * on all list box items (the parent ListBox extends the ListBoxItem). So, to be clear, each item has
 * all the properties available through any of the layouts.
 */
ListBoxItem.prototype.getExtensionSetNames = function(instance, targetInstance) {
	var listboxLayoutGroup = instance.parent.findChildOfType("com.nokia.carbide.uiq.ListBoxLayoutGroup");
	if (listboxLayoutGroup == null) {
		return;
	}
	var listboxLayouts = listboxLayoutGroup.children;
	var names = [];
	for (var i = 0, j = 0; i < listboxLayouts.length; i++) {
		var layout = listboxLayouts[i];
		var normalSet = getExtensionSetName(layout.properties.standard_normal_layout);
		if (normalSet != null) {
			names[j++] = normalSet;
		}
		var highlightSet = getExtensionSetName(layout.properties.standard_highlight_layout);
		if (highlightSet != null) {
			names[j++] = highlightSet;
		}
	}
	return names;
}

function getExtensionSetName(standardLayout) {
	switch (standardLayout) {
		case "EQikListBoxLine":
			return "Line";
		case "EQikListBoxIconLine":
			return "IconLine";
		case "EQikListBoxLineIcon":
			return "LineIcon";
		case "EQikListBoxTwoLines":
			return "TwoLines";
		case "EQikListBoxIconTwoLines":
			return "IconTwoLines";
		case "EQikListBoxMediumIconTwoLines":
			return "MediumIconTwoLines";
		default:
			return null;
	}
}

////////////////////////////////////////////////////////////////////////////////
// IVisualAppearance

ListBoxItem.prototype.draw = function(instance, laf, graphics) {
	//Render based upon standard normal layout
	var listboxLayout = getListBoxLayout(instance);
//	println("ListBoxItem.prototype.draw instance: " + instance);
//	println("ListBoxItem.prototype.draw listboxLayout: " + listboxLayout);
	if (listboxLayout != null && listboxLayout.properties != null) {
		normalLayout = listboxLayout.properties.standard_normal_layout;
		switch (normalLayout) {
			case "EQikListBoxLine": renderLineLayout(instance, laf, graphics);
				break;
			case "EQikListBoxIconLine": renderIconLineLayout(instance, laf, graphics);
				break;
			case "EQikListBoxLineIcon": renderLineIconLayout(instance, laf, graphics);
				break;
			case "EQikListBoxTwoLines": renderTwoLinesLayout(instance, laf, graphics);
				break;
			case "EQikListBoxIconTwoLines": renderIconTwoLinesLayout(instance, laf, graphics);
				break;
			case "EQikListBoxMediumIconTwoLines": renderMediumIconTwoLinesLayout(instance, laf, graphics);
				break;
			default:
				break;
		}
	}
}

ListBoxItem.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
	var height = getRowHeight(instance, laf);
	var listboxLayout = getListBoxLayout(instance);
	if (listboxLayout != null && listboxLayout.properties != null) {
		normalLayout = listboxLayout.properties.standard_normal_layout;
		switch (normalLayout) {
			case "EQikListBoxLine":
			case "EQikListBoxIconLine":
			case "EQikListBoxLineIcon": return new Point(wHint, height);
			case "EQikListBoxTwoLines":
			case "EQikListBoxIconTwoLines":
			case "EQikListBoxMediumIconTwoLines": return new Point(wHint, height * 2);
			default: return null;
		}
	}
}

function getListBoxLayout(instance) {
	var properties = instance.properties;
	if (properties != null) {
		var layoutGroup = instance.parent.findChildOfType("com.nokia.carbide.uiq.ListBoxLayoutGroup");
		if (layoutGroup == null) {
			return null;
		}
		var layouts = layoutGroup.children;
		var layoutRef = properties.layout;
		if (layoutRef == "") {
			//take default_layout of listbox parent
			layoutRef = instance.parent.properties.default_layout;
			if (layoutRef == "") {
				//take first layout, if exists
				if (layouts.length > 0) {
					return layouts[0];
				} else {
					return null;
				}
			}
		}
		var layout = null;
		for (var i = 0; i < layouts.length; i++) {
			if (layouts[i].name == layoutRef) {
				layout = layouts[i];
				break;
			}
		}
		return layout;
	}
}

function getRowHeight(instance, laf) {
	if (instance.rootContainer.isInstanceOf("com.nokia.carbide.uiq.CQikView")) {
		return laf.getInteger("RowLayoutManager.View.rowHeight", 27);
	} else {
		return laf.getInteger("RowLayoutManager.Dialog.rowHeight", 28);
	}
}

function renderLineLayout(instance, laf, graphics) {
	var hG = laf.getInteger("listbox.horizontalGap", 5);	//horizontal gap
	var font = laf.getFont("NormalFont");					//normal font
	
	var bounds = instance.getRenderingBounds();
	var wT = bounds.width - 2 * hG;
	var hT = getFontHeight(font);
	var xT = hG;
	var yT = ( bounds.height - hT ) / 2;
	var text1 = instance.properties.EQikListBoxSlotText1;
	var flags = font.OVERFLOW_ELLIPSIS | font.WRAPPING_ENABLED | font.ANTIALIAS_OFF;
	graphics.setFont(font);
	if (text1 != null) {
		graphics.drawFormattedString(text1, new Rectangle(xT, yT, wT, hT), flags);
	}
}

function renderIconLineLayout(instance, laf, graphics) {
	var hG = laf.getInteger("listbox.horizontalGap", 5);	//horizontal gap
	var font = laf.getFont("NormalFont");					//normal font
	var iW = laf.getInteger("listbox.smallIconWidth", 18);	//image width
	var iH = laf.getInteger("listbox.smallIconHeight", 18);	//image height
	var itGap = hG;											//image to text gap
	
	var bounds = instance.getRenderingBounds();
	var wT = bounds.width - iW - 2 * hG - itGap;
	var hT = getFontHeight(font);
	var xT = hG + iW + itGap;
	var yT = ( bounds.height - hT ) / 2;
	var text1 = instance.properties.EQikListBoxSlotText1;
	var flags = font.OVERFLOW_ELLIPSIS | font.WRAPPING_ENABLED | font.ANTIALIAS_OFF;
	graphics.setFont(font);
	if (text1 != null) {
		graphics.drawFormattedString(text1, new Rectangle(xT, yT, wT, hT), flags);
	}
	
	var H = bounds.height;
	var xI = hG;
	var yI = ( H - iH ) / 2;
	renderImage(ListBoxItem.prototype, instance, laf, graphics, xI, yI, "EQikListBoxSlotLeftSmallIcon1", true);
}

function renderLineIconLayout(instance, laf, graphics) {
	var hG = laf.getInteger("listbox.horizontalGap", 5);	//horizontal gap
	var font = laf.getFont("NormalFont");					//normal font
	var iW = laf.getInteger("listbox.smallIconWidth", 18);	//image width
	var iH = laf.getInteger("listbox.smallIconHeight", 18);	//image height
	var itGap = hG;											//image to text gap
	
	var bounds = instance.getRenderingBounds();
	var wT = bounds.width - iW - 2 * hG - itGap;
	var hT = getFontHeight(font);
	var xT = hG;
	var yT = ( bounds.height - hT ) / 2;
	var text1 = instance.properties.EQikListBoxSlotText1;
	var flags = font.OVERFLOW_ELLIPSIS | font.WRAPPING_ENABLED | font.ANTIALIAS_OFF;
	graphics.setFont(font);
	if (text1 != null) {
		graphics.drawFormattedString(text1, new Rectangle(xT, yT, wT, hT), flags);
	}
	
	var xI = bounds.width - hG - iW;
	var yI = ( bounds.height - iH ) / 2;
	renderImage(ListBoxItem.prototype, instance, laf, graphics, xI, yI, "EQikListBoxSlotRightSmallIcon1", true);
}

function renderTwoLinesLayout(instance, laf, graphics) {
	var hG = laf.getInteger("listbox.horizontalGap", 5);	//horizontal gap
	var font = laf.getFont("NormalFont");					//normal font
	
	var bounds = instance.getRenderingBounds();
	var wT = bounds.width - 2 * hG;
	var hT = getFontHeight(font);
	var xT = hG;
	var yT = ( bounds.height / 2 - hT ) / 2;
	var text1 = instance.properties.EQikListBoxSlotText1;
	var flags = font.OVERFLOW_ELLIPSIS | font.WRAPPING_ENABLED | font.ANTIALIAS_OFF;
	graphics.setFont(font);
	if (text1 != null) {
		graphics.drawFormattedString(text1, new Rectangle(xT, yT, wT, hT), flags);
	}
	yT += bounds.height / 2;
	var text2 = instance.properties.EQikListBoxSlotText2;
	if (text2 != null) {
		graphics.drawFormattedString(text2, new Rectangle(xT, yT, wT, hT), flags);
	}
}

function renderIconTwoLinesLayout(instance, laf, graphics) {
	var hG = laf.getInteger("listbox.horizontalGap", 5);	//horizontal gap
	var font = laf.getFont("NormalFont");					//normal font
	var iW = laf.getInteger("listbox.smallIconWidth", 18);	//image width
	var iH = laf.getInteger("listbox.smallIconHeight", 18);	//image height
	var itGap = hG;											//image to text gap
	
	var bounds = instance.getRenderingBounds();
	var wT = bounds.width - iW - 2 * hG - itGap;
	var hT = getFontHeight(font);
	var xT = hG + iW + itGap;
	var yT = ( bounds.height / 2 - hT ) / 2;
	var text1 = instance.properties.EQikListBoxSlotText1;
	var flags = font.OVERFLOW_ELLIPSIS | font.WRAPPING_ENABLED | font.ANTIALIAS_OFF;
	graphics.setFont(font);
	if (text1 != null) {
		graphics.drawFormattedString(text1, new Rectangle(xT, yT, wT, hT), flags);
	}
	yT += bounds.height / 2;
	var text2 = instance.properties.EQikListBoxSlotText2;
	if (text2 != null) {
		graphics.drawFormattedString(text2, new Rectangle(xT, yT, wT, hT), flags);
	}
	
	var xI = hG;
	var yI = ( bounds.height - 2 * iH ) / 4;
	renderImage(ListBoxItem.prototype, instance, laf, graphics, xI, yI, "EQikListBoxSlotLeftSmallIcon1", true);
}

function renderMediumIconTwoLinesLayout(instance, laf, graphics) {
	var hG = laf.getInteger("listbox.horizontalGap", 5);			//horizontal gap
	var font = laf.getFont("NormalFont");							//normal font
	var iW = laf.getInteger("listbox.mediumIconWidth", 40);			//image width
	var iH = laf.getInteger("listbox.mediumIconHeight", 40);		//image height
//	var iW = laf.getInteger("listbox.mediumThumbnailWidth", 48);	//image width
//	var iH = laf.getInteger("listbox.mediumThumbnailHeight", 48);	//image height
	var itGap = hG;													//image to text gap
	
	var bounds = instance.getRenderingBounds();
	var wT = bounds.width - iW - 2 * hG - itGap;
	var hT = getFontHeight(font);
	var xT = hG + iW + itGap;
	var yT = ( bounds.height / 2 - hT ) / 2;
	var text1 = instance.properties.EQikListBoxSlotText1;
	var flags = font.OVERFLOW_ELLIPSIS | font.WRAPPING_ENABLED | font.ANTIALIAS_OFF;
	graphics.setFont(font);
	if (text1 != null) {
		graphics.drawFormattedString(text1, new Rectangle(xT, yT, wT, hT), flags);
	}
	yT += bounds.height / 2;
	var text2 = instance.properties.EQikListBoxSlotText2;
	if (text2 != null) {
		graphics.drawFormattedString(text2, new Rectangle(xT, yT, wT, hT), flags);
	}
	
	var xI = hG;
	var yI = ( bounds.height - iH ) / 2;
	renderImage(ListBoxItem.prototype, instance, laf, graphics, xI, yI, "EQikListBoxSlotLeftMediumIcon1", true);
//	renderImage(ListBoxItem.prototype, instance, laf, graphics, xI, yI, "EQikListBoxSlotLeftMediumThumbnail1", true);
}

////////////////////////////////////////////////////////////////////////////////
// IDirectImageEdit

//setupCommonDirectImageEditing(ListBoxItem.prototype, "EQikListBoxSlotLeftSmallIcon1", 
//	null  	// areafunction
//);

//setupCommonDirectImageEditing(ListBoxItem.prototype, "EQikListBoxSlotRightSmallIcon1", 
//		null  	// areafunction
//);

////////////////////////////////////////////////////////////////////////////////
// IImagePropertyRenderingInfo

ListBoxItem.prototype.getViewableSize = function(instance, propertyId, laf) {
	var iW = 0;
	var iH = 0;
	switch(propertyId) {
		case "EQikListBoxSlotLeftSmallIcon1":
		case "EQikListBoxSlotRightSmallIcon1":
			iW = laf.getInteger("listbox.smallIconWidth", 18);	//image width
			iH = laf.getInteger("listbox.smallIconHeight", 18);	//image height
			break;
		case "EQikListBoxSlotLeftMediumIcon1":
			iW = laf.getInteger("listbox.mediumIconWidth", 40);	//image width
			iH = laf.getInteger("listbox.mediumIconHeight", 40);	//image height
			break;
//		case "EQikListBoxSlotLeftMediumThumbnail1":
//			iW = laf.getInteger("listbox.mediumThumbnailWidth", 48);	//image width
//			iH = laf.getInteger("listbox.mediumThumbnailHeight", 48);	//image height
//			break;
		default: return null;
	}
	return new Point(iW, iH);
}

ListBoxItem.prototype.getAlignmentWeights = function(instance, propertyId, laf) {
	return new Point(ImageUtils.ALIGN_LEFT, ImageUtils.ALIGN_TOP);
}

ListBoxItem.prototype.isScaling = function(instance, propertyId, laf) {
	return true;
}

ListBoxItem.prototype.isPreservingAspectRatio = function(instance, propertyId, laf) {
	return true;
}
