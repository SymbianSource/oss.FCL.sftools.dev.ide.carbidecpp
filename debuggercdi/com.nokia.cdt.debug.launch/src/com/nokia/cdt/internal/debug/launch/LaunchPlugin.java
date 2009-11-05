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
package com.nokia.cdt.internal.debug.launch;

import java.util.*;

import org.eclipse.cdt.core.model.*;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.debug.core.executables.Executable;
import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.*;
import org.eclipse.debug.ui.*;
import org.eclipse.debug.ui.contexts.DebugContextEvent;
import org.eclipse.debug.ui.contexts.IDebugContextListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.*;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.progress.UIJob;
import org.osgi.framework.BundleContext;

import com.freescale.cdt.debug.cw.core.CWPlugin;
import com.freescale.cdt.debug.cw.core.ui.ShowAllVariablesToggleAction;
import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IService;
import com.nokia.cdt.debug.cw.symbian.*;
import com.nokia.cdt.internal.debug.launch.wizard.LaunchCreationWizard;
import com.nokia.cdt.internal.debug.launch.wizard.LaunchCreationWizardFactory;
import com.nokia.cpp.internal.api.utils.core.Logging;

/**
 * The main plugin class to be used in the desktop.
 */
public class LaunchPlugin extends AbstractUIPlugin implements ILaunchListener, ILaunchConfigurationListener, IStartup {
	//The shared instance.
	private static LaunchPlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;
	
	private ArrayList<ILaunchConfiguration> recentlyLaunchedConfigs = new ArrayList<ILaunchConfiguration>();
	
	public static final String PLUGIN_ID = "com.nokia.cdt.debug.launch"; //$NON-NLS-1$

	public static final String EMULATION_LAUNCH_TYPE = "com.nokia.cdt.debug.launch.emulationLaunch"; //$NON-NLS-1$
	public static final String PROXY_LAUNCH_TYPE = "com.nokia.cdt.debug.launch.proxyLaunch"; //$NON-NLS-1$

	// Preference constants
	public static final String Use_New_Project_Assist = "com.nokia.cdt.debug.launch.Use_New_Project_Assist"; //$NON-NLS-1$

	
	/**
	 * The constructor.
	 */
	public LaunchPlugin() {
		super();
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);

		DebugPlugin.getDefault().getLaunchManager().addLaunchListener(this);
		DebugPlugin.getDefault().getLaunchManager().addLaunchConfigurationListener(this);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
		resourceBundle = null;
		
