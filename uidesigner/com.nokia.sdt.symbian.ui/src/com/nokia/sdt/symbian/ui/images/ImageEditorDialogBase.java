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

import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.StatusBuilder;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;
import com.nokia.sdt.datamodel.adapter.IImagePropertyRenderingInfo;
import com.nokia.sdt.datamodel.images.IImagePropertyInfoBase;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.symbian.SymbianPlugin;
import com.nokia.sdt.symbian.images.Messages;
import com.nokia.sdt.symbian.ui.UIPlugin;
import com.nokia.sdt.utils.*;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * This dialog allows the user to edit one or more image properties.
 * 
 *
 */
public abstract class ImageEditorDialogBase extends TrayDialog implements IImageEditorDialog {
    /**
	 * 
	 */
	private static final String IMAGE_EDITOR_DIALOG_SECTION = "ImageEditorDialog"; //$NON-NLS-1$

	protected static final int IMAGE_LIST_COMBO_ITEMS_BLANK_INDEX = 0;

	private static final String NAME_KEY = ".uid"; //$NON-NLS-1$
	private static final String HELP_CONTEXT_ID = UIPlugin.PLUGIN_ID + "imageEditorDialogContextId"; //$NON-NLS-1$

    private Image warningIcon, errorIcon;
    protected IImagePropertyInfoBase dataBlock;

    private CLabel messageLabel;
    
	protected ICellEditorValidator cellEditorValidator;
	private boolean stickyValidationMessage;	// used to keep an initial message for a while
	protected IImageEditorDialogMainPane editorPane;

