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
package com.nokia.carbide.cpp.internal.project.ui.editors.images;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.ImageMakefileViewPathHelper;
import com.nokia.carbide.cpp.epoc.engine.image.*;
import com.nokia.carbide.cpp.epoc.engine.model.*;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileView;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.internal.project.ui.images.CarbideImageModelFactory;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PartInitException;
import org.osgi.framework.Version;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This class encapsulates the editing context
 *
 */
public class MultiImageEditorContext extends MultiImageEditorContextBase implements IDisposable {
	public static final int UNKNOWN_FILE = 0;
	public static final int MBM_FILE = 1;
	public static final int MIF_FILE = 2;

	enum SourceDisposition {
		FROM_VIEW,
		FROM_FILE
	};
	
	private SourceDisposition sourceDisposition;
	
	boolean openedFromFile;
	private int imageFileType;
	private String generatedExtension;
	/** The backing IFile (or null) */
	protected IResource file;

	/** The owned multi-image source, for a FROM_VIEW disposition */
	protected IMultiImageSource ownedMultiImageSource;
	/** The working multi-image source */
	protected IMultiImageSource multiImageSource;
	protected List<IMultiImageSource> allMultiImageSources;
	
	/**
	 * Creates a multi-page editor example.
	 * @param dialog if running in a dialog, the dialog 
	 */
	public MultiImageEditorContext() {
		super();
		
		this.imageFileType = UNKNOWN_FILE;
		this.generatedExtension = "bin"; //$NON-NLS-1$
		
	}
	
	/** Generic reaction to any change in the history. */
	protected void updateDirtyState() {
		isDirty = !multiImageSource.equals(ownedMultiImageSource);
		super.updateDirtyState();
	}


	/**
	 * Initialize from an open view.
	 */
	public void initFromView(IView view, IMultiImageSource source) {
		
		sourceDisposition = SourceDisposition.FROM_VIEW;
		this.ownedMultiImageSource = source;
		this.multiImageSource = ownedMultiImageSource.copy();
		this.view = view;
		this.model = view != null ? (IOwnedModel) view.getModel() : null;
		this.editorHistory = null;
		this.editorUndoContext = null;
		
		if (model != null) {
			IPath wsPath = FileUtils.convertToWorkspacePath(model.getPath(), true);
			if (wsPath != null) {
				this.file = ResourcesPlugin.getWorkspace().getRoot().getFile(wsPath);
				this.project = file.getProject();
				this.carbideProjectInfo = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			}
			setTargetFileName(multiImageSource.getTargetFile());
			
			this.containerModel = CarbideImageModelFactory.createMultiImageSourceModel(carbideProjectInfo, model.getPath(), multiImageSource);
		} else {
			this.file = null;
			this.project = null;
			this.carbideProjectInfo = null;
			setTargetFileName("untitled.mif"); //$NON-NLS-1$
			this.containerModel = CarbideImageModelFactory.createMultiImageSourceModel(null, null, multiImageSource);
		}
		openedFromFile = false;

		if (view instanceof IMMPView || !source.isSVGSourceAllowed()) {
			this.imageFileType = MBM_FILE;
			this.generatedExtension = "mbm"; //$NON-NLS-1$
		} else if (view instanceof IImageMakefileView) {
			this.imageFileType = MIF_FILE;
			this.generatedExtension = "mif"; //$NON-NLS-1$
			this.imagePathHelper = new ImageMakefileViewPathHelper(
					(IImageMakefileView) view, 
					new ISymbianBuildContext[] { getCarbideBuildConfiguration().getBuildContext() });
		}
		
		commonInit();
	}

	/**
	 * Get or refresh the multi image source.
	 * @throws PartInitException 
	 */
	private void getMultiImageSourceWorkaround() throws PartInitException {
		if (view instanceof IMMPView) {
			List<IMultiImageSource> multiImageSources = ((IMMPView)view).getMultiImageSources();
			if (multiImageSources.size() == 0) {
				throw new PartInitException(
						Messages.getString("MultiImageEditorContext.NoMbmsInMMPError") //$NON-NLS-1$
						+ model.getPath()); //$NON-NLS-1$
			}
			this.ownedMultiImageSource = multiImageSources.get(0);
			this.multiImageSource = ownedMultiImageSource.copy();
			this.imageFileType = MBM_FILE;
			this.generatedExtension = "mbm"; //$NON-NLS-1$
		} else {
			List<IMultiImageSource> multiImageSources = ((IImageMakefileView)view).getMultiImageSources();
			if (multiImageSources.size() == 0) {
				throw new PartInitException(
						Messages.getString("MultiImageEditorContext.NoMifsFoundError")  //$NON-NLS-1$
						+ model.getPath()); //$NON-NLS-1$
			}
			this.ownedMultiImageSource = multiImageSources.get(0);
			this.multiImageSource = ownedMultiImageSource.copy();
			this.imageFileType = MIF_FILE;
			this.generatedExtension = "mif"; //$NON-NLS-1$

		}
	}

