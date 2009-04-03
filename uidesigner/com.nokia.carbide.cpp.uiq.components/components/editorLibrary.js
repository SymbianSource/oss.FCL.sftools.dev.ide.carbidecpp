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


include("containerLibrary.js")
include("embeddedControlImplLibrary.js")// for isSettingItemList

editorLibraryStrings = getLocalizedStrings("editorLibrary");

/**
 *	Set up rendering support for editor components.  This clears out
 *	a rectangle of the component's size and draws text inside it
 *	according to a component-specific formatter and MFNE layout.
 *	This sets up a component to appear in a form.
 *
 *	@param prototype the prototype to add IVisualAppearance to.
 *	The prototype must implement these functions:
 *	<p>
 *	<li>
 *		getDisplayText(instance): return the text formatted for display
 *							(may be empty or null).  Return the entire
 *							string, even if longer than getMaxLength()
 *	<li>
 *		getMaxLength(instance): return the character limit on the text
 *	<li>
 *		getFlags(instance, laf): return an IFont#xxx mask desribing
 *								parameters to font.drawFormattedString()
 *
 */
function setupEditorRendering(prototype) {
	prototype.drawContent = function(instance, laf, graphics, rect) {
		var properties = instance.properties;
		
		if( properties.isVisible != null && properties.isVisible == false)
			return;

		var flags = this.getFlags(instance, laf);
		if (isSettingItemList(instance.parent)) {
			flags = (flags & ~Font.ALIGN_MASK) | Font.ALIGN_CENTER;
			
			// white on blue doesn't look good antialiased
			flags = (flags & ~Font.ANTIALIAS_MASK) | Font.ANTIALIAS_OFF;
		}
		
		var font = this.getFont(instance, laf);
		if (font == null)
			font = laf.getFont("NormalFont");
			
		graphics.setFont(font);
	
		//graphics.setBackground(laf.getColor("CAknSettingItemList.ContentBackground"));
		graphics.setBackground(getBackgroundColor(instance, laf));
		graphics.fillRectangle(rect);
	
		var color = null;
		if (prototype.getDisplayColor) {
			color = prototype.getDisplayColor(instance, laf);
		}
		if (color == null && isSettingItemList(instance.parent))
			color = laf.getColor("CAknSettingItemList.ContentForeground");
		
		if (color == null)
			color = laf.getColor("EEikColorControlText");
		graphics.setForeground(color);

		var text = "" + this.getDisplayText(instance);
		if (instance.isInstanceOf("com.nokia.carbide.uiq.CEikEdwinBase"))
			text = text.replace(/\n/,"");
		else
			text = chooseScalableText(text, font, rect.width);
		var maxLength = this.getMaxLength(instance);
		if (maxLength == 0 || text.length <= maxLength) {
			// in settings list, blank text is replaced
			if ((!text || text == "") && isSettingItemList(instance.parent)) {
				text = instance.properties.itemEmptyText;
				if (!text || text == "")
					text = editorLibraryStrings.getString("EmptyText");
			}

			graphics.drawFormattedString(text, rect, flags, 0);	
		} else {
			var visibleText = text.substring(0, maxLength);
			var truncatedText = text.substring(maxLength);

			var visibleSize = font.formattedStringExtent(visibleText, new Point(rect.width, rect.height), flags, 0);
			var truncatedSize = font.formattedStringExtent(truncatedText, new Point(rect.width, rect.height), flags, 0);

			// indicate truncated text 
			if (false) {
				// dim background of truncated part
				graphics.setBackground(laf.getColor("EEikColorControlHighlightBackground"));
				graphics.fillRectangle(rect.x + visibleSize.x, rect.y, truncatedSize.x, rect.height);
				graphics.drawFormattedString(text, rect, flags, 0);	
			} else {
				// dim letters of truncated part
				graphics.drawFormattedString(visibleText, rect, flags, 0);	
				graphics.setForeground(Colors.getColor(200, 200, 200));
				rect.x += visibleSize.x;
				graphics.drawFormattedString(truncatedText, rect, flags, 0);	
			}
		}
		
	}
	
	prototype.getContentSize = function(instance, laf, size) {
		var properties = instance.properties;
		var isInForm = isForm(instance.parent);
		
		var font = this.getFont(instance, laf);
		if (font == null)
			font = laf.getFont("NormalFont");

		var flags = this.getFlags(instance, laf);
		var text = this.getDisplayText(instance);
		if (text == null || text == "")
			text = "Temporary";		// provide enough space so click-added 
									// editor has some perceivable width
			
		// use wrapping width to get adjusted height
		var size = font.formattedStringExtent(text, size, flags, 0);
		
		// this property is not necessarily present, in which case only one line is used
		var lines = properties.lines;
		if (isNaN(lines) || lines < 1)
			lines = 1;
			
		var linesHeight = getFontHeight(font) * lines;
		var textHeight = size.y;

		// this property flag is not necessarily present, in which case this code falls through
		var isResizable = 'flags' in properties
			&& 'EEikEdwinResizable' in properties.flags
			&& properties.flags.EEikEdwinResizable;
			
		//println("lines("+lines+"), linesHeight(" + linesHeight + "), textHeight(" + textHeight + "), resizable(" + isResizable + ")");
		
		if (isResizable && (textHeight > linesHeight))
			size.y = textHeight;
		else if (!isInForm)
			size.y = linesHeight;	// don't resize if in container
		
		return size;
	}
	
	// now make this form-able
	setupEmbeddedRendering(prototype);
	
}



