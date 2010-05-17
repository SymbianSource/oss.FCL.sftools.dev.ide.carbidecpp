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
package com.nokia.carbide.cdt.internal.builder;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.extension.IEnvironmentModifier;
import com.nokia.carbide.cdt.builder.project.*;
import com.nokia.carbide.cdt.internal.builder.ui.BuilderPreferencePage;
import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.internal.x86build.X86BuildPlugin;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.cpp.internal.api.utils.core.HostOS;
import com.nokia.cpp.internal.api.utils.core.PathUtils;
import com.nokia.cpp.internal.api.utils.core.TrackedResource;

import org.eclipse.cdt.core.settings.model.ICStorageElement;
import org.eclipse.cdt.utils.spawner.EnvironmentReader;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import java.io.File;
import java.util.*;

public class EnvironmentVarsInfo2 implements IEnvironmentVarsInfo {

	// NOTE: all the paths below use Win32 paths.  We convert these to Unix format at the end of the
	// #getResolved...() and #getModified...() methods.
	
	private static final String X86_COMP_LINK_FOLDER = "Symbian_Tools\\Command_Line_Tools";
	
	private static final String X86_WINLIBS_FOLDER = "Symbian_Support\\Runtime\\Runtime_x86\\Runtime_Win32\\Libs";
	private static final String X86_WIN32SDK_FOLDER = "Symbian_Support\\Win32-x86 Support\\Libraries\\Win32 SDK";

	private static final String X86_LINK_LIB_LIST = "MSL_All_MSE_Symbian_D.lib;gdi32.lib;user32.lib;kernel32.lib;";
	                        
	// use host-independent case
	private static final String MWCSYM2INCLUDES = "MWCSym2Includes";
	private static final String MWSYM2LIBRARIES = "MWSym2Libraries";
	private static final String MWSYM2LIBRARYFILES = "MWSym2LibraryFiles";
	private static final String MWCINCLUDES = "MWCIncludes";
	private static final String PATH = "PATH";
	private static final String EPOCROOT = "EPOCROOT";
	
	private static final String MSL_COMMON_INCLUDE = "Symbian_Support\\MSL\\MSL_C\\MSL_Common\\Include";
	private static final String MSL_WIN32_INCLUDE = "Symbian_Support\\MSL\\MSL_C\\MSL_Win32\\Include";
	private static final String MSL_C_MSL_X86 = "Symbian_Support\\MSL\\MSL_C\\MSL_X86";
	private static final String MSL_CPP_COMMON_INCLUDE = "Symbian_Support\\MSL\\MSL_C++\\MSL_Common\\Include";
	private static final String MSL_EXTRAS_COMMON_INCLUDE = "Symbian_Support\\MSL\\MSL_Extras\\MSL_Common\\Include";
	private static final String MSL_EXTRAS_WIN32_INCLUDE = "Symbian_Support\\MSL\\MSL_Extras\\MSL_Win32\\Include";
	private static final String MSL_WIN32_SDK = "Symbian_Support\\Win32-x86 Support\\Headers\\Win32 SDK";
	
	private static final String NO_DEPENDENCIES = "NO_DEPENDENCIES";
	private static final String CARBIDE_NO_DEPENDENCIES = "CARBIDE_NO_DEPENDENCIES";
	private static final String NO_DEPENDENCIES_VALUE = "-nd";

	private static final String VAR_SEPARATOR = ";";
	private static final String EQUALS = "=";
	
	private static final String ENV_VAR_STORAGE = "ENV_VAR_STORAGE";
	private static final String ENV_VAR_NAME_STORAGE = "ENV_VAR_NAME_STORAGE";
	private static final String ENV_VAR_VALUE_STORAGE = "ENV_VAR_VALUE_STORAGE";
	private static final String ENV_VAR_TYPE_STORAGE = "ENV_VAR_TYPE_STORAGE";
	
	private TrackedResource projectTracker;
	private final ISymbianBuildContext context;
	private List<IEnvironmentVariable> envVarsList = new ArrayList<IEnvironmentVariable>();
	

	EnvironmentVarsInfo2(IProject project, ISymbianBuildContext context) {
		this.projectTracker = new TrackedResource(project);
		this.context = context;
	}

	@SuppressWarnings("deprecation")
	public EnvironmentVarsInfo2(EnvironmentVarsInfo oldInfo) {
		projectTracker = new TrackedResource(oldInfo.getProject());
		context = oldInfo.getContext();
		envVarsList.addAll(oldInfo.getModifiedEnvVarsListFromSettings());
	}
	
