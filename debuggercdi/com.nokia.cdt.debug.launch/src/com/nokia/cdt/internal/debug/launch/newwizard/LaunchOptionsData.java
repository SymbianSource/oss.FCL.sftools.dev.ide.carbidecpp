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

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.osgi.service.prefs.Preferences;

import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IService;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

/**
 * Data manipulated by the launch wizard and its dialogs.
 */
public class LaunchOptionsData {
	public interface IPathValidator {
		/**
		 * @param path IPath
		 * @return Error string or <code>null</code> if is valid
		 */
		String isValidPath(IPath path);
	}

	// settings detected in project
	private final List<IPath> mmps;
	private final List<IPath> exes;
	private final IPath defaultExecutable;
	private final IProject project;
	private final String configurationName;
	private final IService service;
	private final boolean isEmulation;
	private final boolean emulatorOnly;
	private final String mode;
	private IConnection connection;
	
	// overall target
	public static class LaunchType {
		private final String launchId;

		public LaunchType(String launchId) {
			this.launchId = launchId;
		}
		
		public boolean isApplicable(LaunchOptionsData data) {
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
	
	// settings made in the Other Settings section
	enum EBuildBeforeLaunchOption {
		ALWAYS,
		NEVER,
		USE_WORKSPACE_SETTING,
	}

	public LaunchOptionsData(List<IPath> mmps, List<IPath> exes,
			IPath defaultExecutable, IProject project,
			String configurationName, boolean isEmulation,
			boolean emulatorOnly, String mode, 
			IService trkService) {
		this.mmps = mmps;
		this.exes = exes;
		this.defaultExecutable = defaultExecutable;
		this.project = project;
		this.configurationName = configurationName;
		this.isEmulation = isEmulation;
		this.emulatorOnly = emulatorOnly;
		this.mode = mode;
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
			return "Run";
		else if (mode.equals(ILaunchManager.DEBUG_MODE))
			return "Debug";
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
	 * Copy the data, for use by a transient dialog.
	 * @return new copy of data
	 */
	public LaunchOptionsData copy() {
		LaunchOptionsData d = new LaunchOptionsData(
				mmps, exes, defaultExecutable, project, configurationName,
				isEmulation, emulatorOnly, mode, service);
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
	public void apply(LaunchOptionsData dialogData) {
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
		return false;
	}

	public void setConnection(IConnection connection) {
		this.connection = connection;
	}

	public IConnection getConnection() {
		return connection;
	}

}
