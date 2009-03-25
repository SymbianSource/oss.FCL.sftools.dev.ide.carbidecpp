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

import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.component.symbian.properties.PropertyExtenderImplementationFactory;
import com.nokia.sdt.datamodel.adapter.IPropertyExtenders;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;


/**
 * 
 *
 */
public class SrcMappingTestArrays extends SrcMappingBase {

    
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        // register the factory for reconciling properties
        ComponentSystemPlugin.initializeImplementationsRegistry();
        PropertyExtenderImplementationFactory fac = new PropertyExtenderImplementationFactory();
        
        ComponentSystemPlugin.getImplementationTypes().addInterface(
                IPropertyExtenders.class.getName(),
                fac.getCodeImplAdapterClass(),
                fac.getScriptImplAdapterClass());
        
    }
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testArrayOfStrings() throws Exception {
        exportInstance("com.nokia.examples.srcmapArrayOfStrings", "testArrayOfStrings");
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref/TestArrayOfStrings.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("STRING_LIST");
        assertNotNull(def);

        IAstMemberInitializer init = def.findMemberInitializer("strings");
        assertNotNull(init);
        
        checkArrayInit(init);
        IAstExpression[] exprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(2, exprs.length);
        checkResourceExpr(exprs[0], "LBUF");
        checkResourceExprMemberInit(exprs[0], "txt", IAstExpression.class, "STR_1");
        checkResourceExpr(exprs[1], "LBUF");
        checkResourceExprMemberInit(exprs[1], "txt", IAstExpression.class, "STR_10");

    }

    public void testArrays() throws Exception {
    	exportInstance("com.nokia.examples.srcmapArrays", "test5");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        
        checkRefFile("ref/TestArrays.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ARRAY_TYPES");
        assertNotNull(def);

        // check all the simple types for proper formatting and DOM expression
        IAstMemberInitializer init = def.findMemberInitializer("ints");
        assertNotNull(init);
        
        checkArrayInit(init);
        IAstExpression[] exprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(3, exprs.length);
        checkLiteral(exprs[0], IAstLiteralExpression.K_INTEGER, "2");
        checkLiteral(exprs[1], IAstLiteralExpression.K_INTEGER, "3");
        checkLiteral(exprs[2], IAstLiteralExpression.K_INTEGER, "5");

        /////////
        
        init = def.findMemberInitializer("structs");
        assertNotNull(init);
        
        checkArrayInit(init);
        exprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(2, exprs.length);
        checkResourceExpr(exprs[0], "SUBTYPE");
        checkResourceExprMemberInit(exprs[0], "flag", IAstLiteralExpression.K_INTEGER, "1");
        checkResourceExprMemberInit(exprs[0], "number", IAstLiteralExpression.K_INTEGER, "23");
        checkResourceExpr(exprs[1], "SUBTYPE");
        checkResourceExprMemberInit(exprs[1], "flag", IAstLiteralExpression.K_INTEGER, "1");
        checkResourceExprMemberInit(exprs[1], "number", IAstLiteralExpression.K_INTEGER, "42");

    }

    public void testArraysNoInit() throws Exception {
    	// nothing in the arrays --> no initializer
    	exportInstance("com.nokia.examples.srcmapArrays", "test5_n");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestArraysNoInit.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ARRAY_TYPES");
        assertNotNull(def);

        IAstMemberInitializer init = def.findMemberInitializer("ints");
        assertNull(init);
        
        init = def.findMemberInitializer("structs");
        assertNull(init);
    }

    public void testArraysDefaultElements() throws Exception {
        // Default values in arrays cannot be left out
        // since  an array is a series of contiguous elements.
        // The per-element initialization pattern could handle
        // this, but it brings in more complexities (interactions 
        // with any default initializer on the STRUCT) so we 
        // forstall this work for later.

    	exportInstance("com.nokia.examples.srcmapArrays", "test5_de");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestArraysDefaultElements.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ARRAY_TYPES");
        assertNotNull(def);

        // check all the simple types for proper formatting and DOM expression
        IAstMemberInitializer init = def.findMemberInitializer("ints");
        assertNotNull(init);
        
        checkArrayInit(init);
        IAstExpression[] exprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(3, exprs.length);
        checkLiteral(exprs[0], IAstLiteralExpression.K_INTEGER, "2");
        checkLiteral(exprs[1], IAstLiteralExpression.K_INTEGER, "0");
        checkLiteral(exprs[2], IAstLiteralExpression.K_INTEGER, "5");

        /////////
        
        init = def.findMemberInitializer("structs");
        assertNotNull(init);
        
        checkArrayInit(init);
        exprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(2, exprs.length);
        checkResourceExpr(exprs[0], "SUBTYPE");
        checkResourceExprMemberInit(exprs[0], "number", IAstLiteralExpression.K_INTEGER, "23");
        checkResourceExprNoMemberInit(exprs[0], "flag");
        checkResourceExpr(exprs[1], "SUBTYPE");
        checkResourceExprNoMemberInit(exprs[1], "flag");
        checkResourceExprNoMemberInit(exprs[1], "number");

    }

    public void testArraysDefault() throws Exception {
        
        // Arrays have default initializers, but at this time
        // we choose not to omit our own initializers even
        // if the sequences match.  This is because the logic
        // required to test this is a bit difficult to write,
        // given the weirdness of the RSS model for array
        // initialization.

    	exportInstance("com.nokia.examples.srcmapArrays_defaults", "test5_d");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestArraysDefault.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ARRAY_TYPES_DEFAULTS");
        assertNotNull(def);

        IAstMemberInitializer init = def.findMemberInitializer("ints");
        assertNotNull(init);

        // although this matches the default, we don't (currently)
        // omit it from RSS
        checkArrayInit(init);
        IAstExpression[] exprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(3, exprs.length);
        checkLiteral(exprs[0], IAstLiteralExpression.K_INTEGER, "2");
        checkLiteral(exprs[1], IAstLiteralExpression.K_INTEGER, "3");
        checkLiteral(exprs[2], IAstLiteralExpression.K_INTEGER, "5");

        /////////
        
        init = def.findMemberInitializer("structs");
        assertNotNull(init);
        
        // Struct arrays can never be defaulted.  But the instances
        // specify default struct values and the member should be absent
        checkArrayInit(init);
        exprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(2, exprs.length);
        checkResourceExpr(exprs[0], "SUBTYPE_DEFAULTS");
        checkResourceExprNoMemberInit(exprs[0], "number");
        checkResourceExprNoMemberInit(exprs[0], "flag");
        checkResourceExpr(exprs[1], "SUBTYPE_DEFAULTS");
        checkResourceExprNoMemberInit(exprs[1], "flag");
        checkResourceExprNoMemberInit(exprs[1], "number");
    }

    public void testArraysOfRefs() throws Exception {

        exportInstance("com.nokia.examples.srcmapArrays_refs", "test5_refs");
        
        // export children, as a normal recursive descent would do
        exportInstance("com.nokia.examples.srcmapBasic", "test0");
        checkNoMessages();
        exportInstance("com.nokia.examples.srcmapRefs", "test4");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestArraysOfRefs.rss", sf);

        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ARRAY_TYPES");
        assertNotNull(def);

        // check all the simple types for proper formatting and DOM expression
        IAstMemberInitializer init = def.findMemberInitializer("refs");
        assertNotNull(init);
        
        checkArrayInit(init);
        IAstExpression[] exprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(2, exprs.length);
        checkInit(exprs[0], IAstIdExpression.class, "r_test0"); 
        checkInit(exprs[1], IAstIdExpression.class, "r_test4"); 
        
        // make sure the referents have contents
        IAstResourceDefinition ref = tu.findResourceDefinition("r_test0");
        assertNotNull(ref);
        
        // make sure referent written
        checkMemberInit(ref, "text", IAstLiteralExpression.K_STRING, "\"check this textual data\"");
         
        ref = tu.findResourceDefinition("r_test4");
        assertNotNull(ref);
        
        // make sure referent written
        checkMemberInit(ref, "theLink", IAstIdExpression.class, "r_test0");
        checkMemberInit(ref, "theLlink", IAstIdExpression.class, "r_test0");
        
    }

    public void testArraysFixedElements() throws Exception {
    	exportInstance("com.nokia.examples.srcmapArrays_FixedElements", "testArrayFixed");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestArraysFixedElements.rss", sf);

        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ARRAY_TYPES");
        assertNotNull(def);

        // check all the simple types for proper formatting and DOM expression
        IAstMemberInitializer init = def.findMemberInitializer("ints");
        assertNotNull(init);
        
        checkArrayInit(init);
        IAstExpression[] exprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(2, exprs.length);
        checkLiteral(exprs[0], IAstLiteralExpression.K_INTEGER, "42");
        checkLiteral(exprs[1], IAstLiteralExpression.K_INTEGER, "23");

        /////////
        
        init = def.findMemberInitializer("structs");
        assertNotNull(init);
        
        checkArrayInit(init);
        exprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(2, exprs.length);
        checkResourceExpr(exprs[0], "SUBTYPE");
        checkResourceExprMemberInit(exprs[0], "number", IAstLiteralExpression.K_INTEGER, "23");
        checkResourceExprNoMemberInit(exprs[0], "flag");
        checkResourceExpr(exprs[1], "SUBTYPE");
        checkResourceExprMemberInit(exprs[1], "number", IAstLiteralExpression.K_INTEGER, "42");
        checkResourceExprNoMemberInit(exprs[1], "flag");

    }

    public void testArraysFixedElementsError() throws Exception {
    	exportInstance("com.nokia.examples.srcmapArrays_FixedElementsBroken", "testArrayFixed");
        assertTrue(hadErrors);
        checkMessage("IllegalMemberArrayIndex");

        rewriteTu(tu);
        checkRefFile("ref/TestArraysFixedElementsError.rss", sf);

    }

    public void testArrayBadMapping() throws Exception {
    	exportInstance("com.nokia.examples.srcmapArrays_BadMapping", "test5");
        assertTrue(hadErrors);

        checkMessage("CannotMapElementsToArray");
        
        rewriteTu(tu);
        checkRefFile("ref/TestArraysBadMapping.rss", sf);
    }
    
    public void testArrayBadMapping2() throws Exception {
    	exportInstance("com.nokia.examples.srcmapArrays_BadMapping2", "test5");
        assertTrue(hadErrors);
        
        checkMessage("CannotMapElementsToArray");

        rewriteTu(tu);
        checkRefFile("ref/TestArraysBadMapping2.rss", sf);
    }
    
    public void testArrayDialog() throws Exception {
        // this one maps an array of child elements to resource expressions
        // -- we don't want to see resources for the children
        
        exportInstance("com.nokia.examples.srcmapDialog", "testDialog");
        checkNoMessages();

        // export children, as a normal recursive descent would do
        exportInstance("com.nokia.examples.srcmapDialogCheckbox", "testDialogCheckbox");
        checkNoMessages();
        exportInstance("com.nokia.examples.srcmapDialogLabel", "testDialogLabel");
        checkNoMessages();

        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestArrayDialog.rss", sf);

        // ONLY the top-level one should be written;
        // the children have been emitted already
        IAstResourceDefinition defs[] = tu.getResourceDefinitions();
        assertEquals(1, defs.length);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("DIALOG");
        assertNotNull(def);

        IAstMemberInitializer init = def.findMemberInitializer("lines");
        assertNotNull(init);
        
        checkArrayInit(init);
        IAstExpression[] exprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(2, exprs.length);
        
        checkResourceExpr(exprs[0], "CHECKBOX");
        IAstResource rsrc = (IAstResource)exprs[0];
        assertEquals("CHECKBOX", rsrc.getStructType().getStructName().getName());
        checkMemberInit(rsrc, "defaultChecked", IAstLiteralExpression.K_INTEGER, "1");

        checkResourceExpr(exprs[1], "LABEL");
        rsrc = (IAstResource)exprs[1];
        checkMemberInit(rsrc, "text", IAstLiteralExpression.K_STRING, "\"literal string\"");
    }

    public void testArrayDialogRef() throws Exception {
        // this one maps an array of child elements to resource expressions
        // -- we wouldn't want to see child instances' resources,
        // except for this one case where another component
        // refers to the resource directly
        
        exportInstance("com.nokia.examples.srcmapDialog", "testDialog");
        checkNoMessages();

        // export children, as a normal recursive descent would do
        exportInstance("com.nokia.examples.srcmapDialogCheckbox", "testDialogCheckbox");
        checkNoMessages();
        exportInstance("com.nokia.examples.srcmapDialogLabel", "testDialogLabel");
        checkNoMessages();

        // export referent
        exportInstance("com.nokia.examples.srcmapRefs", "testDialogRef");
        checkNoMessages();

        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestArrayDialogRef.rss", sf);

        // the top-level one plus the checkbox one plus the referent 
        // should be written 
        IAstResourceDefinition defs[] = tu.getResourceDefinitions();
        assertEquals(3, defs.length);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("DIALOG");
        assertNotNull(def);

        //////////// repeated
        IAstMemberInitializer init = def.findMemberInitializer("lines");
        assertNotNull(init);
        
        checkArrayInit(init);
        IAstExpression[] exprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(2, exprs.length);
        
        checkResourceExpr(exprs[0], "CHECKBOX");
        IAstResource rsrc = (IAstResource)exprs[0];
        assertEquals("CHECKBOX", rsrc.getStructType().getStructName().getName());
        checkMemberInit(rsrc, "defaultChecked", IAstLiteralExpression.K_INTEGER, "1");

        checkResourceExpr(exprs[1], "LABEL");
        rsrc = (IAstResource)exprs[1];
        checkMemberInit(rsrc, "text", IAstLiteralExpression.K_STRING, "\"literal string\"");
        //////////// repeated

        // the link should point to the resource
        def = tu.findResourceDefinition("r_test_dialog_ref");
        assertNotNull(def);
        checkMemberInit(def, "theLink", IAstIdExpression.class, "r_test_dialog_checkbox");
        
        // and the resource should exist both above and here
        def = tu.findResourceDefinition("r_test_dialog_checkbox");
        assertNotNull(def);
        assertEquals("CHECKBOX", def.getStructType().getStructName().getName());
        checkMemberInit(def, "defaultChecked", IAstLiteralExpression.K_INTEGER, "1");
        
    }
    
    public void testArrayEmptyDialog() throws Exception {
        // this one maps an array of child elements to a resource

        exportInstance("com.nokia.examples.srcmapDialog", "testDialogEmpty");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestArrayEmptyDialog.rss", sf);

        // only the top-level one should be written
        IAstResourceDefinition defs[] = tu.getResourceDefinitions();
        assertEquals(1, defs.length);

        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("DIALOG");
        assertNotNull(def);

        IAstMemberInitializer init = def.findMemberInitializer("lines");
        assertNull(init);
    }

    public void testArrayDialogLinks() throws Exception {
        // this one maps an array of child elements to a resource

        exportInstance("com.nokia.examples.srcmapDialogLinks", "testDialogLinks");
        checkNoMessages();
        
        // export children, as a normal recursive descent would do
        exportInstance("com.nokia.examples.srcmapDialogCheckbox", "testDialogCheckbox");
        checkNoMessages();
        exportInstance("com.nokia.examples.srcmapDialogLabel", "testDialogLabel");
        checkNoMessages();

        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestArrayDialogLinks.rss", sf);

        // all three should be written
        IAstResourceDefinition defs[] = tu.getResourceDefinitions();
        assertEquals(3, defs.length);

        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("DIALOG_LINKS");
        assertNotNull(def);

        IAstMemberInitializer init = def.findMemberInitializer("lines");
        assertNotNull(init);
        
        checkArrayInit(init);
        IAstExpression[] exprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(2, exprs.length);

        // make sure linked resource written, completely
        IAstResourceDefinition cb = tu.findResourceDefinitionOfType("CHECKBOX");
        assertNotNull(cb);
        checkMemberInit(cb, "defaultChecked", IAstLiteralExpression.K_INTEGER, "1");

        // and the link is in the parent resource
        assertEquals(cb.getName().getName(), exprs[0].getCurrentText(sgProvider.getSourceFormatter()));
        
        // make sure linked resource written, completely
        IAstResourceDefinition label = tu.findResourceDefinitionOfType("LABEL");
        assertNotNull(label);
        checkMemberInit(label, "text", IAstLiteralExpression.K_STRING, "\"literal string\"");
        
        // and the link is in the parent resource
        assertEquals(label.getName().getName(), exprs[1].getCurrentText(sgProvider.getSourceFormatter()));

    }
    
    public void testArrayEmptyDialogLinks() throws Exception {
        // this one maps an array of child elements to a resource

        exportInstance("com.nokia.examples.srcmapDialog", "testDialogEmpty");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestArrayEmptyDialogLinks.rss", sf);

        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("DIALOG");
        assertNotNull(def);

        IAstMemberInitializer init = def.findMemberInitializer("lines");
        assertNull(init);
    }

    public void testArrayDialogSub() throws Exception {
        // this one maps an array of child elements to resource expressions.
        // Some of the properties are generated by the dialog component
        // and some are from the contained components
        
        exportInstance("com.nokia.examples.srcmapDialogSub", "testDialogSub");
        checkNoMessages();

        // export children, as a normal recursive descent would do
        exportInstance("com.nokia.examples.srcmapDialogCheckbox", "testDialogCheckboxSub");
        checkNoMessages();
        exportInstance("com.nokia.examples.srcmapDialogLabel", "testDialogLabelSub");
        checkNoMessages();

        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestArrayDialogSub.rss", sf);

        // ONLY the top-level one should be written;
        // the children have been emitted already
        IAstResourceDefinition defs[] = tu.getResourceDefinitions();
        assertEquals(1, defs.length);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("DIALOG");
        assertNotNull(def);

        IAstMemberInitializer init = def.findMemberInitializer("lines");
        assertNotNull(init);
        
        checkArrayInit(init);
        IAstExpression[] lineExprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(2, lineExprs.length);
        
        checkResourceExpr(lineExprs[0], "DIALOG_LINE");
        IAstResource rsrc = (IAstResource)lineExprs[0];
        checkMemberInit(rsrc, "id", IAstIdExpression.class, "EMyContainerTestDialogCheckboxSub");
        checkMemberInit(rsrc, "type", IAstIdExpression.class, "EEikCtCheckbox");
        
        rsrc = getResourceMember(rsrc, "field", "CHECKBOX");
        assertEquals("CHECKBOX", rsrc.getStructType().getStructName().getName());
        checkMemberInit(rsrc, "defaultChecked", IAstLiteralExpression.K_INTEGER, "1");

        checkResourceExpr(lineExprs[1], "DIALOG_LINE");
        rsrc = (IAstResource)lineExprs[1];
        checkMemberInit(rsrc, "id", IAstIdExpression.class, "EMyContainerTestDialogLabelSub");
        checkMemberInit(rsrc, "type", IAstIdExpression.class, "EEikCtLabel");

        rsrc = getResourceMember(rsrc, "field", "LABEL");
        checkMemberInit(rsrc, "text", IAstLiteralExpression.K_STRING, "\"literal string\"");
    }

    public void testArraySingle() throws Exception {
        exportInstance("com.nokia.examples.srcmapArraySingle", "testArraySingle");
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref/TestArraySingle.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ARRAY_TYPES");
        assertNotNull(def);

        IAstMemberInitializer init = def.findMemberInitializer("ints");
        assertNotNull(init);
        
        checkArrayInit(init);
        IAstExpression[] exprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(1, exprs.length);
        checkLiteral(exprs[0], IAstLiteralExpression.K_INTEGER, "987");

        /////////
        
        init = def.findMemberInitializer("structs");
        assertNotNull(init);
        
        checkArrayInit(init);
        exprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(1, exprs.length);
        checkResourceExpr(exprs[0], "SUBTYPE");
        checkResourceExprMemberInit(exprs[0], "flag", IAstLiteralExpression.K_INTEGER, "1");
        checkResourceExprMemberInit(exprs[0], "number", IAstLiteralExpression.K_INTEGER, "25");

    }


    public void testArrayMapIntoProperty() throws Exception {
        exportInstance("testArrayMapIntoProperty");
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref/TestArrayMapIntoProperty.rss", sf);

        assertEquals(10, tu.getResourceDefinitions().length);
        
    }
    
    public void testArrayPartial() throws Exception {
        exportInstance("testArrayPartial");
        checkNoMessages();

        rewriteTu(tu);
        checkRefFile("ref/TestArrayPartial.rss", sf);
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ARRAY_TYPES_DEFAULTS");
        assertNotNull(def);

        IAstMemberInitializer init = def.findMemberInitializer("structs");
        assertNotNull(init);
        
        checkArrayInit(init);
        IAstExpression[] exprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(2, exprs.length);
 
    }
    
    /**
     * Test that we can write elements to the end of an array using member="array[]"
     * @throws Exception
     */
    public void testArrayEnd() throws Exception {
        _testArrayEnd("testMapArrayEndContainer1", new String[] { "first" });
        _testArrayEnd("testMapArrayEndContainer2", new String[] { "first", "third" });
        _testArrayEnd("testMapArrayEndContainer3", new String[] { "first", "second" });
        _testArrayEnd("testMapArrayEndContainer4", new String[] { "second", "third" });
        _testArrayEnd("testMapArrayEndContainer5", new String[] { "first", "second", "third" });
        

    }

	private void _testArrayEnd(String testName, String[] items) throws Exception {
        tu.getSourceFile().removeAllContents();
        tu.refresh();
        manipulator.getResourceTracker().reset();
        manipulator.getTypeHandler().reset();

        exportInstance(testName);
        checkNoMessages();

        rewriteTu(tu);
        checkRefFile("ref/" + testName + ".rss", sf);
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ARRAY");
        assertNotNull(def);
        
        IAstMemberInitializer init = def.findMemberInitializer("items");
        assertNotNull(init);
        
        checkArrayInit(init);
        IAstExpression[] exprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(1, exprs.length); // one child

        IAstResource rsrcExpr = ((IAstResource) tu.findResourceDefinition(((IAstIdExpression) exprs[0]).getName().getName()));
        assertEquals("ARRAY_EMBEDDED", rsrcExpr.getStructType().getStructName().getName());
        init = rsrcExpr.findMemberInitializer("items");
        
        assertNotNull(init);
        IAstExpression[] elExprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(items.length, elExprs.length);
        
        for (int i = 0; i < items.length; i++)
        	checkMemberInit((IAstResource) elExprs[i], "txt", IAstLiteralExpression.K_STRING, "\"" + items[i] +"\"");
	}
}
