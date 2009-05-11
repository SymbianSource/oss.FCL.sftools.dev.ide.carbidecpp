/*
* Copyright (c) 2007-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.model.sbv;

import com.nokia.carbide.cpp.epoc.engine.model.ETristateFlag;
import com.nokia.carbide.cpp.epoc.engine.model.IView;

import java.util.Map;

/**
 * A view onto .VAR (Symbian Binary Variation) contents.  This is a parse over a single .VAR file.
 * <p>
 * Note: this view cannot be rewritten.
 * 
 * 
 */
public interface ISBVView extends IView<ISBVOwnedModel> {
	
	/** Get the name of SBV as a platform. */
	String getName();
	
	/** Set the CUSTOMIZES platform.
	 * @param platform may not be null, but may be "" */
	void setCustomizes(String platform);
	
	/** Get the CUSTOMIZES platform. 
	 * @return platform this customizes; never null, but may be the empty string if .var is invalid.  */
	String getCustomizes();
	
	/** Tell whether the .var is compiled with its parent (COMPILEWITHPARENT,
	 * COMPILEALONE, or unspecified) */
	ETristateFlag getCompileWithParent();
	
	/** Set the COMPILEWITHPARENT disposition. */
	void setCompileWithParent(ETristateFlag flag);

	/** Get the map of customization options, which is a map of
	 * the (capitalized) first token on the line to the remainder of the line.
	 * @return map never null */
	Map<String, String> getCustomizationOptions();

	/** Replace the map of customization options. 
	 * @param map may not be null*/
	void setCustomizationOptions(Map<String, String> map);

}
