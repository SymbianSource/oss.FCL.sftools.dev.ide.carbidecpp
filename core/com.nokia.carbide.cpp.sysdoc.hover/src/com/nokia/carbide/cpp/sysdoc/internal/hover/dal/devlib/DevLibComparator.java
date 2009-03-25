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
package com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib;

import java.util.Comparator;

public class DevLibComparator implements Comparator<DevLibProperties> {

	public int compare(DevLibProperties o2, DevLibProperties o1) {

		if (o1 == o2) {
			return 0;
		}

		double version = o1.getInterXProperties().getVersion()
				- o2.getInterXProperties().getVersion();

		if (version != 0) {
			return (int) Math.signum(Double.doubleToLongBits(version));
			// return (int)Math.floor(version);
		}

		int licence = o1.getInterXProperties().getLicence().compareTo(
				o2.getInterXProperties().getLicence());
		if (licence != 0) {
			return licence;
		}

		long time = o1.getInterXProperties().getDateGenerated().getTime()
				- o2.getInterXProperties().getDateGenerated().getTime();
		return (int) (Math.signum(time));
	}
}
