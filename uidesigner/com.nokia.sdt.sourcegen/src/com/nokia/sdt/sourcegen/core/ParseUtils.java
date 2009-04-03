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
/**
 * 
 */
package com.nokia.sdt.sourcegen.core;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Tuple;


/**
 * Utilities for parsing source text.
 * 
 *
 */
public class ParseUtils {

	/** Find insert position in the given range before the given character, flush
	 * to the start of the line */
	public static int getInsertPositionAtEnd(char[] text, int start, int length, char lastCh) {
	    int end = start + length;
	    int idx;
	    for (idx = end - 1; idx >= start; idx--) {
	        if (text[idx] == lastCh) {
	            break;
	        }
	    }
	    // now go back to a non-space character (probably a newline)
	    while (idx > start && (text[idx-1] == '\t' || text[idx-1] == ' ')) {
	        idx--;
	    }
	    if (idx > start && text[idx] != lastCh && text[idx-1] != '\n')
	        idx++;
	    
	    //System.out.println("write at " +idx+": " + new String(text, idx, end - idx));
	    return idx;
	}

	/** Find insert position in the given range before the given character, flush
	 * to the start of the line */
	public static int getInsertPositionAtStart(char[] text, int start, int length, char firstCh) {
	    int end = start + length;
	    int idx;
	    for (idx = start; idx < end; idx++) {
	        if (text[idx] == firstCh) {
	            break;
	        }
	    }
	    // now go back to a non-space character (probably a newline)
	    while (idx > start && (text[idx-1] == '\t' || text[idx-1] == ' ')) {
	        idx--;
	    }
	    if (idx > start && text[idx] != firstCh && text[idx-1] != '\n')
	        idx++;
	    
	    //System.out.println("write at " +idx+": " + new String(text, idx, end - idx));
	    return idx;
	}

	/** Find insert position in the given range after the given character, flush
	 * to the first text in the line */
	public static int getInsertPositionAfterStart(char[] text, int start, int length, char firstCh) {
	    int end = start + length;
	    int idx;
	    for (idx = start; idx < end; idx++) {
	        if (text[idx] == firstCh) {
	        	idx++;
	            break;
	        }
	    }
	    
	    // skip to the first non-whitespace character
	    while (idx < end && Character.isWhitespace(text[idx]))
	    	idx++;
	
	    return idx;
	}

	/**
	 * Get length of newline. 
	 * @param text
	 * @param offset offset of a '\r' or '\n' character
	 * @return length of sequence
	 */
	public static int newlineLengthBefore(char[] text, int offset) {
	    if (offset <= 0)
	        return 0;
	    if (text[offset - 1] == '\r')
	        return 1;
	    else /* must be '\n' */ if (offset > 1 && text[offset - 2] == '\r')
	        return 2;
	    else
	        return 1;
	}

	/**
	 * Get length of newline. 
	 * @param text
	 * @param offset of a '\r' or '\n' character
	 * @return length of sequence
	 */
	public static int newlineLength(char[] text, int offset) {
	    if (offset >= text.length)
	        return 0;
	    else if (text[offset] == '\n')
	        return 1;
	    else /* must be \r */ if (offset + 1 < text.length && text[offset + 1] == '\n')
	        return 2;
	    else
	        return 1;
	}

	/**
	 * Scan backwards to find the start of a line,
	 * skipping any block comments (which may have their own
	 * newlines embedded)
	 * @param text
	 * @param start
	 * @return offset at start of line
	 */
	public static int skipToLogicalBeginningOfLineBackward(char[] text, int start, int limit) {
	    while (start > limit) {
	        if (text[start-1] == '\n' || text[start-1] == '\r')
	            break;
	        if (start - 1 > limit && text[start-2] == '*' && text[start-1] == '/') {
	            // scan backward past block comment
	            start --;
	            while (start > limit) {
	                if (text[start - 1] == '/' && text[start] == '*')
	                    break;
	                start--;
	            }
	        }
	        start--;
	    }
	    return start;
	}

