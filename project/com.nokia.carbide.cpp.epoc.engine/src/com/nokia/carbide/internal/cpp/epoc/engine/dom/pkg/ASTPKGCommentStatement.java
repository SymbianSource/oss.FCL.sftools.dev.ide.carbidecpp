/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGCommentStatement;


public class ASTPKGCommentStatement extends ASTPKGStatement implements
		IASTPKGCommentStatement {

	IASTLiteralTextNode comment;

	public ASTPKGCommentStatement(IASTLiteralTextNode comment) {
		super();
		setComment(comment);
	}

	private ASTPKGCommentStatement(ASTPKGCommentStatement statement) {
		super(statement);
		if (statement.getComment() != null)
			setComment((IASTLiteralTextNode) statement.getComment().copy());
		else
			setComment(null);

		dirty = statement.dirty;
	}

	public IASTLiteralTextNode getComment() {
		return comment;
	}

	public void setComment(IASTLiteralTextNode comment) {
		unparent(comment);
		parent(comment);
		this.comment = comment;
		fireChanged();
		dirty = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTPKGCommentStatement(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitText(";"); //$NON-NLS-1$
		if (comment != null) {
			handler.emitNode(comment);
		}
		// Is it correct to have new line here? shouldn't it be handled
		// elsewhere?
		// e.g. writing new line here gives before and after set source
		// node =
		// ASTPKGFactory.createPKGCommentStatement(getLiteralTextNodeSpanningUpTo(firstComment,
		// eol));
		// ParserUtils.setSourceRangeFromTokenSpanUpTo(node, start, eol);
		// handler.emitNewline();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		if (comment != null)
			return new IASTNode[] { comment };
		else
			return NO_CHILDREN;
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
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
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
		final ASTPKGCommentStatement other = (ASTPKGCommentStatement) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		return true;
	}
}
