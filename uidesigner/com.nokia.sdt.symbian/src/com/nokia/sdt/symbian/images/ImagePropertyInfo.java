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

import com.nokia.carbide.cpp.epoc.engine.image.ImageFormat;
import com.nokia.sdt.component.property.IPropertyInformation;
import com.nokia.sdt.datamodel.images.IImagePropertyInfo;
import com.nokia.sdt.datamodel.images.IProjectImageInfo;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.symbian.SymbianPlugin;
import com.nokia.sdt.symbian.dm.UIQModelUtils;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.text.MessageFormat;

/**
 * For a property using the Symbian-style bmpfile/bmpid/bmpmask or URI strings,
 * this class encapsulates the original string values and data derived
 * from them.
 * 
 *
 */
public class ImagePropertyInfo implements IImagePropertyInfo {
    public MultiImageInfo multiImageInfo; //< either null or set
    public ImageInfo bitmapInfo; //< either null or set
    public ImageInfo maskInfo; //< either null or set
    public URIImageInfo uriInfo; //< either null or set
    
    // cached values if initialized from IPropertySource,
    // used to avoid losing data when referencing missing images
    public String bmpid; //< either "" or set
    public String bmpmask; //< either "" or set
    public String bmpfile; //< either "" or set
    public String uri; //< either "" or set
    
    public ImagePropertyInfo() {
    	this.multiImageInfo = null;
    	this.bitmapInfo = null;
    	this.maskInfo = null;
    	this.uriInfo = null;
    }
    
    public ImagePropertyInfo(IPropertySource ps,
    		String filePropertyId,
    		String imagePropertyId, 
    		String maskPropertyId, 
    		String uriPropertyId) {
    	finishLoad(ps, filePropertyId, imagePropertyId, maskPropertyId, uriPropertyId);
    }

	/**
     * This constructor generates the ImagePropertyInfo using a known image
     * container and image infos.
     * @param multiImageInfo
     * @param imageInfo
     * @param maskInfo
     */
    public ImagePropertyInfo(MultiImageInfo multiImageInfo, ImageInfo imageInfo, ImageInfo maskInfo) {
        this.multiImageInfo = multiImageInfo;
        this.bitmapInfo = imageInfo;
        this.maskInfo = maskInfo;
    }
    
    /**
     * This constructor generates the ImagePropertyInfo using a known image
     * container and property paths for the image/mask pair.
     * @param multiImageInfo
     * @param ps 
     * @param imagePropertyId
     * @param maskPropertyId
     * @param uriPropertyId
     */
    public ImagePropertyInfo(MultiImageInfo multiImageInfo,
    		IPropertySource ps,
    		String imagePropertyId, String maskPropertyId, String uriPropertyId) {
        this.multiImageInfo = multiImageInfo;
        if (multiImageInfo != null)
        	this.bmpfile = multiImageInfo.getBinaryFile().toOSString();
        finishLoad(ps, null, imagePropertyId, maskPropertyId, uriPropertyId);
    }
    

	public ImagePropertyInfo(URIImageInfo uriInfo) {
		this.uriInfo = uriInfo;
	}

