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

import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExport;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;


public class Export implements IExport {

	private IPath sourcePath;
	private IPath targetPath;
	private boolean zipped;

	public Export() {
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IExport))
			return false;
		IExport other = (IExport) obj;
		return other.getSourcePath().equals(sourcePath)
		&& ((other.getTargetPath() == null) == (targetPath == null))
		&& (other.getTargetPath() == null || 
				other.getTargetPath().equals(targetPath))
			&& other.isZipped() == zipped;
	}
	
	@Override
	public int hashCode() {
		return sourcePath.hashCode() ^
		((targetPath == null ? 0 : targetPath.hashCode()) << 2) ^
		(zipped ? 0 : 55);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Export: " + sourcePath + " -> " + targetPath + " zipped = " + zipped; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExport#isValid()
	 */
	public boolean isValid() {
		return sourcePath != null;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExport#getSourcePath()
	 */
	public IPath getSourcePath() {
		return sourcePath;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExport#getTargetPath()
	 */
	public IPath getTargetPath() {
		return targetPath;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExport#isZipped()
	 */
	public boolean isZipped() {
		return zipped;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExport#setSourcePath(org.eclipse.core.runtime.IPath)
	 */
	public void setSourcePath(IPath path) {
		Check.checkArg(path);
		this.sourcePath = path;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExport#setTargetPath(org.eclipse.core.runtime.IPath)
	 */
	public void setTargetPath(IPath path) {
		this.targetPath = path;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExport#setZipped(boolean)
	 */
	public void setZipped(boolean zipped) {
		this.zipped = zipped;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExport#copy()
	 */
	public IExport copy() {
		Export copy = new Export();
		copy.sourcePath = sourcePath;
		copy.targetPath = targetPath;
		copy.zipped = zipped;
		return copy;
	}
}
