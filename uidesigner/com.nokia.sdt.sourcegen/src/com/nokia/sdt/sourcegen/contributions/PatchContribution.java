/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.sourcegen.contributions;

import com.nokia.cpp.internal.api.utils.core.Check;

import org.osgi.framework.Version;

import java.util.regex.Matcher;

/**
 * A contribution identified as being used for patches
 * 
 *
 */
public class PatchContribution {
	private IContribution contrib;
	private Version fromVersion;
	private Version toVersion;

	/**
	 * Tell whether the contribution is intended for patching.
	 * @param contrib
	 * @return
	 */
	static boolean isPatchContribution(IContribution contrib) {
		String mode = contrib.getMode();
		if (mode == null)
			return false;
		return IContribution.MODE_UPGRADE_PATTERN.matcher(mode).matches();
	}
	
	/**
	 * 
	 */
	public PatchContribution(IContribution contrib) {
		this.contrib = contrib;
		Matcher matcher = IContribution.MODE_UPGRADE_PATTERN.matcher(contrib.getMode());
		Check.checkArg(matcher.matches());
		this.fromVersion = new Version(matcher.group(1));
		this.toVersion = new Version(matcher.group(2));
	}

	/**
	 * Get the contribution
	 * @return
	 */
	public IContribution getContribution() {
		return contrib;
	}
	
	/**
	 * Get the "from" version
	 */
	public Version getFromVersion() {
		return fromVersion;
	}

	/**
	 * Get the "to" version
	 */
	public Version getToVersion() {
		return toVersion;
	}
}
