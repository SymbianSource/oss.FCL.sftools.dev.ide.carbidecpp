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
package com.nokia.carbide.cpp.internal.api.project.core;

import com.nokia.carbide.cdt.builder.*;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.*;
import com.nokia.carbide.cpp.epoc.engine.image.IBitmapSource;
import com.nokia.carbide.cpp.epoc.engine.image.IBitmapSourceReference;
import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.*;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExtension;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakefileReference.EMakeEngine;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.*;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AllNodesViewFilter;
import com.nokia.carbide.cpp.internal.project.core.Messages;
import com.nokia.carbide.cpp.internal.qt.core.QtCorePlugin;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;

import java.util.*;

public class ResourceChangeListener implements IResourceChangeListener {

	public final static int UPDATE_FILES_OPTION_ASK = 0;
	public final static int UPDATE_FILES_OPTION_ALWAYS = 1;
	public final static int UPDATE_FILES_OPTION_NEVER = 2;

	private static int iterationCount = 0;
	private static IMMPSelectionResolver resolver = null;
	private static IUpdateProjectFilesQuery updateQuery = null;
	private static int addedUpdatePrefOption = UPDATE_FILES_OPTION_ASK;
	private static int changedUpdatePrefOption = UPDATE_FILES_OPTION_ASK;
	
	private static List<IFile> resourcesToIgnoreOnNextChange = new ArrayList<IFile>(0);


	public static void setResolver(IMMPSelectionResolver theResolver) {
		resolver = theResolver;
	}

	public static void setUpdateQuery(IUpdateProjectFilesQuery theQuery) {
		updateQuery = theQuery;
	}
	
	public static void setAddedUpdateOption(int option) {
		if (option >= UPDATE_FILES_OPTION_ASK && option <= UPDATE_FILES_OPTION_NEVER) {
			addedUpdatePrefOption = option;
		}
	}

	public static void setChangedUpdateOption(int option) {
		if (option >= UPDATE_FILES_OPTION_ASK && option <= UPDATE_FILES_OPTION_NEVER) {
			changedUpdatePrefOption = option;
		}
	}

