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

package com.nokia.sdt.component.symbian.componentchecker;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.symbian.IFacetContainer;
import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.SourceGenType;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * 
 *
 */
public class TestSourceGenDebug extends BaseComponentTest {
	private static final String TEST_SRCGEN_DEBUG_FLAG = "srcgen-debug-flag";


	/**
	 * @param checker
	 */
	TestSourceGenDebug(ComponentChecker checker, IComponent component) {
		super(checker, component);
	}

	public void runTests() {
		if (!(component instanceof IFacetContainer))
			return;
		
		IFacetContainer fc = (IFacetContainer) component;
		SourceGenType sg = getSourceGenTypeFromContainer(fc);
		if (sg != null) {
			check(!sg.isDebug(),
					Severity.ERROR,
					component, TEST_SRCGEN_DEBUG_FLAG, 
						"sourceGen debug=\"true\" enabled");
		}
	}
	
    private SourceGenType getSourceGenTypeFromContainer(IFacetContainer fc) {
        EStructuralFeature sourcegenFeature = 
            ComponentPackage.eINSTANCE.getComponentType().getEStructuralFeature(
                ComponentPackage.COMPONENT_TYPE__SOURCE_GEN);

        SourceGenType srcgenObj = null;
        EObject container = fc.getEMFContainer();
        Object featureObj = container.eGet(sourcegenFeature);
        if (featureObj instanceof SourceGenType)
            srcgenObj = (SourceGenType) featureObj;
        
        return srcgenObj;
    }

}

