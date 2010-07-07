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

package com.nokia.cdt.debug.cw.symbian;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.IBinaryParser;
import org.eclipse.cdt.core.IBinaryParser.IBinaryObject;
import org.eclipse.cdt.core.ICExtensionReference;
import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.IBinary;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchDelegate;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Version;

import com.freescale.cdt.debug.cw.core.ProcessFactory;
import com.freescale.cdt.debug.cw.core.RemoteConnectionsTRKHelper;
import com.freescale.cdt.debug.cw.core.settings.ConnectionTypeInfo;
import com.freescale.cdt.debug.cw.core.settings.DebuggerCommonData;
import com.freescale.cdt.debug.cw.core.settings.Utils;
import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.ISISBuilderInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.internal.registry.Registry;
import com.nokia.cpp.internal.api.utils.core.PathUtils;

import cwdbg.PreferenceConstants;

@SuppressWarnings("restriction")
public class SettingsData {

	// NOTE: Many of these constants are mirrored in 
	// com.freescale.cdt.debug.cw.core.RemoteConnectionsTRKHelper
	// Due to dependency ordering, they can't be used directly
	// If you change any of these, please be sure to change them there, as well.
	
	public static final String PREFIX = "com.nokia.cdt.debug.cw.symbian.SettingsData"; //$NON-NLS-1$
	public static final String LaunchConfig_Emulator = PREFIX + ".LaunchConfig_Emulator"; //$NON-NLS-1$
	public static final String LaunchConfig_AppTRK = PREFIX + ".LaunchConfig_AppTRK"; //$NON-NLS-1$
	public static final String LaunchConfig_SysTRK = PREFIX + ".LaunchConfig_SysTRK"; //$NON-NLS-1$

	public static final String ATTR_originalName = "originalName"; //$NON-NLS-1$

	// Executable targeting
	public static final int LCS_ExeTargetingRule_AllInSDK = 0;
	public static final int LCS_ExeTargetingRule_AllInProject = 1;
	public static final int LCS_ExeTargetingRule_ExeList = 2;
	public static final int LCS_ExeTargetingRule_All = 3;
	public static final String LCS_ExecutableTargetingRule = PREFIX + ".LCS_ExecutableTargetingRule";
	public static final String AttachToProcessDialog_Selection = PREFIX + ".AttachToProcessName";

	// Launch Type IDs
	private static final String LAUNCH_TYPE_PREFIX = "com.nokia.cdt.debug.launch."; //$NON-NLS-1$
	public static final String CONTEXT_LAUNCH_TYPE_ID = LAUNCH_TYPE_PREFIX + "contextLaunch"; //$NON-NLS-1$
	public static final String EMULATION_LAUNCH_TYPE_ID = LAUNCH_TYPE_PREFIX + "emulationLaunch"; //$NON-NLS-1$
	public static final String APP_TRK_LAUNCH_TYPE_ID = LAUNCH_TYPE_PREFIX + "appTRKLaunch"; //$NON-NLS-1$
	public static final String SYS_TRK_LAUNCH_TYPE_ID = LAUNCH_TYPE_PREFIX + "systemTRKLaunch"; //$NON-NLS-1$
	public static final String ATTACH_LAUNCH_TYPE_ID = LAUNCH_TYPE_PREFIX + "attachLaunch"; //$NON-NLS-1$

	//================ Shadowed CW Preference Panels =================================
	// These match prefs in a pref panel that exist in the CodeWarrior IDE. The names
	// have to match exactly the name of the fields in the C++ pref struct.

	// Utility method: forms Shadowed Pref panel Name (the panel name shared with backend).
	private static String SPN(String cwPanelName, String cwPrefName)
	{
		return Utils.formShadowedPrefName(cwPanelName, cwPrefName);
	}

	//---< Serial Communication Panel> -------------------
	
	public static final String spn_SerialComm = "Serial Communications"; //$NON-NLS-1$

	public static final String spn_SerialComm_port= SPN(spn_SerialComm, "port"); //$NON-NLS-1$
	public static final String spn_SerialComm_rate= SPN(spn_SerialComm, "rate"); //$NON-NLS-1$
	public static final String spn_SerialComm_databits= SPN(spn_SerialComm, "databits"); //$NON-NLS-1$
	public static final String spn_SerialComm_stopbits= SPN(spn_SerialComm, "stopbits"); //$NON-NLS-1$
	public static final String spn_SerialComm_parity= SPN(spn_SerialComm, "parity"); //$NON-NLS-1$
	public static final String spn_SerialComm_flowcontrol= SPN(spn_SerialComm, "flowcontrol"); //$NON-NLS-1$
	public static final String spn_SerialComm_logdata= SPN(spn_SerialComm, "logdata"); //$NON-NLS-1$
	public static final String spn_SerialComm_serialConn= SPN(spn_SerialComm, "serial"); //$NON-NLS-1$
	public static final String spn_SerialComm_xtiConn= SPN(spn_SerialComm, "tcpip"); //$NON-NLS-1$

	//================= End of Shadowed panels ===========================================

	public static final String TARGET_PATH_INCLUDES_FILENAME = "TARGET_PATH_INCLUDES_FILENAME"; //$NON-NLS-1$

	public static final int J_PN_TrkTimeout_Default = 2000;
	
	private static String[] exceptionPropertyNames = null;
	private static IPath mainExeTargetPath = null;
	private static IPath mainExeHostPath = null;
	private static IPath mainExeWorkspaceRelativeMMPPath = null;
	
