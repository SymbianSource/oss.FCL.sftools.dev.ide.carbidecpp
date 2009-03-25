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

package com.nokia.carbide.internal.cpp.epoc.engine.model.mmp;

import com.nokia.carbide.cpp.epoc.engine.model.EGeneratedHeaderFlags;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;

import java.util.ArrayList;
import java.util.List;


public class MMPResource implements IMMPResource {

	private EGeneratedHeaderFlags flagsFlags;
	private List<EMMPLanguage> languages;
	private IPath source;
	private String targetFile;
	private IPath targetPath;
	private String uid2;
	private String uid3;
	private List<String> dependsFiles;

	public MMPResource() {
		flagsFlags = EGeneratedHeaderFlags.NoHeader;
		languages = new ArrayList<EMMPLanguage>(1);
		dependsFiles = new ArrayList<String>(1);
	}
	
	public MMPResource(MMPResource resource) {
		this.flagsFlags = resource.flagsFlags;
		this.languages = new ArrayList<EMMPLanguage>(resource.languages);
		this.source = resource.source;
		this.targetFile = resource.targetFile;
		this.targetPath = resource.targetPath;
		this.uid2 = resource.uid2;
		this.uid3 = resource.uid3;
		this.dependsFiles = new ArrayList<String>(resource.dependsFiles);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource#copy()
	 */
	public IMMPResource copy() {
		return new MMPResource(this);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IMMPResource)) 
			return false;
		IMMPResource other = (IMMPResource) obj;
		return other.getSource().equals(source)
		&& ObjectUtils.equals(other.getTargetPath(), targetPath)
		&& ObjectUtils.equals(other.getTargetFile(), targetFile)
		&& ObjectUtils.equals(other.getLanguages(), languages)
		&& ObjectUtils.equals(other.getHeaderFlags(), flagsFlags)
		&& ObjectUtils.equals(other.getUid2(), uid2)
		&& ObjectUtils.equals(other.getUid3(), uid3)
		&& ObjectUtils.equals(other.getDependsFiles(), dependsFiles);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MMPResource: "+source+"->"+targetPath+"/"+targetFile+" depends "+dependsFiles+" "+flagsFlags+" @"+uid2+"/"+uid3+" langs "+languages; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource#isValid()
	 */
	public boolean isValid() {
		return source != null;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource#getHeaderFlags()
	 */
	public EGeneratedHeaderFlags getHeaderFlags() {
		return flagsFlags;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource#getLanguages()
	 */
	public List<EMMPLanguage> getLanguages() {
		return languages;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource#setLanguages(java.util.List)
	 */
	public void setLanguages(List<EMMPLanguage> languages) {
		this.languages = languages;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource#getSource()
	 */
	public IPath getSource() {
		return source;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource#getTargetFile()
	 */
	public String getTargetFile() {
		return targetFile;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource#getTargetPath()
	 */
	public IPath getTargetPath() {
		return targetPath;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource#getUid2()
	 */
	public String getUid2() {
		return uid2;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource#getUid3()
	 */
	public String getUid3() {
		return uid3;
	}
	
	public List<String> getDependsFiles() {
		return dependsFiles;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource#setHeaderFlags(com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource.EResourceHeader)
	 */
	public void setHeaderFlags(EGeneratedHeaderFlags flag) {
		Check.checkArg(flag);
		this.flagsFlags = flag;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource#setSource(org.eclipse.core.runtime.IPath)
	 */
	public void setSource(IPath path) {
		Check.checkArg(path);
		this.source = path;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource#setTargetFile(java.lang.String)
	 */
	public void setTargetFile(String name) {
		this.targetFile = name;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource#setTargetPath(org.eclipse.core.runtime.IPath)
	 */
	public void setTargetPath(IPath path) {
		this.targetPath = path;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource#setUid2(java.lang.String)
	 */
	public void setUid2(String uid2) {
		this.uid2 = uid2;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource#setUid3(java.lang.String)
	 */
	public void setUid3(String uid3) {
		this.uid3 = uid3;
	}

	public void setDependsFiles(List<String> dependsFiles) {
		this.dependsFiles = dependsFiles;
	}

}
