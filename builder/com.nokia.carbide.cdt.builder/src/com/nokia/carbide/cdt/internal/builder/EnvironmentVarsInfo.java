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
import com.nokia.carbide.cdt.builder.project.*;
import com.nokia.carbide.cdt.internal.api.builder.CarbideConfigurationDataProvider;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.*;
import com.nokia.carbide.cdt.internal.builder.ui.BuilderPreferencePage;
import com.nokia.carbide.cdt.internal.builder.xml.CarbideBuildConfigurationLoader;
import com.nokia.carbide.cpp.internal.x86build.X86BuildPlugin;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

import org.eclipse.cdt.utils.spawner.EnvironmentReader;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

/**
 * @deprecated use {@link EnvironmentVarsInfo2} instead
 */
public class EnvironmentVarsInfo implements IEnvironmentVarsInfo {
	
	private static final String X86_COMP_LINK_FOLDER = "Symbian_Tools\\Command_Line_Tools";
	
	private static final String X86_WINLIBS_FOLDER = "Symbian_Support\\Runtime\\Runtime_x86\\Runtime_Win32\\Libs";
	private static final String X86_WIN32SDK_FOLDER = "Symbian_Support\\Win32-x86 Support\\Libraries\\Win32 SDK";

	private static final String X86_LINK_LIB_LIST = "MSL_All_MSE_Symbian_D.lib;gdi32.lib;user32.lib;kernel32.lib;";
	                                                 
	private static final String MWCSYM2INCLUDES = "MWCSYM2INCLUDES";
	private static final String MWSYM2LIBRARIES = "MWSYM2LIBRARIES";
	private static final String MWSYM2LIBRARYFILES = "MWSYM2LIBRARYFILES";
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
	
	EnvVarsType envVarsType;
	private IProject project;
	private final ISymbianBuildContext context;
	private List<IEnvironmentVariable> envVarsList = new ArrayList<IEnvironmentVariable>();
	
	public EnvVarsType getEnvVarsType() {
		return envVarsType;
	}
	
	public EnvironmentVarsInfo(IProject project, ISymbianBuildContext context, EnvVarsType envVarsType){
		this.project = project;
		this.context = context;
		if (envVarsType == null){
			this.envVarsType = CarbideBuildConfigFactory.eINSTANCE.createEnvVarsType();
		} else {
			this.envVarsType = envVarsType;
		}
		
		createEnvironmentVarsForContext();
	}
	
	EnvironmentVarsInfo(IProject project, ISymbianBuildContext context){
		this.project = project;
		this.context = context;
		this.envVarsType = CarbideBuildConfigFactory.eINSTANCE.createEnvVarsType();
		createEnvironmentVarsForContext();
	}
	
	public IProject getProject() {
		return project;
	}

	public ISymbianBuildContext getContext() {
		return context;
	}

	private void createEnvironmentVarsForContext(){
		if (context == null){
			return;
		}
		
		// Get the current settings
		EList savedVarsList = getEnvVarsType().getVar();
		for (Iterator i = savedVarsList.iterator(); i.hasNext();){
			VarType currVar = (VarType)i.next();
			UseType useType = currVar.getUse();
			int varUse = IEnvironmentVariable.ENV_VAR_USE_DEFAULT;
			if (useType.getValue() == UseType.APPEND){
				varUse = IEnvironmentVariable.ENV_VAR_USE_APPEND;
			} else if (useType.getValue() == UseType.PREPEND) {
				varUse = IEnvironmentVariable.ENV_VAR_USE_PREPEND;
			} else if (useType.getValue() == UseType.REPLACE) {
				varUse = IEnvironmentVariable.ENV_VAR_USE_REPLACE;
			} else if (useType.getValue() == UseType.UNDEFINE){
				varUse = IEnvironmentVariable.ENV_VAR_USE_UNDEFINE;
			}
			envVarsList.add(new EnvironmentVariable(currVar.getName(), currVar.getValue(), varUse));
		}
		
		if (envVarsList.size() > 0){
			return;
		}
	    
		// No vars are saved, create new ones.
		createDefaultEnvVars();
	}

	public List<IEnvironmentVariable> getModifiedEnvVarsListFromSettings() {
		return envVarsList;
	}
	
	
	public IEnvironmentVariable getEnvVarFromConfiguration(String varName){
		
		for (IEnvironmentVariable currVar : envVarsList){
			if (currVar.getName().toUpperCase().equals(varName.toUpperCase())){
				return currVar;
			}
		}
		
		return null;
	}

