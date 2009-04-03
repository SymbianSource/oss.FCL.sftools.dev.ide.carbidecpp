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
package com.nokia.sdt.sourcegen.contributions.domains.cpp;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.sdt.sourcegen.SourceGenPlugin;
import com.nokia.sdt.sourcegen.SourceGenUtils;
import com.nokia.sdt.sourcegen.core.*;
import com.nokia.sdt.utils.*;

import org.eclipse.cdt.core.dom.CDOM;
import org.eclipse.cdt.core.dom.IASTServiceProvider.UnsupportedDialectException;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.model.*;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.MessageFormat;
import java.util.Arrays;

/**
 * Maintain information about the contents of a file we're
 * planning to modify
 * 
 * 
 *
 */
public class WorkspaceSourceManipulator implements ISourceManipulator {
    /** the context */
    SourceGenContext context;
    /** the project-relative path to the source file */
    private IPath path;
    /** the file */
    private IFile file;
    /** the parse tree */
    private IASTTranslationUnit tu;
    /** translation unit */
    private ITranslationUnit tunit;
    /** current text since last commit or load */
    private char[] currentText;
    /** original text from disk */
    private char[] origText;
    /** the buffer */
    private IBuffer buffer;
    /** working copy for modifying file: null until changes are attempted */
    private IWorkingCopy wc;
    
