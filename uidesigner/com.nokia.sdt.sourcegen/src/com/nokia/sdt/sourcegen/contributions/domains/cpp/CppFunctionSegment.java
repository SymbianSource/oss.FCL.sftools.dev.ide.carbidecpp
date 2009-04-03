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

import com.nokia.sdt.sourcegen.core.ParseUtils;

import org.eclipse.cdt.core.dom.ast.*;

/**
 * 
 *
 */
public class CppFunctionSegment extends CppLocationSegmentBase {

	public boolean DEBUG = false;
	
    private String[] args;

    /**
     * @param funcArgs 
     */
    public CppFunctionSegment(String segment, String name, String[] funcArgs) {
        super(ICppLocationSegment.L_FUNCTION, segment, name);
        this.args = funcArgs;
        for (int i = 0; i < args.length; i++)
            this.args[i] = args[i].trim();
    }
    
    public CppFunctionSegment(String name, String[] funcArgs) {
        this(null, name, funcArgs);
    }
    
    public String[] getArgs() {
        return args;
    }

    class FunctionFinder extends LinearNodeVisitor {
        private CodeRange parent;
        private IASTFunctionDefinition match;
        FunctionFinder(CodeRange parent) {
            this.parent = parent;
            this.match = null;
            this.shouldVisitDeclarations = true;
        }
        
        public IASTFunctionDefinition getMatch() {
            return match;
        }
        
        /* (non-Javadoc)
         * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.LinearNodeVisitor#visit(org.eclipse.cdt.core.dom.ast.IASTDeclaration)
         */
        public int visit(IASTDeclaration declaration) {
            if (declaration.getParent() != parent.getNode())
                return PROCESS_CONTINUE;
            
            if (!(declaration instanceof IASTFunctionDefinition))
                return PROCESS_CONTINUE;

            
            IASTFunctionDefinition funcdef = (IASTFunctionDefinition) declaration;
            if (!funcdef.getDeclarator().getName().toString().equals(getName())) 
                return PROCESS_CONTINUE;

            // validate source range
            IASTFileLocation loc = funcdef.getFileLocation();
            if (loc.getNodeOffset() < parent.getOffset()
                    || loc.getNodeOffset() >= parent.getOffset() + parent.getLength())
                return PROCESS_CONTINUE;

        	// use this routine to get the best args possible without
        	// using the CModel (which must be refreshed, but apparently
        	// cannot be unless the file is saved to disk...?)
        	String[] params = ASTSignatureUtil.getParameterSignatureArray(funcdef.getDeclarator());
        	fixupParams(params);
        	
            boolean varargs = false;
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("...")) { //$NON-NLS-1$
                    varargs = true;
                    break;
                }
                if (i >= params.length)
                    return PROCESS_CONTINUE;
                
                boolean matches = args[i].equals(params[i]);
                if (!matches) {
                	// also accept base types
                	matches = (args[i].indexOf(' ') < 0 && params[i].indexOf(args[i]) >= 0);
                }
                if (!matches) {
                	// remove any template args, which ASTSignatureUtil leaves out
                	String simpleArg = 	args[i].replaceAll("(<.*>)", ""); //$NON-NLS-1$ //$NON-NLS-1$ //$NON-NLS-2$
                	matches = simpleArg.equals(params[i]);
                }
                if (!matches) {
                	if (DEBUG)
                		System.out.println("failed match of " + getName() + " due to arg #" + i + ": location's " + args[i] + " doesn't match CDT's " + params[i]); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                	return PROCESS_CONTINUE;
                }
            }

            // unless the searched function has varargs, we only
            // match a function with the same number of arguments
            if (!varargs && params.length != args.length)
                return PROCESS_CONTINUE;

            match = funcdef;
            return PROCESS_ABORT;
        }

		/**
		 * @param params
		 */
		private void fixupParams(String[] params) {
			for (int i = 0; i < params.length; i++) {
				// remove unnecessary spaces
				params[i] = params[i].replaceAll("\\s+([&*])", "$1"); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
    }
    
    public CodeRange getChildRange(CodeRange parent) {
        FunctionFinder finder = new FunctionFinder(parent);
        parent.node.accept(finder);
        
        if (finder.getMatch() == null)
            return null;
        
        return expandChildRange(parent, new CodeRange(parent, finder.getMatch()));

        //return CdtUtils.expandRange(text, parent.offset, parent.offset + parent.length,
        //        new CodeRange(finder.getMatch()), false);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ICppLocationSegment#getInsertPosition(char[], int, int)
     */
    public int getInsertPosition(char[] text, CodeRange range, ICppLocationSegment segment) {
        return ParseUtils.getInsertPositionAtEnd(text, range.offset, range.length, '}');
    }
}