	public static String[] getExceptionPropertyNames()
	{
		if (exceptionPropertyNames == null)
		{
			final String[] exceptionPrefNames = { "exc_CONTROL_C", //$NON-NLS-1$
				"exc_FLT_INEXACT_RESULT", "exc_CONTROL_BREAK", //$NON-NLS-1$ //$NON-NLS-2$
				"exc_FLT_INVALID_OPERATION", "exc_DATATYPE_MISALIGNMENT", //$NON-NLS-1$ //$NON-NLS-2$
				"exc_FLT_STACK_CHECK", "exc_ACCESS_VIOLATION", "exc_FLT_OVERFLOW", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				"exc_IN_PAGE_ERROR", "exc_FLT_UNDERFLOW", "exc_NO_MEMORY", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				"exc_INT_DIVIDE_BY_ZERO", "exc_ILLEGAL_INSTRUCTION", //$NON-NLS-1$ //$NON-NLS-2$
				"exc_INT_OVERFLOW", "exc_NONCONTINUABLE_EXCEPTION", //$NON-NLS-1$ //$NON-NLS-2$
				"exc_PRIV_INSTRUCTION", "exc_INVALID_DISPOSITION", //$NON-NLS-1$ //$NON-NLS-2$
				"exc_STACK_OVERFLOW", "exc_ARRAY_BOUNDS_EXCEEDED", //$NON-NLS-1$ //$NON-NLS-2$
				"exc_DLL_NOT_FOUND", "exc_FLT_DENORMAL_OPERAND", //$NON-NLS-1$ //$NON-NLS-2$
				"exc_DLL_INIT_FAIL", "exc_FLT_DIVIDE_BY_ZERO", "exc_MS_CPLUS" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		};
			
			exceptionPropertyNames = new String[exceptionPrefNames.length];
			for (int i = 0; i < exceptionPrefNames.length; i++) {
				final String cwPanelName = "x86 Exceptions Panel"; //$NON-NLS-1$
				exceptionPropertyNames[i] = Utils.formShadowedPrefName(cwPanelName, exceptionPrefNames[i]);
			}
		}
		return exceptionPropertyNames;
	}


	static private void addSymbianSDKMapping(String epocRoot, ILaunchConfigurationWorkingCopy configuration) {
		if (epocRoot.length() > 0 && new File(epocRoot).exists())
			configuration.setAttribute(SymbianPlugin.Epoc_Root, epocRoot);
	}

	static public void addSymbianSDKMapping(Path executable, ILaunchConfigurationWorkingCopy configuration) {
		
		String epocRoot = "";
		String[] segs = executable.segments();
		for (int i = 0; i < segs.length; i++) {
			if (segs[i].equalsIgnoreCase("epoc32"))
				epocRoot = executable.removeLastSegments(segs.length - i).toOSString();				
		}
		addSymbianSDKMapping(epocRoot, configuration);
	}

	static public void addSymbianSDKMapping(IProject project, ILaunchConfigurationWorkingCopy configuration) {
		if (project != null) {
	        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			if (cpi == null){
				try {
					String exeLocation = configuration.getAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_NAME, "");
					Path exePath = new Path(exeLocation);
					addSymbianSDKMapping(exePath, configuration);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}else{
				String epocRoot = cpi.getDefaultConfiguration().getSDK().getEPOCROOT();
				addSymbianSDKMapping(epocRoot, configuration);
			}
		}
	}
	
