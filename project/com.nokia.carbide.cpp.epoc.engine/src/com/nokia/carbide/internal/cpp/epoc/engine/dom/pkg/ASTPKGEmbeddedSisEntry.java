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
package com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGEmbeddedSisEntry;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTLiteralTextNode;

public class ASTPKGEmbeddedSisEntry extends ASTLiteralTextNode implements IASTPKGEmbeddedSisEntry {
	private static final Pattern embeddedSisPattern = Pattern.compile("@(\"[^\"]+\")");
	
	private String embeddedSis;
	
	public ASTPKGEmbeddedSisEntry(String entry) {
		super(IASTLiteralTextNode.EStyle.RAW);
		this.setValue(entry);
	}
	
	public void setValue(String value) {
		super.setValue(value);
		Matcher matcher = embeddedSisPattern.matcher(value);
		embeddedSis = "";	//$NON-NLS-1$
		if (matcher.matches()) {
			embeddedSis = matcher.group(1).trim();
		}
	}

	public String getEmbeddedSis() {
		return embeddedSis;
	}
	
}
