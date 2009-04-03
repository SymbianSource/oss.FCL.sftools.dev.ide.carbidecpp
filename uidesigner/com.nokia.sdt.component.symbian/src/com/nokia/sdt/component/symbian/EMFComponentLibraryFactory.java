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


package com.nokia.sdt.component.symbian;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentProvider;
import com.nokia.sdt.emf.component.ComponentFactory;
import com.nokia.sdt.emf.component.ComponentType;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ILocalizedStrings;

import org.eclipse.core.runtime.CoreException;
import org.osgi.framework.Bundle;

import java.io.File;
import java.io.IOException;

/**
 * Factory class for creating an IComponentLibrary implementation that can read components
 * from .component files starting at a root directory
 */
public class EMFComponentLibraryFactory {
	
	/**
	 * Creates an implementation of IComponentLibrary that reads components from .component xml files<p>
	 * @param file the root directory for .component files
	 * @return IComponentLibrary
	 * @throws CoreException
	 * @throws IOException
	 */
	public static IComponentLibrary createComponentLibrary(File file) throws CoreException, IOException {
		return new ComponentLibrary(file.getCanonicalPath());
	}
	
	
	/**
	 * Creates an implementation of IComponent based on a ComponentType EMF object
	 * @param provider the provider returned from {@link IComponentLibrary#getProvider()}
	 * for an IComponentLibrary loaded as a componentLibrary extension
	 * @param componentType the EMF data for the component or null to create an empty ComponentType
	 * @param componentFileOrDir the file or directory associated with the component
	 * @param bundle the Bundle of the component
	 * @param strings the ILocalizedStrings object used to map localized strings
	 * @return IComponent
	 */
	public static IComponent createComponent(IComponentProvider provider, ComponentType componentType,
								File componentFileOrDir, Bundle bundle, ILocalizedStrings strings) {
		Check.checkArg(provider instanceof ComponentProvider);
		if (componentType == null)
			componentType = ComponentFactory.eINSTANCE.createComponentType();
		return new Component((ComponentProvider) provider, componentType, componentFileOrDir, bundle, strings);
	}

}
