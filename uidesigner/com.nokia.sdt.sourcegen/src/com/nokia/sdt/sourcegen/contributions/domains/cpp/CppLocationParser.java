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

package com.nokia.sdt.sourcegen.contributions.domains.cpp;

import com.nokia.sdt.datamodel.util.NamePropertySupport;
import com.nokia.sdt.sourcegen.contributions.ILocation;
import com.nokia.sdt.sourcegen.contributions.SourceGenException;
import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.cpp.internal.api.utils.core.Check;

import java.util.*;

/**
 * Parser for C++ location paths.
 * <p>
 * Create the class, invoke one of the #parse() methods, and verify the returned
 * list has more than one segment and that #getError() is null.
 * 
 *
 */
public class CppLocationParser {

    private String[] segments;
    private int index;
    private String path;
    
    private static final int S_TOPLEVEL = 1;
    private static final int S_CLASS = 2;
    private static final int S_FUNCTION = 3;
    private static final int S_REGION = 4;
    private static final int S_NONE = 0;
    private int state;
    private ArrayList locSegments;
	private SourceGenException error;

    /**
     * Create a parse handler for location paths
     * @param component the contributing component (for messages)
     */
    public CppLocationParser() {
        this.error = null;
    }

    /**
     * Return the next legal state
     * @param oldState the current S_xxx value
     * @param locationType the last L_xxx type
     * @return a new S_xxx state
     */
    int nextState(int oldState, int locationType) {
        switch (locationType) {
        case ICppLocationSegment.L_CLASS:
            return S_CLASS;
        case ICppLocationSegment.L_FUNCTION:
            return S_FUNCTION;
        case ICppLocationSegment.L_ENUM:
            return S_REGION;
        case ICppLocationSegment.L_TO_FILE:
            return S_TOPLEVEL;
        case ICppLocationSegment.L_NAMESPACE:
        case ICppLocationSegment.L_REGION:
            // no change, need to go up
            return oldState;
        case ICppLocationSegment.L_BASES:
            return S_REGION;
        }
        return S_NONE;
    }

    int nextState() {
        if (locSegments.size() == 0)
            return state;
        ICppLocationSegment segment = (ICppLocationSegment)(locSegments.get(locSegments.size() - 1));
        return nextState(state, segment.getType());
    }

    public SourceGenException getError() {
    	return error;
    }
    
    /**
     * Parse the given path into segments
     * @param path the full path with all templates expanded
     * <p>
     * @return list of ICppLocationSegment, or null (messages reported)
     */
    public List parse(String path) {
        return parse(path, S_TOPLEVEL);
    }

    /**
     * Parse the given path into segments
     * @param path the relative path with all templates expanded
     * @param from the last segment on the prefix to this path
     * <p>
     * @return list of ICppLocationSegment, or null (messages reported)
     */
    public List parse(String path, ILocation from) {
        // get the parsing state
        int state = -1;
        while (state == -1 && (from instanceof CppLocation)) {
            CppLocation loc = (CppLocation) from;
            if (loc.getSegment() == null)
                break;
            state = nextState(state, loc.getSegment().getType());
            from = from.getParent();
        }
        if (state == -1)
            state = S_TOPLEVEL;
        return parse(path, state);
    }

    /**
     * Parse the given path into segments
     * @param path the relative path with all templates expanded
     * @param segments list of ICppLocationSegments on the prefix to this path
     * <p>
     * @return list of ICppLocationSegment, or null (messages reported)
     */
    public List parse(String path, List segments) {
        // get the parsing state
        int state = -1;
        for (ListIterator iter = segments.listIterator(segments.size()); state == -1 && iter.hasPrevious(); ) {
            ICppLocationSegment seg = (ICppLocationSegment) iter.previous();
            state = nextState(state, seg.getType());
        }
        if (state == -1)
            state = S_TOPLEVEL;
        return parse(path, state);
    }

