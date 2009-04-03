/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

import java.util.*;

/**
 * 
 *
 */
public class DomainRegistry {

    private Map domainMap;
    
    /**
     */
    public DomainRegistry() {
        this.domainMap = new HashMap();
    }

    public void registerDomain(String name, IDomain domain) {
        Check.checkArg(name);
        Check.checkArg(domain);
        Check.checkArg(domainMap.get(name) == null);
        
        domainMap.put(name, domain);
    }
    
    public IDomain getDomain(String domain) {
        Check.checkArg(domain);
        return (IDomain) domainMap.get(domain);
    }

    /**
     * 
     */
    public void reset() {
        for (Iterator iter = domainMap.values().iterator(); iter.hasNext();) {
            IDomain domain = (IDomain) iter.next();
            domain.reset();
        }
    }

	/**
	 * @return
	 */
	public Iterator iterator() {
		return domainMap.values().iterator();
	}
}
