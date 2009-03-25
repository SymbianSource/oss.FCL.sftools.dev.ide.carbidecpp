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
package com.nokia.sdt.component.symbian.actions;

import org.eclipse.emf.ecore.EObject;

public class MoveDownActionDelegate extends VerticalMoveActionDelegate {

	protected boolean isApplicableForDirection(EObject object, EObject[] children) {
		return !isTemporaryEditor(object) && !object.equals(children[children.length - 1]);
	}

	protected int getNewInsertPosition(EObject object, EObject[] children) {
		for (int i = 0; i < children.length; i++) {
			EObject temp = children[i];
			if (temp.equals(object)) {
				return i + 2; // this will be adjusted in the command when objects are moving down
			}
		}
		
		return children.length - 1;
	}

}
