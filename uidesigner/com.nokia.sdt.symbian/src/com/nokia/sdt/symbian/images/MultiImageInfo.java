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

package com.nokia.sdt.symbian.images;

import com.nokia.carbide.cpp.epoc.engine.image.IImageSource;
import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.model.IModelProvider;
import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.carbide.cpp.internal.project.ui.images.IMultiImageSourceImageContainerModel;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.core.runtime.IPath;

import java.util.*;

/**
 * Information about a multi-image file.
 *
 */
public class MultiImageInfo {

    public static final int MBM_FILE = 1;
    public static final int MIF_FILE = 2;
    
    /**
     * The type of the multi-image file (MBM_FILE or MIF_FILE)
     */
    private int type;
    /**
     * This is the name of the source MMP or icon makefile file.
     */
    private IPath sourceFile;
    /**
     * This is the name of the expected *.mbm or *.mif file.
     */
    private IPath binaryFile;
    /**
     * The base name of the multi-image file, without path or extension,
     * in title case format
     */
    private String fileBase;
    
    /**
     * Derived image data
     */
    private List<ImageInfo> images;
    
    private int imageIndex;
    
    /**
     * The project
     */
    //private TrackedResource trackedProject;
    /** Map of images to their masks */
    private Map<ImageInfo, ImageInfo> imageToMaskMap;
    
/////////
    
    IModelProvider provider;
    IView view;
	private IPath rootLocation;
	private IMultiImageSourceImageContainerModel imageContainerModel;
	//private String projectName;
	//private IMultiImageSourceImageContainerModel imageContainerModel;
    
    public void dispose() {
        imageToMaskMap.clear();
        //trackedProject.dispose();
    }

    private ImageInfo[] addImageSource(IImageSource source) {
    	ImageInfo addedImage = null;
    	ImageInfo addedMask = null;
    	if (source.getDepth() > 0 && source.getPath() != null) {
    		addedImage = new ImageInfo(this, source, false, imageIndex++);
    		images.add(addedImage);
    	}
    	
    	if (source.getMaskDepth() != 0) {
			addedMask = new ImageInfo(this, source, true, imageIndex++);
			images.add(addedMask);
		} else {
			// when an image does not have a mask, its index is skipped
			imageIndex++;
		}
		
        if (addedImage != null && addedMask != null)
            imageToMaskMap.put(addedImage, addedMask);

        return new ImageInfo[] { addedImage, addedMask };
    }

    /**
     * Creates image data for the given multi-image source in
     * the given MMP or scalable icon makefile
     * @param multiImageFilePath project-relative file (mmp or makefile)
     * @param multiImageSource multi-image info
     */
    public MultiImageInfo(IPath rootPath, IPath multiImageFilePath, IMultiImageSource multiImageSource) {
        Check.checkArg(multiImageSource);
        
        /*IMultiImageSourceImageContainerModel imageContainerModel = CarbideImageModelFactory.createMultiImageSourceModel(
	        		cpi,
	        		ProjectUtils.getRealProjectLocation(cpi.getProject()).append(multiImageFilePath), 
	        		multiImageSource);*/
        //String projectName = cpi.getProject().getName();
		init(rootPath, multiImageFilePath, multiImageSource);
    }

    /**
     * Creates image data for the given multi-image source in
     * the given MMP or scalable icon makefile
     * @param multiImageFilePath project-relative file (mmp or makefile)
     * @param multiImageSource multi-image info
     */
    /*
    public MultiImageInfo(IMultiImageSourceImageContainerModel imageContainerModel, IProject project, IPath multiImageFilePath, IMultiImageSource multiImageSource) {
        Check.checkArg(multiImageSource);
        */
        /*
        IPath projectPath = ProjectUtils.getRealProjectLocation(project);
		IMultiImageSourceImageContainerModel imageContainerModel = CarbideImageModelFactory.createMultiImageSourceModel(
        			projectPath, 
	        		new ImageLoader(),
	        		null,
	        		true,
	        		projectPath.append(multiImageFilePath),
	        		multiImageSource);*/
      //  String projectName = project.getName();
	//	init(multiImageFilePath, multiImageSource, imageContainerModel, projectName);
//    }

	private void init(IPath rootLocation,
			IPath multiImageFilePath,
			IMultiImageSource multiImageSource) {
		//this.imageContainerModel = imageContainerModel;
        //this.projectName = projectName;
        this.rootLocation = rootLocation;
        this.sourceFile = multiImageFilePath;

        if (FileUtils.getSafeFileExtension(multiImageFilePath).equalsIgnoreCase(BuildLogic.MMP_EXTENSION)) {
            this.type = MBM_FILE;
        } else /*if (extension.compareToIgnoreCase(BuildLogic.MIF_EXTENSION) == 0)*/ {
            this.type = MIF_FILE;
        }

        // construct binary filename, without the drive letter so we don't depend on it
        //this.binaryFile = BuildLogic.getTargetFile(project, multiImageFilePath, getBinaryExtension());

        imageToMaskMap = new HashMap<ImageInfo, ImageInfo>();
        images = new ArrayList<ImageInfo>();
        
        revert(multiImageSource);
	}
 
