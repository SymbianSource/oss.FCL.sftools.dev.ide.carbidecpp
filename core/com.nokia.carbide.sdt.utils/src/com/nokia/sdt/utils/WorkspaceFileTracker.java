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
package com.nokia.sdt.utils;

import org.eclipse.core.filebuffers.manipulation.ContainerCreator;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.Logging;

import java.io.*;

public class WorkspaceFileTracker implements IFileTracker {

    private IWorkspaceRoot root;

	/**
     * Create a file tracker for files owned by the current
     * project.
     */
    public WorkspaceFileTracker() {
    	root = ResourcesPlugin.getWorkspace().getRoot();

    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.IFileTracker#loadFileText(org.eclipse.core.runtime.IPath)
     */
    public char[] loadFileText(File file) throws CoreException {
        IFile wsFile = FileUtils.convertFileToIFile(file);
        
        StringBuffer buffer = new StringBuffer();
        InputStream is = null;
        try {
            // it's possible to have a workspace relatvie path for a file that
        	// doesn't exist.  this can happen if they try to import a bld.inf
            // that physically exists under the workspace root directory in the
        	// file system.
            if (wsFile != null && wsFile.exists()) {
            	try {
            		is = wsFile.getContents();
            	} catch (CoreException e) {
            		// bug 5105: most likely, the file is out-of-sync; try refreshing and reading again.
            		// Allow any continuing or unresolved CoreException to propagate.
            		wsFile.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());
            		is = wsFile.getContents();
            	}
            } 
            if (is == null) {
                is = new FileInputStream(file);
            }
            
            try {
	            Reader reader;
	            try {
	                if (wsFile != null)
	                    reader = new InputStreamReader(is, wsFile.getCharset());
	                else
	                    reader = new InputStreamReader(is);
	            } catch (UnsupportedEncodingException e) {
	                throw new CoreException(Logging.newStatus(UtilsPlugin.getDefault(), e));
	            }
	        
	            char buff[] = new char[8192];
	            int len;
	            while ((len = reader.read(buff)) > 0) {
	                buffer.append(buff, 0, len);
	            }
	            reader.close();
            } finally {
            	is.close();
            }
        } catch (IOException e) {
            throw new CoreException(Logging.newStatus(UtilsPlugin.getDefault(), e));
        }
        
        return buffer.toString().toCharArray();
        
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.IFileTracker#saveFileText(org.eclipse.core.runtime.IPath, char[])
     */
    public void saveFileText(File file, String charSet, char[] text) throws CoreException {
        // the file may or may not be in the workspace

        IFile wsFile = FileUtils.convertFileToIFile(file);
        if (wsFile != null && !wsFile.getParent().exists()) {
        	new ContainerCreator(root.getWorkspace(), 
        			wsFile.getParent().getFullPath()).createContainer(new NullProgressMonitor());
        }
        
        byte[] bytes;
        try {
            if (wsFile != null && charSet == null)
                bytes = new String(text).getBytes(wsFile.getCharset(true));
            else if (charSet != null)
                bytes = new String(text).getBytes(charSet);
            else
            	bytes = new String(text).getBytes();
        } catch (UnsupportedEncodingException e) {
            throw new CoreException(Logging.newStatus(UtilsPlugin.getDefault(), e));
        }
        if (wsFile != null) {
            InputStream is = new ByteArrayInputStream(bytes);
            if (!wsFile.exists()) {
                wsFile.create(is, IResource.NONE, null);
            } else {
            	// avoid churn when file charset will not change
                if (charSet != null) {
                	String currentCharSet = wsFile.getCharset();
                	if (currentCharSet == null || !currentCharSet.equals(charSet)) {
                		wsFile.setCharset(charSet, null);
                	}
                }
                wsFile.setContents(is, false, true, null);
            }
        } else {
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes);
                fos.close();
            } catch (IOException e) {
                throw new CoreException(Logging.newStatus(UtilsPlugin.getDefault(), e));
            }
        }
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.utils.IFileSaver#touchFile(java.io.File)
     */
    public void touchFile(File file) throws CoreException {
        IFile wsFile = FileUtils.convertFileToIFile(file);
        if (wsFile != null) {
        	if (wsFile.exists())
        		wsFile.setLocalTimeStamp(System.currentTimeMillis());
        } else {
        	if (file.exists())
        		file.setLastModified(System.currentTimeMillis());
        }
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.utils.IFileSaver#fileExists(java.io.File)
     */
    public boolean fileExists(File file) {
        IFile wsFile = FileUtils.convertFileToIFile(file);
        if (wsFile != null) {
        	return wsFile.exists();
        } else {
        	return file.exists();
        }
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.utils.IFileLoader#getModificationTime(java.io.File)
     */
    public long getModificationTime(File file) {
        IFile wsFile = FileUtils.convertFileToIFile(file);
        if (wsFile != null) {
        	return wsFile.getLocalTimeStamp();
        } else {
        	return file.lastModified();
        }
    }
}
