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

import com.nokia.sdt.sourcegen.SourceGenPlugin;
import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.sdt.sourcegen.core.SourceGenContext;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.utils.IFileSaver;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage;
import org.eclipse.cdt.core.dom.parser.cpp.GPPScannerExtensionConfiguration;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.core.parser.CodeReader;
import org.eclipse.cdt.core.parser.ExtendedScannerInfo;
import org.eclipse.cdt.core.parser.IParserLogService;
import org.eclipse.cdt.core.parser.IScannerInfo;
import org.eclipse.cdt.core.parser.IScannerInfoProvider;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.MessageFormat;
import java.util.HashMap;

/**
 * This source manipulator works without a project.
 * Well, actually, it doesn't work at all.  Something is weird here
 * and CDT manages to parse only part of any file.
 * 
 * 
 */
public class StandaloneSourceManipulator implements ISourceManipulator {
    protected static final GPPScannerExtensionConfiguration CPP_GNU_SCANNER_EXTENSION = new GPPScannerExtensionConfiguration();

    /** the context */
    SourceGenContext context;
    /** the project-relative path to the source file */
    private IPath path;
    /** the file */
    private File file;
    /** the parse tree */
    private IASTTranslationUnit tu;
    /** original text */
    private String origText;
    /** the buffer */
    private StringBuffer buffer;
    private boolean bufferChanged;
    
    /**
     * Load and parse information for the source file at 'path'
     * relative to the project the domain is registered for.
     * It is allowable for domain to have no project, in which
     * case this object is effectively disabled.
     * @param path
     */
    public StandaloneSourceManipulator(SourceGenContext context, IPath path) {
        Check.checkArg(context);
        this.context = context;
        this.path = path;
        this.file = new File(context.getEffectiveBaseDir(), path.toString());
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ISourceManipulator#getPath()
     */
    public IPath getPath() {
        return path;
        
    }
    /**
     * Tell if the file is loaded
     */
    public boolean isLoaded() {
        return origText != null;
    }
    
    /**
     * Load the file into memory 
     */
    public void load() throws CoreException {
        if (buffer != null) {
            // need to revert if changes are present
            Check.checkState(false);
        }
        
        // already loaded?
        if (origText != null)
            return;
        
        // read the file
        try {
            StringBuffer sb = new StringBuffer();
            Reader reader = new InputStreamReader(
                    new FileInputStream(file), "UTF-8"); //$NON-NLS-1$
            char[] buf = new char[1024];
            int len;
            while ((len = reader.read(buf)) > 0) {
                sb.append(buf, 0, len);
            }
            reader.close();
            origText = sb.toString();
        } catch (IOException e) {
            throw new CoreException(Logging.newStatus(SourceGenPlugin.getDefault(),
                    IStatus.ERROR,
                    MessageFormat.format(Messages.getString("StandaloneSourceManipulator.CannotReadFile"), //$NON-NLS-1$
                    new Object[] { path.toString(), e.getLocalizedMessage() })));
        }
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ISourceManipulator#unload()
     */
    public void unload() throws CoreException {
        buffer = null;
        origText = null;
    }
    
    /**
     * Revert any current changes and reload
     * @throws CoreException
     */
    public void revert() throws CoreException {
    	unload();
        load();
    }
    
    /**
     * Get the persisted file text, if loaded
     * @return text or null if not on disk
     * @see #load()
     */
    public char[] getPersistedText() {
        if (origText != null) {
            return origText.toCharArray();
        }
        return null;
    }

    /** Get the file's parse tree
     * 
     * @return IASTTranslationUnit or null if file cannot be parsed (message logged)
     */
    public IASTTranslationUnit getParseTree() {
        if (tu != null)
            return tu;
        
        if (file == null)
            return null;
        
        IScannerInfo scanInfo = null;
        if (context.getProject() != null) {
            IScannerInfoProvider provider = CCorePlugin.getDefault().getScannerInfoProvider(context.getProject());
            if (provider != null) {
                IFile resource = context.getProject().getFile(path);
                IScannerInfo buildScanInfo = provider.getScannerInformation(
                        resource != null ? (IResource)resource : (IResource)context.getProject());
                if (buildScanInfo != null)
                    scanInfo = new ScannerInfo(buildScanInfo.getDefinedSymbols(), buildScanInfo.getIncludePaths());
            }
        }
        if (scanInfo == null)
            scanInfo = new ExtendedScannerInfo(new HashMap(), new String[] { "."}); //$NON-NLS-1$
        
        CodeReader reader = context.getCodeReaderFactory().createCodeReaderForTranslationUnit(file.getAbsolutePath());
        if( reader == null )
            return null;
        IParserLogService log;
        
        //log = ParserUtil.getParserLogService();
        log = new IParserLogService() {

            public void traceLog(String message) {
                System.out.println(message);
            }

            public boolean isTracing() {
                return true;
            }
            
        };
        
        try {
			tu = GPPLanguage.getDefault().getASTTranslationUnit(reader, scanInfo, context.getCodeReaderFactory(), null, log);
		} catch (CoreException e) {
			e.printStackTrace();
			SourceGenPlugin.getDefault().log(e);
		}
        return tu;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ISourceManipulator#getTranslationUnit()
     */
    public ITranslationUnit getTranslationUnit() {
    	return null;
    }
    
    /** Tell whether the file exists on disk */
    public boolean exists() {
        return file != null && file.exists();
        //return tunit != null && tunit.exists();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ISourceManipulator#getTextReplacer()
     */
    public ITextReplacer getTextReplacer() throws CoreException {
        if (buffer == null) {
            buffer = new StringBuffer(origText != null ? origText : ""); //$NON-NLS-1$
            bufferChanged = false;
        }
        return new ITextReplacer() {
            public void replaceText(int offset, int length, String text) {
                bufferChanged = true;
                Check.checkState(buffer != null);
                buffer.replace(offset, offset + length, text);
            }
        };
    }

    /**
     * @return true if file has a working copy
     */
    public boolean changed() {
        return buffer != null && bufferChanged;
    }

    /**
     * Return the new text -- the persisted text if the 
     * file was loaded, the modified text if the file has
     * a working copy, or null
     * @see #load()
     * @see #getPersistedText()
     */
    public char[] getCurrentText() {
        if (buffer != null)
            return buffer.toString().toCharArray();
        else if (origText != null)
            return origText.toCharArray();
        else
            return null;
        
    }
    /** Save the file if is has changed */
    public void save(IFileSaver fileSaver, IProgressMonitor monitor) throws CoreException {
        if (changed()) {
            try {
                context.resolveOrCreateFile(path);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(buffer.toString().getBytes());
                fos.close();
            } catch (IOException e) {
                throw new CoreException(Logging.newStatus(SourceGenPlugin.getDefault(), e));
            }
            origText = buffer.toString();
            buffer = null;
            tu = null;
        }
    }

    /**
     *  Reconcile changes to working copy 
     */
    public void commit() throws CoreException {
        // the "persisted" text is the current text 
        if (buffer != null) {
            origText = buffer.toString();
            buffer = null;
        }
        tu = null;
    }

    /**
     * 
     */
    public void create() throws CoreException {
        context.resolveOrCreateFile(path);
    }
    
}