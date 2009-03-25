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
package com.nokia.sdt.sourcegen;


/**
 * Interface that handles formatting segments. This handles the work of dealing
 * with spacing, indentation, and brace placement.
 * 
 * 
 */
public interface ISourceFormatter {
	public static final ISourceFormatter NULL_SOURCE_FORMATTER = new ISourceFormatter() {

		ISourceFormatting formatting = new DefaultSourceFormatting(); 
		public void setBuffer(CharSequence buffer) {
		}

		public CharSequence getFormattedText(Object str) {
			return str.toString();
		}

		public CharSequence getReformattedText(CharSequence str) {
			return str;
		}

		public ISourceFormatting getSourceFormatting() {
			return formatting;
		}
		
		public void setSourceFormatting(ISourceFormatting formatting) {
			this.formatting = formatting;
		}
	};


	/** A text segment for newlines */
	public static final String SEGMENT_NEWLINE = "\n";  //$NON-NLS-1$
	/** A text segment to force indent */
	public static final FormattingSegment SEGMENT_FORMATTING_INDENT = new FormattingSegment("\t");  //$NON-NLS-1$
	/** A text segment for optional spaces */
	public static final FormattingSegment SEGMENT_FORMATTING_SPACE = new FormattingSegment(" ");  //$NON-NLS-1$
	/** A text segment that is formatting open brace, which includes a trailing newline */
	public static final FormattingSegment SEGMENT_FORMATTING_LBRACE = new FormattingSegment("{");  //$NON-NLS-1$
	/** A text segment that is formatting close brace, which includes a trailing newline */
	public static final FormattingSegment SEGMENT_FORMATTING_RBRACE = new FormattingSegment("}"); //$NON-NLS-1$
	/** A text segment that is formatting comma, which includes a trailing newline */
	public static final FormattingSegment SEGMENT_FORMATTING_COMMA = new FormattingSegment(","); //$NON-NLS-1$

	/**
	 * Set the buffer which contains the incrementally updated source text.
	 */
	public void setBuffer(CharSequence buffer);
	
	/**
	 * Return formatted text for the given string, which is to be placed at the
	 * end of 'current'.
	 * <p>
	 * The formatting may prepend or append text, e.g., "\t\t\t"+str for the
	 * first bit of text in a triply-indented block, or str+"\n\t" for a brace
	 * which is supposed to be on the next line.
	 * <p>
	 * The determination of formatting must be based on the current text passed in.
	 * 
	 * @param buffer
	 *            the source buffer
	 * @param str
	 *            the text to format, either a SpecialSegment or toString()'ed
	 * @return formatted text
	 */
	public CharSequence getFormattedText(Object str);

	/**
	 * Return the previously formatted text, reformatted with respect to the current buffer.
	 * This is expected to strip whitespace, etc. where it is already accounted for.
	 */
	public CharSequence getReformattedText(CharSequence str);

	/**
	 * Get the formatting.
	 */
	public ISourceFormatting getSourceFormatting();
	
	/**
	 * Set the formatting
	 */
	public void setSourceFormatting(ISourceFormatting formatting);

}