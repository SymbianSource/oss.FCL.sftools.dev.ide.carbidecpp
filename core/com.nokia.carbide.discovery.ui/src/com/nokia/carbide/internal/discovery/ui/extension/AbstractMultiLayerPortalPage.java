/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.internal.discovery.ui.extension;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;

import com.nokia.carbide.internal.discovery.ui.editor.PortalEditor;
import com.nokia.carbide.internal.discovery.ui.editor.StackComposite;
import com.nokia.carbide.internal.discovery.ui.extension.IPortalExtension;

/**
 * An abstract class implementing IPortalPage allowing a single portal page
 * to contain multiple layers controlled by a command bar
 */
public abstract class AbstractMultiLayerPortalPage implements IPortalExtension {

	private StackComposite stackComposite;

	@Override
	public Control createControl(Composite parent, IEditorPart part) {
		PortalEditor portalEditor = (PortalEditor) part.getAdapter(PortalEditor.class);
		stackComposite = new StackComposite(parent, portalEditor.getBackgroundParent());
		return stackComposite;
	}
	
	

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public IActionBar[] createCommandBars(IEditorPart part, IActionUIUpdater updater) {
		// TODO Auto-generated method stub
		return null;
	}

}
