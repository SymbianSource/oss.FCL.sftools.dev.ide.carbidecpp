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

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

import java.util.*;

/**
 * A class encapsulating a single project for updating
 */
public class SymbianProjectInfo {
	
	private static final String INF = "inf"; //$NON-NLS-1$
	private static final String MMP = "mmp"; //$NON-NLS-1$
	private static final String CPP = "cpp"; //$NON-NLS-1$
	private static final String C = "c"; //$NON-NLS-1$
	private static final String H = "h"; //$NON-NLS-1$
	private static final String RSS = "rss"; //$NON-NLS-1$
	private static final String AIFDEF = "aifdef"; //$NON-NLS-1$
	private static final String MBMDEF = "mbmdef"; //$NON-NLS-1$
	private static final String MIFDEF = "mifdef"; //$NON-NLS-1$
	private static final String BLD_INF = "bld.inf"; //$NON-NLS-1$
	
	private IProject project;
	private IPath bldInfPath;
	private IPath groupFolderPath;
	private Set<IPath> userIncludePaths = new HashSet<IPath>();
	private List<IPath> sourceFiles = new ArrayList<IPath>();
	private List<IPath> rssFiles = new ArrayList<IPath>();
	private List<IPath> mifdefFiles = new ArrayList<IPath>();
	private List<IPath> mbmdefFiles = new ArrayList<IPath>();
	private List<IPath> aifdefFiles = new ArrayList<IPath>();
	private List<IPath> mmpFiles = new ArrayList<IPath>();
	
	private SymbianProjectInfo(IProject project) {
		this.project = project;
	}

	public static SymbianProjectInfo createProjectInfo(IProject project) throws CoreException {
		final SymbianProjectInfo symbianProjectInfo = new SymbianProjectInfo(project);
		
		project.accept(new IResourceProxyVisitor() {
			public boolean visit(IResourceProxy proxy) throws CoreException {
				// ignore generated files (which includes an unwanted winscw_uid.cpp)
				if (proxy.getType() == IResource.FOLDER) {
					if (proxy.getName().equalsIgnoreCase(".generated")) { //$NON-NLS-1$
						return false;
					}
					return true;
				}
				symbianProjectInfo.recordFileInfo(proxy);
				return true;
			}
		}, IResource.NONE);
		
		
		return symbianProjectInfo;
	}

	private void recordFileInfo(IResourceProxy proxy) {
		if (proxy.getType() != IResource.FILE)
			return;
		
		String fileName = proxy.getName();
		String extension = (new Path(fileName)).getFileExtension();
		if (CPP.equalsIgnoreCase(extension) || C.equalsIgnoreCase(extension))
			recordSourceFile(proxy);
		else if (H.equalsIgnoreCase(extension))
			recordIncludePath(proxy);
		else if (RSS.equalsIgnoreCase(extension))
			recordRSS(proxy);
		else if (AIFDEF.equalsIgnoreCase(extension))
			recordAIFDEF(proxy);
		else if (MBMDEF.equalsIgnoreCase(extension))
			recordMBMDEF(proxy);
		else if (MIFDEF.equalsIgnoreCase(extension))
			recordMIFDEF(proxy);
		else if (INF.equalsIgnoreCase(extension))
			recordInf(proxy);
		else if (MMP.equalsIgnoreCase(extension))
			recordMMP(proxy);
	}

	private void recordMIFDEF(IResourceProxy proxy) {
		IResource resource = proxy.requestResource();
		IPath path = resource.getLocation();
		mifdefFiles.add(path);
	}

	private void recordMBMDEF(IResourceProxy proxy) {
		IResource resource = proxy.requestResource();
		IPath path = resource.getLocation();
		mbmdefFiles.add(path);
	}

	private void recordAIFDEF(IResourceProxy proxy) {
		IResource resource = proxy.requestResource();
		IPath path = resource.getLocation();
		aifdefFiles.add(path);
	}

	private void recordRSS(IResourceProxy proxy) {
		IResource resource = proxy.requestResource();
		IPath path = resource.getLocation();
		rssFiles.add(path);
	}

	private void recordIncludePath(IResourceProxy proxy) {
		IResource resource = proxy.requestResource().getParent();
		IPath path = resource.getLocation();
		userIncludePaths.add(path);
	}

	private void recordSourceFile(IResourceProxy proxy) {
		IResource resource = proxy.requestResource();
		IPath path = resource.getLocation();
		sourceFiles.add(path);
	}

	private void recordMMP(IResourceProxy proxy) {
		IResource resource = proxy.requestResource();
		IPath path = resource.getLocation();
		mmpFiles.add(path);
	}

	private void recordInf(IResourceProxy proxy) {
		// only record the first bld.inf
		if (bldInfPath == null) {
			IResource resource = proxy.requestResource();
			IPath path = resource.getLocation();
			String fileName = path.lastSegment();
			if (fileName.equalsIgnoreCase(BLD_INF)) {
				bldInfPath = path;
			}
		}
	}
	
	public IPath getGroupFolderPath() {
		if (groupFolderPath == null) {
			if (bldInfPath != null)
				groupFolderPath = bldInfPath.removeLastSegments(1);
			else {
				for (IPath mmpPath : mmpFiles) {
					groupFolderPath = mmpPath.removeLastSegments(1);
					break;
				}
			}
			if (groupFolderPath == null)
				groupFolderPath = project.getLocation();
		}
		return groupFolderPath;
	}

	public Collection<IPath> getAifdefFiles() {
		return aifdefFiles;
	}

	public IPath getBldInfPath() {
		return bldInfPath;
	}

	public Collection<IPath> getMbmdefFiles() {
		return mbmdefFiles;
	}

	public Collection<IPath> getMifdefFiles() {
		return mifdefFiles;
	}

	public Collection<IPath> getRssFiles() {
		return rssFiles;
	}

	public Collection<IPath> getSourceFiles() {
		return sourceFiles;
	}

	public Collection<IPath> getUserIncludePaths() {
		return userIncludePaths;
	}

	public Collection<IPath> getMmpFiles() {
		return mmpFiles;
	}
}
