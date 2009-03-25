/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.component.symbian.test.sourcegen;

import com.nokia.cpp.internal.api.utils.core.MessageLocation;
import com.nokia.sdt.component.symbian.sourcegen.TemplateJavaScriptExpressionFormatter;
import com.nokia.sdt.sourcegen.templates.backend.ITextChunkVisitor;
import com.nokia.sdt.sourcegen.templates.backend.TextChunkBackEnd;
import com.nokia.sdt.sourcegen.templates.core.IBackEnd;
import com.nokia.sdt.sourcegen.templates.core.TemplateCompiler;
import com.nokia.sdt.sourcegen.templates.frontend.FrontEnd;
import com.nokia.sdt.sourcegen.templates.frontend.IMessageLocationFactory;

import org.eclipse.core.runtime.Path;

import junit.framework.TestCase;

public class TestJSExpressionFormatter extends TestCase {
	String run(String input) throws Exception {
        FrontEnd fe = new FrontEnd(true);
        fe.setSource(input);
        fe.setMessageLocationFactory(new IMessageLocationFactory() {
            public MessageLocation createLocation(int line, int column) {
            	MessageLocation loc = new MessageLocation(new Path("."), line, column);
                return loc;
            }
        });
        ITextChunkVisitor visitor;
        visitor = new TemplateJavaScriptExpressionFormatter();
        IBackEnd be = new TextChunkBackEnd(visitor);
        TemplateCompiler compiler = new TemplateCompiler(fe, be);
        compiler.compile();
        return visitor.toString();

	}
	public void testBasic() throws Exception {
		String ret = run("src");
		assertEquals("\"src\"", ret);
	}
	public void testVar() throws Exception {
		String ret = run("${src}");
		assertEquals("src", ret);
	}

	public void testMixed() throws Exception {
		String ret = run("${src}/file.h");
		assertEquals("src+\"/file.h\"", ret);
	}

	public void testMixed2() throws Exception {
		String ret = run("${src}/file.${ext}");
		assertEquals("src+\"/file.\"+ext", ret);
	}

}
