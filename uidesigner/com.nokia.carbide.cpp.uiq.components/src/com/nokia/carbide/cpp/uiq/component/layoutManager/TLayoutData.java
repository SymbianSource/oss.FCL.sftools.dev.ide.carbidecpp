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
/* START_USECASES: CU10 END_USECASES */

package com.nokia.carbide.cpp.uiq.component.layoutManager;

import org.eclipse.emf.ecore.EObject;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;

public class TLayoutData {
	
	private static final String LAYOUTCONTROLBASE_ID = "com.nokia.carbide.uiq.LayoutControlBase";
	private static final String SBB_ID = "com.nokia.carbide.uiq.SystemBuildingBlock";
	private static final String QIKCONTAINER_ID = "com.nokia.carbide.uiq.CQikContainer";
	private static final String ISLAYOUTMANAGER_ATTR = "is-layout-manager";
	private static final String DEFAULTLAYOUTDATA = "defaultLayoutData";
	private static final String LAYOUTDATA = "layoutData";
	
	public enum TQikLayoutHorizontalAlignment {
		EQikLayoutHAlignInherit,
		EQikLayoutHAlignFill,
		EQikLayoutHAlignLeft,
		EQikLayoutHAlignCenter,
		EQikLayoutHAlignRight
	}
	
	public enum TQikLayoutVerticalAlignment {
		EQikLayoutVAlignInherit,
		EQikLayoutVAlignFill,
		EQikLayoutVAlignTop,
		EQikLayoutVAlignCenter,
		EQikLayoutVAlignBottom
	}
	
	public enum TQikLayoutBoolSetting {
		EQikLayoutInheritBoolValue(-1),
		EQikLayoutFalseValue(0),
		EQikLayoutTrueValue(1);
		
		private final int intValue;
		
		TQikLayoutBoolSetting(int intValue) {
			this.intValue = intValue;
		}
		
		public int intValue() {
			return intValue;
		}
	}
	
	public enum TQikLayoutLogicalSizes {
		EQikLayoutUseControlMinimum(-1),
		EQikLayoutInheritMinimum(-2),
		EQikLayoutSmallIconSize(-3),
		EQikLayoutMediumIconSize(-4),
		EQikLayoutLargeIconSize(-5),
		EQikLayoutMediumThumbnailSize(-6),
		EQikLayoutLargeThumbnailSize(-7),
		EQikLayoutMediumPortraitThumbnailSize(-8),
		EQikLayoutLargePortraitThumbnailSize(-9);
		
		private final int intValue;
		
		TQikLayoutLogicalSizes(int intValue) {
			this.intValue = intValue;
		}
		
		public int intValue() {
			return intValue;
		}
	}
	
	public enum TQikLayoutLogicalMargins {
		EQikLayoutInheritMargin(-1),
		EQikLayoutContentGap(-2),
		EQikLayoutContentGapRight(-3),
		EQikLayoutContentGapLeft(-4),
		EQikLayoutLeftEdgeToContent(-5),
		EQikLayoutRightEdgeToContent(-6),
		EQikLayoutLeftEdgeToPopOut(-7),
		EQikLayoutRightEdgeToPopOut(-8),
		EQikLayoutRowTopMargin(-9),
		EQikLayoutRowBottomMargin(-10);
		
		private final int intValue;
		
		TQikLayoutLogicalMargins(int intValue) {
			this.intValue = intValue;
		}
		
		public int intValue() {
			return intValue;
		}
	}
	
	public final class TMargins8 {
		int left;
		int right;
		int top;
		int bottom;
		
		public TMargins8() {
			left = TQikLayoutLogicalMargins.EQikLayoutInheritMargin.intValue();
			right = TQikLayoutLogicalMargins.EQikLayoutInheritMargin.intValue();
			top = TQikLayoutLogicalMargins.EQikLayoutInheritMargin.intValue();
			bottom = TQikLayoutLogicalMargins.EQikLayoutInheritMargin.intValue();
		}
	}
	
