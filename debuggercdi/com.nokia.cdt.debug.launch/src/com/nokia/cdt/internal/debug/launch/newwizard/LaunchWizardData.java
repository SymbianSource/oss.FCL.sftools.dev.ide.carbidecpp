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

package com.nokia.cdt.internal.debug.launch.newwizard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.osgi.framework.Bundle;
import org.osgi.service.prefs.Preferences;

import com.freescale.cdt.debug.cw.core.RemoteConnectionsTRKHelper;
import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IService;
import com.nokia.carbide.remoteconnections.internal.api.IConnectedService2;
import com.nokia.carbide.remoteconnections.internal.registry.Registry;
import com.nokia.cdt.debug.cw.symbian.SettingsData;
import com.nokia.cdt.internal.debug.launch.wizard.LaunchOptions;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import cwdbg.PreferenceConstants;

/**
 * Data manipulated by the launch wizard and its dialogs.
 */
@SuppressWarnings("restriction")
public class LaunchWizardData extends LaunchOptions {
	/**
	 * This plugin is only shipped in internal layouts and is used as a fallback
	 * to determine whether Sys TRK is more likely to be available than App TRK 
	 * if we cannot otherwise tell.
	 */
	private static final String COM_NOKIA_CARBIDE_SYMSEE_TRK_SUPPORT = "com.nokia.carbide.symsee.trk.support";

	public interface IPathValidator {
		/**
		 * @param path IPath
		 * @return Error string or <code>null</code> if is valid
		 */
		String isValidPath(IPath path);
	}

	private final IService service;
	
	// overall target
	public static class LaunchType {
		private final String launchId;

		public LaunchType(String launchId) {
			this.launchId = launchId;
		}
		
		public boolean isApplicable(LaunchWizardData data) {
			return true;
		}
		
		public String getLaunchId() {
			return launchId;
		}
	};
	
	public final static LaunchType APP_TRK = new LaunchType(null);
	public final static LaunchType SYS_TRK = new LaunchType(null);
	public final static LaunchType ATTACH_TO_PROCESS_LAUNCH = new LaunchType(null);
	public final static LaunchType PLATSIM_RUN_MODE = new LaunchType(null);
	public final static LaunchType PLATSIM_STOP_MODE = new LaunchType(null);
	
	// settings made in Debug/Run Process section
	enum EExeSelection {
		USE_PROJECT_EXECUTABLE,
		USE_REMOTE_EXECUTABLE,
		ATTACH_TO_PROCESS,
	};
	
	private EExeSelection exeSelection;
	private IPath exeSelectionPath = Path.EMPTY;
	private EBuildBeforeLaunchOption buildBeforeLaunch;
	private boolean installPackage;
	private String sisPath;
	private IConnection connection;
	private List<IPath> launchableExes;
	
	// settings made in the Other Settings section
	enum EBuildBeforeLaunchOption {
		ALWAYS,
		NEVER,
		USE_WORKSPACE_SETTING,
	}

	public LaunchWizardData(LaunchOptions launchOptions, IService trkService) {
		this.mmps = launchOptions.mmps;
		this.exes = launchOptions.exes;
		this.defaultExecutable = launchOptions.defaultExecutable;
		this.project = launchOptions.project;
		this.configurationName = launchOptions.configurationName;
		this.isEmulation = launchOptions.isEmulation;
		this.emulatorOnly = launchOptions.emulatorOnly;
		this.mode = launchOptions.mode;
		this.service = trkService;
	}

	/**
	 * @return the service
	 */
	public IService getService() {
		return service;
	}

	/**
	 * @return
	 */
	public boolean isDebug() {
		return mode.equals(ILaunchManager.DEBUG_MODE);
	}

	public String getModeLabel() {
		if (mode.equals(ILaunchManager.RUN_MODE))
			return Messages.getString("LaunchWizardData.RunModeLabel"); //$NON-NLS-1$
		else if (mode.equals(ILaunchManager.DEBUG_MODE))
			return Messages.getString("LaunchWizardData.DebugModeLabel"); //$NON-NLS-1$
		else
			return TextUtils.titleCase(mode);
			
	}
	
	/**
	 * Validate the detected and/or configured data
	 * @return IStatus, never <code>null</code>
	 */
	public IStatus validate() {
		return Status.OK_STATUS;
	}

	/**
	 * @return 
	 * @return
	 */
	public List<IPath> getExes() {
		return exes;
	}
	
	public List<IPath> getLaunchableExes() {
		if (launchableExes == null) {
			launchableExes = new ArrayList<IPath>();
			for (IPath path : exes) {
				if ("exe".equalsIgnoreCase(path.getFileExtension()))
					launchableExes.add(path);
			}
		}
		return launchableExes;
	}

	/**
	 * @return the defaultExecutable
	 */
	public IPath getDefaultExecutable() {
		return defaultExecutable;
	}
	
