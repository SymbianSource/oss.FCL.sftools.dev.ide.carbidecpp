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

package com.nokia.carbide.cpp.internal.codescanner;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.IMarkerGenerator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.DefaultMMPViewConfiguration;
import com.nokia.carbide.cdt.builder.EMMPPathContext;
import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cdt.builder.EpocEnginePathHelper;
import com.nokia.carbide.cdt.builder.MMPViewPathHelper;
import com.nokia.carbide.cdt.builder.builder.CarbideCPPBuilder;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.MMPViewRunnableAdapter;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.internal.codescanner.config.CSConfigManager;
import com.nokia.carbide.cpp.internal.codescanner.error.parsers.CSErrorParser;
import com.nokia.carbide.cpp.internal.codescanner.markers.CSMarker;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

/**
 * A class to handle calling the CodeScanner command line tool with
 * the appropriate arguments and configuration file.
 */
public class CSScanner {

	// enum for type of resource to be scanned
	public enum ScanType {
		scan_INF, 
		scan_MMP, 
		scan_other
	}

	// private members
	private final String[] csParserIds = new String[] {
		CSErrorParser.CS_ERROR_PARSER_ID	// parser for errors generated by 
											// the CodeScanner command line tool
	};

	/**
	 * Start a build job and call the CodeScanner command line tool with 
	 * the appropriate arguments and configuration file.
	 * @param project - project associated with the file to be scanned
	 * @param filePath - full path of the file to be scanned
	 * @param scanType - enum indicating the type of scanning to perform
	 */
	public void scanFile (final IProject project,
						  final IPath filePath,
						  final ScanType scanType) {
    	String format = Messages.getString("CSScanner.ScanningProjectMessage");
    	String message = MessageFormat.format(format, project.getName());
		Job buildJob = new Job(message) {
			protected IStatus run(IProgressMonitor monitor){
				IPath workingDirectory = new Path(project.getLocation().toOSString());
				ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
				if (cpi == null) {
					// emit error if project info cannot be found
					return new Status(IStatus.ERROR, CSPlugin.PLUGIN_ID, Messages.getString("CSScanner.ProjectInfoErrorMessage")); 								
				}

				ICarbideBuildConfiguration defaultConfig = cpi.getDefaultConfiguration();
				String configFilePath = workingDirectory.toOSString() + File.separator + CSConfigManager.CS_CONFIG_SETTINGS_FILE;
				CSConfigManager configManager = CSPlugin.getConfigManager();

				// load either project specific or global CodeScanner settings
				if (!configManager.loadConfigSettings(project)) {
					// emit error if CodeScanner settings cannot be loaded
					return new Status(IStatus.ERROR, CSPlugin.PLUGIN_ID, Messages.getString("CSAction.ConfigSettingsErrorMessage")); 								
				}

				// set up arguments to be added to configuration file
				configManager.setOutputFormatArgument();
				List<IPath> pathList;
				switch (scanType) {
					case scan_INF:
		        		// append files included in .INF file
		        		pathList = new ArrayList<IPath>();
		        		addPathsFromINF(pathList, project);
		        		configManager.setInputArguments(pathList);
		        		break;

					case scan_MMP:
		        		// append files included in .MMP file
		        		pathList = new ArrayList<IPath>();
		        		addPathsFromMMP(pathList, filePath, project);
		        		configManager.setInputArguments(pathList);
		        		break;

					case scan_other:
					default:
						// nothing to append when scanning a single file
						break;
				}

		        // remove CodeScanner specific markers
				CSMarker.removeAllMarkers(project);

		        // set up command launcher class to handle calling CodeScanner and parse output
		        CSCommandLauncher cmdLauncher;
		        cmdLauncher = new CSCommandLauncher(project, monitor, csParserIds, workingDirectory);

		        monitor.beginTask(Messages.getString("CSScanner.TaskTitle"), 100);
		    	String format = Messages.getString("CSScanner.ScanningResourceMessage");
		    	String taskName = MessageFormat.format(format, filePath.toOSString());
		        monitor.setTaskName(taskName);
		        cmdLauncher.writeToConsole("\n***" + taskName + "\n");

		        String csCommand = configManager.getCSCommandLineTool();
		        if (csCommand == null) {
					// emit error if CodeScanner command line tool cannot be found
		        	cmdLauncher.addMarker(null, -1,	Messages.getString("ErrorParser.ProecessError"), IMarkerGenerator.SEVERITY_ERROR_BUILD, null);
					return new Status(IStatus.CANCEL, CSPlugin.PLUGIN_ID, null); 								
		        }

		        // construct the arguments...
		        List<String> csArgList = new ArrayList<String>();

		        // configuration file
		        File configFile = configManager.createConfigFile(project, configFilePath);
		        if (configFile != null) {
			        String csConfigFilePath = configFile.getAbsolutePath();
			        if (csConfigFilePath.length() > 0) {
			        	csArgList.add("-c");
			        	csArgList.add(csConfigFilePath);
			        }					        	
		        }

		        // input file/folder
		        csArgList.add(filePath.toOSString());

		        // specify folder for HTML or XML results (optional)
		        if (configManager.generateHtmlResults() || configManager.generateXmlResults()) {
		        	csArgList.add(configManager.getCSResultsDirectory());
		        }

				String[] args = new String[csArgList.size()];
				csArgList.toArray(args);
				cmdLauncher.showCommand(true);

				// executeCommand, a special extension to the regular execute which will handle
				// writing the console output, error parsing, and creating error markers.
				cmdLauncher.executeCommand(new Path(csCommand), args, CarbideCPPBuilder.getResolvedEnvVars(defaultConfig), workingDirectory);

				// clean up the configuration file
				if (configFile != null) {
					configFile.delete();
				}

				return new Status(IStatus.OK, CSPlugin.PLUGIN_ID, Messages.getString("CSScanner.ScanCompletedMessage")); 
			}
		};

		buildJob.setPriority(Job.BUILD);
		buildJob.schedule();
	}
	