    /**
     * Parse the given path into segments
     * @param path a relative path with all templates expanded
     * <p>
     * @return list of ICppLocationSegment, or null (messages reported)
     */
    public List parse(String path, int initialState) {
        this.path = path;
        locSegments = new ArrayList();
        segments = path.split("/"); //$NON-NLS-1$
        index = 0;
        state = initialState;
        try {
            while (index < segments.length) {
                if (!segments[index].equals("")) {  //$NON-NLS-1$
                    switch (state) {
                    case S_TOPLEVEL:
                        parseForLevel(topLevelNodes);
                        break;
                    case S_CLASS:
                        parseForLevel(classLevelNodes);
                        break;
                    case S_FUNCTION:
                        parseForLevel(functionLevelNodes);
                        break;
                    case S_REGION:
                        parseForLevel(regionLevelNodes);
                        break;
                    case S_NONE:
                        throw new SourceGenException("CppLocationParser.IllegalNode",  //$NON-NLS-1$
                                new String[] { segments[index],
                                Messages.getString("CppLocationParser.EndOfPath"), //$NON-NLS-1$
                                path});
                    }
                }
                index++;
            }
            return locSegments;
        } catch (SourceGenException e) {
        	//error(e);
        	this.error = e;
            return null;
        }
    }

    // global variables holding the result of the last parseSegment() call
    final int MAX_ARGS = 8;
    String[] args = new String[MAX_ARGS];
    int argCount;
    
    /**
     * Parse the current segment and place arguments in field args 
     * @param pairs pairs of String, Integer entries for valid tokens and values
     * @return matching token value
     * @throws SourceGenException if none of the names match or if arguments are malformed
     */
    private int parseSegment(Object[] pairs) throws SourceGenException {
        int matchIdx = -1;
        for (int i = 0; i < pairs.length; i += 2) {
            if (segments[index].startsWith(pairs[i].toString() + "(")) { //$NON-NLS-1$
                matchIdx = i;
                break;
            }
        }
        if (matchIdx == -1) {
            StringBuffer allSegments = new StringBuffer();
            for (int i = 0; i < pairs.length; i += 2) {
                allSegments.append(pairs[i].toString());
                if (i + 2 < pairs.length)
                    allSegments.append(", "); //$NON-NLS-1$
            }
            throw new SourceGenException("CppLocationParser.IllegalNode",  //$NON-NLS-1$
                    new String[] { segments[index],
                    allSegments.toString(),
                    path});
        }
        
        // check argument list
        String argStr = segments[index].substring(pairs[matchIdx].toString().length());
        if (argStr.charAt(0) != '(' || argStr.charAt(argStr.length()-1) != ')') {
            throw new SourceGenException("CppLocationParser.IllegalArguments",  //$NON-NLS-1$
                    new String[] { segments[index],
                    path});
        }
        
        // extract arguments
        argCount = parseArguments(argStr.substring(1, argStr.length() -1), args);
        
        return ((Integer)pairs[matchIdx + 1]).intValue();
    }

    /**
     * Parse the given string into comma-separated arguments,
     * honoring parenthesis-enclosed arguments. 
     * @param argStr the argument to parse
     * @param args the array into which to write
     * @return number of arguments
     */
    int parseArguments(String argStr, String[] args) throws SourceGenException {
        int j = 0;
        int argCount = 0;
        int parenCount = 0;
        while (j <= argStr.length()) {
            if (argCount >= args.length) {
                throw new SourceGenException("CppLocationParser.TooManyArguments",  //$NON-NLS-1$
                        new String[] { segments[index],
                        Integer.toString(args.length),
                        path});
            }
            int n = j;
            char ch = 0;
            while (n < argStr.length()) {
                ch = argStr.charAt(n);
                if (parenCount == 0 && ch == ',')
                    break;
                if (ch == '(')
                    parenCount++;
                else if (ch == ')')
                    parenCount--;
                n++;
            }
            if (parenCount != 0) {
                throw new SourceGenException("CppLocationParser.MismatchedParens",  //$NON-NLS-1$
                        new String[] { segments[index],
                        path});
            }
            args[argCount] = argStr.substring(j, n);
            argCount++;
            j = n + 1;
        }
        
        return argCount;
    }

