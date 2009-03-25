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
package com.nokia.sdt.component.symbian.builder;

import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;

/** Auto-generated class for the project nature that is associated
 * with user-component plugin projects.  
 * 
 * 
 *
 */
public class UserComponentProjectNature implements IProjectNature {

	/**
	 * ID of this project nature
	 */
	public static final String NATURE_ID = "com.nokia.sdt.component.symbian.userComponentProjectNature"; //$NON-NLS-1$

	private IProject project;

	/*
     * Add the UI Designer user component project nature to the project.
     *  
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IProjectNature#configure()
	 */
	public void configure() throws CoreException {
		toggleCustomComponents(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IProjectNature#deconfigure()
	 */
	public void deconfigure() throws CoreException {
		toggleCustomComponents(false);
    }

	private void toggleCustomComponents(boolean enable) {
		if (project == null)
			return;
		
		if (enable) {
			// tell UI Designer about this project
			ComponentSystemPlugin.getDefault().addUserComponentProject(project);
		} else {
	        // tell UI Designer to remove this project
	        ComponentSystemPlugin.getDefault().removeUserComponentProject(project);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IProjectNature#getProject()
	 */
	public IProject getProject() {
		return project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
	 */
	public void setProject(IProject project) {
		this.project = project;
	}

	public static boolean hasNature(IProject project) {
		try {
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();

			for (int i = 0; i < natures.length; ++i) {
				if (UserComponentProjectNature.NATURE_ID.equals(natures[i])) {
					return true;
				}
			}
		} catch (CoreException e) {
            Logging.log(ComponentSystemPlugin.getDefault(), e.getStatus());
		}
		return false;
	}


	/**
	 * Toggles the user component project nature on a project
	 * 
	 * @param project to have nature added or removed
	 * @param enable true: add, false: remove
	 */
	public static void setNature(IProject project, boolean enable) {
		try {
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();

			for (int i = 0; i < natures.length; ++i) {
				if (UserComponentProjectNature.NATURE_ID.equals(natures[i])) {
					if (enable)
						return;
					
					// Remove the nature
					String[] newNatures = new String[natures.length - 1];
					System.arraycopy(natures, 0, newNatures, 0, i);
					System.arraycopy(natures, i + 1, newNatures, i,
							natures.length - i - 1);
					description.setNatureIds(newNatures);
					project.setDescription(description, null);
					return;
				}
			}

			// Add the nature
			if (enable) {
				String[] newNatures = new String[natures.length + 1];
				System.arraycopy(natures, 0, newNatures, 0, natures.length);
				newNatures[natures.length] = UserComponentProjectNature.NATURE_ID;
				description.setNatureIds(newNatures);
				project.setDescription(description, null);
			}
		} catch (CoreException e) {
            Logging.log(ComponentSystemPlugin.getDefault(), e.getStatus());
		}
	}
}
