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
package com.nokia.sdt.emf.dm.impl;

import com.nokia.sdt.emf.dm.*;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.util.EMap;

public class DefaultStringKeyProvider implements IStringKeyProvider {

	private IDesignerData designerData;
	private String prefix = "STR_";
	private int nextSuffix = 1;
	
	public DefaultStringKeyProvider(IDesignerData designerData) {
		Check.checkArg(designerData);
		this.designerData = designerData;	
	}
	
	private String rootNamePart() {
		INode rootContainer = (INode) designerData.getRootContainers().get(0);
		String name = rootContainer.getName();
		// this can happen in rare cases (localized strings on root application component)
		if (name == null)
			name = ""; //$NON-NLS-1$
		else
			name += "_"; //$NON-NLS-1$
		return name;
	}

	private String assignKey() {
		String result = null;
		ILocalizedStringBundle bundle = designerData.getStringBundle();
		ILocalizedStringTable lst = bundle.addLocalizedStringTable(bundle.getDefaultLanguage());
		EMap localizedMap = lst.getStrings();
		EMap macroMap = designerData.getMacroTable().getStringMacros();
		String base = prefix + rootNamePart();
		
		while (true) {
			String test = base + Integer.toString(nextSuffix++);
			if (!localizedMap.containsKey(test) &&
				!macroMap.containsKey(test)) {
				result = test;
				break;
			}
		}
		return result;
	}

	public String assignLocalizedStringKey() {
		return assignKey();
	}

	public String assignMacroStringKey() {
		return assignKey();
	}

    /* (non-Javadoc)
     * @see com.nokia.sdt.emf.dm.IStringKeyProvider#compareKeys(java.lang.String, java.lang.String)
     */
    public int compareKeys(String key1, String key2) {
        // compare by the prefix and then by the numeric key --
    	// leave user generated keys in the same order
    	if (!key1.startsWith(prefix) || !key2.startsWith(prefix)) {
    		// not both generated keys; presume equal to ensure stable sort
    		return 0;
    	}
    		
        try {
            int kval1 = Integer.parseInt(key1.substring(prefix.length()));
            int kval2 = Integer.parseInt(key2.substring(prefix.length()));
            return kval1 - kval2;
        } catch (NumberFormatException e) {
            // not comparable -- perhaps a user-edited key; leave ordering alone
        	return 0;
        }
    }
}