	/**
	 * Save changes to the multi-image source.
	 * <p>
	 * If the context was initialized with a null IOperationHistory,
	 * the incoming IMultiImageSource is directly modified.  Otherwise,
	 * the change is executed and added as an operation to that history. 
	 */
	public void doSave() {
		if (sourceDisposition == SourceDisposition.FROM_VIEW) {
			// modify through the parent editor's operation history
			if (editorHistory != null) {
				// make the save operation for the parent editor
				IUndoableOperation operation = getEditingOperation();
				try {
					operation.addContext(editorUndoContext);
					editorHistory.execute(operation, null, null);
				} catch (ExecutionException e) {
					ProjectUIPlugin.log(e);
				}
			} else {
				ownedMultiImageSource.set(multiImageSource);
			}
		} else {
			// in this mode, we directly update the view
			while (true) {
				try {
					ownedMultiImageSource.set(multiImageSource);
					view.commit();
					break;
				} catch (IllegalStateException e) {
					// rollback or overwrite?
					ProjectUIPlugin.log(e);
					MessageDialog dialog = new MessageDialog(
							(Shell) editorContext.getAdapter(Shell.class),
							Messages.getString("MultiImageEditorContext.ConflictingChangesTitle"), //$NON-NLS-1$
							MessageDialog.getImage(MessageDialog.DLG_IMG_MESSAGE_ERROR),
							MessageFormat.format(
									Messages.getString("MultiImageEditorContext.ConflictingChangesMessage"), //$NON-NLS-1$
									new Object[] { ((IOwnedModel) view.getModel()).getPath() }),
							MessageDialog.ERROR,
							new String[] { IDialogConstants.OK_LABEL },
							0);
					dialog.open();
					view.revert();
				}
			}

			try {
				getMultiImageSourceWorkaround();
				
				// redraw entire UI
				this.page.dispose();
				createControl(this.parent);
				this.page.setVisible(true);
			} catch (PartInitException e) {
				// just fail -- this is a workaround, after all 
				ProjectUIPlugin.log(e);
			}
		}
	}

	/**
	 * Revert to last saved content.
	 */
	public void revert() {
		this.multiImageSource = ownedMultiImageSource.copy();
		setupOperationHistory();
		isDirty = false;
	}

	public void createControl(Composite parent) {
		this.parent = parent;
		this.page = new MultiImageListPage(parent, SWT.NONE, this);
		setHelpContext(page, ImageEditorIds.MULTI_IMAGE_EDITOR_DIALOG);
	}

	/**
	 * Get the extension (no leading dot) presumed for the output
	 * file.
	 * @return "mbm" or "mif"
	 */
	public String getGeneratedExtension() {
		return generatedExtension;
	}

	/**
	 * Get the multi-image source being edited.
	 * @return
	 */
	public IMultiImageSource getMultiImageSource() {
		return multiImageSource;
	}

	/**
	 * Get the edited resource, or null.
	 * @return
	 */
	public IResource getResource() {
		return file;
	}

	/**
	 * Test method
	 * @param mis
	 */
	public void setMultiImageSource(IMultiImageSource mis) {
		ownedMultiImageSource = mis;
		this.multiImageSource = ownedMultiImageSource.copy();
	}
	
	/**
	 * Set the other multi-image sources in the editing context.  Used
	 * to ensure the user doesn't rename the current multi-image source
	 * in a conflicting way.
	 * @param multiImageSources
	 */
	public void setMultiImageSources(List<IMultiImageSource> multiImageSources) {
		this.allMultiImageSources = multiImageSources;
	}


