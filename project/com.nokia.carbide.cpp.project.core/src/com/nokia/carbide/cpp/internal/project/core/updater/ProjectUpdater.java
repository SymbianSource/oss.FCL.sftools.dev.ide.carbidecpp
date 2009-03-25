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


package com.nokia.carbide.cpp.internal.project.core.updater;

import com.nokia.carbide.cdt.builder.*;
import com.nokia.carbide.cpp.epoc.engine.*;
import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.model.EGeneratedHeaderFlags;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.*;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakefileReference.EMakeEngine;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileView;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.*;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AllNodesViewFilter;
import com.nokia.carbide.cpp.internal.api.project.core.ProjectCorePluginUtility;
import com.nokia.carbide.cpp.internal.project.core.Messages;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.internal.api.cpp.epoc.engine.image.*;
import com.nokia.carbide.updater.extension.IProjectUpdater;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.cdt.core.*;
import org.eclipse.cdt.core.model.*;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.make.core.makefile.ICommand;
import org.eclipse.cdt.make.core.makefile.ITargetRule;
import org.eclipse.cdt.managedbuilder.core.ManagedBuildManager;
import org.eclipse.cdt.managedbuilder.core.ManagedCProjectNature;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.*;

/**
 * Class implementing the com.nokia.carbide.updater.projectUpdater extension point
 */
public class ProjectUpdater implements IProjectUpdater {

	private class UpdateBldInfViewRunnable implements IBldInfViewRunnable {
		private IPath path;
		private EpocEnginePathHelper helper;

		private UpdateBldInfViewRunnable(IPath path) {
			this.helper = new EpocEnginePathHelper(project);
			this.path = path;
		}

		public Object failedLoad(CoreException exception) {
			if (exception != null)
				return exception.getStatus();
			return genericErrorStatus(new Exception(Messages.getString("ProjectUpdater.InfNotExistError"))); //$NON-NLS-1$
		}

		public Object run(IBldInfView view) {
			while (true) {
				IStatus status = updateBldInfView(view);
				if (!status.isOK())
					return status;
				
				try {
					view.commit();
					break;
				} 
				catch (IllegalStateException e) {
					view.revert();
				}
			}
			return Status.OK_STATUS;
		}

		private IStatus updateBldInfView(IBldInfView view) {
			
			updateMakMakeReferences(view);
			
			removeDeadProjectExports(view);
				
			IStatus status = updateMIFDEFs(view);
			if (!status.isOK())
				return status;
			
			updatePlatforms(view);
			
			return Status.OK_STATUS;
		}

		/**
		 * Remove any makmake references to files that don't exist.
		 * These will make bldmake bldfiles stop dead.
		 */
		private void updateMakMakeReferences(IBldInfView view) {
			List<IMakMakeReference> references = view.getMakMakeReferences();
			// remove dead MMPs and makefiles, which cause bldmake bldfiles to fail
			IMakMakeReference existingMMPRef = null;
			for (Iterator iter = references.iterator(); iter.hasNext();) {
				IMakMakeReference ref = (IMakMakeReference) iter.next();
				IPath refPath = helper.convertToWorkspace(ref.getPath());
				if (refPath == null || ResourcesPlugin.getWorkspace().getRoot().findMember(refPath) == null) {
					iter.remove();
				} else if (refPath != null && refPath.removeFirstSegments(1).toOSString().equalsIgnoreCase(path.toOSString())) {
					existingMMPRef = ref;
				}
			}
			
			// add a ref to the converted MMP if it's not already there
			// (we want to keep any existing flags set on the line)
			if (existingMMPRef == null) {
				IMMPReference mmpRef = view.createMMPReference();
				mmpRef.setPath(path);
				references.add(mmpRef);
			}
		}
		
		/**
		 * Remove PRJ_EXPORTS entries that don't exist.  
		 * These will make bldmake bldfiles stop dead.
		 */
		private void removeDeadProjectExports(IBldInfView view) {
			List<IExport> exports = view.getExports();
			for (Iterator iter = exports.iterator(); iter.hasNext();) {
				IExport export = (IExport) iter.next();
				IPath path = helper.convertToWorkspace(export.getSourcePath());
				if (path == null || ResourcesPlugin.getWorkspace().getRoot().findMember(path) == null) {
					iter.remove();
				}
			}
		}
	}

	private class UpdateMMPViewRunnable implements IMMPViewRunnable {

		public Object failedLoad(CoreException exception) {
			if (exception != null)
				return exception.getStatus();
			return genericErrorStatus(new Exception(Messages.getString("ProjectUpdater.MMPNotExistError"))); //$NON-NLS-1$
		}

		public Object run(IMMPView view) {
			while (true) {
				IStatus status = updateMMPView(view);
				if (!status.isOK())
					return status;
				
				try {
					view.commit();
					break;
				} 
				catch (IllegalStateException e) {
					view.revert();
				}
			}

			return Status.OK_STATUS;
		}

	}
	
	private class FindIconMakefileViewRunnable implements IImageMakefileViewRunnable {

		public Object failedLoad(CoreException exception) {
			return Status.CANCEL_STATUS; // flag this one doesn't load!
		}

		public Object run(IImageMakefileView view) {
			List<IMultiImageSource> multiImageSources = view.getMultiImageSources();
			if (multiImageSources != null && !multiImageSources.isEmpty())
				return Status.OK_STATUS; // flag to use this one
			
			return null; // flag to ignore this one
		}
		
	}

	private class AddSourceToIconMakefileViewRunnable implements IImageMakefileViewRunnable {

		private static final String IMAGE_MAKEFILE_DEFAULT_TARGET = "RESOURCE"; //$NON-NLS-1$
		private static final String IMAGE_MAKEFILE_RELEASEABLES_TARGET = "RELEASABLES"; //$NON-NLS-1$
		
		private final List<IMultiImageSource> sources;

		public AddSourceToIconMakefileViewRunnable(List<IMultiImageSource> sources) {
			this.sources = sources;
		}

		public Object failedLoad(CoreException exception) {
			if (exception != null)
				return exception.getStatus();
			return genericErrorStatus(new Exception(Messages.getString("ProjectUpdater.MKNotExistError"))); //$NON-NLS-1$
		}

		public Object run(IImageMakefileView view) {
			while (true){ 
			
				for (IMultiImageSource mis : sources) {
					updateMultiImageSource(view, mis);
				}
	
				// ensure mifconv rules end up under RESOURCE:
				view.setDefaultImageTarget(IMAGE_MAKEFILE_DEFAULT_TARGET);
	
				// add the filenames to the RELEASABLES rule
				ITargetRule releaseRule = view.findRuleForTarget(IMAGE_MAKEFILE_RELEASEABLES_TARGET, true);
				ICommand[] releaseCommands = releaseRule.getCommands();
				
				for (ICommand cmd : releaseCommands) {
					if (cmd.toString().contains("echo")) { //$NON-NLS-1$
						StringBuilder newCommand = new StringBuilder();
						newCommand.append("\t@echo "); //$NON-NLS-1$
						for (IMultiImageSource mis : sources) {
							newCommand.append(view.getUnexpandedMultiImageSourceTargetPath(mis));
							newCommand.append(' ');
						}
						newCommand.append('\n');
						view.replaceDirective(cmd, newCommand.toString());
						break;
					}
				}
			
				try {
					view.commit();
					break;
				} 
				catch (IllegalStateException e) {
					if (!view.merge()) {
						view.revert();
					}
				}
			}
			
			return Status.OK_STATUS;
		}

		/**
		 * @param mis
		 */
		private void updateMultiImageSource(IImageMakefileView view, IMultiImageSource source) {
			List<IMultiImageSource> multiImageSources = view.getMultiImageSources();
			for (IMultiImageSource existing : multiImageSources) {
				if (existing.getTargetFile().equalsIgnoreCase(source.getTargetFile())) {
					multiImageSources.remove(existing);
					break;
				}
			}
			
			multiImageSources.add(source);
		}

	}

	private static final String DEFAULT_ICON_MK_FILE_NAME = "Icons_scalable_dc.mk"; //$NON-NLS-1$
	private static final String ICON_FILE_NOCASE = "(?i).*icon.*"; //$NON-NLS-1$

