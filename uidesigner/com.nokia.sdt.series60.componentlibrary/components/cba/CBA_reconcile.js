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
/**
 *	Script for reconciling the CBA property of a CBA component.
 *	Implements IReconcileProperty
 *
 */

var summaryIndex = 0;
var leftIdIndex = 1;
var rightIdIndex = 2;
var middleIdIndex = 3;

var FIXED_CBA_PREFIX = "R_AVKON_SOFTKEYS_";

var EMPTY = new Array("EMPTY", "EAknSoftkeyEmpty", "EAknSoftkeyEmpty", "EAknSoftkeyEmpty");
var EMPTY_WITH_IDS = new Array("EMPTY_WITH_IDS", "EAknSoftkeyEmpty", "EAknSoftkeyEmpty", "EAknSoftkeyEmpty");
var OK_EMPTY = new Array("OK_EMPTY", "EAknSoftkeyOk", "", "EAknSoftkeyEmpty");
var SELECT_CANCEL = new Array("SELECT_CANCEL", "EAknSoftkeySelect", "EAknSoftkeyCancel", "EAknSoftkeyEmpty");
var OK_CANCEL = new Array("OK_CANCEL", "EAknSoftkeyOk", "EAknSoftkeyCancel", "EAknSoftkeyEmpty");
var OK_DETAILS = new Array("OK_DETAILS", "EAknSoftkeyOk", "EAknSoftkeyDetails", "EAknSoftkeyEmpty");
var CALL_CANCEL = new Array("CALL_CANCEL", "EAknSoftkeyCall", "EAknSoftkeyCancel", "EAknSoftkeyEmpty");
var OPTIONS_BACK = new Array("OPTIONS_BACK", "EAknSoftkeyOptions", "EAknSoftkeyBack", "EAknSoftkeyEmpty");
var OPTIONS_DONE = new Array("OPTIONS_DONE", "EAknSoftkeyOptions", "EAknSoftkeyDone", "EAknSoftkeyEmpty");
var OPTIONS_CANCEL = new Array("OPTIONS_CANCEL", "EAknSoftkeyOptions", "EAknSoftkeyCancel", "EAknSoftkeyEmpty");
var OPTIONS_EXIT = new Array("OPTIONS_EXIT", "EAknSoftkeyOptions", "EAknSoftkeyExit", "EAknSoftkeyEmpty");
var OK_BACK = new Array("OK_BACK", "EAknSoftkeyOk", "EAknSoftkeyBack", "EAknSoftkeyEmpty");
var CANCEL = new Array("CANCEL", "", "EAknSoftkeyCancel", "EAknSoftkeyEmpty");
var BACK = new Array("BACK", "", "EAknSoftkeyBack", "EAknSoftkeyEmpty");
var CLOSE = new Array("CLOSE", "", "EAknSoftkeyClose", "EAknSoftkeyEmpty");
var DONE_BACK = new Array("DONE_BACK", "EAknSoftkeyDone", "EAknSoftkeyBack", "EAknSoftkeyEmpty");
var DONE_CANCEL = new Array("DONE_CANCEL", "EAknSoftkeyDone", "EAknSoftkeyCancel", "EAknSoftkeyEmpty");
var MARK_BACK = new Array("MARK_BACK", "EAknSoftkeyMark", "EAknSoftkeyOk", "EAknSoftkeyEmpty");
var UNMARK_BACK = new Array("UNMARK_BACK", "EAknSoftkeyUnmark", "EAknSoftkeyOk", "EAknSoftkeyEmpty");
var YES_NO = new Array("YES_NO", "EAknSoftkeyYes", "EAknSoftkeyNo", "EAknSoftkeyEmpty");
var UNLOCK_EMPTY = new Array("UNLOCK_EMPTY", "EAknSoftkeyUnlock", "", "EAknSoftkeyEmpty");
var SAVE_BACK = new Array("SAVE_BACK", "EAknSoftkeySave", "EAknSoftkeyBack", "EAknSoftkeyEmpty");
var SHOW_CANCEL = new Array("SHOW_CANCEL", "EAknSoftkeyShow", "EAknSoftkeyCancel", "EAknSoftkeyEmpty");
var SHOW_EXIT = new Array("SHOW_EXIT", "EAknSoftkeyShow", "EAknSoftkeyExit", "EAknSoftkeyEmpty");
var EXIT = new Array("EXIT", "", "EAknSoftkeyBack", "EAknSoftkeyEmpty");
var READ_EXIT = new Array("READ_EXIT", "EAknSoftkeyRead", "EAknSoftkeyExit", "EAknSoftkeyEmpty");
var LISTEN_EXIT = new Array("LISTEN_EXIT", "EAknSoftkeyListen", "EAknSoftkeyExit", "EAknSoftkeyEmpty");
var SEARCH_BACK = new Array("SEARCH_BACK", "EAknSoftkeySearch", "EAknSoftkeyBack", "EAknSoftkeyEmpty");
var AGAIN_QUIT = new Array("AGAIN_QUIT", "EAknSoftkeyAgain", "EAknSoftkeyQuit", "EAknSoftkeyEmpty");
var QUIT = new Array("QUIT", "", "EAknSoftkeyQuit", "EAknSoftkeyEmpty");
var INSERT_BACK = new Array("INSERT_BACK", "EAknSoftkeyInsert", "EAknSoftkeyBack", "EAknSoftkeyEmpty");
// Options with MSK support
var SELECT_CANCEL__SELECT = new Array("SELECT_CANCEL__SELECT", "EAknSoftkeySelect", "EAknSoftkeyCancel", "EAknSoftkeySelect");
var SELECT_BACK__SELECT = new Array("SELECT_BACK__SELECT", "EAknSoftkeySelect", "EAknSoftkeyBack", "EAknSoftkeySelect");
var OK_CANCEL__MARK = new Array("OK_CANCEL__MARK", "EAknSoftkeyOk", "EAknSoftkeyCancel", "EAknSoftkeyMark");
var OK_CANCEL__OK = new Array("OK_CANCEL__OK", "EAknSoftkeyOk", "EAknSoftkeyCancel", "EAknSoftkeyOk");
var OK_EMPTY__OK = new Array("OK_EMPTY__OK", "EAknSoftkeyOk", "EAknSoftkeyEmpty", "EAknSoftkeyOk");
var OK_CANCEL__UNMARK = new Array("OK_CANCEL__UNMARK", "EAknSoftkeyOk", "EAknSoftkeyCancel", "EAknSoftkeyUnmark");
var SEND_CANCEL__SEND = new Array("SEND_CANCEL__SEND", "EAknSoftkeySend", "EAknSoftkeyCancel", "EAknSoftkeySend");
var YES_NO__YES = new Array("YES_NO__YES", "EAknSoftkeyYes", "EAknSoftkeyNo", "EAknSoftkeyYes");
var SHOW_EXIT__SHOW = new Array("SHOW_EXIT__SHOW", "EAknSoftkeyShow", "EAknSoftkeyExit", "EAknSoftkeyShow");
var CALL_CANCEL__CALL = new Array("CALL_CANCEL__CALL", "EAknSoftkeyCall", "EAknSoftkeyCancel", "EAknSoftkeyCall");
var READ_EXIT__READ = new Array("READ_EXIT__READ", "EAknSoftkeyRead", "EAknSoftkeyExit", "EAknSoftkeyRead");
var LISTEN_EXIT__LISTEN = new Array("LISTEN_EXIT__LISTEN", "EAknSoftkeyListen", "EAknSoftkeyExit", "EAknSoftkeyListen");
var OPTIONS_BACK__SELECT = new Array("OPTIONS_BACK__SELECT", "EAknSoftkeyOptions", "EAknSoftkeyBack", "EAknSoftkeySelect");
var OPTIONS_DONE__SELECT = new Array("OPTIONS_DONE__SELECT", "EAknSoftkeyOptions", "EAknSoftkeyDone", "EAknSoftkeySelect");
var OPTIONS_CANCEL__SELECT = new Array("OPTIONS_CANCEL__SELECT", "EAknSoftkeyOptions", "EAknSoftkeyCancel", "EAknSoftkeySelect");
var OPTIONS_EXIT__SELECT = new Array("OPTIONS_EXIT__SELECT", "EAknSoftkeyOptions", "EAknSoftkeyExit", "EAknSoftkeySelect");
var OK_CANCEL__SELECT = new Array("OK_CANCEL__SELECT", "EAknSoftkeyOk", "EAknSoftkeyCancel", "EAknSoftkeySelect");
var OK_BACK__OK = new Array("OK_BACK__OK", "EAknSoftkeyOk", "EAknSoftkeyBack", "EAknSoftkeyOk");
var SELECT_CLOSE__SELECT = new Array("SELECT_CLOSE__SELECT", "EAknSoftkeySelect", "EAknSoftkeyClose", "EAknSoftkeySelect");
var NEXT_EXIT__NEXT = new Array("NEXT_EXIT__NEXT", "EAknSoftkeyNext", "EAknSoftkeyExit", "EAknSoftkeyNext");
var USSD_ANSWER_EXIT__ANSWER = new Array("USSD_ANSWER_EXIT__ANSWER", "EAknSoftkeyAnswer", "EAknSoftkeyExit", "EAknSoftkeyAnswer");
var HIDE_CANCEL__HIDE = new Array("HIDE_CANCEL__HIDE", "EAknSoftkeyHide", "EAknSoftkeyCancel", "EAknSoftkeyHide");
var OPTIONS_BACK__SELECT = new Array("OPTIONS_BACK__SELECT", "EAknSoftkeyOptions", "EAknSoftkeyBack", "EAknSoftkeySelect");

