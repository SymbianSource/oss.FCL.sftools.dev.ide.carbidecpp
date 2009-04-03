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


package com.nokia.sdt.uidesigner.ui.editparts;

import com.nokia.sdt.component.adapter.IDesignerImages;
import com.nokia.sdt.datamodel.adapter.IComponentInstancePropertyListener;
import com.nokia.sdt.displaymodel.adapter.IDisplayObject;
import com.nokia.sdt.uidesigner.ui.editparts.policy.NonLayoutObjectDirectEditPolicy;
import com.nokia.sdt.uidesigner.ui.figure.NonLayoutObjectFigure;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.*;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.DragEditPartsTracker;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Image;

import java.util.*;

/**
 * 
 *
 */
public class NonLayoutObjectEditPart extends ModelObjectEditPart 
											implements IComponentInstancePropertyListener {

	private NonLayoutObjectLabelEditManager manager;

	
	/**
	 * Upon activation, attach to the model element as a property change listener.
	 */
	public void activate() {
		if (!isActive()) {
			super.activate();
			getComponentInstance().addPropertyListener(this);
		}
	}

	/**
	 * Upon deactivation, detach from the model element as a property change listener.
	 */
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getComponentInstance().removePropertyListener(this);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.uidesigner.editparts.ModelObjectEditPart#isApplicableObject(java.lang.Object)
	 */
	protected boolean isApplicableChild(EObject object) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.uidesigner.ui.editparts.ModelObjectEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		NonLayoutObjectFigure figure = new NonLayoutObjectFigure();
		IDesignerImages images = Adapters.getDesignerImages((EObject) getModel());
		Image image = null;
		if (images != null)
			image = images.getLargeIcon();
		if (image == null)
			image = EditorUtils.getUnknownLargeIcon();

        figure.setIcon(image);
		
		return figure;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.uidesigner.ui.editparts.ModelObjectEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new NonLayoutObjectDirectEditPolicy());
	}

	private void performDirectEdit(KeyEvent initial){
		if (manager == null) {
			NonLayoutObjectFigure figure = (NonLayoutObjectFigure) getFigure();
			manager = new NonLayoutObjectLabelEditManager(this, TextCellEditor.class, 
					new NonLayoutObjectCellEditorLocator(figure), getEditor());
		}
		manager.setInitialKeyEvent(initial);
		manager.show();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.EditPart#performRequest(org.eclipse.gef.Request)
	 */
	public void performRequest(Request request){
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
			if (getFigure() instanceof NonLayoutObjectFigure) {
				NonLayoutObjectFigure nonLayoutObjectFigure = (NonLayoutObjectFigure) getFigure();
				if (request instanceof DirectEditRequest) {
					DirectEditRequest editRequest = (DirectEditRequest) request;
					KeyEvent initial= null;
					boolean doDirectEdit = editRequest.getLocation().equals(new Point(-1, -1));
					if (doDirectEdit) {
						initial = (KeyEvent) editRequest.getExtendedData().get(INITIAL_KEY_EVENT);
						editRequest.getExtendedData().remove(INITIAL_KEY_EVENT);
					}
					else {
						Point localPoint = new Point(editRequest.getLocation());
						nonLayoutObjectFigure.translateToRelative(localPoint);
						doDirectEdit = nonLayoutObjectFigure.pointInText(localPoint);
					}
					
					if (doDirectEdit)
						performDirectEdit(initial);
				}
			}
		}
	}
	
	private void updateLabel() {
		IFigure figure = getFigure();
		if (figure instanceof NonLayoutObjectFigure) {
			NonLayoutObjectFigure nonLayoutFigure = (NonLayoutObjectFigure) figure;
			String labelText = getComponentInstance().getName();
			if (labelText == null)
				labelText = ""; //$NON-NLS-1$
			nonLayoutFigure.setText(labelText);
			nonLayoutFigure.repaint();
		}
	}

	public void propertyChanged(EObject componentInstance, Object propertyId) {
		if (propertyId.equals(getEditor().getDataModel().getNamePropertyId()))
			refreshVisuals();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	protected void refreshVisuals() {
		updateLabel();
	}
	
	@Override
	public DragTracker getDragTracker(Request request) {
		return new DragEditPartsTracker(this) {
				// Override to perform range selection
			protected void performSelection() {
				if (hasSelectionOccurred())
					return;
				setFlag(FLAG_SELECTION_PERFORMED, true);
				boolean didSelectRange = false;
				Input currentInput = getCurrentInput();
				if (currentInput.isShiftKeyDown())
					didSelectRange = selectRange(currentInput);
				if (!didSelectRange) {
					setFlag(FLAG_SELECTION_PERFORMED, false);
					super.performSelection();
				}
			}

			private boolean selectRange(Input currentInput) {
				boolean selected = false;
				EditPartViewer viewer = getCurrentViewer();
				List selectedParts = viewer.getSelectedEditParts();
				EditPart clickedPart = getSourceEditPart();
				List allParts = clickedPart.getParent().getChildren();
				EditPart primarySelectedPart = getPrimarySelectedPart(selectedParts);
				if (primarySelectedPart != null) {
					List newSelectedParts = new ArrayList();
					if (currentInput.isModKeyDown(SWT.MOD1))
						newSelectedParts.addAll(selectedParts);

					collectRangeSelection(clickedPart, allParts, primarySelectedPart, newSelectedParts);
					viewer.deselectAll();
					StructuredSelection newSelection = new StructuredSelection(newSelectedParts);
					getEditor().getSelectionManager().setSelection(newSelection);
					selected = true;
				}
				return selected;
			}

			private void collectRangeSelection(EditPart clickedPart, List allParts, 
												EditPart primarySelectedPart, Collection newSelection) {
				// now start adding parts to the new selection
				boolean startAdding = false;
				boolean adding = false;
				for (Iterator<EditPart> iter = allParts.iterator(); iter.hasNext();) {
					startAdding = false;
					EditPart curPart = iter.next();
					if (!adding && (curPart.equals(primarySelectedPart) || curPart.equals(clickedPart))) {
						startAdding = true;
						adding = true;
					}
					if (adding) {
						if (!newSelection.contains(curPart))
							newSelection.add(curPart);
						if (!startAdding && (curPart.equals(primarySelectedPart) || curPart.equals(clickedPart)))
							break;
					}
				}
			}

			private EditPart getPrimarySelectedPart(List selectedParts) {
				EditPart primarySelectedPart = null;
				for (Iterator<EditPart> iter = selectedParts.iterator(); iter.hasNext();) {
					EditPart curPart = iter.next();
					if (curPart.getSelected() == EditPart.SELECTED_PRIMARY) {
						primarySelectedPart = curPart;
						break;
					}
				}
				return primarySelectedPart;
			}
		};
	}
}
