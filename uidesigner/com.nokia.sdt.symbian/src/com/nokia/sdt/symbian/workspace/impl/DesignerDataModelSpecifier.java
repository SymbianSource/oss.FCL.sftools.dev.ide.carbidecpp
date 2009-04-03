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
package com.nokia.sdt.symbian.workspace.impl;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.sdt.datamodel.*;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.sourcegen.ISourceGenProvider;
import com.nokia.sdt.sourcegen.ISourceGenSession;
import com.nokia.sdt.symbian.Messages;
import com.nokia.sdt.symbian.SymbianPlugin;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.sdt.symbian.workspace.ISymbianDataModelSpecifier;
import com.nokia.sdt.workspace.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.*;
import org.eclipse.ui.part.FileEditorInput;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

public class DesignerDataModelSpecifier implements 
						IDesignerDataModelSpecifier,
						ISymbianDataModelSpecifier {

	private IFile designFile;
	private boolean isRootDesign;
	
		// Used only by runWithLoadedModel, never the same
		// instance as a model loaded in the editor, but is
		// invalidated whenever a model is saved
	private boolean cachingEnabled;
	private LoadResult cachedModel;
	private int cachedModelEntryCount; // reentrancy counter
	private boolean cachedModelInvalid;
	
	static final String RESOURCE_PROPERTY_QUALIFIER = "com.nokia.std.symbian"; //$NON-NLS-1$
	static final String RESOURCE_PROPERTY_LOCAL = "SnapshotFile"; //$NON-NLS-1$
	static final int SNAPSHOT_FORMAT = SWT.IMAGE_BMP;
	static final String SNAPSHOT_PREFIX = "snp"; //$NON-NLS-1$
	static final String SNAPSHOT_EXTENSION = ".bmp"; //$NON-NLS-1$
	
	public DesignerDataModelSpecifier(
			IFile designFile, boolean isRootDesign) {
		Check.checkArg(designFile);
		this.designFile = designFile;
		this.isRootDesign = isRootDesign;
		this.cachingEnabled = isRootDesign;
	}
	
	public void dispose() {
		ProjectContext context = (ProjectContext) WorkspaceContext.getContext().getExistingContextForProject(designFile.getProject());
		if (context != null) {
			context.removeSpecifier(this);
		}
		clearModelCache();
	}
			
	public String toString() {
		return designFile.getProjectRelativePath().toString();
	}
	
	void updateFile(IFile designFile) {
		Check.checkArg(designFile);
		this.designFile = designFile;
	}

	public IProjectContext getProjectContext() {
		IProjectContext result = null;
		if (Platform.isRunning()) {
			IProject project = designFile.getProject();
			if (project != null) {
				result = WorkspaceContext.getContext().getContextForProject(project);
			}
		}
		return result;
	}

	public String getDisplayName() {
		return toString();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.workspace.IDesignerDataModelSpecifier#getPrimaryResourcePath()
	 */
	public IPath getPrimaryResourcePath() {
		return designFile.getProjectRelativePath();
	}
	
	public IResource getPrimaryResource() {
		return designFile;
	}

	public String isEditable() {
		if (isRootDesign)
			return null;
		
		// verify that a view design is referenced from the root
		IDesignerDataModelSpecifier rootDmSpec = SymbianModelUtils.getRootDataModel(getProjectContext());
		if (rootDmSpec == null)
			return MessageFormat.format(
					Messages.getString("DesignerDataModelSpecifier.CannotEditDueToMissingApplication"), //$NON-NLS-1$
					new Object[] { SymbianModelUtils.ROOT_UIDESIGN_NAME, SymbianModelUtils.UIDESIGN_EXTENSIONS[0] } );
		
		return (String) rootDmSpec.runWithLoadedModelNoSourceGen(new IRunWithModelAction() {

			public Object run(LoadResult lr) {
				if (lr.getModel() == null)
					return lr.getStatus().getMessage();

				List<IDesignerDataModelSpecifier> referencedDesigns 
					= SymbianModelUtils.getSpecifiersForReferencedDesigns(lr.getModel());
				if (!referencedDesigns.contains(DesignerDataModelSpecifier.this)) {
					return Messages.getString("DesignerDataModelSpecifier.CannotEditUntilImportedIntoApplication"); //$NON-NLS-1$
				}
				return null;
			}
			
		});
	}

	public boolean isUIDesign() {
		// the root NXD may ultimately have a portion editable
		// as a UI design, but not yet.
		return !isRootDesign;
	}
	
	public boolean isRoot() {
		return isRootDesign;
	}
	
	class MakeSnapshotAction implements IRunWithModelAction {
		Image resultImage;
		public Object run(LoadResult lr) {
			IDesignerDataModel model = lr.getModel();
			if (model != null) {
				resultImage = takeSnapshotOfLoadedModel(model);
			}
			return null;
		}
	}
	
	private Image createNewSnapshot() {
		Image result = null;
		MakeSnapshotAction action = new MakeSnapshotAction();
		runWithLoadedModelNoSourceGen(action);
		result = action.resultImage;
		return result;
	}
	
	private Image takeSnapshotOfLoadedModel(IDesignerDataModel model) {
		Image result = null;
		EObject rcs[] = model.getRootContainers();
		if (rcs != null && rcs.length > 0) {
			try {
				IDisplayModel displayModel = model.getDisplayModelForRootContainer(rcs[0]);
				if (displayModel != null) {
					result = displayModel.createCompositeImage();
				}
			}
			catch (CoreException x) {
				SymbianPlugin.getDefault().log(x);
			}
		}	
		return result;
	}
	
	public void updateSnapshot(IDesignerDataModel model) {
		Image image = takeSnapshotOfLoadedModel(model);
		if (image != null) {
			clearSnapshot();
			File snapshotFile = newSnapshotFile(snapshotDir());
			saveSnapshot(image, snapshotFile);
		}
	}

	private static File snapshotDir() {
		File result = null;
		IPath statePath = SymbianPlugin.getDefault().getStateLocation();
		IPath imagesDir = statePath.append("snapshots/"); //$NON-NLS-1$
		result = imagesDir.toFile();
		if (!result.exists()) {
			result.mkdir();
		}
		return result;
	}
	
	private File newSnapshotFile(File snapshotsDir) {
		File result = null;
		if (designFile != null) {
			try {
				result = File.createTempFile(SNAPSHOT_PREFIX, SNAPSHOT_EXTENSION, snapshotsDir);
				if (result != null) {
					QualifiedName qname = new QualifiedName(RESOURCE_PROPERTY_QUALIFIER, RESOURCE_PROPERTY_LOCAL);
					designFile.setPersistentProperty(qname, result.getName());
				}
			}
			catch (IOException x) {
				SymbianPlugin.getDefault().log(x);
			}
			catch (CoreException x) {
				SymbianPlugin.getDefault().log(x);	
			}
		}
		return result;
	}
	
	private static File previousSnapshotFile(File snapshotsDir, IFile file) {
		File result = null;
		if (file != null) {
			try {
				QualifiedName qname = new QualifiedName(RESOURCE_PROPERTY_QUALIFIER, RESOURCE_PROPERTY_LOCAL);
				String fileName = file.getPersistentProperty(qname);
				if (!TextUtils.isEmpty(fileName)) {
					File snFile = new File(snapshotsDir, fileName);
					if (snFile.exists())
						result = snFile;
					else
						file.setPersistentProperty(qname, null);
				}
			}
			catch (CoreException x) {
				SymbianPlugin.getDefault().log(x);
			}
		}
		return result;
	}

	
	String fullFilePath(IFile file) {
		String path = file.getLocation().toOSString();
		return path;
	}
	
	private void saveSnapshot(Image image, File snapshotFile) {
		ImageLoader loader = new ImageLoader();
		loader.data = new ImageData[] {image.getImageData()};
	    loader.save(snapshotFile.getAbsolutePath(), SNAPSHOT_FORMAT);
	}
	
	private void deleteSnapshotFile(File snapshotFile) {
		if (designFile != null) {
			QualifiedName qname = new QualifiedName(RESOURCE_PROPERTY_QUALIFIER, RESOURCE_PROPERTY_LOCAL);
			try {
				designFile.setPersistentProperty(qname, null);
			} catch (CoreException x) {
				SymbianPlugin.getDefault().log(x);
			}
		}
		if (snapshotFile.exists())
			snapshotFile.delete();
	}
	
	private Image loadSnapshot(File snapshotFile) {
		Image result = null;
		if (snapshotFile.exists()) {
			ImageLoader loader = new ImageLoader();
			try {
				ImageData[] data = loader.load(snapshotFile.getAbsolutePath());
				if (data != null && data.length == 1) {
					result = new Image(Display.getDefault(), data[0]);
				}
			}
			catch (SWTException x) {
				SymbianPlugin.getDefault().log(x);
			}
		}
		return result;
	}

	public Image getSnapshot() {
		if (getProjectContext() == null)
			return null;
		
		Image snapshot = null;
		if (!isRootDesign) {
			File snapshotDir = snapshotDir();
			File snapshotFile = previousSnapshotFile(snapshotDir, designFile);
			if (snapshotFile != null) {
				snapshot = loadSnapshot(snapshotFile);
				if (snapshot == null) {
					deleteSnapshotFile(snapshotFile);
					snapshotFile = null;
				}
			}
			
			if (snapshot == null) {
				snapshot = createNewSnapshot();
				if (snapshot != null) {
					snapshotFile = newSnapshotFile(snapshotDir);
					saveSnapshot(snapshot, snapshotFile);
				}
			}
		}
		return snapshot;
	}

	public synchronized LoadResult load(boolean withSourceGen) {
		LoadResult result = null;
		if (designFile != null) {
			URI uri = URI.createPlatformResourceURI(designFile.getFullPath().toString());
			IProjectContext pc = getProjectContext();
			ISourceGenProvider provider = null;
			if (withSourceGen)
				provider = pc.getSourceGenProvider();
			result = LoaderRegistry.loadModel(uri, this, provider);
			DesignerDataModel model = (DesignerDataModel) result.getModel();
			if (model != null) {
				if (isRootDesign) {
					if (pc != null) {
						ISymbianPrivateProjectContext sppc = 
							(ISymbianPrivateProjectContext) pc.getAdapter(ISymbianPrivateProjectContext.class);
						if (sppc != null) {
							sppc.ensureCurrentLanguage(model);
						}
					}
				}
				model.setModelSpecifier(this);
				DesignerDataModelNotifier.instance().fireDataModelInitialized(model);
			}
		}
		return result;
	}

	public LoadResult load() {
		return load(true);
	}
	public LoadResult loadNoSourceGen() {
		return load(false);
	}

	public Object getAdapter(Class adapter) {
		Object result = null;
		if (adapter.isInstance(this)) {
			result = this;
		}
		else if (adapter == IResource.class) {
			result = designFile;
		}
		else {
			result = Platform.getAdapterManager().getAdapter(this, adapter);
		}
		return result;
	}
	
	public IFile getModelFile() {
		return designFile;
	}

	public void clearSnapshot() {
		File snapshotDir = snapshotDir();
		File snapshotFile = previousSnapshotFile(snapshotDir, designFile);
		if (snapshotFile != null) {
			deleteSnapshotFile(snapshotFile);
		}
	}
	
	/**
	 * Clear any snapshot for a file, not currently associated
	 * with an IDesignerDataModelSpecifier. Used in response
	 * to workspace file deletions to clean up orphaned snapshots.
	 */
	public static void clearSnapshotForFile(IFile file) {
		File snapshotDir = snapshotDir();
		File snapshotFile = previousSnapshotFile(snapshotDir, file);
		if (snapshotFile != null && snapshotFile.exists()) {
			snapshotFile.delete();
		}
	}

	public void refreshSnapshot() {
		clearSnapshot();
		Image image = getSnapshot();
		if (image != null)
			image.dispose();
	}

	public void openInEditor() throws CoreException {
		// need to allow root data model to be opened for future app editor
		if (designFile != null) {
			IWorkbench workbench = PlatformUI.getWorkbench();
			IWorkbenchPage activePage = workbench.getActiveWorkbenchWindow().getActivePage();
			IEditorRegistry registry = workbench.getEditorRegistry();
			IEditorDescriptor ed = registry.getDefaultEditor(designFile.getName());
			if (ed != null) {
				activePage.openEditor(new FileEditorInput(designFile), ed.getId());
			}
		}
	}

	public void modelUnloaded(IDesignerDataModel model) {
	}
	
	public synchronized void modelSaved(IDesignerDataModel model) {
		// Any cached model will be deleted as soon as its
		// reentrancy count goes to zero
		cachedModelInvalid = true;
		clearModelCache();
	}
	
	void enableModelCaching(boolean cachingEnabled) {
		this.cachingEnabled = cachingEnabled;
		if (!cachingEnabled) {
			clearModelCache();
		}
	}

	synchronized void clearModelCache() {
		if (cachedModel != null && cachedModelEntryCount == 0) {
			IDesignerDataModel dm = cachedModel.getModel();
			cachedModel = null;
			cachedModelInvalid = false;
			if (dm != null) {
				dm.dispose();
			}
		} else {
			// model will be released when use count goes to zero
			cachedModelInvalid = true;
		}
	}
	
	synchronized void clearCachedDisplayModels() {
		if (cachedModel != null && cachedModelEntryCount == 0) {
			IDesignerDataModel dm = cachedModel.getModel();
			EObject[] rootContainers = dm.getRootContainers();
			if (rootContainers != null) {
				for (EObject rc : rootContainers) {
					IDisplayModel displayModel = dm.getExistingDisplayModelForRootContainer(rc);
					if (displayModel != null) {
						displayModel.dispose();
					}
				}
			}
		}
	}
	
	private synchronized Object runWithLoadedModel(IRunWithModelAction action, boolean withSourceGen) {
		Object result = null;
		try {
			if (cachedModel != null) {
				IDesignerDataModel dataModel = cachedModel.getModel();
				ISourceGenSession sourceGenSession = dataModel.getSourceGenSession();
				boolean wantsButHasNoSourceGenSession = withSourceGen &&
					((sourceGenSession == null) || sourceGenSession.isDisposed());
				if (cachedModelEntryCount == 0) { // top-level request
					if (wantsButHasNoSourceGenSession)
						clearModelCache();
				}
				else if (wantsButHasNoSourceGenSession) { // nested request that can't be fulfilled
					String fmt = 
						Messages.getString("DesignerDataModelSpecifier.runWithLoadedModelSGError"); //$NON-NLS-1$
					String message = MessageFormat.format(fmt, new Object[0]);
					SymbianPlugin plugin = SymbianPlugin.getDefault();
					Logging.log(plugin, Logging.newStatus(plugin, new Throwable(message)));
				}
			}
			if (cachedModel == null) {
				cachedModel = load(withSourceGen);
				cachedModelInvalid = cachedModel != null &&
									 cachedModel.getModel() == null;
			}
			++cachedModelEntryCount;
			result = action.run(cachedModel);
		}
		finally {
			--cachedModelEntryCount;
			if (!cachingEnabled || cachedModelInvalid) {
				clearModelCache();
			}
		}
		return result;
	}
    
	public Object runWithLoadedModel(IRunWithModelAction action) {
		return runWithLoadedModel(action, true);
	}

	public Object runWithLoadedModelNoSourceGen(IRunWithModelAction action) {
		return runWithLoadedModel(action, false);
	}

    public MessageLocation createMessageLocation() {
    	MessageLocation result = null;
    	if (designFile != null) {
    		result = new MessageLocation(designFile.getLocation()); 
    	}
    	return result;
    }
          
	public boolean equals(Object obj) {
		if (super.equals(obj))
			return true;
		
		if (!(obj instanceof IDesignerDataModelSpecifier))
			return false;
		
		return ((IDesignerDataModelSpecifier) obj).getPrimaryResource().equals(getPrimaryResource());
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.workspace.IDesignerDataModelSpecifier#notifyModelResourceChanged()
	 */
	public void notifyModelResourceChanged() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				clearModelCache();
			}
		});
	}
}
