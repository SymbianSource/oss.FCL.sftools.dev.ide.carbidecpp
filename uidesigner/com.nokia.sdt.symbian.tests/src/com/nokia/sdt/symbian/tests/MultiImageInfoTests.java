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

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cdt.builder.DefaultImageMakefileViewConfiguration;
import com.nokia.carbide.cdt.builder.DefaultMMPViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileModel;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileView;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPModel;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AllNodesViewFilter;
import com.nokia.cpp.internal.api.utils.core.ProjectUtils;
import com.nokia.sdt.symbian.images.*;

/**
 * Test the MultiImageInfo and ImageInfo classes 
 * in com.nokia.sdt.symbian.images.
 * 
 * 
 * 
 *
 */
public class MultiImageInfoTests extends BaseImageTests {
    protected static MultiImageInfo mbmInfo;
    protected static MultiImageInfo mifInfo;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        IMMPModel mmpModel = EpocEnginePlugin.getMMPModelProvider().getSharedModel(new Path(PROJECT_NAME + "/group/foo.mmp"));
        IMMPView mmpView = mmpModel.createView(new DefaultMMPViewConfiguration(project,
        		null,
        		new AllNodesViewFilter()));
        		
        IMultiImageSource multiImageSource = mmpView.getMultiImageSources().get(0);
        mbmInfo = new MultiImageInfo(ProjectUtils.getRealProjectLocation(project), 
                new Path("group/foo.mmp"),
                multiImageSource);
        mmpView.dispose();
        EpocEnginePlugin.getMMPModelProvider().releaseSharedModel(mmpModel);
        
        IImageMakefileModel mkModel = EpocEnginePlugin.getImageMakefileModelProvider().getSharedModel(
        		new Path(PROJECT_NAME + "/random/Icon_svgs.mk"));
        IImageMakefileView mkView = mkModel.createView(
        		new DefaultImageMakefileViewConfiguration(project, null, 
        				new AllNodesViewFilter()));
        
        multiImageSource = mkView.getMultiImageSources().get(0);
        
        mifInfo = new MultiImageInfo(ProjectUtils.getRealProjectLocation(project),  
                new Path("random/Icon_svgs.mk"),
                multiImageSource);

