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

package com.nokia.sdt.symbian.images;

import com.nokia.carbide.cdt.builder.DefaultMMPViewConfiguration;
import com.nokia.carbide.cdt.builder.DefaultViewConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.*;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMMPReference;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPData;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AllNodesViewFilter;

import org.eclipse.core.runtime.IPath;

import java.util.regex.Pattern;

/**
 * Routines encapsulating logic related to the way builds work.
 * 
 *
 */
public abstract class BuildLogic {
    static final String MMP_EXTENSION = "mmp"; //$NON-NLS-1$
    static final String MBM_EXTENSION = "mbm"; //$NON-NLS-1$
    static final String MIF_EXTENSION = "mif"; //$NON-NLS-1$
    public static final String MBG_EXTENSION = "mbg"; //$NON-NLS-1$
    static final String AIF_DEF_EXTENSION = "aifdef"; //$NON-NLS-1$
    static final String AIF_EXTENSION = "aif"; //$NON-NLS-1$
    static final String BLDINF_PATH = "bld.inf"; //$NON-NLS-1$
	public static final Pattern ICON_MAKEFILE_PATTERN = Pattern.compile(
			".*icon.*mk", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$

	public final static Pattern IMAGE_EXTENSION_PATTERN = 
		Pattern.compile("(?i)png|gif|bmp|jpg|svg"); //$NON-NLS-1$

	/**
	 * Get the first UID3 value for an MMP in the project.
	 * @param info
	 * @return String or null
	 */
	public static String getProjectUID3(final ICarbideProjectInfo info) {
		final IPath projectPath = info.getProject().getLocation();
		return (String) EpocEnginePlugin.runWithBldInfView(info.getAbsoluteBldInfPath(), 
				new DefaultViewConfiguration(info),
				new BldInfViewRunnableAdapter() {

					public Object run(IBldInfView view) {
						for (IMMPReference mmp : view.getAllMMPReferences()) {
							String uid = (String) EpocEnginePlugin.runWithMMPData(
									projectPath.append(mmp.getPath()), 
									new DefaultMMPViewConfiguration(info, new AllNodesViewFilter()),
									new MMPDataRunnableAdapter() {

										public Object run(IMMPData data) {
											return data.getUid3();
										}
										
									});
							if (uid != null) {
								return uid;
							}
						}
						return null;
					}
		});
	}

}
