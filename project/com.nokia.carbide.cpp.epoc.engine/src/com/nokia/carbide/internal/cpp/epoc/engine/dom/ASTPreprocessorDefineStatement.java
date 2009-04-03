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

package com.nokia.carbide.internal.cpp.epoc.engine.dom;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTPreprocessorDefineStatement extends ASTPreprocessorStatement
		implements IASTPreprocessorDefineStatement {

	private IASTLiteralTextNode macroName;

	private IASTListNode<IASTLiteralTextNode> macroArgs;

	private IASTPreprocessorTokenStream macroExpansion;

	public ASTPreprocessorDefineStatement(IASTLiteralTextNode macroName,
			IASTListNode<IASTLiteralTextNode> macroArgs,
			IASTPreprocessorTokenStream macroExpansion) {
		setMacroName(macroName);
		setMacroArgs(macroArgs);
		setMacroExpansion(macroExpansion);
		dirty = false;
	}
	
	public ASTPreprocessorDefineStatement(ASTPreprocessorDefineStatement statement) {
		super(statement);
		setMacroName((IASTLiteralTextNode) statement.getMacroName().copy());
		if (statement.getMacroArgs() != null)
			setMacroArgs((IASTListNode<IASTLiteralTextNode>) statement.getMacroArgs().copy());
		if (statement.getMacroExpansion() != null)
			setMacroExpansion((IASTPreprocessorTokenStream) statement.getMacroExpansion().copy());
		dirty = statement.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTPreprocessorDefineStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTPreprocessorDefineStatement node = (ASTPreprocessorDefineStatement) obj;
		return equalValue(node.macroName, macroName)
			&& equalValue(node.macroArgs, macroArgs)
			&& equalValue(node.macroExpansion, macroExpansion);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ hashCodeOr0(macroName) ^ hashCodeOr0(macroArgs) ^ hashCodeOr0(macroExpansion) ^ 0x1394583;
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		if (macroArgs != null) {
			if (macroExpansion != null) {
				return new IASTNode[] { macroName, macroArgs, macroExpansion };
			} else {
				return new IASTNode[] { macroName, macroArgs };
			}
		} else {
			if (macroExpansion != null) {
				return new IASTNode[] { macroName, macroExpansion };
			} else {
				return new IASTNode[] { macroName };
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTPreprocessorDefineStatement(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitText("#define "); //$NON-NLS-1$
		handler.emitNode(macroName);
		if (macroArgs != null) {
			handler.emitText("("); //$NON-NLS-1$
			handler.emitNode(macroArgs);
			handler.emitText(")"); //$NON-NLS-1$
		}
		if (macroExpansion != null) {
			handler.emitSpace();
			handler.emitNode(macroExpansion);
		}
		handler.emitNewline();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorDefineStatement#getMacroName()
	 */
	public IASTLiteralTextNode getMacroName() {
		return macroName;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorDefineStatement#setMacroName(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode)
	 */
	public void setMacroName(IASTLiteralTextNode name) {
		Check.checkArg(name);
		unparent(this.macroName);
		parent(name);
		this.macroName = name;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorDefineStatement#getMacroArgs()
	 */
	public IASTListNode<IASTLiteralTextNode> getMacroArgs() {
		return macroArgs;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorDefineStatement#setMacroArgs(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode)
	 */
	public void setMacroArgs(IASTListNode<IASTLiteralTextNode> args) {
		unparent(this.macroArgs);
		parent(args);
		this.macroArgs = args;
		// ensure arg list is rewritten properly
		if (this.macroArgs != null)
			((ASTListNode<IASTLiteralTextNode>) this.macroArgs).setSeparator(","); //$NON-NLS-1$
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorDefineStatement#getMacroExpansion()
	 */
	public IASTPreprocessorTokenStream getMacroExpansion() {
		return macroExpansion;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorDefineStatement#setMacroExpansion(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode)
	 */
	public void setMacroExpansion(IASTPreprocessorTokenStream expansion) {
		unparent(this.macroExpansion);
		parent(expansion);
		this.macroExpansion = expansion;
		fireChanged();
		dirty = true;
	}
	
}
