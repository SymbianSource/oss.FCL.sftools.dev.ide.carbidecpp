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

package com.nokia.sdt.sourcegen.doms.rss.dom;

import com.nokia.sdt.sourcegen.ISourceFormatter;
import com.nokia.sdt.sourcegen.core.ISourceFile;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorDefineDirective;

import java.util.List;

/**
 * This represents the sum total of declarations, definitions,
 * and localized strings provided by a translation unit
 * (a main file and all its includes).
 * <p>
 * Some files in the TU are rewritable (i.e. changes to their
 * IAstNodes can be regenerated as source and written back to disk).
 * For such files, their IAstSourceFile representative is a
 * subinterface that holds onto these nodes and implements 
 * rewrite() to update the text for an ISourceFile.
 * <p>
 * For the case of read-only sources or for "compiler-generated"
 * definitions, nodes can be added to the translation unit's
 * scope without affecting generated source by writing to
 * #getLocalSourceFile().
 * 
 * 
 *
 */
public interface ITranslationUnit {
    /** 
     * Get the main source file 
     * @return ISourceFile 
     */
    public IAstSourceFile getSourceFile();

    /** 
     * Set the main source file 
     */
    public void setSourceFile(IAstSourceFile file);

    /**
     * Mark the translation unit dirty when a child AstRssSourceFile
     * has been modified.
     * 
     */
    public void markDirty();
    
    /**
     * Refresh the translation unit when the toplevel declarations
     * or macros in the main file or any of its includes are known to 
     * have changed.
     * (This automatically happens on #setSourceFile(IAstSourceFile) ) 
     * <p>
     * This is an alternative to the design where every operation
     * on IAstSourceFile is replicated here...
     */
    public void refresh();

    /**
     * Get the set of unique files referenced in the translation unit.
     * These are ISourceFile entries because multiple IAstSourceFiles
     * may reference the same ISourceFile. 
     */
    public ISourceFile[] getIncludedFileSet();

    /**
     * Get the set of files contributions from #includes in the translation 
     * unit.  These are provided in depth-first traversal order.
     * Multiple entries may reference the same ISourceFile,
     * and due to include guards and macro magic, others may have 
     * differing contents. 
     */
    public IAstSourceFile[] getIncludedFiles();

    /**
     * Find all the nodes which represent includes of this file.
     * There may be duplicates (due to #includes of the same file
     * inside various headers) but we expect only the first to
     * have any real contents.
     */
    public IAstSourceFile[] findIncludedFiles(ISourceFile file);

    /**
     * Tell whether the given file was ever included.
     * @param path full path to file
     * @return ISourceFile for included file, or null
     */
    public IAstSourceFile findIncludedFile(String path);
    
    /**
     * Get all the #define directives in the translation unit
     */
    public IAstPreprocessorDefineDirective[] getDefines();
    
    /**
     * Look up a macro by name.  The last one defined in any
     * source file is returned.
     */
    public IAstPreprocessorDefineDirective findDefine(String name);
    
    /** Get all the top-level nodes (structs, resources, enums) 
     * defined in any of the source files or stored in this TU */
    public IAstTopLevelNode[] getTopLevelNodes();

    /** Get all the declarations anywhere in the TU */
    public IAstDeclaration[] getDeclarations();

    /** Get all the resource definitions anywhere in the TU */
    public IAstResourceDefinition[] getResourceDefinitions();

    /** Find a STRUCT declaration anywhere in the TU by name */
    public IAstStructDeclaration findStructDeclaration(String name);

    /** Find an ENUM declaration anywhere in the TU by name */
    public IAstEnumDeclaration findEnumDeclaration(String name);

    /** Find an enumerator declaration anywhere in the TU by name */
    public IAstEnumerator findEnumerator(String name);

    /** Find a RESOURCE definition anywhere in the TU by name */
    public IAstResourceDefinition findResourceDefinition(String name);

    /** Find a RESOURCE definition anywhere in the TU with the given type */
    public IAstResourceDefinition findResourceDefinitionOfType(String type);

    /**
     * Find the file contributing a node
     * @param node
     * @return IAstSourceFile, or null if the node comes from the TU
     * @throws AssertionError if the node is nowhere in the TU 
     */
    public IAstSourceFile findTopLevelNode(IAstTopLevelNode node);
    
    /**
     * Get the "local" source file.
     * This is a virtual source file which is never written to disk. 
     * It can be used to add "compiler-defined" macros/declarations/etc.
     */
    public IAstSourceFile getLocalSourceFile();

    /**
     * Set the "local" source file.
     * This is a virtual source file which is never written to disk. 
     * It can be used to add "compiler-defined" macros/declarations/etc.
     */
    public void setLocalSourceFile(IAstSourceFile file);

    /**
     * Flatten the source to remove all #includes
     * (for testing)
     */
    public void flatten();
    
    /** 
     * Rewrite the translation unit
     * <p> 
     * This iterates the main file and all the includes
     * and updates their file text.  It does <i>not</i> write
     * anything to disk.
     * @return list of changed IAstSourceFiles
     */
    public List rewrite(ISourceFormatter formatter);

	/**
	 * Add a source file which is included in the universe
	 * of declarations for the TU but which is not directly
	 * #included.
	 * @param srcFile
	 */
	public void addAuxiliarySourceFile(IAstSourceFile srcFile);

	/**
	 * Tell if a file is auxiliary
	 * @param astSourceFile
	 * @return
	 */
	public boolean isAuxiliaryFile(IAstSourceFile srcFile);
	
	/**
	 * Get the list of editable files.
	 */
	public IAstSourceFile[] getEditableFiles();
    
}
