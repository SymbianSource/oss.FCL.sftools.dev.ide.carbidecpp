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
package com.nokia.carbide.cpp.internal.builder.utils.handlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.CoreModelUtil;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.core.resources.FileStorage;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICIncludePathEntry;
import org.eclipse.cdt.core.settings.model.ICLanguageSettingEntry;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.extension.CConfigurationData;
import org.eclipse.cdt.core.settings.model.extension.CFolderData;
import org.eclipse.cdt.core.settings.model.extension.CLanguageData;
import org.eclipse.cdt.internal.ui.util.EditorUtility;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.DefaultMMPViewConfiguration;
import com.nokia.carbide.cdt.builder.EMMPPathContext;
import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cdt.builder.EpocEnginePathHelper;
import com.nokia.carbide.cdt.builder.MMPViewPathHelper;
import com.nokia.carbide.cdt.builder.builder.CarbideCPPBuilder;
import com.nokia.carbide.cdt.builder.builder.CarbideCommandLauncher;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.MMPDataRunnableAdapter;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPLanguage;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPData;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefineFactory;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildInfo;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildInfo;
import com.nokia.carbide.cpp.internal.builder.utils.Activator;
import com.nokia.carbide.cpp.internal.builder.utils.ui.LanguageSelectionDialog;
import com.nokia.carbide.cpp.internal.builder.utils.ui.PreprocessPreferencePage;
import com.nokia.carbide.cpp.sdk.core.ISBSv1BuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.HostOS;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

public class PreprocessHandler extends AbstractHandler {

	private static final String MMP_EXTENSION = ".mmp"; //$NON-NLS-1$
	private static final String INF_EXTENSION = ".inf"; //$NON-NLS-1$
	