	/**
	 * Append all MMP file paths that are applicable to the given build configuration,
	 * plus source files listed in these MMP files, to a path list.
	 * @param pathList - path list to append to
	 * @param project - the associated project
	 */
	private void addPathsFromINF(List<IPath> pathList, IProject project) {
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		List<IPath> mmpFileList = EpocEngineHelper.getMMPFilesForBuildConfiguration(cpi.getDefaultConfiguration());
        if (mmpFileList != null) {
    		for (IPath mmpPath : mmpFileList){
    			IPath mmpFullPath = convertMMPPathToFullPath(project, mmpPath);

    			// append MMP file
    			pathList.add(mmpFullPath);

            	// append content of this MMP file
            	addPathsFromMMP(pathList, mmpFullPath, project);
            }
        }
	}

	/**
	 * Append paths of all the files listed in the given MMP file to a path list.
	 * @param pathList - path list to append to
	 * @param mmpFullPath - full path of MMP file
	 * @param project - the associated project
	 */
	private void addPathsFromMMP(List<IPath> pathList, IPath mmpFullPath, IProject project) {
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);

		// append source files listed in this MMP file
		List<IPath> sourceFileList = EpocEngineHelper.getSourceFilesForConfiguration(cpi.getDefaultConfiguration(), mmpFullPath);
    	if (sourceFileList != null) {
    		for (IPath sourcePath : sourceFileList) {
        		if (sourcePath.toOSString().length() > 0) {
        			pathList.add(sourcePath);
        		}
        	}		
    	}

    	// append user include paths listed in this MMP file
    	List<IPath> userIncludePathList = getMMPUserIncludePaths(project, mmpFullPath);
    	if (userIncludePathList != null) {
        	for (IPath userIncludePath : userIncludePathList) {
        		if (userIncludePath.toOSString().length() > 0) {
        			pathList.add(userIncludePath);
        		}
        	}		
    	}

