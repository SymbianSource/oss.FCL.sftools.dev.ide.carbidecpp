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

package com.nokia.sdt.sourcegen.contributions.domains.cpp;

import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTNodeLocation;
import org.eclipse.cdt.core.model.ITranslationUnit;

import java.util.*;

/**
 * This represents a range of source code and the IASTNode
 * that encompasses it.
 * 
 * 
 *
 */
public class CodeRange {

    IASTNode node;
    int offset;
    int length;
    char[] text;
	
	CodeRange parent;
	List<CodeRange> kids;
	
	private ITranslationUnit unit;	// only set at root
    
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CodeRange) {
			CodeRange range = (CodeRange) obj;
			return range.offset == offset && range.length == length && range.text == text;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return text.hashCode() ^ offset ^ (length << 16);
	}
	
    public CodeRange(CodeRange parent, IASTNode node, int offset, int length) {
        Check.checkArg(node);
        Check.checkArg(parent);
        this.parent = parent;
        this.text = parent.getText();
        this.node = node;
        this.offset = offset;
        this.length = length;
    }

    public CodeRange(char[] text, IASTNode node, ITranslationUnit unit, int offset, int length) {
        Check.checkArg(node);
        this.parent = null;
        this.unit = unit;
        this.text = text;
        this.node = node;
        this.offset = offset;
        this.length = length;
    }

    public CodeRange(char[] text, IASTNode node, ITranslationUnit unit) {
        Check.checkArg(node);
        this.parent = null;
        this.unit = unit;
        this.text = text;
        this.node = node;
        this.offset = 0;
        this.length = text.length;
    }

    public CodeRange(CodeRange parent, IASTNode node) {
    	Check.checkArg(parent);
        IASTNodeLocation nodeLoc = node.getFileLocation();
        this.parent = parent;
        this.text = parent.getText();
        this.node = node;
        this.offset = nodeLoc != null ? nodeLoc.getNodeOffset() : 0;
        this.length = nodeLoc != null ? nodeLoc.getNodeLength() : 0;
//        this.node = node;
        /*IASTNodeLocation[] locs = node.getNodeLocations();
        this.offset = locs[0].getNodeOffset();
        this.length = locs[locs.length-1].getNodeOffset() + locs[locs.length-1].getNodeLength() - this.offset;
        */
        //this.offset = ((org.eclipse.cdt.internal.core.dom.parser.ASTNode)node).getOffset();
        //this.length = ((org.eclipse.cdt.internal.core.dom.parser.ASTNode)node).getLength();
    }

    public CodeRange(CodeRange parent, IASTNode commonParent, IASTNode start, IASTNode end) {
    	Check.checkArg(parent);
    	this.parent = parent;
    	this.text = parent.getText();
        IASTNodeLocation startLoc = start.getFileLocation(); 
        IASTNodeLocation endLoc = end.getFileLocation(); 
        this.node = commonParent;
        
        this.offset = startLoc != null ? startLoc.getNodeOffset() : 0;
        this.length = endLoc != null ? endLoc.getNodeLength() + (endLoc.getNodeOffset() - this.offset) : 0;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    	return "CodeRange: " + offset + " - " + (offset+length) + " = '" + new String(text, offset, length) + "'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    }
    
    /**
     * Get the text for the whole file
     * @return file text
     */
    public char[] getText() {
    	return text;
    }
    
    /**
     * @return Returns the length.
     */
    public int getLength() {
        return length;
    }

    /**
     * @return Returns the node.
     */
    public IASTNode getNode() {
        return node;
    }

    /**
     * @return Returns the offset.
     */
    public int getOffset() {
        return offset;
    }
    
    public CodeRange getParent() {
    	return parent;
    }
    
    /**
     * Add a child range
     */
    public void addChildRange(CodeRange range) {
    	if (kids == null) {
    		kids = new LinkedList<CodeRange>();
    	}
	   	for (int idx = 0; idx < kids.size(); idx++) {
    		if (range.getOffset() < kids.get(idx).getOffset()) {
    			kids.add(idx, range);
    			return;
    		}
    	}
    	range.parent = this;
    	kids.add(range);
    }
    
    public CodeRange[] getChildren() {
		if (kids == null)
			return new CodeRange[0];
		return (CodeRange[]) kids.toArray(new CodeRange[kids.size()]);
	}

	/**
     * Validate that the children (recursively) make sense,
     * e.g., that they do not overlap each other.
     * @return true: children are valid, false: some problem
     */
    public boolean validateChildren() {
    	CodeRange prev = null;
    	if (kids == null)
    		return true;
    	for (Iterator iter = kids.iterator(); iter.hasNext();) {
			CodeRange range = (CodeRange) iter.next();
			if (prev != null) {
				if (prev.getEndOffset() > range.getOffset()
						&& !prev.equals(range))
					return false;
			}
			if (!range.validateChildren())
				return false;
			prev = range;
		} 
    	return true;
    }
    
    /**
     * Tell if a node is contained in this one 
     */
    public boolean contains(CodeRange range) {
    	return (range.offset >= offset && range.offset + range.length <= offset + length);
    }

    public boolean sameRange(CodeRange range) {
    	return range.text == text && range.offset == offset && range.length == length;
    }
    
    /**
     * Clone a code range node (no children copied)
     */
	public CodeRange clone() {
		if (parent != null)
			return new CodeRange(parent, node, offset, length);
		else
			return new CodeRange(text, node, unit, offset, length);
	}

	/**
	 * Clone a range with an alternate parent
	 * @param parent
	 */
	public CodeRange clone(CodeRange parent) {
		return new CodeRange(parent, node, offset, length);
	}

	/**
	 * Get end of range.
	 */
	public int getEndOffset() {
		return offset + length;
	}

	/**
	 * Tell if a range contains a span of text
	 * @param start start offset
	 * @param length length
	 * @return
	 */
	public boolean contains(int start, int length) {
    	return (start >= this.offset && start + length <= this.offset + this.length);
	}
	
	public ITranslationUnit getTranslationUnit() {
		CodeRange range = this;
		while (range != null) {
			if (range.unit != null)
				return range.unit;
			range = range.parent;
		}
		return null;
	}
}
