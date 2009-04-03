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


package com.nokia.carbide.templatewizard.process;

/**
 * Represents a parameter specified as a &lt;parameter&gt; child element 
 * of a &lt;process&gt; element in a template xml file.<br>
 * An instance of this is created for each &lt;parameter&gt; element,
 * and a list of these is passed as an argument to 
 * {@link IProcess#process(com.nokia.carbide.template.engine.ITemplate, 
 * java.util.List, org.eclipse.core.runtime.IProgressMonitor)}
 * at its execution.
 */
public interface IParameter {
	
	/**
	 * @return the name attribute of this parameter
	 */
	String getName();
	
	/**
	 * @param name
	 * @return the value of this attribute
	 */
	String getAttributeValue(String name);
	
}

