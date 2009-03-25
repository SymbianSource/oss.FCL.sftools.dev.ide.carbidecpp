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

 
 /** Helpers for controls embedded in forms or setting item lists */

var DRAW_BOUNDING_RECTS = false;

include("formLibrary.js") // for isForm function
include("settingsListLibrary.js")// for isSettingItemList
include("transient_dialog/popupDialogLibrary.js")//isDataQuery

/**
 * Implement IVisualAppearance routines with form/setting item support.
 * This automatically handles reducing the content area for
 * a component when it is displayed in a form/setting item list 
 * and returning a preferred size.
 *
 * @param prototype the prototype to modify<p>
 * 	This must provide these routines:	<br>
 * <p>
 *		drawContent(instance, laf, graphics, rect)	<br>
 *			Draw content into 'rect' (x, y are 0 and GC is shifted)	<br>
 *<p>
 *		getContentSize(instance, laf, size)	<br>
 *			Get the required size of the content, given the nominal constraints
 *			in 'size'.  For instance, a text field may increase the height
 *			due to wrapping.<p>
 *			The content bounds calculation is usually independent of whether the
 *			parent is a form, unless the rendering is significantly
 *			different.  The calculation <i>is</i> different for a setting
 *			item list, which uses a different font.  Returns a Point.	<br>
 */
