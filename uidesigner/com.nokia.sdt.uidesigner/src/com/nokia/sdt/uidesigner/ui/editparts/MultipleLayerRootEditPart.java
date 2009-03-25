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

import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.editor.ISelectionManager;
import com.nokia.sdt.uidesigner.ui.figure.LayoutContentsFigure;
import com.nokia.sdt.uidesigner.ui.figure.TransientLayer;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.draw2d.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.*;
import org.eclipse.gef.editparts.ScalableRootEditPart;

import java.util.Iterator;
import java.util.List;

public class MultipleLayerRootEditPart extends ScalableRootEditPart {

	private static final String TRANSIENT_LAYER = "Transient Layer"; //$NON-NLS-1$
	private LayeredPane layeredPane;
	private TransientLayer transientLayer;
	private LayoutContentsFigure layoutContentsFigure;
	private EditPartHelper helper;
	private boolean isTransientMode;
	private TransientObjectEditPart transientObjectEditPart;
	
	public MultipleLayerRootEditPart() {
		helper = new EditPartHelper(this);
	}

	public IDesignerEditor getEditor() {
		return helper.getEditor();
	}

	protected void createLayers(LayeredPane layeredPane) {
		super.createLayers(layeredPane);
		this.layeredPane = layeredPane;
		ScalableLayeredPane scalableLayers = (ScalableLayeredPane) layeredPane.getLayer(SCALABLE_LAYERS);
		Layer layer = scalableLayers.getLayer(PRINTABLE_LAYERS);
		transientLayer = new TransientLayer();
		layer.add(transientLayer, TRANSIENT_LAYER);
	}

	public void setTransientMode(EObject selectedObject) {
		if (isTransientMode()) {
			EObject transientObject = (EObject) transientObjectEditPart.getModel();
			if (EditorUtils.isSameObjectOrChild(transientObject, selectedObject))
				return;
			else
				setLayoutMode();
		}
		
		isTransientMode = true;
		transientLayer.installRootFigure(getEditor(), layoutContentsFigure, 
									EditorUtils.getNoTransientChildClipping(selectedObject));
		unregisterLayoutVisuals(this);
		refreshTransientVisuals(createTransientPart(selectedObject));
	}
	
	private TransientObjectEditPart createTransientPart(EObject object) {
		transientObjectEditPart = new TransientObjectEditPart();
		EObject transientObject = EditorUtils.findTransientObject(object);
		Check.checkContract(transientObject != null);
		transientObjectEditPart.setModel(transientObject);
		getTransientPartParent().addChild(transientObjectEditPart, -1);
		return transientObjectEditPart;
	}
	
	private void removeTransientPart() {
		ISelectionManager selectionManager = getEditor().getSelectionManager();
		selectionManager.setSync(false);
		getTransientPartParent().removeChild(transientObjectEditPart);
		selectionManager.setSync(true);
		transientObjectEditPart = null;
	}
	
	public TransientObjectEditPart getTransientRootEditPart() {
		return transientObjectEditPart;
	}
	
	private LayoutContainerEditPart getTransientPartParent() {
		GraphicalViewer upperGraphicalViewer = getEditor().getUpperGraphicalViewer();
		return (LayoutContainerEditPart) 
			upperGraphicalViewer.getEditPartRegistry().get(getEditor().getNonLayoutRoot());
	}
	
	public void setLayoutMode() {
		if (!isTransientMode())
			return;
		isTransientMode = false;
		removeTransientPart();
		transientLayer.uninstallRootFigure();
		registerLayoutVisuals(this);
		getContentPane().repaint();
	}
	
	public boolean isTransientMode() {
		return isTransientMode;
	}
	
	private static void refreshTransientVisuals(EditPart part) {
		Check.checkArg(part instanceof TransientObjectEditPart);
		TransientObjectEditPart transientPart = (TransientObjectEditPart) part;
		transientPart.refreshVisuals();
		transientPart.registerVisuals();
	}
	
	private static LayoutObjectEditPart getNonTransientLayoutEditPart(Object object) {
		if ((!(object instanceof TransientObjectEditPart)) &&
				(object instanceof LayoutObjectEditPart))
			return (LayoutObjectEditPart) object;
		
		return null;
	}

	private static void unregisterLayoutVisuals(EditPart part) {
		List children = part.getChildren();
		for (Iterator iter = children.iterator(); iter.hasNext();) {
			EditPart childPart = (EditPart) iter.next();
			LayoutObjectEditPart layoutObjectPart = getNonTransientLayoutEditPart(childPart);
			if (layoutObjectPart != null) {
				layoutObjectPart.unregisterVisuals();
			}
			unregisterLayoutVisuals(childPart);
		}
	}

	private static void registerLayoutVisuals(EditPart part) {
		List children = part.getChildren();
		for (Iterator iter = children.iterator(); iter.hasNext();) {
			EditPart childPart = (EditPart) iter.next();
			LayoutObjectEditPart layoutObjectPart = getNonTransientLayoutEditPart(childPart);
			if (layoutObjectPart != null) {
				layoutObjectPart.registerVisuals();
			}
			registerLayoutVisuals(childPart);
		}
	}

	public IFigure getTransientLayer() {
		return transientLayer;
	}
	
	public IFigure getTransientLayerRootFigure() {
		return transientLayer.getTransientRootFigure();
	}
	
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (childEditPart instanceof TransientObjectEditPart)
			getTransientLayerRootFigure().add(((GraphicalEditPart) childEditPart).getFigure());
		else
			super.addChildVisual(childEditPart, index);
		
		if (layoutContentsFigure == null) {
			GraphicalEditPart graphicalEditPart = (GraphicalEditPart) childEditPart;
			IFigure childFigure = graphicalEditPart.getFigure();
			Check.checkState(childFigure instanceof LayoutContentsFigure);
			layoutContentsFigure = (LayoutContentsFigure) childFigure;
		}
	}

	public void reset() {
		ScalableLayeredPane scalableLayers = (ScalableLayeredPane) layeredPane.getLayer(SCALABLE_LAYERS);
		Layer layer = scalableLayers.getLayer(PRINTABLE_LAYERS);
		layer.remove(transientLayer);
		transientLayer = new TransientLayer();
		layer.add(transientLayer, TRANSIENT_LAYER);
	}

}
