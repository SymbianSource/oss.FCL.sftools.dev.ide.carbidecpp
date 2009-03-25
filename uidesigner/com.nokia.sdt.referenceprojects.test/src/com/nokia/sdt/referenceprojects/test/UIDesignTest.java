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
package com.nokia.sdt.referenceprojects.test;

import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.IMessageListener;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.LoadResult;
import com.nokia.sdt.utils.*;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import org.eclipse.core.runtime.*;

import java.util.ArrayList;
import java.util.Iterator;

public class UIDesignTest extends StatefulTestCase implements ITestStateConstants {
	
	protected static final String CDT_SCANNER_PROBLEM_KEY_SUFFIX = ".CdtScannerProblem";
	protected static final String CDT_PREPROCESSOR_INCLUSION_TEXT = "Preprocessor Inclusion";
	protected static final String AST_PREPROCESSOR_PROBLEM_KEY = "ASTPreprocessor.IncludeFileNotFound";
	protected static final String AST_PREPROCESSOR_RSSI_TEXT = ".rssi";
	
	IDesignerDataModelSpecifier specifier;
	IDesignerDataModel model;
	
	StringBuffer logBuffer = new StringBuffer();
	private ILogListener loglistener = new ILogListener() {	
		public void logging(IStatus status, String plugin) {
			if (status.getMessage().contains(".series60.")) {
				logBuffer.append(status.getMessage());
				logBuffer.append("\n");
			}
		}
	};
	
	final boolean EMIT_MESSAGES = true;
    ArrayList<IMessage> messages = new ArrayList<IMessage>();
    IMessageListener messageListener = new IMessageListener() {

		public boolean isHandlingMessage(IMessage msg) {
			return true;
		}
        public void emitMessage(IMessage msg) {
        	// Ignore CDT "preprocessor inclusion not found" messages,
        	// which arise after the root model is saved when loading
        	// the view model whose sources have not yet been saved.
        	if ((msg.getMessageKey().contains(CDT_SCANNER_PROBLEM_KEY_SUFFIX)
        			&& msg.getMessage().contains(CDT_PREPROCESSOR_INCLUSION_TEXT)) ||
        			(msg.getMessageKey().equals(AST_PREPROCESSOR_PROBLEM_KEY)
        			&& msg.getMessage().contains(AST_PREPROCESSOR_RSSI_TEXT)))
        		return;
            if (EMIT_MESSAGES) {
                System.err.println(msg);
            }
            messages.add(msg);
        }
    };

	
	protected void setUp() throws Exception {
		Logging.addListener(loglistener);
		MessageReporting.addListener(messageListener);
		
		specifier = (IDesignerDataModelSpecifier) getState(MODEL_SPECIFIER);
		LoadResult result = specifier.load();
		assertNotNull(result.getModel());
		model = result.getModel();
	}

	protected void tearDown() throws Exception {
		if (model != null) {
			model.dispose();
		}
		Logging.removeListener(loglistener);
		MessageReporting.removeListener(messageListener);
	}
	
	/**
     * Assert that no messages were emitted
     */
    protected void checkNoMessages() {
        
        StringBuffer buf = new StringBuffer();
        for (Iterator iter = messages.iterator(); iter.hasNext();) {
            IMessage message = (IMessage) iter.next();
            buf.append("unexpected message: " + message + "\n");
        }
        if (buf.length() > 0) {
            fail(buf.toString());
        }
    }
    
    protected void checkEmptyLog() {
    	assertTrue(logBuffer.toString(), logBuffer.length() == 0);
    }
	
	public void testSave() throws Exception {
		model.saveModel(new NullProgressMonitor());
		checkEmptyLog();
		checkNoMessages();
	}
}
