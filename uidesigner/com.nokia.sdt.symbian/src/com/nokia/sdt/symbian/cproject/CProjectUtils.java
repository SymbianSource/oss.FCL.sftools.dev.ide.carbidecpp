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


package com.nokia.sdt.symbian.cproject;

import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.cdt.core.CCProjectNature;
import org.eclipse.cdt.core.model.*;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * 
 *
 */
public class CProjectUtils {

    /**
     * Attempts to extract a C Element from the initial selection.
     * <br>
     * *** Adapted from CDT class: 
     * ICElement 
     * org.eclipse.cdt.ui.wizards.NewClassCreationWizardPage.
     * getInitialCElement(IStructuredSelection selection)
     * 
     * @param selection the initial selection
     * @return a C Element, or <code>null</code> if not available
     */
    public static ICElement getInitialCElement(IStructuredSelection selection) {
        ICElement celem = getCElementFromSelection(selection);
        if (celem == null || celem.getElementType() == ICElement.C_MODEL) {
            try {
                ICProject[] projects = CoreModel.create(ResourcesPlugin.getWorkspace().getRoot()).getCProjects();
                if (projects.length == 1) {
                    celem = projects[0];
                }
            } catch (CModelException e) {
                Check.failedArg(e);
            }
        }
        return celem;
    }

    /**
     * Returns the C Element which corresponds to the given selected object.
     * 
     * @param selection the selection to be inspected
     * @return a C element matching the selection, or <code>null</code>
     * if no C element exists in the given selection
     */
    public static ICElement getCElementFromSelection(IStructuredSelection selection) {
        if (selection != null && !selection.isEmpty()) {
           	return getCElementFromSelectedObject(selection.getFirstElement());
        }
        return null;
    }
    
    /**
     * Returns the C Element which corresponds to the given selected object.
     * <br>
     * *** Adapted from internal CDT class: 
     * ICElement org.eclipse.cdt.internal.ui.wizards.classwizard.NewClassWizardUtil.
     * getCElementFromSelection(IStructuredSelection selection)
     * 
     * @param object the selected object
     * @return a C element matching the object, or <code>null</code>
     * if no C element exists for the given object
     */
    public static ICElement getCElementFromSelectedObject(Object object) {
        ICElement celem = null;
        if (object instanceof IAdaptable) {
            IAdaptable adaptable = (IAdaptable) object;            
            
            celem = (ICElement) adaptable.getAdapter(ICElement.class);
            if (celem == null) {
                IResource resource = (IResource) adaptable.getAdapter(IResource.class);
                if (resource != null && resource.getType() != IResource.ROOT) {
                    while (celem == null && resource.getType() != IResource.PROJECT) {
                        resource = resource.getParent();
                        celem = (ICElement) resource.getAdapter(ICElement.class);
                    }
                    if (celem == null) {
                        celem = CoreModel.getDefault().create(resource); // c project
                    }
                }
                if (celem == null) {
                	celem = (ICElement) adaptable.getAdapter(ICProject.class);
                }
            }
        }
        return celem;
    }

	public static boolean hasCCProjectNature(IProject project) {
		try {
			return project.hasNature(CCProjectNature.CC_NATURE_ID);
		} 
		catch (CoreException e) {
			//throws exception if the project is not open.
		}
		
		return false;
	}
}