	/**
	 * Scan forward to find the end of a line (excluding terminators),
	 * skipping any block comments (which may have their own
	 * newlines embedded)
	 * @param text
	 * @param end current position from which to locate end of line
	 * @param limit upper bound to search
	 * @return offset of terminator at end of line  
	 */
	public static int skipToLogicalEndOfLineForward(char[] text, int end, int limit) {
	    while (end < limit) {
	        if (text[end] == '\n' || text[end] == '\r')
	            break;
	        if (end + 1 < limit && text[end] == '/' && text[end+1] == '*') {
	            // scan forward past block comment
	            end += 2;
	            while (end + 1 < limit) {
	                if (text[end] == '*' && text[end + 1] == '/')
	                    break;
	                end++;
	            }
	            end += 2; // skip '*' and '/'
	        } else
	        	end++;
	    }
	    return end;
	}

	/**
	 * Skip to and past the end of line
	 * @param text
	 * @param start
	 * @param endIdx
	 * @return position of next line
	 */
	public static int skipToNextLine(char[] text, int start, int endIdx) {
		while (start < endIdx) {
			if (text[start] == '\r') {
				if (start + 1 < endIdx && text[start + 1] == '\n') {
					start += 2;
				} else {
					start++;
				}
				break;
			} else if (text[start] == '\n') {
				start++;
				break;
			} else
				start++;
		}
	
		return start;
	}

	/**
	 * Skip to and past the end of line
	 * @param text
	 * @param start
	 * @return position of next line
	 */
	public static int skipToNextLine(CharSequence text, int start) {
		int endIdx = text.length();
		while (start < endIdx) {
			char ch = text.charAt(start);
			if (ch == '\r') {
				if (start + 1 < endIdx && text.charAt(start + 1) == '\n') {
					start += 2;
				} else {
					start++;
				}
				break;
			} else if (ch == '\n') {
				start++;
				break;
			} else
				start++;
		}
	
		return start;
	}

	/**
	 * Skip a newline if present
	 * @param subText
	 * @param i
	 */
	public static int skipIfNewLine(CharSequence subText, int i) {
		if (i < subText.length()) {
			char ch = subText.charAt(i); 
			if (ch == '\r') {
				if (i + 1 < subText.length() && subText.charAt(i+1) == '\n')
					return i + 2;
				else
					return i + 1;
			}
			else if (ch == '\n')
				return i + 1;
		}
		return i;
	}

	/**
	 * Skip a newline if present
	 * @param subText
	 * @param i incoming offset
	 * @return new offset
	 */
	public static int skipIfNewLine(char[] text, int i) {
		if (i < text.length) {
			char ch = text[i]; 
			if (ch == '\r') {
				if (i + 1 < text.length && text[i+1] == '\n')
					return i + 2;
				else
					return i + 1;
			}
			else if (ch == '\n')
				return i + 1;
		}
		return i;
	}

	/**
	 * Scan forward to skip a comment, either a block comment or
	 * a C++ single line comment.<p>  
	 * @param text
	 * @param end current position from which to locate comments
	 * @param limit upper bound to search
	 * @param includeTrailingSpace if true, skip whitespace and include
	 * any trailing newline; else, stop if no comment found on line. 
	 * @return new end offset   
	 */
	public static int includeTrailingComment(char[] text, int end, int limit, boolean includeTrailingSpace) {
		int currentEoc = end;
	    while (end < limit) {
	        if (text[end] == '\n' || text[end] == '\r') {
	        	if (includeTrailingSpace)
	        		currentEoc = skipIfNewLine(text, end);
	            break;
	        }
	        if (end + 1 < limit && text[end] == '/' && text[end+1] == '*') {
	            // scan forward past block comment
	            end += 2;
	            while (end + 1 < limit) {
	                if (text[end] == '*' && text[end + 1] == '/')
	                    break;
	                end++;
	            }
	            end += 2; // skip '*' and '/'
	            currentEoc = end;
	        } else if (end + 1 < limit && text[end] == '/' && text[end+1] == '/') {
	        	// scan forward past end of line comment
	        	end += 2;
	        	while (end < limit && text[end] != '\r' && text[end] != '\n')
	        		end++;
	        	if (includeTrailingSpace)
	        		currentEoc = skipIfNewLine(text, end);
	        	else
	        		currentEoc = end;
	        	break;
	        } else if (text[end] == ' ' || text[end] == '\t')
	        	end++;
	        else
	        	break;
	    }
	    return currentEoc;
	}

