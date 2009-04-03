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
package com.nokia.carbide.cpp.internal.project.ui.views;

/**
 * Obects in the SPN view that want to have text displayed in the
 * status line area (bottom of the workbench) other than the standard
 * #getLabel should impement this interface.
 *
 */
public interface ISPNStatusLineDecorator {

	public String getStatusLineText();
}
