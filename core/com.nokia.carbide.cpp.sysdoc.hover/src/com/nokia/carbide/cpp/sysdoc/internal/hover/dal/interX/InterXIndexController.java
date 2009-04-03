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
package com.nokia.carbide.cpp.sysdoc.internal.hover.dal.interX;

import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverManager;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.ExecutorAgent;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;

/**
 * 
 * It is a controller for indexing interchange files. It kicks off a progress
 * monitoring to display the indexing progress.
 * 
 */
final public class InterXIndexController {
	private static volatile InterXIndexer interXIndexer = null;
	private volatile static boolean indexingCompleted = false;
	private volatile static boolean isIndexingCancelled=false;
	
	public static InterXIndexer getInterXIndexer() {
		if (interXIndexer == null) {
			creatIndexer();
		}
		return interXIndexer;
	}

	public static void creatIndexer() {
		Logger.logDebug("started indexing !!!!");
		HoverManager.getInstance().setEnabled(false);
		InterXProperties interXProperties = HoverManager.getInstance()
				.getDevLibLocator().getInterXProperties();
		interXIndexer = new InterXIndexer(interXProperties);
		indexingCompleted = false;
		isIndexingCancelled=false;
		Runnable indexGenRunnable = new Runnable() {
			public void run() {
				try {
					interXIndexer.startIndexing();
				} catch (Exception e) {
					HoverManager.getInstance().haltHoveringService(e);
				}
			}
		};
		ExecutorAgent.run(indexGenRunnable);
	}

	public static boolean isIndexingCompleted() {
		return indexingCompleted;

	}

	protected static void setIndexingCompleted(boolean b) {
		HoverManager.getInstance().setEnabled(b);
		indexingCompleted = b;
	}

	public static boolean isIndexingCancelled() {
		return isIndexingCancelled;
	}

	public static void cancelIndexing() {			
		InterXIndexController.isIndexingCancelled = true;
	}
	
}
