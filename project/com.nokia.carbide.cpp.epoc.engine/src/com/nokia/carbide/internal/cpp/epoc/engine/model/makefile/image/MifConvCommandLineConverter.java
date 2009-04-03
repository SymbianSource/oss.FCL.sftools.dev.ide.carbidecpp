/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.model.makefile.image;

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.image.*;
import com.nokia.carbide.cpp.epoc.engine.model.EGeneratedHeaderFlags;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.ArgList;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.*;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Convert MIFCONV command lines into image build containers (and v.v.)
 *
 */
public class MifConvCommandLineConverter implements
		IImageBuilderCommandLineConverter {

	static final Pattern HEADER_PATTERN = Pattern.compile("/H(.*)", //$NON-NLS-1$
			Pattern.CASE_INSENSITIVE);
	static final Pattern PARAM_FILE_PATTERN = Pattern.compile("/F(.*)", //$NON-NLS-1$
			Pattern.CASE_INSENSITIVE);
	static final Pattern EXTENSIONS_PATTERN = Pattern.compile("/E", //$NON-NLS-1$
			Pattern.CASE_INSENSITIVE);
	private static final Pattern SVG_EXTENSION_PATTERN = Pattern.compile("svgt?", //$NON-NLS-1$
			Pattern.CASE_INSENSITIVE);
	private static final Pattern BITMAP_EXTENSION_PATTERN = Pattern.compile("bmp", //$NON-NLS-1$
			Pattern.CASE_INSENSITIVE);
	private static final String EPOCROOT_SUBSTITUTION = "$(EPOCROOT)"; //$NON-NLS-1$
	private static final String ZDIR_SUBSTITUTION = "$(ZDIR)"; //$NON-NLS-1$
	private static final String ZDIR_CONTENTS = "epoc32\\data\\z"; //$NON-NLS-1$
	
	// tracked during parsing (non-reentrant!)
	private ImageFormat imageFormat;
	private IImageMakefileViewConfiguration config;

	public MifConvCommandLineConverter() {
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageBuilderCommandLineConverter#parse(com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileView, java.util.List)
	 */
	public IMultiImageSource parse(IImageMakefileView view, List<String> argv) {
		setup(view);
		
		
		ListIterator<String> iter = argv.listIterator();
		if (!iter.hasNext()) {
			// no program???
			return null;
		}
		iter.next();
		
		// get target filename
		if (!iter.hasNext()) {
			// no target???
			return null;
		}
		String target = iter.next();
		
		// convert the full filesystem path to the expected target z:\ relative path.
		boolean epocRootVariableRelative = false;
		if (target.startsWith(EPOCROOT_SUBSTITUTION)) {
			target = target.substring(EPOCROOT_SUBSTITUTION.length());
			epocRootVariableRelative = true;
		}
		if (target.startsWith(ZDIR_SUBSTITUTION)) {
			target = target.substring(ZDIR_SUBSTITUTION.length());
			epocRootVariableRelative = true;
		}
		else if (target.length() >= ZDIR_CONTENTS.length() 
				&& target.substring(0, ZDIR_CONTENTS.length()).equalsIgnoreCase(ZDIR_CONTENTS)) {
			target = target.substring(ZDIR_CONTENTS.length());
			epocRootVariableRelative = true;
		}
		//IPath targetFilePath = new Path(target).makeRelative();
		IPath targetFilePath = FileUtils.createPossiblyRelativePath(target);
		if (epocRootVariableRelative) {
			targetFilePath = targetFilePath.makeRelative();
		}
		
		IMultiImageSource multiImageSource = view.createMultiImageSource();
		multiImageSource.setHeaderFlags(EGeneratedHeaderFlags.NoHeader);

		multiImageSource.setTargetPath(targetFilePath.removeLastSegments(1));
		multiImageSource.setTargetFile(targetFilePath.lastSegment());
		
		imageFormat = null;
		parseOptions(view, multiImageSource, iter);
		
		return multiImageSource;
	}

	/**
	 * @param view
	 */
	private void setup(IImageMakefileView view) {
		this.config = (IImageMakefileViewConfiguration) view.getViewConfiguration();
	}

	private void parseOptions(IImageMakefileView view, IMultiImageSource multiImageSource, ListIterator<String> iter) {
		// check for options
		while (iter.hasNext()) {
			String arg = iter.next();
			Matcher matcher;
			
			// check if a header is created
			matcher = HEADER_PATTERN.matcher(arg);
			if (matcher.matches()) {
				multiImageSource.setHeaderFlags(EGeneratedHeaderFlags.Header);
				IPath headerPath = null;
				String headerName = matcher.group(1);
				if (headerName.startsWith(EPOCROOT_SUBSTITUTION)) {
					headerName = headerName.substring(EPOCROOT_SUBSTITUTION.length());
					headerPath = FileUtils.createPossiblyRelativePath(headerName);
				} else {
					headerPath = fromMakefileToProjectPath(view, FileUtils.createPossiblyRelativePath(headerName));
				}
				
				// ignore if default
				IPath defaultHeaderFilePath = multiImageSource.getDefaultGeneratedHeaderFilePath();
				if (!headerPath.equals(defaultHeaderFilePath))
					multiImageSource.setGeneratedHeaderFilePath(headerPath);
				continue;
			}
			
			// check for parameter file list 
			matcher = PARAM_FILE_PATTERN.matcher(arg);
			if (matcher.matches()) {
				List<String> params = readParamFile(view, matcher.group(1));
				parseOptions(view, multiImageSource, params.listIterator());
				continue;
			}
			
			// check for image sources
			if (ImageFormat.matches(arg)) {
				// /OPT filename
				imageFormat = new ImageFormat(arg);
				continue;
			}

			// unused option
			if (arg.startsWith("/")) //$NON-NLS-1$
				continue;
			
			// if image format not set, unknown garbage
			if (imageFormat == null)
				continue;
			
			// else, image file
			String file = arg;
			IPath filePath = fromMakefileToProjectPath(view, FileUtils.createPossiblyRelativePath(file));
			IImageSource source = null;
			if (filePath.getFileExtension() == null 
					|| SVG_EXTENSION_PATTERN.matcher(filePath.getFileExtension()).matches()) {
				ISVGSource svgSource = multiImageSource.createSVGSource();
				if (svgSource == null)
					continue;
				svgSource.setColor(imageFormat.isColor());
				svgSource.setDepth(imageFormat.getDepth());
				svgSource.setMaskDepth(imageFormat.getMaskDepth());
				source = svgSource;
			} else if (BITMAP_EXTENSION_PATTERN.matcher(filePath.getFileExtension()).matches()) {
				IBitmapSource bmpSource = multiImageSource.createBitmapSource();
				if (bmpSource == null)
					continue;
				bmpSource.setColor(imageFormat.isColor());
				bmpSource.setDepth(imageFormat.getDepth());
				bmpSource.setMaskDepth(imageFormat.getMaskDepth());
				
				// no mask file specified; it's implicit
				source = bmpSource;
			}
			if (source == null) {
				continue;
			}
			
			source.setPath(filePath);
			multiImageSource.getSources().add(source);
		}		
	}

	/**
	 * Read more parameters from the given file.
	 * @param string
	 * @return non-null list of arguments
	 */
	private List<String> readParamFile(IImageMakefileView view, String filename) {
		IPath filePath = FileUtils.createPossiblyRelativePath(filename);
		if (!filePath.isAbsolute() && !filePath.toString().startsWith("$")) {
			filePath = view.getModelPath().removeLastSegments(1).append(filePath);
		}
		File file = filePath.toFile();
		try {
			String text = new String(FileUtils.readFileContents(file, null));
			// apparently mifconv has a poor parser here.
			// It expects/allows catenated lines, but inserts a space between lines.
			// It also allows spaces after the backslash.
			text = text.replaceAll("\\\\\\s*(\r|\n|\r\n|$)", " "); //$NON-NLS-1$ //$NON-NLS-2$
			ArgList list = new ArgList(text);
			return list.getArgv();
		} catch (CoreException e) {
			return Collections.EMPTY_LIST;
		}
	}

	private IPath fromMakefileToProjectPath(IImageMakefileView view, IPath filePath) {
		return view.convertModelToProjectPath(filePath);
	}

	private IPath fromProjectToMakefilePath(IImageMakefileView view, IPath projectPath) {
		return view.convertProjectToModelPath(projectPath);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageBuilderCommandLineConverter#create(com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileView, com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource, java.util.List)
	 */
	public List<String> create(IImageMakefileView view,
			IMultiImageSource multiImageSource,
			List<String> origArgv) {
		if (!multiImageSource.isValid()) {
			EpocEnginePlugin.log(new IllegalArgumentException("Not updating/writing commands for invalid multi-image source: "  //$NON-NLS-1$
					+ multiImageSource));
			return origArgv;
		}
		
		if (origArgv != null)
			origArgv = new ArrayList<String>(origArgv);
		
		setup(view);
		
		List<String> args = new ArrayList<String>();
		String arg;
		IPath file;
		
		// command
		if (origArgv == null) {
			arg = view.unexpandMacros(config.getImageBuilderName(), true);
			args.add(arg);
		} else {
			args.add(origArgv.remove(0));
		}
		
		// target 
		arg = ((ImageMakefileView) view).getUnexpandedMultiImageSourceTargetPath(multiImageSource);
		args.add(arg);
		if (origArgv != null)
			origArgv.remove(0);
		
		boolean needNewLine = true;
		
		// header 
		if (multiImageSource.getHeaderFlags() != EGeneratedHeaderFlags.NoHeader) {
			IPath generatedHeaderFilePath = multiImageSource.getGeneratedHeaderFilePath();
			if (generatedHeaderFilePath == null) {
				generatedHeaderFilePath = multiImageSource.getDefaultGeneratedHeaderFilePath();
			}
			if (!generatedHeaderFilePath.isAbsolute() && !generatedHeaderFilePath.toString().startsWith(EPOCROOT_SUBSTITUTION)) {
				generatedHeaderFilePath = new Path(EPOCROOT_SUBSTITUTION + generatedHeaderFilePath.toOSString());
			}
			file = fromProjectToMakefilePath(view, generatedHeaderFilePath);
			
			arg = "/H" + view.unexpandMacros(file.toOSString(), //$NON-NLS-1$
					true);
			if (needNewLine)
				addNewLine(view, args);
			args.add(arg);
			needNewLine = true;
		}
		
		if (origArgv != null) {
			// remove existing header arg if present
			removeArgMatching(origArgv, "(?i)/H.*"); //$NON-NLS-1$
			
			// remove any /F entries: we don't update parameter files
			removeArgMatching(origArgv, "(?i)/F.*"); //$NON-NLS-1$

			// keep remaining arguments, except for files
			for (Iterator<String> iter = origArgv.iterator(); iter.hasNext();) {
				String origArg = iter.next();
				if (origArg.matches("(?i)/[a-bd-z].*")) { //$NON-NLS-1$
					args.add(origArg);
					iter.remove();
				}
			}			
		}
		
		// files
		imageFormat = null;
		for (IImageSource source : multiImageSource.getSources()) {
			addNewLine(view, args);
			
			boolean explicitMaskPath = false;
			
			// the mask file is not emitted unless it's not default, 
			// in which case we need to split it out
			int maskDepth = 0;
			IPath maskPath = null;
			if (source instanceof IBitmapSource) {
				maskDepth = ((IBitmapSource) source).getMaskDepth();
				if (maskDepth > 0) {
					maskPath = ((IBitmapSource) source).getMaskPath();
					if (maskPath == null || 
							maskPath.equals(source.getDefaultMaskPath())) {
						maskPath = null;
					} else {
						explicitMaskPath = true;
					}
				}
			} else if (source instanceof ISVGSource) {
				// need to emit the number (32,8) so the mask enum is generated
				maskDepth = ((ISVGSource) source).getMaskDepth();
			}
			
			// emit new image format if necessary
			ImageFormat format = new ImageFormat(source.isColor(), source.getDepth(), 
					explicitMaskPath ? 0 : maskDepth);
			if (imageFormat == null || !format.equals(imageFormat)) {
				imageFormat = format;
				args.add("/" + format.toString()); //$NON-NLS-1$
			}
			
			// emit source file
			file = fromProjectToMakefilePath(view, source.getPath());
			
			// can't emit a drive letter since mifconv (passing through to bmconv) is broken
			if (source instanceof IBitmapSource) {
				file = file.setDevice(null);
			}
			args.add(view.unexpandMacros(file.toOSString(), false));
			
			// emit explicit mask path if needed
			if (explicitMaskPath) {
				file = fromProjectToMakefilePath(view, maskPath);
				args.add("/" + maskDepth); //$NON-NLS-1$
				args.add(view.unexpandMacros(file.setDevice(null).toOSString(), false));
			}
		}
		
		return args;
	}

	private void removeArgMatching(List<String> args, String pattern) {
		for (Iterator<String> iter = args.iterator(); iter.hasNext();) {
			String arg = iter.next();
			if (arg.matches(pattern))
				iter.remove();
		}
	}

	/**
	 * @param args
	 */
	private void addNewLine(IImageMakefileView view, List<String> args) {
		// add arg and a newline
		args.add(" \\" + view.getEOL() + "\t\t"); //$NON-NLS-1$ //$NON-NLS-2$
	}

}
