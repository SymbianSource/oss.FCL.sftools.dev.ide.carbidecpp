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

package com.nokia.sdt.symbian.images;

import com.nokia.sdt.component.property.IPropertyInformation;
import com.nokia.sdt.datamodel.images.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.symbian.SymbianPlugin;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This is the data abstraction for multiple images in one property that
 * share a common MBM/MIF file but select different image/mask pairs from
 * them.
 * <p>
 * This implementation does not allow adding new entries dynamically;
 * thus, it is not suitable for image arrays (yet).
 * 
 *
 */
public class SymbianMultiImagePropertyInfo implements IMultiImagePropertyInfo {
	private String[][] abstractProperties;
	private Map<String, IImagePropertyInfo> infoMap;
	private String fileId;
	private MultiImageInfo multiImageInfo;
	
	/**
	 * Create a multi-image property info for standard Symbian images which consist
	 * of a bmpfile and pairs of bmpid/bmpmasks.
	 * @param converter
	 * @param ps compound property holding fileId
	 * @param fileId
	 * @param abstractProperties array of arrays where each subarray contains
	 * the abstract name followed by the bmpid and bmpmask property names
	 * (e.g. { "normal", "bmpid", "bmpmask" } ) 
	 */
	public SymbianMultiImagePropertyInfo(
			IPropertySource ps,
			String fileId,
			String[][] abstractProperties) {
		Check.checkArg(ps);
		Check.checkArg(fileId);
		Check.checkArg(abstractProperties);

		EObject object = ((IPropertyInformation) ps).getPropertyOwner(null);
		
		this.fileId = fileId;
		this.abstractProperties = abstractProperties;
		this.infoMap = new LinkedHashMap<String, IImagePropertyInfo>();

		IProjectImageInfo projectImageInfo = ModelUtils.getProjectImageInfo(object);
		String file = null;
        if (projectImageInfo != null && ps != null) {
            file = (String) ps.getPropertyValue(fileId);
            if (file == null) {
                Logging.log(SymbianPlugin.getDefault(),
                        Logging.newStatus(SymbianPlugin.getDefault(), IStatus.ERROR, 
                                "Cannot find '" + fileId + "' property, resetting")); //$NON-NLS-1$ //$NON-NLS-2$
                file = ""; //$NON-NLS-1$
            }
            
            // clean up property
            file = file.replaceAll("\\\\\\\\", "\\\\"); //$NON-NLS-1$ //$NON-NLS-2$
            
            this.multiImageInfo = ((ProjectImageInfo)projectImageInfo).getInfoForBinaryFile(new Path(file));
		}
        
        // fill the map with entries for every possible image,
        // empty if no project or bmpfile exists yet
        for (String[] abstractProperty : abstractProperties) {
			String abstractId = abstractProperty[0];
			String imageId = abstractProperty[1];
			String maskId = abstractProperty[2];
			
			ImagePropertyInfo info = new ImagePropertyInfo(multiImageInfo, 
						ps,
						imageId,
						maskId,
						null);
			info.bmpfile = file;
			infoMap.put(abstractId, info);
		}
        
	}
	
	public SymbianMultiImagePropertyInfo(String fileId, String[][] abstractProperties,
			Map<String, IImagePropertyInfo> map) {
		this.fileId = fileId;
		this.infoMap = map;
		this.abstractProperties = abstractProperties;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.images.IMultiImagePropertyInfo#getImagePropertyInfoMap()
	 */
	public Map<String, IImagePropertyInfo> getImagePropertyInfoMap() {
		return infoMap;
	}
	
	/**
	 * Save contents to the property source.
	 * @param ps
	 */
	public void save(IPropertySource ps) {
		// save the fileid only once
		boolean first = true;
		for (String[] abstractProperty : abstractProperties) {
			String abstractId = abstractProperty[0];
			IImagePropertyInfoBase info = infoMap.get(abstractId);
			if (info instanceof ImagePropertyInfo) {
				String imageId = abstractProperty[1];
				String maskId = abstractProperty[2];
				if (first) {
					// save the common file once
					ImagePropertyInfo.save((ImagePropertyInfo) info,
							ps, fileId, null, null, null);
					first = false;
				}
				ImagePropertyInfo.save((ImagePropertyInfo) info,
						ps, null, imageId, maskId, null);
			}
		}
	}
	
	/**
	 * Get the array of property mappings.  Each array element
	 * consists of abstract image id, bitmap id, and mask id.
	 * @return
	 */
	public String[][] getAbstractProperties() {
		return abstractProperties;
	}

	/**
	 * @return
	 */
	public String getFilePropertyId() {
		return fileId;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.images.IMultiImagePropertyInfo#getText()
	 */
	public String getText() {
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for (Map.Entry<String, IImagePropertyInfo> entry : infoMap.entrySet()) {
			if (!first)
				builder.append("; "); //$NON-NLS-1$
			else {
				first = false;
			}
			builder.append(MessageFormat.format("{0}: {1}", //$NON-NLS-1$
					new Object[] { entry.getKey(), entry.getValue() }));
		}
		return builder.toString();
	}
}
