/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.sdt.utils.svg;

import java.io.File;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.utils.ImageUtils;
import com.nokia.sdt.utils.UtilsPlugin;

/**
 * This class manages a reference to a loaded SVG document
 * and provides SWT Images from it
 *
 */
public class SVGLoader {

	/** Pattern to match sizes which convert roughly to pixels */
	private static Pattern sizePattern = Pattern.compile("(\\d+)(pt|px)?"); //$NON-NLS-1$

    private SWTImageTranscoder trans;
    private Document document;
    private String uri;

	private Device device;

    public SVGLoader(File file) {
        try {
            this.uri = file.toURL().toString();
        } catch (MalformedURLException e) {
            Check.failedArg(e);
        }
    }
    
    
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SVGLoader other = (SVGLoader) obj;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}



	/**
     * @deprecated 
     */
    public SVGLoader(Device device, File file) {
    	this(file);
    	this.device = device;
    }

    /**
     * Create an SVG document from an absolute file. 
     * @return SVG XML DOM
     */
    private Document getSvgDomFromFile(String uri) throws Exception {
        Check.checkArg(uri);
        Document mydoc = null;
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
        mydoc = f.createDocument(uri);
        return mydoc;
    }

    /**
     * @deprecated
     */
    public Image getImage(Point size) throws CoreException {
    	ImageData imageData = getImageData(size);
    	if (device == null || imageData == null)
    		return null;
		return new Image(device, imageData);
    }
    
    /**
     * Transcode and create an image from the SVG.
     * @param size the size to scale to, or null
     * @return new ImageData
     */
	public ImageData getImageData(Point size) throws CoreException {
		try {
			if (document == null) {
				document = getSvgDomFromFile(uri);
			}
			TranscoderInput transcoderInput = new TranscoderInput(document);
			transcoderInput.setURI(uri);
			return load(transcoderInput, size);
		} catch (Exception e) {
			if (e instanceof TranscoderException) {
				TranscoderException tce = (TranscoderException) e;
				if (tce.getException() != null)
					e = tce.getException();
			}
			throw new CoreException(Logging.newStatus(UtilsPlugin.getDefault(),
					e));
		}
	}

    private ImageData load(TranscoderInput input, Point size)
            throws TranscoderException {

    	//System.out.println("loading svg " + uri + " at " + size);
    	
        // create the transcoder output
        TranscoderOutput output = null;

        // create a transcoder
        trans = new SWTImageTranscoder();

        // be sure to execute script code every time
        trans.addTranscodingHint(ImageTranscoder.KEY_EXECUTE_ONLOAD,
                new Boolean(true));

        //trans.addTranscodingHint(SWTImageTranscoder.KEY_USE_TRANSPARENT_PIXEL,
        //        new Boolean(true));

        Document doc = input.getDocument();
        if (doc == null)
            return null;

        Point defaultSize = readDefaultSize(doc);
        
        if (size == null || (size.x < 0 && size.y < 0)) {
            // Provide a hard cap on the size of ImageData provided for the default size (bug 5363).
        	// Let explicit requests for the larger sizes succeed and fail.
            if ((long)defaultSize.x * defaultSize.y > 2048 * 2048) {
            	// 2048*2048*32bpp = 16 megs!
            	defaultSize = ImageUtils.scaleSizeToSize(defaultSize, new Point(2048, 2048));
            }
        	size = defaultSize;
        } else if (size.x < 0 && size.y > 0) {
        	// aspect with y set
        	size = new Point(defaultSize.x * size.y / defaultSize.y, size.y);
        } else if (size.x > 0 && size.y < 0) {
        	// aspect with x set
        	size = new Point(size.x, defaultSize.y * size.x / defaultSize.x);
        } else  {
        	// non-aspect 
        }
        
        //System.out.println("dims: "+theWidth+","+theHeight);
        if (size != null && size.x > 0) {
            //trans.addTranscodingHint(ImageTranscoder.KEY_WIDTH, new Float(
            //		size.x));
            trans.addTranscodingHint(ImageTranscoder.KEY_MAX_WIDTH, new Float(
            		size.x));
        }
        if (size != null && size.y > 0) {
            //trans.addTranscodingHint(ImageTranscoder.KEY_HEIGHT, new Float(
            //    size.y));
            trans.addTranscodingHint(ImageTranscoder.KEY_MAX_HEIGHT, new Float(
                    size.y));
        }

        // save the image
        trans.transcode(input, output);

        ImageData imageData = trans.getImageData();
        
        // if the size is not what we expected, force it
        // (we may desire a different aspect ratio)
        /*
        Display display = Display.getDefault();
        ImageDump dump = new ImageDump(new Shell(display), new Image(display, imageData));
        dump.open();
        while (display.readAndDispatch()) {
        	
        }
        */
        
        if (imageData != null && size.x > 0 && size.y > 0) {
        	if (imageData.width != size.x || imageData.height != size.y) {
        		imageData = ImageUtils.scaleImageData(imageData, size, false, false);
        	}
        }
        
        return imageData;
    }

    /**
     * Read the size of the image as specified in the SVG.
     * @param doc
     * @return Point where x or y is -1 for the default size.
     */
	private Point readDefaultSize(Document doc) {
		Point size = new Point(-1, -1);
        Element root = doc.getDocumentElement();
        if (root != null) {
        	// the width and height should be trusted first (bug 5363)
        	//
            // This may be an absolute value, assumed to be pixels,
            // or a percentage or a value with units.  Only accept
            // it if it parses cleanly as an int, meaning pixels.
            String width = root.getAttributeNS(null, "width"); //$NON-NLS-1$
            String height = root.getAttributeNS(null, "height"); //$NON-NLS-1$
            if (width != null) {
            	Matcher matcher = sizePattern.matcher(width);
            	if (matcher.matches())
            		size.x = Integer.parseInt(matcher.group(1));
            }
            if (height != null) {
            	Matcher matcher = sizePattern.matcher(height);
            	if (matcher.matches())
            		size.y = Integer.parseInt(matcher.group(1));
            }
        	
            // try the viewBox
            if (size.x < 0 || size.y < 0) {
	            String viewBox = root.getAttributeNS(null, "viewBox"); //$NON-NLS-1$
	            if (viewBox != null) {
	                String number = "(-?\\d+(\\.\\d+)?)"; //$NON-NLS-1$
	                Pattern pattern = Pattern.compile(
	                        "\\s*" + number +  //$NON-NLS-1$
	                        ",?\\s+" + number + //$NON-NLS-1$
	                        ",?\\s+" + number +  //$NON-NLS-1$
	                        ",?\\s+" + number + //$NON-NLS-1$
	                        "\\s*"); //$NON-NLS-1$
	                Matcher matcher = pattern.matcher(viewBox);
	                if (matcher.matches()) {
	                    size.x = (int)Math.round(Double.parseDouble(matcher.group(5)));
	                    size.y = (int)Math.round(Double.parseDouble(matcher.group(7)));
	                }
	            }

            }
        }
		return size;
	}

}