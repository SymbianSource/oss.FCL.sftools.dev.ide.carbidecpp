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

package com.nokia.carbide.cpp.internal.codescanner.config;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.internal.codescanner.Messages;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.*;
import com.nokia.carbide.cpp.internal.codescanner.xml.CSConfigXMLLoader;

/**
 * A class for handling CodeScanner configuration settings.
 *
 */
public class CSConfigSettings {

	private CodescannerConfigType csConfig;

	/**
	 * The constructor.
	 */
	public CSConfigSettings() {
		csConfig = null;
	}
	
	/**
	 * Create the default CodeScanner configuration settings.
	 */
	public void loadDefaultConfig() {
		CodescannerConfigType config = CSConfigFactory.eINSTANCE.createCodescannerConfigType();
		config.setArguments(createDefaultArguments());
		config.setCategories(createDefaultCategories());
		config.setCustomrules(createDefaultCustomRules());
		config.setScripts(createDefaultScripts());
		config.setSeverities(createDefaultSeverities());
		config.setSources(createDefaultSources());
		csConfig = config;
	}

	/**
	 * Load CodeScanner configuration settings from a file.
	 * @param configFile - file containing CodeScanner configuration settings.
	 * @return true on success
	 */
	public boolean loadConfig(File configFile) {
		if (configFile == null)
			return false;
		boolean success = true;
		try {
			this.csConfig = CSConfigXMLLoader.loadCSConfig(configFile.toURL());	
			success = (this.csConfig != null);
		}
		catch (URISyntaxException eURI){
			eURI.printStackTrace();
			loadConfigError(configFile.getName(), eURI.getMessage());
			success = false;
		}
		catch (IOException eIO){
			eIO.printStackTrace();
			loadConfigError(configFile.getName(), eIO.getMessage());
			success = false;
		}
		return success;
	}

	/**
	 * Save CodeScanner configuration settings to a file.
	 * @param configFile - file to write CodeScanner configuration settings.
	 * @return true on success
	 */
	public boolean saveConfig(File configFile) {
		if (configFile == null)
			return false;
		boolean success = true;
		try {
			if (this.csConfig != null) {
				configFile.createNewFile();
				success = CSConfigXMLLoader.writeCSConfig(this.csConfig, configFile.toURL());
			}
			else
				success = false;
		}
		catch (URISyntaxException eURI){
			eURI.printStackTrace();
			saveConfigError(configFile.getName(), eURI.getMessage());
			success = false;
		}
		catch (IOException eIO){
			eIO.printStackTrace();
			saveConfigError(configFile.getName(), eIO.getMessage());
			success = false;
		}
		return success;
	}

	/**
	 * Retrieve entire CodeScanner configuration settings
	 * @return CodeScanner configuration settings
	 */
	public CodescannerConfigType getConfig() {
		return this.csConfig;
	}

	/**
	 * Set entire CodeScanner configuration settings
	 * @param config - new configuration settings
	 */
	public void setConfig(CodescannerConfigType config) {
		this.csConfig = config;
	}

	/**
	 * Retrieve CodeScanner arguments
	 * @return CodeScanner arguments
	 */
	public ArgumentsType getArguments() {
		return this.csConfig.getArguments();
	}

	/**
	 * Set CodeScanner arguments
	 * @param arguments - new arguments
	 */
	public void setArguments(ArgumentsType arguments) {
		this.csConfig.setArguments(arguments);
	}

	/**
	 * Retrieve a CodeScanner argument.
	 * @param argument - argument element containing the attribute
	 * @return argument element
	 */
	public Object getArgument(CSArgument argument) {
		switch (argument) {
			case argument_input:
				return this.csConfig.getArguments().getInput();
			case argument_lxr:
				return this.csConfig.getArguments().getLxr();
			case argument_lxrversion:
				return this.csConfig.getArguments().getLxrversion();
			case argument_outputformat:
				return this.csConfig.getArguments().getOutputformat();
			case argument_timestampedoutput:
				return this.csConfig.getArguments().getTimestampedoutput();
			case argument_unknown:
			default:
				return null;
		}
	}

	/**
	 * Set values of the "input" arguments
	 * @param inputPaths - new values
	 */
	public void setInputArguments(List<String> inputPaths) {
		EList<String> inputArgumentList = this.csConfig.getArguments().getInput();
		inputArgumentList.clear();
		if (inputPaths != null) {
			for (String inputPath : inputPaths) {
				inputArgumentList.add(inputPath);
			}
		}
	}

	/**
	 * Set value of the "lxr" argument
	 * @param value - new value
	 */
	public void setLxrArgument(String value) {
		this.csConfig.getArguments().setLxr(value);
	}

	/**
	 * Set value of the "lxrversion" argument
	 * @param value - new value
	 */
	public void setLxrVersionArgument(String value) {
		this.csConfig.getArguments().setLxrversion(value);
	}

	/**
	 * Set value of the "outputformat" argument
	 * @param value - new value
	 */
	public void setOutputFormatArgument(String value) {
		this.csConfig.getArguments().setOutputformat(value);
	}

	/**
	 * Set value of the "timestampedoutput" argument
	 * @param value - new value
	 */
	public void setTimeStampedOutputArgument(String value) {
		this.csConfig.getArguments().setTimestampedoutput(value);
	}

	/**
	 * Retrieve the custom rules
	 * @return custom rules
	 */
	public CustomrulesType getCustomRules() {
		return this.csConfig.getCustomrules();
	}

	/**
	 * Set the custom rules
	 * @param customRules - new set of custom rules
	 */
	public void SetCustomRules(CustomrulesType customRules) {
		this.csConfig.setCustomrules(customRules);
	}

	/**
	 * Retrieve CodeScanner script category settings
	 * @return CodeScanner script category settings
	 */
	public CategoriesType getCategories() {
		return this.csConfig.getCategories();
	}

	/**
	 * Set CodeScanner script category settings
	 * @param categories - new script category settings
	 */
	public void setCategories(CategoriesType categories) {
		this.csConfig.setCategories(categories);
	}

	/**
	 * Retrieve CodeScanner script settings
	 * @return CodeScanner script settings
	 */
	public ScriptsType getScripts() {
		return this.csConfig.getScripts();
	}

	/**
	 * Set CodeScanner script settings
	 * @param scripts - new script settings
	 */
	public void setScripts(ScriptsType scripts) {
		this.csConfig.setScripts(scripts);
	}

	/**
	 * Retrieve CodeScanner script severity settings
	 * @return CodeScanner script severity settings
	 */
	public SeveritiesType getSeverities() {
		return this.csConfig.getSeverities();
	}

	/**
	 * Set CodeScanner script severity settings
	 * @param severities - new script severity settings
	 */
	public void setSeverities(SeveritiesType severities) {
		this.csConfig.setSeverities(severities);
	}

	/**
	 * Retrieve CodeScanner source filter settings
	 * @return CodeScanner source filter settings
	 */
	public SourcesType getSourceFilters() {
		return this.csConfig.getSources();
	}

	/**
	 * Set CodeScanner source filter settings
	 * @param sourcefilters - new source filter settings
	 */
	public void setSourceFilters(SourcesType sourcefilters) {
		this.csConfig.setSources(sourcefilters);
	}

	/**
	 * Retrieve a CodeScanner script element
	 * @param script - script element containing the attribute
	 * @return script element
	 */
	public Object getScript(CSScript script) {
		switch (script) {
			case script_accessArrayElementWithoutCheck:
				return this.csConfig.getScripts().getAccessArrayElementWithoutCheck();
			case script_accessArrayElementWithoutCheck2:
				return this.csConfig.getScripts().getAccessArrayElementWithoutCheck2();
			case script_activestart:
				return this.csConfig.getScripts().getActivestart();
			case script_activestop:
				return this.csConfig.getScripts().getActivestop();
			case script_arraypassing:
				return this.csConfig.getScripts().getArraypassing();
			case script_arrayptrcleanup:
				return this.csConfig.getScripts().getArrayptrcleanup();
			case script_assertdebuginvariant:
				return this.csConfig.getScripts().getAssertdebuginvariant();
			case script_baddefines:
				return this.csConfig.getScripts().getBaddefines();
			case script_baseconstruct:
				return this.csConfig.getScripts().getBaseconstruct();
			case script_callActiveObjectWithoutCheckingOrStopping:
				return this.csConfig.getScripts().getCallActiveObjectWithoutCheckingOrStopping();
			case script_changenotification:
				return this.csConfig.getScripts().getChangenotification();
			case script_cleanup:
				return this.csConfig.getScripts().getCleanup();
			case script_commentcode:
				return this.csConfig.getScripts().getCommentcode();
			case script_connect:
				return this.csConfig.getScripts().getConnect();
			case script_ConnectAndDontCloseMemberVariable:
				return this.csConfig.getScripts().getConnectAndDontCloseMemberVariable();
			case script_constnames:
				return this.csConfig.getScripts().getConstnames();
			case script_consttdescptr:
				return this.csConfig.getScripts().getConsttdescptr();
			case script_controlornull:
				return this.csConfig.getScripts().getControlornull();
			case script_crepository:
				return this.csConfig.getScripts().getCrepository();
			case script_ctltargettype:
				return this.csConfig.getScripts().getCtltargettype();
			case script_customizableicons:
				return this.csConfig.getScripts().getCustomizableicons();
			case script_debugrom:
				return this.csConfig.getScripts().getDebugrom();
			case script_declarename:
				return this.csConfig.getScripts().getDeclarename();
			case script_deleteMemberVariable:
				return this.csConfig.getScripts().getDeleteMemberVariable();
			case script_destructor:
				return this.csConfig.getScripts().getDestructor();
			case script_doubleSemiColon:
				return this.csConfig.getScripts().getDoubleSemiColon();
			case script_driveletters:
				return this.csConfig.getScripts().getDriveletters();
			case script_eikbuttons:
				return this.csConfig.getScripts().getEikbuttons();
			case script_eikonenvstatic:
				return this.csConfig.getScripts().getEikonenvstatic();
			case script_enummembers:
				return this.csConfig.getScripts().getEnummembers();
			case script_enumnames:
				return this.csConfig.getScripts().getEnumnames();
			case script_exportinline:
				return this.csConfig.getScripts().getExportinline();
			case script_exportpurevirtual:
				return this.csConfig.getScripts().getExportpurevirtual();
			case script_flags:
				return this.csConfig.getScripts().getFlags();
			case script_foff:
				return this.csConfig.getScripts().getFoff();
			case script_forbiddenwords:
				return this.csConfig.getScripts().getForbiddenwords();
			case script_forgottoputptroncleanupstack:
				return this.csConfig.getScripts().getForgottoputptroncleanupstack();
			case script_friend:
				return this.csConfig.getScripts().getFriend();
			case script_goto:
				return this.csConfig.getScripts().getGoto();
			case script_ifassignments:
				return this.csConfig.getScripts().getIfassignments();
			case script_ifpreprocessor:
				return this.csConfig.getScripts().getIfpreprocessor();
			case script_inheritanceorder:
				return this.csConfig.getScripts().getInheritanceorder();
			case script_intleaves:
				return this.csConfig.getScripts().getIntleaves();
			case script_jmp:
				return this.csConfig.getScripts().getJmp();
			case script_leave:
				return this.csConfig.getScripts().getLeave();
			case script_LeaveNoError:
				return this.csConfig.getScripts().getLeaveNoError();
			case script_leavingoperators:
				return this.csConfig.getScripts().getLeavingoperators();
			case script_LFunctionCantLeave:
				return this.csConfig.getScripts().getLFunctionCantLeave();
			case script_longlines: 
				return this.csConfig.getScripts().getLonglines();
			case script_magicnumbers:
				return this.csConfig.getScripts().getMagicnumbers();
			case script_mclassdestructor:
				return this.csConfig.getScripts().getMclassdestructor();
			case script_memberlc:
				return this.csConfig.getScripts().getMemberlc();
			case script_membervariablecallld:
				return this.csConfig.getScripts().getMembervariablecallld();
			case script_missingcancel:
				return this.csConfig.getScripts().getMissingcancel();
			case script_missingcclass:
				return this.csConfig.getScripts().getMissingcclass();
			case script_mmpsourcepath:
				return this.csConfig.getScripts().getMmpsourcepath();
			case script_multilangrsc:
				return this.csConfig.getScripts().getMultilangrsc();
			case script_multipledeclarations:
				return this.csConfig.getScripts().getMultipledeclarations();
			case script_multipleinheritance:
				return this.csConfig.getScripts().getMultipleinheritance();
			case script_mydocs:
				return this.csConfig.getScripts().getMydocs();
			case script_namespace:
				return this.csConfig.getScripts().getNamespace();
			case script_newlreferences:
				return this.csConfig.getScripts().getNewlreferences();
			case script_noleavetrap:
				return this.csConfig.getScripts().getNoleavetrap();
			case script_nonconsthbufc:
				return this.csConfig.getScripts().getNonconsthbufc();
			case script_nonconsttdesc:
				return this.csConfig.getScripts().getNonconsttdesc();
			case script_nonleavenew:
				return this.csConfig.getScripts().getNonleavenew();
			case script_nonunicodeskins:
				return this.csConfig.getScripts().getNonunicodeskins();
			case script_null:
				return this.csConfig.getScripts().getNull();
			case script_open:
				return this.csConfig.getScripts().getOpen();
			case script_pointertoarrays:
				return this.csConfig.getScripts().getPointertoarrays();
			case script_pragmadisable:
				return this.csConfig.getScripts().getPragmadisable();
			case script_pragmamessage:
				return this.csConfig.getScripts().getPragmamessage();
			case script_pragmaother:
				return this.csConfig.getScripts().getPragmaother();
			case script_privateinheritance:
				return this.csConfig.getScripts().getPrivateinheritance();
			case script_pushaddrvar:
				return this.csConfig.getScripts().getPushaddrvar();
			case script_pushmember:
				return this.csConfig.getScripts().getPushmember();
			case script_readresource:
				return this.csConfig.getScripts().getReadresource();
			case script_resourcenotoncleanupstack:
				return this.csConfig.getScripts().getResourcenotoncleanupstack();
			case script_resourcesonheap:
				return this.csConfig.getScripts().getResourcesonheap();
			case script_returndescriptoroutofscope:
				return this.csConfig.getScripts().getReturndescriptoroutofscope();
			case script_rfs:
				return this.csConfig.getScripts().getRfs();
			case script_rssnames:
				return this.csConfig.getScripts().getRssnames();
			case script_stringliterals:
				return this.csConfig.getScripts().getStringliterals();
			case script_stringsinresourcefiles:
				return this.csConfig.getScripts().getStringsinresourcefiles();
			case script_struct:
				return this.csConfig.getScripts().getStruct();
			case script_tcclasses:
				return this.csConfig.getScripts().getTcclasses();
			case script_tclassdestructor:
				return this.csConfig.getScripts().getTclassdestructor();
			case script_todocomments:
				return this.csConfig.getScripts().getTodocomments();
			case script_trapcleanup:
				return this.csConfig.getScripts().getTrapcleanup();
			case script_trapeleave:
				return this.csConfig.getScripts().getTrapeleave();
			case script_traprunl:
				return this.csConfig.getScripts().getTraprunl();
			case script_trspassing:
				return this.csConfig.getScripts().getTrspassing();
			case script_uids:
				return this.csConfig.getScripts().getUids();
			case script_uncompressedaif:
				return this.csConfig.getScripts().getUncompressedaif();
			case script_uncompressedbmp:
				return this.csConfig.getScripts().getUncompressedaif();
			case script_unicodesource:
				return this.csConfig.getScripts(). getUnicodesource();
			case script_userafter:
				return this.csConfig.getScripts().getUserafter();
			case script_userfree:
				return this.csConfig.getScripts().getUserfree();
			case script_userWaitForRequest:
				return this.csConfig.getScripts().getUserWaitForRequest();
			case script_variablenames:
				return this.csConfig.getScripts().getVariablenames();
			case script_voidparameter:
				return this.csConfig.getScripts().getVoidparameter();
			case script_worryingcomments:
				return this.csConfig.getScripts().getWorryingcomments();
			case script_unknown:
			default:
				return null;
		}
	}

