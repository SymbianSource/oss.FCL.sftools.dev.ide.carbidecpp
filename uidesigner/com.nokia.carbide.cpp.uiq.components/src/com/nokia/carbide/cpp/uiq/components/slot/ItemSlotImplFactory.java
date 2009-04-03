/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.cpp.uiq.components.slot;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import com.nokia.carbide.cpp.uiq.component.layoutManager.TLayoutData;
import com.nokia.carbide.cpp.uiq.component.layoutManager.TLayoutData.TQikLayoutHorizontalAlignment;
import com.nokia.sdt.utils.drawing.TextRendering;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.utils.drawing.IFont;
import java.util.*;


import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IComponentInstanceChildListener;
import com.nokia.sdt.datamodel.adapter.IComponentInstancePropertyListener;
import com.nokia.sdt.datamodel.adapter.ILayout;
import com.nokia.sdt.datamodel.adapter.IVisualAppearance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.utils.drawing.GC;

public class ItemSlotImplFactory implements IImplFactory {
	
	public Object getImpl(EObject componentInstance) {
		return new ItemSlotImplementations(componentInstance);
	}
	private static Hashtable ht = new Hashtable();

	static{
		ht.put("EQikCtOnelineBuildingBlock-EQikItemSlot1", 1);						//$NON-NLS-1$
		ht.put("EQikCtOnelineBuildingBlock-EQikItemSlot2", 1);						//$NON-NLS-1$
		ht.put("EQikCtCaptionedTwolineBuildingBlock-EQikItemSlot1",1);				//$NON-NLS-1$
		ht.put("EQikCtCaptionedTwolineBuildingBlock-EQikItemSlot2",2);				//$NON-NLS-1$
		ht.put("EQikCtTwolineBuildingBlock-EQikItemSlot1", 2);						//$NON-NLS-1$
		ht.put("EQikCtManylinesBuildingBlock-EQikItemSlot1",4);						//$NON-NLS-1$
		ht.put("EQikCtIconOnelineBuildingBlock-EQikItemSlot1",1);					//$NON-NLS-1$
		ht.put("EQikCtIconCaptionedTwolineBuildingBlock-EQikItemSlot1",1);			//$NON-NLS-1$
		ht.put("EQikCtIconCaptionedTwolineBuildingBlock-EQikItemSlot2",2);			//$NON-NLS-1$
		ht.put("EQikCtIconTwolineBuildingBlock-EQikItemSlot1", 2);					//$NON-NLS-1$
		ht.put("EQikCtOnelineIconBuildingBlock-EQikItemSlot1",1);					//$NON-NLS-1$
		ht.put("EQikCtIconOnelineIconBuildingBlock-EQikItemSlot1", 1);				//$NON-NLS-1$
		ht.put("EQikCtIconTwolineIconBuildingBlock-EQikItemSlot1", 2);				//$NON-NLS-1$
		ht.put("EQikCtMediumThumbnailDoubleOnelineBuildingBlock-EQikItemSlot1", 1);	//$NON-NLS-1$
		ht.put("EQikCtMediumThumbnailDoubleOnelineBuildingBlock-EQikItemSlot2", 1);	//$NON-NLS-1$
		ht.put("EQikCtLargeThumbnailThreelineBuildingBlock-EQikItemSlot1", 3);		//$NON-NLS-1$
		ht.put("EQikCtCaptionedOnelineBuildingBlock-EQikItemSlot1", 1);				//$NON-NLS-1$
		ht.put("EQikCtCaptionedOnelineBuildingBlock-EQikItemSlot2", 1);				//$NON-NLS-1$
		ht.put("EQikCtIconCaptionedOnelineBuildingBlock-EQikItemSlot1", 1);			//$NON-NLS-1$
		ht.put("EQikCtIconCaptionedOnelineBuildingBlock-EQikItemSlot2", 1);			//$NON-NLS-1$
		ht.put("EQikCtTwolineIconBuildingBlock-EQikItemSlot1", 2);					//$NON-NLS-1$
		ht.put("EQikCtIconIconOnelineBuildingBlock-EQikItemSlot1", 1);				//$NON-NLS-1$
		ht.put("EQikCtHalflineHalflineBuildingBlock-EQikItemSlot1",1);				//$NON-NLS-1$
		ht.put("EQikCtHalflineHalflineBuildingBlock-EQikItemSlot2",1);				//$NON-NLS-1$
		ht.put("EQikCtCaptionedHalflineBuildingBlock-EQikItemSlot1", 1);			//$NON-NLS-1$
		ht.put("EQikCtCaptionedHalflineBuildingBlock-EQikItemSlot2", 1);			//$NON-NLS-1$
	}
	
