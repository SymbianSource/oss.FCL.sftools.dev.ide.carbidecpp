/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.component.symbian.sourcegen;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.emf.component.DefineMacroType;
import com.nokia.sdt.emf.component.ImportArgumentsType;
import com.nokia.sdt.emf.component.MacroArgumentType;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.sdt.utils.MessageReporting;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SourceGenMacroSupport {
    private Map<String, ResolvedMacro> nameToMacroMap;
    
    public SourceGenMacroSupport(Map<String, ResolvedMacro> baseNameToMacroMap) {
    	if (baseNameToMacroMap != null)
    		nameToMacroMap = new LinkedHashMap<String, ResolvedMacro>(baseNameToMacroMap);
    	else
    		nameToMacroMap = new LinkedHashMap<String, ResolvedMacro>();
    }
    
    public ResolvedMacro findMacro(String id) {
    	return nameToMacroMap.get(id);
    }
    
	public ResolvedMacro defineMacro(IComponent component, MessageLocation messageLocation, DefineMacroType type) {
		
		
		// Collection of inherited arguments, which may be redefined
		Set<String> inheritedArguments = new HashSet<String>();
		
		// Resolve the macro's arguments so it doesn't refer to other macros'
		Map<String, MacroArgumentType> arguments = new LinkedHashMap<String, MacroArgumentType>();
		Map<String, ResolvedMacro> argumentOrigins = new LinkedHashMap<String, ResolvedMacro>();
		Set<ResolvedMacro> includedMacros = new HashSet<ResolvedMacro>();
		
		for (Iterator iter = type.getImportArguments().iterator(); iter.hasNext();) {
			ImportArgumentsType importArgs = (ImportArgumentsType) iter.next();
			ResolvedMacro theMacro = nameToMacroMap.get(importArgs.getMacroName());
			if (theMacro == null) {
	   			defineMacroError(messageLocation, type, 
	   					"SourceGenXMLParser.BadImportArgumentsMacro", //$NON-NLS-1$
    					new Object[] { importArgs.getMacroName() });
			} else if (importArgs.getArguments() != null 
					&& importArgs.getArguments().size() > 0) {
				// import a selection of arguments
				if (importArgs.getExceptArguments() != null) {
					defineMacroError(messageLocation, type,
							"SourceGenXMLParser.InvalidExceptArgumentsUsage", //$NON-NLS-1$
	    					new Object[] { importArgs.getMacroName() });
				}
				
				includedMacros.add(theMacro);
				
				for (Iterator iterator = importArgs.getArguments().iterator(); iterator
						.hasNext();) {
					String argName = (String) iterator.next();
					MacroArgumentType theArg = theMacro.arguments.get(argName);
					if (theArg == null) {
						defineMacroError(messageLocation, type,
								"SourceGenXMLParser.BadPassArgumentsArgument", //$NON-NLS-1$
		    					new Object[] { argName, importArgs.getMacroName() });
					} else {
						if (arguments.containsKey(argName)) {
							/*defineMacroError(type,
									"SourceGenXMLParser.DupMacroArgumentOnImport", //$NON-NLS-1$
			    					new Object[] { argName, importArgs.getMacroName() });
							*/
						} else {
							arguments.put(argName, theArg);
							argumentOrigins.put(argName, theMacro);
							inheritedArguments.add(argName);
						}
					}
				}
			} else {
				// import all arguments, except any not imported
				List<String> exceptArguments = importArgs.getExceptArguments();
				if (exceptArguments == null) {
					exceptArguments = Collections.EMPTY_LIST;
				}
				
				for (Map.Entry<String, MacroArgumentType> entry : theMacro.arguments.entrySet()) {
					String argName = entry.getKey();
					if (arguments.containsKey(argName)
							|| exceptArguments.contains(argName)) {
						// ignore
					} else {
						arguments.put(argName, entry.getValue());
						argumentOrigins.put(argName, theMacro);
						inheritedArguments.add(argName);
					}
				}
			}
		}
		
    	ResolvedMacro resolvedMacro = new ResolvedMacro(component, type, arguments, argumentOrigins, includedMacros);

		// add our arguments
		for (Iterator iter = type.getMacroArgument().iterator(); iter.hasNext();) {
			MacroArgumentType argument = (MacroArgumentType) iter.next();
			String argName = argument.getName();
			if (arguments.containsKey(argName)
					&& !inheritedArguments.contains(argName)) {
				defineMacroError(messageLocation, type,
						"SourceGenXMLParser.DupMacroArgument", //$NON-NLS-1$
    					new Object[] { argName });
			}
			arguments.put(argName, argument);
			argumentOrigins.put(argName, resolvedMacro);
		}
		
		// record the resolved macro
		nameToMacroMap.put(type.getId(), resolvedMacro);
		
		return resolvedMacro;
	}


	public Map getNameToMacroMap() {
		return nameToMacroMap;
	}

	void defineMacroError(MessageLocation messageLocation, DefineMacroType type, String msgKey, Object[] args) {
    	defineMacroErrorLit(messageLocation, type, msgKey, Messages.getString(msgKey), args);
    }

    private void defineMacroErrorLit(MessageLocation messageLocation, DefineMacroType type, String msgKey, String message, Object[] args) {
    	String outerFormat = Messages.getString("SourceGenXMLParser.DefineMacroErrorFormat"); //$NON-NLS-1$

    	String messageHead = MessageFormat.format(message, args);
    	String messageTail = MessageFormat.format(outerFormat, 
    			new Object[] { type.getId() });
    	
        MessageReporting.emitMessage(new Message(IMessage.ERROR, 
                messageLocation, 
                msgKey,  
                "{0}", //$NON-NLS-1$
                new Object[] { messageHead + " " + messageTail } //$NON-NLS-1$
                ));
    }

}
