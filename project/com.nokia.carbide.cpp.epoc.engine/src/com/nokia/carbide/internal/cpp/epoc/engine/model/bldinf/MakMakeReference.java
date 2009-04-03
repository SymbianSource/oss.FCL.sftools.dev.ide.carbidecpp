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

package com.nokia.carbide.internal.cpp.epoc.engine.model.bldinf;

import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakMakeReference;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MakMakeReference implements IMakMakeReference {

	private IPath path;
	private List<String> attributes;
	protected static final String MANUAL_KEYWORD = "MANUAL"; //$NON-NLS-1$
	protected static final String SUPPORT_KEYWORD = "SUPPORT"; //$NON-NLS-1$
	protected static final String TIDY_KEYWORD = "TIDY"; //$NON-NLS-1$
	protected static final String BUILD_AS_ARM_KEYWORD = "BUILD_AS_ARM"; //$NON-NLS-1$

	public MakMakeReference() {
		attributes = new ArrayList<String>();
	}
	
	public MakMakeReference(MakMakeReference other) {
		this.path = other.path;
		this.attributes = new ArrayList<String>(other.attributes);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MakMakeReference)) 
			return false;
		MakMakeReference other = (MakMakeReference) obj;
		
		// for comparison, the extension doesn't matter
		boolean pathsEqual = (path == null && other.path == null)
		|| (path != null && other.path != null &&
				other.path.removeFileExtension().toString().toLowerCase().
				equals(path.removeFileExtension().toString().toLowerCase())); 
		return pathsEqual && other.attributes.equals(attributes);
	}
	
	@Override
	public int hashCode() {
		return (path != null ? path.removeFileExtension().toString().toLowerCase().hashCode() : 0)
			^ (attributes.hashCode() << 1);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[" + path + " @" + TextUtils.catenateStrings(attributes.toArray(), " ") + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}
	
	protected boolean findAttribute(String keyword) {
		for (String attr : attributes) {
			if (attr.equalsIgnoreCase(keyword))
				return true;
		}
		return false;
	}
	
	protected void setAttribute(String keyword, boolean set) {
		for (Iterator<String> iter = attributes.iterator(); iter.hasNext(); ) {
			if (iter.next().equalsIgnoreCase(keyword)) {
				if (set)
					return;
				else
					iter.remove();
			}
		}
		if (set)
			attributes.add(keyword);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakMakeReference#isValid()
	 */
	public boolean isValid() {
		return path != null;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakMakeReference#getAttributes()
	 */
	public List<String> getAttributes() {
		return attributes;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakMakeReference#setAttributes(java.util.List)
	 */
	public void setAttributes(List<String> attributes) {
		Check.checkArg(attributes);
		this.attributes = attributes;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakMakeReference#getPath()
	 */
	public IPath getPath() {
		return path;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakMakeReference#isManual()
	 */
	public boolean isManual() {
		return findAttribute(MANUAL_KEYWORD);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakMakeReference#isSupport()
	 */
	public boolean isSupport() {
		return findAttribute(SUPPORT_KEYWORD);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakMakeReference#isTidy()
	 */
	public boolean isTidy() {
		return findAttribute(TIDY_KEYWORD);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakMakeReference#setManual(boolean)
	 */
	public void setManual(boolean manual) {
		setAttribute(MANUAL_KEYWORD, manual);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakMakeReference#setPath(org.eclipse.core.runtime.IPath)
	 */
	public void setPath(IPath path) {
		Check.checkArg(path);
		this.path = path;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakMakeReference#setSupport(boolean)
	 */
	public void setSupport(boolean support) {
		setAttribute(SUPPORT_KEYWORD, support);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakMakeReference#setTidy(boolean)
	 */
	public void setTidy(boolean tidy) {
		setAttribute(TIDY_KEYWORD, tidy);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMMPReference#isBuildAsArm()
	 */
	public boolean isBuildAsArm() {
		return findAttribute(BUILD_AS_ARM_KEYWORD);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMMPReference#setBuildAsArm(boolean)
	 */
	public void setBuildAsArm(boolean build_as_arm) {
		setAttribute(BUILD_AS_ARM_KEYWORD, build_as_arm);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakMakeReference#copy()
	 */
	public IMakMakeReference copy() {
		return new MakMakeReference(this);
	}
	
}
