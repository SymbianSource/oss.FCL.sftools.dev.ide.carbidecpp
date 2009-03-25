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

package com.nokia.sdt.datamodel.util;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import org.eclipse.core.runtime.IPath;

public class MessageLocators {

    private MessageLocators() {
        
    }
    
    static public MessageLocation getComponentLocation(IComponent component) {
        return component.createMessageLocation();
    }

    static public MessageLocation getComponentOrInstanceLocation(IComponentInstance instance) {
        IComponent component = instance.getComponent();
        if (component == null)
            return getInstanceLocation(instance);
        MessageLocation loc = component.createMessageLocation();
        IPath wsPath = loc.getPath();
        if (wsPath != null)
            return loc;
        else
            return getInstanceLocation(instance);
    }

    static public MessageLocation getInstanceLocation(IComponentInstance instance) {
        IDesignerDataModelSpecifier dmSpec = instance.getDesignerDataModel().getModelSpecifier();
        if (dmSpec != null)
            return dmSpec.createMessageLocation();
        else if (instance.getComponent() != null)
            return instance.getComponent().createMessageLocation();
        else
            return null;
    }
    
    static public MessageLocation getWorkspaceRelativeLocation(MessageLocation loc) {
        IPath newPath = FileUtils.convertToWorkspaceLocation(loc.getLocation());
        if (newPath == loc.getPath())
            return loc;
        else if (newPath != null) {
            loc.setLocation(newPath);
            return loc;
        } else
            return loc;
    }

    
}
