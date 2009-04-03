/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.cpp.internal.api.utils.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public abstract class ControlVisitor {
	
	public interface Visitor {
		/**
		 * Visit one control.
		 * @return null to continue traveral, non-null to terminate
		 */
		Object visit(Control control);
	}

	/**
	 * Visit the given control and its children.
	 * Returns the first non-null value returned
	 * by the visitor, or null if all controls visited
	 * and no value was returned.
	 */
	public static Object visit(Composite control, Visitor v) {
		Object result = v.visit(control);
		if (result == null) {
			result = visitChildren(control, v);
		}
		return result;
	}
	
	/**
	 * Visit the given control's children, but not
	 * the control itself.
	 * Returns the first non-null value returned
	 * by the visitor, or null if all controls visited
	 * and no value was returned.
	 */
	public static Object visitChildren(Composite control, Visitor v) {
		Object result = null;
		Control[] children = control.getChildren();
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				Control child = children[i];
				if (child instanceof Composite) {
					result = visit((Composite)child, v);
				}
				else {
					result = v.visit(child);
				}
				if (result != null)
					break;
			}
		}
		return result;
	}
}
