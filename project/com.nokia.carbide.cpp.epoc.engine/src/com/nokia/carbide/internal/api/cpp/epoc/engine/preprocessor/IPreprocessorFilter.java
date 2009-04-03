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

package com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIncludeStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorStatement;

import org.eclipse.core.runtime.IPath;

/**
 * Implement this interface to control whether various nodes are
 * included in the flattened preprocessed TU.
 * @see IPreprocessor#setFilter(IPreprocessorFilter)
 *
 */
public interface IPreprocessorFilter {
	/**
	 * Called when traversing a #&lt;directive&gt; <b>including</b>
	 * #includes
	 * @param directive a #directive from the original file 
	 * @return directive or <code>null</code> to discard node
	 */
	IASTPreprocessorStatement filterDirective(IASTPreprocessorStatement directive); 

	/**
	 * Called when entering parsing for an #include (after
	 * {@link #filterDirective(IASTPreprocessorStatement)}).
	 * <p> 
	 * Not called  for invalid #includes or ones whose files cannot be loaded.
	 * @param include the #include that is being handled
	 * @param path the resolved file path
	 * @return <code>null</code> or new node to insert
	 */
	IASTPreprocessorStatement filterIncludeEntry(
			IASTPreprocessorIncludeStatement include, IPath path);

	/**
	 * Called when exiting parsing for the previous successful #include.
	 * @return <code>null</code> or new node to insert
	 */
	IASTPreprocessorStatement filterIncludeExit();
}