    /**
     *  Parse a node given a list of legal nodes 
     */
    private void parseForLevel(Object[] nodes) throws SourceGenException {
        int which = parseSegment(nodes);
        switch (which) {
        case ICppLocationSegment.L_TO_FILE:
            parseToFile();
            break;
        case ICppLocationSegment.L_CLASS:
            parseClass();
            break;
        case ICppLocationSegment.L_ENUM:
            parseEnum();
            break;
        case ICppLocationSegment.L_FUNCTION:
            parseFunction();
            break;
        case ICppLocationSegment.L_NAMESPACE:
            parseNamespace();
            break;        
        case ICppLocationSegment.L_REGION:
            parseRegion();
            break;      
        case ICppLocationSegment.L_BASES:
            parseBases();
            break;      
        default:
            Check.checkState(false);
        }
        state = nextState();
    }

    static final Object[] topLevelNodes = new Object[] {
        "class", new Integer(ICppLocationSegment.L_CLASS),  //$NON-NLS-1$
        "function", new Integer(ICppLocationSegment.L_FUNCTION),  //$NON-NLS-1$
        "region", new Integer(ICppLocationSegment.L_REGION), //$NON-NLS-1$
        "enum", new Integer(ICppLocationSegment.L_ENUM), //$NON-NLS-1$
        "namespace", new Integer(ICppLocationSegment.L_NAMESPACE), //$NON-NLS-1$
        "to-file", new Integer(ICppLocationSegment.L_TO_FILE) //$NON-NLS-1$
    };
                                                     
    static final Object[] classLevelNodes = new Object[] {
        "class", new Integer(ICppLocationSegment.L_CLASS),  //$NON-NLS-1$
        "function", new Integer(ICppLocationSegment.L_FUNCTION),  //$NON-NLS-1$
        "region", new Integer(ICppLocationSegment.L_REGION), //$NON-NLS-1$
        "enum", new Integer(ICppLocationSegment.L_ENUM), //$NON-NLS-1$
        "to-file", new Integer(ICppLocationSegment.L_TO_FILE), //$NON-NLS-1$
        "bases", new Integer(ICppLocationSegment.L_BASES) //$NON-NLS-1$
    };

    static final Object[] functionLevelNodes = new Object[] {
        "class", new Integer(ICppLocationSegment.L_CLASS),  //$NON-NLS-1$
        "region", new Integer(ICppLocationSegment.L_REGION), //$NON-NLS-1$
        "enum", new Integer(ICppLocationSegment.L_ENUM), //$NON-NLS-1$
        "to-file", new Integer(ICppLocationSegment.L_TO_FILE) //$NON-NLS-1$
    };

    static final Object[] regionLevelNodes = new Object[] {
        "region", new Integer(ICppLocationSegment.L_REGION), //$NON-NLS-1$
        "to-file", new Integer(ICppLocationSegment.L_TO_FILE) //$NON-NLS-1$
    };

    /**
     * @param string
     */
    private void checkLegalId(String string) throws SourceGenException {
        if (!NamePropertySupport.isLegalName(string)) {
            throw new SourceGenException("CppLocationParser.BadIdentifier",  //$NON-NLS-1$
                    new String[] { segments[index],
                    path});
        }
    }
    
    /**
     * Parse the to-file() segment
     */
    private void parseToFile() throws SourceGenException {
        if (argCount != 0 && !(argCount == 1 && args[0].equals(""))) { //$NON-NLS-1$
            throw new SourceGenException("CppLocationParser.BadArgCount",  //$NON-NLS-1$
                    new String[] { segments[index],
                    "0", //$NON-NLS-1$
                    path});
        }
        locSegments.add(new CppToFileSegment(segments[index]));
    }

    /**
     * Parse a region:
     * <pre>
     * region(&lt;name&gt;)
     * </pre>
     */
    private void parseRegion() throws SourceGenException {
        if (argCount != 1) {
            throw new SourceGenException("CppLocationParser.BadArgCount",  //$NON-NLS-1$
                    new String[] { segments[index],
                    "1", //$NON-NLS-1$
                    path});
        }
        // test the identifier -- it can pretty much be anything
        if (args[0].length() == 0) {
            throw new SourceGenException("CppLocationParser.BadIdentifier",  //$NON-NLS-1$
                    new String[] { segments[index],
                    path});
        }
        locSegments.add(new CppRegionSegment(segments[index], args[0]));
    }

