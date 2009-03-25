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
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IVisualAppearance;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.adapter.*;
import com.nokia.sdt.editor.ICreationTool;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.UIDesignerPlugin;
import com.nokia.sdt.uidesigner.ui.editparts.IEditPartEditorProvider;
import com.nokia.sdt.uidesigner.ui.editparts.NonLayoutObjectEditPart;
import com.nokia.sdt.uidesigner.ui.utils.*;
import com.nokia.sdt.utils.*;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.swt.graphics.Point;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;

/**
 * 
 * 
 */
public class CreateCommand extends DataModelCommandWrapper {
	/** The new model object. */
	private EObject newObject;
	/** The tool of the new object */
	private ICreationTool tool;
	/** Cache whether newComponent is non-layout */
	private boolean isNonLayoutComponent;
	/** A request to create a new object. */
	private final CreateRequest request;
	/** The container's EditPart */
	private EditPart editPart;
	/** The status associated with containment query */
	private IStatus status;
	
	private final EObject parent;
	private final EObject originalParent;
	
	private IDesignerEditor editor;
	
	private EObject objectToSelect;

	/** The default size that the policy will use if none set */
	private static final Dimension POLICY_DEFAULT_SIZE = new Dimension(-1, -1);
	/** Our default size, if all else fails */
	public static final Dimension MIN_SIZE = new Dimension(32, 24);
	
	public CreateCommand(EObject parent, CreateRequest request, int index,
			IDesignerDataModel dataModel, EditPart editPart, IDesignerEditor editor) {
		Check.checkArg(parent);
		Check.checkArg(request);
		Check.checkArg(dataModel);
		Check.checkArg(editPart);
		Check.checkArg(editor);
		this.editor = editor;
		
		Object object = request.getNewObjectType();
		Check.checkContract(object instanceof ICreationTool);
		this.tool = (ICreationTool) object;
		
		isNonLayoutComponent = EditorUtils.isNonLayoutComponent(tool);
		this.request = request;
		this.originalParent = parent;
		
		// Don't allow parenting of INonLayoutObject to ILayoutObject (container)
		// if parent ILayoutObject, change parent to the root container
		EObject nonLayoutParent = getNonLayoutParent();
		if (!nonLayoutParent.equals(parent) && isNonLayoutComponent) {
			this.parent = getNonLayoutParent();
			index = IDesignerDataModel.AT_END;
		}
		else
			this.parent = parent;
			
		this.editPart = editPart;
		
		newObject = tool.createNewObject(dataModel);
		setDataModelCommand(dataModel.createAddNewComponentInstanceCommand(this.parent, newObject, index));
		setEditor(editor);
		
		setLabel(tool.getLabel()
				+ Strings.getString("CreateCommand.CreationLabel")); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		objectToSelect = null;
		super.execute();
		Collection result = getCommand().getResult();
		Check.checkContract((result != null) && !result.isEmpty());
		tool.addNotify(parent);
		setRequestLayoutLocation(result);
	}
	
	private void setRequestLayoutLocation(Collection objects) {
		for (Object object : objects) {
			if (object instanceof EObject)
				setRequestLayoutLocation((EObject) object);
		}
	}
	
	private boolean shouldSetRequestedBounds(EObject object) {
		ILayoutObject layoutObject = Adapters.getLayoutObject(object);
		EObject parent = Adapters.getComponentInstance(object).getParent();
		ILayoutContainer parentContainer = Adapters.getLayoutContainer(parent);
		if (layoutObject == null || parentContainer == null ||
				!parentContainer.canMoveChild(layoutObject) ||
				!parentContainer.canResizeChild(layoutObject))
			return false;
		
		return true;
	}
	
	private void setRequestLayoutLocation(EObject object) {
		// Get desired location and size from the request
		if (shouldSetRequestedBounds(object)) {
			IDisplayModel displayModel = 
				(IDisplayModel) editor.getAdapter(IDisplayModel.class);
			ILayoutObject layoutObject = Adapters.getLayoutObject(object);
			Rectangle constraint = (Rectangle) request.getExtendedData().get(EditorUtils.CONSTRAINT);
			Dimension requestedSize = null;
			if (request.getSize() != null)
				requestedSize = request.getSize();
			else {
				IVisualAppearance appearance = Adapters.getVisualAppearance(newObject);
				if ((appearance != null) && (editPart instanceof IEditPartEditorProvider)) {
					Point preferredSize = appearance.getPreferredSize(-1, -1, displayModel.getLookAndFeel());
					if (preferredSize != null)
						requestedSize = new Dimension(preferredSize);
				}
			}
			if (requestedSize == null)
				requestedSize = MIN_SIZE;
			if (constraint == null) {
				constraint = new Rectangle(0, 0, requestedSize.width, requestedSize.height);
			}
			else {
				if (EditorUtils.isTransient(object)) {
					EObject rootContainer = displayModel.getRootContainer();
					if (!Adapters.isTransientObject(object))
						rootContainer = EditorUtils.findTransientObject(object);
					EObject container = originalParent;
					while (!container.equals(rootContainer)) {
						ILayoutObject lo = Adapters.getLayoutObject(container);
						org.eclipse.swt.graphics.Rectangle bounds = lo.getBounds();
						constraint.translate(bounds.x, bounds.y);
						IComponentInstance ci = Adapters.getComponentInstance(container);
						container = ci.getParent();
					}
				}
			}
			if (constraint.getSize().equals(POLICY_DEFAULT_SIZE))
				constraint.setSize(requestedSize);
			else
				constraint.setSize(constraint.getSize());
			org.eclipse.swt.graphics.Rectangle r = new org.eclipse.swt.graphics.Rectangle(
					constraint.x, constraint.y, constraint.width, constraint.height);
			layoutObject.setBounds(r);
		}
	}
	
	private EObject getNonLayoutParent() {
		return (EObject) editor.getNonLayoutRoot();
	}
	
	public boolean canExecute() {
		boolean result = false;
		if (super.canExecute()) {
			if (isNonLayoutComponent) {
				result = true; // let the non-layout objects in
			}
			else if ((editPart instanceof NonLayoutObjectEditPart)) { // no layout objects in non-layout area
				String fmt = Strings.getString("CreateCommand.LayoutObjectInNonLayoutContainerError"); //$NON-NLS-1$
				String message = MessageFormat.format(fmt, new Object[] { tool.getLabel() });
				status = new Status(IStatus.ERROR, UIDesignerPlugin.PLUGIN_ID, 1, message, null);
				result = false;
			}
			else {
				IContainer container = Adapters.getContainer(parent);
				if (container != null) {
					StatusHolder holder = new StatusHolder();
					IComponent component = (IComponent) tool.getAdapter(IComponent.class);
					if (component != null) {
						result = container.canContainComponent(component, holder);
						status = holder.getStatus();
					}
					else {
						result = true;
						status = Status.OK_STATUS;
					}
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

	public void undo() {
		objectToSelect = parent;
		super.undo();
	}
	
	public void redo() {
		objectToSelect = null;
		super.redo();
	}

	protected Collection getAffectedObjects() {
		if (objectToSelect != null)
			return Collections.singletonList(objectToSelect);
		
		return super.getAffectedObjects();
	}
}