    public void revert(IMultiImageSource multiImageSource) {
    	imageToMaskMap.clear();
    	images.clear();
    	binaryFile = null;

        String targetFile = multiImageSource.getTargetFile();
        this.fileBase = TextUtils.titleCase(TextUtils.stripExtension(
        		targetFile).toLowerCase());
        
        // The path is relative to the target directory, or absolute.
        // Force it absolute for the normal case.  (Doesn't matter
        // since we don't write this back to the model.)
        this.binaryFile = multiImageSource.getTargetFilePath().makeAbsolute();
        
        imageIndex = 0;
    	for (IImageSource imageSource : multiImageSource.getSources()) {
    		addImageSource(imageSource);
    	}
    }

    /**
     * Get the multi image file type
     * @see #MBM_FILE
     * @see #MIF_FILE
     */
    public int getFileType() {
        return type;
    }
    
    /**
     * Get the source path owning the image list (MMP, makefile), project-relative
     */
    public String getSourceFile() {
        return sourceFile.toString();
    }


    /**
     * Get base filename of source file
     */
    public String getSourceFilename() {
        return sourceFile.lastSegment();
    }
    
    /**
     * Get the binary file extension
     */
    public String getBinaryExtension() {
        return (type == MBM_FILE ? BuildLogic.MBM_EXTENSION : BuildLogic.MIF_EXTENSION);
    }

    /**
     * Get base filename of binary file
     */
    public String getBinaryFilename() {
        return binaryFile.lastSegment();
    }

    /**
     * Get the system-relative binary file (full path)
     */
    public IPath getBinaryFile() {
        return binaryFile;
    }

    /**
     * Get the generated include
     */
    public String getIncludeFilename() {
        return fileBase + "." + BuildLogic.MBG_EXTENSION; //$NON-NLS-1$
    }
    

    /**
     * Get the full path of the project.
     * Always return something meaningful, even if the project
     * and workspace have disappeared.
     */
    public IPath getRootPath() {
    	/*
        IPath root = null;
        IProject project = (IProject) ResourcesPlugin.getWorkspace().getRoot().findMember(projectName);
        if (project != null) {
            root = project.getLocation();
        }
        if (root == null)
            root = new Path(new File(".").getAbsolutePath()); //$NON-NLS-1$
            
        return root;
        */
        //return imageContainerModel.getBaseLocation();
    	return rootLocation;
    }

    /**
     * @return the file base, in "Titlecase" format
     */
    public String getBase() {
        return fileBase;
    }
    
    /**
     * @param id
     * @return imageinfo or null
     */
    public ImageInfo findImageByEnumerator(String id) {
        for (ImageInfo info : images) {
            if (info.getImageEnumeration().equals(id))
                return info;
        }
        return null;
    }

    /**
     * Get the image for the given file
     * @param filePath
     * @return info or null
     */
    public ImageInfo findImageByFile(String filePath) {
        for (Iterator iter = images.iterator(); iter.hasNext();) {
            ImageInfo info = (ImageInfo) iter.next();
            if (info.getFilePath().equalsIgnoreCase(filePath))
                return info;
        }
        return null;
    }

    /**
     * Get the list of (project-relative) image fles
     */
    public String[] getImageFileList() {
    	List<String> files = new ArrayList<String>();
        for (Iterator iter = images.iterator(); iter.hasNext();) {
            ImageInfo info = (ImageInfo) iter.next();
            if (!(info.isSVG() && info.isMask()))
            	files.add(info.getFilePath());
        }
        return (String[]) files.toArray(new String[files.size()]);
    }

    /**
     * Get the list of files available as masks.  Excludes SVGs.
	 * @return
	 */
	public String[] getMaskFileList() {
		List<String> files = new ArrayList<String>();
        for (Iterator iter = images.iterator(); iter.hasNext();) {
            ImageInfo info = (ImageInfo) iter.next();
            if (!info.isSVG())
            	files.add(info.getFilePath());
        }
        return (String[]) files.toArray(new String[files.size()]);
	}

	/**
     * Get the list of image enumerators, both images and masks
     */
    public String[] getImageEnumeratorList() {
        String[] list = new String[images.size()];
        int idx = 0;
        for (Iterator iter = images.iterator(); iter.hasNext();) {
            ImageInfo info = (ImageInfo) iter.next();
            list[idx++] = info.getImageEnumeration();
        }
        return list;
    }

    /**
     * Get the mask corresponding to a given image from the MBMDEF/MIFDEF file
     * @return mask or null
     */
    public ImageInfo getMaskForImage(ImageInfo image) {
        return imageToMaskMap.get(image);
    }

	/**
	 * Get image at index, where it is presumed that bitmaps start
	 * at even indices and masks are at odd indices.  If no mask exists,
	 * null is returned.
	 * @param index
	 * @return
	 */
	public ImageInfo getImageAtIndex(int index) {
        for (ImageInfo info : images) {
        	if (info.getIndex() == index)
        		return info;
        }
        return null;
	}
	
	public ImageInfo[] getImageInfos() {
		return (ImageInfo[]) images.toArray(new ImageInfo[images.size()]);
	}

	
	public void setImageContainerModel(IMultiImageSourceImageContainerModel containerModel) {
		imageContainerModel = containerModel;
	}
	public IMultiImageSourceImageContainerModel getImageContainerModel() {
		return imageContainerModel;
	}
}
