/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.sdt.sourcegen.contributions.IContribution;
import com.nokia.sdt.sourcegen.contributions.ILocation;
import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.IMessage;

import org.eclipse.cdt.core.dom.ast.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Contribution filter that parses a contribution's text and,
 * if it is a prototype, only adds it if it is unique within
 * the enclosing class. 
 * 
 */
public class UniquePrototypeContributionFilter implements IContributionFilter {

    Set<String> currentPrototypes;
    private CppLocation classLocation;
	private CppLocation owningLocation;
	
	// used to tell when to automatically re-sync 
	private int lastContribCount;
    
    /**
     * Initialize with a child of a class() location
     */
    public UniquePrototypeContributionFilter(CppLocation location) {
        Check.checkArg(location);
        this.currentPrototypes = new HashSet<String>();

        this.owningLocation = location;
        this.classLocation = null;
        ILocation loc = location;
        while (loc != null) {
            if (loc instanceof CppLocation && 
                    ((CppLocation) loc).getSegment().getType() == ICppLocationSegment.L_CLASS) {
                classLocation = ((CppLocation) loc);
                break;
            }
            loc = loc.getParent();
        }
        
        if (classLocation == null) {
            Messages.emit(IMessage.ERROR,
                    location.getPrimarySourceGenLocation().createMessageLocation(),
                    "UniquePrototypeContributionFilter.InvalidUniquePrototypeFilterUsage", //$NON-NLS-1$
                    new Object[] { location.getLocationPath() });
        }

    }
    
    class FunctionDeclFinder extends LinearNodeVisitor {
        private CodeRange parent;
        private IASTFunctionDefinition match;
		private boolean remove;
        
        FunctionDeclFinder(CodeRange parent) {
        	this(parent, false);
        }
        FunctionDeclFinder(CodeRange parent, boolean remove) {
            this.parent = parent;
            this.match = null;
            this.shouldVisitDeclarations = true;
            this.remove = remove;
        }
        
        public IASTFunctionDefinition getMatch() {
            return match;
        }
        
        public int visit(IASTDeclaration declaration) {
            if (declaration.getParent() != parent.getNode())
                return PROCESS_CONTINUE;
            
            // bail if this node is outside our scope
            if (remove && 
            		(declaration.getFileLocation().getNodeOffset() < parent.offset
            				|| declaration.getFileLocation().getNodeOffset() + declaration.getFileLocation().getNodeLength() 
            				>= parent.offset + parent.length))
            	return PROCESS_CONTINUE;
            		
            
                
            // a function looks like IASTSimpleDeclaration with the decl-specifier being
            // an IASTFunctionDeclarator
            if (!(declaration instanceof IASTSimpleDeclaration))
                return PROCESS_CONTINUE;
            
            IASTDeclarator[] drs = ((IASTSimpleDeclaration) declaration).getDeclarators();
            if (drs.length == 0 || !(drs[0] instanceof IASTFunctionDeclarator))
                return PROCESS_CONTINUE;
                
            IASTFunctionDeclarator decl = (IASTFunctionDeclarator) drs[0];

            // no K&R support
            if (!(decl instanceof IASTStandardFunctionDeclarator)) 
                return PROCESS_CONTINUE;
            
            String declText = declaration.getRawSignature();
            CharSequence signature = CdtUtils.getPrototypeSignature(
                    CdtUtils.stripComments(declText));
            if (signature != null) {
            	if (remove)
            		currentPrototypes.remove(signature.toString());
            	else
            		currentPrototypes.add(signature.toString());
            }
            
            return PROCESS_CONTINUE;
        }
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.IContributionFilter#reset()
     */
    public void reset() {
        currentPrototypes.clear();
        lastContribCount = classLocation != null ? classLocation.getContributions().size() : 0;
        
        // scan prototypes
        if (classLocation != null) {
            CodeRange range = classLocation.getCodeRange();
            if (range != null) {
                FunctionDeclFinder finder = new FunctionDeclFinder(range);
                range.node.accept(finder);
            }
            // remove any prototypes we see in our owned section, since they'll be cleared out
            // after the incoming contributions are placed
            if (owningLocation.isOwned()) {
                range = owningLocation.getCodeRange();
                if (range != null) {
                    FunctionDeclFinder finder = new FunctionDeclFinder(range, true);
                    range.node.accept(finder);
                }
            }
        }
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.IContributionChecker#acceptContribution(com.nokia.sdt.sourcegen.contributions.domains.cpp.ICppLocationSegment, com.nokia.sdt.sourcegen.contributions.IContribution)
     */
    public boolean acceptContribution(IContribution contrib) {
        if (classLocation == null)
            return true;
        
        CharSequence signatureSeq = CdtUtils.getPrototypeSignature(
                CdtUtils.stripComments(contrib.getText()));
        if (signatureSeq == null)
            return true;

        if (lastContribCount != classLocation.getContributions().size())
        	reset();
        
        String signature = signatureSeq.toString();
        
        // see if it matches original prototypes
        if (currentPrototypes.contains(signature))
            return false;
        
        // accept, and remember for this round
        currentPrototypes.add(signature);
        
        return true;
    }

    
}
