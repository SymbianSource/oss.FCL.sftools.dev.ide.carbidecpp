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


package com.nokia.sdt.uidesigner.ui.actions;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPart;

import com.nokia.cpp.internal.api.utils.core.Check;

public class NudgeAction extends SelectionAction {
	
	private static final int NUDGE_DELTA = 1;
	private static final int BIG_DELTA = 10;
	
	private static final String BASE_ID = "com.nokia.sdt.uidesigner.actions.Nudge."; //$NON-NLS-1$
	private static final String BIG_ID_SUFFIX = "_big"; //$NON-NLS-1$
	public static final String UP_ID = BASE_ID + "up"; //$NON-NLS-1$
	public static final String DOWN_ID = BASE_ID + "down"; //$NON-NLS-1$
	public static final String LEFT_ID = BASE_ID + "left"; //$NON-NLS-1$
	public static final String RIGHT_ID = BASE_ID + "right"; //$NON-NLS-1$
	public static final String UP_BIG_ID = UP_ID + BIG_ID_SUFFIX;
	public static final String DOWN_BIG_ID = DOWN_ID + BIG_ID_SUFFIX;
	public static final String LEFT_BIG_ID = LEFT_ID + BIG_ID_SUFFIX;
	public static final String RIGHT_BIG_ID = RIGHT_ID + BIG_ID_SUFFIX;
	
	private int direction;
	private boolean big;

	/**
	 * @param part the IWorkbenchPart
	 * @param direction can be one of: SWT.UP, SWT.DOWN, SWT.LEFT, or SWT.RIGHT
	 */
	public NudgeAction(IWorkbenchPart part, int direction, boolean big) {
		super(part);
		Check.checkArg((direction == SWT.UP) ||	(direction == SWT.DOWN) ||
					(direction == SWT.LEFT) || (direction == SWT.RIGHT));
		this.direction = direction;
		this.big = big;
		switch (direction) {
			case SWT.UP:
				setId(big ? UP_BIG_ID : UP_ID);
				break;
			case SWT.DOWN:
				setId(big ? DOWN_BIG_ID : DOWN_ID);
				break;
			case SWT.LEFT:
				setId(big ? LEFT_BIG_ID : LEFT_ID);
				break;
			case SWT.RIGHT:
				setId(big ? RIGHT_BIG_ID : RIGHT_ID);
				break;
		}
	}

	private Command createNudgeCommand() {
		ChangeBoundsRequest request = new ChangeBoundsRequest(RequestConstants.REQ_RESIZE);
		request.setConstrainedMove(true);
		int delta = scale(big ? BIG_DELTA : NUDGE_DELTA);
		int xdelta = 0, ydelta = 0;
		switch (direction) {
			case SWT.UP:
				ydelta = -delta;
				break;
			case SWT.DOWN:
				ydelta = delta;
				break;
			case SWT.LEFT:
				xdelta = -delta;
				break;
			case SWT.RIGHT:
				xdelta = delta;
				break;
		}
		request.setMoveDelta(new Point(xdelta, ydelta));
		List editParts = getSelectedObjects();
		request.setEditParts(editParts);
		request.setLocation(new Point(-1, -1));

		CompoundCommand command = new CompoundCommand();
		command.setDebugLabel(getText());
		for (int i = 0; i < editParts.size(); i++) {
			Object o = editParts.get(i);
			if (o instanceof EditPart) {
				EditPart editPart = (EditPart) o;
				command.add(editPart.getCommand(request));
			}
		}
		return command;
	}
	
	private int scale(int delta) {
		ZoomManager zoomer = (ZoomManager) getWorkbenchPart().getAdapter(ZoomManager.class);
		double result = delta * zoomer.getZoom();
		if (result < 1.0)
			result = 1.0;
		return (int) result;
	}

	/**
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		Command cmd = createNudgeCommand();
		if (cmd == null)
			return false;
		return cmd.canExecute();
	}

	/**
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	public void run() {
		execute(createNudgeCommand());
	}
}
