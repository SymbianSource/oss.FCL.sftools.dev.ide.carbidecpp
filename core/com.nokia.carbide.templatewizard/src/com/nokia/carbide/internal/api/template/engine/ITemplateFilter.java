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


package com.nokia.carbide.internal.api.template.engine;

import com.nokia.carbide.template.engine.ITemplate;

/**
 * Implement this interface to filter templates via {@link TemplateEngine#getFilteredTemplates(ITemplateFilter)}
 *
 */
public interface ITemplateFilter {

	/**
	 * Tell whether to accept the given template
	 * @param template
	 * @return true: take template, false: ignore template
	 */
	boolean accept(ITemplate template);

}
