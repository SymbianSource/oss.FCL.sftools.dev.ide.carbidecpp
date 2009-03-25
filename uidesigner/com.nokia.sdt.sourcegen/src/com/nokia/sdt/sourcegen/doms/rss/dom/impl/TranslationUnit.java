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
import com.nokia.sdt.sourcegen.core.ISourceFile;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorDefineDirective;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorIncludeDirective;
import com.nokia.cpp.internal.api.utils.core.Check;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * This class caches the contributions from all the ISourceFiles
 * 
 * 
 *
 */
public class TranslationUnit implements ITranslationUnit {

    /** The main file */
    private IAstSourceFile sourceFile;
    /** Local decls */
    private IAstSourceFile localSource;

    /** Auxiliary files */
    private List<IAstSourceFile> auxFiles;
    
    /** List of IAstSourceFile included throughout TU, including #includes and auxiliary files */
    private List includeFiles;
    
    /** List of IAstPreprocessorDefineDirective throughout TU (cached) */
    private List macros;
    /** Map of String -> IAstPreprocessorMacroExpression throughout TU (cached) */
    private Map macroMap;
    /** List of IAstTopLevelNode throughout TU (cached) */
    private List topLevelNodes;

    /** Flag indicating whether following caches need to be regenerated */
    private boolean declCachesDirty;
    /** List of IAstDeclaration throughout TU (cached) */
    private List declarations;
    /** Map of String -> IAstStructDeclaration throughout TU (cached) */
    private Map structDeclarations;
    /** Map of String -> IAstResourceDefinition throughout TU (cached) */
    private Map resourceDefinitions;
    /** Map of String -> IAstEnumDeclaration throughout TU (cached)
     * <p>
     * Note: unnamed declarations are present too, with unique keys 
     */
    private Map enumDeclarations;
    private int uniqueId;

