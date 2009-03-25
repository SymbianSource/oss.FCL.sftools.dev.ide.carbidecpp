/*
* Copyright (c) 2007, 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.templatewizard.processes;

import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.carbide.templatewizard.Messages;
import com.nokia.carbide.templatewizard.process.IParameter;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;

/**
 * Process used in templates to copy a tree of files from one location to
 * another, optionally template-expanding contents along the way. The leaf of
 * source is NOT placed into the target, so repeat the leaf directory in
 * targetPath if required.
 * <p>
 * Example:
 * 
 * <pre>
 * 	&lt;process class=&quot;com.nokia.carbide.templatewizard.processes.CopyFileTree&quot;
 * 	 	bundle=&quot;com.nokia.carbide.templatewizard&quot;&gt;
 * 		&lt;parameter name=&quot;project&quot; projectName=&quot;$(projectName)&quot; /&gt;
 * 		&lt;parameter name=&quot;tree&quot; sourcePath=&quot;contents&quot; targetPath=&quot;.&quot; substitute=&quot;true&quot; /&gt; 
 * 	&lt;/process&gt;
 * </pre>
 * 
 */
public class CopyFileTree extends CopyFiles {

	/** This attribute is a regular expression used to filter out files or directories
		(leaf name only).  If not specified, CVS and .svn directories are the default filter. */
	private static final String EXCLUSION_PATTERN_ATTRIBUTE = "excludedFileOrDirectoryPattern"; //$NON-NLS-1$

	/** This attribute is a regular expression used to exclude out files or directories
	from variable substitution (leaf name only).  If a directory matches, all its files
	and subdirectories are excluded from substitution.  If not specified, all files are 
	substituted if 'substitute=true'. */
	private static final String NO_SUBSTITUTE_PATTERN_ATTRIBUTE = "noSubstituteFileOrDirectoryPattern"; //$NON-NLS-1$

	/** This parameter provides sourcePath and targetPath attributes, where the target will contain 
		the contents of source
	 */
	private static final String TREE_PARAMETER = "tree"; //$NON-NLS-1$

	@Override
	public void process(ITemplate template, List<IParameter> parameters, IProgressMonitor monitor) throws CoreException {
		monitor.beginTask(Messages.getString("CopyFileTree.CopyingTaskLabel"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
		try {
			init(template, parameters);
	
			IParameter parameter = getRequiredParameterByName(TREE_PARAMETER, parameters);
			String sourcePath = parameter.getAttributeValue(SOURCE_PATH_ATTRIBUTE);
			
			URL sourcePathUrl = null;
			try {
				sourcePathUrl = new URL(baseSourceUrl, sourcePath);
				sourcePathUrl = FileLocator.toFileURL(sourcePathUrl);
			} catch (Exception e1) {
				fail(MessageFormat.format(
						Messages.getString("CopyFileTree.BadSourcePathURL"), //$NON-NLS-1$
						sourcePath), e1);
			}
			
			String targetPath = parameter.getAttributeValue(TARGET_PATH_ATTRIBUTE);
			IContainer targetFolder = null;
			if (targetPath.length() > 0  && !targetPath.equals(".")) { //$NON-NLS-1$
				targetFolder = project.getFolder(targetPath);
			} else {
				targetFolder = project;
			}
			
			File sourceDir = new File(sourcePathUrl.getPath());
	
			String substituteValue = parameter.getAttributeValue(SUBSTITUTE_ATTRIBUTE);
			boolean substitute = substituteValue != null && Boolean.parseBoolean(substituteValue);
			
			String exclusionPattern = parameter.getAttributeValue(EXCLUSION_PATTERN_ATTRIBUTE);
			if (exclusionPattern == null)
				exclusionPattern = "(?i).svn|CVS"; //$NON-NLS-1$
			
			String noSubstitutePattern = parameter.getAttributeValue(NO_SUBSTITUTE_PATTERN_ATTRIBUTE);
			if (noSubstitutePattern == null)
				noSubstitutePattern = ""; //$NON-NLS-1$
			
			copyFileTree(sourceDir, targetFolder, substitute, noSubstitutePattern, exclusionPattern, monitor);
		} finally {
			monitor.done();
		}
	}

	/**
	 * Create a copy of sourceFile into targetFolder (the parent).
	 * @param sourceFile
	 * @param targetFolder
	 * @param substitute
	 * @param noSubstitutePattern 
	 * @param exclusionPattern 
	 * @param monitor
	 * @throws CoreException
	 */
	private void copyFileTree(File sourceFile, IContainer targetFolder,
			boolean substitute,
			String noSubstitutePattern, String exclusionPattern, IProgressMonitor monitor) throws CoreException {
		if (sourceFile.getName().matches(exclusionPattern))
			return;
		
		// we reset the flag recursively
		if (sourceFile.getName().matches(noSubstitutePattern)) {
			substitute = false;
		}
		
		if (!targetFolder.exists()) {
			((IFolder)targetFolder).create(false, true, new SubProgressMonitor(monitor, 1));
		}
		if (sourceFile.isDirectory()) {
			File[] sourceFiles = sourceFile.listFiles();
			for (File sourceFileChild : sourceFiles) {
				IContainer dir = null;
				if (sourceFileChild.isDirectory())
					dir = targetFolder.getFolder(new Path(sourceFileChild.getName()));
				else
					dir = targetFolder;
				
				
				copyFileTree(sourceFileChild, dir, substitute, noSubstitutePattern, exclusionPattern, monitor);
			}
		} else {
			IFile targetFile = targetFolder.getFile(new Path(sourceFile.getName()));
			URL sourceUrl = null;
			try {
				sourceUrl = new URL("file", null, sourceFile.getAbsolutePath()); //$NON-NLS-1$
			} catch (MalformedURLException e) {
				fail(e.getLocalizedMessage());
			}
			copyFile(sourceUrl, targetFile, substitute, monitor);
		}
	}
}
