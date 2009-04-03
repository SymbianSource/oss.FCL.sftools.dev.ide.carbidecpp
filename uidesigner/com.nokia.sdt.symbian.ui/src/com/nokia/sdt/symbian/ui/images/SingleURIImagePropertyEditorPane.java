/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.symbian.ui.images;

import com.nokia.carbide.cdt.builder.DefaultViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.*;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExport;
import com.nokia.carbide.cpp.internal.project.ui.images.providers.ThumbnailImageModelLabelProvider;
import com.nokia.carbide.cpp.ui.images.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.*;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.datamodel.adapter.IImagePropertyRenderingInfo;
import com.nokia.sdt.datamodel.images.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.symbian.images.*;
import com.nokia.sdt.symbian.ui.UIPlugin;
import com.nokia.sdt.utils.*;
import com.nokia.sdt.utils.ui.*;

import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.progress.UIJob;

import java.text.MessageFormat;
import java.util.List;

/**
 * This pane in the image editor dialog allows editing a URI image property.
 *  
 */
public class SingleURIImagePropertyEditorPane extends Composite implements IImageEditorDialogMainPane, IProjectImageInfoListener {

	private static final int THUMB_WIDTH = 32;
	private static final int THUMB_HEIGHT = 32;
	private ImageEditorDialogBase dialog;
	private ProjectImageInfo projectImageInfo;
	private Text uriText;
	private String uriString;
	private ImageIcon imageIcon;
	private Label imageInfoLabel;
	private ImageDescriptor currentPreviewImageDescriptor;
	private Image currentPreviewImage;
	private Button uriSourceEditButton;
	private IURIRepresentableImageModel currentImageModel;
	private ThumbnailGridViewer thumbnailViewer;
	private EObject object;
	private String propertyPath;
    private IImagePropertyRenderingInfo imagePropertyRenderingInfo;
    private ILookAndFeel laf;
	private Button addFilesystemImageButton;
	private Button addProjectImageButton;

