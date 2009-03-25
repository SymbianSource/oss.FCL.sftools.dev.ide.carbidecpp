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
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.symbian.images.*;
import com.nokia.sdt.testsupport.TestDataModelHelpers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * 
 *
 */
public class ImagePropertyInfoTests extends BaseImageTests {
    protected IDesignerDataModel dataModel;

    protected ImagePropertyInfo getImagePropertyInfo(IPropertySource ps) {
    	return (ImagePropertyInfo) ModelUtils.getImagePropertyInfo(((IPropertyInformation)ps).getPropertyOwner(null), ps);
    }
    
    /*
     * @see BaseImageTests#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        dataModel = TestDataModelHelpers.loadDataModelWithTestComponents(project,
                BASE_DIR + FILE_NAME, provider);
    }

    public void testMissingMultiImageFile() throws Exception {
        IPropertySource ps = getImagePropertyFor("MissingMultiImageFile");
        
        ImagePropertyInfo info = getImagePropertyInfo(ps);
        // the referenced file does not exist and cannot be resolved to a source file
        assertNull(info.multiImageInfo);
        assertNull(info.uriInfo);
        assertFalse(info.isURI());

    }
    
    public void testMbmMaskedImage() throws Exception {
        IPropertySource ps = getImagePropertyFor("MbmMaskedImage");
        String file = (String) ps.getPropertyValue(SymbianImageValueConverter.IMAGE_PROPERTY_MULTI_IMAGE_FILE_SUBPROPERTY);
        assertNotNull(file);
        
        // the property is not escaped
        assertEquals("\\system\\apps\\image-tests\\Foo.mbm", file);
        
        ImagePropertyInfo info = getImagePropertyInfo(ps);
        assertNotNull(info.multiImageInfo);
        assertEquals("\\system\\apps\\image-tests\\Foo.mbm", info.multiImageInfo.getBinaryFile().toOSString());
        assertNotNull(info.bitmapInfo);
        assertEquals("EMbmFooQgn_note_ok", info.bitmapInfo.getImageEnumeration());
        assertNotNull(info.maskInfo);
        assertEquals("EMbmFooQgn_note_ok_mask_soft", info.maskInfo.getImageEnumeration());
        assertNull(info.uriInfo);
        assertFalse(info.isURI());

        assertEquals("group/foo.mmp", info.multiImageInfo.getSourceFile());
        assertEquals("group/qgn_note_ok.bmp", info.bitmapInfo.getFilePath());
        assertEquals("group/qgn_note_ok_mask_soft.bmp", info.maskInfo.getFilePath());
    }
    
    public void testMbmUnmaskedFromMaskedImage() throws Exception {
        IPropertySource ps = getImagePropertyFor("MbmUnmaskedFromMaskedImage");
        ImagePropertyInfo info = getImagePropertyInfo(ps);
        assertNotNull(info.bitmapInfo);
        assertEquals("EMbmFooQgn_note_ok", info.bitmapInfo.getImageEnumeration());
        assertNull(info.maskInfo);
        assertNull(info.uriInfo);
        assertFalse(info.isURI());

        assertEquals("group/foo.mmp", info.multiImageInfo.getSourceFile());
        assertEquals("group/qgn_note_ok.bmp", info.bitmapInfo.getFilePath());
    }
    
    public void testMbmUnmaskedImage() throws Exception {
        IPropertySource ps = getImagePropertyFor("MbmUnmaskedImage");
        ImagePropertyInfo info = getImagePropertyInfo(ps);
        assertNotNull(info.bitmapInfo);
        assertEquals("EMbmFooNote", info.bitmapInfo.getImageEnumeration());
        assertNull(info.maskInfo);
        assertNull(info.uriInfo);
        assertFalse(info.isURI());

        assertEquals("group/foo.mmp", info.multiImageInfo.getSourceFile());
        assertEquals("group/note.bmp", info.bitmapInfo.getFilePath());
    }
    
    
    public void testMifMaskedImage() throws Exception {
        IPropertySource ps = getImagePropertyFor("MifMaskedImage");
        String file = (String) ps.getPropertyValue(SymbianImageValueConverter.IMAGE_PROPERTY_MULTI_IMAGE_FILE_SUBPROPERTY);
        assertNotNull(file);
        
        // the property is not escaped
        assertEquals("\\system\\resources\\Svgs.mif", file);
        
        ImagePropertyInfo info = getImagePropertyInfo(ps);
        assertNotNull(info.multiImageInfo);
        assertEquals("\\system\\resources\\Svgs.mif", info.multiImageInfo.getBinaryFile().toOSString());
        assertNotNull(info.bitmapInfo);
        assertEquals("EMbmSvgsQgn_note_ok", info.bitmapInfo.getImageEnumeration());
        assertNotNull(info.maskInfo);
        assertEquals("EMbmSvgsQgn_note_ok_mask", info.maskInfo.getImageEnumeration());
        assertNull(info.uriInfo);
        assertFalse(info.isURI());

        assertEquals("random/Icon_svgs.mk", info.multiImageInfo.getSourceFile());
        assertEquals("group/qgn_note_ok.bmp", info.bitmapInfo.getFilePath());
        assertEquals("group/qgn_note_ok_mask_soft.bmp", info.maskInfo.getFilePath());
    }
    
    public void testMifSvgImage() throws Exception {
        IPropertySource ps = getImagePropertyFor("MifSvgImage");
        
        ImagePropertyInfo info = getImagePropertyInfo(ps);
        assertNotNull(info.multiImageInfo);
        assertNotNull(info.bitmapInfo);
        assertEquals("EMbmSvgsAppicon", info.bitmapInfo.getImageEnumeration());
        assertNotNull(info.maskInfo);
        assertEquals("EMbmSvgsAppicon_mask", info.maskInfo.getImageEnumeration());
        assertNull(info.uriInfo);
        assertFalse(info.isURI());

        assertEquals("random/Icon_svgs.mk", info.multiImageInfo.getSourceFile());
        assertEquals("aif/AppIcon.svg", info.bitmapInfo.getFilePath());
        
        // there is no imageinfo but there is an implicit mask
        String maskid = (String) ps.getPropertyValue(SymbianImageValueConverter.IMAGE_PROPERTY_MASK_ID_SUBPROPERTY);
        assertNotNull(maskid);
        assertEquals("EMbmSvgsAppicon_mask", maskid);
    }
    /**
     */
    private IPropertySource getImagePropertyFor(String name) {
        EObject obj = dataModel.findByNameProperty(name);
        assertNotNull(obj);
        IPropertySource ps = ModelUtils.getPropertySource(obj);
        assertNotNull(ps);
        ps = (IPropertySource) ps.getPropertyValue("image");
        assertNotNull(ps);
        return ps;
    }
    

