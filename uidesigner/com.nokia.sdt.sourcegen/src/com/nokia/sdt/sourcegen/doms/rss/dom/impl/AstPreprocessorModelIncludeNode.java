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

package com.nokia.sdt.sourcegen.doms.rss.dom.impl;

import com.nokia.sdt.sourcegen.doms.rss.dom.IAstPreprocessorModelIncludeNode;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile;

/**
 * 
 *
 */
public class AstPreprocessorModelIncludeNode extends AstPreprocessorIncludeDirective implements
		IAstPreprocessorModelIncludeNode {

	/**
	 * @param filename
	 * @param userPath
	 * @param file
	 */
	public AstPreprocessorModelIncludeNode(String filename, boolean userPath, IAstSourceFile file) {
		super(filename, userPath, file);
	}
	
	public AstPreprocessorModelIncludeNode(IAstSourceFile file) {
		this(file.getSourceFile().getFileName(), true, file);
	}
}
