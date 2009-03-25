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

import com.nokia.cpp.internal.api.utils.core.ChainedIterator;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.sourcegen.ISourceFormatter;
import com.nokia.sdt.sourcegen.core.ISourceFile;
import com.nokia.sdt.sourcegen.core.ISourceRange;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstListNode.ITextSegmentGenerator;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.*;

import java.util.*;

public class AstRssSourceFile extends AstSourceFile implements IAstRssSourceFile, ITextSegmentGenerator {

    /** List of IAstTopLevelNodes (cached) */
    private List topLevelNodes;
    /** List of IAstNameStatements and IAstCharacterSetStatements (cached) */
    private List initialStmtNodes;
    /** List of IAstPreprocessorDefineDirective (cached) */
    private List defines;
    /** List of system IAstPreprocessorIncludeDirective (local to this file) (cached) */
    private List systemIncludes;
    /** List of user non-model IAstPreprocessorIncludeDirective (local to this file) (cached) */
    private List userIncludes;
    /** List of IAstPreprocessorLocIncludeNode (cached) */
    private List locIncludes;
    /** List of IAstPreprocessorRlsIncludeNode (cached) */
    private List rlsIncludes;
    /** List of IAstMacroExpression (cached) */
    private List macroExprs;
    /** List of IAstRlsString (cached) */
    private List rlsStrings;
    /** List of user model IAstPreprocessorIncludeDirective (local to this file) (cached) */
    private List modelIncludes;

    /** List of cached lists (above) in default emission order */
    protected List categories;
    
    protected void init(Class baseType) {
    	super.init(baseType);
    	
    	fileNodes.setTextSegmentGenerator(this);    	
    	fileNodes.setDirty(false);
    	
        topLevelNodes = new ArrayList();
        initialStmtNodes = new ArrayList(2);
        defines = new ArrayList();
        systemIncludes = new ArrayList();
        userIncludes = new ArrayList();
        locIncludes = new ArrayList();
        rlsIncludes = new ArrayList();
        rlsStrings = new ArrayList();
        macroExprs = new ArrayList();
        modelIncludes = new ArrayList();
        
        // set up ordering for categories in default file layout
        categories = new ArrayList();
        categories.add(initialStmtNodes);
        categories.add(systemIncludes);
        categories.add(userIncludes);
        categories.add(locIncludes);
        categories.add(rlsIncludes);
        categories.add(rlsStrings);
        categories.add(defines);
        categories.add(topLevelNodes);
        categories.add(modelIncludes);
        
        refresh();
        dirty = false;
    }

    /**
     * Create a source file node for 'file' 
     */
    public AstRssSourceFile(ISourceFile file) {
        super(file, IAstNode.class);
    }

