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


package com.nokia.sdt.uidesigner.ui.command;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;
import com.nokia.sdt.uidesigner.ui.utils.Strings;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;

/**
 * 
 *
 */
public class ChangeBoundsCommand extends Command {
	/** Stores the new size and location. */
	private Rectangle newBounds;
	/** Stores the old size and location. */
	private Rectangle oldBounds;
	/** object to manipulate. */
	private String objectName;
	private IDesignerDataModel dataModel;

	public ChangeBoundsCommand(EObject object, Rectangle rectangle) {
		Check.checkArg(object);
		Check.checkArg(rectangle);
		newBounds = rectangle.getCopy();
		IComponentInstance componentInstance = Adapters.getComponentInstance(object);
		objectName = componentInstance.getName();
		dataModel = componentInstance.getDesignerDataModel();
		
		setLabel(Adapters.getComponentInstance(object).getName()
				+ Strings.getString("ChangeBoundsCommand.ChangeBoundsLabel")); //$NON-NLS-1$
	}
	
	private ILayoutObject getLayoutObject() {
		EObject object = dataModel.findByNameProperty(objectName);
		Check.checkState(object != null);
			
		return Adapters.getLayoutObject(object);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		oldBounds = new Rectangle(getLayoutObject().getBounds());
		redo();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {
		org.eclipse.swt.graphics.Rectangle r = 
			new org.eclipse.swt.graphics.Rectangle(
					newBounds.x, newBounds.y, newBounds.width, newBounds.height);
		getLayoutObject().setBounds(r);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		org.eclipse.swt.graphics.Rectangle r = 
			new org.eclipse.swt.graphics.Rectangle(
					oldBounds.x, oldBounds.y, oldBounds.width, oldBounds.height);
		getLayoutObject().setBounds(r);
	}
}
