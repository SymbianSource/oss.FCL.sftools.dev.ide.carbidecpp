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
package com.nokia.carbide.cpp.sysdoc.internal.hover.view;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Stack;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.sysdoc.hover.Activator;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverConstants;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;

/**
 * An implementation of IBrowserInformationControlAgent which provides layout
 * and content information for pop up browser and developer library view.
 */
public class BrowserInformationControlAgentImp implements
		IBrowserInformationControlAgent {
	private Browser fBrowser;
	private Shell shell;
	private boolean isView = false;

	public BrowserInformationControlAgentImp(boolean isView) {
		this.isView = isView;
	}

	private ToolBar toolBar;
	private ToolItem backPushBtn;
	private ToolItem fwdPushBtn;
	private ToolItem externalBrowserBtn;
	private ToolItem homeSDLBrowserBtn;
	private ToolItem viewBrowserBtn;
	private ToolItem helpBrowserBtn;
	private GridData gridDataBrowser;
	private Stack<String> urlStack;
	private GridData parentData;
	private ToolItem settingBtn;

	// creates content
	public void createContent(Composite parent) {
		createLayout(parent);

		fBrowser.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				if (e.character == 0x1B) // ESC
					getShell().dispose(); // XXX: Just hide? Would avoid
				// constant recreations.
			}

			public void keyReleased(KeyEvent e) {
			}
		});

		// Replace browser's built-in context menu with none
		fBrowser.setMenu(new Menu(getShell(), SWT.NONE));
		fBrowser.setToolTipText("beeeeee");
	}

	// creates content
	private void createLayout(final Composite parent) {
		int w = HoverConstants.BROWSER_WIDTH + 20;
		int hIndent = -5;
		int vIndent = -8;

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;

		// developer library view
		if (isView) {
			gridLayout.marginLeft = -5;
			gridLayout.marginTop = -5;
		}

		gridLayout.marginRight = -5;
		gridLayout.marginBottom = -5;
		parent.setLayout(gridLayout);
		parentData = new GridData();
		parentData.horizontalIndent = hIndent;
		parentData.verticalIndent = vIndent;

		parentData.grabExcessHorizontalSpace = true;
		parentData.grabExcessVerticalSpace = true;
		parentData.verticalAlignment = GridData.FILL;
		parentData.horizontalAlignment = GridData.FILL;
		parent.setLayoutData(parentData);

		toolBar = new ToolBar(parent, SWT.FLAT | SWT.WRAP | SWT.RIGHT);
		GridData toolBarGridData = new GridData(GridData.FILL_HORIZONTAL
				| GridData.GRAB_HORIZONTAL);
		toolBarGridData.widthHint = w;

		toolBar.setLayoutData(toolBarGridData);

		fBrowser = new Browser(parent, SWT.RESIZE);

		gridDataBrowser = new GridData();
		gridDataBrowser.grabExcessHorizontalSpace = true;
		gridDataBrowser.grabExcessVerticalSpace = true;
		gridDataBrowser.verticalAlignment = GridData.FILL;
		gridDataBrowser.horizontalAlignment = GridData.FILL;
		gridDataBrowser.verticalIndent = vIndent - 1;

		fBrowser.setLayoutData(gridDataBrowser);
		addLocationListeners();

		Display display = getShell().getDisplay();
		parent.setForeground(display.getSystemColor(SWT.COLOR_INFO_FOREGROUND));
		parent.setBackground(display.getSystemColor(SWT.COLOR_INFO_BACKGROUND));

		fBrowser.setForeground(display.getSystemColor(SWT.COLOR_BLUE)); // SWT.COLOR_INFO_FOREGROUND)
		fBrowser.setBackground(display.getSystemColor(SWT.COLOR_BLACK));

		prepareToolBar();
	}

	private void addLocationListeners() {
		urlStack = new Stack<String>();
		fBrowser.addLocationListener(new LocationListener() {

			public void changed(LocationEvent event) {
				Browser browser = (Browser) event.widget;
				checkBackFrwButtonsValid(browser);
				urlStack.push(event.location);
			}

			public void changing(LocationEvent event) {
			}
		});
	}

	// control back and forward buttons
	private void checkBackFrwButtonsValid(Browser browser) {
		if (urlStack.isEmpty())
			return;

		String lastURL = urlStack.peek();
		if (BrowserHelper.getInstance().isAboutOrHoverAuxPage(lastURL)) {
			backPushBtn.setEnabled(false);
		} else {
			backPushBtn.setEnabled(browser.isBackEnabled());
		}

		if (urlStack.size() > 0) {
			fwdPushBtn.setEnabled(browser.isForwardEnabled());
		}
	}

	// creates toolbar
	private void prepareToolBar() {

		// back button of browser
		backPushBtn = new ToolItem(toolBar, SWT.PUSH);
		Image backIcon = PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_TOOL_BACK);
		backPushBtn.setImage(backIcon);
		backPushBtn.setToolTipText("Back");
		backPushBtn.setEnabled(false);

		backPushBtn.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			public void widgetSelected(SelectionEvent arg0) {
				fBrowser.back();
			}
		});

		// forward button of browser
		fwdPushBtn = new ToolItem(toolBar, SWT.PUSH);
		Image fwdIcon = PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_TOOL_FORWARD);
		fwdPushBtn.setImage(fwdIcon);
		fwdPushBtn.setToolTipText("Forward");
		fwdPushBtn.setEnabled(false);

		fwdPushBtn.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			public void widgetSelected(SelectionEvent arg0) {
				fBrowser.forward();
			}
		});

		// display content in developer library view
		viewBrowserBtn = new ToolItem(toolBar, SWT.PUSH);
		Image viewBrowserImage = getImage("icons/view.gif");
		viewBrowserBtn.setImage(viewBrowserImage);
		viewBrowserBtn.setToolTipText("Show in Developer Library View");
		viewBrowserBtn.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			public void widgetSelected(SelectionEvent arg0) {
				openDevLibView();
			}
		});

		if (isView) {
			viewBrowserBtn.setEnabled(false);
		}

		// display content in an external browser
		externalBrowserBtn = new ToolItem(toolBar, SWT.PUSH);
		Image externalBrowserImage = getImage("icons/web.gif");
		externalBrowserBtn.setImage(externalBrowserImage);
		externalBrowserBtn.setToolTipText("Open in an External Browser");

		externalBrowserBtn.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			public void widgetSelected(SelectionEvent arg0) {
				BrowserHelper.getInstance().doOpenExternalBrowser(
						fBrowser.getUrl(), getShell(), isView);
			}
		});

		// display hover help preference panel
