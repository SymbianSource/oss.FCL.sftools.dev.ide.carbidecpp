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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.views.properties.IPropertySource;

import com.nokia.carbide.cpp.uiq.component.layoutManager.TLayoutData.TQikLayoutHorizontalAlignment;
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.datamodel.adapter.ILayout;
import com.nokia.sdt.datamodel.adapter.IVisualAppearance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;

public class RowLayoutControl {
	
	static final String CONTROL_ISVISIBLE_PROPERTY = "isVisible"; //$NON-NLS-1$
	static final String TQIKLAYOUTBOOLSETTING_INHERIT = "EQikLayoutInheritBoolValue"; //$NON-NLS-1$
	static final String TQIKLAYOUTBOOLSETTING_FALSE = "EQikLayoutFalseValue"; //$NON-NLS-1$
	static final String TQIKLAYOUTBOOLSETTING_TRUE = "EQikLayoutTrueValue"; //$NON-NLS-1$
	static final String TQIKLAYOUTHORIZONTALALIGNMENT_DEFAULT = "TQikLayoutHorizontalAlignment-default";	//$NON-NLS-1$
	
	private int horizontalAlignment;	
	
	private static Point layoutRowSize;
	
	/** TODO:
	 * Text in the view should be aligned so that the baseline
	 * of the text is on the baseline of the row.
	 */
//	int baseline;
	
	/** TODO:
	 * No visible changes were seen when setting this property
	 * in our prototypes. Marking as Question for UIQ.
	 */
//	static int mirrored;
	
	private static boolean isNormalized;
	
	static RowLayoutData defaultLayoutData;
	
	RowLayoutData layoutData;
	
	List<Rectangle> rowGroup;
	
	ILayoutObject layoutObject;
	
	Point preferredSize;
	
	public static void setLayoutRowSize(Point layoutRowSize) {
		RowLayoutControl.layoutRowSize = layoutRowSize;
	}
	
	public static Point getLayoutRowSize() {
		return layoutRowSize;
	}
	
	public static boolean isNormalized() {
		return isNormalized;
	}

	public static void setNormalized(boolean isNormalized) {
		RowLayoutControl.isNormalized = isNormalized;
	}
	
	public static void setDefaultLayoutData(RowLayoutData defaultLayoutData) {
		RowLayoutControl.defaultLayoutData = defaultLayoutData;
	}
	
	public void setLayoutObject(ILayoutObject layoutObject) {
		this.layoutObject = layoutObject;
	}
	
	public void setLayoutData(RowLayoutData layoutData) {
		this.layoutData = layoutData;
	}
	
	public int getRowGroupHeight() {
		return layoutRowSize.y * rowGroup.size();
	}
	
	public boolean layoutControlWhenInvisible() {
		boolean isVisible = getBooleanPropertyValue(CONTROL_ISVISIBLE_PROPERTY);
		RowLayoutData ld = layoutData.layoutWhenInvisible.equals(TQIKLAYOUTBOOLSETTING_INHERIT) ? defaultLayoutData : layoutData;
		if (ld.layoutWhenInvisible.equals(TQIKLAYOUTBOOLSETTING_FALSE) && !isVisible)
			return false;
		else
			return true;
	}
	
	public void setSizeFromLayoutData(ILookAndFeel laf) {
		preferredSize = getPreferredSize(laf);
		if (preferredSize == null)
			preferredSize = layoutRowSize;
		
		Rectangle oldbounds = layoutObject.getBounds();
		int width = getWidthFromLayoutData();
		int height = 0;
		if (layoutControlWhenInvisible())
			height = getHeightFromLayoutData();
		
		Rectangle newbounds = new Rectangle(oldbounds.x, oldbounds.y, width, height);
		layoutObject.setBounds(newbounds);
	}
	
	public void setRowsSize(int normalizedSize) {
		int layoutObjectHeight = !isNormalized ? layoutObject.getBounds().height : normalizedSize;
		int heightRatio = layoutObjectHeight / layoutRowSize.y;
		int heightMod = layoutObjectHeight % layoutRowSize.y;
		int numberOfRows = 0;
		if ( heightRatio > 0 )
			numberOfRows = ( heightMod > 0 ) ? ++heightRatio : heightRatio;
		else
			numberOfRows = 1;
		rowGroup = new ArrayList<Rectangle>(numberOfRows);
		for (int i = 0; i < numberOfRows; i++) {
			Rectangle rowRect = new Rectangle(0, 0, layoutRowSize.x, layoutRowSize.y);
			rowGroup.add(rowRect);
		}
	}
	
	public void setRowsPosition(Point position) {
		int offset = 0;
		for (int i = 0; i < rowGroup.size(); i++) {
			Rectangle rowRect = rowGroup.get(i);
			rowRect.x = position.x;
			rowRect.y = position.y + offset;
			offset += layoutRowSize.y;
		}
	}
	
