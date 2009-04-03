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

import com.nokia.carbide.cpp.internal.ui.images.FileImageModel;
import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.ICarbideSharedImages;
import com.nokia.carbide.cpp.ui.images.IFileImageModel;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.cpp.internal.api.utils.ui.DirectorySelectorWithHistory;
import com.nokia.cpp.internal.api.utils.ui.ProjectFolderSelectionDialog;
import com.nokia.sdt.utils.ui.ThumbnailGridViewer;
import com.nokia.sdt.utils.ui.ThumbnailWithDescriptionComposite;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.List;

/**
 * Base dialog which supports showing thumbnails of images which the user
 * may select.  Provides capability for copying images into the project as well.
 *   
 */
public abstract class SelectOrAddImagesDialogBase extends TrayDialog {

	private static final String PROJECT_DESTINATION_PATH_SETTING = "AddImages.ProjectDestinationPath"; //$NON-NLS-1$
	private static final String OVERWRITE_FILES_SETTING = "AddImages.OverwriteFiles"; //$NON-NLS-1$
	private static final String COPY_IMAGES_TO_PROJECT_SETTING = "AddImages.CopyImagesToProject"; //$NON-NLS-1$

	static public class CopiedImageModel extends FileImageModel {
		private final IPath destPath;

		public CopiedImageModel(IFileImageModel model, IPath destPath) {
			super(model.getImageContainerModel(), 
					model.getImageContainerModel().getBaseLocation(),
					model.getSourceLocation());
			this.destPath = destPath;
		}
		
		/** Get project-relative path (if relative) or absolute filesystem path (if absolute) */
		public IPath getDestinationPath() {
			return destPath;
		}
	}
	
	private static final String ADD_IMAGES_TO_PROJECT_DIALOG_SECTION = "AddImagesDialog"; //$NON-NLS-1$
	protected ThumbnailGridViewer thumbnailViewer;
	protected ThumbnailWithDescriptionComposite thumbAndDesc;
	private Composite fileCopyingComposite;
	protected List<IFileImageModel> selectedImages;

	// variables set from events on fileCopyingComposite
	protected boolean doCopyImages;
	protected Path projectDestination;
	protected boolean doOverwrite;
	protected List<CopiedImageModel> copiedImages;
	
	/**
	 * @param shell
	 */
	public SelectOrAddImagesDialogBase(Shell shell) {
		super(shell);
		this.selectedImages = Collections.EMPTY_LIST;
		setShellStyle(getShellStyle() | SWT.DIALOG_TRIM | SWT.RESIZE);    
	}

	/**
	 * @param parentShell
	 */
	public SelectOrAddImagesDialogBase(IShellProvider parentShell) {
		super(parentShell);
		this.selectedImages = Collections.EMPTY_LIST;
		setShellStyle(getShellStyle() | SWT.DIALOG_TRIM | SWT.RESIZE);    
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		if (Platform.isRunning()) {
			newShell.setImage(CarbideUIPlugin.getSharedImages().getImage(
					ICarbideSharedImages.IMG_ADD_IMAGE_FILE_16_16));
		}
	}

	@Override
	protected IDialogSettings getDialogBoundsSettings() {
		IDialogSettings settings = CarbideUIPlugin.getDefault().getDialogSettings();
		if (settings == null)
			return null;
		IDialogSettings section = settings.getSection(ADD_IMAGES_TO_PROJECT_DIALOG_SECTION);
		if (section == null) {
			section = settings.addNewSection(ADD_IMAGES_TO_PROJECT_DIALOG_SECTION);
		}
		return section;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
	
		createDialogContents(composite);
	
		// be sure to call #createThumbnailViewer() during #createDialogContents()
		Check.checkState(thumbnailViewer != null);
		thumbnailViewer.getComposite().setFocus();
		
		return composite;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		getButton(IDialogConstants.OK_ID).setEnabled(false);
	}
	
	/**
	 * Create the contents of the dialog.  At some point the thumbnail viewer should
	 * be created with {@link #createThumbnailViewer(Composite)}.  The composite
	 * has a GridLayout.
	 * @param composite
	 */
	protected abstract void createDialogContents(Composite composite);

