/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.cdt.builder.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.IPDOMManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.extension.ICarbidePrefsModifier;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;


public class TestCarbideProjectSettingsModifier extends TestCase {
	static IProject project;
	static ISymbianSDK symbianSDKS60_30;
	
	protected static final String PROJECT_NAME = "test-prj-modifier-project";
	

	
	// First thing we have to do is actually create a project in a workspace...
	protected void setUp() throws Exception {
		if (project == null){
			// turn off the indexer
			CCorePlugin.getIndexManager().setDefaultIndexerId(IPDOMManager.ID_NO_INDEXER);

			project = ProjectCorePlugin.createProject(PROJECT_NAME, null);

			ProjectCorePlugin.postProjectCreatedActions(project, "group/bld.inf", TestPlugin.getUsableBuildConfigs(), new ArrayList<String>(), "Debug MMP", null, new NullProgressMonitor());
		}
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	// Make sure the project nature is there
	@SuppressWarnings("deprecation")
	public void testModifier() throws Exception{
		assertNotNull(project);

        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
        assertNotNull(cpi);
        
        // get registry
        
		ICarbidePrefsModifier extProvider = checkForFeatureExtension();
        
        assertNotNull(extProvider); // will be null when SBSv1 support is removed
        
		String original = extProvider.getAbdlBuildArg(cpi
				.getDefaultConfiguration().getBuildContext());
		assertTrue(original.equals(""));

		extProvider.setAbldBuildArg(cpi.getDefaultConfiguration()
				.getBuildContext(), "FOO");

		cpi.getDefaultConfiguration().saveConfiguration(false);

		String test = extProvider.getAbdlBuildArg(cpi.getDefaultConfiguration()
				.getBuildContext());
		assertTrue(test.equals("FOO"));

		extProvider.setAbldBuildArg(cpi.getDefaultConfiguration()
				.getBuildContext(), original);

		cpi.getDefaultConfiguration().saveConfiguration(false);
		test = extProvider.getAbdlBuildArg(cpi.getDefaultConfiguration()
				.getBuildContext());
		assertTrue(original.equals(test));
	}
	
	@SuppressWarnings("deprecation")
	public static ICarbidePrefsModifier checkForFeatureExtension() {
		
			ICarbidePrefsModifier testExt = null;

			IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
			IExtensionPoint extensionPoint = extensionRegistry.getExtensionPoint("com.nokia.carbide.cdt.builder.carbideProjectPrefModifier"); //$NON-NLS-1$
			IExtension[] extensions = extensionPoint.getExtensions();
			
			for (int i = 0; i < extensions.length; i++) {
				IExtension extension = extensions[i];
				IConfigurationElement[] elements = extension.getConfigurationElements();
				Check.checkContract(elements.length == 1);
				IConfigurationElement element = elements[0];
				
				boolean failed = false;
				try {
					Object extObject = element.createExecutableExtension("class"); //$NON-NLS-1$
					if (extObject instanceof ICarbidePrefsModifier) {
						testExt = (ICarbidePrefsModifier)extObject;
						break;
					} else {
						failed = true;
					}
				} 
				catch (CoreException e) {
					failed = true;
				}
				
				if (failed) {
					CarbideBuilderPlugin.log(Logging.newStatus(CarbideBuilderPlugin.getDefault(), 
							IStatus.ERROR,
							"Unable to load com.nokia.carbide.cdt.builder.carbideProjectPrefModifier extension from " + extension.getContributor().getName()));
				}
			}
		
		return testExt;
	}
}