    public TranslationUnit(IAstSourceFile mainFile) {
        this.macros = new ArrayList();
        this.macroMap = new LinkedHashMap();
        this.includeFiles = new ArrayList();
        this.auxFiles = new ArrayList<IAstSourceFile>();
        this.topLevelNodes = new ArrayList();
        this.declarations = new ArrayList();
        this.structDeclarations = new LinkedHashMap();
        this.resourceDefinitions = new LinkedHashMap();
        this.enumDeclarations = new LinkedHashMap();
        setSourceFile(mainFile);
        refresh();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "AstTranslationUnit { sourceFile="+sourceFile.getSourceFile()+ " }"; //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    /** Iterate over the source file and includes (IAstSourceFile) */ 
    class FilesIterator implements Iterator {
        private boolean gotLocal;
        private boolean gotMain;
        private Iterator srcIter;

        /** Make an iterator over the source file and includes (IAstSourceFile) */ 
        public FilesIterator() {
            gotLocal = localSource == null;
            gotMain = false;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public boolean hasNext() {
            return !gotLocal ||!gotMain || srcIter.hasNext();
        }

        public Object next() {
            if (!gotLocal) {
                gotLocal = true;
                return localSource;
            }
            if (!gotMain) {
                gotMain = true;
                srcIter = includeFiles.iterator();
                return sourceFile;
            }
            return srcIter.next();
        }
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#markDirty()
     */
    public void markDirty() {
        declCachesDirty = true;
    }
    
    /*
     *  (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#refresh()
     */
    public void refresh() {

        // clear caches
        uniqueId = 0;
        topLevelNodes.clear();
        macros.clear();
        macroMap.clear();
        includeFiles.clear();

        // find all the referenced files
        Stack<ISourceFile> includeStack = new Stack<ISourceFile>();
        if (localSource != null)
            gatherIncludes(includeStack, includeFiles, localSource);
        gatherIncludes(includeStack, includeFiles, sourceFile);
        // anything directly #included is no longer auxiliary
        auxFiles.removeAll(includeFiles);
        includeFiles.addAll(auxFiles);

        // gather contributions from all the tracked files
        for (Iterator iter = new FilesIterator(); iter.hasNext();) {
            IAstSourceFile file = (IAstSourceFile) iter.next();
            
            // don't assume anything about the way a source file
            // manages its contents
            IAstNode[] kids = file.getFileNodes();
            for (int i = 0; i < kids.length; i++) {
                if (kids[i] instanceof IAstTopLevelNode)
                    topLevelNodes.add(kids[i]);
                if (kids[i] instanceof IAstPreprocessorDefineDirective) {
                    IAstPreprocessorDefineDirective define = (IAstPreprocessorDefineDirective) kids[i];
                    macros.add(define);
                    macroMap.put(define.getMacro().getName(), define);
                }
                if (kids[i] instanceof IAstSourceFile) {
                    gatherIncludes(includeStack, includeFiles, (IAstSourceFile)kids[i]);
                }
            }
        }
        
        // now cache special kinds of toplevel nodes for lookups
        resourceDefinitions.clear();
        structDeclarations.clear();
        declarations.clear();
        enumDeclarations.clear();
        for (Iterator iter = topLevelNodes.iterator(); iter.hasNext();) {
            IAstTopLevelNode node = (IAstTopLevelNode) iter.next();

            if (node instanceof IAstDeclaration) {
                declarations.add(node);
            }

            if (node instanceof IAstResourceDefinition) {
                IAstResourceDefinition res = (IAstResourceDefinition) node;
                if (res.getName() != null) {
                    resourceDefinitions.put(res.getName().getName(), res);
                }
            }
            else if (node instanceof IAstStructDeclaration) {
                IAstStructDeclaration decl = (IAstStructDeclaration) node;
                if (decl.getStructName() != null) {
                    structDeclarations.put(decl.getStructName().getName(), decl);
                }
            }
            else if (node instanceof IAstEnumDeclaration) {
                IAstEnumDeclaration enm = (IAstEnumDeclaration) node;
                String name;
                if (enm.getName() != null)
                    name = enm.getName().getName();
                else
                    name = makeUniqueKey();
                enumDeclarations.put(name, enm);
            }
        }
    
        declCachesDirty = false;
    }

    /**
     * Make a unique string as a key for unnamed namespaces
     */
    private String makeUniqueKey() {
        uniqueId++;
        return " @@uniquekey@@" + uniqueId; //$NON-NLS-1$
    }

    /**
     * Recursively gather all the #includes in depth-first traversal order.
     * 
     * @param includes
     * @param file
     */
    private void gatherIncludes(Stack<ISourceFile> includeStack, List includes, IAstSourceFile file) {
        // don't assume much about the way a source file
        // manages its contents
        IAstNode[] kids = file.getFileNodes();
        for (int i = 0; i < kids.length; i++) {
            if (kids[i] instanceof IAstPreprocessorIncludeDirective) {
                IAstPreprocessorIncludeDirective incl = (IAstPreprocessorIncludeDirective) kids[i];
                if (incl.getFile() != null) {
                	Check.checkState(incl.getFile() != getSourceFile());
                	if (!includeStack.contains(incl.getFile().getSourceFile())) {
                		includeStack.push(incl.getFile().getSourceFile());
                		includeFiles.add(incl.getFile());
                		gatherIncludes(includeStack, includes, incl.getFile());
                		includeStack.pop();
                	}
                }
            } else if (kids[i] instanceof IAstSourceFile) {
                IAstSourceFile asf = (IAstSourceFile) kids[i];
            	if (!includeStack.contains(asf.getSourceFile())) {
            		includeStack.push(asf.getSourceFile());
            		includeFiles.add(asf);
            		gatherIncludes(includeStack, includes, asf);
            		includeStack.pop();
            	}
            }
        }
        
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#getSourceFile()
     */
    public IAstSourceFile getSourceFile() {
        return sourceFile;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#setSourceFile(com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile)
     */
    public void setSourceFile(IAstSourceFile file) {
        Check.checkArg(file);
        Check.checkArg(file.getSourceFile());
        this.sourceFile = file;
        file.setTranslationUnit(this);
    }

  /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#getIncludedFileSet()
     */
    public ISourceFile[] getIncludedFileSet() {
        if (declCachesDirty)
            refresh();
        Set list = new LinkedHashSet();
        for (Iterator iter = includeFiles.iterator(); iter.hasNext();) {
            IAstSourceFile file = (IAstSourceFile) iter.next();
            ISourceFile sfile = file.getSourceFile();
            list.add(sfile);
        }
        return (ISourceFile[]) list.toArray(new ISourceFile[list.size()]);
    }


    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#getIncludedFiles()
     */
    public IAstSourceFile[] getIncludedFiles() {
        if (declCachesDirty)
            refresh();

        return (IAstSourceFile[]) includeFiles.toArray(new IAstSourceFile[includeFiles.size()]);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#findIncludedFile(com.nokia.sdt.sourcegen.doms.rss.dom.ISourceFile)
     */
    public IAstSourceFile[] findIncludedFiles(ISourceFile file) {
        if (declCachesDirty)
            refresh();

        List matches = new ArrayList();
        for (Iterator iter = includeFiles.iterator(); iter.hasNext();) {
            IAstSourceFile srcfile = (IAstSourceFile) iter.next();
            if (srcfile.getSourceFile() == file)
                matches.add(srcfile);
        }
        return (IAstSourceFile[]) matches.toArray(new IAstSourceFile[matches.size()]);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#findIncludedFile(java.lang.String)
     */
    public IAstSourceFile findIncludedFile(String path) {
        if (declCachesDirty)
            refresh();

        File pathFile = new File(path);
        try {
            pathFile = pathFile.getCanonicalFile();
        } catch (IOException e) {
            
        }
        for (Iterator iter = includeFiles.iterator(); iter.hasNext();) {
            IAstSourceFile srcfile = (IAstSourceFile) iter.next();
            if (pathFile.equals(srcfile.getSourceFile().getFile()))
                    return srcfile;
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#getMacros()
     */
    public IAstPreprocessorDefineDirective[] getDefines() {
        return (IAstPreprocessorDefineDirective[]) macros.toArray(new IAstPreprocessorDefineDirective[macros.size()]);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#findMacro(java.lang.String)
     */
    public IAstPreprocessorDefineDirective findDefine(String name) {
        return (IAstPreprocessorDefineDirective)macroMap.get(name);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#getTopLevelNodes()
     */
    public IAstTopLevelNode[] getTopLevelNodes() {
        if (declCachesDirty)
            refresh();
        return (IAstTopLevelNode[]) topLevelNodes.toArray(new IAstTopLevelNode[topLevelNodes.size()]);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#getDeclarations()
     */
    public IAstDeclaration[] getDeclarations() {
        if (declCachesDirty)
            refresh();
        return (IAstDeclaration[]) declarations.toArray(new IAstDeclaration[declarations.size()]);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#getResourceDefinitions()
     */
    public IAstResourceDefinition[] getResourceDefinitions() {
        if (declCachesDirty)
            refresh();
        return (IAstResourceDefinition[]) resourceDefinitions.values().toArray(new IAstResourceDefinition[resourceDefinitions.size()]);
    }

    /**
     * Locate the source file that contains the given declaration
     * @param node
     * @return the file containing the node, or null
     */
    public IAstSourceFile findTopLevelNode(IAstTopLevelNode node) {
        // if no dirty source, the node's source range should still point
        // to the definition file
        if (!node.hasDirtySourceTree()) {
            for (Iterator iter = new FilesIterator(); iter.hasNext();) {
                IAstSourceFile file = (IAstSourceFile) iter.next();
                if (file.getSourceFile() == node.getSourceRange().getFile())
                    return file;
            }
        }
        
        // else, search through each of our files for the one
        // that owns the node
        for (Iterator iter = new FilesIterator(); iter.hasNext();) {
            IAstSourceFile file = (IAstSourceFile) iter.next();
            if (file instanceof IAstRssSourceFile) {
                IAstRssSourceFile rssfile = (IAstRssSourceFile) file;
                if (rssfile.hasTopLevelNode(node))
                    return file;
            }
        }
        
        // node comes from nowhere!
        throw new AssertionError(node.toString());
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#findEnumDeclaration(java.lang.String)
     */
    public IAstEnumDeclaration findEnumDeclaration(String name) {
        Check.checkArg(name);
        if (declCachesDirty)
            refresh();
        return (IAstEnumDeclaration) enumDeclarations.get(name);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#findEnumerator(java.lang.String)
     */
    public IAstEnumerator findEnumerator(String name) {
        Check.checkArg(name);
        if (declCachesDirty)
            refresh();
        for (Iterator iter = enumDeclarations.values().iterator(); iter.hasNext();) {
            IAstEnumDeclaration enm = (IAstEnumDeclaration) iter.next();
            IAstEnumerator enr = enm.findEnumerator(name);
            if (enr != null)
                return enr;
        }
        return null;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#findStructDeclaration(java.lang.String)
     */
    public IAstStructDeclaration findStructDeclaration(String name) {
        Check.checkArg(name);
        if (declCachesDirty)
            refresh();
        return (IAstStructDeclaration) structDeclarations.get(name);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#findResourceDefinition(java.lang.String)
     */
    public IAstResourceDefinition findResourceDefinition(String name) {
        Check.checkArg(name);
        if (declCachesDirty)
            refresh();
        return (IAstResourceDefinition) resourceDefinitions.get(name);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#findResourceDefinitionOfType(java.lang.String)
     */
    public IAstResourceDefinition findResourceDefinitionOfType(String type) {
        Check.checkArg(type);
        if (declCachesDirty)
            refresh();
        for (Iterator iter = resourceDefinitions.values().iterator(); iter.hasNext();) {
            IAstResourceDefinition def = (IAstResourceDefinition) iter.next();
            if (def.getStructType().getStructName().getName().equals(type))
                return def;
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#flatten()
     */
    public void flatten() {
        sourceFile.flatten();
        if (localSource != null) {
            localSource.flatten();
            sourceFile.moveContents(localSource);
        }
    }


    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#rewrite()
     */
    public List rewrite(ISourceFormatter formatter) {
        refresh();

        List changed = new ArrayList();
        
        Set<ISourceFile> touchedFiles = new HashSet<ISourceFile>();
        for (Iterator iter = new FilesIterator(); iter.hasNext();) {
            IAstSourceFile file = (IAstSourceFile) iter.next();
            if (touchedFiles.contains(file.getSourceFile()))
            	continue;
            if (file.rewrite(formatter)) {
                changed.add(file);
                touchedFiles.add(file.getSourceFile());
            }
        }
        return changed;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#getLocalSourceFile()
     */
    public IAstSourceFile getLocalSourceFile() {
        return localSource;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#setLocalSourceFile(com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile)
     */
    public void setLocalSourceFile(IAstSourceFile file) {
        Check.checkArg(file);
        Check.checkArg(file.getSourceFile() == null);
        this.localSource = file;
        file.setTranslationUnit(this);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#addAuxiliarySourceFile(com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile)
     */
    public void addAuxiliarySourceFile(IAstSourceFile srcFile) {
    	Check.checkArg(!auxFiles.contains(srcFile));
    	Check.checkArg(srcFile.getParent() == null);
    	for (Iterator iter = auxFiles.iterator(); iter.hasNext();) {
			IAstSourceFile file = (IAstSourceFile) iter.next();
			Check.checkArg(!file.equals(srcFile.getSourceFile()));
		}
    	auxFiles.add(srcFile);
    	markDirty();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#isAuxiliaryFile(com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile)
     */
    public boolean isAuxiliaryFile(IAstSourceFile srcFile) {
    	return auxFiles.contains(srcFile);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#getEditableFiles()
     */
    public IAstSourceFile[] getEditableFiles() {
        refresh();

        Set<IAstSourceFile> editable = new HashSet<IAstSourceFile>();
        
        for (Iterator iter = new FilesIterator(); iter.hasNext();) {
            IAstSourceFile file = (IAstSourceFile) iter.next();
            if (!file.isReadOnly() && !isAuxiliaryFile(file)) {
                editable.add(file);
            }
        }
        return (IAstSourceFile[]) editable.toArray(new IAstSourceFile[editable.size()]);
    }
    
}
