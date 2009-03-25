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

package com.nokia.carbide.cpp.epoc.engine.model.bsf;

import com.nokia.carbide.cpp.epoc.engine.model.ETristateFlag;
import com.nokia.carbide.cpp.epoc.engine.model.IView;

import java.util.Map;

/**
 * A view onto BSF contents.  This is a parse over a single BSF file,
 * and does not provide any direct information about referenced or customized
 * platforms.
 * <p>
 * Note: this view cannot be rewritten (yet).
 * 
 * 
 */
public interface IBSFView extends IView<IBSFOwnedModel> {
	
	/** Symbian-defined standard customization option flag */
	static final String THUMB_OPTIONS = "THUMB_OPTIONS"; //$NON-NLS-1$
	/** Symbian-defined standard customization option flag */
	static final String ARM_OPTIONS = "ARM_OPTIONS"; //$NON-NLS-1$
	/** Symbian-defined standard customization option flag */
	static final String KERNEL_OPTIONS = "KERNEL_OPTIONS"; //$NON-NLS-1$
	/** Symbian-defined standard customization option flag */
	static final String COMMON_OPTIONS = "COMMON_OPTIONS"; //$NON-NLS-1$
	/** Symbian-defined standard customization option flag */
	static final String INVARIANT_OPTIONS = "INVARIANT_OPTIONS"; //$NON-NLS-1$
	
	/** Get the name of BSF as a platform. */
	String getName();
	
	/** Set the CUSTOMIZES platform.
	 * @param platform may not be null, but may be "" */
	void setCustomizes(String platform);
	
	/** Get the CUSTOMIZES platform. 
	 * @return platform this customizes; never null, but may be the empty string if BSF is invalid.  */
	String getCustomizes();
	
	/** Tell whether the BSF is compiled with its parent (COMPILEWITHPARENT,
	 * COMPILEALONE, or unspecified) */
	ETristateFlag getCompileWithParent();
	
	/** Set the COMPILEWITHPARENT disposition. */
	void setCompileWithParent(ETristateFlag flag);

	/** Tell whether the BSF is a variant.
	 * <p> 
	 * This is true if the BSF specifies VARIANT, not VIRTUALVARIANT.
	 */
	boolean isVariant();
	
	/** Tell whether the BSF is a variant.
	 */
	void setVariant(boolean flag);
	
	/** Tell whether the BSF is a virtual variant.
	 * This is true if the BSF specifies VIRTUALVARIANT. */
	boolean isVirtualVariant();
	
	/** Tell whether the BSF is a virtual variant.
	 * @param flag true: is virtual variant
	 */
	void setVirtualVariant(boolean flag);
	
	/** Get the map of customization options, which is a map of
	 * the (capitalized) first token on the line to the remainder of the line.
	 * @return map never null */
	Map<String, String> getCustomizationOptions();

	/** Replace the map of customization options. 
	 * @param map may not be null*/
	void setCustomizationOptions(Map<String, String> map);

}
