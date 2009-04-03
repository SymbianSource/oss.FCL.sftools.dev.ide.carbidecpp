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
* Filters build configurations from the tree that don't contain Qt libraries.
*
*/
package com.nokia.carbide.cpp.internal.qt.core;

import org.eclipse.jface.viewers.TreeNode;

import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;

public class QtConfigFilter extends QtFilter {

	public boolean select(Object toTest) {
		if (toTest instanceof TreeNode) {
			ISymbianBuildContext context = (ISymbianBuildContext)((TreeNode)toTest).getValue();
			if (getLibFile(context).exists()) {
				return true;
			}
		}
		return false;
	}

}