	public void resourceChanged(IResourceChangeEvent event) {
		// ignore project closed/deleted, pre/post build events
		if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
			// resource changed events always start at the workspace root, so projects
			// are the next level down
			IResourceDelta[] projects = event.getDelta().getAffectedChildren();
			if (projects.length > 0) {
				for (IResourceDelta projectDelta : projects) {
					if (((projectDelta.getFlags() & IResourceDelta.OPEN) != 0) || projectDelta.getKind() == IResourceDelta.REMOVED) {
						// the project(s) was either just created, opened, renamed, closed or deleted.  in any
						// case we can ignore the event as none of these cases require a change to the model.
						continue;
					}
					
					// ignore marker deltas
					if ((projectDelta.getFlags() & IResourceDelta.MARKERS) != 0) {
						continue;
					}
					
					// ignore description deltas
					if ((projectDelta.getFlags() & IResourceDelta.DESCRIPTION) != 0) {
						continue;
					}

					IResource resource = projectDelta.getResource();
					if (resource != null && resource instanceof IProject) {
						// if the project is not a Carbide.c++ project then ignore the change
						IProject project = (IProject)resource;
						try {

							if (project.isAccessible() &&
									project.hasNature(CarbideBuilderPlugin.CARBIDE_PROJECT_NATURE_ID) &&
									!project.hasNature(QtCorePlugin.QT_PROJECT_NATURE_ID)) {
						        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
						        if (cpi == null) {
						        	continue;
						        }

						        // there is a case where we'll get resource added notifications for all resources in the
								// existing project.  see boog #4401 for details.  this will be a problem when there are
								// extra files under the project that aren't included in the bld.inf or mmp files.  in
								// those cases, the user would get a ton of dialogs asking if they want to add those files
								// to the Symbian project files.  to work around this problem, we'll check to see if the
								// main bld.inf project is part of this resource change notification.  if so, we assume it's
								// this special case and therefore ignore it.  I can't think of any other case where the
								// main bld.inf would ever be added to the project, so I think this is a valid assumption.
						        IResourceDelta infDelta = projectDelta.findMember(cpi.getProjectRelativeBldInfPath());
						        if (infDelta != null && infDelta.getKind() == IResourceDelta.ADDED) {
						        	continue;
						        }
								
								// visit the children
								visitChildren(projectDelta);
							}
						} catch (CoreException e) {
							ProjectCorePlugin.log(e.getStatus());
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	private void visitChildren(IResourceDelta delta) {
		IResourceDelta[] addedChildren = delta.getAffectedChildren(IResourceDelta.ADDED);
		if (addedChildren.length > 0) {
			// a file or folder may have been renamed
			if (addedChildren.length == 1) {
				if (0 != (addedChildren[0].getFlags() & IResourceDelta.MOVED_FROM)) {
					IResourceDelta[] removedChildren = getDeltaChildren(delta, true);
					if (removedChildren.length == 1) {
						if (0 != (removedChildren[0].getFlags() & IResourceDelta.MOVED_TO)) {
							resourceRenamed(removedChildren[0].getResource(), addedChildren[0].getResource());
							return;
						}
					}
				}
			}
			
			// one or more files and or folders have been created.  for each folder, add any
			// sources contained in it to the model at the same time.
			List<IFile> addedFiles = new ArrayList<IFile>(0);
			for (IResourceDelta child : addedChildren) {
				if (child.getAffectedChildren().length > 0) {
					// a different folder
					visitChildren(child);
				} else {
					IResource resource = child.getResource();
					if (resource != null && resource instanceof IFile) {
						IFile file = (IFile)resource;
						// don't add the .cproject file just so the job doesn't show up
						if (!file.getName().equals(".cproject")) {
							addedFiles.add(file);
						}
					}
				}
			}
			if (addedFiles.size() > 0) {
				filesCreated(addedFiles);
			}
		}
		
		IResourceDelta[] removedChildren = delta.getAffectedChildren(IResourceDelta.REMOVED);
		if (removedChildren.length > 0) {
			// one or more files and or folders have been deleted.
			List<IResource> removedResources = new ArrayList<IResource>(0);
			for (IResourceDelta child : removedChildren) {
				IResource resource = child.getResource();
				if (resource != null && (resource instanceof IFile || resource instanceof IFolder)) {
					removedResources.add(resource);
				}
			}
			if (removedResources.size() > 0) {
				resourcesDeleted(removedResources);
			}
		}

		IResourceDelta[] changedChildren = delta.getAffectedChildren(IResourceDelta.CHANGED);
		if (changedChildren.length > 0) {
			if (changedChildren.length == 1) {
				// changes are specific to one folder.  either the folder itself or one of
				// its files has been renamed
				IResourceDelta child = changedChildren[0];
				if (child.getAffectedChildren().length > 0) {
					visitChildren(child);
				}
			} else {
				// more than one folder has changes.  this could be deletes from multiple folders or
				// moves from one folder to another.
				boolean rename = false;
				
				if (changedChildren.length == 2) {
					// see if files were moved from one folder to another
					IResourceDelta[] oldFiles = changedChildren[0].getAffectedChildren(IResourceDelta.REMOVED);
					IResourceDelta[] newFiles = changedChildren[1].getAffectedChildren(IResourceDelta.ADDED);
					
					// if empty then try the other way around
					if (oldFiles.length == 0 && newFiles.length == 0) {
						oldFiles = changedChildren[1].getAffectedChildren(IResourceDelta.REMOVED);
						newFiles = changedChildren[0].getAffectedChildren(IResourceDelta.ADDED);
					}
					
					for (IResourceDelta oldDelta : oldFiles) {
						// find out where it was moved to
						String oldName = oldDelta.getFullPath().lastSegment();
						
						for (IResourceDelta newDelta : newFiles) {
							if (newDelta.getFullPath().lastSegment().equals(oldName)) {
								resourceRenamed(oldDelta.getResource(), newDelta.getResource());
								rename = true;
								break;
							}
						}
					}
				}
				
				if (!rename) {
					// it wasn't a move from one to the other.  check for deletes from multiple directories
					List<IResource> removedResources = new ArrayList<IResource>(0);
					for (IResourceDelta child : changedChildren) {
						for (IResourceDelta removedChild : getDeltaChildren(child, true)) {
							IResource resource = removedChild.getResource();
							if (resource != null && (resource instanceof IFile || resource instanceof IFolder)) {
								removedResources.add(resource);
							}
						}
					}
					if (removedResources.size() > 0) {
						resourcesDeleted(removedResources);
					}
					
					// check for adds to multiple directories
					List<IFile> createdFiles = new ArrayList<IFile>();
					for (IResourceDelta child : changedChildren) {
						for (IResourceDelta addedChild : getDeltaChildren(child, false)) {
							IResource resource = addedChild.getResource();
							if (resource instanceof IFile) {
								createdFiles.add((IFile) resource);
							}
						}
					}
					if (createdFiles.size() > 0) {
						filesCreated(createdFiles);
					}
					
				}
			}
		}
	}
	
	/*
	 * @param removed true if want removed else want created
	 */
	private IResourceDelta[] getDeltaChildren(IResourceDelta delta, boolean removed) {
		List<IResourceDelta> deltaChildren = new ArrayList<IResourceDelta>();
		
		// get direct children
		for (IResourceDelta child : delta.getAffectedChildren(removed ? IResourceDelta.REMOVED : IResourceDelta.ADDED)) {
			deltaChildren.add(child);
		}
		
		// check recursively
		for (IResourceDelta child : delta.getAffectedChildren(IResourceDelta.CHANGED)) {
			for (IResourceDelta child2 : getDeltaChildren(child, removed)) {
				deltaChildren.add(child2);
			}
		}

		return deltaChildren.toArray(new IResourceDelta[deltaChildren.size()]);
	}
	
	/**
	 * Helper function for project resource listener.
	 * @param oldResource
	 * @param newResource
	 */
	private void resourceRenamed(final IResource oldResource, final IResource newResource) {
		if ((oldResource instanceof IFile  && newResource instanceof IFile)
				|| (oldResource instanceof IFolder && newResource instanceof IFolder)) {
			// run this in a workspace job as it modifies resources (bld.inf/mmp)
			WorkspaceJob job = new WorkspaceJob(Messages.getString("ResourceChangeListener.ResourceRenamedJobName")) { //$NON-NLS-1$
				@Override
				public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
					workspaceFileOrFolderRenamed(oldResource, newResource);
					return Logging.newStatus(ProjectCorePlugin.getDefault(), IStatus.OK, null);
				}
			};
			job.setRule(newResource.getProject());
			job.setUser(true);
			job.schedule();
		} else {
			assert(false);
		}
	}

	private void workspaceFileOrFolderRenamed(final IResource oldResource, final IResource newResource) {
		if ((oldResource instanceof IFile  && newResource instanceof IFile)
			|| (oldResource instanceof IFolder && newResource instanceof IFolder)) {

			// bail if never option is set
			if (UPDATE_FILES_OPTION_NEVER == changedUpdatePrefOption) {
				return;
			}

			final IProject project = newResource.getProject();
	        final ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);

	        if (cpi.getDefaultConfiguration() == null)
	        	return;

			final IPath newResourcePath = newResource.getProjectRelativePath();
			final IPath oldResourcePath = oldResource.getProjectRelativePath();
			
			EpocEnginePlugin.runWithBldInfView(cpi.getWorkspaceRelativeBldInfPath(),
					new DefaultViewConfiguration(project, null, new AllNodesViewFilter()),
					new BldInfViewRunnableAdapter() {

						public Object run(IBldInfView infView) {
							EpocEnginePathHelper infPathHelper = new EpocEnginePathHelper(project);

							// NOTE: because the resource is already deleted at this point, the path
							// helper classes are not able to get the canonicalized paths.  so if the
							// case in the file system does not match that of the bld.inf/mmp file, using
							// IPath#equals and IPath#isPrefixOf will not worjk correctly.  therefore we
							// need to do case-insensitive string comparisons.
							
							// we need to keep track if any changes are made - IView#hasChanges has not
							// been implemented yet.
							boolean bldinfHasChanges = false;
							List<String> bldInfChangeDetails = new ArrayList<String>(0);

							// check the bld.inf exports
							for (IExport export : infView.getExports()) {
								IPath path = infPathHelper.convertToProject(export.getSourcePath());
								if (path != null) {
									if (oldResource instanceof IFile) {
										if (path.toOSString().compareToIgnoreCase(oldResourcePath.toOSString()) == 0) {
											export.setSourcePath(newResourcePath);
											bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.ExportText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newResourcePath); //$NON-NLS-1$ //$NON-NLS-2$
											// if the filename changed and the targetpath filename was the same then rename it too
											if (!newResourcePath.lastSegment().equals(oldResourcePath.lastSegment())) {
												IPath oldTargetPath = export.getTargetPath();
												if (oldTargetPath.lastSegment().equals(oldResourcePath.lastSegment())) {
													IPath newTargetPath = export.getTargetPath().removeLastSegments(1).append(newResourcePath.lastSegment());
													export.setTargetPath(newTargetPath);
													bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.ExportTargetPathText") + oldTargetPath + Messages.getString("ResourceChangeListener.ChangedToText") + newTargetPath); //$NON-NLS-1$ //$NON-NLS-2$
												}
											}
											bldinfHasChanges = true;
										}
									} else if (oldResource instanceof IFolder) {
										if (path.toOSString().toLowerCase().startsWith(oldResourcePath.toOSString().toLowerCase())) {
											IPath newSourcePath = newResourcePath.append(path.removeFirstSegments(oldResourcePath.segmentCount()));
											export.setSourcePath(newSourcePath);
											bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.ExportText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newSourcePath); //$NON-NLS-1$ //$NON-NLS-2$
											bldinfHasChanges = true;
										}
									}
								}
							}
							
							// check the bld.inf test exports
							for (IExport testExport : infView.getTestExports()) {
								IPath path = infPathHelper.convertToProject(testExport.getSourcePath());
								if (path != null) {
									if (oldResource instanceof IFile) {
										if (path.toOSString().compareToIgnoreCase(oldResourcePath.toOSString()) == 0) {
											testExport.setSourcePath(newResourcePath);
											bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.TestExportText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newResourcePath); //$NON-NLS-1$ //$NON-NLS-2$
											// if the filename changed and the targetpath filename was the same then rename it too
											if (!newResourcePath.lastSegment().equals(oldResourcePath.lastSegment())) {
												IPath oldTargetPath = testExport.getTargetPath();
												if (oldTargetPath.lastSegment().equals(oldResourcePath.lastSegment())) {
													IPath newTargetPath = testExport.getTargetPath().removeLastSegments(1).append(newResourcePath.lastSegment());
													testExport.setTargetPath(newTargetPath);
													bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.TestExportTargetPathText") + oldTargetPath + Messages.getString("ResourceChangeListener.ChangedToText") + newTargetPath); //$NON-NLS-1$ //$NON-NLS-2$
												}
											}
											bldinfHasChanges = true;
										}
									} else if (oldResource instanceof IFolder) {
										if (path.toOSString().toLowerCase().startsWith(oldResourcePath.toOSString().toLowerCase())) {
											IPath newSourcePath = newResourcePath.append(path.removeFirstSegments(oldResourcePath.segmentCount()));
											testExport.setSourcePath(newSourcePath);
											bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.TestExportText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newSourcePath); //$NON-NLS-1$ //$NON-NLS-2$
											bldinfHasChanges = true;
										}
									}
								}
							}

							// check the bld.inf mak make refs (mmp and make files)
							for (IMakMakeReference ref : infView.getMakMakeReferences()) {
								IPath path = infPathHelper.convertToProject(ref.getPath());
								if (path != null) {
									if (oldResource instanceof IFile) {
										if (path.toOSString().compareToIgnoreCase(oldResourcePath.toOSString()) == 0) {
											// there should not be more than one mmp with the same pathname
											ref.setPath(newResourcePath);
											bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.MakMakeReferenceText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newResourcePath); //$NON-NLS-1$ //$NON-NLS-2$
											bldinfHasChanges = true;
											break;
										}
									} else if (oldResource instanceof IFolder) {
										if (path.toOSString().toLowerCase().startsWith(oldResourcePath.toOSString().toLowerCase())) {
											IPath newSourcePath = newResourcePath.append(path.removeFirstSegments(oldResourcePath.segmentCount()));
											ref.setPath(newSourcePath);
											bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.MakMakeReferenceText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newSourcePath); //$NON-NLS-1$ //$NON-NLS-2$
											bldinfHasChanges = true;
										}
									}
								}
							}
							
							for (IMakMakeReference ref : infView.getTestMakMakeReferences()) {
								IPath path = infPathHelper.convertToProject(ref.getPath());
								if (path != null) {
									if (oldResource instanceof IFile) {
										if (path.toOSString().compareToIgnoreCase(oldResourcePath.toOSString()) == 0) {
											// there should not be more than one make file with the same pathname
											ref.setPath(newResourcePath);
											bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.TestMakMakeReferenceText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newResourcePath); //$NON-NLS-1$ //$NON-NLS-2$
											bldinfHasChanges = true;
											break;
										}
									} else if (oldResource instanceof IFolder) {
										if (path.toOSString().toLowerCase().startsWith(oldResourcePath.toOSString().toLowerCase())) {
											IPath newSourcePath = newResourcePath.append(path.removeFirstSegments(oldResourcePath.segmentCount()));
											ref.setPath(newSourcePath);
											bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.TestMakMakeReferenceText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newSourcePath); //$NON-NLS-1$ //$NON-NLS-2$
											bldinfHasChanges = true;
										}
									}
								}
							}

