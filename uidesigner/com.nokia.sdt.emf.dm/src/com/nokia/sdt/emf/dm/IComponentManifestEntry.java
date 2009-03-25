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
package com.nokia.sdt.emf.dm;

import org.eclipse.emf.ecore.EObject;
import org.osgi.framework.Version;

/**
 * Records information about a component
 * @model
 */
public interface IComponentManifestEntry extends EObject{

	/**
	 * @model
	 */
	String getComponentID();
	
	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IComponentManifestEntry#getComponentID <em>Component ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component ID</em>' attribute.
	 * @see #getComponentID()
	 * @generated
	 */
	void setComponentID(String value);

	/**
	 * @model
	 */
	Version getVersion();
	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IComponentManifestEntry#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(Version value);

}
