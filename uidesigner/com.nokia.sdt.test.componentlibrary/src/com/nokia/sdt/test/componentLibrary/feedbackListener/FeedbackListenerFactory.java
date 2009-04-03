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
package com.nokia.sdt.test.componentLibrary.feedbackListener;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.*;
import com.nokia.sdt.component.symbian.displaymodel.*;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.uidesigner.ui.utils.DrawingUtils;
import com.nokia.sdt.utils.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.views.properties.IPropertySource;

public class FeedbackListenerFactory implements IImplFactory {

	private class FeedbackAndContainmentImpl 
					extends AbstractTargetFeedbackListener 
									implements IQueryContainment, ILayout {
		
		public FeedbackAndContainmentImpl(EObject container) {
			super(container);
		}

		@Override
		protected ITargetFeedbackFigure createTargetFeedbackFigure() {
			return new AbstractTargetFeedbackFigure() {
				
				@Override
				protected void drawFeedback(Graphics graphics) {
					graphics.setLineWidth(2);
					graphics.setLineStyle(SWT.LINE_DOT);
					graphics.setForegroundColor(ColorConstants.black);
					org.eclipse.swt.graphics.Rectangle r = getChildBounds();
					Rectangle rect = new Rectangle(r.x, r.y, r.width, r.height);
					translateToFeedbackLayer(rect);
					
					graphics.drawRectangle(rect);
					boolean overRegion = mouseOverRegion();
					setExecutable(overRegion);
					if (overRegion) {
						DrawingUtils.drawAlphaBlendedRectangle(graphics, rect, 90, ColorConstants.gray);
					}
				}
				
				private boolean mouseOverRegion() {
					org.eclipse.swt.graphics.Rectangle r = getChildBounds();
					Rectangle childBounds = new Rectangle(r.x, r.y,r.width, r.height);
					translateToFeedbackLayer(childBounds);
					return childBounds.contains(getMouseLocation());
				}
			};
		}

		public void endTargetFeedback() {
			// nothing to do
		}

		public boolean canContainComponent(IComponent component, StatusHolder statusHolder) {
			IComponentInstance instance = Utilities.getComponentInstance(getContainer());
			EObject[] children = instance.getChildren();
			IComponentInstance lmInstance = Utilities.getComponentInstance(children[0]);
			Check.checkContract(lmInstance.getComponentId().equals("com.nokia.test.feedbackLayoutManager"));
			boolean isAllowed = children != null && children.length == 1;
			if (!isAllowed) {
				statusHolder.setStatus(Logging.newStatus(null, IStatus.ERROR, "This container is full."));
			}
			else {
				IAttributes attributes = (IAttributes) component.getAdapter(IAttributes.class);
				isAllowed = attributes.getBooleanAttribute(CommonAttributes.IS_LAYOUT_OBJECT, false);
				if (!isAllowed)
					statusHolder.setStatus(Logging.newStatus(null, IStatus.ERROR, "This kind of object is not allowed in this container"));
			}
				
			return isAllowed;
		}

		public boolean canContainChild(IComponentInstance child, StatusHolder statusHolder) {
			return canContainComponent(child.getComponent(), statusHolder);
		}

		public boolean canRemoveChild(IComponentInstance child) {
			return true;
		}

		public boolean isValidComponentInPalette(IComponent component) {
			return true;
		}
		
		private EObject getContainer() {
			IComponentInstance ci = Utilities.getComponentInstance(getEObject());
			return ci.getParent();
		}
		
		private org.eclipse.swt.graphics.Rectangle getChildBounds() {
			IPropertySource ps = Utilities.getPropertySource(getEObject());
			ILayoutObject containerObject = Utilities.getLayoutObject(getContainer());
			org.eclipse.swt.graphics.Rectangle bounds = containerObject.getBounds();
			int m = (Integer) ps.getPropertyValue("margin");
			return new org.eclipse.swt.graphics.Rectangle(m, m, bounds.width - 2 * m, bounds.height - 2 * m);
		}

		public void layout(ILookAndFeel laf) {
			IComponentInstance ci = Utilities.getComponentInstance(getContainer());
			EObject[] children = ci.getChildren();
			if (children == null || children.length == 0)
				return;
			
			ILayoutObject layoutChild = null;
			for (EObject child : children) {
				layoutChild = Utilities.getLayoutObject(child);
				if (layoutChild != null) {
					layoutChild.setBounds(getChildBounds());
					break;
				}
			}
		}

		public Point getPreferredSize(int wHint, int hHint, ILookAndFeel laf) {
			return null; // not used
		}
	}

	public Object getImpl(final EObject object) {
		return new FeedbackAndContainmentImpl(object);
	}
}