	public void loadFromStorage(ICStorageElement rootStorage) {
		for (ICStorageElement se : rootStorage.getChildren()) {
			if (se.getName().equals(ENV_VAR_STORAGE)) {
				String name = se.getAttribute(ENV_VAR_NAME_STORAGE);
				String value = se.getAttribute(ENV_VAR_VALUE_STORAGE);
				String typeString = se.getAttribute(ENV_VAR_TYPE_STORAGE);

				int type =  IEnvironmentVariable.ENV_VAR_USE_REPLACE;
				try {
					type = Integer.parseInt(typeString);
				} catch (NumberFormatException e) {
				}
					
				envVarsList.add(new EnvironmentVariable(name, value, type));
			}
		}
	}
	
	public void saveToStorage(ICStorageElement rootStorage) {

		for (IEnvironmentVariable currEnvVar : envVarsList) {
			ICStorageElement varStorage = rootStorage.createChild(ENV_VAR_STORAGE);
			varStorage.setAttribute(ENV_VAR_NAME_STORAGE, currEnvVar.getName());
			varStorage.setAttribute(ENV_VAR_VALUE_STORAGE, currEnvVar.getValue());
			varStorage.setAttribute(ENV_VAR_TYPE_STORAGE, Integer.toString(currEnvVar.getUse()));
		}
	}

	public List<IEnvironmentVariable> getModifiedEnvVarsListFromSettings() {
		return envVarsList;
	}
	
	public IEnvironmentVariable getEnvVarFromConfiguration(String varName) {
		
		for (IEnvironmentVariable currVar : envVarsList) {
			if (currVar.getName().equalsIgnoreCase(varName)) {
				return currVar;
			}
		}
		
		return null;
	}

