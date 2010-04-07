package com.nokia.cdt.internal.debug.launch;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.debug.core.executables.Executable;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

public abstract class AbstractSymbianLaunchShortcut implements ILaunchShortcut2 {

	protected abstract void launchProject(IProject project, Executable executable, IPath defaultMMP, String mode);
	
	/**
	 * Override to tell whether this existing configuration matches the type of
	 * one the shortcut would create. The default implementation returns true
	 * for all configurations.
	 */
	protected boolean isSupportedConfiguration(ILaunchConfiguration config) throws CoreException {
		return true;
	}
 	
	public void launch(IEditorPart editor, String mode) {
		// launch an existing config if one exists
		ILaunchConfiguration[] configs = getLaunchConfigurations(editor);
		if (configs.length > 0) {
			// just launch the first one that supports the mode
			for (ILaunchConfiguration config : configs) {
				try {
					if (config.supportsMode(mode) && isSupportedConfiguration(config)) {
						DebugUITools.launch(configs[0], mode);
						return;
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}

		IEditorInput editorInput = editor.getEditorInput();
		if (editorInput instanceof IFileEditorInput) {
			IFile file = ((IFileEditorInput)editorInput).getFile();
			if (file != null) {
				launchProject(file.getProject(), null, null, mode);
			}
		}
	}

	public void launch(ISelection selection, String mode) {

		// launch an existing config if one exists
		ILaunchConfiguration[] configs = getLaunchConfigurations(selection);
		if (configs.length > 0) {
			// find all the ones that support the mode and shortcut (#11013)
			List<ILaunchConfiguration> matches = new ArrayList<ILaunchConfiguration>();
			for (int i = 0; i < configs.length; i++) {
				ILaunchConfiguration config = configs[i];
				try {
					if (config.supportsMode(mode) && isSupportedConfiguration(config)) {
						matches.add(config);
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
			// if only one matches, just launch
			if (matches.size() > 0) {
				if (matches.size() == 1 || WorkbenchUtils.isJUnitRunning()) {
					DebugUITools.launch(matches.get(0), mode);
				} else {
					// else, ask the user
					ILaunchConfiguration selected = chooseConfiguration(matches, mode);
					if (selected != null)
						DebugUITools.launch(selected, mode);
				}
				return;
			}
		}
		
		IPath defaultMMP = null;
		boolean launched = false;
		Executable executable = null;
		
		if (selection instanceof IStructuredSelection) {
			Object firstElement = ((IStructuredSelection) selection).getFirstElement();
			if (firstElement != null && firstElement instanceof Executable)
			{
				launchProject(((Executable)firstElement).getProject(), (Executable)firstElement, defaultMMP, mode);
				launched = true;
			}
			else
			if (firstElement != null && firstElement instanceof IAdaptable)
			{
				IFile file = (IFile) ((IAdaptable) firstElement).getAdapter(IFile.class);
				if (file != null)
				{
					IPath filePath = file.getLocation();
					if ("mmp".equalsIgnoreCase(filePath.getFileExtension())) //$NON-NLS-1$
					{
						defaultMMP = filePath;
					}
					else
					{		
						executable = new Executable(file.getLocation(), file.getProject(), file);
						launchProject(file.getProject(), executable, defaultMMP, mode);
						launched = true;					
					}
				}
			}
		}
		
		if (!launched)
		{
			List<IProject> projects = CarbideBuilderPlugin.getProjectsFromSelection(selection);		
			if (projects.size() > 0) {
				launchProject(projects.get(0), executable, defaultMMP, mode);
			}
		}
	}
	
	/**
	 * Show a selection dialog that allows the user to choose one of the specified
	 * launch configurations.  Return the chosen config, or <code>null</code> if the
	 * user cancelled the dialog.
	 */
	protected ILaunchConfiguration chooseConfiguration(List<ILaunchConfiguration> configList, String mode) {
		IDebugModelPresentation labelProvider = DebugUITools.newDebugModelPresentation();
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(WorkbenchUtils.getSafeShell(), labelProvider);
		dialog.setElements(configList.toArray());
		dialog.setTitle(Messages.getString("AbstractSymbianLaunchShortcut.ChooseConfigTitle"));  //$NON-NLS-1$
		dialog.setMessage(Messages.getString("AbstractSymbianLaunchShortcut.ChooseConfigLabel"));  //$NON-NLS-1$
		dialog.setMultipleSelection(false);
		int result = dialog.open();
		labelProvider.dispose();
		if (result == Window.OK) {
			return (ILaunchConfiguration) dialog.getFirstResult();
		}
		return null;
	}


	public ILaunchConfiguration[] getLaunchConfigurations(ISelection selection) {
		IPath defaultMMP = null;
		if (selection instanceof IStructuredSelection) {
			Object firstElement = ((IStructuredSelection) selection).getFirstElement();
			if (firstElement != null && firstElement instanceof Executable)
			{
				return LaunchPlugin.getDefault().getLaunchConfigurations(((Executable)firstElement).getProject(), (Executable)firstElement, defaultMMP);
			}
			else
			if (firstElement != null && firstElement instanceof IAdaptable)
			{
				IFile file = (IFile) ((IAdaptable) firstElement).getAdapter(IFile.class);
				if (file != null)
				{
					IPath filePath = file.getLocation();
					if ("mmp".equalsIgnoreCase(filePath.getFileExtension())) //$NON-NLS-1$
					{
						defaultMMP = filePath;
					}
					Executable executable = new Executable(file.getLocation(), file.getProject(), file);
					return LaunchPlugin.getDefault().getLaunchConfigurations(file.getProject(), executable, defaultMMP);
				}
			}
		}
		
		List<IProject> projects = CarbideBuilderPlugin.getProjectsFromSelection(selection);		
		if (projects.size() > 0) {
			return LaunchPlugin.getDefault().getLaunchConfigurations(projects.get(0), null, defaultMMP);
		}
		return null;
	}

	public ILaunchConfiguration[] getLaunchConfigurations(IEditorPart editorpart) {
		IEditorInput editorInput = editorpart.getEditorInput();
		if (editorInput instanceof IFileEditorInput) {
			IFile file = ((IFileEditorInput)editorInput).getFile();
			if (file != null) {
				return LaunchPlugin.getDefault().getLaunchConfigurations(file.getProject(), null, null);
			}
		}
		return null;
	}

	public IResource getLaunchableResource(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			Object firstElement = ((IStructuredSelection) selection).getFirstElement();
			if (firstElement != null && firstElement instanceof IFile)
			{
				IFile file = (IFile) firstElement;
				return file.getProject();
			}
			if (firstElement != null && firstElement instanceof Executable)
			{
				return ((Executable)firstElement).getProject();
			}
		}
		List<IProject> projects = CarbideBuilderPlugin.getProjectsFromSelection(selection);		
		if (projects.size() > 0) {
			return projects.get(0);
		}
		return null;
	}

	public IResource getLaunchableResource(IEditorPart editorpart) {
		IEditorInput editorInput = editorpart.getEditorInput();
		if (editorInput instanceof IFileEditorInput) {
			IFile file = ((IFileEditorInput)editorInput).getFile();
			if (file != null) {
				return file.getProject();
			}
		}
		return null;
	}
}