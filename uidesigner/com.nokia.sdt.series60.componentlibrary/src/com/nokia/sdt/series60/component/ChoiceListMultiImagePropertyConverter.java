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
package com.nokia.sdt.series60.component;

import com.nokia.sdt.symbian.images.SymbianMultiImagePropertyConverter;

/**
 * The multi-image property converter for CAknChoiceList, which has four images
 * sharing one MBM/MIF file.
 *
 */
public class ChoiceListMultiImagePropertyConverter extends
		SymbianMultiImagePropertyConverter {

	public ChoiceListMultiImagePropertyConverter() {
		super("bmpfile");
		
		addAbstractImageId("normal", "bmpid", "bmpmask");
		addAbstractImageId("dimmed", "bmpidDimmed", "bmpmaskDimmed");
		addAbstractImageId("pressed", "bmpidPressed", "bmpmaskPressed");
		addAbstractImageId("hover", "bmpidHover", "bmpmaskHover");
	}

}