	private static final String SYMBIAN_NATURE_ID = "com.symbian.cdt.core.symbiannature"; //$NON-NLS-1$
    private static final String SDT_SYMBIAN_BUNDLE_ID = "com.nokia.sdt.symbian"; //$NON-NLS-1$
	private static final String SYMBIAN_LANGUAGE_UTILS_CLASS = 
		"com.nokia.sdt.symbian.workspace.SymbianLanguageUtils"; //$NON-NLS-1$
	private static final String GET_DESIGN_LANGAUGES_METHOD_NAME = "getDesignLanguages"; //$NON-NLS-1$

	
	private static final String DEFAULT_PRJ_PLATFORM = "DEFAULT"; //$NON-NLS-1$
	private static final String COMMENT_DELIM = "//"; //$NON-NLS-1$
	private static final String DEFAULT_PRJ_PLATFORM_COMMENT = Messages.getString("ProjectUpdater.PlatformsNotAvailableWithAllSDKsComment"); //$NON-NLS-1$

	private static final String CUR_DIR = "."; //$NON-NLS-1$
	private static final String EPOC32_INC = "\\epoc32\\include"; //$NON-NLS-1$
	
	private static final String[] IMPLICIT_LIB_PREFIXES = {
		"dfpaeabi", //$NON-NLS-1$
		"dfprvct", //$NON-NLS-1$
		"drtaeabi", //$NON-NLS-1$
		"drtrvct", //$NON-NLS-1$
		"scppnwdl", //$NON-NLS-1$
		"usrt" //$NON-NLS-1$
	};
	
	private static final String[] IMPLICIT_LIBS = {
		"EDLLSTUB.LIB", //$NON-NLS-1$
		"EGCC.LIB", //$NON-NLS-1$
		"EEXE.LIB", //$NON-NLS-1$
		"EDLL.LIB" //$NON-NLS-1$
	};
	
	private static class ImplicitStaticLibSpec extends Pair<Tuple, String> {
		private final static int FAMILY = 0;
		private final static int FROM_VERS = 1;
		private final static int TO_VERS = 2;
		
		public static Tuple createSDKSpec(String sdkFamily, Version fromVersion, Version toVersion) {
			return new Tuple(sdkFamily, fromVersion, toVersion);
		}
		
		public ImplicitStaticLibSpec(Tuple first, String second) {
			super(first, second);
		}
		
		public boolean matchesSDK(ISymbianSDK symbianSDK) {
			String family = (String) first.get(FAMILY);
			if (!family.equalsIgnoreCase(symbianSDK.getFamily()))
				return false;
			
			Version sdkVers = symbianSDK.getSDKVersion();
			Version fromVers = (Version) first.get(FROM_VERS);
			if (sdkVers.compareTo(fromVers) < 0)
				return false;
			
			Version toVers = (Version) first.get(TO_VERS);
			if (sdkVers.compareTo(toVers) > 0)
				return false;
			
			return true;
		}
		
		public String getLibrary() {
			return second;
		}
	}
	
	private static final ImplicitStaticLibSpec[] IMPLICIT_STATIC_LIBS = {
		new ImplicitStaticLibSpec(
				ImplicitStaticLibSpec.createSDKSpec(
						ISymbianSDK.UIQ_FAMILY_ID, new Version(3,0,0), new Version(3,0,0)), 
				"QikAlloc.lib") //$NON-NLS-1$
	};

	private static final String CDT_SETTINGS_MBS_PREFS_FILE_NAME = ".settings/org.eclipse.cdt.managedbuilder.core.prefs"; //$NON-NLS-1$

	// property on project resource with default configuration id value
	private static final QualifiedName DEFAULT_CONFIG_KEY = 
		new QualifiedName("org.eclipse.cdt.managedbuilder.core", "defaultConfig"); //$NON-NLS-1$ //$NON-NLS-2$

	private SymbianProjectInfo projectInfo;
	private SymbianBuildParser parser;
	private IProject project;
	private IProgressMonitor monitor;

	private static final boolean DUMP = false;

	public ProjectUpdater() {
		super();
	}

	private void addMultiImageSources(IMakefileReference iconMakefile, List<IMultiImageSource> multiImageSources) throws CoreException {
		IPath path = project.getFullPath().append(iconMakefile.getPath());
		IStatus status = (IStatus) EpocEnginePlugin.runWithImageMakefileView(path, 
				new DefaultImageMakefileViewConfiguration(project, null, new AllNodesViewFilter()), 
				new AddSourceToIconMakefileViewRunnable(multiImageSources));
		if (!status.isOK())
			throw new CoreException(status);
	}

	private IMakefileReference createNewIconMakefile(IBldInfView view) throws CoreException {
		EpocEnginePathHelper pathHelper = new EpocEnginePathHelper(project);
		IPath makefilePath = projectInfo.getGroupFolderPath().append(DEFAULT_ICON_MK_FILE_NAME);
		makefilePath = pathHelper.convertFilesystemToWorkspace(makefilePath);
		IWorkspaceRoot root = project.getWorkspace().getRoot();
		IFile file = root.getFile(makefilePath);
		if (!file.exists()) {
			String contents = getCanonicalIconMakeFileContents(project.getName());
			file.create(new ByteArrayInputStream(contents.getBytes()), true, monitor);
		}
		IMakefileReference reference = view.createMakefileReference();
		reference.setPath(makefilePath.removeFirstSegments(1));
		reference.setMakeEngine(EMakeEngine.GNUMAKEFILE);
		List<IMakMakeReference> makMakeReferences = view.getMakMakeReferences();
		boolean hasReference = false;
		for (IMakMakeReference makMakeReference : makMakeReferences) {
			if (makMakeReference.getPath().toString().equalsIgnoreCase(reference.getPath().toString())) {
				hasReference = true;
				break;
			}
		}
		if (!hasReference)
			makMakeReferences.add(0, reference);
		
		return reference;
	}

	private IMakefileReference findIconMakefile(IMakefileReference[] makefileReferences) {
		if (makefileReferences == null)
			return null;
		
		List<IMakefileReference> loadableMakefiles = new ArrayList<IMakefileReference>();
		// look for icons in the file
		for (int i = 0; i < makefileReferences.length; i++) {
			IMakefileReference reference = makefileReferences[i];
			IPath path = project.getFullPath().append(reference.getPath());
			IStatus status = (IStatus) EpocEnginePlugin.runWithImageMakefileView(path, 
					new DefaultImageMakefileViewConfiguration(project, null, new AllNodesViewFilter()), 
					new FindIconMakefileViewRunnable());
			if (status == null)
				loadableMakefiles.add(reference);
			else if (status.isOK())
				return reference;
		}
		
		// see if a file name has the word icon in it
		for (IMakefileReference reference : loadableMakefiles) {
			IPath path = reference.getPath();
			String filename = path.lastSegment();
			if (filename.matches(ICON_FILE_NOCASE))
				return reference;
		}
		
		return null;
	}

	public String getDocumentation() {
		return 
		"<div><h4>" +  //$NON-NLS-1$
		Messages.getString("ProjectUpdater.Doc1") +  //$NON-NLS-1$
		Messages.getString("ProjectUpdater.Doc2") +  //$NON-NLS-1$
		"</h4>" + //$NON-NLS-1$
		"<p><font color=\"red\">" +  //$NON-NLS-1$
		Messages.getString("ProjectUpdater.Doc3") + //$NON-NLS-1$
		Messages.getString("ProjectUpdater.Doc4") +  //$NON-NLS-1$
		Messages.getString("ProjectUpdater.Doc4b") +  //$NON-NLS-1$
		"</font> " +  //$NON-NLS-1$
		Messages.getString("ProjectUpdater.Doc5") +  //$NON-NLS-1$
		"</p>" + //$NON-NLS-1$
		"<p>" +  //$NON-NLS-1$
		Messages.getString("ProjectUpdater.Doc6") + //$NON-NLS-1$
		Messages.getString("ProjectUpdater.Doc7") + //$NON-NLS-1$
		Messages.getString("ProjectUpdater.Doc8") +  //$NON-NLS-1$
		"</p>" //$NON-NLS-1$
		
//		+ "<p>After projects are updated any projects using the UI Designer will be checked for required "
//		+ "file updates. You will be able to preview those changes before any changes are applied.</p>"
		+ "</div>"; //$NON-NLS-1$
	}