	public String[] getResolvedEnvironmentVariables() {
		ISymbianSDK sdk = null;
		IPath epocRoot = null;
		if (context != null){
			sdk = context.getSDK();
			if (sdk != null){
				epocRoot = new Path(sdk.getEPOCROOT());
			}
		}
		
		if (epocRoot == null){
			return new String[0];
		}
		
		String returnEnvArray[] = EnvironmentReader.getRawEnvVars(); // This the environment we will modify to send back
		
		// Iterate through the variables in the configuration settigns and make the new environement
		for (IEnvironmentVariable currEnvVar : envVarsList){

			boolean foundRawEnv = false;
			int i = 0;
			for (String rawVar : returnEnvArray){
				String tempVarName = currEnvVar.getName().toUpperCase() + EQUALS;
				if ((rawVar.toUpperCase() + EQUALS).startsWith(tempVarName)){
					if (currEnvVar.getUse() == IEnvironmentVariable.ENV_VAR_USE_PREPEND){
						String prependValue = currEnvVar.getName() + EQUALS + currEnvVar.getValue();
						if (!prependValue.endsWith(VAR_SEPARATOR)){
							prependValue += VAR_SEPARATOR;
						}
						prependValue += rawVar.substring(currEnvVar.getName().length() + 1);
						returnEnvArray[i] = prependValue;
					} else if (currEnvVar.getUse() == IEnvironmentVariable.ENV_VAR_USE_APPEND){
						returnEnvArray[i] = returnEnvArray[i] + VAR_SEPARATOR + currEnvVar.getName() + currEnvVar.getValue();
					} else if (currEnvVar.getUse() == IEnvironmentVariable.ENV_VAR_USE_REPLACE){
						returnEnvArray[i] = currEnvVar.getName() + EQUALS + currEnvVar.getValue();
					} else if (currEnvVar.getUse() == IEnvironmentVariable.ENV_VAR_USE_UNDEFINE){
						returnEnvArray[i] = currEnvVar.getName() + EQUALS + currEnvVar.getValue();
					} 
					foundRawEnv = true;
					break;
				}
								
				i++; // next raw var
			}
			
			if (foundRawEnv == false){
				// variable does not exist in system, add it as is.
				returnEnvArray = addToArray(returnEnvArray, currEnvVar.getName() + EQUALS + currEnvVar.getValue());
			}
		}
		
		// Now check for specific variables relating to the Nokia x86 environment
		// and update the environment that we just updated, if at all.
		if (context.getPlatformString().equals(ISymbianBuildContext.EMULATOR_PLATFORM) &&
			BuilderPreferencePage.useBuiltInX86Vars()){
			
			int i = 0;
			
			for (String currEnvVar : returnEnvArray){
				
				if ((currEnvVar.toUpperCase() + EQUALS).startsWith(EPOCROOT)){
					// EPOCROOT can only work in replace mode. So always change this value
					// with the current EPOCROOT unless the user has specified that it be replaced.
					IEnvironmentVariable epocRootVar = getEnvVarFromConfiguration(EPOCROOT);
					if (epocRootVar != null && epocRootVar.getUse() == IEnvironmentVariable.ENV_VAR_USE_REPLACE){
						// do nothing... it's already been replaced above...
					} 
					else {
						String epocRootStr = epocRoot.toOSString();
						if (epocRootStr.indexOf(":") == 1){
							epocRootStr = epocRootStr.substring(2);
						}
						returnEnvArray[i] = EPOCROOT + EQUALS + epocRootStr;
					}	
				} else if ((currEnvVar.toUpperCase() + EQUALS).startsWith(PATH + EQUALS)){
					// Prepend the path of the compiler always...
					String prependValue = PATH + EQUALS + getNokiaX86BuildDir() + X86_COMP_LINK_FOLDER + VAR_SEPARATOR;
					prependValue += epocRoot.toOSString() + "epoc32\\tools" + VAR_SEPARATOR;
					prependValue += epocRoot.toOSString() + "epoc32\\gcc\\bin" + VAR_SEPARATOR;
					prependValue += currEnvVar.substring(PATH.length() + 1);
					returnEnvArray[i] = prependValue;
				} else if ((currEnvVar.toUpperCase() + EQUALS).startsWith(MWCSYM2INCLUDES + EQUALS)){
						returnEnvArray[i] = MWCSYM2INCLUDES + EQUALS + getMWIncludesEnvString(); 
					//}
				} else if ((currEnvVar.toUpperCase() + EQUALS).startsWith(MWSYM2LIBRARIES + EQUALS)){
						returnEnvArray[i] = MWSYM2LIBRARIES + EQUALS + getMWLibraryIncludesEnvString();
					
				} else if ((currEnvVar.toUpperCase() + EQUALS).startsWith(MWSYM2LIBRARYFILES + EQUALS)){
						returnEnvArray[i] = MWSYM2LIBRARYFILES + EQUALS + getMWLibraryFilesEnvString();
				}
				
				i++;  // next variable index
				
			} // for
			
			// Double check to make sure that the MW* variables even exist
			// If not we'll need to add them as the installer doesn't
			if (System.getenv(MWCSYM2INCLUDES) == null){
				returnEnvArray = addToArray(returnEnvArray, MWCSYM2INCLUDES + EQUALS + getMWIncludesEnvString());
			}
			if (System.getenv(MWSYM2LIBRARIES) == null) {
				returnEnvArray = addToArray(returnEnvArray, MWSYM2LIBRARIES + EQUALS + getMWLibraryIncludesEnvString());
			}
			if (System.getenv(MWSYM2LIBRARYFILES) == null) {
				returnEnvArray = addToArray(returnEnvArray, MWSYM2LIBRARYFILES + EQUALS + getMWLibraryFilesEnvString());
			}
			
			
		}
		else { 
			// This isn't emulation and/or we're just using the default environment.
			// Set up EPOCROOT and PATH vars for SDK...
			int i = 0;
			for (String currEnvVar : returnEnvArray){
				if ((currEnvVar.toUpperCase() + EQUALS).startsWith(PATH + EQUALS)){
					// Prepend the path of the SDK tools always...
					String prependValue = PATH + EQUALS + epocRoot.toOSString() + "epoc32\\tools" + VAR_SEPARATOR;
					prependValue += epocRoot.toOSString() + "epoc32\\gcc\\bin" + VAR_SEPARATOR;
					prependValue += currEnvVar.substring(PATH.length() + 1);
					returnEnvArray[i] = prependValue;
				} else if ((currEnvVar.toUpperCase() + EQUALS).startsWith(EPOCROOT)){
					// EPOCROOT can only work in replace mode. So always change this value
					// with the current EPOCROOT unless the user has specified that it be replaced.
					IEnvironmentVariable epocRootVar = getEnvVarFromConfiguration(EPOCROOT);
					if (epocRootVar != null && epocRootVar.getUse() == IEnvironmentVariable.ENV_VAR_USE_REPLACE){
						// do nothing... it's already been replaced above...
					} 
					else {
						String epocRootStr = epocRoot.toOSString();
						if (epocRootStr.indexOf(":") == 1){
							epocRootStr = epocRootStr.substring(2);
						}
						returnEnvArray[i] = EPOCROOT + EQUALS + epocRootStr;
					}	
				} 
				i++;
			}
		}
		
		// Insure that EPOCROOT really exists, if not we have to 
		// add this for all build platforms (configurations)
		if (System.getenv(EPOCROOT) == null){
			String epocRootStr = epocRoot.toOSString();
			if (epocRootStr.indexOf(":") == 1){
				epocRootStr = epocRootStr.substring(2);
			}
			
			returnEnvArray = addToArray(returnEnvArray, EPOCROOT + EQUALS + epocRootStr);
		}

		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi != null) {
			// for managed projects, set the NO_DEPENDENCIES env variable to -nd to turn off dependency generation in makmake
			if (cpi.areMakefilesManaged()) {
				// we have to use something other than NO_DEPENDENCIES in EKA1 since some SDK's like S60_2nd_FP3
				// have part of the no dependency support but not all, which would cause errors.
				if (sdk.isEKA1()) {
					returnEnvArray = addToArray(returnEnvArray, CARBIDE_NO_DEPENDENCIES + EQUALS + NO_DEPENDENCIES_VALUE);
				} else {
					returnEnvArray = addToArray(returnEnvArray, NO_DEPENDENCIES + EQUALS + NO_DEPENDENCIES_VALUE);
				}
			}
			
			// set the MAKE variable to the correct value when concurrent building is enabled
			if (cpi.isConcurrentBuildingEnabled()) {
				returnEnvArray = addToArray(returnEnvArray, "MAKE=make -j " + cpi.concurrentBuildJobs());
			}
		}

