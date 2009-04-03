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
package com.nokia.sdt.sourcegen.provider;

import com.nokia.sdt.sourcegen.ISourceGenProvider;
import com.nokia.sdt.symbian.Messages;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.progress.UIJob;

public class SourceChangedJob extends UIJob {

	private ISourceGenProvider provider;
	private boolean force;

	public SourceChangedJob(ISourceGenProvider provider, boolean force) {
		super(Messages.getString("SourceChangedJob.jobName")); //$NON-NLS-1$
		Check.checkArg(provider);
		this.provider = provider;
		this.force = force;
	}

	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		IStatus result = null;
		synchronized (provider) {
			result = provider.updateSourceState(monitor, force);
		}
		if (result == null)
			result = Status.OK_STATUS;
		return result;
	}

	/**
	 * Upgrade force status
	 * @param force
	 */
	public void setForce(boolean force) {
		this.force |= force;
	}
}
