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

package com.nokia.sdt.symbian.workspace;

import com.nokia.sdt.datamodel.IDesignerDataModel;

import org.eclipse.core.resources.IFile;

public interface ISymbianDataModelSpecifier {
	
	boolean isRoot();

	IFile getModelFile();
	
	void clearSnapshot();
	
	void refreshSnapshot();
	
	void updateSnapshot(IDesignerDataModel model);
}