        mkView.dispose();
        EpocEnginePlugin.getImageMakefileModelProvider().releaseSharedModel(mkModel);
    }
    
    public void testMbmFile() throws Exception {
        assertEquals(MultiImageInfo.MBM_FILE, mbmInfo.getFileType());
        assertEquals("foo.mmp", mbmInfo.getSourceFilename());
        assertEquals("Foo", mbmInfo.getBase());
        assertEquals("mbm", mbmInfo.getBinaryExtension());

        String binfile = mbmInfo.getBinaryFilename();
        assertEquals("Foo.mbm", binfile);

        // NOTE: the .cdtbuild file in the project is hardcoded to return
        // this value -- if the real logic changes, change this test and the .cdtbuild file
        IPath path = mbmInfo.getBinaryFile();
        assertEquals(new Path("/system/apps/" + PROJECT_NAME + "/Foo.mbm"), path);
    }

    public void testMbmData() throws Exception {
        ImageInfo info = mbmInfo.findImageByFile("group/qgn_note_ok.bmp");
        assertNotNull(info);
        assertEquals(8, info.getBitDepth());
        assertTrue(info.isColor());
        
        info = mbmInfo.findImageByFile("group/qgn_note_ok_mask_soft.bmp");
        assertNotNull(info);
        assertEquals(8, info.getBitDepth());
        assertTrue(info.isColor());

        info = mbmInfo.findImageByFile("group/image.bmp");
        assertNotNull(info);
        assertEquals(12, info.getBitDepth());
        assertFalse(info.isColor());
        
        info = mbmInfo.findImageByFile("group/missing.bmp");
        assertNotNull(info);
        assertEquals(1, info.getBitDepth());
        assertFalse(info.isColor());
}
    
    public void testMbmList() throws Exception {
        String[] imgs = mbmInfo.getImageFileList();
        // counting each bmp
        assertEquals(5, imgs.length);

        // may change...
        assertEquals("group/qgn_note_ok.bmp", imgs[0]);
        
        // look up
        ImageInfo info = mbmInfo.findImageByFile("group/qgn_note_ok.bmp");
        assertNotNull(info);

        // check enumeration
        String enm = info.getImageEnumeration();
        assertEquals("EMbmFooQgn_note_ok", enm);
        
        ImageInfo info2 = mbmInfo.findImageByEnumerator(enm);
        assertEquals(info, info2);

        /// this one has no paired item
        
        info = mbmInfo.findImageByFile("group/note.bmp");
        assertNotNull(info);

        enm = info.getImageEnumeration();
        assertEquals("EMbmFooNote", enm);
        
        info2 = mbmInfo.findImageByEnumerator(enm);
        assertEquals(info, info2);

    }

    public void testMbmEnums() throws Exception {
        String[] enms = mbmInfo.getImageEnumeratorList();
        // counting each enum
        assertEquals(5, enms.length);
        assertEquals("EMbmFooQgn_note_ok", enms[0]);

        // check mapping
        for (int i = 0; i < enms.length; i++) {
            ImageInfo info = mbmInfo.findImageByEnumerator(enms[i]);
            assertNotNull(info);
            ImageInfo info2 = mbmInfo.findImageByFile(info.getFilePath());
            assertEquals(info, info2);
        }
    }

    
    public void testMbmPairs() throws Exception {
        // check that items paired in the MBM are available
        ImageInfo info = mbmInfo.findImageByEnumerator("EMbmFooQgn_note_ok");
        assertNotNull(info);
        
        ImageInfo mask = mbmInfo.getMaskForImage(info);
        assertNotNull(mask);
        
        // mbm names the mask after the file correctly
        ImageInfo mask2 = mbmInfo.findImageByEnumerator("EMbmFooQgn_note_ok_mask");
        assertNull(mask2);

        mask2 = mbmInfo.findImageByEnumerator("EMbmFooQgn_note_ok_mask_soft");
        assertNotNull(mask2);

        assertEquals(mask, mask2);
        
        /// this one has no paired item
        
        info = mbmInfo.findImageByEnumerator("EMbmFooNote");
        assertNotNull(info);
        
        mask = mbmInfo.getMaskForImage(info);
        assertNull(mask);
        
        //// this has a paired item, but its file is missign

        info = mbmInfo.findImageByEnumerator("EMbmFooImage");
        assertNotNull(info);
        
        // although the file is missing, this should work...
        mask = mbmInfo.getMaskForImage(info);
        assertNotNull(mask);
    }

    
    
    public void testMifs() throws Exception {
        assertEquals("Icon_svgs.mk", mifInfo.getSourceFilename());
        assertEquals(MultiImageInfo.MIF_FILE, mifInfo.getFileType());

        assertEquals("Svgs", mifInfo.getBase());
        assertEquals("mif", mifInfo.getBinaryExtension());

        String binfile = mifInfo.getBinaryFilename();
        assertEquals("Svgs.mif", binfile);

        IPath path = mifInfo.getBinaryFile();
        assertEquals(new Path("/system/resources/Svgs.mif"), path);
        
        // leaving out implied SVG masks but not bmps
        String[] imgs = mifInfo.getImageFileList();
        assertEquals(4, imgs.length);
        
    }

    public void testMifEnums() throws Exception {
        String[] enms = mifInfo.getImageEnumeratorList();
        // one for image and generated masks
        assertEquals(5, enms.length);
        assertEquals("EMbmSvgsAppicon", enms[0]);

        for (int i = 0; i < enms.length; i++) {
            ImageInfo info = mifInfo.findImageByEnumerator(enms[i]);
            assertNotNull(info);
            ImageInfo info2 = mifInfo.findImageByFile(info.getFilePath());
            assertEquals(info, info2);
        }
        
        
    }

    public void testMifPairs() throws Exception {
        // Only non-SVG files have pairs in this format.
        // Although SVG files have implicit masks, they do not
        // come from this class.
        
        ImageInfo info = mifInfo.findImageByEnumerator("EMbmSvgsQgn_note_ok");
        assertNotNull(info);
        
        ImageInfo mask = mifInfo.getMaskForImage(info);
        assertNotNull(mask);
        
        // the "soft" enum is never emitted; only "mask"
        ImageInfo mask2 = mifInfo.findImageByEnumerator("EMbmSvgsQgn_note_ok_mask_soft");
        assertNull(mask2);
        
        mask2 = mifInfo.findImageByEnumerator("EMbmSvgsQgn_note_ok_mask");
        assertNotNull(mask2);
        assertEquals(mask, mask2);
        
        /// this one has a paired item too
        
        info = mifInfo.findImageByEnumerator("EMbmSvgsAppicon");
        assertNotNull(info);
        
        mask = mifInfo.getMaskForImage(info);
        assertNotNull(mask);
        
        /// this one has no paired item
        
        info = mifInfo.findImageByEnumerator("EMbmSvgsImg");
        assertNotNull(info);
        
        mask = mifInfo.getMaskForImage(info);
        assertNull(mask);
        
    }
    

}
