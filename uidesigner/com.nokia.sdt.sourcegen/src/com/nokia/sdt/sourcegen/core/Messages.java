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

package com.nokia.sdt.sourcegen.core;


import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.Message;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;
import com.nokia.sdt.datamodel.*;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.sourcegen.SourceGenPlugin;
import com.nokia.sdt.symbian.dm.ModelMessage;
import com.nokia.sdt.utils.*;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Our message handler
 * 
 * 
 *
 */
public class Messages {
    private static final String BUNDLE_NAME = "com.nokia.sdt.sourcegen.core.messages";//$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private Messages() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
    
    public static String getFormattedString(String key, Object arg) { 
        String format= null; 
        if (arg == null) 
            arg= ""; //$NON-NLS-1$ 
        try { 
            format= RESOURCE_BUNDLE.getString(key); 
            return MessageFormat.format(format, new Object[] { arg }); 
        } catch (MissingResourceException e) { 
            format = "!" + key + "! :" + arg;//$NON-NLS-2$ //$NON-NLS-1$
            return format;
        } 
    }
    
    public static String getFormattedString (String key, Object[] args) {
        try { 
            String format= RESOURCE_BUNDLE.getString(key); 
            return MessageFormat.format(format, args);
        } catch (MissingResourceException e) {
            String msg;
            msg = "!" + key + "! ";//$NON-NLS-2$ //$NON-NLS-1$
            for (int i = 0; i < args.length; i++) {
                msg += "," + args[i]; //$NON-NLS-1$
            }
            return msg;
            
        }
    }

    public static void emit(int severity, MessageLocation ref, String key, Object[] args) {
		emit(severity, ref, key, args, false);
	}

	public static void emit(int severity, MessageLocation ref, String key, Object[] args, boolean log) {
        IMessage msg = new Message(severity, ref,
                key, Messages.getString(key), args);
        MessageReporting.emitMessage(msg);
        if (log && SourceGenPlugin.getDefault() != null) {
        	SourceGenPlugin.getDefault().log(new Exception(msg.toString()));
        }
    }
    
    public static IModelMessage createModelMessage(int severity, IDesignerDataModelSpecifier dmSpec, String key, Object[] args) {
        IModelMessage msg = new ModelMessage(severity, dmSpec.createMessageLocation(),
                key,
                MessageFormat.format(Messages.getString(key), args),
                null, null, null, null);
        return msg;
    }

    public static void emit(String key, IStatus status) {
        IMessage msg = new Message(status.getSeverity(), null,
                key, status.getMessage());
        MessageReporting.emitMessage(msg);
    }

    /**
     * @param severity
     * @param spec
     * @param key
     * @param args
     */
    public static void emit(int severity, IDesignerDataModelSpecifier spec, String key, Object[] args) {
    	IResource wsFile = spec.getPrimaryResource();
    	IPath fullPath = null;
    	if (wsFile == null)
    		fullPath = new Path(spec.getPrimaryResourcePath().toFile().getAbsolutePath());
    	else 
    		fullPath = wsFile.getLocation();
    		
        emit(severity, new MessageLocation(fullPath), key, args, false);
    }

    /**
     * @param severity
     * @param spec
     * @param key
     * @param args
     */
    public static void emit(int severity, IDesignerDataModel dataModel, String key, Object[] args) {
    	if (dataModel.getModelSpecifier() != null) {
    		emit(severity, dataModel.getModelSpecifier(), key, args);
    	} else {
    		String path = dataModel.getResources()[0].getURI().toFileString();
    		emit(severity, new MessageLocation(new Path(path)),
                key, args, false);
    	}
    }

    public static void emit(int severity, ISourceFile sf, String key, Object[] args) {
    	IResource rsrc = ParserMessages.resolveToResource(sf);
    	if (rsrc != null)
	        emit(severity, new MessageLocation(rsrc.getLocation()), 
	        		key, args, false);
    	else
	        emit(severity, new MessageLocation(new Path(sf.getFile().getAbsolutePath())), 
	        		key, args, false);
    		
    }

    static public MessageLocation getComponentOrInstanceLocation(IComponentInstance instance) {
        MessageLocation loc = instance.getComponent().createMessageLocation();
        if (isInWorkspace(loc.getLocation()))
            return loc;
        else
            return getInstanceLocation(instance);
    }

    static MessageLocation getInstanceLocation(IComponentInstance instance) {
        IDesignerDataModelSpecifier modelSpecifier = instance.getDesignerDataModel().getModelSpecifier();
        if (modelSpecifier != null)
            return modelSpecifier.createMessageLocation();
        else
            return new MessageLocation(new Path("<unknown>:'" + instance.getName() + "'")); //$NON-NLS-1$ //$NON-NLS-2$
    }

    static boolean isInWorkspace(IPath cpath) {
        if (!Platform.isRunning())
            return false;
        IPath wsPath = FileUtils.convertToWorkspacePath(cpath);
        if (wsPath != null)
        	return true;
        return false;
        //IPath root = ResourcesPlugin.getWorkspace().getRoot().getLocation();
        //return root.matchingFirstSegments(cpath) == root.segmentCount();
    }

    /**
     * @param severity
     * @param instance
     * @param key
     * @param args
     */
    public static void emit(int severity, IComponentInstance instance, String key, Object[] args) {
        emit(severity, getComponentOrInstanceLocation(instance), key, args, false);
    }
    
    public static void emitAndLog(int severity, IComponentInstance instance, String key, Object[] args) {
        emit(severity, getComponentOrInstanceLocation(instance), key, args, true);
    }
    
    /**
     * Create a message
     * @param severity (Message.xxx)
     * @param msgKey
     */
    static public Message create(int severity, MessageLocation location, String msgKey) {
        return new Message(severity, location, msgKey, Messages.getString(msgKey));
    }

    /**
     * Emit a message
     * @param severity (Message.xxx)
     * @param msgKey
     * @param arg
     */
    static public Message create(int severity, MessageLocation location,String msgKey, Object arg) {
        return new Message(severity, location, msgKey, Messages.getString(msgKey), arg); 
    }
    
    /**
     * Emit a message
     * @param severity (Message.xxx)
     * @param msgKey
     * @param args
     */
    static public Message create(int severity, MessageLocation location,String msgKey, Object[] args) {
        return new Message(severity, location, msgKey, Messages.getString(msgKey), args); 
    }        
}