	/**
	 * Retrieve the "enabled" attribute of a CodeScanner script element
	 * @param script - script element containing the attribute
	 * @return attribute value
	 */
	public boolean getScriptEnabled(CSScript script) {
		switch (script) {
			case script_accessArrayElementWithoutCheck:
				return this.csConfig.getScripts().getAccessArrayElementWithoutCheck().isEnable();
			case script_accessArrayElementWithoutCheck2:
				return this.csConfig.getScripts().getAccessArrayElementWithoutCheck2().isEnable();
			case script_activestart:
				return this.csConfig.getScripts().getActivestart().isEnable();
			case script_activestop:
				return this.csConfig.getScripts().getActivestop().isEnable();
			case script_arraypassing:
				return this.csConfig.getScripts().getArraypassing().isEnable();
			case script_arrayptrcleanup:
				return this.csConfig.getScripts().getArrayptrcleanup().isEnable();
			case script_assertdebuginvariant:
				return this.csConfig.getScripts().getAssertdebuginvariant().isEnable();
			case script_baddefines:
				return this.csConfig.getScripts().getBaddefines().isEnable();
			case script_baseconstruct:
				return this.csConfig.getScripts().getBaseconstruct().isEnable();
			case script_callActiveObjectWithoutCheckingOrStopping:
				return this.csConfig.getScripts().getCallActiveObjectWithoutCheckingOrStopping().isEnable();
			case script_changenotification:
				return this.csConfig.getScripts().getChangenotification().isEnable();
			case script_cleanup:
				return this.csConfig.getScripts().getCleanup().isEnable();
			case script_commentcode:
				return this.csConfig.getScripts().getCommentcode().isEnable();
			case script_connect:
				return this.csConfig.getScripts().getConnect().isEnable();
			case script_ConnectAndDontCloseMemberVariable:
				return this.csConfig.getScripts().getConnectAndDontCloseMemberVariable().isEnable();
			case script_constnames:
				return this.csConfig.getScripts().getConstnames().isEnable();
			case script_consttdescptr:
				return this.csConfig.getScripts().getConsttdescptr().isEnable();
			case script_controlornull:
				return this.csConfig.getScripts().getControlornull().isEnable();
			case script_crepository:
				return this.csConfig.getScripts().getCrepository().isEnable();
			case script_ctltargettype:
				return this.csConfig.getScripts().getCtltargettype().isEnable();
			case script_customizableicons:
				return this.csConfig.getScripts().getCustomizableicons().isEnable();
			case script_debugrom:
				return this.csConfig.getScripts().getDebugrom().isEnable();
			case script_declarename:
				return this.csConfig.getScripts().getDeclarename().isEnable();
			case script_deleteMemberVariable:
				return this.csConfig.getScripts().getDeleteMemberVariable().isEnable();
			case script_destructor:
				return this.csConfig.getScripts().getDestructor().isEnable();
			case script_doubleSemiColon:
				return this.csConfig.getScripts().getDoubleSemiColon().isEnable();
			case script_driveletters:
				return this.csConfig.getScripts().getDriveletters().isEnable();
			case script_eikbuttons:
				return this.csConfig.getScripts().getEikbuttons().isEnable();
			case script_eikonenvstatic:
				return this.csConfig.getScripts().getEikonenvstatic().isEnable();
			case script_enummembers:
				return this.csConfig.getScripts().getEnummembers().isEnable();
			case script_enumnames:
				return this.csConfig.getScripts().getEnumnames().isEnable();
			case script_exportinline:
				return this.csConfig.getScripts().getExportinline().isEnable();
			case script_exportpurevirtual:
				return this.csConfig.getScripts().getExportpurevirtual().isEnable();
			case script_flags:
				return this.csConfig.getScripts().getFlags().isEnable();
			case script_foff:
				return this.csConfig.getScripts().getFoff().isEnable();
			case script_forbiddenwords:
				return this.csConfig.getScripts().getForbiddenwords().isEnable();
			case script_forgottoputptroncleanupstack:
				return this.csConfig.getScripts().getForgottoputptroncleanupstack().isEnable();
			case script_friend:
				return this.csConfig.getScripts().getFriend().isEnable();
			case script_goto:
				return this.csConfig.getScripts().getGoto().isEnable();
			case script_ifassignments:
				return this.csConfig.getScripts().getIfassignments().isEnable();
			case script_ifpreprocessor:
				return this.csConfig.getScripts().getIfpreprocessor().isEnable();
			case script_inheritanceorder:
				return this.csConfig.getScripts().getInheritanceorder().isEnable();
			case script_intleaves:
				return this.csConfig.getScripts().getIntleaves().isEnable();
			case script_jmp:
				return this.csConfig.getScripts().getJmp().isEnable();
			case script_leave:
				return this.csConfig.getScripts().getLeave().isEnable();
			case script_LeaveNoError:
				return this.csConfig.getScripts().getLeaveNoError().isEnable();
			case script_leavingoperators:
				return this.csConfig.getScripts().getLeavingoperators().isEnable();
			case script_LFunctionCantLeave:
				return this.csConfig.getScripts().getLFunctionCantLeave().isEnable();
			case script_longlines: 
				return this.csConfig.getScripts().getLonglines().isEnable();
			case script_magicnumbers:
				return this.csConfig.getScripts().getMagicnumbers().isEnable();
			case script_mclassdestructor:
				return this.csConfig.getScripts().getMclassdestructor().isEnable();
			case script_memberlc:
				return this.csConfig.getScripts().getMemberlc().isEnable();
			case script_membervariablecallld:
				return this.csConfig.getScripts().getMembervariablecallld().isEnable();
			case script_missingcancel:
				return this.csConfig.getScripts().getMissingcancel().isEnable();
			case script_missingcclass:
				return this.csConfig.getScripts().getMissingcclass().isEnable();
			case script_mmpsourcepath:
				return this.csConfig.getScripts().getMmpsourcepath().isEnable();
			case script_multilangrsc:
				return this.csConfig.getScripts().getMultilangrsc().isEnable();
			case script_multipledeclarations:
				return this.csConfig.getScripts().getMultipledeclarations().isEnable();
			case script_multipleinheritance:
				return this.csConfig.getScripts().getMultipleinheritance().isEnable();
			case script_mydocs:
				return this.csConfig.getScripts().getMydocs().isEnable();
			case script_namespace:
				return this.csConfig.getScripts().getNamespace().isEnable();
			case script_newlreferences:
				return this.csConfig.getScripts().getNewlreferences().isEnable();
			case script_noleavetrap:
				return this.csConfig.getScripts().getNoleavetrap().isEnable();
			case script_nonconsthbufc:
				return this.csConfig.getScripts().getNonconsthbufc().isEnable();
			case script_nonconsttdesc:
				return this.csConfig.getScripts().getNonconsttdesc().isEnable();
			case script_nonleavenew:
				return this.csConfig.getScripts().getNonleavenew().isEnable();
			case script_nonunicodeskins:
				return this.csConfig.getScripts().getNonunicodeskins().isEnable();
			case script_null:
				return this.csConfig.getScripts().getNull().isEnable();
			case script_open:
				return this.csConfig.getScripts().getOpen().isEnable();
			case script_pointertoarrays:
				return this.csConfig.getScripts().getPointertoarrays().isEnable();
			case script_pragmadisable:
				return this.csConfig.getScripts().getPragmadisable().isEnable();
			case script_pragmamessage:
				return this.csConfig.getScripts().getPragmamessage().isEnable();
			case script_pragmaother:
				return this.csConfig.getScripts().getPragmaother().isEnable();
			case script_privateinheritance:
				return this.csConfig.getScripts().getPrivateinheritance().isEnable();
			case script_pushaddrvar:
				return this.csConfig.getScripts().getPushaddrvar().isEnable();
			case script_pushmember:
				return this.csConfig.getScripts().getPushmember().isEnable();
			case script_readresource:
				return this.csConfig.getScripts().getReadresource().isEnable();
			case script_resourcenotoncleanupstack:
				return this.csConfig.getScripts().getResourcenotoncleanupstack().isEnable();
			case script_resourcesonheap:
				return this.csConfig.getScripts().getResourcesonheap().isEnable();
			case script_returndescriptoroutofscope:
				return this.csConfig.getScripts().getReturndescriptoroutofscope().isEnable();
			case script_rfs:
				return this.csConfig.getScripts().getRfs().isEnable();
			case script_rssnames:
				return this.csConfig.getScripts().getRssnames().isEnable();
			case script_stringliterals:
				return this.csConfig.getScripts().getStringliterals().isEnable();
			case script_stringsinresourcefiles:
				return this.csConfig.getScripts().getStringsinresourcefiles().isEnable();
			case script_struct:
				return this.csConfig.getScripts().getStruct().isEnable();
			case script_tcclasses:
				return this.csConfig.getScripts().getTcclasses().isEnable();
			case script_tclassdestructor:
				return this.csConfig.getScripts().getTclassdestructor().isEnable();
			case script_todocomments:
				return this.csConfig.getScripts().getTodocomments().isEnable();
			case script_trapcleanup:
				return this.csConfig.getScripts().getTrapcleanup().isEnable();
			case script_trapeleave:
				return this.csConfig.getScripts().getTrapeleave().isEnable();
			case script_traprunl:
				return this.csConfig.getScripts().getTraprunl().isEnable();
			case script_trspassing:
				return this.csConfig.getScripts().getTrspassing().isEnable();
			case script_uids:
				return this.csConfig.getScripts().getUids().isEnable();
			case script_uncompressedaif:
				return this.csConfig.getScripts().getUncompressedaif().isEnable();
			case script_uncompressedbmp:
				return this.csConfig.getScripts().getUncompressedaif().isEnable();
			case script_unicodesource:
				return this.csConfig.getScripts(). getUnicodesource().isEnable();
			case script_userafter:
				return this.csConfig.getScripts().getUserafter().isEnable();
			case script_userfree:
				return this.csConfig.getScripts().getUserfree().isEnable();
			case script_userWaitForRequest:
				return this.csConfig.getScripts().getUserWaitForRequest().isEnable();
			case script_variablenames:
				return this.csConfig.getScripts().getVariablenames().isEnable();
			case script_voidparameter:
				return this.csConfig.getScripts().getVoidparameter().isEnable();
			case script_worryingcomments:
				return this.csConfig.getScripts().getWorryingcomments().isEnable();
			case script_unknown:
			default:
				return true;
		}
	}

	/**
	 * Set the "enabled" attribute of a CodeScanner script element
	 * @param script - script element containing the attribute
	 * @param value - new attribute value
	 */
	public void setScriptEnabled(CSScript script, boolean value) {
		switch (script) {
			case script_accessArrayElementWithoutCheck:
				this.csConfig.getScripts().getAccessArrayElementWithoutCheck().setEnable(value);
				break;
			case script_accessArrayElementWithoutCheck2:
				this.csConfig.getScripts().getAccessArrayElementWithoutCheck2().setEnable(value);
				break;
			case script_activestart:
				this.csConfig.getScripts().getActivestart().setEnable(value);
				break;
			case script_activestop:
				this.csConfig.getScripts().getActivestop().setEnable(value);
				break;
			case script_arraypassing:
				this.csConfig.getScripts().getArraypassing().setEnable(value);
				break;
			case script_arrayptrcleanup:
				this.csConfig.getScripts().getArrayptrcleanup().setEnable(value);
				break;
			case script_assertdebuginvariant:
				this.csConfig.getScripts().getAssertdebuginvariant().setEnable(value);
				break;
			case script_baddefines:
				this.csConfig.getScripts().getBaddefines().setEnable(value);
				break;
			case script_baseconstruct:
				this.csConfig.getScripts().getBaseconstruct().setEnable(value);
				break;
			case script_callActiveObjectWithoutCheckingOrStopping:
				this.csConfig.getScripts().getCallActiveObjectWithoutCheckingOrStopping().setEnable(value);
				break;
			case script_changenotification:
				this.csConfig.getScripts().getChangenotification().setEnable(value);
				break;
			case script_cleanup:
				this.csConfig.getScripts().getCleanup().setEnable(value);
				break;
			case script_commentcode:
				this.csConfig.getScripts().getCommentcode().setEnable(value);
				break;
			case script_connect:
				this.csConfig.getScripts().getConnect().setEnable(value);
				break;
			case script_ConnectAndDontCloseMemberVariable:
				this.csConfig.getScripts().getConnectAndDontCloseMemberVariable().setEnable(value);
				break;
			case script_constnames:
				this.csConfig.getScripts().getConstnames().setEnable(value);
				break;
			case script_consttdescptr:
				this.csConfig.getScripts().getConsttdescptr().setEnable(value);
				break;
			case script_controlornull:
				this.csConfig.getScripts().getControlornull().setEnable(value);
				break;
			case script_crepository:
				this.csConfig.getScripts().getCrepository().setEnable(value);
				break;
			case script_ctltargettype:
				this.csConfig.getScripts().getCtltargettype().setEnable(value);
				break;
			case script_customizableicons:
				this.csConfig.getScripts().getCustomizableicons().setEnable(value);
				break;
			case script_debugrom:
				this.csConfig.getScripts().getDebugrom().setEnable(value);
				break;
			case script_declarename:
				this.csConfig.getScripts().getDeclarename().setEnable(value);
				break;
			case script_deleteMemberVariable:
				this.csConfig.getScripts().getDeleteMemberVariable().setEnable(value);
				break;
			case script_destructor:
				this.csConfig.getScripts().getDestructor().setEnable(value);
				break;
			case script_doubleSemiColon:
				this.csConfig.getScripts().getDoubleSemiColon().setEnable(value);
				break;
			case script_driveletters:
				this.csConfig.getScripts().getDriveletters().setEnable(value);
				break;
			case script_eikbuttons:
				this.csConfig.getScripts().getEikbuttons().setEnable(value);
				break;
			case script_eikonenvstatic:
				this.csConfig.getScripts().getEikonenvstatic().setEnable(value);
				break;
			case script_enummembers:
				this.csConfig.getScripts().getEnummembers().setEnable(value);
				break;
			case script_enumnames:
				this.csConfig.getScripts().getEnumnames().setEnable(value);
				break;
			case script_exportinline:
				this.csConfig.getScripts().getExportinline().setEnable(value);
				break;
			case script_exportpurevirtual:
				this.csConfig.getScripts().getExportpurevirtual().setEnable(value);
				break;
			case script_flags:
				this.csConfig.getScripts().getFlags().setEnable(value);
				break;
			case script_foff:
				this.csConfig.getScripts().getFoff().setEnable(value);
				break;
			case script_forbiddenwords:
				this.csConfig.getScripts().getForbiddenwords().setEnable(value);
				break;
			case script_forgottoputptroncleanupstack:
				this.csConfig.getScripts().getForgottoputptroncleanupstack().setEnable(value);
				break;
			case script_friend:
				this.csConfig.getScripts().getFriend().setEnable(value);
				break;
			case script_goto:
				this.csConfig.getScripts().getGoto().setEnable(value);
				break;
			case script_ifassignments:
				this.csConfig.getScripts().getIfassignments().setEnable(value);
				break;
			case script_ifpreprocessor:
				this.csConfig.getScripts().getIfpreprocessor().setEnable(value);
				break;
			case script_inheritanceorder:
				this.csConfig.getScripts().getInheritanceorder().setEnable(value);
				break;
			case script_intleaves:
				this.csConfig.getScripts().getIntleaves().setEnable(value);
				break;
			case script_jmp:
				this.csConfig.getScripts().getJmp().setEnable(value);
				break;
			case script_leave:
				this.csConfig.getScripts().getLeave().setEnable(value);
				break;
			case script_LeaveNoError:
				this.csConfig.getScripts().getLeaveNoError().setEnable(value);
				break;
			case script_leavingoperators:
				this.csConfig.getScripts().getLeavingoperators().setEnable(value);
				break;
			case script_LFunctionCantLeave:
				this.csConfig.getScripts().getLFunctionCantLeave().setEnable(value);
				break;
			case script_longlines: 
				this.csConfig.getScripts().getLonglines().setEnable(value);
				break;
			case script_magicnumbers:
				this.csConfig.getScripts().getMagicnumbers().setEnable(value);
				break;
			case script_mclassdestructor:
				this.csConfig.getScripts().getMclassdestructor().setEnable(value);
				break;
			case script_memberlc:
				this.csConfig.getScripts().getMemberlc().setEnable(value);
				break;
			case script_membervariablecallld:
				this.csConfig.getScripts().getMembervariablecallld().setEnable(value);
				break;
			case script_missingcancel:
				this.csConfig.getScripts().getMissingcancel().setEnable(value);
				break;
			case script_missingcclass:
				this.csConfig.getScripts().getMissingcclass().setEnable(value);
				break;
			case script_mmpsourcepath:
				this.csConfig.getScripts().getMmpsourcepath().setEnable(value);
				break;
			case script_multilangrsc:
				this.csConfig.getScripts().getMultilangrsc().setEnable(value);
				break;
			case script_multipledeclarations:
				this.csConfig.getScripts().getMultipledeclarations().setEnable(value);
				break;
			case script_multipleinheritance:
				this.csConfig.getScripts().getMultipleinheritance().setEnable(value);
				break;
			case script_mydocs:
				this.csConfig.getScripts().getMydocs().setEnable(value);
				break;
			case script_namespace:
				this.csConfig.getScripts().getNamespace().setEnable(value);
				break;
			case script_newlreferences:
				this.csConfig.getScripts().getNewlreferences().setEnable(value);
				break;
			case script_noleavetrap:
				this.csConfig.getScripts().getNoleavetrap().setEnable(value);
				break;
			case script_nonconsthbufc:
				this.csConfig.getScripts().getNonconsthbufc().setEnable(value);
				break;
			case script_nonconsttdesc:
				this.csConfig.getScripts().getNonconsttdesc().setEnable(value);
				break;
			case script_nonleavenew:
				this.csConfig.getScripts().getNonleavenew().setEnable(value);
				break;
			case script_nonunicodeskins:
				this.csConfig.getScripts().getNonunicodeskins().setEnable(value);
				break;
			case script_null:
				this.csConfig.getScripts().getNull().setEnable(value);
				break;
			case script_open:
				this.csConfig.getScripts().getOpen().setEnable(value);
				break;
			case script_pointertoarrays:
				this.csConfig.getScripts().getPointertoarrays().setEnable(value);
				break;
			case script_pragmadisable:
				this.csConfig.getScripts().getPragmadisable().setEnable(value);
				break;
			case script_pragmamessage:
				this.csConfig.getScripts().getPragmamessage().setEnable(value);
				break;
			case script_pragmaother:
				this.csConfig.getScripts().getPragmaother().setEnable(value);
				break;
			case script_privateinheritance:
				this.csConfig.getScripts().getPrivateinheritance().setEnable(value);
				break;
			case script_pushaddrvar:
				this.csConfig.getScripts().getPushaddrvar().setEnable(value);
				break;
			case script_pushmember:
				this.csConfig.getScripts().getPushmember().setEnable(value);
				break;
			case script_readresource:
				this.csConfig.getScripts().getReadresource().setEnable(value);
				break;
			case script_resourcenotoncleanupstack:
				this.csConfig.getScripts().getResourcenotoncleanupstack().setEnable(value);
				break;
			case script_resourcesonheap:
				this.csConfig.getScripts().getResourcesonheap().setEnable(value);
				break;
			case script_returndescriptoroutofscope:
				this.csConfig.getScripts().getReturndescriptoroutofscope().setEnable(value);
				break;
			case script_rfs:
				this.csConfig.getScripts().getRfs().setEnable(value);
				break;
			case script_rssnames:
				this.csConfig.getScripts().getRssnames().setEnable(value);
				break;
			case script_stringliterals:
				this.csConfig.getScripts().getStringliterals().setEnable(value);
				break;
			case script_stringsinresourcefiles:
				this.csConfig.getScripts().getStringsinresourcefiles().setEnable(value);
				break;
			case script_struct:
				this.csConfig.getScripts().getStruct().setEnable(value);
				break;
			case script_tcclasses:
				this.csConfig.getScripts().getTcclasses().setEnable(value);
				break;
			case script_tclassdestructor:
				this.csConfig.getScripts().getTclassdestructor().setEnable(value);
				break;
			case script_todocomments:
				this.csConfig.getScripts().getTodocomments().setEnable(value);
				break;
			case script_trapcleanup:
				this.csConfig.getScripts().getTrapcleanup().setEnable(value);
				break;
			case script_trapeleave:
				this.csConfig.getScripts().getTrapeleave().setEnable(value);
				break;
			case script_traprunl:
				this.csConfig.getScripts().getTraprunl().setEnable(value);
				break;
			case script_trspassing:
				this.csConfig.getScripts().getTrspassing().setEnable(value);
				break;
			case script_uids:
				this.csConfig.getScripts().getUids().setEnable(value);
				break;
			case script_uncompressedaif:
				this.csConfig.getScripts().getUncompressedaif().setEnable(value);
				break;
			case script_uncompressedbmp:
				this.csConfig.getScripts().getUncompressedbmp().setEnable(value);
				break;
			case script_unicodesource:
				this.csConfig.getScripts(). getUnicodesource().setEnable(value);
				break;
			case script_userafter:
				this.csConfig.getScripts().getUserafter().setEnable(value);
				break;
			case script_userfree:
				this.csConfig.getScripts().getUserfree().setEnable(value);
				break;
			case script_userWaitForRequest:
				this.csConfig.getScripts().getUserWaitForRequest().setEnable(value);
				break;
			case script_variablenames:
				this.csConfig.getScripts().getVariablenames().setEnable(value);
				break;
			case script_voidparameter:
				this.csConfig.getScripts().getVoidparameter().setEnable(value);
				break;
			case script_worryingcomments:
				this.csConfig.getScripts().getWorryingcomments().setEnable(value);
				break;
			case script_unknown:
			default:
				break;
		}
	}

