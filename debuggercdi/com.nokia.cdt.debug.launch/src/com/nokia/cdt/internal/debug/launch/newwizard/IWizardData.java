/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.cdt.internal.debug.launch.newwizard;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;

/**
 * The main interface for the wizard data 
 * (implementors will typically also implement section specific interfaces)
 */
public interface IWizardData {

	/**
	 * @return the label for the launch mode (i.e., "Debug" or "Run")
	 */
	String getModeLabel();

	/**
	 * Create a launch configuration from the data
	 * @return ILaunchConfigurationWorkingCopy
	 * @throws CoreException
	 */
	ILaunchConfigurationWorkingCopy createConfiguration() throws CoreException;

	/**
	 * Make and return a copy
	 * @return IWizardData
	 */
	IWizardData copy();

	/**
	 * Set this data from another
	 * @param launchWizardData IWizardData
	 */
	void apply(IWizardData launchWizardData);

	/**
	 * Validate the data
	 * @return IStatus
	 */
	IStatus validate();

	/**
	 * Return true if this launch is for debug 
	 * @return boolean
	 */
	boolean isDebug();

	/**
	 * Get the project for this launch
	 * @return IProject
	 */
	IProject getProject();

}