	public String getUpdateLabel() {
		return Messages.getString("ProjectUpdater.Label"); //$NON-NLS-1$
	}

	public boolean needsUpdate(IProject project, IProgressMonitor monitor) {
		IProjectDescription description = null;
		try {
			description = project.getDescription();
		} 
		catch (CoreException e) {
		}

		if (description != null && description.hasNature(SYMBIAN_NATURE_ID)) {
			// ping the project to force MBS to notice it's out of date
			// (so we can delete the error message <i>before</i> it's
			// converted)
			ManagedBuildManager.getBuildInfo(project, true);
			return true;
		}
		return false;
	}
	
	public IStatus update(IProject project, IProgressMonitor monitor) {
		this.project = project;
		this.monitor = monitor;
		
		try {
			monitor.beginTask(MessageFormat.format(Messages.getString("ProjectUpdater.TaskName"), //$NON-NLS-1$
					new Object[] { project.getName() }), 10);
			return doUpdateProject(new SubProgressMonitor(monitor, 1));
		} finally {
			monitor.done();
		}
	}
	
	private IStatus doUpdateProject(IProgressMonitor monitor) {
		printDebugHeader();
		
		// first, refresh the project
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
		} catch (CoreException e) {
			return e.getStatus();
		}
		monitor.worked(1);
		if (monitor.isCanceled())
			return Status.CANCEL_STATUS;
		
		IStatus status = createProjectInfo();
		if (!status.isOK())
			return status;
		monitor.worked(1);
		if (monitor.isCanceled())
			return Status.CANCEL_STATUS;
		
		status = createBuildParser();
		if (!status.isOK())
			return status;
		monitor.worked(1);
		if (monitor.isCanceled())
			return Status.CANCEL_STATUS;
		
		if (DUMP) {
			printDebugProjectInfo();
			printDebugParserData();
			
			return Status.OK_STATUS;
		}
		
		IPath absBldInfPath = getBldInfPath();
		status = ensureSafeFile(absBldInfPath, monitor);
		if (!status.isOK())
			return status;
		
		IPath absMMPPath = getNewMMPPath();
		status = ensureSafeFile(absMMPPath, monitor);
		if (!status.isOK())
			return status;
		
		monitor.worked(1);
		if (monitor.isCanceled())
			return Status.CANCEL_STATUS;
		
		status = setLinkedProperty();
		if (!status.isOK())
			return status;

		monitor.worked(1);
		if (monitor.isCanceled())
			return Status.CANCEL_STATUS;

		final EpocEnginePathHelper pathHelper = new EpocEnginePathHelper(project);
		IPath bldInfPath = pathHelper.convertFilesystemToWorkspace(absBldInfPath);

		// make path relative to workspace
		IPath mmpPath = pathHelper.convertFilesystemToWorkspace(absMMPPath);

		// make project relative
		status = (IStatus) EpocEnginePlugin.runWithBldInfView(bldInfPath, 
				new DefaultViewConfiguration(project, null, new AllNodesViewFilter()), 
				new UpdateBldInfViewRunnable(mmpPath.removeFirstSegments(1)));
		if (!status.isOK())
			return status;

		monitor.worked(1);
		if (monitor.isCanceled())
			return Status.CANCEL_STATUS;

		status = (IStatus) EpocEnginePlugin.runWithMMPView(mmpPath, 
				new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
				new UpdateMMPViewRunnable());
		if (!status.isOK())
			return status;

		monitor.worked(1);
		if (monitor.isCanceled())
			return Status.CANCEL_STATUS;

		// this is the point of no return
		status = removeUnusedProjectNaturesAndBuildInfo();
		if (!status.isOK())
			return status;

		monitor.worked(1);
		if (monitor.isCanceled())
			return Status.CANCEL_STATUS;

		// Remove error markers about missing MBS info. 
		// Don't stop here even if it returns a bad status or is canceled,
		// otherwise the project is broken.
		removeMBSErrorMarkers();
		monitor.worked(1);

		status = convertToCarbideProject(bldInfPath, mmpPath, monitor);
		if (!status.isOK())
			return status;
		
