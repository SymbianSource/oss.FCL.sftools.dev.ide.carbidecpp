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
 
package com.nokia.carbide.internal.bugreport.export;

import java.util.*;
import org.eclipse.swt.widgets.Shell;

/**
 * Interface for a product plug-in. 
 *
 */
public interface IProduct {
	
	/**
	 * This method should return the name for the product in BugZilla.
	 * @return the product name
	 */
	String getProductName();
	
	/**
	 * These fields are added to the bug_report which is sent to the server.
	 * Don't use this for attachments, use getAttachments() instead.
	 * 
	 * E.g	'cf_priority'-'normal'
	 * 		'version'-'v1.2.2'
	 * 
	 * @return field-value pairs which are sent to the server
	 */
	Hashtable<String, String> getFields();
	
	/**
	 * If product wants to send attachment files, provide file paths
	 * with this method. Do not try to add attachment with getFields().
	 * 
	 * @return null if no attachments is to be sent, otherwise return 
	 * a string array containing file paths to attachment file to be sent.
	 */
	String[] getAttachments();
	
	/**
	 * This method should return the maximum length for the summary field in 
	 * the bug_reporter wizard UI.
	 * @return maximum length for the bug_summary.
	 */
	int getMaxSummaryLength();
	
	/**
	 * This method should return the maximum length for the description field 
	 * in the bug_reporter wizard UI.
	 * @return maximum length for the bug_description
	 */
	int getMaxDescriptionLength();
	
	/**
	 * This method should return the maximum size for the attachment file 
	 * in bytes. 
	 * @return maximum attachment file size in bytes.
	 */
	long getMaxAttachmentSize();
	
	/**
	 * This method is called when user presses next button in bug_description 
	 * page in the wizard.    
	 * @return if returns true, UI service is needed -> wizard does not move 
	 * to next page, but instead calls getUiServiceText and after that possibly 
	 * showUiService. If returns false, wizard moves from description page to 
	 * summary page.
	 */
	boolean uiServiceNeeded();
	
	/**
	 * This text is shown to user if UI service is needed (uiServiceNeeded() 
	 * returns true). This text is placed on a yes,no question dialog, so 
	 * that user can be queried e.g. "password not set, would you like to 
	 * set one now?". If user answers yes, to this question, showUiService() 
	 * method is called next.
	 * @return ui service query text.
	 */
	String getUiServiceText();
	
	/**
	 * Called when some ui service is needed from the plug-in. When this is 
	 * called, product plug-in can e.g. show a preference page.
	 * @see uiServiceNeeded, getUiServiceText
	 * @param shell shell for the UI component
	 */
	void showUiService(Shell shell);
	
	/**
	 * Bug_description wizard page can show a link to plugin UI service (e.g 
	 * preference page). Link is shown if this method returns the text what 
	 * is shown in the link.
	 * @return "" if no link is to be shown, otherwise link text
	 */
	String getUiServiceLinkText();
	
	/**
	 * This method should return the page title for the bug_description page 
	 * in the wizard.
	 * @return page title for bug_description page.
	 */
	String getPageTitleText();
	
	/**
	 * This method should return the page description for the bug_description 
	 * page in the wizard.
	 * @return page description for bug_description page.
	 */
	String getPageDescriptionText();
	
	/**
	 * This method should return the text for the description label in the 
	 * bug_description wizard page.
	 * @return text for description label.
	 */
	String getDescriptionLabelText();
	
	/**
	 * This method should return the URL of the server where bug_reports are sent.
	 * @return URL of the server.
	 */
	String getUrl();
	
	/**
	 * When a bug_entry is added, we will write a message to the Bug_Reports console. 
	 * This method should return a link to the added url.
	 * @param bugId the bug_entry which was added
	 * @return url for the added bug_entry in BugZilla.
	 */
	String getShowBugUrl(String bugId);
	
	/**
	 * When a bug_entry is added, we will write a message to the Bug_Report console.
	 * This method should return the text which is shown in the console.
	 * @param bugId the bug_entry which was added
	 * @return text for the added bug_entry for the console
	 */
	String getConsoleText(String bugId);
	
}