/*		settingBtn = new ToolItem(toolBar, SWT.PUSH);
		Image settingImage = getImage("icons/preference.gif");
		settingBtn.setImage(settingImage);
		settingBtn.setToolTipText("Open Hover Help Preferences");

		settingBtn.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			public void widgetSelected(SelectionEvent arg0) {
				openHHPreferencesPanel();
			}
		});
*/		

		ToolItem itemSeparator = new ToolItem(toolBar, SWT.SEPARATOR);
		itemSeparator.setWidth(10);

		// a link to Symbian Developer Library home page
		homeSDLBrowserBtn = new ToolItem(toolBar, SWT.PUSH | SWT.SEPARATOR);

		Image homeIcon = PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_ETOOL_HOME_NAV);
		homeSDLBrowserBtn.setImage(homeIcon);
		homeSDLBrowserBtn.setToolTipText("Online Developer Library");
		homeSDLBrowserBtn.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			public void widgetSelected(SelectionEvent arg0) {
				BrowserHelper.getInstance().doOpenExternalBrowser(
						HoverConstants.SDL_WEB_ADDRESS, getShell(), isView);
			}
		});

		// display help content of hover help
		helpBrowserBtn = new ToolItem(toolBar, SWT.PUSH | SWT.SEPARATOR);

		Image helpIcon = PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_LCL_LINKTO_HELP);
		helpBrowserBtn.setImage(helpIcon);
		helpBrowserBtn.setToolTipText("Help for Hover Help");
		helpBrowserBtn.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			public void widgetSelected(SelectionEvent arg0) {
				displayHelpContent();
			}
		});

	}

	// display hover help content in eclipse help view
	protected void displayHelpContent() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().showView("org.eclipse.help.ui.HelpView");
		} catch (PartInitException e) {
			Logger.logError(e);
		}
		PlatformUI.getWorkbench().getHelpSystem().displayHelp(
				HoverConstants.HELP_CONTEXTID);
		if (!isView) {
			getShell().dispose();
		}
	}

	/*
	// display Hover Help Prefence Panel
	protected void openHHPreferencesPanel() {
		if (!isView && !getShell().isDisposed()) {
			getShell().dispose();
		}
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getShell();// getShell();
		PreferenceDialog pref = PreferencesUtil.createPreferenceDialogOn(shell,
				PreferencePageView.PREFERENCE_ID,
				new String[] { PreferencePageView.PREFERENCE_ID }, null);
		if (pref != null) {
			pref.open();
		}
	}
	*/

	// open developer library view and show content of pop-up browser
	protected void openDevLibView() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().showView(DeveloperLibraryView.VIEW_ID);
			String urlStr = fBrowser.getUrl();
			URL url = null;
			try {
				url = new URL(urlStr);
			} catch (MalformedURLException e) {
				Logger.logError(e);
			}
			DeveloperLibraryView.setURL(url);
			shell.dispose();
		} catch (PartInitException e) {
			Logger.logError(e, "Can not open Developer Library View");
		}
	}

	// fetch image in plug-in or return a default shared image
	private Image getImage(String imagePath) {
		Image image = null;
		Display display = getShell().getDisplay();

		try {
			InputStream os = FileLocator.openStream(Activator.getDefault()
					.getBundle(), new Path(imagePath), false);
			image = new Image(display, os);// PlatformUI.getWorkbench().getSharedImages().getImage());
		} catch (IOException e) {
			image = PlatformUI.getWorkbench().getSharedImages().getImage(
					ISharedImages.IMG_DEF_VIEW);
		}
		return image;
	}

	public Browser getBrowser() {
		return fBrowser;
	}

	private Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	public void setVisible() {
		Logger.logDebug("set visible");
		urlStack.clear();
	}

	/**
	 * Sets URL of browser
	 */
	public void setURL(URL url) {
		String urlStr = url.toString();
		// make sure it is not an empty url
		if (fBrowser != null
				&& !fBrowser.isDisposed()
				&& !BrowserHelper.getInstance().isAboutOrHoverAuxPage(
						url.toString())) {
			fBrowser.setUrl(urlStr);
		}
	}

}