	/**
	 * Retrieve the "category" attribute of a CodeScanner script element
	 * @param script - script element containing the attribute
	 * @return attribute value
	 */
	public String getScriptCategory(CSScript script) {
		switch (script) {
			case script_accessArrayElementWithoutCheck:
				return this.csConfig.getScripts().getAccessArrayElementWithoutCheck().getCategory().toString();
			case script_accessArrayElementWithoutCheck2:
				return this.csConfig.getScripts().getAccessArrayElementWithoutCheck2().getCategory().toString();
			case script_activestart:
				return this.csConfig.getScripts().getActivestart().getCategory().toString();
			case script_activestop:
				return this.csConfig.getScripts().getActivestop().getCategory().toString();
			case script_arraypassing:
				return this.csConfig.getScripts().getArraypassing().getCategory().toString();
			case script_arrayptrcleanup:
				return this.csConfig.getScripts().getArrayptrcleanup().getCategory().toString();
			case script_assertdebuginvariant:
				return this.csConfig.getScripts().getAssertdebuginvariant().getCategory().toString();
			case script_baddefines:
				return this.csConfig.getScripts().getBaddefines().getCategory().toString();
			case script_baseconstruct:
				return this.csConfig.getScripts().getBaseconstruct().getCategory().toString();
			case script_callActiveObjectWithoutCheckingOrStopping:
				return this.csConfig.getScripts().getCallActiveObjectWithoutCheckingOrStopping().getCategory().toString();
			case script_changenotification:
				return this.csConfig.getScripts().getChangenotification().getCategory().toString();
			case script_cleanup:
				return this.csConfig.getScripts().getCleanup().getCategory().toString();
			case script_commentcode:
				return this.csConfig.getScripts().getCommentcode().getCategory().toString();
			case script_connect:
				return this.csConfig.getScripts().getConnect().getCategory().toString();
			case script_ConnectAndDontCloseMemberVariable:
				return this.csConfig.getScripts().getConnectAndDontCloseMemberVariable().getCategory().toString();
			case script_constnames:
				return this.csConfig.getScripts().getConstnames().getCategory().toString();
			case script_consttdescptr:
				return this.csConfig.getScripts().getConsttdescptr().getCategory().toString();
			case script_controlornull:
				return this.csConfig.getScripts().getControlornull().getCategory().toString();
			case script_crepository:
				return this.csConfig.getScripts().getCrepository().getCategory().toString();
			case script_ctltargettype:
				return this.csConfig.getScripts().getCtltargettype().getCategory().toString();
			case script_customizableicons:
				return this.csConfig.getScripts().getCustomizableicons().getCategory().toString();
			case script_debugrom:
				return this.csConfig.getScripts().getDebugrom().getCategory().toString();
			case script_declarename:
				return this.csConfig.getScripts().getDeclarename().getCategory().toString();
			case script_deleteMemberVariable:
				return this.csConfig.getScripts().getDeleteMemberVariable().getCategory().toString();
			case script_destructor:
				return this.csConfig.getScripts().getDestructor().getCategory().toString();
			case script_doubleSemiColon:
				return this.csConfig.getScripts().getDoubleSemiColon().getCategory().toString();
			case script_driveletters:
				return this.csConfig.getScripts().getDriveletters().getCategory().toString();
			case script_eikbuttons:
				return this.csConfig.getScripts().getEikbuttons().getCategory().toString();
			case script_eikonenvstatic:
				return this.csConfig.getScripts().getEikonenvstatic().getCategory().toString();
			case script_enummembers:
				return this.csConfig.getScripts().getEnummembers().getCategory().toString();
			case script_enumnames:
				return this.csConfig.getScripts().getEnumnames().getCategory().toString();
			case script_exportinline:
				return this.csConfig.getScripts().getExportinline().getCategory().toString();
			case script_exportpurevirtual:
				return this.csConfig.getScripts().getExportpurevirtual().getCategory().toString();
			case script_flags:
				return this.csConfig.getScripts().getFlags().getCategory().toString();
			case script_foff:
				return this.csConfig.getScripts().getFoff().getCategory().toString();
			case script_forbiddenwords:
				return this.csConfig.getScripts().getForbiddenwords().getCategory().toString();
			case script_forgottoputptroncleanupstack:
				return this.csConfig.getScripts().getForgottoputptroncleanupstack().getCategory().toString();
			case script_friend:
				return this.csConfig.getScripts().getFriend().getCategory().toString();
			case script_goto:
				return this.csConfig.getScripts().getGoto().getCategory().toString();
			case script_ifassignments:
				return this.csConfig.getScripts().getIfassignments().getCategory().toString();
			case script_ifpreprocessor:
				return this.csConfig.getScripts().getIfpreprocessor().getCategory().toString();
			case script_inheritanceorder:
				return this.csConfig.getScripts().getInheritanceorder().getCategory().toString();
			case script_intleaves:
				return this.csConfig.getScripts().getIntleaves().getCategory().toString();
			case script_jmp:
				return this.csConfig.getScripts().getJmp().getCategory().toString();
			case script_leave:
				return this.csConfig.getScripts().getLeave().getCategory().toString();
			case script_LeaveNoError:
				return this.csConfig.getScripts().getLeaveNoError().getCategory().toString();
			case script_leavingoperators:
				return this.csConfig.getScripts().getLeavingoperators().getCategory().toString();
			case script_LFunctionCantLeave:
				return this.csConfig.getScripts().getLFunctionCantLeave().getCategory().toString();
			case script_longlines: 
				return this.csConfig.getScripts().getLonglines().getCategory().toString();
			case script_magicnumbers:
				return this.csConfig.getScripts().getMagicnumbers().getCategory().toString();
			case script_mclassdestructor:
				return this.csConfig.getScripts().getMclassdestructor().getCategory().toString();
			case script_memberlc:
				return this.csConfig.getScripts().getMemberlc().getCategory().toString();
			case script_membervariablecallld:
				return this.csConfig.getScripts().getMembervariablecallld().getCategory().toString();
			case script_missingcancel:
				return this.csConfig.getScripts().getMissingcancel().getCategory().toString();
			case script_missingcclass:
				return this.csConfig.getScripts().getMissingcclass().getCategory().toString();
			case script_mmpsourcepath:
				return this.csConfig.getScripts().getMmpsourcepath().getCategory().toString();
			case script_multilangrsc:
				return this.csConfig.getScripts().getMultilangrsc().getCategory().toString();
			case script_multipledeclarations:
				return this.csConfig.getScripts().getMultipledeclarations().getCategory().toString();
			case script_multipleinheritance:
				return this.csConfig.getScripts().getMultipleinheritance().getCategory().toString();
			case script_mydocs:
				return this.csConfig.getScripts().getMydocs().getCategory().toString();
			case script_namespace:
				return this.csConfig.getScripts().getNamespace().getCategory().toString();
			case script_newlreferences:
				return this.csConfig.getScripts().getNewlreferences().getCategory().toString();
			case script_noleavetrap:
				return this.csConfig.getScripts().getNoleavetrap().getCategory().toString();
			case script_nonconsthbufc:
				return this.csConfig.getScripts().getNonconsthbufc().getCategory().toString();
			case script_nonconsttdesc:
				return this.csConfig.getScripts().getNonconsttdesc().getCategory().toString();
			case script_nonleavenew:
				return this.csConfig.getScripts().getNonleavenew().getCategory().toString();
			case script_nonunicodeskins:
				return this.csConfig.getScripts().getNonunicodeskins().getCategory().toString();
			case script_null:
				return this.csConfig.getScripts().getNull().getCategory().toString();
			case script_open:
				return this.csConfig.getScripts().getOpen().getCategory().toString();
			case script_pointertoarrays:
				return this.csConfig.getScripts().getPointertoarrays().getCategory().toString();
			case script_pragmadisable:
				return this.csConfig.getScripts().getPragmadisable().getCategory().toString();
			case script_pragmamessage:
				return this.csConfig.getScripts().getPragmamessage().getCategory().toString();
			case script_pragmaother:
				return this.csConfig.getScripts().getPragmaother().getCategory().toString();
			case script_privateinheritance:
				return this.csConfig.getScripts().getPrivateinheritance().getCategory().toString();
			case script_pushaddrvar:
				return this.csConfig.getScripts().getPushaddrvar().getCategory().toString();
			case script_pushmember:
				return this.csConfig.getScripts().getPushmember().getCategory().toString();
			case script_readresource:
				return this.csConfig.getScripts().getReadresource().getCategory().toString();
			case script_resourcenotoncleanupstack:
				return this.csConfig.getScripts().getResourcenotoncleanupstack().getCategory().toString();
			case script_resourcesonheap:
				return this.csConfig.getScripts().getResourcesonheap().getCategory().toString();
			case script_returndescriptoroutofscope:
				return this.csConfig.getScripts().getReturndescriptoroutofscope().getCategory().toString();
			case script_rfs:
				return this.csConfig.getScripts().getRfs().getCategory().toString();
			case script_rssnames:
				return this.csConfig.getScripts().getRssnames().getCategory().toString();
			case script_stringliterals:
				return this.csConfig.getScripts().getStringliterals().getCategory().toString();
			case script_stringsinresourcefiles:
				return this.csConfig.getScripts().getStringsinresourcefiles().getCategory().toString();
			case script_struct:
				return this.csConfig.getScripts().getStruct().getCategory().toString();
			case script_tcclasses:
				return this.csConfig.getScripts().getTcclasses().getCategory().toString();
			case script_tclassdestructor:
				return this.csConfig.getScripts().getTclassdestructor().getCategory().toString();
			case script_todocomments:
				return this.csConfig.getScripts().getTodocomments().getCategory().toString();
			case script_trapcleanup:
				return this.csConfig.getScripts().getTrapcleanup().getCategory().toString();
			case script_trapeleave:
				return this.csConfig.getScripts().getTrapeleave().getCategory().toString();
			case script_traprunl:
				return this.csConfig.getScripts().getTraprunl().getCategory().toString();
			case script_trspassing:
				return this.csConfig.getScripts().getTrspassing().getCategory().toString();
			case script_uids:
				return this.csConfig.getScripts().getUids().getCategory().toString();
			case script_uncompressedaif:
				return this.csConfig.getScripts().getUncompressedaif().getCategory().toString();
			case script_uncompressedbmp:
				return this.csConfig.getScripts().getUncompressedaif().getCategory().toString();
			case script_unicodesource:
				return this.csConfig.getScripts(). getUnicodesource().getCategory().toString();
			case script_userafter:
				return this.csConfig.getScripts().getUserafter().getCategory().toString();
			case script_userfree:
				return this.csConfig.getScripts().getUserfree().getCategory().toString();
			case script_userWaitForRequest:
				return this.csConfig.getScripts().getUserWaitForRequest().getCategory().toString();
			case script_variablenames:
				return this.csConfig.getScripts().getVariablenames().getCategory().toString();
			case script_voidparameter:
				return this.csConfig.getScripts().getVoidparameter().getCategory().toString();
			case script_worryingcomments:
				return this.csConfig.getScripts().getWorryingcomments().getCategory().toString();
			case script_unknown:
			default:
				return "other";
		}
	}

