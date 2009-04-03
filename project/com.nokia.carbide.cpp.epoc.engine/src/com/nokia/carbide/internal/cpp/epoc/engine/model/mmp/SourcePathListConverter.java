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

import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListHolder;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.ASTMMPFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPSingleArgumentStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IMMPSourcePathDependentContext;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ListStatementListConverter;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

class SourcePathListConverter implements ListStatementListConverter<IASTLiteralTextNode, IPath> {
	private static final IPath CURRENT_DIRECTORY_PATH = new Path("."); //$NON-NLS-1$
	private IPath currentSourcePath;
	private MMPView view;
	private String stmtType;
	
	public SourcePathListConverter(MMPView view, String stmtType) {
		this.view = view;
		this.stmtType = stmtType;
	}
	
	public void setStatementName(String stmtType) {
		this.stmtType = stmtType;
	}
	public void setCurrentSourcePath(IPath path) {
		this.currentSourcePath = path;
	}
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#allowEmptyStatements()
	 */
	public boolean allowEmptyStatements() {
		return false;
	}
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#elementMatches(java.lang.Object, java.lang.Object)
	 */
	public boolean elementMatches(IPath element, IPath another) {
		return element.toString().toLowerCase().equals(another.toString().toLowerCase());
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#fromNode(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode)
	 */
	public IPath fromNode(IASTLiteralTextNode node) {
		IPath path = FileUtils.createPossiblyRelativePath(node.getValue());
		//if (MMPView.isAbsoluteLikePath(path))
		//	return path;
		if (MMPView.equalPath(CURRENT_DIRECTORY_PATH, currentSourcePath))
			return path;
		return currentSourcePath.append(path);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#toNode(java.lang.Object)
	 */
	public IASTLiteralTextNode toNode(IPath elementObj) {
		IPath relativePath = MMPView.fromProjectToRelativePath(currentSourcePath, elementObj);
		return ASTFactory.createPreprocessorLiteralTextNode(view.pathString(relativePath));
	}
	/**
	 * @return
	 */
	public IPath getCurrentSourcePath() {
		return currentSourcePath;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.mmp.StatementListConverter#canAddToStatement(java.lang.Object)
	 */
	public boolean canAddToStatement(IPath model) {
		IPath mmpSourceElementPath = view.getSourcePathFromSource(model);
		return MMPView.equalPath(getCurrentSourcePath(), mmpSourceElementPath);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.mmp.StatementListConverter#createContextStatement(java.lang.Object)
	 */
	public IASTMMPStatement createContextStatement(IPath model) {
		IPath mmpSourceElementPath = view.getSourcePathFromSource(model);
		if (MMPView.equalPath(mmpSourceElementPath, currentSourcePath))
			return null;
		currentSourcePath = mmpSourceElementPath;
		return ASTMMPFactory.createMMPSingleArgumentStatement(
				EMMPStatement.SOURCEPATH.toString(),
				view.pathString(view.fromProjectToMmpPath(mmpSourceElementPath)));
	}
	
	public IASTListHolder<IASTLiteralTextNode> createNewListStatement() {
		return ASTMMPFactory.createMMPListArgumentStatement(stmtType);
	}

	public boolean changeRequiresNewContext(IPath existing, IPath newElement) {
		return false;
	}

	public Pair<IASTNode, IASTNode> getInsertAnchors() {
		return null;
	}

	public void associateContextStatement(IASTStatement stmt,
			IASTStatement contextStmt) {
		IMMPSourcePathDependentContext mmpContext = ((IASTMMPStatement) stmt).getSourcePathDependentContext();
		mmpContext.setSourcePathStatement((IASTMMPSingleArgumentStatement) contextStmt);
	}

	
}