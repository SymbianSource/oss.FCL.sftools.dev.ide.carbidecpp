/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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
 
package com.nokia.carbide.internal.bugreport.model;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.console.*;

import com.nokia.carbide.internal.bugreport.export.IProduct;

/**
 * Handles the way a bug_report line is printed to the console 
 *
 */
public class ConsoleLine implements IHyperlink {

	IProduct product;
	String bugID;
	String showBugUrl;
	
	public ConsoleLine(IProduct prdct, String bugId) {
		product = prdct;
		bugID = bugId;
		showBugUrl = product.getShowBugUrl(bugId);
	}
	
	/**
	 * The text what is to be written to console line. 
	 * @return console line text
	 */
	public String getLineText() {
		return product.getConsoleText(bugID);
	}
	
	/**
	 * Lenght of the link. The length will be the lenght 
	 * of the bug_id.
	 * @return length of the link
	 */
	public int getLinkLength() {
		return bugID.length();
	}
	
	/**
	 * Returns the offset of where the link in this console line 
	 * will start. 
	 * @return offset of the link
	 */
	public int getLinkOffset() {
		String linkText = product.getConsoleText(bugID);
		return linkText.indexOf(bugID);
	}
	
	public void linkActivated() {
		try	{
			IWorkbench workbench = PlatformUI.getWorkbench();   
			IWebBrowser browser = workbench.getBrowserSupport().getExternalBrowser();
			browser.openURL(new java.net.URL(showBugUrl));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void linkEntered() {
	}

	public void linkExited() {
	}
}
