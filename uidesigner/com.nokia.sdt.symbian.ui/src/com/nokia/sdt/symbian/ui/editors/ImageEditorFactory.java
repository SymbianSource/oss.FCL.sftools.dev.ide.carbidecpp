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

package com.nokia.sdt.symbian.ui.editors;

import com.nokia.sdt.component.property.AbstractPropertyEditorFactory;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.symbian.images.ProjectImageInfo;
import com.nokia.sdt.symbian.ui.UIPlugin;
import com.nokia.sdt.symbian.ui.images.IImageEditorDialog;
import com.nokia.sdt.symbian.ui.images.ImageEditorDialogFactory;
import com.nokia.sdt.ui.IDialogCellEditorActivator;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

/**
 * Creates editor for image properties.  An image consists of
 * a MBMDEF/MIFDEF file, a selected bitmap, and a selected mask (or none);
 * or, a URI for UIQ.
 * 
 *
 */
public class ImageEditorFactory extends AbstractPropertyEditorFactory {

    public ImageEditorFactory() {
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.component.property.IPropertyEditorFactory#createLabelProvider(org.eclipse.emf.ecore.EObject)
     */
    public ILabelProvider createLabelProvider(EObject object, String propertyPath) {
    	return new ImageLabelProvider(object);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.component.property.IPropertyEditorFactory#createCellEditor(org.eclipse.swt.widgets.Composite, org.eclipse.emf.ecore.EObject)
     */
    public CellEditor createCellEditor(Composite parent, EObject object, String propertyPath) {
        ProjectImageInfo info = (ProjectImageInfo) ModelUtils.getProjectImageInfo(object);
        if (info == null)
            return null;
        ImageLabelProvider labelProvider = (ImageLabelProvider) createLabelProvider(object, propertyPath);
        CellEditor result = new ImageCellEditor(object, propertyPath, labelProvider);
        result.setValidator(createCellEditorValidator(object, propertyPath));
        result.create(parent);
        return result;
    }
    
    class ImageCellEditor extends DialogCellEditor implements IDialogCellEditorActivator {

        private ILabelProvider labelProvider;
        private Label label;
        private EObject object;
        private String propertyPath;
        
        public ImageCellEditor(EObject object, String propertyPath, ILabelProvider labelProvider) {
            super();
            this.labelProvider = labelProvider;
            this.object = object;
            this.propertyPath = propertyPath;
            setValueValid(true);
        }

        /* (non-Javadoc)
         * @see org.eclipse.jface.viewers.CellEditor#dispose()
         */
        @Override
        public void dispose() {
            labelProvider.dispose();
            super.dispose();
        }

        /* (non-Javadoc)
         * @see org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(org.eclipse.swt.widgets.Control)
         */
        protected Object openDialogBox(Control cellEditorWindow) {
            Object value = getValue();
            IImageEditorDialog dialog = ImageEditorDialogFactory.createImageEditorDialog(
            		cellEditorWindow.getShell(),
            		object, propertyPath,
            		value,
            		getValidator());
            if (dialog == null) {
            	UIPlugin.log(
            			Logging.newStatus(UIPlugin.getDefault(), 
            					IStatus.ERROR,
            					"Could not instantiate an image editor dialog for object " +
            					object + " propertyPath = " + propertyPath + " value = " +value));
            	return null;
            }
            
            int ret = dialog.open();
            
            if (ret == Dialog.OK) {
            	value = dialog.getDialogData();
            	setValueValid(true);
            	return value;
            } else {
            	return null;
            }
        }
        
        /* (non-Javadoc)
         * @see org.eclipse.jface.viewers.DialogCellEditor#createContents(org.eclipse.swt.widgets.Composite)
         */
        @Override
        protected Control createContents(Composite cell) {
            label = new Label(cell, SWT.NONE);
            return label;
        }
        
        /* (non-Javadoc)
         * @see org.eclipse.jface.viewers.DialogCellEditor#updateContents(java.lang.Object)
         */
        @Override
        protected void updateContents(Object value) {
            String text = labelProvider.getText(value);
            if (!label.isDisposed())
                label.setText(text);
        }

		/* (non-Javadoc)
		 * @see com.nokia.sdt.uidesigner.ui.IDialogCellEditorActivator#invokeEditor()
		 */
		public Object invokeEditor(Composite parent) {
			return openDialogBox(parent);
		}
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.component.property.IPropertyEditorFactory#createCellEditorValidator(org.eclipse.emf.ecore.EObject)
     */
    public ICellEditorValidator createCellEditorValidator(EObject object, String propertyPath) {
        return new ImageCellEditorValidator(object, propertyPath);
    }
}
