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
* This class examines the build-time files in a project to find:
* <p>
* 1) which files from the project are being exported via PRJ_EXPORTS
* 2) which files are being exported via PKG files 
* <p>
* and the mappings from files on the source system to either EPOCROOT exports
* or device install files.
* <p>
* This class does NOT provide information about any generated files (resource, executables, etc).
* <p>
* This class is intended to be short-lived.
*
*/
package com.nokia.carbide.cdt.internal.api.builder;

import com.nokia.carbide.cdt.builder.*;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.*;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExport;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

public class ProjectExportsGatherer {
	private boolean gathered;
	private Collection<IPath> pkgFiles;
	private IPath bldInfPath;
	
	/** Map of absolute filesystem paths to EPOCROOT device+path locations */
	private Map<IPath, IPath> filesystemToEpocExportsMap;
	/** Map of absolute filesystem paths to device+path locations on target */
	private Map<IPath, IPath> filesystemToPkgInstallFilesMap;
	/** Map of absolute filesystem paths to EPOCROOT device+path locations for files
	 * which are not installed on the device in any SIS file */
	private Map<IPath, IPath> uninstalledFilesystemToEpocExportsMap;
	private IPath projectPath;
	private IPath epocRoot;
	private String platform;
	private String target;
	private Map<IPath, IPath> allFilesystemToEpocVisibleMap;

	private ProjectExportsGatherer() {
		this.filesystemToEpocExportsMap = new LinkedHashMap<IPath, IPath>();
		this.filesystemToPkgInstallFilesMap = new LinkedHashMap<IPath, IPath>();
		this.uninstalledFilesystemToEpocExportsMap = new LinkedHashMap<IPath, IPath>();
		this.allFilesystemToEpocVisibleMap = new LinkedHashMap<IPath, IPath>();
		this.gathered = false;
	}

	/**
	 * Create a gatherer for the given project, scanning for all exports in bld.inf,
	 * using the given configuration to resolve any EPOCROOT, platform, or 
	 * target macros from PKG files.
	 * @param projectInfo
	 * @param configuration
	 */
	public ProjectExportsGatherer(ICarbideProjectInfo projectInfo, ICarbideBuildConfiguration configuration) {
		this();
		Check.checkArg(projectInfo);
		Check.checkArg(configuration);
		this.projectPath = ProjectUtils.getRealProjectLocation(projectInfo.getProject());
		this.bldInfPath = projectInfo.getAbsoluteBldInfPath();
		this.epocRoot = new Path(configuration.getSDK().getEPOCROOT());
		this.platform = configuration.getPlatformString();
		this.target = configuration.getTargetString();
		gatherPkgFiles();

	}

	/**
	 * Create a gatherer for the given project, scanning for all exports in bld.inf,
	 * but using its default configuration to resolve any EPOCROOT, platform, or 
	 * target macros from PKG files.
	 * @param projectInfo
	 */
	public ProjectExportsGatherer(ICarbideProjectInfo projectInfo) {
		this();
		Check.checkArg(projectInfo);
		this.projectPath = ProjectUtils.getRealProjectLocation(projectInfo.getProject());
		this.bldInfPath = projectInfo.getAbsoluteBldInfPath();
		this.epocRoot = new Path(projectInfo.getDefaultConfiguration().getSDK().getEPOCROOT());
		this.platform = projectInfo.getDefaultConfiguration().getPlatformString();
		this.target = projectInfo.getDefaultConfiguration().getTargetString();
		gatherPkgFiles();

	}

	/**
	 * Set up with a full bld.inf path and project root wherein .pkg files are scanned.
	 * @param projectPath
	 * @param bldInfPath
	 * @param epocRoot
	 * @param platform
	 * @param target
	 */
	public ProjectExportsGatherer(IPath projectPath, IPath bldInfPath,
			IPath epocRoot, String platform, String target) {
		this();
		this.projectPath = projectPath;
		this.bldInfPath = bldInfPath;
		this.epocRoot = epocRoot;
		this.platform = platform;
		this.target = target;
		
		gatherPkgFiles();
	}

	
	/**
	 * Get the bld.inf-provided map of filesystem paths to EPOCROOT-contained drive+path paths
	 * (e.g. c:\source\myfile.txt -> c:\private\1000000E\myfile.txt)
	 * @return map of full path to full path, never null
	 */
	public Map<IPath, IPath> getFilesystemToEpocExportMap() {
		ensureGathered();
		return filesystemToEpocExportsMap;
	}