	/** Configure the thumbnail and composite.  You need to:
	 * <li>set the viewer title 
	 * <li>set the content provider
	 * <li>set the label provider
	 * <li>set the input
	 * @param thumbAndDesc
	 */
	protected abstract void configureThumbnailViewer(ThumbnailWithDescriptionComposite thumbAndDesc);

	/**
	 * Create the thumbnail viewer in which users select what files to add.
	 */
	protected void createThumbnailViewer(Composite parent) {
		
		thumbAndDesc = new ThumbnailWithDescriptionComposite(
				parent, 
				SWT.FLAT | SWT.MULTI);
		
		
		thumbAndDesc.setAutoSelectFirst(false);
		thumbnailViewer = thumbAndDesc.getThumbnailViewer();
		
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		//gridData.minimumHeight = 00;
		thumbAndDesc.setLayoutData(gridData);
	
		thumbnailViewer.addPostSelectionChangedListener(new ISelectionChangedListener() {
			
			public void selectionChanged(SelectionChangedEvent event) {
				handleThumbnailViewerSelectionChanged((IStructuredSelection) event.getSelection());
			}
			
		});

		
		thumbnailViewer.addOpenListener(new IOpenListener() {
	
			public void open(OpenEvent event) {
			}
			
		});
	
		thumbAndDesc.setDescriptionTitle(Messages.getString("AddImagesDialog.HelpTextLabel")); //$NON-NLS-1$
		thumbAndDesc.setViewerTitle(Messages.getString("AddFilesystemImagesDialog.AvailableDirectoryImagesLabel")); //$NON-NLS-1$

		configureThumbnailViewer(thumbAndDesc);
	}

	/**
	 * Handle the change of selection inside the thumbnail viewer.
	 * @param selection
	 */
	protected void handleThumbnailViewerSelectionChanged(IStructuredSelection selection) {
		selectedImages = selection.toList();
		Button okButton = getButton(IDialogConstants.OK_ID);
		if (okButton != null)
			okButton.setEnabled(selection != null && !selection.isEmpty());
	}

	/**
	 * Create UI that lets the user type in a directory or select one from a Browse...
	 * button.
	 * @param parent parent with a GridLayout
	 * @return the DirectorySelectorWithHistory
	 */
	protected DirectorySelectorWithHistory createDirectorySelectionComposite(Composite parent) {
		Composite directoryComposite = new Composite(parent, SWT.NONE);
		directoryComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		directoryComposite.setLayout(new GridLayout(3, false));
		Label label = new Label(directoryComposite, SWT.NONE);
		label.setText(Messages.getString("AddFilesystemImagesDialog.DirectoryLabel")); //$NON-NLS-1$
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		
		DirectorySelectorWithHistory directories = new DirectorySelectorWithHistory(directoryComposite,
				getDialogSettings(), null);
		directories.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		return directories;
	}
	
	/**
	 * Get the dialog settings used to persist the directory list 
	 * @return null for no persistence or a IDialogSettings
	 */
	protected IDialogSettings getDialogSettings() {
		return null;
	}

