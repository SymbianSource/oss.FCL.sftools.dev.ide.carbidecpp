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
package com.nokia.sdt.datamodel;

import org.eclipse.core.runtime.IStatus;

/**
 * LoadResult encapsulates the results of
 * loading a data model. If successful, 
 * an IDesignerDataModel is created. IStatus
 * may contain one or more warnings or errors, 
 * independent of whether a model was created.
 *
 */
public class LoadResult {
	
	private IDesignerDataModel model;
	private IStatus status;

	public LoadResult(IDesignerDataModel model,
					  IStatus status) {
		this.model = model;
		this.status = status;
	}

	public IDesignerDataModel getModel() {
		return model;
	}

	public IStatus getStatus() {
		return status;
	}
	
	public void dispose() {
		if (model != null) {
			model.dispose();
			model = null;
		}
		status = null;
	}
}
