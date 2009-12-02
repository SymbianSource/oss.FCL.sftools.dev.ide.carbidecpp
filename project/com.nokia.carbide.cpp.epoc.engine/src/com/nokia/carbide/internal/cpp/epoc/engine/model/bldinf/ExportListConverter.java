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

import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExport;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListHolder;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.ASTBldInfFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfExportStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.model.StructuredItemStatementListConverter;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

class ExportListConverter implements StructuredItemStatementListConverter<IASTBldInfExportStatement, IExport> {

	/**
	 * 
	 */
	private BldInfView bldInfView;
	protected final String sectionName;

	/**
	 * @param bldInfView
	 */
	ExportListConverter(BldInfView bldInfView, String sectionName) {
		this.bldInfView = bldInfView;
		this.sectionName = sectionName;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#elementMatches(java.lang.Object, java.lang.Object)
	 */
	public boolean elementMatches(IExport element, IExport another) {
		//return element.equals(another);
		return BldInfView.equalPath(element.getSourcePath(), another.getSourcePath());
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
	public IExport fromNode(IASTBldInfExportStatement exportStmt) {
		IExport export = this.bldInfView.createExport();
		export.setSourcePath(this.bldInfView.fromBldInfToProjectPath(exportStmt.getSourcePath()));
		if (exportStmt.getZipModifier() != null && exportStmt.getZipModifier().
				getValue().equalsIgnoreCase(BldInfView.ZIP_MODIFIER))
			export.setZipped(true);
		export.setTargetPath(this.bldInfView.fromBldInfExportTargetToProjectPath(
				export.isZipped() ? null : export.getSourcePath(), exportStmt.getTargetPath()));
		return export;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.bldinf.BldInfView.ListConverter#toNode(java.lang.Object)
	 */
	public IASTBldInfExportStatement toNode(IExport export) {
		if (!export.isValid())
			return null;
		IPath sourcePath = this.bldInfView.fromProjectToBldInfPath(export.getSourcePath());
		String source = bldInfView.pathString(sourcePath);
		IPath targetPath = this.bldInfView.fromProjectToBldInfExportTargetPath(
				export.getTargetPath(),
				export.isZipped() ? null : export.getSourcePath());
		String target = null;
		if (targetPath != null) {
			// need the backslashes for an export to a drive, else the build rules are broken
			// (they use 'copy' in DOS which doesn't like forward slashes)
			if (ViewBase.isWin32DrivePath(targetPath)) {
				target = HostOS.convertPathToWindows(targetPath.toOSString());
				source = HostOS.convertPathToWindows(sourcePath.toOSString());
			}
			else
				target = bldInfView.pathString(targetPath);
		}
		IASTBldInfExportStatement stmt = ASTBldInfFactory.createBldInfExportStatement(
			source,
			target,
			export.isZipped() ? BldInfView.ZIP_MODIFIER : null);
		return stmt;
	}

	public void updateNode(IASTBldInfExportStatement existing,
			IASTBldInfExportStatement newElement) {
		if (!equalPathNodes(
				existing.getSourcePath(), 
				newElement.getSourcePath())) {
			if (newElement.getSourcePath() == null) {
				existing.setSourcePath(null);
			} else {
				existing.setSourcePath((IASTLiteralTextNode) newElement.getSourcePath().copy());
			}
		}
		if (!equalPathNodes(
				existing.getTargetPath(), 
				newElement.getTargetPath())) {
			if (newElement.getTargetPath() == null) {
				existing.setTargetPath(null);
			} else {
				existing.setTargetPath((IASTLiteralTextNode) newElement.getTargetPath().copy());
			}
		}
		if (!ObjectUtils.equals(existing.getZipModifier(), newElement.getZipModifier())) {
			if (newElement.getZipModifier() == null) {
				existing.setZipModifier(null);
			} else {
				existing.setZipModifier((IASTLiteralTextNode) newElement.getZipModifier().copy());
			}
		}
	}
	
	private boolean equalPathNodes(IASTLiteralTextNode path1,
			IASTLiteralTextNode path2) {
		if ((path1 != null) != (path2 != null))
			return false;
		if (path1 == null)
			return true;
		return BldInfView.equalPath(new Path(path1.getValue()),
				new Path(path2.getValue()));
	}

	public boolean changeRequiresNewContext(IExport existing, IExport newElement) {
		return false;
	}
	
	public IASTStatement createContextStatement(IExport model) {
		return null;
	}
	
	public IASTListHolder<IASTBldInfExportStatement> createNewListStatement() {
		return ASTBldInfFactory.createBldInfBlockStatement(sectionName);
	}

	public Pair<IASTNode, IASTNode> getInsertAnchors() {
		return null;
	}
	
	public void associateContextStatement(IASTStatement stmt,
			IASTStatement contextStmt) {
		
	}
}
