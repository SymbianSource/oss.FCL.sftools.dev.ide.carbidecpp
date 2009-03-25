/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.symbian.ui.images;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

class NewImageExport {
	public IPath projectPath;
	public IPath epocPath;
	public IPath targetPath;
	public NewImageExport(IPath projectPath, IPath targetPath) {
		this.projectPath = projectPath;
		this.targetPath = targetPath;
		
		this.epocPath = new Path("epoc32\\data\\").append( //$NON-NLS-1$
				targetPath.getDevice().substring(0, 1)).append(targetPath.setDevice(null));
	}
	
}