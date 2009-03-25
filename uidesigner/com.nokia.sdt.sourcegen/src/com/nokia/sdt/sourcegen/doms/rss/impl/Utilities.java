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
package com.nokia.sdt.sourcegen.doms.rss.impl;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.Message;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.MessageLocators;
import com.nokia.sdt.sourcegen.SourceGenPlugin;
import com.nokia.sdt.sourcegen.core.ISourceFile;
import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile;
import com.nokia.sdt.utils.*;

import java.lang.reflect.Constructor;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 *
 */
public abstract class Utilities {

	/**
	 * Clean up the given name by replacing non-identifier
	 * characters with "_"
	 * @param name
	 * @return identifier-friendly name
	 */
	public static String getCleanIdentifierName(String name) {
		if (name == null)
			return null;
		
	    // clean up possible index operators
	    Pattern pattern = Pattern.compile("\\[(\\d+)\\]"); //$NON-NLS-1$
	    Matcher matcher = pattern.matcher(name);
	    name = matcher.replaceAll("_$1"); //$NON-NLS-1$
	    
	    return TextUtils.legalizeIdentifier(name);
	}

	public static void emit(IComponentInstance instance, int severity, String msgKey, Object[] args) {
		MessageReporting.emitMessage(
				new Message(
						severity,
						MessageLocators.getComponentOrInstanceLocation(instance),
						msgKey,
						MessageFormat.format(Messages.getString(msgKey), args)));
	}

	/**
	 * Create an object derived from IAstSourceFile, where the first
	 * argument is ISourceFile.
	 * @param klazz target class
	 * @param args arguments appearing after ISourceFile in the list, or null
	 * @return new instance or null with logged error
	 */
	public static IAstSourceFile constructAstSourceFile(Class klazz, Object[] args,
			ISourceFile file) {
       try {
    	   Class[] ctorClasses;
           Object[] ctorArgs;
           if (args != null) {
        	   ctorArgs = new Object[args.length+1];
        	   ctorArgs[0] = file;
        	   System.arraycopy(args, 0, ctorArgs, 1, args.length);
        	   ctorClasses = new Class[args.length+1];
        	   ctorClasses[0] = ISourceFile.class;
        	   for (int i = 0; i < args.length; i++) {
        		   Class klass = args[i].getClass();
        		   if (klass == Integer.class)
        			   ctorClasses[i+1] = Integer.TYPE;
        		   else
        			   ctorClasses[i+1] = klass;
        	   }
           } else {
        	   ctorArgs = new Object[] { file }; 
        	   ctorClasses = new Class[] { ISourceFile.class }; 
           }

           Constructor ctor = klazz.getConstructor(ctorClasses);
           Check.checkArg(ctor);
           IAstSourceFile srcFile = (IAstSourceFile) ctor.newInstance(ctorArgs);
           Check.checkArg(srcFile);
           return srcFile;
       } catch (Exception e) {
           Logging.log(SourceGenPlugin.getDefault(),
                   Logging.newStatus(SourceGenPlugin.getDefault(), e));
           return null;
       }
	}

	/**
	 * @param klazz
	 * @param ctorArgs
	 * @return new object or null with logged error
	 */
	public static Object createInstance(Class klazz, Object[] ctorArgs) {
       try {
    	   Class[] ctorClasses;
    	   ctorClasses = new Class[ctorArgs.length];
    	   ctorClasses[0] = ISourceFile.class;
    	   for (int i = 0; i < ctorArgs.length; i++) {
    		   Class klass = ctorArgs[i].getClass();
    		   if (klass == Integer.class)
    			   ctorClasses[i] = Integer.TYPE;
    		   else if (klass == Boolean.class)
    			   ctorClasses[i] = Boolean.TYPE;
    		   else
    			   ctorClasses[i] = klass;
    	   }

           Constructor ctor = klazz.getDeclaredConstructor(ctorClasses);
           Check.checkArg(ctor);
           Object ret = ctor.newInstance(ctorArgs);
           Check.checkArg(ret);
           return ret;
       } catch (Exception e) {
           Logging.log(SourceGenPlugin.getDefault(),
                   Logging.newStatus(SourceGenPlugin.getDefault(), e));
           return null;
       }
	}

}