	public Object execute(ExecutionEvent event) throws ExecutionException {

		ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (selection == null) {
			return null;
		}

		final IFile selectedFile = getFileToPreprocess(selection);
		
		if (selectedFile == null) {
			return null;
		}

		// Save all open editor windows before starting...
		WorkbenchUtils.saveOpenEditorsIfRequired();
		
		Job buildJob = new Job("Preprocessing file") {
			protected IStatus run(IProgressMonitor monitor) {
				try {
					IProject project = selectedFile.getProject();
					
					monitor.beginTask("Preprocessing " + selectedFile.getName(), 4);
					
			        final ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			        if (cpi != null) {
			        	final IPath path = selectedFile.getLocation();
			        	if (path != null) {
			        		ICarbideBuildConfiguration buildConfig = cpi.getDefaultConfiguration();
			        		
			        		try {
			        			CarbideCPPBuilder.removeAllMarkers(project);
			        		} catch (CoreException e) {
			        			e.printStackTrace();
			        		}

			        		CarbideCommandLauncher launcher = new CarbideCommandLauncher(project, monitor, CarbideCPPBuilder.getParserIdArray(buildConfig.getErrorParserId()), cpi.getINFWorkingDirectory());
							launcher.showCommand(true);

							String cppTool = "cpp" + HostOS.EXE_EXT; //$NON-NLS-1$
							for (String var : CarbideCPPBuilder.getResolvedEnvVars(buildConfig)) {
								if (var.compareTo("ALT_PRE") == 0 || var.startsWith("ALT_PRE=")) { //$NON-NLS-1$ //$NON-NLS-2$
									cppTool = "rcpp" + HostOS.EXE_EXT; //$NON-NLS-1$
									break;
								}
							}
							
							List<String> args = new ArrayList<String>();

							String cppArgs[] = PreprocessPreferencePage.getCppArguments().split(" ");
							if (cppArgs != null && cppArgs.length > 0) {
								for (String arg : cppArgs) {
									args.add(arg);
								}
							}

							monitor.worked(1);
							
							// add the includes and macros
							List<IPath> userIncludes = new ArrayList<IPath>();
							List<IPath> systemIncludes = new ArrayList<IPath>();
							
							if (!getEntriesForConfig(project, userIncludes, systemIncludes)) {
			        			launcher.writeToConsole("\n***Stopping. Unable to get includes for project.\n");
			           			CarbideBuilderPlugin.createCarbideProjectMarker(project, IMarker.SEVERITY_ERROR,  "Unable to get includes and macros for project.", IMarker.PRIORITY_LOW);
								return new Status(IStatus.ERROR, "Carbide.c++ builder utils plugin", IStatus.ERROR, "Failed", null);
							}
							
							for (IPath userInclude : userIncludes) {
								args.add("-I"); //$NON-NLS-1$
				        		args.add("\"" + userInclude.toOSString() + "\""); //$NON-NLS-1$ //$NON-NLS-2$
							}
							
							// implicit inclusion of the directory where the file being preprocessed is located
							args.add("-I"); //$NON-NLS-1$
			        		args.add("\"" + selectedFile.getLocation().removeLastSegments(1).toOSString() + "\""); //$NON-NLS-1$ //$NON-NLS-2$
							
							if (systemIncludes.size() > 0) {
								args.add("-I-"); //$NON-NLS-1$
								
								for (IPath systemInclude : systemIncludes) {
									args.add("-I"); //$NON-NLS-1$
					        		args.add("\"" + systemInclude.toOSString() + "\""); //$NON-NLS-1$ //$NON-NLS-2$
								}
							}
							
							List<String> macros = getMacros(buildConfig, path, monitor);
							for (String macro : macros) {
				        		args.add("-D" + macro); //$NON-NLS-1$
							}

							monitor.worked(1);
							if (monitor.isCanceled()) {
								return new Status(IStatus.CANCEL, "Carbide.c++ builder utils plugin", IStatus.CANCEL, "Cancelled", null); 
							}
							
							// add the compiler prefix file if any
							IPath compilerPrefix = buildConfig.getCompilerPrefixFile();
							if (compilerPrefix != null) {
								args.add("-include"); //$NON-NLS-1$
				        		args.add("\"" + compilerPrefix.toOSString() + "\""); //$NON-NLS-1$ //$NON-NLS-2$
							}

							// add the sdk prefix file if any
							File sdkPrefix = null;
							if (buildConfig.getBuildContext() instanceof ISBSv1BuildContext) {
								sdkPrefix = buildConfig.getSDK().getPrefixFile(ISymbianBuilderID.SBSV1_BUILDER);
							} else {
								sdkPrefix = buildConfig.getSDK().getPrefixFile(ISymbianBuilderID.SBSV2_BUILDER);
							}
							if (sdkPrefix != null && sdkPrefix.exists()) {
								args.add("-include"); //$NON-NLS-1$
				        		args.add("\"" + sdkPrefix.getAbsolutePath() + "\""); //$NON-NLS-1$ //$NON-NLS-2$
							}

							// now add the path to the file
			        		args.add("\"" + path.toOSString() + "\""); //$NON-NLS-1$ //$NON-NLS-2$

			        		// output to a temp file
			        		IPath topMakefileDir = new Path(buildConfig.getSDK().getEPOCROOT()).append("epoc32\\build"); //$NON-NLS-1$
			        		topMakefileDir = topMakefileDir.append(cpi.getINFWorkingDirectory().setDevice(null));
			        		
			        		topMakefileDir.toFile().mkdirs();
			        		
			        		final IPath tempFilePath = topMakefileDir.append(path.removeFileExtension().lastSegment() + ".ii");
			        		final File tempFile = tempFilePath.toFile();
			        		if  (tempFile.exists()) {
			        			tempFile.delete();
			        		}
			        		
			        		try {
								tempFile.createNewFile();
							} catch (IOException e) {
								CarbideBuilderPlugin.log(e);
							}
			        		
			        		args.add("-o"); //$NON-NLS-1$
			        		args.add(tempFile.getAbsolutePath());
			        		
			        		launcher.writeToConsole("\n***Preprocessing " + path.toOSString() + "\n");
			        		
			        		monitor.worked(1);
			        		
			        		int retVal = launcher.executeCommand(new Path(cppTool), args.toArray(new String[args.size()]), CarbideCPPBuilder.getResolvedEnvVars(buildConfig), cpi.getINFWorkingDirectory());
			        		if (retVal != 0) {
			        			launcher.writeToConsole("\n=== Preprocess command failed with error code " + retVal + " ===");
			        			launcher.writeToConsole("\n***Stopping. Check the Problems view or Console output for errors.\n");
			           			CarbideBuilderPlugin.createCarbideProjectMarker(project, IMarker.SEVERITY_ERROR, cppTool + " returned with exit value = " + retVal, IMarker.PRIORITY_LOW);
			        		}
			        		
			        		monitor.worked(1);

			        		if (PreprocessPreferencePage.outputToConsole()) {
				        		// open the file and pipe the contents to the console
								try {
				        			launcher.writeToConsole("\n");

				        			// take it one line at a time so we don't overwhelm the console view.  note that
				        			// we send the text pretty fast but it takes a lot longer for the console to actually
				        			// render it, so canceling the job may appear to not work
				        			String text = new String(FileUtils.readFileContents(tempFile, null));
									for (String line : text.split("\n")) {
								        if (monitor.isCanceled()) {
											return new Status(IStatus.CANCEL, "Carbide.c++ builder utils plugin", IStatus.CANCEL, "Cancelled", null); 
										}
								        launcher.writeToConsole(line);
									}
								} catch (CoreException e) {
									CarbideBuilderPlugin.log(e);
								}
			        		} else {
			        			// open file in a new editor window
				    			Display.getDefault().asyncExec(new Runnable() {
				    				public void run() {
										try {
											EditorUtility.openInEditor(new FileStorage(tempFilePath));
										} catch (PartInitException e) {
											CarbideBuilderPlugin.log(e);
										} catch (CModelException e) {
											CarbideBuilderPlugin.log(e);
										}
				    				}
				    			});
			        		}
			        		
			        		launcher.writeToConsole(launcher.getTimingStats());
			        	}
			        }

			        if (monitor.isCanceled()) {
						return new Status(IStatus.CANCEL, "Carbide.c++ builder utils plugin", IStatus.CANCEL, "Cancelled", null); 
					}
				} finally {
					monitor.done();
				}

				return new Status(IStatus.OK, "Carbide.c++ builder utils plugin", IStatus.OK, "Complete", null); 
			}
		};
		
		buildJob.setPriority(Job.BUILD);
		buildJob.setUser(true);
		buildJob.schedule();

		return null;
	}

