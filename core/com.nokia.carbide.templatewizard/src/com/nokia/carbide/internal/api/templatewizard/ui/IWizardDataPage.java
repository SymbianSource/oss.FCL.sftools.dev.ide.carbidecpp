/*
* Copyright (c) 2006, 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.internal.api.templatewizard.ui;

import org.eclipse.jface.wizard.IWizardPage;

import java.util.Map;

/**
 * An extended interface for {@link IWizardPage} that provides the data
 * collected in the page as a {@link Map} of values indexed by field identifier.
 * <p>
 * These may be template wizard pages or pages which otherwise wish to contribute 
 * values to a template process. 
 */
public interface IWizardDataPage extends IWizardPage {
	
	/**
	 * Returns a <code>java.util.Map<String, Object></code> of values indexed by field identifier.
	 * @return <code>java.util.Map<String, Object></code>
	 */
	Map<String, Object> getPageValues();
	
}