    	// append user resources listed in this MMP file
    	List<IPath> userResourceList = getMMPUserResources(project, mmpFullPath);
    	if (userResourceList != null) {
        	for (IPath userResource : userResourceList) {
        		if (userResource.toOSString().length() > 0) {
        			pathList.add(userResource);
        		}
        	}		
    	}
	}

	/**
	 * Convert workspace relative path of an MMP file to full path.
	 * @param project - project associated with MMP file
	 * @param mmpPath - workspace relative path of MMP file
	 * @return full path of MMP file
	 */
	private IPath convertMMPPathToFullPath(IProject project, IPath mmpPath) {
		EpocEnginePathHelper helper = new EpocEnginePathHelper(project);
		IPath projectPath = helper.convertToWorkspace(project.getLocation());
		int segmentCount = FileUtils.matchingFirstSegments(projectPath, mmpPath);
		IPath mmpFullPath = helper.convertToFilesystem(mmpPath.removeFirstSegments(segmentCount).setDevice(null));
		return mmpFullPath;
	}

	/**
	 * Get the resolved user include paths (paths specified in USERINCLUDE statements)
	 * from the given MMP.
	 * @param project - the project associated with the MMP
	 * @param inMMPPath - full path to the MMP file 
	 * @return list of user include paths if any can be found, null otherwise
	 */
	private List<IPath> getMMPUserIncludePaths(final IProject project, IPath inMMPPath) {
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi != null) {
			ICarbideBuildConfiguration buildConfig = cpi.getDefaultConfiguration();
			IMMPViewConfiguration viewConfiguration = new DefaultMMPViewConfiguration(project, buildConfig.getBuildContext(), new AcceptedNodesViewFilter());
			EpocEnginePathHelper helper = new EpocEnginePathHelper(cpi.getProject());
			IPath workspaceRelativeMMPPath = helper.convertToWorkspace(inMMPPath);
			final String epocRoot = buildConfig.getSDK().getEPOCROOT();
			final List<IPath> userIncludePaths = new ArrayList<IPath>();
			EpocEnginePlugin.runWithMMPView(workspaceRelativeMMPPath, viewConfiguration, 
				new MMPViewRunnableAdapter() {
					public Object run(IMMPView mmpView) {
						MMPViewPathHelper helper = new MMPViewPathHelper(mmpView, epocRoot);
						for (IPath path : mmpView.getUserIncludes()) {
							IPath fullpath = helper.convertMMPToFilesystem(EMMPPathContext.USERINCLUDE, path);
							if (fullpath != null) {
								userIncludePaths.add(fullpath);
							}
						}

						return null;
					}
				}
			);

			return userIncludePaths;
		}

		return null;
	}

	/**
	 * Get the user resources (resources specified in RESOURCE and START RESOURCE statements)
	 * from the given MMP.
	 * @param project - the project associated with the MMP
	 * @param inMMPPath - full path to the MMP file 
	 * @return list of user resources if any can be found, null otherwise
	 */
	private List<IPath> getMMPUserResources(final IProject project, IPath inMMPPath) {
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi != null) {
			ICarbideBuildConfiguration buildConfig = cpi.getDefaultConfiguration();
			IMMPViewConfiguration viewConfiguration = new DefaultMMPViewConfiguration(project, buildConfig.getBuildContext(), new AcceptedNodesViewFilter());
			final EpocEnginePathHelper pathHelper = new EpocEnginePathHelper(cpi.getProject());
			IPath workspaceRelativeMMPPath = pathHelper.convertToWorkspace(inMMPPath);
			final String epocRoot = buildConfig.getSDK().getEPOCROOT();
			final List<IPath> userResources = new ArrayList<IPath>();
			EpocEnginePlugin.runWithMMPView(workspaceRelativeMMPPath, viewConfiguration, 
				new MMPViewRunnableAdapter() {
					public Object run(IMMPView mmpView) {
						MMPViewPathHelper helper = new MMPViewPathHelper(mmpView, epocRoot);
						for (IPath path : mmpView.getUserResources()) {
							IPath fullpath = helper.convertMMPToFilesystem(EMMPPathContext.RESOURCE, path);
							if (fullpath != null) {
								userResources.add(fullpath);
							}
						}

						for (IMMPResource mmpResource : mmpView.getResourceBlocks()) {
							IPath path = mmpResource.getSource();
							IPath fullpath = pathHelper.convertToFilesystem(path);
							if (fullpath != null) {
								userResources.add(fullpath);
							}
						}

						return null;
					}
				}
			);

			return userResources;
		}

		return null;
	}

}
