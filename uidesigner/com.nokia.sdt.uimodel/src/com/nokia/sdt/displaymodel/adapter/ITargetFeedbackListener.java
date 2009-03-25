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


package com.nokia.sdt.displaymodel.adapter;

import com.nokia.sdt.datamodel.adapter.IModelAdapter;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.DropRequest;

/**
 * A listener for target feedback events
 */
public interface ITargetFeedbackListener extends IModelAdapter {
	
	static final String EDIT_PART_AFTER_INSERTION = "after edit part";
	static final String CHAINED_COMMAND = "command";
	
	/**
	 * Return the figure to be used as visual feedback or null for only default feedback.
	 * @param editPart {@link GraphicalEditPart}
	 * @return <code>IFigure</code> used as feedback
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalEditPolicy#addFeedback(IFigure figure)
	 */
	IFigure beginTargetFeedback(GraphicalEditPart editPart);
	
	/**
	 * DropRequest objects contain a mouse location.<br>
	 * All DropRequest objects may be cast to Request to add extended data values 
	 * and examine the type (REQ_CREATE, REQ_ADD, or REQ_MOVE).<br>
	 * Some DropRequest may be cast to a GroupRequest, to get source EditPart objects.<br>
	 * To insert objects before some other EditPart, add that EditPart to the extended data 
	 * with AFTER_EDIT_PART as the key, otherwise or if null, always added at the end.
	 * Add an additional command to the request's extended data, using COMMAND as the key.
	 * Adding {@value UnexecutableCommand#INSTANCE} will cause the drop to fail.<br>
	 * Ensure the figure is representing the correct feedback for the request.
	 * @param request DropRequest
	 * 
	 * @see org.eclipse.gef.Request#getType()
	 * @see org.eclipse.gef.requests.DropRequest#getLocation()
	 * @see org.eclipse.gef.Request#getExtendedData()
	 * @see org.eclipse.gef.EditPolicy#showTargetFeedback(Request)
	 * @see org.eclipse.gef.RequestConstants
	 */
	void mouseMoved(DropRequest dropRequest);
	
	/**
	 * Notification that the feedback is ending.
	 */
	void endTargetFeedback();
}
