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

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import java.util.List;

/**
 * This interface can be implemented to add arbitrary code-based 
 * wizard pages to a wizard specified by a template.
 * <p>
 * Classes implementing this interface must have a 0-argument public constructor,
 * and are only constructed once per template per Eclipse session. 
 * <p>
 * These classes will be initialized and used as many times as the template is chosen by the users.
 * The pages returned by implementations of this interface will be displayed just before 
 * any pages declared as &lt;wizardPage&gt; elements in the template xml file.
 */
public interface IExtraPagesProvider {
	
    /**
     * Creates template-specific additional pages for wizard to add before data-driven pages.<br>
     * Parameters are those used to initialize the IWorkbenchWizard.
     * @see org.eclipse.ui.IWorkbenchWizard.init(IWorkbench workbench, IStructuredSelection selection)
     * 
     * @param wizard the wizard requesting the pages
     * @param workbench the current workbench
     * @param selection the current object selection
     */
	void init(IWorkbenchWizard wizard, IWorkbench workbench, IStructuredSelection selection);
	
    /**
     * Returns the extra wizard pages.<br>
     * Subsequent calls between calls to 
     * {@link IExtraPagesProvider#init(IWorkbenchWizard, IWorkbench, IStructuredSelection)} 
     * will expect to get the same page instances.
     * @param wizard the wizard that requested creation of the pages
     * @return List of IWizardDataPage
     */
	List<IWizardDataPage> getPages(IWorkbenchWizard wizard);
}
