/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.tests;

import com.nokia.carbide.cdt.builder.DefaultViewParserConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.*;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.*;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.*;

import java.io.File;
import java.util.*;

/**
 * This class runs as a Java application and will copy all the referenced files
 * from a given bld.inf to another location.
 * <p>
 * Run with args:  [-all] &lt;source bld.inf&gt; &lt;target directory&gt;
 * <p>
 * "-all" will invoke with an AllNodesViewFilter.
 *
 */
public class BldInfFilesGatherer {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		IViewFilter filter = new AcceptedNodesViewFilter();
		
		if (args.length < 2) {
			System.err.println("Run as: [-all] <source bld.inf> <target directory>");
			System.exit(1);
		}
		
		int idx = 0;
		if (args[idx].equals("-all")) {
			filter = new AllNodesViewFilter();
		}
		
		final IPath cwd = new Path(new File(".").getAbsolutePath());
		
		IPath modelPath = new Path(new File(args[idx++]).getAbsolutePath());
		File destination = new File(args[idx++]);
		
		IModelProvider<IBldInfOwnedModel, IBldInfModel> modelProvider = ModelProviderFactory.createModelProvider(new BldInfModelFactory());
		IBldInfModel model = modelProvider.getSharedModel(modelPath);
		
		final IViewFilter filter_ = filter;
		final TrackingIncludeFileLocator trackingIncludeFileLocator =
			new TrackingIncludeFileLocator(null, null);
		
		IViewConfiguration config = new IViewConfiguration() {

			DefaultViewParserConfiguration defaultViewParserConfiguration;
			{
				defaultViewParserConfiguration =
					new DefaultViewParserConfiguration(cwd);
				defaultViewParserConfiguration.setIncludeFileLocator(trackingIncludeFileLocator);
			
			}
			public Collection<IDefine> getMacros() {
				return Collections.EMPTY_LIST;
			}

			public IViewFilter getViewFilter() {
				return filter_;
			}

			public IViewParserConfiguration getViewParserConfiguration() {
				return defaultViewParserConfiguration;
			}
			
		};
		
		// create a view to get the includes populated
		IBldInfView view = model.createView(config);
		for (IMessage message : view.getMessages()) {
			System.err.println(message);
		}
		
		CommonPathFinder finder = new CommonPathFinder();
		ArrayList<File> files = new ArrayList<File>(trackingIncludeFileLocator.getLocatedFiles());
		files.add(new File(modelPath.toOSString()));
		for (File file : files) {
			finder.addFile(new Path(file.getAbsolutePath()));
			//System.out.println(file);
		}
		
		IPath prefix = finder.getCommonPath();
		destination.mkdirs();
		
		for (File file : files) {
			IPath relPath = new Path(file.getAbsolutePath()).removeFirstSegments(prefix.segmentCount()).setDevice(null);
			File targFile = new File(destination, relPath.toOSString());
			System.out.println(file + " -> " + targFile);
			targFile.getParentFile().mkdirs();
			FileUtils.copyFile(file, targFile);
		}
		
	}

}
