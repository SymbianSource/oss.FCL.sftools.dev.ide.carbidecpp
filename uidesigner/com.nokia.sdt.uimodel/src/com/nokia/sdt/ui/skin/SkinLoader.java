/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.ui.skin;

import com.nokia.sdt.ui.skin.impl.SkinTypeImpl;
import com.nokia.sdt.ui.skin.util.SkinResourceFactoryImpl;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 
 *
 */
public class SkinLoader {
	public static ISkin loadSkin(URL url) throws URISyntaxException, IOException  {
		if (url == null)
			return null;

		SkinResourceFactoryImpl factory = new SkinResourceFactoryImpl();
		Resource r = null;
		URI xmlURI = URI.createURI(url.toString());
		r = factory.createResource(xmlURI);
		((XMLResource) r).getDefaultSaveOptions().put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.FALSE);

		r.load(null);
		EList contents = r.getContents();
	
		DocumentRoot root = (DocumentRoot) contents.get(0);
		SkinTypeImpl skin = (SkinTypeImpl) root.getSkin();
		// now get the skin's location
		URL resolved = FileLocator.resolve(url);
		Path path = new Path(resolved.getFile());
		File file = path.toFile();
		String parent = file.getParent();
		skin.setID(TextUtils.stripExtension(file.getName()));
		skin.setDirectory(parent);
		return skin;
	} 
}
