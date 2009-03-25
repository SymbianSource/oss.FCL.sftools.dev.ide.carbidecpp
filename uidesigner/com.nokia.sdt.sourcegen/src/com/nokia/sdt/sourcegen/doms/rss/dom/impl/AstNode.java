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

package com.nokia.sdt.sourcegen.doms.rss.dom.impl;

import com.nokia.sdt.sourcegen.ISourceFormatter;
import com.nokia.sdt.sourcegen.core.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.cpp.internal.api.utils.core.Check;

import java.io.PrintStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 *
 */
abstract public class AstNode implements IAstNode {

    private IAstNode parent;
    /** The range for only the tokens associated
     * with the node (subset of sourceRange)
     */
    private ISourceRange sourceRange;
    /** The range for all the whitespace and tokens associated
     * with the node
     */
    private ISourceRange extendedRange;

    public static final Object[] NO_SEGMENTS = new Object[0];

    /** Tell if the node has dirty source, which means
     * either some attribute changed or the node did
     * not come from source (i.e. range == null).
     * Subclasses must update this in setters that
     * affect generated source. 
     */
    protected boolean dirty;
    
    public AstNode() {
    }

    public String toString() {
    	return dump();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#isDirty()
     */
    public boolean isDirty() {
        return dirty;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#isDirtyTree()
     */
    public boolean isDirtyTree() {
        if (dirty) 
            return true;
        IAstNode children[] = getChildren();
        for (int i = 0; i < children.length; i++) {
            if (children[i].isDirtyTree())
                return true;
        }
        return false;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#setDirty(boolean)
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#setDirtyTree(boolean)
     */
    public void setDirtyTree(boolean dirty) {
    	setDirty(dirty);
    	IAstNode[] kids = getChildren();
    	for (int i = 0; i < kids.length; i++) {
			kids[i].setDirtyTree(dirty);
		}
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getParent()
     */
    public IAstNode getParent() {
        return parent;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#setParent(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    public void setParent(IAstNode node) {
        if (node != null)
            Check.checkArg(parent == null);
        parent = node;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getSourceRange()
     */
    public ISourceRange getSourceRange() {
        return sourceRange;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#setSourceRange(com.nokia.sdt.sourcegen.doms.rss.dom.ISourceRange)
     */
    public void setSourceRange(ISourceRange range) {
        this.sourceRange = range;
        if (range == null)
        	extendedRange = null;
        else
        	extendedRange = new SourceRange(range);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getTokenRange()
     */
    public ISourceRange getExtendedRange() {
        return extendedRange;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#setTokenRange(com.nokia.sdt.sourcegen.doms.rss.dom.ISourceRange)
     */
    public void setExtendedRange(ISourceRange range) {
        if (range != null) {
            Check.checkState(sourceRange != null);
        /*    Check.checkArg(parent == null || parent.getSourceRange() == null 
                    || parent.getSourceRange().contains(range));*/
        }
        this.extendedRange = range;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#hasDirtySource()
     */
    public boolean hasDirtySource() {
    	return isDirty() || (sourceRange == null);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#hasDirtySource()
     */
    public boolean hasDirtySourceTree() {
        return isDirtyTree() || (sourceRange == null);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getOriginalText()
     */
    public String getOriginalText() {
        if (extendedRange != null)
            return extendedRange.getText();
        else if (sourceRange != null)
            return sourceRange.getText();
        else
            return null;
    }

    /**
     * Iterate over the text segments provided by #getTextSegments()
     * on a recursive descent through the node tree.<p>
     * These are all Objects for which toString()
     * will be used to get text.  No IAstNodes should be present. 
     * SpecialSegments may be present.  This will be a mix of unchanged text 
     * and changed text.
     */
    static class SourceIterator implements Iterator {
        
        /** list of segments */
        LinkedList segments = new LinkedList();
        IAstNode currentNode;
        
        SourceIterator(AstNode node) {
            segments.add(node);
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public boolean hasNext() {
            /*
             * This looks magic.  Here's what's up. We maintain a FIFO where initially
             * we have an AstNode entry.  When the current item is an AstNode,
             * we remove it from the FIFO and replace it with its constituent text segments via   
             * AstNode#getTextSegments().  This continues until (eventually)
             * no more AstNodes are seen and all that is left are non-AstNodes.
             * 
             * entry:  { AstNode }  
             * remove and expand: AstNode provides { 1, AstNode, 2 }
             * giving: { 1, AstNode, 2 }
             * consume: 1
             * entry: { AstNode, 2 } 
             * remove and expand: AstNode provides { 3, 4 }
             * giving: { 3, 4, 2 }
             * consume: 3
             * consume: 4
             * consume: 2
             */
            while (!segments.isEmpty()) {
                if (segments.getFirst() instanceof AstNode) {
                    AstNode node = (AstNode) segments.remove(0);
                    currentNode = node;
                    
                    Object[] segs = node.getTextSegments();
                    if (segs.length > 0)
                        segments.addAll(0, Arrays.asList(segs));
                    else
                        segments.add(0, "");    /* marker to ensure we see node */ //$NON-NLS-1$
                }
                else
                    break;
            }
            return !segments.isEmpty();
        }

        public Object next() {
            if (segments.isEmpty())
                throw new NoSuchElementException();
            return segments.remove(0);
        }
        
        public IAstNode currentNode() {
            return currentNode;
        }
    }
    
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#handleSegments(com.nokia.sdt.sourcegen.doms.rss.dom.ISegmentHandler)
	 */
	public void handleSegments(ISegmentHandler handler) {
		Object[] segments = getTextSegments();
		for (int i = 0; i < segments.length; i++) {
            Object segment = segments[i];

            if (segment instanceof IAstNode) {
                IAstNode subNode = (IAstNode) segment;
                handler.handleAstNode(subNode);
            } else {
            	handler.handleText(segment);
            }
		}
	}

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getCurrentText()
     */
    public String getNewText(ISourceFormatter formatter) {
        NewTextHandler handler = new NewTextHandler(formatter);
        handler.handleAstNode(this);
        return handler.toString();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getCurrentText()
     */
    public String getCurrentText(ISourceFormatter formatter) {
        CurrentTextHandler handler = new CurrentTextHandler(formatter);
        handler.handleAstNode(this);
        return handler.toString();
    }

    public String getCurrentTextUpdating(ISourceFormatter formatter, ISourceFile file, int offset, int line) {
        CurrentTextUpdatingHandler handler = new CurrentTextUpdatingHandler(formatter, file, offset, line);
        handler.handleAstNode(this);
        return handler.toString();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getTranslationUnit()
     */
    public ITranslationUnit getTranslationUnit() {
        IAstNode node = this;
        while (node != null) {
            if (node instanceof IAstSourceFile) {
                return ((IAstSourceFile) node).getTranslationUnit();
            }
            node = node.getParent();
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getAstSourceFile()
     */
    public IAstSourceFile getAstSourceFile() {
        IAstNode node = this;
        while (node != null) {
            if (node instanceof IAstSourceFile) {
                return (IAstSourceFile) node;
            }
            node = node.getParent();
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#accept(com.nokia.sdt.sourcegen.doms.rss.dom.AstVisitor)
     */
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#acceptReference(com.nokia.sdt.sourcegen.doms.rss.dom.AstVisitor)
     */
    public void acceptReference(AstVisitor visitor) {
        visitor.visitReference(this);
    }

    /**
     * Dump the source from scratch for use by #toString()
     * @return
     */
    protected String dump() {
    	try {
    		return getNewText(null);
    	} catch (Throwable t) {
    		return "<error>"; //$NON-NLS-1$
    	}
    }

    public void dump(final PrintStream out) {
        class Visitor extends AstVisitor {
            StringBuffer indent;
            Visitor() {
                this.indent = new StringBuffer();
            }
            
            /* (non-Javadoc)
             * @see com.nokia.sdt.sourcegen.doms.rss.dom.AstVisitor#visit(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
             */
            public int visit(IAstNode node) {
                
                out.println(indent + "child: " + node); //$NON-NLS-1$
                dump(node);
                
                indent.append('\t');
                traverseChildren(node);
                indent.deleteCharAt(indent.length()-1);
                
                return PROCESS_CONTINUE;
            }
            
            /* (non-Javadoc)
             * @see com.nokia.sdt.sourcegen.doms.rss.dom.AstVisitor#visitReference(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
             */
            public int visitReference(IAstNode node) {
                out.println(indent+"reference: " + node); //$NON-NLS-1$
                dump(node);
                return PROCESS_CONTINUE;
            }
            
            String compress(String txt) {
                if (txt == null)
                    return "<null>"; //$NON-NLS-1$
                Pattern p = Pattern.compile("\\s+", Pattern.MULTILINE); //$NON-NLS-1$
                Matcher m = p.matcher(txt);
                return m.replaceAll(" "); //$NON-NLS-1$
            }
            
            void dump(IAstNode node) {
                out.println(indent+"range: " + node.getSourceRange()); //$NON-NLS-1$
                out.println(indent+"orig : '" + compress(node.getOriginalText()) + "'"); //$NON-NLS-1$ //$NON-NLS-2$
                
            }
        }
        
        accept(new Visitor());
    }
    
    /**
     * This iterator wraps another iterator.
     * If the "remove" call is made, it marks the owning
     * node dirty.
     * 
     *
     */
    protected class DirtyTrackingIterator<E> implements Iterator<E> {

    	private Iterator<E> iterator;

		/**
		 * 
		 */
		public DirtyTrackingIterator(Iterator<E> iterator) {
			Check.checkArg(iterator);
			this.iterator = iterator;
		}
		
		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			return iterator.hasNext();
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		public E next() {
			return iterator.next();
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			iterator.remove();
			dirty = true;
		}
    	
    }
    
    /**
     * This iterator wraps another iterator.
     * If the "remove" call is made, it marks the owning
     * node dirty.
     * 
     *
     */
    protected class DirtyTrackingListIterator<E> implements ListIterator<E> {

    	private ListIterator<E> iterator;

		/**
		 * 
		 */
		public DirtyTrackingListIterator(ListIterator<E> iterator) {
			Check.checkArg(iterator);
			this.iterator = iterator;
		}
		
		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			return iterator.hasNext();
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		public E next() {
			return iterator.next();
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			iterator.remove();
			dirty = true;
		}

		/* (non-Javadoc)
		 * @see java.util.ListIterator#hasPrevious()
		 */
		public boolean hasPrevious() {
			return iterator.hasPrevious();
		}

		/* (non-Javadoc)
		 * @see java.util.ListIterator#previous()
		 */
		public E previous() {
			return iterator.previous();
		}

		/* (non-Javadoc)
		 * @see java.util.ListIterator#nextIndex()
		 */
		public int nextIndex() {
			return iterator.nextIndex();
		}

		/* (non-Javadoc)
		 * @see java.util.ListIterator#previousIndex()
		 */
		public int previousIndex() {
			return iterator.previousIndex();
		}

		/* (non-Javadoc)
		 * @see java.util.ListIterator#set(E)
		 */
		public void set(E o) {
			iterator.set(o);
		}

		/* (non-Javadoc)
		 * @see java.util.ListIterator#add(E)
		 */
		public void add(E o) {
			iterator.add(o);
			dirty = true;
		}
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#isNodeInTree(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    public boolean isNodeInTree(IAstNode node) {
    	if (node == this)
    		return true;
    	IAstNode[] kids = getChildren();
    	for (int i = 0; i < kids.length; i++) {
			if (kids[i].isNodeInTree(node))
				return true;
		}
    	return false;
    }

    /** Implementation for IAstListElement derivations 
     * @see IAstListElement
     * */
    public void removeFromParent() {
    	IAstListNode parent = (IAstListNode) getParent();
    	Check.checkState(parent != null);
    	// The AstSourceFile impl should use removeItem itself instead of requiring this code
    	if (parent.getParent() instanceof IAstSourceFile)
    		((IAstSourceFile) parent.getParent()).removeFileNode(this);
    	else
    		parent.removeItem(this);
    }
}
