/**
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

package com.nokia.carbide.remoteconnections.internal.api;

import org.eclipse.jface.resource.ImageDescriptor;

import com.nokia.carbide.remoteconnections.interfaces.IConnection;

/**
 * An extended interface to a connection
 * @since 3.0
 */
public interface IConnection2 extends IConnection {
	
	/**
	 * Whether this connection is dynamic (managed by an automated process)
	 * Dynamic connections are not persisted or user editable.
	 * @return boolean
	 */
	boolean isDynamic();
	
	/**
	 * Sets this connection's dynamic attribute.
	 * @see IConnection2#isDynamic()
	 * @param dynamic boolean
	 */
	void setDynamic(boolean dynamic);
	
	/**
	 * The status of a connection
	 */
	public interface IConnectionStatus {
		enum EConnectionStatus {
			READY, NOT_READY, IN_USE, IN_USE_DISCONNECTED, NONE
		};
		
		EConnectionStatus getEConnectionStatus();
		
		String getShortDescription();
		
		String getLongDescription();
	}
	
	/**
	 * Gets this connection's status
	 * @return IStatus
	 */
	IConnectionStatus getStatus();

	/**
	 * Sets this connection's status
	 * @see IConnection2#getStatus()
	 * @param status IStatus
	 */
	void setStatus(IConnectionStatus status);
	
	/**
	 * A listener for status changes
	 */
	public interface IConnectionStatusChangedListener {
		void statusChanged(IConnectionStatus status);
	}

	/**
	 * Adds a listener for status changes
	 * @param listener IStatusChangedListener
	 */
	void addStatusChangedListener(IConnectionStatusChangedListener listener);
	
	/**
	 * Removes a listener for status changes
	 * @param listener IStatusChangedListener
	 */
	void removeStatusChangedListener(IConnectionStatusChangedListener listener);

	/**
	 * An optional icon representing this connection.
	 * If none is set, the default icon is used.
	 * @return ImageDescriptor
	 */
	ImageDescriptor getImageDescriptor();
	
	/**
	 * Set the image descriptor for this connection.
	 * @see IConnection2#getImageDescriptor()
	 * @param imageDescriptor
	 */
	void setImageDescriptor(ImageDescriptor imageDescriptor);
	
}
