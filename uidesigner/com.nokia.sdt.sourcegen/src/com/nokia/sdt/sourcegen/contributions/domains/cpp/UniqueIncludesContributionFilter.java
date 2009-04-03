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
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Pair;
import com.nokia.sdt.utils.SubString;

import java.util.HashSet;
import java.util.Set;

/**
 * Contribution filter that parses a contribution's text and,
 * only adds it if it is a unique #include.  We assume that
 * system and user includes are the same in this context.  
 * 
 */
public class UniqueIncludesContributionFilter implements IContributionFilter {

    Set<String> currentIncludes;
    private CppLocation location;
	private CppLocation owningLocation;
    
    /**
     * Initialize with a region using #includes
     */
    public UniqueIncludesContributionFilter(CppLocation location) {
        Check.checkArg(location);
        
        this.owningLocation = location;
        
        // go to the file
        while (location.getParent() instanceof CppLocation)
        	location = (CppLocation) location.getParent();
        
        this.location = location;
        this.currentIncludes = new HashSet<String>();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.IContributionFilter#reset()
     */
    public void reset__() {
    	currentIncludes.clear();

        
        // scan #includes and populate list, but not for owned loc, where they are deleted anyway
        if (location != null) {
        	CodeRange ownedRange;
        	if (owningLocation.isOwned())
        		ownedRange = owningLocation.getCodeRange();
        	else
        		ownedRange = null;
        	
        	// this checks the whole file, since includes are only 
        	// unique in that scope
            CodeRange range = location.getCodeRange();
            if (range != null) {
            	int end = range.getOffset() + range.getLength();
            	char[] text = range.text;
            	int idx = range.getOffset();

            	// iterate lines
            	while (idx < end) {
            		int bol = idx;
            		int eol = idx;
            		boolean maybeDirective = false;
            		while (eol < end && text[eol] != '\r' && text[eol] != '\n') {
            			if (text[eol] == '#')
            				maybeDirective = true;
            			eol++;
            		}
            		
            		if (maybeDirective && 
            				(ownedRange == null || !ownedRange.contains(bol, eol - bol))) { 
	            		SubString line = new SubString(text, bol, eol - bol);
	            		CharSequence incl = CdtUtils.getInclude(CdtUtils.stripComments(line));
	            		if (incl != null)
	            			currentIncludes.add(incl.toString());
            		}
            		
            		if (eol < end) {
            			if (text[eol] == '\r' && eol + 1 < end && text[eol+1] == '\n')
            				idx = eol + 2;
            			else
            				idx = eol + 1;
            		} else
            			break;
            	}
            }
        }
    }
    
    public void reset() {
    	currentIncludes.clear();
        
        // scan #includes and populate list, but not for owned loc, where they are deleted anyway
        if (location != null) {
        	// this checks the whole file, since includes are only 
        	// unique in that scope
        	SerialCppLocationRangeGatherer gatherer = 
        		new SerialCppLocationRangeGatherer();
        	location.accept(gatherer, true);
        	
        	for (Pair<CppLocation, CodeRange> info : gatherer.list) {
        		if (info.first.isOwned())
        			continue;
        		
        		CodeRange range = info.second;
        		
            	int end = range.getOffset() + range.getLength();
            	char[] text = range.text;
            	int idx = range.getOffset();

            	// iterate lines
            	while (idx < end) {
            		int bol = idx;
            		int eol = idx;
            		boolean maybeDirective = false;
            		while (eol < end && text[eol] != '\r' && text[eol] != '\n') {
            			if (text[eol] == '#')
            				maybeDirective = true;
            			eol++;
            		}
            		
            		if (maybeDirective) {
	            		SubString line = new SubString(text, bol, eol - bol);
	            		CharSequence incl = CdtUtils.getInclude(CdtUtils.stripComments(line));
	            		if (incl != null)
	            			currentIncludes.add(incl.toString().toLowerCase());
            		}
            		
            		if (eol < end) {
            			if (text[eol] == '\r' && eol + 1 < end && text[eol+1] == '\n')
            				idx = eol + 2;
            			else
            				idx = eol + 1;
            		} else
            			break;
            	}
            }
        }
    	
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.IContributionChecker#acceptContribution(com.nokia.sdt.sourcegen.contributions.domains.cpp.ICppLocationSegment, com.nokia.sdt.sourcegen.contributions.IContribution)
     */
    public boolean acceptContribution(IContribution contrib) {
        if (location == null)
            return true;
        
        String include = CdtUtils.getInclude(
                CdtUtils.stripComments(contrib.getText()));
        if (include == null)
            return true;

        // case-insensitive check
        include = include.toLowerCase();
        
        // see if it matches original prototypes
        if (currentIncludes.contains(include))
            return false;
        
        // accept, and remember for this round
        currentIncludes.add(include);
        
        return true;
    }

    
}