	public void layoutControl() {
		Rectangle newbounds = calculateBounds();
		layoutObject.setBounds(newbounds);
	}
	
	private Rectangle calculateBounds() {
		Rectangle firstRow = rowGroup.get(0);
		int width = calculateWidth();
		int height = calculateHeight();
		int x = calculateX(width);
		int y = calculateY(height);
		Rectangle bounds = new Rectangle(firstRow.x + x, firstRow.y + y, width, height);
		return bounds;
	}
	
	public void setHorizontalAlignment () {
		RowLayoutData horizontalAlignmentData = new RowLayoutData ();
		RowLayoutData ld = layoutData.horizontalAlignment == horizontalAlignmentData.haEnum.indexOf("EQikLayoutHAlignInherit") ? defaultLayoutData : layoutData;
		horizontalAlignment = ld.horizontalAlignment;
		horizontalAlignmentData = null;
		ld = null;
	}	
	
	private int getWidthFromLayoutData() {
		RowLayoutData ld = layoutData.minimumWidth == RowLayoutData.TQikLayoutLogicalSizes.EQikLayoutInheritMinimum ? defaultLayoutData : layoutData;
		int width = layoutObject.getBounds().width;
		setHorizontalAlignment ();
		if (horizontalAlignment == 0 || horizontalAlignment == 1) {	//0: EQikLayoutHAlignInherit, 1: EQikLayoutHAlignFill
			width = preferredSize.x;
		} else {		
			switch(ld.minimumWidth) {
			case RowLayoutData.TQikLayoutLogicalSizes.EQikLayoutUseControlMinimum:
				width = preferredSize.x;
				break;
			case RowLayoutData.TQikLayoutLogicalSizes.EQikLayoutInheritMinimum:
				width = preferredSize.x;
				break;
			case RowLayoutData.TQikLayoutLogicalSizes.EQikLayoutSmallIconSize:
				width = 18;
				break;
			case RowLayoutData.TQikLayoutLogicalSizes.EQikLayoutMediumIconSize:
				width = 40;
				break;
			case RowLayoutData.TQikLayoutLogicalSizes.EQikLayoutLargeIconSize:
				width = 64;
				break;
			case RowLayoutData.TQikLayoutLogicalSizes.EQikLayoutMediumThumbnailSize:
				width = 48;
				break;
			case RowLayoutData.TQikLayoutLogicalSizes.EQikLayoutLargeThumbnailSize:
				width = 73;
				break;
			case RowLayoutData.TQikLayoutLogicalSizes.EQikLayoutMediumPortraitThumbnailSize:
				width = 48;
				break;
			case RowLayoutData.TQikLayoutLogicalSizes.EQikLayoutLargePortraitThumbnailSize:
				width = 73;
				break;
			default:
				if (ld.minimumWidth > 0 &&  ld.minimumWidth < 2147483647 ) {
					width = ld.minimumWidth;
					break;
				} else {
					break;
				}
			}
		}
		return width;
	}
	
	private int getHeightFromLayoutData() {
		RowLayoutData ld = layoutData.minimumHeight == RowLayoutData.TQikLayoutLogicalSizes.EQikLayoutInheritMinimum ? defaultLayoutData : layoutData;
		
		int height = layoutObject.getBounds().height;
		switch(ld.minimumHeight) {
		case RowLayoutData.TQikLayoutLogicalSizes.EQikLayoutUseControlMinimum:
			height = preferredSize.y;
			break;
		case RowLayoutData.TQikLayoutLogicalSizes.EQikLayoutInheritMinimum:
			height = preferredSize.y;
			break;
		case RowLayoutData.TQikLayoutLogicalSizes.EQikLayoutSmallIconSize:
			height = 18;
			break;
		case RowLayoutData.TQikLayoutLogicalSizes.EQikLayoutMediumIconSize:
			height = 40;
			break;
		case RowLayoutData.TQikLayoutLogicalSizes.EQikLayoutLargeIconSize:
			height = 64;
			break;
		case RowLayoutData.TQikLayoutLogicalSizes.EQikLayoutMediumThumbnailSize:
			height = 48;
			break;
		case RowLayoutData.TQikLayoutLogicalSizes.EQikLayoutLargeThumbnailSize:
			height = 73;
			break;
		case RowLayoutData.TQikLayoutLogicalSizes.EQikLayoutMediumPortraitThumbnailSize:
			height = 48;
			break;
		case RowLayoutData.TQikLayoutLogicalSizes.EQikLayoutLargePortraitThumbnailSize:
			height = 73;
			break;
		default:
			if (ld.minimumHeight > 0 &&  ld.minimumHeight < 2147483647 ) {
				height = ld.minimumHeight;
				break;
			} else {
				break;
			}
		}
		return height;
	}
	
