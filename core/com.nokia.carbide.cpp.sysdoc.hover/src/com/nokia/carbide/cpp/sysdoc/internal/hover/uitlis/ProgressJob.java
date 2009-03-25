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
 *				Deniz TURAN
 * Description: 
 * 				
 */
package com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis;

/**
 * A runnable abstract class which used by progress monitors. It holds status of
 * progress monitor, whether the progress has finished or not.
 */
public class ProgressJob {
	protected boolean done = false;
	private final static Object lock = new Object();
	private Runnable task;

	public boolean isDone() {
		return done;
	}

	public void start() {
		synchronized (lock) {
			if (task != null) {
				task.run();
			}
		}
	}

	public Runnable getTask() {
		return task;
	}

	public void setTask(Runnable job) {
		this.task = job;
	}

	public void setDone(boolean b) {
		done = b;
	}

}
