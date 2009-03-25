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
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstResource;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertySource;

public class InstanceMappingContext {

    private IComponentInstance instance;
    private IPropertySource properties;
    IComponent component;
    private IPropertySource rootProperties;
	private String indexProperty;	// set when mapping a sequence property
	private int index;	// set when mapping a child of a container
    private String rsrcName;
    private IAstResource rsrc;
	private String memberName;
	private IArrayInstanceElementMapper arrayElementMapper;

    public InstanceMappingContext(IComponentInstance instance, IPropertySource properties) {
        this.instance = instance;
        this.component = instance.getComponent();
        this.properties = properties;
        this.rootProperties = (IPropertySource) EcoreUtil.getRegisteredAdapter(instance.getEObject(), IPropertySource.class);
    }

    public String getRsrcName() {
        return rsrcName;
    }

    public void setRsrcName(String rsrcName) {
        this.rsrcName = rsrcName;
    }

    public InstanceMappingContext(IComponentInstance instance, IPropertySource properties, String arrayIndexProperty, int arrayIndex) {
    	this(instance, properties);
    	this.indexProperty = arrayIndexProperty;
    	this.index = arrayIndex;
    }

    /**
     * @return Returns the instance.
     */
    public IComponentInstance getInstance() {
        return instance;
    }

    /**
     * @return Returns the properties.
     */
    public IPropertySource getProperties() {
        return properties;
    }

    public void setProperties(IPropertySource source) {
        this.properties = source;
    }

    /**
     * @return Returns the component.
     */
    public IComponent getComponent() {
        return component;
    }

    public PropertyContext getPropertyContext() {
        return new PropertyContext(instance, properties);
    }

    public boolean isRootPropertySource() {
        return rootProperties == properties;
    }

    /**
     * If an array is being mapped, return its index as a property id.
     * @return string value or null
     */
	public String getArrayIndexProperty() {
		return indexProperty;
	}

	public void setArrayIndexProperty(String index) {
		this.indexProperty = index;
	}

	public void setArrayIndex(int i) {
		index = i;
	}
	
	public int getArrayIndex() {
		return index;
	}
	

    public IAstResource getResource() {
        return rsrc;
    }

    public void setResource(IAstResource rsrc) {
        this.rsrc = rsrc;
    }

    public InstanceMappingContext copy() {
        InstanceMappingContext ctx = new InstanceMappingContext(
                instance, properties, indexProperty, index);
        ctx.rsrc = rsrc;
        ctx.memberName = memberName;
        ctx.arrayElementMapper = arrayElementMapper;
        return ctx;
    }

	public String getMemberName() {
		return memberName;
	}
	
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public IArrayInstanceElementMapper getArrayElementMapper() {
		return arrayElementMapper;
	}

	public void setArrayElementMapper(IArrayInstanceElementMapper arrayElementMapper) {
		this.arrayElementMapper = arrayElementMapper;
	}

}
