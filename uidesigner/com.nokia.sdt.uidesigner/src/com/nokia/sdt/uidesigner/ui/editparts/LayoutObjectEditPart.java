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

import com.nokia.sdt.datamodel.adapter.IDirectEdit;
import com.nokia.sdt.datamodel.adapter.IDirectLabelEdit;
import com.nokia.sdt.displaymodel.LayoutAreaConfigurationListener;
import com.nokia.sdt.displaymodel.IDisplayModel.LayoutAreaConfiguration;
import com.nokia.sdt.displaymodel.adapter.*;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer.ELayoutCategory;
import com.nokia.sdt.editor.IDesignerEditor.TransientListener;
import com.nokia.sdt.uidesigner.ui.editparts.policy.*;
import com.nokia.sdt.uidesigner.ui.figure.LayoutObjectFigure;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.*;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Image;

import java.util.Collection;

/**
 * 
 *
 */
public class LayoutObjectEditPart extends ModelObjectEditPart 
					implements LayoutObjectListener, IImageProvider, LayoutAreaConfigurationListener {

	private TransientListener transientListener;
	private LayoutObjectLabelEditManager lastLabelEditManager;

	/**
	 * Upon activation, attach to the display object as a listener.
	 */
	public void activate() {
		if (!isActive()) {
			super.activate();
			getLayoutObject().addListener(this);
			getFigure().repaint();
			getEditor().getDisplayModel().addLayoutAreaListener(this);
			getEditor().addTransientListener(transientListener = new TransientListener() {
				public void enteredTransientMode() {}
				public void prepareToExitTransientMode() {
					if ((lastLabelEditManager != null) && (lastLabelEditManager.isDirty())) {
						lastLabelEditManager.commit();
					}
				}
				public void exitingTransientMode() {}
			});
		}
	}
	
	public Image getImage() {
		return getLayoutObject().getImage();
	}

	/**
	 * Upon deactivation, detach from the display object as a listener.
	 */
	public void deactivate() {
		if (isActive()) {
			if (getEditor().getDisplayModel() != null)
				getEditor().getDisplayModel().removeLayoutAreaListener(this);
			super.deactivate();
			getLayoutObject().removeListener(this);
			getEditor().removeTransientListener(transientListener);
			transientListener = null;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		return new LayoutObjectFigure(this);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		super.createEditPolicies();
		ILayoutContainer container = Adapters.getLayoutContainer((EObject) getParent().getModel());
		ILayoutObject layoutObject = getLayoutObject();
		if (container.canResizeChild(layoutObject)) {
			installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new ResizableEditPolicy(getEditor()));
		}
		else {
			NonResizableEditPolicy policy = new NonResizableEditPolicy(getEditor());
			// NOTE: this implementation implies non-movable only follows non-resizable
			policy.setDragAllowed(container.canMoveChild(layoutObject) ||
					container.getLayoutCategory().equals(ELayoutCategory.ORDERED));
			installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, policy);
		}

		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new LayoutObjectDirectEditPolicy());
}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	protected void refreshVisuals() {
		ILayoutObject layoutObject = getLayoutObject();
		if (layoutObject == null)
			return;
		
		imageChanged(layoutObject);
		boundsChanged(layoutObject);
	}

	/* (non-Javadoc)
	 * @see com.nokia.uidesigner.editparts.ModelObjectEditPart#isApplicableObject(java.lang.Object)
	 */
	protected boolean isApplicableChild(EObject object) {
		return !Adapters.isTransientObject(object) &&
					(Adapters.getLayoutObject(object) != null);
	}

	public void imageChanged(ILayoutObject object) {
		if (!object.equals(getLayoutObject()))
			return;
		getContentPane().repaint();
		// potentially, some transient children may not be inside their parent's bounds
		// the DeferredUpdateManager in draw2d will ignore calls to repaint these
		// so we need to force them to draw by redrawing the whole lightweight system
		if (isIgnorableFigure(getContentPane())) {
			RootEditPart root = getRoot();
			EditPartViewer viewer = root.getViewer();
			viewer.getControl().redraw();
		}
	}
	
	private boolean isIgnorableFigure(IFigure figure) {
		if (figure == null)
			return true;
		IFigure parent = figure.getParent();
		Rectangle r = figure.getBounds().getCopy();
		r.intersect(figure.getBounds());
		while (parent != null && !r.isEmpty()) {
			parent.translateToParent(r);
			r.intersect(parent.getBounds());
			parent = parent.getParent();
		}
		
		return r.isEmpty();
	}

	public void boundsChanged(ILayoutObject object) {
		if (!object.equals(getLayoutObject()))
			return;
		Rectangle newBounds = new Rectangle(object.getBounds());
		IFigure figure = getFigure();
		IFigure contentPane = getContentPane();
		if (figure != contentPane)
			figure.setBounds(newBounds);
		contentPane.setBounds(newBounds);
	}

	public Border getFigureBorder() {
		return null;
	}

	public void registerVisuals() {
		super.registerVisuals();
	}

	public void unregisterVisuals() {
		super.unregisterVisuals();
	}
	
	private void performDirectLabelEdit(IDirectEdit directEdit, KeyEvent initial, String propertyPath) {
		Figure figure = (Figure) getFigure();
        org.eclipse.swt.graphics.Rectangle bounds = directEdit.getVisualBounds(propertyPath, getEditor().getDisplayModel().getLookAndFeel());
		LayoutObjectCellEditorLocator layoutObjectCellEditorLocator = 
					new LayoutObjectCellEditorLocator(figure, bounds);
		lastLabelEditManager = new LayoutObjectLabelEditManager(this, TextCellEditor.class, 
				layoutObjectCellEditorLocator, propertyPath, getEditor());
		lastLabelEditManager.setInitialKeyEvent(initial);
		lastLabelEditManager.show();
	}

	/*private void performDirectImageEdit(IDirectEdit directEdit, Point location, String propertyPath) {
        final LayoutObjectFigure figure = (LayoutObjectFigure) getFigure();
        final org.eclipse.swt.graphics.Rectangle bounds = directEdit.getVisualBounds(propertyPath, getEditor().getDisplayModel().getLookAndFeel());
        CellEditorLocator cellEditorLocator = new DirectImageEditCellEditorLocator(figure, bounds, location);
        DirectImageEditManager manager = new DirectImageEditManager(this, cellEditorLocator, propertyPath, (EObject) getModel());
        manager.show();
    }*/

	private String getDirectEditPropertyFromPoint(IDirectEdit directEdit, Point clickPoint) {
		String[] propertyPaths = directEdit.getPropertyPaths();
        if (propertyPaths == null)
            return null;
		for (int i = 0; i < propertyPaths.length; i++) {
			org.eclipse.swt.graphics.Rectangle bounds = 
				directEdit.getVisualBounds(propertyPaths[i], getEditor().getDisplayModel().getLookAndFeel());
			if ((bounds != null) && bounds.contains(clickPoint.x, clickPoint.y))
				return propertyPaths[i];
		}
		return null;
	}

	private String getFirstDirectEditProperty(IDirectEdit directEdit) {
	    String[] propertyPaths = directEdit.getPropertyPaths();
	    if (propertyPaths != null && propertyPaths.length > 0)
	    	return propertyPaths[0];
	    else
	    	return null;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.EditPart#performRequest(org.eclipse.gef.Request)
	 */
	public void performRequest(Request request){
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
			Figure figure = (Figure) getFigure();
			if (request instanceof DirectEditRequest) {
				DirectEditRequest editRequest = (DirectEditRequest) request;
				KeyEvent initial = null;
				boolean fromKey = editRequest.getLocation().equals(new Point(-1, -1));
				String propertyPath = null;
				Point localPoint = null;
                
                // Try to match a direct label edit, then a direct image edit,
                // on the component.  Labels win if a property claims to support both.
                
				IDirectLabelEdit directLabelEdit = Adapters.getDirectLabelEdit((EObject) getModel());
				
				// Turn off direct image editing; its UI is weird.
				// Instead, use the context menu.
				//IDirectImageEdit directImageEdit = Adapters.getDirectImageEdit((EObject) getModel());

                if (directLabelEdit != null && fromKey) {
                    // a keypress activates the direct label edit on the first available property
					initial = (KeyEvent) editRequest.getExtendedData().get(INITIAL_KEY_EVENT);
					editRequest.getExtendedData().remove(INITIAL_KEY_EVENT);
					propertyPath = getFirstDirectEditProperty(directLabelEdit);
					if (propertyPath != null)
						performDirectLabelEdit(directLabelEdit, initial, propertyPath);
				}
				else {
                    // a mouse click activates whatever is under the cursor that matches
				    localPoint = new Point(editRequest.getLocation());
					figure.translateToRelative(localPoint);
					Point location = figure.getLocation();
					localPoint.translate(-location.x, -location.y);
                    
                    if (directLabelEdit != null) {
                        propertyPath = getDirectEditPropertyFromPoint(directLabelEdit, localPoint);
                        if (propertyPath == null)
                            directLabelEdit = null;
                    }
                    /*
                    if (propertyPath == null && directImageEdit != null) { 
                        propertyPath = getDirectEditPropertyFromPoint(directImageEdit, localPoint);
                        if (propertyPath == null)
                            directImageEdit = null;
                    }*/
                    if (propertyPath != null) {
                        if (directLabelEdit != null)
                            performDirectLabelEdit(directLabelEdit, initial, propertyPath);
                        /*else if (directImageEdit != null) {
                            if (localPoint == null)
                                localPoint = new Point(0, 0);
                            performDirectImageEdit(directImageEdit, localPoint, propertyPath);
                        }*/
                    }
				}
			}
		}
	}

    public void configurationChanging(LayoutAreaConfiguration configuration) {
    }

    public void configurationChanged(LayoutAreaConfiguration configuration) {
        getLayoutObject().forceRedraw();
    }

    public void configurationSetChanged(Collection newConfigurations) {
    }
}
