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
* The activator class controls the plug-in life cycle
*
*/
package com.nokia.carbide.cpp.internal.qt.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.ide.IDEInternalPreferences;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.progress.UIJob;
import org.osgi.framework.BundleContext;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideConfigurationChangedListener;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.ISISBuilderInfo;
import com.nokia.carbide.cdt.internal.api.builder.SISBuilderInfo2;
import com.nokia.carbide.cpp.internal.api.sdk.ISDKManagerLoadedHook;
import com.nokia.carbide.cpp.internal.qt.core.QtCorePlugin;
import com.nokia.carbide.cpp.internal.qt.core.QtSDKUtils;
import com.nokia.carbide.cpp.internal.qt.ui.wizard.Messages;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;

public class QtUIPlugin extends AbstractUIPlugin implements ICarbideConfigurationChangedListener, ISDKManagerLoadedHook {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.nokia.carbide.cpp.qt.ui"; //$NON-NLS-1$

	// The shared instance
	private static QtUIPlugin plugin;
	
	/**
	 * The constructor
	 */
	public QtUIPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		CarbideBuilderPlugin.removeBuildConfigChangedListener(this);
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static QtUIPlugin getDefault() {
		return plugin;
	}

	/**
	 * Sets up the builder settings so the pkg files generated by qmake are built by
	 * the respective build configuration.
	 * @param project
	 */
	public static void setupSISBuilderSettings(IProject project) {

		// loop through the build configs and if a pkg file exists for it then
		// add it to the sis builder settings
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi != null) {
			final String underscore = "_"; //$NON-NLS-1$
			final String template = "template"; //$NON-NLS-1$
			
			for (ICarbideBuildConfiguration config : cpi.getBuildConfigurations()) {
				IFile file = project.getFile(project.getName() + underscore + config.getPlatformString().toLowerCase() +
						underscore + config.getTargetString().toLowerCase() + ".pkg"); //$NON-NLS-1$
				
				if (file == null || !file.exists() && !config.getPlatformString().equals(ISymbianBuildContext.EMULATOR_PLATFORM)) {
					// Qt 4.6 only creates one PKG file per project. Do not add for WINSCW
					file = project.getFile(project.getName() + underscore + template + ".pkg"); //$NON-NLS-1$
				} 
				
				if (file != null && file.exists()){
					SISBuilderInfo2 sisInfo = new SISBuilderInfo2(project);
					sisInfo.setPKGFile(file.getLocation().toOSString());
					// set to self signing
					sisInfo.setSigningType(ISISBuilderInfo.SELF_SIGN);
					config.getSISBuilderInfoList().add(sisInfo);
					config.saveConfiguration(false);
				}
			}
		}
	}
	
	public static void switchToQtPerspective() {
		// set the perspective to Qt C++
		try {
			IWorkbench workbench = getDefault().getWorkbench();
			IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
			if (activeWorkbenchWindow == null) {
				IWorkbenchWindow windows[] = workbench.getWorkbenchWindows();
				activeWorkbenchWindow = windows[0];
			}
			final IPerspectiveDescriptor perspective = workbench.getPerspectiveRegistry().findPerspectiveWithId("com.trolltech.qtcppproject.QtCppPerspective"); //$NON-NLS-1$
			final IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
			if (activePage != null)
			{
				if (activePage.getPerspective().getId().equals(perspective.getId()))
					return;  // already on the default perspective for this projects
			}
			
			if (activePage != null) {
				
				UIJob job = new UIJob(""){ //$NON-NLS-1$
					public IStatus runInUIThread(IProgressMonitor monitor) {
						boolean switchToDefaultPerspective = false;
						IPreferenceStore store = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
						
						if (store != null){
							String promptSetting = store.getString(IDEInternalPreferences.PROJECT_SWITCH_PERSP_MODE);
							if ((promptSetting.equals(MessageDialogWithToggle.ALWAYS)) ){
								switchToDefaultPerspective = true;
							}
							else if ((promptSetting.equals(MessageDialogWithToggle.PROMPT))) {
								MessageDialogWithToggle toggleDialog = MessageDialogWithToggle.openYesNoQuestion(
										null,
										Messages.PerspectiveSwitchDialog_Title,
										Messages.PerspectiveSwitchDialog_Query,
										Messages.PerspectiveSwitchDialog_RememberDecisionText,
										false,
										null,
										null);
								
								boolean toggleState = toggleDialog.getToggleState();
								switchToDefaultPerspective = toggleDialog.getReturnCode() == IDialogConstants.YES_ID;
								
								// set the store
								if (toggleState){
									if (switchToDefaultPerspective)
										store.setValue(IDEInternalPreferences.PROJECT_SWITCH_PERSP_MODE, MessageDialogWithToggle.ALWAYS);
									else
										store.setValue(IDEInternalPreferences.PROJECT_SWITCH_PERSP_MODE, MessageDialogWithToggle.NEVER);
								}
							}
						}
						
						if (switchToDefaultPerspective){
							activePage.setPerspective(perspective);
						}
						
						return Status.OK_STATUS;
					}};
				job.setSystem(true);
				job.setRule(null); // no rule needed here - could just block important jobs
				job.schedule();
			}
		} catch (IllegalStateException e) {
			// PlatformUI.getWorkbench() throws if running headless
		}
	}
	
	/** 
	 * Implements ICarbideConfigurationChangedListener
	 */
	public void buildConfigurationChanged(ICarbideBuildConfiguration currentConfig) {
		checkDefaultQtSDKForProject(currentConfig);
	}
	
	/**
	 * For the newly selected build configuration, check and see if there's an analogous internally installed
	 * Qt-SDK, and if so make that the default. The default should not change if already set to &ltDefault&gt in the qt preferences or
	 * if the new configuration has no internally built Qt-SDK.
	 * @param currentConfig
	 */
	@SuppressWarnings("restriction")
	private void checkDefaultQtSDKForProject(ICarbideBuildConfiguration currentConfig){
		IProject project = currentConfig.getCarbideProject().getProject();
		if (project != null && QtCorePlugin.isQtProject(project)) {
			try {
				String qtSDKName = QtSDKUtils.getQtSDKNameForSymbianSDK(currentConfig.getSDK());
				// If qtSDK is not internally installed or <Default> is set, don't change anything
				String currQtSDK = QtSDKUtils.getDefaultQtSDKForProject(project);
				if (qtSDKName == null || currQtSDK == null || currQtSDK.equals(QtSDKUtils.QT_DEFAULT_SDK_NAME)) {
					return;
				}
				
				QtSDKUtils.setDefaultQtSDKForProject(project, qtSDKName);
				
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		
	}

	/** 
	 * Implements ISDKManagerLoadedHook
	 */
	public void symbianSDKManagerLoaded() {
		CarbideBuilderPlugin.addBuildConfigChangedListener(this);
	}
}
