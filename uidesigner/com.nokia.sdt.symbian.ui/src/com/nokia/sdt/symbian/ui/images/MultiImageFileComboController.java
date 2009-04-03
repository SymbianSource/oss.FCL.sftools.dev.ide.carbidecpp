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

import com.nokia.sdt.datamodel.images.IProjectImageInfo;
import com.nokia.sdt.datamodel.images.IProjectImageInfoListener;
import com.nokia.sdt.symbian.images.*;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * This controller provides UI and support for the file selector UI
 * in the image property editor.
 *  
 * 
 *
 */
public class MultiImageFileComboController extends Composite implements IProjectImageInfoListener {

	private static final String NAME_KEY = ".uid"; //$NON-NLS-1$
    private static final int MINIMUM_COMBO_WIDTH = 300;
    private static final int EDIT_MBM_MIF_ID = IDialogConstants.CLIENT_ID;

	private CCombo multiImageFileCombo;
    private String[] multiImageFileList;
	private Composite multiImageGroup;

	private Button editButton;
	private final ProjectImageInfo projectImageInfo;
	
	private MultiImageInfo multiImageInfo;
	private IMultiImageFileComboHandler handler;
	private ImageEditorDialogBase dialog;

	interface IMultiImageFileComboHandler {
		/**
		 * @param multiImageInfo 
		 * @param imageFileComboItems
		 * @param maskFileComboItems
		 */
		//void setImageAndMaskComboItems(MultiImageInfo multiImageInfo, String[] imageFileComboItems,
		//		String[] maskFileComboItems);

		/** Called when the MultiImageInfo has changed. */
		void refresh();
		
