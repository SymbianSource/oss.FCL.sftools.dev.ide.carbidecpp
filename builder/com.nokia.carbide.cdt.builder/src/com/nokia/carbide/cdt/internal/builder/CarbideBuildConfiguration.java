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
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.builder.CarbideCPPBuilder;
import com.nokia.carbide.cdt.builder.project.IBuildArgumentsInfo;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.IEnvironmentVarsInfo;
import com.nokia.carbide.cdt.builder.project.IROMBuilderInfo;
import com.nokia.carbide.cdt.builder.project.ISISBuilderInfo;
import com.nokia.carbide.cdt.internal.api.builder.SISBuilderInfo2;
import com.nokia.carbide.cpp.internal.api.sdk.SDKManagerInternalAPI;
import com.nokia.carbide.cpp.internal.api.sdk.SymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.cpp.internal.api.utils.core.TrackedResource;

public class CarbideBuildConfiguration extends SymbianBuildContext implements ICarbideBuildConfiguration {
	
	static final String NOT_INSTALLED = "(SDK not found)"; //$NON-NLS-1$

	protected static final String CARBIDE_STORAGE_ID = "CarbideConfigurationDataProvider"; //$NON-NLS-1$
	protected final static String SIS_BUILDER_DATA_ID = "SIS_BUILDER_DATA_ID"; //$NON-NLS-1$
	protected final static String ENV_VAR_DATA_ID = "ENV_VAR_DATA_ID"; //$NON-NLS-1$
	protected final static String ARGUMENTS_DATA_ID = "ARGUMENTS_DATA_ID"; //$NON-NLS-1$
	protected final static String ROM_BUILDER_DATA_ID = "ROM_BUILDER_DATA_ID"; //$NON-NLS-1$
	
	protected TrackedResource projectTracker;
	protected List<ISISBuilderInfo> sisBuilderInfoList;
	protected EnvironmentVarsInfo2 envVarsInfo;
	protected BuildArgumentsInfo buildArgumentsInfo;
	protected BuildConfigurationData buildConfigData;
	protected ROMBuilderInfo romBuilderInfo;
	

	public CarbideBuildConfiguration(IProject project, ISymbianBuildContext context) {
		super(context.getSDK(), context.getPlatformString(), context.getTargetString());
		projectTracker = new TrackedResource(project);
		sisBuilderInfoList = new ArrayList<ISISBuilderInfo>(0);
		envVarsInfo = new EnvironmentVarsInfo2(project, context);
		buildArgumentsInfo = new BuildArgumentsInfo(getSDK());
		buildConfigData = new BuildConfigurationData(this);
		romBuilderInfo = new ROMBuilderInfo(getSDK());
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
				} else if (se.getName().equals(ARGUMENTS_DATA_ID)) {
					buildArgumentsInfo.loadFromStorage(se);
				} else if (se.getName().equals(ROM_BUILDER_DATA_ID)) {
					romBuilderInfo.loadFromStorage(se);
				}
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
			buildArgumentsInfo.saveToStorage(rootStorage.createChild(ARGUMENTS_DATA_ID));
			romBuilderInfo.saveToStorage(rootStorage.createChild(ROM_BUILDER_DATA_ID));
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
				ICConfigurationDescription configDes = projDes.getConfigurationById(getDisplayString());
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
	
	@Override
	/**
	 * Compares two configurations to see if their display names are equivalent
	 */
	public boolean equals(Object obj) {
		if (obj instanceof ICarbideBuildConfiguration || obj instanceof ISymbianBuildContext){
			ISymbianBuildContext context = (ISymbianBuildContext)obj;
			if (context.getDisplayString().equals(this.getDisplayString())){
				return true;
			}
		} else {
			return false;
		}
		return false;
	}
	
	public int getErrorParserId(){
		String plat = this.getPlatformString();
		
		if (plat.equals(ISymbianBuildContext.EMULATOR_PLATFORM)){
			return ERROR_PARSERS_WINSCW;
		} else if (plat.startsWith("ARMV")){
			return ERROR_PARSERS_ARMVx;
		} else if (plat.equals(ISymbianBuildContext.THUMB_PLATFORM) ||
				   plat.equals(ISymbianBuildContext.ARMI_PLATFORM) ||
				   plat.equals(ISymbianBuildContext.ARM4_PLATFORM)){
			return ERROR_PARSERS_ARM_EKA1;
		} else if (plat.equals(ISymbianBuildContext.GCCE_PLATFORM)){
			return ERROR_PARSERS_GCCE;
		}
	
		return ERROR_PARSERS_ALL;  
	}

	public List<String> getBuiltinMacros() {
		List<String> macros = new ArrayList<String>();
		
		// add the macros that should always be defined
		macros.add("__SYMBIAN32__"); //$NON-NLS-1$
		macros.add("_UNICODE"); //$NON-NLS-1$
		
		ISymbianSDK sdk = getSDK();
		if (sdk != null && sdk.getOSVersion().getMajor() >= 9) {
			macros.add("__SUPPORT_CPP_EXCEPTIONS__"); //$NON-NLS-1$
		}
		
		if (getTargetString().equals(DEBUG_TARGET)) {
			macros.add("_DEBUG"); //$NON-NLS-1$
		} else {
			macros.add("NDEBUG"); //$NON-NLS-1$
		}
		
		return macros;
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
		if (SDKManagerInternalAPI.getMissingSdk(SymbianBuildContext.getSDKIDFromConfigName(config)) != null) {
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

	public IBuildArgumentsInfo getBuildArgumentsInfo() {
		return buildArgumentsInfo;
	}
	
	public void setBuildArgumentsInfo(IBuildArgumentsInfo buildArgumentsInfo) {
		if (buildArgumentsInfo instanceof BuildArgumentsInfo) {
			this.buildArgumentsInfo = (BuildArgumentsInfo)buildArgumentsInfo;
		}
	}

	public IROMBuilderInfo getROMBuildInfo() {
		return romBuilderInfo;
	}
}