// array for S60 3.1 and prior.
var cbaTable = new Array(EMPTY, EMPTY_WITH_IDS, OK_EMPTY, SELECT_CANCEL, OK_CANCEL, OK_DETAILS, 
						CALL_CANCEL, OPTIONS_BACK, OPTIONS_DONE, OPTIONS_CANCEL, OPTIONS_EXIT, 
						OK_BACK, CANCEL, BACK, CLOSE, DONE_BACK, DONE_CANCEL, MARK_BACK, 
						UNMARK_BACK, YES_NO, UNLOCK_EMPTY, SAVE_BACK, SHOW_CANCEL, SHOW_EXIT, 
						EXIT, READ_EXIT, LISTEN_EXIT, SEARCH_BACK, AGAIN_QUIT, QUIT, INSERT_BACK);

// array for S60 3.2+ -  Middle Soft Key support added
var cbaTablePlusMSK = new Array(EMPTY, EMPTY_WITH_IDS, OK_EMPTY, SELECT_CANCEL, OK_CANCEL, OK_DETAILS, 
						CALL_CANCEL, OPTIONS_BACK, OPTIONS_DONE, OPTIONS_CANCEL, OPTIONS_EXIT, 
						OK_BACK, CANCEL, BACK, CLOSE, DONE_BACK, DONE_CANCEL, MARK_BACK, 
						UNMARK_BACK, YES_NO, UNLOCK_EMPTY, SAVE_BACK, SHOW_CANCEL, SHOW_EXIT, 
						EXIT, READ_EXIT, LISTEN_EXIT, SEARCH_BACK, AGAIN_QUIT, QUIT, INSERT_BACK, SELECT_CANCEL__SELECT,
						SELECT_BACK__SELECT, OK_CANCEL__MARK, OK_CANCEL__OK, OK_EMPTY__OK, OK_CANCEL__UNMARK, 
						SEND_CANCEL__SEND, YES_NO__YES, SHOW_EXIT__SHOW, CALL_CANCEL__CALL, READ_EXIT__READ,
						LISTEN_EXIT__LISTEN, OPTIONS_BACK__SELECT, OPTIONS_DONE__SELECT, OPTIONS_CANCEL__SELECT,
						OPTIONS_EXIT__SELECT, OK_CANCEL__SELECT, OK_BACK__OK, SELECT_CLOSE__SELECT, NEXT_EXIT__NEXT,
						USSD_ANSWER_EXIT__ANSWER, HIDE_CANCEL__HIDE, OPTIONS_BACK__SELECT );