	private int calculateWidth() {
		RowLayoutData horizontalAlignmentData = new RowLayoutData ();
//		RowLayoutData ld = layoutData.horizontalAlignment == horizontalAlignmentData.haEnum.indexOf("EQikLayoutHAlignInherit") ? defaultLayoutData : layoutData;
		
		TLayoutData layoutData = new TLayoutData();
		layoutData.obtainData(layoutObject.getEObject());
		int horizontalAlignment = layoutData.horizontalAlignment.ordinal();
		if ( horizontalAlignment == horizontalAlignmentData.haEnum.indexOf("EQikLayoutHAlignInherit") ) {
			horizontalAlignment = getDefaultHorizontalAlignment ();
		}
		
		int width = layoutObject.getBounds().width;
		switch(horizontalAlignment) {
		case 0://EQikLayoutHAlignInherit
			break;
		case 1://EQikLayoutHAlignFill
			width = layoutRowSize.x - getLeftMargin() - getRightMargin();
			break;
		case 2://EQikLayoutHAlignLeft
			break;
		case 3://EQikLayoutHAlignCenter
			break;
		case 4://EQikLayoutHAlignRight
			break;
		default:
			break;
		}
		return width;
	}
	
	private int calculateHeight() {
		RowLayoutData verticalAlignmentData = new RowLayoutData ();
		RowLayoutData ld = layoutData.verticalAlignment == verticalAlignmentData.vaEnum.indexOf("EQikLayoutVAlignInherit") ? defaultLayoutData : layoutData;
		
		int height = layoutObject.getBounds().height;
		switch (ld.verticalAlignment) {
		case 0://EQikLayoutVAlignInherit
			break;
		case 1://EQikLayoutVAlignFill
			height = getRowGroupHeight() - getTopMargin() - getBottomMargin();
			break;
		case 2://EQikLayoutVAlignTop
			break;
		case 3://EQikLayoutVAlignCenter
			break;
		case 4://EQikLayoutVAlignBottom
			break;
		default:
			break;
		}
		return height;
	}
	
	private int calculateX(int width) {
		RowLayoutData horizontalAlignmentData = new RowLayoutData ();
//		RowLayoutData ld = layoutData.horizontalAlignment == horizontalAlignmentData.haEnum.indexOf("EQikLayoutHAlignInherit") ? defaultLayoutData : layoutData;
		
		int x = layoutObject.getBounds().x;
		int lm = getLeftMargin();
		int rm = getRightMargin();
		
		TLayoutData layoutData = new TLayoutData();
		layoutData.obtainData(layoutObject.getEObject());
		int horizontalAlignment = layoutData.horizontalAlignment.ordinal();
		if ( horizontalAlignment == horizontalAlignmentData.haEnum.indexOf("EQikLayoutHAlignInherit") ) {
			horizontalAlignment = getDefaultHorizontalAlignment ();
		}
		
		switch(horizontalAlignment) {
		case 0://EQikLayoutHAlignInherit
			x = ( lm + layoutRowSize.x - rm - width ) / 2;
			break;
		case 1://EQikLayoutHAlignFill
			x = lm;
			break;
		case 2://EQikLayoutHAlignLeft
			x = lm;
			break;
		case 3://EQikLayoutHAlignCenter
			x = ( lm + layoutRowSize.x - rm - width ) / 2;
			break;
		case 4://EQikLayoutHAlignRight
			x = layoutRowSize.x - rm - width;
			break;
		default:
			break;
		}
		return x;
	}
	
	private int getDefaultHorizontalAlignment () {
		RowLayoutData horizontalAlignmentData = new RowLayoutData ();
		EObject objectComponent = layoutObject.getEObject();
		if ( objectComponent == null ) {
			return horizontalAlignmentData.haEnum.indexOf("EQikLayoutHAlignInherit");		
		}
		
		String defaultHAlignment = ModelUtils.readAttribute( objectComponent, TQIKLAYOUTHORIZONTALALIGNMENT_DEFAULT );
		if (  defaultHAlignment == null ) {
			return horizontalAlignmentData.haEnum.indexOf("EQikLayoutHAlignInherit");					
		} 
		
		return horizontalAlignmentData.haEnum.indexOf(defaultHAlignment);		
	}
	
