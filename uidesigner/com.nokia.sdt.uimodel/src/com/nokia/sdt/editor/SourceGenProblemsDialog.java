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
package com.nokia.sdt.editor;

import com.nokia.sdt.uimodel.Messages;
import com.nokia.cpp.internal.api.utils.core.IMessage;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;

import java.text.MessageFormat;
import java.util.*;

public class SourceGenProblemsDialog extends MessageDialog {
	private Collection<IMessage> messages;

	/**
	 * @param parentShell
	 * @param dialogTitle
	 * @param dialogTitleImage
	 * @param dialogMessage
	 * @param dialogImageType
	 * @param dialogButtonLabels
	 * @param defaultIndex
	 */
	public SourceGenProblemsDialog(Shell parentShell, String dialogTitle, Image dialogTitleImage, String dialogMessage, int dialogImageType, String[] dialogButtonLabels, int defaultIndex) {
		super(parentShell, dialogTitle, dialogTitleImage, dialogMessage,
				dialogImageType, dialogButtonLabels, defaultIndex);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#getInitialSize()
	 */
	@Override
	protected Point getInitialSize() {
		return super.getInitialSize();
		//return new Point(500, 300);
	}

	/**
	 * @param shell
	 * @param displayName
	 * @param messages
	 * @return
	 */
	public static SourceGenProblemsDialog create(Shell shell, String displayName, Collection<IMessage> messages) {
		boolean anyErrors = false;
		for (Iterator iter = messages.iterator(); iter.hasNext();) {
			IMessage message = (IMessage) iter.next();
			if (message.getSeverity() == IMessage.ERROR) {
				anyErrors = true;
				break;
			}
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(
				MessageFormat.format(
						Messages.getString("SourceGenProblemsDialog.SourceGenProblemsEncounteredMessage"), //$NON-NLS-1$
						displayName));
		
		SourceGenProblemsDialog dialog = new SourceGenProblemsDialog(shell, Messages.getString("SourceGenProblemsDialog.SourceGenProblemsEncounteredTitle"), null, //$NON-NLS-1$
				buffer.toString(), 
				(anyErrors ? MessageDialog.ERROR : MessageDialog.WARNING),
				new String[] { IDialogConstants.OK_LABEL },
				IDialogConstants.OK_ID);
		dialog.setMessages(messages);
		return dialog;
	}

	/**
	 * @param messages
	 */
	private void setMessages(Collection<IMessage> messages) {
		this.messages = messages;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.MessageDialog#createCustomArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createCustomArea(Composite parent) {
		/*
		// show the problems reported
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		Text text = new Text(composite, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL);
		
		StringBuffer buffer = new StringBuffer();
		for (Iterator iter = messages.iterator(); iter.hasNext();) {
			IMessage message = (IMessage) iter.next();
			buffer.append(message.getMessageLocation().getPath());
			buffer.append(": "); //$NON-NLS-1$
			buffer.append(message.getMessage());
			buffer.append("\n"); //$NON-NLS-1$
		}
		text.setText(TextUtils.formatTabbedList(messages));
		*/

		// show the files affected
		CLabel label = new CLabel(parent, SWT.WRAP);
		
		StringBuffer buffer = new StringBuffer();
		Set<IPath> visitedPaths = new HashSet<IPath>();
		for (Iterator iter = messages.iterator(); iter.hasNext();) {
			IMessage message = (IMessage) iter.next();
			IPath path = message.getMessageLocation().getPath();
			if (visitedPaths.contains(path))
				continue;
			buffer.append("\t\t"); //$NON-NLS-1$
			buffer.append(path);
			buffer.append("\n"); //$NON-NLS-1$
			visitedPaths.add(path);
		}
		label.setText(buffer.toString());

		return label;
	}
}