	/**
	 * Set the "category" attribute of a CodeScanner script element
	 * @param script - script element containing the attribute
	 * @param value - new attribute value
	 */
	public void setScriptCategory(CSScript script, String value) {
		CategoryType category = CategoryType.get(value);
		switch (script) {
			case script_accessArrayElementWithoutCheck:
				this.csConfig.getScripts().getAccessArrayElementWithoutCheck().setCategory(category);
				break;
			case script_accessArrayElementWithoutCheck2:
				this.csConfig.getScripts().getAccessArrayElementWithoutCheck2().setCategory(category);
				break;
			case script_activestart:
				this.csConfig.getScripts().getActivestart().setCategory(category);
				break;
			case script_activestop:
				this.csConfig.getScripts().getActivestop().setCategory(category);
				break;
			case script_arraypassing:
				this.csConfig.getScripts().getArraypassing().setCategory(category);
				break;
			case script_arrayptrcleanup:
				this.csConfig.getScripts().getArrayptrcleanup().setCategory(category);
				break;
			case script_assertdebuginvariant:
				this.csConfig.getScripts().getAssertdebuginvariant().setCategory(category);
				break;
			case script_baddefines:
				this.csConfig.getScripts().getBaddefines().setCategory(category);
				break;
			case script_baseconstruct:
				this.csConfig.getScripts().getBaseconstruct().setCategory(category);
				break;
			case script_callActiveObjectWithoutCheckingOrStopping:
				this.csConfig.getScripts().getCallActiveObjectWithoutCheckingOrStopping().setCategory(category);
				break;
			case script_changenotification:
				this.csConfig.getScripts().getChangenotification().setCategory(category);
				break;
			case script_cleanup:
				this.csConfig.getScripts().getCleanup().setCategory(category);
				break;
			case script_commentcode:
				this.csConfig.getScripts().getCommentcode().setCategory(category);
				break;
			case script_connect:
				this.csConfig.getScripts().getConnect().setCategory(category);
				break;
			case script_ConnectAndDontCloseMemberVariable:
				this.csConfig.getScripts().getConnectAndDontCloseMemberVariable().setCategory(category);
				break;
			case script_constnames:
				this.csConfig.getScripts().getConstnames().setCategory(category);
				break;
			case script_consttdescptr:
				this.csConfig.getScripts().getConsttdescptr().setCategory(category);
				break;
			case script_controlornull:
				this.csConfig.getScripts().getControlornull().setCategory(category);
				break;
			case script_crepository:
				this.csConfig.getScripts().getCrepository().setCategory(category);
				break;
			case script_ctltargettype:
				this.csConfig.getScripts().getCtltargettype().setCategory(category);
				break;
			case script_customizableicons:
				this.csConfig.getScripts().getCustomizableicons().setCategory(category);
				break;
			case script_debugrom:
				this.csConfig.getScripts().getDebugrom().setCategory(category);
				break;
			case script_declarename:
				this.csConfig.getScripts().getDeclarename().setCategory(category);
				break;
			case script_deleteMemberVariable:
				this.csConfig.getScripts().getDeleteMemberVariable().setCategory(category);
				break;
			case script_destructor:
				this.csConfig.getScripts().getDestructor().setCategory(category);
				break;
			case script_doubleSemiColon:
				this.csConfig.getScripts().getDoubleSemiColon().setCategory(category);
				break;
			case script_driveletters:
				this.csConfig.getScripts().getDriveletters().setCategory(category);
				break;
			case script_eikbuttons:
				this.csConfig.getScripts().getEikbuttons().setCategory(category);
				break;
			case script_eikonenvstatic:
				this.csConfig.getScripts().getEikonenvstatic().setCategory(category);
				break;
			case script_enummembers:
				this.csConfig.getScripts().getEnummembers().setCategory(category);
				break;
			case script_enumnames:
				this.csConfig.getScripts().getEnumnames().setCategory(category);
				break;
			case script_exportinline:
				this.csConfig.getScripts().getExportinline().setCategory(category);
				break;
			case script_exportpurevirtual:
				this.csConfig.getScripts().getExportpurevirtual().setCategory(category);
				break;
			case script_flags:
				this.csConfig.getScripts().getFlags().setCategory(category);
				break;
			case script_foff:
				this.csConfig.getScripts().getFoff().setCategory(category);
				break;
			case script_forbiddenwords:
				this.csConfig.getScripts().getForbiddenwords().setCategory(category);
				break;
			case script_forgottoputptroncleanupstack:
				this.csConfig.getScripts().getForgottoputptroncleanupstack().setCategory(category);
				break;
			case script_friend:
				this.csConfig.getScripts().getFriend().setCategory(category);
				break;
			case script_goto:
				this.csConfig.getScripts().getGoto().setCategory(category);
				break;
			case script_ifassignments:
				this.csConfig.getScripts().getIfassignments().setCategory(category);
				break;
			case script_ifpreprocessor:
				this.csConfig.getScripts().getIfpreprocessor().setCategory(category);
				break;
			case script_inheritanceorder:
				this.csConfig.getScripts().getInheritanceorder().setCategory(category);
				break;
			case script_intleaves:
				this.csConfig.getScripts().getIntleaves().setCategory(category);
				break;
			case script_jmp:
				this.csConfig.getScripts().getJmp().setCategory(category);
				break;
			case script_leave:
				this.csConfig.getScripts().getLeave().setCategory(category);
				break;
			case script_LeaveNoError:
				this.csConfig.getScripts().getLeaveNoError().setCategory(category);
				break;
			case script_leavingoperators:
				this.csConfig.getScripts().getLeavingoperators().setCategory(category);
				break;
			case script_LFunctionCantLeave:
				this.csConfig.getScripts().getLFunctionCantLeave().setCategory(category);
				break;
			case script_longlines: 
				this.csConfig.getScripts().getLonglines().setCategory(category);
				break;
			case script_magicnumbers:
				this.csConfig.getScripts().getMagicnumbers().setCategory(category);
				break;
			case script_mclassdestructor:
				this.csConfig.getScripts().getMclassdestructor().setCategory(category);
				break;
			case script_memberlc:
				this.csConfig.getScripts().getMemberlc().setCategory(category);
				break;
			case script_membervariablecallld:
				this.csConfig.getScripts().getMembervariablecallld().setCategory(category);
				break;
			case script_missingcancel:
				this.csConfig.getScripts().getMissingcancel().setCategory(category);
				break;
			case script_missingcclass:
				this.csConfig.getScripts().getMissingcclass().setCategory(category);
				break;
			case script_mmpsourcepath:
				this.csConfig.getScripts().getMmpsourcepath().setCategory(category);
				break;
			case script_multilangrsc:
				this.csConfig.getScripts().getMultilangrsc().setCategory(category);
				break;
			case script_multipledeclarations:
				this.csConfig.getScripts().getMultipledeclarations().setCategory(category);
				break;
			case script_multipleinheritance:
				this.csConfig.getScripts().getMultipleinheritance().setCategory(category);
				break;
			case script_mydocs:
				this.csConfig.getScripts().getMydocs().setCategory(category);
				break;
			case script_namespace:
				this.csConfig.getScripts().getNamespace().setCategory(category);
				break;
			case script_newlreferences:
				this.csConfig.getScripts().getNewlreferences().setCategory(category);
				break;
			case script_noleavetrap:
				this.csConfig.getScripts().getNoleavetrap().setCategory(category);
				break;
			case script_nonconsthbufc:
				this.csConfig.getScripts().getNonconsthbufc().setCategory(category);
				break;
			case script_nonconsttdesc:
				this.csConfig.getScripts().getNonconsttdesc().setCategory(category);
				break;
			case script_nonleavenew:
				this.csConfig.getScripts().getNonleavenew().setCategory(category);
				break;
			case script_nonunicodeskins:
				this.csConfig.getScripts().getNonunicodeskins().setCategory(category);
				break;
			case script_null:
				this.csConfig.getScripts().getNull().setCategory(category);
				break;
			case script_open:
				this.csConfig.getScripts().getOpen().setCategory(category);
				break;
			case script_pointertoarrays:
				this.csConfig.getScripts().getPointertoarrays().setCategory(category);
				break;
			case script_pragmadisable:
				this.csConfig.getScripts().getPragmadisable().setCategory(category);
				break;
			case script_pragmamessage:
				this.csConfig.getScripts().getPragmamessage().setCategory(category);
				break;
			case script_pragmaother:
				this.csConfig.getScripts().getPragmaother().setCategory(category);
				break;
			case script_privateinheritance:
				this.csConfig.getScripts().getPrivateinheritance().setCategory(category);
				break;
			case script_pushaddrvar:
				this.csConfig.getScripts().getPushaddrvar().setCategory(category);
				break;
			case script_pushmember:
				this.csConfig.getScripts().getPushmember().setCategory(category);
				break;
			case script_readresource:
				this.csConfig.getScripts().getReadresource().setCategory(category);
				break;
			case script_resourcenotoncleanupstack:
				this.csConfig.getScripts().getResourcenotoncleanupstack().setCategory(category);
				break;
			case script_resourcesonheap:
				this.csConfig.getScripts().getResourcesonheap().setCategory(category);
				break;
			case script_returndescriptoroutofscope:
				this.csConfig.getScripts().getReturndescriptoroutofscope().setCategory(category);
				break;
			case script_rfs:
				this.csConfig.getScripts().getRfs().setCategory(category);
				break;
			case script_rssnames:
				this.csConfig.getScripts().getRssnames().setCategory(category);
				break;
			case script_stringliterals:
				this.csConfig.getScripts().getStringliterals().setCategory(category);
				break;
			case script_stringsinresourcefiles:
				this.csConfig.getScripts().getStringsinresourcefiles().setCategory(category);
				break;
			case script_struct:
				this.csConfig.getScripts().getStruct().setCategory(category);
				break;
			case script_tcclasses:
				this.csConfig.getScripts().getTcclasses().setCategory(category);
				break;
			case script_tclassdestructor:
				this.csConfig.getScripts().getTclassdestructor().setCategory(category);
				break;
			case script_todocomments:
				this.csConfig.getScripts().getTodocomments().setCategory(category);
				break;
			case script_trapcleanup:
				this.csConfig.getScripts().getTrapcleanup().setCategory(category);
				break;
			case script_trapeleave:
				this.csConfig.getScripts().getTrapeleave().setCategory(category);
				break;
			case script_traprunl:
				this.csConfig.getScripts().getTraprunl().setCategory(category);
				break;
			case script_trspassing:
				this.csConfig.getScripts().getTrspassing().setCategory(category);
				break;
			case script_uids:
				this.csConfig.getScripts().getUids().setCategory(category);
				break;
			case script_uncompressedaif:
				this.csConfig.getScripts().getUncompressedaif().setCategory(category);
				break;
			case script_uncompressedbmp:
				this.csConfig.getScripts().getUncompressedaif().setCategory(category);
				break;
			case script_unicodesource:
				this.csConfig.getScripts(). getUnicodesource().setCategory(category);
				break;
			case script_userafter:
				this.csConfig.getScripts().getUserafter().setCategory(category);
				break;
			case script_userfree:
				this.csConfig.getScripts().getUserfree().setCategory(category);
				break;
			case script_userWaitForRequest:
				this.csConfig.getScripts().getUserWaitForRequest().setCategory(category);
				break;
			case script_variablenames:
				this.csConfig.getScripts().getVariablenames().setCategory(category);
				break;
			case script_voidparameter:
				this.csConfig.getScripts().getVoidparameter().setCategory(category);
				break;
			case script_worryingcomments:
				this.csConfig.getScripts().getWorryingcomments().setCategory(category);
				break;
			case script_unknown:
			default:
				break;
		}
	}

	/**
	 * Retrieve the "severity" attribute of a CodeScanner script element
	 * @param script - script element containing the attribute
	 * @return attribute value
	 */
	public String getScriptSeverity(CSScript script) {
		switch (script) {
			case script_accessArrayElementWithoutCheck:
				return this.csConfig.getScripts().getAccessArrayElementWithoutCheck().getSeverity().toString();
			case script_accessArrayElementWithoutCheck2:
				return this.csConfig.getScripts().getAccessArrayElementWithoutCheck2().getSeverity().toString();
			case script_activestart:
				return this.csConfig.getScripts().getActivestart().getSeverity().toString();
			case script_activestop:
				return this.csConfig.getScripts().getActivestop().getSeverity().toString();
			case script_arraypassing:
				return this.csConfig.getScripts().getArraypassing().getSeverity().toString();
			case script_arrayptrcleanup:
				return this.csConfig.getScripts().getArrayptrcleanup().getSeverity().toString();
			case script_assertdebuginvariant:
				return this.csConfig.getScripts().getAssertdebuginvariant().getSeverity().toString();
			case script_baddefines:
				return this.csConfig.getScripts().getBaddefines().getSeverity().toString();
			case script_baseconstruct:
				return this.csConfig.getScripts().getBaseconstruct().getSeverity().toString();
			case script_callActiveObjectWithoutCheckingOrStopping:
				return this.csConfig.getScripts().getCallActiveObjectWithoutCheckingOrStopping().getSeverity().toString();
			case script_changenotification:
				return this.csConfig.getScripts().getChangenotification().getSeverity().toString();
			case script_cleanup:
				return this.csConfig.getScripts().getCleanup().getSeverity().toString();
			case script_commentcode:
				return this.csConfig.getScripts().getCommentcode().getSeverity().toString();
			case script_connect:
				return this.csConfig.getScripts().getConnect().getSeverity().toString();
			case script_ConnectAndDontCloseMemberVariable:
				return this.csConfig.getScripts().getConnectAndDontCloseMemberVariable().getSeverity().toString();
			case script_constnames:
				return this.csConfig.getScripts().getConstnames().getSeverity().toString();
			case script_consttdescptr:
				return this.csConfig.getScripts().getConsttdescptr().getSeverity().toString();
			case script_controlornull:
				return this.csConfig.getScripts().getControlornull().getSeverity().toString();
			case script_crepository:
				return this.csConfig.getScripts().getCrepository().getSeverity().toString();
			case script_ctltargettype:
				return this.csConfig.getScripts().getCtltargettype().getSeverity().toString();
			case script_customizableicons:
				return this.csConfig.getScripts().getCustomizableicons().getSeverity().toString();
			case script_debugrom:
				return this.csConfig.getScripts().getDebugrom().getSeverity().toString();
			case script_declarename:
				return this.csConfig.getScripts().getDeclarename().getSeverity().toString();
			case script_deleteMemberVariable:
				return this.csConfig.getScripts().getDeleteMemberVariable().getSeverity().toString();
			case script_destructor:
				return this.csConfig.getScripts().getDestructor().getSeverity().toString();
			case script_doubleSemiColon:
				return this.csConfig.getScripts().getDoubleSemiColon().getSeverity().toString();
			case script_driveletters:
				return this.csConfig.getScripts().getDriveletters().getSeverity().toString();
			case script_eikbuttons:
				return this.csConfig.getScripts().getEikbuttons().getSeverity().toString();
			case script_eikonenvstatic:
				return this.csConfig.getScripts().getEikonenvstatic().getSeverity().toString();
			case script_enummembers:
				return this.csConfig.getScripts().getEnummembers().getSeverity().toString();
			case script_enumnames:
				return this.csConfig.getScripts().getEnumnames().getSeverity().toString();
			case script_exportinline:
				return this.csConfig.getScripts().getExportinline().getSeverity().toString();
			case script_exportpurevirtual:
				return this.csConfig.getScripts().getExportpurevirtual().getSeverity().toString();
			case script_flags:
				return this.csConfig.getScripts().getFlags().getSeverity().toString();
			case script_foff:
				return this.csConfig.getScripts().getFoff().getSeverity().toString();
			case script_forbiddenwords:
				return this.csConfig.getScripts().getForbiddenwords().getSeverity().toString();
			case script_forgottoputptroncleanupstack:
				return this.csConfig.getScripts().getForgottoputptroncleanupstack().getSeverity().toString();
			case script_friend:
				return this.csConfig.getScripts().getFriend().getSeverity().toString();
			case script_goto:
				return this.csConfig.getScripts().getGoto().getSeverity().toString();
			case script_ifassignments:
				return this.csConfig.getScripts().getIfassignments().getSeverity().toString();
			case script_ifpreprocessor:
				return this.csConfig.getScripts().getIfpreprocessor().getSeverity().toString();
			case script_inheritanceorder:
				return this.csConfig.getScripts().getInheritanceorder().getSeverity().toString();
			case script_intleaves:
				return this.csConfig.getScripts().getIntleaves().getSeverity().toString();
			case script_jmp:
				return this.csConfig.getScripts().getJmp().getSeverity().toString();
			case script_leave:
				return this.csConfig.getScripts().getLeave().getSeverity().toString();
			case script_LeaveNoError:
				return this.csConfig.getScripts().getLeaveNoError().getSeverity().toString();
			case script_leavingoperators:
				return this.csConfig.getScripts().getLeavingoperators().getSeverity().toString();
			case script_LFunctionCantLeave:
				return this.csConfig.getScripts().getLFunctionCantLeave().getSeverity().toString();
			case script_longlines: 
				return this.csConfig.getScripts().getLonglines().getSeverity().toString();
			case script_magicnumbers:
				return this.csConfig.getScripts().getMagicnumbers().getSeverity().toString();
			case script_mclassdestructor:
				return this.csConfig.getScripts().getMclassdestructor().getSeverity().toString();
			case script_memberlc:
				return this.csConfig.getScripts().getMemberlc().getSeverity().toString();
			case script_membervariablecallld:
				return this.csConfig.getScripts().getMembervariablecallld().getSeverity().toString();
			case script_missingcancel:
				return this.csConfig.getScripts().getMissingcancel().getSeverity().toString();
			case script_missingcclass:
				return this.csConfig.getScripts().getMissingcclass().getSeverity().toString();
			case script_mmpsourcepath:
				return this.csConfig.getScripts().getMmpsourcepath().getSeverity().toString();
			case script_multilangrsc:
				return this.csConfig.getScripts().getMultilangrsc().getSeverity().toString();
			case script_multipledeclarations:
				return this.csConfig.getScripts().getMultipledeclarations().getSeverity().toString();
			case script_multipleinheritance:
				return this.csConfig.getScripts().getMultipleinheritance().getSeverity().toString();
			case script_mydocs:
				return this.csConfig.getScripts().getMydocs().getSeverity().toString();
			case script_namespace:
				return this.csConfig.getScripts().getNamespace().getSeverity().toString();
			case script_newlreferences:
				return this.csConfig.getScripts().getNewlreferences().getSeverity().toString();
			case script_noleavetrap:
				return this.csConfig.getScripts().getNoleavetrap().getSeverity().toString();
			case script_nonconsthbufc:
				return this.csConfig.getScripts().getNonconsthbufc().getSeverity().toString();
			case script_nonconsttdesc:
				return this.csConfig.getScripts().getNonconsttdesc().getSeverity().toString();
			case script_nonleavenew:
				return this.csConfig.getScripts().getNonleavenew().getSeverity().toString();
			case script_nonunicodeskins:
				return this.csConfig.getScripts().getNonunicodeskins().getSeverity().toString();
			case script_null:
				return this.csConfig.getScripts().getNull().getSeverity().toString();
			case script_open:
				return this.csConfig.getScripts().getOpen().getSeverity().toString();
			case script_pointertoarrays:
				return this.csConfig.getScripts().getPointertoarrays().getSeverity().toString();
			case script_pragmadisable:
				return this.csConfig.getScripts().getPragmadisable().getSeverity().toString();
			case script_pragmamessage:
				return this.csConfig.getScripts().getPragmamessage().getSeverity().toString();
			case script_pragmaother:
				return this.csConfig.getScripts().getPragmaother().getSeverity().toString();
			case script_privateinheritance:
				return this.csConfig.getScripts().getPrivateinheritance().getSeverity().toString();
			case script_pushaddrvar:
				return this.csConfig.getScripts().getPushaddrvar().getSeverity().toString();
			case script_pushmember:
				return this.csConfig.getScripts().getPushmember().getSeverity().toString();
			case script_readresource:
				return this.csConfig.getScripts().getReadresource().getSeverity().toString();
			case script_resourcenotoncleanupstack:
				return this.csConfig.getScripts().getResourcenotoncleanupstack().getSeverity().toString();
			case script_resourcesonheap:
				return this.csConfig.getScripts().getResourcesonheap().getSeverity().toString();
			case script_returndescriptoroutofscope:
				return this.csConfig.getScripts().getReturndescriptoroutofscope().getSeverity().toString();
			case script_rfs:
				return this.csConfig.getScripts().getRfs().getSeverity().toString();
			case script_rssnames:
				return this.csConfig.getScripts().getRssnames().getSeverity().toString();
			case script_stringliterals:
				return this.csConfig.getScripts().getStringliterals().getSeverity().toString();
			case script_stringsinresourcefiles:
				return this.csConfig.getScripts().getStringsinresourcefiles().getSeverity().toString();
			case script_struct:
				return this.csConfig.getScripts().getStruct().getSeverity().toString();
			case script_tcclasses:
				return this.csConfig.getScripts().getTcclasses().getSeverity().toString();
			case script_tclassdestructor:
				return this.csConfig.getScripts().getTclassdestructor().getSeverity().toString();
			case script_todocomments:
				return this.csConfig.getScripts().getTodocomments().getSeverity().toString();
			case script_trapcleanup:
				return this.csConfig.getScripts().getTrapcleanup().getSeverity().toString();
			case script_trapeleave:
				return this.csConfig.getScripts().getTrapeleave().getSeverity().toString();
			case script_traprunl:
				return this.csConfig.getScripts().getTraprunl().getSeverity().toString();
			case script_trspassing:
				return this.csConfig.getScripts().getTrspassing().getSeverity().toString();
			case script_uids:
				return this.csConfig.getScripts().getUids().getSeverity().toString();
			case script_uncompressedaif:
				return this.csConfig.getScripts().getUncompressedaif().getSeverity().toString();
			case script_uncompressedbmp:
				return this.csConfig.getScripts().getUncompressedaif().getSeverity().toString();
			case script_unicodesource:
				return this.csConfig.getScripts(). getUnicodesource().getSeverity().toString();
			case script_userafter:
				return this.csConfig.getScripts().getUserafter().getSeverity().toString();
			case script_userfree:
				return this.csConfig.getScripts().getUserfree().getSeverity().toString();
			case script_userWaitForRequest:
				return this.csConfig.getScripts().getUserWaitForRequest().getSeverity().toString();
			case script_variablenames:
				return this.csConfig.getScripts().getVariablenames().getSeverity().toString();
			case script_voidparameter:
				return this.csConfig.getScripts().getVoidparameter().getSeverity().toString();
			case script_worryingcomments:
				return this.csConfig.getScripts().getWorryingcomments().getSeverity().toString();
			case script_unknown:
			default:
				return "high";
		}
	}

