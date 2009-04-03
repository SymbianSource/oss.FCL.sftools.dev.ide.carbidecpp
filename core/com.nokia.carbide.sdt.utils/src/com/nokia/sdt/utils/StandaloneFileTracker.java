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

import org.eclipse.core.runtime.CoreException;

import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.Logging;

import java.io.*;

public class StandaloneFileTracker implements IFileTracker {

    /**
     * 
     */
    public StandaloneFileTracker() {
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.IFileTracker#loadFileText(org.eclipse.core.runtime.IPath)
     */
    public char[] loadFileText(File file) throws CoreException {
    	return FileUtils.readFileContents(file, "UTF-8");
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.IFileTracker#saveFileText(org.eclipse.core.runtime.IPath, char[])
     */
    public void saveFileText(File file, String charSet, char[] text) throws CoreException {
        try {
            Writer writer;
            if (charSet != null)
            	writer = new OutputStreamWriter(
                    new FileOutputStream(file), charSet);
            else
            	writer = new OutputStreamWriter(
            		new FileOutputStream(file));
            writer.write(text);
            writer.close();                
        } catch (UnsupportedEncodingException e) {
            throw new CoreException(Logging.newStatus(UtilsPlugin.getDefault(), e));
        } catch (IOException e) {
            throw new CoreException(Logging.newStatus(UtilsPlugin.getDefault(), e));
        }
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.utils.IFileSaver#touchFile(java.io.File)
     */
    public void touchFile(File file) throws CoreException {
    	if (file.exists())
    		file.setLastModified(System.currentTimeMillis());
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.utils.IFileSaver#fileExists(java.io.File)
     */
    public boolean fileExists(File file) {
    	return file.exists();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.utils.IFileLoader#getModificationTime(java.io.File)
     */
    public long getModificationTime(File file) {
    	return file.lastModified();
    }
}
