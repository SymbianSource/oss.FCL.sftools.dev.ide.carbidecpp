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
public class LayoutObjectEditPartFactory implements EditPartFactory {

	/* (non-Javadoc)
	 * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart, java.lang.Object)
	 */
	public EditPart createEditPart(EditPart context, Object object) {
		// get EditPart for model object
		EditPart part = null;
		if (context instanceof AbstractContentsEditPart)
			part = new RootContainerLayoutEditPart();
		else
			part = getPartForModelObject(object);
		
		if (part != null)
			// store model element in EditPart
			part.setModel(object);
		return part;
	}

	/**
	 * Maps a model object to an EditPart. 
	 */
	private EditPart getPartForModelObject(Object object) {
		if (object instanceof ContentsObject) {
			ContentsLayoutEditPart editPart = new ContentsLayoutEditPart();
			// for the contents, we need some coupling between the pseudo-model and the controller
			((ContentsObject) object).setEditPart(editPart);
			return editPart;
		}
		else {
			if (Adapters.isTransientObject((EObject) object))
				return new TransientObjectEditPart();
			else if (Adapters.getLayoutContainer((EObject) object) != null)
				return new LayoutContainerEditPart();
			else if (Adapters.getLayoutObject((EObject) object) != null)
				return new LayoutObjectEditPart();
		}

		return null;
	}

}
