/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.model.mmp;

import com.nokia.carbide.cpp.epoc.engine.model.EGeneratedHeaderFlags;

import org.eclipse.core.runtime.IPath;

import java.util.List;

/**
 * Abstract representation of a single (set of) compiled resources.
 * <p>
 * This is used only for new-style START RESOURCE blocks.
 * <p>  
 * Old style resources are specified in the MMP view's TARGETPATH,
 * LANG, RESOURCE/SYSTEMRESOURCE lists.
 *
 */
public interface IMMPResource {
	/** tell if entry is valid (e.g. initialized): source must be set */
	boolean isValid();
	
	/** Get the header generation flag */
	EGeneratedHeaderFlags getHeaderFlags();
	/** Set the header generation flag */
	void setHeaderFlags(EGeneratedHeaderFlags flag);
	
	/** Get the project-relative RSS source file */
	IPath getSource();
	/** Set the project-relative RSS source file */
	void setSource(IPath path);

	/** Access/modify the languages to emit (never null) */
	List<EMMPLanguage> getLanguages();
	/** Set language list (may not be null) */
	void setLanguages(List<EMMPLanguage> languages);
	
	/** Get the target filename; may be null to be automatically
	 * derived from the source name */
	String getTargetFile();
	/** Set the target filename; may be null to automatically
	 * derive from the source path */
	void setTargetFile(String name);

	/** Get the EPOCROOT-relative target path
	 */
	IPath getTargetPath();
	/** Set the EPOCROOT-relative target path  
	 */
	void setTargetPath(IPath path);
	
	/** Get the UID2 value (may be null) */
	String getUid2();
	/** Set the UID2 value (may be null) */
	void setUid2(String uid2);
	
	/** Get the UID3 value (may be null) */
	String getUid3();
	/** Set the UID3 value (may be null) */
	void setUid3(String uid3);
	
	/** Get the depends filenames */
	List<String> getDependsFiles();
	/** Set the depends filenames */
	void setDependsFiles(List<String> names);
	
	/** Return copy of self */
	IMMPResource copy();
}