	/**
	 * Set the "severity" attribute of a CodeScanner script element
	 * @param script - script element containing the attribute
	 * @param value - new attribute value
	 */
	public void setScriptSeverity(CSScript script, String value) {
		SeverityType severity = SeverityType.get(value);
		switch (script) {
			case script_accessArrayElementWithoutCheck:
				this.csConfig.getScripts().getAccessArrayElementWithoutCheck().setSeverity(severity);
				break;
			case script_accessArrayElementWithoutCheck2:
				this.csConfig.getScripts().getAccessArrayElementWithoutCheck2().setSeverity(severity);
				break;
			case script_activestart:
				this.csConfig.getScripts().getActivestart().setSeverity(severity);
				break;
			case script_activestop:
				this.csConfig.getScripts().getActivestop().setSeverity(severity);
				break;
			case script_arraypassing:
				this.csConfig.getScripts().getArraypassing().setSeverity(severity);
				break;
			case script_arrayptrcleanup:
				this.csConfig.getScripts().getArrayptrcleanup().setSeverity(severity);
				break;
			case script_assertdebuginvariant:
				this.csConfig.getScripts().getAssertdebuginvariant().setSeverity(severity);
				break;
			case script_baddefines:
				this.csConfig.getScripts().getBaddefines().setSeverity(severity);
				break;
			case script_baseconstruct:
				this.csConfig.getScripts().getBaseconstruct().setSeverity(severity);
				break;
			case script_callActiveObjectWithoutCheckingOrStopping:
				this.csConfig.getScripts().getCallActiveObjectWithoutCheckingOrStopping().setSeverity(severity);
				break;
			case script_changenotification:
				this.csConfig.getScripts().getChangenotification().setSeverity(severity);
				break;
			case script_cleanup:
				this.csConfig.getScripts().getCleanup().setSeverity(severity);
				break;
			case script_commentcode:
				this.csConfig.getScripts().getCommentcode().setSeverity(severity);
				break;
			case script_connect:
				this.csConfig.getScripts().getConnect().setSeverity(severity);
				break;
			case script_ConnectAndDontCloseMemberVariable:
				this.csConfig.getScripts().getConnectAndDontCloseMemberVariable().setSeverity(severity);
				break;
			case script_constnames:
				this.csConfig.getScripts().getConstnames().setSeverity(severity);
				break;
			case script_consttdescptr:
				this.csConfig.getScripts().getConsttdescptr().setSeverity(severity);
				break;
			case script_controlornull:
				this.csConfig.getScripts().getControlornull().setSeverity(severity);
				break;
			case script_crepository:
				this.csConfig.getScripts().getCrepository().setSeverity(severity);
				break;
			case script_ctltargettype:
				this.csConfig.getScripts().getCtltargettype().setSeverity(severity);
				break;
			case script_customizableicons:
				this.csConfig.getScripts().getCustomizableicons().setSeverity(severity);
				break;
			case script_debugrom:
				this.csConfig.getScripts().getDebugrom().setSeverity(severity);
				break;
			case script_declarename:
				this.csConfig.getScripts().getDeclarename().setSeverity(severity);
				break;
			case script_deleteMemberVariable:
				this.csConfig.getScripts().getDeleteMemberVariable().setSeverity(severity);
				break;
			case script_destructor:
				this.csConfig.getScripts().getDestructor().setSeverity(severity);
				break;
			case script_doubleSemiColon:
				this.csConfig.getScripts().getDoubleSemiColon().setSeverity(severity);
				break;
			case script_driveletters:
				this.csConfig.getScripts().getDriveletters().setSeverity(severity);
				break;
			case script_eikbuttons:
				this.csConfig.getScripts().getEikbuttons().setSeverity(severity);
				break;
			case script_eikonenvstatic:
				this.csConfig.getScripts().getEikonenvstatic().setSeverity(severity);
				break;
			case script_enummembers:
				this.csConfig.getScripts().getEnummembers().setSeverity(severity);
				break;
			case script_enumnames:
				this.csConfig.getScripts().getEnumnames().setSeverity(severity);
				break;
			case script_exportinline:
				this.csConfig.getScripts().getExportinline().setSeverity(severity);
				break;
			case script_exportpurevirtual:
				this.csConfig.getScripts().getExportpurevirtual().setSeverity(severity);
				break;
			case script_flags:
				this.csConfig.getScripts().getFlags().setSeverity(severity);
				break;
			case script_foff:
				this.csConfig.getScripts().getFoff().setSeverity(severity);
				break;
			case script_forbiddenwords:
				this.csConfig.getScripts().getForbiddenwords().setSeverity(severity);
				break;
			case script_forgottoputptroncleanupstack:
				this.csConfig.getScripts().getForgottoputptroncleanupstack().setSeverity(severity);
				break;
			case script_friend:
				this.csConfig.getScripts().getFriend().setSeverity(severity);
				break;
			case script_goto:
				this.csConfig.getScripts().getGoto().setSeverity(severity);
				break;
			case script_ifassignments:
				this.csConfig.getScripts().getIfassignments().setSeverity(severity);
				break;
			case script_ifpreprocessor:
				this.csConfig.getScripts().getIfpreprocessor().setSeverity(severity);
				break;
			case script_inheritanceorder:
				this.csConfig.getScripts().getInheritanceorder().setSeverity(severity);
				break;
			case script_intleaves:
				this.csConfig.getScripts().getIntleaves().setSeverity(severity);
				break;
			case script_jmp:
				this.csConfig.getScripts().getJmp().setSeverity(severity);
				break;
			case script_leave:
				this.csConfig.getScripts().getLeave().setSeverity(severity);
				break;
			case script_LeaveNoError:
				this.csConfig.getScripts().getLeaveNoError().setSeverity(severity);
				break;
			case script_leavingoperators:
				this.csConfig.getScripts().getLeavingoperators().setSeverity(severity);
				break;
			case script_LFunctionCantLeave:
				this.csConfig.getScripts().getLFunctionCantLeave().setSeverity(severity);
				break;
			case script_longlines: 
				this.csConfig.getScripts().getLonglines().setSeverity(severity);
				break;
			case script_magicnumbers:
				this.csConfig.getScripts().getMagicnumbers().setSeverity(severity);
				break;
			case script_mclassdestructor:
				this.csConfig.getScripts().getMclassdestructor().setSeverity(severity);
				break;
			case script_memberlc:
				this.csConfig.getScripts().getMemberlc().setSeverity(severity);
				break;
			case script_membervariablecallld:
				this.csConfig.getScripts().getMembervariablecallld().setSeverity(severity);
				break;
			case script_missingcancel:
				this.csConfig.getScripts().getMissingcancel().setSeverity(severity);
				break;
			case script_missingcclass:
				this.csConfig.getScripts().getMissingcclass().setSeverity(severity);
				break;
			case script_mmpsourcepath:
				this.csConfig.getScripts().getMmpsourcepath().setSeverity(severity);
				break;
			case script_multilangrsc:
				this.csConfig.getScripts().getMultilangrsc().setSeverity(severity);
				break;
			case script_multipledeclarations:
				this.csConfig.getScripts().getMultipledeclarations().setSeverity(severity);
				break;
			case script_multipleinheritance:
				this.csConfig.getScripts().getMultipleinheritance().setSeverity(severity);
				break;
			case script_mydocs:
				this.csConfig.getScripts().getMydocs().setSeverity(severity);
				break;
			case script_namespace:
				this.csConfig.getScripts().getNamespace().setSeverity(severity);
				break;
			case script_newlreferences:
				this.csConfig.getScripts().getNewlreferences().setSeverity(severity);
				break;
			case script_noleavetrap:
				this.csConfig.getScripts().getNoleavetrap().setSeverity(severity);
				break;
			case script_nonconsthbufc:
				this.csConfig.getScripts().getNonconsthbufc().setSeverity(severity);
				break;
			case script_nonconsttdesc:
				this.csConfig.getScripts().getNonconsttdesc().setSeverity(severity);
				break;
			case script_nonleavenew:
				this.csConfig.getScripts().getNonleavenew().setSeverity(severity);
				break;
			case script_nonunicodeskins:
				this.csConfig.getScripts().getNonunicodeskins().setSeverity(severity);
				break;
			case script_null:
				this.csConfig.getScripts().getNull().setSeverity(severity);
				break;
			case script_open:
				this.csConfig.getScripts().getOpen().setSeverity(severity);
				break;
			case script_pointertoarrays:
				this.csConfig.getScripts().getPointertoarrays().setSeverity(severity);
				break;
			case script_pragmadisable:
				this.csConfig.getScripts().getPragmadisable().setSeverity(severity);
				break;
			case script_pragmamessage:
				this.csConfig.getScripts().getPragmamessage().setSeverity(severity);
				break;
			case script_pragmaother:
				this.csConfig.getScripts().getPragmaother().setSeverity(severity);
				break;
			case script_privateinheritance:
				this.csConfig.getScripts().getPrivateinheritance().setSeverity(severity);
				break;
			case script_pushaddrvar:
				this.csConfig.getScripts().getPushaddrvar().setSeverity(severity);
				break;
			case script_pushmember:
				this.csConfig.getScripts().getPushmember().setSeverity(severity);
				break;
			case script_readresource:
				this.csConfig.getScripts().getReadresource().setSeverity(severity);
				break;
			case script_resourcenotoncleanupstack:
				this.csConfig.getScripts().getResourcenotoncleanupstack().setSeverity(severity);
				break;
			case script_resourcesonheap:
				this.csConfig.getScripts().getResourcesonheap().setSeverity(severity);
				break;
			case script_returndescriptoroutofscope:
				this.csConfig.getScripts().getReturndescriptoroutofscope().setSeverity(severity);
				break;
			case script_rfs:
				this.csConfig.getScripts().getRfs().setSeverity(severity);
				break;
			case script_rssnames:
				this.csConfig.getScripts().getRssnames().setSeverity(severity);
				break;
			case script_stringliterals:
				this.csConfig.getScripts().getStringliterals().setSeverity(severity);
				break;
			case script_stringsinresourcefiles:
				this.csConfig.getScripts().getStringsinresourcefiles().setSeverity(severity);
				break;
			case script_struct:
				this.csConfig.getScripts().getStruct().setSeverity(severity);
				break;
			case script_tcclasses:
				this.csConfig.getScripts().getTcclasses().setSeverity(severity);
				break;
			case script_tclassdestructor:
				this.csConfig.getScripts().getTclassdestructor().setSeverity(severity);
				break;
			case script_todocomments:
				this.csConfig.getScripts().getTodocomments().setSeverity(severity);
				break;
			case script_trapcleanup:
				this.csConfig.getScripts().getTrapcleanup().setSeverity(severity);
				break;
			case script_trapeleave:
				this.csConfig.getScripts().getTrapeleave().setSeverity(severity);
				break;
			case script_traprunl:
				this.csConfig.getScripts().getTraprunl().setSeverity(severity);
				break;
			case script_trspassing:
				this.csConfig.getScripts().getTrspassing().setSeverity(severity);
				break;
			case script_uids:
				this.csConfig.getScripts().getUids().setSeverity(severity);
				break;
			case script_uncompressedaif:
				this.csConfig.getScripts().getUncompressedaif().setSeverity(severity);
				break;
			case script_uncompressedbmp:
				this.csConfig.getScripts().getUncompressedaif().setSeverity(severity);
				break;
			case script_unicodesource:
				this.csConfig.getScripts(). getUnicodesource().setSeverity(severity);
				break;
			case script_userafter:
				this.csConfig.getScripts().getUserafter().setSeverity(severity);
				break;
			case script_userfree:
				this.csConfig.getScripts().getUserfree().setSeverity(severity);
				break;
			case script_userWaitForRequest:
				this.csConfig.getScripts().getUserWaitForRequest().setSeverity(severity);
				break;
			case script_variablenames:
				this.csConfig.getScripts().getVariablenames().setSeverity(severity);
				break;
			case script_voidparameter:
				this.csConfig.getScripts().getVoidparameter().setSeverity(severity);
				break;
			case script_worryingcomments:
				this.csConfig.getScripts().getWorryingcomments().setSeverity(severity);
				break;
			case script_unknown:
			default:
				break;
		}
	}

	/**
	 * Retrieve the cclassIgnoreRE value of the missingcclass script
	 * @return cclassIgnoreRE
	 */
	public String getScriptCClassIgnore() {
		return this.csConfig.getScripts().getMissingcclass().getCclassIgnoreRE();
	}

