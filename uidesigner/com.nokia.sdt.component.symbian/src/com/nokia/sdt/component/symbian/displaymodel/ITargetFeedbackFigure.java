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

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;

import java.util.List;

public interface ITargetFeedbackFigure extends IFigure {
	
	void setTargetEditPart(GraphicalEditPart editPart);
	
	void setMouseLocation(Point point);
	
	void setSourceEditParts(List editParts);
	
	boolean isExecutable();
	
	EditPart getEditPartAfterInsertion();
}