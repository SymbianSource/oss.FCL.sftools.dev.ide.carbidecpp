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

package com.nokia.carbide.internal.cpp.epoc.engine.model.mmp;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ModelConverter;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ViewASTBase;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;

class PathListConverter implements ModelConverter<IASTLiteralTextNode, IPath> {
	/**
	 * 
	 */
	private MMPView view;
	private IPath sourcePath;

	public PathListConverter(MMPView view, IPath sourcePath) {
		this.view = view;
		this.sourcePath = sourcePath;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#elementMatches(java.lang.Object, java.lang.Object)
	 */
	public boolean elementMatches(IPath element, IPath another) {
		return element.toString().toLowerCase().equals(another.toString().toLowerCase());
	}
	
	public IPath fromNode(IASTLiteralTextNode node) {
		// work around boog in Eclipse:  a string with ".\.." will be collapsed to ""
		IPath source = FileUtils.createPossiblyRelativePath(node.getValue());
		if (sourcePath != null && !source.isAbsolute())
			return sourcePath.append(node.getValue());
		else
			return this.view.fromMmpToProjectPath(source);
	}

	public IASTLiteralTextNode toNode(IPath path) {
		if (sourcePath != null)
			path = ViewASTBase.fromProjectToRelativePath(sourcePath, path);
		else
			path = this.view.fromProjectToMmpPath(path);
		return ASTFactory.createPreprocessorLiteralTextNode(view.pathString(path));
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#allowEmptyStatements()
	 */
	public boolean allowEmptyStatements() {
		return false;
	}
	
	public IASTStatement createContextStatement(IPath model) {
		return null;
	}
}