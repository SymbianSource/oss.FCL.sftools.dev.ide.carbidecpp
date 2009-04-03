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

import com.nokia.sdt.datamodel.adapter.IScrollBoundsProvider;
import com.nokia.sdt.displaymodel.IDisplayModel.LayoutAreaConfiguration;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.uidesigner.ui.editparts.policy.ContainerOrderedLayoutEditPolicy;
import com.nokia.sdt.uidesigner.ui.editparts.policy.ContainerXYLayoutEditPolicy;
import com.nokia.sdt.uidesigner.ui.figure.*;
import com.nokia.sdt.uidesigner.ui.figure.ScrollingContainerFigure.ScrollPane;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.*;

import java.util.*;

/**
 * 
 *
 */
public class LayoutContainerEditPart extends LayoutObjectEditPart implements ExposeHelper {

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		boolean vScroll = getLayoutContainer().scrollsVertically();
		boolean hScroll = getLayoutContainer().scrollsHorizontally();
		if (vScroll || hScroll) {
			EObject model = getLayoutContainer().getEObject();
			IScrollBoundsProvider scrollBoundsProvider = Adapters.getScrollBoundsProvider(model);
			return new ScrollingContainerFigure(this, scrollBoundsProvider, getEditor(), vScroll, hScroll);
		}
		else if (EditorUtils.isTransient(getModel()))
			return new TransientObjectFigure(this, 
					EditorUtils.getNoTransientChildClipping((EObject) getModel()));
		
		return new LayoutObjectFigure(this);
	}
	
	public boolean isScrollable() {
		IFigure figure = getFigure();
		if (figure instanceof ScrollingContainerFigure) {
			return ((ScrollingContainerFigure) figure).isScrollable();
		}
		
		return false;
	}
	
	public void setScrollbarsVisible(boolean visible) {
		IFigure figure = getFigure();
		if (figure instanceof ScrollingContainerFigure) {
			((ScrollingContainerFigure) figure).setScrollbarsVisible(visible);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		super.createEditPolicies();
		LayoutManager layout = getContentPane().getLayoutManager();

		ILayoutContainer layoutContainer = getLayoutContainer();
		if (layoutContainer != null) {
			switch (layoutContainer.getLayoutCategory()) {
				case ORDERED:
					installEditPolicy(EditPolicy.CONTAINER_ROLE, 
							new ContainerOrderedLayoutEditPolicy());
					break;
				case ABSOLUTE:
					installEditPolicy(EditPolicy.CONTAINER_ROLE, 
							new ContainerXYLayoutEditPolicy((XYLayout) layout));
					break;
				default:
					break;
			}
		}
	}

	public void setLayoutConstraint(EditPart child, IFigure childFigure, Object constraint) {
		IFigure parent = childFigure.getParent();
		if (parent != null)
			parent.setConstraint(childFigure, constraint);
	}

	protected void addChildVisual(EditPart childEditPart, int index) {
		if (childEditPart instanceof TransientObjectEditPart) {
			if (getEditor().isTransientMode()) {
				IFigure child = ((GraphicalEditPart)childEditPart).getFigure();
				getEditor().getTransientLayerRootFigure().add(child);
			}
		}
		else if (childEditPart instanceof LayoutObjectEditPart) {
			super.addChildVisual(childEditPart, index);
		}
	}
	
	protected void removeChildVisual(EditPart childEditPart) {
		if (childEditPart instanceof TransientObjectEditPart) {
			IFigure child = ((GraphicalEditPart)childEditPart).getFigure();
			if (getEditor().isTransientMode())
				getEditor().getTransientLayerRootFigure().remove(child);
		}
		else if (childEditPart instanceof LayoutObjectEditPart) {
			super.removeChildVisual(childEditPart);
		}
	}

	public void addChild(EditPart child, int index) {
		super.addChild(child, index);
	}
	
	public void removeChild(EditPart child) {
		super.removeChild(child);
	}

	public void childRemoved(EObject parent, EObject child) {
		super.childRemoved(parent, child);
		if (!getEditor().isComponentEditing()) {
			EditPartViewer viewer = getViewer();
			if (viewer.getControl().isVisible()) {
				List children = getChildren();
				if (children.isEmpty())
					viewer.select(this);
				else
					viewer.select((EditPart) children.get(0));
			}
		}
	}

	@Override
	public IFigure getContentPane() {
		IFigure figure = getFigure();
		if (figure instanceof ScrollingContainerFigure)
			return ((ScrollingContainerFigure) figure).getContentPane();
		
		return figure;
	}

	@Override
	public Object getAdapter(Class key) {
		if (isScrollable() && key.equals(ExposeHelper.class))
			return this;
		return super.getAdapter(key);
	}

	public void exposeDescendant(EditPart editpart) {
		if (isScrollable()) {
			IFigure figure = getFigure();
			if (figure instanceof ScrollingContainerFigure) {
				ScrollPane scrollPane = ((ScrollingContainerFigure) figure).getScrollPane();
				IFigure childFigure = ((GraphicalEditPart) editpart).getFigure();
				Rectangle childBounds = new Rectangle(childFigure.getBounds());
				Viewport viewport = scrollPane.getViewport();
				Point viewLocation = viewport.getViewLocation();
				Rectangle bounds = new Rectangle(viewport.getBounds());
				bounds.setLocation(viewLocation);
				Point topLeft = childBounds.getTopLeft();
				Point bottomLeft = childBounds.getBottomLeft().translate(0, -2);
				if (!bounds.contains(topLeft) || !bounds.contains(bottomLeft)) {
					scrollPane.scrollTo(childBounds.getLocation());
				}
			}
		}
	}
	
	@Override
	protected List getModelChildren() {
		ILayoutContainer layoutContainer = getLayoutContainer();
		if (layoutContainer == null)
			return Collections.EMPTY_LIST;
		
		EObject[] objects = layoutContainer.getVisibleChildren();
		List visibleChildren = objects == null ? Collections.EMPTY_LIST : Arrays.asList(objects);
		List modelChildren = new ArrayList();
		for (Object object : visibleChildren) {
			if (isInactiveTransientObject(object))
				continue;
			modelChildren.add(object);
		}

		return modelChildren;
	}

    private boolean isInactiveTransientObject(Object object) {
    	EObject transientObject = EditorUtils.findTransientObject(object);
    	return transientObject != null && 
    		!transientObject.equals(getEditor().getTransientRootObject());
	}

	public void configurationChanged(LayoutAreaConfiguration configuration) {
    	super.configurationChanged(configuration);
        ILayoutContainer layoutContainer = getLayoutContainer();
        if (layoutContainer != null)
        	layoutContainer.layoutChildren();
    }

}
