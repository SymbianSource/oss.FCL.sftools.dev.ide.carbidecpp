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

package com.nokia.sdt.datamodel;

import com.nokia.sdt.uimodel.UIModelPlugin;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import java.util.Collection;

public abstract class ModelMessages {
	
	public static final String modelMarkerType = UIModelPlugin.PLUGIN_ID + ".modelMarker";

	public static Collection<IModelMessage> validateAndUpdateMarkers(IDesignerDataModel model) {
		Collection<IModelMessage> validationMessages = model.validate();
		setModelMessageMarkers(model.getModelSpecifier(), validationMessages);
		return validationMessages;
	}
	
	public static void setModelMessageMarkers(IDesignerDataModelSpecifier modelSpecifier, 
			Collection<IModelMessage> messages) {
		clearModelMessageMarkers(modelSpecifier);
		if (messages != null) {
			for (IModelMessage modelMessage : messages) {
				addModelMessageMarker(modelSpecifier, modelMessage);
			}
		}
	}
	
	public static void clearModelMessageMarkers(IDesignerDataModelSpecifier modelSpecifier) {
		try {
			IResource primaryResource = modelSpecifier.getPrimaryResource();
			primaryResource.deleteMarkers(modelMarkerType, false, IResource.DEPTH_ZERO);
		} catch (CoreException x) {
			UIModelPlugin.log(x);
		}
	}
	
	public static void addModelMessageMarker(IDesignerDataModelSpecifier modelSpecifier,
			IModelMessage modelMessage) {
		modelMessage.createMarker(modelSpecifier.getPrimaryResource(), modelMarkerType);
	}
}