	/**
	 * Set the cclassIgnoreRE value of the missingcclass script
	 * @param value - new cclassIgnoreRE value
	 */
	public void setScriptCClassIgnore(String value) {
		this.csConfig.getScripts().getMissingcclass().setCclassIgnoreRE(value);
	}

	/**
	 * Retrieve the wordsRE value of the forbiddenwords script
	 * @return wordsRE
	 */
	public String getScriptForbiddenWords() {
		return this.csConfig.getScripts().getForbiddenwords().getWordsRE();
	}

	/**
	 * Set the wordsRE value of the forbiddenwords script
	 * @param value - new wordsRE value
	 */
	public void setScriptForbiddenWords(String value) {
		this.csConfig.getScripts().getForbiddenwords().setWordsRE(value);
	}

	/**
	 * Retrieve the iconsRE value of the customizableicons script
	 * @return iconsRE
	 */
	public String getScriptIcons() {
		return this.csConfig.getScripts().getCustomizableicons().getIconsRE();
	}

	/**
	 * Set the iconsRE value of the customizableicons script
	 * @param value - new iconsRE value
	 */
	public void setScriptIcons(String value)	 {
		this.csConfig.getScripts().getCustomizableicons().setIconsRE(value);
	}

	/**
	 * Retrieve the LFunctionIgnoreRE value of the LFunctionCantLeave script
	 * @return LFunctionIgnoreRE
	 */
	public String getScriptLFunctionIgnore() {
		return this.csConfig.getScripts().getLFunctionCantLeave().getLFunctionIgnoreRE();
	}

	/**
	 * Set the LFunctionIgnoreRE value of the LFunctionCantLeave script
	 * @param value - new LFunctionIgnoreRE value
	 */
	public void setScriptLFunctionIgnore(String value) {
		this.csConfig.getScripts().getLFunctionCantLeave().setLFunctionIgnoreRE(value);
	}

	/**
	 * Retrieve the "length" attribute of the longlines script
	 * @return attribute value
	 */
	public int getScriptLongLinesLength() {
		return this.csConfig.getScripts().getLonglines().getLength();
	}

	/**
	 * Set the "length" attribute of the longlines script
	 * @param value - new attribute value
	 */
	public void setScriptLongLinesLength(int value) {
		this.csConfig.getScripts().getLonglines().setLength(value);
	}
	
	/**
	 * Retrieve the openIgnoreRE value of the open script
	 * @return openIgnoreRE
	 */
	public String getScriptOpenIgnore() {
		return this.csConfig.getScripts().getOpen().getOpenIgnoreRE();
	}

	/**
	 * Set the openIgnoreRE value of the open script
	 * @param value - new openIgnoreRE value
	 */
	public void setScriptOpenIgnore(String value) {
		this.csConfig.getScripts().getOpen().setOpenIgnoreRE(value);
	}

	/**
	 * Retrieve the worryRE value of the worryingcomments script
	 * @return worryRE
	 */
	public String getScriptWorryingComments() {
		return this.csConfig.getScripts().getWorryingcomments().getWorryRE();
	}

	/**
	 * Set the worryRE value of the worryingcomments script
	 * @param value - new worryRE value
	 */
	public void setScriptWorryingComments(String value) {
		this.csConfig.getScripts().getWorryingcomments().setWorryRE(value);
	}

	/**
	 * Retrieve the "enabled" attribute of a CodeScanner script category element
	 * @param category - script category element containing the attribute
	 * @return attribute value
	 */
	public boolean getCategoryEnabled(CSCategory category) {
		switch (category) {
			 case category_legal:
				 return this.csConfig.getCategories().getLegal().isEnable();
			 case category_panic:
				 return this.csConfig.getCategories().getPanic().isEnable();
			 case category_canpanic:
				 return this.csConfig.getCategories().getCanpanic().isEnable();
			 case category_functionality:
				 return this.csConfig.getCategories().getFunctionality().isEnable();
			 case category_localisation:
				 return this.csConfig.getCategories().getLocalisation().isEnable();
			 case category_performance:
				 return this.csConfig.getCategories().getPerformance().isEnable();
			 case category_codingstandards:
				 return this.csConfig.getCategories().getCodingstandards().isEnable();
			 case category_documentation:
				 return this.csConfig.getCategories().getDocumentation().isEnable();
			 case category_codereview:
				 return this.csConfig.getCategories().getCodereview().isEnable();
			 case category_other:
				 return this.csConfig.getCategories().getOther().isEnable();
			 default:
				 return true;
		}
	}

	/**
	 * Set the "enabled" attribute of a CodeScanner script category element
	 * @param category - script category element containing the attribute
	 * @param value - new attribute value
	 */
	public void setCategoryEnabled(CSCategory category, boolean value) {
		switch (category) {
			 case category_legal:
				this.csConfig.getCategories().getLegal().setEnable(value);
				break;
			 case category_panic:
				this.csConfig.getCategories().getPanic().setEnable(value);
				break;
			 case category_canpanic:
				this.csConfig.getCategories().getCanpanic().setEnable(value);
				break;
			 case category_functionality:
				this.csConfig.getCategories().getFunctionality().setEnable(value);
				break;
			 case category_localisation:
				this.csConfig.getCategories().getLocalisation().setEnable(value);
				break;
			 case category_performance:
				this.csConfig.getCategories().getPerformance().setEnable(value);
				break;
			 case category_codingstandards:
				this.csConfig.getCategories().getCodingstandards().setEnable(value);
				break;
			 case category_documentation:
				this.csConfig.getCategories().getDocumentation().setEnable(value);
				break;
			 case category_codereview:
				this.csConfig.getCategories().getCodereview().setEnable(value);
				break;
			 case category_other:
				this.csConfig.getCategories().getOther().setEnable(value);
				break;
		}
	}

	/**
	 * Retrieve the "enabled" attribute of a CodeScanner script severity element
	 * @param severity - script severity element containing the attribute
	 * @return attribute value
	 */
	public boolean getSeverityEnabled(CSSeverity severity) {
		switch (severity) {
			 case severity_high:
				 return this.csConfig.getSeverities().getHigh().isEnable();
			 case severity_medium:
				 return this.csConfig.getSeverities().getMedium().isEnable();
			 case severity_low:
				 return this.csConfig.getSeverities().getLow().isEnable();
			 default:
				 return true;
		}
	}

	/**
	 * Set the "enabled" attribute of a CodeScanner script severity element
	 * @param severity - script severity element containing the attribute
	 * @param value - new attribute value
	 */
	public void setSeverityEnabled(CSSeverity severity, boolean value) {
		switch (severity) {
			 case severity_high:
				 this.csConfig.getSeverities().getHigh().setEnable(value);
				 break;
			 case severity_medium:
				 this.csConfig.getSeverities().getMedium().setEnable(value);
				 break;
			 case severity_low:
				 this.csConfig.getSeverities().getLow().setEnable(value);
				 break;
		}
	}

	/**
	 * Create the default arguments element.
	 */
	private ArgumentsType createDefaultArguments() {
		ArgumentsType arguments = CSConfigFactory.eINSTANCE.createArgumentsType();
		EList<String> inputArgumentList = arguments.getInput();
		inputArgumentList.clear();
		arguments.setLxr(null);
		arguments.setLxrversion(null);
		arguments.setOutputformat(null);
		arguments.setTimestampedoutput(null);
		return arguments;
	}
	
	/**
	 * Create the default categories element.
	 */
	private CategoriesType createDefaultCategories() {
		CategoriesType categories = CSConfigFactory.eINSTANCE.createCategoriesType();

		CanpanicType category_Canpanic = CSConfigFactory.eINSTANCE.createCanpanicType();
		category_Canpanic.setEnable(true);
		categories.setCanpanic(category_Canpanic);

		CodereviewType category_Codereview = CSConfigFactory.eINSTANCE.createCodereviewType();
		category_Codereview.setEnable(true);
		categories.setCodereview(category_Codereview);

		CodingstandardsType category_Codingstandards = CSConfigFactory.eINSTANCE.createCodingstandardsType();
		category_Codingstandards.setEnable(true);
		categories.setCodingstandards(category_Codingstandards);

		DocumentationType category_Documentation = CSConfigFactory.eINSTANCE.createDocumentationType();
		category_Documentation.setEnable(true);
		categories.setDocumentation(category_Documentation);

		FunctionalityType category_Functionality = CSConfigFactory.eINSTANCE.createFunctionalityType();
		category_Functionality.setEnable(true);
		categories.setFunctionality(category_Functionality);

		LegalType category_Legal = CSConfigFactory.eINSTANCE.createLegalType();
		category_Legal.setEnable(true);
		categories.setLegal(category_Legal);

		LocalisationType category_Localisation = CSConfigFactory.eINSTANCE.createLocalisationType();
		category_Localisation.setEnable(true);
		categories.setLocalisation(category_Localisation);

		OtherType category_Other = CSConfigFactory.eINSTANCE.createOtherType();
		category_Other.setEnable(true);
		categories.setOther(category_Other);

		PanicType category_Panic = CSConfigFactory.eINSTANCE.createPanicType();
		category_Panic.setEnable(true);
		categories.setPanic(category_Panic);

		PerformanceType category_Performance = CSConfigFactory.eINSTANCE.createPerformanceType();
		category_Performance.setEnable(true);
		categories.setPerformance(category_Performance);

		return categories;
	}

	/**
	 * Create the default customrules element.
	 * @return
	 */
	private CustomrulesType createDefaultCustomRules() {
		CustomrulesType customRules = CSConfigFactory.eINSTANCE.createCustomrulesType();
		EList<CustomruleType> customRuleList = customRules.getCustomrule();
		customRuleList.clear();
		return customRules;
	}

