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

import java.util.List;

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.model.EGeneratedHeaderFlags;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AllNodesViewFilter;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.ASTMMPFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPListArgumentStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPProblemStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPSingleArgumentStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStartBlockStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPUidStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IMMPSourcePathDependentContext;
import com.nokia.carbide.internal.cpp.epoc.engine.model.StructuredItemStatementListConverter;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

class ResourceBlockListConverter implements StructuredItemStatementListConverter<IASTMMPStartBlockStatement, IMMPResource> {

	final static String RESOURCE_KEYWORD = "RESOURCE"; //$NON-NLS-1$
	/**
	 * 
	 */
	private MMPView view;
	private IPath currentSourcePath;

	/**
	 * @param view
	 * @param currentSourcePath 
	 */
	ResourceBlockListConverter(MMPView view, IPath currentSourcePath) {
		this.view = view;
		this.currentSourcePath = currentSourcePath;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#elementMatches(java.lang.Object, java.lang.Object)
	 */
	public boolean elementMatches(IMMPResource element, IMMPResource another) {
		return MMPView.equalPath(element.getSource(), another.getSource());
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#allowEmptyStatements()
	 */
	public boolean allowEmptyStatements() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#fromNode(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode, com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListArgumentInfoIter)
	 */
	public IMMPResource fromNode(IASTMMPStartBlockStatement node) {
		if (!node.getBlockType().getValue().equalsIgnoreCase(RESOURCE_KEYWORD))
			return null;
		
		if (node.getBlockArguments().size() < 1) {
			EpocEnginePlugin.log(new IllegalArgumentException("Skipping malformed START RESOURCE block in " + node.getSourceReference() +": " + node.getNewText())); //$NON-NLS-1$ //$NON-NLS-2$
			return null;
		}
		
		IMMPResource resource = this.view.createMMPResource();
		resource.setHeaderFlags(EGeneratedHeaderFlags.NoHeader);
		IPath source = FileUtils.createPossiblyRelativePath(node.getBlockArguments().get(0).getValue());
		/*
		resource.setSource(currentSourcePath != null && !MMPView.isAbsoluteLikePath(source)
				? currentSourcePath.append(source) 
				: this.view.fromMmpToProjectPath(source));*/
		if (MMPView.isAbsoluteLikePath(source))
			resource.setSource(source);
		else
			resource.setSource(currentSourcePath.append(source));
		
		for (IASTMMPStatement stmt : node.getStatements()) {
			if (EMMPStatement.DEPENDS.matches(stmt)) {
				List<String> dependsList = resource.getDependsFiles();
				dependsList.add(((IASTMMPSingleArgumentStatement) stmt).getArgument().getValue());
				resource.setDependsFiles(dependsList);
			}
			else if (EMMPStatement.TARGET.matches(stmt)) {
				resource.setTargetFile(((IASTMMPSingleArgumentStatement) stmt).getArgument().getValue());
			}
			else if (EMMPStatement.TARGETPATH.matches(stmt)) {
				resource.setTargetPath(new Path(
						HostOS.convertPathToUnix(((IASTMMPSingleArgumentStatement) stmt).getArgument().getValue())));
			}
			else if (EMMPStatement.HEADER.matches(stmt)) {
				resource.setHeaderFlags(EGeneratedHeaderFlags.Header);
			} 
			else if (EMMPStatement.HEADERONLY.matches(stmt)) {
				resource.setHeaderFlags(EGeneratedHeaderFlags.HeaderOnly);
			} 
			else if (EMMPStatement.LANG.matches(stmt)) {
				resource.setLanguages(view.createLanguageList((IASTMMPListArgumentStatement) stmt));
			}
			else if (EMMPStatement.UID.matches(stmt)) {
				IASTMMPUidStatement uid = (IASTMMPUidStatement) stmt;
				if (uid.getUid2() != null)
					resource.setUid2(uid.getUid2().getValue());
				if (uid.getUid3() != null)
					resource.setUid3(uid.getUid3().getValue());
			}
			else if (EMMPStatement.START_BLOCK.matches(stmt)
					// marked unknown, not an actual IASTMMPStartBlockStatement
					&& stmt.getNewText().toUpperCase().indexOf(RESOURCE_KEYWORD) > 0
					&& view.getViewConfiguration().getViewFilter() instanceof AllNodesViewFilter) {
				// likely this #if construct under the "all" view, ignore:
				//
				//	#ifdef MACRO
				//	START RESOURCE a.rss
				//	#else
				//	START RESOURCE b.rss
				//	#endif
				//		...
				//	END
			}
			else if (stmt instanceof IASTMMPProblemStatement) {
				// ignore
			}
			else {
				EpocEnginePlugin.log(new IllegalArgumentException("Ignoring unknown statement in START RESOURCE in " + stmt.getSourceReference() + ": " + stmt.getNewText())); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		
		return resource;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#toNode(java.lang.Object, com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListArgumentInfoIter)
	 */
	public IASTMMPStartBlockStatement toNode(IMMPResource resource) {
		if (!resource.isValid()) {
			EpocEnginePlugin.log(new IllegalArgumentException("invalid resource block info: " + resource)); //$NON-NLS-1$
			return null;
		}

		IPath srcPath = resource.getSource();
		if (!MMPView.isAbsoluteLikePath(srcPath)) {
			srcPath = MMPView.fromProjectToRelativePath(currentSourcePath, srcPath);
		}
		
		IASTListNode<IASTLiteralTextNode> blockArguments = ASTFactory.createPreprocessorLiteralTextNodeList(
				new String[] { view.pathString(srcPath) });
		
		IASTListNode<IASTMMPStatement> statements = ASTMMPFactory.createMMPStatementListNode();
		
		if (resource.getDependsFiles().size() != 0) {
			List<String> dependsList = resource.getDependsFiles();
			for (String depends: dependsList) {
				statements.add(ASTMMPFactory.createMMPSingleArgumentStatement(
						EMMPStatement.DEPENDS.toString(), depends));
			}
		}
		
		if (resource.getTargetFile() != null) {
			statements.add(ASTMMPFactory.createMMPSingleArgumentStatement(
					EMMPStatement.TARGET.toString(), resource.getTargetFile()));
		}
		
		if (resource.getTargetPath() != null && resource.getTargetPath().segmentCount() > 0) {
			statements.add(ASTMMPFactory.createMMPSingleArgumentStatement(
					EMMPStatement.TARGETPATH.toString(), view.pathString(resource.getTargetPath())));
		}
		
		switch (resource.getHeaderFlags()) {
		case NoHeader:
			// nothing
			break;
		case Header:
			statements.add(ASTMMPFactory.createMMPFlagStatement(
					EMMPStatement.HEADER.toString()));
			break;
		case HeaderOnly:
			statements.add(ASTMMPFactory.createMMPFlagStatement(
					EMMPStatement.HEADERONLY.toString()));
			break;
		default:
			Check.checkState(false);
		}
		
		if (resource.getLanguages().size() != 0) {
			statements.add(view.createLanguageStatement(resource.getLanguages()));
		}
		
		if (resource.getUid2() != null) {
			statements.add(ASTMMPFactory.createMMPUidStatement(resource.getUid2(), resource.getUid3()));
		}
		
		IASTMMPStartBlockStatement blockStmt = ASTMMPFactory.createMMPStartBlockStatement(
				ASTFactory.createPreprocessorLiteralTextNode(RESOURCE_KEYWORD),
				blockArguments,
				statements);
		return blockStmt;
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.mmp.MMPView.StructuredItemListConverter#updateNode(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStatement, com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStatement)
	 */
	public void updateNode(IASTMMPStartBlockStatement blockStmt, IASTMMPStartBlockStatement updatedNode) {
		if (!blockStmt.getBlockArguments().equals(updatedNode.getBlockArguments())) {
			blockStmt.setBlockArguments((IASTListNode<IASTLiteralTextNode>) updatedNode.getBlockArguments().copy());
		}
		if (!blockStmt.getStatements().equals(updatedNode.getStatements())) {
			// merge the statements
			view.mergeStatementList(blockStmt.getStatements(), updatedNode.getStatements());
		}
		
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.mmp.StatementListConverter#canAddToStatement(java.lang.Object)
	 */
	public boolean canAddToStatement(IMMPResource model) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.mmp.StatementListConverter#createContextStatement(java.lang.Object)
	 */
	public IASTMMPStatement createContextStatement(IMMPResource model) {
		// no SOURCEPATH for absolute paths (since we don't make things relative to this in #toNode() anyway)
		if (MMPView.isAbsoluteLikePath(model.getSource())) {
			return null;
		}
		IPath mmpSourceElementPath = view.getSourcePathFromSource(model.getSource());
		if (MMPView.equalPath(mmpSourceElementPath, currentSourcePath))
			return null;
		currentSourcePath = mmpSourceElementPath;
		return ASTMMPFactory.createMMPSingleArgumentStatement(
				EMMPStatement.SOURCEPATH.toString(),
				view.pathString(view.fromProjectToMmpPath(mmpSourceElementPath)));
	}

	public boolean changeRequiresNewContext(IMMPResource existing, IMMPResource newElement) {
		// only if path changes
		return !MMPView.equalPath(existing.getSource().removeLastSegments(1),
				newElement.getSource().removeLastSegments(1));
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
