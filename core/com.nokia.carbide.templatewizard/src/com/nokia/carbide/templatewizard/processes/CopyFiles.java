/*
* Copyright (c) 2006, 2008 Nokia Corporation and/or its subsidiary(-ies).
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
import com.nokia.carbide.templatewizard.TemplateWizardPlugin;
import com.nokia.carbide.templatewizard.process.AbstractProjectProcess;
import com.nokia.carbide.templatewizard.process.IParameter;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.cpp.internal.api.utils.ui.FilesListDialog;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;

/**
 * Process used in templates to files in a project.<p>
 * See the documentation for Creating Wizard Templates.
 */
public class CopyFiles extends AbstractProjectProcess {

	protected static final String UPPER_SUFFIX = "$upper"; //$NON-NLS-1$
	protected static final String LOWER_SUFFIX = "$lower"; //$NON-NLS-1$
	protected static final String TITLE_SUFFIX = "$title"; //$NON-NLS-1$
	protected static final String TITLELOWER_SUFFIX = "$titlelower"; //$NON-NLS-1$
	protected static final String C_ESCAPED_SUFFIX = "$c_escaped"; //$NON-NLS-1$
	protected static final String SOURCE_PATH_ATTRIBUTE = "sourcePath"; //$NON-NLS-1$
	protected static final String TARGET_PATH_ATTRIBUTE = "targetPath"; //$NON-NLS-1$
	protected static final String SUBSTITUTE_ATTRIBUTE = "substitute"; //$NON-NLS-1$
	protected static final String OVERWRITE_ATTRIBUTE = "overwrite"; //$NON-NLS-1$

	protected static final String FILE_PARAMETER = "file"; //$NON-NLS-1$
	
	private static final String NO_PROJECT_ERROR = Messages.getString("CopyFiles.NoProjectError"); //$NON-NLS-1$
	private static final String COPY_FILE_ERROR = Messages.getString("CopyFiles.CopyFileError"); //$NON-NLS-1$

	protected IProject project;
	protected URL baseSourceUrl;
	protected Map templateValues;

	private static interface IConvert {
		String convert(String value);
	}
	
	private static class SuffixOperator {
		private final String suffix;
		private final IConvert convert;

		public SuffixOperator(String suffix, IConvert convert) {
			this.suffix = suffix;
			this.convert = convert;
		}

		public IConvert getConvert() {
			return convert;
		}

		public boolean hasSuffix(String s) {
			return s.endsWith(suffix);
		}
		
		public String getRoot(String s) {
			return s.substring(0, s.length() - suffix.length());
		}
	}
	
	private static SuffixOperator[] suffixOperators = {
		new SuffixOperator(UPPER_SUFFIX, new IConvert() {
			public String convert(String value) {
				return value.toUpperCase();
			}
		}),
		new SuffixOperator(LOWER_SUFFIX, new IConvert() {
			public String convert(String value) {
				return value.toLowerCase();
			}
		}),
		new SuffixOperator(TITLE_SUFFIX, new IConvert() {
			public String convert(String value) {
				char c = Character.toUpperCase(value.charAt(0));
				return "" + c + value.substring(1); //$NON-NLS-1$
			}
		}),
		new SuffixOperator(TITLELOWER_SUFFIX, new IConvert() {
			public String convert(String value) {
				return TextUtils.titleCase(value.toLowerCase());
			}
		}),
		new SuffixOperator(C_ESCAPED_SUFFIX, new IConvert() {
			public String convert(String value) {
				return TextUtils.escape(value, '\"');
			}
		})
	};
	
	private static class FileInfo {
		private URL sourceFileURL;
		private IFile targetFile;
		private boolean substitute;
		
		public FileInfo(URL sourceFileURL, IFile targetFile, boolean substitute) {
			this.sourceFileURL = sourceFileURL;
			this.targetFile = targetFile;
			this.substitute = substitute;
		}

		public URL getSourceFileURL() {
			return sourceFileURL;
		}

		public IFile getTargetFile() {
			return targetFile;
		}

		public boolean substitute() {
			return substitute;
		}

	}
	@Override
	public void process(ITemplate template, List<IParameter> parameters, IProgressMonitor monitor) throws CoreException {
		super.process(template, parameters, monitor);
		
		// get info about each file
		List<FileInfo> infos = new ArrayList<FileInfo>();
        for (IParameter parameter : parameters) {
			if (parameter.getName().equals(FILE_PARAMETER)) {
				infos.add(getFileInfo(parameter));
			}
		}
        
        // see if any target files exist
        List<IFile> existingFiles = new ArrayList<IFile>();
        for (FileInfo fileInfo : infos) {
			if (FileUtils.exists(fileInfo.targetFile)) {
				existingFiles.add(fileInfo.targetFile);
			}
		}
        
        List<FileInfo> fileInfosToCopy = new ArrayList<FileInfo>(infos);
        if (!existingFiles.isEmpty()) {
        	handleExistingFiles(infos, existingFiles, fileInfosToCopy);
        } 
        
        for (FileInfo fileInfo : fileInfosToCopy) {
			copyFile(fileInfo.getSourceFileURL(), fileInfo.getTargetFile(), fileInfo.substitute(), monitor);
		}
	}

