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

import com.nokia.sdt.component.adapter.IComponentAdapter;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.sourcegen.doms.rss.IRssModelGenerator;

/**
 * This interface defines a source mapping, whereby component
 * instances are transformed into RSS DOM, and
 * RSS DOM into component instances.  This is inherently
 * a two-way mapping, although the specific rules used to
 * define a mapping may need special handling for each
 * direction (i.e. resulting in a pair of one-way mappings).
 * 
 * 
 *
 */
public interface IComponentSourceMapping extends IComponentAdapter {
    /**
     * The maximum number of times to rename an identifier to
     * avoid conflicts with read-only or pre-existing but 
     * differently-typed identifiers with the same base name
     * (e.g. for r_rsrc: r_rsrc_1, r_rsrc_2, ...)  
     */
    public static final int MAX_NAME_CONFLICTS = 100;
    
    /**
     * This algorithm is used (via &lt;mapEnum &gt; nameAlgorithm="...")
     * to name Command enums
     */
    public static final Object NAME_ALGORITHM_COMMANDS = "com.nokia.sdt.component.symbian.NAME_ALG_COMMANDS"; //$NON-NLS-1$

    /**
     * This algorithm is used (via &lt;mapEnum &gt; nameAlgorithm="...")
     * to get the identifier of the dialog-control-type attribute
     */
    public static final Object NAME_ALGORITHM_CONTROL_TYPE = "com.nokia.sdt.component.symbian.NAME_ALG_CONTROL_TYPE"; //$NON-NLS-1$

    /**
     * This algorithm is used (via &lt;mapEnum &gt; nameAlgorithm="...")
     * to create an enum for a dialog line entry
     */
    public static final Object NAME_ALGORITHM_DIALOG_LINE_ID = "com.nokia.sdt.component.symbian.NAME_ALG_DIALOG_LINE_ID"; //$NON-NLS-1$

    /**
     * This algorithm is used (via &lt;mapEnum &gt; nameAlgorithm="...")
     * to create an enum for a status pane
     */
    public static final Object NAME_ALGORITHM_STATUS_PANE_ID = "com.nokia.sdt.component.symbian.NAME_ALG_STATUS_PANE_ID"; //$NON-NLS-1$

    /**
     * This algorithm is used (via &lt;mapEnum &gt; nameAlgorithm="...")
     * to create an enum for the view UID
     */
    public static final Object NAME_ALGORITHM_VIEW_UID = "com.nokia.sdt.component.symbian.NAME_ALG_VIEW_UID"; //$NON-NLS-1$
    
    /** 
     * Export the component instance, creating or updating 
     * resources in the translation unit
     * @param instance the component instance to export
     * @param generator
     */
    public void exportInstance(IRssModelGenerator generator, IComponentInstance instance);
}
