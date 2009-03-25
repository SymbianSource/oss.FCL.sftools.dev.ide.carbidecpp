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
package com.nokia.carbide.cpp.project.ui.processes;

import com.nokia.carbide.cdt.builder.*;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.*;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMMPReference;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AllNodesViewFilter;
import com.nokia.carbide.cpp.internal.project.ui.Messages;
import com.nokia.carbide.cpp.internal.project.ui.dialogs.MMPSelectionResolver;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.carbide.templatewizard.process.IParameter;
import com.nokia.carbide.templatewizard.processes.CreateFolders;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.settings.model.*;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.*;

import java.text.MessageFormat;
import java.util.*;

public class CreateSourceFolders extends CreateFolders {
	
	private static final String NOT_CPROJECT_ERROR = Messages.getString("CreateSourceFolders.NotCProjectError"); //$NON-NLS-1$
	protected static final String FORCE_USERINCLUDE_ATTRIBUTE = "forceUserInclude"; //$NON-NLS-1$

	@Override
	public void process(ITemplate template, List<IParameter> parameters, IProgressMonitor monitor) throws CoreException {
		super.process(template, parameters, monitor);

		ICProject cProject = CoreModel.getDefault().create(project);
		String error = MessageFormat.format(NOT_CPROJECT_ERROR, new Object[] { getProjectName() });
        failIfNull(cProject, error);
        
        // we only handle new style projects
		if (CCorePlugin.getDefault().isNewStyleProject(cProject.getProject())) {
			List<ICSourceEntry> newEntries = getNewEntriesFromFolderParameters(parameters);
			addNewEntriesToConfigs(newEntries, monitor);
			List<IPath> userIncludes = getUserIncludes(parameters);
			addUserIncludes(userIncludes);
		}
	}

	private List<ICSourceEntry> getNewEntriesFromFolderParameters(List<IParameter> parameters) throws CoreException {
		// for all new folders to add, create ICSourceEntry objects for them
		List<ICSourceEntry> newEntries = new ArrayList<ICSourceEntry>();
		for (IParameter parameter : parameters) {
			if (parameter.getName().equals(FOLDER_PARAMETER)) {
				String pathString = getRequiredAttributeFromParameter(parameter, PATH_ATTRIBUTE);
				IFolder folder = project.getFolder(pathString);
				Check.checkContract(FileUtils.exists(folder)); // super class processing should have already created the folders
				newEntries.add(new CSourceEntry(folder, null, 0)); 
			}
		}
		return newEntries;
	}

	private void addNewEntriesToConfigs(List<ICSourceEntry> newEntries, IProgressMonitor monitor) throws CoreException {
		// get project description, and for each config, add the new entries to the existing ones
		ICProjectDescription description = CCorePlugin.getDefault().getProjectDescription(project);
		try {
			ICConfigurationDescription configs[] = description.getConfigurations();
			for (int i = 0; i < configs.length; i++){
				ICConfigurationDescription config = configs[i];
				Set<ICSourceEntry> sourceEntrySet = new HashSet<ICSourceEntry>();
				ICSourceEntry[] entries = config.getSourceEntries();
				// add all existing entries
				for (int j = 0; j < entries.length; j++) {
					IPath path = new Path(entries[j].getValue());
					// don't include entries with less than 2 segments 
					if (path.segmentCount() < 2)
						continue;
					sourceEntrySet.add(entries[j]);
				}
				// add the new entries
				for (ICSourceEntry newEntry : newEntries) {
					sourceEntrySet.add(newEntry);
				}
				// set as source entries in the config
				ICSourceEntry[] sourceEntries = 
					(ICSourceEntry[])sourceEntrySet.toArray(new ICSourceEntry[sourceEntrySet.size()]);
				config.setSourceEntries(sourceEntries);
			}
		} 
		catch (WriteAccessException e) {
			throw new CoreException(Logging.newStatus(getPlugin(), e));
		}
		CCorePlugin.getDefault().setProjectDescription(project, description, false, monitor);
	}
		
	private List<IPath> getUserIncludes(List<IParameter> parameters) {
		// for all folder parameters check to see which should be forced into user includes, and add them
		List<IPath> userIncludes = new ArrayList<IPath>();
		for (IParameter parameter : parameters) {
			if (parameter.getName().equals(FOLDER_PARAMETER)) {
				String value = parameter.getAttributeValue(FORCE_USERINCLUDE_ATTRIBUTE);
				if (Boolean.parseBoolean(value)) {
					userIncludes.add(new Path(parameter.getAttributeValue(PATH_ATTRIBUTE)));
				}
			}
		}
		
		return userIncludes;
	}

	private void addUserIncludes(final List<IPath> userIncludes) {
        final ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
//        EpocEngineHelper.get
        
        
		EpocEnginePlugin.runWithBldInfView(cpi.getWorkspaceRelativeBldInfPath(),
				new DefaultViewConfiguration(project, null, new AllNodesViewFilter()),
				new BldInfViewRunnableAdapter() {
					public Object run(IBldInfView infView) {
						IMMPReference[] mmps = infView.getAllMMPReferences();
						if (mmps.length > 1) {
							MMPSelectionResolver mmpResolver = 
								new MMPSelectionResolver(
										Messages.getString("CreateSourceFolders.DialogMessage")); //$NON-NLS-1$
							List<IMMPReference> resolvedMMPs = 
								mmpResolver.addSourceToWhichMMPs(Arrays.asList(mmps), Collections.EMPTY_LIST);
							mmps = (IMMPReference[]) 
								resolvedMMPs.toArray(new IMMPReference[resolvedMMPs.size()]);
						}
						for (IMMPReference mmp : mmps) {
							final IPath workspaceRelativeMMPPath = new Path(project.getName()).append(mmp.getPath());
							EpocEnginePlugin.runWithMMPView(workspaceRelativeMMPPath,
									new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()),
									new MMPViewRunnableAdapter() {
										public Object run(IMMPView mmpView) {
											boolean didAdd = false;
											List<IPath> mmpViewUserIncludes = mmpView.getUserIncludes();
											for (IPath path : userIncludes) {
												if (!mmpViewUserIncludes.contains(path)) {
													mmpViewUserIncludes.add(path);
													didAdd = true;
												}
											}
											if (didAdd) {
												mmpView.commit();
											}

											return null;
										}
									});
						}
						return null;
					}
				});
	}

	protected Plugin getPlugin() {
		return ProjectCorePlugin.getDefault();
	}

}
