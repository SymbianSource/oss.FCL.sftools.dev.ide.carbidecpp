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

package com.nokia.sdt.sourcegen;

import java.util.List;

/**
 * Information about a sourcegen template group.
 * 
 *
 */
public interface ISourceGenTemplateGroup {
    /** Get the id of the template group */
    String getId();
    
    /** Get the template forms key (or null) */
    String getForms();

   /** Get the list of named templates included (ISourceGenTemplate);
    * these have all the derived attributes applied
    */
    List<ISourceGenTemplate> getTemplates();
}
