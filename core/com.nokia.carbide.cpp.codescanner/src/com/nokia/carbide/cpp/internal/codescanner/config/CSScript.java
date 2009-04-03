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

/**
 * Enumeration for all existing script types of CodeScanner rules.
 *
 */
public enum CSScript {
	script_accessArrayElementWithoutCheck("accessArrayElementWithoutCheck"),
	script_accessArrayElementWithoutCheck2("accessArrayElementWithoutCheck2"),
	script_activestart("activestart"),
	script_activestop("activestop"),
	script_arraypassing("arraypassing"),
	script_arrayptrcleanup("arrayptrcleanup"),
	script_assertdebuginvariant("assertdebuginvariant"),
	script_baddefines("baddefines"),
	script_baseconstruct("baseconstruct"),
	script_callActiveObjectWithoutCheckingOrStopping("callActiveObjectWithoutCheckingOrStopping"),
	script_changenotification("changenotification"),
	script_cleanup("cleanup"),
	script_commentcode("commentcode"),
	script_connect("connect"),
	script_ConnectAndDontCloseMemberVariable("ConnectAndDontCloseMemberVariable"),
	script_constnames("constnames"),
	script_consttdescptr("consttdescptr"),
	script_controlornull("controlornull"),
	script_ctltargettype("ctltargettype"),
	script_debugrom("debugrom"),
	script_declarename("declarename"),
	script_deleteMemberVariable("deleteMemberVariable"),
	script_destructor("destructor"),
	script_doubleSemiColon("doubleSemiColon"),
	script_driveletters("driveletters"),
	script_eikbuttons("eikbuttons"),
	script_eikonenvstatic("eikonenvstatic"),
	script_enummembers("enummembers"),
	script_enumnames("enumnames"),
	script_exportinline("exportinline"),
	script_exportpurevirtual("exportpurevirtual"),
	//script_externaldriveletters("externaldriveletters"),
	script_foff("foff"),
	script_forbiddenwords("forbiddenwords"),
	script_forgottoputptroncleanupstack("forgottoputptroncleanupstack"),
	script_friend("friend"),
	script_goto("goto"),
	script_ifassignments("ifassignments"),
	script_ifpreprocessor("ifpreprocessor"),
	script_inheritanceorder("inheritanceorder"),
	script_intleaves("intleaves"),
	script_jmp("jmp"),
	script_leave("leave"),
	script_LeaveNoError("LeaveNoError"),
	script_leavingoperators("leavingoperators"),
	script_LFunctionCantLeave("LFunctionCantLeave"),
	script_longlines("longlines"), 
	script_magicnumbers("magicnumbers"),
	script_mclassdestructor("mclassdestructor"),
	script_memberlc("memberlc"),
	script_membervariablecallld("membervariablecallld"),
	script_missingcancel("missingcancel"),
	script_missingcclass("missingcclass"),
	script_mmpsourcepath("mmpsourcepath"),
	script_multilangrsc("multilangrsc"),
	script_multipledeclarations("multipledeclarations"),
	script_multipleinheritance("multipleinheritance"),
	script_mydocs("mydocs"),
	script_namespace("namespace"),
	script_newlreferences("newlreferences"),
	script_noleavetrap("noleavetrap"),
	script_nonconsthbufc("nonconsthbufc"),
	script_nonconsttdesc("nonconsttdesc"),
	script_nonleavenew("nonleavenew"),
	script_nonunicodeskins("nonunicodeskins"),
	script_null("null"),
	script_open("open"),
	script_pointertoarrays("pointertoarrays"),
	script_pragmadisable("pragmadisable"),
	script_pragmamessage("pragmamessage"),
	script_pragmaother("pragmaother"),
	script_privateinheritance("privateinheritance"),
	script_pushaddrvar("pushaddrvar"),
	script_pushmember("pushmember"),
	script_readresource("readresource"),
	script_resourcenotoncleanupstack("resourcenotoncleanupstack"),
	script_resourcesonheap("resourcesonheap"),
	script_returndescriptoroutofscope("returndescriptoroutofscope"),
	script_rfs("rfs"),
	script_rssnames("rssnames"),
	script_stringliterals("stringliterals"),
	script_stringsinresourcefiles("stringsinresourcefiles"),
	script_struct("struct"),
	script_tcclasses("tcclasses"),
	script_tclassdestructor("tclassdestructor"),
	script_todocomments("todocomments"),
	script_trapcleanup("trapcleanup"),
	script_trapeleave("trapeleave"),
	script_traprunl("traprunl"),
	script_trspassing("trspassing"),
	script_uids("uids"),
	script_uncompressedaif("uncompressedaif"),
	script_uncompressedbmp("uncompressedbmp"),
	script_unicodesource("unicodesource"),
	script_userafter("userafter"),
	script_userfree("userfree"),
	script_userWaitForRequest("userWaitForRequest"),
	script_variablenames("variablenames"),
	script_voidparameter("voidparameter"),
	script_worryingcomments("worryingcomments"),
	script_unknown("unknown");
	
	private String name;
	
	/**
	 * Constructor
	 */
	CSScript(String str) {
		name = str;
	}
	
	/**
	 * Return the name of a CSSCript enum constant.
	 */
	public String toString() {
		return name;
	}

	/**
	 * Return the CSScript enum constant with the specified name.
	 * @param name - name of the constant to return
	 * @return the CSScript enum constant with the specified name
	 */
	public static CSScript toScript(String name) {
        try {
            return valueOf(name);
        } 
        catch (Exception e) {
            return script_unknown;
        }
	}

}