	public TQikLayoutHorizontalAlignment horizontalAlignment;
	public TQikLayoutVerticalAlignment verticalAlignment;
	public TQikLayoutBoolSetting layoutWhenInvisible;
	public int minimumWidth;
	public int minimumHeight;
	public TMargins8 margins;
	
	private boolean isHorizontalAlignmentSet;
	private boolean isVerticalAlignmentSet;
	private boolean isLayoutWhenInvisibleSet;
	private boolean isMinimumWidthSet;
	private boolean isMinimumHeightSet;
	private boolean isLeftMarginSet;
	private boolean isRightMarginSet;
	private boolean isTopMarginSet;
	private boolean isBottomMarginSet;
	
	public TLayoutData() {
		horizontalAlignment = TQikLayoutHorizontalAlignment.EQikLayoutHAlignInherit;
		verticalAlignment = TQikLayoutVerticalAlignment.EQikLayoutVAlignInherit;
		layoutWhenInvisible = TQikLayoutBoolSetting.EQikLayoutInheritBoolValue;
		minimumWidth = TQikLayoutLogicalSizes.EQikLayoutInheritMinimum.intValue();
		minimumHeight = TQikLayoutLogicalSizes.EQikLayoutInheritMinimum.intValue();
		margins = new TMargins8();
		
		isHorizontalAlignmentSet = false;
		isVerticalAlignmentSet = false;
		isLayoutWhenInvisibleSet = false;
		isMinimumWidthSet = false;
		isMinimumHeightSet = false;
		isLeftMarginSet = false;
		isRightMarginSet = false;
		isTopMarginSet = false;
		isBottomMarginSet = false;
	}
	