function setupEmbeddedRendering(prototype) {
	prototype.draw = function(instance, laf, graphics) {
		var properties = instance.properties;
		if (isForm(instance.parent)) {
			var rects = getFormItemRectangles(instance, laf);
			var promptRect = rects[FORM_PROMPT_RECT_INDEX];
			var contentRect = rects[FORM_CONTENT_RECT_INDEX];
			
			drawFormPrompt(prototype, instance, laf, graphics, rects);
			if (DRAW_BOUNDING_RECTS) {
				graphics.setForeground(Colors.getColor(255, 0, 0));
				graphics.setXORMode(true);
				graphics.drawRectangle(promptRect.x, promptRect.y, promptRect.width-1, promptRect.height-1 );
				graphics.setXORMode(false);
			}
						
			var oldX = graphics.getOffX();
			var oldY = graphics.getOffY();
			graphics.setOffX(contentRect.x);
			graphics.setOffY(contentRect.y);
			contentRect.x = 0;
			contentRect.y = 0;
			this.drawContent(instance, laf, graphics, contentRect);

			if (DRAW_BOUNDING_RECTS) {
				graphics.setForeground(Colors.getColor(255, 0, 0));
				graphics.setXORMode(true);
				graphics.drawRectangle(contentRect.x, contentRect.y, contentRect.width-1, contentRect.height-1 );
				graphics.setXORMode(false);
			}
						
			graphics.setOffX(oldX);
			graphics.setOffY(oldY);

		} else if (isSettingItemList(instance.parent)) {

			var rects = getSettingItemRectangles(instance, laf);
			var numberRect = rects[SIL_NUMBER_RECT_INDEX];
			var titleRect = rects[SIL_TITLE_RECT_INDEX];
			var contentRect = rects[SIL_CONTENT_RECT_INDEX];
			
			drawSettingItemPrompt(prototype, instance, laf, graphics, rects);
			if (DRAW_BOUNDING_RECTS) {
				graphics.setForeground(Colors.getColor(255, 0, 0));
				graphics.setXORMode(true);
				graphics.drawRectangle(numberRect.x, numberRect.y, numberRect.width-1, numberRect.height-1 );
				graphics.drawRectangle(titleRect.x, titleRect.y, titleRect.width-1, titleRect.height-1 );
				graphics.setXORMode(false);
			}
						
			var oldX = graphics.getOffX();
			var oldY = graphics.getOffY();
			graphics.setOffX(contentRect.x);
			graphics.setOffY(contentRect.y);
			contentRect.x = 0;
			contentRect.y = 0;
			this.drawContent(instance, laf, graphics, contentRect);

			if (DRAW_BOUNDING_RECTS) {
				graphics.setForeground(Colors.getColor(128, 0, 0));
				graphics.setXORMode(true);
				graphics.drawRectangle(contentRect.x, contentRect.y, contentRect.width-1, contentRect.height-1 );
				graphics.setXORMode(false);
			}
						
			graphics.setOffX(oldX);
			graphics.setOffY(oldY);

		} else if (isDataQuery(instance.parent)) {
			// draw inner shadows
			var width = properties.size.width;
			var height = properties.size.height;
			graphics.setForeground(laf.getColor("control.shadow.inner"));
			graphics.drawRectangle(0, 0, width - 1, height - 1);
			
			
			graphics.setForeground(laf.getColor("control.shadow.outer"));
			graphics.drawLine(1, 1, width - 1, 1);
			graphics.drawLine(1, 1, 1, height - 1);
			
			// draw content
			var rect = new Rectangle(3, 3,
					properties.size.width - 6, properties.size.height - 6);
			this.drawContent(instance, laf, graphics, rect);
		} else {
			var rect = new Rectangle(0, 0,
					properties.size.width, properties.size.height);
			this.drawContent(instance, laf, graphics, rect);
		}
	}
	
	prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
		var properties = instance.properties;
		
		// [[[ ordinary calculations
		
		// get current size
		width = properties.size.width;
		height = properties.size.height;
		
		// if either of these are empty, use the parent's bounds as starting point
		if ((width == 0) || (height == 0)) {
			width = instance.parent.properties.size.width;
			height = instance.parent.properties.size.height;
		}
		
		// use hints if provided
		if (wHint >= 0)
			width = wHint;
		if (hHint >= 0)
			height = hHint;
			
		// ]]]
		
		var rect = new Rectangle(0, 0, width, height);

		if (isForm(instance.parent)) {
			var rects = getFormItemRectanglesInRect(instance, laf, rect);
			var promptRect = rects[FORM_PROMPT_RECT_INDEX];
			var contentRect = rects[FORM_CONTENT_RECT_INDEX];
			
			// ensure size is big enough for content
			size = this.getContentSize(instance, laf, 
						new Point(contentRect.width, contentRect.height));
			
			// for double-space mode, add height for the prompt
			if (isDoubleSpaced(instance.parent)) {
				size.y += promptRect.height + getFormLineGap(laf);
			} else {
				size.y += getFormLineGap(laf);
			
			}
		} else if (isSettingItemList(instance.parent)) {
			var rects = getSettingItemRectanglesInRect(instance, laf, rect);
			var contentRect = rects[SIL_CONTENT_RECT_INDEX];
			
			// ensure size is big enough for content
			size = this.getContentSize(instance, laf, 
						new Point(contentRect.width, contentRect.height));
			
			//size.y += getLineGap(laf);
		} else {
			size = this.getContentSize(instance, laf, new Point(width, height));
		}
		
		return size;
	}
	
	// drawPrompt needs these routines
	setupEmbeddedImagePropertyInfo(prototype);
}


/**
 *	Set up direct label editing implementation for a component with
 *	one editable label
 *	@param prototype the impl prototype to update
 *	@param property the name of the edited property
 *	@param areafunction a function, taking (instance, laf, rect), which returns the
 *		editable area of the label (or null).  If null, the default behavior is
 *		to use the entire rendered area.  Otherwise, the area should be a subset
 *		of rect (which is adjusted to the content area in a form if necessary).
 *	@param fontfunction a function, taking an instance and laf, which returns the
 *		IFont to edit with (or null).  If null, the default behavior is to return
 *		null, indicating a default system font.
 */
