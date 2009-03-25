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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import com.nokia.carbide.cpp.epoc.engine.image.IBitmapSource;
import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.image.ImageFormat;
import com.nokia.carbide.cpp.epoc.engine.model.EGeneratedHeaderFlags;
import com.nokia.carbide.internal.api.cpp.epoc.engine.image.IMbmMifDefFileConverter;
import com.nokia.carbide.internal.api.cpp.epoc.engine.image.IPathResolver;
import com.nokia.carbide.internal.cpp.epoc.engine.model.mmp.MMPBitmap;
import com.nokia.cpp.internal.api.utils.core.*;


public class MbmdefFileConverter extends BaseImageConverter implements IMbmMifDefFileConverter {

	private static Pattern MBMDEF_LINE_PATTERN = Pattern.compile(
			"([^|]+)\\|([^|]+)(?:\\|(.*))?" //$NON-NLS-1$
			);
	
	public MbmdefFileConverter() {
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.ICoronaImageFileConverter#convert(org.eclipse.core.runtime.IPath)
	 */
	public IMultiImageSource convert(IPath targetPath, String fileName, String fileText, IPathResolver resolver) throws CoreException {
		IMultiImageSource mis = new MMPBitmap();
		
		String fileBase = TextUtils.stripExtension(fileName);
		mis.setTargetFile(fileBase + ".mbm"); //$NON-NLS-1$
		
		mis.setTargetPath(targetPath);
		mis.setHeaderFlags(EGeneratedHeaderFlags.Header);
		//mis.setGeneratedHeaderFilePath(new Path("epoc32/include").append(fileBase + ".mbg"));
		
		String[] lines = fileText.split("\r\n|\r|\n"); //$NON-NLS-1$
		for (String line : lines) {
			if (line.trim().length() == 0)
				continue;
			
			Matcher matcher = MBMDEF_LINE_PATTERN.matcher(line);
			if (matcher.matches()) {
				ImageFormat format = new ImageFormat(matcher.group(1));
				// either project-relative or absolute
				IPath imageFile = resolver.resolvePath(matcher.group(2));
				IPath maskFile = null;
				if (matcher.group(3) != null && matcher.group(3).length() > 0) {
					// either project-relative or absolute
					maskFile = resolver.resolvePath(matcher.group(3));
				}
				IBitmapSource source = mis.createBitmapSource();
				source.setPath(imageFile);
				source.setColor(format.isColor());
				source.setDepth(format.getDepth());
				source.setMaskDepth(format.getMaskDepth());
				if (maskFile != null) {
					source.setMaskPath(maskFile);
				}
				
				mis.getSources().add(source);
			}
		}
		
		return mis;
	}

}