    public void testURIImage() throws Exception {
        IPropertySource ps = getImagePropertyFor("UriImage");
        
        ImagePropertyInfo info = getImagePropertyInfo(ps);
        assertNull(info.multiImageInfo);
        assertNull(info.bitmapInfo);
        assertNull(info.maskInfo);
        assertNotNull(info.uriInfo);
        assertTrue(info.isURI());

        // it will be a legal path, not a DOS path
        assertTrue(info.uriInfo.isSimpleFile());
        assertEquals("z:\\path\\filename.ext", info.uriInfo.getFullPath());
        assertEquals("z:\\path\\filename.ext", info.uriInfo.getFullPath());
        assertNull(info.uriInfo.getQuery());

        
        ps = getImagePropertyFor("UriImage2");
        
        info = getImagePropertyInfo(ps);
        assertNull(info.multiImageInfo);
        assertNull(info.bitmapInfo);
        assertNull(info.maskInfo);
        assertNotNull(info.uriInfo);
        assertTrue(info.isURI());

        // it will be a portable path, not a DOS path
        assertFalse(info.uriInfo.isSimpleFile());
        assertEquals("\\path\\filename2.mbm?idx=0&mask=1", info.uriInfo.getSchemeSpecificPart());
        assertEquals("\\path\\filename2.mbm", info.uriInfo.getFullPath());
        assertEquals("idx=0&mask=1", info.uriInfo.getQuery());

        // non-file URI
        ps = getImagePropertyFor("UriImage3");
        
        info = getImagePropertyInfo(ps);
        assertNull(info.multiImageInfo);
        assertNull(info.bitmapInfo);
        assertNull(info.maskInfo);
        assertNotNull(info.uriInfo);
        assertTrue(info.isURI());
        
        assertFalse(info.uriInfo.isSimpleFile());
        assertEquals("00000000", info.uriInfo.getFullPath());
        assertEquals("size=small", info.uriInfo.getQuery());

        // this one has all four fields set
        ps = getImagePropertyFor("UriImage4");
        
        info = getImagePropertyInfo(ps);
        assertNull(info.multiImageInfo);
        assertNull(info.bitmapInfo);
        assertNull(info.maskInfo);
        assertNotNull(info.uriInfo);

        assertTrue(info.uriInfo.isSimpleFile());
        assertEquals("filename.svg", info.uriInfo.getFullPath());
        assertNull(info.uriInfo.getQuery());

        // this one has all four fields set
        ps = getImagePropertyFor("UriImage5");
        
        info = getImagePropertyInfo(ps);
        assertNull(info.multiImageInfo);
        assertNull(info.bitmapInfo);
        assertNull(info.maskInfo);
        assertNotNull(info.uriInfo);

        assertTrue(info.uriInfo.isSimpleFile());
        assertEquals("z:\\foo\\bar\\filename.svg", info.uriInfo.getFullPath());
        assertEquals("file:///z:\\foo\\bar\\filename.svg", info.uriInfo.getPropertyString());
        assertNull(info.uriInfo.getQuery());


    }
}