	public String[] getResolvedEnvironmentVariables() {
		ISymbianSDK sdk = null;
		IPath epocRoot = null;
		if (context != null) {
			sdk = context.getSDK();
			if (sdk != null) {
				epocRoot = new Path(sdk.getEPOCROOT());
			}
		}
		
		if (epocRoot == null) {
			return new String[0];
		}
		
		String returnEnvArray[] = EnvironmentReader.getRawEnvVars(); // This the environment we will modify to send back
		
		// Iterate through the variables in the configuration settigns and make the new environement
		for (IEnvironmentVariable currEnvVar : envVarsList) {

			boolean foundRawEnv = false;
			int i = 0;
			for (String rawVar : returnEnvArray) {
				String tempVarName = currEnvVar.getName().toUpperCase() + EQUALS;
				if ((rawVar.toUpperCase() + EQUALS).startsWith(tempVarName)) {
					if (currEnvVar.getUse() == IEnvironmentVariable.ENV_VAR_USE_PREPEND) {
						String prependValue = currEnvVar.getName() + EQUALS + currEnvVar.getValue();
						if (!prependValue.endsWith(VAR_SEPARATOR)) {
							prependValue += VAR_SEPARATOR;
						}
						prependValue += rawVar.substring(currEnvVar.getName().length() + 1);
						returnEnvArray[i] = prependValue;
					} else if (currEnvVar.getUse() == IEnvironmentVariable.ENV_VAR_USE_APPEND) {
						String appendValue = returnEnvArray[i];
						if (!appendValue.endsWith(VAR_SEPARATOR)) {
							appendValue += VAR_SEPARATOR;
						}
						appendValue += currEnvVar.getValue();
						returnEnvArray[i] = appendValue;
					} else if (currEnvVar.getUse() == IEnvironmentVariable.ENV_VAR_USE_REPLACE) {
						returnEnvArray[i] = currEnvVar.getName() + EQUALS + currEnvVar.getValue();
					} else if (currEnvVar.getUse() == IEnvironmentVariable.ENV_VAR_USE_UNDEFINE) {
						returnEnvArray[i] = currEnvVar.getName() + EQUALS + currEnvVar.getValue();
					} 
					foundRawEnv = true;
					break;
				}
								
				i++; // next raw var
			}
			
			if (foundRawEnv == false) {
				// variable does not exist in system, add it as is.
				returnEnvArray = addToArray(returnEnvArray, currEnvVar.getName() + EQUALS + currEnvVar.getValue());
			}
		}
		
		// Now check for specific variables relating to the Nokia x86 environment
		// and update the environment that we just updated, if at all.
		if (context.getPlatformString().equals(ISymbianBuildContext.EMULATOR_PLATFORM) &&
			BuilderPreferencePage.useBuiltInX86Vars()) {
			
			int i = 0;
			
			for (String currEnvVar : returnEnvArray) {
				
				if ((currEnvVar.toUpperCase() + EQUALS).startsWith(EPOCROOT)) {
					// EPOCROOT can only work in replace mode. So always change this value
					// with the current EPOCROOT unless the user has specified that it be replaced.
					IEnvironmentVariable epocRootVar = getEnvVarFromConfiguration(EPOCROOT);
					if (epocRootVar != null && epocRootVar.getUse() == IEnvironmentVariable.ENV_VAR_USE_REPLACE) {
						// do nothing... it's already been replaced above...
					} 
					else {
						String epocRootStr = epocRoot.toOSString();
						if (epocRootStr.indexOf(":") == 1) {
							// only strip the drive for SBSv1
							if (!CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(projectTracker.getProject())) {
								epocRootStr = epocRootStr.substring(2);
							}
						}
						returnEnvArray[i] = EPOCROOT + EQUALS + epocRootStr;
					}	
				} else if ((currEnvVar.toUpperCase() + EQUALS).startsWith(PATH + EQUALS)) {
					// Prepend the path of the compiler always...
					String prependValue = PATH + EQUALS + getNokiaX86BuildDir() + X86_COMP_LINK_FOLDER + VAR_SEPARATOR;
					prependValue += epocRoot.toOSString() + "epoc32\\tools" + VAR_SEPARATOR;
					prependValue += epocRoot.toOSString() + "epoc32\\gcc\\bin" + VAR_SEPARATOR;

					if (CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(projectTracker.getProject())) {
						// add the sbs bin directory to the path
						IPath sbsHome = SBSv2Utils.getSBSBinDirectory();
						if (sbsHome != null) {
							prependValue += sbsHome.toOSString() + VAR_SEPARATOR;
						}
					}

					prependValue += currEnvVar.substring(PATH.length() + 1);
					returnEnvArray[i] = prependValue;
				} else if ((currEnvVar.toUpperCase() + EQUALS).startsWith(MWCSYM2INCLUDES.toUpperCase() + EQUALS)) {
						returnEnvArray[i] = MWCSYM2INCLUDES + EQUALS + getMWIncludesEnvString(); 
					//}
				} else if ((currEnvVar.toUpperCase() + EQUALS).startsWith(MWSYM2LIBRARIES.toUpperCase() + EQUALS)) {
						returnEnvArray[i] = MWSYM2LIBRARIES + EQUALS + getMWLibraryIncludesEnvString();
					
				} else if ((currEnvVar.toUpperCase() + EQUALS).startsWith(MWSYM2LIBRARYFILES.toUpperCase() + EQUALS)) {
						returnEnvArray[i] = MWSYM2LIBRARYFILES + EQUALS + getMWLibraryFilesEnvString();
				}
				
				i++;  // next variable index
				
			} // for
			
			// Double check to make sure that the MW* variables even exist
			// If not we'll need to add them as the installer doesn't
			if (System.getenv(MWCSYM2INCLUDES) == null) {
				returnEnvArray = addToArray(returnEnvArray, MWCSYM2INCLUDES + EQUALS + getMWIncludesEnvString());
			}
			if (System.getenv(MWSYM2LIBRARIES) == null) {
				returnEnvArray = addToArray(returnEnvArray, MWSYM2LIBRARIES + EQUALS + getMWLibraryIncludesEnvString());
			}
			if (System.getenv(MWSYM2LIBRARYFILES) == null) {
				returnEnvArray = addToArray(returnEnvArray, MWSYM2LIBRARYFILES + EQUALS + getMWLibraryFilesEnvString());
			}
			// fix for bug #7489
			if (System.getenv(MWCINCLUDES) == null) {
				returnEnvArray = addToArray(returnEnvArray, MWCINCLUDES + EQUALS + getMWIncludesEnvString());
			}
		}
		else { 
			// This isn't emulation and/or we're just using the default environment.
			// Set up EPOCROOT and PATH vars for SDK...
			int i = 0;
			for (String currEnvVar : returnEnvArray) {
				if ((currEnvVar.toUpperCase() + EQUALS).startsWith(PATH + EQUALS)) {
					// Prepend the path of the SDK tools always...
					String prependValue = PATH + EQUALS + epocRoot.toOSString() + "epoc32\\tools" + VAR_SEPARATOR;
					prependValue += epocRoot.toOSString() + "epoc32\\gcc\\bin" + VAR_SEPARATOR;
					
					if (CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(projectTracker.getProject())) {
						// add the sbs bin directory to the path
						IPath sbsHome = SBSv2Utils.getSBSBinDirectory();
						if (sbsHome != null) {
							prependValue += sbsHome.toOSString() + VAR_SEPARATOR;
						}
					}
					
					prependValue += currEnvVar.substring(PATH.length() + 1);
					returnEnvArray[i] = prependValue;
				} else if ((currEnvVar.toUpperCase() + EQUALS).startsWith(EPOCROOT)) {
					// EPOCROOT can only work in replace mode. So always change this value
					// with the current EPOCROOT unless the user has specified that it be replaced.
					IEnvironmentVariable epocRootVar = getEnvVarFromConfiguration(EPOCROOT);
					if (epocRootVar != null && epocRootVar.getUse() == IEnvironmentVariable.ENV_VAR_USE_REPLACE) {
						// do nothing... it's already been replaced above...
					} 
					else {
						String epocRootStr = epocRoot.toOSString();
						if (epocRootStr.indexOf(":") == 1) {
							// only strip the drive for SBSv1
							if (!CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(projectTracker.getProject())) {
								epocRootStr = epocRootStr.substring(2);
							}
						}
						returnEnvArray[i] = EPOCROOT + EQUALS + epocRootStr;
					}	
				} 
				i++;
			}
		}
		
		// Insure that EPOCROOT really exists, if not we have to 
		// add this for all build platforms (configurations)
		if (System.getenv(EPOCROOT) == null) {
			String epocRootStr = epocRoot.toOSString();
			if (epocRootStr.indexOf(":") == 1) {
				// only strip the drive for SBSv1
				if (!CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(projectTracker.getProject())) {
					epocRootStr = epocRootStr.substring(2);
				}
			}
			
			returnEnvArray = addToArray(returnEnvArray, EPOCROOT + EQUALS + epocRootStr);
		}

		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(projectTracker.getProject());
		if (cpi != null) {
			
			if (!CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(projectTracker.getProject())) {
				// for managed projects, set the NO_DEPENDENCIES env variable to -nd to turn off dependency generation in makmake
				if (cpi.areMakefilesManaged()) {
					returnEnvArray = addToArray(returnEnvArray, NO_DEPENDENCIES + EQUALS + NO_DEPENDENCIES_VALUE);
				}
				
				// set the MAKE variable to the correct value when concurrent building is enabled
				if (cpi.isConcurrentBuildingEnabled()) {
					returnEnvArray = addToArray(returnEnvArray, "MAKE=make -j " + cpi.concurrentBuildJobs());
				}
			}

			// now check for any extensions
			List<IEnvironmentModifier> modifiers = CarbideBuilderPlugin.getEnvironmentModifierExtensions();
			if (modifiers.size() > 0) {

				Map<String, String> varsMap = stringArrayToMap(returnEnvArray);
				
				ICarbideBuildConfiguration buildConfig = cpi.getNamedConfiguration(context.getDisplayString());
				if (buildConfig != null) {
					for (IEnvironmentModifier modifier : modifiers) {
						varsMap = modifier.getModifiedEnvironment(buildConfig, varsMap);
					}
				}

				returnEnvArray = mapToStringArray(varsMap);
			}
		}
		
		// Fix up the notation in the environment (convert Win32-type variables to Unix-type)
		if (HostOS.IS_UNIX) {
			for (int i = 0; i < returnEnvArray.length; i++) {
				returnEnvArray[i] = PathUtils.convertPathListToUnix(returnEnvArray[i]);
			}
		}

		return returnEnvArray;
	}
	
