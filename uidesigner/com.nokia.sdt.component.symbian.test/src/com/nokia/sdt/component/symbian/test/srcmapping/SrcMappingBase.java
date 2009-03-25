/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.component.symbian.test.srcmapping;

import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.IMessageListener;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.Message;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.component.symbian.sourcemapping.*;
import com.nokia.sdt.component.symbian.test.PluginTest;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.emf.component.MappingResourceType;
import com.nokia.sdt.emf.component.TwoWayMappingType;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.sourcegen.*;
import com.nokia.sdt.sourcegen.core.*;
import com.nokia.sdt.sourcegen.core.ResourceTracker.ResourceInfo;
import com.nokia.sdt.sourcegen.doms.rss.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorDefineDirective;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IMacro;
import com.nokia.sdt.sourcegen.doms.rss.parser.RssParser;
import com.nokia.sdt.sourcegen.provider.SourceGenProvider;
import com.nokia.sdt.sourcegen.provider.SourceGenSession;
import com.nokia.sdt.sourcegen.tests.*;
import com.nokia.sdt.symbian.ISymbianSourceGenSession;
import com.nokia.sdt.symbian.SymbianSourceFormatter;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.sdt.testsupport.*;
import com.nokia.sdt.utils.*;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import org.eclipse.core.runtime.*;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

/**
 * Base routines and helpers for source mapping tests
 * 
 * 
 *
 */
public abstract class SrcMappingBase extends TestCase {

    /** Turn this on when #checkRefFile() should write reference files anew */
    static boolean CREATE_FILES = false;
    /** Turn this on when #checkRefFile() should rewrite reference files when formatting changes */
    static boolean UPDATE_FILES = false;
    /** Turn this on to emit messagehandler messages to console */
    static boolean EMIT_MESSAGES = false;
    
    protected static final String BASE_DIR = "data/srcmapping/";
    protected static final String OUT_DIR = "tmp/";
    protected static final String DATA_MODEL_DEFAULT = BASE_DIR + "fixture.nxd";
    
    static protected ComponentProvider provider;
    protected IDesignerDataModel dataModel;
    protected IComponentSet set;
    protected File file;
    protected ISourceFile sf;
    protected IAstSourceFile asf;
    protected IAstRssSourceFile rss;
    protected ITranslationUnit tu;

    boolean hadErrors;
    protected IMessageListener msgHandler;
    protected IncludePathHandler includeHandler;
    protected ArrayList messages;
    private INameGenerator nameGenerator;
	protected RssProvider rssProvider;
	protected IRssModelGenerator generator;
	//private IRssRootModelProxy proxy;
	protected IRssModelManipulator manipulator;
	protected SourceGenProvider sgProvider;
	private String origDir;
	private ILogListener logHandler;
	protected IRssModelStringHandler locHandler;
	protected IRssModelStringHandler rlsHandler;
	protected IDesignerData designerData;
	protected ISourceGenSession session;
    protected IRssProjectFileManager fileManager;
    
 
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        CREATE_FILES = false;
        UPDATE_FILES = false;
        EMIT_MESSAGES = false;
        
        if (Platform.isRunning())
        	TestHelpers.setPlugin(PluginTest.getDefault());
        else {
        	origDir = System.getProperty("user.dir");
    		System.setProperty("user.dir", new File(origDir, "../com.nokia.sdt.component.symbian.test").getAbsolutePath());
        }

        // turn off any template
        SourceGenUtils.setSourceFileHeaderTemplate(null);

        if (provider == null) {
            provider = TestDataModelHelpers.findOrCreateProviderForUserComponents(BASE_DIR + "components");
            provider.inhibitPluginScan();
        }

        hadErrors = false;
        messages = new ArrayList();
        msgHandler = new IMessageListener() {

			public boolean isHandlingMessage(IMessage msg) {
				return true;
			}
            public void emitMessage(IMessage msg) {
                if (EMIT_MESSAGES)
                    System.err.println(msg);
                messages.add(msg);
                hadErrors = true;
            }
        };
        MessageReporting.addListener(msgHandler);
        
        logHandler = new ILogListener() {

			public void logging(IStatus status, String plugin) {
                if (EMIT_MESSAGES)
                    System.err.println(status.getMessage());
                messages.add(new Message(IStatus.ERROR, 
                		new MessageLocation(new Path(".")),
                		"ErrorLogMessage",
                		status.getMessage()));
                hadErrors = true;
			}
        };
        Logging.addListener(logHandler);
        
