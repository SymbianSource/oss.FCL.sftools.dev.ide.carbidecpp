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
import com.nokia.sdt.sourcegen.doms.rss.dom.*;

import org.eclipse.emf.common.command.Command;

import java.util.Collections;

/**
 * Test updating existing RSS with source mapping
 * 
 *
 */
public class SrcMappingTestUpdatingDialog extends SrcMappingBase {
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.component.symbian.test.srcmapping.SrcMappingBase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    /**
     * Load the RSS and return the dialog instance.
     * @param userFile
     * @return
     * @throws Exception
     */
    protected IComponentInstance getDialogFixture(String userFile) throws Exception {
    	return getDialogFixture(userFile, new String[0]);
    }

    protected IComponentInstance getDialogFixture(String userFile, 
    		String[] headers) throws Exception {
    	return getDialogFixture(userFile, headers, new String[0]);
    }

    protected IComponentInstance getDialogFixture(String userFile, 
    		String[] headers,
    		String[] genFiles) throws Exception {
    	parseRssFrom(userFile, headers, genFiles);

        IComponentInstance instance = getInstance("testDialogSub");
        setInstanceToResourceMapping("testDialogSub", "r_test_dialog_sub");
        setArrayElementMapping(instance, ".", "lines",
        		"EMyContainerTestDialogCheckboxSub",
        		"testDialogCheckboxSub");
        setArrayElementMapping(instance, ".", "lines",
        		"EMyContainerTestDialogLabelSub",
        		"testDialogLabelSub");
    	
        return instance;
    	
    }

    /**
     * Test that rewriting fields alongside dialogs works
     * @throws Exception
     */
    public void testDialog1() throws Exception {
        
        IComponentInstance instance = getDialogFixture(
        		"user/TestUpdatingDialog1.rss",
    			null,
    			new String[] { "user/DialogFixtureEnums.hrh" });

        IAstResourceDefinition def = tu.findResourceDefinition("r_test_dialog_sub");
        assertNull(def.findMemberInitializer("flags"));

        setProperty(instance, "flags", "123");
		exportInstance(instance);
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref2/TestUpdatingDialog1.rss", sf);
        
        IAstResourceDefinition def2 = tu.findResourceDefinition("r_test_dialog_sub");
        assertNotNull(def2);
        assertTrue(def == def2);
        
        checkMemberInit(def, "flags", IAstLiteralExpression.K_INTEGER, "123");
        
        ////
        generator.getModelManipulator().getResourceTracker().resetComplete();
        IComponentInstance checkboxInstance = getInstance("testDialogCheckboxSub");
		setProperty(checkboxInstance, "checked", "false");

		exportInstance(instance);
		checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref2/TestUpdatingDialog1_b.rss", sf);
        

    }
    
	/**
     * Test that adding unset fields works
     * @throws Exception
     */
    public void testDialog2() throws Exception {
        
    	IComponentInstance instance = getDialogFixture(
    			"user/TestUpdatingDialog2.rss",
    			null,
    			new String[] { "user/DialogFixtureEnums.hrh" });

        IAstResourceDefinition def = tu.findResourceDefinition("r_test_dialog_sub");
        assertNotNull(def.findMemberInitializer("flags"));
        
        setProperty(instance, "flags", "123");
		exportInstance(instance);
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref2/TestUpdatingDialog2.rss", sf);
        
        IAstResourceDefinition def2 = tu.findResourceDefinition("r_test_dialog_sub");
        assertNotNull(def2);
        assertTrue(def == def2);
        
        checkMemberInit(def, "flags", IAstLiteralExpression.K_INTEGER, "123");
    }
    

    /**
     * Test that we can update arrays with mapping where an
     * element is a unique instance.  This supports reordering,
     * unknown elements, etc.
     * @throws Exception
     */
    public void testArrayWithUniqueInstances_DM_Add() throws Exception {

    	// the items are in the same order as the DM
    	IComponentInstance instance = getDialogFixture(
    			"user/TestArrayWithUniqueInstances.rss",
    			null,
    			new String[] { "user/DialogFixtureEnums.hrh" });

        // new element!
        IComponentInstance widgetInstance = createAndAddInstance(
        		instance, "com.nokia.examples.srcmapDialogWidget", 
        		"testDialogWidgetSub", -1);
        setProperty(widgetInstance, "x", "1");
        setProperty(widgetInstance, "y", "10");

		exportInstance(instance);
        checkNoMessages();
        
        IAstResourceDefinition def = tu.findResourceDefinition("r_test_dialog_sub");
        assertEquals(3, ((IAstExpressionList) def.findMemberInitializer("lines").getInitializerExpression().getExpression()).size());
        
        rewriteTu(tu);
        checkRefFile("ref2/TestArrayWithUniqueInstances_DM_Add.rss", sf);
        
    }

