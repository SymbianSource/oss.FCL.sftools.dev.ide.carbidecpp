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

package com.nokia.sdt.datamodel.util;

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.uimodel.Messages;
import com.nokia.sdt.uimodel.UIModelPlugin;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import java.text.MessageFormat;

/**
 * This is the result of a NodePath#walk() call.
 * It represents either success (with an instance and result) or
 * failure (with a message key and IStatus object).
 * 
 *
 */
public class NodePathLookupResult {
    NodePathLookupResult(IComponentInstance instance, IPropertySource properties, IPropertyDescriptor descriptor, String propertyName, Object result) {
        this.instance = instance;
        this.properties = properties;
        this.descriptor = descriptor;
        this.propertyName = propertyName;
        this.result = result;
        this.key = null;
        this.status = null;
    }
    static IStatus makeStatus(String msgKey, Object[] args) {
        return Logging.newStatus(UIModelPlugin.getDefault(), IStatus.ERROR, 
                MessageFormat.format(Messages.getString(msgKey), args));        
    }
    NodePathLookupResult(String msgKey, String[] args) {
        this.instance = null;
        this.result = null;
        this.descriptor = null;
        this.propertyName = null;
        this.key = msgKey;
        this.status = makeStatus(msgKey, args);
    }
    /** instance owning property */
    public IComponentInstance instance;
    /** property source owning value */
    public IPropertySource properties;
    /** result of the lookup */
    public Object result;
    /** message key, or null */
    public String key;
    /** status message if error, or null */
    public IStatus status;
    /** descriptor for property */
    public IPropertyDescriptor descriptor;
    /** name of property */
    public String propertyName;
}