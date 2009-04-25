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

import com.nokia.carbide.cpp.internal.codescanner.Messages;


/**
 * A class for handling a Codescanner rule.
 *
 */
public class CSRule {
	private CSScript		script;
	private CSCategory		category;
	private CSSeverity		severity;
	private boolean			enabled;
	
	/**
	 * Create an instance of CSRule
	 * @param script - script type of a rule
	 * @param category - category type of a rule
	 * @param severity - severity type of a rule
	 * @param enabled - on/off flag of a rule
	 */
	public CSRule(CSScript script, 
				  CSCategory category, 
				  CSSeverity severity, 
				  boolean enabled) {
		this.script = script;
		this.category = category;
		this.severity = severity;
		this.enabled = enabled;
	}
	
	/**
	 * Return the category type of a rule
	 * @return category type of a rule
	 */
	public CSCategory getCategory() {
		return this.category;
	}

	/**
	 * Set the category type of a rule
	 * @param category - new category type
	 */
	public void setCategory(CSCategory category) {
		this.category = category;
	}
	
	/**
	 * Check whether a rule is enabled
	 * @return boolean indicating whether a rule is enabled
	 */
	public boolean getEnabled() {
		return this.enabled;
	}
	
	/**
	 * Set the enable/disable flag of a rule
	 * @param enabled - enable/disable flag for a rule
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * Return a detail description of a CodeScanner rule in a string
	 * @return description in a string
	 */
	public String getDetails() {
		if (this.script == null)
			return "";
		switch (this.script) {
		case script_accessArrayElementWithoutCheck:
			return Messages.getString("CSRule.AccessArrayElementWithoutCheck");
		case script_accessArrayElementWithoutCheck2:
			return Messages.getString("CSRule.AccessArrayElementWithoutCheck2");
		case script_activestart:
			return Messages.getString("CSRule.Activestart");
		case script_activestop:
			return Messages.getString("CSRule.Activestop");
		case script_arraypassing:
			return Messages.getString("CSRule.Arraypassing");
		case script_arrayptrcleanup:
			return Messages.getString("CSRule.Arrayptrcleanup");
		case script_assertdebuginvariant:
			return Messages.getString("CSRule.Assertdebuginvariant");
		case script_baddefines:
			return Messages.getString("CSRule.Baddefines");
		case script_baseconstruct:
			return Messages.getString("CSRule.Baseconstruct");
		case script_callActiveObjectWithoutCheckingOrStopping:
			return Messages.getString("CSRule.CallActiveObjectWithoutCheckingOrStopping");
		case script_changenotification:
			return Messages.getString("CSRule.Changenotification");
		case script_cleanup:
			return Messages.getString("CSRule.Cleanup");
		case script_commentcode:
			return Messages.getString("CSRule.Commentcode");
		case script_connect:
			return Messages.getString("CSRule.Connect");
		case script_ConnectAndDontCloseMemberVariable:
			return Messages.getString("CSRule.ConnectAndDontCloseMemberVariable");
		case script_constnames:
			return Messages.getString("CSRule.Constnames");
		case script_consttdescptr:
			return Messages.getString("CSRule.Consttdescptr");
		case script_controlornull:
			return Messages.getString("CSRule.Controlornull");
		case script_crepository:
			return Messages.getString("CSRule.Crepository");
		case script_ctltargettype:
			return Messages.getString("CSRule.Ctltargettype");
		case script_customizableicons:
			return Messages.getString("CSRule.Customizableicons");
		case script_debugrom:
			return Messages.getString("CSRule.Debugrom");
		case script_declarename:
			return Messages.getString("CSRule.Declarename");
		case script_deleteMemberVariable:
			return Messages.getString("CSRule.DeleteMemberVariable");
		case script_destructor:
			return Messages.getString("CSRule.Destructor");
		case script_doubleSemiColon:
			return Messages.getString("CSRule.DoubleSemiColon");
		case script_driveletters:
			return Messages.getString("CSRule.Driveletters");
		case script_eikbuttons:
			return Messages.getString("CSRule.Eikbuttons");
		case script_eikonenvstatic:
			return Messages.getString("CSRule.Eikonenvstatic");
		case script_enummembers:
			return Messages.getString("CSRule.Enummembers");
		case script_enumnames:
			return Messages.getString("CSRule.Enumnames");
		case script_exportinline:
			return Messages.getString("CSRule.Exportinline");
		case script_exportpurevirtual:
			return Messages.getString("CSRule.Exportpurevirtual");
		case script_flags:
			return Messages.getString("CSRule.Flags");
		case script_foff:
			return Messages.getString("CSRule.Foff");
		case script_forbiddenwords:
			return Messages.getString("CSRule.Forbiddenwords");
		case script_forgottoputptroncleanupstack:
			return Messages.getString("CSRule.Forgottoputptroncleanupstack");
		case script_friend:
			return Messages.getString("CSRule.Friend");
		case script_goto:
			return Messages.getString("CSRule.Goto");
		case script_ifassignments:
			return Messages.getString("CSRule.Ifassignments");
		case script_ifpreprocessor:
			return Messages.getString("CSRule.Ifpreprocessor");
		case script_inheritanceorder:
			return Messages.getString("CSRule.Inheritanceorder");
		case script_intleaves:
			return Messages.getString("CSRule.Intleaves");
		case script_jmp:
			return Messages.getString("CSRule.Jmp");
		case script_leave:
			return Messages.getString("CSRule.Leave");
		case script_LeaveNoError:
			return Messages.getString("CSRule.LeaveNoError");
		case script_leavingoperators:
			return Messages.getString("CSRule.Leavingoperators");
		case script_LFunctionCantLeave:
			return Messages.getString("CSRule.LFunctionCantLeave");
		case script_longlines:
			return Messages.getString("CSRule.Longlines");
		case script_magicnumbers:
			return Messages.getString("CSRule.Magicnumbers");
		case script_mclassdestructor:
			return Messages.getString("CSRule.Mclassdestructor");
		case script_memberlc:
			return Messages.getString("CSRule.Memberlc");
		case script_membervariablecallld:
			return Messages.getString("CSRule.Membervariablecallld");
		case script_missingcancel:
			return Messages.getString("CSRule.Missingcancel");
		case script_missingcclass:
			return Messages.getString("CSRule.Missingcclass");
		case script_mmpsourcepath:
			return Messages.getString("CSRule.Mmpsourcepath");
		case script_multilangrsc:
			return Messages.getString("CSRule.Multilangrsc");
		case script_multipledeclarations:
			return Messages.getString("CSRule.Multipledeclarations");
		case script_multipleinheritance:
			return Messages.getString("CSRule.Multipleinheritance");
		case script_mydocs:
			return Messages.getString("CSRule.Mydocs");
		case script_namespace:
			return Messages.getString("CSRule.Namespace");
		case script_newlreferences:
			return Messages.getString("CSRule.Newlreferences");
		case script_noleavetrap:
			return Messages.getString("CSRule.Noleavetrap");
		case script_nonconsthbufc:
			return Messages.getString("CSRule.Nonconsthbufc");
		case script_nonconsttdesc:
			return Messages.getString("CSRule.Nonconsttdesc");
		case script_nonleavenew:
			return Messages.getString("CSRule.Nonleavenew");
		case script_nonunicodeskins:
			return Messages.getString("CSRule.Nonunicodeskins");
		case script_null:
			return Messages.getString("CSRule.Null");
		case script_open:
			return Messages.getString("CSRule.Open");
		case script_pointertoarrays:
			return Messages.getString("CSRule.Pointertoarrays");
		case script_pragmadisable:
			return Messages.getString("CSRule.Pragmadisable");
		case script_pragmamessage:
			return Messages.getString("CSRule.Pragmamessage");
		case script_pragmaother:
			return Messages.getString("CSRule.Pragmaother");
		case script_privateinheritance:
			return Messages.getString("CSRule.Privateinheritance");
		case script_pushaddrvar:
			return Messages.getString("CSRule.Pushaddrvar");
		case script_pushmember:
			return Messages.getString("CSRule.Pushmember");
		case script_readresource:
			return Messages.getString("CSRule.Readresource");
		case script_resourcenotoncleanupstack:
			return Messages.getString("CSRule.Resourcenotoncleanupstack");
		case script_resourcesonheap:
			return Messages.getString("CSRule.Resourcesonheap");
		case script_returndescriptoroutofscope:
			return Messages.getString("CSRule.Returndescriptoroutofscope");
		case script_rfs:
			return Messages.getString("CSRule.Rfs");
		case script_rssnames:
			return Messages.getString("CSRule.Rssnames");
		case script_stringliterals:
			return Messages.getString("CSRule.Stringliterals");
		case script_stringsinresourcefiles:
			return Messages.getString("CSRule.Stringsinresourcefiles");
		case script_struct:
			return Messages.getString("CSRule.Struct");
		case script_tcclasses:
			return Messages.getString("CSRule.Tcclasses");
		case script_tclassdestructor:
			return Messages.getString("CSRule.Tclassdestructor");
		case script_todocomments:
			return Messages.getString("CSRule.Todocomments");
		case script_trapcleanup:
			return Messages.getString("CSRule.Trapcleanup");
		case script_trapeleave:
			return Messages.getString("CSRule.Trapeleave");
		case script_traprunl:
			return Messages.getString("CSRule.Traprunl");
		case script_trspassing:
			return Messages.getString("CSRule.Trspassing");
		case script_uids:
			return Messages.getString("CSRule.Uids");
		case script_uncompressedaif:
			return Messages.getString("CSRule.Uncompressedaif");
		case script_uncompressedbmp:
			return Messages.getString("CSRule.Uncompressedbmp");
		case script_unicodesource:
			return Messages.getString("CSRule.Unicodesource");
		case script_userafter:
			return Messages.getString("CSRule.Userafter");
		case script_userfree:
			return Messages.getString("CSRule.Userfree");
		case script_userWaitForRequest:
			return Messages.getString("CSRule.UserWaitForRequest");
		case script_variablenames:
			return Messages.getString("CSRule.Variablenames");
		case script_voidparameter:
			return Messages.getString("CSRule.Voidparameter");
		case script_worryingcomments:
			return Messages.getString("CSRule.Worryingcomments");
		default:
			return "";
		}
	}

	/**
	 * Return the script type of a rule
	 * @return script type of a rule
	 */
	public CSScript getScript() {
		return this.script;
	}

	/**
	 * Set the script type of a rule
	 * @param script - new script type
	 */
	public void setScript(CSScript script) {
		this.script = script;
	}
	
	/**
	 * Return the severity type of a rule
	 * @return severity type of a rule
	 */
	public CSSeverity getSeverity() {
		return this.severity;
	}

	/**
	 * Set the severity type of a rule
	 * @param severity - new severity type
	 */
	public void setSeverity(CSSeverity severity) {
		this.severity = severity;
	}
	
}
