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
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;

public class DocumentSourceLocation implements IDocumentSourceRegion {

	private IDocument document;
	private IPath location;
	private IRegion region;

	public DocumentSourceLocation(IDocument document, IPath location, IRegion region) {
		Check.checkArg(document);
		Check.checkArg(location);
		Check.checkArg(region);
		this.document = document;
		this.location = location;
		this.region = region;
	}

	@Override
	public String toString() {
		String regionStr = ""; //$NON-NLS-1$
		regionStr = region.getOffset() + " - " + (region.getOffset() + region.getLength()); //$NON-NLS-1$
		return location + " @" + regionStr; //$NON-NLS-1$
	}
	
	@Override
	public int hashCode() {
		return document.hashCode() ^ (location.hashCode() << 4) ^ (region.hashCode() >> 1);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#setDocument(org.eclipse.jface.text.IDocument)
	 */
	public void setDocument(IDocument document) {
		Check.checkArg(document);
		this.document = document;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#setLocation(org.eclipse.core.runtime.IPath)
	 */
	public void setLocation(IPath location) {
		Check.checkArg(location);
		this.location = location;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#setRegion(org.eclipse.jface.text.IRegion)
	 */
	public void setRegion(IRegion region) {
		Check.checkArg(region);
		this.region = region;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getDocument()
	 */
	public IDocument getDocument() {
		return document;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getFullPath()
	 */
	public IPath getFullPath() {
		return FileUtils.convertToWorkspacePath(location);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getLocation()
	 */
	public IPath getLocation() {
		return location;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getProjectRelativePath()
	 */
	public IPath getProjectRelativePath() {
		IPath projectPath = FileUtils.convertToWorkspacePath(location);
		if (projectPath == null)
			return null;
		return projectPath.removeFirstSegments(1);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getSourceRange()
	 */
	public IRegion getRegion() {
		return region;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getSourceReference()
	 */
	public String getSourceReference() {
		IPath path = getFullPath();
		if (path == null)
			path = getLocation();
		try {
			return path.toOSString() + ":"  //$NON-NLS-1$
				+ (getDocument().getLineOfOffset(getRegion().getOffset()) + 1);
		} catch (BadLocationException e) {
			return path.toOSString();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getMessageLocation()
	 */
	public MessageLocation getMessageLocation() {
		IPath fullPath = null;
		int line = -1;
		int column = -1;
		
		fullPath = getLocation();
		try {
			int offset = getRegion().getOffset();
			line = getDocument().getLineOfOffset(offset) + 1;
			column = offset - getDocument().getLineOffset(line - 1) + 1;
			return new MessageLocation(fullPath, line, column);
		} catch (BadLocationException e) {
			return new MessageLocation(fullPath);
		}
	}
	
	public boolean contains(IDocument document, IRegion region) {
		if (region == null)
			return false;
		if (this.document != document)
			return false;
		if (this.region != null
				&& this.region.getOffset() <= region.getOffset()
				&& this.region.getOffset() + this.region.getLength() >= 
					region.getOffset() + region.getLength()) {
			return true;
		}
		return false;
	}
	
	public boolean contains(ISourceRegion location) {
		if (location == null)
			return false;
		/*
		if (!(location instanceof IDocumentSourceLocation)) {
			location = location.getOptimizedSourceLocation();
		}
		*/
		if (location instanceof IDocumentSourceRegion) {
			IDocumentSourceRegion docRegion = (IDocumentSourceRegion) location;
			if (docRegion.getDocument() != document)
				return false;
			IRegion inRegion = docRegion.getRegion();
			if (inRegion != null
					&& region.getOffset() <= inRegion.getOffset()
					&& region.getOffset() + region.getLength() >= inRegion.getOffset() + inRegion.getLength()) {
				return true;
			}
		}
		return false;
	}

	public ISourceRegion copy() {
		DocumentSourceLocation ret = new DocumentSourceLocation(document, location, region);
		return ret;
	}

	public boolean equals(Object location) {
		if (!(location instanceof IDocumentSourceRegion))
			return false;
		IDocumentSourceRegion docRegion = (IDocumentSourceRegion) location;
		return docRegion.getDocument() == this.document
		&& docRegion.getLocation().equals(this.location)
		&& docRegion.getRegion().equals(this.region);
	}

	public String getText() {
		try {
			return document.get(region.getOffset(), region.getLength());
		} catch (BadLocationException e) {
			Check.failedArg(e);
			return null;
		}
	}
	
	public ISourceRegion getCanonicalSourceRegion() {
		return this.copy();
	}
	
	public IDocumentSourceRegion getExclusiveTailRegion() {
		return new DocumentSourceLocation(document, location, new Region(region.getOffset() + region.getLength(), 0));
	}

	public IDocumentSourceRegion getExclusiveHeadRegion() {
		return new DocumentSourceLocation(document, location, new Region(region.getOffset(), 0));
	}

	public IDocumentSourceRegion getInclusiveHeadRegion() {
		return this;
	}
	public IDocumentSourceRegion getInclusiveTailRegion() {
		return this;
	}

	public int compareTo(IDocumentSourceRegion docRegion) {
		if (document != docRegion.getDocument())
			throw new IllegalArgumentException();
		if (region.getOffset() + region.getLength() < docRegion.getRegion().getOffset())
			return -1;
		if (region.getOffset() > docRegion.getRegion().getOffset() + docRegion.getRegion().getLength())
			return 1;
		return 0;
	}
	
	public void validate() throws IllegalStateException {
		if (document == null || location == null || region == null)
			throw new IllegalStateException("missing document, location, or region"); //$NON-NLS-1$
		if (region.getOffset() < 0 || region.getOffset() + region.getLength() > document.getLength())
			throw new IllegalStateException("region is outside document"); //$NON-NLS-1$
	}
	
	public int accept(ISourceRegionVisitor visitor) {
		int ret = visitor.visit(this);
		if (ret != ISourceRegionVisitor.VISIT_ABORT) {
			ret = visitor.visit((ISourceRegion)this);
		}
		return ret;
	}
	
	public IDocument getFirstDocument() {
		return document;
	}
	
	public IPath getFirstLocation() {
		return location;
	}
	
	public boolean isEmpty() {
		return region.getLength() == 0;
	}
	
	public ISourceRegion getRegionWithout(ISourceRegion removeLocation_) {
		Check.checkArg(removeLocation_ instanceof IDocumentSourceRegion);
		IDocumentSourceRegion removeDocRegion = (IDocumentSourceRegion) removeLocation_;
		if (removeDocRegion.getDocument() != document) {
			return this;
		}
		IRegion removeRegion = removeDocRegion.getRegion();
		int removeStart = removeRegion.getOffset();
		int removeEnd = removeStart + removeRegion.getLength();
		int start = region.getOffset();
		int end = start + region.getLength();
		if (removeStart >= end) {
			return this;
		}
		if (start >= removeEnd) {
			return this;
		}
		// they intersect.  The arg may be inside this or at a boundary
		int newStart = 0, newEnd = 0;
		if (start >= removeStart) {
			if (end <= removeEnd) {
				// empty
				newStart = end;
				newEnd = end;
			} else {
				// remove prefix
				newStart = removeEnd;
				newEnd = end;
			}
		} else if (end <= removeEnd) {
			// remove suffix
			newStart = start;
			newEnd = removeStart;
		} else {
			// remove middle!
			IMultiDocumentSourceRegion multiRegion = new MultiDocumentSourceLocation();
			multiRegion.addSourceRegion(new DocumentSourceLocation(document, location,
					new Region(start, removeStart - start)));
			multiRegion.addSourceRegion(new DocumentSourceLocation(document, location,
					new Region(removeEnd, end - removeEnd)));
			return multiRegion.getCanonicalSourceRegion();
		}
		
		return new DocumentSourceLocation(document, location, 
				new Region(newStart, newEnd - newStart));
	}
	
	public IDocumentSourceRegion extendToStart() {
		return new DocumentSourceLocation(document, location,
				new Region(0, region.getOffset() + region.getLength()));
	}
	
	public IDocumentSourceRegion[] getDocumentSourceRegions() {
		return new IDocumentSourceRegion[] { this };
	}
}
