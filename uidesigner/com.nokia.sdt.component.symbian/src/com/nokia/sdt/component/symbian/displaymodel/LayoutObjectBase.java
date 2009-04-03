/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.sdt.component.symbian.displaymodel;

import com.nokia.sdt.component.adapter.IDesignerImages;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IComponentInstancePropertyListener;
import com.nokia.sdt.datamodel.adapter.IVisualAppearance;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.displaymodel.adapter.LayoutObjectListener;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.utils.drawing.GC;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import java.util.ArrayList;

public abstract class LayoutObjectBase extends AdapterImpl implements ILayoutObject {

	protected IComponentInstance instance;
	private ArrayList listeners = new ArrayList();
	protected IDisplayModel displayModel;

	public LayoutObjectBase(IDisplayModel displayModel, IComponentInstance instance) {
		Check.checkArg(displayModel);
		Check.checkArg(instance);
		this.instance = instance;
		this.displayModel = displayModel;

		// on any property change, redraw
		instance.addPropertyListener(new IComponentInstancePropertyListener() {

			public void propertyChanged(EObject componentInstance, Object propertyId) {
				handlePropertyChange(componentInstance, propertyId);
			}
		});

	}
	
	/** Default handler for a property change on the instance.  Subclasses
	 * may override to add additional behavior. */
	protected void handlePropertyChange(EObject componentInstance, Object propertyId) {
		fireImageChanged();
		tellParentToLayout();
	}
	
	protected void tellParentToLayout() {
		ILayoutContainer parentContainer = getParentContainer();
		if (Utilities.isTransient(getEObject()) && !Utilities.isTransient(parentContainer.getEObject()))
			return;
		if (parentContainer != null)
			parentContainer.layoutChildren();
	}

	public Image getImage() {
		Image image = Utilities.createImageTransparent(getBounds());
		if (image == null)
			return null;
		
		GC gc = new GC(Display.getDefault(), image);
		gc.setBackground(gc.getTransparentColor());
		Rectangle imageBounds = image.getBounds();
		gc.fillRectangle(imageBounds);
		
		IVisualAppearance visual = Utilities.getVisualAppearance(getEObject());
		if (visual != null) {
			visual.draw(gc, displayModel.getLookAndFeel());
		} else {
			IDesignerImages di = (IDesignerImages) instance.getComponent().getAdapter(IDesignerImages.class);
			if (di != null) {
				Image layoutImage = di.getStaticLayoutImage();
				if (layoutImage != null) {
					Rectangle srcBounds = layoutImage.getBounds();
					gc.drawImage(layoutImage, 0, 0, srcBounds.width, srcBounds.height,
							0, 0, imageBounds.width, imageBounds.height);
				}
			}
		}
		gc.dispose();
		return image;
	}

	public void addListener(LayoutObjectListener listener) {
		ArrayList newList = new ArrayList(listeners);
		newList.add(listener);
		listeners = newList;
	}

	public void removeListener(LayoutObjectListener listener) {
		ArrayList newList = new ArrayList(listeners);
		newList.remove(listener);
		listeners = newList;
	
	}

	public ILayoutContainer getParentContainer() {
		IComponentInstance instance = 
			(IComponentInstance) EcoreUtil.getRegisteredAdapter(getEObject(), IComponentInstance.class);
		EObject parent = instance.getParent();
		if (parent != null)
			return (ILayoutContainer) EcoreUtil.getRegisteredAdapter(parent, ILayoutContainer.class);
		
		return null;
	}

	public void forceRedraw() {
	    fireImageChanged();
	}

	protected LayoutObjectListener[] getListeners() {
		return (LayoutObjectListener[]) listeners.toArray(new LayoutObjectListener[listeners.size()]);
	}

	protected void fireBoundsChanged() {
		LayoutObjectListener[] listeners = getListeners();
		for (int i = 0; i < listeners.length; i++) {
			listeners[i].boundsChanged(this);
		}
	}

	protected void fireImageChanged() {
		LayoutObjectListener[] listeners = getListeners();
		for (int i = 0; i < listeners.length; i++) {
			listeners[i].imageChanged(this);
		}
	}

	public EObject getEObject() {
		return instance.getEObject();
	}

	public boolean isAdapterForType(Object type) {
		return ILayoutObject.class.equals(type);
	}

}