		return returnEnvArray;
	}
	
	public String[] getModifiedEnvironmentVariables() {
		
		String[] fullSetResolvedVariables = getResolvedEnvironmentVariables();
		String[] modifiedVars = new String[0];
		for (String currVar : fullSetResolvedVariables) {
			String systemVar = System.getenv(currVar.substring(0, currVar.indexOf("=")));
			if (systemVar == null || systemVar.length() == 0){
				// This was a var we added, so add it as modified
				if (!currVar.substring(0, currVar.indexOf("=")).equalsIgnoreCase("PROMPT")){
					modifiedVars = addToArray(modifiedVars, currVar); // don't know why PROMPT won't get read
				}
			} else if (!systemVar.equals(currVar.substring(currVar.indexOf("=")+1))) {
				modifiedVars = addToArray(modifiedVars, currVar);
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
		envVarsList.clear();
		this.envVarsList = inEnvVarsList; // update the object's list in memory
		ConfigurationType configType = null;

		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi != null) {
			ICarbideBuildConfiguration config = cpi.getNamedConfiguration(context.getDisplayString());
			CarbideBuilderConfigInfoType buildConfigType = null;
			try {
				buildConfigType = CarbideBuildConfigurationLoader.loadBuildConfigurations(CarbideConfigurationDataProvider.getConfigurationSettingsFile(project).toURL());
			} catch (IOException e){
				e.printStackTrace(); // settings file has disappeared.
			} catch (URISyntaxException e){
				e.printStackTrace();
			}
			
			// Update/Create a new configuration
			// First check to see if the configuration already exists, if so update
			// otherwise create a new one.
			boolean configExists = false;
			for (Iterator i = buildConfigType.getConfiguration().iterator(); i.hasNext();) {
				ConfigurationType currConfig = (ConfigurationType)i.next();
				 if (currConfig.getName() != null && currConfig.getName().equals(config.getDisplayString())){
					 // Configuration exists, update changed data...
					 configType = currConfig;
					 currConfig.setName(config.getDisplayString());
					 configExists = true;
				 }
			}
			envVarsType = CarbideBuildConfigFactory.eINSTANCE.createEnvVarsType();
			if (configExists != false){
				// Config exists... update the XML interface to prepare for writing to disk
				for (IEnvironmentVariable currEnvVar : envVarsList){
					// update the interface to the XML.
					
					
					VarType newVarType = CarbideBuildConfigFactory.eINSTANCE.createVarType();
					
					newVarType.setName(currEnvVar.getName());
					switch (currEnvVar.getUse()){
						case IEnvironmentVariable.ENV_VAR_USE_PREPEND:
						{
							newVarType.setUse(UseType.PREPEND_LITERAL);
							break;
						}
						case IEnvironmentVariable.ENV_VAR_USE_APPEND:
						{
							newVarType.setUse(UseType.APPEND_LITERAL);
							break;
						}
						case IEnvironmentVariable.ENV_VAR_USE_REPLACE:
						{
							newVarType.setUse(UseType.REPLACE_LITERAL);
							break;
						}
						case IEnvironmentVariable.ENV_VAR_USE_UNDEFINE:
						{
							newVarType.setUse(UseType.UNDEFINE_LITERAL);
							break;
						} 
					}
					
					newVarType.setValue(currEnvVar.getValue());
					EList envVarList = envVarsType.getVar();
					envVarList.add(newVarType);
				}
				
				// this list is now rebuilt, set the XML interface
				configType.setEnvVars(envVarsType); 
			}
		}
	}
	
	/**
	 * Fill out the 'envVarsList' with the variables that will be modified from default
	 *
	 */
	private void createDefaultEnvVars(){
		envVarsList.clear();  // create a new list
	}
	
	public String[] getDefaultEnvVarsSettings(ICarbideProjectInfo carbideProject, ISymbianBuildContext context){
		EnvironmentVarsInfo defaultEnvVarsInfo = new EnvironmentVarsInfo(carbideProject.getProject(), context);
		return defaultEnvVarsInfo.getResolvedEnvironmentVariables();
	}
	
	public List<IEnvironmentVariable> getDefaultEnvVarsList(ICarbideProjectInfo carbideProject, ISymbianBuildContext context){
		EnvironmentVarsInfo defaultEnvVarsInfo = new EnvironmentVarsInfo(carbideProject.getProject(), context);
		return defaultEnvVarsInfo.getModifiedEnvVarsListFromSettings();
	}
	
	/**
	 * Get the default string value for the MWSYM2LIBRARYFILES variable.
	 * @return
	 */
	private String getMWLibraryFilesEnvString(){
		return X86_LINK_LIB_LIST;
	}
	
	/**
	 * Get the default string value for the MWSYM2LIBRARIES variable.
	 * @return
	 */
	private String getMWLibraryIncludesEnvString(){
		String nokiaX86BuildFolder = getNokiaX86BuildDir();
		String mwSym2LibPathsValue = nokiaX86BuildFolder + X86_WIN32SDK_FOLDER + VAR_SEPARATOR;
		return mwSym2LibPathsValue += nokiaX86BuildFolder + X86_WINLIBS_FOLDER;
	}
	
	/**
	 * Get the default string value for the MWCSYM2INCLUDES variable.
	 * @return
	 */
	private String getMWIncludesEnvString(){
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
	private String getNokiaX86BuildDir(){
		return X86BuildPlugin.getX86BuildDirectoryPath().toOSString() + File.separator;
	}
	

}
