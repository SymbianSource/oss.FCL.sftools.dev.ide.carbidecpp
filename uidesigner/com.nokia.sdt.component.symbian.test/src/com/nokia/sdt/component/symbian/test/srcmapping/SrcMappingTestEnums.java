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

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.sourcegen.INameAlgorithm;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.provider.SourceGenProvider;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.emf.ecore.EObject;

/**
 * 
 *
 */
public class SrcMappingTestEnums extends SrcMappingBase {

	   public static class NameAlgorithmTestListBoxLayoutId implements INameAlgorithm{

	    	public NameAlgorithmTestListBoxLayoutId() {
	    	}

	    	public String getEnumDeclarationName(IComponentInstance instance,
	    			String containerName, String instanceName, String propertyId) {
	    		return "TLayoutIds"; //$NON-NLS-1$
	    	}
	     
	    	public String getEnumeratorName(IComponentInstance instance,
	    			String containerName, String instanceName, String propertyId) {
	    		return "ELayout" + TextUtils.titleCase(instanceName) + "Id"; //$NON-NLS-1$
	    	}

	    	public String getInitialEnumeratorValue(IComponentInstance instance,
	    			String propertyId) {
	    		// no initial value; every value is determined by position in parent
	    		return null;
	    	}	
	    	
	    	public String getEnumeratorValue(IComponentInstance instance,
	    			String propertyId) {
	    		if (instance.getParent() == null)
	    			return null;
	    		IComponentInstance parent = ModelUtils.getComponentInstance(instance.getParent());
	    		EObject[] kids = parent.getChildren();
	    		int layoutIndex = 0;
	    		for (int index = 0; index < kids.length; index++) {
	    			if (kids[index] == instance.getEObject())
	    				return "" + layoutIndex;
	    			String componentId = ModelUtils.getComponentInstance(kids[index]).getComponentId();
	    			if (componentId.contains("Layout"))
	    				layoutIndex++;
	    		}
	    		Check.checkState(false);
	    		return null;
	    	}
	    	

	    }
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        SourceGenProvider.registerNameAlgorithm("com.nokia.examples.NAME_ALG_LISTBOXLAYOUT_ID",
        		NameAlgorithmTestListBoxLayoutId.class);
 
    }
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }


    public void testEnums() throws Exception {
    	exportInstance("com.nokia.examples.srcmapEnums", "test2");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestEnums.rss", sf);
                
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("COLORS");
        assertNotNull(def);

        // check all the simple types for proper formatting and DOM expression
        checkMemberInit(def, "aknFgColor", IAstIdExpression.class, "CAknColorGreen");
        checkMemberInit(def, "aknBgColor", IAstIdExpression.class, "CAknColorBlue");
    }

    public void testEnumsOtherHeader() throws Exception {
        // the mapEnum element has its own headers
        // disjoint from the struct headers; be sure they're
        // included so we don't get warnings
        exportInstance("com.nokia.examples.srcmapEnumsOtherHeader", "test2_oh");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestEnumsOtherHeader.rss", sf);
                
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("COLORS");
        assertNotNull(def);

        // check all the simple types for proper formatting and DOM expression
        checkMemberInit(def, "aknFgColor", IAstIdExpression.class, "CAknColorGreen");
        checkMemberInit(def, "aknBgColor", IAstIdExpression.class, "CAknColorBlue");
    }

    public void testEnumsNoInit() throws Exception {
    	exportInstance("com.nokia.examples.srcmapEnums_defaults2", "test2_dn2");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestEnumsNoInit.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("COLORS");
        assertNotNull(def);

        // nothing was initialized, and the component property defaults
        // all match the resource defaults, so nothing should be emitted
        checkNoMemberInit(def, "aknFgColor");
        checkNoMemberInit(def, "aknBgColor");
    }

    public void testEnumsNoInit2() throws Exception {
    	exportInstance("com.nokia.examples.srcmapEnums", "test2_n");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestEnumsNoInit2.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("COLORS");
        assertNotNull(def);

        // nothing was initialized, and the component property defaults
        // all match the resource defaults, but the default is not zero
        checkMemberInit(def, "aknFgColor", IAstIdExpression.class, "CAknColorRed");
        checkMemberInit(def, "aknBgColor", IAstIdExpression.class, "CAknColorRed");
    }

    public void testEnumsInitDefault() throws Exception {
    	exportInstance("com.nokia.examples.srcmapEnums_defaults", "test2_d");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestEnumsInitDefault.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("COLORS_DEFAULTS");
        assertNotNull(def);

        // all values initialized as defaults
        checkNoMemberInit(def, "aknFgColor");
        checkNoMemberInit(def, "aknBgColor");
    }
    public void testExtendedWithEnums() throws Exception {
    	// both of these have DM values (either default or explicit) which
    	// should be mapped correctly to the same RSS
    	exportInstance("testExtendedWithEnums");
    	exportInstance("testExtendedWithEnumsDefaults");
    	checkNoMessages();
    	
    	rewriteTu(tu);
    	checkRefFile("ref/TestExtendedWithEnums.rss", sf);
    	
    	IAstResourceDefinition[] defs = tu.getResourceDefinitions();
    	int found = 0;
    	for (IAstResourceDefinition def : defs) {
    		if (!def.getStructType().getStructName().getName().equals("COLORS"))
    			continue;
    		found++;
        	checkMemberInit(def, "aknFgColor", IAstLiteralExpression.K_INTEGER, "2");
        	checkMemberInit(def, "aknBgColor", IAstLiteralExpression.K_INTEGER, "123");
    	}
    	assertEquals(2, found);
    }
    public void testExtendedWithEnumsInitDefault() throws Exception {
    	exportInstance("testExtendedWithEnumsInitDefault");
    	exportInstance("testExtendedWithEnumsDefaultsInitDefault");
    	checkNoMessages();
    	
    	rewriteTu(tu);
    	checkRefFile("ref/TestExtendedWithEnumsInitDefault.rss", sf);
    	
    	
    	IAstResourceDefinition[] defs = tu.getResourceDefinitions();
    	int found = 0;
    	for (IAstResourceDefinition def : defs) {
    		if (!def.getStructType().getStructName().getName().equals("COLORS_DEFAULTS"))
    			continue;
    		found++;
        	// all values initialized as defaults
        	// the first as the value of the enum...
        	checkNoMemberInit(def, "aknFgColor");
        	// and the second as the actual enum name
        	checkNoMemberInit(def, "aknBgColor");
    	}
    	assertEquals(2, found);
    	
    }
    public void testExtendedWithEnumsUnique() throws Exception {
    	exportInstance("testExtendedWithEnumsUnique");
    	checkNoMessages();
 
    	rewriteTu(tu);
    	checkRefFile("ref/TestExtendedWithEnumsUnique.rss", sf);
    	
    	IAstResourceDefinition def = tu.findResourceDefinitionOfType("COLORS");
    	assertNotNull(def);
    	checkMemberInit(def, "aknFgColor", IAstLiteralExpression.K_INTEGER, "3");
    	checkMemberInit(def, "aknBgColor", IAstLiteralExpression.K_INTEGER, "2");
    }
    public void testExtendedWithEnumsUniqueInitDefault() throws Exception {
    	exportInstance("testExtendedWithEnumsInitDefaultUnique");
    	checkNoMessages();
 
    	rewriteTu(tu);
    	checkRefFile("ref/TestExtendedWithEnumsUniqueInitDefault.rss", sf);
    	
    	IAstResourceDefinition def = tu.findResourceDefinitionOfType("COLORS_DEFAULTS");
    	assertNotNull(def);
    	checkMemberInit(def, "aknFgColor", IAstLiteralExpression.K_INTEGER, "3");
    	checkMemberInit(def, "aknBgColor", IAstLiteralExpression.K_INTEGER, "2");
    }
    
    /**
     * Test that we can suppress default values
     * @throws Exception
     */
    public void testEnumsInitNoDefault() throws Exception {
    	exportInstance("com.nokia.examples.srcmapEnums_nodefaults", "test2_nd");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestEnumsInitNoDefault.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("COLORS_DEFAULTS");
        assertNotNull(def);

        // all values initialized as defaults, but not suppressed
        checkMemberInit(def, "aknFgColor", IAstIdExpression.class, "CAknColorGreen");
        checkMemberInit(def, "aknBgColor", IAstIdExpression.class, "CAknColorCyan");
    }

    public void testEnumsExplicit() throws Exception {
    	exportInstance("com.nokia.examples.srcmapEnums_explicit", "test2_explicit");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestEnumsExplicit.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("COLORS");
        assertNotNull(def);

        // check all the simple types for proper formatting and DOM expression
        checkMemberInit(def, "aknFgColor", IAstIdExpression.class, "CAknColorGreen");
    }


    public void testEnumExplicitUnmapped() throws Exception {
    	exportInstance("com.nokia.examples.srcmapEnums_explicit", "test2_unmapped");
        // this has an enum which has no mapping 
        assertTrue(hadErrors);
        checkMessage("UnknownEnum");
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestEnumsExpicitUnmapped.rss", sf);
        
    }
    
    public void testEnumBroken() throws Exception {
    	// this has an invalid enum
    	exportInstance("com.nokia.examples.srcmapEnums", "test2_broken");
        assertTrue(hadErrors);
        //checkMessage("InvalidPropertyValue");	// won't find this anymore
        //checkMessage("CannotMapProperty");
        checkMessage("PossiblyInvalidEnum");
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestEnumsBroken.rss", sf);
        
    }

    public void testEnumAlgDialogLineId() throws Exception {
    	exportInstance("com.nokia.examples.srcmapEnums_dlgLineID", "testEnumDialogLineId");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestEnumsDialogLineId.rss", sf);

        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("DIALOG_LINE");
        assertNotNull(def);

        // check all the simple types for proper formatting and DOM expression
        checkMemberInit(def, "id", IAstIdExpression.class, "EMyContainerTestEnumDialogLineId");

        IAstSourceFile[] files = tu.getIncludedFiles();
        boolean foundHeader = false;
        IAstRssSourceFile headerFile = null;
        for (int i = 0; i < files.length; i++) {
			if (files[i].getSourceFile().getFileName().endsWith(".hrh")) {
				headerFile = (IAstRssSourceFile) files[i];
				foundHeader=true;
				IAstEnumDeclaration[] enums = (IAstEnumDeclaration[]) files[i].getFileNodes(IAstEnumDeclaration.class);
				assertEquals(1, enums.length);
				IAstEnumerator[] emrs = enums[0].getEnumerators();
				assertEquals(1, emrs.length);
			}
		}
        assertTrue(foundHeader);
        
        // do it again, this time with a broken tu, and ensure the enum decl didn't appear twice
        tu.getSourceFile().removeAllContents();
        tu.refresh();
        manipulator.getResourceTracker().reset();
        manipulator.getTypeHandler().reset();
        
    	exportInstance("com.nokia.examples.srcmapEnums_dlgLineID", "testEnumDialogLineId");
        checkNoMessages();
        
        rewriteTu(tu);
        
        IAstEnumDeclaration[] enums = (IAstEnumDeclaration[]) headerFile.getFileNodes(IAstEnumDeclaration.class);
        assertEquals(1, enums.length);
    }

    public void testEnumAlgDialogLineControl() throws Exception {
    	exportInstance("com.nokia.examples.srcmapEnums_dlgCtrl", "testEnumDialogControl");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestEnumsDialogControl.rss", sf);
         
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("DIALOG_LINE");
        assertNotNull(def);

        // check all the simple types for proper formatting and DOM expression
        checkMemberInit(def, "type", IAstIdExpression.class, "EEikCtDubiousControl");
        
    }

    public void testEnumAlgErrors() throws Exception {
    	exportInstance("com.nokia.examples.srcmapEnums_nameAlgErrors", "testEnumDialogLineId");
        assertTrue(hadErrors);
        
        checkMessage("UnknownAlgorithm");
         
    }
    
    public void testEnumAlgWithReferences() throws Exception {
    	exportInstance("testEnumDialogReferencer1");
    	exportInstance("testEnumDialogReferencer2");
    	exportInstance("testEnumDialogReferencer3");
    	checkNoMessages();
    	
    	rewriteTu(tu);
    	checkRefFile("ref/TestEnumAlgWithReferences.rss", sf);

        IAstSourceFile[] files = tu.getIncludedFiles();
        boolean foundHeader = false;
        for (int i = 0; i < files.length; i++) {
			if (files[i].getSourceFile().getFileName().endsWith(".hrh")) {
				foundHeader=true;
				IAstEnumDeclaration[] enums = (IAstEnumDeclaration[]) files[i].getFileNodes(IAstEnumDeclaration.class);
				assertEquals(1, enums.length);
				IAstEnumerator[] emrs = enums[0].getEnumerators();
				
				// only need two enums for two shared references and a unique one
				assertEquals(2, emrs.length);
			}
		}
        assertTrue(foundHeader);
    }

    private IAstEnumDeclaration getFirstEnum() {
        IAstSourceFile[] files = tu.getIncludedFiles();
        for (int i = 0; i < files.length; i++) {
			if (files[i].getSourceFile().getFileName().endsWith(".hrh")) {
				IAstEnumDeclaration[] enums = (IAstEnumDeclaration[]) files[i].getFileNodes(IAstEnumDeclaration.class);
				if (enums.length > 0)
					return enums[0];
			}
		}
        return null;
    }
    
    /** 
     * test the algorithm for listbox layouts, which
     * emits initialized enums in the same order as their ordering within the parent
     */
    public void testEnumListboxLayoutEnumAlg1() throws Exception {
    	exportInstance("testLayout1");
    	checkNoMessages();
    	
    	IAstEnumDeclaration decl = getFirstEnum();
    	assertEquals(1, decl.getEnumerators().length);
    	IAstEnumerator emr = decl.getEnumerators()[0];
    	assertNotNull(emr.getInitializerExpression());
    	assertEquals("0", emr.getInitializerExpression().getExpression().getNewText(null));
    	assertEquals("ELayoutTestLayout1Id", emr.getName().getName());
    	
    }
    /** 
     * test the algorithm for listbox layouts, which
     * emits initialized enums in the same order as their ordering within the parent
     */
    public void testEnumListboxLayoutEnumAlg2() throws Exception {
    	exportInstance("testLayout2a");
    	checkNoMessages();

    	IAstEnumDeclaration decl = getFirstEnum();
    	assertEquals(1, decl.getEnumerators().length);
    	IAstEnumerator emr = decl.getEnumerators()[0];
    	assertNotNull(emr.getInitializerExpression());
    	assertEquals("1", emr.getInitializerExpression().getExpression().getNewText(null));
    	assertEquals("ELayoutTestLayout2aId", emr.getName().getName());

    	exportInstance("testLayout2b");
    	checkNoMessages();

    	decl = getFirstEnum();
    	assertEquals(2, decl.getEnumerators().length);
    	emr = decl.getEnumerators()[0];
    	assertNotNull(emr.getInitializerExpression());
    	assertEquals("1", emr.getInitializerExpression().getExpression().getNewText(null));
    	assertEquals("ELayoutTestLayout2aId", emr.getName().getName());
    	
    	emr = decl.getEnumerators()[1];
    	assertNotNull(emr.getInitializerExpression());
    	assertEquals("0", emr.getInitializerExpression().getExpression().getNewText(null));
    	assertEquals("ELayoutTestLayout2bId", emr.getName().getName());

    	
    }
    
    /** 
     * test the algorithm for listbox layouts, which
     * emits initialized enums in the same order as their ordering within the parent
     */
    public void testEnumListboxLayoutEnumAlg3() throws Exception {
    	exportInstance("testLayout3b");
    	checkNoMessages();
    	
       	IAstEnumDeclaration decl = getFirstEnum();
    	assertEquals(1, decl.getEnumerators().length);
    	IAstEnumerator emr = decl.getEnumerators()[0];
    	assertNotNull(emr.getInitializerExpression());
    	assertEquals("0", emr.getInitializerExpression().getExpression().getNewText(null));
    	assertEquals("ELayoutTestLayout3bId", emr.getName().getName());

    	exportInstance("testLayout3a");
    	checkNoMessages();
    	
    	decl = getFirstEnum();
    	assertEquals(2, decl.getEnumerators().length);
    	emr = decl.getEnumerators()[0];
    	assertNotNull(emr.getInitializerExpression());
    	assertEquals("0", emr.getInitializerExpression().getExpression().getNewText(null));
    	assertEquals("ELayoutTestLayout3bId", emr.getName().getName());
    	
       	emr = decl.getEnumerators()[1];
    	assertNotNull(emr.getInitializerExpression());
    	assertEquals("1", emr.getInitializerExpression().getExpression().getNewText(null));
    	assertEquals("ELayoutTestLayout3aId", emr.getName().getName());

    	// now delete the first, which renumbers the remaining enum
    	removeInstance(getInstance("testLayout3b"));
	    generator.generateResources(getInstance("layoutContainer3"));

    	checkNoMessages();
    	
    	decl = getFirstEnum();
    	assertEquals(1, decl.getEnumerators().length);
    	emr = decl.getEnumerators()[0];
    	assertNotNull(emr.getInitializerExpression());
    	assertEquals("0", emr.getInitializerExpression().getExpression().getNewText(null));
    	assertEquals("ELayoutTestLayout3aId", emr.getName().getName());

    }
    
    
}
