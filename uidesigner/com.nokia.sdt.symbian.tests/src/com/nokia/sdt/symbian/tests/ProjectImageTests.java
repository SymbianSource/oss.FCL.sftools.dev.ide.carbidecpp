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

package com.nokia.sdt.symbian.tests;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.images.IProjectImageInfo;
import com.nokia.sdt.datamodel.images.IProjectImageInfoProvider;
import com.nokia.sdt.displaymodel.GlobalCache;
import com.nokia.sdt.symbian.images.ProjectImageInfo;
import com.nokia.sdt.testsupport.TestDataModelHelpers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Test the image information in com.nokia.sdt.symbian.images.
 * 
 * 
 * 
 *
 */
public class ProjectImageTests extends BaseImageTests {
    protected IDesignerDataModel dataModel;
    protected EObject root;
 
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        dataModel = TestDataModelHelpers.loadDataModelWithTestComponents(project,
                BASE_DIR + FILE_NAME, provider);
        root = dataModel.findByNameProperty("root");

        
    }
    
    /**
     * Test that the project is scanned correctly for images
     * @throws Exception
     */
    public void testScanProject() throws Exception {

        // validate we can get info at all
        IProjectImageInfoProvider imageProvider = (IProjectImageInfoProvider) EcoreUtil.getRegisteredAdapter(root, IProjectImageInfoProvider.class);
        assertNotNull(imageProvider); 

        IProjectImageInfo data = imageProvider.getProjectImageInfo();
        assertNotNull(data);
        assertTrue(data instanceof ProjectImageInfo);
        
        // get the mbmdef and mifdef files in the project
        String[] imgFiles = ((ProjectImageInfo)data).getMultiImageFileList();
        assertNotNull(imgFiles);
        assertEquals(4, imgFiles.length);
  
       
        // new provider should be provided after refreshing
        GlobalCache.disposeAll();
       	dataModel.dispose();
 
        IDesignerDataModel dataModel2 = TestDataModelHelpers.loadDataModelWithTestComponents(project,
                BASE_DIR + FILE_NAME, provider);
        EObject root2 = dataModel2.findByNameProperty("root");

        IProjectImageInfoProvider provider2 = (IProjectImageInfoProvider) EcoreUtil.getRegisteredAdapter(root2, IProjectImageInfoProvider.class);
        assertNotNull(provider2);
        assertNotSame(imageProvider, provider2);
        IProjectImageInfo data2 = provider2.getProjectImageInfo();
        assertTrue(data2 instanceof ProjectImageInfo);
        
        assertNotSame(data, data2);
    }

    /**
     * Test that the provider is refreshed appropriately
     * @throws Exception
     */
    public void testRefreshProvider() throws Exception {

        // validate we can get info at all
        IProjectImageInfoProvider imageProvider = (IProjectImageInfoProvider) EcoreUtil.getRegisteredAdapter(root, IProjectImageInfoProvider.class);
        assertNotNull(imageProvider); 

        IProjectImageInfo data = imageProvider.getProjectImageInfo();
        assertNotNull(data);
        
        // new provider should be provided after refreshing
        GlobalCache.disposeAll();
        dataModel.dispose();
        
        IDesignerDataModel dataModel2 = TestDataModelHelpers.loadDataModelWithTestComponents(project,
                BASE_DIR + FILE_NAME, provider);
        EObject root2 = dataModel2.findByNameProperty("root");

        IProjectImageInfoProvider provider2 = (IProjectImageInfoProvider) EcoreUtil.getRegisteredAdapter(root2, IProjectImageInfoProvider.class);
        assertNotNull(provider2);
        assertNotSame(imageProvider, provider2);
        
        IProjectImageInfo data2 = provider2.getProjectImageInfo();
        assertNotNull(data2);
        assertTrue(data2 instanceof ProjectImageInfo);
        assertNotSame(data, data2);

    }

}