var CUSTOM = "com.nokia.sdt.series60.CBA.Type.CUSTOM";

function findSpecifiedType(typeString) {
	for (var i = 0; i < cbaTablePlusMSK.length; i++) {
		var test = FIXED_CBA_PREFIX + cbaTablePlusMSK[i][summaryIndex];
		if (typeString == test)
			return typeString;
	}
	
	return null;
}


function CBAReconcile() {
}

CBAReconcile.prototype.createDisplayValue = function(instance, propertyTypeName, propertyValue) {
	if (!propertyTypeName.equals("com.nokia.sdt.series60.CBAProperty") && 
		!propertyTypeName.equals("com.nokia.sdt.series60.CBAProperty_3_2")) {
		return null;
	}

	var specifiedType = findSpecifiedType(propertyValue.type);
	
	if (propertyTypeName.equals("com.nokia.sdt.series60.CBAProperty_3_2")){
		for (var i = 0; i < cbaTablePlusMSK.length; i++) {
			if ( propertyValue.leftId.equals(cbaTablePlusMSK[i][leftIdIndex])     &&
				 (propertyValue.leftText.length == 0) 					   &&
				 propertyValue.rightId.equals(cbaTablePlusMSK[i][rightIdIndex])   &&
				 (propertyValue.rightText.length == 0)					   &&
				 propertyValue.middleId.equals(cbaTablePlusMSK[i][middleIdIndex]) &&
				 (propertyValue.middleText.length == 0) ) {
					// if we had stored a specified type, use it!
					if (specifiedType != null)
						return specifiedType;
					else
						return FIXED_CBA_PREFIX + cbaTablePlusMSK[i][summaryIndex];
				}
		}
	} else {
		// pre middle soft key
		for (var i = 0; i < cbaTable.length; i++) {
			if ( propertyValue.leftId.equals(cbaTable[i][leftIdIndex])     &&
				 (propertyValue.leftText.length == 0) 					   &&
				 propertyValue.rightId.equals(cbaTable[i][rightIdIndex])   &&
				 (propertyValue.rightText.length == 0) ) {
					// if we had stored a specified type, use it!
					if (specifiedType != null)
						return specifiedType;
					else
						return FIXED_CBA_PREFIX + cbaTable[i][summaryIndex];
				}
		}
	}
	
	return CUSTOM;
}