	private void handleExistingFiles(List<FileInfo> infos, final List<IFile> existingFiles, List<FileInfo> fileInfosToCopy) {
		boolean overwrite = true;
		String autoOverwrite = projectParameter.getAttributeValue(OVERWRITE_ATTRIBUTE);
		if (autoOverwrite == null && !WorkbenchUtils.isJUnitRunning()) { // should ask
			final int[] result = {IDialogConstants.CANCEL_ID};
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					Shell shell = WorkbenchUtils.getActiveShell();
					String title = Messages.getString("CopyFiles.FilesExistTitle"); //$NON-NLS-1$
					String message = Messages.getString("CopyFiles.OverwriteFilesQuestion"); //$NON-NLS-1$
					FilesListDialog dialog = new FilesListDialog(shell, existingFiles, title, message);
					dialog.setAltButtonLabels(IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL);
					result[0] = dialog.open();
				}
			});
			overwrite = result[0] == IDialogConstants.OK_ID;
		}
		else if (autoOverwrite != null) {
			overwrite = Boolean.parseBoolean(autoOverwrite);
		}
		
		if (!overwrite) {
			fileInfosToCopy.clear();
			for (FileInfo fileInfo : infos) {
				if (!FileUtils.exists(fileInfo.targetFile))
					fileInfosToCopy.add(fileInfo);
			}
		}
	}

	@Override
	protected void init(ITemplate template, List<IParameter> parameters) throws CoreException {
		super.init(template, parameters);
		
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		project = root.getProject(getProjectName());
		String error = MessageFormat.format(NO_PROJECT_ERROR, new Object[] { getProjectName() });
		failIfNull(project, error);
		baseSourceUrl = FileUtils.getParentPathURL(template.getTemplateUrl());
		templateValues = template.getTemplateValues();
	}

	private FileInfo getFileInfo(IParameter parameter) throws CoreException {
		URL sourceFileUrl = getSourceFileUrl(parameter);

		String targetPathStr = getRequiredAttributeFromParameter(parameter, TARGET_PATH_ATTRIBUTE);
 		
		IPath workspacePath = new Path(project.getName()).append(targetPathStr);
 		
 		// canonicalize the path in the workspace to ensure the directory names are the same case
 		IPath targetPath = FileUtils.getCanonicalWorkspacePath(workspacePath);
 
 		IFile targetFile = project.getWorkspace().getRoot().getFile(targetPath);
		
		String substituteValue = parameter.getAttributeValue(SUBSTITUTE_ATTRIBUTE);
		
		// substitute by default
		boolean substitute = substituteValue == null || Boolean.parseBoolean(substituteValue);
		
		return new FileInfo(sourceFileUrl, targetFile, substitute);	
	}

	/**
	 * Copy a file from the given source URL to the given workspace file.  This overwrites
	 * the target without complaint.
	 * @param sourceFileUrl URL for source (file, jar, etc)
	 * @param targetFile target file, need not exist
	 * @param substitute if true, apply variable substitution to contents
	 * @param monitor progress monitor 
	 * @throws CoreException if file copying fails
	 */
	protected void copyFile(URL sourceFileUrl, IFile targetFile,
			boolean substitute, IProgressMonitor monitor) throws CoreException {
		if (!substitute) {
			try {
				File toFile = targetFile.getLocation().toFile();
				toFile.createNewFile();
				InputStream is = sourceFileUrl.openStream();
				FileUtils.copyFile(is, toFile);
				IFile ifile = FileUtils.convertFileToIFile(toFile);
				if (ifile != null)
					ifile.refreshLocal(IResource.DEPTH_ZERO, monitor);
			} 
			catch (IOException e) {
				String error = MessageFormat.format(COPY_FILE_ERROR, 
						new Object[] { getProcessName(), e.getLocalizedMessage() });
				fail(error, e);
			}
		}
		else {
			String input = getSourceAsString(sourceFileUrl);
			VariableSubstitutionEngine substitutionEngine = new VariableSubstitutionEngine(null, null);
			substitutionEngine.setVariableToken('(');
			String output = substitutionEngine.substitute(
					new IVariableLookupCallback() {
							Map<String, String> cache = new HashMap<String, String>();
							public Object getValue(String var) {
								if (cache.containsKey(var))
									return cache.get(var);
								String key = var;
								SuffixOperator operator = null;
								for (int i = 0; i < suffixOperators.length; i++) {
									SuffixOperator temp = suffixOperators[i];
									if (temp.hasSuffix(var)) {
										key = temp.getRoot(var);
										operator = temp;
										break;
									}
								}
								String value = (String) templateValues.get(key);
								if (operator != null)
									value = operator.getConvert().convert(value);
								cache.put(var, value);
								return value;
							}
					}, input);
			output = postProcessContent(output);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(output.getBytes());
			// allow overwriting when used in subclasses
			if (targetFile.exists())
				targetFile.setContents(byteArrayInputStream, true, true, monitor);
			else
				targetFile.create(byteArrayInputStream, true, monitor);
		}
	}
	
	/**
	 * @param input the content of the file after all replacements made
	 * @return the input after any post-processing
	 */
	public String postProcessContent(String input) {
		// may be overridden
		return input;
	}

	private String getSourceAsString(URL sourceFileUrl) throws CoreException {
		String string = null;
		try {
			InputStream is = sourceFileUrl.openStream();
			string = new String(FileUtils.readInputStreamContents(is, null));
		} catch (IOException e) {
			String error = MessageFormat.format(COPY_FILE_ERROR, 
					new Object[] { getProcessName(), e.getLocalizedMessage() });
			fail(error, e);
		}
		return string;
	}

	private URL getSourceFileUrl(IParameter parameter) throws CoreException {
		String sourcePathStr = getRequiredAttributeFromParameter(parameter, SOURCE_PATH_ATTRIBUTE);
		try {
			return new URL(baseSourceUrl, sourcePathStr);
		} catch (MalformedURLException e) {
            throw new CoreException(Logging.newStatus(TemplateWizardPlugin.getDefault(), e));
		}
	}

	protected Plugin getPlugin() {
		return TemplateWizardPlugin.getDefault();
	}
}
