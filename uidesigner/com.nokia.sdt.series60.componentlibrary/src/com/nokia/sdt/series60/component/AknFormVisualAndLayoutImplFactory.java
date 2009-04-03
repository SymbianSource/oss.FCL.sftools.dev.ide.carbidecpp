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
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.utils.drawing.GC;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.*;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * 
 *
 */
public class AknFormVisualAndLayoutImplFactory implements IImplFactory {

	/**
	 * 
	 *
	 */
	public static class VisualAndLayout implements IVisualAppearance, ILayout, IScrollBoundsProvider {

		private static final String DOUBLE_SPACED_PROPERTY_NAME = "EEikFormUseDoubleSpacedFormat"; //$NON-NLS-1$
		private static final String SHOW_BITMAPS_PROPERTY_NAME = "EEikFormShowBitmaps"; //$NON-NLS-1$
		
		private static final int TOP_OFFSET = 6;
		
		private EObject eObject;
		private IComponentInstance componentInstance;
		private ILayoutObject layoutObject;
		private IPropertySource propertySource;
		
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
		}
		
		private boolean isDoubleSpaced() {
			return ((Boolean) propertySource.getPropertyValue(DOUBLE_SPACED_PROPERTY_NAME)).booleanValue();
		}

		private boolean isShowingBitmaps() {
			return ((Boolean) propertySource.getPropertyValue(SHOW_BITMAPS_PROPERTY_NAME)).booleanValue();
		}

		private int getPromptDividerOffset(ILookAndFeel laf) {
			if (isDoubleSpaced()) {
				if (isShowingBitmaps()) 
					return laf.getInteger(Series60LAF.FORM_INSET, 20);
				else
					return laf.getInteger(Series60LAF.FORM_DIVIDER_OFFSET_DOUBLE, 6);
			}
			return laf.getInteger(Series60LAF.FORM_DIVIDER_OFFSET_SINGLE, 6);
		}
		
		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.IVisualAppearance#draw(com.nokia.sdt.utils.drawing.GC, com.nokia.sdt.displaymodel.ILookAndFeel)
		 */
		public void draw(GC gc, ILookAndFeel laf) {
		    Rectangle bounds = layoutObject.getBounds();
		    
		    Color color = VisualUtils.colorFromString((String) propertySource.getPropertyValue("backColor"), laf);
		    if (color == null) {
		        color = laf.getColor("EEikColorWindowBackground"); //$NON-NLS-1$
		    }
		    if (color != null) {
		        gc.setBackground(color);
		        gc.fillRectangle(new Rectangle(0, 0, bounds.width, bounds.height));
		    }

            if (!DialogUtils.hasDialogChildren(componentInstance)) {
				DialogUtils.drawDialogNoData(gc, laf);
			}
			else {
				// no decorations on 3.1+
				boolean shouldDecorate = laf.getBoolean(Series60LAF.DECORATIONS, true);
				if (shouldDecorate) {
					int dividerOffset = getPromptDividerOffset(laf);
					gc.setForeground(laf.getColor("EEikColorWindowText"));
					gc.drawLine(dividerOffset - 1, 0, dividerOffset - 1, bounds.height - 1); // vertical
					gc.drawLine(dividerOffset - 1, bounds.height - 1, bounds.width - 1, bounds.height - 1); // horiz
				}
			}
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.IVisualAppearance#getPreferredSize(int, int, com.nokia.sdt.displaymodel.ILookAndFeel)
		 */
		public Point getPreferredSize(int wHint, int hHint, ILookAndFeel laf) {
			return null; // not needed
		}
		
		private int getTopOffset() {
			Rectangle r = layoutObject.getBounds();
			if (r.height < 220)
				return TOP_OFFSET;
			else if (r.height < 340)
				return TOP_OFFSET + (TOP_OFFSET / 2);

			return TOP_OFFSET * 2;
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.ILayout#layout(com.nokia.sdt.displaymodel.ILookAndFeel)
		 */
		public void layout(ILookAndFeel laf) {
			Rectangle rect = layoutObject.getBounds();
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
						Point size = childAppearance.getPreferredSize(rect.width, -1, laf);
						if (size == null)
							heights[i] = 0;
						else {
							heights[i] = size.y + getTopOffset() / 2;
							int extraPadding = isDoubleSpaced() ? 
									laf.getInteger(Series60LAF.FORM_DOUBLE_EXTRA_PADDING, 0) :
										laf.getInteger(Series60LAF.FORM_SINGLE_EXTRA_PADDING, 0);
							heights[i] += 2 * extraPadding;
							maxWidth = Math.max(maxWidth, size.x);
						}
					}
				}
				int totalHeights = 0;
				for (int i = 0; i < children.length; i++)
					totalHeights += heights[i];
				int curY = (totalHeights > rect.height) ? 0 : getTopOffset();
				for (int i = 0; i < children.length; i++) {
					if (Utilities.isNonLayoutObject(children[i]))
						continue;
					
					int extraPadding = isDoubleSpaced() ? 
							laf.getInteger(Series60LAF.FORM_DOUBLE_EXTRA_PADDING, 0) :
								laf.getInteger(Series60LAF.FORM_SINGLE_EXTRA_PADDING, 0);
					Rectangle childBounds = 
						new Rectangle(0, curY + 2 * extraPadding, rect.width, heights[i]);
					curY += heights[i];
					ILayoutObject childObject = 
						(ILayoutObject) EcoreUtil.getRegisteredAdapter(children[i], ILayoutObject.class);
					if (childObject != null) {
                        childObject.setBounds(childBounds);
                        childObject.forceRedraw();
					}
				}
			}
		}

		public Rectangle getScrollBounds(ILookAndFeel laf) {
			Rectangle r = layoutObject.getBounds();
			int topOffset = getTopOffset();
			r.height -= topOffset;
			r.y = topOffset / 2;
			return new Rectangle(0, r.y, r.width, r.height);
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
