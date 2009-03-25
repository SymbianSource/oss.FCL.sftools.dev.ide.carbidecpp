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

import com.nokia.carbide.cpp.internal.project.ui.images.ISymbianMaskedFileImageModel;
import com.nokia.carbide.cpp.ui.images.IImageModel;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;
import com.nokia.sdt.datamodel.adapter.IImagePropertyRenderingInfo;
import com.nokia.sdt.datamodel.images.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.symbian.images.*;
import com.nokia.sdt.symbian.ui.UIPlugin;
import com.nokia.sdt.utils.ui.ImageIcon;

import org.eclipse.core.runtime.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import java.text.MessageFormat;

/**
 * This is the base class for the image editor dialog page that contains the UI
 * for editing one image property by selecting two attributes that
 * comprise a Symbian image -- image and mask.
 * <p>
 * (We use the term "image" instead of bitmap since SVG is supported in S60
 * 3.0).
 * <p>
 * The page is extended to support a normal MBM style property
 * which has an editable file.
 * 
 */
public abstract class MbmImageMaskPropertyEditorPaneBase extends Composite implements IImageEditorPane {

    private static final int IMAGE_LIST_COMBO_ITEMS_REAL_START = 1;
    protected static final int IMAGE_LIST_COMBO_ITEMS_BLANK_INDEX = 0;
    //protected static final int IMAGE_LIST_COMBO_ITEMS_ADD_INDEX = 1;
    private static final int MINIMUM_COMBO_WIDTH = 300;
	private static final String NAME_KEY = ".uid"; //$NON-NLS-1$
	//private static final int MAXIMUM_IMAGE_SIZE = 256;
	private static final String HELP_CONTEXT_ID = UIPlugin.PLUGIN_ID + "imageEditorDialogContextId"; //$NON-NLS-1$
	
    private CCombo maskCombo;
    private CCombo imageCombo;
    private CLabel imageInfoLabel;
    private CLabel maskInfoLabel;
    private CLabel appearanceInfoLabel;
    private ImageIcon imageIcon;
    private ImageIcon maskIcon;
    private ImageIcon appearanceIcon;
    /** the group containing the appearance icon */
    private Composite appearanceGroup;
    MultiImageInfo multiImageInfo;
    protected ImageInfo imageInfo;
	protected ImageInfo maskInfo;
    
    /**
     * Maintain the widgets associated with one combo, info label,
     * and icon, as well as the enumeration accessors, for the image or mask.
     *
     */
    protected abstract class ThumbnailInfo {
        private CCombo combo;
        private ImageIcon icon;
        private CLabel infoLabel;
        private boolean isMask;

        ThumbnailInfo(CCombo combo, ImageIcon icon, CLabel infoLabel, boolean isMask) {
            this.combo = combo;
            this.icon = icon;
            this.infoLabel = infoLabel;
            this.isMask = isMask;
        }

        abstract void setImageInfo(ImageInfo info);
        abstract ImageInfo getImageInfo();
        
        /**
         * Set the image to the current selection
         */
        public void setCurrentImage() {
            if (multiImageInfo == null)
                changeImage(null);
            else {
                ImageInfo info = multiImageInfo.findImageByFile(combo.getText());
                changeImage(info);
            }
        }

        /**
         * Set the image to the given info
         */
        void changeImage(ImageInfo imageInfo) {
            setImageInfo(imageInfo);
            refreshImage();
        }

        /**
         * Change the image to the given index
         */
        private void refreshImage() {
            // anything specific to the image or mask
            //onUpdateBehavior();

        	if (icon.getImage() != null) {
        		icon.getImage().dispose();
        		icon.setImage(null);
        	}
        	
        	ensureImageModel();
            ImageInfo info = getImageInfo();
            if (info != null) {
            	combo.setText(info.getFilePath());
                Image image = null;
                try {
                	IImageModel model = null;
                	if (imageModel != null) {
	                	if (!isMask)
	                		model = imageModel.getBitmapImageModel(true);
	                	else
	                		model = imageModel.getMaskImageModel(true);
	                	if (model != null) {
	                		image = model.getImageDescriptor(null).createImage();
	                	}
	                	icon.setImage(image);
	                	infoLabel.setText(getImageInformation(info, image));
                	}
                } catch (CoreException e) {
                	infoLabel.setText(e.getLocalizedMessage());
				}
            } else {
                combo.setText(""); //$NON-NLS-1$
                icon.setImage(null);
                infoLabel.setText(""); //$NON-NLS-1$
            }

            // update the appearance icon
            updateAppearanceIcon();
            
            Control dialogArea = dialog.getDialogArea();
            if (dialogArea != null)
            	dialogArea.pack(); // this allows the dialog to resize so that the image is not clipped (bug 6208)
        }
        
