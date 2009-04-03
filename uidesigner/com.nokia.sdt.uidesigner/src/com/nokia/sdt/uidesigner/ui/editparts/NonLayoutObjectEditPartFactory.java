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

import com.nokia.sdt.displaymodel.adapter.IDisplayObject;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.ContentsObject;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

 /**
  * 
  *
  */
 public class NonLayoutObjectEditPartFactory implements EditPartFactory {

 	/* (non-Javadoc)
 	 * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart, java.lang.Object)
 	 */
 	public EditPart createEditPart(EditPart context, Object object) {
		EditPart part = null;
		if (context instanceof IEditPartEditorProvider) {
			IDesignerEditor editor = ((IEditPartEditorProvider) context).getEditor();
			if (object.equals(editor.getNonLayoutRoot()))
				part = new RootContainerNonLayoutEditPart();
		}

		if (part == null)
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
			ContentsNonLayoutEditPart editPart = new ContentsNonLayoutEditPart();
			return editPart;
		}
		else if (object instanceof EObject) {
			EObject eobject = (EObject) object;
			IDisplayObject displayObject = Adapters.getDisplayObject(eobject); 
			if (displayObject.isNonLayoutObject())
				return new NonLayoutObjectEditPart();
 		}
		
		return null;
 	}

 }