		DebugPlugin.getDefault().getLaunchManager().removeLaunchListener(this);
		DebugPlugin.getDefault().getLaunchManager().removeLaunchConfigurationListener(this);
	}

	/**
	 * Returns the shared instance.
	 */
	public static LaunchPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the string from the plugin's resource bundle,
	 * or 'key' if not found.
	 */
	public static String getResourceString(String key) {
		ResourceBundle bundle = LaunchPlugin.getDefault().getResourceBundle();
		try {
			return (bundle != null) ? bundle.getString(key) : key;
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the plugin's resource bundle,
	 */
	public ResourceBundle getResourceBundle() {
		try {
			if (resourceBundle == null)
				resourceBundle = ResourceBundle.getBundle("com.nokia.cdt.debug.launch.LaunchPluginResources"); //$NON-NLS-1$
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
		return resourceBundle;
	}

	/**
	 * Convenience method which returns the unique identifier of this plugin.
	 */
	public static String getUniqueIdentifier() {
		if (getDefault() == null) {
			// If the default instance is not yet initialized,
			// return a static identifier. This identifier must
			// match the plugin id defined in plugin.xml
			return PLUGIN_ID;
		}
		return getDefault().getBundle().getSymbolicName();
	}

	public String getDefaultLaunchConfigName(IProject project, Executable executable) {
		// calculate the default name
		String name = project.getName();
        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi != null) {
			name = name + " " + cpi.getDefaultBuildConfigName(); //$NON-NLS-1$
		}
		else if (executable != null)
		{
			name = executable.getName();
		}
		return name;
	}
	

	public ILaunchConfigurationType getEmulationLaunchConfigType() {
		return getLaunchManager().getLaunchConfigurationType(LaunchPlugin.EMULATION_LAUNCH_TYPE);
	}

	public ILaunchConfigurationType getProxyLaunchConfigType() {
		return getLaunchManager().getLaunchConfigurationType(LaunchPlugin.PROXY_LAUNCH_TYPE);
	}

	protected ILaunchManager getLaunchManager() {
		return DebugPlugin.getDefault().getLaunchManager();
	}
	
	private boolean isEmulatorBinaryPath(IPath binaryPath) {
		for (String segment : binaryPath.segments()) {
			if (segment.equalsIgnoreCase("winscw")) //$NON-NLS-1$
				return true;
		}
		
		return false;
	}
	
	private boolean getExePathsFromProjectAndDetermineIfX86(IProject project, List<IPath> exePaths) {
		boolean isX86 = false;
        ICProject cProject = CoreModel.getDefault().create(project);
        if (cProject != null) {
			try {
    			for (IBinary bin : cProject.getBinaryContainer().getBinaries()) {
    				if (bin.isExecutable()) {
    					IPath path = bin.getResource().getLocation();
    					
    					if (isEmulatorBinaryPath(path)) {
							isX86 = true;
						}

    					exePaths.add(path);
    				}
    			}
			} catch (CModelException e) {
				log(e);
			}
        }
        return isX86;
	}

	private ILaunchConfiguration createConfigurationForProject(IProject project, Executable executable, IPath defaultMMP, String mode) throws CoreException {

		boolean openLaunchConfigDialog = false;

		// get the default config name and make sure it's unique
		final String defaultConfigName = getLaunchManager().generateUniqueLaunchConfigurationNameFrom(getDefaultLaunchConfigName(project, executable));
		
		// try to determine if this is for the emulator or not.  also get the list of binaries for the current
		// build configuration or project.
		boolean isX86 = false;
		boolean useEmulatorByDefault = false;
		IPath defaultExecutable = null;
		
		List<IPath> exePaths = new ArrayList<IPath>(0);
		List<IPath> mmpPaths = new ArrayList<IPath>(0);

        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
        ICarbideBuildConfiguration buildConfig = null;
		if (cpi != null) {
			buildConfig = cpi.getDefaultConfiguration();
			
			if (buildConfig == null) {
				return null;
			}
			
			// just check the platform for the default config
			if (buildConfig.getPlatformString().equals(ISymbianBuildContext.EMULATOR_PLATFORM)) {
				isX86 = true;
			}

			if (defaultMMP != null)
			{
				defaultExecutable = EpocEngineHelper.getHostPathForExecutable(buildConfig, defaultMMP);
				if (!"exe".equalsIgnoreCase(defaultExecutable.getFileExtension())) //$NON-NLS-1$
					defaultExecutable = null;
			}
			// get the list of binaries that are being built for the build config and their corresponding mmp's
			EpocEngineHelper.getPathToAllExecutables(buildConfig, new ArrayList<IPath>(), exePaths, new ArrayList<IPath>(), mmpPaths);

		} else {
			if (executable != null) {
				exePaths.add(executable.getPath());				
				isX86 = isEmulatorBinaryPath(executable.getPath());
			}
			else {
				isX86 = getExePathsFromProjectAndDetermineIfX86(project, exePaths);
			}
		}
		
		if (exePaths.size() < 1) {
			// no binaries.  bail.
			throw new CoreException(new Status(IStatus.ERROR, getUniqueIdentifier(), 0, Messages.getString("LaunchPlugin.17"), null )); //$NON-NLS-1$
		}

		if (buildConfig == null) {
			useEmulatorByDefault = SettingsData.isEmulatorRequired(exePaths.get(0));
		}
		else
		if (buildConfig != null) {
			// pick the first exe in the list, otherwise the first binary in the list
			// note that since there is a build config, the exePaths and mmpPaths list
			// will be the same size
			IPath firstExePath = exePaths.get(0);
			IPath firstMmpPath = mmpPaths.get(0);
			for (int i=0; i<exePaths.size(); i++) {
				String fileExt = exePaths.get(i).getFileExtension();
				if (fileExt != null && fileExt.compareToIgnoreCase("exe") == 0) { //$NON-NLS-1$
					firstExePath = exePaths.get(i);
					firstMmpPath = mmpPaths.get(i);
					break;
				}
			}

			useEmulatorByDefault = SettingsData.isEmulatorRequired(buildConfig, firstExePath, firstMmpPath);
		}
		ILaunchConfigurationWorkingCopy config = null;
		
		if (exePaths.size() == 1 && isX86 && useEmulatorByDefault) {
			// no need to launch wizard.  we have what we need
			config = this.getEmulationLaunchConfigType().newInstance(null, defaultConfigName);
			SettingsData.setDefaults(config, SettingsData.LaunchConfig_Emulator, project, mmpPaths.size() == 1 ? mmpPaths.get(0) : null, exePaths.get(0));
		} else {
			// need to launch the wizard to determine:
			// which exe to launch or emulator if not required, 
			// which non-emulator launch type,
			// or both
			try {
				final LaunchCreationWizard wizard =
					LaunchCreationWizardFactory.getInstance().get(project, defaultConfigName, mmpPaths, exePaths, defaultExecutable, isX86, useEmulatorByDefault, mode);
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						wizard.init(PlatformUI.getWorkbench(), null);
						wizard.openWizard(CUIPlugin.getActiveWorkbenchShell());
					}
				});
				config = wizard.getLaunchConfiguration();
				openLaunchConfigDialog = wizard.shouldOpenLaunchConfigurationDialog();
			}
			catch (Exception e) {
				throw new CoreException(new Status(IStatus.ERROR, getUniqueIdentifier(), 0, e.getMessage(), null )); //$NON-NLS-1$
			}
		}
		
		if (config != null) {
			if (openLaunchConfigDialog) {
				IStructuredSelection selection = new StructuredSelection(config.doSave());
				String identifier;
				if (mode.equals("run")) //$NON-NLS-1$
					identifier = new String("org.eclipse.debug.ui.launchGroup.run"); //$NON-NLS-1$
				else
					identifier = new String("org.eclipse.debug.ui.launchGroup.debug"); //$NON-NLS-1$

				DebugUITools.openLaunchConfigurationDialogOnGroup(CUIPlugin.getActiveWorkbenchShell(), selection, identifier);
			}
			else
				return config.doSave();
		}
		return null;
	}

	public ILaunchConfiguration[] getLaunchConfigurations(IProject project, Executable executable, IPath defaultMMP)
	{
		IPath defaultExecutable = null;
		if (defaultMMP != null) {
			ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager()
					.getProjectInfo(project);
			ICarbideBuildConfiguration buildConfig = null;
			if (cpi != null) {
				buildConfig = cpi.getDefaultConfiguration();
				defaultExecutable = EpocEngineHelper.getHostPathForExecutable(buildConfig, defaultMMP);
				if (!"exe".equalsIgnoreCase(defaultExecutable.getFileExtension())) //$NON-NLS-1$
					defaultExecutable = null;
			}
		}
		String defaultConfigName = getDefaultLaunchConfigName(project, executable);
		ArrayList<ILaunchConfiguration> configs = new ArrayList<ILaunchConfiguration>();
		// Try the configurations not from the launch history
		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		try {
			ILaunchConfiguration[] launches = launchManager.getLaunchConfigurations();
			for (ILaunchConfiguration launchConfiguration : launches) {
				if (launchConfiguration.getName().equals(defaultConfigName) || 
						launchConfiguration.getAttribute(SettingsData.ATTR_originalName, launchConfiguration.getName()).equals(defaultConfigName))
				{
					if (defaultExecutable != null)
					{
						String programName = launchConfiguration.getAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_NAME, ""); //$NON-NLS-1$
						if (programName.equalsIgnoreCase(defaultExecutable.toOSString()))
							configs.add(launchConfiguration);				
					}
					else
						configs.add(launchConfiguration);
					break;
				}					
			}
		} catch (CoreException e) { 
			log(e);
		}

		return configs.toArray(new ILaunchConfiguration[configs.size()]);		
	}
	
	public void launchProject(IProject project, Executable executable, IPath defaultMMP, String mode) {
		ILaunchConfiguration configuration = null;
		try {
			configuration = createConfigurationForProject(project, executable, defaultMMP, mode);
		} catch (CoreException e) {
			log(e);
		}

		if (configuration != null) {
			DebugUITools.launch(configuration, mode);
		}
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin("com.nokia.cdt.debug.launch", path); //$NON-NLS-1$
	}

	public void launchRemoved(ILaunch launch) {
		// don't care about this
	}

	public void launchAdded(ILaunch launch) {
		// keep a list of recent launches.  we're really just interested in the
		// order so we can launch the most recently used config if more than one
		// exists for a particular project/build config combo.
		ILaunchConfiguration config = launch.getLaunchConfiguration();
		if (recentlyLaunchedConfigs.contains(config)) {
			recentlyLaunchedConfigs.remove(config);
		}
		
		// insert at the front of the list
		recentlyLaunchedConfigs.add(0, config);
	}

	public void launchChanged(ILaunch launch) {
		// don't care about this
	}

	public void launchConfigurationAdded(ILaunchConfiguration configuration) {
		// don't care about this
	}

	public void launchConfigurationChanged(ILaunchConfiguration configuration) {
		// don't care about this
	}

	public void launchConfigurationRemoved(ILaunchConfiguration configuration) {
		// remove this launch config from our list of recent launches if necessary
		if (recentlyLaunchedConfigs.contains(configuration)) {
			recentlyLaunchedConfigs.remove(configuration);
		}
	}

	public void earlyStartup() {
		UIJob earlyJob = new UIJob("Startup"){//$NON-NLS-1$

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				listenForVariablesView();
				return Status.OK_STATUS;
			}};
			earlyJob.schedule();
	}

	public static IProject getSelectedProject() {
		return CarbideBuilderPlugin.getProjectInContext();
	}
	
	public void addShowAllVariablesAction(final IDebugView variablesView)
	{
		UIJob installSAVJob = new UIJob("Show All Variables Action"){//$NON-NLS-1$

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
		    	ShowAllVariablesToggleAction showAllVarsAction = new ShowAllVariablesToggleAction();
		    	variablesView.setAction("com.freescale.cdt.debug.cw.core.ui.showAllVariablesToggle", showAllVarsAction); //$NON-NLS-1$

				IActionBars actionBars = variablesView.getViewSite().getActionBars();
				IMenuManager viewMenu = actionBars.getMenuManager();
				viewMenu.add(showAllVarsAction);								
				return Status.OK_STATUS;
			}};
			
			installSAVJob.schedule();
		
	}

	private void addVariablesViewListener(IWorkbenchWindow window)
	{
		window.getPartService().addPartListener(new IPartListener2() {

			public void partActivated(IWorkbenchPartReference partRef) {}

			public void partBroughtToTop(IWorkbenchPartReference partRef) {}

			public void partClosed(IWorkbenchPartReference partRef) {}

			public void partDeactivated(IWorkbenchPartReference partRef) {}

			public void partHidden(IWorkbenchPartReference partRef) {}

			public void partInputChanged(IWorkbenchPartReference partRef) {}

			public void partOpened(IWorkbenchPartReference partRef) {
				if (partRef.getId().equals(IDebugUIConstants.ID_VARIABLE_VIEW))
				{
					IDebugView variablesView = (IDebugView) partRef.getPart(true);
					addShowAllVariablesAction(variablesView);
				}
			}

			public void partVisible(IWorkbenchPartReference partRef) {}});
	}
	
	private void setupShowAllVariablesAction(IWorkbenchWindow window)
	{
	    IWorkbenchPage page = window.getActivePage();
	    AbstractDebugView variablesView = (AbstractDebugView) page.findView(IDebugUIConstants.ID_VARIABLE_VIEW);
        if (variablesView == null)
			addVariablesViewListener(window);
        else
        	addShowAllVariablesAction(variablesView);
        
		DebugUITools.getDebugContextManager().getContextService(window).addDebugContextListener(new IDebugContextListener() {

			public void debugContextChanged(DebugContextEvent event) {
				if ((event.getFlags() & DebugContextEvent.ACTIVATED) > 0) {
					contextActivated(event.getContext());
				}
			}

			private void contextActivated(ISelection context) {
				CWPlugin.setDebugContext(((IStructuredSelection) context).getFirstElement());
			}});

	}
	
	public void listenForVariablesView()
	{

		Display.getDefault().asyncExec(new Runnable() {
			public void run() {

				IWorkbenchWindow[] windows = getDefault().getWorkbench().getWorkbenchWindows();
				
				for (int i = 0; i < windows.length; i++) {
					setupShowAllVariablesAction(windows[i]);
				}
				
				getDefault().getWorkbench().addWindowListener(new IWindowListener() {

					public void windowActivated(IWorkbenchWindow window) {}

					public void windowClosed(IWorkbenchWindow window) {}

					public void windowDeactivated(IWorkbenchWindow window) {}

					public void windowOpened(IWorkbenchWindow window) {
						setupShowAllVariablesAction(window);
					}});
				
			}
		});
		
	}

	public static IService getTRKService() {
		return RemoteConnectionsActivator.getConnectionTypeProvider().
					findServiceByID("com.nokia.carbide.trk.support.service.TRKService"); //$NON-NLS-1$
	}
	
	/**
	 * Returns the currently active workbench window or <code>null</code>
	 * if none.
	 * 
	 * @return the currently active workbench window or <code>null</code>
	 */
	public static IWorkbenchWindow getActiveWorkbenchWindow() {
		return getDefault().getWorkbench().getActiveWorkbenchWindow();
	}

	/**
	 * Log throwable to the error log
	 * @param t Throwable
	 */
	public static void log(Throwable t) {
		Logging.log(plugin, Logging.newStatus(plugin, t));
	}
	
	/**
	 * Log status to the error log
	 * @param status status
	 */
	public static void log(IStatus status) {
		Logging.log(plugin, status);
	}
}
