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

package com.nokia.carbide.cpp.internal.codescanner.markers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.internal.codescanner.CSPlugin;
import com.nokia.carbide.cpp.internal.codescanner.config.CSRule;
import com.nokia.carbide.cpp.internal.codescanner.config.CSScript;
import com.nokia.carbide.cpp.internal.codescanner.kb.CSKbRule;

/**
 * A class to display CodeScanner marker details as a quick-fix.
 */
public class CSMarkerResolution implements IMarkerResolution2 {

	private String description;
	private String label;
	private String reference;
	private String ruleName;
	private Pattern msgattern = Pattern.compile("(.*):(.*):(.*)");

	/**
	 * Constructor.
	 * @param marker - the marker to resolve
	 */
	public CSMarkerResolution (IMarker marker) {
		String message = marker.getAttribute(IMarker.MESSAGE, null);
		Matcher msgMatcher = msgattern.matcher(message);
		if (msgMatcher.matches()) {
			label = msgMatcher.group(3);
		}
		ruleName = marker.getAttribute(CSMarker.CS_MARKER_RULE_NAME, null);
		if (ruleName != null) {
			CSScript script = CSScript.toScript("script_" + ruleName);
			if (script != CSScript.script_unknown) {
				// existing CodeScanner rule.
	    		CSRule rule = new CSRule(script, null, null, true);
	    		description = rule.getDetails();
			}
			else {
				// try looking for a match from the knowledge base rules
    			CSKbRule rule = CSPlugin.getKbManager().getRule(ruleName);
    			if (rule == null) {
    				CSPlugin.getKbManager().clearRules();
    				CSPlugin.getKbManager().readRulesFromPlugins();
    				rule = CSPlugin.getKbManager().getRule(ruleName);
    			}
    			if (rule != null) {
    				description = rule.getDescription();
    				reference = rule.getLink();
    			}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IMarkerResolution2#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IMarkerResolution2#getImage()
	 */
	public Image getImage() {
		try {
			String iconPath = "icons/NoReference.png"; //$NON-NLS-1$
			if (reference != null) {
				iconPath = "icons/Reference.png"; //$NON-NLS-1$
			}
			URL installURL = CSPlugin.getDefault().getBundle().getEntry("/"); //$NON-NLS-1$
			URL url = new URL(installURL, iconPath);
			return ImageDescriptor.createFromURL(url).createImage();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IMarkerResolution#getLabel()
	 */
	public String getLabel() {
		return label;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IMarkerResolution#run(org.eclipse.core.resources.IMarker)
	 */
	public void run(IMarker marker) {
		if (reference != null) {
			try {
				URL url = new URL(reference);
				PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(url);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
