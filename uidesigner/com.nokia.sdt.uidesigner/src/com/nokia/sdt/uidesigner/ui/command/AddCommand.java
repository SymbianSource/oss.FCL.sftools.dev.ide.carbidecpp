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

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.StatusBuilder;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.displaymodel.adapter.IContainer;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.utils.*;
import com.nokia.sdt.utils.*;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.swt.graphics.Rectangle;

import java.util.Collection;
import java.util.Collections;

/**
 * 
 * 
 */
public class AddCommand extends DataModelCommandWrapper {

	private final EObject parent;
	private final EObject child;
	/** The status associated with containment query */
	private IStatus status;
	/** The container's EditPart */
	private EditPart editPart;

	public AddCommand(EObject parent, EObject child, int index, IDesignerDataModel dataModel, 
			EditPart editPart, IDesignerEditor editor) {
		Check.checkArg(parent);
		Check.checkArg(child);
		Check.checkArg(dataModel);
		Check.checkArg(editPart);
		this.parent = parent;
		this.child = child;
		this.editPart = editPart;
		
		setDataModelCommand(dataModel.createMoveComponentInstanceCommand(child, parent, index));
		setEditor(editor);
	
		setLabel(Adapters.getComponentInstance(child).getName()
			+ Strings.getString("AddCommand.AddLabel")); //$NON-NLS-1$
	}

	public boolean canExecute() {
		boolean result = false;
		if (super.canExecute()) {
			IComponentInstance childInstance = Adapters.getComponentInstance(child);
			EObject oldParent = childInstance.getParent();
			if (parent.equals(oldParent)) {
				result = EditorUtils.canReorderChildren(parent, Collections.singletonList(child));
			}
			else {
				IContainer container = Adapters.getContainer(parent);
				if (container != null) {
					StatusHolder holder = new StatusHolder();
	                result = container.canContainChild(childInstance, holder);
					status = holder.getStatus();
				}
			}
		}
		
		if (editPart instanceof GraphicalEditPart) {
			IFigure figure = ((GraphicalEditPart) editPart).getContentPane();
			if (!result && (status != null) && 
					(status.getSeverity() >= IStatus.ERROR)) {
				String message = StatusBuilder.getMergedMessage(status, "\n - "); //$NON-NLS-1$
				if (message != null) {
					figure.setToolTip(EditorUtils.createToolTip(message));
					return false;
				}
			}

			figure.setToolTip(null);
		}

		return result;
	}

	@Override
	public void execute() {
		super.execute();
		// if it's a layout object with empty size, size to CreateCommand's min size
		Collection affectedObjects = getAffectedObjects();
		for (Object object : affectedObjects) {
			if (object instanceof EObject) {
				ILayoutObject layoutObject = Adapters.getLayoutObject((EObject) object);
				if (layoutObject != null && layoutObject.getBounds().isEmpty()) {
					Dimension minSize = CreateCommand.MIN_SIZE;
					layoutObject.setBounds(new Rectangle(0, 0, minSize.width, minSize.height));
				}
			}
		}
	}
	
	
}