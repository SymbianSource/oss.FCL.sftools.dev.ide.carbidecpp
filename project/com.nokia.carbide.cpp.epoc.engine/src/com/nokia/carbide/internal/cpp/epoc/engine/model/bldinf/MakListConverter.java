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

import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMMPReference;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakMakeReference;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakefileReference;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakefileReference.EMakeEngine;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListHolder;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.ASTBldInfFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfMakMakeStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfMakefileStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfMmpfileStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.model.StructuredItemStatementListConverter;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

class MakListConverter implements StructuredItemStatementListConverter<IASTBldInfMakMakeStatement, IMakMakeReference> {

	/**
	 * 
	 */
	private BldInfView bldInfView;
	protected String sectionName;

	/**
	 * @param bldInfView
	 */
	MakListConverter(BldInfView bldInfView, String sectionName) {
		this.bldInfView = bldInfView;
		this.sectionName = sectionName;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#elementMatches(java.lang.Object, java.lang.Object)
	 */
	public boolean elementMatches(IMakMakeReference element, IMakMakeReference another) {
		if (!element.getClass().equals(another.getClass()))
			return false;
		return BldInfView.equalPath(element.getPath(), another.getPath());
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.bldinf.BldInfView.ListConverter#fromNode(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode)
	 */
	public IMakMakeReference fromNode(IASTBldInfMakMakeStatement mmpfileStmt_) {
		IMakMakeReference mak = null;
		if (mmpfileStmt_ instanceof IASTBldInfMmpfileStatement) {
			IMMPReference mmpfile = this.bldInfView.createMMPReference();
			IPath path = this.bldInfView.fromBldInfToProjectPath(mmpfileStmt_.getPath());
			// bldmake.pl ignores the extension and forces .MMP
			path = path.removeFileExtension().addFileExtension("mmp"); //$NON-NLS-1$
			mmpfile.setPath(path);
			mak = mmpfile;
		} else if (mmpfileStmt_ instanceof IASTBldInfMakefileStatement) {
			IASTBldInfMakefileStatement makefileStmt = (IASTBldInfMakefileStatement) mmpfileStmt_;
			IMakefileReference makefile = this.bldInfView.createMakefileReference();
			String token = makefileStmt.getType().getValue().toUpperCase();
			try {
				makefile.setMakeEngine(EMakeEngine.valueOf(token));
			} catch (IllegalArgumentException e) {
				// shouldn't have even parsed
				Check.checkState(false);
			}
			makefile.setPath(this.bldInfView.fromBldInfToProjectPath(
					mmpfileStmt_.getPath()));
			mak = makefile;
			
		} else {
			Check.checkState(false);
		}

		mak.setAttributes(this.bldInfView.getStringList(mmpfileStmt_.getAttributes()));
		return mak;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.bldinf.BldInfView.ListConverter#toNode(java.lang.Object)
	 */
	public IASTBldInfMakMakeStatement toNode(IMakMakeReference mak) {
		if (!mak.isValid())
			return null;
		
		if (mak instanceof IMMPReference) {
			IMMPReference mmp = (IMMPReference) mak;
			IASTBldInfMakMakeStatement stmt = ASTBldInfFactory.createBldInfMmpfileStatement(
					bldInfView.pathString(this.bldInfView.fromProjectToBldInfPath(mak.getPath())),
					mmp.getAttributes());
			return stmt;
		} else if (mak instanceof IMakefileReference) {
			IMakefileReference makefile = (IMakefileReference) mak;
			String token = makefile.getMakeEngine().toString().toLowerCase();
			IASTBldInfMakMakeStatement stmt = ASTBldInfFactory.createBldInfMakefileStatement(
					token,
					bldInfView.pathString(this.bldInfView.fromProjectToBldInfPath(makefile.getPath())),
					makefile.getAttributes());
			return stmt;
		} else {
			Check.checkState(false);
			return null;
		}
	}
	
	public void updateNode(IASTBldInfMakMakeStatement existing,
			IASTBldInfMakMakeStatement newElement) {
		IPath exPath = new Path(existing.getPath().getValue()).removeFileExtension();
		IPath newPath = new Path(newElement.getPath().getValue()).removeFileExtension();
		if (!exPath.equals(newPath)) {
			existing.setPath((IASTLiteralTextNode) newElement.getPath().copy());
		}
		if (!ObjectUtils.equals(existing.getAttributes(), newElement.getAttributes())) {
			if (newElement.getAttributes() == null) {
				existing.setAttributes(null);
			} else {
				existing.setAttributes((IASTListNode<IASTLiteralTextNode>) newElement.getAttributes().copy());
			}
		}
		if (existing instanceof IASTBldInfMakefileStatement) {
			IASTBldInfMakefileStatement exMake = (IASTBldInfMakefileStatement) existing;
			IASTBldInfMakefileStatement newMake = (IASTBldInfMakefileStatement) newElement;
			if (!exMake.getType().equals(newMake.getType())) {
				exMake.setType((IASTLiteralTextNode) newMake.getType().copy());
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.bldinf.BldInfView.ListConverter#allowEmptyStatements()
	 */
	public boolean allowEmptyStatements() {
		return true;
	}
	
	public boolean canAddToStatement(IMakMakeReference model) {
		return false;
	}
	
	public boolean changeRequiresNewContext(IMakMakeReference existing,
			IMakMakeReference newElement) {
		return false;
	}
	
	public IASTStatement createContextStatement(IMakMakeReference model) {
		return null;
	}

	public IASTListHolder<IASTBldInfMakMakeStatement> createNewListStatement() {
		return ASTBldInfFactory.createBldInfBlockStatement(sectionName);
	}

	public Pair<IASTNode, IASTNode> getInsertAnchors() {
		return null;
	}

	public void associateContextStatement(IASTStatement stmt,
			IASTStatement contextStmt) {
		
	}
	
}
