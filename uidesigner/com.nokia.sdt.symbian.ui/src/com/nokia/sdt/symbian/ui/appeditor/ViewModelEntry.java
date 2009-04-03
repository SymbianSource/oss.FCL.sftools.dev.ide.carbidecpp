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
/**
 * 
 */
package com.nokia.sdt.symbian.ui.appeditor;

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.symbian.ui.noexport.Messages;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;

public class ViewModelEntry implements IAdaptable {
		
	final boolean isAvkonView;
	final IComponentInstance viewReference;
	final String containerName;
	String relativePath;
	IDesignerDataModelSpecifier specifier;
	String mainComponentFriendlyName;
			
	public ViewModelEntry(boolean isAvkonView, IComponentInstance viewReference, String relativePath, String containerName) {
		this.isAvkonView = isAvkonView;
		this.viewReference = viewReference;
		this.relativePath = relativePath;
		this.containerName = containerName;
	}
	
	public IDesignerDataModelSpecifier getSpecifier() {
		return specifier;
	}
	
	public IComponentInstance getViewReference() {
		return viewReference;
	}
	
	public boolean isAvkonView() {
		return isAvkonView;
	}
	
	public Object getAdapter(Class adapter) {
		Object result = null;
		if (adapter.isInstance(this)) {
			result = this;
		}
		else if (specifier != null) {
			// expose IDesignerDataModelSpecifier + it's supported adapters
			result = specifier.getAdapter(adapter);
		}
		else {
			result = Platform.getAdapterManager().getAdapter(this, adapter);
		}
		return result;
	}

	public String toString() {
		String result = null;
		if (specifier != null) {
			result = specifier.getDisplayName();
		}
		else if (relativePath != null) {
			result = relativePath;
		}
		else {
			result = Messages.getString("ViewModelEntry.unknownEntryDefaultText"); //$NON-NLS-1$
		}
		return result;
	}
}