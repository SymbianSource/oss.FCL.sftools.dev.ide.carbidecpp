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
package com.nokia.carbide.cpp.qt.test;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.nokia.carbide.cpp.internal.qt.core.QtCorePlugin;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;

public class GeneralTests extends TestCase {
	
	public void testAddNature() throws CoreException {
		// create a simple project
		IProject project = ProjectCorePlugin.createProject("qtnaturetest", null);
		assertNotNull(project);

		// add the qt nature
		QtCorePlugin.addQtNature(project, null);
		
		if (!project.hasNature(QtCorePlugin.QT_PROJECT_NATURE_ID)) {
			fail();
		}
	}
}