							// check the extensions
							BldInfViewPathHelper helper = new BldInfViewPathHelper(infView, cpi.getDefaultConfiguration());
							for (IExtension extension : infView.getAllExtensions()) {
								IPath path = infPathHelper.convertToProject(helper.convertExtensionTemplateToFilesystem(extension.getTemplatePath()));
								if (path != null) {
									if (oldResource instanceof IFile) {
										if (path.toOSString().compareToIgnoreCase(oldResourcePath.toOSString()) == 0) {
											// there should not be more than one mmp with the same pathname
											extension.setTemplatePath(newResourcePath);
											bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.MakMakeReferenceText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newResourcePath); //$NON-NLS-1$ //$NON-NLS-2$
											bldinfHasChanges = true;
											break;
										}
									} else if (oldResource instanceof IFolder) {
										if (path.toOSString().toLowerCase().startsWith(oldResourcePath.toOSString().toLowerCase())) {
											IPath newSourcePath = newResourcePath.append(path.removeFirstSegments(oldResourcePath.segmentCount()));
											extension.setTemplatePath(newSourcePath);
											bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.MakMakeReferenceText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newSourcePath); //$NON-NLS-1$ //$NON-NLS-2$
											bldinfHasChanges = true;
										}
									}
								}
							}

							// now go through each mmp file
							IMMPReference[] mmps = infView.getAllMMPReferences();
							for (int i=0; i<mmps.length; i++) {
								// create an mmp model and view here and get the source paths
								final IPath workspaceRelativeMMPPath = new Path(project.getName()).append(mmps[i].getPath());
								EpocEnginePlugin.runWithMMPView(workspaceRelativeMMPPath,
										new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()),
										new MMPViewRunnableAdapter() {

											public Object run(IMMPView mmpView) {
												MMPViewPathHelper mmpPathHelper = new MMPViewPathHelper(cpi.getDefaultConfiguration());

												boolean mmpHasChanges = false;
												List<String> mmpChangeDetails = new ArrayList<String>(0);

												// check the sources.
												List<IPath> sources = mmpView.getSources();
												for (ListIterator<IPath> iter = sources.listIterator(); iter.hasNext();) {
													IPath path = mmpPathHelper.convertMMPToProject(EMMPPathContext.SOURCE, iter.next());
													if (path != null) {
														if (oldResource instanceof IFile) {
															if (path.toOSString().compareToIgnoreCase(oldResourcePath.toOSString()) == 0) {
																iter.set(newResourcePath);
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.SourceText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newResourcePath); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
																// there should not be more than one source file with the same pathname
																break;
															}
														} else if (oldResource instanceof IFolder) {
															if (path.toOSString().toLowerCase().startsWith(oldResourcePath.toOSString().toLowerCase())) {
																IPath newSourcePath = newResourcePath.append(path.removeFirstSegments(oldResourcePath.segmentCount())); 
																iter.set(newSourcePath);
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.SourceText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newSourcePath); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
															}
														}
													}
												}

												// check the resources
												for (IMMPResource resourceBlock : mmpView.getResourceBlocks()) {
													IPath path = mmpPathHelper.convertMMPToProject(EMMPPathContext.START_RESOURCE, resourceBlock.getSource());
													if (path != null) {
														if (oldResource instanceof IFile) {
															if (path.toOSString().compareToIgnoreCase(oldResourcePath.toOSString()) == 0) {
																resourceBlock.setSource(newResourcePath);
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.ResourceText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newResourcePath); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
																// there should not be more than one resource file with the same pathname
																break;
															}
														} else if (oldResource instanceof IFolder) {
															if (path.toOSString().toLowerCase().startsWith(oldResourcePath.toOSString().toLowerCase())) {
																IPath newSourcePath = newResourcePath.append(path.removeFirstSegments(oldResourcePath.segmentCount()));
																resourceBlock.setSource(newSourcePath);
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.ResourceText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newSourcePath); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
															}
														}
													}
												}
												
												// check the user resources
												List<IPath> userResources = mmpView.getUserResources();
												for (ListIterator<IPath> iter = userResources.listIterator(); iter.hasNext();) {
													IPath path = mmpPathHelper.convertMMPToProject(EMMPPathContext.RESOURCE, iter.next());
													if (path != null) {
														if (oldResource instanceof IFile) {
															if (path.toOSString().compareToIgnoreCase(oldResourcePath.toOSString()) == 0) {
																iter.set(newResourcePath);
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.ResourceText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newResourcePath); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
																// there should not be more than one resource file with the same pathname
																break;
															}
														} else if (oldResource instanceof IFolder) {
															if (path.toOSString().toLowerCase().startsWith(oldResourcePath.toOSString().toLowerCase())) {
																IPath newSourcePath = newResourcePath.append(path.removeFirstSegments(oldResourcePath.segmentCount())); 
																iter.set(newSourcePath);
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.ResourceText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newSourcePath); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
															}
														}
													}
												}
												
												// check the system resources
												List<IPath> systemResources = mmpView.getSystemResources();
												for (ListIterator<IPath> iter = systemResources.listIterator(); iter.hasNext();) {
													IPath path = mmpPathHelper.convertMMPToProject(EMMPPathContext.SYSTEMRESOURCE, iter.next());
													if (path != null) {
														if (oldResource instanceof IFile) {
															if (path.toOSString().compareToIgnoreCase(oldResourcePath.toOSString()) == 0) {
																iter.set(newResourcePath);
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.ResourceText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newResourcePath); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
																// there should not be more than one resource file with the same pathname
																break;
															}
														} else if (oldResource instanceof IFolder) {
															if (path.toOSString().toLowerCase().startsWith(oldResourcePath.toOSString().toLowerCase())) {
																IPath newSourcePath = newResourcePath.append(path.removeFirstSegments(oldResourcePath.segmentCount()));
																iter.set(newSourcePath);
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.ResourceText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newSourcePath); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
															}
														}
													}
												}

												// check the documents
												List<IPath> documents = mmpView.getDocuments();
												for (ListIterator<IPath> iter = documents.listIterator(); iter.hasNext();) {
													IPath path = mmpPathHelper.convertMMPToProject(EMMPPathContext.DOCUMENT, iter.next());
													if (path != null) {
														if (oldResource instanceof IFile) {
															if (path.toOSString().compareToIgnoreCase(oldResourcePath.toOSString()) == 0) {
																iter.set(newResourcePath);
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.DocumentText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newResourcePath); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
																// there should not be more than one document file with the same pathname
																break;
															}
														} else if (oldResource instanceof IFolder) {
															if (path.toOSString().toLowerCase().startsWith(oldResourcePath.toOSString().toLowerCase())) {
																IPath newSourcePath = newResourcePath.append(path.removeFirstSegments(oldResourcePath.segmentCount()));
																iter.set(newSourcePath);
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.DocumentText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newSourcePath); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
															}
														}
													}
												}

												// check the def file
												IPath defFilePath = mmpView.getDefFile();
												boolean wasFixed = mmpView.isDefFileInFixedDirectory();
												if (defFilePath != null) {
													defFilePath = mmpPathHelper.convertMMPToProject(EMMPPathContext.DEFFILE, defFilePath);
													if (defFilePath != null) {
														if (oldResource instanceof IFile) {
															if (defFilePath.toOSString().compareToIgnoreCase(oldResourcePath.toOSString()) == 0) {
																mmpView.setDefFile(newResourcePath, wasFixed);
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.DefFileText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newResourcePath); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
															}
														} else if (oldResource instanceof IFolder) {
															if (defFilePath.toOSString().toLowerCase().startsWith(oldResourcePath.toOSString().toLowerCase())) {
																IPath newSourcePath = newResourcePath.append(defFilePath.removeFirstSegments(oldResourcePath.segmentCount()));
																mmpView.setDefFile(newSourcePath, wasFixed);
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.DefFileText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newSourcePath); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
															}
														}
													}
												}
												
												// check the aifs
												for (IMMPAIFInfo aif : mmpView.getAifs()) {
													IPath path = mmpPathHelper.convertMMPToProject(EMMPPathContext.AIF_SOURCE, aif.getResource());
													if (path != null) {
														if (oldResource instanceof IFile) {
															if (path.toOSString().compareToIgnoreCase(oldResourcePath.toOSString()) == 0) {
																aif.setResource(newResourcePath);
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.AifResourceText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newResourcePath); //$NON-NLS-1$ //$NON-NLS-2$
																// if the .aif filename was the same as the resource witout the extension
																// then change it to.
																IPath aifTarget = aif.getTarget();
																if (aifTarget.removeFileExtension().lastSegment().equals(oldResourcePath.removeFileExtension().lastSegment())) {
																	IPath newAifTargetPath = aifTarget.removeLastSegments(1).append(newResourcePath.removeFileExtension().lastSegment()).addFileExtension(FileUtils.getSafeFileExtension(aifTarget));
																	aif.setTarget(newAifTargetPath);
																	mmpChangeDetails.add(Messages.getString("ResourceChangeListener.AifTargetPathText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newAifTargetPath); //$NON-NLS-1$ //$NON-NLS-2$
																}
																mmpHasChanges = true;
															}
														} else if (oldResource instanceof IFolder) {
															if (path.toOSString().toLowerCase().startsWith(oldResourcePath.toOSString().toLowerCase())) {
																IPath newSourcePath = newResourcePath.append(path.removeFirstSegments(oldResourcePath.segmentCount()));
																aif.setResource(newSourcePath);
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.AifResourceText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newSourcePath); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
															}
														}
													}
													
													for (IBitmapSourceReference bmpSource : aif.getSourceBitmaps()) {
														IPath path2 = mmpPathHelper.convertMMPToProject(EMMPPathContext.AIF_BITMAP, bmpSource.getPath());
														if (path2 != null) {
															if (oldResource instanceof IFile) {
																if (path2.toOSString().compareToIgnoreCase(oldResourcePath.toOSString()) == 0) {
																	bmpSource.setPath(newResourcePath);
																	mmpChangeDetails.add(Messages.getString("ResourceChangeListener.AifBitmapText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newResourcePath); //$NON-NLS-1$ //$NON-NLS-2$
																	mmpHasChanges = true;
																}
															} else if (oldResource instanceof IFolder) {
																if (path2.toOSString().toLowerCase().startsWith(oldResourcePath.toOSString().toLowerCase())) {
																	IPath newSourcePath = newResourcePath.append(path.removeFirstSegments(oldResourcePath.segmentCount()));
																	bmpSource.setPath(newSourcePath);
																	mmpChangeDetails.add(Messages.getString("ResourceChangeListener.AifBitmapText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newSourcePath); //$NON-NLS-1$ //$NON-NLS-2$
																	mmpHasChanges = true;
																}
															}
														}
													}
												}
												
												// check the mbms
												for (IMMPBitmap bmp : mmpView.getBitmaps()) {
													for (IBitmapSource bmpSrc : bmp.getBitmapSources()) {
														IPath path = mmpPathHelper.convertMMPToProject(EMMPPathContext.START_BITMAP_SOURCE, bmpSrc.getPath());
														if (path != null) {
															if (oldResource instanceof IFile) {
																if (path.toOSString().compareToIgnoreCase(oldResourcePath.toOSString()) == 0) {
																	bmpSrc.setPath(newResourcePath);
																	mmpChangeDetails.add(Messages.getString("ResourceChangeListener.BitmapText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newResourcePath); //$NON-NLS-1$ //$NON-NLS-2$
																	mmpHasChanges = true;
																}
															} else if (oldResource instanceof IFolder) {
																if (path.toOSString().toLowerCase().startsWith(oldResourcePath.toOSString().toLowerCase())) {
																	IPath newSourcePath = newResourcePath.append(path.removeFirstSegments(oldResourcePath.segmentCount()));
																	bmpSrc.setPath(newSourcePath);
																	mmpChangeDetails.add(Messages.getString("ResourceChangeListener.BitmapText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newSourcePath); //$NON-NLS-1$ //$NON-NLS-2$
																	mmpHasChanges = true;
																}
															}
														}
													}
												}

												if (oldResource instanceof IFolder) {
													// check user includes
													List<IPath> userIncludes = mmpView.getUserIncludes();
													for (ListIterator<IPath> iter = userIncludes.listIterator(); iter.hasNext();) {
														IPath path = mmpPathHelper.convertMMPToProject(EMMPPathContext.USERINCLUDE, iter.next());
														if (path != null && path.toOSString().toLowerCase().startsWith(oldResourcePath.toOSString().toLowerCase())) {
															iter.set(newResourcePath);
															mmpChangeDetails.add(Messages.getString("ResourceChangeListener.UserIncludeText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newResourcePath); //$NON-NLS-1$ //$NON-NLS-2$
															mmpHasChanges = true;
														}
													}

													// check system includes
													List<IPath> systemIncludes = mmpView.getSystemIncludes();
													for (ListIterator<IPath> iter = systemIncludes.listIterator(); iter.hasNext();) {
														IPath path = mmpPathHelper.convertMMPToProject(EMMPPathContext.SYSTEMINCLUDE, iter.next());
														if (path != null && path.toOSString().toLowerCase().startsWith(oldResourcePath.toOSString().toLowerCase())) {
															iter.set(newResourcePath);
															mmpChangeDetails.add(Messages.getString("ResourceChangeListener.SystemIncludeText") + oldResourcePath + Messages.getString("ResourceChangeListener.ChangedToText") + newResourcePath); //$NON-NLS-1$ //$NON-NLS-2$
															mmpHasChanges = true;
														}
													}
												}

												// now commit the changes to the mmp
												if (mmpHasChanges) {
													IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(workspaceRelativeMMPPath);
													
													// first check to see if we should make the change
													boolean shouldUpdate = false;
													switch (changedUpdatePrefOption) {
													case UPDATE_FILES_OPTION_ASK: {
														if (updateQuery != null) {
															shouldUpdate = updateQuery.shouldUpdate(project, file, mmpChangeDetails, false);
														}
														break;
													}
													case UPDATE_FILES_OPTION_ALWAYS: {
														shouldUpdate = true;
														break;
													}
													}
													
													if (shouldUpdate) {
														commitStanza(mmpView, file);
													}
												}
												return null;
											}
									
								});
							}

							// now commit the changes to the bld.inf
							if (bldinfHasChanges) {
								IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(cpi.getWorkspaceRelativeBldInfPath());
								
								// first check to see if we should make the change
								boolean shouldUpdate = false;
								switch (changedUpdatePrefOption) {
								case UPDATE_FILES_OPTION_ASK: {
									if (updateQuery != null) {
										shouldUpdate = updateQuery.shouldUpdate(project, file, bldInfChangeDetails, false);
									}
									break;
								}
								case UPDATE_FILES_OPTION_ALWAYS: {
									shouldUpdate = true;
									break;
								}
								}
								
								if (shouldUpdate) {
									commitStanza(infView, file);
								}
							}
							return null;
						}
						
					});
			
			iterationCount++;
			
		} else {
			assert(false);
		}
	}

	/**
	 * Helper function for project resource listener.
	 * @param resources the list of deleted files and/or folders, assumed to be in the same project
	 */
	private void resourcesDeleted(final List<IResource> resources) {
		if (resources.size() < 1) {
			return;
		}

		// run this in a workspace job as it modifies resources (bld.inf/mmp)
		WorkspaceJob job = new WorkspaceJob(Messages.getString("ResourceChangeListener.ResourceDeletedJobName")) { //$NON-NLS-1$
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				workspaceResourcesDeleted(resources);
				return Logging.newStatus(ProjectCorePlugin.getDefault(), IStatus.OK, null);
			}
		};
		job.setRule(resources.get(0).getProject());
		job.setUser(true);
		job.schedule();
	}
		
	private void workspaceResourcesDeleted(final List<IResource> resources) {
		// bail if never option is set
		if (UPDATE_FILES_OPTION_NEVER == changedUpdatePrefOption) {
			return;
		}

		// all resources should be in the same project
		final IProject project = resources.get(0).getProject();
        final ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		
        if (cpi == null || cpi.getDefaultConfiguration() == null)
        	return;
        
		EpocEnginePlugin.runWithBldInfView(cpi.getWorkspaceRelativeBldInfPath(),
				new DefaultViewConfiguration(project, null, new AllNodesViewFilter()),
				new BldInfViewRunnableAdapter() {

					public Object run(IBldInfView infView) {
						EpocEnginePathHelper infPathHelper = new EpocEnginePathHelper(project);

						// we need to keep track if any changes are made - IView#hasChanges has not
						// been implemented yet.
						boolean bldinfHasChanges = false;
						List<String> bldInfChangeDetails = new ArrayList<String>(0);

						// do for each deleted resource
						for (IResource resource : resources) {
							IPath resourcePath = resource.getProjectRelativePath();
							
							// NOTE: because the resource is already deleted at this point, the path
							// helper classes are not able to get the canonicalized paths.  so if the
							// case in the file system does not match that of the bld.inf/mmp file, using
							// IPath#equals and IPath#isPrefixOf will not worjk correctly.  therefore we
							// need to do case-insensitive string comparisons.
							
							// check the bld.inf exports
							List<IExport> exports = infView.getExports();
							for (Iterator<IExport> iter = exports.iterator(); iter.hasNext();) {
								IPath path = infPathHelper.convertToProject(iter.next().getSourcePath());
								if (path != null) {
									if (resource instanceof IFile) {
										if (path.toOSString().compareToIgnoreCase(resourcePath.toOSString()) == 0) {
											iter.remove();
											bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.ExportText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
											bldinfHasChanges = true;
										}
									} else if (resource instanceof IFolder) {
										if (path.toOSString().toLowerCase().startsWith(resourcePath.toOSString().toLowerCase())) {
											iter.remove();
											bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.ExportText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
											bldinfHasChanges = true;
										}
									}
								}
							}
							
							// check the bld.inf test exports
							List<IExport> testExports = infView.getTestExports();
							for (Iterator<IExport> iter = testExports.iterator(); iter.hasNext();) {
								IPath path = infPathHelper.convertToProject(iter.next().getSourcePath());
								if (path != null) {
									if (resource instanceof IFile) {
										if (path.toOSString().compareToIgnoreCase(resourcePath.toOSString()) == 0) {
											iter.remove();
											bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.TestExportText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
											bldinfHasChanges = true;
										}
									} else if (resource instanceof IFolder) {
										if (path.toOSString().toLowerCase().startsWith(resourcePath.toOSString().toLowerCase())) {
											iter.remove();
											bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.TestExportText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
											bldinfHasChanges = true;
										}
									}
								}
							}

							// check the bld.inf mak make refs (mmp and make files)
							List<IMakMakeReference> refs = infView.getMakMakeReferences();
							for (Iterator<IMakMakeReference> iter = refs.iterator(); iter.hasNext();) {
								IPath path = infPathHelper.convertToProject(iter.next().getPath());
								if (path != null) {
									if (resource instanceof IFile) {
										if (path.toOSString().compareToIgnoreCase(resourcePath.toOSString()) == 0) {
											iter.remove();
											bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.MakMakeReferenceText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
											bldinfHasChanges = true;
											// there should not be more than one mmp with the same pathname
											break;
										}
									} else if (resource instanceof IFolder) {
										if (path.toOSString().toLowerCase().startsWith(resourcePath.toOSString().toLowerCase())) {
											iter.remove();
											bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.MakMakeReferenceText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
											bldinfHasChanges = true;
										}
									}
								}
							}
							
							refs = infView.getTestMakMakeReferences();
							for (Iterator<IMakMakeReference> iter = refs.iterator(); iter.hasNext();) {
								IPath path = infPathHelper.convertToProject(iter.next().getPath());
								if (path != null) {
									if (resource instanceof IFile) {
										if (path.toOSString().compareToIgnoreCase(resourcePath.toOSString()) == 0) {
											iter.remove();
											bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.TestMakMakeReferenceText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
											bldinfHasChanges = true;
											// there should not be more than one make file with the same pathname
											break;
										}
									} else if (resource instanceof IFolder) {
										if (path.toOSString().toLowerCase().startsWith(resourcePath.toOSString().toLowerCase())) {
											iter.remove();
											bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.TestMakMakeReferenceText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
											bldinfHasChanges = true;
										}
									}
								}
							}

							// check the extensions
							BldInfViewPathHelper helper = new BldInfViewPathHelper(infView, cpi.getDefaultConfiguration());
							List<IExtension> extensions = infView.getExtensions();
							for (Iterator<IExtension> iter = extensions.iterator(); iter.hasNext();) {
								IPath path = infPathHelper.convertToProject(helper.convertExtensionTemplateToFilesystem(iter.next().getTemplatePath()));
								if (path != null) {
									if (resource instanceof IFile) {
										if (path.toOSString().compareToIgnoreCase(resourcePath.toOSString()) == 0) {
											iter.remove();
											bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.MakMakeReferenceText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
											bldinfHasChanges = true;
											// there should not be more than one mmp with the same pathname
											break;
										}
									} else if (resource instanceof IFolder) {
										if (path.toOSString().toLowerCase().startsWith(resourcePath.toOSString().toLowerCase())) {
											iter.remove();
											bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.MakMakeReferenceText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
											bldinfHasChanges = true;
										}
									}
								}
							}

							extensions = infView.getTestExtensions();
							for (Iterator<IExtension> iter = extensions.iterator(); iter.hasNext();) {
								IPath path = infPathHelper.convertToProject(helper.convertExtensionTemplateToFilesystem(iter.next().getTemplatePath()));
								if (path != null) {
									if (resource instanceof IFile) {
										if (path.toOSString().compareToIgnoreCase(resourcePath.toOSString()) == 0) {
											iter.remove();
											bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.MakMakeReferenceText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
											bldinfHasChanges = true;
											// there should not be more than one mmp with the same pathname
											break;
										}
									} else if (resource instanceof IFolder) {
										if (path.toOSString().toLowerCase().startsWith(resourcePath.toOSString().toLowerCase())) {
											iter.remove();
											bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.MakMakeReferenceText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
											bldinfHasChanges = true;
										}
									}
								}
							}
						}

						// now go through each mmp file
						IMMPReference[] mmps = infView.getAllMMPReferences();
						for (int i=0; i<mmps.length; i++) {
							// create an mmp model and view here and get the source paths
							final IPath workspaceRelativeMMPPath = new Path(project.getName()).append(mmps[i].getPath());
							EpocEnginePlugin.runWithMMPView(workspaceRelativeMMPPath,
									new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()),
									new MMPViewRunnableAdapter() {

										public Object run(IMMPView mmpView) {
											MMPViewPathHelper mmpPathHelper = new MMPViewPathHelper(cpi.getDefaultConfiguration());

											boolean mmpHasChanges = false;
											List<String> mmpChangeDetails = new ArrayList<String>(0);

											// do for each deleted resource
											for (IResource resource : resources) {
												IPath resourcePath = resource.getProjectRelativePath();

												// check the sources
												List<IPath> sources = mmpView.getSources();
												for (Iterator<IPath> iter = sources.iterator(); iter.hasNext();) {
													IPath path = mmpPathHelper.convertMMPToProject(EMMPPathContext.SOURCE, iter.next());
													if (path != null) {
														if (resource instanceof IFile) {
															if (path.toOSString().compareToIgnoreCase(resourcePath.toOSString()) == 0) {
																iter.remove();
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.SourceText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
																// there should not be more than one source file with the same pathname
																break;
															}
														} else if (resource instanceof IFolder) {
															if (path.toOSString().toLowerCase().startsWith(resourcePath.toOSString().toLowerCase())) {
																iter.remove();
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.SourceText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
															}
														}
													}
												}

												// check the resources
												List<IMMPResource> mmpResources = mmpView.getResourceBlocks();
												for (Iterator<IMMPResource> iter = mmpResources.iterator(); iter.hasNext();) {
													IPath path = mmpPathHelper.convertMMPToProject(EMMPPathContext.START_RESOURCE, iter.next().getSource());
													if (path != null) {
														if (resource instanceof IFile) {
															if (path.toOSString().compareToIgnoreCase(resourcePath.toOSString()) == 0) {
																iter.remove();
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.ResourceText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
																// there should not be more than one resource file with the same pathname
																break;
															}
														} else if (resource instanceof IFolder) {
															if (path.toOSString().toLowerCase().startsWith(resourcePath.toOSString().toLowerCase())) {
																iter.remove();
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.ResourceText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
															}
														}
													}
												}
												
												// check the user resources
												List<IPath> userResources = mmpView.getUserResources();
												for (Iterator<IPath> iter = userResources.iterator(); iter.hasNext();) {
													IPath path = mmpPathHelper.convertMMPToProject(EMMPPathContext.RESOURCE, iter.next());
													if (path != null) {
														if (resource instanceof IFile) {
															if (path.toOSString().compareToIgnoreCase(resourcePath.toOSString()) == 0) {
																iter.remove();
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.ResourceText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
																// there should not be more than one resource file with the same pathname
																break;
															}
														} else if (resource instanceof IFolder) {
															if (path.toOSString().toLowerCase().startsWith(resourcePath.toOSString().toLowerCase())) {
																iter.remove();
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.ResourceText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
															}
														}
													}
												}
												
												// check the system resources
												List<IPath> systemResources = mmpView.getSystemResources();
												for (Iterator<IPath> iter = systemResources.iterator(); iter.hasNext();) {
													IPath path = mmpPathHelper.convertMMPToProject(EMMPPathContext.SYSTEMRESOURCE, iter.next());
													if (path != null) {
														if (resource instanceof IFile) {
															if (path.toOSString().compareToIgnoreCase(resourcePath.toOSString()) == 0) {
																iter.remove();
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.ResourceText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
																// there should not be more than one resource file with the same pathname
																break;
															}
														} else if (resource instanceof IFolder) {
															if (path.toOSString().toLowerCase().startsWith(resourcePath.toOSString().toLowerCase())) {
																mmpHasChanges = true;
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.ResourceText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
																iter.remove();
															}
														}
													}
												}

												// check the documents
												List<IPath> documents = mmpView.getDocuments();
												for (Iterator<IPath> iter = documents.iterator(); iter.hasNext();) {
													IPath path = mmpPathHelper.convertMMPToProject(EMMPPathContext.DEFFILE, iter.next());
													if (path != null) {
														if (resource instanceof IFile) {
															if (path.toOSString().compareToIgnoreCase(resourcePath.toOSString()) == 0) {
																iter.remove();
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.DocumentText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
																// there should not be more than one document file with the same pathname
																break;
															}
														} else if (resource instanceof IFolder) {
															if (path.toOSString().toLowerCase().startsWith(resourcePath.toOSString().toLowerCase())) {
																iter.remove();
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.DocumentText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
															}
														}
													}
												}

												// check the def file
												IPath defFilePath = mmpView.getDefFile();
												if (defFilePath != null) {
													defFilePath = mmpPathHelper.convertMMPToProject(EMMPPathContext.DEFFILE, defFilePath);
													if (defFilePath != null) {
														if (resource instanceof IFile) {
															if (defFilePath.toOSString().compareToIgnoreCase(resourcePath.toOSString()) == 0) {
																mmpView.setDefFile(null, false);
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.DefFileText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
															}
														} else if (resource instanceof IFolder) {
															if (defFilePath.toOSString().toLowerCase().startsWith(resourcePath.toOSString().toLowerCase())) {
																mmpView.setDefFile(null, false);
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.DefFileText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
															}
														}
													}
												}
												
												// check the aifs
												List<IMMPAIFInfo> aifs = mmpView.getAifs();
												for (Iterator<IMMPAIFInfo> iter = aifs.iterator(); iter.hasNext();) {
													IMMPAIFInfo aif = iter.next();
													IPath path = mmpPathHelper.convertMMPToProject(EMMPPathContext.AIF_SOURCE, aif.getResource());
													if (path != null) {
														if (resource instanceof IFile) {
															if (path.toOSString().compareToIgnoreCase(resourcePath.toOSString()) == 0) {
																iter.remove();
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.AifResourceText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
															}
														} else if (resource instanceof IFolder) {
															if (path.toOSString().toLowerCase().startsWith(resourcePath.toOSString().toLowerCase())) {
																iter.remove();
																mmpChangeDetails.add(Messages.getString("ResourceChangeListener.AifResourceText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
																mmpHasChanges = true;
															}
														}
													}
													
													List<IBitmapSourceReference> bmpSources = aif.getSourceBitmaps();
													for (Iterator<IBitmapSourceReference> iter2 = bmpSources.iterator(); iter2.hasNext();) {
														IPath path2 = mmpPathHelper.convertMMPToProject(EMMPPathContext.AIF_BITMAP, iter2.next().getPath());
														if (path2 != null) {
															if (resource instanceof IFile) {
																if (path2.toOSString().compareToIgnoreCase(resourcePath.toOSString()) == 0) {
																	iter2.remove();
																	mmpChangeDetails.add(Messages.getString("ResourceChangeListener.AifBitmapText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
																	mmpHasChanges = true;
																}
															} else if (resource instanceof IFolder) {
																if (path2.toOSString().toLowerCase().startsWith(resourcePath.toOSString().toLowerCase())) {
																	iter2.remove();
																	mmpChangeDetails.add(Messages.getString("ResourceChangeListener.AifBitmapText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
																	mmpHasChanges = true;
																}
															}
														}
													}
												}
												
												// check the mbms
												List<IMMPBitmap> bmps = mmpView.getBitmaps();
												for (IMMPBitmap bmp : bmps) {
													List<IBitmapSource> bmpSources = bmp.getBitmapSources();
													for (Iterator<IBitmapSource> iter = bmpSources.iterator(); iter.hasNext();) {
														IPath path = mmpPathHelper.convertMMPToProject(EMMPPathContext.START_BITMAP_SOURCE, iter.next().getPath());
														if (path != null) {
															if (resource instanceof IFile) {
																if (path.toOSString().compareToIgnoreCase(resourcePath.toOSString()) == 0) {
																	iter.remove();
																	mmpChangeDetails.add(Messages.getString("ResourceChangeListener.BitmapText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
																	mmpHasChanges = true;
																}
															} else if (resource instanceof IFolder) {
																if (path.toOSString().toLowerCase().startsWith(resourcePath.toOSString().toLowerCase())) {
																	iter.remove();
																	mmpChangeDetails.add(Messages.getString("ResourceChangeListener.BitmapText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
																	mmpHasChanges = true;
																}
															}
														}
													}
												}

												if (resource instanceof IFolder) {
													// check user includes
													List<IPath> userIncludes = mmpView.getUserIncludes();
													for (Iterator<IPath> iter = userIncludes.iterator(); iter.hasNext();) {
														IPath path = mmpPathHelper.convertMMPToProject(EMMPPathContext.USERINCLUDE, iter.next());
														if (path != null && path.toOSString().compareToIgnoreCase(resourcePath.toOSString()) == 0) {
															iter.remove();
															mmpChangeDetails.add(Messages.getString("ResourceChangeListener.UserIncludeText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
															mmpHasChanges = true;
														}
													}
													
													// check system includes
													List<IPath> systemIncludes = mmpView.getSystemIncludes();
													for (Iterator<IPath> iter = systemIncludes.iterator(); iter.hasNext();) {
														IPath path = mmpPathHelper.convertMMPToProject(EMMPPathContext.SYSTEMINCLUDE, iter.next());
														if (path != null && path.toOSString().compareToIgnoreCase(resourcePath.toOSString()) == 0) {
															iter.remove();
															mmpChangeDetails.add(Messages.getString("ResourceChangeListener.SystemIncludeText") + resourcePath + Messages.getString("ResourceChangeListener.DeletedText")); //$NON-NLS-1$ //$NON-NLS-2$
															mmpHasChanges = true;
														}
													}
												}
											}

											// now commit the changes to the mmp
											if (mmpHasChanges) {
												IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(workspaceRelativeMMPPath);
												
												// first check to see if we should make the change
												boolean shouldUpdate = false;
												switch (changedUpdatePrefOption) {
												case UPDATE_FILES_OPTION_ASK: {
													if (updateQuery != null) {
														shouldUpdate = updateQuery.shouldUpdate(project, file, mmpChangeDetails, false);
													}
													break;
												}
												case UPDATE_FILES_OPTION_ALWAYS: {
													shouldUpdate = true;
													break;
												}
												}
												
												if (shouldUpdate) {
													commitStanza(mmpView, file);
												}
											}
											return null;
										}
								
							});
						}

						// now commit the changes to the bld.inf
						if (bldinfHasChanges) {
							IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(cpi.getWorkspaceRelativeBldInfPath());
							
							// first check to see if we should make the change
							boolean shouldUpdate = false;
							switch (changedUpdatePrefOption) {
							case UPDATE_FILES_OPTION_ASK: {
								if (updateQuery != null) {
									shouldUpdate = updateQuery.shouldUpdate(project, file, bldInfChangeDetails, false);
								}
								break;
							}
							case UPDATE_FILES_OPTION_ALWAYS: {
								shouldUpdate = true;
								break;
							}
							}
							
							if (shouldUpdate) {
								commitStanza(infView, file);
							}
						}
						return null;
					}
					
				});
		
		iterationCount++;
	}

	/**
	 * Helper function for project resource listener.
	 * @param files the list of new files, assumed to be in the same directory
	 */
	private void filesCreated(final List<IFile> files) {
		if (files.size() < 1) {
			return;
		}

		// run this in a workspace job as it modifies resources (bld.inf/mmp)
		WorkspaceJob job = new WorkspaceJob(Messages.getString("ResourceChangeListener.ResourceCreatedJobName")) { //$NON-NLS-1$
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				// all files should be in the same project and same directory
				final IProject project = files.get(0).getProject();
				workspacefilesCreated(project, files, false);
				return Logging.newStatus(ProjectCorePlugin.getDefault(), IStatus.OK, null);
			}
		};
		job.setRule(files.get(0).getProject());
		job.setUser(true);
		job.schedule();
	}

	/**
	 * Add the given list of source or mmp/mk files to the project.  If more than one mmp file exists
	 * in the project and it can't be determined which mmp file the new source belongs to (by checking
	 * existing source paths), the user will be asked to choose which mmp file to use (if any).  The
	 * list of files will be trimmed if it contains any files other than source files or mmp/mk files
	 * since these are the types of files that get added to bld.inf/mmp files.
	 * @param project the project to add the files to
	 * @param files the list of IFiles to add (source files, mmp/mk files)
	 * @param updateNoMatterWhat ignore any pref settings and just update
	 */
	public static void workspacefilesCreated(final IProject project, final List<IFile> files, final boolean updateNoMatterWhat) {
		// bail if never option is set
		if (!updateNoMatterWhat && UPDATE_FILES_OPTION_NEVER == addedUpdatePrefOption) {
			return;
		}
		
		// strip out any resources we're supposed to ignore
		files.removeAll(resourcesToIgnoreOnNextChange);
		resourcesToIgnoreOnNextChange.clear();

		// first filter the list
		final List<IFile> filesToAdd = new ArrayList<IFile>();
		for (IFile file : files) {
			String filename = file.getName();
			// make sure it's a source file.  note that we mark .rss files as source files in CDT so we
			// can take advantage of the parsing, but we don't want to treat them as source files here.
			if (!filename.toLowerCase().endsWith(".rss") && CoreModel.isValidSourceUnitName(file.getProject(), filename)) { //$NON-NLS-1$
				filesToAdd.add(file);
			}
			// also need to check for mmp and make files
			if (filename.toLowerCase().endsWith(".mmp") || filename.toLowerCase().endsWith(".mk")) { //$NON-NLS-1$ //$NON-NLS-2$
				filesToAdd.add(file);
			}
		}
		
		if (filesToAdd.size() < 1) {
			return;
		}
		
        final ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);

        if (cpi == null || cpi.getDefaultConfiguration() == null)
        	return;
        
		final IPath directory = filesToAdd.get(0).getProjectRelativePath().removeLastSegments(1);

		EpocEnginePlugin.runWithBldInfView(cpi.getWorkspaceRelativeBldInfPath(),
				new DefaultViewConfiguration(project, null, new AllNodesViewFilter()),
				new BldInfViewRunnableAdapter() {

					public Object run(IBldInfView infView) {
						// we need to keep track if any changes are made - IView#hasChanges has not
						// been implemented yet.
						boolean bldinfHasChanges = false;
						List<String> bldInfChangeDetails = new ArrayList<String>(0);

						// see if any of the added files are mmp or make files which should be
						// added to the bld.inf file.
						List<IFile> mmpFiles = new ArrayList<IFile>(0);
						List<IFile> makeFiles = new ArrayList<IFile>(0);
						for (Iterator<IFile> iter = filesToAdd.iterator(); iter.hasNext();) {
							IFile file = iter.next();
							if (file.getName().toLowerCase().endsWith(".mmp")) { //$NON-NLS-1$
								mmpFiles.add(file);
								iter.remove();
							}
							if (file.getName().toLowerCase().endsWith(".mk")) { //$NON-NLS-1$
								makeFiles.add(file);
								iter.remove();
							}
						}

						EpocEnginePathHelper infPathHelper = new EpocEnginePathHelper(project);
						List<IMakMakeReference> refs = infView.getMakMakeReferences();

						if (!mmpFiles.isEmpty()) {
							for (IFile file : mmpFiles) {
								// make sure the mmp file is not already in the bld.inf for whatever reason
								boolean alreadyExists = false;
								for (IMakMakeReference ref : refs) {
									IPath filePath = infPathHelper.convertToProject(ref.getPath());
									if (filePath != null && 
											filePath.toOSString().compareToIgnoreCase(
													file.getProjectRelativePath().toOSString()) == 0) {
										alreadyExists = true;
										break;
									}
								}
								if (!alreadyExists) {
									IMMPReference newRef = infView.createMMPReference();
									newRef.setPath(file.getProjectRelativePath());
									refs.add(newRef);
									bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.MMPFileText") + file.getProjectRelativePath() + Messages.getString("ResourceChangeListener.AddedText")); //$NON-NLS-1$ //$NON-NLS-2$
									bldinfHasChanges = true;
								}
							}
						}

						if (!makeFiles.isEmpty()) {
							for (IFile file : makeFiles) {
								// make sure the make file is not already in the bld.inf for whatever reason
								boolean alreadyExists = false;
								for (IMakMakeReference ref : refs) {
									IPath filePath = infPathHelper.convertToProject(ref.getPath());
									if (filePath != null && filePath.toOSString().compareToIgnoreCase(
											file.getProjectRelativePath().toOSString()) == 0) {
										alreadyExists = true;
										break;
									}
								}
								if (!alreadyExists) {
									IMakefileReference newRef = infView.createMakefileReference();
									newRef.setPath(file.getProjectRelativePath());
									newRef.setMakeEngine(EMakeEngine.GNUMAKEFILE);
									refs.add(newRef);
									bldInfChangeDetails.add(Messages.getString("ResourceChangeListener.MakeFileText") + file.getProjectRelativePath() + Messages.getString("ResourceChangeListener.AddedText")); //$NON-NLS-1$ //$NON-NLS-2$
									bldinfHasChanges = true;
								}
							}
						}

						// now commit the changes to the bld.inf
						if (bldinfHasChanges) {
							IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(cpi.getWorkspaceRelativeBldInfPath());
							
							// first check to see if we should make the change
							boolean shouldUpdate = updateNoMatterWhat;
							if (!shouldUpdate) {
								switch (addedUpdatePrefOption) {
								case UPDATE_FILES_OPTION_ASK: {
									if (updateQuery != null) {
										shouldUpdate = updateQuery.shouldUpdate(project, file, bldInfChangeDetails, true);
									}
									break;
								}
								case UPDATE_FILES_OPTION_ALWAYS: {
									shouldUpdate = true;
									break;
								}
								}
							}
							
							if (shouldUpdate) {
								commitStanza(infView, file);
							}
						}
						
						// if there are no source files then we are done
						if (filesToAdd.isEmpty()) {
							return null;
						}

						// determine which mmp file to add the source file(s) to.  if there is only one
						// mmp then it's simple.  if there is more than one then use this logic:
						// 		if only one mmp has the sourcepath entry matching the added files the use it
						//		otherwise we have to ask the user which mmp file(s) to add it to.
						IMMPReference[] mmps = infView.getAllMMPReferences();
						List<IMMPReference> mmpsToAddFilesTo = new ArrayList<IMMPReference>();
						if (mmps.length == 1) {
							mmpsToAddFilesTo.add(mmps[0]);
						} else if (mmps.length > 1) {
							// see how many mmps already contain a sourcepath entry for the directory containing
							// the new source file(s).  if only one does then use it.  if none or more than one do
							// then display a dialog listing all mmps in the bld.inf.  the user can select one or more.
							final List<IMMPReference> mmpsContainingSourcePath = new ArrayList<IMMPReference>();
							
							for (int i=0; i<mmps.length; i++) {
								final IMMPReference mmp = mmps[i];
								final IPath workspaceRelativeMMPPath = new Path(project.getName()).append(mmp.getPath());
								EpocEnginePlugin.runWithMMPView(workspaceRelativeMMPPath,
										new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()),
										new MMPViewRunnableAdapter() {

											public Object run(IMMPView mmpView) {
												MMPViewPathHelper mmpPathHelper = new MMPViewPathHelper(cpi.getDefaultConfiguration());

												// see if it contains the sourcepath
												IPath[] sourcePaths = mmpView.getEffectiveSourcePaths();
												for (int j=0; j<sourcePaths.length; j++) {
													IPath path = mmpPathHelper.convertMMPToProject(EMMPPathContext.SOURCEPATH, sourcePaths[j]);
													if (path != null && path.toOSString().compareToIgnoreCase(
															directory.toOSString()) == 0) {
														mmpsContainingSourcePath.add(mmp);
													}
												}

												return null;
											}
								});
							}
							
							if (mmpsContainingSourcePath.size() == 1) {
								mmpsToAddFilesTo.add(mmpsContainingSourcePath.get(0));
							} else {
								if (resolver != null) {
									mmpsToAddFilesTo.addAll(resolver.addSourceToWhichMMPs(Arrays.asList(mmps), mmpsContainingSourcePath));
								}
							}
						}

						for (IMMPReference mmp : mmpsToAddFilesTo) {
							// create an mmp model and view here and add the new source file(s)
							final IPath workspaceRelativeMMPPath = new Path(project.getName()).append(mmp.getPath());
							EpocEnginePlugin.runWithMMPView(workspaceRelativeMMPPath,
									new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()),
									new MMPViewRunnableAdapter() {

										public Object run(IMMPView mmpView) {
											MMPViewPathHelper mmpPathHelper = new MMPViewPathHelper(cpi.getDefaultConfiguration());

											boolean mmpHasChanges = false;
											List<String> mmpChangeDetails = new ArrayList<String>(0);

											// add the source(s)
											List<IPath> sources = mmpView.getSources();
											for (IFile file : filesToAdd) {
												IPath filePath = null;
												try {
													filePath = mmpPathHelper.convertProjectOrFullPathToMMP(EMMPPathContext.SOURCE, file.getProjectRelativePath());
												} catch (InvalidDriveInMMPPathException e) {
													// shouldn't get here; we passed in a project-relative path
													ProjectCorePlugin.log(e);
													filePath = null;
												}
												// make sure the file is not already in the mmp for whatever reason
												if (filePath != null) {
													boolean found = false;
													for (IPath source : sources) {
														if (source.toOSString().equalsIgnoreCase(filePath.toOSString())) {
															found = true;
															break;
														}
													}
													if (!found) {
														sources.add(filePath);
														mmpChangeDetails.add(Messages.getString("ResourceChangeListener.SourceText") + filePath + Messages.getString("ResourceChangeListener.AddedText")); //$NON-NLS-1$ //$NON-NLS-2$
														mmpHasChanges = true;
													}
												}
											}
											
											// now commit the changes to the mmp
											if (mmpHasChanges) {
												IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(workspaceRelativeMMPPath);
												
												// check to see if we should make the change
												boolean shouldUpdate = updateNoMatterWhat;
												if  (!shouldUpdate) {
													switch (addedUpdatePrefOption) {
													case UPDATE_FILES_OPTION_ASK: {
														if (updateQuery != null) {
															shouldUpdate = updateQuery.shouldUpdate(project, file, mmpChangeDetails, true);
														}
														break;
													}
													case UPDATE_FILES_OPTION_ALWAYS: {
														shouldUpdate = true;
														break;
													}
													}
												}

												if (shouldUpdate) {
													commitStanza(mmpView, file);
												}
											}
											return null;
										}
								
							});
						}
						return null;
					}
					
				});
		
		iterationCount++;
	}
	
	/**
	 * Add the given mmp file to the project.  This ignores any pref values about when to update.
	 * @param project the project to add the mmp file to
	 * @param file the mmp file
	 * @param isTestMMP whether the mmp is a test or regular mmp file
	 * @since 2.0
	 */
	public static void addMMPFileToProject(final IProject project, final IFile file, final boolean isTestMMP) {

		final ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
        if (cpi == null || cpi.getDefaultConfiguration() == null)
        	return;
        
		EpocEnginePlugin.runWithBldInfView(cpi.getWorkspaceRelativeBldInfPath(),
				new DefaultViewConfiguration(project, null, new AllNodesViewFilter()),
				new BldInfViewRunnableAdapter() {

					public Object run(IBldInfView infView) {
						List<IMakMakeReference> refs = isTestMMP ? infView.getTestMakMakeReferences() : infView.getMakMakeReferences();

						IMMPReference newRef = infView.createMMPReference();
						newRef.setPath(file.getProjectRelativePath());
						refs.add(newRef);
						
						// now commit the changes to the bld.inf
						IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(cpi.getWorkspaceRelativeBldInfPath());
						commitStanza(infView, file);
						return null;
					}
					
				});
		
		resourcesToIgnoreOnNextChange.add(file);
		
		iterationCount++;
	}

	public static int getIteration() {
		return iterationCount;
	}

	private static void commitStanza(IView view, IFile file) {
		// removed IView#hasChanges() check
		if (confirmEdit(file)) {
			while (true) {
				try {
					view.commit();
					break;
				} catch (IllegalStateException e) {
					if (!view.merge()) {
						view.revert();
					}
				}
			}
		} else {
			view.revert();
		}
	}

	/**
	 * Confirm editing should be allowed. If needed, interacts with the
	 * Team VCS provider to check that file can be modified.
	 * @return true if ok to proceed with changes.
	 */
	private static boolean confirmEdit(IFile file) {
		IWorkbench workbench = null;
		try {
			workbench = PlatformUI.getWorkbench();
		} catch (IllegalStateException e) {
			// no workbench (headless mode)
			return true;
		}
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();

		if (window == null) {
			IWorkbenchWindow windows[] = workbench.getWorkbenchWindows();
			window = windows[0];
		}

		final Shell shell = window.getShell();

		IStatus status = ResourcesPlugin.getWorkspace().validateEdit(
				new IFile[] {file}, shell);
		return status == null || status.isOK();
	}
}