        protected void onUpdateBehavior() {
            
        }
        
        /**
         * Get string detailing info about the image
         * @param info the image
         * @return a string
         */
        private String getImageInformation(ImageInfo info, Image image) {
            if (info == null)
                return ""; //$NON-NLS-1$
            
            if (info.isSVG() && isMask)
            	return Messages.getString("ImageEditorDialog.ImplicitMaskInfo"); //$NON-NLS-1$
            
            String format;
            if (image == null) {
                format = Messages.getString("ImageEditorDialog.ImageInfoMissing"); //$NON-NLS-1$
            } else {
                format = Messages.getString("ImageEditorDialog.ImageInfoFormat"); //$NON-NLS-1$ 
            }
                
            Point size = icon.getImageSize();
            return MessageFormat.format(
                    format,
                    new Object[] { 
                          new Integer(size.x),
                          new Integer(size.y),
                          new Integer(info.getBitDepth()),
                          !isMask ?
                                  (info.isColor() ? 
                                          Messages.getString("ImageEditorDialog.Color") : //$NON-NLS-1$
                                              Messages.getString("ImageEditorDialog.Grayscale")) //$NON-NLS-1$
                          : Messages.getString("ImageEditorDialog.Mask") //$NON-NLS-1$
                                  
                    });
        }

        /**
         * Select current entry from the combo.
         * Two of these entries are special, and they either
         * clear the item or add a new item.
         */
        public void selectImageEntry() {
            int index = combo.getSelectionIndex();
            if (index == IMAGE_LIST_COMBO_ITEMS_BLANK_INDEX) {
                changeImage(null);
            }
            else if (index >= IMAGE_LIST_COMBO_ITEMS_REAL_START) {
                String filename = combo.getText();
				ImageInfo info = multiImageInfo.findImageByFile(filename);
                setImageInfo(info);
                refreshImage();
            }
            onUpdateBehavior();
            dialog.validate();
            dialog.getShell().layout(true, true);

        }

         
    }
    
    protected class ImageThumbnailInfo extends ThumbnailInfo {

        ImageThumbnailInfo() {
            super(imageCombo, imageIcon, imageInfoLabel, false);
        }
        /* (non-Javadoc)
         * @see com.nokia.sdt.symbian.ui.images.ImageEditorDialog.ThumbnailInfo#getImageInfo()
         */
        @Override
        ImageInfo getImageInfo() {
            return imageInfo;
        }
        
        /* (non-Javadoc)
         * @see com.nokia.sdt.symbian.ui.images.ImageEditorDialog.ThumbnailInfo#setImageInfo(com.nokia.sdt.symbian.images.ImageInfo)
         */
        @Override
        void setImageInfo(ImageInfo info) {
            imageInfo = info;
        }

        /* (non-Javadoc)
         * @see com.nokia.sdt.symbian.ui.images.ImageEditorDialog.ThumbnailInfo#onUpdateBehavior()
         */
        @Override
        protected void onUpdateBehavior() {
            super.onUpdateBehavior();
            
            // when setting the image, the mask is automatically
            // updated to match
            if (imageInfo == null) {
                maskThumb.changeImage(null);
            } else {
                maskThumb.changeImage(multiImageInfo.getMaskForImage(imageInfo));
            }
        }
    }
    
    class MaskThumbnailInfo extends ThumbnailInfo {

        MaskThumbnailInfo() {
            super(maskCombo, maskIcon, maskInfoLabel, true);
        }

        /* (non-Javadoc)
         * @see com.nokia.sdt.symbian.ui.images.ImageEditorDialog.ThumbnailInfo#setImageInfo(com.nokia.sdt.symbian.images.ImageInfo)
         */
        @Override
        void setImageInfo(ImageInfo info) {
            maskInfo = info;
        }

