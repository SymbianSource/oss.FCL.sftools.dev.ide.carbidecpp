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

package com.nokia.sdt.component.symbian.targetFeedback;

import com.nokia.sdt.component.symbian.implementations.ICodeImplAdapter;
import com.nokia.sdt.displaymodel.adapter.ITargetFeedbackListener;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.requests.DropRequest;

public class TargetFeedbackListenerAdapterCode extends AdapterImpl implements ITargetFeedbackListener, ICodeImplAdapter {
	ITargetFeedbackListener impl = null;
	
	public TargetFeedbackListenerAdapterCode() {
	}
	
	public void init(Object impl, EObject instance) {
		Check.checkArg(instance);
		Check.checkContract(impl instanceof ITargetFeedbackListener);
		
		this.impl = (ITargetFeedbackListener) impl;
		setTarget(instance);
	}
	
	public IFigure beginTargetFeedback(GraphicalEditPart editPart) {
		return impl.beginTargetFeedback(editPart);
	}

	public void endTargetFeedback() {
		impl.endTargetFeedback();
	}

	public void mouseMoved(DropRequest dropRequest) {
		impl.mouseMoved(dropRequest);
	}

	public EObject getEObject() {
		return (EObject) getTarget();
	}

	public boolean isAdapterForType(Object type) {
		return type.equals(ITargetFeedbackListener.class);
	}

	public Object getImpl() {
		return impl;
	}
}
