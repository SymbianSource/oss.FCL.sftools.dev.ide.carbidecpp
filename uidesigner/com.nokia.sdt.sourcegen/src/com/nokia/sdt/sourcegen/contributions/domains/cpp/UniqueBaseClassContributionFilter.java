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
import com.nokia.sdt.sourcegen.contributions.domains.cpp.CppBasesSegment.BasesFinder;
import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.IMessage;

import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier.ICPPASTBaseSpecifier;

import java.util.HashSet;
import java.util.Set;

/**
 * Contribution filter that parses a contribution's text and,
 * only adds it if it is unique within the enclosing class.
 * Each contribution is expected to have the class name at the
 * end. 
 * 
 */
public class UniqueBaseClassContributionFilter implements IContributionFilter {

    Set<String> currentBaseClasses;
    private CppLocation classLocation;
    private CppLocation owningLocation;
    
    /**
     * Initialize with a bases() location
     */
    public UniqueBaseClassContributionFilter(CppLocation location) {
        Check.checkArg(location);
        this.currentBaseClasses = new HashSet<String>();

        this.owningLocation = location;
        
        ILocation classOwner = location.getParent();
        while (classOwner instanceof CppLocation) {
        	if (((CppLocation) classOwner).getSegment().getType() 
        		== ICppLocationSegment.L_CLASS) {
        		classLocation = (CppLocation) classOwner;
        		break;
        	}
        	classOwner = classOwner.getParent();
        }
        if (classLocation == null) {
            Messages.emit(IMessage.ERROR,
                    location.getPrimarySourceGenLocation().createMessageLocation(),
                    "UniqueBaseClassContributionFilter.InvalidUniqueBasesFilterUsage", //$NON-NLS-1$
                    new Object[] { location.getLocationPath() });
        }

    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.IContributionFilter#reset()
     */
    public void reset() {
    	currentBaseClasses.clear();

        // find the class
        
        // scan base classes
        if (classLocation != null && !owningLocation.isOwned()) {
            CodeRange range = classLocation.getCodeRange();
            if (range != null) {
                BasesFinder finder = new BasesFinder(range);
                range.node.accept(finder);
                
                ICPPASTCompositeTypeSpecifier spec = finder.getMatch();
                if (spec != null) {
                	  // if the bases list is empty, it doesn't exist 
                    ICPPASTBaseSpecifier[] bases = spec.getBaseSpecifiers();
                    for (int i = 0; i < bases.length; i++) {
						String declText = bases[i].getRawSignature();
						String klass = CdtUtils.getBaseClass(
								CdtUtils.stripComments(declText));
						if (klass != null)
							currentBaseClasses.add(klass);
					}
                    	
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
        
        String klass = CdtUtils.getBaseClass(
                CdtUtils.stripComments(contrib.getText()));
        if (klass == null)
            return true;

        // see if it matches original prototypes
        if (currentBaseClasses.contains(klass))
            return false;
        
        // accept, and remember for this round
        currentBaseClasses.add(klass);
        
        return true;
    }

    
}
