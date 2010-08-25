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
package com.nokia.carbide.cpp.project.core.tests;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.osgi.framework.Bundle;

import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFilesType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfComponentType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.MakMakeRefType;
import com.nokia.carbide.cpp.api.test.support.xml.BldInfImportDataLoader;
import com.nokia.carbide.cpp.internal.api.sdk.BuildContextSBSv1;
import com.nokia.carbide.cpp.internal.project.utils.BldInfImportWrapper;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;

public class TestBldInfImportDataLoader extends TestCase {
	
	List<IProject> importedProject = new ArrayList<IProject>();
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testXMLReader () throws Exception {
		
		// Read the test data from XML....
		File testDataFile = pluginRelativeFile("Data/infImportData/infImportTestData.xml");
		assertTrue("infImportTestData.xml file does not exist! Can't do test.", testDataFile.exists());
		
		BldInfFilesType bldInfData = BldInfImportDataLoader.loadBldInfImportData(testDataFile.toURI().toURL());
		assertNotNull("Error loading test bld.inf xml data.", bldInfData);
		
		EList<BldInfFileType> infList = bldInfData.getBldInfFile();
		assertTrue("Size is zero (0) when reading bld.inf import data. Corrupted XML read.", infList.size() != 0);
		
		// Got this far so the XML is loaded
		for (BldInfFileType currInfData : infList){
			String projectName = currInfData.getProjectName();
			String sdkID = currInfData.getSdkId();
			IPath infPath = new Path(currInfData.getPath());
			
			assertTrue(currInfData.getPath().toLowerCase().endsWith("bld.inf"));
			
			// rootDir is optional, test for null first
			IPath rootDirPath = null;
			if (currInfData.getRootDirectory() != null){
				rootDirPath = new Path(currInfData.getRootDirectory().getPath());
			}
			
			ISymbianSDK sdk = SDKCorePlugin.getSDKManager().getSDK(sdkID, false);
			assertNotNull("SDK is not present. ID = " + sdkID, sdk);
			
			List<ISymbianBuildContext> buildConfigs = new ArrayList<ISymbianBuildContext>();
			for (BuildConfigType currConfigData : currInfData.getBuildConfigurations().getBuildConfig()){
				String plat = currConfigData.getPlatform();
				String target = currConfigData.getTarget().getName();
				BuildContextSBSv1 context = new BuildContextSBSv1(sdk, plat, target);
				buildConfigs.add(context);
			}
			
			// Add the selected components, if any, that need to be built. If empty, building from bld.inf is assumed
			List<String> componentsList = new ArrayList<String>();
			if (currInfData.getInfComponents() != null){
				for (InfComponentType currComponent : currInfData.getInfComponents().getInfComponent()){
					componentsList.add(currComponent.getName());
				}
			}
			// Add the makmake references
			List<String> makMakRefList = new ArrayList<String>();
			if (currInfData.getMakMakeRefs() != null){
				for (MakMakeRefType currMakMakeRef : currInfData.getMakMakeRefs().getMakMakeRef()){
					makMakRefList.add(currMakMakeRef.getName());
				}
			}
			
			BldInfImportWrapper infWrapper = new BldInfImportWrapper(projectName, rootDirPath, infPath, 
					componentsList, makMakRefList, buildConfigs);
			
			infWrapper.createProjectFromBldInf(false);
			IProject project = infWrapper.getProject();
			
			assertNotNull(project);
			assertEquals(project.getName(), projectName);
			importedProject.add(project);

		}
		
		assertEquals(2, importedProject.size());
	}
	
	
	private File pluginRelativeFile(String file) throws IOException {
		Bundle bundle = TestsPlugin.getDefault().getBundle();
		URL url = FileLocator.find(bundle, new Path("."), null);
		if (url == null)
			fail("could not make URL from bundle " + bundle + " and path " + file);
		url = FileLocator.resolve(url);
		TestCase.assertEquals("file", url.getProtocol());
		return new File(url.getPath(), file);
	}
	
}
