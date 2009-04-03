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
package com.nokia.sdt.component.symbian.sourcemapping;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.emf.component.*;
import com.nokia.sdt.sourcegen.IComponentSourceMapping;
import com.nokia.sdt.sourcegen.doms.rss.IRssModelGenerator;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import java.util.*;

public class SourceMappingAdapterXML implements IComponentSourceMapping {

	/** Presumed rsrcid for the unnamed mapping */
    private static final String UNNAMED_MAPPING = "<unnamed>"; //$NON-NLS-1$
    
	private IComponent component;
    private SourceMappingType srcmap;
    
    public SourceMappingAdapterXML(Plugin plugin, IComponent component, SourceMappingType srcmap) {
        this.component = component;
        this.srcmap = srcmap;
        
        // validate
        Map<String, EObject> mappings = new HashMap<String, EObject>();
        updateAndValidateResourceIds(mappings, srcmap.eContents());
    }

    /**
     * Ensure that every mapResourceXXX element has a unique id,
     * either by having id set or by defaulting it to the member name.  
     * An exception is made for uses of the same id at the same level
     * in a &lt;select&gt;.
     * @param mappings
     * @param list
     */
    private void updateAndValidateResourceIds(Map<String, EObject> mappings, EList list) {
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			EObject object = (EObject) iter.next();
			if (object instanceof MappingResourceType) {
				MappingResourceType mr = (MappingResourceType) object;

				if (mr instanceof MapResourceMemberType) {
					// the default id is the member name
					MapResourceMemberType mrm = (MapResourceMemberType) mr;
					if (mrm.getId() == null)
						mrm.setId("member$"+mrm.getMember()); //$NON-NLS-1$
						
				}
				else if (mr instanceof MapResourceElementType) {
					// the default id is 'element'
					MapResourceElementType mre = (MapResourceElementType) mr;
					if (mre.getId() == null)
						mre.setId("$element"); //$NON-NLS-1$
				}
				
				
				String rsrcId = mr.getId();
				if (rsrcId == null)
					rsrcId = UNNAMED_MAPPING;
				
				EObject existingMapping = mappings.get(rsrcId);
				if (existingMapping != null && !isSameSelectParent(existingMapping, object)) {
					ComponentMessageUtils.emit(component,
							IStatus.ERROR,
							"SourceMappingAdapterXML.TwoMapResourcesWithSameRsrcId", //$NON-NLS-1$
							new String[] { rsrcId, component.getId() });
				}
				mappings.put(rsrcId, mr);
			}
			
			updateAndValidateResourceIds(mappings, object.eContents());
		}
	}

    /**
     * Tell if the two EObjects share the same &lt;select&gt; parent element
     * @param obj1
     * @param obj2
     * @return
     */
	private boolean isSameSelectParent(EObject obj1, EObject obj2) {
		while (obj1 != null && obj2 != null) {
			if (obj1 == obj2 && obj1 instanceof SelectType)
				return true;
			obj1 = obj1.eContainer();
			obj2 = obj2.eContainer();
		}
		return false;
	}

	/**
     * Internal use: get the source mapping facet
     */
    SourceMappingType getSourceMapping() {
        return srcmap;
    }
    
    /*
     *  (non-Javadoc)
     * @see com.nokia.sdt.component.adapter.IComponentSourceMapping#exportInstance(com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit, com.nokia.sdt.datamodel.adapter.IComponentInstance)
     */
    public void exportInstance(IRssModelGenerator generator, IComponentInstance instance) {
        Check.checkArg(instance);
        InstanceSourceMapper ism = new InstanceSourceMapper(generator, instance);
        ism.export(srcmap, instance, component);
    }

     public IComponent getComponent() {
        return component;
    }

     /**
      * Get the primary &lt;mapResource&gt; element from a component's
      * source mapping definition.
      * @param component
      * @return MapResourceType, or null if missing component, no
      * source mapping, or not a straightforward mapping (i.e. &lt;select&gt;)  
      */
    public static MapResourceType getPrimaryMapResourceType(IComponent component) {
        // refresh the mapping facet from the current component instance
        IComponentSourceMapping csm = (IComponentSourceMapping) component.getAdapter(IComponentSourceMapping.class);
        if (csm == null) {
            return null;
        }
        SourceMappingType smt = ((SourceMappingAdapterXML)csm).getSourceMapping();
        Check.checkState(smt != null);
        if (smt.getMapResource().size() == 0)
            return null;
        return (MapResourceType) smt.getMapResource().get(0);
    }


}
