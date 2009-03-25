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
package com.nokia.carbide.cpp.sysdoc.internal.hover.dal.interX;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.xml.sax.Attributes;

import com.nokia.carbide.cpp.sysdoc.internal.hover.core.MessagesConstants;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverConstants.LICENCE;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;

/**
 * 
 * Class holds general information about an interchange file such as root
 * directory, version, average object length.
 * 
 * Note: Developer Libraries versions in interchange file does not provide a
 * natural order due to naming/versioing confusing. For example, among following
 * versions number "GT_tb91","GT_9.5", "GT_9.4", it is not possible to figure
 * out the latest version number is "GT_tb91" which is SF Day 1 developer
 * library without a lookup table approach. Therefore all version is taken as
 * 1.0 until an universal versioning schema is proposed. Thats leads auto
 * selection developer library heuristic based on licence and date generation
 * for time being.
 * 
 */
public class InterXProperties {

	public static final String INTERX_ATTR_ROOT_DIR = "root_dir";
	public static final String INTERX_ATTR_CONFIGURATION = "configuration";
	public static final String INTERX_ATTR_OBJ = "Obj";
	public static final String INTERX_ATTR_PATH = "path";
	public static final String INTERX_ATTR_ID = "id";
	public static final String INTERX_ATTR_COMPONENT = "Component";
	public static final String INTERX_ATTR_COMPONENT_NAME = "name";
	// publishedAll, publicAll, ...
	public static final String INTERX_ATTR_IM_RANGE = "IM_range";
	public static final String INTERX_ATTR_OBJ_COUNT = "objCount";
	public static final String INTERX_ATTR_DATE_GENERATED = "date";
	public static final String INTERX_ATTR_USER_FRIENDLY_NAME = "label";
	public static final String INTERCHANGEFILE_ELEMENT_SYSTEMWIDELINKS = "SystemWideLinks";

	private URL interXURL;
	private String rootDir;
	protected double version;
	protected LICENCE licence;
	protected Date dateGenerated;
	private Integer objCount; // read from interchange
	private Integer objDefaultCount = 100000; // assume that
	private String userFriendlyName;
	private boolean valid = false;

	public InterXProperties(URL interXFileName) {
		this.interXURL = interXFileName;
	}

	public void extractAttributes(final Attributes attributes) {
		rootDir = attributes.getValue(INTERX_ATTR_ROOT_DIR);
		/*
		 * String configurationStr = attributes
		 * .getValue(INTERX_ATTR_CONFIGURATION); configurationStr =
		 * configurationStr.replace("GT_", ""); try { int indexOfDot =
		 * configurationStr.indexOf("."); if (indexOfDot==-1){
		 * 
		 * } configurationStr = configurationStr.replace(".", "");
		 * configurationStr = configurationStr.substring(0, indexOfDot) + "." +
		 * configurationStr.substring(indexOfDot); version =
		 * Double.parseDouble(configurationStr); } catch (Exception e) {
		 * Logger.logWarn("Can not optain version number for " + interXURL +
		 * " As default 1.0 will be used as version"); version = 1.0; }
		 */

		/*
		 * Note: Developer Libraries versions in interchange file does not provide a
		 * natural order due to naming/versioing confusing. For example, among following
		 * versions number "GT_tb91","GT_9.5", "GT_9.4", it is not possible to figure
		 * out the latest version number is "GT_tb91" which is SF Day 1 developer
		 * library without a lookup table approach. Therefore all version is taken as
		 * 1.0 until an universal versioning schema is proposed. Thats leads auto
		 * selection developer library heuristic based on licence and date generation
		 * for time being.
		 */

		version = 1.0;
		String im_range = attributes.getValue(INTERX_ATTR_IM_RANGE);
		licence = LICENCE.getLicenceType(im_range);

		String objLenStr = attributes.getValue(INTERX_ATTR_OBJ_COUNT);
		try {
			objCount = Integer.parseInt(objLenStr);
		} catch (Exception e) {
			objCount = objDefaultCount;
		}

		String datePublishedStr = attributes
				.getValue(INTERX_ATTR_DATE_GENERATED);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dateGenerated = format.parse(datePublishedStr);
		} catch (Exception e) {
			Logger.logWarn(MessagesConstants.NOT_VALID_TIME_FORMAT + ":"
					+ interXURL);
			dateGenerated = new Date(0);
		}
		userFriendlyName = attributes.getValue(INTERX_ATTR_USER_FRIENDLY_NAME);
		if (userFriendlyName == null) {
			if (interXURL.toString().contains("_public_")) {
				userFriendlyName = "Developer Library " + version + " Public ";
			} else {
				userFriendlyName = "Developer Library " + version + " Platform";
			}
		}
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		userFriendlyName += " (" + dateFormat.format(dateGenerated) + ")";
		checkValid();
	}

	public double getVersion() {
		return version;
	}

	public String getRootDir() {
		return rootDir;
	}

	public LICENCE getLicence() {
		return licence;
	}

	public URL getInterXURL() {
		return interXURL;
	}

	public Integer getObjCount() {
		return objCount;
	}

	public Date getDateGenerated() {
		return dateGenerated;
	}

	public String getUserFriendlyName() {
		return userFriendlyName;
	}

	public boolean isValid() {
		return valid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateGenerated == null) ? 0 : dateGenerated.hashCode());
		result = prime * result + ((licence == null) ? 0 : licence.hashCode());
		long temp;
		temp = Double.doubleToLongBits(version);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InterXProperties other = (InterXProperties) obj;
		if (dateGenerated == null) {
			if (other.dateGenerated != null)
				return false;
		} else if (!dateGenerated.equals(other.dateGenerated))
			return false;
		if (licence == null) {
			if (other.licence != null)
				return false;
		} else if (!licence.equals(other.licence))
			return false;
		if (Double.doubleToLongBits(version) != Double
				.doubleToLongBits(other.version))
			return false;
		return true;
	}

	/**
	 * check if interchange properties hold valid values
	 */
	private void checkValid() {
		if (rootDir != null && version != 0 && objCount > 0
				&& this.dateGenerated != null && this.userFriendlyName != null) {
			valid = true;
		} else {
			valid = false;
		}
	}

	@Override
	public String toString() {
		String str = userFriendlyName + " " + version + " " + licence + " "
				+ dateGenerated.toString();
		return str;
	}
}
