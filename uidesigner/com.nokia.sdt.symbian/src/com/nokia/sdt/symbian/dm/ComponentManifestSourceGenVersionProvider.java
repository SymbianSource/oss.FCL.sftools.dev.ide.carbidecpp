/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.symbian.dm;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.sourcegen.ISourceGenComponentVersionProvider;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Pair;

import org.osgi.framework.Version;

import java.util.*;

/**
 * Implementation of ISourceGenComponentVersionProvider using the
 * component manifest deltas.
 * 
 *
 */
public class ComponentManifestSourceGenVersionProvider implements
		ISourceGenComponentVersionProvider {

	private IDesignerData designerData;

	public ComponentManifestSourceGenVersionProvider(IDesignerDataModel dataModel) {
		Check.checkArg(dataModel instanceof DesignerDataModel);
		this.designerData = ((DesignerDataModel) dataModel).getDesignerData();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.ISourceGenComponentVersionProvider#getComponentVersionDeltas()
	 */
	public Map<String, Pair<Version, Version>> getComponentVersionDeltas() {
		IComponentManifest manifest = designerData.getComponentManifest();
		Map<String, ComponentManifestDelta> componentDeltas = manifest.getComponentDeltas(designerData);
		
		Map<String, Pair<Version, Version>> versionDeltas = new HashMap<String, Pair<Version,Version>>();
		if (componentDeltas != null) {
			for (Iterator iter = componentDeltas.entrySet().iterator(); iter.hasNext();) {
				Map.Entry<String, ComponentManifestDelta> entry = (Map.Entry<String, ComponentManifestDelta>) iter.next();
				switch (entry.getValue().getType()) {
				case ComponentManifestDelta.NEWER:
					versionDeltas.put(entry.getKey(), 
							new Pair(entry.getValue().getOldVersion(),
									entry.getValue().getNewVersion()));
					break;
				case ComponentManifestDelta.MISSING:
					// ignore
					break;
				case ComponentManifestDelta.OLDER:
					// ignore downgrades now
					break;
				}
			}
		}
		return versionDeltas;
	}
}
