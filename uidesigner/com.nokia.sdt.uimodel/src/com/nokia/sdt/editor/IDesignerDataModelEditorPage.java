/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.editor;

/**
 * An optional interface for pages in an IDesignerDataModelEditor
 */
public interface IDesignerDataModelEditorPage {

	/**
	 * Called when the editor's data model is reloaded
	 */
	void reload(); 
	
}