		/** Called after the MultiImageImage selected by the combo has been changed. */
		void setMultiImageInfo(MultiImageInfo info);
		
	}
	/**
	 * @param dialog
	 * @param parent
	 * @param projectImageInfo
	 * @param data
	 * @param object
	 * @param propertyPath
	 * @param cellEditorValidator
	 */
	public MultiImageFileComboController(final ImageEditorDialogBase dialog,
			Composite parent, ProjectImageInfo projectImageInfo,
			final IMultiImageFileComboHandler handler) {
		super(parent, SWT.NONE);
		this.projectImageInfo = projectImageInfo;
		this.handler = handler;
		this.dialog = dialog;
		
		projectImageInfo.addListener(this);
        
        addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				MultiImageFileComboController.this.projectImageInfo.removeListener(
						MultiImageFileComboController.this);
			}
        	
        });

		// the controls allowing selection of a multi-image file
	    multiImageGroup = this; //new Composite(parent, SWT.NONE);
	    multiImageGroup.setLayout(new FormLayout());

	    final CLabel multiImageFileLabel = new CLabel(multiImageGroup, SWT.NONE);
	    final FormData formData_2 = new FormData();
	    formData_2.top = new FormAttachment(0, 0);
	    multiImageFileLabel.setLayoutData(formData_2);
	    multiImageFileLabel.setText(Messages.getString("ImageEditorDialog.MultiImageFile")); //$NON-NLS-1$
	    multiImageFileLabel.setData(NAME_KEY, "multiImageFileLabel"); //$NON-NLS-1$
	
	    multiImageFileCombo = new CCombo(multiImageGroup, SWT.BORDER);
	    final FormData formData_3 = new FormData();
	    formData_3.top = new FormAttachment(multiImageFileLabel, 5, SWT.BOTTOM);
	    formData_3.left = new FormAttachment(multiImageFileLabel, 10, SWT.LEFT);
	    formData_3.width = MINIMUM_COMBO_WIDTH; 
	    multiImageFileCombo.setLayoutData(formData_3);
	    multiImageFileCombo.setData(NAME_KEY, "multiImageFileCombo"); //$NON-NLS-1$
	
	    final CLabel spacingMultiImageFileLabel = new CLabel(multiImageGroup, SWT.NONE);
	    final FormData formData_5 = new FormData();
	    formData_5.top = new FormAttachment(multiImageFileCombo, 0, SWT.BOTTOM);
	    spacingMultiImageFileLabel.setLayoutData(formData_5);

	    editButton = new Button(multiImageGroup, SWT.PUSH);
		editButton.setText(Messages
				.getString("ImageEditorDialog.EditMBMMIFButtonLabel")); //$NON-NLS-1$
		editButton.setData(new Integer(EDIT_MBM_MIF_ID));
		editButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editMbmMifFile();
			}
		});
	    final FormData formData_6 = new FormData();
	    formData_6.top = new FormAttachment(multiImageFileCombo, 0, SWT.TOP);
	    formData_6.left = new FormAttachment(multiImageFileCombo, 5, SWT.RIGHT);
	    editButton.setLayoutData(formData_6);

	    /*
        FormData fd_0 = new FormData();
	    fd_0.top = new FormAttachment(0);
	    fd_0.left = new FormAttachment(0);
	    multiImageGroup.setLayoutData(fd_0);
	    */
	    
        // initialize the list of multi image files,
        // and match up the current one with one in the list
        multiImageFileList = projectImageInfo.getMultiImageFileList();
        for (int i = 0; i < multiImageFileList.length; i++) {
            multiImageFileCombo.add(multiImageFileList[i]);
        }
	    
        multiImageFileCombo.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
            	selectMultiImageFile();
            }
        });
	}

	public void refresh() {
        //selectMultiImageFile();
        if (multiImageInfo.getFileType() == MultiImageInfo.MBM_FILE)
        	dialog.setValidationMessage(IStatus.INFO, Messages.getString("ImageEditorDialog.MbmDefInfo")); //$NON-NLS-1$
        else
        	dialog.setValidationMessage(IStatus.INFO, Messages.getString("ImageEditorDialog.MifDefInfo")); //$NON-NLS-1$
        
        //handler.refresh();

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
		refresh();
	}

	public MultiImageInfo getMultiImageInfo() {
		return multiImageInfo;
	}
	
	
	/**
	 * Edit the selected multi-image file.
	 */
	protected void editMbmMifFile() {
		ImageModelEditingUtilities.editMbmMifFile(getShell(), 
				projectImageInfo.getCarbideProjectInfo(), multiImageInfo);
		setMultiImageInfo(multiImageInfo);
		handler.refresh();
	}


	/**
     * Change the multi-image file to the selected one
     * @return true if setting changed
     */
    protected boolean selectMultiImageFile() {
        MultiImageInfo newMultiImageInfo = projectImageInfo.getInfoForBinaryFile(
        		new Path(multiImageFileCombo.getText()));
        boolean changed = false;
        if (newMultiImageInfo == null) {
        	setMultiImageInfo(null);
            changed = (multiImageInfo != null);
        } else {
            if (multiImageInfo == null || multiImageInfo != newMultiImageInfo) {
            	setMultiImageInfo(newMultiImageInfo);
                changed = true;
            }
        }
        if (changed)
        	handler.setMultiImageInfo(multiImageInfo);
        return changed;
    }

    /**
     * Change the multi-image file.
     * If the new file is the same as the old, we treat this
     * as a refresh command.
     * @param info new file, or null for none
     */
    public void setMultiImageInfo(MultiImageInfo info) {
        if (info == null) {
            multiImageInfo = null;
            multiImageFileCombo.setText(""); //$NON-NLS-1$
            //imageFileComboItems = null;
            //maskFileComboItems = null;
        } else {
            multiImageInfo = info;
            multiImageFileCombo.setText(info.getBinaryFile().toOSString());
            /*
            String[] imageItems = info.getImageFileList();
            String[] maskItems = info.getMaskFileList();
            
            // put the commands at the front
            imageFileComboItems = new String[imageItems.length + IMAGE_LIST_COMBO_ITEMS_REAL_START];
            System.arraycopy(imageItems, 0, imageFileComboItems, IMAGE_LIST_COMBO_ITEMS_REAL_START, imageItems.length);
            imageFileComboItems[IMAGE_LIST_COMBO_ITEMS_BLANK_INDEX] = Messages.getString("ImageEditorDialog.NoImageCommand"); //$NON-NLS-1$
            maskFileComboItems = new String[maskItems.length + IMAGE_LIST_COMBO_ITEMS_REAL_START];
            System.arraycopy(maskItems, 0, maskFileComboItems, IMAGE_LIST_COMBO_ITEMS_REAL_START, maskItems.length);
            maskFileComboItems[IMAGE_LIST_COMBO_ITEMS_BLANK_INDEX] = Messages.getString("ImageEditorDialog.NoImageCommand"); //$NON-NLS-1$
            */
        }
		
        if (multiImageInfo == null) {
            if (editButton != null)
            	editButton.setEnabled(false);
        } else {
            if (editButton != null)
            	editButton.setEnabled(true);
        }

    }

	/**
	 * @return
	 */
    public CCombo getCombo() {
		return multiImageFileCombo;
	}
}
