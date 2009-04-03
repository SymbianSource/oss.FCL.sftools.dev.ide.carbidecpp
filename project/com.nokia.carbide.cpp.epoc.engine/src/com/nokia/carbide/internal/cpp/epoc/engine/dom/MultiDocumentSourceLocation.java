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
package com.nokia.carbide.internal.cpp.epoc.engine.dom;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IDocumentSourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IMultiDocumentSourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ISourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ISourceRegionVisitor;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MultiDocumentSourceLocation implements
		IMultiDocumentSourceRegion {

	List<IDocumentSourceRegion> regions;
	
	public MultiDocumentSourceLocation() {
		this.regions = new ArrayList<IDocumentSourceRegion>(2);
	}
	
	public MultiDocumentSourceLocation(List<IDocumentSourceRegion> newRegions) {
		Check.checkArg(newRegions);
		this.regions = newRegions;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("["); //$NON-NLS-1$
		for (IDocumentSourceRegion region : regions) {
			builder.append(region);
			builder.append("\n"); //$NON-NLS-1$
		}
		builder.append("]"); //$NON-NLS-1$
		return builder.toString();
	}

	public void addSourceRegion(ISourceRegion region) {
		if (region instanceof IDocumentSourceRegion)
			addDocumentSourceRegion((IDocumentSourceRegion) region);
		else {
			MultiDocumentSourceLocation multiLocation = (MultiDocumentSourceLocation) region;
			for (IDocumentSourceRegion docRegion : multiLocation.regions) {
				addDocumentSourceRegion(docRegion);
			}
		}
	}

	private void addDocumentSourceRegion(IDocumentSourceRegion docRegion) {
		regions.add(docRegion);
	}

	public IDocumentSourceRegion[] getDocumentSourceRegions() {
		return (IDocumentSourceRegion[]) regions.toArray(new IDocumentSourceRegion[regions.size()]);
	}

	public boolean contains(ISourceRegion otherRegion) {
		// locations is pre-canonicalized so we won't have two locs
		// that are in the same document, so no need to check partial ranges
		List<IDocumentSourceRegion> remainingRegions = new LinkedList<IDocumentSourceRegion>(regions);
		IDocumentSourceRegion[] docSourceRegions = otherRegion.getDocumentSourceRegions();
		for (IDocumentSourceRegion otherDocRegion : docSourceRegions) {
			boolean found = false;
			for (Iterator<IDocumentSourceRegion> iter = remainingRegions.iterator(); iter.hasNext(); ) {
				IDocumentSourceRegion remainingRegion = iter.next();
				if (remainingRegion.contains(otherDocRegion)) {
					found = true;
					if (remainingRegion.equals(otherDocRegion)) {
						iter.remove();
					}
					break;
				}
			}
			if (!found)
				return false;
		}
		return true;
	}

	public ISourceRegion copy() {
		MultiDocumentSourceLocation ret = new MultiDocumentSourceLocation();
		ret.regions = new ArrayList<IDocumentSourceRegion>(regions.size());
		for (IDocumentSourceRegion region : regions) {
			ret.regions.add((IDocumentSourceRegion) region.copy());
		}
		return ret;
	}

	public boolean equals(Object location) {
		if (location instanceof MultiDocumentSourceLocation) {
			MultiDocumentSourceLocation multiLocation = (MultiDocumentSourceLocation) location;
			if (multiLocation.regions.size() != regions.size())
				return false;
			for (int idx = 0; idx < regions.size(); idx++) {
				if (!multiLocation.regions.get(idx).equals(regions.get(idx)))
					return false;
			}
			return true;
		}
		return false;
	}

	public MessageLocation getMessageLocation() {
		return regions.get(0).getMessageLocation();
	}

	public ISourceRegion getCanonicalSourceRegion() {
		if (regions.size() == 0) {
			return null;
		}
		if (regions.size() == 1) {
			return regions.get(0).copy();
		}
		
		List<IDocumentSourceRegion> newRegions = new ArrayList<IDocumentSourceRegion>(regions.size());
		IDocument lastDocument = null;
		IPath lastLocation = null;
		IRegion lastRegion = null;
		for (IDocumentSourceRegion region : regions) {
			if (region.getRegion().getLength() == 0)
				continue;
			if (region.getDocument() == lastDocument
					&& lastRegion.getOffset() + lastRegion.getLength() == region.getRegion().getOffset()) {
				// combine
				IDocumentSourceRegion loc = new DocumentSourceLocation(
						lastDocument, lastLocation, 
						new Region(lastRegion.getOffset(), 
								region.getRegion().getOffset() + region.getRegion().getLength() - lastRegion.getOffset()));
				newRegions.set(newRegions.size() - 1, loc);
				lastDocument = loc.getDocument();
				lastLocation = loc.getLocation();
				lastRegion = loc.getRegion();
			} else {
				newRegions.add((IDocumentSourceRegion) region.copy());
				lastDocument = region.getDocument();
				lastLocation = region.getLocation();
				lastRegion = region.getRegion();
			}
		}
		
		if (newRegions.size() == 0) {
			return null;
		}
		if (newRegions.size() == 1) {
			return newRegions.get(0).copy();
		}
		return new MultiDocumentSourceLocation(newRegions);
	}

	public String getSourceReference() {
		return regions.get(0).getSourceReference();
	}

	public String getText() {
		StringBuilder builder = new StringBuilder();
		for (IDocumentSourceRegion region : regions) {
			builder.append(region.getText());
		}
		return builder.toString();
	}

	public IDocumentSourceRegion getExclusiveTailRegion() {
		return regions.get(regions.size() - 1).getExclusiveTailRegion();
	}
	public IDocumentSourceRegion getExclusiveHeadRegion() {
		return regions.get(0).getExclusiveHeadRegion();
	}
	public IDocumentSourceRegion getInclusiveHeadRegion() {
		return regions.get(0).getInclusiveHeadRegion();
	}
	public IDocumentSourceRegion getInclusiveTailRegion() {
		return regions.get(regions.size() - 1).getInclusiveTailRegion();
	}
	
	public void validate() throws IllegalStateException {
		for (IDocumentSourceRegion region : regions)
			region.validate();

		for (int i = 0; i < regions.size() - 1; i++) {
			IDocumentSourceRegion iLoc = regions.get(i);
			for (int j = i + 1; j < regions.size(); j++) {
				IDocumentSourceRegion jLoc = regions.get(j);
				if (iLoc.contains(jLoc) || jLoc.contains(iLoc)) {
					throw new IllegalStateException("overlapping location added: " + iLoc + " and " + jLoc); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
		}
	}
	
	public int accept(ISourceRegionVisitor visitor) {
		int ret = visitor.visit(this);
		if (ret != ISourceRegionVisitor.VISIT_ABORT) {
			ret = visitor.visit((ISourceRegion)this);
		}
		if (ret == ISourceRegionVisitor.VISIT_ABORT)
			return ret;
		if (ret == ISourceRegionVisitor.VISIT_SIBLINGS)
			return ret;
		for (IDocumentSourceRegion region : regions) {
			ret = visitor.visit(region);
			if (ret == ISourceRegionVisitor.VISIT_ABORT)
				return ret;
		}
		return ret;
	}
	
	public IDocument getFirstDocument() {
		return regions.get(0).getFirstDocument();
	}
	
	public IPath getFirstLocation() {
		return regions.get(0).getFirstLocation();
	}
	
	public boolean isEmpty() {
		for (IDocumentSourceRegion region : regions) {
			if (!region.isEmpty())
				return false;
		}
		return true;
	}
	
	public ISourceRegion getRegionWithout(ISourceRegion removeRegion) {
		IMultiDocumentSourceRegion multiDocumentSourceRegion = new MultiDocumentSourceLocation();
		ISourceRegion lastRegion = null;
		for (ISourceRegion region : regions) {
			for (IDocumentSourceRegion removeDocRegion : removeRegion.getDocumentSourceRegions()) {
				region = region.getRegionWithout(removeDocRegion);
			}
			if (!region.isEmpty()) {
				multiDocumentSourceRegion.addSourceRegion(region);
			}
			lastRegion = region;
		}
		if (multiDocumentSourceRegion.isEmpty()) {
			multiDocumentSourceRegion.addSourceRegion(lastRegion);
		}
		return multiDocumentSourceRegion.getCanonicalSourceRegion();
	}
}
