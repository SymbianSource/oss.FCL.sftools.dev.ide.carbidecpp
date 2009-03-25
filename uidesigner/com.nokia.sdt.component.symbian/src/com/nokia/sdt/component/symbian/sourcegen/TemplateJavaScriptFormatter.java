/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.component.symbian.sourcegen;

import com.nokia.sdt.sourcegen.templates.backend.*;

/**
 * This formatter generates Javascript code from template
 * text chunks
 * 
 * 
 */
public class TemplateJavaScriptFormatter implements ITextChunkVisitor {
    StringBuffer buffer;
    private String contribText;

	/** any text ever emitted? */
	private boolean anyText;
	
	/** is the current literal/expression string empty? */
	private boolean empty;
	private String newContrib;
	private String addContrib;
	private String contrib;
	
    /**
     * Format text chunks for Javascript contributions
     * @param contribText name of variable to use for appending text
     * @param contrib name of IContribution variable
     * @param contribs name of List variable for contributions
     */
    public TemplateJavaScriptFormatter(String contribText, String contrib, String contribs, String newContrib) {
    	this.contrib = contrib;
        this.contribText = contribText;
        this.addContrib = "\n\t" + contrib +".setText(" + contribText +");\n" +
        				"\t" + contribs + ".add(" + contrib + ");\n";
        this.newContrib = newContrib;
        empty = true;
        buffer = new StringBuffer();
    }
    
    public void reset() {
        buffer.setLength(0);
        empty = true;
        anyText = false;
    }

    private void makeNewContribToInitialize() {
		buffer.append("\n" + newContrib);
		if (anyText) {
			// mark this as being more text from the same template,
			// so we don't indent it separately
			buffer.append('\t');
			buffer.append(contrib);
			buffer.append(".setContinuation();\n");
		}
		buffer.append('\t');
		buffer.append(contribText);
		buffer.append(" = ");
    }

    private void appendText(TextChunk chunk) {
    	if (empty) {
    		makeNewContribToInitialize();
    	}
        else {
        	if (buffer.lastIndexOf("\n") + 80 < buffer.length())
        		buffer.append("\n\t\t");
        	buffer.append(" + ");
        }
        buffer.append(chunk.toString());
        empty = false;
        anyText = true;
    }

    private void clipContribution() {
    	if (!empty) {
    		buffer.append(";\n\t");
    		buffer.append(addContrib);
    		empty = true;
    	}
	}


    public String toString() {
    	if (!empty) {
	    	if (!anyText) {
	    		makeNewContribToInitialize();
	    		buffer.append("\"\";\n");
	    	}
    		buffer.append(addContrib);
    		empty = true;
    		anyText = true;
    	}
        return buffer.toString();
    }
    
    public void visit(ExprTextChunk chunk) {
    	appendText(chunk);
    }

    public void visit(RawTextChunk chunk, boolean isFirst, boolean isLast) {
        String text = chunk.getText();
        if (text.length() > 0) {
	        // any initial span of whitespace is clipped to start
	        // at the next new line 

        	chunk.setText(clipWhitespaceFromText(text, isFirst, isLast));
	        
	        if (chunk.getText().equals(""))
	            return;
        }
        appendText(chunk);
    }

    public static String clipWhitespaceFromText(String text, boolean isFirst, boolean isLast) {
        int nl;
        boolean strippedPrefix = false;
        if (isFirst) {
            nl = text.indexOf('\n');
            if (nl != -1) {
                int clipIdx = 0;
                char ch = 0;
                while (clipIdx < nl) {
                    ch = text.charAt(clipIdx);
                    if (!(ch == ' ' || ch == '\t'))
                        break;
                    clipIdx++;
                }
    
                if (clipIdx == nl) {
                    text = text.substring(nl + 1);
                    strippedPrefix = true;
                }
            }
        }
        
        if (isLast) {
            // any final span of whitespace in raw text 
            // is clipped at the last newline
            nl = text.lastIndexOf('\n');
            if (nl != -1 || strippedPrefix) {
                int clipIdx = nl + 1;
                char ch = 0;
                while (clipIdx < text.length()) {
                    ch = text.charAt(clipIdx);
                    if (!(ch == ' ' || ch == '\t'))
                        break;
                    clipIdx++;
                }
    
                if (clipIdx == text.length()) {
                    text = text.substring(0, nl + 1);
                }
                
            }
        }
        
        return text;
	}

	public void visit(ScriptTextChunk chunk) {
    	clipContribution();
        buffer.append(chunk.toString());
    }

	public void visit(LiteralTextChunk chunk) {
    	clipContribution();
        buffer.append(chunk.toString());
    }
}