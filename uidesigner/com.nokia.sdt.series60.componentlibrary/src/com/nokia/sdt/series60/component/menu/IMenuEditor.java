/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.series60.component.menu;

import com.nokia.sdt.editor.IDesignerEditor;


public interface IMenuEditor {

	public static String MENUBAR_ID = "com.nokia.sdt.series60.MenuBar"; //$NON-NLS-1$
	public static String MENUPANE_ID = "com.nokia.sdt.series60.MenuPane"; //$NON-NLS-1$
	public static String MENUITEM_ID = "com.nokia.sdt.series60.MenuItem"; //$NON-NLS-1$
	public static String MENUITEMBASE_ID = "com.nokia.sdt.series60.MenuItemBase"; //$NON-NLS-1$
	
	public static String LABEL_PROPERTY_ID = "text"; //$NON-NLS-1$
	
	void setEditor(IDesignerEditor editor);
	
	IDesignerEditor getEditor();
	
	boolean isSubMenu();
	
	boolean isTemporaryObject();
}