	public class ItemSlotImplementations implements IVisualAppearance,
													ILayout,
													IComponentInstanceChildListener,
													IComponentInstancePropertyListener {
		
		private static final String LAYOUT_CONTROL_BASE_ID = "com.nokia.carbide.uiq.LayoutControlBase";	//$NON-NLS-1$
		private static final String CAPTION_PROPERTY = "caption";	//$NON-NLS-1$
		private static final String SBB_TYPE_PROPERTY = "type";	//$NON-NLS-1$
		private static final String SLOTID_PROPERTY = "slotId"; //$NON-NLS-1$
		
		private EObject eobjectCI;
		
		public ItemSlotImplementations(EObject eobjectCI) {
			this.eobjectCI = eobjectCI;
			
		}
		
		public EObject getEObject() {
			return eobjectCI;
		}
		
		/***** IVisualAppearance *****/
		public void draw(GC gc, ILookAndFeel laf) {
			if (!hasChildren()) {
				//draw caption text property
				String caption = (String)ModelUtils.getEditablePropertyValue(eobjectCI, CAPTION_PROPERTY);
				IFont font = laf.getFont("NormalFont");	//$NON-NLS-1$
				gc.setFont(font);
				int flags = IFont.OVERFLOW_ELLIPSIS | IFont.WRAPPING_ENABLED | IFont.ANTIALIAS_OFF;
				
				Rectangle layoutBounds = ModelUtils.getLayoutObject(eobjectCI).getBounds();
				Rectangle renderingBounds = new Rectangle(0, 0, layoutBounds.width, layoutBounds.height);
				
				gc.drawFormattedString(caption, renderingBounds, flags);
			}
		}
		
		public Point getPreferredSize(int wHint, int hHint, ILookAndFeel laf) {
			Point preferredSize = null;
			if (!hasChildren()) {
				preferredSize = getPreferredSizeFromCaption(wHint, hHint, laf);
			} else {
				preferredSize = getPreferredSizeFromChildren(wHint, hHint, laf);
			}
			return preferredSize;
		}
		
		/***** ILayout *****/
		public void layout(ILookAndFeel laf) {
			ILayoutObject lo = ModelUtils.getLayoutObject(eobjectCI);
			Rectangle bounds = lo.getBounds();
			EObject[] children = getChildren();
//			Point preferredSize = getPreferredSizeFromChildren(children, bounds.width, bounds.height, laf);
			Point preferredSize = new Point(bounds.width, bounds.height);
			layoutChildren(children, preferredSize);
			collateralLayout();
		}
		
		/***** IComponentInstanceChildListener *****/
		public void childAdded(EObject parent, EObject child) {
			IComponentInstance childCI = ModelUtils.getComponentInstance(child);
			childCI.addPropertyListener(this);
		}
		
		public void childRemoved(EObject parent, EObject child) {
			IComponentInstance childCI = ModelUtils.getComponentInstance(child);
			childCI.removePropertyListener(this);
		}
		
		public void childrenReordered(EObject parent) {
			
		}
		
		/***** IComponentInstancePropertyListener *****/
		public void propertyChanged(EObject componentInstance, Object propertyId) {
			if (ModelUtils.isInstanceOf(componentInstance, LAYOUT_CONTROL_BASE_ID)) {
				IComponentInstance ci = ModelUtils.getComponentInstance(componentInstance);
				EObject parent = ci.getParent();
				if (ModelUtils.isSameComponentInstance(eobjectCI, parent)) {
					ILayoutContainer lc = ModelUtils.getLayoutContainer(parent);
					if (lc != null) {
						lc.layoutChildren();
						collateralLayout();
					}
				}
			}
			if (((String)propertyId).equals(CAPTION_PROPERTY) && !hasChildren()) {
				collateralLayout();
			}
		}
		
		private boolean hasChildren() {
			EObject[] children = getChildren();
			return children != null && children.length > 0;
		}
		
		private EObject[] getChildren() {
			IComponentInstance ci = ModelUtils.getComponentInstance(eobjectCI);
			return ci.getChildren();
		}
		
		private Point getPreferredSizeFromChildren(EObject[] children, int wHint, int hHint, ILookAndFeel laf) {
			Point childrenPreferredSize = null;
			if (children != null) {
				for (EObject child : children) {
					if (isChildControlReferenceReady(child)) {
						childrenPreferredSize = getChildPreferredSize(child, wHint, hHint, laf);
					}
				}
			}
			return childrenPreferredSize;
		}
		
		private Point getPreferredSizeFromChildren(int wHint, int hHint, ILookAndFeel laf) {
			return getPreferredSizeFromChildren(getChildren(), wHint, hHint, laf);
		}
		
		private Point getChildPreferredSize(EObject child, int wHint, int hHint, ILookAndFeel laf) {
			ILayout layout = (ILayout) EcoreUtil.getRegisteredAdapter(child, ILayout.class);
	    	if (layout != null)
				return layout.getPreferredSize(wHint, hHint, laf);

	    	IVisualAppearance visual = 
	    		(IVisualAppearance) EcoreUtil.getRegisteredAdapter(child, IVisualAppearance.class);
	    	if (visual != null)
	    		return visual.getPreferredSize(wHint, hHint, laf);
	    	
	    	return null;
		}
		
		private void layoutChildren(EObject[] children, Point size) {
			if (children != null && size != null) {
				for (EObject child : children) {
					if (isChildControlReferenceReady(child)) {
						ILayoutObject childLO = ModelUtils.getLayoutObject(child);
						childLO.setBounds(new Rectangle(0, 0, size.x, size.y));
					}
				}
			}
		}
		
		private boolean isChildControlReferenceReady(EObject child) {
			Object childControlRefPV = ModelUtils.getEditablePropertyValue(child, "control");	//$NON-NLS-1$
			if (childControlRefPV != null) {
				return !((String)childControlRefPV).equals("");		//$NON-NLS-1$
			}
			return false;
		}
		
		private void collateralLayout() {
			IComponentInstance ci = ModelUtils.getComponentInstance(eobjectCI);
			EObject parentObj = ci.getParent();
			IComponentInstance parentCI = ModelUtils.getComponentInstance(parentObj);
			if (parentCI != null) {
				parentCI.firePropertyChanged("collateralLayout");		//$NON-NLS-1$
			}
		}
		
		private Point getPreferredSizeFromCaption(int wHint, int hHint, ILookAndFeel laf) {
			String caption = (String)ModelUtils.getEditablePropertyValue(eobjectCI, CAPTION_PROPERTY);
			String sbbType = getSBBType();
			int maxNumberOfLines = getAllowedNumberOfLines(sbbType);
			IFont font = laf.getFont("NormalFont");	//$NON-NLS-1$
			int flags = IFont.OVERFLOW_ELLIPSIS | IFont.WRAPPING_ENABLED | IFont.ANTIALIAS_OFF;
			
			Point prefSize = new Point(wHint, hHint);
			int width = 0;
			int height = 0;
			int lineGap = 0;
			if (maxNumberOfLines > 0 && maxNumberOfLines < 4) {
				String[] lines = TextRendering.formatIntoLines(font, caption, prefSize.x, flags, maxNumberOfLines);
				for (String line : lines) {
					Point lineSize = TextRendering.formattedStringExtent(font, line, prefSize, flags, lineGap);
					width = Math.max(width, lineSize.x);
					height += lineSize.y;
				}
			} else {
				if (maxNumberOfLines == 4) {
					Point captionSize = TextRendering.formattedStringExtent(font, caption, prefSize, flags, lineGap);
					width = captionSize.x;
					height += captionSize.y;
				} else {
					height = prefSize.y;
				}
			}
			if (getHorizontalAlignment() == TQikLayoutHorizontalAlignment.EQikLayoutHAlignFill ||
					getHorizontalAlignment() == TQikLayoutHorizontalAlignment.EQikLayoutHAlignInherit) {
				prefSize.x = wHint;
			} else {
				//If width is zero (no caption nor control) give a 5 pixel width, thus we do not
				//prevent user from dropping a new control over the slot and visual impact is minimum
				prefSize.x = width > 0 ? width : 5;
			}
			prefSize.y = height > 0 ? height : hHint;
			return prefSize;
		}
		
		private String getSBBType() {
			IComponentInstance ci = ModelUtils.getComponentInstance(eobjectCI);
			EObject parentObj = ci.getParent();
			return (String)ModelUtils.getEditablePropertyValue(parentObj, SBB_TYPE_PROPERTY);
		}
		
		private int getAllowedNumberOfLines(String sbbType) {
			String slotId = (String)ModelUtils.getEditablePropertyValue(eobjectCI, SLOTID_PROPERTY);	//$NON-NLS-1$
			String hashTableKey = sbbType + "-" + slotId;	//$NON-NLS-1$
            return Integer.parseInt(ht.get(hashTableKey).toString());
		}
		
		private TQikLayoutHorizontalAlignment getHorizontalAlignment() {
			IComponentInstance ci = ModelUtils.getComponentInstance(getEObject());
			TLayoutData layoutData = new TLayoutData();
			layoutData.obtainData(ci.getParent());
			return layoutData.horizontalAlignment;
		}
	}
}
