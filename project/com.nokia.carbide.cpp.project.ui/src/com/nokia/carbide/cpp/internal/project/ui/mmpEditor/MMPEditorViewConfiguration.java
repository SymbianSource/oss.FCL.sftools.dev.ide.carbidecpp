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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor;

import org.eclipse.core.resources.IProject;

import com.nokia.carbide.cdt.builder.DefaultMMPViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;

public class MMPEditorViewConfiguration extends DefaultMMPViewConfiguration implements IMMPViewConfiguration {

	public MMPEditorViewConfiguration(ISymbianBuildContext context, IProject project) {
		super(project, context, new AcceptedNodesViewFilter());
	}
}