	/**
	 * Get the map of all host files (usually expected to be inside EPOCROOT)
	 * which map to .pkg install-file entries, as full filesystem paths to 
	 * installed device (drive+path) paths.
	 * @return map of full path to full path, never null
	 */
	public Map<IPath, IPath> getFilesystemToPkgInstallFileMap() {
		ensureGathered();
		return filesystemToPkgInstallFilesMap;
	}
	
	/**
	 * Get the mappings of full filesystem paths to EPOC-like (drive+path) paths
	 * for files which are exported via bld.inf but not installed to the device in any PKG file.
	 * @return map of full path to full path, never null
	 */
	public Map<IPath, IPath> getUninstalledFilesystemToEpocExportsMap() {
		ensureGathered();
		return uninstalledFilesystemToEpocExportsMap;
	}
	
	/**
	 * Get the full map of any filesystem file which is available to the emulator or
	 * device.  The values in the map are epoc-like (drive+path) paths.  This map may contain 
	 * multiple references to the same epoc path (e.g. from bld.inf export and .pkg install file).
	 * @return map of full path to full path, never null
	 */
	public Map<IPath, IPath> getAllFilesystemToEpocVisibleMap() {
		ensureGathered();
		return allFilesystemToEpocVisibleMap;
	}
	
	/**
	 * Get PKG files in the project.  Unfortunately ICarbideProjectInfo#getSISBuilderInfos()
	 * doesn't help, since these are only populated if the user explicitly sets them up.
	 */
	private void gatherPkgFiles() {
		this.pkgFiles = new ArrayList<IPath>();
		File[] pkgFileFiles = FileUtils.listFilesInTree(projectPath.toFile(), new FileFilter() {
	
			public boolean accept(File pathname) {
				if (pathname.isDirectory())
					return true;
				String name = pathname.getName().toLowerCase();
				// get pkg files but not build leftovers
				return name.endsWith(".pkg") && !name.startsWith("_resolved"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			
		}, false);
		
		for (File file : pkgFileFiles) {
			pkgFiles.add(new Path(file.getAbsolutePath()));
		}
		
	}

	/**
	 * Gather the information once.
	 */
	private void ensureGathered() {
		if (!gathered) {
			filesystemToEpocExportsMap.clear();
			filesystemToPkgInstallFilesMap.clear();
			uninstalledFilesystemToEpocExportsMap.clear();
			allFilesystemToEpocVisibleMap.clear();
			gather();
			correlate();
			gathered = true;
		}
	}

	/**
	 * Gather the image information from the project's bld.inf and all the .pkg files
	 * in the project.
	 */
	private void gather() {
		EpocEnginePlugin.runWithBldInfView(bldInfPath, 
			new DefaultViewConfiguration(projectPath), 
			new BldInfViewRunnableAdapter() {

				public Object run(IBldInfView view) {
					gatherExports(view.getExports());
					gatherExports(view.getTestExports());
					return null;
				}
		});
		
		
		for (IPath pkgFile : pkgFiles) {
			PKGModelHelper.runWithPKGView(pkgFile, 
					new DefaultViewConfiguration(projectPath), 
					new PKGViewRunnableAdapter() {

						public Object run(IPKGView view) {
							gatherInstalledFiles(view, view.getAllInstallFiles());
							return null;
						}
				
			});
		}
	}

	/**
	 * Gather information from a bld.inf exports list
	 * @param exports
	 */
	protected void gatherExports(List<IExport> exports) {
		EpocEnginePathHelper helper = new EpocEnginePathHelper(projectPath);
		
		for (IExport export : exports) {
			IPath targetPath = export.getTargetPath();
			
			// we only support exports which might reasonably make it onto the device,
			// which means ones either explictly or implicitly targeting a drive.
			if (isAbsoluteDrivePath(targetPath)) {
				// accept
			}
			else if (targetPath.isAbsolute() && targetPath.getDevice() == null
					&& targetPath.segmentCount() > 1
					&& targetPath.segment(0).equalsIgnoreCase("epoc32")) { //$NON-NLS-1$
				// adjust \epoc32\data\<drive>\<path> to <drive>:\<path>
				// and \epoc32\release\<platform>\<target>\<drive>\<path> to <drive>:\<path>
				if (targetPath.segmentCount() > 4
						&& targetPath.segment(1).equalsIgnoreCase("data") //$NON-NLS-1$
						&& targetPath.segment(2).length() == 1) {
					targetPath = targetPath.removeFirstSegments(3)
						.makeAbsolute().setDevice(targetPath.segment(2) + ":"); //$NON-NLS-1$
				} else if (targetPath.segmentCount() > 6
						&& targetPath.segment(1).equalsIgnoreCase("release") //$NON-NLS-1$
						&& targetPath.segment(4).length() == 1) {
					targetPath = targetPath.removeFirstSegments(5)
						.makeAbsolute().setDevice(targetPath.segment(4) + ":"); //$NON-NLS-1$
				} else {
					// likely epoc32\include installation
				}
			} 
			else {
				// not absolute, not installed to phone
				targetPath = targetPath.makeAbsolute();
			}
			
			if (targetPath != null) {
				IPath realSourceLocation = helper.convertToFilesystem(export.getSourcePath()); 
				filesystemToEpocExportsMap.put(realSourceLocation, targetPath);
			}
		}
	}

	/**
	 * Gather information from PKG installed files
	 * @param allInstallFiles
	 */
	protected void gatherInstalledFiles(IPKGView view, IPKGInstallFile[] allInstallFiles) {
		PKGViewPathHelper pkgViewPathHelper = new PKGViewPathHelper(view.getModel().getPath(), epocRoot, platform, target);
		for (IPKGInstallFile installFile : allInstallFiles) {
			for (Map.Entry<EPKGLanguage, IPath> entry : installFile.getSourceFiles().entrySet()) {
				if (installFile.getDestintationFile().isEmpty())
					continue;
				
				// add one entry for each unique source
				IPath fullPath = pkgViewPathHelper.getAbsolutePathFromViewPath(entry.getValue());
				filesystemToPkgInstallFilesMap.put(fullPath, installFile.getDestintationFile());
			}
		}
	}
	
	/**
	 * Correlate gathered information for the final two maps
	 */
	private void correlate() {
		for (Map.Entry<IPath, IPath> entry : filesystemToEpocExportsMap.entrySet()) {
			IPath installed = filesystemToPkgInstallFilesMap.get(entry.getValue());
			if (installed == null) {
				installed = filesystemToPkgInstallFilesMap.get(epocHostToEPOCROOTData(entry.getValue()));
			}
			if (installed == null) {
				installed = filesystemToPkgInstallFilesMap.get(epocHostToEPOCPlatformData(entry.getValue()));
			}
			if (installed == null) {
				uninstalledFilesystemToEpocExportsMap.put(entry.getKey(), entry.getValue());
			}
			allFilesystemToEpocVisibleMap.put(entry.getKey(), entry.getValue());
		}
		allFilesystemToEpocVisibleMap.putAll(filesystemToPkgInstallFilesMap);
	}

	/**
	 * Tell if the path is a Win32 path with drive letter or UNC.
	 * @param path
	 */
	protected boolean isWin32DrivePath(IPath path) {
		return (path.getDevice() != null && path.getDevice().length() == 2) 
			|| (!HostOS.IS_WIN32 && path.segmentCount() > 0 && path.segment(0).matches("[A-Za-z]:"));
	}

	/**
	 * Tell if the path is absolute -- e.g., according to the host or to Windows conventions.
	 * @param path
	 */
	protected boolean isAbsoluteDrivePath(IPath path) {
		if (path.isAbsolute())
			return true;
		if (isWin32DrivePath(path))
			return true;
		return false;
	}
	
	/**
	 * Convert a path like c:\private\foo.svg to $(EPOCROOT)data\c\private\foo.svg 
	 * @param host
	 * @return converted path or original
	 */
	private IPath epocHostToEPOCROOTData(IPath host) {
		if (!isAbsoluteDrivePath(host))
			return host;
		IPath nativ = epocRoot.append("epoc32").append("data") //$NON-NLS-1$ //$NON-NLS-2$
			.append(convertDriveToPathSegment(host));
		return nativ;
	}
	
	/**
	 * @param host
	 * @return
	 */
	private IPath convertDriveToPathSegment(IPath host) {
		if (HostOS.IS_WIN32)
			return new Path(host.getDevice().substring(0, 1)).append(host.setDevice(null));
		else
			return new Path(host.segment(0).substring(0, 1)).append(host.removeFirstSegments(1)); 
	}

	/**
	 * Convert a path like c:\private\foo.svg to $(EPOCROOT)release\<platform>\<target>\<drive>\<path>
	 * @param host
	 * @return converted path or original
	 */
	private IPath epocHostToEPOCPlatformData(IPath host) {
		if (!isAbsoluteDrivePath(host))
			return host;
		IPath nativ = epocRoot.append("epoc32").append("release").append(platform).append(target) //$NON-NLS-1$ //$NON-NLS-2$
			.append(convertDriveToPathSegment(host));
		return nativ;
	}
}