	private int calculateY(int height) {
		RowLayoutData verticalAlignmentData = new RowLayoutData ();
		RowLayoutData ld = layoutData.verticalAlignment == verticalAlignmentData.vaEnum.indexOf("EQikLayoutVAlignInherit") ? defaultLayoutData : layoutData;
		
		int y = layoutObject.getBounds().y;
		int tm = getTopMargin();
		int bm = getBottomMargin();
		switch(ld.verticalAlignment) {
		case 0://EQikLayoutVAlignInherit
			y = ( tm + getRowGroupHeight() - bm - height ) / 2;
			break;
		case 1://EQikLayoutVAlignFill
			y = tm;
			break;
		case 2://EQikLayoutVAlignTop
			y = tm;
			break;
		case 3://EQikLayoutVAlignCenter
			y = ( tm + getRowGroupHeight() - bm - height ) / 2;
			break;
		case 4://EQikLayoutVAlignBottom
			y = getRowGroupHeight() - bm - height;
			break;
		default:
			break;
		}
		return y;
	}
	
	private int getLeftMargin() {
		RowLayoutData ld = layoutData.leftMargin == RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutInheritMargin ? defaultLayoutData : layoutData;
		
		int lm = 0;
		switch(ld.leftMargin) {
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutInheritMargin:
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutContentGap:
			lm = 5;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutContentGapRight:
			lm = 3;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutContentGapLeft:
			lm = 3;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutLeftEdgeToContent:
			lm = 5;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutRightEdgeToContent:
			lm = 5;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutLeftEdgeToPopOut:
			lm = 8;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutRightEdgeToPopOut:
			lm = 8;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutRowTopMargin:
			lm = 2;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutRowBottomMargin:
			lm = 2;
			break;
		default:
			break;
		}
		return lm;
	}
	
	private int getRightMargin() {
		RowLayoutData ld = layoutData.rightMargin == RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutInheritMargin ? defaultLayoutData : layoutData;
		
		int rm = 0;
		switch(ld.rightMargin) {
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutInheritMargin:
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutContentGap:
			rm = 5;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutContentGapRight:
			rm = 3;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutContentGapLeft:
			rm = 3;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutLeftEdgeToContent:
			rm = 5;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutRightEdgeToContent:
			rm = 5;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutLeftEdgeToPopOut:
			rm = 8;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutRightEdgeToPopOut:
			rm = 8;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutRowTopMargin:
			rm = 2;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutRowBottomMargin:
			rm = 2;
			break;
		default:
			break;
		}
		return rm;
	}
	
	private int getTopMargin() {
		RowLayoutData ld = layoutData.topMargin == RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutInheritMargin ? defaultLayoutData : layoutData;
		
		int tm = 0;
		switch(ld.topMargin) {
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutInheritMargin:
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutContentGap:
			tm = 1;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutContentGapRight:
			tm = 0;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutContentGapLeft:
			tm = 0;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutLeftEdgeToContent:
			tm = 1;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutRightEdgeToContent:
			tm = 1;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutLeftEdgeToPopOut:
			tm = 3;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutRightEdgeToPopOut:
			tm = 3;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutRowTopMargin:
			tm = 0;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutRowBottomMargin:
			tm = 0;
			break;
		default:
			break;
		}
		return tm;
	}
	
	private int getBottomMargin() {
		RowLayoutData ld = layoutData.bottomMargin == RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutInheritMargin ? defaultLayoutData : layoutData;
		
		int bm = 0;
		switch(ld.bottomMargin) {
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutInheritMargin:
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutContentGap:
			bm = 2;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutContentGapRight:
			bm = 1;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutContentGapLeft:
			bm = 1;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutLeftEdgeToContent:
			bm = 2;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutRightEdgeToContent:
			bm = 2;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutLeftEdgeToPopOut:
			bm = 3;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutRightEdgeToPopOut:
			bm = 3;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutRowTopMargin:
			bm = 0;
			break;
		case RowLayoutData.TQikLayoutLogicalMargins.EQikLayoutRowBottomMargin:
			bm = 0;
			break;
		default:
			break;
		}
		return bm;
	}
	
	private Point getPreferredSize(ILookAndFeel laf) {
		EObject obj = layoutObject.getEObject();
		
		ILayout layout = (ILayout) EcoreUtil.getRegisteredAdapter(obj, ILayout.class);
    	if (layout != null)
			return layout.getPreferredSize(layoutRowSize.x, layoutRowSize.y, laf);
    	
		IVisualAppearance vaLayoutObject = Utilities.getVisualAppearance(obj);
		if (vaLayoutObject != null)
			return vaLayoutObject.getPreferredSize(layoutRowSize.x, layoutRowSize.y, laf);
		
		return null;
	}
	
	private boolean getBooleanPropertyValue(Object id) {
		boolean result = false;
		IPropertySource ps = Utilities.getPropertySource(layoutObject.getEObject());
		if (ps != null && ps instanceof IPropertySource) {
			Object po = ps.getPropertyValue(id);
			if (po != null && po instanceof Boolean)
				result = ((Boolean)po).booleanValue();
			else if (po == null)
				result = false;
		}
		else if (ps == null)
			result = false;
		
		return result;
	}
}
