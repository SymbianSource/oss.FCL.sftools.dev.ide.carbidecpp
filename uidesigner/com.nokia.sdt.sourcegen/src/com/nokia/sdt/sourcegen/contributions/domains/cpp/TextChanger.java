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
import com.nokia.cpp.internal.api.utils.core.TextUtils;

/**
 * This class serves as a simple tree structure over text.
 * It serves to distinguish user text from generated text
 * and provide a means to update several discontinguous
 * areas by applying a hierarchically-arranged set of text blocks
 * (ILocation/IContribution).
 * 
 * 
 *
 */
public class TextChanger {

    static public class TreeNode {

        /**
         * Keep this region of text
         */
        static public final int OP_KEEP = 0;
        /**
         * Delete this region of text
         */
        static public final int OP_DELETE = 1;
        /**
         * Insert at this region of text
         */
        static public final int OP_INSERT = 2;
        
        int offset;
        int length;
        int oper;
        ITextInserter insertCallback;
        
        TreeNode next;
        
        public TreeNode(int oper, int offset, int length, ITextInserter insertCallback) {
            Check.checkState(offset >= 0);
            Check.checkState(length >= 0);
            this.oper = oper;
            this.offset = offset;
            this.length = length;
            if (oper == OP_INSERT)
                Check.checkArg(insertCallback);
            else
                Check.checkArg(insertCallback == null);
            this.insertCallback = insertCallback;
        }
        
        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        public String toString() {
            String opname = "???"; //$NON-NLS-1$
            if (oper == OP_KEEP) opname = "KEEP"; //$NON-NLS-1$
            else if (oper == OP_DELETE) opname = "DELETE"; //$NON-NLS-1$
            else if (oper == OP_INSERT) opname = "INSERT"; //$NON-NLS-1$
            return "oper("+opname+") offset=("+offset+") length=("+length+") cb="+insertCallback; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        }
        
        /**
         * @param text
         */
        public String dump(char[] text) {
        	try {
	            if (oper != OP_INSERT)
	                return this.toString() + ":\n'" + TextUtils.cleanUpXMLText(new String(text, offset, length)) + "'\n"; //$NON-NLS-1$ //$NON-NLS-2$
	            else
	                return toString() + "\n"; //$NON-NLS-1$
        	} catch (IndexOutOfBoundsException e) {
        		return toString() + ": !!! ERROR !!! invalid bounds"; //$NON-NLS-1$
        	}
        }
        
        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        public boolean equals(Object obj) {
            if (!(obj instanceof TreeNode))
                return false;
            TreeNode n = (TreeNode) obj;
            return n.oper == oper
            && n.offset == offset
            && n.length == length
            && n.insertCallback == insertCallback;
        }
        
        /**
         * @param curOffset current offset into buffer
         * @return new offset
         */
        public int execute(ITextReplacer replacer, int curOffset) {
            switch (oper) {
            case OP_KEEP:
                //System.out.println("keeping: '" + buffer.getText(curOffset, length) + "'");
                curOffset += length;
                break;
            case OP_DELETE:
                //System.out.println("deleting: '" + buffer.getText(curOffset, length) + "'");
                replacer.replaceText(curOffset, length, ""); //$NON-NLS-1$
                break;
            case OP_INSERT:
                String text = insertCallback.getInsertText();
                if (text != null) {
                    //System.out.println("inserting: '" + text + "'");
                    replacer.replaceText(curOffset, 0, text);
                    curOffset += text.length();
                }
                break;
            }
            return curOffset;
        }

        public int getOper() {
            return oper;
        }

        /**
         * @return Returns the length.
         */
        public int getLength() {
            return length;
        }

        /**
         * @return Returns the offset.
         */
        public int getOffset() {
            return offset;
        }

        public TreeNode getNext() {
            return next;
        }

      
        
    }
    
    TreeNode head;
    boolean changed;
    
    public TextChanger(char[] text) {
        if (text != null) {
            this.head = new TreeNode(TreeNode.OP_KEEP, 0, text.length, null);
        } else {
            this.head = null;
        }
        changed = false;
    }
    