	/**
	 * @param singleMbmOrUriImageEditorDialog
	 * @param imageDialogPane
	 * @param projectImageInfo
	 * @param object
	 * @param propertyPath
	 * @param cellEditorValidator
	 */
	public SingleURIImagePropertyEditorPane(
			ImageEditorDialogBase dialog,
			Composite composite, ProjectImageInfo projectImageInfo,
			EObject object, String propertyPath) {
        super(composite, SWT.NONE);
        this.dialog = dialog;
        Check.checkArg(projectImageInfo);
        this.object = object;
        this.propertyPath = propertyPath;
        this.projectImageInfo = projectImageInfo;
        this.imagePropertyRenderingInfo = (IImagePropertyRenderingInfo) EcoreUtil.getRegisteredAdapter(object, IImagePropertyRenderingInfo.class);
        IDisplayModel displayModel;
        try {
            displayModel = ModelUtils.getDisplayModel(object);
            if (displayModel == null)
                this.laf = null;
            else
                this.laf = displayModel.getLookAndFeel();
        } catch (CoreException e) {
            this.laf = null;
        }

        projectImageInfo.addListener(this);
        
        addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				SingleURIImagePropertyEditorPane.this.projectImageInfo.removeListener(
						SingleURIImagePropertyEditorPane.this);
			}
        	
        });
        createUI(this);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.images.ProjectImageInfo.Listener#dirtyNotification(com.nokia.sdt.symbian.images.ProjectImageInfo)
	 */
	public void dirtyNotification(IProjectImageInfo info) {
		// expect the designer to handle this
		//info.refresh();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.images.ProjectImageInfo.Listener#changed(com.nokia.sdt.symbian.images.ProjectImageInfo)
	 */
	public void changed(IProjectImageInfo info) {
		reload();
	}
	
	/**
	 * 
	 */
	private void createUI(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		
		ThumbnailWithDescriptionComposite thumbnailComposite = 
			new ThumbnailWithDescriptionComposite(parent, SWT.FLAT);
		thumbnailComposite.setViewerTitle(Messages.getString("SingleURIImagePropertyEditorPane.SelectImageLabel")); //$NON-NLS-1$
		thumbnailComposite.setDescriptionTitle(Messages.getString("SingleURIImagePropertyEditorPane.HelpLabel")); //$NON-NLS-1$
		thumbnailComposite.getDescriptionText().setText(
				Messages.getString("SingleURIImagePropertyEditorPane.SelectImageHelp") //$NON-NLS-1$
				);
		GridData gridData2 = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData2.minimumHeight = 90;
		//gridData2.heightHint = 400;
		thumbnailComposite.setLayoutData(gridData2);
		
		thumbnailViewer = thumbnailComposite.getThumbnailViewer();
		
		ProjectBuildImagesProvider availableExportedImagesProvider = new ProjectBuildImagesProvider();
		thumbnailViewer.setContentProvider(availableExportedImagesProvider); 

		ThumbnailImageModelLabelProvider thumbnailImageLabelProvider = 
			new ThumbnailImageModelLabelProvider(new Point(THUMB_WIDTH, THUMB_HEIGHT));
		thumbnailViewer.setLabelProvider(thumbnailImageLabelProvider);
		
		thumbnailViewer.setInput(projectImageInfo);
		
		thumbnailViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				if (!(event.getSelection() instanceof IStructuredSelection))
					return;
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				
				IURIRepresentableImageModel model = (IURIRepresentableImageModel) selection.getFirstElement();
				if (currentImageModel == model)
					return;
				
				currentImageModel = model;
				
				String text = ""; //$NON-NLS-1$
				if (model != null) {
					// get the target path
					text = model.getURI();
					if (!uriText.getText().equals(text))
						uriText.setText(text);
					updateImagePath(text);
				}
			}
			
		});
		
		////
		
		Label description = new Label(parent, SWT.NONE);
		description.setText(Messages.getString("SingleURIImagePropertyEditorPane.ImageURILabel")); //$NON-NLS-1$
		description.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		/////////
		
		Composite textAndButton = new Composite(parent, SWT.NONE);
		textAndButton.setLayout(new GridLayout(2, false));
		textAndButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		uriText = new Text(textAndButton, SWT.BORDER);
		uriText.setToolTipText(Messages.getString("SingleURIImagePropertyEditorPane.URITextTooltipText")); //$NON-NLS-1$
		GridData gridData = new GridData(SWT.FILL, SWT.TOP, true, false);
		gridData.horizontalIndent = 6;
		uriText.setLayoutData(gridData);
		uriText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				updateSelectionToURI(uriText.getText());
			}
			
		});
		
		uriSourceEditButton = new Button(textAndButton, SWT.PUSH);
		uriSourceEditButton.setToolTipText(Messages.getString("SingleURIImagePropertyEditorPane.EditProvidingFileTooltipText")); //$NON-NLS-1$
		uriSourceEditButton.setText(Messages.getString("SingleURIImagePropertyEditorPane.EditButtonLabel")); //$NON-NLS-1$
		uriSourceEditButton.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false));
		uriSourceEditButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editUriSource();
			}
		});
		
		///////////
		
		// the preview group
		Label appearanceLabel = new Label(parent, SWT.NONE);
		appearanceLabel.setText(Messages.getString("SingleURIImagePropertyEditorPane.AppearanceLabel")); //$NON-NLS-1$
		description.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
		
		imageIcon = new ImageIcon(parent, SWT.NONE);
		imageIcon.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		
		imageInfoLabel = new Label(parent, SWT.NONE);
		imageInfoLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, false, false));
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.ui.images.IImageEditorDialogMainPane#addButtons()
	 */
	public void addButtons(Composite parent) {
		// A button for adding images
		addFilesystemImageButton = dialog.createPaneButton(parent, this, Messages.getString("SingleURIImagePropertyEditorPane.AddFilesystemButtonLabel")); //$NON-NLS-1$
		addFilesystemImageButton.setToolTipText(Messages.getString("SingleURIImagePropertyEditorPane.AddFilesystemHelpText")); //$NON-NLS-1$
		addFilesystemImageButton.setVisible(false);
		addFilesystemImageButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				UIJob job = new UIJob(Messages.getString("SingleURIImagePropertyEditorPane.AddFilesystemJobLabel")) { //$NON-NLS-1$

					@Override
					public IStatus runInUIThread(IProgressMonitor monitor) {
						AddFilesystemImagesForExportDialog dialog = new AddFilesystemImagesForExportDialog(
								getShell(), projectImageInfo.getProject());
						int ret = dialog.open();
						if (ret == IDialogConstants.OK_ID) {
							addProjectExports(dialog.getTargetPkgFile(), dialog.getNewExports());
							return Status.OK_STATUS;
						} else {
							return Status.CANCEL_STATUS;
						}
					}

					
				};
				
				job.setUser(false);
				job.schedule();
			}
		});
		
		// A button for adding images
		addProjectImageButton = dialog.createPaneButton(parent, this, Messages.getString("SingleURIImagePropertyEditorPane.ExportProjectButton")); //$NON-NLS-1$
		addProjectImageButton.setToolTipText(Messages.getString("SingleURIImagePropertyEditorPane.ExportProjectHelpText")); //$NON-NLS-1$
		addProjectImageButton.setVisible(false);
		addProjectImageButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				UIJob job = new UIJob(Messages.getString("SingleURIImagePropertyEditorPane.ExportProjectFilesTaskLabel")) { //$NON-NLS-1$

					@Override
					public IStatus runInUIThread(IProgressMonitor monitor) {
						AddProjectImagesForExportDialog dialog = new AddProjectImagesForExportDialog(
								getShell(), projectImageInfo.getProject());
						int ret = dialog.open();
						if (ret == IDialogConstants.OK_ID) {
							addProjectExports(dialog.getTargetPkgFile(), dialog.getNewExports());
							return Status.OK_STATUS;
						} else {
							return Status.CANCEL_STATUS;
						}
					}
					
				};
				
				job.setUser(false);
				job.schedule();
			}
		});
		
	}
	
	protected void addProjectExports(
			final IPath pkgFile, final List<NewImageExport> newExports) {
		// add exports to PRJ_EXPORTS
		EpocEnginePlugin.runWithBldInfView(projectImageInfo.getCarbideProjectInfo().getAbsoluteBldInfPath(),
				new DefaultViewConfiguration(projectImageInfo.getCarbideProjectInfo()),
				new BldInfViewRunnableAdapter() {
					public Object run(IBldInfView view) {
						for (NewImageExport newExport : newExports) {
							IExport targetExport = null;
							for (IExport export : view.getExports()) {
								if (export.getSourcePath().toOSString().equalsIgnoreCase(
										newExport.projectPath.toOSString())) {
									targetExport = export;
									break;
								}
							}
							if (targetExport == null) {
								targetExport = view.createExport();
								targetExport.setSourcePath(newExport.projectPath);
								targetExport.setTargetPath(newExport.targetPath);
								view.getExports().add(targetExport);
							} else {
								targetExport.setTargetPath(newExport.targetPath);
							}
						}
						do {
							try {
								view.commit();
								break;
							} catch (IllegalStateException e) {
								if (!reportFailedCommit(modelPath))
									break;
							}
						} while (true);
						return null;
					}
		});
		
		PKGModelHelper.runWithPKGView(
				pkgFile,
				new DefaultViewConfiguration(projectImageInfo.getCarbideProjectInfo()),
				new PKGViewRunnableAdapter() {
			public Object run(IPKGView view) {
				for (NewImageExport newExport : newExports) {
				
					/*
					// see if there is already an export with this file
					IPKGInstallFile targetInstall = null;
					boolean existed = false;
					for (IPKGInstallFile install : view.getAllInstallFiles()) {
						for (IPath source : install.getSourceFiles().values()) {
							if (source.toOSString().equalsIgnoreCase(
									newExport.projectPath.toOSString())) {
								existed = true;
								break;
							}
						}
					}
					
					// TODO: update entry later
					if (existed)
						return null;
					*/
					IPKGInstallFile file = view.createInstallFile(null);
					
					// add entry for same path in every language
					for (EPKGLanguage language : view.getLanguages()) {
						file.getSourceFiles().put(language, new Path("$(EPOCROOT)" + newExport.epocPath.toOSString()));
					}
					file.setDestinationFile(newExport.targetPath);
					
					view.addInstallFile(file);
				}
				
				do {
					try {
						view.commit();
						break;
					} catch (IllegalStateException e) {
						if (!reportFailedCommit(modelPath))
							break;
					}
				} while (true);		
				
				return null;
			}
		});
	}
	
	/**
	 * @param modelPath
	 * @param exception
	 */
	protected boolean reportFailedCommit(IPath modelPath) {
		String msg;
		msg = MessageFormat.format(Messages.getString("SingleURIImagePropertyEditorPane.FailedToSaveMessage"), //$NON-NLS-1$
				new Object[] { modelPath });
		return MessageDialog.openConfirm(getShell(), Messages.getString("SingleURIImagePropertyEditorPane.FailedToSaveTitle"), //$NON-NLS-1$
				msg);
	}

	public void show() {
		if (addFilesystemImageButton != null)
			addFilesystemImageButton.setVisible(true);
		if (addProjectImageButton != null)
			addProjectImageButton.setVisible(true);
	}
	
	public void hide() {
		addFilesystemImageButton.setVisible(false);
		addProjectImageButton.setVisible(false);
	}

	
	/**
	 * Update the selection to the given URI 
	 * @param text
	 */
	protected void updateSelectionToURI(String text) {
		currentImageModel = null;
		for (IImageModel model : projectImageInfo.getProjectImageModels()) {
			if (model instanceof IURIRepresentableImageModel) {
				IURIRepresentableImageModel uriModel = (IURIRepresentableImageModel) model;
				if (uriModel.getURI().equals(text)) {
					currentImageModel = uriModel;
					if (currentImageModel.getImageContainerModel() != null
							&& currentImageModel.getImageContainerModel().createEditorProvider() != null) {
						uriSourceEditButton.setEnabled(true);
					}
					break;
				}
			}
		}
		// don't reset selection if no model found; this results in oscillating
		// notifications that end up messing up the text
		if (currentImageModel != null)
			thumbnailViewer.setSelection(new StructuredSelection(currentImageModel));
		updateImagePath(text);
	}

	/**
	 * Edit the file providing the URI
	 */
	protected void editUriSource() {
		if (currentImageModel == null || currentImageModel.getImageContainerModel() == null)
			return;
		
		IImageContainerEditorProvider editorProvider = currentImageModel.getImageContainerModel().createEditorProvider();
		if (editorProvider == null)
			return;
		
		Job editingJob = editorProvider.createEditingJob(getShell());
		editingJob.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				reload();
				// refresh buttons and stuff
				updateSelectionToURI(uriString);
			}
		});
		editingJob.schedule();
	}

	/**
	 * Update the URI from the given string
	 * @param string
	 */
	protected void updateImagePath(String string) {
		uriString = string;
		
		updateImage();
		
		dialog.validate();
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Widget#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		if (currentPreviewImage != null && currentPreviewImageDescriptor != null)
			currentPreviewImageDescriptor.destroyResource(currentPreviewImage);
		currentPreviewImage = null;
		currentPreviewImageDescriptor = null;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.ui.images.IImageEditorDialogMainPane#getImagePropertyInfo()
	 */
	public IImagePropertyInfoBase getImagePropertyInfo() {
	   	return new ImagePropertyInfo(new URIImageInfo(projectImageInfo, uriString));
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.ui.images.IImageEditorDialogMainPane#setImagePropertyInfo(com.nokia.sdt.datamodel.images.IImagePropertyInfoBase)
	 */
	public void setImagePropertyInfo(IImagePropertyInfoBase info_) {
    	Check.checkArg(info_ instanceof ImagePropertyInfo);
    	ImagePropertyInfo info = (ImagePropertyInfo) info_;
    	URIImageInfo uriInfo = info.uriInfo;
    	if (uriInfo != null)
    		uriString = uriInfo.getPropertyString();
    	else if (info.uri != null)
    		uriString = info.uri;
    	else
    		uriString = ""; //$NON-NLS-1$
    	
    	uriText.setText(uriString);
    	updateSelectionToURI(uriString);
    	refresh();
		
	}

	/**
	 * 
	 */
	private void refresh() {
		validate();
	}

	/**
	 * Update the icon and description from the current URI string and selection
	 */
	private void updateImage() {
    	Runnable runnable = new Runnable() {
    		public void run() {
    			IImagePropertyRendering rendering = new UIQImagePropertyRendering();
    			rendering.setImagePropertyRenderingInfo(imagePropertyRenderingInfo);
    			rendering.setImagePropertyInfo(
    					ModelUtils.getComponentInstance(object), 
    					(IImagePropertyInfo) getImagePropertyInfo());

                Point size = imagePropertyRenderingInfo.getViewableSize(propertyPath, laf);

                if (currentPreviewImage != null) {
                	currentPreviewImage.dispose();
                	currentPreviewImage = null;
                }
                
                ImageData imageData = rendering.getImageData();
                if (imageData != null) {
                	currentPreviewImage = new Image(getDisplay(), imageData);
                	if (size == null) {
                		size = new Point(imageData.width, imageData.height);
                	}
                }

    			// pass ownership to icon
    			if (currentPreviewImage != null) {
	                imageIcon.setImage(currentPreviewImage);
	
	    			imageInfoLabel.setText(MessageFormat.format(Messages.getString("SingleURIImagePropertyEditorPane.ImageFormatFormat"), //$NON-NLS-1$
							new Object[] { 
							size.x, size.y,
							ImageUtils.getImageBitDepth(currentPreviewImage)
	    				})
	    			);
	
	    			//dialog.getShell().pack();			
    			} else {
    				imageIcon.setImage(null);
    				imageInfoLabel.setText(""); //$NON-NLS-1$
    			}
    			
    			dialog.getShell().layout(true, true);
    		}
    	};
    	BusyIndicator.showWhile(getShell().getDisplay(), runnable);
		
    	// see if the image is known and enable editing its source
		uriSourceEditButton.setEnabled(false);
		if (currentImageModel != null) {
			if (currentImageModel.getImageContainerModel() != null
					&& currentImageModel.getImageContainerModel().createEditorProvider() != null) {
				uriSourceEditButton.setEnabled(true);
			}
		}
	}
	

	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.ui.images.IImageEditorDialogMainPane#validate()
	 */
	public IStatus validate() {
    	String message = null;
    	int severity = IStatus.INFO;
    	
    	URIImageInfo info = new URIImageInfo(projectImageInfo, uriString);
    	
    	if (message == null) {
    		IImageModel model = info.getImageModel();
    		if (model != null) {
	    		IStatus status = model.validate(); 
		        if (status != null && !status.isOK()) {
		        	message = status.getMessage();
		        	severity = status.getSeverity();
		        }
    		}
    	}
    	
    	// check the URI for validity
    	if (message == null) {
    		
    		IStatus status = info.validate();
    		if (status != null && !status.isOK()) {
    			message = status.getMessage();
    			severity = status.getSeverity();
    		}
    	}
    	
    	// finally, warn if user selects an unknown image
    	if (message == null && currentImageModel == null && currentPreviewImage == null) {
    		message = Messages.getString("SingleURIImagePropertyEditorPane.UnrecognizedURIMessage"); //$NON-NLS-1$
    		severity = IStatus.WARNING;
    	}
    	
        if (message == null)
        	return Status.OK_STATUS;
        else
        	return Logging.newStatus(UIPlugin.getDefault(), severity, message);
	}
    
	/**
	 * Reload information from the project image info, either due to an explicit
	 * notification of its change, or likely change due to editing behavior.
	 */
	private void reload() {
		thumbnailViewer.setInput(projectImageInfo);
		
	}
}
