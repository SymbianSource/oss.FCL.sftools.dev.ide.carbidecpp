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

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.image.*;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo;
import com.nokia.carbide.internal.api.cpp.epoc.engine.image.IAifDefFileConverter;
import com.nokia.carbide.internal.api.cpp.epoc.engine.image.IPathResolver;
import com.nokia.carbide.internal.cpp.epoc.engine.Messages;
import com.nokia.carbide.internal.cpp.epoc.engine.model.mmp.MMPAIFInfo;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.*;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AifdefFileConverter extends BaseImageConverter implements
		IAifDefFileConverter {

	final static Pattern AIFDEF_LINE_PATTERN = Pattern.compile(
			"([^|]+)\\|([^|]+)\\|(.*)"); //$NON-NLS-1$
	final static Pattern AIFDEF_INLINE_FILES_PATTERN = Pattern.compile(
			"(/?(?:c?)(?:\\d+)(?:,(?:\\d+))?)\\|(.*)"); //$NON-NLS-1$
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.IAifDefFileConverter#convert(org.eclipse.core.runtime.IPath, java.lang.String, java.lang.String)
	 */
	public IMMPAIFInfo convert(IPath targetPath, String fileName, String fileText, IPathResolver resolver) throws CoreException {
		IMMPAIFInfo aifInfo = new MMPAIFInfo();
		
		String fileBase = TextUtils.stripExtension(fileName);
		aifInfo.setTarget(FileUtils.createPossiblyRelativePath(fileBase + ".aif")); //$NON-NLS-1$
		
		String[] lines = fileText.split("\r\n|\r|\n"); //$NON-NLS-1$
		for (String line : lines) {
			if (line.trim().length() == 0)
				continue;
			
			Matcher matcher = AIFDEF_LINE_PATTERN.matcher(line);
			if (matcher.matches()) {
				/*
c:/workspace/CarbideTest/FormTime/|aif/FormTimeAif.rss|c4,1|aif/context_pane_icon.bmp|aif/context_pane_icon_mask.bmp|aif/list_icon.bmp|aif/list_icon_mask.bmp

or

c:/Symbian/Carbide/workspace/Legacy22/|aif/Legacy22Aif.rss|untitled.mbmdef
				 */
				IPath projectPath = new Path(matcher.group(1));
				IPath aifPath = resolver.resolvePath(projectPath.append(matcher.group(2)).toOSString());
				aifInfo.setResource(aifPath);
				
				Matcher imageMatcher = AIFDEF_INLINE_FILES_PATTERN.matcher(matcher.group(3));
				if (imageMatcher.matches()) {
					ImageFormat format = new ImageFormat(imageMatcher.group(1));
					String[] files = imageMatcher.group(2).split("\\|"); //$NON-NLS-1$
					
					aifInfo.setColor(format.isColor());
					aifInfo.setColorDepth(format.getDepth());
					aifInfo.setMaskDepth(format.getMaskDepth());
					
					if (format.getMaskDepth() > 0) {
						// read images and masks
						for (int i = 0; i < files.length - 1; i += 2) {
							IBitmapSourceReference source = aifInfo.createBitmapSourceReference();
							source.setPath(resolver.resolvePath(files[i]));
							source.setMaskPath(resolver.resolvePath(files[i + 1]));
							aifInfo.getSourceBitmaps().add(source);
						}
					} else {
						// read images
						for (int i = 0; i < files.length; i ++) {
							IBitmapSourceReference source = aifInfo.createBitmapSourceReference();
							source.setPath(resolver.resolvePath(files[i]));
							aifInfo.getSourceBitmaps().add(source);
						}
					}
				} else {
					// either .mbm or .mbmdef allowed, theoretically,
					// but only .mbmdef available in Carbide UI
					String mbmdefFile = matcher.group(3);
					IPath mbmdefPath = resolver.resolvePath(projectPath.append(mbmdefFile).toOSString());
					if (mbmdefPath.getDevice() == null)
						mbmdefPath = new Path(projectPath.removeTrailingSeparator().lastSegment()).append(mbmdefPath);
					String mbmdefText = readFileText(mbmdefPath);
					IMultiImageSource multiImageSource = new MbmdefFileConverter().convert(targetPath, mbmdefFile, mbmdefText, resolver);
					if (multiImageSource.getSources().size() > 0) {
						IBitmapSource first = (IBitmapSource) multiImageSource.getSources().get(0);
						aifInfo.setColor(first.isColor());
						aifInfo.setColorDepth(first.getDepth());
						aifInfo.setMaskDepth(first.getMaskDepth());
					}
					boolean reportedIssue = false;
					for (IImageSource imageSource : multiImageSource.getSources()) {
						IBitmapSource bitmapSource = (IBitmapSource) imageSource;
						if (bitmapSource.getDepth() > aifInfo.getColorDepth()) {
							// increase color depth if necessary...
							aifInfo.setColorDepth(bitmapSource.getDepth());
						}
						if (bitmapSource.isColor() && !aifInfo.isColor()) {
							// update grayscale to color if necessary...
							aifInfo.setColor(true);
						}
						if (bitmapSource.getMaskDepth() != aifInfo.getMaskDepth()) {
							// but don't let the mask depth change among mbmdef entries
							if (!reportedIssue) {
								String format = Messages.getString("AifdefFileConverter.AifColorMismatchProblem"); //$NON-NLS-1$
								EpocEnginePlugin.log(
										Logging.newStatus(EpocEnginePlugin.getDefault(),
												IStatus.WARNING,
											MessageFormat.format(format, new Object[] {
													fileName, mbmdefFile
											})));
								reportedIssue = true;
							}
							// toss out invalid entry
							if (bitmapSource.getMaskDepth() == 0 && aifInfo.getMaskDepth() > 0)
								continue;
						} 

						aifInfo.getSourceBitmaps().add(bitmapSource);
					}
				}
				break;
			}
		}
		
		return aifInfo;
	}

}