	/**
	 * This adds a selection of widgets which provide a text entry, browse button,
	 * and overwrite-allowed button, plus behavior, for letting the user copy files
	 * into the project.
	 * <p>
	 * Once established, 'fileCopyingComposite' is set, and {@link #okPressed()} will
	 * copy the files.
	 * @param parent parent with a GridLayout
	 */
	protected void createCopyFilesToProjectComposite(Composite parent) {
		fileCopyingComposite = new Group(parent, SWT.NONE);
		fileCopyingComposite.setLayout(new GridLayout(1, false));
		fileCopyingComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		//Label separator = new Label(fileCopyingComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		//separator.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		Composite twoHalves = new Composite(fileCopyingComposite, SWT.NONE);
		twoHalves.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		twoHalves.setLayout(new GridLayout(2, false));

		final Button copyImagesButton = new Button(twoHalves, SWT.CHECK);
		copyImagesButton.setText(Messages.getString("AddFilesystemImagesDialog.CopyImagesCheckboxLabel")); //$NON-NLS-1$
		copyImagesButton.setSelection(true);
		doCopyImages = getBooleanSettingWithDefault(COPY_IMAGES_TO_PROJECT_SETTING, true);
		copyImagesButton.setSelection(doCopyImages);
		copyImagesButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
		copyImagesButton.addSelectionListener(new SelectionAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				doCopyImages = copyImagesButton.getSelection();
			}
		});

		final Button overwriteButton = new Button(twoHalves, SWT.CHECK);
		overwriteButton.setText(Messages.getString("AddFilesystemImagesDialog.OverwriteExistingCheckboxLabel")); //$NON-NLS-1$
		overwriteButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		doOverwrite = getBooleanSettingWithDefault(OVERWRITE_FILES_SETTING, false);
		overwriteButton.setSelection(doOverwrite);
		overwriteButton.addSelectionListener(new SelectionAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				doOverwrite = overwriteButton.getSelection();
			}
			
		});
		Composite textAndButton = new Composite(fileCopyingComposite, SWT.NONE);
		textAndButton.setLayout(new GridLayout(3, false));
		textAndButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label projectDestinationLabel = new Label(textAndButton, SWT.NONE);
		projectDestinationLabel.setText(Messages.getString("AddFilesystemImagesDialog.ProjectLocationLabel")); //$NON-NLS-1$
		projectDestinationLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));

		final Text projectDestinationText = new Text(textAndButton, SWT.BORDER);
		projectDestinationText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		projectDestination = new Path(getStringSettingWithDefault(PROJECT_DESTINATION_PATH_SETTING, "gfx")); //$NON-NLS-1$
		projectDestinationText.setText(projectDestination.toOSString());
		projectDestinationText.addModifyListener(new ModifyListener() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			public void modifyText(ModifyEvent e) {
				projectDestination = new Path(projectDestinationText.getText()); 
			}
		});
		
		final Button selectProjectLocationButton = new Button(textAndButton, SWT.PUSH);
		selectProjectLocationButton.setText(Messages.getString("AddFilesystemImagesDialog.ChooseButtonLabel")); //$NON-NLS-1$
		selectProjectLocationButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		copyImagesButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				projectDestinationText.setEnabled(copyImagesButton.getSelection());
				selectProjectLocationButton.setEnabled(copyImagesButton.getSelection());
				overwriteButton.setEnabled(copyImagesButton.getSelection());
			}
		});

		// this must be overridden if you are using this UI
		Check.checkState(getProject() != null);
		
		selectProjectLocationButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ProjectFolderSelectionDialog dialog = new ProjectFolderSelectionDialog(
						getShell(), getProject(), 
						Messages.getString("AddFilesystemImagesDialog.SelectDestinationFolderLabel")); //$NON-NLS-1$
				if (dialog.open() == IDialogConstants.OK_ID) {
					IPath path = dialog.getPath();
					projectDestinationText.setText(path.toString());
				}
			}
		});

	}

	/**
	 * Get a boolean setting with the given key, using the given default if not defined
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	protected boolean getBooleanSettingWithDefault(String key, boolean defaultValue) {
		IDialogSettings settings = getDialogSettings();
		if (settings == null)
			return defaultValue;
		String value = settings.get(key);
		if (value == null)
			return defaultValue;
		return Boolean.parseBoolean(value);
		
	}

	/**
	 * Get a boolean setting with the given key, using the given default if not defined
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	protected String getStringSettingWithDefault(String key, String defaultValue) {
		IDialogSettings settings = getDialogSettings();
		if (settings == null)
			return defaultValue;
		String value = settings.get(key);
		if (value == null)
			return defaultValue;
		return value;
		
	}
	
	protected void saveSetting(String key, String value) {
		IDialogSettings settings = getDialogSettings();
		if (settings == null)
			return;
		settings.put(key, value);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		if (fileCopyingComposite != null) {
			saveSetting(COPY_IMAGES_TO_PROJECT_SETTING, Boolean.toString(doCopyImages));
			saveSetting(OVERWRITE_FILES_SETTING, Boolean.toString(doOverwrite));
			saveSetting(PROJECT_DESTINATION_PATH_SETTING, projectDestination.toOSString());
			copyImagesToProject();
		}
		super.okPressed();
	}
	/**
	 * Get the project to which to add files.
	 * @return
	 */
	protected IProject getProject() {
		return null;
	}

	/**
	 * Invoke {@link #copyFileToDestination(Shell, IProject, IPath, StatusBuilder)} for
	 * all the selected images.  Invokes {@link ICopiedFilesHandler#filesCopied(List)}
	 * with the destination project-relative paths of the files copied.
	 */
	protected void copyImagesToProject() {
		ProgressBar copyingProgressBar = new ProgressBar(getShell(), SWT.HORIZONTAL);
		copyingProgressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		copyingProgressBar.setMinimum(0);
		copyingProgressBar.setMaximum(selectedImages.size());
		int index = 0;
		
		StatusBuilder builder = new StatusBuilder(CarbideUIPlugin.getDefault());
		
		copiedImages = new ArrayList<CopiedImageModel>();
		for (IFileImageModel model : selectedImages) {
			copyingProgressBar.setSelection(index++);
			IPath projectPath = copyImageToProject(model.getSourceLocation(), builder);
			copiedImages.add(new CopiedImageModel(model, projectPath));
		}
		
		if (builder.getTotalCount() != 0) {
			List<String> msgs = new ArrayList<String>();
			for (Object status : builder.getStatusList()) {
				msgs.add(((IStatus)status).getMessage());
			}
			MessageDialog.openError(
					getShell(), 
					Messages.getString("AddFilesystemImagesDialog.CopyingFailedTitle"),  //$NON-NLS-1$
					MessageFormat.format(Messages.getString("AddFilesystemImagesDialog.CopyingFailedDescription"), //$NON-NLS-1$
							new Object[] { 
							TextUtils.formatTabbedList(msgs) 
					}));
		}
		
		handleFilesCopied();
		
	}
	
	/**
	 * Perform some action on the list of files (see #copiedImages).
	 * Even if "copy images to project" was not specified, the copiedImages
	 * list is established.
	 */
	protected void handleFilesCopied() {
		
	}

	/**
	 * Utility routine used for copying a file from the filesystem to the project.  It should
	 * be called once for each selected item in the thumbnail viewer when {@link #copyImagesToProject()} 
	 * is called.
	 * <p>
	 * This uses the settings established by the {@link #createCopyFilesToProjectComposite(Composite)}
	 * UI to determine where in the project to copy the file and whether to overwrite or query.
	 * @param srcFullPath the filesystem path of the source
	 * @param builder status builder to which is added errors
	 * @return the resulting image path, relative to the project, or srcFullPath if the copy failed
	 */
	protected IPath copyImageToProject(IPath srcFullPath, StatusBuilder builder) {
		if (getProject() == null || !doCopyImages)
			return srcFullPath;
		
		String filename = srcFullPath.lastSegment();
		IPath destPath = projectDestination.append(filename);
		
		try {
			InputStream is = new FileInputStream(new File(srcFullPath.toOSString()));
			IFile file = getProject().getFile(destPath);
			if (file.exists()) {
				// check to see if the source and destinations are the same and disallow
				if (file.getLocation().equals(srcFullPath)) {
					MessageDialog.openError(getShell(), Messages.getString("AddFilesystemImagesDialog.OverwriteFileTitle"),  //$NON-NLS-1$
							MessageFormat.format(Messages.getString("AddFilesystemImagesDialog.SameSourceAndDestinationFilesError"), //$NON-NLS-1$
									new Object[] { file.getLocation() }));
				}
				else {	
					boolean rewrite = false;
					if (doOverwrite) {
						rewrite = true;
					} else {
						rewrite = MessageDialog.openQuestion(getShell(), Messages.getString("AddFilesystemImagesDialog.OverwriteFileTitle"),  //$NON-NLS-1$
								MessageFormat.format(Messages.getString("AddFilesystemImagesDialog.OverwriteFileMessage"), //$NON-NLS-1$
										new Object[] { file.getLocation() }));
					}
					if (rewrite) {
						file.setContents(is, false, true, new NullProgressMonitor());
					}
				}
			} else { 
				file.create(is, false, new NullProgressMonitor());
			}
			is.close();
			return destPath;
		} catch (IOException e) {
			builder.add(Logging.newStatus(CarbideUIPlugin.getDefault(), e));
			return srcFullPath;
		} catch (CoreException e) {
			builder.add(e.getStatus());
			return srcFullPath;
		}
		
	}
}