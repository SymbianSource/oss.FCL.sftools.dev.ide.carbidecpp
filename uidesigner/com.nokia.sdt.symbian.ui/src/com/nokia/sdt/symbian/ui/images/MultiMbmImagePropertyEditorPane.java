/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.sdt.datamodel.images.*;
import com.nokia.sdt.symbian.images.*;
import com.nokia.sdt.symbian.ui.UIPlugin;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This pane in the image editor dialog allows editing a multi-image
 * property where the file selection is common for all the images
 * but the image/pair info can change.
 *  
 * 
 *
 */
public class MultiMbmImagePropertyEditorPane extends Composite implements IImageEditorDialogMainPane, IProjectImageInfoListener {

	/**
	 * This is a pane that shows the image/mask/preview selectors but not the file combo.
	 * The file information is shared with the outer MultiMbmImagePropertyEditorPane.
	 * 
	 *
	 */
	private class SharedFileImagePropertyEditorPane extends
			MbmImageMaskPropertyEditorPaneBase {
		/**
		 * @param dialog
		 * @param parent
		 * @param projectImageInfo
		 * @param data
		 * @param object
		 * @param propertyPath
		 * @param cellEditorValidator
		 */
		private SharedFileImagePropertyEditorPane(ImageEditorDialogBase dialog,
				Composite parent, ProjectImageInfo projectImageInfo,
				EObject object, String propertyPath,
				ICellEditorValidator cellEditorValidator) {
			super(dialog, parent, projectImageInfo, object, propertyPath);
		}

		@Override
		protected void selectDefaultImage() {
			if (multiImageInfo == null)
				return;
				
			String[] enums = multiImageInfo.getImageEnumeratorList();
			if (enums.length > 0) {
				ImageInfo info = multiImageInfo.findImageByEnumerator(enums[0]);
				imageThumb.changeImage(info);
				ImageInfo mask = multiImageInfo.getMaskForImage(info);
				maskThumb.changeImage(mask);
				
        		dialog.setValidationMessage(IStatus.INFO, Messages.getString("MultiMbmImagePropertyEditorPane.SelectedDefaultImages.Message"));  //$NON-NLS-1$
        		dialog.setStickyValidationMessage(true);
			}
		}

		@Override
		protected Control startCreatingUI(
				Composite composite) {
			return null;
		}

		public IImagePropertyInfoBase getImagePropertyInfo() {
			return new ImagePropertyInfo(multiImageInfo, imageInfo, maskInfo);
		}

		public void setImagePropertyInfo(
				IImagePropertyInfoBase info) {
			Check.checkArg(info instanceof ImagePropertyInfo);
			setMultiImageInfo(((ImagePropertyInfo) info).multiImageInfo);
			imageInfo = ((ImagePropertyInfo) info).bitmapInfo;
			maskInfo = ((ImagePropertyInfo) info).maskInfo;
			refresh();
			
			//setImageAndMaskComboItems(multiImageFileComboController.getImageFileComboItems(), 
		    //						multiImageFileComboController.getMaskFileComboItems());
		
		}
		
	}


	private ProjectImageInfo projectImageInfo;
	private EObject object;
	private String propertyPath;
	private ICellEditorValidator validator;
	private Map<String, IImageEditorPane> imagePaneMap;
	private String[][] abstractProperties;
	private String filePropertyId;
	private ImageEditorDialogBase dialog;
	private MultiImageFileComboController multiImageFileComboController;
	private Combo abstractImageCombo;
	private String[] abstractIds;
	private LinkedHashMap<String, IImagePropertyInfo> infoMap;
	private Composite stackedImagePanes;
	private StackLayout stackLayout;

	/**
	 * @param dialog
	 * @param parent
	 * @param projectImageInfo
	 * @param data
	 * @param object
	 * @param propertyPath
	 * @param cellEditorValidator
	 */
	public MultiMbmImagePropertyEditorPane(ImageEditorDialogBase dialog,
			Composite parent, ProjectImageInfo projectImageInfo,
			EObject object, String propertyPath,
			ICellEditorValidator cellEditorValidator) {
		super(parent, SWT.NONE);
		this.dialog = dialog;
		this.projectImageInfo = projectImageInfo;
		this.object = object;
		this.propertyPath = propertyPath;
		this.validator = cellEditorValidator;

		createUI(this);
		
		//setImagePropertyInfo(data);
	}
	

