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

package com.nokia.sdt.symbian.tests;

import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.IMessageListener;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.Message;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.sourcegen.core.ISourceFile;
import com.nokia.sdt.sourcegen.doms.rss.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.IncludePathHandler;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.TranslationUnit;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorIncludeDirective;
import com.nokia.sdt.sourcegen.provider.SourceGenProvider;
import com.nokia.sdt.sourcegen.tests.TestSourceFormatting;
import com.nokia.sdt.symbian.SymbianSourceFormatter;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.sdt.testsupport.FileHelpers;
import com.nokia.sdt.testsupport.TestDataModelHelpers;
import com.nokia.sdt.utils.*;

import org.eclipse.core.runtime.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Test that the appropriate #includes for image properties are
 * added.  This is done through a nastily-placed check for 
 * writing "bmpfile" properties to resource in the bowels
 * of InstanceSourceMapper.  We check it in this plugin because
 * we don't depend on projects in com.nokia.sdt.component.symbian.test.
 * 
 *
 */
public class TestImageIncludes extends BaseImageTests {

	private SourceGenProvider sgProvider;
	private IDesignerDataModel dataModel;
	private IRssModelGenerator generator;
	private IAstRssSourceFile rss;
	private ISourceFile sf;
	private File file;
    protected IMessageListener msgHandler;
	private ILogListener logHandler;
	private ArrayList messages;
	private IncludePathHandler includeHandler;

	protected void setUp() throws Exception {
		super.setUp();
		
	       messages = new ArrayList();
	        msgHandler = new IMessageListener() {

    			public boolean isHandlingMessage(IMessage msg) {
    				return true;
    			}
	            public void emitMessage(IMessage msg) {
	                messages.add(msg);
	            }
	        };
	        MessageReporting.addListener(msgHandler);
	        
	        logHandler = new ILogListener() {

				public void logging(IStatus status, String plugin) {
	                messages.add(new Message(IStatus.ERROR, 
	                		new MessageLocation(new Path(".")),
	                		"ErrorLogMessage",
	                		status.getMessage()));
				}
	        };
	        Logging.addListener(logHandler);

	        includeHandler = new IncludePathHandler();
	        includeHandler.addSystemIncludePath(FileHelpers.projectRelativeFile(BASE_DIR + "SDK").getAbsolutePath());
	        includeHandler.addUserIncludePath(new File(".").getAbsolutePath());
	        includeHandler.addUserIncludePath(FileHelpers.projectRelativeFile(BASE_DIR + "user").getAbsolutePath());
	        
        sgProvider = new SourceGenProvider();
        sgProvider.setSourceFormatter(new SymbianSourceFormatter(TestSourceFormatting.INSTANCE));
        sgProvider.setIncludeFileLocator(includeHandler);
        
        dataModel = TestDataModelHelpers.loadDataModelWithTestComponents(project,
                BASE_DIR + FILE_NAME, provider, sgProvider);

        generator = (IRssModelGenerator) ((DesignerDataModel)dataModel).getSourceGenSession().getAdapter(IRssModelGenerator.class);
        generator.getModelManipulator().getResourceHandler().setGeneratingProjectUniqueResources(false);

        rss = generator.getRssFile(null, true);
        assertNotNull(rss);
        new TranslationUnit(rss);
        sf = rss.getSourceFile();
        assertNotNull(sf);
        file = sf.getFile();
        assertNotNull(file);

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
        if (buf.length() > 0)
            fail(buf.toString());
    }

	public void testImageInclude() throws Exception {
    	generator.setRssFileForTesting(rss);
    	generator.generateResources(ModelUtils.getComponentInstance(
    			dataModel.findByNameProperty("test_image")));
		checkNoMessages();
		
		IAstPreprocessorIncludeDirective[] incls = rss.getIncludeFiles();
		assertEquals(2, incls.length);
		assertEquals("images.rh", incls[0].getFilename());
		assertEquals("Test.mbg", incls[1].getFilename());
	}
}