	/**
	 * Scan backward past any comments (single-line or block) and stop at the
	 * beginning of a line. Do not include any comments that live at the end of
	 * another line. Do not include blank lines if they do not have
	 * comments preceding. Stop if any non-whitespace encountered.
	 * 
	 * @param text
	 * @param start
	 *            current position from which to search backward
	 * @param limit
	 *            lower bound to search
	 * @param includeLeadingSpace if true, include whitespace before any comment
	 * @return offset of beginning of line
	 */
	public static int includeLeadingComment(char[] text, int start, int limit, boolean includeLeadingSpace) {
		// The algorithm works on a logical line-by-line basis,
		// where a logical line may be several lines spanned by
		// a block comment.
		//
		// We scan whitespace backward.  Non-whitespace may be
		// '*/', which prompts us to find a the beginning, and 
		// continue.  Or it may be other text, in which case
		// we scan backward for '//'.
		// we temporarily skip to the beginning of the line
		// and verify that 
		
		int currentLineStart = start;
		
		if (includeLeadingSpace)
			currentLineStart = skipOnlyWhitespaceToLineStart(text, currentLineStart, limit); 
		
		while (start > limit) {
			int prevCommentStart = findPreviousComment(text, currentLineStart, limit, true);
			// this check includes prevCommentStart==-1
			if (prevCommentStart >= limit) {
				currentLineStart = prevCommentStart;
				if (includeLeadingSpace)
					currentLineStart = skipOnlyWhitespaceToLineStart(text, currentLineStart, limit); 
			} else
				break;
		}
		return currentLineStart;
	}

	/**
	 * Find a comment preceding the cursor
	 * @param text
	 * @param currentLineStart
	 * @param limit
	 * @param mustBeAloneOnLine true: comment cannot have text preceding on line
	 * @return
	 */
	private static int findPreviousComment(char[] text, int start, int limit, boolean mustBeAloneOnLine) {
		int currentBoc = -1;
		boolean lastWasBlockComment = false;
		while (start >= limit) {
			if (start == limit) {
				if (currentBoc >= limit)
					return currentBoc;
				else
					break;
			}
	        start--;
	        if (text[start] == '\r' || text[start] == '\n') {
	        	// were we waiting to verify that a comment was alone
	        	// on the line (preceded perhaps by other one-line or block comments?)
	        	if (currentBoc >= limit)
	        		return currentBoc;
	        } else if (text[start] == ' ' || text[start] == '\t') {
	        	// skip spaces
	        } else if (start > limit && text[start-1] == '*' && text[start] == '/') {
	            // scan backward past block comment
	            while (start > limit) {
	            	start--;
	                if (text[start] == '/' && text[start+1] == '*') {
	                    break;
	                }
	            }
	            currentBoc = start;
	            lastWasBlockComment = true;
	            if (!mustBeAloneOnLine)
	            	return currentBoc;
	        } else {
	        	// not whitespace -- better be a single-line comment alone on the line
	        	int ptr = start;
	        	while (ptr >= limit) {
	        		if (text[ptr] == '/' && text[ptr - 1] == '/') {
	        			ptr--;
	        			// thee may be multiple one-line comments but we only want the last 
	        			if (currentBoc == -1) {
	        				currentBoc = ptr;
	        	            lastWasBlockComment = false;
	        	            if (!mustBeAloneOnLine)
	        	            	return currentBoc;
	        			}	        				
	        			break;
	        		}
	        		if (text[ptr] == '\r' || text[ptr] == '\n') {
	        			// oops, no comment at all on the line
	        			return -1;
	        		}
	        		ptr--;
	        	}
	        	start = ptr;
	        }
		}
		
		return lastWasBlockComment ? currentBoc : -1;
	}

	/**
	 * Skip to line start but only if only whitespace is encountered.
	 * @param text
	 * @param currentLineStart
	 * @param limit
	 * @return
	 */
	private static int skipOnlyWhitespaceToLineStart(char[] text, int start, int limit) {
		int orig = start;
		while (start > limit) {
			start--;
			if (text[start] == '\r' || text[start] == '\n')
				return start+1;
			if (text[start] != ' ' && text[start] != '\t')
				return orig;
		}
		return limit;
	}

