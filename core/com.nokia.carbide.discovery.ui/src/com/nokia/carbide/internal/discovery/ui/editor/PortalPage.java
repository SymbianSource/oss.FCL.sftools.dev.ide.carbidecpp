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
package com.nokia.carbide.internal.discovery.ui.editor;

import org.eclipse.swt.widgets.Control;

import com.nokia.carbide.internal.discovery.ui.extension.IPortalExtension;


public class PortalPage {
	
	private IPortalExtension portalExtension;
	private String pageId;
	private Control control;
	private boolean initialized;
	
	public PortalPage(IPortalExtension portalExtension, String pageId) {
		this.portalExtension = portalExtension;
		this.pageId = pageId;
	}

	public IPortalExtension getPortalExtension() {
		return portalExtension;
	}
	
	public String getPageId() {
		return pageId;
	}
	
	public void setControl(Control control) {
		this.control = control;
	}
	
	public Control getControl() {
		return control;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}
	
	public boolean isInitialized() {
		return initialized;
	}
	
}
