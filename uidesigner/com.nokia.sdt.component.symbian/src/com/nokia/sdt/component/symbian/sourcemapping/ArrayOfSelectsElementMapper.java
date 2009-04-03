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

package com.nokia.sdt.component.symbian.sourcemapping;

import com.nokia.sdt.component.symbian.sourcemapping.InstanceSourceMapper.ResourceMapping;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.emf.component.MapResourceElementType;
import com.nokia.sdt.emf.component.SelectType;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Message;

import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Map an element with a &lt;select&gt; node.  This means the
 * element may or may not return an expression.
 * 
 *
 */
public class ArrayOfSelectsElementMapper implements IArrayInstanceElementMapper {

	private InstanceSourceMapper mapper;
	private SelectType select;
	private String memberName;

	public ArrayOfSelectsElementMapper(InstanceSourceMapper mapper, SelectType select, String memberName) {
		this.mapper = mapper;
		this.select = select;
		this.memberName = memberName;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.sourcemapping.IArrayInstanceElementMapper#canIdentifyInstances()
	 */
	public boolean canIdentifyInstances() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.sourcemapping.IArrayInstanceElementMapper#lookupInstance(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
	 */
	public String lookupInstance(IAstExpression expr) {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.sourcemapping.IArrayInstanceElementMapper#createOrUpdate(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression, com.nokia.sdt.datamodel.adapter.IComponentInstance, org.eclipse.ui.views.properties.IPropertySource)
	 */
	public IAstExpression createOrUpdate(IAstExpression expr,
			IComponentInstance instance, IPropertySource properties) {

		// expr should always be null since we don't handle existing expressions
		Check.checkContract(expr == null);
		
        ResourceMapping[] rsrcs = mapper.mapInstance(new Object[] { select },
                instance, properties, InstanceSourceMapper.MAP_EXPRESSION, null);

        // may not have any items
        if (rsrcs.length != 0) {
            if (rsrcs.length != 1) {
                mapper.emit(Message.ERROR, "ArrayFromContainerPropertyMapper.AmbiguousResourceForReference",  //$NON-NLS-1$
                        new Object[] {  
                        instance.getName(), instance.getComponentId(),
                        mapper.mappingContext.getInstance().getName(), 
                        mapper.mappingContext.component.getId(),
                        memberName,
                        rsrcs.length });
            }
            IAstResource rsrc = rsrcs[0].rsrc;
            Check.checkState(rsrc instanceof IAstResourceExpression);
            return (IAstExpression) rsrc;
        }
        
        return null;
	}

	public void registerMapping(MapResourceElementType mre, IAstResource rsrc) {
		
	}
	
}
