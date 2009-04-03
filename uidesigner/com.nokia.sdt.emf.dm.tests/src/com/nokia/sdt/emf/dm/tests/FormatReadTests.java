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

package com.nokia.sdt.emf.dm.tests;

import com.nokia.sdt.emf.dm.IDesignerData;
import com.nokia.cpp.internal.api.utils.core.ProjectUtils;

import org.custommonkey.xmlunit.XMLTestCase;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.osgi.framework.Bundle;

import java.io.*;
import java.net.URL;

public class FormatReadTests extends XMLTestCase {

	static final String PROJECT_NAME = "datamodel-format-read-tests";
	static final String FILE_NAME = "model.nxd";
	
	ResourceSet resourceSet;
	Resource resource;
	File projectDir;
	File inputModelFile;
	
	protected void setUp() throws Exception {
		IProject project = ProjectUtils.createAndOpenProject(PROJECT_NAME);
		projectDir = new File(project.getLocation().toOSString());		
	
		resourceSet = new ResourceSetImpl();
		Bundle bundle = Platform.getBundle("com.nokia.sdt.emf.dm.tests");
		Path path = new Path("/data/formatloadtest.nxd");
		URL url = Platform.find(bundle, path);
		url = Platform.asLocalURL(url);
		inputModelFile = new File(url.getFile());
		assertTrue(inputModelFile.exists());
	}

	protected void tearDown() throws Exception {
		ProjectUtils.closeAndDeleteProject(PROJECT_NAME);
	}

	public void testLoad() throws IOException  {
		IDesignerData designerData = loadModel();
		assertNotNull(designerData);
	}
	
	private IDesignerData loadModel() throws IOException {					
		URI uri = URI.createFileURI(inputModelFile.toString());
		resource = resourceSet.getResource(uri, true);
		
		IDesignerData designerData = (IDesignerData) resource.getContents().get(0);		
		return designerData;
	}
	
	public void testRoundTrip() throws Exception {
		IDesignerData designerData = loadModel();
		assertNotNull(designerData);
		
		File dmFile = new File(projectDir, FILE_NAME);	
		URI uri = URI.createFileURI(dmFile.toString());
		
		resource.setURI(uri);
		resource.save(null);
		
		Reader inputReader = new FileReader(inputModelFile);
		Reader outputReader = new FileReader(dmFile);
		try {
			assertXMLEqual(inputReader, outputReader);
		}
		finally {
			inputReader.close();
			outputReader.close();
		}
	}

}
