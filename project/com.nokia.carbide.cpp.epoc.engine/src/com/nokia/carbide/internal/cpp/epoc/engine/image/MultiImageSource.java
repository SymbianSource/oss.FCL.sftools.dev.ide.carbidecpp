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

package com.nokia.carbide.internal.cpp.epoc.engine.image;

import com.nokia.carbide.cpp.epoc.engine.image.*;
import com.nokia.carbide.cpp.epoc.engine.model.EGeneratedHeaderFlags;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import java.util.ArrayList;
import java.util.List;


public class MultiImageSource implements IMultiImageSource {

	private EGeneratedHeaderFlags headerFlags;
	private List<IImageSource> sources;
	private String targetFile;
	private IPath targetPath;
	private boolean allowBitmaps;
	private boolean allowSVGs;
	protected IPath generatedHeaderPath;
	private boolean canSetHeaderPath;

	public MultiImageSource(boolean canSetHeaderPath, boolean allowBitmaps, boolean allowSVGs) {
		this.canSetHeaderPath = canSetHeaderPath;
		this.generatedHeaderPath = null;

		this.headerFlags = EGeneratedHeaderFlags.NoHeader;
		this.sources = new ArrayList<IImageSource>();
		this.allowBitmaps = allowBitmaps;
		this.allowSVGs = allowSVGs;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource#copy()
	 */
	public IMultiImageSource copy() {
		MultiImageSource copy = new MultiImageSource(
				canSetHeaderPath, allowBitmaps, allowSVGs);
		copy.sources = copySources();
		copy.targetFile = targetFile;
		copy.targetPath = targetPath;
		copy.headerFlags = headerFlags;
		copy.generatedHeaderPath = generatedHeaderPath;
		return copy;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource#set(com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource)
	 */
	public void set(IMultiImageSource multiImageSource_) {
		MultiImageSource mis = (MultiImageSource) multiImageSource_;
		this.canSetHeaderPath = mis.canSetHeaderPath;
		this.allowBitmaps = mis.allowBitmaps;
		this.allowSVGs = mis.allowSVGs;
		this.targetFile = mis.targetFile;
		this.targetPath = mis.targetPath;
		this.headerFlags = mis.headerFlags;
		setFromSources(mis.getSources());
	}
	
	/**
	 * Rewrite our own source list with copies of the sources from the
	 * other list.  Doesn't change our list's object, in case someone
	 * is holding onto it.
	 */
	private void setFromSources(List<IImageSource> sources) {
		this.sources.clear();
		for (IImageSource imageSource : sources) {
			this.sources.add((IImageSource) imageSource.copy());
		}
	}

	/**
	 * @return
	 */
	protected List<IImageSource> copySources() {
		List<IImageSource> copy = new ArrayList<IImageSource>();
		for (IImageSource imageSource : sources) {
			copy.add((IImageSource) imageSource.copy());
		}
		return copy;
	}

	public IPath getDefaultGeneratedHeaderFilePath() {
		String fileName = targetFile != null && targetFile.length() > 0 ? targetFile : "<unnamed>"; //$NON-NLS-1$
		return new Path("epoc32\\include").append(fileName).removeFileExtension().addFileExtension("mbg");	 //$NON-NLS-1$ //$NON-NLS-2$
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[" + TextUtils.catenateStrings(sources.toArray(), ",") + "] " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			+ "-> " +targetPath+"/"+targetFile + " & " +generatedHeaderPath; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MultiImageSource))
			return false;
		MultiImageSource other = (MultiImageSource) obj;
		return other.headerFlags.equals(headerFlags)
		&& ObjectUtils.equals(other.targetFile, targetFile)
		&& ObjectUtils.equals(other.targetPath, targetPath)
		&& other.sources.equals(sources)
		&& other.canSetHeaderPath == canSetHeaderPath
		&& (!canSetHeaderPath || ObjectUtils.equals(other.generatedHeaderPath, generatedHeaderPath));
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (headerFlags.hashCode() << 31) ^
		((targetFile != null ? targetFile.hashCode() : 283) << 3) ^
		((targetPath != null ? targetPath.hashCode() : 182384) << 9) ^
			sources.hashCode() ^
			((generatedHeaderPath != null ? generatedHeaderPath.hashCode() : -38378283)) ^
			-39291;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPBitmap#isValid()
	 */
	public boolean isValid() {
		return targetFile != null && targetFile.length() > 0; 
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageBuildContainer#getTargetFilePath()
	 */
	public IPath getTargetFilePath() {
		if (targetPath == null)
			if (targetFile == null)
				return null;
			else
				return FileUtils.createPossiblyRelativePath(targetFile);
		else
			if (targetFile == null)
				return targetPath;
			else
				return targetPath.append(targetFile);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPBitmap#getHeaderFlags()
	 */
	public EGeneratedHeaderFlags getHeaderFlags() {
		return headerFlags;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPBitmap#getSources()
	 */
	public List<IImageSource> getSources() {
		return sources;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPBitmap#getTargetFile()
	 */
	public String getTargetFile() {
		return targetFile;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPBitmap#getTargetPath()
	 */
	public IPath getTargetPath() {
		return targetPath;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPBitmap#setHeaderFlags(com.nokia.carbide.cpp.epoc.engine.model.mmp.EGeneratedHeaderFlags)
	 */
	public void setHeaderFlags(EGeneratedHeaderFlags flag) {
		Check.checkArg(flag);
		this.headerFlags = flag;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPBitmap#setSources(java.util.List)
	 */
	public void setSources(List<IImageSource> sources) {
		Check.checkArg(sources);
		this.sources = sources;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPBitmap#setTargetFile(java.lang.String)
	 */
	public void setTargetFile(String name) {
		this.targetFile = name;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPBitmap#setTargetPath(org.eclipse.core.runtime.IPath)
	 */
	public void setTargetPath(IPath path) {
		this.targetPath = path;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageBuildContainer#isBitmapSourceAllowed()
	 */
	public boolean isBitmapSourceAllowed() {
		return allowBitmaps;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageBuildContainer#isSVGSourceAllowed()
	 */
	public boolean isSVGSourceAllowed() {
		return allowSVGs;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource#createUnknownImageSource()
	 */
	public IImageSource createUnknownImageSource() {
		return new ImageSource();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageBuildContainer#createBitmapSource()
	 */
	public IBitmapSource createBitmapSource() {
		if (!allowBitmaps)
			return null;
		return new BitmapSource();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageBuildContainer#createSVGSource()
	 */
	public ISVGSource createSVGSource() {
		if (!allowSVGs)
			return null;
		return new SVGSource();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageBuildContainer#hasDisallowedSources()
	 */
	public boolean hasDisallowedSources() {
		for (IImageSource source : sources) {
			if (source instanceof IBitmapSource && !allowBitmaps)
				return true;
			if (source instanceof ISVGSource && !allowSVGs)
				return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageBuildContainer#getGeneratedHeaderFilePath()
	 */
	public IPath getGeneratedHeaderFilePath() {
		return generatedHeaderPath;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource#canSetGeneratedHeaderFilePath()
	 */
	public boolean canSetGeneratedHeaderFilePath() {
		return canSetHeaderPath;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IImageBuildContainer#setGeneratedHeaderFilePath(org.eclipse.core.runtime.IPath)
	 */
	public void setGeneratedHeaderFilePath(IPath path) {
		if (!canSetHeaderPath)
			throw new UnsupportedOperationException("header file cannot be set"); //$NON-NLS-1$
		this.generatedHeaderPath = path;
		this.headerFlags = path != null ? EGeneratedHeaderFlags.Header : EGeneratedHeaderFlags.NoHeader;
	}
	
	private String constructEnumerator(IPath path) {
		if (path == null)
			return null;
		String mbmBaseName;
		if (targetFile == null || targetFile.length() == 0)
			mbmBaseName = "Unnamed"; //$NON-NLS-1$
		else
			mbmBaseName = TextUtils.titleCase(new Path(targetFile).removeFileExtension().lastSegment().toLowerCase());
		String baseName = TextUtils.titleCase(path.removeFileExtension().lastSegment().toLowerCase());
		return "EMbm" + mbmBaseName + baseName;  //$NON-NLS-1$
	}
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource#getGeneratedImageEnumerator(com.nokia.carbide.cpp.epoc.engine.image.IImageSource)
	 */
	public String getGeneratedImageEnumerator(IImageSource image) {
		return constructEnumerator(image.getPath());
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource#getGeneratedMaskEnumerator(com.nokia.carbide.cpp.epoc.engine.image.IImageSource)
	 */
	public String getGeneratedMaskEnumerator(IImageSource image) {
		if (targetFile == null || image.getMaskDepth() == 0)
			return null;
		IPath maskPath = null;
		if (image instanceof IBitmapSource)
			maskPath = ((IBitmapSource) image).getMaskPath();
		else if (image instanceof ISVGSource)
			maskPath = ((ISVGSource) image).getImpliedMaskPath();
		else if (image.getMaskDepth() > 0)
			maskPath = image.getDefaultMaskPath();
		String enumerator = constructEnumerator(maskPath);
		
		// NOTE: we assume this is a MIF file if it allows SVGs
		if (allowSVGs && enumerator != null)
			enumerator = enumerator.replaceFirst("_soft$", ""); //$NON-NLS-1$ //$NON-NLS-2$
		
		return enumerator;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource#findMatchingSource(org.eclipse.core.runtime.IPath)
	 */
	public IImageSource findMatchingSource(IPath filePath) {
		String enumerator = constructEnumerator(filePath);
		for (IImageSource imageSource : sources) {
			String targetEnum = constructEnumerator(imageSource.getPath());
			if (ObjectUtils.equals(targetEnum, enumerator))
				return imageSource;
			if (imageSource instanceof IBitmapSource) {
				targetEnum = constructEnumerator(((IBitmapSource) imageSource).getMaskPath());
				if (ObjectUtils.equals(targetEnum, enumerator))
					return imageSource;
			}				
		}
		return null;
	}
}
