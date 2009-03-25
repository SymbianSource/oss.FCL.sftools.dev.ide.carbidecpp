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

package com.nokia.carbide.cpp.internal.codescanner.kb;

import java.util.ArrayList;

import org.osgi.framework.Version;

/**
 * A class for handling a CodeScanner knowledge base rule.
 *
 */
public class CSKbRule {

	// private members
	private String name;
	private ArrayList<String> filetypes;
	private ArrayList<CSKbRuleKeyword> keywords;
	private String severity;
	private String title;
	private String description;
	private String link;
	private ArrayList<Version> platforms;
	private String pluginID;

	/**
	 * Constructor.
	 */
	public CSKbRule() {
		name = null;
		filetypes = null;
		keywords = null;
		severity = null;
		title = null;
		description = null;
		link = null;
		platforms = null;
		pluginID = null;
	}

	/**
	 * Constructor.
	 */
	public CSKbRule(String name, 
					   ArrayList<String> filetypes,
					   ArrayList<CSKbRuleKeyword> keywords,
					   String severity,
					   String title,
					   String description,
					   String link,
					   ArrayList<Version> platforms,
					   String pluginID) {
		this.name = name;
		this.filetypes = filetypes;
		this.keywords = keywords;
		this.severity = severity;
		this.title = title;
		this.description = description;
		this.link = link;
		this.platforms = platforms;
		this.pluginID = pluginID;
	}
	
	/**
	 * Retrieve description of a knowledge base rule.
	 * @return description of a knowledge base rule
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Retrieve file types of a knowledge base rule.
	 * @return file types of a knowledge base rule
	 */
	public ArrayList<String> getFileTypes() {
		return filetypes;
	}

	/**
	 * Retrieve keywords of a knowledge base rule.
	 * @return keywords of a knowledge base rule
	 */
	public ArrayList<CSKbRuleKeyword> getKeywords() {
		return keywords;
	}

	/**
	 * Retrieve external link of a knowledge base rule.
	 * @return external link of a knowledge base rule
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Retrieve name of a knowledge base rule.
	 * @return name of a knowledge base rule
	 */
	public String getName() {
		return name;
	}

	/**
	 * Retrieve platforms of a knowledge base rule.
	 * @return platforms of a knowledge base rule
	 */
	public ArrayList<Version> getPlatforms() {
		return platforms;
	}

	/**
	 * Retrieve plugin ID of a knowledge base rule.
	 * @return plugin ID of a knowledge base rule
	 */
	public String getPluginId() {
		return pluginID;
	}

	/**
	 * Retrieve severity of a knowledge base rule.
	 * @return severity of a knowledge base rule
	 */
	public String getSeverity() {
		return severity;
	}

	/**
	 * Retrieve title of a knowledge base rule.
	 * @return title of a knowledge base rule
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set description of a knowledge base rule.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Set file types of a knowledge base rule.
	 */
	@SuppressWarnings("unchecked")
	public void setFileTypes(ArrayList<String> filetypes) {
		if (filetypes == null) {
			this.filetypes = null;
		}
		else {
			Object object = filetypes.clone();
			if (object instanceof ArrayList) {
				this.filetypes = (ArrayList<String>)object;
			}
		}
	}

	/**
	 * Set keywords of a knowledge base rule.
	 */
	@SuppressWarnings("unchecked")
	public void setKeywords(ArrayList<CSKbRuleKeyword> keywords) {
		if (keywords == null) {
			this.keywords = null;
		}
		else {
			Object object = keywords.clone();
			if (object instanceof ArrayList) {
				this.keywords = (ArrayList<CSKbRuleKeyword>)object;
			}
		}
	}

	/**
	 * Set external link of a knowledge base rule.
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * Set name of a knowledge base rule.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Set platforms of a knowledge base rule.
	 */
	@SuppressWarnings("unchecked")
	public void setPlatforms(ArrayList<Version> platforms) {
		if (platforms == null) {
			this.platforms = null;
		}
		else {
			Object object = platforms.clone();
			if (object instanceof ArrayList) {
				this.platforms = (ArrayList<Version>)object;
			}
		}
	}

	/**
	 * Set plugin ID of a knowledge base rule.
	 */
	public void setPluginId(String pluginID) {
		this.pluginID = pluginID;
	}

	/**
	 * Set severity of a knowledge base rule.
	 */
	public void setSeverity(String severity) {
		this.severity = severity;
	}

	/**
	 * Set title of a knowledge base rule.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
