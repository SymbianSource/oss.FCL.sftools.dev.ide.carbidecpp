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

package com.nokia.carbide.remoteconnections.internal.ui;

import java.util.Collection;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus.EStatus;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2.IConnectionStatus;
import com.nokia.carbide.remoteconnections.internal.api.IConnection2.IConnectionStatus.EConnectionStatus;
import com.nokia.carbide.remoteconnections.internal.registry.Registry;

/**
 * 
 */
public class ConnectionUIUtils {

	private static final ImageDescriptor STATUS_AVAIL_IMGDESC = 
	RemoteConnectionsActivator.getImageDescriptor("icons/statusAvailable.png"); //$NON-NLS-1$
	private static final ImageDescriptor STATUS_UNAVAIL_IMGDESC = 
	RemoteConnectionsActivator.getImageDescriptor("icons/statusUnavailable.png"); //$NON-NLS-1$
	private static final ImageDescriptor STATUS_UNK_IMGDESC = 
	RemoteConnectionsActivator.getImageDescriptor("icons/statusUnknown.png"); //$NON-NLS-1$
	private static final ImageDescriptor STATUS_INUSE_IMGDESC =
		RemoteConnectionsActivator.getImageDescriptor("icons/statusInUse.png"); //$NON-NLS-1$
	
	private static final ImageDescriptor CONNECTION_READY_IMGDESC = 
		RemoteConnectionsActivator.getImageDescriptor("icons/connectionStatusReady.png"); //$NON-NLS-1$
	private static final ImageDescriptor CONNECTION_IN_USE_IMGDESC = 
		RemoteConnectionsActivator.getImageDescriptor("icons/connectionStatusInUse.png"); //$NON-NLS-1$
	private static final ImageDescriptor CONNECTION_NOT_READY_IMGDESC = 
		RemoteConnectionsActivator.getImageDescriptor("icons/connectionStatusNotReady.png"); //$NON-NLS-1$
	private static final ImageDescriptor CONNECTION_IN_USE_DISCONNECTED_IMGDESC =
		RemoteConnectionsActivator.getImageDescriptor("icons/connectionStatusInUseDisconnected.png"); //$NON-NLS-1$
	
	public static final ImageDescriptor CONNECTION_IMGDESC = 
		RemoteConnectionsActivator.getImageDescriptor("icons/connection.png"); //$NON-NLS-1$
	
	private static final Image STATUS_AVAIL_IMG = STATUS_AVAIL_IMGDESC.createImage();
	private static final Image STATUS_UNAVAIL_IMG = STATUS_UNAVAIL_IMGDESC.createImage();
	private static final Image STATUS_INUSE_IMG = STATUS_INUSE_IMGDESC.createImage();
	private static final Image STATUS_UNK_IMG = STATUS_UNK_IMGDESC.createImage();

	private static final Image CONNECTION_READY_IMG = CONNECTION_READY_IMGDESC.createImage(); 
	private static final Image CONNECTION_IN_USE_IMG =  CONNECTION_IN_USE_IMGDESC.createImage();
	private static final Image CONNECTION_NOT_READY_IMG = CONNECTION_NOT_READY_IMGDESC.createImage();
	private static final Image CONNECTION_IN_USE_DISCONNECTED_IMG = CONNECTION_IN_USE_DISCONNECTED_IMGDESC.createImage();

	private static final Image CONNECTION_IMG = CONNECTION_IMGDESC.createImage();
	
	public static final Color COLOR_RED = new Color(Display.getDefault(), 192, 0, 0);
	public static final Color COLOR_GREEN = new Color(Display.getDefault(), 0, 128, 0);
	public static final Color COLOR_ELECTRIC = new Color(Display.getDefault(), 0, 0, 255);
	public static final Color COLOR_GREY = new Color(Display.getDefault(), 96, 96, 96);
	public static final Color COLOR_HYPERLINK = JFaceColors.getHyperlinkText(Display.getDefault());
	
	/**
	 * Get the image representing the connection status.
	 * @param connection
	 * @return Image, not to be disposed
	 */
	public static Image getConnectionStatusImage(IConnectionStatus status) {
		if (status != null) {
			EConnectionStatus severity = status.getEConnectionStatus();
			
			switch (severity) {
			case READY:
				return CONNECTION_READY_IMG;
			case IN_USE:
				return CONNECTION_IN_USE_IMG;
			case NOT_READY:
				return CONNECTION_NOT_READY_IMG;
			case IN_USE_DISCONNECTED:
				return CONNECTION_IN_USE_DISCONNECTED_IMG;
			}
		}
		return ConnectionUIUtils.CONNECTION_IMG;
	}

	/**
	 * Get the image representing the connection status.
	 * @param connection
	 * @return Image, not to be disposed
	 */
	public static Image getConnectionImage(IConnection connection) {
		// TODO: remove this when we have real statuses from a connection
		if (isSomeServiceInUse(connection)) {
			return ConnectionUIUtils.STATUS_INUSE_IMG;
		}
		if (connection instanceof IConnection2) {
			IConnectionStatus status = ((IConnection2) connection).getStatus();
			return getConnectionStatusImage(status);
		} else {
			// old connection logic
			if (isSomeServiceInUse(connection)) {
				return ConnectionUIUtils.STATUS_INUSE_IMG;
			}
			return ConnectionUIUtils.CONNECTION_IMG;
		}
	}

	/**
	 * @param status
	 * @return
	 */
	public static Image getConnectedServiceStatusImage(IConnectedService.IStatus.EStatus status) {
		switch (status) {
		case DOWN:
			return ConnectionUIUtils.STATUS_UNAVAIL_IMG;
		case UP:
			return ConnectionUIUtils.STATUS_AVAIL_IMG;
		case IN_USE:
			return ConnectionUIUtils.CONNECTION_IMG;
		case UNKNOWN:
			return ConnectionUIUtils.STATUS_UNK_IMG;
		}
		return null;
	}

	public static IStatus getFirstInUseServiceStatus(IConnection connection) {
		Collection<IConnectedService> connectedServices = 
			Registry.instance().getConnectedServices(connection);
		// if any service is in-use, then connection is in-use
		if (connectedServices.isEmpty())
			return null;
		
		for (IConnectedService connectedService : connectedServices) {
			IStatus status = connectedService.getStatus();
			if (status.getEStatus().equals(EStatus.IN_USE))
				return status;
		}
		
		return null;
	}

	public static boolean isSomeServiceInUse(IConnection connection) {
		return getFirstInUseServiceStatus(connection) != null;
	}
}