	public String[] getModifiedEnvironmentVariables() {
		
		String[] fullSetResolvedVariables = getResolvedEnvironmentVariables();
		String[] modifiedVars = new String[0];
		for (String currVar : fullSetResolvedVariables) {
			String systemVar = System.getenv(currVar.substring(0, currVar.indexOf("=")));
			if (systemVar == null || systemVar.length() == 0) {
				// This was a var we added, so add it as modified
				if (!currVar.substring(0, currVar.indexOf("=")).equalsIgnoreCase("PROMPT")) {
					modifiedVars = addToArray(modifiedVars, currVar); // don't know why PROMPT won't get read
				}
			} else if (!systemVar.equals(currVar.substring(currVar.indexOf("=")+1))) {
				modifiedVars = addToArray(modifiedVars, currVar);
			}
		}
		
		// Fix up the notation in the environment (convert Win32-type variables to Unix-type)
		if (HostOS.IS_UNIX) {
			for (int i = 0; i < modifiedVars.length; i++) {
				modifiedVars[i] = PathUtils.convertPathListToUnix(modifiedVars[i]);
			}
		}
		
		return modifiedVars;
	}
	
	private static String[] addToArray(String[] array, String s)
	 {
	    String ans[] = new String[array.length + 1];
	    System.arraycopy(array, 0, ans, 0, array.length);
	    ans[ans.length - 1] = s;
	    return ans;
	 }
	
