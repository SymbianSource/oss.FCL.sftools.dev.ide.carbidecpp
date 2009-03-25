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
package com.nokia.sdt.sourcegen.contributions.domains.cpp;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.nokia.sdt.sourcegen.contributions.ILocation;
import com.nokia.sdt.sourcegen.contributions.ILocationVisitor;
import com.nokia.cpp.internal.api.utils.core.Pair;

/**
 * Visit the ranges occupied by ranges in a file, gathering
 * an ordered list of CppLocation -> CodeRange pairs spanning
 * the entire contents of the start location.
 *  
 * 
 *
 */
public class SerialCppLocationRangeGatherer implements ILocationVisitor {

	public List<Pair<CppLocation, CodeRange>> list;
	private Stack<CodeRange> rangeStack;
	private int lastEndOffset;
	
	public SerialCppLocationRangeGatherer() {
		this.list = new ArrayList<Pair<CppLocation, CodeRange>>();
		this.rangeStack = new Stack<CodeRange>();
		this.lastEndOffset = 0;
	}
	
	private CodeRange getRange(ILocation location_) {
		if (!(location_ instanceof CppLocation)) {
			return null;
		}
		CppLocation location = (CppLocation) location_;
		CodeRange range = location.getCodeRange();
		return range;
	}
	
	public boolean enter(ILocation location) {
		CodeRange range = getRange(location);
		if (range == null)
			return false;
		rangeStack.push(range);
		return true;
	}
	
	public void exit(ILocation location) {
		CodeRange range = rangeStack.pop();
		
		int end = range.getEndOffset();
		if (lastEndOffset < end) {
			// add interstitial range
			list.add(new Pair(location, new CodeRange(
					range, 
					range.getNode(), 
					lastEndOffset, 
					end - lastEndOffset)));			
		}
		lastEndOffset = end;
	}
	
	public boolean visit(ILocation location) {
		CodeRange range = getRange(location);
		if (range == null)
			return true;

		CodeRange parentRange = rangeStack.peek();

		int start = range.getOffset();
		if (lastEndOffset < start) {
			// add interstitial range
			list.add(new Pair(location.getParent(), new CodeRange(
					parentRange, 
					parentRange.getNode(), 
					lastEndOffset, 
					start - lastEndOffset)));
		}

		list.add(new Pair(location, range));

		lastEndOffset = range.getEndOffset();
		return true;
	}

}
