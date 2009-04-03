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
package com.nokia.sdt.component.symbian.laf;

import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.component.symbian.displaymodel.DisplayModelBase;
import com.nokia.sdt.component.symbian.displaymodel.Messages;
import com.nokia.sdt.component.symbian.visual.javascript.Fonts;
import com.nokia.sdt.displaymodel.XMLLookAndFeel;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.utils.ImageUtils;
import com.nokia.sdt.utils.drawing.IFont;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Display;

import java.io.*;
import java.net.URL;
import java.text.MessageFormat;

	/**
	 * Subclass of XMLLookAndFeel that adds support for instantiating 
	 * fonts and images.
	 *
	 */
public class SymbianLookAndFeel extends XMLLookAndFeel {

	private DisplayModelBase displayModelBase;

	public SymbianLookAndFeel(DisplayModelBase displayModelBase, URI xmlURI) throws IOException {
		super();
		Check.checkArg(displayModelBase);
		this.displayModelBase = displayModelBase;
		initFromURI(xmlURI);
	}
	
	public SymbianLookAndFeel(DisplayModelBase displayModelBase) {
		super();
		Check.checkArg(displayModelBase);
		this.displayModelBase = displayModelBase;
	}
	
	protected SymbianLookAndFeel() {
		
	}
	
	private static Image loadImage(File imageFile) {
		Image result = null;
		try {
			ImageData imageData = loadImageData(imageFile);
			if (imageData != null) {
				result = new Image(Display.getDefault(), imageData);
			}
		}
		catch (IOException x) {
			ComponentSystemPlugin.log(x);
		}
		return result;
	}

	private static Image loadMaskedImage(File imageFile, File maskFile) {
		Image result = null;
		try {
			ImageData imageData = loadImageData(imageFile);
			ImageData imageMaskData = loadImageData(maskFile);
			if (imageData != null && imageMaskData != null) {
				// To avoid adding new API just to say whether
				// a mask is 0/1 based or alpha based
                if (maskFile.getName().indexOf("mask_soft.") > 0) //$NON-NLS-1$
                    result = ImageUtils.createAlphaMaskedImage(Display.getDefault(),
                            imageData, imageMaskData);
                else
                    result = ImageUtils.createMaskedImage(Display.getDefault(),
							imageData, imageMaskData);
			}
		}
		catch (IOException x) {
			ComponentSystemPlugin.log(x);
		}
		return result;
	}

	private static ImageData loadImageData(File imageFile) throws IOException {
		ImageData result = null;
		FileInputStream is = new FileInputStream(imageFile);
		if (is != null) {
			ImageLoader loader = new ImageLoader();
			ImageData[] data = loader.load(is);
			is.close();
			if (data != null && data.length >= 1) {
				result = data[0];
			}
		}
		return result;
	}

    /**
     * Resolve an existing file relative to the base URL
     * @param baseURL base directory 
     * @param relPath the relative path to the base URL
     * @return new file or null
     */
	private File findRelativeFile(URL baseURL, String relPath) {
		Throwable t = null;
		URL url = null;
		
		try {
			url = FileLocator.resolve(new URL(baseURL, relPath));
		} catch (IOException e) {
			url = null;
		}
		
		if (url == null || !url.getProtocol().equals("file")) { //$NON-NLS-1$
			String param;
			if (url != null)
				param = url.toString();
			else
				param = baseURL.toString() + relPath; //$NON-NLS-1$ //$NON-NLS-2$
			Object params[] = {param };
			String messagePattern = Messages.getString("SymbianLookAndFeel.CannotFindFile"); 
			String msg = MessageFormat.format(messagePattern, params);
			ComponentSystemPlugin.log(t, msg);
			return null;
		}
		
		return new File(url.getPath());
	}

	protected void addFont(String key, String relPath, float size) {
		IFont font = null;
		
		// fonts are font relative to the display model's font base
		File fontFile = findRelativeFile(displayModelBase.getFontURL("."), relPath);
		if (fontFile != null) {
			font = Fonts.getFont(fontFile, size);
		}
		if (font != null) {
			addFont(key, font);
		}
		else {
			String fmt = Messages.getString("SymbianLookAndFeel.CannotLoadFont"); //$NON-NLS-1$
			Object params[] = {relPath};
			String msg = MessageFormat.format(fmt, params);
			ComponentSystemPlugin.log(null, msg);
		}
	}

	protected void addMaskedImage(String key, String baseDir, String imageFilePath, String maskFilePath) {
		URL baseURL = displayModelBase.getImageURL(baseDir != null ? baseDir : "."); //$NON-NLS-1$
		File imageFile = findRelativeFile(baseURL, imageFilePath);
		File maskFile = maskFilePath != null ? findRelativeFile(baseURL, maskFilePath) : null;
		Image image = null;
		if (imageFile != null && maskFile != null) {
			image = loadMaskedImage(imageFile, maskFile);
		} else if (imageFile != null) {
			image = loadImage(imageFile);
		} 
		if (image != null) {
			addImage(key, image);
		} else {
			String fmt = maskFilePath != null ?
					Messages.getString("SymbianLookAndFeel.CannotLoadMaskedImage") : //$NON-NLS-1$
						Messages.getString("SymbianLookAndFeel.CannotLoadImage"); //$NON-NLS-1$
			Object params[] = {imageFilePath, maskFilePath};
			String msg = MessageFormat.format(fmt, params);
			ComponentSystemPlugin.log(null, msg);
		}
	}

}