	private void finishLoad(IPropertySource ps,
    		String filePropertyId,
    		String imagePropertyId,
    		String maskPropertyId,
    		String uriPropertyId) {
		if (ps == null)
			return;

		if (multiImageInfo == null && filePropertyId != null) {
			EObject object = ((IPropertyInformation) ps).getPropertyOwner(null);
			if (object != null) {
				IProjectImageInfo info = ModelUtils.getProjectImageInfo(object);
				if (info instanceof ProjectImageInfo) {
					
					String file = (String) ps.getPropertyValue(filePropertyId);
			        if (file == null) {
			            reportMissingProperty(filePropertyId);
			            file = ""; //$NON-NLS-1$
			        }
			        this.bmpfile = file;
			        
			        // clean up property
			        file = file.replaceAll("\\\\\\\\", "\\\\"); //$NON-NLS-1$ //$NON-NLS-2$
			        
			        this.multiImageInfo = ((ProjectImageInfo) info).getInfoForBinaryFile(new Path(file));
				}
			}
		} else {
			this.bmpfile = ""; //$NON-NLS-1$
		}
		
		String id; //$NON-NLS-1$
		if (imagePropertyId != null) {
	        id = (String) ps.getPropertyValue(imagePropertyId);
	        if (id == null) {
	        	reportMissingProperty(imagePropertyId);
	            id = ""; //$NON-NLS-1$
	        }
	        this.bmpid = id;
	        if (multiImageInfo != null)
	        	this.bitmapInfo = multiImageInfo.findImageByEnumerator(id);
		} else {
			this.bmpid = ""; //$NON-NLS-1$
		}

		if (maskPropertyId != null) {
	        id = (String) ps.getPropertyValue(maskPropertyId);
	        if (id == null) {
	        	reportMissingProperty(maskPropertyId);
	            id = ""; //$NON-NLS-1$
	        }
	        this.bmpmask = id;
	        if (multiImageInfo != null)
	            this.maskInfo = multiImageInfo.findImageByEnumerator(id);
		} else {
			this.bmpmask = ""; //$NON-NLS-1$
		}
		
        // check for URI on UIQ models
        id = ""; //$NON-NLS-1$
        if (uriPropertyId != null) {
        	id = (String) ps.getPropertyValue(uriPropertyId);
	        if (id == null) {
	        	if (ps instanceof IPropertyInformation && 
	        			((IPropertyInformation) ps).getPropertyTypeName(null).equals(
	        					UIQModelUtils.UIQ_IMAGE_PROPERTY_TYPE)) {
	        		reportMissingProperty(uriPropertyId);
	        	}
	            id = ""; //$NON-NLS-1$
	        }
        }
        this.uri = id;
        if (uri != null && uri.length() > 0) {
        	EObject object = ((IPropertyInformation) ps).getPropertyOwner(null);
        	this.uriInfo = new URIImageInfo((ProjectImageInfo) ModelUtils.getProjectImageInfo(object), uri);
        	this.bitmapInfo = null;
        	this.maskInfo = null;
        	this.multiImageInfo = null;
        }

    }