        /* (non-Javadoc)
         * @see com.nokia.sdt.symbian.ui.images.ImageEditorDialog.ThumbnailInfo#getImageInfo()
         */
        @Override
        ImageInfo getImageInfo() {
            return maskInfo;
        }
        
    }
     

    protected ThumbnailInfo imageThumb;
	protected ThumbnailInfo maskThumb;
    private Composite imageGroup;
    private Composite maskGroup;
    //private ImagePropertyInfo dataBlock;
    protected ProjectImageInfo projectImageInfo;
    
    private EObject object;
    private String propertyPath;
    private IImagePropertyRenderingInfo imagePropertyRenderingInfo;
    private ILookAndFeel laf;
	ImageEditorDialogBase dialog;
	private ISymbianMaskedFileImageModel imageModel;
	protected String[] imageFileComboItems;
	protected String[] maskFileComboItems;
	
	public MbmImageMaskPropertyEditorPaneBase(ImageEditorDialogBase dialog,
			Composite parent,
            ProjectImageInfo projectImageInfo,
            EObject object, String propertyPath) {
        super(parent, SWT.NONE);
        this.dialog = dialog;
        Check.checkArg(projectImageInfo);
        this.projectImageInfo = projectImageInfo;
        this.object = object;
        this.propertyPath = propertyPath;
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
        
        createUI();
        
        //setImagePropertyInfo(dataBlock);
    }

    /**
	 * 
	 */
	public void ensureImageModel() {
		if (multiImageInfo != null) {
			imageModel = SymbianImageModelFactory.createSymbianMaskedImageFileModel(
					multiImageInfo,
					imageInfo, maskInfo);
		} else {
			imageModel = null;
		}
	}

	private void createUI() {
    	setLayout(new FormLayout());
        
        Control top = startCreatingUI(this);
        if (top != null) {
	        FormData fd_0 = new FormData();
	        fd_0.top = new FormAttachment(0, 5);
	        fd_0.left = new FormAttachment(0, 0);
	        top.setLayoutData(fd_0);
        }
        
        Composite leftHandSide = new Composite(this, SWT.NONE);
        FormData fd_8 = new FormData();
        if (top != null)
        	fd_8.top = new FormAttachment(top, 5);
        else
        	fd_8.top = new FormAttachment(0, 5);
        fd_8.left = new FormAttachment(0, 0);
        fd_8.bottom = new FormAttachment(100, 0);
        leftHandSide.setLayoutData(fd_8);
        
        leftHandSide.setLayout(new FormLayout());
        
        createImageGroup(leftHandSide);
        FormData fd_1 = new FormData();
        fd_1.left = new FormAttachment(0, 5);
        fd_1.top = new FormAttachment(0, 5);
        fd_1.right = new FormAttachment(100, 0);
        imageGroup.setLayoutData(fd_1);

        createMaskGroup(leftHandSide);
        FormData fd_2 = new FormData();
        fd_2.left = new FormAttachment(imageGroup, 0, SWT.LEFT);
        fd_2.top = new FormAttachment(imageGroup, 10, SWT.BOTTOM);
        fd_2.bottom = new FormAttachment(100, 0);
        fd_2.right = new FormAttachment(100, 0);
        maskGroup.setLayoutData(fd_2);

        createAppearanceGroup(this);
        FormData fd_3 = new FormData();
        fd_3.top = new FormAttachment(leftHandSide, 0, SWT.TOP);
        fd_3.left = new FormAttachment(leftHandSide, 10, SWT.RIGHT);
        fd_3.right = new FormAttachment(100, -5);
        appearanceGroup.setLayoutData(fd_3);
        
        imageThumb = new ImageThumbnailInfo();
        maskThumb = new MaskThumbnailInfo();
        
        // add listeners after initializing

        finishCreatingUI(this);

        WorkbenchUtils.setHelpContextId(this, HELP_CONTEXT_ID);
    }