/**
 *	Format the number to have zeroes on the left.
 */
function padWithZeroes(number, count) {
	var str = "" + number;
	if (str.length >= count)
		return str;
	var zeroes = "0000000000000000000".substring(0, count);
	return zeroes.substring(0, count - str.length) + str;
}

/**
 *	Format the number to have spaces on the left.
 */
function padWithSpaces(number, count) {
	var str = "" + number;
	if (str.length >= count)
		return str;
	var zeroes = "                   ".substring(0, count);
	return zeroes.substring(0, count - str.length) + str;
}

/**
 *	Format a time the way S60 would.  This is piggishly restricted
 *	to the American format, since S60 has some weirdness with formatting
 *	(hiding hours -- but not AM/PM!, and hiding seconds) which would make
 *	our task of mirroring these in a locale-dependent way too difficult.
 *
 *	Likely it would be better to use a format string in the future.
 */
 
function formatTime(h, m, s, showHours, showMinutes, showSeconds, showAmPm) {
	var str = "";
	var suffix = "";
		
	if (showAmPm) {
		if (h >= 12) {
			h -= 12;
			suffix = " PM";
		} else {
			suffix = " AM";
		}
		if (h == 0)
			h = 12;
	}

	if (showHours) {
		str += padWithZeroes(h, 2) + ":";
	}
	
	if (showMinutes) {
		str += padWithZeroes(m, 2);
	}
	
	if (showSeconds) {
		str += ":" + padWithZeroes(s, 2);
	}
	
	str += suffix;
	
	return str;

}

/**
 *	Format a date the way S60 would.  
 */
 
function formatDate(d) {
	return formatDateDMY(d.day, d.month, d.year);
}

function formatDateDMY(day, month, year) {
	var str = "";
	str = padWithZeroes(day - 0 + 1, 2) + "/" +
		padWithZeroes(month - 0 + 1, 2) + "/" +
		padWithZeroes(year, 4);
	return str;

}

function commonEditorGetFlags(instance, laf) {
	var properties = instance.properties;
	var flags = 0;

	if (properties.alignment == undefined)
		return flags;

	switch (properties.alignment) {
		case "EAknEditorAlignCenter":
			flags = Font.ALIGN_CENTER; break;
		case "EAknEditorAlignLeft":
			flags = Font.ALIGN_LEFT; break;
		case "EAknEditorAlignRight":
			flags = Font.ALIGN_RIGHT; break;
	}

	return flags;
}

var CONTROL_ID = "com.nokia.sdt.series60.CCoeControl";

function isControl(instance) {
	return instance.componentId == CONTROL_ID;
}

