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

package com.nokia.carbide.cpp.internal.news.reader.editor;

/**
 * Interface for the Carbide.c++ news editor.
 *
 */
public interface INewsEditor {

	/**
	 * Retrieve the news page object associated with the editor.
	 * @return news page object associated with the editor
	 */
	NewsPage getNewsPage();

}
