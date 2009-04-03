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
package com.nokia.sdt.symbian;

/**
 * Attributes common to S60 and UIQ components
 *
 */
public interface SymbianComponentAttributes {

	/**
	 * An attribute to define the control type in RSS (EEikCt... or EAknCt...).
	 */
	public static final String CONTROL_TYPE = "rss-control-type-enum"; //$NON-NLS-1$

	/**
	 * Tells whether the component is a design reference.  If so, it is expected to
	 * publish the properties:
	 * <p>
	 * filePath:string<br>
	 * baseName:string<br>
	 * </p>
	 */
	public static final String IS_DESIGN_REFERENCE = "is-design-reference"; //$NON-NLS-1$

}