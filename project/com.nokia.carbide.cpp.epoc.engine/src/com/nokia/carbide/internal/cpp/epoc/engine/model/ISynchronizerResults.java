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
package com.nokia.carbide.internal.cpp.epoc.engine.model;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.cpp.internal.api.utils.core.*;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ISynchronizerResults {

	/**
	 * Get the mapping of files to modified preparser translation units.
	 */
	Map<File, IASTTranslationUnit> getUpdatedFileMap();
	
	/**
	 * Get any problems encountered.
	 */
	List<IMessage> getMessages();
}
