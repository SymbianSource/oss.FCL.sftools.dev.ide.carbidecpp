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
package com.nokia.carbide.cdt.builder.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.IPDOMManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.IBuildArgumentsInfo;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectModifier;
import com.nokia.carbide.cdt.builder.project.ISISBuilderInfo;
import com.nokia.carbide.cdt.internal.api.builder.SISBuilderInfo2;
import com.nokia.carbide.cpp.internal.api.sdk.SymbianBuildContext;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;


public class ProjectPropertiesTest extends TestCase {
	static IProject project;
	
	protected static final String PROJECT_NAME = "test-properties-project";
	
	private final String BLD_INF_PATH = "group\\";
	private final String BUILD_CONFIG_NAME1 = "Emulator Debug (WINSCW) [S60_3rd]";
	private final String BUILD_CONFIG_NAME2 = "Phone Debug (GCCE) [S60_3rd]";
	private final String BUILD_CONFIG_NAME3 = "Phone Release (ARMV5) [S60_3rd]";
	private final String TRUE = "true";
	private final String INF_COMPONENTS = "icons_scalable_dc.mk;HelloWorld.mmp";
	
	private final String PKG_FILE1 = "\\sis\\test.pkg";
	private final String KEY_FILE1 = "C:\\mycerts\\mykey.key";
	private final String CER_FILE1 = "C:\\mycerts\\mycert.cer";
	private final String SIS_ADD_OPTS = "-v";
	private final String SIS_OUTPUT = "..\\..\\sisout\\test.sis";
	private final String SISX_OUTPUT = "..\\..\\sisout\\test.sisx";
	private final String EPOCROOT_VAR = "%EPOCROOT%";
	
	// First thing we have to do is actually create a project in a workspace...
	protected void setUp() throws Exception {
		if (project == null){
			// turn off the indexer
			CCorePlugin.getIndexManager().setDefaultIndexerId(IPDOMManager.ID_NO_INDEXER);

			// there must be at least one build config to start with
			List<ISymbianBuildContext> configs = new ArrayList<ISymbianBuildContext>();
			configs.add(SymbianBuildContext.getBuildContextFromDisplayName(BUILD_CONFIG_NAME1));
			
			project = ProjectCorePlugin.createProject(PROJECT_NAME, null);
			ProjectCorePlugin.postProjectCreatedActions(project, "group/bld.inf", configs, new ArrayList<String>(), "Debug MMP", null, new NullProgressMonitor());
		}
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	// Make sure the project nature is there
	public void testProjectNature() throws Exception{
		assertNotNull(project);
		assertNotNull(project.getNature(CarbideBuilderPlugin.CARBIDE_PROJECT_NATURE_ID));
	}
	
	// Next we'll add the global project settings. We'll need this before writing specific configuration data
	public void testWriteProjectProperties() throws Exception{
        ICarbideProjectModifier cpm = CarbideBuilderPlugin.getBuildManager().getProjectModifier(project);
		assertNotNull(cpm);
		
		cpm.writeProjectSetting(ICarbideProjectInfo.PROJECT_RELATIVE_INFFILE_PROPS_KEY, BLD_INF_PATH);
		// The default build config would be the first in the list from the SDK/Config checked tree.
		cpm.writeProjectSetting(ICarbideProjectInfo.BLD_FROM_INF_PROPS_KEY, TRUE);
		cpm.writeProjectSetting(ICarbideProjectInfo.INF_COMPONENTS_PROPS_KEY, INF_COMPONENTS);
		cpm.saveChanges();
		
		// get a new copy of the info to make sure the changes we really applied
        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		assertEquals(BLD_INF_PATH, cpi.getProjectRelativeBldInfPath().toOSString());
		assertEquals(BUILD_CONFIG_NAME1, cpi.getDefaultBuildConfigName());
		assertTrue(cpi.isBuildingFromInf());
		assertEquals(2, cpi.getInfBuildComponents().size());
	}
	
	// Here we would write all the configuration data, one <configuration/> for each
	// checked item from the SDK/Config viewer.
	public void testCreateNewConfiguration() throws Exception{
        ICarbideProjectModifier cpm = CarbideBuilderPlugin.getBuildManager().getProjectModifier(project);
		assertNotNull(cpm);
		
		// At this point we should have a settings file and one configuration
		List<ICarbideBuildConfiguration> configList = cpm.getBuildConfigurations();
		assert(configList.size() == 1);
		
		// Create a new configuration (which gets written to disk).
		// WARNING: This test will only pass when you have an SDK that is installed that
		// corresponds to the SDK specified in the build config display name
		ISymbianBuildContext context = SymbianBuildContext.getBuildContextFromDisplayName(BUILD_CONFIG_NAME2);
		assertNotNull(context);
		
		ICarbideBuildConfiguration newConfig = cpm.createNewConfiguration(context, true);
		
		cpm.saveChanges();
		
		assertNotNull(newConfig);
		// Check that the config name was set correctly in the object
		assertEquals(BUILD_CONFIG_NAME2, newConfig.getDisplayString());
		
		// check that we can get back to the project from the config...
		assertEquals(cpm, newConfig.getCarbideProject());
		assertEquals(project, newConfig.getCarbideProject().getProject());
		
	}
	
	public void testReadConfigurationData() throws Exception {
		
		//TODO: Here need to read all config data on this call...
		// Right now we're just dealing with a single config property.
        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		assertNotNull(cpi);
		
		List<ICarbideBuildConfiguration> configList = cpi.getBuildConfigurations();
		assertEquals(2, configList.size());
		
		ICarbideBuildConfiguration config = cpi.getNamedConfiguration(BUILD_CONFIG_NAME1);
		assertEquals(BUILD_CONFIG_NAME1, config.getDisplayString());
	}
	
	public void testDeleteConfigurationData() throws Exception {
        ICarbideProjectModifier cpm = CarbideBuilderPlugin.getBuildManager().getProjectModifier(project);
		assertNotNull(cpm);
		
		ICarbideBuildConfiguration config = cpm.getNamedConfiguration(BUILD_CONFIG_NAME2);
		assertNotNull(config);
		cpm.deleteConfiguration(config);
		cpm.saveChanges();
		
		// get a new copy of the info to make sure the changes we really applied
        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);

        List<ICarbideBuildConfiguration> configList = cpi.getBuildConfigurations();
		assertEquals(1, configList.size());
	}
	
