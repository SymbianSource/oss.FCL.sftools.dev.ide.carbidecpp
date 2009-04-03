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

import java.util.ArrayList;
import java.util.List;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGConditionalBlock;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGConditionalContainer;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGIfElseifContainer;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTPKGConditionalBlock extends ASTPKGStatement implements
		IASTPKGConditionalBlock {
	List<IASTPKGConditionalContainer> containers;

	public ASTPKGConditionalBlock(List<IASTPKGConditionalContainer> containers) {
		super();
		setListOfIfElseifElseContainers(containers);
		dirty = false;
	}

	public ASTPKGConditionalBlock(ASTPKGConditionalBlock statement) {
		super(statement);
		List<IASTPKGConditionalContainer> newContainers = new ArrayList<IASTPKGConditionalContainer>();
		for (IASTPKGConditionalContainer container : statement
				.getListOfIfElseifElseContainers()) {
			newContainers.add((IASTPKGConditionalContainer) container.copy());
		}
		setListOfIfElseifElseContainers(newContainers);
		dirty = statement.dirty;
	}

	public List<IASTPKGConditionalContainer> getListOfIfElseifElseContainers() {
		return containers;
	}

	public void setListOfIfElseifElseContainers(
			List<IASTPKGConditionalContainer> containers) {
		Check.checkArg(containers);
		if (this.containers != null) {
			for (IASTPKGConditionalContainer oldContainer : this.containers) {
				unparent(oldContainer);
			}
		}
		for (IASTPKGConditionalContainer newContainer : containers) {
			if (newContainer.getParent() != null) {
				unparent(newContainer); // we may got here because this
				// container was added to a list
			}
			parent(newContainer);
		}
		this.containers = containers;
		fireChanged();
		dirty = true;
	}

	public IASTNode copy() {
		return new ASTPKGConditionalBlock(this);
	}

	public IASTNode[] getChildren() {
		return containers.toArray(new IASTNode[0]);
	}

	public void rewrite(IRewriteHandler handler) {
		handler.emitText("IF"); //$NON-NLS-1$
		handler.emitNode(((IASTPKGIfElseifContainer) containers.get(0))
				.getCondition());
		// conditional handles newline
		// handler.emitNewline();
		handler.emitNode(containers.get(0).getStatements());
		if (containers.size() > 2) {
			for (int i = 1; i < containers.size() - 1; i++) {
				handler.emitText("ELSEIF"); //$NON-NLS-1$
				handler.emitNode(((IASTPKGIfElseifContainer) containers.get(i))
						.getCondition());
				// conditional handles newline
				// handler.emitNewline();
				handler.emitNode(containers.get(i).getStatements());
			}
		}
		if (containers.size() > 1) {
			handler.emitText("ELSE"); //$NON-NLS-1$
			handler.emitNewline();
			handler.emitNode(containers.get(containers.size() - 1)
					.getStatements());
		}
		handler.emitText("ENDIF"); //$NON-NLS-1$
		// a block is a statement, rewrite in statement with handle the new line
		// at the end
		// handler.emitNewline();
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
		result = prime * result
				+ ((containers == null) ? 0 : containers.hashCode());
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
		final ASTPKGConditionalBlock other = (ASTPKGConditionalBlock) obj;
		if (containers == null) {
			if (other.containers != null)
				return false;
		} else if (containers.size() != other.containers.size()) {
			return false;
		} else {
			for (int i = 0; i < containers.size(); i++) {
				if (!containers.get(i).equals(other.containers.get(i))) {
					return false;
				}
			}
		}
		return true;
	}

}