    public TextChanger(int length) {
        if (length != 0) {
            this.head = new TreeNode(TreeNode.OP_KEEP, 0, length, null);
        } else {
            this.head = null;
        }
        changed = false;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (TreeNode n = head; n != null; n = n.getNext()) {
            buf.append(n);
            buf.append('\n');
        }
        return buf.toString();
    }
    
    /**
     * @param text
     */
    public String dump(char[] text) {
        StringBuffer buf = new StringBuffer();
        for (TreeNode n = head; n != null; n = n.getNext()) {
            buf.append(n.dump(text));
        }
        return buf.toString();
    }

    public TreeNode getChanges() {
        return head;
    }
    
    public boolean changed() {
    	return this.changed;
    }

    /**
     * Register a request to insert via a callback at the given position.
     * @param offset char offset with respect to original text
     * @param insertCallback
     */
    public void insert(int offset, ITextInserter insertCallback) {
    	this.changed = true;
        TreeNode newNode = new TreeNode(TreeNode.OP_INSERT, offset, 0, insertCallback);
        
        TreeNode node = null, prevNode = null;
        int index = 0;
        for (node = head; node != null; node = node.next) {
            if (newNode.offset >= node.offset 
                    && node.length > 0
                    && newNode.offset < node.offset + node.length) {
                merge(prevNode, node, newNode);
                return;
            }
            index++;
            prevNode = node;
        }
        
        // add new node to end
        if (prevNode != null) {
            Check.checkState(prevNode.offset + prevNode.length == newNode.offset);
            prevNode.next = newNode;
        }
        else {
            Check.checkState(newNode.offset == 0);
            head = newNode;
        }
        
    }

    /**
     * Register a request to delete the text covering the given range.
     * @param offset char offset with respect to original text
     * @param length
     */
    public void delete(int offset, int length) {
        if (length == 0)
            return;

    	this.changed = true;

        TreeNode newNode = new TreeNode(TreeNode.OP_DELETE, offset, length, null);
        TreeNode node = null, prevNode = null;
        int index = 0;
        for (node = head; node != null; node = node.next) {
            if (newNode.offset >= node.offset 
                    && node.length > 0
                    && newNode.offset < node.offset + node.length) {
                
                merge(prevNode, node, newNode);
                return;
            }
            index++;
            prevNode = node;
        }
        
        // deleting something that isn't here
        Check.checkState(false);
    }


    /** 
     * Merge the given node into the list with relation to
     * the given previous and current (super-range) node.
     * @param prevNode node after which to add
     * @param node node to modify
     * @param newNode new node to add 
     */
    private void merge(TreeNode prevNode, TreeNode node, TreeNode newNode) {
        // insert node either before, after, or between this node
        if (newNode.offset == node.offset && newNode.length == node.length) {
            // replace nodes
            if (prevNode != null)
                prevNode.next = newNode;
            else
                head = newNode;
            newNode.next = node.next;
        } else if (newNode.offset == node.offset) {
            // add the new node before the existing one,
            // and use the suffix of the existing node 
            node.offset += newNode.length;
            node.length -= newNode.length;
            Check.checkState(node.length >= 0);
            if (prevNode != null)
                prevNode.next = newNode;
            else
                head = newNode;
            newNode.next = node;
        } else if (newNode.offset + newNode.length == node.offset + node.length) {
            // add the new node after the existing one, and
            // use the prefix of the existing node
            node.length = newNode.offset - node.offset;
            Check.checkState(node.length >= 0);
            newNode.next = node.next;
            node.next = newNode;
        } else if (node.offset + node.length == newNode.offset) { 
        	newNode.next = node.next;
        	node.next = newNode;
    	} else {
            // split the existing node, placing the new one in between
            TreeNode afterNode = new TreeNode(node.oper, 
                    node.offset + (newNode.offset - node.offset) + newNode.length,
                    node.length - newNode.length - (newNode.offset - node.offset),
                    null);

            node.length = newNode.offset - node.offset;
            Check.checkState(node.length >= 0);

            afterNode.next = node.next;
            newNode.next = afterNode;
            node.next = newNode;
        }
    }

    /**
     * Apply all the changes in the text
     * @param replacer
     */
    public void apply(ITextReplacer replacer) {
        int offset = 0;
        for (TreeNode node = head; node != null; node = node.next) {
            offset = node.execute(replacer, offset);
        }
    }
}
