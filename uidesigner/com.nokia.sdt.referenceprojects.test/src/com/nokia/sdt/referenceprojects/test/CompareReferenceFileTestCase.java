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

import com.nokia.sdt.testsupport.FileHelpers;

import org.eclipse.core.resources.IFile;

import java.io.File;

public class CompareReferenceFileTestCase extends StatefulTestCase implements ITestStateConstants{

	File referenceFile;
	IFile targetFile;

	protected void setUp() throws Exception {
		super.setUp();
		referenceFile = (File) getState(REFERENCE_FILE);
		assertNotNull(referenceFile);
		targetFile = (IFile) getState(TARGET_FILE);
		assertNotNull(targetFile);
	}
	
	public void testFileEquals() throws Exception {
		File file = targetFile.getLocation().toFile();
		String msg = referenceFile.getName() + " , " + file.getName();
		FileHelpers.compareFiles(msg, referenceFile, file, false, true);
	}

}