	public static void main(String[] args) {
		final String TEST_PREFS = "c:/temp/mii.prefs"; //$NON-NLS-1$

		Display display = new Display();
		
		IMultiImageSource mis = MultiImageSourceFactory.createMultiImageSource(true, true, true);
		mis.setTargetPath(new Path("\\resources\\apps")); //$NON-NLS-1$
		mis.setTargetFile("foo.mbm"); //$NON-NLS-1$
		mis.setHeaderFlags(EGeneratedHeaderFlags.Header);
		
		IBitmapSource bm = mis.createBitmapSource();
		bm.setPath(new Path("gfx/pic.bmp")); //$NON-NLS-1$
		bm.setMaskPath(new Path("mask-.bmp")); //$NON-NLS-1$
		bm.setColor(true);
		bm.setDepth(8);
		bm.setMaskDepth(8);
		mis.getSources().add(bm);
		
		bm = mis.createBitmapSource();
		bm.setPath(new Path("gfx/pic2-.bmp")); //$NON-NLS-1$
		bm.setMaskPath(new Path("gfx/mask2.bmp")); //$NON-NLS-1$
		bm.setColor(true);
		bm.setDepth(16);
		bm.setMaskDepth(1);
		mis.getSources().add(bm);
	
		ISVGSource sv = mis.createSVGSource();
		sv.setPath(new Path("aif/menu_icon.svg")); //$NON-NLS-1$
		sv.setColor(true);
		sv.setDepth(32);
		sv.setMaskDepth(8);
		mis.getSources().add(sv);
		
		sv = mis.createSVGSource();
		sv.setPath(new Path("aif/menu_icon2.svg")); //$NON-NLS-1$
		sv.setColor(true);
		sv.setDepth(32);
		sv.setMaskDepth(8);
		mis.getSources().add(sv);

		MultiImageEditorContext context = new MultiImageEditorContext();

		try {
			context.getDialogSettings().load(TEST_PREFS);
		} catch (IOException e) {
		}

		context.initFromView(null, mis);
		
		MultiImageEditorDialog dialog = new MultiImageEditorDialog((Shell)null, context);
		dialog.open();
		context.dispose();
		
		if (ProjectUIPlugin.getDefault() == null) {
			try {
				context.getDialogSettings().save(TEST_PREFS);
			} catch (IOException e) {
			}
		}

		display.dispose();
	}

	/**
	 * Get image file type
	 * @return MBM_FILE or MIF_FILE or UNKNOWN_FILE
	 */
	public int getFileType() {
		return imageFileType;
	}
	/**
	 * Tell whether the "HEADERONLY" semantics are supported.
	 * @return
	 */
	public boolean supportsHeaderOnlyFlag() {
		if (imageFileType == MBM_FILE) {
			// flag added in 9.2
			ISymbianBuildContext buildContext = getCarbideBuildConfiguration().getBuildContext();
			if (buildContext == null)
				return false;
			Version version = buildContext.getSDK().getOSVersion();
			if (version.getMajor() >= 9 && version.getMinor() >= 2)
				return true;
			else
				return false;
		} else if (imageFileType == MIF_FILE) {
			// nope: can't avoid output file
			return false;
		}
		return false;
	}

	/**
	 * Get the summary editing operation from the session.
	 * @return
	 */
	public IUndoableOperation getEditingOperation() {
		AbstractOperation operation = new AbstractOperation(
				MessageFormat.format(Messages.getString("MultiImageEditorContext.ModifyImageSourceCommandLabel"), //$NON-NLS-1$
						new Object[] { multiImageSource.getTargetFile() })) {
			
			IView theView = view;
			IPath origMultiImageSourcePath = ownedMultiImageSource.getTargetFilePath();
			IPath newMultiImageSourcePath = multiImageSource.getTargetFilePath();
			IMultiImageSource originalMultiImageSource = ownedMultiImageSource.copy();
			IMultiImageSource savedMultiImageSource = multiImageSource.copy();
			
			{
				if (origMultiImageSourcePath == null)
					origMultiImageSourcePath = newMultiImageSourcePath;
			}
	
			IMultiImageSource getMultiImageSource(IPath path) throws ExecutionException {
				List<IMultiImageSource> multiImageSourceList;
				if (theView instanceof IMMPView) {
					multiImageSourceList = ((IMMPView) theView).getMultiImageSources();
				} else {
					multiImageSourceList = ((IImageMakefileView) theView).getMultiImageSources();
				}
				for (IMultiImageSource mis : multiImageSourceList) {
					if (mis.getTargetFilePath() != null 
							&& mis.getTargetFilePath().equals(path)) {
						return mis;
					}
				}
				throw new ExecutionException("Could not find multi-image source that was edited at " + path); //$NON-NLS-1$
			}
			
			@Override
			public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				return redo(monitor, info);
			}
	
			@Override
			public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				IMultiImageSource mis = getMultiImageSource(origMultiImageSourcePath);
				mis.set(savedMultiImageSource);
				return Status.OK_STATUS;
			}
	
