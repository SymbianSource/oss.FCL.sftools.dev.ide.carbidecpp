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

import com.nokia.sdt.component.property.IPropertyInformation;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.images.IImagePropertyInfo;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.symbian.images.*;
import com.nokia.sdt.testsupport.TestDataModelHelpers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.Map;

/**
 * Test that a multi-image property -- one that has a single file
 * but multiple image/mask pairs -- is handled properly
 * 
 *
 */
public class MultiImagePropertyInfoTests extends BaseImageTests {
    protected IDesignerDataModel dataModel;

    protected SymbianMultiImagePropertyInfo getMultiImagePropertyInfo(IPropertySource ps) {
    	return (SymbianMultiImagePropertyInfo) ModelUtils.getMultiImagePropertyInfo(((IPropertyInformation)ps).getPropertyOwner(null), ps);
    }
    
    /*
     * @see BaseImageTests#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        dataModel = TestDataModelHelpers.loadDataModelWithTestComponents(project,
                BASE_DIR + FILE_NAME, provider);
    }

    /**
	 */
	private IPropertySource getImagePropertyFor(String name) {
	    EObject obj = dataModel.findByNameProperty(name);
	    assertNotNull(obj);
	    IPropertySource ps = ModelUtils.getPropertySource(obj);
	    assertNotNull(ps);
	    ps = (IPropertySource) ps.getPropertyValue("images");
	    assertNotNull(ps);
	    return ps;
	}

	public void testMissingMultiImageFile() throws Exception {
        IPropertySource ps = getImagePropertyFor("test_multi_image_missing");
        
        SymbianMultiImagePropertyInfo multiInfo = getMultiImagePropertyInfo(ps);
        // the referenced file does not exist and cannot be resolved to a source file
        assertNotNull(multiInfo);
        Map<String, IImagePropertyInfo> infoMap = multiInfo.getImagePropertyInfoMap();
        assertNotNull(infoMap);
        assertEquals(2, infoMap.size());
        
        ImagePropertyInfo info;
        info = (ImagePropertyInfo) infoMap.get("first");
        assertNull(info.multiImageInfo);
        assertNull(info.uriInfo);
        assertFalse(info.isURI());
        assertEquals("Unknown.mbm", info.bmpfile);
        assertNull(info.bitmapInfo);
        assertEquals("EMbmUnknownQgn_note_ok", info.bmpid);
        assertNull(info.maskInfo);
        assertEquals("EMbmUnknownQgn_note_ok_mask_soft", info.bmpmask);
        
        info = (ImagePropertyInfo) infoMap.get("second");
        assertNull(info.multiImageInfo);
        assertNull(info.uriInfo);
        assertFalse(info.isURI());
        assertEquals("Unknown.mbm", info.bmpfile);
        assertNull(info.bitmapInfo);
        assertEquals("", info.bmpid);
        assertNull(info.maskInfo);
        assertEquals("", info.bmpmask);

    }

	public void testMultiImageFile() throws Exception {
	    IPropertySource ps = getImagePropertyFor("test_multi_image");
	    
	    SymbianMultiImagePropertyInfo multiInfo = getMultiImagePropertyInfo(ps);
	    assertNotNull(multiInfo);
	    
	    Map<String, IImagePropertyInfo> infoMap = multiInfo.getImagePropertyInfoMap();
	    assertNotNull(infoMap);
	    assertEquals(2, infoMap.size());
	    
	    MultiImageInfo common;
	    
	    ImagePropertyInfo info;
	    info = (ImagePropertyInfo) infoMap.get("first");
	    common =info.multiImageInfo;
	    assertNotNull(common);
	    assertNull(info.uriInfo);
	    assertFalse(info.isURI());
	    assertEquals("testMulti.mbm", info.bmpfile);
	    assertNotNull(info.bitmapInfo);
	    assertEquals("EMbmTestmultiPic1", info.bmpid);
	    assertNull(info.maskInfo);
	    assertEquals("", info.bmpmask);
	    
	    info = (ImagePropertyInfo) infoMap.get("second");
	    
	    assertNotNull(info.multiImageInfo);
	    assertSame(common, info.multiImageInfo);	// don't load twice
	    assertNull(info.uriInfo);
	    assertFalse(info.isURI());
	    assertEquals("testMulti.mbm", info.bmpfile);
	    assertNotNull(info.bitmapInfo);
	    assertEquals("EMbmTestmultiPic2", info.bmpid);
	    assertNotNull(info.maskInfo);
	    assertEquals("EMbmTestmultiPic2_mask", info.bmpmask);
	
	}

}