        includeHandler = new IncludePathHandler();
        
        nameGenerator = new INameGenerator() {

            public String getApplicationName() {
                return "MyApp";
            }

            public String getViewName(EObject root) {
                return "MyContainer";
            }

            public String getProjectRelativeDirectory(IDesignerDataModel dataModel, String id) {
                return id;
            }
        };
        
        includeHandler.addSystemIncludePath(FileHelpers.projectRelativeFile(BASE_DIR + "SDK").getAbsolutePath());
        includeHandler.addUserIncludePath(new File(".").getAbsolutePath());
        includeHandler.addUserIncludePath(FileHelpers.projectRelativeFile(BASE_DIR + "user").getAbsolutePath());

        sgProvider = new SourceGenProvider();
        sgProvider.setBaseDirectory(new File(OUT_DIR));
        sgProvider.setNameGenerator(nameGenerator);
        sgProvider.setIncludeFileLocator(includeHandler);
        sgProvider.setSourceFormatter(new SymbianSourceFormatter(TestSourceFormatting.INSTANCE));
    
        fileManager = sgProvider.getFileManager();
        
        SourceGenProvider.registerNameAlgorithm("com.nokia.sdt.component.symbian.NAME_ALG_COMMANDS", 
        		NameAlgorithmCommands.class);
        SourceGenProvider.registerNameAlgorithm("com.nokia.sdt.component.symbian.NAME_ALG_CONTROL_TYPE",
        		NameAlgorithmControlType.class);
        SourceGenProvider.registerNameAlgorithm("com.nokia.sdt.component.symbian.NAME_ALG_DIALOG_LINE_ID",
        		NameAlgorithmDialogId.class);
        SourceGenProvider.registerNameAlgorithm("com.nokia.sdt.component.symbian.NAME_ALG_STATUS_PANE_ID",
        		NameAlgorithmStatusPaneId.class);
        SourceGenProvider.registerNameAlgorithm("com.nokia.sdt.component.symbian.NAME_ALG_VIEW_UID",
        		NameAlgorithmViewUid.class);
         
        SourceGenUtils.setDefaultSourceGenProvider(sgProvider);
        initWithDataModel(getDataModelPath());
        
