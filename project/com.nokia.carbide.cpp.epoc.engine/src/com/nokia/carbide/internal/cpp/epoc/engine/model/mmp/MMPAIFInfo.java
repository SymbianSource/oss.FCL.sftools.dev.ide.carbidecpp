/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.model.mmp;

import com.nokia.carbide.cpp.epoc.engine.image.IBitmapSourceReference;
import com.nokia.carbide.cpp.epoc.engine.image.ImageFormat;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo;
import com.nokia.carbide.internal.cpp.epoc.engine.image.BitmapSourceReference;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;

import java.util.ArrayList;
import java.util.List;


public class MMPAIFInfo implements IMMPAIFInfo {

	private IPath resource;
	private IPath target;
	private int depth;
	private int maskDepth;
	private boolean color;
	private List<IBitmapSourceReference> bitmaps;

	public MMPAIFInfo() {
		this.bitmaps = new ArrayList<IBitmapSourceReference>(1);
	}

	public MMPAIFInfo(IPath target, IPath resource,
			boolean isColor, int colorDepth, int maskDepth, List<IBitmapSourceReference> bitmaps) {
		setTarget(target);
		setResource(resource);
		setColor(isColor);
		setColorDepth(colorDepth);
		setMaskDepth(maskDepth);
		setSourceBitmaps(bitmaps);
	}

	public MMPAIFInfo(IPath target, IPath resource) {
		this();
		setTarget(target);
		setResource(resource);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MMPAIFInfo: target="+target+" resource="+resource+" color="+color+" depth="+depth+" maskDepth="+maskDepth+ //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		" bitmaps="+bitmaps; //$NON-NLS-1$
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IMMPAIFInfo))
			return false;
		IMMPAIFInfo other = (IMMPAIFInfo) obj;
		return ObjectUtils.equals(other.getTarget(), target)
		&& ObjectUtils.equals(other.getResource(), resource)
		&& other.isColor() == isColor()
		&& other.getColorDepth() == depth
		&& other.getMaskDepth() == maskDepth
		&& ObjectUtils.equals(other.getSourceBitmaps(), bitmaps);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo#isValid()
	 */
	public boolean isValid() {
		if (target != null && resource != null
		&& (depth == 0 || bitmaps.size() > 0)) {
			// validate that bitmaps make sense
			if (maskDepth > 0) {
				for (IBitmapSourceReference ref : bitmaps) {
					if (ref.getMaskPath() == null)
						return false;
				}
			} 
			return true;
		} else {
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo#getResource()
	 */
	public IPath getResource() {
		return resource;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo#getSourceBitmaps()
	 */
	public List<IBitmapSourceReference> getSourceBitmaps() {
		return bitmaps;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo#getTarget()
	 */
	public IPath getTarget() {
		return target;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo#setResource(org.eclipse.core.runtime.IPath)
	 */
	public void setResource(IPath path) {
		this.resource = path;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo#setTarget(org.eclipse.core.runtime.IPath)
	 */
	public void setTarget(IPath path) {
		this.target = path;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo#getColorDepth()
	 */
	public int getColorDepth() {
		return depth;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo#getMaskDepth()
	 */
	public int getMaskDepth() {
		return maskDepth;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo#isColor()
	 */
	public boolean isColor() {
		return color;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo#setColor(boolean)
	 */
	public void setColor(boolean color) {
		this.color = color;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo#setColorDepth(int)
	 */
	public void setColorDepth(int depth) {
		Check.checkArg(depth >= 0);
		this.depth = depth;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo#setMaskDepth(int)
	 */
	public void setMaskDepth(int depth) {
		Check.checkArg(depth >= 0);
		this.maskDepth = depth;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo#setSourceBitmaps(java.util.List)
	 */
	public void setSourceBitmaps(List<IBitmapSourceReference> bitmaps) {
		this.bitmaps = bitmaps;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#createBitmapSourceReference()
	 */
	public IBitmapSourceReference createBitmapSourceReference() {
		return new BitmapSourceReference();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo#getImageFormat()
	 */
	public ImageFormat getImageFormat() {
		return new ImageFormat(color, depth, maskDepth);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo#copy()
	 */
	public IMMPAIFInfo copy() {
		MMPAIFInfo info = new MMPAIFInfo(target, resource, color, depth, maskDepth,
				copyBitmapSources());
		return info;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo#set(com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo)
	 */
	public void set(IMMPAIFInfo aifInfo) {
		this.target = aifInfo.getTarget();
		this.resource = aifInfo.getResource();
		this.color = aifInfo.isColor();
		this.depth = aifInfo.getColorDepth();
		this.maskDepth = aifInfo.getMaskDepth();
		this.bitmaps = ((MMPAIFInfo)aifInfo).copyBitmapSources();
	}

	/**
	 */
	private List<IBitmapSourceReference> copyBitmapSources() {
		List<IBitmapSourceReference> list = new ArrayList<IBitmapSourceReference>();
		for (IBitmapSourceReference ref : bitmaps) {
			list.add((IBitmapSourceReference) ref.copy());
		}
		return list;
	}
}
