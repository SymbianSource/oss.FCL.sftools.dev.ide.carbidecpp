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
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.image.ISVGSource;
import com.nokia.carbide.cpp.epoc.engine.image.ImageFormat;
import com.nokia.carbide.internal.api.cpp.epoc.engine.image.IMbmMifDefFileConverter;
import com.nokia.carbide.internal.api.cpp.epoc.engine.image.IPathResolver;
import com.nokia.cpp.internal.api.utils.core.*;


public class MifdefFileConverter extends BaseImageConverter implements IMbmMifDefFileConverter {

	private static Pattern MIFDEF_LINE_PATTERN = Pattern.compile(
			"([^|]+)\\|(.*)" //$NON-NLS-1$
			);
	
	public MifdefFileConverter() {
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.image.ICoronaImageFileConverter#convert(org.eclipse.core.runtime.IPath)
	 */
	public IMultiImageSource convert(IPath targetPath, String fileName, String fileText, IPathResolver resolver) throws CoreException {
		IMultiImageSource mis = new MultiImageSource(true, false, true);
		
		String fileBase = TextUtils.stripExtension(fileName);
		mis.setTargetFile(fileBase + ".mif"); //$NON-NLS-1$
		
		mis.setTargetPath(targetPath);
		mis.setGeneratedHeaderFilePath(new Path("epoc32/include").append(fileBase + ".mbg")); //$NON-NLS-1$ //$NON-NLS-2$
		
		String[] lines = fileText.split("\r\n|\r|\n"); //$NON-NLS-1$
		for (String line : lines) {
			if (line.trim().length() == 0)
				continue;
			
			Matcher matcher = MIFDEF_LINE_PATTERN.matcher(line);
			if (matcher.matches()) {
				ImageFormat format = new ImageFormat(matcher.group(1));
				IPath imageFile = resolver.resolvePath(matcher.group(2));
				ISVGSource source = mis.createSVGSource();
				source.setPath(imageFile);
				source.setColor(format.isColor());
				source.setDepth(format.getDepth());
				source.setMaskDepth(format.getMaskDepth());
				mis.getSources().add(source);
			}
		}
		
		return mis;
	}

}
