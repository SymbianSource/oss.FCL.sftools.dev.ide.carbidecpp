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
package com.nokia.carbide.cpp.uiq.components.slot;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import com.nokia.carbide.cpp.uiq.components.util.UiqUtils;
import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.ILayout;
import com.nokia.sdt.datamodel.adapter.IVisualAppearance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;

public class IconSlotImplFactory implements IImplFactory {
	
	public IconSlotImplFactory() {
	}
	
	public Object getImpl(EObject componentInstance) {
		return new IconSlotImplementations(componentInstance);
	}
	
	class IconSlotImplementations implements ILayout {
		
		private EObject eobjectCI;
		
		public IconSlotImplementations(EObject componentInstance) {
			this.eobjectCI = componentInstance;
		}
		
		public Point getPreferredSize(int wHint, int hHint, ILookAndFeel laf) {
			IComponentInstance ci = ModelUtils.getComponentInstance(getEObject());
			EObject parentObj = ci.getParent();
			int sbbTypeId = UiqUtils.getSbbTypeId(parentObj);
			switch (sbbTypeId) {
			case UiqUtils.SbbTypeIds.iconCaptionedOneline:
			case UiqUtils.SbbTypeIds.iconCaptionedTwoline:
			case UiqUtils.SbbTypeIds.iconIconOneline:
			case UiqUtils.SbbTypeIds.iconOneline:
			case UiqUtils.SbbTypeIds.iconOnelineIcon:
			case UiqUtils.SbbTypeIds.iconTwoline:
			case UiqUtils.SbbTypeIds.iconTwolineIcon:
			case UiqUtils.SbbTypeIds.onelineIcon:
			case UiqUtils.SbbTypeIds.twolineIcon:
				return laf.getDimension("sbbIcon.size");
			case UiqUtils.SbbTypeIds.largeThumbnailThreeline:
				return laf.getDimension("sbbLargeThumbnail.size");
			case UiqUtils.SbbTypeIds.mediumThumbnailDoubleOneline:
				return laf.getDimension("sbbMediumThumbnail.size");
			}
			return null;
		}

		public void layout(ILookAndFeel laf) {
			ILayoutObject lo = ModelUtils.getLayoutObject(eobjectCI);
			Point iconSlotSize = new Point(lo.getBounds().width, lo.getBounds().height);
			Point childPS = getPreferredSizeFromChild(iconSlotSize.x, iconSlotSize.y, laf);
			if (childPS == null) {
				return;
			}
			//scale if needed
			childPS.x = childPS.x > iconSlotSize.x ? iconSlotSize.x : childPS.x;
			childPS.y = childPS.y > iconSlotSize.y ? iconSlotSize.y : childPS.y;
			//justify horizontally/vertically
			int x = (iconSlotSize.x - childPS.x) / 2;
			int y = (iconSlotSize.y - childPS.y) / 2;
			Rectangle newbounds = new Rectangle(x, y, childPS.x, childPS.y);
			//set child bounds
			EObject[] children = getChildren();
			if (children != null) {
				for (EObject child : children) {
					ILayoutObject childLO = ModelUtils.getLayoutObject(child);
					childLO.setBounds(newbounds);
				}
			}
		}
		
		private Point getPreferredSizeFromChild(int wHint, int hHint, ILookAndFeel laf) {
			EObject[] children = getChildren();
			if (children != null && children.length > 0) {
				EObject imageChild = children[0];
				ILayout layout = (ILayout) EcoreUtil.getRegisteredAdapter(imageChild, ILayout.class);
		    	if (layout != null)
					return layout.getPreferredSize(wHint, hHint, laf);

		    	IVisualAppearance visual = 
		    		(IVisualAppearance) EcoreUtil.getRegisteredAdapter(imageChild, IVisualAppearance.class);
		    	if (visual != null)
		    		return visual.getPreferredSize(wHint, hHint, laf);
			}
	    	
			return null;
		}
		
		private EObject[] getChildren() {
			IComponentInstance ci = ModelUtils.getComponentInstance(eobjectCI);
			return ci.getChildren();
		}
		
		public EObject getEObject() {
			return eobjectCI;
		}
	}	
}
