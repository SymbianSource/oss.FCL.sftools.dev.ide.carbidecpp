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
package com.nokia.carbide.internal.cpp.epoc.engine;

import org.eclipse.jface.text.Document;

public class ModelDocument extends Document {

	public ModelDocument() {
	}

	public ModelDocument(String initialContent) {
		super(initialContent);
	}

	@Override
	public String toString() {
		return "Document w/" + getLength() + " chars:\n" + get(); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
