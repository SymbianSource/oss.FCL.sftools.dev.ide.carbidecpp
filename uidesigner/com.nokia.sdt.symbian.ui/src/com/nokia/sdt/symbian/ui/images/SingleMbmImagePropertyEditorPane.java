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
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * This pane in the image editor dialog allows editing standard 
 * Symbian image property, which allows the multi-image file, image, and mask
 * to be modified independently.
 * <p>
 * In this dialog, we use the MultiImageFileComboController to drive the
 * valid entries in the Images and Masks combos from the MbmImageMaskPropertyEditorPaneBase.
 *  
 * 
 *
 */
public class SingleMbmImagePropertyEditorPane extends MbmImageMaskPropertyEditorPaneBase implements IImageEditorDialogMainPane, IProjectImageInfoListener {
	private MultiImageFileComboController multiImageFileCombo;

	/**
	 * @param dialog
	 * @param parent
	 * @param projectImageInfo
	 * @param data
	 * @param object
	 * @param propertyPath
	 * @param cellEditorValidator
	 */
	public SingleMbmImagePropertyEditorPane(ImageEditorDialogBase dialog,
			Composite parent, ProjectImageInfo projectImageInfo,
			EObject object, String propertyPath) {
		super(dialog, parent, projectImageInfo, object, propertyPath);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.ui.images.MbmImageEditorDialogPaneBase#initComposite(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control startCreatingUI(Composite composite) {

		multiImageFileCombo = new MultiImageFileComboController(
				dialog, composite, projectImageInfo, 
				new MultiImageFileComboController.IMultiImageFileComboHandler() {

					/* (non-Javadoc)
					 * @see com.nokia.sdt.symbian.ui.images.MultiImageFileComboController.IMultiImageFileComboHandler#setMultiImageInfo(com.nokia.sdt.symbian.images.MultiImageInfo)
					 */
					public void setMultiImageInfo(MultiImageInfo info) {
						dialog.setValidationMessage(IStatus.OK, null); // clear the old validation message - in case it was sticky
						SingleMbmImagePropertyEditorPane.this.setMultiImageInfo(info);
						SingleMbmImagePropertyEditorPane.this.refresh();
						//refresh();
						//changeMultiImageFile(info);
						//MbmImagePropertyEditorPane.this.setMultiImageInfo(info);
						//resetImages();
						//refresh();
					}
					
					/* (non-Javadoc)
					 * @see com.nokia.sdt.symbian.ui.images.MultiImageFileComboController.IMultiImageFileComboHandler#refresh()
					 */
					public void refresh() {
						SingleMbmImagePropertyEditorPane.this.refresh();
					}
			});
		
		projectImageInfo.addListener(this);
        
        addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				SingleMbmImagePropertyEditorPane.this.projectImageInfo.removeListener(
						SingleMbmImagePropertyEditorPane.this);
			}
        	
        });
	        

	    return multiImageFileCombo;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.ui.images.MbmImageEditorDialogPaneBase#addListeners()
	 */
	@Override
	protected void finishCreatingUI(Composite parent) {
		super.finishCreatingUI(parent);
		
        

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
	
    /* (non-Javadoc)
     * @see com.nokia.sdt.symbian.ui.images.IImageEditorPane#getImagePropertyInfo()
     */
    public IImagePropertyInfoBase getImagePropertyInfo() {
    	return new ImagePropertyInfo(multiImageInfo, imageInfo, maskInfo);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.symbian.ui.images.IImageEditorPane#setImagePropertyInfo(com.nokia.sdt.datamodel.images.IImagePropertyInfo)
     */
    public void setImagePropertyInfo(IImagePropertyInfoBase infoBase) {
    	Check.checkArg(infoBase instanceof ImagePropertyInfo);
    	ImagePropertyInfo info = (ImagePropertyInfo) infoBase;
    	if (info.multiImageInfo == null) {
    		selectDefaultImage();
    	} else {
	    	setMultiImageInfo(info.multiImageInfo);
	    	multiImageFileCombo.setMultiImageInfo(multiImageInfo);
	    	imageInfo = info.bitmapInfo;
	    	maskInfo = info.maskInfo;
	    	refresh();
    	}
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.symbian.ui.images.ImageMaskPropertyEditorPaneBase#refresh()
     */
    @Override
    public void refresh() {
    	
    	// match an existing MultiImageInfo again
    	MultiImageInfo[] infos = projectImageInfo.getMultiImageInfos();
    	MultiImageInfo origInfo = multiImageInfo;
    	if (origInfo != null) {
	    	for (MultiImageInfo info : infos) {
	    		if (info.getBinaryFile().equals(origInfo.getBinaryFile())) {
	    			if (origInfo != info) {
		    			setMultiImageInfo(info);
		    			multiImageFileCombo.refresh();
	    			}
	    			break;
	    		}
	    	}
    	}
    	
    	super.refresh();

    	getShell().layout(true, true);

    	//setMultiImageInfo(multiImageInfo);
    	//changeMultiImageFile(multiImageInfo);
    	//multiImageFileCombo.setMultiImageInfo(multiImageInfo);
    	//setImageAndMaskComboItems(multiImageFileCombo.getImageFileComboItems(),
    	//		multiImageFileCombo.getMaskFileComboItems());
    }
    
    protected void selectDefaultImage() {
		MultiImageInfo[] infos = projectImageInfo.getMultiImageInfos();		
		if (infos.length > 0) {
			MultiImageInfo mii = infos[0];
			setMultiImageInfo(mii);
			multiImageFileCombo.setMultiImageInfo(mii);
			//changeMultiImageFile(mii);
			String[] enums = mii.getImageEnumeratorList();
			if (enums.length > 0) {
				ImageInfo info = mii.findImageByEnumerator(enums[0]);
				//imageThumb.changeImage(info);
				imageInfo = info;
				ImageInfo mask = mii.getMaskForImage(info);
				//maskThumb.changeImage(mask);
				maskInfo = mask;
				
				refresh();
        		dialog.setValidationMessage(IStatus.INFO, Messages.getString("ImageEditorDialog.SelectedDefaultMessage")); //$NON-NLS-1$
        		dialog.setStickyValidationMessage(true);
			}
		}
	}

    /* (non-Javadoc)
     * @see com.nokia.sdt.symbian.ui.images.MbmImageMaskPropertyEditorPaneBase#validate()
     */
    @Override
    public IStatus validate() {
    	return super.validate();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.symbian.ui.images.IImageEditorDialogMainPane#addButtons(org.eclipse.swt.widgets.Composite)
     */
    public void addButtons(Composite parent) {
    	
    }
	public void show() {
	}
	
	public void hide() {
		
	}

}
