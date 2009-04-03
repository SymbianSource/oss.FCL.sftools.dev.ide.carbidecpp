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

import java.util.*;

public class RowLayoutData {
	
	public static class TQikLayoutLogicalSizes {
		public static final int EQikLayoutUseControlMinimum = -1;
		public static final int EQikLayoutInheritMinimum= -2;
		public static final int EQikLayoutSmallIconSize = -3;
		public static final int EQikLayoutMediumIconSize = -4;
		public static final int EQikLayoutLargeIconSize = -5;
		public static final int EQikLayoutMediumThumbnailSize = -6;
		public static final int EQikLayoutLargeThumbnailSize = -7;
		public static final int EQikLayoutMediumPortraitThumbnailSize = -8;
		public static final int EQikLayoutLargePortraitThumbnailSize = -9;
	}

	public static class TQikLayoutLogicalMargins {
		public static final int EQikLayoutInheritMargin = -1;
		public static final int EQikLayoutContentGap = -2;
		public static final int EQikLayoutContentGapRight = -3;
		public static final int EQikLayoutContentGapLeft = -4;
		public static final int EQikLayoutLeftEdgeToContent = -5;
		public static final int EQikLayoutRightEdgeToContent = -6;
		public static final int EQikLayoutLeftEdgeToPopOut = -7;
		public static final int EQikLayoutRightEdgeToPopOut = -8;
		public static final int EQikLayoutRowTopMargin = -9;
		public static final int EQikLayoutRowBottomMargin = -10;
	}
	
	String layoutWhenInvisible;
	int horizontalAlignment;
	int verticalAlignment;
	int minimumWidth;
	int minimumHeight;
	int leftMargin;
	int rightMargin;
	int topMargin;
	int bottomMargin;
	int verticalExcessGrabWeight;

	public ArrayList haEnum = new ArrayList();
	public ArrayList vaEnum = new ArrayList();

	public RowLayoutData() {
		fillTQikLayoutHorizontalAlignment();
		fillTQikLayoutVerticalAlignment();
	}

	public void fillTQikLayoutHorizontalAlignment() {
		haEnum.add("EQikLayoutHAlignInherit");
		haEnum.add("EQikLayoutHAlignFill");
		haEnum.add("EQikLayoutHAlignLeft");
		haEnum.add("EQikLayoutHAlignCenter");
		haEnum.add("EQikLayoutHAlignRight");
	}
	
	public void fillTQikLayoutVerticalAlignment() {
		vaEnum.add("EQikLayoutVAlignInherit");
		vaEnum.add("EQikLayoutVAlignFill");
		vaEnum.add("EQikLayoutVAlignTop");
		vaEnum.add("EQikLayoutVAlignCenter");
		vaEnum.add("EQikLayoutVAlignBottom");
	}	
}
