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
package com.nokia.carbide.cpp.internal.project.ui.actions;

import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileView;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.internal.project.ui.editors.images.MultiImageEditorContext;
import com.nokia.carbide.cpp.internal.project.ui.editors.images.MultiImageEditorDialog;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import java.text.MessageFormat;
import java.util.List;

public abstract class ModifyMultiImageSourceActionBase implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;
	private Shell shell;
	protected IFile modelFile;
	private IMultiImageSource lastMultiImageSource;

	public ModifyMultiImageSourceActionBase() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
		if (window != null)
			shell = window.getShell();
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}
	
	public void setModelFile(IFile file) {
		this.modelFile = file;
	}
	
	/**
	 * Locate a match for the multi image source.
	 * @param view
	 * @param path 
	 * @return
	 */
	protected IMultiImageSource findMultiImageSource(IView view, IPath targetFilePath) {
		List<IMultiImageSource> multiImageSources = getMultiImageSources(view);
		
		IMultiImageSource source = null;
		for (IMultiImageSource mis : multiImageSources) {
			if (mis.getTargetFilePath().equals(targetFilePath)) {
				source = mis;
				break;
			}
		}
		
		// UI designer has quirks
		if (source == null) {
			for (IMultiImageSource mis : multiImageSources) {
				if (mis.getTargetFilePath().makeRelative().equals(targetFilePath.makeRelative())) {
					source = mis;
					break;
				}
			}
		}
		
		Check.checkState(source != null);
		return source;
	}

	/**
	 * @param view
	 * @return
	 */
	protected List<IMultiImageSource> getMultiImageSources(IView view) {
		List<IMultiImageSource> multiImageSources;
		if (view instanceof IMMPView)
			multiImageSources = ((IMMPView)view).getMultiImageSources();
		else 
			multiImageSources = ((IImageMakefileView)view).getMultiImageSources();
		return multiImageSources;
	}

	/**
	 * Invoke an editing dialog for the given multi-image source.
	 * @param view
	 */
	protected void editMbmMifFileInView(IView view, IMultiImageSource source) {
		Check.checkState(modelFile != null);
		
		MultiImageEditorContext context = new MultiImageEditorContext();
		context.initFromView(view, source);
		context.setMultiImageSources(getMultiImageSources(view));
		lastMultiImageSource = source;
		
		MultiImageEditorDialog dialog = new MultiImageEditorDialog(shell, context);
		if (dialog.open() == IDialogConstants.OK_ID) {
			if (context.isDirty()) {
				context.doSave();
				
				postDialogAcceptedActions(view, source);
				
				commitStanza(view);
			}
		}
		context.dispose();
	}

	/**
	 * Commit the view, with querying and merge on conflict.
	 */
	protected void commitStanza(IView view) {
		while (true) {
			try {
				view.commit();
				break;
			} catch (IllegalStateException e) {
				if (view.merge())
					continue;
				
				boolean overwrite = MessageDialog.openConfirm(shell,
						Messages.getString("ModifyMultiImageSourceActionBase.EditConflictTitle"), //$NON-NLS-1$
						MessageFormat.format(
								Messages.getString("ModifyMultiImageSourceActionBase.EditConflictMsg"), //$NON-NLS-1$
								new Object[] { modelFile.getLocation() }));
				if (overwrite) {
					view.forceSynchronized();
				} else {
					break;
				}
			}
		}
		
	}

	/**
	 * Do stuff after the dialog is accepted and the context is saved.
	 */
	protected void postDialogAcceptedActions(IView view, IMultiImageSource source) {
		
	}

	protected Shell getShell() {
		if (window != null)
			return window.getShell();
		return null;
	}
	
	protected Object failedLoadHandler(CoreException exception) {
		if (exception != null) {
			ProjectUIPlugin.log(exception);
			Logging.showErrorDialog(getShell(),
					Messages.getString("ModifyMultiImageSourceActionBase.FailedLoadTitle"),  //$NON-NLS-1$
					MessageFormat.format(Messages.getString("ModifyMultiImageSourceActionBase.FailedLoadMsg"), //$NON-NLS-1$
							new Object[] { modelFile.getLocation() }),
					exception.getStatus());
		} else {
			MessageDialog.openError(getShell(),
					Messages.getString("ModifyMultiImageSourceActionBase.FailedLoadTitle"),  //$NON-NLS-1$
					MessageFormat.format(Messages.getString("ModifyMultiImageSourceActionBase.FailedLoadMsg"), //$NON-NLS-1$
							new Object[] { modelFile.getLocation() }));

		}
		return null;
	}
	
	public IMultiImageSource getLastMultiImageSource() {
		return lastMultiImageSource;
	}
	
}
