package com.nokia.cdt.internal.debug.launch.newwizard;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IService;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class CommandRunLaunchWizard2 extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public CommandRunLaunchWizard2() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		IProject project = null;
		if (sel instanceof IStructuredSelection) {
			Object obj = ((IStructuredSelection) sel).getFirstElement();
			if (obj instanceof IResource)
				project = ((IResource) obj).getProject();
			else if (obj instanceof IAdaptable) {
				IResource rsrc = (IResource)((IAdaptable) obj).getAdapter(IResource.class);
				if (rsrc != null)
					project = rsrc.getProject();
			}
			if (project == null)
				throw new ExecutionException("No project in selection");
			
			ICarbideProjectInfo info = CarbideBuilderPlugin.getBuildManager()
				.getProjectInfo(project);
			if (info == null) 
				throw new ExecutionException("Not a Carbide project");
			List<IPath> mmpFiles = EpocEngineHelper.getMMPFilesForProject(info);
			IService trkService = RemoteConnectionsActivator.getConnectionTypeProvider().
				findServiceByID("com.nokia.carbide.trk.support.service.TRKService"); //$NON-NLS-1$

			List<IPath> allExePaths = new ArrayList<IPath>();
			List<IPath> currBuiltExePaths = new ArrayList<IPath>();
			List<IPath> allMMPPaths = new ArrayList<IPath>();
			List<IPath> currBuiltMMPPaths = new ArrayList<IPath>(); 

			EpocEngineHelper.getPathToAllExecutables(info.getDefaultConfiguration(),
					allExePaths,
					currBuiltExePaths,
					allMMPPaths,
					currBuiltMMPPaths); 
					
			LaunchWizard wiz = new LaunchWizard(project, 
					info.getDefaultBuildConfigName(),
					mmpFiles,
					currBuiltExePaths,
					EpocEngineHelper.getHostPathForExecutable(info.getDefaultConfiguration(), mmpFiles.get(0)),
					false, false, 
					ILaunchManager.DEBUG_MODE,
					trkService
					);
			WizardDialog dialog = new WizardDialog(HandlerUtil.getActiveShell(event), wiz);
			dialog.setPageSize(500, 300);
			dialog.open();
		}
				
		return null;
	}
}