	/**
	 * If the selected file is a translation unit, bld.inf or mmp file, returns
	 * the IFile, otherwise returns null
	 * @param selection the selection
	 * @return the IFile, or null
	 */
	public static IFile getFileToPreprocess(ISelection selection) {
		
		IFile selectedFile = null;

		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection)selection;
			if (ss.size() == 1) {
				Object selectionItem = ss.getFirstElement();
				if (selectionItem instanceof ITranslationUnit) {
					ITranslationUnit tu = (ITranslationUnit) selectionItem;
					if (tu.isSourceUnit() && tu.getResource() instanceof IFile) {
						selectedFile = (IFile)tu.getResource();
					}
				} else {
					IFile file = null;
					if (selectionItem instanceof IFile) {
						file = (IFile)selectionItem;
					} else if (selectionItem instanceof IAdaptable) {
						file = (IFile)((IAdaptable)selectionItem).getAdapter(IFile.class);
					}

					if (file != null) {
						ITranslationUnit tu = CoreModelUtil.findTranslationUnit(file);
						if (tu != null && tu.isSourceUnit()) {
							selectedFile = file;
						} else {
							String filename = file.getName().toLowerCase();
							if (filename.endsWith(MMP_EXTENSION) || filename.endsWith(INF_EXTENSION)) {
								selectedFile = file;
							}
						}
					}
				}
			}
		} else if (selection instanceof ITextSelection) {
			IWorkbenchWindow window = Activator.getActiveWorkbenchWindow();
			if (window != null) {
				IWorkbenchPage page = window.getActivePage();
				if (page != null) {
					IWorkbenchPart part = page.getActivePart();
					if (part instanceof IEditorPart) {
						IEditorPart epart = (IEditorPart) part;
						IResource resource = (IResource) epart.getEditorInput().getAdapter(IResource.class);
						ICElement element = CoreModel.getDefault().create(resource);
						if (element instanceof ITranslationUnit) {
							ITranslationUnit tu = (ITranslationUnit) element;
							if (tu.isSourceUnit() && tu.getResource() instanceof IFile) {
								selectedFile = (IFile)tu.getResource();
							}
						} else {
							if (resource instanceof IFile) {
								IFile file = (IFile)resource;
								String filename = file.getName().toLowerCase();
								if (filename.endsWith(MMP_EXTENSION) || filename.endsWith(INF_EXTENSION)) {
									selectedFile = file;
								}
							}
						}
					}
				}
			}
		}
		
		return selectedFile;
	}

	private boolean getEntriesForConfig(IProject project, List<IPath> userIncludes, List<IPath> systemIncludes) {
		ICProjectDescription projDes = CoreModel.getDefault().getProjectDescription(project);
		if (projDes != null) {
			ICConfigurationDescription configDes = projDes.getActiveConfiguration();
			if (configDes != null) {
				CConfigurationData configData = configDes.getConfigurationData();
				if (configData != null) {
					CFolderData rootFolderData = configData.getRootFolderData();
					if (rootFolderData != null) {
						CLanguageData[] languageDatas = rootFolderData.getLanguageDatas();
						if (languageDatas != null && languageDatas.length > 0) {
							CLanguageData languageData = languageDatas[0];
							ICLanguageSettingEntry[] includeEntries = languageData.getEntries(ICLanguageSettingEntry.INCLUDE_PATH);
							if (includeEntries != null) {
								for (ICLanguageSettingEntry includeEntry : includeEntries) {
									if (includeEntry instanceof ICIncludePathEntry) {
										ICIncludePathEntry include = (ICIncludePathEntry)includeEntry;
										if (include.isLocal()) {
											userIncludes.add(include.getLocation());
										} else {
											systemIncludes.add(include.getLocation());
										}
									}
								}
								return true;
							}
						}
					}
				}
			}
		}

		return false;
	}
	
	private List<String> getMacros(ICarbideBuildConfiguration buildConfig, final IPath filePath, final IProgressMonitor monitor) {
		final List<String> macros = new ArrayList<String>();
		ISymbianSDK sdk = buildConfig.getSDK();
		if (buildConfig.getBuildContext() instanceof ISBSv1BuildContext) {
			ISBSv1BuildInfo sbsv1BuildInfo = (ISBSv1BuildInfo)sdk.getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER);
			// platform macros
			for (String platMacro : sbsv1BuildInfo.getPlatformMacros(buildConfig.getPlatformString())) {
				macros.add("__" + platMacro + "__"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			// vendor macros (e.g. __SERIES60_3x__)
			for (String builtinMacro : sbsv1BuildInfo.getVendorSDKMacros()) {
				macros.add(builtinMacro);
			}
		} else {
			ISBSv2BuildInfo sbsv2BuildInfo = (ISBSv2BuildInfo)sdk.getBuildInfo(ISymbianBuilderID.SBSV2_BUILDER);
			// platform macros
			for (Iterator<String> it = sbsv2BuildInfo.getPlatformMacros(buildConfig.getPlatformString()).keySet().iterator(); it.hasNext(); ) { 
				String platMacro = it.next();
				macros.add(platMacro);
			} 
		}
		
		// built in macros
		for (String builtinMacro : buildConfig.getBuiltinMacros()) {
			macros.add(builtinMacro);
		}
		
		IProject project = buildConfig.getCarbideProject().getProject();
		
		List<IPath> mmps = EpocEngineHelper.getMMPsForSource(project, filePath);
		if (mmps.size() > 0) {
			// just use the first mmp I guess...
			EpocEnginePathHelper helper = new EpocEnginePathHelper(project);
			IPath workspaceRelativeMMPPath = helper.convertToWorkspace(mmps.get(0));
			
			// target type macro (e.g. __DLL__)
			EpocEnginePlugin.runWithMMPData(workspaceRelativeMMPPath, 
					new DefaultMMPViewConfiguration(project, buildConfig.getBuildContext(), new AcceptedNodesViewFilter()), 
					new MMPDataRunnableAdapter() {

					public Object run(IMMPData mmpData) {
						String targetType = mmpData.getSingleArgumentSettings().get(EMMPStatement.TARGETTYPE);
						if (targetType != null) {
							targetType = targetType.toUpperCase();
							macros.add(targetType);
						}
						
						// mmp macros
						List<String> macroList = mmpData.getListArgumentSettings().get(EMMPStatement.MACRO);
						for (String macro : macroList) {
							// Symbian docs say they are converted to upper case always
							macros.add(macro.toUpperCase());
						}

						// also need to get language macro for resource files
						if (FileUtils.getSafeFileExtension(filePath).compareTo("rss") == 0) {
							List<EMMPLanguage> languages = null;
							MMPViewPathHelper helper = new MMPViewPathHelper(mmpData, (IPath)null);

							for (IMMPResource resBlock : mmpData.getResourceBlocks()) {
								IPath fullPath = helper.convertMMPToFilesystem(EMMPPathContext.START_RESOURCE, resBlock.getSource());
								if (fullPath.equals(filePath)) {
									languages = resBlock.getLanguages();
									break;
								}
							}
							
							// if not a resource block or resource block has no language specified, see if there's
							// an mmp language specified
							if (languages == null || languages.size() == 0) {
								languages = mmpData.getLanguages();
								if (languages == null || languages.size() == 0) {
									// default is non-localize
									languages = Collections.singletonList(EMMPLanguage.SC_NonLocalized);
								}
							}
							
							final List<String> languageMacro = new ArrayList<String>(1);
							languageMacro.add("LANGUAGE_" + languages.get(0).getCodeString());
							
							// ask the user which language to preprocess for (if more than one)
							if (languages.size() > 1) {
								languageMacro.clear();
								
								final List<EMMPLanguage> finalLanguages = languages;
								Display.getDefault().syncExec(new Runnable() {

									public void run() {
										List<String> langs = new ArrayList<String>();
										for (EMMPLanguage lang : finalLanguages) {
											langs.add(lang.getName());
										}
										
										LanguageSelectionDialog dlg = new LanguageSelectionDialog(WorkbenchUtils.getSafeShell(), langs);
										if (Window.OK == dlg.open()) {
											languageMacro.add("LANGUAGE_" + finalLanguages.get(dlg.getselectedIndex()).getCodeString());
										} else {
											monitor.setCanceled(true);
										}
									}
								});
							}
							
							if (languageMacro.size() > 0) {
								macros.add(languageMacro.get(0));
							}
						}
						
						return null;
					}
			});
		}
		
		return macros;
	}
}
