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
package com.nokia.sdt.utils;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;

import com.nokia.cpp.internal.api.utils.core.StatusBuilder;

/**
 * Instances of this class can be used as reference parameters for returning IStatus in addition to a return value.
 */
public class StatusHolder {

	private IStatus status;
	
	public void setStatus(IStatus status) {
		this.status = status;
	}

	public IStatus getStatus() {
		return status;
	}
	
	private static void addChildren(StatusBuilder builder, MultiStatus source) {
		IStatus[] children = source.getChildren();
		for (int i = 0; i < children.length; i++) {
			IStatus child = children[i];
			builder.add(child);
		}
	}
	
	public void appendStatus(IStatus otherStatus, String delim) {
		if (otherStatus == null)
			return;
		if (status == null)
			setStatus(otherStatus);
		else {
			StatusBuilder builder = new StatusBuilder(status.getPlugin());
			if (status.isMultiStatus()) {
				addChildren(builder, (MultiStatus) status);
			}
			if (otherStatus.isMultiStatus()) {
				addChildren(builder, (MultiStatus) otherStatus);
			}
			
			String mergedMessage = status.getMessage() + delim + otherStatus.getMessage();
			status = builder.createMultiStatus("{0}", new Object[] { mergedMessage });
		}
	}
}
