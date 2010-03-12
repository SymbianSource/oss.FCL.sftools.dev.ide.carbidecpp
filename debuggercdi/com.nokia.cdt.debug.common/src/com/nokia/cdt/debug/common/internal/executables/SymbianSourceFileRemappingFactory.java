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
package com.nokia.cdt.debug.common.internal.executables;

import org.eclipse.cdt.core.model.IBinary;
import org.eclipse.cdt.debug.core.executables.ISourceFileRemapping;
import org.eclipse.cdt.debug.core.executables.ISourceFileRemappingFactory;

public class SymbianSourceFileRemappingFactory implements ISourceFileRemappingFactory {

	public ISourceFileRemapping createRemapper(IBinary binary) {
		return new SymbianSourceFileRemapping();
	}

}