function setupCommonEmbeddedDirectLabelEditing(prototype, property,
	areafunction, fontfunction) {
	
	prototype.getPropertyPaths = function(instance) {
		if (property) {
			if (isForm(instance.parent))
				return [ "prompt", property ];
			else if (isSettingItemList(instance.parent))
				return [ "itemTitle", "compulsoryLabel", property ];
			else
				return [ property ];
		} else {
			if (isForm(instance.parent))
				return [ "prompt" ];
			else if (isSettingItemList(instance.parent))
				return [ "itemTitle", "compulsoryLabel" ];
			else
				return [ ];
		}
	}

	prototype.getLabelBounds = function(instance, propertyPath, laf) {

		if (isForm(instance.parent)) {
			var rects = getFormItemRectangles(instance, laf);
			var promptLabelRect = rects[FORM_PROMPT_LABEL_RECT_INDEX];
			var contentRect = rects[FORM_CONTENT_RECT_INDEX];
		
			if (propertyPath == "prompt") {
				return promptLabelRect;
			}
			else if (property && propertyPath == property) {
				rect = contentRect;
				if (areafunction)
					rect = areafunction(instance, laf, rect);
				return rect;
			}
			else /* fall through */ ;
		} else if (isSettingItemList(instance.parent)) {
			var rects = getSettingItemRectangles(instance, laf);
			var titleLabelRect = rects[SIL_TITLE_RECT_INDEX];
			var contentRect = rects[SIL_CONTENT_RECT_INDEX];
		
			if (propertyPath == "itemTitle") {
				return titleLabelRect;
			}
			if (propertyPath == "compulsoryLabel") {
				return rects[SIL_INDICATOR_RECT_INDEX];
			}
			if (property && propertyPath == property) {
				rect = contentRect;
				if (areafunction)
					rect = areafunction(instance, laf, rect);
				return rect;
			}
			/* fall through */
		
		}

		var rect = new Rectangle(0, 0, 
			instance.properties.size.width, instance.properties.size.height);
		
		if (property && propertyPath == property) {
			if (areafunction)
				rect = areafunction(instance, laf, rect);
		
			return rect;
		}
		
		return null;
	}

	prototype.getLabelFont = function(instance, propertyPath, laf) {
		if (propertyPath == "prompt")
			return getPromptFont(laf);
		if (propertyPath == "itemTitle")
			return getTitleFont(laf);
			
		if (property && propertyPath) {
			if (fontfunction)
				return fontfunction(instance, laf);
		}
			
		return null;
	}
}

/**
 *	Support image validation for a form-provided promptImage property.
 */
function setupCommonEmbeddedDirectImageEditing(prototype) {
	setupCommonDirectImageEditing(prototype, "promptImage", 
	
	/* areafunction */
	function (instance, laf, propertyId) {
		if (isForm(instance.parent) &&
			instance.parent.properties.EEikFormShowBitmaps) {
			var rects = getFormItemRectangles(instance, laf);
			return rects[FORM_PROMPT_IMAGE_RECT_INDEX];
		} else {
			return null;
		}
	}
	);
}

/**
 *	Support image property info support for form
 */
function setupEmbeddedImagePropertyInfo(prototype) {
	// IImagePropertyInfo
	prototype.getViewableSize = function(instance, propertyId, laf) {
		if (propertyId == "promptImage") {
			if (isForm(instance.parent)) {
				// return rect even if prompt is invisible
				var rects = getFormItemRectangles(instance, laf);
				var rect = rects[FORM_PROMPT_IMAGE_RECT_INDEX];
				if (rect != null)
					return new Point(rect.width, rect.height);
			}
		}
		return null;
	}

	prototype.isScaling = function(instance, propertyId, laf) {
		if (propertyId == "promptImage") {
			return isScalingIcons();
		}
		return true;
	}
	
	prototype.getAlignmentWeights = function(instance, propertyId, laf) {
		if (propertyId == "promptImage") {
			return new Point(ImageUtils.ALIGN_CENTER_OR_LEFT, ImageUtils.ALIGN_CENTER_OR_TOP);
		}
		return null;
	}
	
	prototype.isPreservingAspectRatio = function(instance, propertyId, laf) {
		if (propertyId == "promptImage") {
			return true;
		}
		return true;
	}

}
