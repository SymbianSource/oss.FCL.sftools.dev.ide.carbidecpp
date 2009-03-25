/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.sourcegen.doms.rss.parser;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;

import org.eclipse.core.runtime.IPath;

/**
 * Be informed of various node traversals when the augmented preprocessed
 * translation unit is iterated.
 * 
 *
 */
public interface IAugmentedPreprocessorCallbacks {
	/**
	 * Called when traversing a #&lt;directive&gt; <b>including</b>
	 * #includes
	 * @param directive
	 */
	void handleDirective(IASTPreprocessorStatement directive); 

	/**
	 * Called when entering an #include, only if its syntax is legal
	 * and its file is located
	 * Called after {@link #handleDirective(IASTPreprocessorStatement)}.
	 * @param include the #include
	 * @param path the resolved path
	 */
	void handleIncludeEntry(IASTPreprocessorIncludeStatement include, IPath path);
	
	/**
	 * Called when exiting the previous #include (only if it existed)
	 */
	void handleIncludeExit();

	/**
	 * Called when a problem is encountered.
	 * @param problem the problem
	 */
	void handleProblem(IASTProblemTopLevelNode problem);

}