			@Override
			public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				IMultiImageSource mis = getMultiImageSource(newMultiImageSourcePath);
				if (mis == null)
					throw new ExecutionException(MessageFormat.format("Cannot locate {0} for undo", //$NON-NLS-1$
							new Object[] { newMultiImageSourcePath }));
				mis.set(originalMultiImageSource);
				return Status.OK_STATUS;
			}
		};
		return operation;
	}

	/**
	 * @return
	 */
	public IStatus getValidationStatus() {
		if (!multiImageSource.isValid()) {
			return Logging.newStatus(ProjectUIPlugin.getDefault(), IStatus.ERROR, 
				Messages.getString("MultiImageEditorContext.BadImageSourceNoTarget")); //$NON-NLS-1$
		}
		
		// check that at least one file is supplied for MBMs, else epocmbm fails
		if (getFileType() == MBM_FILE && multiImageSource.getSources().size() == 0) {
			return Logging.newStatus(ProjectUIPlugin.getDefault(), IStatus.ERROR, 
					Messages.getString("MultiImageEditorContext.EmptyMBMError")); //$NON-NLS-1$
		}
		
		// check for invalid filenames
		for (IImageSource source : multiImageSource.getSources()) {
			if (!isValidPath(source.getPath())) {
				return Logging.newStatus(ProjectUIPlugin.getDefault(), IStatus.WARNING, 
						MessageFormat.format(Messages.getString("MultiImageEditorContext.InvalidFilenameEnumeratorWarning"), //$NON-NLS-1$
								new Object[] { source.getPath() })); 
			}
			IPath maskPath = null;
			if (source instanceof IBitmapSource)
				maskPath =  ((IBitmapSource) source).getMaskPath();
			if (maskPath != null && !isValidPath(maskPath)) {
				return Logging.newStatus(ProjectUIPlugin.getDefault(), IStatus.WARNING, 
						MessageFormat.format(Messages.getString("MultiImageEditorContext.InvalidFilenameEnumeratorWarning"), //$NON-NLS-1$
								new Object[] { maskPath })); 
			}
		}
		
		// check for conflicts with other multi-image sources
		if (allMultiImageSources != null) for (IMultiImageSource source : allMultiImageSources) {
			if (source == ownedMultiImageSource || source == multiImageSource)
				continue;
			if (source.getTargetFilePath().toOSString().equalsIgnoreCase(
					multiImageSource.getTargetFilePath().toOSString())) {
				return Logging.newStatus(ProjectUIPlugin.getDefault(), IStatus.ERROR, 
					Messages.getString("MultiImageEditorContext.BadImageSourceSameTarget")); //$NON-NLS-1$
			}
			
			IPath sourceHeader = source.getGeneratedHeaderFilePath();
			if (sourceHeader == null && source.getHeaderFlags() != EGeneratedHeaderFlags.NoHeader)
				sourceHeader = source.getDefaultGeneratedHeaderFilePath();
			IPath myHeader = multiImageSource.getGeneratedHeaderFilePath(); 
			if (myHeader == null && multiImageSource.getHeaderFlags() != EGeneratedHeaderFlags.NoHeader)
				myHeader = multiImageSource.getDefaultGeneratedHeaderFilePath();
			
			if (sourceHeader != null && myHeader != null) { 
				if (sourceHeader.toOSString().equalsIgnoreCase(myHeader.toOSString())) {
					return Logging.newStatus(ProjectUIPlugin.getDefault(), IStatus.WARNING, 
						Messages.getString("MultiImageEditorContext.BadImageSourceSameHeaderPath")); //$NON-NLS-1$
				}
				if (sourceHeader.lastSegment().equalsIgnoreCase(myHeader.lastSegment())) {
					return Logging.newStatus(ProjectUIPlugin.getDefault(), IStatus.WARNING, 
						Messages.getString("MultiImageEditorContext.BadImageSourceSameHeaderName")); //$NON-NLS-1$
				}
			}
		}
		
		return Status.OK_STATUS;
	}
	
	/**
	 * @return
	 */
	public List<IImageSource> createImageSources(List<IImageSourceReference> imageSourceReferences) {
		List<IImageSource> imageSources = new ArrayList<IImageSource>();
		for (IImageSourceReference ref : imageSourceReferences) {
			IImageSource imageSource = null;
			if (ref instanceof IBitmapSourceReference) {
				imageSource = getMultiImageSource().createBitmapSource();
				imageSource.setFrom(ref);
			} else if (ref instanceof ISVGSourceReference) {
				imageSource = getMultiImageSource().createSVGSource();
				imageSource.setFrom(ref);
			} else {
				Check.checkState(false);
			}
			imageSources.add(imageSource);
		}
		return imageSources;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.MultiImageEditorContextBase#createBitmapSourceReference()
	 */
	@Override
	public IBitmapSourceReference createBitmapSourceReference() {
		return multiImageSource.createBitmapSource();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.MultiImageEditorContextBase#createSVGSourceReference()
	 */
	@Override
	public ISVGSourceReference createSVGSourceReference() {
		return multiImageSource.createSVGSource();
	}
	
}
