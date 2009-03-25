/**
 *	Script for reconciling the CBA property of a CBA component.
 *	Implements IReconcileProperty
 *
 */

var summaryIndex = 0;
var leftIdIndex = 1;
var leftTextIndex = 2;
var rightIdIndex = 3;
var rightTextIndex = 4;

var text_softkey_option = lookupString("text_softkey_option");
var text_softkey_select = lookupString("text_softkey_select");
var text_softkey_ok = lookupString("text_softkey_ok");
var text_softkey_done = lookupString("text_softkey_done");
var text_softkey_call = lookupString("text_softkey_call");
var text_softkey_yes = lookupString("text_softkey_yes");
var text_softkey_no = lookupString("text_softkey_no");
var text_softkey_cancel = lookupString("text_softkey_cancel");
var text_softkey_exit = lookupString("text_softkey_exit");
var text_softkey_quit = lookupString("text_softkey_quit");
var text_softkey_back = lookupString("text_softkey_back");
var text_softkey_insert_char = lookupString("text_softkey_insert_char");
var text_softkey_close = lookupString("text_softkey_close");
var text_softkey_empty = "";
var text_softkey_save = lookupString("text_softkey_save");
var text_softkey_show = lookupString("text_softkey_show");
var text_softkey_search = lookupString("text_softkey_search");
var text_softkey_read = lookupString("text_softkey_read");
var text_softkey_listen = lookupString("text_softkey_listen");
var text_softkey_answer = lookupString("text_softkey_answer");
var text_softkey_again = lookupString("text_softkey_again");
var text_softkey_unlock = lookupString("text_softkey_unlock");
var text_softkey_mark = lookupString("text_softkey_mark");
var text_softkey_unmark = lookupString("text_softkey_unmark");
var text_softkey_copy = lookupString("text_softkey_copy");
var text_softkey_paste = lookupString("text_softkey_paste");
var text_softkey_details = lookupString("text_softkey_details");
var text_softkey_stop = lookupString("text_softkey_stop");
var text_softkey_pause = lookupString("text_softkey_pause");
var text_softkey_accept = lookupString("text_softkey_accept");

var FIXED_CBA_PREFIX = "r_avkon_softkeys_";

var EMPTY = new Array("empty", "EAknSoftkeyEmpty", text_softkey_empty, "EAknSoftkeyEmpty", text_softkey_empty);
var EMPTY_WITH_IDS = new Array("empty_with_ids", "EAknSoftkeyEmpty", text_softkey_empty, "EAknSoftkeyEmpty", text_softkey_empty);
var OK_EMPTY = new Array("ok_empty", "EAknSoftkeyOk", text_softkey_ok, "", text_softkey_empty);
var SELECT_CANCEL = new Array("select_cancel", "EAknSoftkeySelect", text_softkey_select, "EAknSoftkeyCancel", text_softkey_cancel);
var OK_CANCEL = new Array("ok_cancel", "EAknSoftkeyOk", text_softkey_ok, "EAknSoftkeyCancel", text_softkey_cancel);
var OK_DETAILS = new Array("ok_details", "EAknSoftkeyOk", text_softkey_ok, "EAknSoftkeyDetails", text_softkey_details);
var CALL_CANCEL = new Array("call_cancel", "EAknSoftkeyCall", text_softkey_call, "EAknSoftkeyCancel", text_softkey_cancel);
var OPTIONS_BACK = new Array("options_back", "EAknSoftkeyOptions", text_softkey_option, "EAknSoftkeyBack", text_softkey_back);
var OPTIONS_DONE = new Array("options_done", "EAknSoftkeyOptions", text_softkey_option, "EAknSoftkeyDone", text_softkey_done);
var OPTIONS_CANCEL = new Array("options_cancel", "EAknSoftkeyOptions", text_softkey_option, "EAknSoftkeyCancel", text_softkey_cancel);
var OPTIONS_EXIT = new Array("options_exit", "EAknSoftkeyOptions", text_softkey_option, "EAknSoftkeyExit", text_softkey_exit);
var OK_BACK = new Array("ok_back", "EAknSoftkeyOk", text_softkey_ok, "EAknSoftkeyBack", text_softkey_back);
var CANCEL = new Array("cancel", "", text_softkey_empty, " EAknSoftkeyCancel", text_softkey_cancel);
var BACK = new Array("back", "", text_softkey_empty, " EAknSoftkeyBack", text_softkey_back);
var CLOSE = new Array("close", "", text_softkey_empty, "EAknSoftkeyClose", text_softkey_close);
var DONE_BACK = new Array("done_back", "EAknSoftkeyDone", text_softkey_done, "EAknSoftkeyBack", text_softkey_back);
var DONE_CANCEL = new Array("done_cancel", "EAknSoftkeyDone", text_softkey_done, "EAknSoftkeyCancel", text_softkey_cancel);
var SELECT_BACK = new Array("select_back", "EAknSoftkeySelect", text_softkey_select, "EEikBidCancel", text_softkey_back);
var MARK_BACK = new Array("mark_back", "EAknSoftkeyMark", text_softkey_mark, "EAknSoftkeyOk", text_softkey_back);
var UNMARK_BACK = new Array("unmark_back", "EAknSoftkeyUnmark", text_softkey_unmark, "EAknSoftkeyOk", text_softkey_back);
var YES_NO = new Array("yes_no", "EAknSoftkeyYes", text_softkey_yes, "EAknSoftkeyNo", text_softkey_no);
var UNLOCK_EMPTY = new Array("unlock_empty", "EAknSoftkeyUnlock", text_softkey_unlock, "", text_softkey_empty);
var SAVE_BACK = new Array("save_back", "EAknSoftkeySave", text_softkey_save, "EAknSoftkeyBack", text_softkey_back);
var SHOW_CANCEL = new Array("show_cancel", "EAknSoftkeyShow", text_softkey_show, "EAknSoftkeyCancel", text_softkey_cancel);
var SHOW_EXIT = new Array("show_exit", "EAknSoftkeyShow", text_softkey_show, "EAknSoftkeyExit", text_softkey_exit);
var ANSWER_EXIT = new Array("answer_exit", "EAknSoftkeyShow", text_softkey_answer, "EAknSoftkeyExit", text_softkey_exit);
var EXIT = new Array("exit", "", text_softkey_empty, "EAknSoftkeyBack", text_softkey_exit);
var READ_EXIT = new Array("read_exit", "EAknSoftkeyRead", text_softkey_read, "EAknSoftkeyExit", text_softkey_exit);
var LISTEN_EXIT = new Array("listen_exit", "EAknSoftkeyListen", text_softkey_listen, "EAknSoftkeyExit", text_softkey_exit);
var SEARCH_BACK = new Array("search_back", "EAknSoftkeySearch", text_softkey_search, "EAknSoftkeyBack", text_softkey_back);
var AGAIN_QUIT = new Array("again_quit", "EAknSoftkeyAgain", text_softkey_again, "EAknSoftkeyQuit", text_softkey_quit);
var QUIT = new Array("quit", "", text_softkey_empty, "EAknSoftkeyQuit", text_softkey_quit);
var INSERT_BACK = new Array("insert_back", "EAknSoftkeyInsert", text_softkey_insert_char, "EAknSoftkeyBack", text_softkey_back);