	/**
	 * Set the executable selection mode
	 * @param selection
	 */
	public void setExeSelection(EExeSelection selection) {
		this.exeSelection = selection;
	}
	/**
	 * Set the path for the exe
	 * @param path or <code>null</code>
	 */
	public void setExeSelectionPath(IPath path) {
		this.exeSelectionPath = path != null ? path : Path.EMPTY;
	}

	/**
	 * @return
	 */
	public EExeSelection getExeSelection() {
		return exeSelection;
	}
	
	public IPath getExeSelectionPath() {
		return exeSelectionPath;
	}

	public String getConnectionName() {
		IConnection connection = getConnection();
		if (connection == null)
			return null;
		return connection.getDisplayName();
	}

	public void setBuildBeforeLaunchOption(
			EBuildBeforeLaunchOption setting) {
		this.buildBeforeLaunch = setting;
	}

	public EBuildBeforeLaunchOption getBuildBeforeLaunch() {
		return buildBeforeLaunch;
	}
	
	/** Get current workspace setting */
	public boolean isWorkspaceBuildBeforeLaunch() {
		// here's how to get the prefs from a plugin's #getPreferenceStore() without violating access
		String prefId = IDebugUIConstants.PREF_BUILD_BEFORE_LAUNCH;
		int idx = prefId.lastIndexOf('.');
		String plugin = prefId.substring(0, idx);
		Preferences node = Platform.getPreferencesService().getRootNode().node(InstanceScope.SCOPE).node(plugin);
		return node.getBoolean(prefId, true);
	}

	/** Get actual launch-time setting */
	public boolean isCurrentBuildBeforeLaunch() {
		if (buildBeforeLaunch != EBuildBeforeLaunchOption.USE_WORKSPACE_SETTING)
			return buildBeforeLaunch == EBuildBeforeLaunchOption.ALWAYS;
		return isWorkspaceBuildBeforeLaunch();
	}

	/**
	 * @param selection
	 */
	public void setInstallPackage(boolean selection) {
		this.installPackage = selection;
	}
	
	/**
	 * @return the installPackage
	 */
	public boolean isInstallPackage() {
		return installPackage;
	}

	/**
	 * @return
	 */
	public IProject getProject() {
		return project;
	}

	/**
	 * @param sisPath
	 */
	public void setSisPath(String sisPath) {
		this.sisPath = sisPath;
	}
	
	/**
	 * @return
	 */
	public String getSisPath() {
		return sisPath;
	}
	
	/**
	 * Copy the data, for use by a transient dialog.
	 * @return new copy of data
	 */
	public LaunchWizardData copy() {
		LaunchOptions launchOptions = new LaunchOptions();
		launchOptions.mmps = mmps;
		launchOptions.exes = exes;
		launchOptions.defaultExecutable = defaultExecutable;
		launchOptions.project = project;
		launchOptions.configurationName = configurationName;
		launchOptions.isEmulation = isEmulation;
		launchOptions.emulatorOnly = emulatorOnly;
		launchOptions.mode = mode;
		LaunchWizardData d = new LaunchWizardData(launchOptions, service);
		d.exeSelection = exeSelection;
		d.exeSelectionPath = exeSelectionPath;
		d.buildBeforeLaunch = buildBeforeLaunch;
		d.installPackage = installPackage;
		d.sisPath = sisPath;
		d.connection = connection;
		return d;
	}

	/**
	 * Apply the given data to the receiver (when a transient dialog is accepted) 
	 * @param dialogData
	 */
	public void apply(LaunchWizardData dialogData) {
		exeSelection = dialogData.exeSelection;
		exeSelectionPath = dialogData.exeSelectionPath;
		buildBeforeLaunch = dialogData.buildBeforeLaunch;
		installPackage = dialogData.installPackage;
		sisPath = dialogData.sisPath;
		connection = dialogData.connection;
	}

	/**
	 * @return
	 */
	public boolean requiresInstallPackage() {
		return isSysTRKConnection() == Boolean.FALSE /* but not if unknown */ || installPackage;
	}

	public void setConnection(IConnection connection) {
		this.connection = connection;
	}

	public IConnection getConnection() {
		return connection;
	}

	public ILaunchConfigurationWorkingCopy createConfiguration() throws CoreException {
		String launchTypeId = getApplicableLaunchTypeId();
		ILaunchConfigurationType launchType = DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurationType(launchTypeId);
		ILaunchConfigurationWorkingCopy config = launchType.newInstance(null, configurationName);
		initializeConfigSettings(launchTypeId, config);
		
		return config;
	}

