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
package com.nokia.carbide.cpp.epoc.engine.model;

/** 
 * Define the semantics of header generation for files whose
 * build step creates an optional header (usually with enums or #defines). 
 */
public enum EGeneratedHeaderFlags {
	/** No header file */
	NoHeader,
	/** Emit header file to system includes */
	Header,
	/** Emit header file -- and no target file -- to system includes */
	HeaderOnly
}