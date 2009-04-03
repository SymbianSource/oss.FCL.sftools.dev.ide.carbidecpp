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


package com.nokia.sdt.series60.component;

import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.component.symbian.laf.Series60LAF;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.utils.drawing.GC;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * 
 *
 */
public class AknDialogVisualAndLayoutImplFactory implements IImplFactory {

	/**
	 * 
	 *
	 */
	public static class VisualAndLayout implements IVisualAppearance, ILayout {

		private EObject eObject;
		private IComponentInstance componentInstance;
		private ILayoutObject layoutObject;
		private IPropertySource propertySource;
		private final static int TOP_LEFT = 3;
		private final static int BOTTOM_RIGHT = 5;
		private Rectangle lastCalculatedRect;
		
		private final static String FILL_RECT_PROPERTY = "fillRect";
		
		/**
		 * @param instance
		 */
		public VisualAndLayout(EObject instance) {
			eObject = instance;
			this.componentInstance = (IComponentInstance) EcoreUtil.getRegisteredAdapter(
					eObject, IComponentInstance.class);
			this.layoutObject = (ILayoutObject) EcoreUtil.getRegisteredAdapter(
					eObject, ILayoutObject.class);
			this.propertySource = (IPropertySource) EcoreUtil.getRegisteredAdapter(
					eObject, IPropertySource.class);
			Check.checkArg(componentInstance);
			Check.checkArg(layoutObject);
			Check.checkArg(propertySource);
			componentInstance.addPropertyListener(new IComponentInstancePropertyListener() {
				public void propertyChanged(EObject componentInstance, Object propertyId) {
					if (propertyId.equals(FILL_RECT_PROPERTY)) {
						ILayoutContainer container = 
							(ILayoutContainer) EcoreUtil.getRegisteredAdapter(getEObject(), ILayoutContainer.class);
						container.layoutChildren();
					}
				}
			});
		}
		
		private boolean fillClientRect() {
			return ((Boolean) propertySource.getPropertyValue(FILL_RECT_PROPERTY)).booleanValue();
		}
		
		private Rectangle getMaximumUsableRect() {
			Rectangle bounds = layoutObject.getBounds();
			Rectangle rect = new Rectangle(0, 0, bounds.width, bounds.height);
			if (!fillClientRect()) {
				return VisualUtils.shrinkRect(rect, TOP_LEFT, TOP_LEFT, BOTTOM_RIGHT, BOTTOM_RIGHT);
			}
			
			return rect;
		}
		
		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.IVisualAppearance#draw(com.nokia.sdt.utils.drawing.GC, com.nokia.sdt.displaymodel.ILookAndFeel)
		 */
		public void draw(GC gc, ILookAndFeel laf) {
			Rectangle rect = (lastCalculatedRect != null) ? lastCalculatedRect : getMaximumUsableRect();

			Color color = VisualUtils.colorFromString((String) propertySource.getPropertyValue("backColor"), laf);
			if (color != null) {
				gc.setBackground(color);
				gc.fillRectangle(rect);
			}
			if (!fillClientRect()) {
				gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLACK));
				gc.drawRectangle(rect);
				int l = rect.x;
				int r = l + rect.width;
				int t = rect.y;
				int b = t + rect.height;
				gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW));
				gc.drawLine(r + 1, t + 1, r + 1, b + 1);
				gc.drawLine(l + 1, b + 1, r + 1, b + 1);
				gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
				gc.drawLine(r + 2, t + 2, r + 2, b + 2);
				gc.drawLine(l + 2, b + 2, r + 2, b + 2);
			}
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.IVisualAppearance#getPreferredSize(int, int, com.nokia.sdt.displaymodel.ILookAndFeel)
		 */
		public Point getPreferredSize(int wHint, int hHint, ILookAndFeel laf) {
			return null; // not needed
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.ILayout#layout(com.nokia.sdt.displaymodel.ILookAndFeel)
		 */
		public void layout(ILookAndFeel laf) {
			Rectangle usableRect = getMaximumUsableRect();
			Rectangle savedRect = lastCalculatedRect;
			EObject[] children = componentInstance.getChildren();
			if (children != null) {
				int[] heights = new int[children.length];
				int maxWidth = 0;
				for (int i = 0; i < children.length; i++) {
					if (Utilities.isNonLayoutObject(children[i]))
						continue;
					
					IVisualAppearance childAppearance = 
						(IVisualAppearance) EcoreUtil.getRegisteredAdapter(children[i], IVisualAppearance.class);
					if (childAppearance != null) {
						Point size = null;
						if (fillClientRect()) // if we're filling the rect, give child our width
							size = childAppearance.getPreferredSize(usableRect.width, -1, laf);
						else // otherwise, let them give us their width
							size = childAppearance.getPreferredSize(-1, -1, laf);
						if (size == null)
							heights[i] = 0;
						else {
							heights[i] = size.y;
							maxWidth = Math.max(maxWidth, size.x);
						}
					}
				}
				calculateRect(usableRect, heights, maxWidth, laf);
				int curY = lastCalculatedRect.y;
				for (int i = 0; i < children.length; i++) {
					if (Utilities.isNonLayoutObject(children[i]))
						continue;
					
					Rectangle childBounds = 
						new Rectangle(lastCalculatedRect.x, curY, lastCalculatedRect.width, heights[i]);
					curY += heights[i];
					ILayoutObject childObject = 
						(ILayoutObject) EcoreUtil.getRegisteredAdapter(children[i], ILayoutObject.class);
					if (childObject != null)
                        childObject.setBounds(childBounds);
				}
			}
			else {
				calculateRect(usableRect, new int[] {0}, 1, laf);
			}
			if (!lastCalculatedRect.equals(savedRect))
				Utilities.fireImageChanged(getEObject());
		}

		private void calculateRect(Rectangle usableRect, int[] childHeights, int maxWidth, ILookAndFeel laf) {
			if (fillClientRect()) {
				lastCalculatedRect = usableRect;
			}
			else {
				int totalHeight = 0;
				for (int i = 0; i < childHeights.length; i++) {
					totalHeight += childHeights[i];
				}
				Rectangle bounds = layoutObject.getBounds();
				Point screenSize = laf.getDimension(Series60LAF.SCREEN_SIZE);
				Check.checkState(screenSize != null);
				int top = ((screenSize.y - totalHeight) / 2) - bounds.y;
				int left = Math.max((usableRect.width - maxWidth) / 2, usableRect.x);
				int width = Math.min(maxWidth, usableRect.width);
				lastCalculatedRect = new Rectangle(left, top, width, totalHeight);
			}
		}


		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.IModelAdapter#getEObject()
		 */
		public EObject getEObject() {
			return eObject;
		}

	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.IImplFactory#getImpl(org.eclipse.emf.ecore.EObject)
	 */
	public Object getImpl(EObject componentInstance) {
		return new VisualAndLayout(componentInstance);
	}

}
