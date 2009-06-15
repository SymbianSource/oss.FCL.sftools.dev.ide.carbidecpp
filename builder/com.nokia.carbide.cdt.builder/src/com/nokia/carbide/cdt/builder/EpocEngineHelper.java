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
package com.nokia.carbide.cdt.builder;

import com.nokia.carbide.cdt.builder.builder.CarbideCPPBuilder;
import com.nokia.carbide.cdt.builder.project.*;
import com.nokia.carbide.cpp.epoc.engine.*;
import com.nokia.carbide.cpp.epoc.engine.image.*;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.*;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExtension;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileData;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.*;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AllNodesViewFilter;
import com.nokia.carbide.cpp.internal.api.sdk.SymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EpocEngineHelper {

	/**
	 * Get the file system path to a project-relative path.  Uses the view's
	 * parser configuration to resolve the project location, thus can
	 * work even in views created outside the workspace.  This tries to correct
	 * any case discrepancies between the file system and the model.
	 * <p>
	 * <b>Don't use this for MMP IPath resolution -- use 
	 * {@link MMPViewPathHelper} instead!</b>
	 * @param data
	 * @param relativePath
	 * @return path, never null
	 */
	private static IPath getFullPath(IBldInfData data, IPath relativePath) {
		String absolutePath = data.getProjectPath().addTrailingSeparator().append(relativePath).toOSString();
		try {
			absolutePath = new File(absolutePath).getCanonicalPath();
		} catch (IOException e) {
		}
		return new Path(absolutePath);
	}

	/**
	 * Return list of filesystem paths to all MMPs and makefiles referenced by the given
	 * bld.inf full path.  This function differentiates between PRJ_TESTMMPFILES and PRJ_MMPFILES.
	 * Note that this does not include PRJ_EXTENSIONS or PRJ_TESTEXTENSIONS as they are a special
	 * case.  See {@link #getExtensions(IPath, List, List, List, IProgressMonitor)}.
	 * @param bldInfFilePath - The IPath to the bld.inf file that is to be preprocessed.
	 * @param buildConfigs - List of build configuration to parse for.
	 * @param normalMakMakePaths - The list of PRJ_MMPFILES IPath objects for the bld.inf
	 * @param testMakMakePaths - The list of PRJ_TESTMMPFILES IPath objects for the bld.inf.  Those
	 * with the "ignore" attribute are not returned.
	 * @param monitor
	 */
	public static void getMakMakeFiles(final IPath bldInfFilePath, List<ISymbianBuildContext> buildConfigs, 
		List<IPath> normalMakMakePaths, List<IPath> testMakMakePaths, IProgressMonitor monitor) {
		
		// get a bld.inf view for each build target.  take the union of all mmp and make files for each view
		// of the bld.inf file.
		final Set<IPath> normalFiles = new LinkedHashSet<IPath>();
		final Set<IPath> testFiles = new LinkedHashSet<IPath>();
		
		monitor.beginTask("Scanning bld.inf for mmp and make files", buildConfigs.size());

		for (final ISymbianBuildContext context : buildConfigs) {
			EpocEnginePlugin.runWithBldInfData(bldInfFilePath, 
					new DefaultViewConfiguration(context, bldInfFilePath, new AcceptedNodesViewFilter()), 
					new BldInfDataRunnableAdapter() {
						public Object run(IBldInfData data) {
							for (IMakMakeReference ref : data.getMakMakeReferences()) {
								normalFiles.add(getFullPath(data, ref.getPath()));
							}
							for (IMakMakeReference ref : data.getTestMakMakeReferences()) {
								boolean ignore = false;
								for (String att : ref.getAttributes()) {
									if (att.equalsIgnoreCase("ignore")) { //$NON-NLS-1$
										ignore = true;
										break;
									}
								}
								if (!ignore) {
									testFiles.add(getFullPath(data, ref.getPath()));
								}
							}
							return null;
						}
				});

			monitor.worked(1);
		}
		
		monitor.done();
		
		for (IPath normalPath : normalFiles){
			normalMakMakePaths.add(normalPath);
		}
		for (IPath testPath : testFiles){
			testMakMakePaths.add(testPath);
		}
		
	}
	
	/**
	 * Return list of file system paths to all project extensions referenced by the given
	 * bld.inf full path.  This function differentiates between PRJ_EXTENSIONS and PRJ_TESTEXTENSIONS
	 * @param bldInfFilePath - The IPath to the bld.inf file that is to be preprocessed.
	 * @param buildConfigs - List of build configuration to parse for.
	 * @param normalExtensionPaths - The list of PRJ_EXTENSIONS IPath objects for the bld.inf
	 * @param testExtensionPaths - The list of PRJ_TESTEXTENSIONS IPath objects for the bld.inf
	 * @param monitor
	 */
	public static void getExtensions(final IPath bldInfFilePath, List<ISymbianBuildContext> buildConfigs, 
		List<IPath> normalExtensionPaths, List<IPath> testExtensionPaths, IProgressMonitor monitor) {
		
		// get a bld.inf view for each build target.  take the union of all extensions for each view
		// of the bld.inf file.
		final Set<IPath> normalFiles = new LinkedHashSet<IPath>();
		final Set<IPath> testFiles = new LinkedHashSet<IPath>();
		
		monitor.beginTask("Scanning bld.inf project extensions", buildConfigs.size());

		for (final ISymbianBuildContext context : buildConfigs) {
			EpocEnginePlugin.runWithBldInfData(bldInfFilePath, 
					new DefaultViewConfiguration(context, bldInfFilePath, new AcceptedNodesViewFilter()), 
					new BldInfDataRunnableAdapter() {
						public Object run(IBldInfData data) {
							BldInfViewPathHelper helper = new BldInfViewPathHelper(data, context);
							for (IExtension extension : data.getExtensions()) {
								IPath extensionMakefileBase = helper.convertExtensionTemplateToFilesystem(extension.getTemplatePath());
								normalFiles.add(extensionMakefileBase.addFileExtension("mk")); //$NON-NLS-1$
							}
							for (IExtension extension : data.getTestExtensions()) {
								IPath extensionMakefileBase = helper.convertExtensionTemplateToFilesystem(extension.getTemplatePath());
								testFiles.add(extensionMakefileBase.addFileExtension("mk")); //$NON-NLS-1$
							}
							return null;
						}
				});

			monitor.worked(1);
		}
		
		monitor.done();
		
		for (IPath normalPath : normalFiles){
			normalExtensionPaths.add(normalPath);
		}
		for (IPath testPath : testFiles){
			testExtensionPaths.add(testPath);
		}
	}
	
	/**
	 * Return list of all named project extensions referenced by the given
	 * bld.inf full path.  This function differentiates between PRJ_EXTENSIONS and PRJ_TESTEXTENSIONS
	 * @param bldInfFilePath - The IPath to the bld.inf file that is to be preprocessed.
	 * @param buildConfigs - List of build configuration to parse for.
	 * @param normalExtensions - The list of named PRJ_EXTENSIONS for the bld.inf
	 * @param testExtensions - The list of named PRJ_TESTEXTENSIONS for the bld.inf
	 * @param monitor
	 */
	public static void getNamedExtensions(final IPath bldInfFilePath, List<ISymbianBuildContext> buildConfigs, 
		List<IExtension> normalExtensions, List<IExtension> testExtensions, IProgressMonitor monitor) {
		
		// get a bld.inf view for each build target.  take the union of all extensions for each view
		// of the bld.inf file.
		final Set<IExtension> normalFiles = new LinkedHashSet<IExtension>();
		final Set<IExtension> testFiles = new LinkedHashSet<IExtension>();
		
		monitor.beginTask("Scanning bld.inf project extensions", buildConfigs.size());

		for (final ISymbianBuildContext context : buildConfigs) {
			EpocEnginePlugin.runWithBldInfData(bldInfFilePath, 
					new DefaultViewConfiguration(context, bldInfFilePath, new AcceptedNodesViewFilter()), 
					new BldInfDataRunnableAdapter() {
						public Object run(IBldInfData data) {
							for (IExtension extension : data.getExtensions()) {
								if (extension.getName() != null) {
									normalFiles.add(extension);
								}
							}
							for (IExtension extension : data.getTestExtensions()) {
								if (extension.getName() != null) {
									testFiles.add(extension);
								}
							}
							return null;
						}
				});

			monitor.worked(1);
		}
		
		monitor.done();
		
		for (IExtension normal : normalFiles){
			normalExtensions.add(normal);
		}
		for (IExtension test : testFiles){
			testExtensions.add(test);
		}
	}
	
	/**
	 * Determines if the given bld.inf file contains any unnamed project extensions
	 * @param bldInfFilePath - The IPath to the bld.inf file that is to be preprocessed.
	 * @param buildConfigs - List of build configuration to parse for.
	 * @param monitor
	 * @return
	 */
	public static boolean hasUnnamedExtensions(final IPath bldInfFilePath, List<ISymbianBuildContext> buildConfigs, IProgressMonitor monitor) {

		final Set<IExtension> extensions = new LinkedHashSet<IExtension>();

		monitor.beginTask("Scanning bld.inf project extensions", buildConfigs.size());

		for (final ISymbianBuildContext context : buildConfigs) {
			EpocEnginePlugin.runWithBldInfData(bldInfFilePath, 
					new DefaultViewConfiguration(context, bldInfFilePath, new AcceptedNodesViewFilter()), 
					new BldInfDataRunnableAdapter() {
						public Object run(IBldInfData data) {
							for (IExtension extension : data.getExtensions()) {
								if (extension.getName() == null) {
									extensions.add(extension);
								}
							}
							for (IExtension extension : data.getTestExtensions()) {
								if (extension.getName() == null) {
									extensions.add(extension);
								}
							}
							return null;
						}
				});

			monitor.worked(1);
		}
		
		monitor.done();

		return extensions.size() > 0;
	}
	
	/**
	 * Returns two paths in a list - the first is the suggested root directory
	 * for the project.  This is calculated by taking the shortest common path
	 * containing the bld.inf, mmp and make files, exports, source, resource, and include paths.
	 * <p>
	 * The second path is the "minimum" root directory for the project.  This is
	 * the shortest common source path containing the bld.inf, mmp and make files.
	 * <p>
	 * The third path is the "desired" root directory for the project.  This is 
	 * the shortest common source path containing the bld.inf, mmp and make files,
	 * and the sources and resources.  (added as of 1.3)
	 * @param bldInfFilePath full path to bld.inf
	 * @param contexts the list of selected SDKs
	 * @param monitor the progress monitor for this operation
	 * @return list of exactly 3 IPaths
	 */
	public static List<IPath> getProjectRoots(final IPath bldInfFilePath, List<ISymbianBuildContext> contexts, final IProgressMonitor monitor) {
		// Get the "all" view of the bld.inf/mmp files.  We want to find the true
		// project root here.  It may be more narrow
		// for the selected set of build configs, but if they later add other
		// build configs then those files may not be
		// under the project root.  Find the shortest common path from the
		// bld.inf, mmp files, sourcepaths, user includes,
		// resources, bitmaps, etc. and use that as the project root.
		
		// We track the required paths -- to find the MMPs and makefiles --
		// the desired paths -- to find sources and resources --
		// and the optional paths -- sources and includes.  The optional paths
		// may diverge to something crazy like the drive root if an include
		// or source path is on a different drive or is an absolute path,
		// so revert to the desired paths in that case.  If those path still
		// diverge, revert to the required root.
		final Set<IPath> requiredPaths = new LinkedHashSet<IPath>();
		final Set<IPath> optionalPaths = new LinkedHashSet<IPath>();
		final Set<IPath> desiredPaths = new LinkedHashSet<IPath>();
		
		// Get a default SDK for use in resolving SDK-relative #includes
		// (e.g. for *.mmh or *.h files).  Try to get one whose directory exists
		// and which has a variant.mmh file, to cover the greediest import scenario.
		//
		// This doesn't affect the path calculation since SDK-relative paths are
		// filtered out by our use of MMPViewPathHelper,
		// but it avoids a lot of errors about missing #includes showing up in
		// the error log.
		ISymbianBuildContext defaultContext = null;
		for (ISymbianBuildContext context : contexts) {
			if (context.getSDK() != null 
					&& context.getSDK().getEPOCROOT() != null
					&& new File(context.getSDK().getEPOCROOT()).exists()) {
				defaultContext = context;
				if (defaultContext.getSDK().getPrefixFile() != null)
					break;
			}
		}
		final ISymbianBuildContext defaultContextToUse = defaultContext;
		
		requiredPaths.add(bldInfFilePath.removeLastSegments(1));
		
		EpocEnginePlugin.runWithBldInfData(bldInfFilePath, 
				new DefaultViewConfiguration(defaultContextToUse, bldInfFilePath, new AllNodesViewFilter()), 
				new BldInfDataRunnableAdapter() {
					public Object run(IBldInfData data) {
						IMMPReference[] refs = data.getAllMMPReferences();
						IMakefileReference[] makeRefs = data.getAllMakefileReferences();
						IExtension[] extensions = data.getAllExtensions();
						
						monitor.beginTask("Calculating root directory and default name for project", refs.length + makeRefs.length + extensions.length);

						try {
							for (IMMPReference ref : refs) {
								IPath path = ref.getPath();
								path = getFullPath(data, path);
								
								// add the path to the mmp file
								requiredPaths.add(path.removeLastSegments(1));

								getMMPPathReferences(bldInfFilePath, defaultContextToUse, path, optionalPaths, desiredPaths);
								monitor.worked(1);
							}

							for (IMakefileReference ref : makeRefs) {
								IPath path = ref.getPath();
								path = getFullPath(data, path);
								
								// add the Makefile's path
								requiredPaths.add(path.removeLastSegments(1));
								
								getMakefilePathReferences(bldInfFilePath, defaultContextToUse, path, optionalPaths);
								monitor.worked(1);
							}

							// note, all extension makefiles are in EPOCROOT,
							// so it's futile to force the project root to include them
							/*
							BldInfViewPathHelper helper = new BldInfViewPathHelper(view, defaultContextToUse);
							for (IExtension extension : extensions) {
								IPath path = extension.getTemplatePath();
								path = helper.convertExtensionTemplateToFilesystem(path);
								
								// add the extension's path, optionally, since it's in EPOCROOT
								optionalPaths.add(path.removeLastSegments(1));
								
								getMakefilePathReferences(bldInfFilePath, defaultContextToUse, path, optionalPaths, desiredPaths);
								monitor.worked(1);
							}
							*/
							monitor.worked(extensions.length);
							
							// check the export paths
							for (IExport export : data.getExports()) {
								IPath path = getFullPath(data, export.getSourcePath());
								optionalPaths.add(path.removeLastSegments(1));
								monitor.worked(1);
							}
							
							// check the test export paths
							for (IExport testExport : data.getTestExports()) {
								IPath path = getFullPath(data, testExport.getSourcePath());
								optionalPaths.add(path.removeLastSegments(1));
								monitor.worked(1);
							}

						} catch (CoreException e) {
							CarbideBuilderPlugin.log(e);
						} finally {
							monitor.done();
						}
						return null;
					}
			});

		IPath root = null;
		IPath desiredRoot = null;
		IPath requiredRoot = null;

		if (requiredPaths.size() > 0) {
			// requiredPaths contains a list of required paths
			// (for MMPs).  Take the shortest common source path of all.
			CommonPathFinder finder = new CommonPathFinder();
			for (IPath path : requiredPaths) {
				finder.addDirectory(path);
			}
			requiredRoot = finder.getCommonPath();
			root = requiredRoot;
		}
		if (desiredPaths.size() > 0) {
			// desiredPaths contains a list of source paths.  
			// Take the shortest common source path of all.
			CommonPathFinder finder = new CommonPathFinder();
			finder.addDirectory(root);
			for (IPath path : desiredPaths) {
				// boog 9221, bad root can be chosen for non-existent sourcepaths in MMPs
				// so ingore those
				if (path.toFile().exists()){
					finder.addDirectory(path);
				} 
			}
			desiredRoot = finder.getCommonPath();
			root = finder.getCommonPath();
		}
		if (optionalPaths.size() > 0) {
			// optionalPaths contains a list of source and include paths.
			// Take the shortest common source path of all.
			CommonPathFinder finder = new CommonPathFinder();
			finder.addDirectory(root);
			for (IPath path : optionalPaths) {
				// boog 9221, bad root can be chosen for non-existent sourcepaths in MMPs
				// so ingore those
				if (path.toFile().exists()){
					finder.addDirectory(path);
				} 
			}
			root = finder.getCommonPath();
		}
		
		if (root != null) {
			// Ensure the root is sane
			if (root.segmentCount() == 0 && desiredRoot != null && desiredRoot.segmentCount() > 0) {
				CarbideBuilderPlugin.log(Logging.newStatus(CarbideBuilderPlugin.getDefault(), 
						IStatus.WARNING,
						"Project appears to reference the drive's root directory or another drive (" + root + "); reverting to desired root: " + desiredRoot));
				root = desiredRoot;
			}
			if (root.segmentCount() == 0 && requiredRoot != null && requiredRoot.segmentCount() > 0) {
				CarbideBuilderPlugin.log(Logging.newStatus(CarbideBuilderPlugin.getDefault(), 
						IStatus.WARNING,
						"Project appears to reference the drive's root directory or another drive (" + root + "); reverting to required root: " + requiredRoot));
				root = requiredRoot;
			}
		}
		
		if (root == null) {
			// try something else
			root = bldInfFilePath.removeLastSegments(1);
	        if (root.segmentCount() > 0 && root.lastSegment().compareToIgnoreCase("group") == 0) { //$NON-NLS-1$
	        	root = root.removeLastSegments(1);
	        }
		}

		List<IPath> pathsToReturn = new ArrayList<IPath>();
		pathsToReturn.add(root);
		pathsToReturn.add(requiredRoot);
		pathsToReturn.add(desiredRoot);
		
		return pathsToReturn;
	}

	/**
	 * Get the paths referenced from MMP
	 * @param optionalPaths the paths referenced, excluding those for sources and resources
	 * @param desiredPaths the paths referenced for sources and resources
	 */
	private static void getMMPPathReferences(IPath bldInfFilePath,
			ISymbianBuildContext defaultContext,
			final IPath mmpPath, 
			final Set<IPath> optionalPaths, final Set<IPath> desiredPaths) throws CoreException {

		EpocEnginePlugin.runWithMMPData(mmpPath, 
				new DefaultMMPViewConfiguration(defaultContext, bldInfFilePath, new AllNodesViewFilter()), 
				new MMPDataRunnableAdapter() {

				public Object run(IMMPData mmpData) {
					// no EPOCROOT here, since we want to weed out these paths
					MMPViewPathHelper helper = new MMPViewPathHelper(mmpData, null);
					
					// this covers sources, resource lists, and documents
					IPath[] sourcePaths = mmpData.getEffectiveSourcePaths();
					for (IPath path : sourcePaths) {
						IPath fullPath = helper.convertMMPToFilesystem(
								EMMPPathContext.SOURCEPATH, path);
						if (fullPath != null)
							desiredPaths.add(fullPath);
					}
					
					List<IPath> includePaths = mmpData.getUserIncludes();
					for (IPath includePath : includePaths) {
						IPath fullPath = helper.convertMMPToFilesystem(
								EMMPPathContext.USERINCLUDE, includePath);
						if (fullPath != null)
							optionalPaths.add(fullPath);
					}
					
					includePaths = mmpData.getSystemIncludes();
					for (IPath includePath : includePaths) {
						IPath fullPath = helper.convertMMPToFilesystem(
								EMMPPathContext.SYSTEMINCLUDE, includePath);
						if (fullPath != null)
							optionalPaths.add(fullPath);
					}
					
					List<IMMPResource> resources = mmpData.getResourceBlocks();
					for (IMMPResource resource : resources) {
						IPath fullPath = helper.convertMMPToFilesystem(
								EMMPPathContext.START_RESOURCE, resource.getSource());
						if (fullPath != null)
							desiredPaths.add(fullPath);
					}
					
					// images may come from strange places thus are optional
					List<IMMPBitmap> bitmaps = mmpData.getBitmaps();
					for (IMMPBitmap bitmap : bitmaps) {
						for (IBitmapSource source : bitmap.getBitmapSources()) {
							IPath fullPath = helper.convertMMPToFilesystem(
									EMMPPathContext.START_BITMAP_SOURCE, source.getPath());
							if (fullPath != null)
								optionalPaths.add(fullPath);
							fullPath = helper.convertMMPToFilesystem(
									EMMPPathContext.START_BITMAP_SOURCE, source.getMaskPath());
							if (fullPath != null)
								optionalPaths.add(fullPath);
						}
					}
					return null;
				}
		});
	}

	/**
	 * Get the paths referenced from icon makefiles
	 */
	private static void getMakefilePathReferences(IPath bldInfFilePath, 
			final ISymbianBuildContext defaultContext,
			final IPath makefilePath, 
			final Set<IPath> paths) throws CoreException {
		
		// We don't know if it's an image makefile or not, but the presence
		// of detected image compilation commands will give us IMultiImageSources to look at.
		
		EpocEnginePlugin.runWithImageMakefileData(makefilePath, 
				new DefaultImageMakefileViewConfiguration(defaultContext, bldInfFilePath, new AllNodesViewFilter()), 
				new ImageMakefileDataRunnableAdapter() {

				public Object run(IImageMakefileData data) {
					ImageMakefileViewPathHelper helper = new ImageMakefileViewPathHelper(
							data, new ISymbianBuildContext[] { defaultContext });
					List<IMultiImageSource> multiImageSources = data.getMultiImageSources();
					for (IMultiImageSource multiImageSource : multiImageSources) {
						for (IImageSource source : multiImageSource.getSources()) {
							IPath imgPath = helper.findCandidateImagePath(source.getPath());
							if (imgPath != null && !imgPath.isAbsolute())
								paths.add(imgPath);
							imgPath = helper.findCandidateMaskPath(source);
							if (imgPath != null && !imgPath.isAbsolute())
								paths.add(imgPath);
						}
					}
					return null;
				}
		});
	}

	/**
	 * Get full filesystem paths to all MMPs and makefiles (including test targets) accessible from the given
	 * project's bld.inf.  Note that this does not include PRJ_EXTENSIONS or PRJ_TESTEXTENSIONS as they are a special
	 * case.  See {@link #getExtensions(IPath, List, List, List, IProgressMonitor)}.
	 * @param project - The IProject for the project in question
	 * @param makMakeRefs - The list of IPath entries for all the mmp and makefiles (PRJ_MMPFILES contents). List will be added to.
	 * @param testMakMakeReferences - The list of IPath entries for all the test mmp and makefiles(PRJ_TESTMMPFILES contents). List will be added to.
	 */
	public static void getAllMakMakeFiles(IProject project, List<IPath>makMakeRefs, List<IPath>testMakMakeReferences) {
		
        final ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
        if (cpi == null)
        	return;
        
		List<ICarbideBuildConfiguration> buildConfigs = cpi.getBuildConfigurations();
		
		final Set<IPath> tempTestProjects = new LinkedHashSet<IPath>();
		final Set<IPath> tempNormalProjects = new LinkedHashSet<IPath>();
		for (final ICarbideBuildConfiguration buildConfig : buildConfigs) {
			EpocEnginePlugin.runWithBldInfData(cpi.getWorkspaceRelativeBldInfPath(), 
					new DefaultViewConfiguration(project, buildConfig, new AcceptedNodesViewFilter()), 
					new BldInfDataRunnableAdapter() {
						public Object run(IBldInfData data) {
							for (IMakMakeReference normalRef : data.getMakMakeReferences()) {
								tempNormalProjects.add(getFullPath(data, normalRef.getPath()));
							}
							for (IMakMakeReference testRef : data.getTestMakMakeReferences()) {
								tempTestProjects.add(getFullPath(data, testRef.getPath()));
							}
							return null;
						}
				});
		}
		
		for (IPath normalPath : tempNormalProjects){
			makMakeRefs.add(normalPath);
		}
		for (IPath testPath : tempTestProjects){
			testMakMakeReferences.add(testPath);
		}
	}

	/**
	 * Find the full file system path to the main executable.
	 * @return full path or blank for error
	 * 
	 * @deprecated In 1.3, there is no longer a debug mmp, hence no "main" executable.  When launching,
	 * if there is more than one mmp, the user will be asked which executable to target.
	 * Use {@link #getHostPathForExecutable(ICarbideBuildConfiguration, IPath)} instead.
	 * 
	 * This method will only work now if there is one and only one mmp.  Otherwise it will return
	 * an empty string.
	 */
	public static String getPathToMainExecutable(final ICarbideBuildConfiguration buildConfig) {
		final ICarbideProjectInfo info = buildConfig.getCarbideProject();
		String exePath = ""; //$NON-NLS-1$

		if (buildConfig == null){
			return ""; //$NON-NLS-1$
		}
		
		exePath = (String)EpocEnginePlugin.runWithBldInfData(info.getWorkspaceRelativeBldInfPath(), 
				new DefaultViewConfiguration(info.getProject(), buildConfig, new AcceptedNodesViewFilter()), 
				new BldInfDataRunnableAdapter() {
					public Object run(IBldInfData data) {
						String exePath  = ""; //$NON-NLS-1$
						
						IMMPReference[] mmps = data.getAllMMPReferences();
						
						if (mmps.length == 1) {
							final IPath workspaceRelativeMMPPath = new Path(info.getProject().getName()).append(mmps[0].getPath());
							exePath = (String)EpocEnginePlugin.runWithMMPData(workspaceRelativeMMPPath, 
									new DefaultMMPViewConfiguration(info.getProject(), buildConfig, new AcceptedNodesViewFilter()),
									new MMPDataRunnableAdapter() {

									public Object run(IMMPData mmpData) {
										// get the executable name and then prepend it with the epocroot + platform + target
										String exePath = mmpData.getSingleArgumentSettings().get(EMMPStatement.TARGET);
										if (exePath == null) {
											// not likely but possible
											return null;
										}

										String releasePlatform = buildConfig.getSDK().getBSFCatalog().getReleasePlatform(buildConfig.getPlatformString());
										IPath path = buildConfig.getSDK().getReleaseRoot().append(releasePlatform).append(buildConfig.getTargetString());

										// if targetpath is non-null and this is an EKA1 emulator config then add it
										if (buildConfig.getPlatformString().equals(ISymbianBuildContext.EMULATOR_PLATFORM)) {
											if (buildConfig.getSDK().getOSVersion().getMajor() < 9) {
												String targetPath = mmpData.getSingleArgumentSettings().get(EMMPStatement.TARGETPATH);
												if (targetPath != null) {
													path = path.append("z").append(targetPath); //$NON-NLS-1$
												}
											}
										}

										// adapt to variant, if needed
										IPath targetPath = path.append(exePath);
										if (isVariantBldInf(buildConfig.getCarbideProject().getAbsoluteBldInfPath()) || isFeatureVariantMMP(mmpData, info.getProject())) {
											targetPath = getBinaryVariantTargetName(mmpData, targetPath, info.getProject());
											if (targetPath == null){
												return null; 
											}
										}
										
										exePath = targetPath.toOSString();
										return exePath;
									}
							});
						}
						
						return exePath;
					}
			});

		if (exePath == null) {
			exePath = ""; //$NON-NLS-1$
		}
		return exePath;
	}
	
	/**
	 * Find the full file system path to the main executable.
	 * @return full path or blank for error 
	 * 
	 * @deprecated In 1.3, there is no longer a debug mmp, hence no "main" executable.  When launching,
	 * if there is more than one mmp, the user will be asked which executable to target.
	 * Use {@link #getHostPathForExecutable(ICarbideBuildConfiguration, IPath)} instead.
	 * 
	 * This method will only work now if there is one and only one mmp.  Otherwise it will return
	 * an empty string.
	 */
	public static String getPathToMainExecutable(final ICarbideProjectInfo info) {
		return getPathToMainExecutable(info.getDefaultConfiguration());
	}

	/**
	 * Find the full file system path to the main executable.
	 * @return full path or null for error
	 * 
	 * @deprecated In 1.3, there is no longer a debug mmp, hence no "main" executable.  When launching,
	 * if there is more than one mmp, the user will be asked which executable to target.
	 * Use {@link #getTargetPathForExecutable(ICarbideBuildConfiguration, IPath)} instead.
	 * 
	 * This method will only work now if there is one and only one mmp.  Otherwise it will return
	 * an empty string.
	 */
	public static IPath getTargetPathForMainExecutable(final ICarbideProjectInfo info) {
		IPath exePath = null;
		final ICarbideBuildConfiguration buildConfig = info.getDefaultConfiguration();
		
		exePath = (IPath)EpocEnginePlugin.runWithBldInfData(info.getWorkspaceRelativeBldInfPath(), 
				new DefaultViewConfiguration(info.getProject(), buildConfig, new AcceptedNodesViewFilter()), 
				new BldInfDataRunnableAdapter() {
					public Object run(IBldInfData data) {
						IPath exePath  = null;
						
						IMMPReference[] mmps = data.getAllMMPReferences();
						
						if (mmps.length == 1) {
							final IPath workspaceRelativeMMPPath = new Path(info.getProject().getName()).append(mmps[0].getPath());
							exePath = (IPath)EpocEnginePlugin.runWithMMPData(workspaceRelativeMMPPath, 
									new DefaultMMPViewConfiguration(info.getProject(), buildConfig, new AcceptedNodesViewFilter()),
									new MMPDataRunnableAdapter() {
									public Object run(IMMPData mmpData) {
										IPath exePath = null;
										// get the executable name and then prepend it with the epoc release root + platform + target
										IPath path = mmpData.getTargetFilePath();
										if (path != null) {
											exePath = path.makeAbsolute().setDevice("C:"); //$NON-NLS-1$
										} else {
											// if the target path is not set then by default it will usually
											// be left blank.  for EKA2 though binaries need to go in \sys\bin\
											exePath = new Path("C:\\"); //$NON-NLS-1$
											if (buildConfig.getSDK().getOSVersion().getMajor() > 8) {
												exePath = exePath.append("sys\\bin\\"); //$NON-NLS-1$
											}
											String targetName = mmpData.getSingleArgumentSettings().get(EMMPStatement.TARGET);
											if  (targetName != null) {
												exePath = exePath.append(targetName);
											}
										}
										
										// note: this does not need to account for variant targets,
										// since they have the same name on the target side.
										
										return exePath;
									}
							});
						}
						return exePath;
					}
			});

		return exePath;
	}

	/**
	 * Find the full host file system path to the executable built by the given mmp for the given build configuration.
	 * 
	 * @param buildConfig
	 * @param workspaceRelativeMMPPath
	 * @return full path or null if the path cannot be determined.
	 */
	public static IPath getHostPathForExecutable(final ICarbideBuildConfiguration buildConfig, IPath workspaceRelativeMMPPath) {

		final ICarbideProjectInfo cpi = buildConfig.getCarbideProject();

		return (IPath)EpocEnginePlugin.runWithMMPData(workspaceRelativeMMPPath, 
				new DefaultMMPViewConfiguration(buildConfig, new AcceptedNodesViewFilter()),
				new MMPDataRunnableAdapter() {

				public Object run(IMMPData mmpData) {
					// get the executable name and then prepend it with the epocroot + platform + target
					String exePath = mmpData.getSingleArgumentSettings().get(EMMPStatement.TARGET);
					if (exePath == null) {
						// not likely but possible
						return null;
					}
					
					// some target types aren't executables
					String targetType = mmpData.getSingleArgumentSettings().get(EMMPStatement.TARGETTYPE);
					if (targetType != null) {
						if (targetType.toUpperCase().matches("LIB|KLIB|IMPLIB|NONE|STDLIB")) { //$NON-NLS-1$
							return null;
						}
					}

					// adapt the exe filename to the variant, if any
					IPath tempPath = null;
					if (isVariantBldInf(cpi.getAbsoluteBldInfPath()) || isFeatureVariantMMP(mmpData, cpi.getProject())) {
						tempPath = getBinaryVariantTargetName(mmpData, new Path(exePath), cpi.getProject());
						if (tempPath == null){
							return null; 
						}
						exePath = tempPath.lastSegment();
					}
					
					String releasePlatform = buildConfig.getSDK().getBSFCatalog().getReleasePlatform(buildConfig.getBasePlatformForVariation());
					IPath path = buildConfig.getSDK().getReleaseRoot().append(releasePlatform).append(buildConfig.getTargetString());

					// if targetpath is non-null and this is an EKA1 emulator config then add it
					if (buildConfig.getPlatformString().equals(ISymbianBuildContext.EMULATOR_PLATFORM)) {
						if (buildConfig.getSDK().getOSVersion().getMajor() < 9) {
							String targetPath = mmpData.getSingleArgumentSettings().get(EMMPStatement.TARGETPATH);
							if (targetPath != null) {
								path = path.append("z").append(targetPath); //$NON-NLS-1$
							}
						}
					}
					path = path.append(exePath);
					
					return path;
				}
		});								
	}
		
	/**
	 * Find the full target file system path to the executable built by the mmp for the given build configuration.
	 * 
	 * @param buildConfig
	 * @param workspaceRelativeMMPPath
	 * @return full path or null if the path cannot be determined.
	 */
	public static IPath getTargetPathForExecutable(final ICarbideBuildConfiguration buildConfig, IPath workspaceRelativeMMPPath) {
		IPath exePath = (IPath)EpocEnginePlugin.runWithMMPData(workspaceRelativeMMPPath,
				new DefaultMMPViewConfiguration(buildConfig, new AcceptedNodesViewFilter()),
				new MMPDataRunnableAdapter() {

				public Object run(IMMPData mmpData) {
					// some target types aren't executables
					String targetType = mmpData.getSingleArgumentSettings().get(EMMPStatement.TARGETTYPE);
					if (targetType != null) {
						if (targetType.toUpperCase().matches("LIB|KLIB|IMPLIB|NONE|STDLIB")) { //$NON-NLS-1$
							return null;
						}
					}

					IPath exePath = null;
					// get the executable name and then prepend it with the epoc release root + platform + target
					IPath path = mmpData.getTargetFilePath();
					if (path != null) {
						exePath = path.makeAbsolute().setDevice("C:"); //$NON-NLS-1$
					} else {
						// if the target path is not set then by default it will usually
						// be left blank.  for EKA2 though binaries need to go in \sys\bin\
						exePath = new Path("C:\\"); //$NON-NLS-1$
						if (buildConfig.getSDK().getOSVersion().getMajor() > 8) {
							exePath = exePath.append("sys\\bin\\"); //$NON-NLS-1$
						}
						String targetName = mmpData.getSingleArgumentSettings().get(EMMPStatement.TARGET);
						if  (targetName != null) {
							exePath = exePath.append(targetName);
						}
					}
					
					// note: this does not need to account for variant targets,
					// since they have the same name on the target side.
					
					return exePath;
				}
		});

		return exePath;
	}
	
	/**
	 * Add a generated file -> target path mapping for each generated file per language.
	 * In a START RESOURCE block, any LANG statement overrides global languages.
	 * @param resources map to update
	 * @param data the MMP view containing the resource definition
	 * @param resource if a block resource, an IMMPResource, else null
	 * @param baseGeneratedPath full path to base .rsc file, without extension
	 * @param targetPath full path to device-side .rsc directory
	 */
	private static void addResourceLanguageTargets(Map<String, String> resources, IMMPData data, IMMPResource resource, 
			String baseGeneratedPath, String targetPath) {
		List<EMMPLanguage> languages = null;
		// block resource can override languages (entirely)
		if (resource != null) {
			languages = resource.getLanguages();
			if (languages.size() == 0)
				languages = null;
		}
		if (languages == null) {
			languages = data.getLanguages();
			if (languages.size() == 0) {
				// default is non-localize
				languages = Collections.singletonList(EMMPLanguage.SC_NonLocalized);
			}
		}
		for (EMMPLanguage language : languages) {
			String extension = ".R" + language.getCodeString(); //$NON-NLS-1$
			resources.put(baseGeneratedPath + extension, targetPath);
		}
	}
	
	/**
	 * Get a map of the generated resources for the project. 
	 * @param info project info
	 * @return map of local full paths to target full paths
	 * 
	 * @deprecated In 1.3, there is no longer a debug mmp, hence no "main" executable.  When launching,
	 * if there is more than one mmp, the user will be asked which executable to target.
	 * Use {@link #getHostAndTargetResources(ICarbideBuildConfiguration, IPath)} instead.
	 * 
	 * This method will only work now if there is one and only one mmp.  Otherwise it will return
	 * an empty map.
	 */
	public static HashMap<String, String> getHostAndTargetResources(final ICarbideProjectInfo info) {
		final HashMap<String, String> resources = new HashMap<String, String>();
		
		final ICarbideBuildConfiguration buildConfig = info.getDefaultConfiguration();
		
		EpocEnginePlugin.runWithBldInfData(info.getWorkspaceRelativeBldInfPath(), 
				new DefaultViewConfiguration(info.getProject(), buildConfig, new AcceptedNodesViewFilter()), 
				new BldInfDataRunnableAdapter() {

				public Object run(IBldInfData data) {
					IMMPReference[] mmps = data.getAllMMPReferences();
					
					if (mmps.length == 1) {
						final IPath workspaceRelativeMMPPath = new Path(info.getProject().getName()).append(mmps[0].getPath());
						EpocEnginePlugin.runWithMMPData(workspaceRelativeMMPPath, 
								new DefaultMMPViewConfiguration(info.getProject(), buildConfig, new AcceptedNodesViewFilter()),
								new MMPDataRunnableAdapter() {

								public Object run(IMMPData mmpData) {
									// read the project-wide target path
									String targetPath = mmpData.getSingleArgumentSettings().get(EMMPStatement.TARGETPATH);
									if (targetPath != null) {
										// make sure it doesn't start with a "\" but ends with one
										IPath targetP = new Path(targetPath).makeRelative().addTrailingSeparator();
										targetPath = targetP.toOSString();
									} else {
										// for EKA1 just leave empty.  for EKA2 use sys\bin\
										if (buildConfig.getSDK().getOSVersion().getMajor() > 8) {
											targetPath = "sys\\bin\\"; //$NON-NLS-1$
										} else {
											targetPath = ""; //$NON-NLS-1$
										}
									}

									String dataZDir = buildConfig.getSDK().getReleaseRoot().removeLastSegments(1).toOSString() + "\\Data\\z\\"; //$NON-NLS-1$
									// get the aifs
									List<IMMPAIFInfo> aifs = mmpData.getAifs();
									for (IMMPAIFInfo aif : aifs) {
										IPath aifPath = aif.getTarget().makeAbsolute();
										resources.put(dataZDir + targetPath + aifPath.lastSegment(), "C:\\" + targetPath); //$NON-NLS-1$
									}

									// get the bitmaps
									List<IMMPBitmap> bmps = mmpData.getBitmaps();
									for (IMMPBitmap bmp : bmps) {
										IPath mbmPath = bmp.getTargetFilePath().makeRelative();
										resources.put(dataZDir + mbmPath.toOSString(), "C:\\" + mbmPath.removeLastSegments(1).addTrailingSeparator().toOSString()); //$NON-NLS-1$
									}

									// get the user resources
									List<IPath> userResources = mmpData.getUserResources();
									for (IPath userRes : userResources) {
										addResourceLanguageTargets(resources, mmpData, null, 
												dataZDir + targetPath + userRes.removeFileExtension().lastSegment(), "C:\\" + targetPath); //$NON-NLS-1$ //$NON-NLS-2$
									}

									// get the system resources
									List<IPath> systemResources = mmpData.getSystemResources();
									for (IPath systemRes : systemResources) {
										addResourceLanguageTargets(resources, mmpData, null,
												dataZDir + targetPath + systemRes.removeFileExtension().lastSegment(), "C:\\" + targetPath); //$NON-NLS-1$ //$NON-NLS-2$
									}

									// get the resource blocks
									List<IMMPResource> resourceBlocks = mmpData.getResourceBlocks();
									for (IMMPResource resourceBlock : resourceBlocks) {
										IPath resPath = resourceBlock.getTargetPath();
										if (resPath == null) {
											// if not specified in the resource block then get the global
											// target path
											String targetP = mmpData.getSingleArgumentSettings().get(EMMPStatement.TARGETPATH);
											if (targetP != null) {
												resPath = new Path(targetP);
											}
										}
										if (resPath != null) {
											resPath = resPath.makeRelative().addTrailingSeparator();
											String filename = resourceBlock.getTargetFile();
											if (filename == null) {
												filename = resourceBlock.getSource().removeFileExtension().lastSegment();
											} else {
												filename = new Path(filename).removeFileExtension().toOSString();
											}
											// adjust the path if necessary as it's different on the phone for the *_.reg file
											IPath adjustedTargetPath = resPath;
											if (adjustedTargetPath.toOSString().equalsIgnoreCase("private\\10003a3f\\apps\\")) { //$NON-NLS-1$
												adjustedTargetPath = new Path("private\\10003a3f\\import\\apps\\"); //$NON-NLS-1$
											}
											addResourceLanguageTargets(resources, mmpData, resourceBlock,
													dataZDir + resPath.toOSString() + filename, "C:\\" + adjustedTargetPath.toOSString()); //$NON-NLS-1$
										}
									}
									return null;
								}
						});
					}
					return null;
				}
			});

		return resources;
	}

	/**
	 * Get a map of the generated resources for the project. 
	 * @param buildConfig the build configuration
	 * @param workspaceRelativeMMPPath the workspace relative path to the mmp file to get the resources for
	 * @return map of local full paths to target full paths
	 */
	public static HashMap<String, String> getHostAndTargetResources(final ICarbideBuildConfiguration buildConfig, IPath workspaceRelativeMMPPath) {
		// TODO: at some point we could maybe use the sis file (if any) for the current config to build this list
		final HashMap<String, String> resources = new HashMap<String, String>();

		EpocEnginePlugin.runWithMMPData(workspaceRelativeMMPPath,
				new DefaultMMPViewConfiguration(buildConfig, new AcceptedNodesViewFilter()),
				new MMPDataRunnableAdapter() {

				public Object run(IMMPData mmpData) {
					// read the project-wide target path
					String targetPath = mmpData.getSingleArgumentSettings().get(EMMPStatement.TARGETPATH);
					if (targetPath != null) {
						// make sure it doesn't start with a "\" but ends with one
						IPath targetP = new Path(targetPath).makeRelative().addTrailingSeparator();
						targetPath = targetP.toOSString();
					} else {
						// for EKA1 just leave empty.  for EKA2 use sys\bin\
						if (buildConfig.getSDK().getOSVersion().getMajor() > 8) {
							targetPath = "sys\\bin\\"; //$NON-NLS-1$
						} else {
							targetPath = ""; //$NON-NLS-1$
						}
					}

					String dataZDir = buildConfig.getSDK().getReleaseRoot().removeLastSegments(1).toOSString() + "\\Data\\z\\"; //$NON-NLS-1$
					
					// get the aifs
					List<IMMPAIFInfo> aifs = mmpData.getAifs();
					for (IMMPAIFInfo aif : aifs) {
						IPath aifPath = aif.getTarget().makeAbsolute();
						resources.put(dataZDir + targetPath + aifPath.lastSegment(), "C:\\" + targetPath); //$NON-NLS-1$
					}

					// for resources and bitmaps, the target path may be based on the target type if not explicitly
					// set for the resource or bitmap.
					String targetType = mmpData.getSingleArgumentSettings().get(EMMPStatement.TARGETTYPE);
					if (targetType != null) {
						// could be PLUGIN or PLUGIN3
						if (targetType.toUpperCase().startsWith("PLUGIN")) { //$NON-NLS-1$
							targetPath = "resource\\plugins\\"; //$NON-NLS-1$
						} else if (targetType.compareToIgnoreCase("PDL") == 0) { //$NON-NLS-1$
							targetPath = "resource\\printers\\"; //$NON-NLS-1$
						}
					}

					// get the bitmaps
					List<IMMPBitmap> bmps = mmpData.getBitmaps();
					for (IMMPBitmap bmp : bmps) {
						IPath mbmPath = bmp.getTargetFilePath().makeRelative();
						// if there's no target path then use the main target path
						if (mbmPath.segmentCount() == 1) {
							mbmPath = new Path(targetPath).append(mbmPath);
						}
						resources.put(dataZDir + mbmPath.toOSString(), "C:\\" + mbmPath.removeLastSegments(1).addTrailingSeparator().toOSString()); //$NON-NLS-1$
					}

					// get the user resources
					List<IPath> userResources = mmpData.getUserResources();
					for (IPath userRes : userResources) {
						addResourceLanguageTargets(resources, mmpData, null, 
								dataZDir + targetPath + userRes.removeFileExtension().lastSegment(), "C:\\" + targetPath); //$NON-NLS-1$ //$NON-NLS-2$
					}

					// get the system resources
					List<IPath> systemResources = mmpData.getSystemResources();
					for (IPath systemRes : systemResources) {
						addResourceLanguageTargets(resources, mmpData, null,
								dataZDir + targetPath + systemRes.removeFileExtension().lastSegment(), "C:\\" + targetPath); //$NON-NLS-1$ //$NON-NLS-2$
					}

					// get the resource blocks
					List<IMMPResource> resourceBlocks = mmpData.getResourceBlocks();
					for (IMMPResource resourceBlock : resourceBlocks) {
						IPath resPath = resourceBlock.getTargetPath();
						if (resPath == null) {
							// not specified in the resource block so use existing
							resPath = new Path(targetPath);
						}

						String filename = resourceBlock.getTargetFile();
						if (filename == null) {
							filename = resourceBlock.getSource().removeFileExtension().lastSegment();
						} else {
							filename = new Path(filename).removeFileExtension().toOSString();
						}
						
						if (resPath != null) {
							resPath = resPath.makeRelative().addTrailingSeparator();
							// adjust the path if necessary as it's different on the phone for the *_.reg file
							IPath adjustedTargetPath = resPath;
							if (adjustedTargetPath.toOSString().equalsIgnoreCase("private\\10003a3f\\apps\\")) { //$NON-NLS-1$
								adjustedTargetPath = new Path("private\\10003a3f\\import\\apps\\"); //$NON-NLS-1$
							}
							addResourceLanguageTargets(resources, mmpData, resourceBlock,
									dataZDir + resPath.toOSString() + filename, "C:\\" + adjustedTargetPath.toOSString()); //$NON-NLS-1$
						} else {
							CarbideBuilderPlugin.log(Logging.newStatus(CarbideBuilderPlugin.getDefault(), 
									IStatus.WARNING,
									"No TARGETPATH specified for resource " + filename + ".  Skipping..."));
						}
					}
					return null;
				}
		});

		return resources;
	}
	
	/**
	 * Get a map of the generated images from image makefiles for the project. 
	 * @param buildConfig the build configuration
	 * @return map of local full paths to target full paths
	 */
	public static HashMap<String, String> getHostAndTargetImages(final ICarbideBuildConfiguration buildConfig) {
		final HashMap<String, String> resources = new HashMap<String, String>();

		final ICarbideProjectInfo cpi = buildConfig.getCarbideProject();
		EpocEnginePlugin.runWithBldInfView(cpi.getWorkspaceRelativeBldInfPath(),
				new DefaultViewConfiguration(cpi.getProject(), buildConfig, new AcceptedNodesViewFilter()), 
				new BldInfViewRunnableAdapter() {

					public Object run(IBldInfView view) {
						EpocEnginePathHelper helper = new EpocEnginePathHelper(buildConfig);
						
						final String dataZDir = buildConfig.getSDK().getReleaseRoot().removeLastSegments(1).toOSString() + "\\Data\\z\\"; //$NON-NLS-1$

						for (IMakefileReference ref : view.getAllMakefileReferences()) {
							final IPath workspaceRelativeMakefilePath = helper.convertToWorkspace(ref.getPath());

							EpocEnginePlugin.runWithImageMakefileData(workspaceRelativeMakefilePath,
									new DefaultImageMakefileViewConfiguration(cpi, new AcceptedNodesViewFilter()),
									new ImageMakefileDataRunnableAdapter() {

										public Object failedLoad(CoreException exception) {
											// not an image make file
											return null;
										}

										public Object run(IImageMakefileData data) {
											// if we get here then it is an image make file
											for (IMultiImageSource imgSrc : data.getMultiImageSources()) {
												IPath targetPath = imgSrc.getTargetFilePath();
												
												// it can start with /epoc32/data/z/.  if so, remove it.
												if (targetPath != null && targetPath.segment(0).equalsIgnoreCase("epoc32")) {
													targetPath = targetPath.removeFirstSegments(3);
												}
												resources.put(dataZDir + targetPath.toOSString(), "C:\\" + targetPath.toOSString()); //$NON-NLS-1$
											}
											
											return null;
										}
								});
						}
						return null;
					}
				});
		
		return resources;
	}

	/**
	 * Get the user and system include paths referenced by MMPs in the
	 * given project and configuration.  The configuration may be null
	 * to get all the paths for all configurations.
	 * @param projectInfo the project info
	 * @param buildConfiguration the build configuration to check, or null for all
	 * @param userPaths list to which paths are added
	 * @param systemPaths list to which paths are added
	 */
	public static void getProjectIncludePaths(ICarbideProjectInfo projectInfo,
			ICarbideBuildConfiguration buildConfiguration,
			final List<File> userPaths, final List<File> systemPaths) {
		if (projectInfo == null)
			return;
		IMMPReference[] mmps = (IMMPReference[])
			EpocEnginePlugin.runWithBldInfData(
				projectInfo.getWorkspaceRelativeBldInfPath(),
				new DefaultViewConfiguration(projectInfo),
				new BldInfDataRunnableAdapter() {

					public Object failedLoad(CoreException exception) {
						return null;
					}

					public Object run(IBldInfData data) {
						return data.getAllMMPReferences();
					}
					
				});
		if (mmps == null)
			return;
		
		for (IMMPReference mmp : mmps) {
			if (buildConfiguration != null) {
				getMMPIncludePaths(projectInfo.getProject(), 
						mmp.getPath(), buildConfiguration, userPaths, systemPaths);
			} else {
				for (ICarbideBuildConfiguration buildConfig : projectInfo.getBuildConfigurations()) {
					getMMPIncludePaths(projectInfo.getProject(), 
							mmp.getPath(), buildConfig, userPaths, systemPaths);
				}
			}
		}
	}
	
	/**
	 * Gets the list of source files for one MMP file.
	 * @param info the project info of the project to get the source roots for
	 * @param mmpFile - The full path to the MMP file whose SOURCE files are to be retrieved
	 * @return list of workspace relative source files
	 */
	public static List<IPath> getSourceFilesForConfiguration(final ICarbideBuildConfiguration buildConfig, IPath inMMPPath) {
		final List<IPath> sourcePaths = new ArrayList<IPath>();
		final ICarbideProjectInfo  info = buildConfig.getCarbideProject();
		IMMPReference[] mmps = (IMMPReference[])EpocEnginePlugin.runWithBldInfData(info.getWorkspaceRelativeBldInfPath(),
			new DefaultViewConfiguration(info),
			new BldInfDataRunnableAdapter() {

				public Object failedLoad(CoreException exception) {
					return new IMMPReference[0];
				}

				public Object run(IBldInfData data) {
					return data.getAllMMPReferences();
				}
				
			});
		
		for (IMMPReference mmp : mmps) {
			EpocEnginePathHelper helper = new EpocEnginePathHelper(info.getProject());
			final IPath workspaceRelativeMMPPath = helper.convertToWorkspace(mmp.getPath());
			IPath fullMMPPath = helper.convertToFilesystem(mmp.getPath());
			String mmpName = inMMPPath.toOSString();
			String mmpName2 = fullMMPPath.toOSString();
			
			if (mmpName.toUpperCase().equals(mmpName2.toUpperCase())){
				EpocEnginePlugin.runWithMMPData(workspaceRelativeMMPPath, 
						new DefaultMMPViewConfiguration(info.getProject(), buildConfig, new AcceptedNodesViewFilter()), 
						new MMPDataRunnableAdapter() {

						public Object run(IMMPData mmpData) {
							MMPViewPathHelper helper = new MMPViewPathHelper(buildConfig);
							
							Set<IPath> topLevelSourcePaths = new HashSet<IPath>();

							for (IPath path : mmpData.getSources()) {
								IPath fullPath = helper.convertMMPToFilesystem(EMMPPathContext.SOURCE, path);
								if (fullPath != null) {
									// add the absolute source path
									topLevelSourcePaths.add(fullPath);
								}
							}
							
							// Now actually add the values to pass back
							for (IPath path : topLevelSourcePaths) {
								if (!sourcePaths.contains(path)) {
									sourcePaths.add(path);
								}
							}

							return null;
						}
				});
				
				break; // found MMP, no more work...
				
			} else {
				continue;
			}
			
			
		}
	
		return sourcePaths;
	}

	
	/**
	 * Gets the list of source roots for a project.  This is any folder that is a direct
	 * child of the project that contains (directly or indirectly) any source, resource, or
	 * header file.
	 * @param info the project info of the project to get the source roots for
	 * @return list of workspace relative source root paths
	 */
	public static List<IPath> getSourceRootsForProject(final ICarbideProjectInfo info) {
		final List<IPath> sourceRoots = new ArrayList<IPath>();
		final boolean showSourceRootsAtTopOfProject = CCorePlugin.showSourceRootsAtTopOfProject();

		IMMPReference[] mmps = (IMMPReference[])EpocEnginePlugin.runWithBldInfData(info.getWorkspaceRelativeBldInfPath(),
			new DefaultViewConfiguration(info),
			new BldInfDataRunnableAdapter() {

				public Object failedLoad(CoreException exception) {
					return new IMMPReference[0];
				}

				public Object run(IBldInfData data) {
					return data.getAllMMPReferences();
				}
				
			});
		
		boolean indexAll = getIndexAllPreference();
		List<String> buildComponents = null;
		if (!indexAll) // if indexAll, leave buildComponents as null because that will cause the same behavior
			buildComponents = info.isBuildingFromInf() ? null : info.getInfBuildComponents();
		for (IMMPReference mmp : mmps) {
			if (buildComponents != null && !containsIgnoreCase(buildComponents, mmp.getPath().lastSegment()))
				continue;
			
			EpocEnginePathHelper helper = new EpocEnginePathHelper(info.getProject());
			final IPath workspaceRelativeMMPPath = helper.convertToWorkspace(mmp.getPath());
			EpocEnginePlugin.runWithMMPData(workspaceRelativeMMPPath, 
					new DefaultMMPViewConfiguration(info, new AllNodesViewFilter()), 
					new MMPDataRunnableAdapter() {

					public Object run(IMMPData mmpData) {
						MMPViewPathHelper helper = new MMPViewPathHelper(info.getProject(), null);
						
						Set<IPath> topLevelSourcePaths = new HashSet<IPath>();

						// this covers sources, resource lists, and documents
						for (IPath path : mmpData.getEffectiveSourcePaths()) {
							IPath fullPath = helper.convertMMPToWorkspace(EMMPPathContext.SOURCEPATH, path);
							if (fullPath != null) {
								// add the absolute workspace path to the folder directly under the project.
								if (showSourceRootsAtTopOfProject) {
									// ideally we'd be more precise but then CDT creates a C folder named Foo\Bar,
									// rather than just expanding Foo and then seeing bar a C source folder.
									topLevelSourcePaths.add(fullPath.uptoSegment(2).makeAbsolute());
								}
								else // ideal
									topLevelSourcePaths.add(fullPath.makeAbsolute());
							}
						}

						// add potential sourcepaths for which source entries may not yet exist
						for (IPath path : mmpData.getRealSourcePaths()) {
							IPath fullPath = helper.convertMMPToWorkspace(EMMPPathContext.SOURCEPATH, path);
							if (fullPath != null) {
								// add the absolute workspace path to the folder directly under the project.
								if (showSourceRootsAtTopOfProject) {
									// ideally we'd be more precise but then CDT creates a C folder named Foo\Bar,
									// rather than just expanding Foo and then seeing bar a C source folder.
									topLevelSourcePaths.add(fullPath.uptoSegment(2).makeAbsolute());
								}
								else // ideal
									topLevelSourcePaths.add(fullPath.makeAbsolute());
							}
						}

						for (IPath path : mmpData.getUserIncludes()) {
							IPath fullPath = helper.convertMMPToWorkspace(EMMPPathContext.USERINCLUDE, path);
							if (fullPath != null) {
								// add the absolute workspace path to the folder directly under the project.
								if (showSourceRootsAtTopOfProject) {
									// ideally we'd be more precise but then CDT creates a C folder named Foo\Bar,
									// rather than just expanding Foo and then seeing bar a C source folder.
									topLevelSourcePaths.add(fullPath.uptoSegment(2).makeAbsolute());
								}
								else // ideal
									topLevelSourcePaths.add(fullPath.makeAbsolute());
							}
						}
						
						for (IPath path : mmpData.getSystemIncludes()) {
							IPath fullPath = helper.convertMMPToWorkspace(EMMPPathContext.SYSTEMINCLUDE, path);
							if (fullPath != null) {
								// add the absolute workspace path to the folder directly under the project.
								if (showSourceRootsAtTopOfProject) {
									// ideally we'd be more precise but then CDT creates a C folder named Foo\Bar,
									// rather than just expanding Foo and then seeing bar a C source folder.
									topLevelSourcePaths.add(fullPath.uptoSegment(2).makeAbsolute());
								}
								else // ideal
									topLevelSourcePaths.add(fullPath.makeAbsolute());
							}
						}
						
						for (IMMPResource resource : mmpData.getResourceBlocks()) {
							IPath fullPath = helper.convertMMPToWorkspace(EMMPPathContext.START_RESOURCE, resource.getSource());
							if (fullPath != null) {
								// add the absolute workspace path to the folder directly under the project.
								topLevelSourcePaths.add(fullPath.uptoSegment(2).makeAbsolute());
							}
						}

						for (IMMPAIFInfo aif : mmpData.getAifs()) {
							IPath fullPath = helper.convertMMPToWorkspace(EMMPPathContext.AIF_SOURCE, aif.getResource());
							if (fullPath != null) {
								// add the absolute workspace path to the folder directly under the project.
								topLevelSourcePaths.add(fullPath.uptoSegment(2).makeAbsolute());
							}
						}

						for (IPath path : topLevelSourcePaths) {
							if (!sourceRoots.contains(path)) {
								sourceRoots.add(path);
							}
						}
						
						return null;
					}
			});
		}
	
		return sourceRoots;
	}
	
	private static boolean getIndexAllPreference() {
		// Can't access this pref from the project ui plugin because it would cause a circular dependency
		Plugin plugin = Platform.getPlugin("com.nokia.carbide.cpp.project.ui"); //$NON-NLS-1$
		if (plugin == null) {
			CarbideBuilderPlugin.log(Logging.newStatus(CarbideBuilderPlugin.getDefault(), 
					IStatus.WARNING, "Could not find project UI plugin!"));
			return true;
		}
		return plugin.getPluginPreferences().getBoolean("indexAll"); //$NON-NLS-1$
	}

	private static boolean containsIgnoreCase(List<String> list, String s) {
		for (String string : list) {
			if (string.equalsIgnoreCase(s))
				return true;
		}
		return false;
	}

	/**
	 * Get the list of all mmp file paths for any and all build configurations of a project.
	 * @param projectInfo
	 * @return list of full filesystem mmp paths, may be empty
	 */
	public static List<IPath> getMMPFilesForProject(final ICarbideProjectInfo projectInfo) {
		final List<IPath> mmps = new ArrayList<IPath>();
		
		final IPath workspaceRelativeldInfPath = projectInfo.getWorkspaceRelativeBldInfPath();

		EpocEnginePlugin.runWithBldInfData(workspaceRelativeldInfPath,
			new DefaultViewConfiguration(projectInfo.getProject(), null, new AllNodesViewFilter()), 
			new BldInfDataRunnableAdapter() {

				public Object run(IBldInfData data) {
					EpocEnginePathHelper helper = new EpocEnginePathHelper(projectInfo.getProject());

					for (IMMPReference mmp : data.getAllMMPReferences()) {
						IPath fullPath = helper.convertToFilesystem(mmp.getPath());
						mmps.add(fullPath);
					}
					return null;
				}
			});

		return mmps;
	}

	/**
	 * Get the list of all mmp file paths that are applicable to the given build configuration.
	 * @param buildConfig
	 * @return list of workspace relative mmp paths, may be empty
	 */
	public static List<IPath> getMMPFilesForBuildConfiguration(final ICarbideBuildConfiguration buildConfig) {
		final List<IPath> mmps = new ArrayList<IPath>();
		
		final IPath workspaceRelativeldInfPath = buildConfig.getCarbideProject().getWorkspaceRelativeBldInfPath();

		EpocEnginePlugin.runWithBldInfData(workspaceRelativeldInfPath,
			new DefaultViewConfiguration(buildConfig.getCarbideProject().getProject(), buildConfig, new AcceptedNodesViewFilter()), 
			new BldInfDataRunnableAdapter() {

				public Object run(IBldInfData data) {
					EpocEnginePathHelper helper = new EpocEnginePathHelper(buildConfig.getCarbideProject().getProject());

					for (IMMPReference mmp : data.getAllMMPReferences()) {
						IPath workspaceRelativeMMPPath = helper.convertToWorkspace(mmp.getPath());
						mmps.add(workspaceRelativeMMPPath);
					}
					return null;
				}
			});

		return mmps;
	}
	
	/**
	 * Get the list of all macros defined in the specified mmp file supported by specified
	 * build configuration.  These are found in MACRO statements in the mmp files.
	 * @param mmpPath The workspace relative path of the mmp file to get the macros for.
	 * @param buildConfig The build config context.
	 * @return List of macro strings which may be empty.  There is no macro value, only a
	 * string like "FOO".
	 */
	public static List<String> getMMPMacrosForBuildConfiguration(final IPath workspaceRelativeMMPPath, final ICarbideBuildConfiguration buildConfig) {
		final List<String> macros = new ArrayList<String>();
		
		EpocEnginePlugin.runWithMMPData(workspaceRelativeMMPPath, 
				new DefaultMMPViewConfiguration(buildConfig.getCarbideProject().getProject(), buildConfig, new AcceptedNodesViewFilter()), 
				new MMPDataRunnableAdapter() {

				public Object run(IMMPData mmpData) {
					Map<EMMPStatement, List<String>> listArgumentSettings = mmpData.getListArgumentSettings();
					List<String> macroList = listArgumentSettings.get(EMMPStatement.MACRO);
					for (String macro : macroList) {
						if (!macros.contains(macro)) {
							macros.add(macro);
						}
					}
					return null;
				}
		});
		
		return macros;
	}


	/**
	 * Get the resolved user and system include paths from the given MMP 
	 * and build context.
	 * @param project the project, must not be null
	 * @param mmp the project-relative MMP path, may not be null
	 * @param buildContext the build context, may not be null
	 * @param userPaths list of user paths to append to, may not be null
	 * @param systemPaths list of system paths to append to, may not be null
	 */
	public static void getMMPIncludePaths(final IProject project, 
			IPath mmp, final ICarbideBuildConfiguration buildConfiguration,
			final List<File> userPaths, final List<File> systemPaths) {
		IMMPViewConfiguration viewConfiguration = new DefaultMMPViewConfiguration(
				project, buildConfiguration, new AcceptedNodesViewFilter());
		
		final IPath epocRoot = new Path(buildConfiguration.getSDK().getEPOCROOT());
		EpocEnginePlugin.runWithMMPData(new Path(project.getName()).append(mmp), 
				viewConfiguration, 
			new MMPDataRunnableAdapter() {

				public Object failedLoad(CoreException exception) {
					return null;
				}

				public Object run(IMMPData data) {
					MMPViewPathHelper helper = new MMPViewPathHelper(data, epocRoot);
					for (IPath path : data.getUserIncludes()) {
						IPath full = helper.convertMMPToFilesystem(EMMPPathContext.USERINCLUDE, path);
						if (full != null) {
							File resolved = full.toFile();
							if (!userPaths.contains(resolved))
								userPaths.add(resolved);
						}
					}
					for (IPath path : data.getSystemIncludes()) {
						IPath full = helper.convertMMPToFilesystem(EMMPPathContext.SYSTEMINCLUDE, path);
						if (full != null) {
							File resolved = full.toFile();
							if (!systemPaths.contains(resolved))
								systemPaths.add(resolved);
						}
					}
					
					// add \epoc32\include\stdapis as a system include when target type is STDLIB, STDDLL or STDEXE
					String targetType = data.getSingleArgumentSettings().get(EMMPStatement.TARGETTYPE);
					if (targetType != null) {
						if (targetType.toUpperCase().matches("STDLIB|STDDLL|STDEXE")) { //$NON-NLS-1$
							File resolved = epocRoot.append("epoc32\\include\\stdapis").toFile(); //$NON-NLS-1$
							if (!systemPaths.contains(resolved))
								systemPaths.add(resolved);
						}
					}
					return null;
				}
		});
	}

	/**
	 * Return the set of directories containing SDK libraries for a build context,
	 * taking into account the SDK, platform and target.
	 */
	public static IPath[] getLibDirectoriesForBuildContext(ISymbianBuildContext buildContext) {
		ArrayList<IPath> dirList = new ArrayList<IPath>();
		ISymbianSDK sdk = buildContext.getSDK();
		IPath releaseRoot = sdk.getReleaseRoot();
		String platformString = buildContext.getPlatformString();
		boolean isDebug = ISymbianBuildContext.DEBUG_TARGET.equals(buildContext.getTargetString());
		// TODO is this correct, what about ARMv6?
		boolean isARMv5 = ISymbianBuildContext.ARMV5_PLATFORM.equals(platformString) ||
		ISymbianBuildContext.ARMV5_ABIV2_PLATFORM.equals(platformString);
		boolean isGCCE = ISymbianBuildContext.GCCE_PLATFORM.equals(platformString);
		if (isARMv5 || isGCCE) {
			if (isDebug) {
				dirList.add(releaseRoot.append("ARMv5\\UDEB\\")); //$NON-NLS-1$
				dirList.add(releaseRoot.append("ARMv5\\UREL\\")); //$NON-NLS-1$
				dirList.add(releaseRoot.append("ARMv5\\LIB\\")); //$NON-NLS-1$
			}
			else {
				dirList.add(releaseRoot.append("ARMv5\\UREL\\"));			 //$NON-NLS-1$
				dirList.add(releaseRoot.append("ARMv5\\LIB\\")); //$NON-NLS-1$
			} 
		} else {
			if (isDebug) {
				dirList.add(releaseRoot.append(platformString + "\\UDEB\\")); //$NON-NLS-1$
				dirList.add(releaseRoot.append(platformString + "\\UREL\\")); //$NON-NLS-1$
			}
			else {
				dirList.add(releaseRoot.append(platformString + "\\UREL\\"));			 //$NON-NLS-1$
			} 
		}
		return dirList.toArray(new IPath[dirList.size()]);
	}
	
	// regex to match on library file extension
	static final Pattern EXTENSION_FILTER = Pattern.compile("^\\S*\\.lib$"); //$NON-NLS-1$
	// regex to exclude file names of the form foo{xxxxxxxx}.lib
	static final Pattern EXCLUSION_FILTER = Pattern.compile("^\\S*\\{[0-9a-fA-F]{8,8}\\}\\.lib"); //$NON-NLS-1$

	/**
	 * Return the unique libraries appropriate to the given build configuration,
	 * taking into account the SDK, platform and target.
	 */	
	public static Set<String> getSDKLibrariesForBuildContext(ISymbianBuildContext buildContext) {
		final HashSet<String> libs = new HashSet<String>();
		IPath[] libDirs = getLibDirectoriesForBuildContext(buildContext);
		for (IPath libPath : libDirs) {
			File libDir = libPath.toFile();
			if (libDir != null && libDir.isDirectory()) {
				String[] libNames = libDir.list(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						boolean result = false;
						Matcher extMatcher = EXTENSION_FILTER.matcher(name);
						if (extMatcher.matches()) {
							Matcher excludeMatcher = EXCLUSION_FILTER.matcher(name);
							result = !excludeMatcher.matches();
						}
						return result;
					}
				});
				for (String libName : libNames) {
					libs.add(libName);
				}
			}
		}
		return libs;
	}
	
	/**
	 * Get the MMP file that is the parent to a given source file for a given project. A source file is defined
	 * to be any of the MMP keywords: SOURCE, RESOURCE, SYSTEMRESOURCE, START RESOURCE, 
	 * @param project - The IProject with the sourcePath belongs
	 * @param sourcePath - The absolute file system path of the source file that is part of the IProject
	 * @return A list of project relative IPath entries of found MMP files that contain 'sourcePath' (may be empty).
	 */
	public static List<IPath> getMMPsForSource(final IProject project, final IPath sourcePath){
		
		final ICarbideProjectInfo info = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		final ICarbideBuildConfiguration buildConfig = info.getDefaultConfiguration();
		final List<IPath>mmpPaths = new ArrayList<IPath>();
		EpocEnginePlugin.runWithBldInfData(info.getWorkspaceRelativeBldInfPath(), 
				new DefaultViewConfiguration(info.getProject(), buildConfig, new AcceptedNodesViewFilter()), 
				new BldInfDataRunnableAdapter() {

					public Object run(IBldInfData infView) {
						for (final IMMPReference mmp : infView.getAllMMPReferences()) {
								final IPath workspaceRelativeMMPPath = new Path(info.getProject().getName()).append(mmp.getPath());
								
								EpocEnginePlugin.runWithMMPData(workspaceRelativeMMPPath, 
										new DefaultMMPViewConfiguration(info.getProject(), buildConfig, new AcceptedNodesViewFilter()),
										new MMPDataRunnableAdapter() {

										public Object run(IMMPData mmpData) {
											// read the project-wide target path
											 List<IPath> sourcePaths = mmpData.getSources();
											MMPViewPathHelper helper = new MMPViewPathHelper(mmpData, null);
											for (IPath currSrc : sourcePaths){
												IPath fullPath = helper.convertMMPToFilesystem(
														EMMPPathContext.SOURCE, currSrc);
												//System.out.print("\nSOURCE: " + fullPath.toOSString());
												if (fullPath.equals(sourcePath)){
													mmpPaths.add(mmp.getPath());
													return null;
												}
											}
											
											List<IPath> resourcePaths = mmpData.getUserResources();
											for (IPath currRSrc : resourcePaths){
												IPath fullPath = helper.convertMMPToFilesystem(
														EMMPPathContext.RESOURCE, currRSrc);
												//System.out.print("\nEKA1 User Resource: " + fullPath.toOSString());
												if (fullPath.equals(sourcePath)){
													mmpPaths.add(mmp.getPath());
													return null;
												}
											}
											
											List<IPath> systemResourcePaths = mmpData.getSystemResources();
											for (IPath currRSrc : systemResourcePaths){
												IPath fullPath = helper.convertMMPToFilesystem(
														EMMPPathContext.SYSTEMRESOURCE, currRSrc);
												//System.out.print("\nEKA1 System Resource: " + fullPath.toOSString());
												if (fullPath.equals(sourcePath)){
													mmpPaths.add(mmp.getPath());
													return null;
												}
											}
											
											List<IMMPResource> newResourcePaths = new ArrayList<IMMPResource>();
											newResourcePaths = mmpData.getResourceBlocks();
											for(IMMPResource currMMPRsrc : newResourcePaths){
												IPath rsrcPath = currMMPRsrc.getSource();
												IPath fullPath = helper.convertMMPToFilesystem(
														EMMPPathContext.START_RESOURCE , rsrcPath);
												//System.out.print("\nEKA2 Resource: " + fullPath.toOSString());
												if (fullPath.equals(sourcePath)){
													mmpPaths.add(mmp.getPath());
													return null;
												}
											}
											
											return null;
										}
								});
							
						}
						return null;
					}
			});
		
		return mmpPaths;
	}	
	
	/**
	 * Returns host paths to all the executables built by the project for the default build configuration.
	 * @param info project info
	 * @param allExePaths all the executable paths from all the mmps in the inf file for the
	 * default build configuration
	 * @param currBuiltExePaths all the executables that are actually being built for the default
	 * build configuration
	 */
	public static void getPathToAllExecutables(final ICarbideProjectInfo info,
												final List<IPath> allExePaths,
												final List<IPath> currBuiltExePaths) {
		getPathToAllExecutables(info.getDefaultConfiguration(), allExePaths, currBuiltExePaths,
				new ArrayList<IPath>(), new ArrayList<IPath>());
	}

	/**
	 * Returns host paths to all the executables built by the project for the given build configuration.  Also
	 * returns the mmp paths as well.
	 * @param buildConfig 		- 	current build configuration
	 * @param allExePaths 		- 	all the executable paths from all the mmp's that could be built for the given build configuration
	 * @param currBuiltExePaths - 	all the executable paths that are built for the given build configuration
	 * @param allMMPPaths 		- 	all the mmp paths from all the mmp's that could be built for the given build configuration
	 * @param currBuiltMMPPaths - 	all the mmp paths that are built for the given build configuration
	 * 
	 */
	public static void getPathToAllExecutables(final ICarbideBuildConfiguration buildConfig,
												final List<IPath> allExePaths,
												final List<IPath> currBuiltExePaths,
												final List<IPath> allMMPPaths,
												final List<IPath> currBuiltMMPPaths) {
		if (buildConfig == null)
			return;
		
		final ICarbideProjectInfo cpi = buildConfig.getCarbideProject();
		
		EpocEnginePlugin.runWithBldInfData(cpi.getWorkspaceRelativeBldInfPath(), 
				new DefaultViewConfiguration(cpi, buildConfig), 
				new BldInfDataRunnableAdapter() {

					public Object run(IBldInfData infView) {
						
						EpocEnginePathHelper helper = new EpocEnginePathHelper(cpi.getProject());

						// first calculate the paths to all binaries, built or not built
						for (IMakMakeReference mmp : infView.getAllMMPReferences()) {
							IPath workspaceRelativeMMPPath = helper.convertToWorkspace(mmp.getPath());
							IPath path = getHostPathForExecutable(buildConfig, workspaceRelativeMMPPath);
							if (path != null) {
								allExePaths.add(path);
								allMMPPaths.add(workspaceRelativeMMPPath);
							}
						}
						
						// now get the paths to only the binaries that are being built
						if (cpi.isBuildingFromInf()) {
							// get all regular mmp binaries
							for (IMakMakeReference mmp : infView.getMakMakeReferences()) {
								if (!(mmp instanceof IMMPReference))
									continue;
								IPath workspaceRelativeMMPPath = helper.convertToWorkspace(mmp.getPath());
								IPath path = getHostPathForExecutable(buildConfig, workspaceRelativeMMPPath);
								if (path != null) {
									currBuiltExePaths.add(path);
									currBuiltMMPPaths.add(workspaceRelativeMMPPath);
								}
							}
							
							if (cpi.isBuildingTestComps()) {
								// get all test mmp binaries
								for (IMakMakeReference mmp : infView.getTestMakMakeReferences()) {
									if (!(mmp instanceof IMMPReference))
										continue;
									IPath workspaceRelativeMMPPath = helper.convertToWorkspace(mmp.getPath());
									IPath path = getHostPathForExecutable(buildConfig, workspaceRelativeMMPPath);
									if (path != null) {
										currBuiltExePaths.add(path);
										currBuiltMMPPaths.add(workspaceRelativeMMPPath);
									}
								}
							}
						} else {
							// building a subset of regular and test components.  only get those binaries.
							IMMPReference[] mmps = infView.getAllMMPReferences();
							for (String component : cpi.getInfBuildComponents()) {
								for (IMakMakeReference mmp : mmps) {
									if (mmp.getPath().lastSegment().compareToIgnoreCase(component) == 0) {
										IPath workspaceRelativeMMPPath = helper.convertToWorkspace(mmp.getPath());
										IPath path = getHostPathForExecutable(buildConfig, workspaceRelativeMMPPath);
										if (path != null) {
											currBuiltExePaths.add(path);
											currBuiltMMPPaths.add(workspaceRelativeMMPPath);
										}
									}
								}
							}
						}
						
						return null;
					}
			});		
	}
	
	/**
	 * Gets a list of upper case target type strings for the given build configuration.  There will be no
	 * duplicates in the list, and the list may be empty.
	 * @param buildConfig the build configuration to use as a filter when parsing the bld.inf/mmp files
	 * @return list of target type strings
	 */
	public static List<String> getTargetTypesForBuildConfiguration(final ICarbideBuildConfiguration buildConfig) {
		final List<String> targetTypes = new ArrayList<String>();
		
		for (IPath mmpPath : getMMPFilesForBuildConfiguration(buildConfig)) {
			
			EpocEnginePlugin.runWithMMPData(mmpPath, 
					new DefaultMMPViewConfiguration(buildConfig.getCarbideProject().getProject(), buildConfig, new AcceptedNodesViewFilter()), 
					new MMPDataRunnableAdapter() {

					public Object run(IMMPData mmpData) {
						String targetType = mmpData.getSingleArgumentSettings().get(EMMPStatement.TARGETTYPE);
						if (targetType != null) {
							targetType = targetType.toUpperCase();
							if (!targetTypes.contains(targetType)) {
								targetTypes.add(targetType);
							}
						}
						return null;
					}
			});
		}

		return targetTypes;
	}
	
	/**
	 * Tell if the given bld.inf builds against variant platforms.
	 * @return true if the bld.inf exists and VARIANT platform is specified, else false
	 */
	public static boolean isVariantBldInf(IPath bldInfFullPath) {
		Boolean result = (Boolean) EpocEnginePlugin.runWithBldInfData(bldInfFullPath, 
			new DefaultViewConfiguration(bldInfFullPath, new AcceptedNodesViewFilter()), 
			new BldInfDataRunnableAdapter() {

				public Object run(IBldInfData data) {
					for (String platform : data.getPlatforms()) {
						if (platform.equalsIgnoreCase("VARIANT")) //$NON-NLS-1$
							return true;
					}
					return false;
				}
		});
		return result != null ? result.booleanValue() : false; 
	}

	/**
	 * Tell if given MMP builds variant executables under any configuration.
	 * <p>
	 * Note: the variant is only built if the bld.inf is variant as well.  That is not checked here.
	 * @param projectInfo the project to check
	 * @param projectRelativeMMPPath the MMP location in the project 
	 * @return true if the MMP exists and the VAR keyword is used, else false
	 * @see #isVariantBldInf(IPath)
	 */
	public static boolean isVariantMMP(ICarbideProjectInfo projectInfo, IPath projectRelativeMMPPath) {
		Boolean result = (Boolean) EpocEnginePlugin.runWithMMPData(
			projectRelativeMMPPath, 
			new DefaultMMPViewConfiguration(
					projectInfo,
					new AllNodesViewFilter()),
			new MMPDataRunnableAdapter() {
				public Object run(IMMPData data) {
					return data.getSingleArgumentSettings().containsKey(EMMPStatement.VAR);
				}
		});
		return result != null ? result.booleanValue() : false;
	}
	
	/**
	 * Tell if given MMP has the FEATUREVARIANT keyword
	 * 
	 * @param projectInfo the current Carbide project
	 * @param relativeMMPPath -project relative path to the MMP file we need to inspect
	 * 
	 */
	public static boolean hasFeatureVariantKeyword(ICarbideProjectInfo projectInfo, final IPath relativeMMPPath) {
		Boolean result = (Boolean) EpocEnginePlugin.runWithMMPData(
				relativeMMPPath, 
			new DefaultMMPViewConfiguration(
					projectInfo,
					new AllNodesViewFilter()),
			new MMPDataRunnableAdapter() {
				public Object run(IMMPData data) {
					return data.getFlags().contains(EMMPStatement.FEATUREVARIANT);
				}
		});
		return result != null ? result.booleanValue() : false;
	}
	
	/**
	 * A particular MMP file is considered to be a Symbian Binary Variation iff the MMP file
	 * has the keyword "FEATUREVARIANT" flag defined and the build configuration has a valid
	 * build variant target in it's configuration name.
	 * @param mmpData - The data for the MMP file we want to inspect
	 * @param project - The project we need to get the build configuration name from
	 * @return true if a variant executable will be built for this mmp
	 */
	private static boolean isFeatureVariantMMP(IMMPData mmpData, IProject project){
		
		boolean isFeatureVariant = false;
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi != null){
			ICarbideBuildConfiguration defaultConfig = cpi.getDefaultConfiguration();
			if (defaultConfig != null){
				if (defaultConfig.getBuildVariationName().length() > 0 && 
					mmpData.getFlags().contains(EMMPStatement.FEATUREVARIANT)) {
					
					isFeatureVariant = true;
				}
			}
			
		}
		
		return isFeatureVariant;
	}
	
	/**
	 * If the given MMP builds variant executables, return its current target.
	 * Returns the normal target if not variant.
	 * <p>
	 * Note: the variant is only built if the bld.inf is variant as well.  That is not checked here.
	 * @param buildConfiguration the build configuration under which to parse the MMP
	 * @param projectRelativeMMPPath the MMP location in the project 
	 * @return the epocroot-relative target path.  Only null if no target defined in MMP at all.
	 * @see #isVariantBldInf(IPath)
	 */
	public static IPath getVariantMMPTarget(final ICarbideBuildConfiguration buildConfiguration, final IPath projectRelativeMMPPath) {
		IPath result = (IPath) EpocEnginePlugin.runWithMMPData(
			projectRelativeMMPPath, 
			new DefaultMMPViewConfiguration(
					buildConfiguration,
					new AcceptedNodesViewFilter()),
			new MMPDataRunnableAdapter() {
				public Object run(IMMPData data) {
					return getBinaryVariantTargetName(data, data.getTargetFilePath(), buildConfiguration.getCarbideProject().getProject());
				}
		});
		return result;
	}
	
	/**
	 * Get the target name with a variant suffix inserted.
	 * @param mmpData
	 * @param target
	 * @return updated target name
	 * 
	 * @deprecated Deprecated in 2.1. Use {@link #getBinaryVariantTargetName(IMMPData mmpData, IPath target, IProject project)} instead.
	 */
	private static IPath getVariantTargetName(IMMPData mmpData, IPath target) {
		if (target == null)
			return null;
		
		String suffix = mmpData.getSingleArgumentSettings().get(EMMPStatement.VAR);
		if (suffix != null) {
			target = target.removeFileExtension().addFileExtension(suffix + "." + FileUtils.getSafeFileExtension(target)); //$NON-NLS-1$
		}
		
		return target;
	}
	
	/**
	 * Get the target name with a variant suffix inserted. Since 2.1 this includes support for Symbian Binary Variation builds
	 * that have the FEATUREVARIANT keyword in their MMP file.
	 * @param mmpData
	 * @param target
	 * @return updated target name. This may return null for Symbian Binary Variation builds that do not yet have calcualted target files names (i.e. no makefiles)
	 */
	private static IPath getBinaryVariantTargetName(IMMPData mmpData, IPath target, IProject project) {
		if (target == null)
			return null;
		
		if (isFeatureVariantMMP(mmpData, project)) {
			
			// get the Symbian Binary Variation Name
			
			if (CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(project)){
				// raptor is not yet supported
				return null;
			}
			
			ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			if (cpi != null){
				
				// construct the location of the binary variant makefile.
				ICarbideBuildConfiguration defaultConfig = cpi.getDefaultConfiguration();
				File realMakeFile = getMakeFileForSymbianBinaryVariant(mmpData, defaultConfig);	
				
				String MD5Name = getMD5HashForBinaryVariant(defaultConfig, mmpData.getModelPath());
				
				if (MD5Name != null && MD5Name.length() > 0){
					//System.out.println("The target is: " + target.removeFileExtension().addFileExtension(MD5Name + "." + FileUtils.getSafeFileExtension(target)).toOSString()); //$NON-NLS-1$
					return 	target = target.removeFileExtension().addFileExtension(MD5Name + "." + FileUtils.getSafeFileExtension(target)); //$NON-NLS-1$
				} else {
					// The MD5 may not be able to be calculated
					return null;
				}
				
			} else {
				return null;
			}
			
		}
		else {
			String suffix = mmpData.getSingleArgumentSettings().get(EMMPStatement.VAR);
			if (suffix != null) {
				target = target.removeFileExtension().addFileExtension(suffix + "." + FileUtils.getSafeFileExtension(target)); //$NON-NLS-1$
			}
		}
		return target;
	}
	
	/**
	 * Get the MD5 hash for a configuration if it is a true binary variation.
	 * @param config - The Carbide build configuration to check against
	 * @param mmpFullPath - The path to the MMP file that builds the binary
	 * @return A string of the MD5 hash. Returns an empty string if the configuration is not a binary variant or cannot be determined.
	 */
	static public String getMD5HashForBinaryVariant(final ICarbideBuildConfiguration config, final IPath mmpFullPath){
		
		return (String)EpocEnginePlugin.runWithMMPData(mmpFullPath, 
				new DefaultMMPViewConfiguration(config, new AcceptedNodesViewFilter()),
				new MMPDataRunnableAdapter() {

				public Object run(IMMPData mmpData) {
					
					String md5 = "";
					
					File makefile = getMakeFileForSymbianBinaryVariant(mmpData, config);
					if (makefile != null && makefile.exists()){
						md5 = getMD5VariantFromMakefile(makefile, config);
					}

					return md5;	
				}
		});								
	}
	
	
	private static File getMakeFileForSymbianBinaryVariant(IMMPData mmpData, ICarbideBuildConfiguration config){
		
		IPath makefileDir = CarbideCPPBuilder.getBuilderMakefileDir(config);
		
		IPath mmpFile = mmpData.getModelPath();
		mmpFile = mmpFile.removeFileExtension();
		String mmpRootName = mmpFile.lastSegment();
		String plat = config.getPlatformString();
		String basePlat = config.getBasePlatformForVariation();
		String variantPlat = config.getBuildVariationName();
		
		if (variantPlat.length() == 0){
			plat = plat + "." + ISymbianBuildContext.DEFAULT_VARIANT;
		}
		
		String makefilePath = makefileDir.toOSString() + File.separator + mmpRootName + File.separator + basePlat + File.separator;
		String variantMakeFileName = mmpRootName + "." + plat;
		File realMakeFile = new File(makefilePath + variantMakeFileName);
		
		return realMakeFile;
	}
	
	/**
	 * Get the MD5 hash value for an existing configuration by parsing its makefile.
	 * @param makefile - The build makefile to parse to check for the MD5
	 * @param config - The build configuration to check under
	 * @return The string for the MD5 hash. An empty string if it cannot be determined.
	 */
	private static String getMD5VariantFromMakefile(File makefile, ICarbideBuildConfiguration config){
		// TODO: We can also use the .vmap in the release folder as well.
		// we can parse the makefile and get the variant name for each in the comments:
		//
		//# FeatureVariantURELLabel d41d8cd98f00b204e9800998ecf8427e
		//# FeatureVariantUDEBLabel d41d8cd98f00b204e9800998ecf8427e
		String MD5Str = "";

		if (makefile.exists()){
			String text = "";
			try {
				text = new String(FileUtils.readFileContents(makefile, null));
			} catch (CoreException e) {
				e.printStackTrace();
			}
			
			String searchString = "";
			if (config.getTargetString().equals(SymbianBuildContext.DEBUG_TARGET)){
				searchString = "# FeatureVariantUDEBLabel";
			} else {
				searchString = "# FeatureVariantURELLabel";
			}
			
			for (String line : text.split("\n")) {
				if (line.startsWith(searchString)){
					String tokens[] = line.split(" ");
					if (tokens.length == 3){
						MD5Str = tokens[2];
						break;
					}
				}
			}
			
		} 
		
		return MD5Str.trim();
	}
	
	/**
	 * If the given MMP builds variant executables, return all the variant targets it builds.
	 * <p>
	 * Note: the variant is only built if the bld.inf is variant as well.  That is not checked here.
	 * @param buildConfiguration the build configuration under which to parse the MMP
	 * @param projectRelativeMMPPath the MMP location in the project 
	 * @return the array of unique variant targets
	 * @see #isVariantBldInf(IPath)
	 */
	public static IPath[] getVariantMMPTargets(final ICarbideProjectInfo projectInfo, IPath projectRelativeMMPPath) {
		Set<IPath> targets = new HashSet<IPath>();
		for (ICarbideBuildConfiguration buildConfiguration : projectInfo.getBuildConfigurations()) {
			IPath targetPath = getVariantMMPTarget(buildConfiguration, projectRelativeMMPPath);
			if (targetPath != null) {
				targets.add(targetPath);
			}
		}
		return (IPath[]) targets.toArray(new IPath[targets.size()]);
	}	
	
	/**
	 * Get all the #include files, plus self, referenced by a given MMP file in either
	 * a specific build configuration or all build configurations.
	 * @param projectInfo info for the project being considered 
	 * @param buildConfig build configuration, or <code>null</code> for all configurations
	 * @param mmpPath the full filesystem path to the MMP
	 * @param pathList collection of full filesystem paths, updated with results
	 */
	public static void addIncludedFilesFromMMP(ICarbideProjectInfo projectInfo, ICarbideBuildConfiguration buildConfig, IPath mmpPath, final Collection<IPath> pathList) {
		DefaultMMPViewConfiguration viewConfig = buildConfig != null ?
				new DefaultMMPViewConfiguration(buildConfig, new AcceptedNodesViewFilter()) :
					new DefaultMMPViewConfiguration(projectInfo, new AllNodesViewFilter());
		EpocEnginePlugin.runWithMMPData(mmpPath, 
				viewConfig,
				new MMPDataRunnableAdapter() {
					public Object run(IMMPData data) {
						for (IPath path : data.getReferencedFiles()) {
							pathList.add(path);
						}
						return null;
					}
		});
	}
	
	/**
	 * Get all the #include files, plus self, referenced by a given MMP file in any
	 * configuration.
	 * @param projectPath full path of the project 
	 * @param mmpPath the full filesystem path to the MMP
	 * @param pathList collection of full filesystem paths, updated with results
	 */
	public static void addIncludedFilesFromMMP(IPath projectPath, IPath mmpPath, final Collection<IPath> pathList) {
		EpocEnginePlugin.runWithMMPData(mmpPath, 
				new DefaultMMPViewConfiguration(projectPath), 
				new MMPDataRunnableAdapter() {

					public Object run(IMMPData data) {
						for (IPath path : data.getReferencedFiles()) {
							pathList.add(path);
						}
						return null;
					}
		});
	}
	
	/**
	 * Get all the #include files, plus self, referenced by a given bld.inf file in either
	 * a specific build configuration or all build configurations.
	 * @param projectInfo info for the project being considered 
	 * @param buildConfig the build configuration, or <code>null</code> for all configurations
	 * @param bldinfPath the full filesystem path to the bld.inf
	 * @param pathList collection of full filesystem paths, updated with results
	 */
	public static void addIncludedFilesFromBldInf(ICarbideProjectInfo projectInfo, ICarbideBuildConfiguration buildConfig, IPath bldinfPath, final Collection<IPath> pathList) {
		DefaultViewConfiguration viewConfig = buildConfig != null ?
				new DefaultViewConfiguration(projectInfo, buildConfig) :
					new DefaultViewConfiguration(projectInfo);
		EpocEnginePlugin.runWithBldInfData(bldinfPath, 
				viewConfig, 
				new BldInfDataRunnableAdapter() {

					public Object run(IBldInfData data) {
						for (IPath path : data.getReferencedFiles()) {
							pathList.add(path);
						}
						return null;
					}
		});
	}
	
	/**
	 * Get all the #include files, plus self, referenced by a given bld.inf file in any
	 * configuration.
	 * @param projectPath the full filesystem path to the project 
	 * @param bldinfPath the full filesystem path to the bld.inf
	 * @param pathList collection of full filesystem paths, updated with results
	 */
	public static void addIncludedFilesFromBldInf(IPath projectPath, IPath bldinfPath, final Collection<IPath> pathList) {
		EpocEnginePlugin.runWithBldInfData(bldinfPath, 
				new DefaultViewConfiguration(projectPath), 
				new BldInfDataRunnableAdapter() {

					public Object run(IBldInfData data) {
						for (IPath path : data.getReferencedFiles()) {
							pathList.add(path);
						}
						return null;
					}
		});
	}
	
	/**
	 * Returns a list of absolute host paths for any files in the given pkg file.
	 * @param pkgPath absolute path to the pkg file
	 * @param buildConfig build configuration context
	 * @param sisInfo optional sisFinfo.  content search location is used from this to find relative paths and
	 * filenames.  when null, the first sis info for the build configuration that matches the pkg file will be
	 * used.
	 * @return list of absolute paths to files used in pkg
	 */
	public static List<IPath> getFilesInPKG(IPath pkgPath, final ICarbideBuildConfiguration buildConfig, final ISISBuilderInfo sisInfo) {
		final List<IPath> filePaths = new ArrayList<IPath>();
		
		PKGModelHelper.runWithPKGView(pkgPath, 
				new DefaultViewConfiguration(buildConfig.getCarbideProject(), buildConfig), 
				new PKGViewRunnableAdapter() {

				public Object run(IPKGView view) {
					PKGViewPathHelper helper = new PKGViewPathHelper(view, buildConfig);
					if (sisInfo != null) {
						helper.setSISBuilderInfo(sisInfo);
					}

					for (IPKGInstallFile file : view.getAllInstallFiles()) {
						Map<EPKGLanguage, IPath> sourceFiles = file.getSourceFiles();
						
						for (EPKGLanguage language : sourceFiles.keySet()) {
							IPath path = helper.getAbsolutePathFromViewPath(sourceFiles.get(language));
							if (path != null) {
								filePaths.add(path);
							}
						}
					}

					for (IPKGEmbeddedSISFile file : view.getAllEmbeddedSISFiles()) {
						Map<EPKGLanguage, IPath> sourceFiles = file.getSourceFiles();
						
						for (EPKGLanguage language : sourceFiles.keySet()) {
							IPath path = helper.getAbsolutePathFromViewPath(sourceFiles.get(language));
							if (path != null) {
								filePaths.add(path);
							}
						}
					}

					return null;
				}
		});
		
		return filePaths;
	}

	/**
	 * Returns the UID2 string value for the given mmp file and build config context.
	 * @param buildConfiguration the build configuration under which to parse the MMP
	 * @param projectRelativeMMPPath the MMP location in the project 
	 * @return the UID2 string, or null if none.
	 */
	public static String getUID2(ICarbideBuildConfiguration buildConfiguration, IPath projectRelativeMMPPath) {
		String result = (String) EpocEnginePlugin.runWithMMPData(
			projectRelativeMMPPath, 
			new DefaultMMPViewConfiguration(
					buildConfiguration,
					new AcceptedNodesViewFilter()),
			new MMPDataRunnableAdapter() {
				public Object run(IMMPData data) {
					return data.getUid2();
				}
		});
		return result;
	}

	/**
	 * Returns the absolute file system path to the EPOCROOT directory of
	 * the SDK for the active build configuration of the project
	 * @param project the project
	 * @return the absolute path to EPOCROOT, or null if the project is
	 * not a Carbide project, is closed, or there are no build configurations
	 * in the project.
	 * @since 2.0
	 */
	public static IPath getEpocRootForProject(IProject project) {
		IPath epocroot = null;
		
		if (project != null) {
			if (CarbideBuilderPlugin.getBuildManager().isCarbideProject(project) && project.isAccessible()) {
				ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
				if (cpi != null) {
					ICarbideBuildConfiguration config = cpi.getDefaultConfiguration();
					if (config != null) {
						ISymbianSDK sdk = config.getSDK();
						if (sdk != null) {
							epocroot = new Path(sdk.getEPOCROOT());
						}
					}
				}
			}
		}
		
		return epocroot;
	}
	
	/**
	 * Adds the given include path as a user include path to all mmps in the project for
	 * all build configurations
	 * @param project the project
	 * @param projectRelativeIncDirPath project relative path to the include directory
	 * @since 2.0
	 */
	public static void addIncludePathToProject(IProject project, final IPath projectRelativeIncDirPath) {
		if (project == null || projectRelativeIncDirPath == null) {
			return;
		}
		
		if (CarbideBuilderPlugin.getBuildManager().isCarbideProject(project) && project.isAccessible()) {
			ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			if (cpi != null) {

				// loop through all build configurations since the list of mmp's could be different
				// depending on the build configuration
				for (ICarbideBuildConfiguration config : cpi.getBuildConfigurations()) {
					final IPath epocroot = new Path(config.getSDK().getEPOCROOT());

					// loop through each mmp file and add the include path if necessary
					for (final IPath mmpPath : getMMPFilesForBuildConfiguration(config)) {
						
						EpocEnginePlugin.runWithMMPView(mmpPath, 
							new DefaultMMPViewConfiguration(config, new AcceptedNodesViewFilter()), 
							new MMPViewRunnableAdapter() {

								public Object run(IMMPView view) {
									MMPViewPathHelper helper = new MMPViewPathHelper(view, epocroot);
									try {
										// convert the project relative path to an mmp view path
										IPath incPath = helper.convertProjectOrFullPathToMMP(EMMPPathContext.USERINCLUDE, projectRelativeIncDirPath);

										// get the list of user include paths and add it if it's not
										// already there
										List<IPath> userIncludes = view.getUserIncludes();
										if (!userIncludes.contains(incPath)) {
											userIncludes.add(incPath);

											// now commit the changes and release the file
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
										}
										
									} catch (InvalidDriveInMMPPathException e) {
										// shouldn't get here; we passed in a project-relative path
										CarbideBuilderPlugin.log(e);
									}
									
									return null;
								}
						});
					}
				}
			}
		}
	}
	
	/**
	 * Gets the list of mmp and makefiles being built by the given build configuration.  Note that this takes
	 * builder settings into account.
	 * @param buildConfig the build configuration
	 * @return the list of workspace relative compoment paths, may be empty
	 * @since 2.0
	 */
	public static List<IPath> getComponentsBuiltByConifguration(ICarbideBuildConfiguration buildConfig) {
		
		final List<IPath> paths = new ArrayList<IPath>();
		
		final ICarbideProjectInfo cpi = buildConfig.getCarbideProject();
		
		EpocEnginePlugin.runWithBldInfData(cpi.getWorkspaceRelativeBldInfPath(), 
				new DefaultViewConfiguration(cpi, buildConfig), 
				new BldInfDataRunnableAdapter() {

					public Object run(IBldInfData infView) {
						
						EpocEnginePathHelper helper = new EpocEnginePathHelper(cpi.getProject());

						// get the paths of the components being built
						if (cpi.isBuildingFromInf()) {
							// get all regular components
							for (IMakMakeReference component : infView.getMakMakeReferences()) {
								paths.add(helper.convertToWorkspace(component.getPath()));
							}
							
							if (cpi.isBuildingTestComps()) {
								// get all test components
								for (IMakMakeReference component : infView.getTestMakMakeReferences()) {
									paths.add(helper.convertToWorkspace(component.getPath()));
								}
							}
						} else {
							// building a subset of regular and test components.
							for (String component : cpi.getInfBuildComponents()) {
								for (IMakMakeReference comp : infView.getAllMakMakeReferences()) {
									if (comp.getPath().lastSegment().compareToIgnoreCase(component) == 0) {
										paths.add(helper.convertToWorkspace(comp.getPath()));
									}
								}
							}
						}
						
						return null;
					}
			});
		
		return paths;
	}
}