	/* (non-Javadoc)
     * @see com.nokia.sdt.symbian.ui.images.IImageEditorPane#getImagePropertyInfo()
     */
    public IImagePropertyInfoBase getImagePropertyInfo() {
    	Map<String, IImagePropertyInfo> infoMap = new LinkedHashMap<String, IImagePropertyInfo>();
    	for (Map.Entry<String, IImageEditorPane> entry : imagePaneMap.entrySet()) {
    		infoMap.put(entry.getKey(), (IImagePropertyInfo)entry.getValue().getImagePropertyInfo());
    	}
    	return new SymbianMultiImagePropertyInfo(
    			filePropertyId,
    			abstractProperties,
    			infoMap);
    			
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.symbian.ui.images.IImageEditorPane#setImagePropertyInfo(com.nokia.sdt.datamodel.images.IImagePropertyInfo)
     */
    public void setImagePropertyInfo(IImagePropertyInfoBase info) {
    	Check.checkArg(info instanceof SymbianMultiImagePropertyInfo);
    	SymbianMultiImagePropertyInfo multiImagePropertyInfo = (SymbianMultiImagePropertyInfo) info;
		this.filePropertyId = multiImagePropertyInfo.getFilePropertyId();
		this.abstractProperties = multiImagePropertyInfo.getAbstractProperties();
		this.imagePaneMap = new LinkedHashMap<String, IImageEditorPane>();
		this.infoMap = new LinkedHashMap<String, IImagePropertyInfo>(
				multiImagePropertyInfo.getImagePropertyInfoMap());
		
		createAbstractEditorPanes();
		//refresh();
		selectDefaultImage();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.symbian.ui.images.IImageEditorDialogMainPane#validate()
     */
    public IStatus validate() {
    	StatusBuilder builder = new StatusBuilder(UIPlugin.getDefault());
    	for (IImageEditorPane pane : imagePaneMap.values()) {
    		IStatus status = pane.validate();
    		if (status.getSeverity() != IStatus.OK) {
    			builder.add(status);
    		}
    	}
    	IStatus status = builder.createStatus("", new Object[0]); //$NON-NLS-1$
    	if (status == null)
    		status = Status.OK_STATUS;
    	return status;
    }

	/**
	 * @param parent
	 */
	private void createUI(Composite parent) {
		parent = this;
		setLayout(new GridLayout(1, false));
		
		multiImageFileComboController = new MultiImageFileComboController(
				dialog, parent, projectImageInfo, 
				new MultiImageFileComboController.IMultiImageFileComboHandler() {
	
					/* (non-Javadoc)
					 * @see com.nokia.sdt.symbian.ui.images.MultiImageFileComboController.IMultiImageFileComboHandler#setMultiImageInfo(com.nokia.sdt.symbian.images.MultiImageInfo)
					 */
					public void setMultiImageInfo(MultiImageInfo info) {
						//MultiMbmImagePropertyEditorPane.this.setMultiImageInfo(info);
						multiImageFileComboController.setMultiImageInfo(info);
						for (IImageEditorPane pane : imagePaneMap.values()) {
							pane.setImagePropertyInfo(new ImagePropertyInfo(info, null, null));
						}
					}
					
					/* (non-Javadoc)
					 * @see com.nokia.sdt.symbian.ui.images.MultiImageFileComboController.IMultiImageFileComboHandler#refresh()
					 */
					public void refresh() {
						MultiMbmImagePropertyEditorPane.this.refresh();
					}
				});
		
		multiImageFileComboController.setLayoutData(
				new GridData(SWT.FILL, SWT.TOP, true, false));
		//multiImageFileComboController.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_BLUE));
		
		Composite selectAbstractIdBlock = new Composite(parent, SWT.NONE);
		//selectAbstractIdBlock.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_GREEN));
		selectAbstractIdBlock.setLayout(new GridLayout(2, false));
		selectAbstractIdBlock.setLayoutData(new GridData(
				SWT.FILL, SWT.CENTER, true, false
				));
		Label selectAbstractIdLabel = new Label(selectAbstractIdBlock, SWT.NONE);
		selectAbstractIdLabel.setText(Messages.getString("MultiMbmImagePropertyEditorPane.SelectSubImageLabel")); //$NON-NLS-1$

		abstractImageCombo = new Combo(selectAbstractIdBlock, SWT.READ_ONLY);
		abstractImageCombo.setLayoutData(new GridData(
				SWT.FILL, SWT.CENTER, false, false
				));
		abstractImageCombo.addSelectionListener(new SelectionAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selected = abstractImageCombo.getSelectionIndex();
				String currentId = null;
				if (selected >= 0) {
					currentId = abstractImageCombo.getItem(selected);
				}

				if (currentId == null)
					return;
				
				IImageEditorPane pane = imagePaneMap.get(currentId);
				Check.checkState(pane != null);
				
				stackLayout.topControl = (Control) pane;
				stackedImagePanes.layout();
				getShell().pack();
			}
		});

		Group imagePropertyEditingGroup = new Group(parent, SWT.SHADOW_IN);
		imagePropertyEditingGroup.setLayout(new GridLayout(1, false));
		imagePropertyEditingGroup.setLayoutData(new GridData(
				SWT.FILL, SWT.FILL, true, true
		));
		
		
		
		stackedImagePanes = new Composite(imagePropertyEditingGroup, SWT.NONE);
		stackLayout = new StackLayout();
		stackedImagePanes.setLayout(stackLayout);
		stackedImagePanes.setLayoutData(new GridData(
				SWT.FILL, SWT.FILL, true, true
				));
		
		//createAbstractEditorPanes();
		
		projectImageInfo.addListener(this);
        
        addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				MultiMbmImagePropertyEditorPane.this.projectImageInfo.removeListener(
						MultiMbmImagePropertyEditorPane.this);
			}
        	
        });
		
	}


	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.images.IProjectImageInfoListener#changed(com.nokia.sdt.datamodel.images.IProjectImageInfo)
	 */
	public void changed(IProjectImageInfo info) {
		refresh();
	}
	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.images.IProjectImageInfoListener#dirtyNotification(com.nokia.sdt.datamodel.images.IProjectImageInfo)
	 */
	public void dirtyNotification(IProjectImageInfo info) {
		
	}
	
	
	/**
	 * Refresh the UI with the data
	 */
	private void refresh() {
	   	// match an existing MultiImageInfo again
		
    	MultiImageInfo[] infos = projectImageInfo.getMultiImageInfos();
    	MultiImageInfo multiImageInfo = multiImageFileComboController.getMultiImageInfo();
    	if (multiImageInfo != null) {
	    	for (MultiImageInfo info : infos) {
	    		if (info.getBinaryFile().equals(multiImageInfo.getBinaryFile())) {
	    			if (info != multiImageInfo) {
	    				multiImageInfo = info;
	    				multiImageFileComboController.setMultiImageInfo(multiImageInfo);
	    			}
	    			break;
	    		}
	    	}
    	}
		
		// create the image property editors for each id
		for (Map.Entry<String, IImagePropertyInfo> entry : infoMap.entrySet()) {
			IImageEditorPane pane = imagePaneMap.get(entry.getKey());
			pane.setMultiImageInfo(multiImageInfo);
			pane.refresh();
		}
		
    	getShell().layout(true, true);

	}

	/**
	 * Create the panes for all the abstract images
	 */
	private void createAbstractEditorPanes() {
		int selected = abstractImageCombo.getSelectionIndex();
		String currentId = null;
		if (selected >= 0 && selected < abstractImageCombo.getItemCount()) {
			currentId = abstractImageCombo.getItem(selected);
		}

		this.abstractIds = new String[infoMap.size()];
		selected = -1;
		int idx = 0;
		for (String entry : infoMap.keySet()) {
			abstractIds[idx] = entry;
			if (entry.equals(currentId)) {
				selected = idx;
			}
			idx++;
		}
		abstractImageCombo.setItems(abstractIds);
		
		if (selected >= 0) {
			abstractImageCombo.setText(currentId);
		} else {
			abstractImageCombo.setText(abstractIds[0]);
		}
		
		// create the image property editors for each id
		boolean first = true;
		for (Map.Entry<String, IImagePropertyInfo> entry : infoMap.entrySet()) {
			ImagePropertyInfo imagePropertyInfo = (ImagePropertyInfo) entry.getValue();
			IImageEditorPane pane = imagePaneMap.get(entry.getKey());
			if (pane == null) {
				pane = createAbstractImagePane(imagePropertyInfo);
				if (first) {
					multiImageFileComboController.setMultiImageInfo(imagePropertyInfo.multiImageInfo);
					stackLayout.topControl = (Control) pane;
					first = false;
				}
				imagePaneMap.put(entry.getKey(), pane);
			}
		}
		
		stackedImagePanes.layout();
	}

	private IImageEditorPane createAbstractImagePane(IImagePropertyInfo imagePropertyInfo) {
		IImageEditorPane pane;
		pane = new SharedFileImagePropertyEditorPane(dialog, stackedImagePanes,
				projectImageInfo, object, propertyPath, validator);
		pane.setImagePropertyInfo(imagePropertyInfo);
		return pane;
	}


	/**
	 * 
	 */
	public void selectDefaultImage() {
		MultiImageInfo multiImageInfo = multiImageFileComboController.getMultiImageInfo();
		if (multiImageInfo == null) {
			MultiImageInfo[] infos = projectImageInfo.getMultiImageInfos();		
			if (infos.length > 0) {
				MultiImageInfo mii = infos[0];
				multiImageFileComboController.setMultiImageInfo(mii);
				for (IImageEditorPane pane : imagePaneMap.values()) {
					((SharedFileImagePropertyEditorPane) pane).setMultiImageInfo(mii);
					((SharedFileImagePropertyEditorPane) pane).selectDefaultImage();
				}
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.ui.images.IImageEditorDialogMainPane#addButtons()
	 */
	public void addButtons(Composite parent) {
		
	}
	
	public void show() {
	}
	
	public void hide() {
		
	}

}
