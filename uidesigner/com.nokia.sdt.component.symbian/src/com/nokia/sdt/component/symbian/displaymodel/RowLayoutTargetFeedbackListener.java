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


package com.nokia.sdt.component.symbian.displaymodel;

import org.eclipse.emf.ecore.EObject;


public class RowLayoutTargetFeedbackListener extends AbstractTargetFeedbackListener {
	
	private final boolean vertical;

	public RowLayoutTargetFeedbackListener(EObject container, boolean vertical) {
		super(container);
		this.vertical = vertical;
	}

	public void endTargetFeedback() {
		// nothing to do
	}

	@Override
	protected ITargetFeedbackFigure createTargetFeedbackFigure() {
		return new RowLayoutTargetFeedbackFigure(vertical);
	}
}