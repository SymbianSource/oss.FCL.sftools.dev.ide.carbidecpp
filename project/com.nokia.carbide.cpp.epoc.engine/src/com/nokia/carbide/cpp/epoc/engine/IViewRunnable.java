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

package com.nokia.carbide.cpp.epoc.engine;

import org.eclipse.core.runtime.CoreException;

/**
 * Instantiate this interface and pass to EpocEnginePlugin#runWith...View()
 * to encapsulate some of the bookkeeping of model/view handling. 
 *
 */
public interface IViewRunnable<Model, View> {

	/** 
	 * Run with the view.  The view may be modified and committed. 
	 */
	Object run(View view);
	
	/** 
	 * Called instead of #run() when model loading failed.
	 * @param exception if not null, the exception thrown (else, the model
	 * doesn't exist)
	 */
	Object failedLoad(CoreException exception);
}
