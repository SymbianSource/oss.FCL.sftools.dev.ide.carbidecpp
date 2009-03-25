/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.model.makefile.image;

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.image.*;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.ArgList;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.*;
import com.nokia.carbide.internal.cpp.epoc.engine.image.MultiImageSource;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ModelBase;
import com.nokia.carbide.internal.cpp.epoc.engine.model.makefile.MakefileViewBase;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.cdt.make.core.makefile.*;
import org.eclipse.core.runtime.IPath;

import java.util.*;
import java.util.regex.Pattern;


public class ImageMakefileView extends MakefileViewBase implements IImageMakefileView {
	private static final String EPOCROOT_VAR = "EPOCROOT"; //$NON-NLS-1$
	private static final String ZDIR_VAR = "ZDIR"; //$NON-NLS-1$
	private IImageMakefileViewConfiguration config;
	private List<IMultiImageSource> multiImageSources;
	private List<IMultiImageSource> origMultiImageSources;
	private String defaultTarget;
	
	public ImageMakefileView(ModelBase model, IImageMakefileViewConfiguration viewConfiguration) {
		super(model, viewConfiguration);
		this.config = viewConfiguration;
		
		// validate configuration
		Check.checkArg(config.getImageBuilderCommandLineConverter());
		
		multiImageSources = new ArrayList<IMultiImageSource>();
		origMultiImageSources = copyMultiImageSources(multiImageSources);
		
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IData#getModelPath()
	 */
	public IPath getModelPath() {
		return getModel().getPath();
	}
	
	/**
	 * @param multiImageSources2
	 * @return
	 */
	private List<IMultiImageSource> copyMultiImageSources(List<IMultiImageSource> from) {
		List<IMultiImageSource> copy = new ArrayList<IMultiImageSource>();
		for (IMultiImageSource mis : from) {
			copy.add(mis.copy());
		}
		return copy;
	}

	private ICommand[] getImageBuilderCommands() {
		String builder = config.getImageBuilderName();
		return findCommandsInvoking(builder);
	}


	/** Create a build container from the apparent build command. */
	private IMultiImageSource createMultiImageSourceFromImageBuilder(ICommand command) {
		String commandString = fixBrokenCatenatedLines(command.toString()); 
		ITargetRule targetRule = (ITargetRule)command.getParent();
		ArgList args = new ArgList(expandAllMacrosInRuleString(commandString, targetRule));
		List<String> argv = args.getArgv();

		// must compile every time since it depends on macros that may be
		// defined in the makefile itself
		Pattern pattern = Pattern.compile(getProgramMatcherPattern(config.getImageBuilderName()), 
				Pattern.CASE_INSENSITIVE);
		if (pattern.matcher(argv.get(0)).matches()) {
			return config.getImageBuilderCommandLineConverter().parse(this, argv);
		}
		
		return null;
	}

	/**
	 * CDT's make engine is incorrectly handling line continuations.
	 * @param string
	 * @return
	 */
	private String fixBrokenCatenatedLines(String string) {
		return string.replaceAll("\\\\(\\s+)", "$1"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.makefile.MakefileView#internalRevertChanges()
	 */
	@Override
	protected void internalRevertChanges() {
		super.internalRevertChanges();
		
		multiImageSources.clear();
		
		ICommand[] commands = getImageBuilderCommands();
		for (ICommand command : commands) {
			IMultiImageSource source = createMultiImageSourceFromImageBuilder(command);
			if (source == null)
				continue;
			
			multiImageSources.add(source);
		}
		
		origMultiImageSources = copyMultiImageSources(multiImageSources);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.makefile.MakefileView#internalHasChanges()
	 */
	@Override
	protected boolean internalHasChanges() {
		return super.internalHasChanges() || !multiImageSources.equals(origMultiImageSources); 
	}
	
	@Override
	protected void internalCommit() {
		// update shadow document
		Set<String> handledTargets = new HashSet<String>();
		
		String lastBuildTarget = null;
		
		boolean changed;

		// we can't rely on ICommand instances when we issue #replaceDirective() or #deleteDirective()
		// so we need to iterate through to loop, regathering commands each time.
		do {
			changed = false;
			ICommand[] commands = getImageBuilderCommands();
			// match up existing build commands, trying not to change text unless necessary
			for (ICommand command : commands) {
				IMultiImageSource origSource = createMultiImageSourceFromImageBuilder(command);
				if (origSource == null)
					continue;
				
				lastBuildTarget = ((IRule) command.getParent()).getTarget().toString();
				
				IPath target = origSource.getTargetPath().append(origSource.getTargetFile());
				IMultiImageSource model = null;
				for (IMultiImageSource c : multiImageSources) {
					if (!c.isValid()) {
						EpocEnginePlugin.log(new IllegalArgumentException("ignoring invalid image build container: " + c)); //$NON-NLS-1$
						continue;
					}
					if (c.getTargetFilePath().toOSString().equalsIgnoreCase(target.toOSString())) {
						model = c;
						break;
					}
				}
				
				if (model != null) {
					handledTargets.add(target.toOSString().toLowerCase());
					if (origSource.equals(model)) {
						// no change
						continue;
					} else {
						// updated
						String commandLine = createCommandLineFromImageBuilder(model, command);
						
						// gather the original dependencies and referenced files
						ITargetRule targetRule = (ITargetRule)command.getParent();
						List<IPath> dependencies = null;
						List<IPath> originalReferencedFiles = null;
						dependencies = getDependencyPaths(targetRule);
						
						String commandString = fixBrokenCatenatedLines(command.toString()); 
						ArgList args = new ArgList(expandAllMacrosInRuleString(commandString, targetRule));
						IMultiImageSource origModel = config.getImageBuilderCommandLineConverter().parse(this, args.getArgv());
						if (origModel != null)
							originalReferencedFiles = getAllReferencedFiles(origModel.getSources());
						else
							originalReferencedFiles = Collections.EMPTY_LIST;

						// update dependencies for building this file
						if (!targetRule.getTarget().toString().contains(".") 
								&& !targetRule.getTarget().toString().contains("$")
								&& dependencies.isEmpty() && !originalReferencedFiles.isEmpty()) {
							// this looks like an old-style rule that has no actual target
							// file or dependencies (e.g. "RESOURCE:"), so leave it alone
							replaceDirective(command, commandLine);
						} else {
							// update the target rule, with dependencies, and the command
							updateDependencies(dependencies, originalReferencedFiles, getAllReferencedFiles(model.getSources()));
							String targetRuleString = getUpdatedTargetRuleString(targetRule, dependencies);

							// replace the command first...
							replaceDirective(command, commandLine);
							
							// refind the rule we're replacing
							targetRule = findRuleForTarget(targetRule.getTarget().toString(), false);
							Check.checkState(targetRule != null);
							
							replaceTarget(targetRule, targetRuleString);
						}
					}
				} else {
					// removed
					deleteDirective(command);
					if (lastBuildTarget != null) {
						// remove the target rule if it directly built this file
						ITargetRule directImageBuildingRule = findRuleForTarget(target.toOSString().toLowerCase(), false);
						if (directImageBuildingRule != null && directImageBuildingRule.getCommands().length == 0) {
							deleteDirective(directImageBuildingRule);
						}
					}
					changed = true;
					break;
				}
			}
		} while (changed);

		for (IMultiImageSource model : multiImageSources) {
			if (!model.isValid()) {
				EpocEnginePlugin.log(new IllegalArgumentException("ignoring invalid image build container: " + model)); //$NON-NLS-1$
				continue;
			}
			if (!handledTargets.contains(model.getTargetFilePath().toOSString().toLowerCase())) {

				// new mif entry
				String commandLine = createCommandLineFromImageBuilder(model, null);

				// any build rules?
				List<IPath> dependencies = getAllReferencedFiles(model.getSources());
				
				String ruleText;
				ITargetRule lastBuildRule = null;
				if (lastBuildTarget != null) {
					lastBuildRule = findRuleForTarget(lastBuildTarget, false);
				}
				if (lastBuildTarget == null && defaultTarget != null) {
					lastBuildRule = findRuleForTarget(defaultTarget, false);
				}
				if (lastBuildRule == null) {
					// synthesize one
					String rulePrefix;
					if (defaultTarget == null) {
						rulePrefix = getUnexpandedMultiImageSourceTargetPath(model)
								+ ":"; //$NON-NLS-1$
					} else {
						rulePrefix = defaultTarget + ":"; //$NON-NLS-1$
					}
					ruleText = rulePrefix + getUpdatedTargetRuleString(null, dependencies);
					
					appendText(ruleText + commandLine);
				} else {
					// the build rule did not name the target, so it's not in the form
					// that accepts dependencies
					insertText(lastBuildRule, commandLine);
				}
			}
		}
		
		super.internalCommit();
	}
	
	/** Given either an existing original command or null for a new command,
	 * create a pair for the command line and target line for the image builder.
	 * @param model
	 * @param origCommand
	 * @return Pair of the command line and the target
	 */
	private String createCommandLineFromImageBuilder(IMultiImageSource model, ICommand origCommand) {
		List<String> argv = null;
		if (origCommand != null) {
			String commandString = fixBrokenCatenatedLines(origCommand.toString()); 
			ITargetRule targetRule = (ITargetRule)origCommand.getParent();
			ArgList args = new ArgList(expandAllMacrosInRuleString(commandString, targetRule));
			argv = args.getArgv();
		}
		List<String> newArgs = config.getImageBuilderCommandLineConverter().create(this, model, argv);
		if (newArgs == null)
			return "\t" + getEOL(); //$NON-NLS-1$
		ArgList argList = new ArgList("\t", newArgs, getEOL()); //$NON-NLS-1$ //$NON-NLS-2$
		
		return argList.toString();
	}

	/**
	 * Return the text of the target rule with the given dependency list
	 * @param targetRule
	 * @param dependencies
	 * @return new target rule text
	 */
	private String getUpdatedTargetRuleString(ITargetRule targetRule,
			List<IPath> dependencies) {
		String targetName = null;
		if (targetRule != null) {
			targetName = targetRule.getTarget().toString();
		}
		ArgList args = new ArgList(""); //$NON-NLS-1$
		for (IPath path : dependencies) {
			String depString = convertProjectToModelPath(path).toOSString();
			depString = unexpandMacros(depString, true);
			args.getArgv().add(depString);
		}
		String depText;
		if (args.getArgv().size() == 0)
			depText = getEOL(); //$NON-NLS-1$
		else
			depText = " \\"  + getEOL() + "\t\t" + args.toString(" \\" + getEOL() + "\t\t") + getEOL(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		if (targetRule != null)
			return targetName + ":" + depText; //$NON-NLS-1$
		else
			return depText;
	}

	/**
	 * Update the list of original dependencies against the list of referenced files
	 * @param dependencies the parsed dependencies from a rule, updated
	 * @param originalReferencedFiles the files originally referenced by the command
	 * @param allReferencedFiles the files now referenced by the command
	 */
	private void updateDependencies(List<IPath> dependencies,
			List<IPath> originalReferencedFiles,
			List<IPath> allReferencedFiles) {
		for (IPath path : originalReferencedFiles) {
			if (!allReferencedFiles.contains(path)) {
				// remove old dependency
				dependencies.remove(path);
			}
		}
		for (IPath path : allReferencedFiles) {
			if (!dependencies.contains(path)) {
				// add new dependency
				dependencies.add(path);
			}
		}
	}

	/**
	 * Get the project-relative paths referenced the image sources
	 * @param sources
	 * @return
	 */
	private List<IPath> getAllReferencedFiles(List<IImageSource> sources) {
		List<IPath> files = new ArrayList<IPath>();
		for (IImageSource source : sources) {
			IPath path = source.getPath();
			if (path != null)
				files.add(path);
			if (source instanceof IBitmapSource) {
				path = ((IBitmapSource) source).getMaskPath();
				if (path != null)
					files.add(path);
			}
		}
		return files;
	}

	/**
	 * Get the project-relative paths to dependency files
	 * @param targetRule
	 * @return dependencies
	 */
	private List<IPath> getDependencyPaths(ITargetRule targetRule) {
		List<IPath> paths = new ArrayList<IPath>();
		String[] deps = targetRule.getPrerequisites();
		for (String dep : deps) {
			dep = expandAllMacrosInString(dep);
			IPath path = convertModelToProjectPath(FileUtils.createPossiblyRelativePath(dep));
			paths.add(path);
		}
		return paths;
	}

	@Override
	public boolean merge() {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileView#createImageBuildContainer()
	 */
	public IMultiImageSource createMultiImageSource() {
		return new MultiImageSource(true, true, true);
	}
	
	public List<IMultiImageSource> getMultiImageSources() {
		return multiImageSources;
	}

	/**
	 * @param source
	 * @return
	 */
	public String getUnexpandedMultiImageSourceTargetPath(IMultiImageSource source) {
		// target (use exhaustive search since ZDIR is conditionalized)
		if (source.getTargetPath() == null || source.getTargetPath().segmentCount() == 0)
			return source.getTargetFile();
		
		IPath targetPath = source.getTargetFilePath();
		String target;
		if (!targetPath.isAbsolute()) {
			// add ZDIR if available
			IMacroDefinition[] zDirVars = getAllMacroDefinitions(ZDIR_VAR);
			if (zDirVars.length > 0)
				target = "$(" + ZDIR_VAR + ")" + targetPath.makeAbsolute().toOSString(); //$NON-NLS-1$ //$NON-NLS-2$
			else
				target = "$(" + EPOCROOT_VAR + ")" + targetPath.toOSString(); //$NON-NLS-1$ //$NON-NLS-2$
		}
		else
			target = targetPath.toOSString();
		return unexpandMacros(target, true);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileView#setDefaultImageTarget(java.lang.String)
	 */
	public void setDefaultImageTarget(String targetName) {
		this.defaultTarget = targetName;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileView#getDefaultImageTarget()
	 */
	public String getDefaultImageTarget() {
		return defaultTarget;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.makefile.MakefileViewBase#getData()
	 */
	@Override
	public IImageMakefileData getData() {
		return new ImageMakefileData(this);
	}
}