	/**
	 * Create the default scripts element.
	 */
	private ScriptsType createDefaultScripts() {
		ScriptsType scripts = CSConfigFactory.eINSTANCE.createScriptsType();

		AccessArrayElementWithoutCheckType script_AccessArrayElementWithoutCheck = CSConfigFactory.eINSTANCE.createAccessArrayElementWithoutCheckType();
		script_AccessArrayElementWithoutCheck.setCategory(CategoryType.CODEREVIEW);
		script_AccessArrayElementWithoutCheck.setEnable(true);
		script_AccessArrayElementWithoutCheck.setSeverity(SeverityType.LOW);
		scripts.setAccessArrayElementWithoutCheck(script_AccessArrayElementWithoutCheck);

		AccessArrayElementWithoutCheck2Type script_AccessArrayElementWithoutCheck2 = CSConfigFactory.eINSTANCE.createAccessArrayElementWithoutCheck2Type();
		script_AccessArrayElementWithoutCheck2.setCategory(CategoryType.CODEREVIEW);
		script_AccessArrayElementWithoutCheck2.setEnable(true);
		script_AccessArrayElementWithoutCheck2.setSeverity(SeverityType.LOW);
		scripts.setAccessArrayElementWithoutCheck2(script_AccessArrayElementWithoutCheck2);

		ActivestartType script_Activestart = CSConfigFactory.eINSTANCE.createActivestartType();
		script_Activestart.setCategory(CategoryType.CODEREVIEW);
		script_Activestart.setEnable(true);
		script_Activestart.setSeverity(SeverityType.LOW);
		scripts.setActivestart(script_Activestart);

		ActivestopType script_Activestop = CSConfigFactory.eINSTANCE.createActivestopType();
		script_Activestop.setCategory(CategoryType.CODEREVIEW);
		script_Activestop.setEnable(true);
		script_Activestop.setSeverity(SeverityType.LOW);
		scripts.setActivestop(script_Activestop);

		ArraypassingType script_Arraypassing = CSConfigFactory.eINSTANCE.createArraypassingType();
		script_Arraypassing.setCategory(CategoryType.PERFORMANCE);
		script_Arraypassing.setEnable(true);
		script_Arraypassing.setSeverity(SeverityType.MEDIUM);
		scripts.setArraypassing(script_Arraypassing);

		ArrayptrcleanupType script_Arrayptrcleanup = CSConfigFactory.eINSTANCE.createArrayptrcleanupType();
		script_Arrayptrcleanup.setCategory(CategoryType.CODEREVIEW);
		script_Arrayptrcleanup.setEnable(true);
		script_Arrayptrcleanup.setSeverity(SeverityType.LOW);
		scripts.setArrayptrcleanup(script_Arrayptrcleanup);

		AssertdebuginvariantType script_Assertdebuginvariant = CSConfigFactory.eINSTANCE.createAssertdebuginvariantType();
		script_Assertdebuginvariant.setCategory(CategoryType.CODINGSTANDARDS);
		script_Assertdebuginvariant.setEnable(true);
		script_Assertdebuginvariant.setSeverity(SeverityType.LOW);
		scripts.setAssertdebuginvariant(script_Assertdebuginvariant);

		BaddefinesType script_Baddefines = CSConfigFactory.eINSTANCE.createBaddefinesType();
		script_Baddefines.setCategory(CategoryType.CODINGSTANDARDS);
		script_Baddefines.setEnable(true);
		script_Baddefines.setSeverity(SeverityType.LOW);
		scripts.setBaddefines(script_Baddefines);

		BaseconstructType script_Baseconstruct = CSConfigFactory.eINSTANCE.createBaseconstructType();
		script_Baseconstruct.setCategory(CategoryType.CODEREVIEW);
		script_Baseconstruct.setEnable(true);
		script_Baseconstruct.setSeverity(SeverityType.LOW);
		scripts.setBaseconstruct(script_Baseconstruct);

		CallActiveObjectWithoutCheckingOrStoppingType script_CallActiveObjectWithoutCheckingOrStopping = CSConfigFactory.eINSTANCE.createCallActiveObjectWithoutCheckingOrStoppingType();
		script_CallActiveObjectWithoutCheckingOrStopping.setCategory(CategoryType.CODEREVIEW);
		script_CallActiveObjectWithoutCheckingOrStopping.setEnable(true);
		script_CallActiveObjectWithoutCheckingOrStopping.setSeverity(SeverityType.LOW);
		scripts.setCallActiveObjectWithoutCheckingOrStopping(script_CallActiveObjectWithoutCheckingOrStopping);

		ChangenotificationType script_Changenotification = CSConfigFactory.eINSTANCE.createChangenotificationType();
		script_Changenotification.setCategory(CategoryType.CODEREVIEW);
		script_Changenotification.setEnable(true);
		script_Changenotification.setSeverity(SeverityType.LOW);
		scripts.setChangenotification(script_Changenotification);

		CleanupType script_Cleanup = CSConfigFactory.eINSTANCE.createCleanupType();
		script_Cleanup.setCategory(CategoryType.CODINGSTANDARDS);
		script_Cleanup.setEnable(true);
		script_Cleanup.setSeverity(SeverityType.LOW);
		scripts.setCleanup(script_Cleanup);

		CommentcodeType script_Commentcode = CSConfigFactory.eINSTANCE.createCommentcodeType();
		script_Commentcode.setCategory(CategoryType.CODEREVIEW);
		script_Commentcode.setEnable(true);
		script_Commentcode.setSeverity(SeverityType.LOW);
		scripts.setCommentcode(script_Commentcode);

		ConnectType script_Connect = CSConfigFactory.eINSTANCE.createConnectType();
		script_Connect.setCategory(CategoryType.CANPANIC);
		script_Connect.setEnable(true);
		script_Connect.setSeverity(SeverityType.HIGH);
		scripts.setConnect(script_Connect);

		ConnectAndDontCloseMemberVariableType script_ConnectAndDontCloseMemberVariable = CSConfigFactory.eINSTANCE.createConnectAndDontCloseMemberVariableType();
		script_ConnectAndDontCloseMemberVariable.setCategory(CategoryType.FUNCTIONALITY);
		script_ConnectAndDontCloseMemberVariable.setEnable(true);
		script_ConnectAndDontCloseMemberVariable.setSeverity(SeverityType.MEDIUM);
		scripts.setConnectAndDontCloseMemberVariable(script_ConnectAndDontCloseMemberVariable);

		ConstnamesType script_Constnames = CSConfigFactory.eINSTANCE.createConstnamesType();
		script_Constnames.setCategory(CategoryType.CODINGSTANDARDS);
		script_Constnames.setEnable(true);
		script_Constnames.setSeverity(SeverityType.LOW);
		scripts.setConstnames(script_Constnames);

		ConsttdescptrType script_Consttdescptr = CSConfigFactory.eINSTANCE.createConsttdescptrType();
		script_Consttdescptr.setCategory(CategoryType.CODINGSTANDARDS);
		script_Consttdescptr.setEnable(true);
		script_Consttdescptr.setSeverity(SeverityType.LOW);
		scripts.setConsttdescptr(script_Consttdescptr);

		ControlornullType script_Controlornull = CSConfigFactory.eINSTANCE.createControlornullType();
		script_Controlornull.setCategory(CategoryType.CANPANIC);
		script_Controlornull.setEnable(true);
		script_Controlornull.setSeverity(SeverityType.HIGH);
		scripts.setControlornull(script_Controlornull);

		CrepositoryType script_Crepository = CSConfigFactory.eINSTANCE.createCrepositoryType();
		script_Crepository.setCategory(CategoryType.OTHER);
		script_Crepository.setEnable(true);
		script_Crepository.setSeverity(SeverityType.LOW);
		scripts.setCrepository(script_Crepository);

		CtltargettypeType script_Ctltargettype = CSConfigFactory.eINSTANCE.createCtltargettypeType();
		script_Ctltargettype.setCategory(CategoryType.FUNCTIONALITY);
		script_Ctltargettype.setEnable(true);
		script_Ctltargettype.setSeverity(SeverityType.MEDIUM);
		scripts.setCtltargettype(script_Ctltargettype);

		CustomizableiconsType script_Customizableicons = CSConfigFactory.eINSTANCE.createCustomizableiconsType();
		script_Customizableicons.setCategory(CategoryType.OTHER);
		script_Customizableicons.setEnable(true);
		script_Customizableicons.setSeverity(SeverityType.LOW);
		//script_Customizableicons.setIconsRE("");
		scripts.setCustomizableicons(script_Customizableicons);

		DebugromType script_Debugrom = CSConfigFactory.eINSTANCE.createDebugromType();
		script_Debugrom.setCategory(CategoryType.PERFORMANCE);
		script_Debugrom.setEnable(true);
		script_Debugrom.setSeverity(SeverityType.MEDIUM);
		scripts.setDebugrom(script_Debugrom);

		DeclarenameType script_Declarename = CSConfigFactory.eINSTANCE.createDeclarenameType();
		script_Declarename.setCategory(CategoryType.CODINGSTANDARDS);
		script_Declarename.setEnable(true);
		script_Declarename.setSeverity(SeverityType.LOW);
		scripts.setDeclarename(script_Declarename);

		DeleteMemberVariableType script_DeleteMemberVariable = CSConfigFactory.eINSTANCE.createDeleteMemberVariableType();
		script_DeleteMemberVariable.setCategory(CategoryType.CANPANIC);
		script_DeleteMemberVariable.setEnable(true);
		script_DeleteMemberVariable.setSeverity(SeverityType.HIGH);
		scripts.setDeleteMemberVariable(script_DeleteMemberVariable);

		DestructorType script_Destructor = CSConfigFactory.eINSTANCE.createDestructorType();
		script_Destructor.setCategory(CategoryType.CANPANIC);
		script_Destructor.setEnable(true);
		script_Destructor.setSeverity(SeverityType.HIGH);
		scripts.setDestructor(script_Destructor);

		DoubleSemiColonType script_DoubleSemiColon = CSConfigFactory.eINSTANCE.createDoubleSemiColonType();
		script_DoubleSemiColon.setCategory(CategoryType.CODEREVIEW);
		script_DoubleSemiColon.setEnable(true);
		script_DoubleSemiColon.setSeverity(SeverityType.LOW);
		scripts.setDoubleSemiColon(script_DoubleSemiColon);

		DrivelettersType script_Driveletters = CSConfigFactory.eINSTANCE.createDrivelettersType();
		script_Driveletters.setCategory(CategoryType.CODINGSTANDARDS);
		script_Driveletters.setEnable(true);
		script_Driveletters.setSeverity(SeverityType.LOW);
		scripts.setDriveletters(script_Driveletters);

		EikbuttonsType script_Eikbuttons = CSConfigFactory.eINSTANCE.createEikbuttonsType();
		script_Eikbuttons.setCategory(CategoryType.LOCALISATION);
		script_Eikbuttons.setEnable(true);
		script_Eikbuttons.setSeverity(SeverityType.MEDIUM);
		scripts.setEikbuttons(script_Eikbuttons);

		EikonenvstaticType script_Eikonenvstatic = CSConfigFactory.eINSTANCE.createEikonenvstaticType();
		script_Eikonenvstatic.setCategory(CategoryType.PERFORMANCE);
		script_Eikonenvstatic.setEnable(true);
		script_Eikonenvstatic.setSeverity(SeverityType.MEDIUM);
		scripts.setEikonenvstatic(script_Eikonenvstatic);

		EnummembersType script_Enummembers = CSConfigFactory.eINSTANCE.createEnummembersType();
		script_Enummembers.setCategory(CategoryType.CODINGSTANDARDS);
		script_Enummembers.setEnable(true);
		script_Enummembers.setSeverity(SeverityType.LOW);
		scripts.setEnummembers(script_Enummembers);

		EnumnamesType script_Enumnames = CSConfigFactory.eINSTANCE.createEnumnamesType();
		script_Enumnames.setCategory(CategoryType.CODINGSTANDARDS);
		script_Enumnames.setEnable(true);
		script_Enumnames.setSeverity(SeverityType.LOW);
		scripts.setEnumnames(script_Enumnames);

		ExportinlineType script_Exportinline = CSConfigFactory.eINSTANCE.createExportinlineType();
		script_Exportinline.setCategory(CategoryType.FUNCTIONALITY);
		script_Exportinline.setEnable(true);
		script_Exportinline.setSeverity(SeverityType.MEDIUM);
		scripts.setExportinline(script_Exportinline);

		ExportpurevirtualType script_Exportpurevirtual = CSConfigFactory.eINSTANCE.createExportpurevirtualType();
		script_Exportpurevirtual.setCategory(CategoryType.FUNCTIONALITY);
		script_Exportpurevirtual.setEnable(true);
		script_Exportpurevirtual.setSeverity(SeverityType.MEDIUM);
		scripts.setExportpurevirtual(script_Exportpurevirtual);

		FlagsType script_Flags = CSConfigFactory.eINSTANCE.createFlagsType();
		script_Flags.setCategory(CategoryType.OTHER);
		script_Flags.setEnable(true);
		script_Flags.setSeverity(SeverityType.LOW);
		scripts.setFlags(script_Flags);

		FoffType script_Foff = CSConfigFactory.eINSTANCE.createFoffType();
		script_Foff.setCategory(CategoryType.CODEREVIEW);
		script_Foff.setEnable(true);
		script_Foff.setSeverity(SeverityType.LOW);
		scripts.setFoff(script_Foff);

		ForbiddenwordsType script_Forbiddenwords = CSConfigFactory.eINSTANCE.createForbiddenwordsType();
		script_Forbiddenwords.setCategory(CategoryType.CODINGSTANDARDS);
		script_Forbiddenwords.setEnable(true);
		script_Forbiddenwords.setSeverity(SeverityType.LOW);
		script_Forbiddenwords.setWordsRE("Typhoon|Hurricane|Epoc|Nokia Mobile Phones|NMP");
		scripts.setForbiddenwords(script_Forbiddenwords);

		ForgottoputptroncleanupstackType script_Forgottoputptroncleanupstack = CSConfigFactory.eINSTANCE.createForgottoputptroncleanupstackType();
		script_Forgottoputptroncleanupstack.setCategory(CategoryType.CODEREVIEW);
		script_Forgottoputptroncleanupstack.setEnable(true);
		script_Forgottoputptroncleanupstack.setSeverity(SeverityType.LOW);
		scripts.setForgottoputptroncleanupstack(script_Forgottoputptroncleanupstack);

		FriendType script_Friend = CSConfigFactory.eINSTANCE.createFriendType();
		script_Friend.setCategory(CategoryType.CODEREVIEW);
		script_Friend.setEnable(true);
		script_Friend.setSeverity(SeverityType.LOW);
		scripts.setFriend(script_Friend);

		GotoType script_Goto = CSConfigFactory.eINSTANCE.createGotoType();
		script_Goto.setCategory(CategoryType.CODINGSTANDARDS);
		script_Goto.setEnable(true);
		script_Goto.setSeverity(SeverityType.LOW);
		scripts.setGoto(script_Goto);

		IfassignmentsType script_Ifassignments = CSConfigFactory.eINSTANCE.createIfassignmentsType();
		script_Ifassignments.setCategory(CategoryType.CODINGSTANDARDS);
		script_Ifassignments.setEnable(true);
		script_Ifassignments.setSeverity(SeverityType.LOW);
		scripts.setIfassignments(script_Ifassignments);

		IfpreprocessorType script_Ifpreprocessor = CSConfigFactory.eINSTANCE.createIfpreprocessorType();
		script_Ifpreprocessor.setCategory(CategoryType.CODINGSTANDARDS);
		script_Ifpreprocessor.setEnable(true);
		script_Ifpreprocessor.setSeverity(SeverityType.LOW);
		scripts.setIfpreprocessor(script_Ifpreprocessor);

		InheritanceorderType script_Inheritanceorder = CSConfigFactory.eINSTANCE.createInheritanceorderType();
		script_Inheritanceorder.setCategory(CategoryType.CANPANIC);
		script_Inheritanceorder.setEnable(true);
		script_Inheritanceorder.setSeverity(SeverityType.HIGH);
		scripts.setInheritanceorder(script_Inheritanceorder);

		IntleavesType script_Intleaves = CSConfigFactory.eINSTANCE.createIntleavesType();
		script_Intleaves.setCategory(CategoryType.CODEREVIEW);
		script_Intleaves.setEnable(true);
		script_Intleaves.setSeverity(SeverityType.LOW);
		scripts.setIntleaves(script_Intleaves);

		JmpType script_Jmp = CSConfigFactory.eINSTANCE.createJmpType();
		script_Jmp.setCategory(CategoryType.CODINGSTANDARDS);
		script_Jmp.setEnable(true);
		script_Jmp.setSeverity(SeverityType.LOW);
		scripts.setJmp(script_Jmp);

		LeaveType script_Leave = CSConfigFactory.eINSTANCE.createLeaveType();
		script_Leave.setCategory(CategoryType.CANPANIC);
		script_Leave.setEnable(true);
		script_Leave.setSeverity(SeverityType.HIGH);
		scripts.setLeave(script_Leave);

		LeaveNoErrorType script_LeaveNoError = CSConfigFactory.eINSTANCE.createLeaveNoErrorType();
		script_LeaveNoError.setCategory(CategoryType.FUNCTIONALITY);
		script_LeaveNoError.setEnable(true);
		script_LeaveNoError.setSeverity(SeverityType.MEDIUM);
		scripts.setLeaveNoError(script_LeaveNoError);

		LeavingoperatorsType script_Leavingoperators = CSConfigFactory.eINSTANCE.createLeavingoperatorsType();
		script_Leavingoperators.setCategory(CategoryType.CODEREVIEW);
		script_Leavingoperators.setEnable(true);
		script_Leavingoperators.setSeverity(SeverityType.LOW);
		scripts.setLeavingoperators(script_Leavingoperators);

		LFunctionCantLeaveType script_LFunctionCantLeave = CSConfigFactory.eINSTANCE.createLFunctionCantLeaveType();
		script_LFunctionCantLeave.setCategory(CategoryType.CODEREVIEW);
		script_LFunctionCantLeave.setEnable(true);
		script_LFunctionCantLeave.setSeverity(SeverityType.LOW);
		script_LFunctionCantLeave.setLFunctionIgnoreRE("RunL");
		scripts.setLFunctionCantLeave(script_LFunctionCantLeave);

		LonglinesType script_Longlines = CSConfigFactory.eINSTANCE.createLonglinesType();
		script_Longlines.setCategory(CategoryType.CODINGSTANDARDS);
		script_Longlines.setEnable(true);
		script_Longlines.setSeverity(SeverityType.LOW);
		script_Longlines.setLength(160);
		scripts.setLonglines(script_Longlines);

		MagicnumbersType script_Magicnumbers = CSConfigFactory.eINSTANCE.createMagicnumbersType();
		script_Magicnumbers.setCategory(CategoryType.CODINGSTANDARDS);
		script_Magicnumbers.setEnable(true);
		script_Magicnumbers.setSeverity(SeverityType.LOW);
		scripts.setMagicnumbers(script_Magicnumbers);

		MclassdestructorType script_Mclassdestructor = CSConfigFactory.eINSTANCE.createMclassdestructorType();
		script_Mclassdestructor.setCategory(CategoryType.CODINGSTANDARDS);
		script_Mclassdestructor.setEnable(true);
		script_Mclassdestructor.setSeverity(SeverityType.LOW);
		scripts.setMclassdestructor(script_Mclassdestructor);

		MemberlcType script_Memberlc = CSConfigFactory.eINSTANCE.createMemberlcType();
		script_Memberlc.setCategory(CategoryType.CANPANIC);
		script_Memberlc.setEnable(true);
		script_Memberlc.setSeverity(SeverityType.HIGH);
		scripts.setMemberlc(script_Memberlc);

		MembervariablecallldType script_Membervariablecallld = CSConfigFactory.eINSTANCE.createMembervariablecallldType();
		script_Membervariablecallld.setCategory(CategoryType.CODINGSTANDARDS);
		script_Membervariablecallld.setEnable(true);
		script_Membervariablecallld.setSeverity(SeverityType.LOW);
		scripts.setMembervariablecallld(script_Membervariablecallld);

		MissingcancelType script_Missingcancel = CSConfigFactory.eINSTANCE.createMissingcancelType();
		script_Missingcancel.setCategory(CategoryType.CODINGSTANDARDS);
		script_Missingcancel.setEnable(true);
		script_Missingcancel.setSeverity(SeverityType.LOW);
		scripts.setMissingcancel(script_Missingcancel);

		MissingcclassType script_Missingcclass = CSConfigFactory.eINSTANCE.createMissingcclassType();
		script_Missingcclass.setCategory(CategoryType.CANPANIC);
		script_Missingcclass.setEnable(true);
		script_Missingcclass.setSeverity(SeverityType.HIGH);
		script_Missingcclass.setCclassIgnoreRE("CBase");
		scripts.setMissingcclass(script_Missingcclass);

		MmpsourcepathType script_Mmpsourcepath = CSConfigFactory.eINSTANCE.createMmpsourcepathType();
		script_Mmpsourcepath.setCategory(CategoryType.CODINGSTANDARDS);
		script_Mmpsourcepath.setEnable(true);
		script_Mmpsourcepath.setSeverity(SeverityType.LOW);
		scripts.setMmpsourcepath(script_Mmpsourcepath);

		MultilangrscType script_Multilangrsc = CSConfigFactory.eINSTANCE.createMultilangrscType();
		script_Multilangrsc.setCategory(CategoryType.CODEREVIEW);
		script_Multilangrsc.setEnable(true);
		script_Multilangrsc.setSeverity(SeverityType.LOW);
		scripts.setMultilangrsc(script_Multilangrsc);

		MultipledeclarationsType script_Multipledeclarations = CSConfigFactory.eINSTANCE.createMultipledeclarationsType();
		script_Multipledeclarations.setCategory(CategoryType.CODINGSTANDARDS);
		script_Multipledeclarations.setEnable(true);
		script_Multipledeclarations.setSeverity(SeverityType.LOW);
		scripts.setMultipledeclarations(script_Multipledeclarations);

		MultipleinheritanceType script_Multipleinheritance = CSConfigFactory.eINSTANCE.createMultipleinheritanceType();
		script_Multipleinheritance.setCategory(CategoryType.FUNCTIONALITY);
		script_Multipleinheritance.setEnable(true);
		script_Multipleinheritance.setSeverity(SeverityType.MEDIUM);
		scripts.setMultipleinheritance(script_Multipleinheritance);

		MydocsType script_Mydocs = CSConfigFactory.eINSTANCE.createMydocsType();
		script_Mydocs.setCategory(CategoryType.FUNCTIONALITY);
		script_Mydocs.setEnable(true);
		script_Mydocs.setSeverity(SeverityType.MEDIUM);
		scripts.setMydocs(script_Mydocs);

		NamespaceType script_Namespace = CSConfigFactory.eINSTANCE.createNamespaceType();
		script_Namespace.setCategory(CategoryType.CODINGSTANDARDS);
		script_Namespace.setEnable(true);
		script_Namespace.setSeverity(SeverityType.LOW);
		scripts.setNamespace(script_Namespace);

		NewlreferencesType script_Newlreferences = CSConfigFactory.eINSTANCE.createNewlreferencesType();
		script_Newlreferences.setCategory(CategoryType.CODINGSTANDARDS);
		script_Newlreferences.setEnable(true);
		script_Newlreferences.setSeverity(SeverityType.LOW);
		scripts.setNewlreferences(script_Newlreferences);

		NoleavetrapType script_Noleavetrap = CSConfigFactory.eINSTANCE.createNoleavetrapType();
		script_Noleavetrap.setCategory(CategoryType.CODEREVIEW);
		script_Noleavetrap.setEnable(true);
		script_Noleavetrap.setSeverity(SeverityType.LOW);
		scripts.setNoleavetrap(script_Noleavetrap);

		NonconsthbufcType script_Nonconsthbufc = CSConfigFactory.eINSTANCE.createNonconsthbufcType();
		script_Nonconsthbufc.setCategory(CategoryType.CODINGSTANDARDS);
		script_Nonconsthbufc.setEnable(true);
		script_Nonconsthbufc.setSeverity(SeverityType.LOW);
		scripts.setNonconsthbufc(script_Nonconsthbufc);

		NonconsttdescType script_Nonconsttdesc = CSConfigFactory.eINSTANCE.createNonconsttdescType();
		script_Nonconsttdesc.setCategory(CategoryType.CODINGSTANDARDS);
		script_Nonconsttdesc.setEnable(true);
		script_Nonconsttdesc.setSeverity(SeverityType.LOW);
		scripts.setNonconsttdesc(script_Nonconsttdesc);

		NonleavenewType script_Nonleavenew = CSConfigFactory.eINSTANCE.createNonleavenewType();
		script_Nonleavenew.setCategory(CategoryType.CODEREVIEW);
		script_Nonleavenew.setEnable(true);
		script_Nonleavenew.setSeverity(SeverityType.LOW);
		scripts.setNonleavenew(script_Nonleavenew);

		NonunicodeskinsType script_Nonunicodeskins = CSConfigFactory.eINSTANCE.createNonunicodeskinsType();
		script_Nonunicodeskins.setCategory(CategoryType.CODEREVIEW);
		script_Nonunicodeskins.setEnable(true);
		script_Nonunicodeskins.setSeverity(SeverityType.LOW);
		scripts.setNonunicodeskins(script_Nonunicodeskins);

		NullType script_Null = CSConfigFactory.eINSTANCE.createNullType();
		script_Null.setCategory(CategoryType.CODINGSTANDARDS);
		script_Null.setEnable(true);
		script_Null.setSeverity(SeverityType.LOW);
		scripts.setNull(script_Null);

		OpenType script_Open = CSConfigFactory.eINSTANCE.createOpenType();
		script_Open.setCategory(CategoryType.CANPANIC);
		script_Open.setEnable(true);
		script_Open.setSeverity(SeverityType.HIGH);
		script_Open.setOpenIgnoreRE("RDesReadStream|RDesWriteStream");
		scripts.setOpen(script_Open);

		PointertoarraysType script_Pointertoarrays = CSConfigFactory.eINSTANCE.createPointertoarraysType();
		script_Pointertoarrays.setCategory(CategoryType.PERFORMANCE);
		script_Pointertoarrays.setEnable(true);
		script_Pointertoarrays.setSeverity(SeverityType.MEDIUM);
		scripts.setPointertoarrays(script_Pointertoarrays);

		PragmadisableType script_Pragmadisable = CSConfigFactory.eINSTANCE.createPragmadisableType();
		script_Pragmadisable.setCategory(CategoryType.CODINGSTANDARDS);
		script_Pragmadisable.setEnable(true);
		script_Pragmadisable.setSeverity(SeverityType.LOW);
		scripts.setPragmadisable(script_Pragmadisable);

		PragmamessageType script_Pragmamessage = CSConfigFactory.eINSTANCE.createPragmamessageType();
		script_Pragmamessage.setCategory(CategoryType.CODINGSTANDARDS);
		script_Pragmamessage.setEnable(true);
		script_Pragmamessage.setSeverity(SeverityType.LOW);
		scripts.setPragmamessage(script_Pragmamessage);

		PragmaotherType script_Pragmaother = CSConfigFactory.eINSTANCE.createPragmaotherType();
		script_Pragmaother.setCategory(CategoryType.CODEREVIEW);
		script_Pragmaother.setEnable(true);
		script_Pragmaother.setSeverity(SeverityType.LOW);
		scripts.setPragmaother(script_Pragmaother);

		PrivateinheritanceType script_Privateinheritance = CSConfigFactory.eINSTANCE.createPrivateinheritanceType();
		script_Privateinheritance.setCategory(CategoryType.CODINGSTANDARDS);
		script_Privateinheritance.setEnable(true);
		script_Privateinheritance.setSeverity(SeverityType.LOW);
		scripts.setPrivateinheritance(script_Privateinheritance);

		PushaddrvarType script_Pushaddrvar = CSConfigFactory.eINSTANCE.createPushaddrvarType();
		script_Pushaddrvar.setCategory(CategoryType.CANPANIC);
		script_Pushaddrvar.setEnable(true);
		script_Pushaddrvar.setSeverity(SeverityType.HIGH);
		scripts.setPushaddrvar(script_Pushaddrvar);

		PushmemberType script_Pushmember = CSConfigFactory.eINSTANCE.createPushmemberType();
		script_Pushmember.setCategory(CategoryType.CANPANIC);
		script_Pushmember.setEnable(true);
		script_Pushmember.setSeverity(SeverityType.HIGH);
		scripts.setPushmember(script_Pushmember);

		ReadresourceType script_Readresource = CSConfigFactory.eINSTANCE.createReadresourceType();
		script_Readresource.setCategory(CategoryType.CANPANIC);
		script_Readresource.setEnable(true);
		script_Readresource.setSeverity(SeverityType.HIGH);
		scripts.setReadresource(script_Readresource);

		ResourcenotoncleanupstackType script_Resourcenotoncleanupstack = CSConfigFactory.eINSTANCE.createResourcenotoncleanupstackType();
		script_Resourcenotoncleanupstack.setCategory(CategoryType.CODEREVIEW);
		script_Resourcenotoncleanupstack.setEnable(true);
		script_Resourcenotoncleanupstack.setSeverity(SeverityType.LOW);
		scripts.setResourcenotoncleanupstack(script_Resourcenotoncleanupstack);

		ResourcesonheapType script_Resourcesonheap = CSConfigFactory.eINSTANCE.createResourcesonheapType();
		script_Resourcesonheap.setCategory(CategoryType.CODINGSTANDARDS);
		script_Resourcesonheap.setEnable(true);
		script_Resourcesonheap.setSeverity(SeverityType.LOW);
		scripts.setResourcesonheap(script_Resourcesonheap);

		ReturndescriptoroutofscopeType script_Returndescriptoroutofscope = CSConfigFactory.eINSTANCE.createReturndescriptoroutofscopeType();
		script_Returndescriptoroutofscope.setCategory(CategoryType.CANPANIC);
		script_Returndescriptoroutofscope.setEnable(true);
		script_Returndescriptoroutofscope.setSeverity(SeverityType.HIGH);
		scripts.setReturndescriptoroutofscope(script_Returndescriptoroutofscope);

		RfsType script_Rfs = CSConfigFactory.eINSTANCE.createRfsType();
		script_Rfs.setCategory(CategoryType.CODEREVIEW);
		script_Rfs.setEnable(true);
		script_Rfs.setSeverity(SeverityType.LOW);
		scripts.setRfs(script_Rfs);

		RssnamesType script_Rssnames = CSConfigFactory.eINSTANCE.createRssnamesType();
		script_Rssnames.setCategory(CategoryType.CODEREVIEW);
		script_Rssnames.setEnable(true);
		script_Rssnames.setSeverity(SeverityType.LOW);
		scripts.setRssnames(script_Rssnames);

		StringliteralsType script_Stringliterals = CSConfigFactory.eINSTANCE.createStringliteralsType();
		script_Stringliterals.setCategory(CategoryType.CODINGSTANDARDS);
		script_Stringliterals.setEnable(true);
		script_Stringliterals.setSeverity(SeverityType.LOW);
		scripts.setStringliterals(script_Stringliterals);

		StringsinresourcefilesType script_Stringsinresourcefiles = CSConfigFactory.eINSTANCE.createStringsinresourcefilesType();
		script_Stringsinresourcefiles.setCategory(CategoryType.CODEREVIEW);
		script_Stringsinresourcefiles.setEnable(true);
		script_Stringsinresourcefiles.setSeverity(SeverityType.LOW);
		scripts.setStringsinresourcefiles(script_Stringsinresourcefiles);

		StructType script_Struct = CSConfigFactory.eINSTANCE.createStructType();
		script_Struct.setCategory(CategoryType.CODINGSTANDARDS);
		script_Struct.setEnable(true);
		script_Struct.setSeverity(SeverityType.LOW);
		scripts.setStruct(script_Struct);

		TcclassesType script_Tcclasses = CSConfigFactory.eINSTANCE.createTcclassesType();
		script_Tcclasses.setCategory(CategoryType.FUNCTIONALITY);
		script_Tcclasses.setEnable(true);
		script_Tcclasses.setSeverity(SeverityType.MEDIUM);
		scripts.setTcclasses(script_Tcclasses);

		TclassdestructorType script_Tclassdestructor = CSConfigFactory.eINSTANCE.createTclassdestructorType();
		script_Tclassdestructor.setCategory(CategoryType.CODINGSTANDARDS);
		script_Tclassdestructor.setEnable(true);
		script_Tclassdestructor.setSeverity(SeverityType.LOW);
		scripts.setTclassdestructor(script_Tclassdestructor);

		TodocommentsType script_Todocomments = CSConfigFactory.eINSTANCE.createTodocommentsType();
		script_Todocomments.setCategory(CategoryType.CODEREVIEW);
		script_Todocomments.setEnable(true);
		script_Todocomments.setSeverity(SeverityType.LOW);
		scripts.setTodocomments(script_Todocomments);

		TrapcleanupType script_Trapcleanup = CSConfigFactory.eINSTANCE.createTrapcleanupType();
		script_Trapcleanup.setCategory(CategoryType.PANIC);
		script_Trapcleanup.setEnable(true);
		script_Trapcleanup.setSeverity(SeverityType.HIGH);
		scripts.setTrapcleanup(script_Trapcleanup);

		TrapeleaveType script_Trapeleave = CSConfigFactory.eINSTANCE.createTrapeleaveType();
		script_Trapeleave.setCategory(CategoryType.PERFORMANCE);
		script_Trapeleave.setEnable(true);
		script_Trapeleave.setSeverity(SeverityType.MEDIUM);
		scripts.setTrapeleave(script_Trapeleave);

		TraprunlType script_Traprunl = CSConfigFactory.eINSTANCE.createTraprunlType();
		script_Traprunl.setCategory(CategoryType.CODINGSTANDARDS);
		script_Traprunl.setEnable(true);
		script_Traprunl.setSeverity(SeverityType.LOW);
		scripts.setTraprunl(script_Traprunl);

		TrspassingType script_Trspassing = CSConfigFactory.eINSTANCE.createTrspassingType();
		script_Trspassing.setCategory(CategoryType.FUNCTIONALITY);
		script_Trspassing.setEnable(true);
		script_Trspassing.setSeverity(SeverityType.MEDIUM);
		scripts.setTrspassing(script_Trspassing);

		UidsType script_Uids = CSConfigFactory.eINSTANCE.createUidsType();
		script_Uids.setCategory(CategoryType.CODEREVIEW);
		script_Uids.setEnable(true);
		script_Uids.setSeverity(SeverityType.LOW);
		scripts.setUids(script_Uids);

		UncompressedaifType script_Uncompressedaif = CSConfigFactory.eINSTANCE.createUncompressedaifType();
		script_Uncompressedaif.setCategory(CategoryType.PERFORMANCE);
		script_Uncompressedaif.setEnable(true);
		script_Uncompressedaif.setSeverity(SeverityType.MEDIUM);
		scripts.setUncompressedaif(script_Uncompressedaif);

		UncompressedbmpType script_Uncompressedbmp = CSConfigFactory.eINSTANCE.createUncompressedbmpType();
		script_Uncompressedbmp.setCategory(CategoryType.PERFORMANCE);
		script_Uncompressedbmp.setEnable(true);
		script_Uncompressedbmp.setSeverity(SeverityType.MEDIUM);
		scripts.setUncompressedbmp(script_Uncompressedbmp);

		UnicodesourceType script_Unicodesource = CSConfigFactory.eINSTANCE.createUnicodesourceType();
		script_Unicodesource.setCategory(CategoryType.CODEREVIEW);
		script_Unicodesource.setEnable(true);
		script_Unicodesource.setSeverity(SeverityType.LOW);
		scripts.setUnicodesource(script_Unicodesource);

		UserafterType script_Userafter = CSConfigFactory.eINSTANCE.createUserafterType();
		script_Userafter.setCategory(CategoryType.PERFORMANCE);
		script_Userafter.setEnable(true);
		script_Userafter.setSeverity(SeverityType.MEDIUM);
		scripts.setUserafter(script_Userafter);

		UserfreeType script_Userfree = CSConfigFactory.eINSTANCE.createUserfreeType();
		script_Userfree.setCategory(CategoryType.CODEREVIEW);
		script_Userfree.setEnable(true);
		script_Userfree.setSeverity(SeverityType.LOW);
		scripts.setUserfree(script_Userfree);

		UserWaitForRequestType script_UserWaitForRequest = CSConfigFactory.eINSTANCE.createUserWaitForRequestType();
		script_UserWaitForRequest.setCategory(CategoryType.CODEREVIEW);
		script_UserWaitForRequest.setEnable(true);
		script_UserWaitForRequest.setSeverity(SeverityType.LOW);
		scripts.setUserWaitForRequest(script_UserWaitForRequest);

		VariablenamesType script_Variablenames = CSConfigFactory.eINSTANCE.createVariablenamesType();
		script_Variablenames.setCategory(CategoryType.CODINGSTANDARDS);
		script_Variablenames.setEnable(true);
		script_Variablenames.setSeverity(SeverityType.LOW);
		scripts.setVariablenames(script_Variablenames);

		VoidparameterType script_Voidparameter = CSConfigFactory.eINSTANCE.createVoidparameterType();
		script_Voidparameter.setCategory(CategoryType.CODINGSTANDARDS);
		script_Voidparameter.setEnable(true);
		script_Voidparameter.setSeverity(SeverityType.LOW);
		scripts.setVoidparameter(script_Voidparameter);

		WorryingcommentsType script_Worryingcomments = CSConfigFactory.eINSTANCE.createWorryingcommentsType();
		script_Worryingcomments.setCategory(CategoryType.CODEREVIEW);
		script_Worryingcomments.setEnable(true);
		script_Worryingcomments.setSeverity(SeverityType.LOW);
		script_Worryingcomments.setWorryRE("kludge|workaround|\\scrap|hack");
		scripts.setWorryingcomments(script_Worryingcomments);

		return scripts;
	}
	
