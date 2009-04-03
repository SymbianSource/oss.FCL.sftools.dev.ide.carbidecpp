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
import com.nokia.sdt.sourcegen.doms.rss.dom.AstVisitor;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpressionList;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstIdExpression;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstInitializerExpression;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstLiteralExpression;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberInitializer;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstResource;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstResourceDefinition;

/**
 * 
 *
 */
public class SrcMappingTestChoices extends SrcMappingBase {

    
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


    public void testChoiceText() throws Exception {
    	exportInstance("com.nokia.examples.srcmapChoices", "testChoice_Text");
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref/TestChoiceText.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("TEXT_STRUCT");
        assertNotNull(def);
        
        // the value should reference the string
        checkMemberInit(def, "text", IAstLiteralExpression.K_STRING, "\"intertext\"");
        checkNoMemberInit(def, "count");
    }

    public void testChoiceCount() throws Exception {
    	exportInstance("com.nokia.examples.srcmapChoices", "testChoice_Count");
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref/TestChoiceCount.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("COUNT_STRUCT");
        assertNotNull(def);
        
        // the value should reference the number
        checkNoMemberInit(def, "text");
        checkMemberInit(def, "count", IAstLiteralExpression.K_INTEGER, "7");
    }
    
    public void testChoiceABC() throws Exception {
        // check choices, this time with strings rather than booleans
        
    	exportInstance("com.nokia.examples.srcmapChoicesABC", "testChoice_A");
        checkNoMessages();
        
    	exportInstance("com.nokia.examples.srcmapChoicesABC", "testChoice_B");
        checkNoMessages();

    	exportInstance("com.nokia.examples.srcmapChoicesABC", "testChoice_C");
        checkNoMessages();

        ////
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));

        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(3, defs.length);
        
        // NOTE: this indirectly verifies that TranslationUnit
        // returns declarations sorted in a proper order
        checkMemberInit(defs[0], "command", IAstLiteralExpression.K_INTEGER, "23");
        checkMemberInit(defs[1], "command", IAstLiteralExpression.K_INTEGER, "42");
        checkMemberInit(defs[2], "command", IAstLiteralExpression.K_INTEGER, "68");
        
    }    

    public void testChoiceABC_Missing() throws Exception {
    	exportInstance("com.nokia.examples.srcmapChoicesABC", "testChoice_D");
        assertTrue(hadErrors);
        
        checkMessage("NoChoiceMatched");
    }
    
    public void testChoiceTextDefault() throws Exception {
    	exportInstance("com.nokia.examples.srcmapChoices", "testChoice_TextDefault");
        checkNoMessages();

        // in this case, no value provided for "emitCount" 
        // so default of "false" should be used 

        rewriteTu(tu);
        checkRefFile("ref/TestChoiceTextDefault.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("TEXT_STRUCT");
        assertNotNull(def);
        
        // the value should reference the string
        checkMemberInit(def, "text", IAstLiteralExpression.K_STRING, "\"default\"");
        checkNoMemberInit(def, "count");
    }


    public void testChoiceCBABuiltin() throws Exception {
    	exportInstance("com.nokia.examples.srcmapChoicesCBA", "testChoice_CBABuiltin");
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref/TestChoiceCBABuiltin.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("CBA");
        assertNotNull(def);
        
        IAstMemberInitializer init = def.findMemberInitializer("buttons");
        assertNotNull(init);
        checkArrayInit(init);
        IAstExpression[] exprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(1, exprs.length);

        // the initializer points to a resource
        checkInit(exprs[0], IAstIdExpression.class, "r_avkon_softkeys_options_back");
    }

    public void testChoiceCBACustom() throws Exception {
    	exportInstance("com.nokia.examples.srcmapChoicesCBA", "testChoice_CBACustom");
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref/TestChoiceCBACustom.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("CBA");
        assertNotNull(def);
        
        IAstMemberInitializer init = def.findMemberInitializer("buttons");
        assertNotNull(init);
        checkArrayInit(init);
        IAstExpression[] exprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(1, exprs.length);

        // the initializers are resource referencs
        // the link must be an id
        assertTrue(exprs[0] instanceof IAstIdExpression);
        IAstIdExpression id = (IAstIdExpression) exprs[0];
        
        // find the referenced resource
        IAstResourceDefinition ref = tu.findResourceDefinition(id.getName().getName());
        assertNotNull(ref);
        assertEquals("CBA_BUTTON", ref.getStructType().getStructName().getName());
        
        checkMemberInit(ref, "id", IAstIdExpression.class, "EAknSoftkeyPanic");
        checkMemberInit(ref, "txt", IAstLiteralExpression.K_STRING, "\"Panic\"");
    }

    public void testArrayDialogAttrs() throws Exception {
        // This one maps an array of child elements to resource expressions,
        // where only those with given attributes are emitted.

        exportInstance("com.nokia.examples.srcmapChoicesDialogAttrs", "testDialogAttrs");
        checkNoMessages();

        // export children, as a normal recursive descent would do
        exportInstance("com.nokia.examples.srcmapDialogCheckbox", "testDialogCheckboxSub");
        checkNoMessages();
        exportInstance("com.nokia.examples.srcmapBasic", "testDialogNonLineChild");
        checkNoMessages();
        exportInstance("com.nokia.examples.srcmapDialogLabel", "testDialogLabelSub");
        checkNoMessages();

        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestArrayDialogAttrs.rss", sf);

        // only the top-level one and the non-dialog line child should be written;
        // the children have been emitted already
        IAstResourceDefinition defs[] = tu.getResourceDefinitions();
        assertEquals(2, defs.length);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("DIALOG");
        assertNotNull(def);

        IAstMemberInitializer init = def.findMemberInitializer("lines");
        assertNotNull(init);
        
        checkArrayInit(init);
        IAstExpression[] lineExprs = ((IAstExpressionList) init.getInitializerExpression().getExpression()).getList();
        assertEquals(2, lineExprs.length);
        
        checkResourceExpr(lineExprs[0], "DIALOG_LINE");
        IAstResource rsrc = (IAstResource)lineExprs[0];
        
        rsrc = getResourceMember(rsrc, "field", "CHECKBOX");
        assertEquals("CHECKBOX", rsrc.getStructType().getStructName().getName());
        checkMemberInit(rsrc, "defaultChecked", IAstLiteralExpression.K_INTEGER, "1");

        checkResourceExpr(lineExprs[1], "DIALOG_LINE");
        rsrc = (IAstResource)lineExprs[1];

        rsrc = getResourceMember(rsrc, "field", "LABEL");
        checkMemberInit(rsrc, "text", IAstLiteralExpression.K_STRING, "\"literal string\"");
        
        // now check the non-line child
        def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);
        checkMemberInit(def, "text", IAstLiteralExpression.K_STRING, "\"do not inline me\"");
        
    }

    public void testChoicePropertyExistsNoText() throws Exception {
    	exportInstance("testChoicePropertyExistsNoText");
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref/TestChoicePropertyExistsNoText.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("SIMPLE_TYPES");
        assertNotNull(def);
        
        checkMemberInit(def, "long", IAstLiteralExpression.K_INTEGER, "123");
        checkNoMemberInit(def, "buf");
    }
    public void testChoicePropertyExistsWithTextUnset() throws Exception {
    	exportInstance("testChoicePropertyExistsWithTextUnset");
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref/TestChoicePropertyExistsWithTextUnset.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("SIMPLE_TYPES");
        assertNotNull(def);
        
        checkNoMemberInit(def, "long");
        checkNoMemberInit(def, "buf");

        
    }
    
    public void testChoicePropertyExistsWithTextSet() throws Exception {
   	 
    	exportInstance("testChoicePropertyExistsWithTextSet");
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref/TestChoicePropertyExistsWithTextSet.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("SIMPLE_TYPES");
        assertNotNull(def);
        
        checkNoMemberInit(def, "long");
        checkMemberInit(def, "buf", IAstLiteralExpression.K_STRING, "\"setting\"");

        
    }
    
    public void testChoiceIsComponentInstanceOf() throws Exception {
    	exportInstance("testChoiceIsComponentInstanceOf");
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref/TestChoiceIsComponentInstanceOf.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ARRAY");
        assertNotNull(def);
        
        // check all the simple types for proper formatting and DOM expression
        IAstMemberInitializer init = def.findMemberInitializer("items");
        assertNotNull(init);
        
        class InnerVisitor extends AstVisitor {
        	int count = 0;
        	@Override
        	public int visit(IAstNode node) {
        		if (node instanceof IAstMemberInitializer 
        				&& ((IAstMemberInitializer) node).getMember().getMemberName().getName().
        				equals("long")) {
        			assertTrue(count < 3);
        			IAstInitializerExpression value = ((IAstMemberInitializer) node).getInitializerExpression();
        			checkInit(value.getExpression(), IAstIdExpression.class, 
        					count == 1 ? "1" : "0");
        			count++;
        		}
        		traverseChildren(node);
        		return PROCESS_CONTINUE;
        	}
        	
        }
        InnerVisitor visitor = new InnerVisitor();
        init.accept(visitor);
        
        assertEquals(3, visitor.count);
        

    }
}
