/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.internal.discovery.ui.extension;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationAdapter;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.ProgressAdapter;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;

import com.nokia.carbide.discovery.ui.Activator;

public abstract class AbstractBrowserPortalPage implements IPortalPage {

	private final class NavigationActionBar implements IActionBar {
		private List<IAction> actions;
		private IActionUIUpdater updater;
		private boolean loading;

		private NavigationActionBar(IActionUIUpdater updater) {
			this.updater = updater;
			makeActions();
		}

		public void hookBrowser() {
			browser.addLocationListener(new LocationAdapter() {
				@Override
				public void changed(LocationEvent event) {
					NavigationActionBar.this.updater.updateAll();
				}
			});
			browser.addProgressListener(new ProgressAdapter() {
				@Override
				public void changed(ProgressEvent event) {
	                if (event.total == 0)
	                    return;
	                loading = (event.current != event.total);
					NavigationActionBar.this.updater.updateAll();
				}
				@Override
				public void completed(ProgressEvent event) {
					loading = false;
					NavigationActionBar.this.updater.updateAll();
				}
			});
		}

		@Override
		public String getTitle() {
			return "Navigation";
		}

		@Override
		public IAction[] getActions() {
			return (IAction[]) actions.toArray(new IAction[actions.size()]);
		}

		private void makeActions() {
			actions = new ArrayList<IAction>();
			IAction a = new Action("Back") {
				@Override
				public void run() {
					if (browser != null) {
						browser.back();
					}
				}
				
				@Override
				public boolean isEnabled() {
					return browser != null ? browser.isBackEnabled() : false;
				}
			};
			actions.add(a);
			
			a = new Action("Forward") {
				@Override
				public void run() {
					if (browser != null) {
						browser.forward();
					}
				}
				
				@Override
				public boolean isEnabled() {
					return browser != null ? browser.isForwardEnabled() : false;
				}
			};
			actions.add(a);
			
			a = new Action("Refresh Page") {
				@Override
				public void run() {
					if (browser != null) {
						browser.refresh();
						update();
					}
				}

				@Override
				public boolean isEnabled() {
					return browser != null ? !loading : false;
				}
			};
			actions.add(a);

			a = new Action("Stop Loading") {
				@Override
				public void run() {
					if (browser != null) {
						browser.stop();
						update();
					}
				}

				@Override
				public boolean isEnabled() {
					return browser != null ? loading : false;
				}
			};
			actions.add(a);
		}

		public void update() {
			updater.updateAll();
		}

	}

	private Browser browser;
	private NavigationActionBar actionBar;

	@Override
	public Control createControl(Composite parent, IEditorPart part) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		composite.setLayout(new FillLayout());
		try {
			browser = new Browser(composite, SWT.MOZILLA);
		} catch (SWTError e) {
			e.printStackTrace();
		}
		
		return composite;
	}

	protected String getURL() {
		try {
			URL url = new URL(Activator.getFromServerProperties(getClass().getName()));
			return url.toString();
		} catch (MalformedURLException e) {
			Activator.logError("Could not load page due to bad URL", e);
		}
		
		return null;
	}

	@Override
	public void init() {
		if (browser != null) {
			browser.setUrl(getURL());
			actionBar.hookBrowser();
		}
		actionBar.update();
	}
	
	@Override
	public IActionBar[] createCommandBars(IEditorPart part, IActionUIUpdater updater) {
		actionBar = new NavigationActionBar(updater);
		return new IActionBar[] {
			actionBar
		};
	}

	@Override
	public void dispose() {
	}

}