        designerData = ((DesignerDataModel) dataModel).getDesignerData(); 
        session = ((DesignerDataModel) dataModel).getSourceGenSession();
    }
    
    /**
	 * @return
	 */
	protected String getDataModelPath() {
		return DATA_MODEL_DEFAULT;
	}

	protected void initWithDataModel(String path) throws Exception {
		fileManager.reset();
		File dmFile = FileHelpers.projectRelativeFile(path);
    	IDesignerDataModelSpecifier dmSpec = new TestDesignerDataModelSpecifier(dmFile, false, sgProvider);
        dataModel = TestDataModelHelpers.loadDataModelWithTestComponents(dmFile, dmSpec, provider, sgProvider); 
      
        set = dataModel.getComponentSet();
        
        rssProvider = new RssProvider(sgProvider);
        //IRssProjectInfo info = rssProvider.createProjectInfo(null);
        

        //proxy = rssProvider.createRootModelProxy(info,
        //		dmSpec,
        //		//dataModel, new Path(path),
        //		"output", "OUTP");
        
        locHandler = rssProvider.createStringHandler(ISymbianSourceGenSession.FORMAT_LOC);
        rlsHandler = rssProvider.createStringHandler(ISymbianSourceGenSession.FORMAT_RLS);

        //        session = SymbianModelUtils.createSourceGenSession(sgProvider, dataModel, dmSpec);
        //dataModel.setSourceGenSession(session);
        session = dataModel.getSourceGenSession();
        
        generator = (IRssModelGenerator) ((SourceGenSession)session).getAdapter(IRssModelGenerator.class);
        //generator = rssProvider.createModelGenerator(session, proxy);
        generator.getModelManipulator().getResourceHandler().setGeneratingProjectUniqueResources(false);
        
        manipulator = generator.getModelManipulator();

        sf = new SourceFile(new File("output.rss"));
        fileManager.registerSourceFile(sf);
        asf = rss = new AstRssSourceFile(sf);
        tu = new TranslationUnit(asf);
        generator.setRssFileForTesting(rss);
        //tu = new TranslationUnit(asf);
        assertEquals(tu, asf.getTranslationUnit());

        /*asf = rss = generator.getRssFile();
        assertNotNull(rss);
        sf = asf.getSourceFile();
        assertNotNull(sf);*/
        file = sf.getFile();
        assertNotNull(file);
        
    }
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        file.delete();
        MessageReporting.removeListener(msgHandler);
        Logging.removeListener(logHandler);
        if (!Platform.isRunning())
        	System.setProperty("user.dir", origDir);
        SourceGenUtils.setDefaultSourceGenProvider(null);

    }

    IComponentSourceMapping getSourceMapping(EObject object) {
        return getSourceMapping(AdapterHelpers.getComponent(object));
    }

    IComponentSourceMapping getSourceMapping(IComponent component) {
        assertNotNull(component);
        IComponentSourceMapping csm = (IComponentSourceMapping) component
                .getAdapter(IComponentSourceMapping.class);
        return csm;
    }
    
    IComponentSourceMapping getSourceMapping(String componentId) {
    	IComponent component = set.lookupComponent(componentId);
        assertNotNull(component);
        IComponentSourceMapping csm = (IComponentSourceMapping) component
                .getAdapter(IComponentSourceMapping.class);
        return csm;
    }


    public IComponentInstance getInstance(String name) {
        EObject object = dataModel.findByNameProperty(name);
        if (object == null)
            return null;
        return AdapterHelpers.getComponentInstance(object);
    }

    public IPropertySource getProperties(IComponentInstance instance) {
        return ModelUtils.getPropertySource(instance.getEObject());
    }

    protected void checkLiteral(IAstExpression expr, int litType, String src) {
        assertTrue(expr instanceof IAstLiteralExpression);
        assertEquals(litType, ((IAstLiteralExpression)expr).getKind());
        assertEquals(src, expr.getCurrentText(sgProvider.getSourceFormatter()));
    }
    
    /**
     * Make sure the given member is initialized with the given text.
     */
    protected void checkMemberInit(IAstResource def, String memberName, int litType, String src) {
        IAstMemberInitializer init = def.findMemberInitializer(memberName);
        if (init == null)
            fail("cannot find initializer for member "+memberName);

        checkLiteral(init.getInitializerExpression().getExpression(), litType, src);
    }

    /**
     * Make sure the given member is not initialized
     */
    protected void checkNoMemberInit(IAstResource def, String memberName) {
        IAstMemberInitializer init = def.findMemberInitializer(memberName);
        if (init != null)
            fail("found unexpected initializer for " + memberName + ": " + 
                    init.getInitializerExpression().getExpression().getCurrentText(
                    		sgProvider.getSourceFormatter()));
    }

    /**
     * Make sure the expression implements the given class and has the
     * given text
     */
    protected void checkInit(IAstExpression expr, Class klass, String src) {
        if (!klass.isInstance(expr))
            fail("initializer is not of class " + klass + " but " + expr.getClass() + ": " + expr.getCurrentText(sgProvider.getSourceFormatter()));
        assertEquals(src, expr.getCurrentText(sgProvider.getSourceFormatter()));
    }
    
    /**
     * Make sure the given member is initialized and its expression
     * implements the given class.
     */
    protected void checkMemberInit(IAstResource def, String memberName, Class klass, String src) {
        IAstMemberInitializer init = def.findMemberInitializer(memberName);
        if (init == null)
            fail("cannot find initializer for member "+memberName);

        IAstExpression expr = init.getInitializerExpression().getExpression();
        checkInit(expr, klass, src);
        
    }
    
    protected IAstResource getResourceMember(IAstResource def, String memberName, String structName) {
        IAstMemberInitializer init = def.findMemberInitializer(memberName);
        if (init == null)
            fail("cannot find initializer for member "+memberName);

        IAstExpression expr = init.getInitializerExpression().getExpression();
        assertTrue(expr instanceof IAstResourceExpression);
        return (IAstResourceExpression) expr;
    }

    /**
     * Make sure the given struct is initialized with the given type.
     */
    protected void checkResourceExpr(IAstExpression expr, String structType) {
        
        assertTrue(expr instanceof IAstResourceExpression);
        IAstResourceExpression rexpr = (IAstResourceExpression) expr;
        
        assertEquals(structType, rexpr.getStructType().getStructName().getName());
    }

    /**
     * Make sure the given struct member is initialized with the given type.
     */
    protected void checkStructMemberInit(IAstMemberInitializer init, String structType) {
        checkResourceExpr(init.getInitializerExpression().getExpression(),
                structType);
    }

    /**
     * Make sure the given struct member is initialized with the given text.
     */
    protected void checkResourceExprMemberInit(IAstExpression expr, String memberName, int litType, String src) {
        
        assertTrue(expr instanceof IAstResourceExpression);
        IAstResourceExpression rexpr = (IAstResourceExpression) expr;
        
        IAstMemberInitializer memberInit = rexpr.findMemberInitializer(memberName);
        if (memberInit == null)
            fail("did not find initializer for " + memberName);
        
        expr = memberInit.getInitializerExpression().getExpression();
        assertTrue(expr instanceof IAstLiteralExpression);
        assertEquals(litType, ((IAstLiteralExpression)expr).getKind());
        assertEquals(src, expr.getCurrentText(sgProvider.getSourceFormatter()));
        
    }
    /**
     * Make sure the given struct member is initialized with the given text.
     */
    protected void checkResourceExprMemberInit(IAstExpression expr, String memberName, Class klazz, String src) {
        
        assertTrue(expr instanceof IAstResourceExpression);
        IAstResourceExpression rexpr = (IAstResourceExpression) expr;
        
        IAstMemberInitializer memberInit = rexpr.findMemberInitializer(memberName);
        if (memberInit == null)
            fail("did not find initializer for " + memberName);
        
        expr = memberInit.getInitializerExpression().getExpression();
        assertTrue(klazz.isInstance( expr));
        assertEquals(src, expr.getCurrentText(sgProvider.getSourceFormatter()));
        
    }

    /**
     * Make sure the given struct member is not initialized
     */
    protected void checkResourceExprNoMemberInit(IAstExpression expr, String memberName) {
        
        assertTrue(expr instanceof IAstResourceExpression);
        IAstResourceExpression rexpr = (IAstResourceExpression) expr;
        
        IAstMemberInitializer memberInit = rexpr.findMemberInitializer(memberName);
        if (memberInit != null)
            fail("found unexpected initializer for " + memberName + ": " + memberInit.getCurrentText(sgProvider.getSourceFormatter()));
    }
    
    /**
     * Make sure the given struct member is initialized with the given text.
     */
    protected void checkStructMemberInit(IAstMemberInitializer init, String memberName, int litType, String src) {

        checkResourceExprMemberInit(init.getInitializerExpression().getExpression(),
                memberName, litType, src);
    }

    /**
     * @param init
     */
    protected void checkStructMemberInit(IAstMemberInitializer init, String memberName, Class klazz, String src) {
        checkResourceExprMemberInit(init.getInitializerExpression().getExpression(),
                memberName, klazz, src);
        
    }

    /**
     * Make sure the given struct member is not initialized
     */
    protected void checkStructMemberNoInit(IAstMemberInitializer init, String memberName) {
        
        IAstExpression expr = init.getInitializerExpression().getExpression();
        assertTrue(expr instanceof IAstResourceExpression);
        IAstResourceExpression rexpr = (IAstResourceExpression) expr;
        
        IAstMemberInitializer memberInit = rexpr.findMemberInitializer(memberName);
        if (memberInit != null)
            fail("found unexpected initializer for " + memberName + ": " + init.getCurrentText(sgProvider.getSourceFormatter()));
    }

    /**
     * @param files
     */
    protected void writeFiles(List files) throws Exception {
        for (Iterator iter = files.iterator(); iter.hasNext();) {
            IAstSourceFile afile = (IAstSourceFile) iter.next();
            PrintStream ps = new PrintStream(new FileOutputStream(afile.getSourceFile().getFile()));
            ps.print(afile.getSourceFile().getText());
            ps.close();
        }
    }

    /**
     * Assert that the given message key was emitted
     * @param key
     */
    protected void checkMessage(String key) {
        
        for (Iterator iter = messages.iterator(); iter.hasNext();) {
            IMessage message = (IMessage) iter.next();

            // general checks
            String text = message.getMessage();
            if (text.startsWith("!") && text.endsWith("!"))
                fail("unlocalized message: " + text);
            else {
                Pattern pattern = Pattern.compile("\\{\\d+\\}");
                Matcher matcher = pattern.matcher(text);
                if (matcher.matches())
                    fail("unresolved message arguments: " + text);
            }

            String mkey = message.getMessageKey();
            if (mkey.equals(key) || mkey.endsWith("." + key)) {
                return;
            }
        }
        fail("did not find message: " + key + " in messages");
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

    /**
     * @param filename BASE_DIR-relative reference file
     * @param file source file whose contents we want to match
     * @see #UPDATE_FILES
     * @see #CREATE_FILES
     */
    protected void checkRefFile(String filename, ISourceFile file) throws Exception {
        FileHelpers.compareAndUpdateSourceFile(BASE_DIR + filename, new String(file.getText()), UPDATE_FILES, CREATE_FILES);
    }

    protected void checkArrayInit(IAstMemberInitializer init) {
        IAstExpression expr = init.getInitializerExpression().getExpression();
        assertTrue(expr instanceof IAstExpressionList);
    }
    
    protected void exportInstance(String componentId, String instanceId) {
        IComponent base = set.lookupComponent(componentId);
        assertNotNull(base);
        IComponentSourceMapping sourcemapping = getSourceMapping(base);
        assertNotNull(sourcemapping);
        
        IComponentInstance instance = getInstance(instanceId);
        assertNotNull(instance);
        
        // do the export
        sourcemapping.exportInstance(generator, instance);
    }

    protected void exportInstance(String instanceId) {
    	IComponentInstance instance = getInstance(instanceId);
    	assertNotNull(instance);
        IComponent base = instance.getComponent();
        assertNotNull(base);
        IComponentSourceMapping sourcemapping = getSourceMapping(base);
        assertNotNull(sourcemapping);
        
        // do the export
        sourcemapping.exportInstance(generator, instance);
    }

    protected void exportInstance(IComponentInstance instance) {
        IComponent base = instance.getComponent();
        assertNotNull(base);
        IComponentSourceMapping sourcemapping = getSourceMapping(base);
        assertNotNull(sourcemapping);
        
        // do the export
        sourcemapping.exportInstance(generator, instance);
    }
    
    /**
     * Update the translation unit and rss file with contents
     * from an existing file.
     * @param filepath
     */
    protected void parseRss(String filepath) throws Exception {
        file = FileHelpers.projectRelativeFile(BASE_DIR + filepath);
        sf = new SourceFile(file);
        RssParser parser = new RssParser(fileManager, manipulator.getIncludeHandler());
        tu = parser.parseTu(sf);
        checkNoMessages();
        SourceGenTestHelper.parentingTest(tu.getSourceFile());
        SourceGenTestHelper.testSourceConsistency(sgProvider.getSourceFormatter(), tu.getSourceFile());
        asf = tu.getSourceFile();
        rss = (IAstRssSourceFile) asf;
    }

    /**
     * Update the translation unit and rss file with contents
     * from a reference file.  Then, rename the source file to
     * a temporary name so it won't overwrite the reference.
     * @param filepath
     */
    protected void parseRssFrom(String filepath) throws Exception {
    	sf = copyAwayFile(filepath);
        
        RssParser parser = new RssParser(fileManager, manipulator.getIncludeHandler());
        tu = parser.parseTu(sf);
        checkNoMessages();
        SourceGenTestHelper.parentingTest(tu.getSourceFile());
        SourceGenTestHelper.testSourceConsistency(sgProvider.getSourceFormatter(), tu.getSourceFile());
        asf = tu.getSourceFile();
        rss = (IAstRssSourceFile) asf;
        assertFalse(rss.isDirtyTree());
        tu.refresh();
        generator.setRssFileForTesting(rss);
    }
    
    /**
     * Copy away a file so it's not overwritten
	 * @param filepath
	 * @return
	 */
    protected ISourceFile copyAwayFile(String filepath, String outputName) throws Exception {
        File file = FileHelpers.projectRelativeFile(BASE_DIR + filepath);
        File dir = new File("c:\\temp");
        dir.mkdir();
        File copyFile = new File(dir, outputName);
        copyFile.deleteOnExit();
        sf = new SourceFile(copyFile);
        FileUtils.copyFile(file, copyFile);
		return sf;
	}

    /**
     * Copy away a file so it's not overwritten
	 * @param filepath
	 * @return
	 */
    protected ISourceFile copyAwayFile(String filepath) throws Exception {
    	return copyAwayFile(filepath, new File(filepath).getName());
	}

	/**
	 * Parse an RSS file, ensuring required modifiable includes are copied  
	 * @param string
	 * @param strings
	 */
	protected void parseRssFrom(String filepath, String[] includes) throws Exception {
		parseRssFrom(filepath, includes, new String[0]);
	}

	/**
	 * Parse an RSS file, ensuring required modifiable includes are copied,
	 * and ensuring generated files are renamed after the main file  
	 * @param filepath
	 * @param includes
	 * @param genHeaders
	 */
	protected void parseRssFrom(String filepath, String[] includes, String[] genHeaders) throws Exception {
		sgProvider.setSourceGenFlags(ISourceGenProvider.FLAG_ONE_WAY_UPDATE);
		List<ISourceFile> copiedFiles = new ArrayList<ISourceFile>();
		if (includes != null)
		for (int i = 0; i < includes.length; i++) {
			ISourceFile sf = copyAwayFile(includes[i]); 
			fileManager.registerSourceFile(sf);
	        copiedFiles.add(sf);
		}
		String outFile = new File(filepath).getName();
		int origExt = outFile.lastIndexOf('.');
		if (genHeaders != null)
			for (int i = 0; i < genHeaders.length; i++) {
			int ext = genHeaders[i].lastIndexOf('.');
			String newName = outFile.substring(0, origExt) + genHeaders[i].substring(ext);
			ISourceFile sf = copyAwayFile(genHeaders[i], newName); 
			fileManager.registerSourceFile(sf);
	        copiedFiles.add(sf);
		}
		parseRssFrom(filepath);

		// self-test to ensure the parser really found these includes
		for (Iterator iter = copiedFiles.iterator(); iter.hasNext();) {
			ISourceFile sf = (ISourceFile) iter.next();
			IAstSourceFile[] asfs = tu.findIncludedFiles(sf);
			assertTrue(asfs.length > 0);
	        SourceGenTestHelper.parentingTest(asfs[0]);
	        SourceGenTestHelper.testSourceConsistency(sgProvider.getSourceFormatter(), asfs[0]);

		}
	}


    /**
     * Intermediate means of marking resources known so they
     * will be reused on export.  The import process should do
     * something similar to this.
     * @param names
     */
    protected void setInstanceToResourceMapping(String instanceName, String rsrcName) {
        IAstResourceDefinition def = tu.findResourceDefinition(rsrcName);
        IResourceMapping mapping = DmFactory.eINSTANCE.createIResourceMapping();
        mapping.setInstanceName(instanceName);
        mapping.setRsrcId(null);
        mapping.setValue(rsrcName);
    	manipulator.getResourceTracker().registerMapping(mapping, def, dataModel.getModelSpecifier());
    }

    /**
     * Intermediate means of marking resources known so they
     * will be reused on export.  The import process should do
     * something similar to this.
     */
    protected void setInstanceToResourceMapping(String instanceName, String rsrcId, String rsrcName) {
        IAstResourceDefinition def = tu.findResourceDefinition(rsrcName);
        IResourceMapping mapping = DmFactory.eINSTANCE.createIResourceMapping();
        mapping.setInstanceName(instanceName);
        mapping.setRsrcId(rsrcId);
        mapping.setValue(rsrcName);
    	manipulator.getResourceTracker().registerMapping(mapping, def, dataModel.getModelSpecifier());
    }

	/**
	 * @param mr
	 * @param structType
	 * @return
	 */
    MappingResourceType scanForResourceType(TwoWayMappingType typ, String structType) {
		if (typ instanceof MappingResourceType
				&& ((MappingResourceType) typ).getStruct() != null
				&& ((MappingResourceType) typ).getStruct().equals(structType))
			return (MappingResourceType) typ;
		for (Iterator iter = typ.eContents().iterator(); iter.hasNext();) {
			EObject obj = (EObject) iter.next();
			if (obj instanceof TwoWayMappingType) {
				MappingResourceType sub = scanForResourceType((TwoWayMappingType) obj, structType);
				if (sub != null)
					return sub;
			}
		}
		return null;
	}

	/**
	 * Using a particular mapResource... element of an instance,
	 * map an instance to a resource
	 * @param controllingInstance
	 * @param index
	 * @param instance
	 * @param rsrc
	 */
	protected void setInstanceToResourceMapping(IComponentInstance controllingInstance, int index, IComponentInstance instance, IAstResourceDefinition rsrc) {
        MappingResourceType mr = SourceMappingAdapterXML.getPrimaryMapResourceType(controllingInstance.getComponent());
        mr = scanForResourceType(mr, index);
        assertNotNull(mr);

        IResourceMapping mapping = DmFactory.eINSTANCE.createIResourceMapping();
        mapping.setInstanceName(instance.getName());
        mapping.setRsrcId(mr.getId());
        mapping.setValue(rsrc.getName().getName());
    	manipulator.getResourceTracker().registerMapping(mapping, rsrc, dataModel.getModelSpecifier());
	}

	/**
	 * @param mr
	 * @param index
	 * @return
	 */
	private MappingResourceType scanForResourceType(TwoWayMappingType typ, int index) {
		if (typ instanceof MappingResourceType) {
			if (index == 0)
				return (MappingResourceType) typ;
			index--;
		}
		for (Iterator iter = typ.eContents().iterator(); iter.hasNext();) {
			EObject obj = (EObject) iter.next();
			if (obj instanceof TwoWayMappingType) {
				MappingResourceType sub = scanForResourceType((TwoWayMappingType) obj, index);
				if (sub != null)
					return sub;
			}
		}
		return null;
	}


	public List rewriteTu(ITranslationUnit tu) {
     	return tu.rewrite(sgProvider.getSourceFormatter());
    }
    
	/**
	 * @param buffer
	 * @param lxxFileEn
	 * @param statement
	 * @param string
	 */
    protected void addAndInsert(StringBuffer buffer, IAstSourceFile file, boolean doInsert, IAstNode node, String string, String head, String tail) {
		int extStart = buffer.length();
		if (head != null)
			buffer.append(head);
		int start = buffer.length();
		buffer.append(string);
		if (tail == null)
			buffer.append('\n');
		else
			buffer.append(tail);
		int end = buffer.length();
		int extEnd = buffer.length();
		node.setSourceRange(new SourceRange(file.getSourceFile(), start, end - start));
		node.setExtendedRange(new SourceRange(file.getSourceFile(), extStart, extEnd - extStart));
		if (doInsert)
			file.addFileNode(node);
		node.setDirtyTree(false);
	}

    protected void add(StringBuffer buffer, IAstSourceFile file, IAstNode node, String string, String head, String tail) {
    	addAndInsert(buffer, file, true, node, string, head, tail);
    }
    
	/**
	 * @param buffer
	 * @param string
	 */
	protected void add(StringBuffer buffer, String string) {
		buffer.append(string);
	}

	/**
	 * Define a macro
	 */
	protected void define(StringBuffer buffer, IAstSourceFile file, String name, String value, String head, String tail) {
		IMacro macro = new ObjectStyleMacro(name, value);
		IAstPreprocessorDefineDirective def = new AstPreprocessorDefineDirective(macro);

		String prefix = "#define " + name + " ";
		add(buffer, file, def, prefix + value, head, tail);
		
		ISourceRange range = def.getSourceRange();
		int length = range.getLength() - prefix.length();
		if (tail != null)
			length -= tail.length();
		else
			length--; // newline
		def.getMacroValue().setSourceRange(new SourceRange(
				range.getFile(), range.getOffset() + prefix.length(),
				length));
	}
    
	/**
	 * @param buffer
	 * @param rss
	 * @param rlsFileEn
	 */
	protected void addRlsInclude(StringBuffer buffer, IAstRssSourceFile rss, IAstRlsSourceFile rlsFile) {
		String macro;
		if (rlsFile.getLanguageCode() < 10)
			macro = "0" + rlsFile.getLanguageCode();
		else
			macro = "" + rlsFile.getLanguageCode();
		macro = "LANGUAGE_" + macro;
		
    	add(buffer, rss, 
    			new AstPreprocessorRlsIncludeNode(rlsFile.getSourceFile().getFileName(), true, rlsFile, rlsFile.getLanguageCode()),
    			"#ifdef " + macro + "\n" +
    			"#include \"" + rlsFile.getSourceFile().getFileName() + "\"\n" +
    			"#endif", null, null);
	}

	protected void addRlsString(StringBuffer buffer, IAstRlsSourceFile rlsFile, String name, String value, String head, String tail) {
    	IAstName nameNode;
    	IAstLiteralExpression exprNode;
    	int extStart = buffer.length();
    	if (head != null)
    		buffer.append(head);
    	int start = buffer.length();
    	add(buffer, "rls_string ");
    	addAndInsert(buffer, rlsFile, false, (nameNode = new AstName(name)), name, null, "");
    	add(buffer, " ");
    	addAndInsert(buffer, rlsFile, false, (exprNode = new AstLiteralExpression(IAstLiteralExpression.K_STRING, value)), 
    			"\"" + value + "\"", null, "");
    	if (tail == null)
    		add(buffer, "\n");
    	int end = buffer.length();
    	if (tail != null)
    		buffer.append(tail);
    	int extEnd = buffer.length();

    	IAstRlsString node = new AstRlsString(nameNode, exprNode);
		node.setSourceRange(new SourceRange(rlsFile.getSourceFile(), start, end - start));
		node.setExtendedRange(new SourceRange(rlsFile.getSourceFile(), extStart, extEnd - extStart));
		rlsFile.addFileNode(node);
		node.setDirtyTree(false);
	}
	
	/**
	 * Delete an instance and update its source
	 * @param instance
	 */
	protected void removeInstance(IComponentInstance instance) {
		assertNotNull(instance);
		
		List<EObject> list = new ArrayList<EObject>();
		list.add(instance.getEObject());
		Command c = dataModel.createRemoveComponentInstancesCommand(list);
		assertTrue(c.canExecute());
		c.execute();
		
		//generator.removeResourcesForInstance(instance.getName());
	}


	public String getGeneratedResourceName(IComponentInstance instance) {
		ResourceInfo info = manipulator.getResourceTracker().findResourceInfo(
                instance, null);
		assertNotNull(info);
		return info.getName(); 
	}
	
	public IComponentInstance createAndAddInstance(IComponentInstance parent, String componentId, String name, int pos) {
		EObject obj = dataModel.createNewComponentInstance(
				dataModel.getComponentSet().lookupComponent(componentId));
		Command cmd = dataModel.createAddNewComponentInstanceCommand(parent.getEObject(), obj, pos);
		assertTrue(cmd.canExecute());
		cmd.execute();
		IPropertySource ps = ModelUtils.getPropertySource(obj);
		ps.setPropertyValue("name", name);
		return ModelUtils.getComponentInstance(obj);
	}
	
	public void setProperty(IComponentInstance instance, String propertyName, String propertyValue) {
		ModelUtils.getPropertySource(instance.getEObject()).setPropertyValue(propertyName, propertyValue);
	}
	
	protected void setEnumMapping(String instanceName, String propertyId, String nameAlg, String value) {
		IEnumMapping mapping = DmFactory.eINSTANCE.createIEnumMapping();
		mapping.setInstanceName(instanceName);
		mapping.setPropertyId(propertyId.equals(".") ? "" : propertyId);
		mapping.setNameAlgorithm(nameAlg);
		mapping.setValue(value);
		IAstEnumerator enm = tu.findEnumerator(value);

		manipulator.getTypeHandler().registerEnumMapping(mapping, enm);
	}
	
	/**
	 * @param arrayInstance
	 * @param arrayPropertyId
	 * @param arrayMemberId
	 * @param uniqueValue
	 * @param elementInstance
	 */
	protected void setArrayElementMapping(IComponentInstance arrayInstance, String arrayPropertyId, String arrayMemberId, String uniqueValue, String elementInstance) {
		//manipulator.getTypeHandler().registerArrayElement(arrayInstance, arrayPropertyId, arrayMemberId, uniqueValue, elementInstance);

		IArrayMapping arrayMapping = DmFactory.eINSTANCE.createIArrayMapping();
		arrayMapping.setInstanceName(arrayInstance.getName());
		arrayMapping.setPropertyId(arrayPropertyId);
		arrayMapping.setMemberId(arrayMemberId);
		manipulator.getTypeHandler().registerArrayMapping(arrayMapping);
		
		IElementMapping elementMapping = DmFactory.eINSTANCE.createIElementMapping();
		elementMapping.setUniqueValue(uniqueValue);
		elementMapping.setValue(elementInstance);
		manipulator.getTypeHandler().registerArrayElementMapping(arrayMapping, elementMapping);
	}

	protected void registerUserDefinedString(String name) {
		designerData.getStringBundle().getUserGeneratedStringKeys().add(name);
	}

}
