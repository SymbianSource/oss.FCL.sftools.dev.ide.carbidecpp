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

import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.nokia.carbide.cpp.sysdoc.hover.BaseTest;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverConstants.LICENCE;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.interX.InterXProperties;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;

public class DevLibComparatorTest extends BaseTest {

	public void testComprator() {
		DevLibComparator devLibComparator = new DevLibComparator();
		Set<DevLibProperties> devLibProps = new TreeSet<DevLibProperties>(
				devLibComparator);

		InterXProperties interXProperties = new InterXPropertiesHelp(9.5,
				LICENCE.PLATFORM, new Date(1000));
		DevLibProperties devLib = new DevLibPropertiesHelper(interXProperties);
		devLibProps.add(devLib);

		interXProperties = new InterXPropertiesHelp(9.6, LICENCE.PLATFORM,
				new Date(3000));
		DevLibProperties devLib2 = new DevLibPropertiesHelper(interXProperties);
		devLibProps.add(devLib2);

		assertFalse(devLib.equals(devLib2));

		interXProperties = new InterXPropertiesHelp(9.5, LICENCE.PLATFORM,
				new Date(2000));
		DevLibProperties devLib3 = new DevLibPropertiesHelper(interXProperties);
		devLibProps.add(devLib3);

		assertFalse(devLib.equals(devLib3));

		interXProperties = new InterXPropertiesHelp(9.5, LICENCE.PUBLIC,
				new Date(2000));
		DevLibProperties devLib4 = new DevLibPropertiesHelper(interXProperties);
		devLibProps.add(devLib4);

		interXProperties = new InterXPropertiesHelp(9.5, LICENCE.PUBLIC,
				new Date(2000));
		DevLibProperties devLib41 = new DevLibPropertiesHelper(interXProperties);
		devLibProps.add(devLib41);

		assertTrue(devLib4.equals(devLib41));

		interXProperties = new InterXPropertiesHelp(9.5, LICENCE.PUBLIC,
				new Date(1000));
		DevLibProperties devLib5 = new DevLibPropertiesHelper(interXProperties);
		devLibProps.add(devLib5);

		interXProperties = new InterXPropertiesHelp(9.6, LICENCE.PLATFORM,
				new Date(2000));
		DevLibProperties devLib6 = new DevLibPropertiesHelper(interXProperties);
		devLibProps.add(devLib6);

		interXProperties = new InterXPropertiesHelp(9.6, LICENCE.PUBLIC,
				new Date(1000));
		DevLibProperties devLib7 = new DevLibPropertiesHelper(interXProperties);
		devLibProps.add(devLib7);

		interXProperties = new InterXPropertiesHelp(1.0, LICENCE.NONE,
				new Date(0));
		DevLibProperties tempDevLib = new DevLibPropertiesHelper(
				interXProperties);

		Logger.logDebug(devLibProps.toString());
		Iterator<DevLibProperties> it = devLibProps.iterator();

		while (it.hasNext()) {
			DevLibProperties next = it.next();
			int res = devLibComparator.compare(tempDevLib, next);
			assertTrue(res > 0);
		}
	}

	class InterXPropertiesHelp extends InterXProperties {

		public InterXPropertiesHelp(URL interXFileName) {
			super(interXFileName);
		}

		public InterXPropertiesHelp(double version, LICENCE licence, Date date) {
			super(null);
			super.version = version;
			super.dateGenerated = date;
			super.licence = licence;
		}
	}

	class DevLibPropertiesHelper extends DevLibProperties {

		public DevLibPropertiesHelper(InterXProperties interXProperties) {
			super();

			super.interXProperties = interXProperties;
		}
	}
}
