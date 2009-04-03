/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.symbian.images.uriHandlers;

import com.nokia.carbide.cpp.ui.images.IImageModel;
import com.nokia.sdt.symbian.Messages;
import com.nokia.sdt.symbian.images.*;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

import org.eclipse.core.runtime.*;

import java.io.File;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handler for "file" and "" schemes.  
 * 
 *
 */
public class FileURIImageInfoSchemeHandler extends ProjectBasedURIImageInfoSchemeHandlerBase implements
		IURIImageSchemeHandler {

	/** This pattern is used EITHER for mbm dereferencing OR animation specification */
	private static final Pattern IDX_MASK_PATTERN = Pattern.compile("idx=(-?\\d+)(?:&mask=(-?\\d+))?"); //$NON-NLS-1$
	
	public FileURIImageInfoSchemeHandler(ProjectImageInfo projectImageInfo) {
		super(projectImageInfo);
	}
	
	public IImageModel getImageModel(String scheme, String path,
			String query) {
		final IPath hostPath = convertToHostImage(path);
		IImageModel model = null;
		if (query != null) {
			// MBM or animation ref
			Matcher matcher = IDX_MASK_PATTERN.matcher(query);
			if (!matcher.matches())
				return null;
			int idx = Integer.parseInt(matcher.group(1));
			if (idx >= 0) {
				MultiImageInfo info = convertToHostMbm(path);
				if (info == null)
					return null;
				final ImageInfo bitmapinfo = info.getImageAtIndex(Integer.valueOf(matcher.group(1)));
				if (bitmapinfo == null)
					return null;
				final ImageInfo maskInfo;
				if (matcher.group(2) != null) {
					maskInfo = info.getImageAtIndex(Integer.valueOf(matcher.group(2)));
					if (maskInfo == null)
						return null;
				} else {
					maskInfo = null;
				}
				
				model = SymbianImageModelFactory.createSymbianMaskedImageFileModel(info, bitmapinfo, maskInfo);
				return model;
			} else {
				// animation reference; fall through and get first frame
			}
		}
		
		// basic image or first frame of animation
		if (hostPath == null)
			return null;
		
		File file = hostPath.toFile();
		if (!file.exists()) {
			return null;
		}
		
		model = SymbianImageModelFactory.createFileImageModel(
				projectImageInfo.getExternalImageContainerModel(),
				hostPath);
		
		return model;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.images.IURIImageInfoSchemeHandler#validate(java.lang.String, java.lang.String, java.lang.String)
	 */
	public IStatus validate(String scheme, String path, String query) {
		boolean isMbmOrAnimation = false;
		int idx = 0;
		if (query != null) {
			// for a file, this is a MBM/MIF or animation reference
			Matcher matcher = IDX_MASK_PATTERN.matcher(query);
			if (!matcher.matches()) {
				return createStatus(IStatus.ERROR, MessageFormat.format(
						Messages.getString("FileURIImageInfoSchemeHandler.InvalidQueryPattern"), //$NON-NLS-1$
						new Object[] { IDX_MASK_PATTERN.toString() }));
			}
			idx = Integer.parseInt(matcher.group(1));
			isMbmOrAnimation = true;
		}
		
		MultiImageInfo info = convertToHostMbm(path);
		IPath hostPath = convertToHostImage(path);
		
		// check that a match, if any, if a valid type, but ignore nonexistent files
		if (isMbmOrAnimation) {
			// ensure the file resolves to an MBM or an animation.
			// Animations use idx=-1; otherwise it's an MBM ref
			
			if (idx >= 0 && info == null) {
				return createStatus(IStatus.ERROR,
						Messages.getString("FileURIImageInfoSchemeHandler.NotAnMbmError")); //$NON-NLS-1$
			} else if (idx < 0 && info != null) {
				return createStatus(IStatus.ERROR,
						Messages.getString("FileURIImageInfoSchemeHandler.CannotAnimateMbm"));  //$NON-NLS-1$
			}
		} else {
			// ensure the file, if found, does not resolve to an MBM
			if (hostPath == null && info != null) {
				return createStatus(IStatus.ERROR, 
						Messages.getString("FileURIImageInfoSchemeHandler.NotAFileError")); //$NON-NLS-1$
			}
		}
		
		// only allow SVG in newer SDKs
		if (hostPath != null
				&& !projectImageInfo.supportsSVG()
				&& FileUtils.getSafeFileExtension(hostPath).equalsIgnoreCase("svg")) { //$NON-NLS-1$
			return createStatus(IStatus.ERROR,
					Messages.getString("FileURIImageInfoSchemeHandler.NoSVGSupport")); //$NON-NLS-1$
		}
		
		// don't care if the file doesn't exist, here
		return Status.OK_STATUS;
	}

	/**
	 * Take the URI path and convert it to a generated MBM on the host.
	 * @param projectImageInfo_ the project image info
	 * @param path the URI path
	 * @return full path on host, or <code>null</code> 
	 */
	private MultiImageInfo convertToHostMbm(String pathString) {
		return projectImageInfo.getInfoForBinaryFile(pathString);
	}
}
