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
package com.nokia.sdt.uidesigner.events;

import com.nokia.sdt.uidesigner.ui.UIDesignerPlugin;
import com.nokia.sdt.uidesigner.ui.utils.Messages;

/**
 * Action for toggling whether events are display hierarchically
 * by category or in a flat list.
 *
 */
public class CategoriesAction extends EventPageAction{

	public CategoriesAction(EventPage page) {
		super(page);
		setText(Messages.getString("CategoriesAction.0"));  //$NON-NLS-1$
		setToolTipText(Messages.getString("CategoriesAction.1"));  //$NON-NLS-1$
		setImageDescriptor(UIDesignerPlugin.getImageDescriptor("icons/tree_mode.gif")); //$NON-NLS-1$
		setChecked(true);
	}

	public void run() {
		IEventPage page = getPage();
		page.deactivateCellEditor();
		boolean show = isChecked();
		page.showCategories(show);
	}
}