var cbaTable = new Array(EMPTY, EMPTY_WITH_IDS, OK_EMPTY, SELECT_CANCEL, OK_CANCEL, OK_DETAILS, CALL_CANCEL, OPTIONS_BACK, OPTIONS_DONE, OPTIONS_CANCEL, OPTIONS_EXIT, OK_BACK, CANCEL, BACK, CLOSE, DONE_BACK, DONE_CANCEL, SELECT_BACK, MARK_BACK, UNMARK_BACK, YES_NO, UNLOCK_EMPTY, SAVE_BACK, SHOW_CANCEL, SHOW_EXIT, ANSWER_EXIT, EXIT, READ_EXIT, LISTEN_EXIT, SEARCH_BACK, AGAIN_QUIT, QUIT, INSERT_BACK);

var CUSTOM = "com.nokia.sdt.series60.test.CBA.Type.CUSTOM";
var PROPERTY_TYPE = "com.nokia.sdt.series60.test.CBAProperty"

function CBAReconcile() {
}

CBAReconcile.prototype.createDisplayValue = function(instance, propertyTypeName, propertyValue) {
	if (!propertyTypeName.equals(PROPERTY_TYPE)) {
		return null;
	}
	
//	java.lang.System.out.println("createDisplayValue: lid(" + propertyValue.leftId + "), ltext(" + propertyValue.leftText + "), rid(" + propertyValue.rightId + "), rtext(" + propertyValue.rightText + ")");
	for (var i = 0; i < cbaTable.length; i++) {
		if (propertyValue.leftId.equals(cbaTable[i][leftIdIndex]) &&
			propertyValue.leftText.equals(cbaTable[i][leftTextIndex]) &&
			propertyValue.rightId.equals(cbaTable[i][rightIdIndex]) &&
			propertyValue.rightText.equals(cbaTable[i][rightTextIndex])) {
//				java.lang.System.out.println("createDisplayValue: found property value, returning displayValue");
				return FIXED_CBA_PREFIX + cbaTable[i][summaryIndex];
			}
	}
	
	return CUSTOM;
}

CBAReconcile.prototype.isDisplayValueEditable = function(instance, propertyTypeName) {
	if (!propertyTypeName.equals(PROPERTY_TYPE)) {
		return true;
	}
				
	return true;
}
	
CBAReconcile.prototype.applyDisplayValue = function(instance, propertyTypeName, displayValue, propertyValue) {
	if (!propertyTypeName.equals(PROPERTY_TYPE)) {
		return;
	}
		
//	java.lang.System.out.println("applyDisplayValue(" + displayValue + ")");
//	java.lang.System.out.println("applyDisplayValue: lid(" + propertyValue.leftId + "), ltext(" + propertyValue.leftText + "), rid(" + propertyValue.rightId + "), rtext(" + propertyValue.rightText + ")");
	for (var i = 0; i < cbaTable.length; i++) {
		if (displayValue.equals(FIXED_CBA_PREFIX + cbaTable[i][summaryIndex])) {
//			java.lang.System.out.println("applyDisplayValue: found display value, setting propertyValue");
			propertyValue.leftId = cbaTable[i][leftIdIndex];
			propertyValue.leftText = cbaTable[i][leftTextIndex];
			propertyValue.rightId = cbaTable[i][rightIdIndex];
			propertyValue.rightText = cbaTable[i][rightTextIndex];
			return;
		}
	}
}


