/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.component.property;

import com.nokia.sdt.datamodel.IModelMessage;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.Collection;

/**
 * This interface extends {@link ICompoundPropertyValueConverter} by
 * adding validation support.
 * 
 *
 */
public interface ICompoundPropertyValueConverter2 extends
		ICompoundPropertyValueConverter {

	/** Validate the property source, adding any messages to the list */
	void validate(Collection<IModelMessage> messages,
			IPropertyDescriptor pd,
			IPropertySource ps);

}
