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


package com.nokia.sdt.component.symbian.querycontainment;

import com.nokia.sdt.component.symbian.implementations.IImplementationTypeFactory;
import com.nokia.sdt.datamodel.adapter.IQueryContainment;

public class QueryContainmentImplementationFactory implements IImplementationTypeFactory {

	public Class getInterface() {
		return IQueryContainment.class;
	}

	public Class getCodeImplAdapterClass() {
		return QueryContainmentAdapterCode.class;
	}

	public Class getScriptImplAdapterClass() {
		return QueryContainmentAdapterScript.class;
	}
}
