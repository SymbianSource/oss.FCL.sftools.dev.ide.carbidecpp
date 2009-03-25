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

import com.nokia.sdt.datamodel.adapter.ILayout;
import com.nokia.sdt.displaymodel.LayoutAreaConfigurationAdapter;
import com.nokia.sdt.displaymodel.LayoutAreaConfigurationListener;
import com.nokia.sdt.displaymodel.IDisplayModel.LayoutAreaConfiguration;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.uidesigner.ui.figure.LayoutContentsFigure;
import com.nokia.sdt.uidesigner.ui.figure.TransientObjectFigure;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;

import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.*;
import org.eclipse.gef.tools.DragEditPartsTracker;
import org.eclipse.jface.viewers.ISelection;

import java.util.List;

public class TransientObjectEditPart extends LayoutContainerEditPart {

	private LayoutAreaConfigurationListener layoutAreaConfigurationListener;
	private FigureListener figureListener;
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		ILayoutContainer layoutContainer = getLayoutContainer();
		if (layoutContainer != null) {
			boolean vScroll = layoutContainer.scrollsVertically();
			boolean hScroll = layoutContainer.scrollsHorizontally();
			if (vScroll || hScroll)
				return super.createFigure();
		}

		IFigure transientObjectFigure = 
			new TransientObjectFigure(this, 
					EditorUtils.getNoTransientChildClipping((EObject) getModel()));
		transientObjectFigure.addFigureListener(figureListener = new FigureListener() {
			private int lastMoveValue = 0;
			private boolean inited = false;
			public void figureMoved(IFigure source) {
				Rectangle r = new Rectangle(source.getBounds());
				source.translateToAbsolute(r);
				if (inited && (r.y < 0) && (r.y < lastMoveValue)) {
					lastMoveValue = r.y;
					// this code attempts to grow the transient area 
					// for containers that grow vertically.
					LayoutContentsFigure layoutContentsFigure = 
						(LayoutContentsFigure) getEditor().getLayoutContentsFigure();
					layoutContentsFigure.moveVertically(-r.y);
					ISelection selection = getEditor().getSelectionManager().getSelection();
					getEditor().getSelectionManager().revealSelectionInViewer(getViewer(), selection);
				}
				inited = true;
			}
		});
		return transientObjectFigure;
	}

	public void activate() {
		super.activate();
		getEditor().getDisplayModel().addLayoutAreaListener(
				layoutAreaConfigurationListener = new LayoutAreaConfigurationAdapter() {
			public void configurationChanged(LayoutAreaConfiguration configuration) {
				refreshVisuals();
			}
		});
	}

	public void refreshVisuals() {
		if (getEditor().isTransientMode()) {
			EditPart parentPart = getParent();
			if (parentPart.getModel().equals(getEditor().getNonLayoutRoot())) {
				getEditor().getTransientLayerRootFigure().add(getFigure());
				ILayout layout = 
					(ILayout) EcoreUtil.getRegisteredAdapter((EObject) getModel(), ILayout.class); 
				if (layout != null)
					layout.layout(getEditor().getDisplayModel().getLookAndFeel());
			}
			super.refreshVisuals();
		}
	}

	protected boolean isApplicableChild(EObject object) {
		return EditorUtils.isTransient(object);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getDragTracker(org.eclipse.gef.Request)
	 */
	public DragTracker getDragTracker(Request request) {
		return new DragEditPartsTracker(this) {
			protected boolean isMove() {
				return true;
			}
		};
	}
	
	public void setSelected(int value) {
		int previousState = getSelected();
		super.setSelected(value);
		if ((value == SELECTED_PRIMARY) && (previousState == value))
			fireSelectionChanged();
	}

	public void childRemoved(EObject parent, EObject child) {
		if (!getEditor().isTransientMode()) // we don't want to refresh visuals if we're not in transient mode
			return;
		
		super.childRemoved(parent, child);
	}
	
	public void removeNotify() {
		super.removeNotify();
		getEditor().getDisplayModel().removeLayoutAreaListener(layoutAreaConfigurationListener);
		layoutAreaConfigurationListener = null;
		if (figureListener != null)
			getFigure().removeFigureListener(figureListener);
		figureListener = null;
		LayoutContentsFigure layoutContentsFigure = 
			(LayoutContentsFigure) getEditor().getLayoutContentsFigure();
		layoutContentsFigure.repositionToNormal();
	}

	@Override
	protected List getModelChildren() {
		if (getLayoutContainer() != null)
			return super.getModelChildren();
		
		return getAllModelChildren();
	}

}
