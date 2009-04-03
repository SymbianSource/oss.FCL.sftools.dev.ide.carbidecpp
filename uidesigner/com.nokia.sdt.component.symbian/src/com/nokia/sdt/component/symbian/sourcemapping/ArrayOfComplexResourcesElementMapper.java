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

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.emf.component.MapResourceElementType;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorDefineDirective;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Handle elements mapped partially from the parent and
 * partially from the element.
 * 
 *   &lt;mapArrayMember propertyId="." member="foo" &gt;<br>
 *   	&lt;mapResourceElement ... /&gt;<br>
 *   &lt;/mapArrayMember&gt; <br>
 * 
 *
 */
public class ArrayOfComplexResourcesElementMapper implements
		IArrayInstanceElementMapper {

	private String propertyId;
	private String memberName;
	private MapResourceElementType mr;
	private String uniqueField;
	private InstanceSourceMapper mapper;
	private InstanceMappingContext arrayCtx;

	public ArrayOfComplexResourcesElementMapper(InstanceSourceMapper mapper, String propertyId, String memberName, MapResourceElementType mr) {
		this.mapper = mapper;
		this.propertyId = propertyId;
		this.memberName = memberName;
		this.mr = mr;
		this.arrayCtx = mapper.mappingContext;
		
		this.uniqueField = mr.getInstanceIdentifyingMember();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.sourcemapping.IArrayInstanceElementMapper#canIdentifyInstances()
	 */
	public boolean canIdentifyInstances() {
		return uniqueField != null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.sourcemapping.IArrayInstanceElementMapper#lookupInstance(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
	 */
	public String lookupInstance(IAstExpression expr_) {
		// not sure what this is
		if (!(expr_ instanceof IAstResourceExpression))
			return null;
		
		IAstResourceExpression expr = (IAstResourceExpression) expr_;
		
		IAstMemberInitializer init = expr.findMemberInitializer(uniqueField);
		if (init != null) {
			String uniqueValue = getUniqueValue(init.getInitializerExpression().getExpression());
			String instanceName = mapper.rssManipulator.getTypeHandler()
					.findInstanceForArrayElement(
							mapper.mappingContext.getInstance(),
							propertyId,
							memberName,
							uniqueValue);
			if (instanceName == null) {
				// see if that was an expanded macro by looking up the original text
				uniqueValue = init.getInitializerExpression().getExpression().getOriginalText();
				if (uniqueValue != null) {
					instanceName = mapper.rssManipulator.getTypeHandler()
							.findInstanceForArrayElement(
									mapper.mappingContext.getInstance(),
									propertyId,
									memberName,
									uniqueValue);
					if (instanceName == null)
						return null;
				}
			}
			
			// remove obsolete references
			if (mapper.container.getDesignerDataModel().findByNameProperty(instanceName) == null)
				mapper.rssManipulator.getTypeHandler().removeArrayElement(
						mapper.mappingContext.getInstance(),
						propertyId,
						memberName,
						uniqueValue,
						instanceName);
			
			return instanceName;
		}
		return null;
	}

	/**
	 * Determine the unique value behind an expression.  Resolve
	 * macros to their expansions.
	 * @param expression
	 * @return
	 */
	private String getUniqueValue(IAstExpression expression) {
		if (expression instanceof IAstIdExpression) {
			String name = ((IAstIdExpression) expression).getName().getName();
			IAstPreprocessorDefineDirective define = mapper.tu.findDefine(name);
			if (define != null) {
				return define.getMacro().getExpansion();
			}
			return name;
		}
		// get text without any macros, ifdefs, etc
		return expression.getNewText(null);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.sourcemapping.IArrayInstanceElementMapper#createOrUpdate(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression, com.nokia.sdt.datamodel.adapter.IComponentInstance, org.eclipse.ui.views.properties.IPropertySource)
	 */
	public IAstExpression createOrUpdate(IAstExpression expr,
			IComponentInstance instance, IPropertySource properties) {
        // map resource expressions
		if (expr != null && !(expr instanceof IAstResourceExpression)) {
			mapper.reportUnexpectedSyntax(expr, propertyId, Messages.getString("ArrayOfComplexResourcesElementMapper.ResourceExpression")); //$NON-NLS-1$
			expr = null;
		}
        mapper.pushContext(new InstanceMappingContext(instance, properties));
        IAstResource rsrc = mapper.mapResource(mr, InstanceSourceMapper.MAP_EXPRESSION, (IAstResource) expr);
        mapper.popContext();
        Check.checkState(rsrc instanceof IAstResourceExpression);

        // the unique array element is mapped in mapResource()
        
        return (IAstExpression) rsrc;
	}

	public void registerMapping(MapResourceElementType mre, IAstResource rsrc) {
    	IAstMemberInitializer init = rsrc.findMemberInitializer(mre.getInstanceIdentifyingMember());
    	if (init == null) {
    		ComponentMessageUtils.emit(mapper.mappingContext.getInstance(), IStatus.ERROR, "ArrayOfComplexResourcesElementMapper.NoUniqueMemberInitializer", //$NON-NLS-1$ 
    				new String[] { mapper.mappingContext.getInstance().getName(), 
    				mapper.mappingContext.getInstance().getComponentId(), 
    				mre.getStruct(), mre.getInstanceIdentifyingMember() });
    	} else {
    		IAstExpression expression = init.getInitializerExpression().getExpression();
    		String uniqueValue = getUniqueValue(expression); 
    		
    		mapper.rssManipulator.getTypeHandler().registerArrayElement(
        			arrayCtx.getInstance(),
					arrayCtx.getPropertyContext().propertyId,
					arrayCtx.getMemberName(),
					uniqueValue,
					mapper.mappingContext.getInstance().getName());
    	}
		
	}
}