	public ImageEditorDialogBase(Shell parentShell, 
			//ProjectImageInfo projectImageInfo, Object data, 
            //EObject object, String propertyPath,
			IImagePropertyInfoBase imageData,
            ICellEditorValidator cellEditorValidator) {
        super(parentShell);
        //Check.checkArg(projectImageInfo);
        //setShellStyle(SWT.CLOSE | SWT.TITLE | SWT.BORDER
        //       | SWT.APPLICATION_MODAL | SWT.RESIZE | getDefaultOrientation());
        setShellStyle(SWT.DIALOG_TRIM | SWT.MODELESS | SWT.RESIZE);        
        this.cellEditorValidator = cellEditorValidator;
        
        this.dataBlock = imageData;
            
        if (Platform.isRunning()) {
	        errorIcon = PlatformUI.getWorkbench()
	            .getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
	        warningIcon = PlatformUI.getWorkbench()
	            .getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK);
        }
    }

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#getDialogBoundsSettings()
	 */
	@Override
	protected IDialogSettings getDialogBoundsSettings() {
		IDialogSettings settings = UIPlugin.getDefault().getDialogSettings();
		if (settings == null)
			return null;
		IDialogSettings section = settings.getSection(IMAGE_EDITOR_DIALOG_SECTION);
		if (section == null) {
			section = settings.addNewSection(IMAGE_EDITOR_DIALOG_SECTION);
		}
		return section;
	}
	
    /**
     * Get the current dialog data, in a new object
     * @return new dialog data
     */
    public Object getDialogData() {
    	return editorPane.getImagePropertyInfo();
    }

    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        //container.setLayout(new GridLayout(1, false));

        // top of dialog: a label indicating errors/warnings
        messageLabel = new CLabel(container, SWT.WRAP);
        //messageLabel.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_CYAN));
        messageLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
        messageLabel.setData(NAME_KEY, "messageLabel"); //$NON-NLS-1$
        
        //////////
        
        // main content
        editorPane = createEditorPane(container); 
        ((Control)editorPane).setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        
        editorPane.setImagePropertyInfo(dataBlock);
        
        WorkbenchUtils.setHelpContextId(container, HELP_CONTEXT_ID);
        
        editorPane.show();
 
        return container;
    }

	/**
	 * 
	 */
	//abstract protected void initImage();

	/**
	 * Create the editor for the image property info.  The parent
	 * has no layout.
	 * @param container
	 * @return
	 */
    abstract protected IImageEditorDialogMainPane createEditorPane(Composite container);

    
    /**
     * Validate current contents and update enabled/disabled states.
     *
     */
    public void validate() {
    	IStatus status = null;
    	if (editorPane == null)
    		return;
    	
    	// check existing cell editor validator, which might have extended checking
    	if (cellEditorValidator != null) {
    		String message = cellEditorValidator.isValid(getDialogData());
    		int severity;
	    	boolean isValid = (message == null || message.length() == 0);
	    	if (!isValid) {
	    		severity = IStatus.ERROR;
	    		status = Logging.newStatus(UIPlugin.getDefault(), severity, message);
	    	} else {
	    		message = null;
	    	}
    	}

    	// if no validator or no error, directly check -- note, currently this
    	// performs a redundant check, which we can't prove doesn't happen above.
    	if (status == null || status.isOK()) {
    		
    		// validate the current page first
    		status = editorPane.validate();
    	}
    	
        boolean allowExit = (status.getSeverity() != IStatus.ERROR);
        
        if (!stickyValidationMessage) {
        	if (status.isOK())
        		setValidationMessage(IStatus.OK, null);
        	else
        		setValidationMessage(status.getSeverity(), status.getMessage());
        }
        else
    		stickyValidationMessage = false;
        
        Button okButton = getButton(IDialogConstants.OK_ID);
        if (okButton != null)
            okButton.setEnabled(allowExit);
        
        //getShell().pack();
    }
    
    /**
	 * @param severity
	 * @param message
	 */
	public void setValidationMessage(int severity, String message) {
        if (message != null) {
            messageLabel.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
            messageLabel.setImage(severity == IStatus.ERROR ? errorIcon 
                            : severity == IStatus.WARNING 
                            ? warningIcon : null);
            // don't let & embolden or underline a letter
            messageLabel.setText(message.replaceAll("&", "&&")); //$NON-NLS-1$ //$NON-NLS-2$
            
        } else {
            messageLabel.setBackground((Color)null);
            messageLabel.setImage(null);
            messageLabel.setText(""); //$NON-NLS-1$
        }
		
	}
    
    protected void createButtonsForButtonBar(Composite parent) {
    	/*
	    Button okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
	            true);
	    Button cancelButton = createButton(parent, IDialogConstants.CANCEL_ID,
	            IDialogConstants.CANCEL_LABEL, false);
        setButtonLayoutData(okButton);
        setButtonLayoutData(cancelButton);
*/
    	editorPane.addButtons(parent);
    	super.createButtonsForButtonBar(parent);
    	
    	editorPane.show();
    	
	    validate();
	}

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.Dialog#initializeBounds()
     */
    @Override
    protected void initializeBounds() {
    	super.initializeBounds();
    }
    
	/**
	 * @param b
	 */
	public void setStickyValidationMessage(boolean b) {
		this.stickyValidationMessage = b;
	}

	/**
	 * @return
	 */
	public boolean isStickyValidationMessage() {
		return stickyValidationMessage;
	}

	public IStatus validateImageSize(IImagePropertyRenderingInfo propInfo,
			String propertyPath, ILookAndFeel laf, boolean isSVG, Point imageSize) {
		if (propInfo == null || imageSize == null)
			return null;
		
		Point shownSize = propInfo.getViewableSize(propertyPath, laf);
		if (shownSize == null)
		    return null;
		
		if (propInfo.isScaling(propertyPath, laf)) {
			if (!isSVG) {
				// if the image has aspect preserved, then we only care if both
				// axes have changed size (the rest will be filled in)
				boolean scalingBitmap = false;
				if (propInfo.isPreservingAspectRatio(propertyPath, laf)) {
					shownSize = ImageUtils.scaleSizeToSize(imageSize, shownSize);
					if (imageSize.x != shownSize.x && imageSize.y != shownSize.y) {
						scalingBitmap = true;
					}
				} else {
				    if (imageSize.x != shownSize.x || imageSize.y != shownSize.y) {
				    	scalingBitmap = true;
				    }
				}
				if (scalingBitmap) {
					return createSimpleStatus(IStatus.WARNING, "SymbianImageValidator.ScaledImageSizeMsg", //$NON-NLS-1$
							new Object[] { new Integer(shownSize.x), new Integer(shownSize.y) });
				}
			}
		} else {
			// cropping only
		    if (imageSize.x > shownSize.x || imageSize.y > shownSize.y) {
		        return createSimpleStatus(IStatus.WARNING, "SymbianImageValidator.CroppedImageSizeMsg", //$NON-NLS-1$
		                new Object[] { new Integer(shownSize.x), new Integer(shownSize.y) });
		    }
		}
		return null;
	}

    /**
     * @return
     */
    private IStatus createSimpleStatus(int type, String msgKey, Object[] args) {
        StatusBuilder builder = new StatusBuilder(SymbianPlugin.getDefault());
        builder.add(type, Messages.getString(msgKey), args);
        return builder.createStatus(null, null);
    }

	/**
	 * Create a button that is visible only when the pane is visible.
	 * @param parent TODO
	 * @param pane
	 * @param label
	 * @return
	 */
	public Button createPaneButton(
			Composite parent,
			Composite pane, String label) {
		final Button button = createButton(parent, IDialogConstants.CLIENT_ID, label, false);
		return button;
	}

	@Override
	public Control getDialogArea() {
		return super.getDialogArea();
	}
}