		monitor.worked(1);
		return status;
	}
	
	private IStatus createLinkedProjectStatus() {
		String fmt = Messages.getString("ProjectUpdater.linkedProjectUpdateErr"); //$NON-NLS-1$
		String msg = MessageFormat.format(fmt, new Object[] {project.getName()});
		return new Status(IStatus.ERROR, ProjectCorePlugin.PLUGIN_ID, 0, msg, null);
	}

	private IStatus setLinkedProperty() {
		// check if the group folder is linked, and if so, set the persistent property
		IPath groupFolderPath = projectInfo.getGroupFolderPath();
		IPath projectPath = project.getLocation();
		if (!projectPath.isPrefixOf(groupFolderPath)) {
			IPath rootDirectory = groupFolderPath.removeLastSegments(1);
		   	try {
				project.setPersistentProperty(CarbideBuilderPlugin.LINKED_PROJECT_ROOT_DIRECTORY, rootDirectory.toOSString());
			} catch (CoreException e) {
				return e.getStatus();
			}
		}
		
		return Status.OK_STATUS;
	}

	private IPath convertToMMPPath(IPath absoluteOrProjectPath, MMPViewPathHelper pathHelper, EMMPPathContext context) {
		IPath wsPath = FileUtils.convertToWorkspacePath(absoluteOrProjectPath, true);
		IPath path = wsPath == null ? absoluteOrProjectPath : wsPath.removeFirstSegments(1);
		try {
			return pathHelper.convertProjectOrFullPathToMMP(context, path);
		} catch (InvalidDriveInMMPPathException e) {
			return e.getPathNoDevice(); 
		}
	}

	private IStatus convertToCarbideProject(IPath bldInfPath, IPath mmpPath, IProgressMonitor monitor) {
		String projectRelativeBldInfPathStr = bldInfPath.removeFirstSegments(1).toString();
		// buildContexts will have the default context first to make it default
		List<ISymbianBuildContext> buildContexts = new ArrayList<ISymbianBuildContext>(parser.getBuildContexts());

		try {
			// create the c project description
			ICProjectDescription projDes = CCorePlugin.getDefault().createProjectDescription(project, false, true);

			// add the natures
			IProgressMonitor nullMonitor = new NullProgressMonitor();
			CProjectNature.addCNature(project, nullMonitor);
			CCorePlugin.getDefault().convertProjectFromCtoCC(project, nullMonitor);

			// add our carbide builder nature
			CarbideBuilderPlugin.addBuildNature(project);
			
			// and now run it through our project creation stuff
			ProjectCorePluginUtility.setupBuilderSettings(projDes, projectRelativeBldInfPathStr,
					buildContexts, Collections.EMPTY_LIST, null);

			// rename the .cdtproject file so the user knows it's no longer used
			File oldCdtProjectFile = project.getLocation().append(".cdtproject").toFile(); //$NON-NLS-1$
			if (oldCdtProjectFile.exists()) {
				oldCdtProjectFile.renameTo(project.getLocation().append(".cdtproject_obsolete").toFile()); //$NON-NLS-1$
			}
		} catch (CoreException e) {
			return e.getStatus();
		}

		return Status.OK_STATUS;
	}
	
	private IStatus genericErrorStatus(Throwable t) {
		String message = 
			MessageFormat.format(Messages.getString("ProjectUpdater.NotUpdateProjectError"), new Object[] { project.getName() }); //$NON-NLS-1$
		return Logging.newSimpleStatus(ProjectCorePlugin.getDefault(), IStatus.ERROR, message, t);
	}

	private IStatus createBuildParser() {
		String defaultConfigKey = null;
		try {
			defaultConfigKey = project.getPersistentProperty(DEFAULT_CONFIG_KEY);
		} catch (CoreException e) {
			// if we can't find this, we default to the first config
		}
		parser = SymbianBuildParser.createParser(project, defaultConfigKey);
		if (parser == null)
			return genericErrorStatus(new Exception(Messages.getString("ProjectUpdater.NotCreateBuildParserError"))); //$NON-NLS-1$

		return Status.OK_STATUS;
	}

	private IStatus createProjectInfo() {
		try {
			projectInfo = SymbianProjectInfo.createProjectInfo(project);
		} 
		catch (CoreException e) {
			return e.getStatus();
		}
		
		return Status.OK_STATUS;
	}

	private IPath getNewMMPPath() {
		return projectInfo.getGroupFolderPath().append(project.getName().concat(".mmp")); //$NON-NLS-1$
	}

	private IPath getBldInfPath() {
		IPath bldInfPath = projectInfo.getBldInfPath();
		if (bldInfPath == null) {
			bldInfPath = projectInfo.getGroupFolderPath();
			bldInfPath = bldInfPath.append("bld.inf"); //$NON-NLS-1$
		}
		return bldInfPath;
	}
	
	private IStatus ensureSafeFile(IPath absolutePath, IProgressMonitor monitor) {
		// either save off existing, or create new file
		IPath wsPath = FileUtils.convertToWorkspacePath(absolutePath, true);
		if (wsPath == null) {
			// not in the workspace
			return createLinkedProjectStatus();
		}
		IWorkspaceRoot wsRoot = project.getWorkspace().getRoot();
		IFile file = wsRoot.getFile(wsPath);
		try {
			if (file.exists()) {
				IPath saveFilePath = file.getFullPath().addFileExtension("SAVE"); //$NON-NLS-1$
				IResource saveFile = wsRoot.getFile(saveFilePath);
				if (saveFile.exists())
					saveFile.delete(true, monitor);
				file.copy(saveFilePath, true, monitor);
			}
			else
				file.create(new ByteArrayInputStream(new byte[0]), true, monitor);
		} 
		catch (CoreException e) {
			return e.getStatus();
		}
		
		return Status.OK_STATUS;
	}
	
	/**
	 * Rename the given file so the user sees it's outdated.
	 * Not the same as #ensureSafeFile() because we're not overwriting it.
	 * @param path full path
	 */
	private void renameAway(IPath path) throws CoreException {
		IPath wsPath = FileUtils.convertToWorkspacePath(path);
		if (wsPath == null) {
			File file = path.toFile();
			file.renameTo(new File(file.getParentFile(), file.getName() + ".SAVE")); //$NON-NLS-1$
			return;
		}
		IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(wsPath);
		if (resource != null) {
			IPath newName = wsPath.addFileExtension("SAVE"); //$NON-NLS-1$
			resource.move(newName.makeAbsolute(), false, new NullProgressMonitor());
		}
	}

	private IStatus updateMMPView(IMMPView view) {
		ISymbianBuildContext context = parser.getDefaultBuildContext();
		ISymbianSDK symbianSDK = context != null ? context.getSDK() : null;
		String epocRoot = symbianSDK != null ? symbianSDK.getEPOCROOT() : null;
		MMPViewPathHelper pathHelper = new MMPViewPathHelper(project, epocRoot);

		IStatus status = updateAIFDEFs(view, pathHelper);
		if (!status.isOK())
			return status;
		
		status = updateMBMDEFs(view, pathHelper);
		if (!status.isOK())
			return status;
		
		updateWithViewAPI(view);
		updateSingleArgumentSettings(view);
		updateListArgumentSettings(view);
		updateMMPViewPaths(view, pathHelper);
		
		return Status.OK_STATUS;
	}


	class GenericImageConversionPathResolver implements IPathResolver {

		public GenericImageConversionPathResolver() {
		}
		public IPath resolvePath(String pathString) {
			IPath path = FileUtils.createPossiblyRelativePath(pathString);
			if (!path.isAbsolute()) {
				return FileUtils.getCanonicalWorkspacePath(new Path(project.getName()).append(path)).removeFirstSegments(1);
			}
			IPath wsPath = FileUtils.convertToWorkspacePath(path, true);
			IPath resolvedPath;
			if (wsPath == null) {
				try {
					resolvedPath = new Path(path.toFile().getCanonicalPath());
				} catch (IOException e) {
					resolvedPath = path;
				}
			} else {
				resolvedPath = wsPath.removeFirstSegments(1);
			}
			return resolvedPath;
		}
	}

	class MMPImageConversionPathResolver extends GenericImageConversionPathResolver {

		private final MMPViewPathHelper pathHelper;
		private final EMMPPathContext context;
		public MMPImageConversionPathResolver(MMPViewPathHelper pathHelper, EMMPPathContext context) {
			this.pathHelper = pathHelper;
			this.context = context;
			
		}
		public IPath resolvePath(String pathString) {
			IPath path = FileUtils.createPossiblyRelativePath(pathString).removeTrailingSeparator();
			
			IPath resolvedPath = convertToMMPPath(path, pathHelper, context);
			if (resolvedPath != null) {
				if (resolvedPath.isAbsolute()) {
					// This could be the hallmark of a bug in *.aifdef where the full
					// path to the project is embedded, thus limiting portability.
					// If this file came from CVS, it's likely pointing to a bogus location.
					// See if the file actually exists in the project by trimming it down
					// until some suffix is found in the project.
					
					try {
						resolvedPath = new Path(resolvedPath.toFile().getCanonicalPath());
					} catch (IOException e) {
					}
					
					IPath tmpPath = resolvedPath.setDevice(null).makeAbsolute();
					IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
					while (tmpPath.segmentCount() >= 1) {
						// This canonicalization is vital, since the AIF is miscapitalized
						// in some templates...
						tmpPath = FileUtils.getCanonicalWorkspacePath(tmpPath);
						if (root.findMember(tmpPath) != null) {
							// remove project
							resolvedPath = tmpPath.removeFirstSegments(1);
							break;
						}
						tmpPath = tmpPath.removeFirstSegments(1);
					}
				} else {
					resolvedPath = FileUtils.getCanonicalWorkspacePath(
							new Path(project.getName()).append(resolvedPath));
					resolvedPath = resolvedPath.removeFirstSegments(1);
				}
			} else {
				resolvedPath = path;
			}
			
			return resolvedPath;
		}
		
	}

	private IStatus updateAIFDEFs(IMMPView view, MMPViewPathHelper pathHelper) {
		Collection<IPath> aifdefFiles = projectInfo.getAifdefFiles();
		if (aifdefFiles == null || aifdefFiles.isEmpty())
			return Status.OK_STATUS;
		
		IAifDefFileConverter converter = LegacyImageFileConverterFactory.createAifdefConverter();
		String targetPathString = parser.getTargetPath();
		IPath targetPath = null;
		if (targetPathString != null)
			targetPath = new Path(targetPathString);
		
		List<IMMPAIFInfo> aifInfos = new ArrayList<IMMPAIFInfo>();
		MMPImageConversionPathResolver resolver = new MMPImageConversionPathResolver(
				pathHelper,
				EMMPPathContext.AIF_SOURCE);

		for (IPath path : aifdefFiles) {
			File file = path.toFile();
			if (file.exists()) {
				try {
					String contents = new String(FileUtils.readFileContents(file, null));
					IMMPAIFInfo info = converter.convert(
							targetPath, 
							path.lastSegment(), 
							contents, 
							resolver);
					aifInfos.add(info);
					renameAway(path);
				} catch (CoreException e) {
					return e.getStatus();
				}
			}
		}
		
		if (!aifInfos.isEmpty()) // there is never more than one, so always set it
			view.setAifs(aifInfos);
		
		return Status.OK_STATUS;
	}
	
	private IStatus updateMBMDEFs(IMMPView view, MMPViewPathHelper pathHelper) {
		Collection<IPath> mbmdefFiles = projectInfo.getMbmdefFiles();
		if (mbmdefFiles == null || mbmdefFiles.isEmpty())
			return Status.OK_STATUS;
		
		IMbmMifDefFileConverter converter = LegacyImageFileConverterFactory.createMbmdefConverter();
		IPath targetPath = new Path(parser.getMBMTargetPath());
		MMPImageConversionPathResolver resolver = new MMPImageConversionPathResolver(
				pathHelper, EMMPPathContext.START_BITMAP_SOURCE);
		
		List<IMultiImageSource> multiImageSources = view.getMultiImageSources();
		for (IPath path : mbmdefFiles) {
			File file = path.toFile();
			if (file.exists()) {
				try {
					String contents = new String(FileUtils.readFileContents(file, null));
					IMultiImageSource source = converter.convert(
							targetPath, 
							path.lastSegment(), 
							contents,
							resolver);
					for (IMultiImageSource imageSource : multiImageSources) {
						if (imageSource.getTargetFilePath().toString().equalsIgnoreCase(source.getTargetFilePath().toString())) {
							multiImageSources.remove(imageSource);
							break;
						}
					}
					multiImageSources.add(source);
					renameAway(path);
				} catch (CoreException e) {
					return e.getStatus();
				}
			}
		}
		
		return Status.OK_STATUS;
	}
	
	private IStatus updateMIFDEFs(IBldInfView view) {
		Collection<IPath> mifdefFiles = projectInfo.getMifdefFiles();
		if (mifdefFiles == null || mifdefFiles.isEmpty())
			return Status.OK_STATUS;
		
		IMakefileReference iconMakefile = findIconMakefile(view.getAllMakefileReferences());
		if (iconMakefile == null) {
			try {
				iconMakefile = createNewIconMakefile(view);
			} catch (CoreException e) {
				return e.getStatus();
			}
		} else {
			IPath path = project.getLocation().append(iconMakefile.getPath());
			String device = path.getDevice();
			try {
				path = new Path(path.toFile().getCanonicalPath()).setDevice(device);
			} catch (IOException x) {
				ProjectCorePlugin.log(x);
			}
			IStatus status = ensureSafeFile(path, monitor);
			if (!status.isOK()) {
				return status;
			}
		}
		
		IMbmMifDefFileConverter converter = LegacyImageFileConverterFactory.createMifdefConverter();
		IPath targetPath = new Path(parser.getMIFTargetPath());
		GenericImageConversionPathResolver resolver = new GenericImageConversionPathResolver();
		
		List<IMultiImageSource> multiImageSources = new ArrayList<IMultiImageSource>();
		for (IPath path : mifdefFiles) {
			File file = path.toFile();
			if (file.exists()) {
				try {
					String contents = new String(FileUtils.readFileContents(file, null));
					IMultiImageSource source = converter.convert(
							targetPath, 
							path.lastSegment(), 
							contents,
							resolver);
					multiImageSources.add(source);
					renameAway(path);
				} catch (CoreException e) {
					return e.getStatus();
				}
			}
		}
	
		try {
			addMultiImageSources(iconMakefile, multiImageSources);
		} catch (CoreException e) {
			return e.getStatus();
		}
	
		return Status.OK_STATUS;
	}

	private void updatePlatforms(IBldInfView view) {
		boolean makeDefault = false;
		List<String> platformsToAdd = new ArrayList<String>();
		Collection<ISymbianBuildContext> buildContexts = parser.getBuildContexts();
		for (ISymbianBuildContext context : buildContexts) {
			String platform = context.getPlatformString();
			if (!platformsToAdd.contains(platform)) {
				if (!makeDefault && isIncompatiblePlatform(platform)) {
					makeDefault = true;
				}
				platformsToAdd.add(platform);
			}
		}
		
		List<String> platforms = view.getPlatforms();
		platforms.clear();
		if (makeDefault) {
			platforms.add(DEFAULT_PRJ_PLATFORM);
			platforms.add(COMMENT_DELIM);
		}
		platforms.addAll(platformsToAdd);
		if (makeDefault) {
			String[] atoms = DEFAULT_PRJ_PLATFORM_COMMENT.split("\\s"); //$NON-NLS-1$
			for (String atom : atoms) {
				if (atom.length() > 0)
					platforms.add(atom);
			}
		}
	}

	private boolean isIncompatiblePlatform(String platform) {
		Collection<ISymbianBuildContext> buildContexts = parser.getBuildContexts();
		for (ISymbianBuildContext context : buildContexts) {
			ISymbianSDK symbianSDK = context.getSDK();
			List<String> availablePlatforms = symbianSDK.getAvailablePlatforms();
			if (!availablePlatforms.contains(platform))
				return true;
		}
		
		return false;
	}

	private void updateWithViewAPI(IMMPView view) {
		updateUID2(view);
		updateUID3(view);
		updateLanguages(view);
		updateLibraries(view);
		updateStaticLibraries(view);
	}

	private void updateUID2(IMMPView view) {
		String uid2 = parser.getUID2();
		if (uid2 != null && !integersMatch(uid2, view.getUid2()))
			view.setUid2(uid2);
	}

	private void updateUID3(IMMPView view) {
		String uid3 = parser.getUID3();
		if (uid3 != null && !integersMatch(uid3, view.getUid3()))
			view.setUid3(uid3);
	}

	private void updateLanguages(IMMPView view) {
		final Collection languageCodeHolder[] = new Collection[1];
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				languageCodeHolder[0] = getDesignLanguages();
			}
		});
		Collection<String> languageCodes = languageCodeHolder[0];
		if (languageCodes == null) {
			languageCodes = parser.getLanguages();
		}
		if (languageCodes != null) {
			List<EMMPLanguage> languages = view.getLanguages();
			for (String code : languageCodes) {
				try {
					EMMPLanguage language = EMMPLanguage.fromCode(code);
					if (!languages.contains(language))
						languages.add(language);
				} catch (IllegalArgumentException e) {
					// ignore unknown language
				}
			}
		}
	}

	private Collection<String> getDesignLanguages() {
		Bundle bundle = Platform.getBundle(SDT_SYMBIAN_BUNDLE_ID);
		if (bundle != null) {
			Class cls = null;
			try {
				cls = bundle.loadClass(SYMBIAN_LANGUAGE_UTILS_CLASS);
			} catch (ClassNotFoundException e) {}
			if (cls != null) {
				Method method = null;
				try {
					method = cls.getMethod(GET_DESIGN_LANGAUGES_METHOD_NAME, IProject.class, boolean.class);
				} catch (SecurityException e) {
				} catch (NoSuchMethodException e) {
				}
				if (method != null) {
					Object object = null;
					try {
						object = method.invoke(null, project, true);
					} catch (IllegalArgumentException e) {
					} catch (IllegalAccessException e) {
					} catch (InvocationTargetException e) {
					}
					if (object instanceof Collection)
						return (Collection) object;
				}
			}
		}
		return null;
	}

	private void updateLibraries(IMMPView view) {
		Collection<String> parsedLibraries = parser.getLibraries();
		if (parsedLibraries != null) {
			removeImplicitLibraries(parsedLibraries);
			List<String> libraries = view.getLibraries();
			for (String library : parsedLibraries) {
				if (!containsIgnoreCase(libraries, library))
					libraries.add(library);
			}
		}
	}
	
	private void updateStaticLibraries(IMMPView view) {
		// add the implicit static libs that match the default build context
		List<String> staticLibraries = view.getStaticLibraries();
		ISymbianBuildContext context = parser.getDefaultBuildContext();
		if (context != null) {
			ISymbianSDK sdk = context.getSDK();
			for (int i = 0; i < IMPLICIT_STATIC_LIBS.length; i++) {
				ImplicitStaticLibSpec implicitStaticLibSpec = IMPLICIT_STATIC_LIBS[i];
				if (implicitStaticLibSpec.matchesSDK(sdk)) {
					String library = implicitStaticLibSpec.getLibrary();
					if (!containsIgnoreCase(staticLibraries, library))
						staticLibraries.add(library);
				}
			}
		}
	}
	
	private void removeImplicitLibraries(Collection<String> parsedLibraries) {
		ISymbianBuildContext context = parser.getDefaultBuildContext();
		ISymbianSDK sdk = null;
		if (context != null)
			sdk = context.getSDK();
		
		Collection<String> libs = new ArrayList<String>();
		libs.addAll(parsedLibraries);
		parsedLibraries.clear();
		boolean excluded;
		for (String lib : libs) {
			excluded = false;
			for (String prefix : IMPLICIT_LIB_PREFIXES) {
				if (startsWithIgnoreCase(lib, prefix)) {
					excluded = true;
					break;
				}
			}
			if (!excluded) {
				for (String implicitLib : IMPLICIT_LIBS) {
					if (lib.equalsIgnoreCase(implicitLib)) {
						excluded = true;
						break;
					}
				}
			}
			if (!excluded && sdk != null) {
				for (int i = 0; i < IMPLICIT_STATIC_LIBS.length; i++) {
					ImplicitStaticLibSpec implicitStaticLibSpec = IMPLICIT_STATIC_LIBS[i];
					if (implicitStaticLibSpec.matchesSDK(sdk)) {
						String implicitStaticLib = implicitStaticLibSpec.getLibrary();
						if (lib.equalsIgnoreCase(implicitStaticLib)) {
							excluded = true;
							break;
						}
					}
				}
			}
			if (!excluded)
				parsedLibraries.add(lib);
		}
	}
	
	private boolean startsWithIgnoreCase(String test, String prefix) {
		if (test.length() < prefix.length())
			return false;
		
		String testPrefix = test.substring(0, prefix.length());
		return testPrefix.equalsIgnoreCase(prefix);
	}

	private boolean containsIgnoreCase(Collection<String> collection, String test) {
		for (String string : collection) {
			Check.checkContract(string != null && test != null);
			if (string.equalsIgnoreCase(test))
				return true;
		}
		
		return false;
	}
	
	private boolean containsIgnoreCase(Collection<IPath> collection, IPath test) {
		for (IPath path : collection) {
			Check.checkContract(path != null && test != null);
			if (path.toString().equalsIgnoreCase(test.toString()))
				return true;
		}
		
		return false;
	}
	
	private void updateMMPViewPaths(IMMPView view, MMPViewPathHelper pathHelper) {
		updateSystemIncludes(view, pathHelper);
		updateUserIncludes(view, pathHelper);
		updateSourceFiles(view, pathHelper);
		updateRssFiles(view, pathHelper);
	}

	private void updateRssFiles(IMMPView view, MMPViewPathHelper pathHelper) {
		List<IMMPResource> resourceBlocks = view.getResourceBlocks();

		// remove dead resources of all flavors
		for (Iterator<IMMPResource> iter = resourceBlocks.iterator(); iter.hasNext(); ) {
			IMMPResource resource = iter.next();
			IPath projPath = pathHelper.convertMMPToProject(EMMPPathContext.START_RESOURCE, resource.getSource());
			if (projPath != null && project.findMember(projPath) == null) {
				iter.remove();
			}
		}
		for (Iterator<IPath> iter = new ChainedIterator<IPath>(view.getUserResources().iterator(), 
						view.getSystemResources().iterator()); iter.hasNext(); ) {
			IPath projPath = pathHelper.convertMMPToProject(EMMPPathContext.RESOURCE, iter.next());
			if (projPath != null && project.findMember(projPath) == null) {
				iter.remove();
			}
		}

		// now register the actual living resources
		Collection<IPath> rssFiles = projectInfo.getRssFiles();
		if (rssFiles != null) {
			final String RSS_REG_PATTERN = "(?i).*_reg\\.rss"; //$NON-NLS-1$
			for (IPath path : rssFiles) {
				IPath rssFile = 
					convertToMMPPath(path, pathHelper, EMMPPathContext.START_RESOURCE);
				IMMPResource existingResourceBlock = findResourceBlockInMMPView(rssFile, view);
				if (existingResourceBlock != null) {
					// ensure the header is generated -- old MMPs didn't properly mention this

					// but don't do it for *_reg.rss files, since their header isn't used
					if (!existingResourceBlock.getSource().lastSegment().matches(RSS_REG_PATTERN)) {
						existingResourceBlock.setHeaderFlags(EGeneratedHeaderFlags.Header);
					}
				} else if (!rssInMMPView(rssFile, view)) {
					IMMPResource resource = view.createMMPResource();
					resource.setSource(rssFile);
					Map<String, String> targetPathMappings = parser.getTargetPathMappings();
					String targetPathString = targetPathMappings.get(rssFile);
					if (targetPathString == null)
						targetPathString = parser.getTargetPath();
					if (targetPathString != null) {
						resource.setTargetPath(new Path(targetPathString));
					}
					resource.setHeaderFlags(EGeneratedHeaderFlags.Header);
					resourceBlocks.add(resource);
				}
			}
			// now ensure that the _reg.rss is at the end 
			// because it includes the .rsg (generated header) from the main rss file
			IMMPResource addAtEnd = null;
			for (Iterator<IMMPResource> iter = resourceBlocks.iterator(); iter.hasNext();) {
				IMMPResource resource = iter.next();
				if (resource.getSource().lastSegment().matches(RSS_REG_PATTERN)) {
					iter.remove();
					addAtEnd = resource;
					break;
				}
			}
			if (addAtEnd != null)
				resourceBlocks.add(addAtEnd);
		}
	}

	/**
	 * Search for a resource block for the given source.
	 * @param path
	 * @param view
	 * @return
	 */
	private IMMPResource findResourceBlockInMMPView(IPath path, IMMPView view) {
		List<IMMPResource> resourceBlocks = view.getResourceBlocks();
		for (IMMPResource resource : resourceBlocks) {
			IPath resourcePath = resource.getSource();
			if (path.toOSString().equalsIgnoreCase(resourcePath.toOSString()))
				return resource;
		}
		
		return null;
	}

	/**
	 * Search for other mentions of a resource source file.
	 * @param path
	 * @param view
	 * @return
	 */
	private boolean rssInMMPView(IPath path, IMMPView view) {
		List<IMMPAIFInfo> aifs = view.getAifs();
		for (IMMPAIFInfo info : aifs) {
			IPath resourcePath = info.getResource();
			if (path.toOSString().equalsIgnoreCase(resourcePath.toOSString()))
				return true;
		}
		
		List<IPath> userResources = view.getUserResources();
		for (IPath resourcePath : userResources) {
			if (path.toOSString().equalsIgnoreCase(resourcePath.toOSString()))
				return true;
		}
		
		List<IPath> systemResources = view.getSystemResources();
		for (IPath resourcePath : systemResources) {
			if (path.toOSString().equalsIgnoreCase(resourcePath.toOSString()))
				return true;
		}
		
		return false;
	}

	private boolean hasEKA2BuildConfiguration() {
		for (ISymbianBuildContext context : parser.getBuildContexts()) {
			ISymbianSDK symbianSDK = context.getSDK();
			if (symbianSDK.isEKA2()) {
				return true;
			}
		}
		return false;
	}
	
	private void updateSourceFiles(IMMPView view, MMPViewPathHelper pathHelper) {
		// remove source entries for nonexistent files, or
		// the broken entries for old MMPs that have this stranded stuff
		// in the middle:
		//
		//	SOURCEPATH ..\aif
		//	SOURCE list_icon.bmp list_icon_mask.bmp
		//	END
		//
		//	We can't really get rid of the END yet...
		//
		for (Iterator<IPath> iter = view.getSources().iterator(); iter.hasNext(); ) {
			IPath projPath = pathHelper.convertMMPToProject(EMMPPathContext.SOURCE, iter.next());
			if (projPath != null) {
				if (project.findMember(projPath) == null) {
					iter.remove();
				} else {
					String ext = projPath.getFileExtension();
					if (ext != null && ext.equalsIgnoreCase("bmp")) { //$NON-NLS-1$
						iter.remove();
					}
				}
			}
		}
		
		// and add the real actual sources
		Collection<IPath> sourceFiles = projectInfo.getSourceFiles();
		if (sourceFiles != null) {
			List<IPath> sources = view.getSources();
			for (IPath path : sourceFiles) {
				IPath sourceFile = 
					convertToMMPPath(path, pathHelper, EMMPPathContext.SOURCE);
				if (!containsIgnoreCase(sources, sourceFile))
					sources.add(sourceFile);
			}
		}
	}

	private void updateUserIncludes(IMMPView view, MMPViewPathHelper pathHelper) {
		Collection<IPath> userIncludePaths = projectInfo.getUserIncludePaths();
		if (userIncludePaths != null) {
			List<IPath> userIncludes = view.getUserIncludes();
			for (IPath path : userIncludePaths) {
				IPath includePath = 
					convertToMMPPath(path, pathHelper, EMMPPathContext.USERINCLUDE);
				if (!containsIgnoreCase(userIncludes, includePath))
					userIncludes.add(includePath);
			}
		}
	}

	private void updateSystemIncludes(IMMPView view, MMPViewPathHelper pathHelper) {
		Collection<String> systemIncludeStrings = parser.getSystemIncludePaths();
		if (systemIncludeStrings == null)
			systemIncludeStrings = new ArrayList<String>();
		systemIncludeStrings.addAll(getDefaultSystemIncludes());
		List<IPath> systemIncludeList = view.getSystemIncludes();
		for (String includeString : systemIncludeStrings) {
			IPath includePath = 
				convertToMMPPath(new Path(includeString), pathHelper, EMMPPathContext.SYSTEMINCLUDE);
			if (!containsIgnoreCase(systemIncludeList, includePath))
				systemIncludeList.add(includePath);
		}
	}
	
	private Collection<String> getDefaultSystemIncludes() {
		// add . and \epoc32\include in case they don't exist
		Collection<String> defaultSystemIncludes = new ArrayList<String>();
		defaultSystemIncludes.add(CUR_DIR);
		defaultSystemIncludes.add(EPOC32_INC);
		
		return defaultSystemIncludes;
	}

	private void updateListArgumentSettings(IMMPView view) {
		Map<EMMPStatement, List<String>> listArgumentSettings = view.getListArgumentSettings();
		
		if (hasEKA2BuildConfiguration()) {
			updateCapabilities(view);
		}
		
		Collection<String> macros = parser.getMacros();
		if (macros != null) {
			List<String> macroList = listArgumentSettings.get(EMMPStatement.MACRO);
			for (String macro : macros) {
				if (!macroList.contains(macro))
					macroList.add(macro);
			}
		}
	}

	/**
	 * @param view
	 */
	private void updateCapabilities(IMMPView view) {
		Map<EMMPStatement, List<String>> listArgumentSettings = view.getListArgumentSettings();

		String capabilitiesString = parser.getCapability();
		boolean noCapabilities = true;
		List<String> capabilityList = listArgumentSettings.get(EMMPStatement.CAPABILITY);
		
		// remove NONE for now and add it back later if the list is really empty
		if (capabilityList.size() == 1 && 
				capabilityList.get(0).equalsIgnoreCase("NONE")) { //$NON-NLS-1$
			capabilityList.clear();
		}

		if (capabilitiesString != null) {
			
			String[] capabilities = capabilitiesString.split("\\s"); //$NON-NLS-1$
			for (int i = 0; i < capabilities.length; i++) {
				String capability = capabilities[i];
				boolean found = false;
				for (String string : capabilityList) {
					if (string.equalsIgnoreCase(capability)) {
						found = true;
						break;
					}
						
				}
				if (!found) {
					capabilityList.add(capability);
				}
				noCapabilities = false;
			}
		}
		
		if (noCapabilities) {
			capabilityList.add("NONE"); //$NON-NLS-1$
		}
	}

	private void updateSingleArgumentSettings(IMMPView view) {
		Map<EMMPStatement, String> settings = view.getSingleArgumentSettings();
		
		String target = parser.getTarget();
		if (target != null)
			updateFilenameSetting(settings, EMMPStatement.TARGET, target);

		String targetType = parser.getTargetType();
		if (targetType != null) {
			String oldTargetType = settings.get(EMMPStatement.TARGETTYPE);
			if (oldTargetType == null)
				settings.put(EMMPStatement.TARGETTYPE, targetType);
		}

		String stack = parser.getStack();
		if (stack != null)
			updateIntegerSetting(settings, EMMPStatement.EPOCSTACKSIZE, stack);

		if (hasEKA2BuildConfiguration()) {
			String vendorId = parser.getVendorId();
			if (vendorId != null)
				updateIntegerSetting(settings, EMMPStatement.VENDORID, vendorId);
			
			String secureId = parser.getSecureId();
			if (secureId != null)
				updateIntegerSetting(settings, EMMPStatement.SECUREID, secureId);
		}
	}

	/**
	 * Tell if the strings, parsed as integers, have the same value.
	 * @param first integer string or null
	 * @param second integer string or null
	 * @return
	 */
	private boolean integersMatch(String first, String second) {
		if (first == null || second == null)
			return false;
		int radix1 = 10;
		if (first.toLowerCase().startsWith("0x")) { //$NON-NLS-1$
			first = first.substring(2);
			radix1 = 16;
		}
		int radix2 = 10;
		if (second.toLowerCase().startsWith("0x")) { //$NON-NLS-1$
			second = second.substring(2);
			radix2 = 16;
		}
		try {
			// parse as long since UIDs are unsigned
			return Long.parseLong(first, radix1) == Long.parseLong(second, radix2);
		} catch (NumberFormatException e) {
			// whatever that garbage was, consider it different
			return false;
		}
	}
	
	/**
	 * Update an integer setting, not touching it unless it didn't
	 * exist or its value actually changed (integer-wise).  Avoids
	 * false changes.
	 */
	private void updateIntegerSetting(Map<EMMPStatement, String> settings, EMMPStatement stmtType, String newValue) {
		String value = settings.get(stmtType);
		if (value != null) {
			if (integersMatch(newValue, value))
				return;
		}
		settings.put(stmtType, newValue);
	}

	/**
	 * Update a filename setting, canonicalizing it (sort of)
	 * and not updating it unless it really changed.
	 */
	private void updateFilenameSetting(Map<EMMPStatement, String> settings, EMMPStatement stmtType, String newValue) {
		String value = settings.get(stmtType);
		if (value != null) {
			value = new Path(value).toOSString();
			newValue = new Path(newValue).toOSString();
			if (value.equalsIgnoreCase(newValue))
				return;
		}
		settings.put(stmtType, newValue);
	}

	private IStatus removeUnusedProjectNaturesAndBuildInfo() {
		try {
			// remove project natures
			IProjectDescription description = project.getDescription();
			List<String> natureIds = new ArrayList<String>(Arrays.asList(description.getNatureIds()));
			natureIds.remove(SYMBIAN_NATURE_ID);
			natureIds.remove(ManagedCProjectNature.MNG_NATURE_ID);
			description.setNatureIds((String[]) natureIds.toArray(new String[natureIds.size()]));
			project.setDescription(description, monitor);

			// move off .cdtbuild, which will trigger MBS 
			IFile file = project.getFile(ManagedBuildManager.SETTINGS_FILE_NAME);
			if (file.exists()) {
				renameAway(file.getLocation());
			} 
			
			// move off MBS prefs too
			file = project.getFile(CDT_SETTINGS_MBS_PREFS_FILE_NAME); //$NON-NLS-1$
			if (file.exists()) {
				renameAway(file.getLocation());
			} 
		} catch (CoreException e) {
			return e.getStatus();
		} catch (Exception e) {
			return Logging.newStatus(ProjectCorePlugin.getDefault(), e);
		}
		
		return Status.OK_STATUS;
	}

	/** 
	 * Remove markers related the Managed Build Info missing,
	 * since it will no longer matter after converting.
	 * @return
	 */
	private IStatus removeMBSErrorMarkers() {
		try {
			project.deleteMarkers(ICModelMarker.C_MODEL_PROBLEM_MARKER, true, IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
			return e.getStatus();
		}
		
		return Status.OK_STATUS;
		
	}
	
	private void printDebugHeader() {
		if (!DUMP )
			return;
		System.out.println("========================================="); //$NON-NLS-1$
		System.out.println("Update: "+project.getName()); //$NON-NLS-1$
	}

	private void printDebugProjectInfo() {
		if (!DUMP )
			return;
		IPath bldInfPath = projectInfo.getBldInfPath();
		System.out.println("bld.inf path="+bldInfPath); //$NON-NLS-1$
		Collection<IPath> userIncludePaths = projectInfo.getUserIncludePaths();
		System.out.println("UserIncludePaths="+userIncludePaths); //$NON-NLS-1$
		Collection<IPath> sourceFiles = projectInfo.getSourceFiles();
		System.out.println("SourceFiles="+sourceFiles); //$NON-NLS-1$
		Collection<IPath> rssFiles = projectInfo.getRssFiles();
		System.out.println("RssFiles="+rssFiles); //$NON-NLS-1$
		Collection<IPath> aifdefFiles = projectInfo.getAifdefFiles();
		System.out.println("AifdefFiles="+aifdefFiles); //$NON-NLS-1$
		Collection<IPath> mbmdefFiles = projectInfo.getMbmdefFiles();
		System.out.println("MbmdefFiles="+mbmdefFiles); //$NON-NLS-1$
		Collection<IPath> mifdefFiles = projectInfo.getMifdefFiles();
		System.out.println("MifdefFiles="+mifdefFiles); //$NON-NLS-1$
	}

	private void printDebugParserData() {
		if (!DUMP )
			return;
		String target = parser.getTarget();
		System.out.println("target="+target); //$NON-NLS-1$
		String targetType = parser.getTargetType();
		System.out.println("targetType="+targetType); //$NON-NLS-1$
		String uid2 = parser.getUID2();
		System.out.println("uid2="+uid2); //$NON-NLS-1$
		String uid3 = parser.getUID3();
		System.out.println("uid3="+uid3); //$NON-NLS-1$
		String vendorId = parser.getVendorId();
		System.out.println("vendorId="+vendorId); //$NON-NLS-1$
		String secureId = parser.getSecureId();
		System.out.println("secureId="+secureId); //$NON-NLS-1$
		String capability = parser.getCapability();
		System.out.println("capability="+capability); //$NON-NLS-1$
		String stack = parser.getStack();
		System.out.println("stack="+stack); //$NON-NLS-1$
		String targetPath = parser.getTargetPath();
		System.out.println("targetPath="+targetPath); //$NON-NLS-1$
		String mbmTargetPath = parser.getMBMTargetPath();
		System.out.println("mbmTargetPath="+mbmTargetPath); //$NON-NLS-1$
		Collection<ISymbianBuildContext> buildContexts = parser.getBuildContexts();
		System.out.println("build contexts="+buildContexts); //$NON-NLS-1$
		Collection<String> systemIncludes = parser.getSystemIncludePaths();
		System.out.println("system include paths="+systemIncludes); //$NON-NLS-1$
		Collection<String> languages = parser.getLanguages();
		System.out.println("languages="+languages); //$NON-NLS-1$
		Collection<String> libraries = parser.getLibraries();
		System.out.println("libraries="+libraries); //$NON-NLS-1$
		Collection<String> macros = parser.getMacros();
		System.out.println("macros="+macros); //$NON-NLS-1$
		Map<String, String> targetPathMappings = parser.getTargetPathMappings();
		System.out.println("targetPathMappings="+targetPathMappings); //$NON-NLS-1$
	}

	private String getCanonicalIconMakeFileContents(String projectName) {
		final String contents = "# ============================================================================\r\n" +  //$NON-NLS-1$
				"#  Name     : Icons_aif_scalable_dc.mk\r\n" +  //$NON-NLS-1$
				"#  Part of  : ${projectName}\r\n" +  //$NON-NLS-1$
				"#\r\n" +  //$NON-NLS-1$
				"#  Description:\r\n" +  //$NON-NLS-1$
				"# \r\n" +  //$NON-NLS-1$
				"# ============================================================================\r\n" +  //$NON-NLS-1$
				"\r\n" +  //$NON-NLS-1$
				"\r\n" +  //$NON-NLS-1$
				"ifeq (WINS,$(findstring WINS, $(PLATFORM)))\r\n" +  //$NON-NLS-1$
				"ZDIR=$(EPOCROOT)epoc32\\release\\$(PLATFORM)\\$(CFG)\\Z\r\n" +  //$NON-NLS-1$
				"else\r\n" +  //$NON-NLS-1$
				"ZDIR=$(EPOCROOT)epoc32\\data\\z\r\n" +  //$NON-NLS-1$
				"endif\r\n" +  //$NON-NLS-1$
				"\r\n" +  //$NON-NLS-1$
				"\r\n" +  //$NON-NLS-1$
				"TARGETDIR=$(ZDIR)\\resource\\apps\r\n" + //$NON-NLS-1$
				"\r\n" +  //$NON-NLS-1$
				"ICONDIR=..\\gfx\r\n" +  //$NON-NLS-1$
				"\r\n" +  //$NON-NLS-1$
				"do_nothing :\r\n" +  //$NON-NLS-1$
				"\t@rem do_nothing\r\n" +  //$NON-NLS-1$
				"\r\n" +  //$NON-NLS-1$
				"MAKMAKE : do_nothing\r\n" +  //$NON-NLS-1$
				"\r\n" +  //$NON-NLS-1$
				"BLD : do_nothing\r\n" +  //$NON-NLS-1$
				"\r\n" +  //$NON-NLS-1$
				"CLEAN : do_nothing\r\n" +  //$NON-NLS-1$
				"\r\n" +  //$NON-NLS-1$
				"LIB : do_nothing\r\n" +  //$NON-NLS-1$
				"\r\n" +  //$NON-NLS-1$
				"CLEANLIB : do_nothing\r\n" +  //$NON-NLS-1$
				"\r\n" +  //$NON-NLS-1$
				"# ----------------------------------------------------------------------------\r\n" +  //$NON-NLS-1$
				"# TODO: Configure these.\r\n" +  //$NON-NLS-1$
				"#\r\n" +  //$NON-NLS-1$
				"# NOTE 1: DO NOT DEFINE MASK FILE NAMES! They are included automatically by\r\n" +  //$NON-NLS-1$
				"# MifConv if the mask depth is defined.\r\n" +  //$NON-NLS-1$
				"#\r\n" +  //$NON-NLS-1$
				"# NOTE 2: Usually, source paths should not be included in the bitmap\r\n" +  //$NON-NLS-1$
				"# definitions. MifConv searches for the icons in all icon directories in a\r\n" +  //$NON-NLS-1$
				"# predefined order, which is currently \\s60\\icons, \\s60\\bitmaps2.\r\n" +  //$NON-NLS-1$
				"# The directory \\s60\\icons is included in the search only if the feature flag\r\n" +  //$NON-NLS-1$
				"# __SCALABLE_ICONS is defined.\r\n" +  //$NON-NLS-1$
				"# ----------------------------------------------------------------------------\r\n" +  //$NON-NLS-1$
				"# NOTE: if you have JUSTINTIME enabled for your S60 3rd FP1 or newer SDK\r\n"+  //$NON-NLS-1$
				"# and mifconv crashes, consider adding \"/X\" to the command line.\r\n"+  //$NON-NLS-1$
				"# See <http://forum.nokia.com/document/Forum_Nokia_Technical_Library_v1_35/contents/FNTL/Build_process_fails_at_mif_file_creation_in_S60_3rd_Ed_FP1_SDK.htm>\r\n"+  //$NON-NLS-1$
				"# ----------------------------------------------------------------------------\r\n" +  //$NON-NLS-1$
				"\r\n" +  //$NON-NLS-1$
				"RESOURCE :	\r\n" + //$NON-NLS-1$
				"\r\n" + //$NON-NLS-1$
				"FREEZE : do_nothing\r\n" +  //$NON-NLS-1$
				"\r\n" +  //$NON-NLS-1$
				"SAVESPACE : do_nothing\r\n" +  //$NON-NLS-1$
				"\r\n" +  //$NON-NLS-1$
				"RELEASABLES :\r\n" +  //$NON-NLS-1$
				"\t@echo \r\n" +  //$NON-NLS-1$
				"\r\n" +  //$NON-NLS-1$
				"FINAL : do_nothing\r\n" +  //$NON-NLS-1$
				"\r\n" +  //$NON-NLS-1$
				""; //$NON-NLS-1$
		VariableSubstitutionEngine engine = new VariableSubstitutionEngine(null, null);
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("projectName", projectName); //$NON-NLS-1$
		engine.setVariableToken('{');
		return engine.substitute(variables, contents);
	}
}
