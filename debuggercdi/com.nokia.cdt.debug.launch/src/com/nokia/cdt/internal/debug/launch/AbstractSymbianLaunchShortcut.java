package com.nokia.cdt.internal.debug.launch;

import java.util.List;

import org.eclipse.cdt.debug.core.executables.Executable;
import org.eclipse.cdt.debug.core.executables.ISourceFileRemapping;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.cdt.debug.cw.symbian.ui.executables.SymbianSourceFileRemapping;

public abstract class AbstractSymbianLaunchShortcut implements ILaunchShortcut2 {

	protected abstract void launchProject(IProject project, Executable executable, IPath defaultMMP, String mode);

	public void launch(IEditorPart editor, String mode) {
		// launch an existing config if one exists
		ILaunchConfiguration[] configs = getLaunchConfigurations(editor);
		if (configs.length > 0) {
			// just launch the first one that supports the mode
			for (ILaunchConfiguration config : configs) {
				try {
					if (config.supportsMode(mode)) {
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
			// just launch the first one that supports the mode
			for (ILaunchConfiguration config : configs) {
				try {
					if (config.supportsMode(mode)) {
						DebugUITools.launch(configs[0], mode);
						return;
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}
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
					if ("mmp".equalsIgnoreCase(filePath.getFileExtension()))
					{
						defaultMMP = filePath;
					}
					else
					{		
						executable = new Executable(file.getLocation(), file.getProject(), file,
								new ISourceFileRemapping[] {new SymbianSourceFileRemapping()});
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
					if ("mmp".equalsIgnoreCase(filePath.getFileExtension()))
					{
						defaultMMP = filePath;
					}
					Executable executable = new Executable(file.getLocation(), file.getProject(), file,
							new ISourceFileRemapping[] {new SymbianSourceFileRemapping()});
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