    /**
     * Test that we can update arrays with mapping where an
     * element is a unique instance.  This supports reordering,
     * unknown elements, etc.
     * @throws Exception
     */
    public void testArrayWithUniqueInstances_DM_Add_2() throws Exception {

    	// the items are in the same order as the DM
    	IComponentInstance instance = getDialogFixture(
    			"user/TestArrayWithUniqueInstances.rss",
    			null,
    			new String[] { "user/DialogFixtureEnums.hrh" });

        // new element!
        IComponentInstance widgetInstance = createAndAddInstance(
        		instance, "com.nokia.examples.srcmapDialogWidget", 
        		"testDialogWidgetSub", -1);
        setProperty(widgetInstance, "x", "1");
        setProperty(widgetInstance, "y", "10");

        // and add another
        IComponentInstance widgetInstance2 = createAndAddInstance(
        		instance, "com.nokia.examples.srcmapDialogWidget", 
        		"testDialogWidgetSub2", 0);
        setProperty(widgetInstance2, "x", "5");
        setProperty(widgetInstance2, "y", "50");

		exportInstance(instance);
        checkNoMessages();
        
        IAstResourceDefinition def = tu.findResourceDefinition("r_test_dialog_sub");
        assertEquals(4, ((IAstExpressionList) def.findMemberInitializer("lines").getInitializerExpression().getExpression()).size());
        
        rewriteTu(tu);
        checkRefFile("ref2/TestArrayWithUniqueInstances_DM_Add_2.rss", sf);
        
    }

    /**
     * Test that we can update arrays with mapping where an
     * element is a unique instance.  This supports reordering,
     * unknown elements, etc.
     * @throws Exception
     */
    public void testArrayWithUniqueInstances_DM_Reorder() throws Exception {

    	// the items are in the same order as the DM
    	IComponentInstance instance = getDialogFixture(
    			"user/TestArrayWithUniqueInstances.rss",
    			null,
    			new String[] { "user/DialogFixtureEnums.hrh" });

    	// reorder the elements
        IComponentInstance labelInstance = getInstance("testDialogLabelSub");
    	Command cmd = dataModel.createMoveComponentInstanceCommand(labelInstance.getEObject(), instance.getEObject(), 0);
    	assertTrue(cmd.canExecute());
    	cmd.execute();
    	
		exportInstance(instance);
        checkNoMessages();

        IAstResourceDefinition def = tu.findResourceDefinition("r_test_dialog_sub");
        assertEquals(2, ((IAstExpressionList) def.findMemberInitializer("lines").getInitializerExpression().getExpression()).size());

        rewriteTu(tu);
        
        checkRefFile("ref2/TestArrayWithUniqueInstances_DM_Reorder.rss", sf);
        
    }

    /**
     * Test that we can update arrays with mapping where an
     * element is a unique instance.  This supports reordering,
     * unknown elements, etc.
     * @throws Exception
     */
    public void testArrayWithUniqueInstances_DM_Delete_1() throws Exception {

    	// the items are in the same order as the DM
    	IComponentInstance instance = getDialogFixture(
    			"user/TestArrayWithUniqueInstances.rss",
    			null,
    			new String[] { "user/DialogFixtureEnums.hrh" });

    	// delete an element
    	IComponentInstance checkboxInstance = getInstance("testDialogCheckboxSub");
    	Command cmd = dataModel.createRemoveComponentInstancesCommand(
    			Collections.singletonList(checkboxInstance.getEObject()));
    	assertTrue(cmd.canExecute());
    	cmd.execute();

		exportInstance(instance);
        checkNoMessages();
        
        IAstResourceDefinition def = tu.findResourceDefinition("r_test_dialog_sub");
        assertEquals(1, ((IAstExpressionList) def.findMemberInitializer("lines").getInitializerExpression().getExpression()).size());

        rewriteTu(tu);
        checkRefFile("ref2/TestArrayWithUniqueInstances_DM_Delete_1a.rss", sf);

        ///////
        
        IComponentInstance labelInstance = getInstance("testDialogLabelSub");
       	cmd = dataModel.createRemoveComponentInstancesCommand(
    			Collections.singletonList(labelInstance.getEObject()));
     	assertTrue(cmd.canExecute());
    	cmd.execute();
    	
    	manipulator.getResourceTracker().resetComplete();

		exportInstance(instance);
        checkNoMessages();
        
        def = tu.findResourceDefinition("r_test_dialog_sub");
        // deleted empty list
        //assertEquals(0, ((IAstExpressionList) def.findMemberInitializer("lines").getInitializerExpression().getExpression()).size());
        assertNull(def.findMemberInitializer("lines"));

        rewriteTu(tu);
        checkRefFile("ref2/TestArrayWithUniqueInstances_DM_Delete_1b.rss", sf);
        
    }

