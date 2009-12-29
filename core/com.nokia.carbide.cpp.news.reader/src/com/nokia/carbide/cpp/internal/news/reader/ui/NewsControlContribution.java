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

import java.text.MessageFormat;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

import com.nokia.carbide.cpp.internal.news.reader.CarbideNewsReaderPlugin;
import com.nokia.carbide.cpp.internal.news.reader.Messages;
import com.nokia.carbide.cpp.internal.news.reader.editor.NewsEditor;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.FeedCacheManager;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedCache.IFeedCacheChangedlistener;

/**
 * Control to launch Carbide.c++ news page in the workbench.
 *
 */
public class NewsControlContribution extends WorkbenchWindowControlContribution
		implements IFeedCacheChangedlistener {

	/** This is the id on the command in the toolbar contribution associated with this 
	 * widget.  Keep this in sync with the extension point! */
	private static final CharSequence NEWS_TRIM_COMMAND_ID = "newsTrimCommand"; //$NON-NLS-1$
	
	// private data
	private static NewsControlContribution sharedInstance;
	private Label label;
	private boolean alert;
	private Composite container;

	private ToolItem newsIconItem;

	private MouseAdapter toolbarListener;

	private ToolBar toolbar;

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
		
		if (parent instanceof ToolBar) {
			toolbar = (ToolBar) parent;
			ToolItem[] items = toolbar.getItems();
			for (ToolItem item : items) {
				Object data = item.getData();
				if (data instanceof CommandContributionItem &&
						((CommandContributionItem) data).getId().contains(NEWS_TRIM_COMMAND_ID)) {
					newsIconItem = item;
					break;
				}
			}
		} else {
			toolbar = null;
		}
		
		container = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().margins(2, 2).applyTo(container);

		// Create a label for the trim.
		label = new Label(container, SWT.BOTTOM);
		GridDataFactory.swtDefaults().grab(false, true).applyTo(label);
		String text = MessageFormat.format(Messages.NewsControlContribution_UnreadMessageFormat, CarbideNewsReaderPlugin.getFeedManager().getUnreadEntriesCount()); 
		label.setText(text);
		if (alert) {
			Font font = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
			label.setFont(font);
			alert = false;
		}
		
		if (newsIconItem != null) {
			// Yuck, toolbars and items have a wonky UI.  We need to do these events on the parent,
			// since the ToolItem itself is just an area inside the parent.  (#getControl() is only for separators ?!)

			// On icon: left click = open view, right click = menu
			if (toolbar != null) {
				if (toolbarListener != null)
					toolbar.removeMouseListener(toolbarListener);
				
				toolbarListener = new MouseAdapter() {
					/* (non-Javadoc)
					 * @see org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.events.MouseEvent)
					 */
					@Override
					public void mouseDown(MouseEvent event) {
						ToolItem item = toolbar.getItem(new Point(event.x, event.y));
						if (item == newsIconItem) {
							if (event.button == 1) {
								NewsEditor.openEditor();
							} else if (event.button == 3) {
								Point screenLoc = toolbar.toDisplay(event.x, event.y);
								handleNewsMenu(screenLoc);
							}
						}
					}
				};
				toolbar.addMouseListener(toolbarListener);
			}
		}
		
		// On label: left or right click = menu
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (e.button == 1 || e.button == 3) {
					Point screenLoc = label.toDisplay(e.x, e.y);
					handleNewsMenu(screenLoc);
				}
			}
		});
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, NewsUIHelpIDs.NEWSREADER_TRIM_COMMAND);
		return container;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.action.ContributionItem#dispose()
	 */
	public void dispose() {
		FeedCacheManager.removeFeedCacheChangedListener(this);
		if (toolbarListener != null && toolbar != null && !toolbar.isDisposed()) {
			toolbar.removeMouseListener(toolbarListener);
			toolbarListener = null;
		}
		super.dispose();
	}

	private void handleNewsMenu(Point screenLoc) {
		Shell shell = label.getParent().getShell();
		final Display display = shell.getDisplay();
		
		final Menu menu = new Menu(shell, SWT.POP_UP);
		populateMenu(menu);
		
		menu.setLocation(screenLoc.x, screenLoc.y);
		menu.setVisible(true);
		
		while (!menu.isDisposed() && menu.isVisible()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		menu.dispose();

	}

	private void populateMenu(Menu menu) {
		MenuItem openNews = new MenuItem(menu, SWT.PUSH);
		openNews.setText(Messages.NewsControlContribution_OpenNewsReaderMenuItem);
		openNews.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				NewsEditor.openEditor();
			}
		});
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
