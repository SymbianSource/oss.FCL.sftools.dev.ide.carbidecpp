/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.api.cpp.epoc.engine.image;

import com.nokia.carbide.internal.cpp.epoc.engine.image.*;

/**
 * Factory for converters from Carbide 1.x - .mbmdef, .mifdef, and .aifdef files.
 *
 */
public abstract class LegacyImageFileConverterFactory {

	public static IMbmMifDefFileConverter createMbmdefConverter() {
		return new MbmdefFileConverter();
	}
	
	public static IMbmMifDefFileConverter createMifdefConverter() {
		return new MifdefFileConverter();
	}
	
	public static IAifDefFileConverter createAifdefConverter() {
		return new AifdefFileConverter();
	}

}
