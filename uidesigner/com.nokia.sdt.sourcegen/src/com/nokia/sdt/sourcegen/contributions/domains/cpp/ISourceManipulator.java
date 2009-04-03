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

import com.nokia.sdt.utils.IFileSaver;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.runtime.*;

/**
 * 
 *
 */
public interface ISourceManipulator {

    /**
     * Tell if the file is loaded
     */
    boolean isLoaded();

    /**
     * Load the file into memory 
     */
    void load() throws CoreException;

    /**
     * Throw away any changes.  Marks the file unloaded.
     */
    void unload() throws CoreException;
    
    /**
     * Revert any current changes and reload
     * @throws CoreException
     */
    void revert() throws CoreException;

    /**
     * Get the persisted file text, if loaded
     * @return text or null if not on disk
     * @see #load()
     */
    char[] getPersistedText();

    /** Get the file's parse tree (DOM)
     * 
     * @return IASTTranslationUnit or null if file cannot be parsed (message logged)
     */
    IASTTranslationUnit getParseTree();

	/**
	 * Get the translation unit (CModel).
	 * This should only be called after #load().
	 * @return
	 * @throws CoreException 
	 */
	ITranslationUnit getTranslationUnit();

    /** Tell whether the file exists on disk */
    boolean exists();

    /** Prepare the source for modification 
     * 
     * @return buffer
     * @throws CoreException
     */
   // IBuffer getBuffer() throws CoreException;

    /**
     * @return true if file has a working copy
     */
    boolean changed();

    /**
     * Return the new text -- the persisted text if the 
     * file was loaded, the modified text if the file has
     * a working copy, or null
     * @see #load()
     * @see #getPersistedText()
     */
    char[] getCurrentText();

    /**
     * Get an object which allows us to modify text
     */
    ITextReplacer getTextReplacer() throws CoreException;
    

    /**
     *  Reconcile changes to working copy 
     */
    void commit() throws CoreException;

    /** Save the file if is has changed 
     * @param fileSaver
     * @param monitor */
    void save(IFileSaver fileSaver, IProgressMonitor monitor) throws CoreException;


    /**
     *  Create the file if missing 
     */
    void create() throws CoreException;

    /**
     * Get the project-relative path
     */
    IPath getPath();
}