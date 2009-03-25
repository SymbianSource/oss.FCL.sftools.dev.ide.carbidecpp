/*******************************************************************************
 * Copyright (C) 2006 Nokia Corporation. All rights reserved.
 * Made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.nokia.sdt.uidesigner.derived;

import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;

public interface IExtendedFlyoutPreferences extends FlyoutPreferences {
	
	boolean isInfoWidgetExpanded();
	
	void setInfoWidgetExpanded(boolean state);
	
}