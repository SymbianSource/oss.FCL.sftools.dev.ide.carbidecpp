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


package com.nokia.sdt.uidesigner.ui.command;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ICellEditorValidator;

import java.text.MessageFormat;

import com.nokia.sdt.component.property.IUndoablePropertySource;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.uidesigner.ui.UIDesignerPlugin;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;
import com.nokia.sdt.uidesigner.ui.utils.Strings;
import com.nokia.cpp.internal.api.utils.core.*;

public class ChangePropertyCommand extends Command {

	public static final String NAME_PROPERTY = "name"; //$NON-NLS-1$
	private Object newValue;
	private Object oldValue;
	private EObject object;
	private String objectName;
	private IDesignerDataModel dataModel;
	private String propertyPath;
	private ICellEditorValidator validator;
    private IUndoablePropertySource undoablePropertySource;
	
	public ChangePropertyCommand(EObject object, String propertyPath, Object newValue, ICellEditorValidator validator) {
		Check.checkArg(object);
		Check.checkArg(propertyPath);
		
		this.object = object;
		IComponentInstance componentInstance = Adapters.getComponentInstance(object);
		this.objectName = componentInstance.getName();
		this.dataModel = componentInstance.getDesignerDataModel();
		this.propertyPath = propertyPath;

        this.oldValue = ModelUtils.readProperty(object, propertyPath, true).result;
        if ((oldValue instanceof IUndoablePropertySource)) {
            this.undoablePropertySource = (IUndoablePropertySource) oldValue;
            this.oldValue = ((IUndoablePropertySource) oldValue).getUndoValue();
        }
        
		this.newValue = newValue;
		this.validator = validator;
		
		setLabel(Strings.getString("ChangePropertyCommand.ChangeLabel")); //$NON-NLS-1$
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		if (isValid(newValue))
			redo();
	}

    private boolean isValid(Object value) {
        String errorMessage = null;
        if (validator == null)
            return true;

        errorMessage = validator.isValid(value);
        return (errorMessage == null || errorMessage.equals("")); //$NON-NLS-1$
    }
    
    private void setProperty(Object value) {
		EObject target = getObject();
		if (target == null) {
			String fmt = Strings.getString("ChangePropertyCommand.NoTargetError");
			String string = MessageFormat.format(fmt, new Object[] { objectName } );
			IStatus status = Logging.newStatus(UIDesignerPlugin.getDefault(), IStatus.ERROR, 
					string);
			Logging.log(UIDesignerPlugin.getDefault(), status); //$NON-NLS-1$
			return;
		}
		IStatus status = ModelUtils.writeProperty(target, propertyPath, value);
		if (propertyPath.equals(NAME_PROPERTY))
			objectName = (String) value;
		if (status != null) {
			Logging.showErrorDialog(Strings.getString("ChangePropertyCommand.ChangeLabel"),  //$NON-NLS-1$
					Strings.getString("ChangePropertyCommand.Error"), status); //$NON-NLS-1$
		}
    }
    
	public Object getPropertyValue() {
		return newValue;
	}

	private EObject getObject() {
		Check.checkState(dataModel != null);
		EObject obj = dataModel.findByNameProperty(objectName);
		// if the property that was changed was the name property, we won't find it by name!
		if (obj == null) {
			IComponentInstance objectInstance = Adapters.getComponentInstance(object);
			if (objectInstance.getName().equals(objectName))
				return null; // something is wrong!
			
			return object;
		}
		
		return obj;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {
		setProperty(newValue);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
        if (undoablePropertySource != null) {
            undoablePropertySource.setFromUndoValue(oldValue, true);
        } else
            setProperty(oldValue);
	}
	
}
