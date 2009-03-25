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
/**
 * 
 */
package com.nokia.sdt.sourcegen.doms.rss;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.sourcegen.ISourceGenModelGatherer;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import org.eclipse.core.resources.IProject;

/**
 * Represent the RSS information associated with a project.
 * 
 *
 */
public interface IRssProjectInfo {
	/**
	 * Get the project
	 * @return
	 */
	public IProject getProject();
	
	/**
	 * Update model proxies, trying to keep existing ones even if specifiers change.
	 * @param gatherer gatherer for proxies to care about
	 * @param rootModel the root model for the project, if known
	 */
	public void populateProxies(ISourceGenModelGatherer gatherer, IDesignerDataModel rootModel);
	
	/**
	 * Explicitly create and register a proxy for this model.
	 */
	public IRssModelProxy registerProxyFor(IDesignerDataModelSpecifier dmSpec);

	/**
	 * Set the root model proxy (usu. for testing)
	 */
	public void setRootModelProxy(IRssRootModelProxy proxy);

	/**
	 * Add a view model proxy (usu. for testing)
	 */
	public void addViewModelProxy(IRssModelProxy proxy);

	/**
	 * Get the root model proxy, defined as the one
	 * that owns the main resource file.  If there
	 * is no root model proxy, there is no main
	 * resource file.
	 * @return root or null
	 */
	public IRssRootModelProxy getRootModelProxy();
	
	/**
	 * Get the view model proxies, defined as the
	 * ones that generate RSS included in the 
	 * root model. 
	 */
	public IRssModelProxy[] getViewModelProxies();
	
	/**
	 * Get the proxy for the given model specifier, if found.
	 */
	public IRssModelProxy getModelProxy(IDesignerDataModelSpecifier dmSpec);

	/**
	 * Get the proxy for the given data model. 
	 * First, the specifier is checked.  If that fails,
	 * we check for proxies returning this model.
	 */
	public IRssModelProxy getModelProxy(IDesignerDataModel dataModel);
}
