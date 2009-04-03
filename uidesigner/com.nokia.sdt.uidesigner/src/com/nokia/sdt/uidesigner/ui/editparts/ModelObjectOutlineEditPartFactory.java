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


package com.nokia.sdt.uidesigner.ui.editparts;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.nokia.sdt.uidesigner.ui.ContentsObject;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;

/**
 * 
 *
 */
public class ModelObjectOutlineEditPartFactory implements EditPartFactory {

	/* (non-Javadoc)
	 * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart, java.lang.Object)
	 */
	public EditPart createEditPart(EditPart context, Object object) {
		// get EditPart for model object
		EditPart part = getPartForModelObject(object);
		
		return part;
	}

	private EditPart getPartForModelObject(Object object) {
		if (object instanceof ContentsObject) {
			return new ContentsOutlineEditPart(object);
		}
		else if (Adapters.getLayoutContainer((EObject) object) != null
				|| Adapters.getContainer((EObject) object) != null)
			return new ModelContainerOutlineEditPart(object);

		return new ModelObjectOutlineEditPart(object);
	}
}
