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
* Filters SDK's from the tree that don't contain at least one build configuration
* with Qt libraries.
*
*/
package com.nokia.carbide.cpp.internal.qt.core;

import org.eclipse.jface.viewers.TreeNode;

import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.ui.shared.BuildTargetTreeNode;

public class QtSDKFilter extends QtFilter {

	public boolean select(Object toTest) {
		if (toTest instanceof BuildTargetTreeNode) {
			// filter any SDK's that don't have any configs with Qt support
			for (Object child : ((BuildTargetTreeNode)toTest).getChildren()) {
				if (child instanceof TreeNode) {
					ISymbianBuildContext context = (ISymbianBuildContext)((TreeNode)child).getValue();
					if (getLibFile(context).exists()) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
