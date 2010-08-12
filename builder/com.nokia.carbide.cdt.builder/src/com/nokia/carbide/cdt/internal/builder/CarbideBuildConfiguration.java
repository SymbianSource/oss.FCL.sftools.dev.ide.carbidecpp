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
package com.nokia.carbide.cdt.internal.builder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.ICStorageElement;
import org.eclipse.cdt.core.settings.model.extension.CConfigurationData;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cdt.builder.builder.CarbideCPPBuilder;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.IEnvironmentVarsInfo;
import com.nokia.carbide.cdt.builder.project.ISISBuilderInfo;
import com.nokia.carbide.cdt.internal.api.builder.SISBuilderInfo2;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefineFactory;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.internal.api.sdk.BuildContextSBSv1;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildContext;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildInfo;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildContext;
import com.nokia.carbide.cpp.internal.api.sdk.SDKManagerInternalAPI;
import com.nokia.carbide.cpp.internal.api.sdk.sbsv2.SBSv2QueryUtils;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.cpp.internal.api.utils.core.TrackedResource;

@SuppressWarnings("deprecation")
public class CarbideBuildConfiguration implements ICarbideBuildConfiguration {
	
	static final String NOT_INSTALLED = "(SDK not found)"; //$NON-NLS-1$

	public static final String CARBIDE_STORAGE_ID = "CarbideConfigurationDataProvider"; //$NON-NLS-1$
	protected final static String SIS_BUILDER_DATA_ID = "SIS_BUILDER_DATA_ID"; //$NON-NLS-1$
	protected final static String ENV_VAR_DATA_ID = "ENV_VAR_DATA_ID"; //$NON-NLS-1$
	protected final static String ROM_BUILDER_DATA_ID = "ROM_BUILDER_DATA_ID"; //$NON-NLS-1$
	
	protected ISymbianBuildContext context;
	protected TrackedResource projectTracker;
	protected List<ISISBuilderInfo> sisBuilderInfoList;
	protected EnvironmentVarsInfo2 envVarsInfo;
	
	protected BuildConfigurationData buildConfigData;
	
	protected boolean rebuildNeeded;
	
	public CarbideBuildConfiguration(IProject project, ISymbianBuildContext context) {
		this.context = context;
		projectTracker = new TrackedResource(project);
		sisBuilderInfoList = new ArrayList<ISISBuilderInfo>(0);
		envVarsInfo = new EnvironmentVarsInfo2(project, context);
		
		buildConfigData = new BuildConfigurationData(this);
		
		rebuildNeeded = true;
	}
	
