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

package com.nokia.sdt.referenceprojects.test;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import java.io.File;

import junit.framework.Assert;
import junit.framework.Test;

public class ReferenceFileTestSetup extends StatefulTestSetup implements ITestStateConstants{

	File referenceFile;
	String relativePath;
	
	public ReferenceFileTestSetup(Test test, File referenceFile, String relPath) {
		super(test);
		this.referenceFile = referenceFile;
		this.relativePath = relPath;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		IProject project = (IProject) getState(PROJECT);
		Assert.assertNotNull(project);
		IFile file = project.getFile(relativePath);
		Assert.assertNotNull(file);
		putState(REFERENCE_FILE, referenceFile);
		putState(TARGET_FILE, file);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		removeState(REFERENCE_FILE);
		removeState(TARGET_FILE);
	}

}