	/**
	 * Obtains layout data.
	 * 
	 * Owner must be a layout manager, a layout control base, a system building block or a nested-qikcontainer
	 * 
	 * @param owner owner of the layout data property
	 */
	public void obtainData(EObject owner) {
		IComponentInstance ownerCI = ModelUtils.getComponentInstance(owner);
		EObject ownerParent = ownerCI.getParent();
		IComponent ownerComponent = ownerCI.getComponent();
		if (!(ModelUtils.isInstanceOf(owner, LAYOUTCONTROLBASE_ID) ||
				ModelUtils.isInstanceOf(owner, SBB_ID) ||
				ModelUtils.isInstanceOf(owner, QIKCONTAINER_ID) ||
				ModelUtils.booleanAttribute(ownerComponent, ISLAYOUTMANAGER_ATTR))) {
			return;
		}
		if (ModelUtils.isInstanceOf(owner, QIKCONTAINER_ID)) {
			if (!ModelUtils.isInstanceOf(ownerParent, QIKCONTAINER_ID)) {
				return;
			}
		}
		
		String ld = LAYOUTDATA;
		if (ModelUtils.booleanAttribute(ownerComponent, ISLAYOUTMANAGER_ATTR)) {
			ld = DEFAULTLAYOUTDATA;
		}
		
		String propertyPath = null;
		Object pv = null;
		
		//horizontalAlignment;
		propertyPath = ld + "." + "horizontalAlignment";
		pv = ModelUtils.getEditablePropertyValueFromPath(owner, propertyPath);
		if (pv == null) {
			return;
		}
		TQikLayoutHorizontalAlignment hA = Enum.valueOf(TLayoutData.TQikLayoutHorizontalAlignment.class, pv.toString());
		if (hA != TQikLayoutHorizontalAlignment.EQikLayoutHAlignInherit && !isHorizontalAlignmentSet) {
			horizontalAlignment = hA;
			isHorizontalAlignmentSet = true;
		}
		
		//verticalAlignment;
		propertyPath = ld + "." + "verticalAlignment";
		pv = ModelUtils.getEditablePropertyValueFromPath(owner, propertyPath);
		TQikLayoutVerticalAlignment vA = Enum.valueOf(TLayoutData.TQikLayoutVerticalAlignment.class, pv.toString());
		if (vA != TQikLayoutVerticalAlignment.EQikLayoutVAlignInherit && !isVerticalAlignmentSet) {
			verticalAlignment = vA;
			isVerticalAlignmentSet = true;
		}
		
		//layoutWhenInvisible;
		propertyPath = ld + "." + "layoutWhenInvisible";
		pv = ModelUtils.getEditablePropertyValueFromPath(owner, propertyPath);
		TQikLayoutBoolSetting lwi = Enum.valueOf(TLayoutData.TQikLayoutBoolSetting.class, pv.toString());
		if (lwi != TQikLayoutBoolSetting.EQikLayoutInheritBoolValue && !isLayoutWhenInvisibleSet) {
			layoutWhenInvisible = lwi;
			isLayoutWhenInvisibleSet = true;
		}
		
		//minimumWidth;
		propertyPath = ld + "." + "minimumWidth";
		pv = ModelUtils.getEditablePropertyValueFromPath(owner, propertyPath);
		int mW = Integer.parseInt(pv.toString());
		if (mW != TQikLayoutLogicalSizes.EQikLayoutInheritMinimum.intValue() && !isMinimumWidthSet) {
			minimumWidth = mW;
			isMinimumWidthSet = true;
		}
		
		//minimumHeight;
		propertyPath = ld + "." + "minimumHeight";
		pv = ModelUtils.getEditablePropertyValueFromPath(owner, propertyPath);
		int mH = Integer.parseInt(pv.toString());
		if (mH != TQikLayoutLogicalSizes.EQikLayoutInheritMinimum.intValue() && !isMinimumHeightSet) {
			minimumHeight = mH;
			isMinimumHeightSet = true;
		}
		
		//leftMargin
		propertyPath = ld + "." + "leftMargin";
		pv = ModelUtils.getEditablePropertyValueFromPath(owner, propertyPath);
		int lm = Integer.parseInt(pv.toString());
		if (lm != TQikLayoutLogicalMargins.EQikLayoutInheritMargin.intValue() && !isLeftMarginSet) {
			margins.left = lm;
			isLeftMarginSet = true;
		}
		
		//rightMargin
		propertyPath = ld + "." + "rightMargin";
		pv = ModelUtils.getEditablePropertyValueFromPath(owner, propertyPath);
		int rm = Integer.parseInt(pv.toString());
		if (rm != TQikLayoutLogicalMargins.EQikLayoutInheritMargin.intValue() && !isRightMarginSet) {
			margins.right = rm;
			isRightMarginSet = true;
		}
		
		//topMargin
		propertyPath = ld + "." + "topMargin";
		pv = ModelUtils.getEditablePropertyValueFromPath(owner, propertyPath);
		int tm = Integer.parseInt(pv.toString());
		if (tm != TQikLayoutLogicalMargins.EQikLayoutInheritMargin.intValue() && !isTopMarginSet) {
			margins.top = tm;
			isTopMarginSet = true;
		}
		
		//bottomMargin
		propertyPath = ld + "." + "bottomMargin";
		pv = ModelUtils.getEditablePropertyValueFromPath(owner, propertyPath);
		int bm = Integer.parseInt(pv.toString());
		if (bm != TQikLayoutLogicalMargins.EQikLayoutInheritMargin.intValue() && !isBottomMarginSet) {
			margins.bottom = bm;
			isBottomMarginSet = true;
		}
		
		EObject newOwner = null;
		if (ModelUtils.booleanAttribute(ownerComponent, ISLAYOUTMANAGER_ATTR)) {
			//use parent
			newOwner = ownerParent;
		} else {
			//use sibling
			IComponentInstance ownerParentCI = ModelUtils.getComponentInstance(ownerParent);
			EObject[] siblings = ownerParentCI.getChildren();
			for (EObject sibling : siblings) {
				IComponentInstance siblingCI = ModelUtils.getComponentInstance(sibling);
				if (ModelUtils.booleanAttribute(siblingCI.getComponent(), ISLAYOUTMANAGER_ATTR)) {
					newOwner = sibling;
					break;
				}
			}
		}
		obtainData(newOwner);
	}
}