	private static IBinary setProgramNameToFirstBinary(ILaunchConfigurationWorkingCopy configuration, IProject project) {
    	List<IBinary> applicableBinaries = getApplicableBinaries(configuration, project);
		IBinary binaryToUse = null;
		// try to find an exe to use
		for (IBinary binary : applicableBinaries) {
			if (binary.isExecutable()) {
				binaryToUse = binary;
				break;
			}
		}
		// if none, use the first in the list
		if (binaryToUse == null && !applicableBinaries.isEmpty())
			binaryToUse = applicableBinaries.get(0);
		if (binaryToUse != null) {
			IPath location = binaryToUse.getResource().getLocation();
			configuration.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_NAME, location.toOSString());
		}
		return binaryToUse;
	}

	private static List<IBinary> getApplicableBinaries(ILaunchConfiguration configuration, IProject project) {
		List<IBinary> applicableBinaries = new ArrayList<IBinary>();
		boolean isEmulatorConfig = isEmulatorConfiguration(configuration);
		try {
			ICProject cProject = CoreModel.getDefault().create(project);
			if (cProject != null) {
				IBinary[] binaries = cProject.getBinaryContainer().getBinaries();
				for (int i = 0; i < binaries.length; i++) {
					IPath path = getCanonicalPath(binaries[i].getResource().getLocation());
					int numSegs = path.segmentCount();
					if (numSegs >= 3) {
						if (isEmulatorConfig == "WINSCW".equalsIgnoreCase(path.segment(numSegs - 3)))
							applicableBinaries.add(binaries[i]);
					}
				}
			}
		} catch (CModelException e) {
			e.printStackTrace();
			displayWarningDialog(Messages.getString("SettingsData.38")); //$NON-NLS-1$
		}
		return applicableBinaries;
	}


	public static void setEmulationMainTab(ILaunchConfigurationWorkingCopy configuration, IProject project) {
		//======== Emulator: main tab ======================================
		//
		if (project != null) {
			String projectName = project.getName();
			configuration.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROJECT_NAME, projectName);
			configuration.setMappedResources( new IResource[] { project });
			
	        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			if (cpi != null) {
				ICarbideBuildConfiguration buildConfig = cpi.getDefaultConfiguration();
				String configName = projectName + " " + buildConfig.getDisplayString(); //$NON-NLS-1$
				setLaunchConfigurationName(configuration, configName);

				// make sure the selected build configuration of the current project
				// is an emulator build, otherwise warn them that we can't set default values.
				if (buildConfig.getPlatformString().compareTo(ISymbianBuildContext.EMULATOR_PLATFORM) == 0) {
					configuration.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_NAME, mainExeHostPath == null ? "" : mainExeHostPath.toOSString());

					if (isEmulatorRequired(buildConfig, mainExeHostPath, mainExeWorkspaceRelativeMMPPath)) {
						IPath releaseRoot = buildConfig.getSDK().getReleaseRoot();
						String winscwudeb = releaseRoot.toOSString() + File.separator + "WINSCW" + File.separator + buildConfig.getTargetString(); //$NON-NLS-1$ //$NON-NLS-2$

						String emulatorPath = winscwudeb + File.separator + "epoc.exe"; //$NON-NLS-1$
						configuration.setAttribute(DebuggerCommonData.Host_App_Path, getCanonicalPath(emulatorPath));			
					}
					else if (mainExeHostPath != null) {
						configuration.setAttribute(DebuggerCommonData.Host_App_Path, getCanonicalPath(mainExeHostPath.toOSString()));			
					}
				} else {
					displayWarningDialog(Messages.getString("SettingsData.37")); //$NON-NLS-1$
				}
			}
			else {
				if (mainExeHostPath != null && !mainExeHostPath.isEmpty()) {
					configuration.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_NAME, mainExeHostPath.toOSString()); 				 					

					// launch the emulator for non-"exe" files
					if ("exe".equalsIgnoreCase(mainExeHostPath.getFileExtension())) {
	 					configuration.setAttribute(DebuggerCommonData.Host_App_Path, mainExeHostPath.toOSString());			
					} else {
	   					String emulatorPath = mainExeHostPath.removeLastSegments(1).append("epoc.exe").toOSString();//$NON-NLS-1$
	 					configuration.setAttribute(DebuggerCommonData.Host_App_Path, getCanonicalPath(emulatorPath));			
					}
				} else {
					IBinary binary = setProgramNameToFirstBinary(configuration, project);
					if (binary.isExecutable()) {
						configuration.setAttribute(DebuggerCommonData.Host_App_Path, 
								getCanonicalPath(binary.getResource().getLocation()).toOSString());
					}
				}
			}
		}
	}
	
	private static String getCanonicalPath(String path) {
		try {
			return new File(path).getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	private static IPath getCanonicalPath(IPath path) {
		try {
			if (path == null)
				return null;
			return new Path(path.toFile().getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	public static void setEmulationDebuggerTab(ILaunchConfigurationWorkingCopy configuration, IProject project) {
		//============= Emulator debugger tab ============================================
		//
		configuration.setAttribute( DebugPlugin.ATTR_PROCESS_FACTORY_ID, ProcessFactory.ID );
		configuration.setAttribute( ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_START_MODE, ICDTLaunchConfigurationConstants.DEBUGGER_MODE_RUN );
		configuration.setAttribute( PreferenceConstants.J_PN_StopAtMainSymbol, "E32Main" ); //$NON-NLS-1$
		configuration.setAttribute(ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN, false);
		configuration.setAttribute( ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_ENABLE_VARIABLE_BOOKKEEPING, false );
		configuration.setAttribute( ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_ENABLE_REGISTER_BOOKKEEPING, false );
		configuration.setAttribute( PreferenceConstants.J_PN_SymbolLoadingRule, PreferenceConstants.J_PV_SymbolLoadingRule_Auto);
		configuration.setAttribute( PreferenceConstants.J_PN_ViewProcessOutput, true);
		configuration.setAttribute( PreferenceConstants.J_PN_ViewSystemMessage, false);
		configuration.setAttribute( SymbianPlugin.DebugTraceLaunchSetting, true);
	}
	
	public static void setX86ExceptionsTab(ILaunchConfigurationWorkingCopy configuration, IProject project) {
		//======= Emulator: x86 Exceptions tab ===================================
		// 
		String[] exceptionPropertyNames = getExceptionPropertyNames();
		// turn off all exceptions by default as the new emulators hit
		// a bunch of recoverable exceptions while starting up
		for (int i = 0; i < exceptionPropertyNames.length; i++) {
			configuration.setAttribute(exceptionPropertyNames[i], false);
		}
	}
	
	public static void setTrkMainTab(ILaunchConfigurationWorkingCopy configuration, IProject project) {
		//===== TRK :  main tab =================================================== 
		//
		configuration.setAttribute(PreferenceConstants.J_PN_RemoteProcessToLaunch, ""); //$NON-NLS-1$
		configuration.setAttribute(PreferenceConstants.J_PN_ProgramArguments, ""); //$NON-NLS-1$
		configuration.setAttribute(RemoteConnectionsTRKHelper.CONNECTION_ATTRIBUTE, Registry.CURRENT_CONNECTION_ID);

	    HashSet<String> set = new HashSet<String>();
	    set.add(ILaunchManager.DEBUG_MODE);
	    try {
    	    ILaunchDelegate preferredDelegate = configuration.getPreferredDelegate(set);
    	    if (preferredDelegate == null) {
    	        if (configuration.getType().getIdentifier().equals(APP_TRK_LAUNCH_TYPE_ID)) {
    	        	configuration.setPreferredLaunchDelegate(set, "com.nokia.carbide.cpp.edc.launch.appTRKLaunchDelegate"); //$NON-NLS-1$
    	        } else if (configuration.getType().getIdentifier().equals(SYS_TRK_LAUNCH_TYPE_ID)) {
    	        	configuration.setPreferredLaunchDelegate(set, "com.nokia.carbide.cpp.edc.launch.systemTRKLaunchDelegate"); //$NON-NLS-1$
    	        } else if (configuration.getType().getIdentifier().equals(ATTACH_LAUNCH_TYPE_ID)) {
    	        	configuration.setPreferredLaunchDelegate(set, "com.nokia.carbide.cpp.edc.launch.attachLaunchDelegate"); //$NON-NLS-1$
                } 
    	    }
	    } catch (CoreException e) {}

		if (project != null) {
			String projectName = project.getName();
			configuration.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROJECT_NAME, projectName);
			configuration.setMappedResources( new IResource[] { project });

	        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			if (cpi != null) {
				ICarbideBuildConfiguration buildConfig = cpi.getDefaultConfiguration();
				String configName = projectName + " " + buildConfig.getDisplayString(); //$NON-NLS-1$
				setLaunchConfigurationName(configuration, configName);

				// make sure the selected build configuration of the current project is not an emulator build
				// otherwise warn them that we can't set default values.
				if (buildConfig.getPlatformString().compareTo(ISymbianBuildContext.EMULATOR_PLATFORM) != 0) {
					configuration.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_NAME, mainExeHostPath == null ? "" : mainExeHostPath.toOSString());
					configuration.setAttribute(PreferenceConstants.J_PN_RemoteProcessToLaunch, mainExeTargetPath == null ? "" : PathUtils.convertPathToWindows(mainExeTargetPath));
				} else {
					displayWarningDialog(Messages.getString("SettingsData.37")); //$NON-NLS-1$
				}
			} else {
				IBinary binary = setProgramNameToFirstBinary(configuration, project);
				String exeName = null;
				if (mainExeHostPath != null)
					exeName = mainExeHostPath.lastSegment();
				else if (binary != null && binary.isExecutable())
					exeName = binary.getResource().getLocation().lastSegment();
				if (exeName != null)	
					configuration.setAttribute(PreferenceConstants.J_PN_RemoteProcessToLaunch, "C:\\sys\\bin\\" + exeName); //$NON-NLS-1$
			}
		}	
	}
	
	public static void setStopModeMainTab(ILaunchConfigurationWorkingCopy configuration, IProject project) {
		//===== StopMode :  main tab =================================================== 
		//		
		if (project != null) {
			String projectName = project.getName();
			configuration.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROJECT_NAME, projectName);
			configuration.setMappedResources( new IResource[] { project });

	        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			if (cpi != null) {
				ICarbideBuildConfiguration buildConfig = cpi.getDefaultConfiguration();
				String configName = projectName + " " + buildConfig.getDisplayString(); //$NON-NLS-1$
				setLaunchConfigurationName(configuration, configName);

				// make sure the selected build configuration of the current project is not an emulator build
				// otherwise warn them that we can't set default values.
				if (buildConfig.getPlatformString().compareTo(ISymbianBuildContext.EMULATOR_PLATFORM) != 0) {
					configuration.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_NAME, mainExeHostPath == null ? "" : mainExeHostPath.toOSString());					
				} else {
					displayWarningDialog(Messages.getString("SettingsData.37")); //$NON-NLS-1$
				}
			} else {
				setProgramNameToFirstBinary(configuration, project);
			}
		}	
	}
	
	public static void setTrkDebuggerTab(ILaunchConfigurationWorkingCopy configuration, IProject project) {
		//======  TRK:  debugger tab ================================================================
		//
		configuration.setAttribute( DebugPlugin.ATTR_PROCESS_FACTORY_ID, ProcessFactory.ID );			
		configuration.setAttribute( ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_START_MODE, ICDTLaunchConfigurationConstants.DEBUGGER_MODE_RUN );
		configuration.setAttribute( PreferenceConstants.J_PN_StopAtMainSymbol, "E32Main" ); //$NON-NLS-1$
		configuration.setAttribute( ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN, false );
		configuration.setAttribute( ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_ENABLE_VARIABLE_BOOKKEEPING, false );
		configuration.setAttribute( ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_ENABLE_REGISTER_BOOKKEEPING, false );
		configuration.setAttribute( PreferenceConstants.J_PN_ViewUnframedData, true );
		configuration.setAttribute( PreferenceConstants.J_PN_ViewCommMessages, false );
		configuration.setAttribute( PreferenceConstants.J_PN_DefaultInstructionSet, PreferenceConstants.J_PV_DefaultInstructionSet_Auto );
		configuration.setAttribute( PreferenceConstants.J_PN_TRKMessageTimeout, J_PN_TrkTimeout_Default );
	}
	
	public static void setStopModeDebuggerTab(ILaunchConfigurationWorkingCopy configuration, IProject project) {
		//======  Stop Mode:  debugger tab ================================================================
		//
		configuration.setAttribute( DebugPlugin.ATTR_PROCESS_FACTORY_ID, ProcessFactory.ID );
		configuration.setAttribute( ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_START_MODE, ICDTLaunchConfigurationConstants.DEBUGGER_MODE_RUN );
		configuration.setAttribute( PreferenceConstants.J_PN_StopAtMainSymbol, "E32Main" ); //$NON-NLS-1$
		configuration.setAttribute( ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN, false );
		configuration.setAttribute( ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_ENABLE_VARIABLE_BOOKKEEPING, false );
		configuration.setAttribute( ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_ENABLE_REGISTER_BOOKKEEPING, false );
		configuration.setAttribute( PreferenceConstants.J_PN_RomImgStartAddress , 0);
		configuration.setAttribute( PreferenceConstants.J_PN_DebugRunFromStart, PreferenceConstants.J_PV_DebugRunFromStart_Debug);
		configuration.setAttribute( PreferenceConstants.J_PN_DefaultInstructionSet, PreferenceConstants.J_PV_DefaultInstructionSet_Auto );
		configuration.setAttribute( PreferenceConstants.J_PN_TargetProcessor, 8);
		configuration.setAttribute( PreferenceConstants.J_PN_RunTargetInitFile, false);
		configuration.setAttribute( PreferenceConstants.J_PN_TargetInitFilePath, ""); //$NON-NLS-1$
		configuration.setAttribute( PreferenceConstants.J_PN_RunMemConfigFile, false);
		configuration.setAttribute( PreferenceConstants.J_PN_MemConfigFilePath, ""); //$NON-NLS-1$
		configuration.setAttribute( PreferenceConstants.J_PN_ResetTarget, false);
	}

	public static void setSerialConnTab(ILaunchConfigurationWorkingCopy configuration, IProject project) {
		//======== Serial Connection Tab =====================================
		//
		configuration.setAttribute(PreferenceConstants.J_PN_TrkConnectionType, PreferenceConstants.J_PV_TrkConnectionType_Serial);
		configuration.setAttribute(RemoteConnectionsTRKHelper.USES_REMOTE_CONNECTIONS_ATTRIBUTE, true);
		IConnection defaultConnection = RemoteConnectionsTRKHelper.getFirstCompatibleConnection();
		if (defaultConnection != null)
			RemoteConnectionsTRKHelper.setRemoteConnectionAttribute(configuration, defaultConnection);
	}
	
	public static void setFileTransferTab(ILaunchConfigurationWorkingCopy configuration, IProject project) {

		//======== File Transfer tab ============================================
		//
		configuration.setAttribute( PreferenceConstants.J_PN_FilesToTransfer, "" ); //$NON-NLS-1$
		configuration.setAttribute( TARGET_PATH_INCLUDES_FILENAME, "true" ); //$NON-NLS-1$
		
		// only do this for system TRK configurations
		if (project != null) {
			
	        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			if (cpi != null) {
				ICarbideBuildConfiguration buildConfig = cpi.getDefaultConfiguration();
				
				String defaultTargetPath = "C:\\"; //$NON-NLS-1$
				if (buildConfig.getSDK().isEKA2()) {
					// C:\\sys\bin for eka2
					defaultTargetPath += "sys\\bin\\"; //$NON-NLS-1$
				}
				String prefsData = "";
				
				// we'll add all binaries that could be built by the config, but will only enabled those
				// that are actually being built.
				List<IPath> builtComponents = EpocEngineHelper.getComponentsBuiltByConifguration(buildConfig);
				
				// add the binaries and any resource-type files generated by all mmp files in the project
				for (IPath mmp : EpocEngineHelper.getMMPFilesForBuildConfiguration(buildConfig)) {
					String enabled = builtComponents.contains(mmp) ? ",1," : ",0,";
					IPath hp = EpocEngineHelper.getHostPathForExecutable(buildConfig, mmp);
					if (hp != null) {
						IPath tp = EpocEngineHelper.getTargetPathForExecutable(buildConfig, mmp);
						if (tp == null) {
							tp = PathUtils.createPath(defaultTargetPath).append(hp.lastSegment());
						}
						prefsData += hp.toOSString() + "," + PathUtils.convertPathToWindows(tp) + enabled; //$NON-NLS-1$
					}
					
					HashMap<String, String> hostTargetRSRCMap = EpocEngineHelper.getHostAndTargetResources(buildConfig, mmp);
					// Add resource files...
					for (String currHostRSRC : hostTargetRSRCMap.keySet()) {
						prefsData += currHostRSRC + "," + hostTargetRSRCMap.get(currHostRSRC) + new Path(currHostRSRC).lastSegment() + enabled; //$NON-NLS-1$
					}
				}
				
				// check the project for image makefiles
				HashMap<String, String> hostTargetImagesMap = EpocEngineHelper.getHostAndTargetImages(buildConfig);
				for (String currHostImg : hostTargetImagesMap.keySet()) {
					prefsData += currHostImg + "," + hostTargetImagesMap.get(currHostImg) + ",1,"; //$NON-NLS-1$ //$NON-NLS-2$
				}
				
				// Add the files to transfer to the pref
				configuration.setAttribute( PreferenceConstants.J_PN_FilesToTransfer, prefsData );
			}
		}
	}
	
	public static void setInstallationTab(ILaunchConfigurationWorkingCopy configuration, IProject project) {

		//=========== AppTRK Installation tab ==========================================
		//
		configuration.setAttribute( PreferenceConstants.J_PN_SisFileHostPath, "" ); //$NON-NLS-1$
		configuration.setAttribute( PreferenceConstants.J_PN_SisFileTargetPath, "C:\\data\\" ); //$NON-NLS-1$
		configuration.setAttribute( PreferenceConstants.J_PN_AlwaysInstallSisFile, false );
		configuration.setAttribute( PreferenceConstants.J_PN_HideInstallerUI, true );
		configuration.setAttribute( PreferenceConstants.J_PN_InstallToDrive, 2 ); // C:

		if (project != null) {

	        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			if (cpi != null) {
				List<ISISBuilderInfo> sisInfoList = cpi.getDefaultConfiguration().getSISBuilderInfoList();
				if (sisInfoList != null && sisInfoList.size() > 0) {
					// grab the last one in case they are embedded earlier built sis files
					// in later built ones
					configuration.setAttribute(PreferenceConstants.J_PN_SisFileHostPath, sisInfoList.get(sisInfoList.size() - 1).getFinalSISFullPath().toOSString());
				}
			}
		}
	
	}

	public static void setExecutablesTab(ILaunchConfigurationWorkingCopy configuration, String settingsGroup, IProject project) {
		
		//======== Executables Tab =====================================
		// Defaults to all executables from the same SDK
		configuration.setAttribute( LCS_ExecutableTargetingRule, LCS_ExeTargetingRule_AllInSDK ); //$NON-NLS-1$
		configuration.setAttribute( PreferenceConstants.J_PN_ExecutablesToDebug, "" ); //$NON-NLS-1$
		configuration.setAttribute(	PreferenceConstants.J_PN_SymbolLoadingRule,	PreferenceConstants.J_PV_SymbolLoadingRule_Auto );
	}

	/**
	 * There are "internal preferences" that are not visible to (hence not changeable by) users. 
	 * We set the values for them for different launch configurations. But when we change any 
	 * of the values by re-coding, the change won't be picked up by existing launch configurations.
	 *      
	 * This function is supposed to fix that problem. It should be called on every debugger start.
	 * 
	 * Any internal preferences should be set here.
	 * 
	 */ 
	public static void setInternalPreferences(ILaunchConfigurationWorkingCopy configuration, String settingsGroup)
	{
		// All but emulator debugger support OS Data View (kernel aware view) at present.
		// 
		if (settingsGroup.equals(LaunchConfig_Emulator)) 
			configuration.setAttribute( PreferenceConstants.J_PN_SupportOSView, false );
		else
			configuration.setAttribute( PreferenceConstants.J_PN_SupportOSView, true );
		
		if (settingsGroup.equals(LaunchConfig_AppTRK) || 
			    settingsGroup.equals(LaunchConfig_SysTRK) ||
			    settingsGroup.equals(LaunchConfig_Emulator))
		{
			configuration.setAttribute( PreferenceConstants.J_PN_IsSystemModeDebug, false );
		}
	}

	// a convenience function.
	public static void setInternalPreferences(ILaunchConfiguration originalConfig, String settingsGroup)
	{
		ILaunchConfigurationWorkingCopy wc = null;
		try {
			wc = originalConfig.getWorkingCopy();
			
			if (wc == null)
				return;
			
			setInternalPreferences(wc, settingsGroup);
			
			wc.doSave();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	public static void setDefaults(ILaunchConfigurationWorkingCopy configuration, String settingsGroup, IProject project)
	{
		if (project != null) {
	        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			if (cpi != null) {
				ICarbideBuildConfiguration buildConfig = cpi.getDefaultConfiguration();
				// get the list of binaries that are being built for the build config and their corresponding mmp's
				List<IPath> exePaths = new ArrayList<IPath>(0);
				List<IPath> mmpPaths = new ArrayList<IPath>(0);
				EpocEngineHelper.getPathToAllExecutables(buildConfig, new ArrayList<IPath>(), exePaths, new ArrayList<IPath>(), mmpPaths);
				if (exePaths.size() > 0) {
					// pick the first exe in the list, otherwise the first binary in the list
					IPath exePath = exePaths.get(0);
					IPath mmpPath = mmpPaths.get(0);
					for (int i=0; i<exePaths.size(); i++) {
						String fileExt = exePaths.get(i).getFileExtension();
						if (fileExt != null && fileExt.compareToIgnoreCase("exe") == 0) {
							exePath = exePaths.get(i);
							mmpPath = mmpPaths.get(i);
							break;
						}
					}
					
					setDefaults(configuration, settingsGroup, project, mmpPath, exePath);
					return;
				}
			}
		}

		setDefaults(configuration, settingsGroup, project, null, null);
	}
	
	/**
	 * Sets the default values for the launch configuration.
	 * @param configuration
	 * @param settingsGroup
	 * @param project
	 * @param workspaceRelativeMMPPath should not be null for Carbide project (but can be for debug only projects)
	 * @param mainExePath should be null for Carbide projects but should not be null for debug projects
	 */
	public static void setDefaults(ILaunchConfigurationWorkingCopy configuration, String settingsGroup, IProject project, IPath workspaceRelativeMMPPath, IPath mainExePath)
	{
		// set the main executable target and host path here, so that we don't have to call into the EPOC engine every time.
		// calling into EPOC engine every time could be expensive for large projects..
		mainExeHostPath = getCanonicalPath(mainExePath);
		mainExeTargetPath = null;
		mainExeWorkspaceRelativeMMPPath = workspaceRelativeMMPPath;
		
		if (workspaceRelativeMMPPath != null) {
			if (project != null) {
				ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
				if (cpi != null) {			
					ICarbideBuildConfiguration buildConfig = cpi.getDefaultConfiguration();

					// do not canonicalize
					mainExeTargetPath = EpocEngineHelper.getTargetPathForExecutable(buildConfig, workspaceRelativeMMPPath);
				}
			}
		}
		
		// this can be called from various places.  launch configurations can be created by clicking
		// the New button on the Eclipse launch configuration dialog, from our launch wizard, or from
		// the import executable wizard.  the import executable wizard creates a non-managed make project
		// and hence most of the logic below will not work.

		// ==== common to all =======================================================================
		//
		
		// Specify which debugger plugin to use.
		configuration.setAttribute(DebuggerCommonData.Host_App_Path, ""); //$NON-NLS-1$
		configuration.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROJECT_NAME, ""); //$NON-NLS-1$
		configuration.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_NAME, ""); //$NON-NLS-1$
		configuration.setAttribute(ICDTLaunchConfigurationConstants.ATTR_BUILD_BEFORE_LAUNCH, ICDTLaunchConfigurationConstants.BUILD_BEFORE_LAUNCH_USE_WORKSPACE_SETTING);

		if (project != null) {
			configuration.setMappedResources( new IResource[] { project });
		    ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		    configuration.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROJECT_BUILD_CONFIG_ID, 
		    		cpi != null ? cpi.getDefaultBuildConfigName() : ""); //$NON-NLS-1$
		}
		
		// set rom log file defaults.  do this for all launch types since it shouldn't hurt
		// and could be easy to miss some launch types that use this tab
		configuration.setAttribute(PreferenceConstants.J_PN_ParseRomLogFile, false);
		configuration.setAttribute(PreferenceConstants.J_PN_RomLogFilePath, ""); //$NON-NLS-1$
		configuration.setAttribute(PreferenceConstants.J_PN_SymbianKitEpoc32Dir, ""); //$NON-NLS-1$
		configuration.setAttribute(PreferenceConstants.J_PN_LogUnresolved, false);

		configuration.setAttribute(ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_ID, SymbianDebugger.DEBUGGER_ID);

		setExecutablesTab(configuration, settingsGroup, project);
		
		setInternalPreferences(configuration, settingsGroup);

		if (settingsGroup.equals(LaunchConfig_Emulator)) {
			setEmulationMainTab(configuration, project);
			setEmulationDebuggerTab(configuration, project);
			setX86ExceptionsTab(configuration, project);			
		}
		
		if (settingsGroup.equals(LaunchConfig_AppTRK) || 
			    settingsGroup.equals(LaunchConfig_SysTRK))
		{
			setSerialConnTab(configuration, project);
		}

		if (settingsGroup.equals(LaunchConfig_AppTRK)) {
			setInstallationTab(configuration, project);

			// set this so it's there in the config and the apply button doesn't become
			// enabled when switching to the tab
			configuration.setAttribute(PreferenceConstants.J_PN_FilesToTransfer, ""); //$NON-NLS-1$
			configuration.setAttribute(TARGET_PATH_INCLUDES_FILENAME, "true"); //$NON-NLS-1$
		}
		
		if (settingsGroup.equals(LaunchConfig_SysTRK)) {
			setFileTransferTab(configuration, project);
		}

		if (settingsGroup.equals(LaunchConfig_AppTRK) || settingsGroup.equals(LaunchConfig_SysTRK)) {
			setTrkMainTab(configuration, project);
			setTrkDebuggerTab(configuration, project);
			
			// TRK Debugging: specify TRK protocol plugin.
			//
			ConnectionTypeInfo connTI = new ConnectionTypeInfo(
					"Carbide TRK", // Internal ID //$NON-NLS-1$
					"Symbian MetroTRK", // Display name //$NON-NLS-1$
					spn_SerialComm); // Pref panel name

			DebuggerCommonData.setLaunchConfigConnSettings(
					configuration,
					connTI, 
					"Symbian MetroTRK Protocol", //$NON-NLS-1$
					""); // Protocol plugin name //$NON-NLS-1$
		}

		// Add the Symbian OS SDK Mapping.
		addSymbianSDKMapping(project, configuration);

		configuration.setAttribute(ATTR_originalName, configuration.getName());

	}

	/**
	 * Should the debugger launch the Symbian emulator (epoc.exe) as the debug process?
	 * This should be true in all cases except when the executable is an '.exe'.
	 * These can be launched directly. However there is a problem doing this with OS9.1
	 * (it will fail after the first launch) so we still require the emulator in this case.	
	 * @param buildConfig - current build config
	 * @return - true if we should launch epoc.exe as the debug process
	 */
	public static boolean isEmulatorRequired(ICarbideBuildConfiguration buildConfig, IPath mainExeHostPath, IPath mainExeWorkspaceRelativeMMPPath) {
		
        ICarbideProjectInfo cpi = buildConfig.getCarbideProject();
        
		if (cpi != null && mainExeHostPath != null) {
			if ("exe".equalsIgnoreCase(mainExeHostPath.getFileExtension()) && !isSharedLib(cpi.getProject(), mainExeHostPath)) { //$NON-NLS-1$
				Version version = cpi.getDefaultConfiguration().getSDK().getOSVersion();
				if (!(version.getMajor() == 9 && version.getMinor() == 1)) {
					return false;
				}

				// for 9.1 only and exe extension, check the UID2 and if it's 0x100039CE then
				// it needs the emulator, otherwise it doesn't.  see bugzilla #1822 for details.  this
				// is basically to work around an issue in the 9.1 emulator.
				if (mainExeWorkspaceRelativeMMPPath != null) {
					String uid2 = EpocEngineHelper.getUID2(buildConfig, mainExeWorkspaceRelativeMMPPath);
					if (uid2 != null) {
						if (uid2.compareToIgnoreCase("0x100039CE") != 0) { //$NON-NLS-1$
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	public static boolean isEmulatorRequired(IPath mainExeHostPath)
	{
		List<ISymbianSDK> sdkList = new ArrayList<ISymbianSDK>();
		sdkList = SDKCorePlugin.getSDKManager().getSDKList();
		
		for (ISymbianSDK currSDK : sdkList){
			if (new Path(currSDK.getEPOCROOT()).isPrefixOf(mainExeHostPath))
			{
			       // Apparently this only works correctly with the S60 emulator.
		        if (!currSDK.isS60() &&
		        		!currSDK.getFamily().equalsIgnoreCase(ISymbianSDK.TECHVIEW_FAMILY_ID))
		        	return true;
		        
				if ("exe".equalsIgnoreCase(mainExeHostPath.getFileExtension())) { //$NON-NLS-1$
					Version version = currSDK.getOSVersion();
					if (!(version.getMajor() == 9 && version.getMinor() == 1)) {
						return false;
					}
				}
				
			}
		}
		
		return true;
	}
		
	// Check if the given file is a shared library (DLL or APP in Symbian ).
	//
	static public boolean isSharedLib(IProject project, IPath exePath) {
		int fileType;
		ICExtensionReference[] parserRef;
		
		try {
			parserRef = CCorePlugin.getDefault().getBinaryParserExtensions(project);
		} catch (Exception e) {
			return false;
		};
		
		for (int i = 0; i < parserRef.length; i++) {
			try {
				IBinaryParser parser = (IBinaryParser)parserRef[i].createExtension();
				IBinaryObject exe = (IBinaryObject)parser.getBinary(exePath);
				if (exe != null) {
					fileType = exe.getType();
					return fileType == IBinaryParser.IBinaryFile.SHARED;
				}
			} catch (Exception e) {
			}
		}

		try {
			IBinaryParser parser = CCorePlugin.getDefault().getDefaultBinaryParser();

			IBinaryObject exe = (IBinaryObject)parser.getBinary(exePath);
			if (exe != null) {
				fileType = exe.getType();
				return fileType == IBinaryParser.IBinaryFile.SHARED;
			}
		} catch (Exception e) {
		}
		return false;
	}

	private static void displayWarningDialog(final String msg) {
		//  Display warning dialog
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if(window == null){
			IWorkbenchWindow windows[] = PlatformUI.getWorkbench().getWorkbenchWindows();
			window = windows[0];
		}

		final Shell shell = window.getShell();
		//using syncExec could cause a dead-lock
		//that is why asyncExec is used
		shell.getDisplay().asyncExec( new Runnable() {
			public void run() {
				MessageDialog.openWarning(shell, Messages.getString("SettingsData.70"), msg); //$NON-NLS-1$
			}
		} );
	}
	
	public static boolean isAppTRKConfiguration(ILaunchConfiguration configuration) {
		try {
			ILaunchConfigurationType configurationType = configuration.getType();
			String id = configurationType.getIdentifier();
			return id.equals(APP_TRK_LAUNCH_TYPE_ID);
		} catch (CoreException e) {
		}
		
		return false;
	}
	
	public static boolean isSysTRKConfiguration(ILaunchConfiguration configuration) {
		try {
			ILaunchConfigurationType configurationType = configuration.getType();
			String id = configurationType.getIdentifier();
			return id.equals(SYS_TRK_LAUNCH_TYPE_ID);
		} catch (CoreException e) {
		}
		
		return false;
	}
	
	public static boolean isEmulatorConfiguration(ILaunchConfiguration configuration) {
		try {
			ILaunchConfigurationType configurationType = configuration.getType();
			String id = configurationType.getIdentifier();
			return id.equals(EMULATION_LAUNCH_TYPE_ID);
		} catch (CoreException e) {
		}
		
		return false;
	}
	
	public static void setProcessToLaunch(ILaunchConfigurationWorkingCopy configuration, IPath path) {
		configuration.setAttribute(PreferenceConstants.J_PN_RemoteProcessToLaunch, PathUtils.convertPathToWindows(path));
	}
	
	private static void setLaunchConfigurationName(ILaunchConfigurationWorkingCopy config, String proposedName) {
		String name = proposedName;
		
		// the call to generateLaunchConfigurationName below will replace all \'s with _'s
		// the code below just removes any \ or : at the end of the SDK name, if for example
		// they gave the SDK a name of "M:" or "M:\".
		if (name.endsWith("\\]")) { //$NON-NLS-1$
			name = name.substring(0, name.length() - 2) + "]"; //$NON-NLS-1$
		}

		if (name.endsWith(":]")) { //$NON-NLS-1$
			name = name.substring(0, name.length() - 2) + "]"; //$NON-NLS-1$
		}

		config.rename(DebugPlugin.getDefault().getLaunchManager().generateLaunchConfigurationName(name));
	}
}
