/*
* Copyright (c) 2007-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.model.makefile.image;

import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileData;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileView;
import com.nokia.carbide.internal.cpp.epoc.engine.model.BaseData;

import org.eclipse.cdt.make.core.makefile.IMakefile;

import java.util.*;


public class ImageMakefileData extends BaseData<IImageMakefileView> implements IImageMakefileData {

	
	private String defaultTarget;
	private IMakefile makefile;
	private List<IMultiImageSource> multiImageSources;

	public ImageMakefileData(ImageMakefileView view) {
		super(view);
		this.makefile = view.getMakefile();
		this.defaultTarget = view.getDefaultImageTarget();
		this.multiImageSources = Collections.unmodifiableList(copyMultiImageSources(view.getMultiImageSources()));
	}

	/**
	 * @param multiImageSources
	 * @return
	 */
	private List<IMultiImageSource> copyMultiImageSources(
			List<IMultiImageSource> multiImageSources) {
		List<IMultiImageSource> copy = new ArrayList<IMultiImageSource>();
		for (IMultiImageSource mis : multiImageSources)
			copy.add(mis.copy());
		return copy;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.IImageMakefileData#getDefaultImageTarget()
	 */
	public String getDefaultImageTarget() {
		return defaultTarget;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.IImageMakefileData#getMakefile()
	 */
	public IMakefile getMakefile() {
		return makefile;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.IImageMakefileData#getMultiImageSources()
	 */
	public List<IMultiImageSource> getMultiImageSources() {
		return multiImageSources;
	}
}