    /**
     * Parse a namespace:
     * <pre>
     * namespace(&lt;name&gt;)
     * </pre>
     */
    private void parseNamespace() throws SourceGenException {
        if (argCount != 1) {
            throw new SourceGenException("CppLocationParser.BadArgCount",  //$NON-NLS-1$
                    new String[] { 
                    segments[index],
                    "1", //$NON-NLS-1$
                    path});
        }
        checkLegalId(args[0]);
        locSegments.add(new CppNamespaceSegment(segments[index], args[0]));
    }

    /**
     * Parse a function:
     * <pre>
     * function(&lt;name&gt; '(' &lt;args&gt; ')' )
     * </pre>
     */
    private void parseFunction() throws SourceGenException {
        if (argCount != 1) {
            throw new SourceGenException("CppLocationParser.BadArgCount",  //$NON-NLS-1$
                    new String[] { segments[index],
                    "1", //$NON-NLS-1$
                    path});
        }
        
        
        // further parse the arguments
        int start = args[0].indexOf('(');
        int end = args[0].lastIndexOf(')');
        if (!(start < end && start != -1 && end != -1)) {
            throw new SourceGenException("CppLocationParser.BadFunctionArguments",  //$NON-NLS-1$
                    new String[] { segments[index],
                    args[0],
                    path});
        }
        
        String funcName = args[0].substring(0, start);
        
        String[] funcNameParts = funcName.split("::~|::"); //$NON-NLS-1$
        for (int i = 0; i < funcNameParts.length; i++) {
            checkLegalId(funcNameParts[i]);
        }

        String funcArgStr = args[0].substring(start + 1, end);
        String[] maxFuncArgs = new String[8];
        int funcArgCount = parseArguments(funcArgStr, maxFuncArgs);
        
        // handle foo() as a zero-arg function
        if (funcArgCount == 1 && maxFuncArgs[0].equals("")) //$NON-NLS-1$
            funcArgCount = 0;
        
        if (funcArgCount > 0) { 
            for (int i = 0; i < funcArgCount; i++) {
                if (maxFuncArgs[i].length() == 0) {
                    throw new SourceGenException("CppLocationParser.BadFunctionArgument",  //$NON-NLS-1$
                            new String[] { segments[index],
                            maxFuncArgs[funcArgCount],
                            path});
                    
                }
            }
        }
        
        String[] funcArgs = new String[funcArgCount];
        System.arraycopy(maxFuncArgs, 0, funcArgs, 0, funcArgCount);
        locSegments.add(new CppFunctionSegment(segments[index], funcName, funcArgs));
        
    }

    /**
     * 
     */
    private void parseEnum() throws SourceGenException {
        if (argCount != 1) {
            throw new SourceGenException("CppLocationParser.BadArgCount",  //$NON-NLS-1$
                    new String[] { segments[index],
                    "1", //$NON-NLS-1$
                    path});
        }
        checkLegalId(args[0]);
        locSegments.add(new CppEnumSegment(segments[index], args[0]));
        
    }

    /**
     * 
     */
    private void parseClass() throws SourceGenException {
        if (argCount != 1) {
            throw new SourceGenException("CppLocationParser.BadArgCount",  //$NON-NLS-1$
                    new String[] { segments[index],
                    "1", //$NON-NLS-1$
                    path});
        }
        checkLegalId(args[0]);
        locSegments.add(new CppClassSegment(segments[index], args[0]));
    }

    /**
     * Parse the bases() segment
     */
    private void parseBases() throws SourceGenException {
        if (argCount != 0 && !(argCount == 1 && args[0].equals(""))) { //$NON-NLS-1$
            throw new SourceGenException("CppLocationParser.BadArgCount",  //$NON-NLS-1$
                    new String[] { segments[index],
                    "0", //$NON-NLS-1$
                    path});
        }
        locSegments.add(new CppBasesSegment(segments[index]));
    }
    
}