	/**
	 * Skip whitespace in text, including comments.  
	 * @return offset of non-whitespace or newline
	 */
	public static int skipWhitespaceForward(char[] text, int end, int limit) {
	    while (end < limit) {
	        if (end + 1 < limit && text[end] == '/' && text[end+1] == '*') {
	            // scan forward past block comment
	            end += 2;
	            while (end + 1 < limit) {
	                if (text[end] == '*' && text[end + 1] == '/')
	                    break;
	                end++;
	            }
	            end += 2; // skip '*' and '/'
	        } else if (end + 1 < limit && text[end] == '/' && text[end+1] == '/') {
	        	// scan forward past end of line comment
	        	end += 2;
	        	while (end < limit && text[end] != '\r' && text[end] != '\n')
	        		end++;
	        	break;
	        } else if (text[end] == ' ' || text[end] == '\t')
	        	end++;
	        else
	        	break;
	    }
	    return end;
	}

	/**
	 * Match the given string in the array and return the updated position.
	 * 
	 * @return new position or pos if not matched
	 */
	public static int matchText(String str, char[] text, int pos, int limit) {
		int i;
		for (i = 0; i < str.length(); i++) {
			if (pos + i >= limit || text[pos + i] != str.charAt(i))
				return pos;
		}
		return pos + i;
	}

	/**
	 * Search for a directive backwards.  This is identified by
	 * a hash mark abutting whitespace after a line start.
	 * @param text
	 * @param ptr upper barrier
	 * @param limit lower barrier
	 * @return offset or -1 
	 */
	public static int searchDirectiveBackwards(char[] text, int ptr, int limit) {
		char quote = 0;
		while (ptr > limit) {
			ptr--;
			if (quote == text[ptr]) {
				quote = 0;
			} else if (text[ptr] == '#') {
				int line = skipToLogicalBeginningOfLineBackward(text, ptr, limit);
				if (skipWhitespaceForward(text, line, ptr) == ptr) {
					return ptr;
				}
			} else if (text[ptr] == '\'' || text[ptr] == '#')
				quote = text[ptr];
		}
		return -1;
	}

	/**
	 * Search for a directive forwards.  This is identified by
	 * a hash mark abutting whitespace after a line start.
	 * @param text
	 * @param ptr current position (which may be the directive)
	 * @param limit upper barrier
	 * @return offset or -1 
	 */
	public static int searchDirectiveForwards(char[] text, int ptr, int limit) {
		while (ptr < limit) {
			ptr = skipToLogicalEndOfLineForward(text, ptr, limit);
			if (ptr < limit) {
				ptr += newlineLength(text, ptr);
				ptr = skipWhitespaceForward(text, ptr, limit);
				if (ptr < limit) {
					if (text[ptr] == '#')
						return ptr;
				}
			}
		}
		return -1;
	}

	/**
	 * Find a named directive after the cursor and return its argument
	 * with any comments and surrounding space removed.
	 * @param name
	 * @param start pointer to '#'
	 * @param limit
	 * @return tuple of (trimmed argument, directive start, directive end,
	 * argument start, argument end) or null if no match
	 */
	public static Tuple findDirectiveAndArgument(String name, char[] text, int start, int limit) {
		int dirStart = -1, dirEnd = -1, argStart = -1, argEnd = -1;
		Check.checkArg(text[start] == '#');
		start++;
		start = skipWhitespaceForward(text, start, limit);
		if (start + name.length() < limit) {
			for (int i = 0; i < name.length(); i++)
				if (text[start + i] != name.charAt(i))
					return null;
			dirStart = start;
			start += name.length();
			dirEnd = start;
			start = skipWhitespaceForward(text, start, limit);
			
			argStart = start;
			int ptr = start;
			while (ptr < limit) {
				if (text[ptr] == '\r' || text[ptr] == '\n')
					break;
				int newPtr = skipWhitespaceForward(text, ptr, limit);
				if (newPtr == ptr)
					ptr++; // not whitespace
				else
					ptr = newPtr;
			}
			
			argEnd = ptr;

			// there may be comments at EOL; ignore them when deciding what the directive
			// argument's range is
			while (true) {
				int prevComment = findPreviousComment(text, ptr, argStart, false);
				if (prevComment == -1)
					break;
				while (prevComment > argStart && Character.isWhitespace(text[prevComment-1]))
					prevComment--;
				argEnd = prevComment;
				ptr = argEnd;
			}
				
			String arg = new String(text, argStart, argEnd - argStart);
			//arg = CdtUtils.stripComments(arg).toString().trim();
			return new Tuple(arg, dirStart, dirEnd, argStart, argEnd);
		}
		return null;
	}


}
