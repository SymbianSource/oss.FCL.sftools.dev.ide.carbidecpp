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

package com.nokia.carbide.cpp.internal.news.reader.ui;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

import com.nokia.carbide.cpp.internal.news.reader.CarbideNewsReaderPlugin;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCacheManager;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.IFeedCacheChangedlistener;

/**
 * Control to launch Carbide.c++ news page in the workbench.
 *
 */
public class NewsControlContribution extends WorkbenchWindowControlContribution
		implements IFeedCacheChangedlistener {

	// private data
	private static NewsControlContribution sharedInstance;
	private Label label;
	private boolean alert;
	private Composite container;

	/**
	 * The constructor.
	 */
	public NewsControlContribution() {
		sharedInstance = this;
		alert = false;
		FeedCacheManager.addFeedCacheChangedListener(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.action.ControlContribution#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().margins(2, 2).applyTo(container);

		// Create a label for the trim.
		label = new Label(container, SWT.BOTTOM);
		GridDataFactory.swtDefaults().grab(false, true).applyTo(label);
		String text = CarbideNewsReaderPlugin.getFeedManager().getUnreadEntriesCount() + " unread";
		label.setText(text);
		if (alert) {
			Font font = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
			label.setFont(font);
			alert = false;
		}
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, NewsUIHelpIDs.NEWSREADER_TRIM_COMMAND);
		return container;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.action.ContributionItem#dispose()
	 */
	public void dispose() {
		FeedCacheManager.removeFeedCacheChangedListener(this);
		super.dispose();
	}

	/**
	 * Perform the necessary updates in response to local feed cache changes.
	 */
	public void feedCacheChanged(boolean alertUser) {
		setAlert(alertUser);
		// perform update in UI thread
		final IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
				if (window != null) {
					update();
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.action.ContributionItem#isDynamic()
	 */
	@Override
	public boolean isDynamic() {
		return true;
	}

	/**
	 * Returns the shared instance.
	 * @return the shared instance
	 */
	public static NewsControlContribution getDefault() {
		return sharedInstance;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.action.ContributionItem#update()
	 */
	@Override
	public void update() {
		getDefault().getParent().update(true);
	}

	/**
	 * Set the flag that controls whether to generate alerts for user.
	 * @param alertUser - boolean that controls whether to generate alerts for user
	 */
	public void setAlert(boolean alertUser) {
		alert = alertUser;
	}

}