    /**
     * Load and parse information for the source file at 'path'
     * relative to the project the domain is registered for.
     * It is allowable for domain to have no project, in which
     * case this object is effectively disabled.
     * @param path
     */
    public WorkspaceSourceManipulator(SourceGenContext context, IPath path) {
        Check.checkArg(context);
        this.context = context;
        this.path = path;
        if (context.getProject() != null) {
        	IPath wsPath = FileUtils.getCanonicalWorkspacePath(new Path(context.getProject().getName()).append(path)).removeFirstSegments(1);
        	this.file = context.getProject() != null ? context.getProject().getFile(wsPath) : null;
        	this.path = this.file.getProjectRelativePath();
        } else {
        	this.file = null;
        }
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ISourceManipulator#getPath()
     */
    public IPath getPath() {
        return path;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ISourceManipulato#isLoaded()
     */
    public boolean isLoaded() {
        return origText != null;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ISourceManipulato#load()
     */
    public void load() throws CoreException {
        if (wc != null) {
            // need to revert if changes are present
            Check.checkState(false);
        }
        
        // already loaded?
        if (origText != null) {
            return;
        }
        
        if (tunit == null)
            getTunit();
        
        // prepare to read the file
        try {
            buffer = tunit.getBuffer();
            origText = buffer.getCharacters();
        } catch (CModelException e) {
            throw new CoreException(Logging.newStatus(SourceGenPlugin.getDefault(),
                    IStatus.ERROR,
                    MessageFormat.format(Messages.getString("WorkspaceSourceManipulator.CannotReadFile"), //$NON-NLS-1$
                    new Object[] { path.toString(), e.getLocalizedMessage() })));
        }
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ISourceManipulator#unload()
     */
    public void unload() throws CoreException {
        if (wc != null) {
            if (context.getCodeReaderFactory() instanceof WorkInProgressCodeReaderFactory) {
            	WorkInProgressCodeReaderFactory codeReaderFactory = (WorkInProgressCodeReaderFactory) context.getCodeReaderFactory();
            	codeReaderFactory.getCodeReaderCache().remove(file.getLocation().toOSString());
            }
            
            wc.destroy();
            tunit.close();
            tunit = null;
            currentText = null;
            wc = null;
            buffer = null;
            origText = null;
        }
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ISourceManipulato#revert()
     */
    public void revert() throws CoreException {
    	unload();
        load();
    }
    
    /**
     * 
     */
    private void getTunit() throws CoreException {
        // get the file from the project
        if (context.getProject() == null) {
            throw new CoreException(Logging.newStatus(SourceGenPlugin.getDefault(), 
                    IStatus.ERROR, 
                    Messages.getString("WorkspaceSourceManipulator.NoProjectOpen"))); //$NON-NLS-1$
        }
        
        if (file != null) {
            // get the resource for the file
            tunit = (ITranslationUnit)CoreModel.getDefault().create(file);
            if (tunit == null) {
                throw new CoreException(Logging.newStatus(SourceGenPlugin.getDefault(),
                        IStatus.ERROR,
                        MessageFormat.format(Messages.getString("WorkspaceSourceManipulator.NoCppProjectEntry"), //$NON-NLS-1$
                        new Object[] { path.toString() })));
            }
        } else {
            throw new CoreException(Logging.newStatus(SourceGenPlugin.getDefault(),
                    IStatus.ERROR,
                    MessageFormat.format(Messages.getString("WorkspaceSourceManipulator.FileDoesntExist"), //$NON-NLS-1$
                    new Object[] { file.getFullPath().toString() })));
        }
        
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ISourceManipulato#getPersistedText()
     */
    public char[] getPersistedText() {
        if (currentText != null) {
            return currentText;
        }
        if (origText != null)
        	return origText;
        return null;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ISourceManipulato#getParseTree()
     */
    public IASTTranslationUnit getParseTree() {
        if (tu != null)
            return tu;
        
        // CDT seems to require a project when using external accessors
        if (context.getProject() == null)
            return null;
        
        if (file == null)
            return null;

        try {
        	//System.out.print("retrieving DOM for " + file);
        	//long start = System.currentTimeMillis();
            tu = CDOM.getInstance().getASTService().getTranslationUnit( 
                    file,
                    context.getCodeReaderFactory(),
                     null
            );
            //long elapsed = System.currentTimeMillis() - start;
            //System.out.println("... " + elapsed + " ms");
        } catch (UnsupportedDialectException e1) {
        }
        return tu;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ISourceManipulator#getTranslationUnit()
     */
    public ITranslationUnit getTranslationUnit() {
    	return tunit;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ISourceManipulato#exists()
     */
    public boolean exists() {
        return (file != null && file.exists());
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ISourceManipulato#getBuffer()
     */
    public IBuffer getBuffer() throws CoreException {
        if (wc != null) {
            // this has already been opened for modification
            return buffer;
        }

        // create if missing
        if (context.resolveOrCreateFile(path)) {
            byte[] contents = getNewFileHeader(path.toFile());
            context.setFileContents(path, new ByteArrayInputStream(contents));
        }
        
        if (tunit == null)
            getTunit();

        try {
            wc = tunit.getWorkingCopy();
            buffer = wc.getBuffer();
        } catch (CModelException e) {
            throw new CoreException(Logging.newStatus(SourceGenPlugin.getDefault(), e));
        }
        
        return buffer;
    }

    /**
     * Get new file header, with contents formatted for the workspace
     * line endings.
	 * @param file
	 * @return
	 */
	private byte[] getNewFileHeader(File file) {
        String header = SourceGenUtils.getNewFileContents(file);
        String eol = context.getSourceFormatting().getEOL();
        header = TextUtils.canonicalizeNewlines(header, eol);
        byte[] contents = header.getBytes();
        return contents;
	}

	/* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ISourceManipulator#getTextReplacer()
     */
    public ITextReplacer getTextReplacer() throws CoreException {
        getBuffer();
        return new ITextReplacer() {
            public void replaceText(int offset, int length, String text) {
                buffer.replace(offset, length, text);
            }
        };
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ISourceManipulato#getFile()
     */
    public IFile getFile() {
        return file;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ISourceManipulato#changed()
     */
    public boolean changed() {
        if (wc == null)
            return false;
        try {
            //if (wc.hasUnsavedChanges())
            //   return true;
            
            // rescan
            //wc.reconcile();
            //wc.commit(false, null);

        	// never committed anything
            if (!buffer.hasUnsavedChanges() && currentText == null)
            	return false;
            
            // see if new text matches original
        	char[] newText = buffer.getCharacters();
        	if (Arrays.equals(origText, newText))
        		return false;
        	
            return wc.hasUnsavedChanges();
        } catch (CModelException e) {
            return false;
        }
        
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ISourceManipulato#getCurrentText()
     */
    public char[] getCurrentText() {
        if (buffer != null)
            return buffer.getCharacters();
        else
            return null;
        
    }
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ISourceManipulato#save()
     */
    public void save(IFileSaver fileSaver, IProgressMonitor monitor) throws CoreException {
        if (changed()) {
            //wc.reconcile();
            //wc.commit(false, monitor);
        	
            // go through this path so we can massage the output location
        	char[] newText = buffer.getCharacters();
            wc.destroy();
            fileSaver.saveFileText(file.getLocation().toFile(), file.getCharset(), newText);
            
            if (context.getCodeReaderFactory() instanceof WorkInProgressCodeReaderFactory) {
            	WorkInProgressCodeReaderFactory codeReaderFactory = (WorkInProgressCodeReaderFactory) context.getCodeReaderFactory();
            	codeReaderFactory.getCodeReaderCache().remove(file.getLocation().toOSString());
            }

            tunit.close();
            tunit = null;
            currentText = null;
            wc = null;
            buffer = null;
            origText = null;
            tu = null;
        }
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ISourceManipulato#commit()
     */
    public void commit() throws CoreException {
        // the "persisted" text is the current text 
        currentText = buffer.getCharacters();
        if (wc != null) {
            if (context.getCodeReaderFactory() instanceof WorkInProgressCodeReaderFactory) {
            	WorkInProgressCodeReaderFactory codeReaderFactory = (WorkInProgressCodeReaderFactory) context.getCodeReaderFactory();
            	codeReaderFactory.replaceFileContents(file.getLocation().toOSString(), currentText);
            } else {
                try {
                    wc.reconcile();
                    // later: we can get away with this with our own working copy code reader (above, getParseTree())
                    //wc.commit(false, new NullProgressMonitor());
                } catch (CModelException e) {
                    throw new CoreException(Logging.newStatus(SourceGenPlugin.getDefault(), e));
                }
            }
            
        	//tunit = null;
        	//getTunit();
        }
        
        tu = null;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ISourceManipulato#create()
     */
    public void create() throws CoreException {
        if (file != null && !file.exists()) {
        	IPath path = file.getProjectRelativePath();
            context.resolveOrCreateFile(path);
            byte[] contents = getNewFileHeader(path.toFile()); 
            context.setFileContents(path, new ByteArrayInputStream(contents));
        }
    }
}