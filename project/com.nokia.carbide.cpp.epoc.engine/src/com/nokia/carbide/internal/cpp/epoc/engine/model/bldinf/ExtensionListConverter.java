/*
* Copyright (c) 2007-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.model.bldinf;

import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExtension;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.*;
import com.nokia.carbide.internal.cpp.epoc.engine.Messages;
import com.nokia.carbide.internal.cpp.epoc.engine.model.StructuredItemStatementListConverter;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import java.util.*;

class ExtensionListConverter implements StructuredItemStatementListConverter<IASTBldInfExtensionBlockStatement, IExtension> {

	/**
	 * 
	 */
	private static final String EXTENSION_KEYWORD = "EXTENSION"; //$NON-NLS-1$
	private static final String OPTION_KEYWORD = "OPTION"; //$NON-NLS-1$
	private static final String TOOL_KEYWORD = "TOOL"; //$NON-NLS-1$
	private static final String DEPENDENCIES_KEYWORD = "DEPENDENCIES"; //$NON-NLS-1$
	private static final String SOURCES_KEYWORD = "SOURCES"; //$NON-NLS-1$
	private static final String TARGET_KEYWORD = "TARGET"; //$NON-NLS-1$
	
	private BldInfView bldInfView;
	protected final String sectionName;

	/**
	 * @param bldInfView
	 */
	ExtensionListConverter(BldInfView bldInfView, String sectionName) {
		this.bldInfView = bldInfView;
		this.sectionName = sectionName;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#elementMatches(java.lang.Object, java.lang.Object)
	 */
	public boolean elementMatches(IExtension element, IExtension another) {
		return BldInfView.equalPath(element.getTemplatePath(), another.getTemplatePath());
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.bldinf.BldInfView.ListConverter#allowEmptyStatements()
	 */
	public boolean allowEmptyStatements() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.bldinf.BldInfView.ListConverter#fromNode(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode)
	 */
	public IExtension fromNode(IASTBldInfExtensionBlockStatement extensionStmt) {
		IExtension extension = this.bldInfView.createExtension();
		if (extensionStmt.getArguments().size() < 2
				|| !EXTENSION_KEYWORD.equalsIgnoreCase(extensionStmt.getArguments().get(0).getValue())) {
			bldInfView.addErrorMessage(Messages.getString("ExtensionListConverter.UnknownStartBlockError"),  extensionStmt); //$NON-NLS-1$
			return null;
		}
		extension.setTemplatePath(FileUtils.createPossiblyRelativePath(extensionStmt.getArguments().get(1).getValue()));
		for (IASTBldInfExtensionStatement stmt : extensionStmt.getList()) {
			if (stmt.getArguments() == null || stmt.getArguments().size() == 0) {
				bldInfView.addErrorMessage(Messages.getString("ExtensionListConverter.MissingArgumentsError"),  stmt); //$NON-NLS-1$
				continue;
			}
			if (TARGET_KEYWORD.equalsIgnoreCase(stmt.getKeywordName())) {
				extension.setTargetPath(FileUtils.createPossiblyRelativePath(stmt.getArguments().get(0).getValue()));
			} 
			else if (SOURCES_KEYWORD.equalsIgnoreCase(stmt.getKeywordName())) {
				// list of files
				for (IASTLiteralTextNode node : stmt.getArguments()) {
					IPath path = bldInfView.fromBldInfToProjectPath(node);
					extension.getSources().add(path);
				}
			}
			else if (DEPENDENCIES_KEYWORD.equalsIgnoreCase(stmt.getKeywordName())) {
				// list of files
				for (IASTLiteralTextNode node : stmt.getArguments()) {
					extension.getDependencies().add(FileUtils.createPossiblyRelativePath(node.getValue()));
				}
			}
			else if (TOOL_KEYWORD.equalsIgnoreCase(stmt.getKeywordName())) {
				extension.setToolName(stmt.getArguments().get(0).getValue());
			}
			else if (OPTION_KEYWORD.equalsIgnoreCase(stmt.getKeywordName())) {
				if (stmt.getArguments().size() < 2) {
					bldInfView.addErrorMessage(Messages.getString("ExtensionListConverter.MissingOptionArgumentsError"),  stmt); //$NON-NLS-1$
					continue;
				}
				
				String key = null;
				StringBuilder value = new StringBuilder();
				for (IASTLiteralTextNode node : stmt.getArguments()) {
					if (key == null)
						key = node.getValue();
					else {
						if (value.length() > 0)
							value.append(' ');
						value.append(node.getValue());
					}
				}
				extension.getOptions().put(key, value.toString());
			}
			else {
				bldInfView.addErrorMessage(Messages.getString("ExtensionListConverter.UnknownStartExtensionError"),  stmt); //$NON-NLS-1$
			}
		}
		return extension;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.bldinf.BldInfView.ListConverter#toNode(java.lang.Object)
	 */
	public IASTBldInfExtensionBlockStatement toNode(IExtension extension) {
		if (!extension.isValid())
			return null;
		
		IASTListNode<IASTLiteralTextNode> arguments = ASTFactory.createListNode(" "); //$NON-NLS-1$
		arguments.add(ASTFactory.createPreprocessorLiteralTextNode(EXTENSION_KEYWORD));
		arguments.add(ASTFactory.createPreprocessorLiteralTextNode(bldInfView.pathString(extension.getTemplatePath())));
		
		IASTListNode<IASTBldInfExtensionStatement> stmts = ASTBldInfFactory.createBldInfExtensionStatementList();
		if (extension.getTargetPath() != null) {
			stmts.add(ASTBldInfFactory.createBldInfExtensionStatement(TARGET_KEYWORD, new String[] {
					bldInfView.pathString(extension.getTargetPath())
					}));
		}
		if (extension.getToolName() != null) {
			stmts.add(ASTBldInfFactory.createBldInfExtensionStatement(TOOL_KEYWORD, new String[] {
					extension.getToolName()
					}));
		}
		if (!extension.getSources().isEmpty()) {
			String[] sources = new String[extension.getSources().size()];
			int idx = 0;
			for (IPath path : extension.getSources()) {
				sources[idx++] = bldInfView.pathString(bldInfView.fromProjectToBldInfPath(path));
			}
			stmts.add(ASTBldInfFactory.createBldInfExtensionStatement(SOURCES_KEYWORD, sources));
		}
		if (!extension.getDependencies().isEmpty()) {
			String[] depends = new String[extension.getDependencies().size()];
			int idx = 0;
			for (IPath path : extension.getDependencies()) {
				depends[idx++] = bldInfView.pathString(path);
			}
			stmts.add(ASTBldInfFactory.createBldInfExtensionStatement(DEPENDENCIES_KEYWORD, depends));
		}
		if (!extension.getOptions().isEmpty()) {
			for (Map.Entry<String, String> entry : extension.getOptions().entrySet()) {
				String[] options = {
					entry.getKey(), entry.getValue()	
				};
				stmts.add(ASTBldInfFactory.createBldInfExtensionStatement(OPTION_KEYWORD, options));
			}
		}
		IASTBldInfExtensionBlockStatement stmt = ASTBldInfFactory.createBldInfExtensionBlockStatement(
				arguments,
				stmts);
		return stmt;
	}

	public void updateNode(IASTBldInfExtensionBlockStatement blockStmt,
			IASTBldInfExtensionBlockStatement updatedNode) {
		IASTListNode<IASTLiteralTextNode> blockArgs = blockStmt.getArguments();
		IASTListNode<IASTLiteralTextNode> updatedArgs = updatedNode.getArguments();
		if (blockArgs.size() != updatedArgs.size()
				|| updatedArgs.size() < 2) {
			// who knows
			blockStmt.setArguments((IASTListNode<IASTLiteralTextNode>) updatedNode.getArguments().copy());
		} else if (blockArgs.size() == updatedArgs.size() && updatedArgs.size() == 2) {
			// replace 'extension' keyword if changed
			if (!blockArgs.get(0).getValue().equalsIgnoreCase(updatedArgs.get(0).getValue())) {
				blockStmt.getArguments().set(0, (IASTLiteralTextNode) updatedArgs.get(0).copy());
			}
			// update path if really changed
			if (!new Path(blockArgs.get(1).getValue().toLowerCase()).equals(
						new Path(updatedArgs.get(1).getValue().toLowerCase()))) {
				blockStmt.getArguments().set(1, (IASTLiteralTextNode) updatedNode.getArguments().get(1).copy());
			}
		}
		if (!blockStmt.getList().equals(updatedNode.getList())) {
			// merge the statements
			bldInfView.mergeStatementList((IASTListNode)blockStmt.getList(), (IASTListNode)updatedNode.getList());
		}
	}
	
	public boolean changeRequiresNewContext(IExtension existing, IExtension newElement) {
		return false;
	}
	
	public IASTStatement createContextStatement(IExtension model) {
		return null;
	}
	
	public IASTListHolder<IASTBldInfExtensionBlockStatement> createNewListStatement() {
		return ASTBldInfFactory.createBldInfBlockStatement(sectionName);
	}

	public Pair<IASTNode, IASTNode> getInsertAnchors() {
		return null;
	}
	
	public void associateContextStatement(IASTStatement stmt,
			IASTStatement contextStmt) {
		
	}
}
