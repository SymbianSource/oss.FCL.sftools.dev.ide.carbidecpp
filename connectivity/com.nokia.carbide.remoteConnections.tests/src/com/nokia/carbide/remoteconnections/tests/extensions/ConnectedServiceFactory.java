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

package com.nokia.carbide.remoteconnections.tests.extensions;

import com.nokia.carbide.remoteconnections.interfaces.*;

import java.util.*;


public class ConnectedServiceFactory implements IConnectedServiceFactory {

	public IConnectedService createConnectedService(IService service, IConnection connection) {
		if (service instanceof RandomCycleService)
			return new RandomCycleConnectedService(service, connection);
		else if (service instanceof UnknownStatusService) {
			return ((UnknownStatusService) service).createInstance(connection);
		}
		return null;
	}

	public Collection<String> getCompatibleConnectionTypeIds(IService service) {
		if (service instanceof RandomCycleService)
			return Collections.singleton(IntervalConnectionType.class.getName());
		else if (service instanceof UnknownStatusService) {
			String[] ids = {IntervalConnectionType.class.getName(),
					NoSettingsConnectionType.class.getName()};
			return Arrays.asList(ids);
		}
		return Collections.EMPTY_LIST;
	}

}