	/**
	 * Create the default severities element.
	 */
	private SeveritiesType createDefaultSeverities() {
		SeveritiesType severities = CSConfigFactory.eINSTANCE.createSeveritiesType();
		
		HighType severity_High = CSConfigFactory.eINSTANCE.createHighType();
		severity_High.setEnable(true);
		severities.setHigh(severity_High);
		
		MediumType severity_Medium = CSConfigFactory.eINSTANCE.createMediumType();
		severity_Medium.setEnable(true);
		severities.setMedium(severity_Medium);
		
		LowType severity_Low = CSConfigFactory.eINSTANCE.createLowType();
		severity_Low.setEnable(true);
		severities.setLow(severity_Low);
		
		return severities;
	}

	/**
	 * Create the default sources element.
	 */
	private SourcesType createDefaultSources() {
		SourcesType sources = CSConfigFactory.eINSTANCE.createSourcesType();
		EList<String> excludeList = sources.getExclude();
		String[] defaultFileFilters = new String[] {
				".*\\.au",
				".*\\.avi",
				".*\\.bat",
				".*\\.bin",
				".*\\.bmp",
				".*\\.cmd",
				".*\\.dll",
				".*\\.doc",
				".*\\.exe",
				".*\\.gif",
				".*\\.jpg",
				".*\\.lib",
				".*\\.log",
				".*\\.mbm",
				".*\\.mp3",
				".*\\.mpg",
				".*\\.png",
				".*\\.raw",
				".*\\.rtf",
				".*\\.tif",
				".*\\.wav",
				".*\\.wbmp",
				".*\\.wmf",
				".*\\.xls",
				".*\\.zip"
			};
		for (int i = 0; i < defaultFileFilters.length; i++) {
			excludeList.add(defaultFileFilters[i]);
		}
		return sources;
	}

	/**
	 * Create an error dialog when failed to load from CodeScanner configuration file.
	 */
	private void loadConfigError(String fileName, String errorMessage) {
		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (workbenchWindow != null) {
			MessageDialog.openError(workbenchWindow.getShell(), 
				Messages.getString("CSConfigSettings.LoadConfigErrorTitle"), 
				Messages.getString("CSConfigSettings.LoadConfigErrorMessage") + fileName + ": " + errorMessage);
		}
	}

	/**
	 * Create an error dialog when failed to save CodeScanner configuration to file.
	 */
	private void saveConfigError(String fileName, String errorMessage) {
		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (workbenchWindow != null) {
			MessageDialog.openError(workbenchWindow.getShell(), 
				Messages.getString("CSConfigSettings.SaveConfigErrorTitle"), 
				Messages.getString("CSConfigSettings.SaveConfigErrorMessage") + fileName + ": " + errorMessage);
		}
	}

}