	public void testReadWriteMultipleConfigs(){
        ICarbideProjectModifier cpm = CarbideBuilderPlugin.getBuildManager().getProjectModifier(project);
		assertEquals(1, cpm.getBuildConfigurations().size());
		
		ISymbianBuildContext context2 = SymbianBuildContext.getBuildContextFromDisplayName(BUILD_CONFIG_NAME2);
		assertNotNull(context2);
		cpm.createNewConfiguration(context2, true);

		ISymbianBuildContext context3 = SymbianBuildContext.getBuildContextFromDisplayName(BUILD_CONFIG_NAME3);
		assertNotNull(context3);
		cpm.createNewConfiguration(context3, false);
		
		cpm.saveChanges();
		
		// get a new copy of the info to make sure the changes we really applied
        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);

		assertEquals(3, cpi.getBuildConfigurations().size());
		
		ICarbideBuildConfiguration defConfig = cpi.getDefaultConfiguration();
		assertEquals(BUILD_CONFIG_NAME2, defConfig.getDisplayString());
	}
	
	// Test the reading and writing of the default configuration
	public void testDefaultBuildConfig() {
        ICarbideProjectModifier cpm = CarbideBuilderPlugin.getBuildManager().getProjectModifier(project);
		assertEquals(3, cpm.getBuildConfigurations().size());
		
		ICarbideBuildConfiguration defConfig = cpm.getDefaultConfiguration();
		assertEquals(BUILD_CONFIG_NAME2, defConfig.getDisplayString());
		
		// make another configuration the default one...
		ICarbideBuildConfiguration newDefaultConfig = cpm.getNamedConfiguration(BUILD_CONFIG_NAME3);
		assertNotNull(newDefaultConfig);
		cpm.setDefaultConfiguration(newDefaultConfig);
		cpm.saveChanges();
		
		// get a new copy of the info to make sure the changes we really applied
        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);

		// Check to see that we got the new default config
		defConfig = cpi.getDefaultConfiguration();
		assertEquals(BUILD_CONFIG_NAME3, defConfig.getDisplayString());
	}
	
	public void testWritePKGData(){
        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		assertEquals(3, cpi.getBuildConfigurations().size());
		
		ICarbideBuildConfiguration defConfig = cpi.getDefaultConfiguration();
		assertNotNull(defConfig);
		
		ISISBuilderInfo sisBuilderInfo = new SISBuilderInfo2(project);
		sisBuilderInfo.setPKGFile(PKG_FILE1);
		sisBuilderInfo.setCertificate(CER_FILE1);
		sisBuilderInfo.setKey(KEY_FILE1);
		sisBuilderInfo.setAdditionalOptions(SIS_ADD_OPTS);
		sisBuilderInfo.setOutputSISFileName(SIS_OUTPUT);
		sisBuilderInfo.setSignedSISFileName(SISX_OUTPUT);
		sisBuilderInfo.setContentSearchLocation(EPOCROOT_VAR);
		sisBuilderInfo.setSigningType(ISISBuilderInfo.KEY_CERT_SIGN);
		sisBuilderInfo.setEnabled(true);
		defConfig.getSISBuilderInfoList().add(sisBuilderInfo);
		defConfig.saveConfiguration(true);
	}
	
	public void testReadPKGData(){
        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		assertEquals(3, cpi.getBuildConfigurations().size());
		
		ICarbideBuildConfiguration readConfig = cpi.getDefaultConfiguration();
		List<ISISBuilderInfo> readSisInfoList = readConfig.getSISBuilderInfoList();
		assertEquals(readSisInfoList.size(), 1);
		ISISBuilderInfo readSisInfo = readSisInfoList.get(0);
		assertNotNull(readSisInfo);
		assertEquals(PKG_FILE1, readSisInfo.getPKGFileString());
		assertEquals(CER_FILE1, readSisInfo.getCertificate());
		assertEquals(KEY_FILE1, readSisInfo.getKey());
		assertEquals(SIS_ADD_OPTS, readSisInfo.getAdditionalOptions());
		assertEquals(SIS_OUTPUT, readSisInfo.getUnsignedSISFileName());
		assertEquals(SISX_OUTPUT, readSisInfo.getSignedSISFileName());
		assertEquals(EPOCROOT_VAR, readSisInfo.getContentSearchLocation());
		assertEquals(ISISBuilderInfo.KEY_CERT_SIGN, readSisInfo.getSigningType());
		assertEquals(true, readSisInfo.isEnabled());
		
		assertEquals(false, readSisInfo.isCreateStubFormat());
	}
	
	public void testSBSv1BuildArgsReadWrite(){
		
		final String build_ARG = "-testbuild";
		final String clean_ARG = "-testclean";
		final String export_ARG = "-testexport";
		final String final_ARG = "-testfinal";
		final String freeze_ARG = "-testfreeze";
		final String library_ARG = "-testlibrary";
		final String makefile_ARG = "-testmakefile";
		final String resource_ARG = "-testresource";
		final String target_ARG = "-testtarget";
		
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		assertNotNull("Ooops, ICarbideProjectInfo is null, something bad happened.", cpi);
		
		ICarbideBuildConfiguration defaultConfig = cpi.getDefaultConfiguration();
		IBuildArgumentsInfo argInfo = defaultConfig.getBuildArgumentsInfo();
		
		// read the arguments
		String abldBuildArgs = argInfo.getAbldBuildArgs();
		String abldCleanArgs = argInfo.getAbldCleanArgs();
		String abldExportArgs = argInfo.getAbldExportArgs();
		String abldFinalArgs = argInfo.getAbldFinalArgs();
		String abldFreezeArgs = argInfo.getAbldFreezeArgs();
		String abldLibraryArgs = argInfo.getAbldLibraryArgs();
		String abldMakefileArgs = argInfo.getAbldMakefileArgs();
		String abldResourceArgs = argInfo.getAbldResourceArgs();
		String abldTargetArgs = argInfo.getAbldTargetArgs();
		
		abldBuildArgs   += build_ARG;
		abldCleanArgs   += clean_ARG;
		abldExportArgs  += export_ARG;
		abldFinalArgs   += final_ARG;
		abldFreezeArgs  += freeze_ARG;
		abldLibraryArgs += library_ARG;
		abldMakefileArgs  += makefile_ARG;
		abldResourceArgs  += resource_ARG;
		abldTargetArgs    += target_ARG;
		
		// set the argument
		argInfo.setAbldBuildArgs(abldBuildArgs);
		argInfo.setAbldCleanArgs(abldCleanArgs);
		argInfo.setAbldExportArgs(abldExportArgs);
		argInfo.setAbldFinalArgs(abldFinalArgs);
		argInfo.setAbldFreezeArgs(abldFreezeArgs);
		argInfo.setAbldLibraryArgs(abldLibraryArgs);
		argInfo.setAbldMakefileArgs(abldMakefileArgs);
		argInfo.setAbldResourceArgs(abldResourceArgs);
		argInfo.setAbldTargetArgs(abldTargetArgs);
		
		// make sure to update the entire object
		defaultConfig.setBuildArgumentsInfo(argInfo);
		
		// read the args from memory, make sure it's OK
		assertTrue("Failed to re-read build args", argInfo.getAbldBuildArgs().contains(build_ARG));
		assertTrue("Failed to re-read clean args", argInfo.getAbldCleanArgs().contains(clean_ARG));
		assertTrue("Failed to re-read export args", argInfo.getAbldExportArgs().contains(export_ARG));
		assertTrue("Failed to re-read final args", argInfo.getAbldFinalArgs().contains(final_ARG));
		assertTrue("Failed to re-read freeze args", argInfo.getAbldFreezeArgs().contains(freeze_ARG));
		assertTrue("Failed to re-read library args", argInfo.getAbldLibraryArgs().contains(library_ARG));
		assertTrue("Failed to re-read makefile args", argInfo.getAbldMakefileArgs().contains(makefile_ARG));
		assertTrue("Failed to re-read resource args", argInfo.getAbldResourceArgs().contains(resource_ARG));
		assertTrue("Failed to re-read target args", argInfo.getAbldTargetArgs().contains(target_ARG));
		
		// now write to files
		defaultConfig.saveConfiguration(false);
		
		// now read again
		defaultConfig = cpi.getDefaultConfiguration();
		argInfo = defaultConfig.getBuildArgumentsInfo();
		
		// read the args now that were pulled from disk, make sure it's OK
		assertTrue("Failed to re-read build args", argInfo.getAbldBuildArgs().contains(build_ARG));
		assertTrue("Failed to re-read clean args", argInfo.getAbldCleanArgs().contains(clean_ARG));
		assertTrue("Failed to re-read export args", argInfo.getAbldExportArgs().contains(export_ARG));
		assertTrue("Failed to re-read final args", argInfo.getAbldFinalArgs().contains(final_ARG));
		assertTrue("Failed to re-read freeze args", argInfo.getAbldFreezeArgs().contains(freeze_ARG));
		assertTrue("Failed to re-read library args", argInfo.getAbldLibraryArgs().contains(library_ARG));
		assertTrue("Failed to re-read makefile args", argInfo.getAbldMakefileArgs().contains(makefile_ARG));
		assertTrue("Failed to re-read resource args", argInfo.getAbldResourceArgs().contains(resource_ARG));
		assertTrue("Failed to re-read target args", argInfo.getAbldTargetArgs().contains(target_ARG));
	}
	
	public void testChangesNotApplied() {
		ICarbideProjectModifier cpm = CarbideBuilderPlugin.getBuildManager().getProjectModifier(project);
		cpm.writeProjectSetting(ICarbideProjectInfo.PROJECT_RELATIVE_INFFILE_PROPS_KEY, "test");
		cpm.writeProjectSetting(ICarbideProjectInfo.BLD_FROM_INF_PROPS_KEY, "false");
		
		// haven't called saveChanges yet so the above changes should not be applied yet
        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
        assertTrue(cpi.getProjectRelativeBldInfPath().toOSString().compareTo("test") != 0);
        assertTrue(cpi.isBuildingFromInf());
        
        cpm.saveChanges();

        // now they should be
        ICarbideProjectInfo cpi2 = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
        assertTrue(cpi2.getProjectRelativeBldInfPath().toOSString().compareTo("test") == 0);
        assertFalse(cpi2.isBuildingFromInf());
	}

	//	 DON'T ADD ANY TESTS BELOW HERE. LAST TEST WILL DELETE THE PROJECT
	
	public void testLastTestDeleteProject() throws Exception{
		project.delete(true, new NullProgressMonitor());
	}
}
