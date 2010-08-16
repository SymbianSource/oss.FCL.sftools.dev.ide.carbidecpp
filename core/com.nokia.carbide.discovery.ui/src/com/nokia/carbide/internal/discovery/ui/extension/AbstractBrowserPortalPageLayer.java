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
import java.text.MessageFormat;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationAdapter;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.ProgressAdapter;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;

import com.nokia.carbide.discovery.ui.Activator;
import com.nokia.carbide.discovery.ui.Messages;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

public abstract class AbstractBrowserPortalPageLayer implements IPortalPageLayer {

	protected class BrowserAction extends Action {
		
		public BrowserAction(String text) {
			super(text);
		}

		@Override
		public boolean isEnabled() {
			return browser != null && !actionBar.isLoading();
		}
	}
	
	protected class NavigationActionBar implements IActionBar {
		private Set<IAction> actions;
		private IActionUIUpdater updater;
		private boolean loading;

		private NavigationActionBar(IActionUIUpdater updater) {
			this.updater = updater;
			actions = makeActions();
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
	                setLoading(event.current != event.total);
					NavigationActionBar.this.updater.updateAll();
				}
				@Override
				public void completed(ProgressEvent event) {
					setLoading(false);
					NavigationActionBar.this.updater.updateAll();
				}
			});
			browser.addOpenWindowListener(new OpenWindowListener() {
				@Override
				public void open(WindowEvent event) {
					event.browser = browser;
				}
			});
		}

		@Override
		public String getTitle() {
			return Messages.AbstractBrowserPortalPage_NavigationTitle;
		}

		@Override
		public IAction[] getActions() {
			return (IAction[]) actions.toArray(new IAction[actions.size()]);
		}

		public void update() {
			updater.updateAll();
		}

		public void setLoading(boolean loading) {
			Activator.setBusyCursor(browser, loading);
			this.loading = loading;
		}

		@Override
		public String[] getHighlightedActionIds() {
			return null;
		}

		public boolean isLoading() {
			return loading;
		}
	}

	protected Browser browser;
	protected NavigationActionBar actionBar;

	@Override
	public Control createControl(Composite parent, IEditorPart part) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		composite.setLayout(new FillLayout());
		try {
			browser = new Browser(composite, SWT.MOZILLA);
		} catch (Throwable e1) {
			try {
				// try creating regular browser
				browser = new Browser(composite, SWT.NONE);
			} catch (Throwable e2) {
				// don't log with Throwable directly because it may display a dialog
				Activator.logError(MessageFormat.format(Messages.AbstractBrowserPortalPageLayer_BrowserCreateError, e2.getMessage()), null);
			}
		}
		
		return composite;
	}

	protected URL getURL() {
		try {
			return new URL(Activator.getFromServerProperties(getClass().getName()));
		} catch (MalformedURLException e) {
			Activator.logError(Messages.AbstractBrowserPortalPageLayer_BadURLError, e);
		}
		
		return null;
	}

	@Override
	public void init() {
		if (browser != null) {
			Activator.runInUIThreadWhenProxyDataSet(browser, new Runnable() {
				@Override
				public void run() {
					URL url = getURL();
					if (url != null) {
						if (!browser.setUrl(url.toString())) {
							browser.redraw();
						}
					}
					actionBar.hookBrowser();
					actionBar.update();
					browser.setFocus();
				}
			});
		}
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
	
	protected Set<IAction> makeActions() {
		Set<IAction> actions = new LinkedHashSet<IAction>();
		IAction a = new BrowserAction(Messages.AbstractBrowserPortalPage_BackLabel) {
			@Override
			public void run() {
				if (browser != null) {
					browser.back();
				}
			}
			
			@Override
			public boolean isEnabled() {
				return super.isEnabled() && browser.isBackEnabled();
			}
		};
		actions.add(a);
		
		a = new BrowserAction(Messages.AbstractBrowserPortalPageLayer_ForwardLabel) {
			@Override
			public void run() {
				if (browser != null) {
					browser.forward();
				}
			}
			
			@Override
			public boolean isEnabled() {
				return super.isEnabled() && browser.isForwardEnabled();
			}
		};
		actions.add(a);
		
		a = new BrowserAction(Messages.AbstractBrowserPortalPage_RefreshLabel) {
			@Override
			public void run() {
				if (browser != null) {
					browser.refresh();
					actionBar.update();
				}
			}
		};
		actions.add(a);

		a = new Action(Messages.AbstractBrowserPortalPage_StopLabel) {
			@Override
			public void run() {
				if (browser != null) {
					browser.stop();
					actionBar.update();
				}
			}

			@Override
			public boolean isEnabled() {
				return browser != null && actionBar.isLoading();
			}
		};
		actions.add(a);
		
		a = new BrowserAction(Messages.AbstractBrowserPortalPageLayer_ShowInBrowserLabel) {
			public void run() {
				WorkbenchUtils.showWebPageInExternalBrowser(browser.getUrl());
			};
			
			public boolean isEnabled() {
				return super.isEnabled() && browserHasURL();
			};
		};
		actions.add(a);
		
		a = new Action(Messages.AbstractBrowserPortalPageLayer_OpenLocationLabel) {
			public void run() {
				InputDialog dlg = new InputDialog(browser.getShell(), 
						Messages.AbstractBrowserPortalPageLayer_OpenLocationTitle, Messages.AbstractBrowserPortalPageLayer_URLLabel, null, new IInputValidator() {
					@Override
					public String isValid(String newText) {
						try {
							new URL(newText);
						} catch (MalformedURLException e) {
							return Messages.AbstractBrowserPortalPageLayer_InvalidURLError;
						}
						return null;
					}
				});
				int result = dlg.open();
				if (result == Dialog.OK) {
					browser.setUrl(dlg.getValue());
					actionBar.update();
				}
			};
			
			public boolean isEnabled() {
				return browser != null;
			};
		};
		actions.add(a);
		
		return actions;
	}

	protected boolean browserHasURL() {
		String url = browser.getUrl();
		return url.matches("^.*://.*"); //$NON-NLS-1$
	}
}
