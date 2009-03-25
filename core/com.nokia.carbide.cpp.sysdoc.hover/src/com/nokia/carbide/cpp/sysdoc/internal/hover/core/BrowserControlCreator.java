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
package com.nokia.carbide.cpp.sysdoc.internal.hover.core;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.Future;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.internal.text.html.BrowserInformationControlInput;
import org.eclipse.jface.text.AbstractReusableInformationControlCreator;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;

import com.nokia.carbide.cpp.sysdoc.internal.hover.dao.DevLibContent;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dao.NotFoundMarker;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dao.PathNode;
import com.nokia.carbide.cpp.sysdoc.internal.hover.exceptions.HoverException;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.URLHelper;
import com.nokia.carbide.cpp.sysdoc.internal.hover.view.BrowserInformationControl;
import com.nokia.carbide.cpp.sysdoc.internal.hover.view.BrowserInformationControlAgentImp;
import com.nokia.carbide.cpp.sysdoc.internal.hover.view.IBrowserInformationControlAgent;
import com.nokia.carbide.cpp.sysdoc.internal.hover.view.InputUpdater;

/**
 * 
 * Class used for create popup browser and content.
 * 
 */
final public class BrowserControlCreator extends
		AbstractReusableInformationControlCreator {

	private BrowserInformationControl iControl;

	@Override
	public IInformationControl doCreateInformationControl(final Shell parent) {
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=218482
		if (BrowserInformationControl.isAvailable(parent)) {
			createCustumBIC(parent);
			return iControl;
		}
		throw new HoverException(
				MessagesConstants.CANNOT_INIT_POP_UPBROWSER);
	}

	private void createCustumBIC(final Shell parent) {
		IBrowserInformationControlAgent browserInformationControlAgent = new BrowserInformationControlAgentImp(
				false);
		iControl = new BrowserInformationControl(parent, "Courier", true,
				browserInformationControlAgent) {

			@Override
			public void setInput(final Object input) {
				Assert.isLegal(input == null
						|| input instanceof BrowserInformationControlInput
						|| input instanceof DevLibContent
						|| input instanceof Future);
				if (input instanceof Future) {
					super.setInput(" ");
					futureHelpPage(input);
				} else if (input instanceof NotFoundMarker) {
					notFoundHelppage(input);
				} else if (input instanceof DevLibContent) {
					super.setInput(" ");
					switchSysDocHelpPage(input);
				} else {
					super.setInput(input);
				}
			}

			@Override
			public Point computeSizeHint() {
				return new Point(HoverConstants.BROWSER_WIDTH,
						HoverConstants.BROWSER_HEIGHT);
			}

			@Override
			public IInformationControlCreator getInformationPresenterControlCreator() {
				return new BrowserControlCreator();
			}
		};
	}

	private void switchSysDocHelpPage(final Object input) {
		DevLibContent shInput = (DevLibContent) input;
		List<PathNode> pathAsNodesList = shInput.getPathAsNodesList();
		if (pathAsNodesList == null) {
			singleHelpPage(shInput.getURL());
			return;
		}
		if (pathAsNodesList.size() == 1) {
			singleHelpPage(pathAsNodesList.get(0));
			return;
		} else {
			multiHelpOptionPage(pathAsNodesList);
			return;
		}
	}

	private void futureHelpPage(final Object input) {
		if (!((Future) input).isDone()) {
			URL url = InputUpdater.getInitializationInput().getURL();
			iControl.setUrl(url);
		}
		new Thread(new InputUpdater((Future<DevLibContent>) input, iControl,
				false)).start();
	}

	private void notFoundHelppage(Object input) {
		URL url = InputUpdater.getInitializationNotFoundResurce().getURL();
		iControl.setUrl(url);
	}

	private void multiHelpOptionPage(List<PathNode> pathAsNodesList) {
		URL url = InputUpdater.getInitializationMultiOptionHelpPage(
				pathAsNodesList).getURL();
		Logger.logDebug("multi page url:" + url.toString());
		iControl.setUrl(url);
	}

	private void singleHelpPage(URL url) {
		iControl.setUrl(url);
		try {
			Logger.logDebug("browser url:" + url.toString());
			String anchor = url.getRef();
			if (anchor != null) {
				anchor = URLEncoder.encode(url.getRef(), "UTF-8");
				// iControl.execute("window.location.href=#" + anchor);
			}
		} catch (UnsupportedEncodingException uee) {
			Logger.logError(uee);
		}
	}

	private void singleHelpPage(PathNode n) {
		try {
			String fullURL = URLHelper.getFullPath(n.getPath());
			URL url = new URL(fullURL);
			singleHelpPage(url);
		} catch (Exception ex) {
			Logger.logError(ex);
		}
	}
}