CBAReconcile.prototype.isDisplayValueEditable = function(instance, propertyTypeName) {
	if (!propertyTypeName.equals("com.nokia.sdt.series60.CBAProperty") && 
		!propertyTypeName.equals("com.nokia.sdt.series60.CBAProperty_3_2")) {
		return true;
	}
				
	return true;
}
	
CBAReconcile.prototype.applyDisplayValue = function(instance, propertyTypeName, displayValue, propertyValue) {
	if (!propertyTypeName.equals("com.nokia.sdt.series60.CBAProperty") && 
		!propertyTypeName.equals("com.nokia.sdt.series60.CBAProperty_3_2")) {
		return;
	}
	
	if (propertyTypeName.equals("com.nokia.sdt.series60.CBAProperty_3_2")){
		for (var i = 0; i < cbaTablePlusMSK.length; i++) {
			var typeString = FIXED_CBA_PREFIX + cbaTablePlusMSK[i][summaryIndex];
			if (displayValue.equals(typeString)) {
				propertyValue.leftId = cbaTablePlusMSK[i][leftIdIndex];
				propertyValue.leftText = "";
				propertyValue.rightId = cbaTablePlusMSK[i][rightIdIndex];
				propertyValue.rightText = "";
				propertyValue.middleId = cbaTablePlusMSK[i][middleIdIndex];
				propertyValue.middleText = "";
				propertyValue.type = typeString;
				return;
			}
		}
	} else {
		for (var i = 0; i < cbaTable.length; i++) {
			var typeString = FIXED_CBA_PREFIX + cbaTable[i][summaryIndex];
			if (displayValue.equals(typeString)) {
				propertyValue.leftId = cbaTable[i][leftIdIndex];
				propertyValue.leftText = "";
				propertyValue.rightId = cbaTable[i][rightIdIndex];
				propertyValue.rightText = "";
				propertyValue.type = typeString;
				return;
			}
		}
	}
	propertyValue.type = "CUSTOM";
}