    /**
     * Create an unnamed source file.
     */
    public AstRssSourceFile() {
        super();
        init(IAstNode.class);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#toString()
     */
    public String toString() {
        return "AstRssSourceFile { " + getSourceFile().getFileName() + " }\n" + dump(); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstListNode.ITextSegmentGenerator#getTextSegments(com.nokia.sdt.sourcegen.doms.rss.dom.IAstListNode)
     */
    public List getTextSegments(IAstListNode listNode) {
    	List segments = new ArrayList();
		for (Iterator iter = listNode.iterator(); iter.hasNext();) {
			IAstNode node = (IAstNode) iter.next();
			segments.add(node);
		}	
		
		// get the list of children in emission order
        interleave(segments);
        
        addSpacing(segments);
        return segments;
    }
    
    /** Refresh cached lists */
    public void refresh() {
        topLevelNodes.clear();
        initialStmtNodes.clear();
        defines.clear();
        userIncludes.clear();
        systemIncludes.clear();
        locIncludes.clear();
        rlsIncludes.clear();
        macroExprs.clear();
        rlsStrings.clear();
        modelIncludes.clear();
        
        for (Iterator iter = fileNodes.iterator(); iter.hasNext();) {
            IAstNode node = (IAstNode) iter.next();
            List category = getCategory(node);
            if (category != null)
                category.add(node);
        }
    }
    
    protected List getCategory(IAstNode node) {
        if (node instanceof IAstPreprocessorDefineDirective) {
            return defines;
        } else if (node instanceof IAstPreprocessorLocIncludeNode) {
            return locIncludes;
        } else if (node instanceof IAstPreprocessorRlsIncludeNode) {
        	return rlsIncludes;
        } else if (node instanceof IAstPreprocessorModelIncludeNode) {
        	return modelIncludes;
        } else if (node instanceof IAstPreprocessorIncludeDirective) {
            if (((IAstPreprocessorIncludeDirective) node).isUserPath())
                return userIncludes;
            else
                return systemIncludes;
        } else if (node instanceof IAstPreprocessorMacroExpression) {
            return macroExprs;
        } else if (node instanceof IAstRlsString) {
            return rlsStrings;
        } else if (node instanceof IAstNameStatement 
                || node instanceof IAstCharacterSetStatement
                || node instanceof IAstUidStatement) {
            return initialStmtNodes;
        } else if (node instanceof IAstTopLevelNode) {
            return topLevelNodes;
        } else {
            return null;
        }
    }
    
    protected int getCategoryIndex(List category) {
    	int idx = 0;
    	for (Iterator iter = categories.iterator(); iter.hasNext();) {
			List list = (List) iter.next();
			if (list == category)
				return idx;
			idx++;
		}
    	return -1;
    }
    
    private static final List fileNodeSpacingCategory = new ArrayList(0);
    protected List getSpacingCategory(IAstNode node) {
        List category = getCategory(node);
        if (category == defines 
        		|| category == rlsIncludes || category == locIncludes
                || category == userIncludes || category == systemIncludes
                || category == modelIncludes
                || category == macroExprs)
            return fileNodeSpacingCategory;
        else
            return category;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#addNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    public void addFileNode(IAstNode node) {
        // The "best" place to add a node is to the end of
        // other like nodes in its category.
    	//
    	// BUT, since a category contains ALL such instances,
    	// which do not necessarily appear in contiguous order,
    	// the actual fileNodes insertion point may be different.
        List category = getCategory(node);
        
        if (node.getSourceRange() == null && category != null) {
            // Get the insertion point in the file (typically
            // at the end of the node's category, but it might
            // be empty, so it must be at the end of the previous
            // category, etc.)
            IAstNode after = null;
            
            // Categories are in an order, but nodes within them
            // are not mutually ordered.  Only insert after a
            // node which is before other nodes in later categories.
            Iterator iter;
            for (iter = categories.iterator(); iter.hasNext();) {
                List otherCategory = (List) iter.next();
                
                if (otherCategory != category) {
                	if (otherCategory.size() > 0)
                		after = (IAstNode) otherCategory.get(otherCategory.size() - 1);
                } else {
                	after = findLastContiguousNodeInCategory(otherCategory);
                	break;
                }
            }
            
            // add to file nodes at right position
            if (after != null) {
            	fileNodes.insertItem(after, node);
            } else {
                // no existing categories to insert after;
                // insert before any nodes of a later category
                
                IAstNode before = null;
            
                while (iter.hasNext()) {
                    List otherCategory = (List) iter.next();
                    if (otherCategory.size() > 0) {
                        before = (IAstNode) otherCategory.get(0);
                        break;
                    }
                }
                
                fileNodes.insertBeforeFileNode(node, before);
            }
            
            // add to specific category
            category.add(node);
        } else {
            // no category or source range specified
            super.addFileNode(node);
        }
        markDirtyTU();
    }

    
    /**
     * Find the last node in a category which is contiguous in the
     * file with previous nodes in the category.  Contiguous means
     * only non-categorized nodes may appear between.<p>
     *
     *      +-- node order--------+
     *      v           |         |
     *	A = {
     *		1
     *	        B = {
     *				 	2
     *		3
     *					4
     *					5
     *				}
     *						C = {
     *							 6
     *							}
     *		7
     *		}
     *	to insert into A so it is still in order relative
     *	to B and C, we want to add after 1, not 7.
     * 
	 * @param category
	 * @return last node (never null)
	 */
	private IAstNode findLastContiguousNodeInCategory(List category) {
		Check.checkArg(category);

		if (category.size() == 0)
			return null;
		
		// find the index of the node in the file
		IAstNode last = (IAstNode) category.get(0);
		
    	int fileIndex = fileNodes.indexOf(last);
    	Check.checkState(fileIndex >= 0);

    	// for better performance
    	if (category.size() == fileNodes.size())
    		return (IAstNode) category.get(category.size() - 1);
    	
    	while (++fileIndex < fileNodes.size()) {
    		IAstNode other = fileNodes.get(fileIndex);
    		List otherCategory = getCategory(other);
    		if (otherCategory != null) {
    			if (otherCategory != category)
    				break;
    			last = other;
    		}
    	}
    	
		return last;
	}

	/* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#appendNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    public void appendFileNode(IAstNode node) {
    	super.appendFileNode(node);
        List category = getCategory(node);
        if (category != null)
            category.add(node);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#insertNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode, com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    public void insertFileNode(IAstNode after, IAstNode node) {
    	super.insertFileNode(after, node);
        List category = getCategory(node);
        if (after == null) {
            if (category != null)
                category.add(0, node);
        } else {
            if (category != null) {
                // after may not be in this category, which is fine
                int index = category.indexOf(after);
                if (index != -1) {
                    category.add(index + 1, node);
                } else {
                    category.add(node);
                }
            }
        }
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#insertBeforeFileNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode, com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    public void insertBeforeFileNode(IAstNode node, IAstNode before) {
    	super.insertBeforeFileNode(node, before);
        List category = getCategory(node);
        if (before == null) {
            if (category != null)
                category.add(node);
        } else {
            if (category != null) {
                // after may not be in this category, which is fine
                int index = category.indexOf(before);
                if (index != -1) {
                    category.add(index, node);
                } else {
                    category.add(0, node);
                }
            }
        }
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#removeNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    public void removeFileNode(IAstNode node) {
        List category = getCategory(node);
        if (category != null)
            category.remove(node);
        super.removeFileNode(node);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITrackedSourceFile#getTopLevelNodes()
     */
    public IAstTopLevelNode[] getTopLevelNodes() {
        return (IAstTopLevelNode[]) topLevelNodes.toArray(new IAstTopLevelNode[topLevelNodes.size()]);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITrackedSourceFile#addTopLevelNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstTopLevelNode)
     */
    public void appendTopLevelNode(IAstTopLevelNode node) {
        appendFileNode(node);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#insertTopLevelNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstTopLevelNode, com.nokia.sdt.sourcegen.doms.rss.dom.IAstTopLevelNode)
     */
    public void insertTopLevelNode(IAstNode after, IAstTopLevelNode node) {
        insertFileNode(after, node);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITrackedSourceFile#removeTopLevelNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstTopLevelNode)
     */
    public void removeTopLevelNode(IAstTopLevelNode node) {
        removeFileNode(node);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITrackedSourceFile#getPreprocessorStatements()
     */
    public IAstPreprocessorNode[] getPreprocessorNodes() {
        List sublist = new ArrayList();
        for (Iterator iter = fileNodes.iterator(); iter.hasNext();) {
            IAstNode node = (IAstNode) iter.next();
            if (node instanceof IAstPreprocessorNode)
                sublist.add(node);
        }
        return (IAstPreprocessorNode[]) sublist.toArray(new IAstPreprocessorNode[sublist.size()]);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITrackedSourceFile#hasTopLevelNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstTopLevelNode)
     */
    public boolean hasTopLevelNode(IAstTopLevelNode node) {
        Check.checkArg(node);
        return topLevelNodes.contains(node);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITrackedSourceFile#addPreprocessorStatement(com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorStatement)
     */
    public void appendPreprocessorNode(IAstPreprocessorNode node) {
        appendFileNode(node);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#removePreprocessorNode(com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorNode)
     */
    public void removePreprocessorNode(IAstPreprocessorNode node) {
        removeFileNode(node);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITrackedSourceFile#insertPreprocessorStatement(com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorStatement, com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorStatement)
     */
    public void insertPreprocessorNode(IAstNode after, IAstPreprocessorNode node) {
        insertFileNode(after, node);
    }

    /*
     *  (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#addMacro(com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorDefineDirective)
     */
    public void appendDefine(IAstPreprocessorDefineDirective node) {
        appendFileNode(node);
    }
    
    /*
     *  (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#removeMacro(com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorDefineDirective)
     */
    public void removeDefine(IAstPreprocessorDefineDirective node) {
        removeFileNode(node);
    }
    
    /*
     *  (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#findMacro(java.lang.String)
     */
    public IAstPreprocessorDefineDirective findDefine(String name) {
        Check.checkArg(name);
        for (Iterator iter = defines.iterator(); iter.hasNext();) {
            IAstPreprocessorDefineDirective macro = (IAstPreprocessorDefineDirective) iter.next();
            if (macro.getMacro().getName().equals(name))
                return macro;
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITrackedSourceFile#getMacros()
     */
    public IAstPreprocessorDefineDirective[] getDefines() {
        return (IAstPreprocessorDefineDirective[]) defines.toArray(new IAstPreprocessorDefineDirective[defines.size()]);
    }

    protected Iterator allIncludes() {
    	return new ChainedIterator(systemIncludes.iterator(),
    			new ChainedIterator(userIncludes.iterator(),
    					new ChainedIterator(locIncludes.iterator(),
    							new ChainedIterator(rlsIncludes.iterator(),
    									modelIncludes.iterator()))));
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#findIncludeFile(com.nokia.sdt.sourcegen.doms.rss.dom.ISourceFile)
     */
    public IAstSourceFile findIncludeFile(ISourceFile file) {
        for (Iterator iter = allIncludes(); iter.hasNext();) {
            IAstPreprocessorIncludeDirective incl = (IAstPreprocessorIncludeDirective) iter.next();
            if (incl.getFile() != null && incl.getFile().getSourceFile() == file)
                return incl.getFile();
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#findIncludeFile(java.lang.String)
     */
    public IAstSourceFile findIncludeFile(String fileName) {
        for (Iterator iter = allIncludes(); iter.hasNext();) {
            IAstPreprocessorIncludeDirective incl = (IAstPreprocessorIncludeDirective) iter.next();
            if (incl.getFilename().equals(fileName))
                return incl.getFile();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#findIncludeFile(com.nokia.sdt.sourcegen.doms.rss.dom.ISourceFile)
     */
    public IAstPreprocessorIncludeDirective findInclude(ISourceFile file) {
        for (Iterator iter = allIncludes(); iter.hasNext();) {
            IAstPreprocessorIncludeDirective incl = (IAstPreprocessorIncludeDirective) iter.next();
            if (incl.getFile() != null && incl.getFile().getSourceFile() == file)
                return incl;
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#findIncludeFile(java.lang.String)
     */
    public IAstPreprocessorIncludeDirective findInclude(String fileName) {
        for (Iterator iter = allIncludes(); iter.hasNext();) {
            IAstPreprocessorIncludeDirective incl = (IAstPreprocessorIncludeDirective) iter.next();
            if (incl.getFilename().equalsIgnoreCase(fileName))
                return incl;
        }
        return null;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITrackedSourceFile#getIncludeFiles()
     */
    public IAstPreprocessorIncludeDirective[] getIncludeFiles() {
    	return (IAstPreprocessorIncludeDirective[]) getFileNodes(IAstPreprocessorIncludeDirective.class);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#getRlsSourceFiles()
     */
    public IAstRlsSourceFile[] getRlsSourceFiles() {
        List rlsFiles = new ArrayList();
        // all RLS files should be included with special nodes
        for (Iterator iter = rlsIncludes.iterator(); iter.hasNext();) {
            IAstPreprocessorRlsIncludeNode node = (IAstPreprocessorRlsIncludeNode) iter.next();
            if (node.getFile() != null)
                rlsFiles.add((IAstRlsSourceFile) node.getFile());
        }
        /*
        // look for stragglers amongst the full set of includes
        for (Iterator iter = allIncludes(); iter.hasNext();) {
            IAstPreprocessorIncludeDirective incl = (IAstPreprocessorIncludeDirective) iter.next();
            if (!rlsFiles.contains(incl.getFile()) && incl.getFile() instanceof IAstRlsSourceFile)
                rlsFiles.add(incl.getFile());
        }*/
        return (IAstRlsSourceFile[]) rlsFiles.toArray(new IAstRlsSourceFile[rlsFiles.size()]);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITrackedSourceFile#getMacroExpressions()
     */
    public IAstPreprocessorMacroExpression[] getMacroExpressions() {
        return (IAstPreprocessorMacroExpression[]) macroExprs.toArray(new IAstPreprocessorMacroExpression[macroExprs.size()]);
    }
    
    /**
     * Interleave the IAstNode nodes in the list like so:<p>
     * -- nodes with a known source range are ordered by start offset<br>
     * -- nodes with an unknown source range are ordered relative to their order in nodes
     * -- nodes whose source ranges point to another file are ordered relative to order in nodes
     * 
     * @param nodes list of IAstPreprocessorNode and IAstTopLevelNode s
     */
    private void interleave(final List nodes) {
        Collections.sort(nodes, new Comparator() {

            public int compare(Object o1, Object o2) {
                IAstNode n1 = (IAstNode) o1;
                IAstNode n2 = (IAstNode) o2;
                
                ISourceRange r1 = n1.getSourceRange();
                ISourceRange r2 = n2.getSourceRange();
                
                // if no source range, keep original list order
                if (r1 == null || r2 == null)
                    return nodes.indexOf(n1) - nodes.indexOf(n2);
                
                // if source range points to another file, keep original list order
                if (r1.getFile() != AstRssSourceFile.this 
                        || r2.getFile() != AstRssSourceFile.this)
                    return nodes.indexOf(n1) - nodes.indexOf(n2);
                
                // else, order by offset
                return r1.getOffset() - r2.getOffset();
                
            }});
    }

    /**
     * Take a list of text segments and add blank lines where 
     * appropriate, such as between newly generated categories.
     * Do not alter existing text (with source ranges).
     * @param nodes
     */
    private void addSpacing(final List nodes) {
        List prevKnownCategory = null;
        List prevCategory = null;
        List origNodes = new ArrayList(nodes);
        for (Iterator iter = origNodes.iterator(); iter.hasNext();) {
            IAstNode node = (IAstNode) iter.next();
            List category = getSpacingCategory(node);
            
            if (category == null) {
                if (false && prevCategory != null) {
                    //if (node.getSourceRange() == null) {
	                    // a comment or likewise; break before it
	                    int index = nodes.indexOf(node);
	                    if (index > 0)
	                        nodes.add(index, ISourceFormatter.SEGMENT_NEWLINE);
                    //}
                }
                prevKnownCategory = null;
            } else {
                if (prevKnownCategory != null 
                    && category != null
                    && prevKnownCategory != category) {
	                    // a category switch
                	//if (!nodeStartsWithNewline(node)) {
                		int index = nodes.indexOf(node);
                		nodes.add(index, ISourceFormatter.SEGMENT_NEWLINE);
                	//}
                }
                prevKnownCategory = category;
            }
            prevCategory = category;
        }    	
    }

    /**
     * Check whether the node's source starts with a newline.
	 * @param node
	 * @return
	 */
	boolean nodeStartsWithNewline(IAstNode node) {
		ISourceRange range = node.getExtendedRange();
		if (range == null)
			return false;
		
		String text = range.getText();
		if (text.length() == 0)
			return false;	// tho we should check the next one...
		
		char ch = text.charAt(0);
		return ch == '\r' || ch == '\n';
	}

	/* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#detachSource(com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile)
     */
    public void moveContents(IAstSourceFile aFile) {
        if (aFile instanceof IAstRssSourceFile) {
            IAstRssSourceFile file = (IAstRssSourceFile) aFile;
            IAstNode[] nodes = file.getFileNodes();
            for (int i = 0; i < nodes.length; i++) {
                if (nodes[i] instanceof IAstPreprocessorIncludeDirective) {
                    IAstPreprocessorIncludeDirective include = (IAstPreprocessorIncludeDirective) nodes[i];
                    // don't remove an include of this file
                    if (include.getFile() != null && include.getFile().getSourceFile() != getSourceFile()) {
                        file.removeFileNode(nodes[i]);
                        appendFileNode(nodes[i]);
                    }
                } else {
                    file.removeFileNode(nodes[i]);
                    appendFileNode(nodes[i]);
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile#removeAllContents()
     */
    public void removeAllContents() {
        fileNodes.clearItems();
        refresh();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#flatten()
     */
    public void flatten() {
        refresh();
        
        List files = new ArrayList();
        for (Iterator iter = fileNodes.iterator(); iter.hasNext();) {
            IAstNode node = (IAstNode) iter.next();
            if (node instanceof IAstPreprocessorIncludeDirective) {
                IAstPreprocessorIncludeDirective include = (IAstPreprocessorIncludeDirective) node;
                if (include.getFile() != null) {
                    include.getFile().flatten();
                    files.add(include.getFile());
                }
                iter.remove();
            }
        }
        for (Iterator iter = files.iterator(); iter.hasNext();) {
            IAstSourceFile file = (IAstSourceFile) iter.next();
            moveContents(file);
        }
        refresh();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#setReadOnlyRecursive(boolean)
     */
    public void setReadOnlyRecursive(boolean readOnly) {
        setReadOnly(readOnly);
        IAstPreprocessorIncludeDirective[] includes = getIncludeFiles();
        for (int i = 0; i < includes.length; i++) {
            IAstSourceFile asf = includes[i].getFile();
            if (asf instanceof IAstRssSourceFile) {
                ((IAstRssSourceFile)asf).setReadOnlyRecursive(readOnly);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#addRlsString(com.nokia.sdt.sourcegen.doms.rss.dom.IAstRlsString)
     */
    public void appendRlsString(IAstRlsString node) {
        appendFileNode(node);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#removeRlsString(com.nokia.sdt.sourcegen.doms.rss.dom.IAstRlsString)
     */
    public void removeRlsString(IAstRlsString node) {
        removeFileNode(node);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#findRlsString(java.lang.String)
     */
    public IAstRlsString findRlsString(String identifer) {
        for (Iterator iter = rlsStrings.iterator(); iter.hasNext();) {
            IAstRlsString string = (IAstRlsString) iter.next();
            if (string.getIdentifier().getName().equals(identifer))
                return string;
        }
        return null;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#getRlsStrings()
     */
    public IAstRlsString[] getRlsStrings() {
        return (IAstRlsString[]) rlsStrings.toArray(new IAstRlsString[rlsStrings.size()]);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#findRlsFile(int)
     */
    public IAstRlsSourceFile findRlsFile(int langCode) {
    	IAstRlsSourceFile[] files = getRlsSourceFiles();
    	for (int i = 0; i < files.length; i++) {
			if (files[i].getLanguageCode() == langCode)
				return files[i];
		}
    	return null;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#removeRlsFile(com.nokia.sdt.sourcegen.doms.rss.dom.IAstRlsSourceFile)
     */
    public void removeRlsSourceFile(IAstRlsSourceFile rlsFile) {
        for (Iterator iter = rlsIncludes.iterator(); iter.hasNext();) {
            IAstPreprocessorRlsIncludeNode node = (IAstPreprocessorRlsIncludeNode) iter.next();
            if (node.getFile() == rlsFile) {
            	fileNodes.removeItem(node);
            	iter.remove();
            	return;
            }
        }
    	Check.checkArg(false);
    }
    
}