    /**
	 *	Add listeners to the UI once it's all in place 
	 */
	protected void finishCreatingUI(Composite parent) {
        
        imageCombo.addSelectionListener(new SelectionListener() {

            public void widgetSelected(SelectionEvent e) {
                imageThumb.selectImageEntry();
            }

            public void widgetDefaultSelected(SelectionEvent e) {
                imageThumb.selectImageEntry();
            }
        });


        maskCombo.addSelectionListener(new SelectionListener() {

            public void widgetSelected(SelectionEvent e) {
                maskThumb.selectImageEntry();
            }

            public void widgetDefaultSelected(SelectionEvent e) {
                maskThumb.selectImageEntry();
            }
        });

	}

	/**
     * Initialize the main content, which has a FormLayout in place.
	 * @param composite
	 */
	abstract protected Control startCreatingUI(Composite composite);

    /* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.ui.images.IImageEditorPane#setMultiImageInfo(com.nokia.sdt.symbian.images.MultiImageInfo)
	 */
    public void setMultiImageInfo(MultiImageInfo info) {
    	this.multiImageInfo = info;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.symbian.ui.images.IImageEditorPane#setImagePropertyInfo(com.nokia.sdt.datamodel.images.IImagePropertyInfo)
     */
    public void setImagePropertyInfo(IImagePropertyInfoBase info) {
    	Check.checkArg(info instanceof ImagePropertyInfo);
    	imageInfo = ((ImagePropertyInfo) info).bitmapInfo;
    	maskInfo = ((ImagePropertyInfo) info).maskInfo;
    	refresh();
    }

	/**
	 * Select some image by default.
	 */
    abstract protected void selectDefaultImage();
	
	private void createImageGroup(Composite parent) {
		////
	
	    // controls allowing selection of a bitmap image and seeing its icon
	    imageGroup = new Composite(parent, SWT.NONE);
	    imageGroup.setLayout(new FormLayout());
	    
	    final CLabel imageLabel = new CLabel(imageGroup, SWT.NONE);
	    final FormData formData_2_1 = new FormData();
	    formData_2_1.top = new FormAttachment(0, 0);
	    imageLabel.setLayoutData(formData_2_1);
	    imageLabel.setText(Messages.getString("ImageEditorDialog.Image")); //$NON-NLS-1$
	    imageLabel.setData(NAME_KEY, "imageLabel"); //$NON-NLS-1$
	
	    imageCombo = new CCombo(imageGroup, SWT.BORDER+SWT.READ_ONLY);
	    // Without this, a read-only CCombo looks disabled
	    imageCombo.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
	    final FormData formData_3_1 = new FormData();
	    formData_3_1.top = new FormAttachment(imageLabel, 5, SWT.BOTTOM);
	    formData_3_1.left = new FormAttachment(imageLabel, 10, SWT.LEFT);
	    formData_3_1.width = MINIMUM_COMBO_WIDTH; 
	    imageCombo.setLayoutData(formData_3_1);
	    imageCombo.setData(NAME_KEY, "imageCombo"); //$NON-NLS-1$
	
	    imageInfoLabel = new CLabel(imageGroup, SWT.NONE);
	    final FormData formData_4 = new FormData();
	    formData_4.top = new FormAttachment(imageCombo, 5, SWT.BOTTOM);
	    formData_4.left = new FormAttachment(imageCombo, 0, SWT.LEFT);
	    formData_4.right = new FormAttachment(imageCombo, 0, SWT.RIGHT);
	    imageInfoLabel.setLayoutData(formData_4);
	    imageInfoLabel.setAlignment(SWT.RIGHT);
	    imageInfoLabel.setData(NAME_KEY, "imageInfoLabel"); //$NON-NLS-1$
	
	    imageIcon = new ImageIcon(imageGroup, SWT.SHADOW_IN, true);
	    final FormData formData_1 = new FormData();
	    formData_1.left = new FormAttachment(imageCombo, 10, SWT.RIGHT);
	    formData_1.top = new FormAttachment(imageCombo, 0, SWT.TOP);
	    formData_1.bottom = new FormAttachment(100, -5);
	    formData_1.right = new FormAttachment(100, -5);
	    //formData_1.width = MAXIMUM_IMAGE_SIZE;
	    //formData_1.height = MAXIMUM_IMAGE_SIZE;
	    imageIcon.setLayoutData(formData_1);
	    imageIcon.setData(NAME_KEY, "imageIcon"); //$NON-NLS-1$
	    
	    //////////////
	}

	private void createMaskGroup(Composite parent) {
		// controls allowing selection of a mask and seeing its icon
	    maskGroup = new Composite(parent, SWT.NONE);
	    maskGroup.setLayout(new FormLayout());
	    
	    final CLabel maskLabel = new CLabel(maskGroup, SWT.NONE);
	    final FormData formData_2_2 = new FormData();
	    formData_2_2.top = new FormAttachment(0, 0);
	    maskLabel.setLayoutData(formData_2_2);
	    maskLabel.setText(Messages.getString("ImageEditorDialog.Mask2")); //$NON-NLS-1$
	    maskLabel.setData(NAME_KEY, "maskLabel"); //$NON-NLS-1$
	
	    maskCombo = new CCombo(maskGroup, SWT.BORDER+SWT.READ_ONLY);
	    // Without this, a read-only CCombo looks disabled
	    maskCombo.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
	    final FormData formData_3_2 = new FormData();
	    formData_3_2.top = new FormAttachment(maskLabel, 5, SWT.BOTTOM);
	    formData_3_2.left = new FormAttachment(maskLabel, 10, SWT.LEFT);
	    formData_3_2.width = MINIMUM_COMBO_WIDTH; 
	    maskCombo.setLayoutData(formData_3_2);
	    maskCombo.setData(NAME_KEY, "maskCombo"); //$NON-NLS-1$
	
	    maskInfoLabel = new CLabel(maskGroup, SWT.NONE);
	    final FormData formData_4_1 = new FormData();
	    formData_4_1.top = new FormAttachment(maskCombo, 5, SWT.BOTTOM);
	    formData_4_1.left = new FormAttachment(maskCombo, 0, SWT.LEFT);
	    formData_4_1.right = new FormAttachment(maskCombo, 0, SWT.RIGHT);
	    maskInfoLabel.setLayoutData(formData_4_1);
	    maskInfoLabel.setAlignment(SWT.RIGHT);
	    maskInfoLabel.setData(NAME_KEY, "maskInfoLabel"); //$NON-NLS-1$
	
	    maskIcon = new ImageIcon(maskGroup, SWT.SHADOW_IN, true);
	    final FormData formData_1_1 = new FormData();
	    formData_1_1.left = new FormAttachment(maskCombo, 10, SWT.RIGHT);
	    formData_1_1.top = new FormAttachment(maskCombo, 0, SWT.TOP);
	    formData_1_1.bottom = new FormAttachment(100, -5);
	    formData_1_1.right = new FormAttachment(100, -5);
	    //formData_1_1.width = MAXIMUM_IMAGE_SIZE;
	    //formData_1_1.height = MAXIMUM_IMAGE_SIZE;
	    maskIcon.setLayoutData(formData_1_1);
	    maskIcon.setData(NAME_KEY, "maskIcon"); //$NON-NLS-1$
	    
	    //////////////
	}

	private void createAppearanceGroup(Composite parent) {
		// the appearance icon
        appearanceGroup = new Composite(parent, SWT.NONE);
        appearanceGroup.setLayout(new FormLayout());
        
        final CLabel appearanceLabel = new CLabel(appearanceGroup, SWT.BOLD);
        final FormData formData_3_3 = new FormData();
        formData_3_3.top = new FormAttachment(0, 0);
        //formData_3_3.right = new FormAttachment(50, -5);
        appearanceLabel.setLayoutData(formData_3_3);
        appearanceLabel.setText(Messages.getString("ImageEditorDialog.Appearance")); //$NON-NLS-1$
        appearanceLabel.setData(NAME_KEY, "appearanceLabel"); //$NON-NLS-1$

        appearanceIcon = new ImageIcon(appearanceGroup, SWT.SHADOW_IN, false);
        final FormData formData_1_2 = new FormData();
        formData_1_2.top = new FormAttachment(appearanceLabel, 5, SWT.BOTTOM);
        formData_1_2.left = new FormAttachment(appearanceLabel, 20, SWT.LEFT);
        formData_1_2.right = new FormAttachment(100, -5);
	    //formData_1_2.width = MAXIMUM_IMAGE_SIZE;
	    //formData_1_2.height = MAXIMUM_IMAGE_SIZE;
        appearanceIcon.setLayoutData(formData_1_2);
        appearanceIcon.setData(NAME_KEY, "appearanceIcon"); //$NON-NLS-1$

       	appearanceInfoLabel = new CLabel(appearanceGroup, SWT.NONE);
        final FormData formData_4_2 = new FormData();
        formData_4_2.top = new FormAttachment(appearanceIcon, 5, SWT.BOTTOM);
        formData_4_2.left = new FormAttachment(0, 0);
        formData_4_2.right = new FormAttachment(appearanceIcon, 0, SWT.RIGHT);
        formData_4_2.bottom = new FormAttachment(100, 0);
        appearanceInfoLabel.setLayoutData(formData_4_2);
        appearanceInfoLabel.setAlignment(SWT.RIGHT);
        appearanceInfoLabel.setData(NAME_KEY, "appearanceInfoLabel"); //$NON-NLS-1$
        //
	}

	/**
     * Get string detailing info about the appearance icon
     * @return a string
     */
    private String getAppearanceInformation(ImageInfo info, Point size) {
        if (info == null || size == null)
            return ""; //$NON-NLS-1$ //Messages.getString("ImageEditorDialog.NoAppearanceInfoFormat"); //$NON-NLS-1$
        
        String format;
        format = Messages.getString("ImageEditorDialog.AppearanceInfoFormat"); //$NON-NLS-1$ 

        boolean color = info.isSVG() || info.isColor();
        int depth = info.isSVG() ? 32 : info.getBitDepth();
        
        return MessageFormat.format(
                format,
                new Object[] { 
                      new Integer(size.x),
                      new Integer(size.y),
                      new Integer(depth),
                      color ? Messages.getString("ImageEditorDialog.Color") : //$NON-NLS-1$
                                          Messages.getString("ImageEditorDialog.Grayscale") //$NON-NLS-1$
                              
                });
    }
    
    
    private void updateAppearanceIcon() {
        Image oldImage = appearanceIcon.getImage();
        if (oldImage != null)
            oldImage.dispose();
        
        if (multiImageInfo != null) {
            if (imagePropertyRenderingInfo == null || laf == null) {
                appearanceIcon.setImage(null);
                return;
            }
            
        	Runnable runnable = new Runnable() {
        		public void run() {
        			IImagePropertyRendering rendering = new SymbianImagePropertyRendering();
        			rendering.setImageProperty(ModelUtils.getComponentInstance(object), propertyPath, laf);
        			rendering.setImagePropertyRenderingInfo(imagePropertyRenderingInfo);
        			rendering.setImagePropertyInfo(
        					ModelUtils.getComponentInstance(object), 
        					(IImagePropertyInfo) getImagePropertyInfo());

                    Point size = imagePropertyRenderingInfo.getViewableSize(propertyPath, laf);
                    if (size == null)
                        return;

        			ImageData renderedData = rendering.getImageData();
                    
        			// pass ownership to icon
        			Image rendered = null;
        			if (renderedData != null)
        				rendered = new Image(getDisplay(), renderedData);
        			
                    appearanceIcon.setImage(rendered);

        			ImageInfo info = imageInfo != null ? imageInfo
        					: maskInfo != null ? maskInfo
        							: null;
        			appearanceInfoLabel.setText(getAppearanceInformation(info, size));
        		}
        	};
        	BusyIndicator.showWhile(getShell().getDisplay(), runnable);
        } else
            appearanceIcon.setImage(null);
    }

    /* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.ui.images.IImageEditorPane#validate()
	 */
    public IStatus validate() {
    	
    	String message = null;
    	int severity = IStatus.INFO;
    	
    	ensureImageModel();
    	if (imageModel == null)
    		return Status.OK_STATUS;
    	
    	// if no validator or no error, directly check -- note, currently this
    	// performs a redundant check, which we can't prove doesn't happen above.
    	if (message == null) {
    		 IStatus status = imageModel.validate();
    		 if (status != null && !status.isOK()) {
	        	message = status.getMessage();
	        	severity = status.getSeverity();
    		 }
    	}
    	
    	// validate image size info
    	if (message == null) {
    		try {
	    		ImageData realImage = imageModel.getImageDescriptor(null).getImageData();
	    		if (realImage != null) {
		    		IStatus status = dialog.validateImageSize(imagePropertyRenderingInfo,
		    				propertyPath, laf, imageModel.isSVG(),
		    				new Point(realImage.width, realImage.height));
		    		if (status != null && !status.isOK()) {
		    			message = status.getMessage();
		    			severity = status.getSeverity();
		    		}
	    		}
    		} catch (CoreException e) {
    			message = e.getStatus().getMessage();
    			severity = e.getStatus().getSeverity();
    		}
    	}
        
    	if (multiImageInfo == null) {
            imageCombo.setEnabled(false);
            maskCombo.setEnabled(false);
        } else {
            imageCombo.setEnabled(true);
            
            // for an SVG file, the mask cannot be selected
            boolean isSVG = imageInfo != null && imageInfo.isSVG();
            maskCombo.setEnabled(!isSVG);
            if (isSVG)
            	maskInfoLabel.setText(Messages.getString("ImageEditorDialog.ImplicitMaskInfo")); //$NON-NLS-1$

            maskIcon.setVisible(!isSVG);
        }
        
        if (message == null)
        	return Status.OK_STATUS;
        else
        	return Logging.newStatus(UIPlugin.getDefault(), severity, message);
    }
    
    /* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.ui.images.IImageEditorPane#resetImages()
	 */
    public void resetImages() {
        imageThumb.changeImage(null);
        maskThumb.changeImage(null);
    }

	/**
	 * Refresh the UI with the model.
	 */
	public void refresh() {
		// set the image/mask combos with the enums from the multi-image info
		
		if (multiImageInfo == null) {
            imageFileComboItems = new String[0];
            maskFileComboItems = new String[0];
            imageCombo.setItems(imageFileComboItems);
            maskCombo.setItems(maskFileComboItems);
        } else {
            String[] imageItems = multiImageInfo.getImageFileList();
            String[] maskItems = multiImageInfo.getMaskFileList();
            
            // put the commands at the front
            imageFileComboItems = new String[imageItems.length + IMAGE_LIST_COMBO_ITEMS_REAL_START];
            System.arraycopy(imageItems, 0, imageFileComboItems, IMAGE_LIST_COMBO_ITEMS_REAL_START, imageItems.length);
            imageFileComboItems[IMAGE_LIST_COMBO_ITEMS_BLANK_INDEX] = Messages.getString("ImageEditorDialog.NoImageCommand"); //$NON-NLS-1$
            maskFileComboItems = new String[maskItems.length + IMAGE_LIST_COMBO_ITEMS_REAL_START];
            System.arraycopy(maskItems, 0, maskFileComboItems, IMAGE_LIST_COMBO_ITEMS_REAL_START, maskItems.length);
            maskFileComboItems[IMAGE_LIST_COMBO_ITEMS_BLANK_INDEX] = Messages.getString("ImageEditorDialog.NoImageCommand"); //$NON-NLS-1$
            
            imageCombo.setItems(imageFileComboItems);
            maskCombo.setItems(maskFileComboItems);
        }
		
		// retain selection if possible
		String currentImage = imageInfo != null ? imageInfo.getFilePath() : ""; //$NON-NLS-1$
        String currentMask = maskInfo != null ? maskInfo.getFilePath() : ""; //$NON-NLS-1$
        
        boolean foundImage = false;
        for (String img : imageFileComboItems) {
        	if (img.equals(currentImage)) {
        		foundImage = true;
        		if (multiImageInfo != null) {
        			imageInfo = multiImageInfo.findImageByFile(img);
        		} else {
        			imageInfo = null;
        		}
        		imageCombo.setText(currentImage);
        		break;
        	}
        }
        if (foundImage) {
        	imageThumb.changeImage(imageInfo);
        } else {
        	imageThumb.changeImage(null);
        }
        
        boolean isSVG = imageInfo != null && imageInfo.isSVG();
        
        foundImage = false;
        for (String img : maskFileComboItems) {
        	if (img.equals(currentMask)) {
        		foundImage = true;
        		if (multiImageInfo != null) {
        			maskInfo = multiImageInfo.findImageByFile(img);
        		} else {
        			maskInfo = null; 
        		}
        		maskCombo.setText(currentMask);
        		break;
        	}
        }
        if (foundImage) {
        	maskThumb.changeImage(maskInfo);
        } else {
        	maskThumb.changeImage(isSVG ? maskInfo : null);
        }

        validate();

	}
}