	public void setModifiedEnvVarsList(List<IEnvironmentVariable> inEnvVarsList) {
		envVarsList = inEnvVarsList;
	}
	
	public String[] getDefaultEnvVarsSettings(ICarbideProjectInfo carbideProject, ISymbianBuildContext context) {
		EnvironmentVarsInfo2 defaultEnvVarsInfo = new EnvironmentVarsInfo2(carbideProject.getProject(), context);
		return defaultEnvVarsInfo.getResolvedEnvironmentVariables();
	}
	
	public List<IEnvironmentVariable> getDefaultEnvVarsList(ICarbideProjectInfo carbideProject, ISymbianBuildContext context) {
		EnvironmentVarsInfo2 defaultEnvVarsInfo = new EnvironmentVarsInfo2(carbideProject.getProject(), context);
		return defaultEnvVarsInfo.getModifiedEnvVarsListFromSettings();
	}
	
	/**
	 * Get the default string value for the MWSYM2LIBRARYFILES variable.
	 * @return
	 */
	private String getMWLibraryFilesEnvString() {
		return X86_LINK_LIB_LIST;
	}
	
	/**
	 * Get the default string value for the MWSYM2LIBRARIES variable.
	 * @return
	 */
	private String getMWLibraryIncludesEnvString() {
		String nokiaX86BuildFolder = getNokiaX86BuildDir();
		String mwSym2LibPathsValue = nokiaX86BuildFolder + X86_WIN32SDK_FOLDER + VAR_SEPARATOR;
		return mwSym2LibPathsValue += nokiaX86BuildFolder + X86_WINLIBS_FOLDER;
	}
	
	/**
	 * Get the default string value for the MWCSYM2INCLUDES variable.
	 * @return
	 */
	private String getMWIncludesEnvString() {
		String nokiaX86BuildFolder = getNokiaX86BuildDir();
		String mwSym2IncludesVarValue = nokiaX86BuildFolder + MSL_COMMON_INCLUDE + VAR_SEPARATOR;
		mwSym2IncludesVarValue += nokiaX86BuildFolder + MSL_WIN32_INCLUDE + VAR_SEPARATOR;
		mwSym2IncludesVarValue += nokiaX86BuildFolder + MSL_C_MSL_X86 + VAR_SEPARATOR;
		mwSym2IncludesVarValue += nokiaX86BuildFolder + MSL_CPP_COMMON_INCLUDE + VAR_SEPARATOR;
		mwSym2IncludesVarValue += "+" + nokiaX86BuildFolder + MSL_EXTRAS_COMMON_INCLUDE + VAR_SEPARATOR;
		mwSym2IncludesVarValue += nokiaX86BuildFolder + MSL_EXTRAS_WIN32_INCLUDE + VAR_SEPARATOR;
		return mwSym2IncludesVarValue += nokiaX86BuildFolder + MSL_WIN32_SDK + VAR_SEPARATOR;
	}
	
	/**
	 * Get the location where the Nokia X86 Compiler/Linker/MSL is installed in the current environment
	 * @return
	 */
	private String getNokiaX86BuildDir() {
		return X86BuildPlugin.getX86BuildDirectoryPath().toOSString() + File.separator;
	}
	
	private Map<String, String> stringArrayToMap(String[] array) {
		Map<String, String> map = new HashMap<String, String>(array.length);
		for (String var : array) {
			int idx = var.indexOf('=');
			if (idx > 0)
				map.put(var.substring(0, idx), var.substring(idx+1));
			else if (var.length() > 0)
				map.put(var, "");
		}
		
		return map;
	}

	private String[] mapToStringArray(Map<String, String> map) {
		String[] array = new String[map.size()];
		int index = 0;
		
		for (String key : map.keySet()) {
			array[index++] = key + "=" + map.get(key);
		}
		
		return array;
	}
}
