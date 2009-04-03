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

package com.nokia.sdt.component.symbian.sourcemapping;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.MessageLocators;
import com.nokia.sdt.utils.MessageReporting;

public class ComponentMessageUtils {
	public static void emit(IComponentInstance instance, int severity, String msgKey, String[] args) {
		MessageReporting.emitMessage(Messages.create(
    			severity,
    			MessageLocators.getComponentOrInstanceLocation(instance),
    			msgKey, args));
	}

	public static void emit(IComponent component, int severity, String msgKey, String[] args) {
		MessageReporting.emitMessage(Messages.create(
    			severity,
    			component.createMessageLocation(),
    			msgKey, args));
	}
}
