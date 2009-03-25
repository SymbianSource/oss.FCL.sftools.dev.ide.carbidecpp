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

package com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGInstallFileStatement;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTPKGInstallFileStatement extends ASTPKGLocalizedStatementBase
		implements IASTPKGInstallFileStatement {
	IASTLiteralTextNode targetFile;
	IASTListNode<IASTLiteralTextNode> options;
	boolean languageDependentSyntaxStatus;

	public ASTPKGInstallFileStatement(
			IASTListNode<IASTLiteralTextNode> languageVariants,
			IASTLiteralTextNode targetFile,
			IASTListNode<IASTLiteralTextNode> options) {
		super(languageVariants);
		setTargetFile(targetFile);
		setOptions(options);
		setLanguageDependentSyntaxStatus(languageVariants.size() > 1);
		dirty = false;
	}

	public ASTPKGInstallFileStatement(ASTPKGInstallFileStatement statement) {
		super(statement);
		setTargetFile((IASTLiteralTextNode) statement.getTargetFile().copy());
		if (statement.getOptions() != null) {
			setOptions((IASTListNode<IASTLiteralTextNode>) statement
					.getOptions().copy());
		}
		setLanguageDependentSyntaxStatus(statement
				.getLanguageDependentSyntaxStatus());
		dirty = statement.isDirty();
	}

	public boolean getLanguageDependentSyntaxStatus() {
		return languageDependentSyntaxStatus;
	}

	public IASTListNode<IASTLiteralTextNode> getOptions() {
		return options;
	}

	public IASTLiteralTextNode getTargetFile() {
		return targetFile;
	}

	private void setLanguageDependentSyntaxStatus(boolean status) {
		this.languageDependentSyntaxStatus = status;
		fireChanged();
		dirty = true;
	}

	public void setOptions(IASTListNode<IASTLiteralTextNode> options) {
		Check.checkArg(options);
		unparent(this.options);
		parent(options);
		this.options = options;
		fireChanged();
		dirty = true;
	}

	public void setTargetFile(IASTLiteralTextNode targetFile) {
		Check.checkArg(targetFile);
		unparent(this.targetFile);
		parent(targetFile);
		this.targetFile = targetFile;
		fireChanged();
		dirty = true;
	}

	public IASTNode copy() {
		return new ASTPKGInstallFileStatement(this);
	}

	public IASTNode[] getChildren() {
		return makeChildListWith(targetFile, options);
	}

	public void rewrite(IRewriteHandler handler) {
		boolean languageDependant = getLanguageVariants().getChildren().length > 1;
		if (languageDependant) {
			handler.emitText("{"); //$NON-NLS-1$
		}
		handler.emitNode(getLanguageVariants());
		if (languageDependant) {
			handler.emitText("}"); //$NON-NLS-1$
		}
		handler.emitText("-"); //$NON-NLS-1$
		handler.emitNode(getTargetFile());
		IASTListNode<IASTLiteralTextNode> optionNode = getOptions();
		if (optionNode != null && optionNode.getChildren().length > 0) {
			handler.emitText(","); //$NON-NLS-1$
			handler.emitSpace();
			handler.emitNode(optionNode);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((options == null) ? 0 : options.hashCode());
		result = prime * result
				+ ((targetFile == null) ? 0 : targetFile.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ASTPKGInstallFileStatement other = (ASTPKGInstallFileStatement) obj;
		if (options == null) {
			if (other.options != null)
				return false;
		} else if (!options.equals(other.options))
			return false;
		if (targetFile == null) {
			if (other.targetFile != null)
				return false;
		} else if (!targetFile.equals(other.targetFile))
			return false;
		return true;
	}

}