	private void reportMissingProperty(String propertyId) {
		Logging.log(SymbianPlugin.getDefault(),
		        Logging.newStatus(SymbianPlugin.getDefault(), IStatus.ERROR, 
		                "Cannot find '" + propertyId + "' property, resetting", //$NON-NLS-1$ //$NON-NLS-2$
		                new IllegalArgumentException()));
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    	// default impl until arrays support cell editors on elements
    	return getText();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ImagePropertyInfo))
            return false;
        ImagePropertyInfo oldData = (ImagePropertyInfo) obj;
        if ((multiImageInfo == null) != (oldData.multiImageInfo == null))
            return false;
        if (multiImageInfo != null && !multiImageInfo.getSourceFile().equals(oldData.multiImageInfo.getSourceFile()))
            return false;
        if ((bitmapInfo == null) != (oldData.bitmapInfo == null))
            return false;
        if (bitmapInfo != null && !bitmapInfo.getFilePath().equals(oldData.bitmapInfo.getFilePath()))
            return false;
        if ((maskInfo == null) != (oldData.maskInfo == null))
            return false;
        if (maskInfo != null && !maskInfo.getFilePath().equals(oldData.maskInfo.getFilePath()))
            return false;
        if ((uriInfo == null) != (oldData.uriInfo == null))
        	return false;
        if (uriInfo != null && !uriInfo.getPropertyString().equals(oldData.uriInfo.getPropertyString()))
        	return false;
        return true;
    }

    /*
    public void save(IPropertySource ps) {
    	String imagePropertyUriSubproperty = null;
    	IPropertyDescriptor[] propertyDescriptors = ps.getPropertyDescriptors();
    	for (IPropertyDescriptor propertyDescriptor : propertyDescriptors) {
    		if (propertyDescriptor.getId().equals("uri")) { //$NON-NLS-1$
    			imagePropertyUriSubproperty = "uri"; //$NON-NLS-1$
    			break;
    		}
    	}
    	save(ps, IMAGE_PROPERTY_MULTI_IMAGE_FILE_SUBPROPERTY, 
    			IMAGE_PROPERTY_IMAGE_ID_SUBPROPERTY, 
    			IMAGE_PROPERTY_MASK_ID_SUBPROPERTY, 
    			imagePropertyUriSubproperty);
    }*/
    
    public static void save(ImagePropertyInfo info,
    		IPropertySource ps,
    		String filePropertyId,
    		String imagePropertyId,
    		String maskPropertyId,
    		String uriPropertyId) {
       	String file = ""; //$NON-NLS-1$
    	String bmpId = ""; //$NON-NLS-1$
    	String maskId = ""; //$NON-NLS-1$
    	String theUri = ""; //$NON-NLS-1$
    	
        if (uriPropertyId != null && info.uriInfo != null) {
        	// favor URI over other forms, clearing out the triple if URI is set 
        	theUri = info.uriInfo.getPropertyString();
        } else if (uriPropertyId != null && info.uri != null && info.uri.length() > 0) {
        	// favor URI over other forms, clearing out the triple if URI is set
        	theUri = info.uri; 
        } 
        if (theUri.length() == 0) {
        	// old tri-field format
			file = info.multiImageInfo != null 
	        			? info.multiImageInfo.getBinaryFile().toOSString() 
	        			: info.bmpfile;
			bmpId = info.bitmapInfo != null 
	        			? info.bitmapInfo.getImageEnumeration() 
	        			: info.bmpid;
	        if (info.maskInfo != null) {
	        	// bitmap mask
	        	maskId = info.maskInfo.getImageEnumeration();
	        } else {
	        	maskId = info.bmpmask;
	        	if (info.isSVG() && info.getMaskDepth() > 0) {
	        		// the mask is implicit and the enum exists if the mask depth is nonzero
	        		maskId = info.bitmapInfo.getImageEnumeration() + "_mask"; //$NON-NLS-1$
	        	}
	        }
        }
        
        // don't save filename for empty image, unless just saving the file
        if (imagePropertyId != null && maskPropertyId != null) {
	        if ((bmpId == null || bmpId.length() == 0) && (maskId == null || maskId.length() == 0))
	        	file = ""; //$NON-NLS-1$
        }
        if (filePropertyId != null) {
        	ps.setPropertyValue(filePropertyId, file);
        }
        if (imagePropertyId != null) {
        	ps.setPropertyValue(imagePropertyId, bmpId);
        }
        if (maskPropertyId != null) {
        	ps.setPropertyValue(maskPropertyId, maskId);
        }
        if (uriPropertyId != null) {
        	ps.setPropertyValue(uriPropertyId, theUri);
        }
    }
    
    public void save(IPropertySource ps,
    		String filePropertyId,
    		String imagePropertyId,
    		String maskPropertyId,
    		String uriPropertyId) {
    	save(this, ps, filePropertyId, imagePropertyId, maskPropertyId, uriPropertyId); 
    }

	/**
	 * Get the text, as used by a label provider.  This describes
	 * the source info, if possible, else the property info.
	 * @return text, never null
	 */
	public String getText() {
        String text = Messages.getString("ImagePropertyInfo.NoImage"); //$NON-NLS-1$
        
        if (isEmptyImage()) {
        	// keep 'no image'
        } else if (isUnknownImage()) {
        	String unknown = null;
        	if (uri != null)
        		unknown = uri;
        	else if (bmpid != null)
	        	unknown = bmpid;
	        else if (bmpmask != null)
	        	unknown = bmpmask;
	        else
	        	unknown = bmpfile;

            if (unknown != null) {
            	text = MessageFormat.format(
            			Messages.getString("ImagePropertyInfo.UnknownImage"),  //$NON-NLS-1$
            			new Object[] { unknown });
            } 
            // else, should be an error, but let's be more forgiving in this call

        } else {
        	if (uriInfo != null) 
        		text = uriInfo.getPropertyString();
        	else if (bitmapInfo != null)
	        	text = bitmapInfo.getFilePath();
	        else if (maskInfo != null)
	        	text = maskInfo.getFilePath();
	        else if (multiImageInfo != null)
	    		text = multiImageInfo.getSourceFile();
        }
        
        return MessageFormat.format(
                    Messages.getString("ImagePropertyInfo.BracketedString"),  //$NON-NLS-1$
                    new Object[] { text });
	}

	/**
	 * Tell whether the image is empty, i.e. never set.
	 * @return
	 */
	public boolean isEmptyImage() {
		if (uri == null && bmpfile == null && bmpid == null && bmpmask == null) {
			return uriInfo == null && multiImageInfo == null && bitmapInfo == null && maskInfo == null;
		}
		return (uri == null || uri.length() == 0) && 
				(bmpfile == null || bmpfile.length() == 0) && 
				(bmpid == null || bmpid.length() == 0) && 
				(bmpmask == null || bmpmask.length() == 0);
	}

	/**
	 * Tell whether the bitmap is missing, i.e., specified but not found.
	 * @return
	 */
	public boolean isMissingBitmap() {
		return bmpid != null && bmpid.length() > 0 && bitmapInfo == null;
	}

	/**
	 * Tell whether the bitmask is missing, i.e., specified but not found.
	 * For SVG images, the mask property is visible but the info is implicit.
	 * @return
	 */
	public boolean isMissingMask() {
		if (bitmapInfo != null && bitmapInfo.isSVG())
			return false;
		return bmpmask != null && bmpmask.length() > 0 && maskInfo == null;
	}

	/**
	 * Tell whether the multi bitmap file is missing, i.e., specified but not found.
	 */
	public boolean isMissingImageFile() {
		return bmpfile != null && bmpfile.length() > 0 && multiImageInfo == null;
	}
	
	/**
	 * Tell whether the image is set but not found.
	 * @return
	 */
	public boolean isUnknownImage() {
		return !isEmptyImage() 
			&& (isMissingBitmap() || isMissingImageFile() || isMissingMask());
	}
	
	// new for Shiner
	/** Tell whether the image is SVG */
	public boolean isSVG() {
		return bitmapInfo != null && bitmapInfo.isSVG();
	}
	
	/** Provide the actual mask depth, even for SVG */
	public int getMaskDepth() {
		if (maskInfo != null)
			return maskInfo.getBitDepth();
		return 0;
	}

	/** Tell whether the mask is present (and used) */
	public boolean isMasked() {
		return maskInfo != null && maskInfo.getFilePath() != null && maskInfo.getFilePath().length() > 0;
	}
	
	/** Tell whether the image property has a URI */
	public boolean isURI() {
		return uriInfo != null;
	}
	
	/** Tell whether the URI is set but not found. */
	public boolean isMissingURI() {
		if (uriInfo == null) {
			return uri != null && uri.length() > 0;
		}
		// see if the URI is resolvable to something
		return uriInfo.getImageModel() == null;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.images.IImagePropertyInfo#getIncludeFilename()
	 */
	public String getIncludeFilename() {
		if (multiImageInfo != null)
			return multiImageInfo.getIncludeFilename();
		return null;
	}

	/**
	 * @return
	 */
	public ImageFormat getImageFormat() {
		if (bitmapInfo != null && maskInfo != null)
			return new ImageFormat(bitmapInfo.isColor(), bitmapInfo.getBitDepth(), 
					maskInfo.getBitDepth());
		else if (bitmapInfo != null)
			return new ImageFormat(bitmapInfo.isColor(), bitmapInfo.getBitDepth());
		else
			return new ImageFormat(true, 8, 1);
	}
}