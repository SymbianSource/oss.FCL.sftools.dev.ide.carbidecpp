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

package com.nokia.sdt.sourcegen;

/**
 * Default source formatting rules
 * 
 *
 */
public class DefaultSourceFormatting implements ISourceFormatting {
	private String eol = "\n"; //$NON-NLS-1$ 

	public String getEOL() {
		return eol;
	}
	
	public void setEOL(String eol) {
		if (eol == null)
			eol = "\n";
		this.eol = eol;
	}
	
    public boolean isUsingTabs() {
        return true;
    }

    public int getIndentSpaces() {
        return 4;
    }
}
