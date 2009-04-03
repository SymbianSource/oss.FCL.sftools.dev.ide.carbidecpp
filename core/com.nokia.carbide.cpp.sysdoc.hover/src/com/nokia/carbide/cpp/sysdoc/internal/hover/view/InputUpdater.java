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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.IInformationControlExtension2;
import org.eclipse.swt.widgets.Display;

import com.nokia.carbide.cpp.sysdoc.hover.Activator;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dao.DevLibContent;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dao.PathNode;
import com.nokia.carbide.cpp.sysdoc.internal.hover.exceptions.HoverException;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;

/**
 * A runnable which monitors for the asynchronous html reference fetch, and upon
 * completion updates the hover window.
 */
public class InputUpdater implements Runnable {
	private static final int TICK_DURATION = 200;

	private static final Object INSERT_MARKER = "<!--INSERT_HERE-->";

	protected Future<DevLibContent> future;
	protected IInformationControlExtension2 target;
	protected boolean updateOnTick;

	public InputUpdater(Future<DevLibContent> future,
			IInformationControlExtension2 target, boolean updateOnTick) {
		this.future = future;
		this.target = target;
		this.updateOnTick = updateOnTick;
		if (!future.isDone()) {
			asyncUpdateInformation(getInitializationInput());
		}
	}

	public void run() {
		try {
			while (!future.isDone()) {
				if (updateOnTick) {
					asyncUpdateInformation(getInitializationInput());
				}
				Thread.sleep(TICK_DURATION);
			}
			if (future.isCancelled()) {
				return;
			}
			asyncUpdateInformation(future.get());
		} catch (InterruptedException e) {
			Logger.logError(e);
		} catch (ExecutionException e) {
			Logger.logError(e);
		}
	}

	private void asyncUpdateInformation(final DevLibContent s) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				target.setInput(s);
			}
		});
	}

	public static DevLibContent getInitializationInput() {
		StringBuilder sb = new StringBuilder();
		sb.append("Initialising");
		int ticks = (int) (((System.currentTimeMillis() % (TICK_DURATION * 5))) / TICK_DURATION);
		for (int i = 0; i < ticks; i++) {
			sb.append(".");
		}
		return new DevLibContent(getWaitingURL(), sb.toString());
	}

	private static URL getWaitingURL() {
		IPath waitingDir = Activator.getDefault().getStateLocation().append(
				"waiting");
		IPath initHtml = waitingDir.append("init.html");
		IPath barCircleGif = waitingDir.append("bar-circle.gif");
		URL bcg = FileLocator.find(Activator.getDefault().getBundle(),
				new Path("resources/waiting/bar-circle.gif"), null);
		URL init = FileLocator.find(Activator.getDefault().getBundle(),
				new Path("resources/waiting/init.html"), null);
		unpackBinary(bcg, barCircleGif);
		unpackAscii(init, initHtml, null);
		// }
		URL result = null;
		try {
			result = URIUtil.toURI(waitingDir.append("init.html")).toURL();
		} catch (MalformedURLException e) {
			Logger.logError(e);
		}
		return result;
	}

	public static DevLibContent getInitializationNotFoundResurce() {
		return getInitializationNotFoundResurce("Resource Not Found");
	}

	public static DevLibContent getInitializationNotFoundResurce(String msg) {
		StringBuilder sb = new StringBuilder();
		sb.append("Not found error!");
		return new DevLibContent(getNotFoundURL(msg), sb.toString());
	}

	private static URL getNotFoundURL(String msg) {
		String notFoundHtmlFileName = "notFound.html";
		String notFoundImageName = "404-no-file.jpg";
		String notFoundHtmlDir = "resources/html/notFound/";
		IPath notFoundHtmlPath = null;

		try {

			IPath notFoundDir = Activator.getDefault().getStateLocation()
					.append("html").append("notFound");
			notFoundHtmlPath = notFoundDir.append(notFoundHtmlFileName);
			IPath notFoundImagePath = notFoundDir.append(notFoundImageName);

			// File notFoundhtmlFile = new File(notFoundDir.append(
			// notFoundHtmlFileName).toOSString());

			// if (!(notFoundhtmlFile.exists())) {
			URL bcg = FileLocator.find(Activator.getDefault().getBundle(),
					new Path(notFoundHtmlDir + notFoundImageName), null);
			URL url = FileLocator.find(Activator.getDefault().getBundle(),
					new Path(notFoundHtmlDir + notFoundHtmlFileName), null);
			unpackBinary(bcg, notFoundImagePath);
			unpackAscii(url, notFoundHtmlPath, msg);
			// }
		} catch (Exception e) {
			Logger.logDebug(e);
		}
		URL result = null;
		try {
			result = URIUtil.toURI(notFoundHtmlPath).toURL();
		} catch (MalformedURLException murle) {
			Logger.logError(murle);
		}
		return result;
	}

	public static DevLibContent getInitializationMultiOptionHelpPage(
			List<PathNode> pathAsNodesList) {
		// "multiOptionHelpPage.html";

		StringBuilder sb = new StringBuilder();
		sb.append("Not found error!");
		return new DevLibContent(createMultiOptionHelpPage(pathAsNodesList), sb
				.toString());

	}

	private static URL createMultiOptionHelpPage(List<PathNode> pathAsNodesList) {
		String multiHtmlFileName = "multiOptionHelpPage.html";
		String multiHtmlDir = "resources/html/multiOptionHelpPage/";
		IPath multiHtmlPath = null;

		try {

			IPath tempMultiHtmlDir = Activator.getDefault().getStateLocation()
					.append("html").append("multiOptionHelpPage");
			multiHtmlPath = tempMultiHtmlDir.append(multiHtmlFileName);

			URL url = FileLocator.find(Activator.getDefault().getBundle(),
					new Path(multiHtmlDir + multiHtmlFileName), null);
			String content = HTMLContentHelper
					.createMultiOptionHelpPageContent(pathAsNodesList);
			unpackAscii(url, multiHtmlPath, content);

		} catch (Exception e) {
			Logger.logError(e);
		}
		URL result = null;
		try {
			result = URIUtil.toURI(multiHtmlPath).toURL();
		} catch (MalformedURLException murle) {
			Logger.logError(murle);
		}
		return result;
	}

	public static void unpackAscii(URL url, IPath path, String content) {
		try {
			File outputFile = new File(path.toOSString());
			outputFile.getParentFile().mkdirs();
			Reader reader = new InputStreamReader(url.openStream());
			BufferedReader bir = new BufferedReader(reader);
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(outputFile));

			String line;
			while ((line = bir.readLine()) != null) {
				if (content != null && line.equals(INSERT_MARKER)) {
					bos.write(content.getBytes());
				} else {
					bos.write(line.getBytes());
				}
			}
			bir.close();
			bos.close();
		} catch (IOException ioe) {
			throw new HoverException("Can not copy " + url
					+ " with " + path);
		}
	}

	private static void unpackBinary(URL url, IPath path) {
		try {
			File outputFile = new File(path.toOSString());
			outputFile.getParentFile().mkdirs();
			BufferedInputStream bir = new BufferedInputStream(url.openStream());
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(outputFile));

			int len;
			byte buffer[] = new byte[512];

			while ((len = bir.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
			bir.close();
			bos.close();
		} catch (IOException ioe) {
			Logger.logError(ioe);
		}
	}
}