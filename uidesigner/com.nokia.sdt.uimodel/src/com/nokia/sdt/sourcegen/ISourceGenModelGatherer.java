/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.sourcegen;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import java.util.List;

/**
 * Interface defining how the sourcegen provider finds models
 * to include in its scope.
 * 
 *
 */
public interface ISourceGenModelGatherer {
	/** 
	 * Return models for which to generate source.<p>
	 * This passes the root model, which may be newer than anything
	 * cached or available through IDesignerDataModelSpecifier. 
	 * @param rootModel root model, may be null
	 */
	public List<IDesignerDataModelSpecifier> getGeneratableModelSpecifiers(IDesignerDataModel rootModel); 
}
