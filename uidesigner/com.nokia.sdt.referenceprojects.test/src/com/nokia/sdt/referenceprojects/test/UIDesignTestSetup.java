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

import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;
import com.nokia.sdt.workspace.WorkspaceContext;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;

import java.io.File;

import junit.framework.Assert;
import junit.framework.Test;

public class UIDesignTestSetup extends StatefulTestSetup implements ITestStateConstants {

	private File uiDesignFile;
	
	public UIDesignTestSetup(Test test, File uiDesignFile) {
		super(test);
		this.uiDesignFile = uiDesignFile;
	}
	
	@Override
	public String toString() {
		return this.getClass().getName() + "[" + uiDesignFile.getName() + "]";
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		IProject project = (IProject) getState(PROJECT);
		Assert.assertNotNull(project);

		Path path = new Path(uiDesignFile.getName());
		IResource resource = project.findMember(path);
		IDesignerDataModelSpecifier specifier = WorkspaceContext.getContext().findSpecifierForResource(resource);
		Assert.assertNotNull(specifier);
		putState(MODEL_SPECIFIER, specifier);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		removeState(MODEL_SPECIFIER);
	}
	
}
