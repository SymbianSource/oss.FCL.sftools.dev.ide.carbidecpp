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

import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.IBinary;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.launch.AbstractCLaunchDelegate;
import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.freescale.cdt.debug.cw.core.cdi.Session;
import com.nokia.carbide.cdt.builder.builder.CarbideCPPBuilder;
import com.nokia.cdt.debug.common.internal.source.lookup.SourceMappingUtils;
import com.nokia.cdt.debug.cw.symbian.ui.executables.ExecutableTargeted;
import com.nokia.cdt.internal.debug.launch.ui.ExecutablesTab;

public abstract class NokiaAbstractLaunchDelegate extends
		AbstractCLaunchDelegate {

	/**
	 * [comment_20070318] This is my local (not perfect but fine in practice)
	 * solution to prevent user from launching the same LC again. See Bug 3548.
	 * 
	 * Better solution is change
	 * org.eclipse.debug.internal.core.LaunchConfiguration.launch()
	 * 
	 * but we don't want to modify platform code. .............03/18/07
	 */
	static private List<ILaunchConfiguration> m_beingLaunched = Collections
			.synchronizedList(new LinkedList<ILaunchConfiguration>());

	public boolean isBeingLaunched(ILaunchConfiguration lc) {
		if (lc instanceof ILaunchConfigurationWorkingCopy)
			lc = ((ILaunchConfigurationWorkingCopy) lc).getOriginal();

		if (!m_beingLaunched.contains(lc))
			return false;
		else {
			String cfgName = lc.getName();
			String format = LaunchMessages.getString("NokiaAbstractLaunchDelegate.LaunchUnderway"); //$NON-NLS-1$
			String msg = MessageFormat.format(format, new Object[] { cfgName });
			showMessage(LaunchMessages.getString("CarbideCPPLaunchDelegate.DebuggerName"), msg); //$NON-NLS-1$

			return true;
		}
	}

	public void addBeingLaunched(ILaunchConfiguration lc) {
		if (lc instanceof ILaunchConfigurationWorkingCopy)
			lc = ((ILaunchConfigurationWorkingCopy) lc).getOriginal();

		m_beingLaunched.add(lc);
	}

	public void removeBeingLaunched(ILaunchConfiguration lc) {
		if (lc instanceof ILaunchConfigurationWorkingCopy)
			lc = ((ILaunchConfigurationWorkingCopy) lc).getOriginal();

		m_beingLaunched.remove(lc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.launch.AbstractCLaunchDelegate#buildForLaunch(org.eclipse.debug.core.ILaunchConfiguration,
	 *      java.lang.String, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public boolean buildForLaunch(ILaunchConfiguration configuration,
			String mode, IProgressMonitor monitor) throws CoreException {

		/*
		 * This override is just for preventing user from launching the same LC
		 * twice. See my comment [comment_20070318] in this file.
		 */
		if (isBeingLaunched(configuration))
			return false;

		try {
			addBeingLaunched(configuration);

			return super.buildForLaunch(configuration, mode, monitor);

		} finally {
			removeBeingLaunched(configuration);
		}
	}

	protected String getPluginID() {
		return LaunchPlugin.getUniqueIdentifier();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ILaunchConfigurationDelegate2#preLaunchCheck(org.eclipse.debug.core.ILaunchConfiguration,
	 *      java.lang.String, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public boolean preLaunchCheck(ILaunchConfiguration configuration,
			String mode, IProgressMonitor monitor) throws CoreException {

		// Prevent launching the same LC again. See my comment
		// [comment_20070318] in this file.
		if (isBeingLaunched(configuration))
			return false;

		try {
			addBeingLaunched(configuration);

			// Don't go on if a session (from another LC) is being started or
			// stopped.
			synchronized (Session.sessionStartStopMutex()) {
			}

			if (monitor.isCanceled())
				return false;
			
			// If a debug session is already running for the given launch
			// configuration, don't allow user to start another session.
			//
			if (Session.preLaunchCheckForExistingTarget(configuration, new SubProgressMonitor(monitor, 0))) {
				// Pop up a dialog telling user.
				String cfgName = configuration.getName();
				showMessage(LaunchMessages.getString("CarbideCPPLaunchDelegate.DebuggerName"), //$NON-NLS-1$
						LaunchMessages.getString("CarbideCPPLaunchDelegate.DebugUnderway") + //$NON-NLS-1$
								"\t\"" + cfgName + "\""); //$NON-NLS-1$ //$NON-NLS-2$

				return false;
			}
			
			return super.preLaunchCheck(configuration, mode, monitor);
		} finally {
			removeBeingLaunched(configuration);
		}
	}

	public static IProject getProject(ILaunchConfiguration configuration) {
		String projectName = null;
		try {
			projectName = getProjectName(configuration);
		} catch (CoreException e) {}
		if (projectName != null) {
			projectName = projectName.trim();
			if (projectName.length() > 0) {
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
				return project;
			}
		}
		return null;
	}

	protected void doAdditionalSessionSetup(Session session) {
		session.setExecutableTargetedCallback(new ExecutableTargeted());
	}

	protected ILaunchConfiguration synchronizeWithProjectAccessPaths(
			ICProject project, ILaunchConfiguration config)
			throws CoreException {
		ILaunchConfigurationWorkingCopy workingCopy = config.getWorkingCopy();
		SourceMappingUtils.addSourceMappingToLaunch(workingCopy);
		return workingCopy.doSave();
	}

	/**
	 * Returns list of executables that need to be targeted for debugging other
	 * than the main executable which is always targeted. This list includes the
	 * binaries imported into the project as well as all the executables built
	 * as part of the inf file.
	 * 
	 * @return
	 * @throws CModelException
	 */
	protected IPath[] getOtherExecutables(ICProject project, IPath exePath,
			ILaunchConfiguration config, IProgressMonitor monitor) throws CModelException {
		List<IPath> targetedBinaries = new ArrayList<IPath>();
		
		try {
			targetedBinaries.addAll(ExecutablesTab.getExecutablesToTarget(config, monitor));
			
			// remove the main exe if it exists
			if (targetedBinaries.contains(exePath)) {
				targetedBinaries.remove(exePath);
			}
		} catch (CoreException e) {
		}

		return (IPath[]) targetedBinaries.toArray(new IPath[targetedBinaries.size()]);
	}

	/**
	 * Search binary files in the project for those that need be debugged (aside
	 * from the binary built by the project) for this debug session.
	 * 
	 * @return
	 * @throws CModelException
	 */
	protected List<IPath> getImportedExecutables(ICProject project,
			IPath exePath) throws CModelException {
		
		// Target imported executables in the project
		ArrayList<IPath> importedBinaries = new ArrayList<IPath>();
		if (projectMayHaveImportedExecutables(project))
		{
			IBinary[] binaries = project.getBinaryContainer().getBinaries();
			for (int i = 0; i < binaries.length; i++) {
				IBinary binary = binaries[i];
				// only look at binaries with the correct cpu type (e.g. "arm",
				// "x86")
				if (binary.getCPU().toLowerCase().contains(getCPUString())) {
					IPath binaryPath = binary.getResource().getLocation();

					/*
					 * Weed out other binaries that has the same base file name as
					 * the main target. That is pretty common when the project has
					 * build configs for different SDKs, say, S60_3 ARMV5, S60_3
					 * GCCE, UIQ21 GCC. E.g. a Blackflag project may contain several
					 * versions of "blackflag.exe" from different build configs.
					 * Targeting all those would cause problems in DE, say, when
					 * setting a breakpoint during debug, DE would try to duplicate
					 * the break in all other "blackflag.exe", resulting various
					 * problems. ..... 08/30/06
					 */
					if (binaryPath.lastSegment().equals(exePath.lastSegment()))
						continue;

					if (!binary.getPath().lastSegment().startsWith(".") && !binaryPath.equals(exePath)) { //$NON-NLS-1$
						importedBinaries.add(binaryPath);
					}
				}
			}			
		}
		return importedBinaries;
	}

	private boolean projectMayHaveImportedExecutables(ICProject project) {
		try {
			IResource[] members = project.getProject().members();
			for (int i = 0; i < members.length; i++) {
				if (members[i].isLinked())
					return true;
			}
		} catch (CoreException e) {}
		
		return false;
	}

	protected String getTargetLabel(String processName) {
		String format = "{0} (Launched {1})"; //$NON-NLS-1$
		String timestamp = DateFormat.getInstance().format(new Date(System.currentTimeMillis()));
		return MessageFormat.format(format, new Object[]{processName, timestamp});
	}

	protected String getCPUString() {
		// default. May be overridden.
		return "arm"; //$NON-NLS-1$
	}

	protected void showMessage(final String title, final String msg) {
		// Pop up a dialog telling user.
		Display display = Display.getCurrent();
		if (display == null) {
			display = Display.getDefault();
		}

		display.syncExec(new Runnable() {
			public void run() {
				MessageDialog.openWarning(CUIPlugin.getActiveWorkbenchShell(),
						title, msg);
			}
		});
	}

	@Override
	protected boolean existsErrors(IProject proj) throws CoreException {
		return CarbideCPPBuilder.projectHasBuildErrors(proj);
	}
}