	public void loadFromStorage(ICConfigurationDescription projDes) throws CoreException {
		// get the storage for our data
		ICStorageElement rootStorage = projDes.getStorage(CARBIDE_STORAGE_ID, false);
		if (rootStorage != null) {
			for (ICStorageElement se : rootStorage.getChildren()) {
				if (se.getName().equals(SIS_BUILDER_DATA_ID)) {
					SISBuilderInfo2 sisInfo = new SISBuilderInfo2(projectTracker.getProject());
					sisInfo.loadFromStorage(se);
					
					// ignore old 1.2.x style entries
					if (!sisInfo.getPKGFileString().equals("(none)")) {
						sisBuilderInfoList.add(sisInfo);
					}
				} else if (se.getName().equals(ENV_VAR_DATA_ID)) {
					envVarsInfo.loadFromStorage(se);
				} 
				
				// Load build context specific settings.
				getBuildContext().loadConfigurationSettings(se);
				
			}
		} else {
			throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, IStatus.OK, "Unable to load Carbide settings for project " + projectTracker.getProject().getName() + ", " + getDisplayString(), null));
		}
	}
	
	public void saveToStorage(ICConfigurationDescription configDes) throws CoreException {
		ICStorageElement rootStorage = configDes.getStorage(CARBIDE_STORAGE_ID, true);
		if (rootStorage != null) {
			rootStorage.clear();
			
			for (ISISBuilderInfo sisInfo : sisBuilderInfoList) {
				SISBuilderInfo2 info = (SISBuilderInfo2)sisInfo;
				info.saveToStorage(rootStorage.createChild(SIS_BUILDER_DATA_ID));
			}
			
			envVarsInfo.saveToStorage(rootStorage.createChild(ENV_VAR_DATA_ID));
			
			// Save build context specific settings.
			this.getBuildContext().saveConfigurationSettings(rootStorage, getBuildContext());
		}
	}
	
	public ICarbideProjectInfo getCarbideProject() {
		// we need to get the project info from the build manager to ensure we
		// have the correct object.
		return CarbideBuilderPlugin.getBuildManager().getProjectInfo(projectTracker.getProject());
	}

	public String[] getErrorParserList(){
		return CarbideCPPBuilder.getParserIdArray(getErrorParserId());
	}
	
	public boolean saveConfiguration(boolean refreshFileSystem) {

		try {
			ICProjectDescription projDes = CoreModel.getDefault().getProjectDescription(projectTracker.getProject());
			if (projDes != null) {
			
				ICConfigurationDescription configDes = projDes.getConfigurationById(getBuildContext().getConfigurationID());
				if (configDes != null) {
					// save the CDT project description.  this saves all configs but that's the
					// only thing CDT allows at this point.
					CCorePlugin.getDefault().setProjectDescription(projectTracker.getProject(), projDes, true, new NullProgressMonitor());

					return true;
				}
			}
		} catch (CoreException e) {
			CarbideBuilderPlugin.log(e.getStatus());
		}

		return false;
	}

	public List<ISISBuilderInfo> getSISBuilderInfoList() {
		return sisBuilderInfoList;
	}
	
	public String toString(){
		return getDisplayString();
	}

	public IEnvironmentVarsInfo getEnvironmentVarsInfo() {
		return envVarsInfo;
	}

	public void setEnvironmentVarsInfo(IEnvironmentVarsInfo envVarsInfo) {
		if (envVarsInfo instanceof EnvironmentVarsInfo2) {
			this.envVarsInfo = (EnvironmentVarsInfo2)envVarsInfo;
		}
	}
		
	private int getErrorParserId(){
		String plat = this.getPlatformString();
		
		if (context instanceof ISBSv2BuildContext){
			String toolChain = ((ISBSv2BuildContext)context).getToolChain();
			if (toolChain.equalsIgnoreCase(ISBSv2BuildContext.TOOLCHAIN_ARM)){
				return ERROR_PARSERS_ARMVx;
			} else if (toolChain.equalsIgnoreCase(ISBSv2BuildContext.TOOLCHAIN_GCCE)){
				return ERROR_PARSERS_GCCE;
			} else if (toolChain.equalsIgnoreCase(ISBSv2BuildContext.TOOLCHAIN_WINSCW)){
				return ERROR_PARSERS_WINSCW;
			} 
		} else {
			// SBSV1
			if (plat.equals(ISBSv1BuildContext.EMULATOR_PLATFORM)){
				return ERROR_PARSERS_WINSCW;
			} else if (plat.startsWith("ARMV")){
				return ERROR_PARSERS_ARMVx;
			} else if (plat.equals(ISBSv1BuildContext.GCCE_PLATFORM)){
				return ERROR_PARSERS_GCCE;
			}
		}
	
		return ERROR_PARSERS_ALL;  
	}

	public CConfigurationData getBuildConfigurationData() {
		return buildConfigData;
	}
	
	public boolean valid() {
		return (SDKManagerInternalAPI.getMissingSdk(this.getSDK().getUniqueId()) == null);
	}

	public void validateAndSetProjectMarker() {
		if (valid() == false) {
			CarbideBuilderPlugin.createCarbideProjectMarker(this.getCarbideProject().getProject(), IMarker.SEVERITY_ERROR, "SDK " + this.getSDK().getUniqueId() + " from project \"" + this.getCarbideProject().getProject().getName() + "\" is unavailable. Please remap configurations by choosing Project > Properties > Carbide Build Configurations > Manage...", IMarker.PRIORITY_HIGH);
		}
	}
	
	public static String badSdkString() {
		return NOT_INSTALLED;
	}
	
	// Internal helper for missing SDK marking
	public static String toMarkedConfig(String config) {
		if (config == null)
			return null;
		if (SDKManagerInternalAPI.getMissingSdk(BuildContextSBSv1.getSDKIDFromConfigName(config)) != null) {
			return badSdkString() + config;
		}
		return config;
	}
	
	// Internal helper for converting back configurations with missing SDK marking
	public static  String fromMarkedConfig (String config) {
		if (config == null)
			return null;
		if (config.startsWith(badSdkString())) {
			return config.substring(badSdkString().length());
		}
		return config;
	}

	public IPath getTargetOutputDirectory() {
		if (context instanceof ISBSv2BuildContext){
			if (((ISBSv2BuildContext) context).getConfigQueryData() != null){
				return new Path(((ISBSv2BuildContext)context).getConfigQueryData().getOutputPathString());
			} else {
				return new Path("/" + SBSv2QueryUtils.BAD_EPOCROOT);
			}
		} else {
			ISymbianSDK sdk = getSDK();
			ISBSv1BuildContext v1Context = (ISBSv1BuildContext)context;
			ISBSv1BuildInfo sbsv1BuildInfo = (ISBSv1BuildInfo)sdk.getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER);
			String releasePlatform = sbsv1BuildInfo.getBSFCatalog().getReleasePlatform(v1Context.getBasePlatformForVariation());
			return sdk.getReleaseRoot().append(releasePlatform.toLowerCase()).append(getTargetString().toLowerCase());
		} 
	}
 	
	public boolean getRebuildNeeded() {
		return rebuildNeeded;
	}
	
	public void setRebuildNeeded(boolean value) {
		rebuildNeeded = value;
	}

	public ISymbianSDK getSDK() {
		return context.getSDK();
	}

	public String getPlatformString() {
		return context.getPlatformString();
	}

	public String getTargetString() {
		return context.getTargetString();
	}

	public String getDisplayString() {
		return context.getDisplayString();
	}

	public ISymbianBuildContext getBuildContext() {
		return context;
	}
	
	/**
	 * Check that at least one MMP in the project configuration has stdcpp support keyword
	 * @return
	 * @since 3.0
	 */
	public boolean hasSTDCPPSupport() {
		
		ICarbideProjectInfo cpi = this.getCarbideProject();
		
		List<ISymbianBuildContext> buildConfig = new ArrayList<ISymbianBuildContext>();
		List<IPath> normalMakMakePaths = new ArrayList<IPath>();
		List<IPath> testMakMakePaths = new ArrayList<IPath>();
		buildConfig.add(this.getBuildContext());
		EpocEngineHelper.getMakMakeFiles(cpi.getAbsoluteBldInfPath(), buildConfig, normalMakMakePaths, testMakMakePaths, new NullProgressMonitor());
		
		for (IPath mmpPath : normalMakMakePaths){
			if (EpocEngineHelper.hasSTDCPPSupport(cpi, mmpPath)){
				return true;
			}
		}
		
		return false;
	}

	public List<IDefine> getCompileTimeMacros() {
		
		List<IDefine> defines = new ArrayList<IDefine>();
		
		defines.addAll(context.getBuildMacros());
		defines.addAll(context.getCompilerPreincludeDefines());
		defines.addAll(context.getVariantHRHDefines());
		defines.addAll(context.getMetadataMacros());
		
		return defines;
		
	}
	
}
