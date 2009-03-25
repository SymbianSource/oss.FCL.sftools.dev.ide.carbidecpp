/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.cpp.internal.api.utils.core.ListenerList;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

public class DesignerDataModelNotifier {
	
	private ListenerList<IDesignerDataModelListener> listeners;
	private static DesignerDataModelNotifier instance;
	
	private DesignerDataModelNotifier() {
	}
	
	public static DesignerDataModelNotifier instance() {
		if (instance == null)
			instance = new DesignerDataModelNotifier();
		return instance;
	}
	
	public synchronized void addListener(IDesignerDataModelListener listener) {
		if (listeners == null)
			listeners = new ListenerList<IDesignerDataModelListener>();
		
		listeners.add(listener);
	}

	public synchronized void removeListener(IDesignerDataModelListener listener) {
		if (listeners != null) {
			listeners.remove(listener);
		}
	}
	
	public synchronized void fireDataModelChanged(IDesignerDataModelSpecifier modelSpecifier) {
		if (listeners != null) {
			for (IDesignerDataModelListener listener : listeners) {
				listener.dataModelChanged(modelSpecifier);
			}
		}
	}

	public synchronized void fireDataModelInitialized(IDesignerDataModel model) {
		if (listeners != null) {
			for (IDesignerDataModelListener listener : listeners) {
				listener.dataModelInitialized(model);
			}
		}
	}
	
}