    /**
     * Test that we can update arrays with mapping where an
     * element is a unique instance.  This supports reordering,
     * unknown elements, etc.
     * @throws Exception
     */
    public void testArrayWithUniqueInstances_DM_Delete_2() throws Exception {

    	// the items are in the same order as the DM
    	IComponentInstance instance = getDialogFixture(
    			"user/TestArrayWithUniqueInstances.rss",
    			null,
    			new String[] { "user/DialogFixtureEnums.hrh" });

    	// delete an element
        IComponentInstance labelInstance = getInstance("testDialogLabelSub");
        Command cmd = dataModel.createRemoveComponentInstancesCommand(
    			Collections.singletonList(labelInstance.getEObject()));
     	assertTrue(cmd.canExecute());
    	cmd.execute();
    	
		exportInstance(instance);
        checkNoMessages();

        IAstResourceDefinition def = tu.findResourceDefinition("r_test_dialog_sub");
        assertEquals(1, ((IAstExpressionList) def.findMemberInitializer("lines").getInitializerExpression().getExpression()).size());

        rewriteTu(tu);
        checkRefFile("ref2/TestArrayWithUniqueInstances_DM_Delete_2a.rss", sf);

    	IComponentInstance checkboxInstance = getInstance("testDialogCheckboxSub");
    	cmd = dataModel.createRemoveComponentInstancesCommand(
    			Collections.singletonList(checkboxInstance.getEObject()));
    	assertTrue(cmd.canExecute());
    	cmd.execute();

    	manipulator.getResourceTracker().resetComplete();

		exportInstance(instance);
        checkNoMessages();

        def = tu.findResourceDefinition("r_test_dialog_sub");
        //assertEquals(0, ((IAstExpressionList) def.findMemberInitializer("lines").getInitializerExpression().getExpression()).size());
        assertNull(def.findMemberInitializer("lines"));

        rewriteTu(tu);
        checkRefFile("ref2/TestArrayWithUniqueInstances_DM_Delete_2b.rss", sf);
        
    }

    /**
     * Test that remembered existing unknown enums are not reused.
     * @throws Exception
     */
    public void testEnums0() throws Exception {
    	// the enums look generated, but they're not registered
    	IComponentInstance instance = getDialogFixture(
    			"user/TestUpdatingEnums0.rss", 
    			new String[] { "user/TestUpdatingEnums.hrh" },
    			null);
        
		exportInstance(instance);
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref2/TestUpdatingEnums0.rss", sf);

    }

    /**
     * Test that remembered enums are reused.
     * @throws Exception
     */
    public void testEnums1() throws Exception {
    	IComponentInstance instance = getDialogFixture("user/TestUpdatingEnums1.rss", new String[] { "user/TestUpdatingEnums.hrh" });

        IAstResourceDefinition def = tu.findResourceDefinition("r_test_dialog_sub");
		setInstanceToResourceMapping(instance, 0, instance, def);
        
        setEnumMapping(
        		"testDialogLabelSub", ".", "com.nokia.sdt.component.symbian.NAME_ALG_DIALOG_LINE_ID", 
        		"EMyContainerTestDialogLabelSub");
        setEnumMapping(
        		"testDialogCheckboxSub", ".", "com.nokia.sdt.component.symbian.NAME_ALG_DIALOG_LINE_ID", 
        		"EMyContainerTestDialogCheckboxSub");

		exportInstance(instance);
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref2/TestUpdatingEnums1.rss", sf);

    }

    /**
     * Test that remembered enums are reused, when they are macros.
     * @throws Exception
     */
    public void testEnumsFromMacros() throws Exception {
    	IComponentInstance instance = getDialogFixture("user/TestUpdatingEnumsFromMacros.rss", 
    			new String[] { "user/TestUpdatingEnumsFromMacros.hrh" });

        IAstResourceDefinition def = tu.findResourceDefinition("r_test_dialog_sub");
		setInstanceToResourceMapping(instance, 0, instance, def);
        
		// unique vals referenced as macro names
        setEnumMapping(
        		"testDialogLabelSub", ".", "com.nokia.sdt.component.symbian.NAME_ALG_DIALOG_LINE_ID", 
        		"EMyContainerTestDialogLabelSub");
        setEnumMapping(
        		"testDialogCheckboxSub", ".", "com.nokia.sdt.component.symbian.NAME_ALG_DIALOG_LINE_ID", 
        		"EMyContainerTestDialogCheckboxSub");

		exportInstance(instance);
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref2/TestUpdatingEnumsFromMacros.rss", sf);

        // this should work twice
		exportInstance(instance);
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref2/TestUpdatingEnumsFromMacros_b.rss", sf);
        
    }

	/**
	 * Test that deleting an entire dialog works.
	 * @throws Exception
	 */
	public void testDialog() throws Exception {
	    
	    IComponentInstance instance = getDialogFixture("user/TestDeletingDialog.rss");
	
	    removeInstance(instance);
	    generator.generateResources(null);
	    
	    checkNoMessages();
	    rewriteTu(tu);
	    checkRefFile("ref2/TestDeletingDialog.rss", sf);
	    
	    IAstResourceDefinition def = tu.findResourceDefinition("r_test_dialog_sub");
	    assertNull(def);
	}

}