	private void initializeConfigSettings(String launchTypeId, ILaunchConfigurationWorkingCopy config) {
		IPath exePath = getExePath();
		IPath mmpPath = getMmpPath(exePath);
		if (launchTypeId.equals(SettingsData.APP_TRK_LAUNCH_TYPE_ID)) {
    		SettingsData.setDefaults(config, SettingsData.LaunchConfig_AppTRK, project, mmpPath, exePath);
		}
		else if (launchTypeId.equals(SettingsData.SYS_TRK_LAUNCH_TYPE_ID)) {
    		SettingsData.setDefaults(config, SettingsData.LaunchConfig_SysTRK, project, mmpPath, exePath);
		}
		else if (launchTypeId.equals(SettingsData.ATTACH_LAUNCH_TYPE_ID)) {
    		SettingsData.setDefaults(config, SettingsData.LaunchConfig_AppTRK, project, mmpPath, exePath);
		}
		if (exeSelection.equals(EExeSelection.USE_REMOTE_EXECUTABLE))
			SettingsData.setProcessToLaunch(config, exeSelectionPath);
		
		addBuildOptions(config);
		// always set the current connection id
		config.setAttribute(RemoteConnectionsTRKHelper.CONNECTION_ATTRIBUTE, Registry.CURRENT_CONNECTION_ID);
		if (installPackage) {
			config.setAttribute(PreferenceConstants.J_PN_SisFileHostPath, sisPath);
			// special case handling for running out of E: drive - install into E: drive
			if (exeSelection.equals(EExeSelection.USE_REMOTE_EXECUTABLE) && 
					exeSelectionPath.getDevice().equalsIgnoreCase("E:")) //$NON-NLS-1$
				config.setAttribute(PreferenceConstants.J_PN_InstallToDrive, 4); // index 4 == E: drive
		}
	}

	private IPath getMmpPath(IPath exePath) {
		if (!mmps.isEmpty()) {
			for (int i = 0; i < exes.size(); i++) {
				IPath exe = exes.get(i);
				if (exe.lastSegment().equals(exePath.lastSegment()))
					return mmps.get(i);
			}
		}
		return null;
	}

	public IPath getExePath() {
		// if attach, doesn't matter so return first exe
		if (exeSelection.equals(EExeSelection.ATTACH_TO_PROCESS))
			return exes.isEmpty() ? Path.EMPTY : exes.get(0);

		// otherwise, see if we can use the selected path - process to launch string
		// by checking if the file name matches any of the ones in our list of exes
		String filename = exeSelectionPath.lastSegment();
		if (filename != null) {
			for (IPath exePath : exes) {
				if (filename.equalsIgnoreCase(exePath.lastSegment())) {
					return exePath;
				}
			}
		}
		// none could be found matching the selected path, so use the first in the list
		return getLaunchableExes().isEmpty() ? Path.EMPTY : getLaunchableExes().get(0);
	}
	
	private IConnectedService getConnectedService() {
		if (connection != null) {
			Collection<IConnectedService> connectedServices = 
				RemoteConnectionsActivator.getConnectionsManager().getConnectedServices(connection);
			for (IConnectedService connectedService : connectedServices) {
				if (connectedService.getService().getIdentifier().equals(service.getIdentifier()))
					return connectedService;
			}
		}
		return null;
	}
	
	/** Tell whether we can detect that the current connection is Sys TRK.
	 * 
	 * @return Boolean.TRUE if Sys TRK, Boolean.FALSE if App TRK, or <code>null</code> if unknown
	 */
	public Boolean isSysTRKConnection() {
		IConnectedService connectedService = getConnectedService();
		if (connectedService instanceof IConnectedService2) {
			String value = ((IConnectedService2) connectedService).getProperties().get("is-system-trk"); //$NON-NLS-1$
			return Boolean.parseBoolean(value);
		}
		
		return null;
	}
	
	/**
	 * Tell whether Carbide is running in an internal layout.
	 * @return true if the installation includes known internal-only plugins
	 */
	public boolean isInternalLayout() {
		Bundle bundle = Platform.getBundle(COM_NOKIA_CARBIDE_SYMSEE_TRK_SUPPORT);
		if (bundle != null) {
			// assume this is an internal build 
			return true;
		}
		return false;
	}

	private String getApplicableLaunchTypeId() {
		if (exeSelection.equals(EExeSelection.ATTACH_TO_PROCESS))
			return SettingsData.ATTACH_LAUNCH_TYPE_ID;
		else if (!installPackage)
			return SettingsData.SYS_TRK_LAUNCH_TYPE_ID;
		else
			return SettingsData.APP_TRK_LAUNCH_TYPE_ID;
	}

	private void addBuildOptions(ILaunchConfigurationWorkingCopy config) {
		int buildBeforeLaunchValue = ICDTLaunchConfigurationConstants.BUILD_BEFORE_LAUNCH_USE_WORKSPACE_SETTING;
		switch (buildBeforeLaunch) {
		case NEVER:
			buildBeforeLaunchValue = ICDTLaunchConfigurationConstants.BUILD_BEFORE_LAUNCH_DISABLED;
			break;
		case ALWAYS:
			buildBeforeLaunchValue = ICDTLaunchConfigurationConstants.BUILD_BEFORE_LAUNCH_ENABLED;
			break;
		}
		config.setAttribute(ICDTLaunchConfigurationConstants.ATTR_BUILD_BEFORE_LAUNCH, buildBeforeLaunchValue);
	}
}

