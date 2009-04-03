/*
* Copyright (c) 2007-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.sdk.examples.jobs;

import com.nokia.carbide.cdt.builder.*;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.*;
import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.*;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.*;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.sdk.examples.Activator;
//import com.sun.org.apache.html.internal.dom.HTMLDOMImplementationImpl;
import com.sun.org.apache.xml.internal.serialize.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.*;
import org.eclipse.ui.part.FileEditorInput;
import org.osgi.framework.Bundle;
import org.w3c.dom.*;
import org.w3c.dom.html.HTMLDOMImplementation;
import org.w3c.dom.html.HTMLDocument;

import java.io.*;
import java.text.MessageFormat;
import java.util.List;

/**
 * This job examines the project and generates the HTML report.
 * 
 * The interesting places to look at are:
 * - emitBuildConfigurations() accesses the list of build configurations in the project
 * - class BldInfEmitter accesses information from bld.inf
 * - class MMPEmitter accesses information from mmp files
 * 
 * Note that much of the bld.inf and mmp information could be obtained using
 * the EpocEngineHelper API. This class provides an example using the
 * epoc engine directly.
 *  
 */
public class ProjectReportJob extends WorkspaceJob {

	private final IProject project;
	private IProgressMonitor monitor;
	private HTMLDocument doc;
	
	// Location where the report is saved. We write it to the root
	// directory of the project.
	public static final String BASE_REPORT_NAME = "ProjectReport";
	public static final String MMP_EXTENSION = "mmp";
	
	private static class CanceledException extends RuntimeException {
		private static final long serialVersionUID = 5923961698970053040L;
	}
	
	public ProjectReportJob(IProject project) {
		super("");
		this.project = project;
		String fmt = "Creating report for project ''{0}''";
		setName(MessageFormat.format(fmt, project.getName()));
		setRule(project);
		setUser(true);		
	}
	
	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor)
			throws CoreException {
		this.monitor = monitor;
		IStatus status = Status.OK_STATUS;
		monitor.beginTask("Generating report", 100);
	
		try {
			initHTMLDocument();
			worked(1);
			emitDocument();
			
			worked(1);
			// no cancelling after this point
			saveDocument();
		} catch (IOException x) {
			status = statusFromException(x);
		} catch (CanceledException x) {
			status = Status.CANCEL_STATUS;
		} finally {
		}
		return status;
	}
	
	/**
	 * Creates an empty HTML document
	 * @throws IOException 
	 * @throws CoreException 
	 *
	 */
	private void initHTMLDocument() throws CoreException, IOException {
//		 HTMLDOMImplementation domImpl = HTMLDOMImplementationImpl.getHTMLDOMImplementation();
//         doc = domImpl.createHTMLDocument(project.getName());
         
         // install CSS styles
         NodeList nodes = doc.getElementsByTagName("head");
         if (nodes.getLength() > 0) {
        	 Node head = nodes.item(0);
        	 Element styleElement = doc.createElement("style");
        	 styleElement.setAttribute("type", "text/css");
        	 head.appendChild(styleElement);
        	 String styleSheet = readPluginFile(new Path("data/report.css"));
        	 styleElement.appendChild(doc.createTextNode(styleSheet));
         }
	}
	
	private String readPluginFile(IPath projRelativePath) throws CoreException, IOException {
		String result = null;
		Bundle bundle = Activator.getDefault().getBundle();
		InputStream is = FileLocator.openStream(bundle, projRelativePath, false);
		InputStreamReader reader = new InputStreamReader(is);
		char[] buf = new char[is.available()];
		int nread = reader.read(buf);
		if (nread > 0) {
			result = new String(buf, 0, nread);
		}
		return result;
	}
	
	private void emitDocument() {
		
		emitTag(doc.getBody(), "h2", "Project: "+project.getName(), null);
	
		emitBuildConfigurations();
		
		// We emit all the bld.inf and mmp information for each build configuration.
		// That will often have redundant information, but will reflect differences
		// due to macros.
		ICarbideProjectInfo info = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		List<ICarbideBuildConfiguration> buildConfigs = info.getBuildConfigurations();
		for (ICarbideBuildConfiguration config : buildConfigs) {
			String sectionTitle = "Build configuration: " + config.getDisplayString();
			emitTag(doc.getBody(), "h3", sectionTitle, null);
			BldInfEmitter bldInfEmitter = new BldInfEmitter(config);
			bldInfEmitter.emit();
			emitPara(null, null);
			emitTag(doc.getBody(), "hr", null, null);
			emitPara(null, null);
			
			worked(1);
		}		
	}
	
	/**
	 * Emits a table listing the build configurations in the project
	 */
	private void emitBuildConfigurations() {
		ICarbideProjectInfo info = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		List<ICarbideBuildConfiguration> buildConfigs = info.getBuildConfigurations();
	
		Element table = startTable("configs");
		Element row = startTableRow(table, null);
		emitTD(row, "Build Configurations", "header");
		int counter = 0;
		for (ICarbideBuildConfiguration config : buildConfigs) {
			String clsStyle = (counter & 1) != 0? "odd" : "even";
			row = startTableRow(table, null);
			emitTD(row, config.getDisplayString(), clsStyle);
			++counter;
		}
		emitPara(null, null);
		emitTag(doc.getBody(), "hr", null, null);
		emitPara(null, null);
		worked(1);
	}
	
	/**
	 * Save the in-memory document to an HTML file in the project.
	 * @throws IOException
	 * @throws CoreException
	 */
	private void saveDocument() throws IOException, CoreException {
		// pick a unique name for the report so we don't overwrite an existing file
		IFile reportFile = project.getFile(BASE_REPORT_NAME+".html");
		if (reportFile.exists()) {
			int counter = 1;
			while (true) {
				reportFile = project.getFile(BASE_REPORT_NAME+counter+".html");
				if (!reportFile.exists()) {
					break;
				}
				++counter;
			}
		}
		
		OutputFormat format = new OutputFormat(doc);
		format.setIndenting(true);
		format.setLineSeparator(System.getProperty("line.separator"));
		format.setPreserveSpace(false);
		format.setNonEscapingElements(new String[] {"STYLE"});
		
		XMLSerializer serializer =
			new XMLSerializer(new FileWriter(new File(reportFile.getLocationURI())),
					format);
		serializer.serialize(doc);
		reportFile.refreshLocal(IResource.DEPTH_ZERO, monitor);
		
		// Open the report in an editor window
		Display display = PlatformUI.getWorkbench().getDisplay();
		final FileEditorInput editorInput = new FileEditorInput(reportFile);
		display.asyncExec(new Runnable() {
			public void run() {
				IWorkbench workbench = PlatformUI.getWorkbench();
				IEditorDescriptor desc = workbench.getEditorRegistry().getDefaultEditor(editorInput.getName());
				IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
				try {
					page.openEditor(editorInput, desc.getId());
				} catch (PartInitException x) {
				}
			}
		});
	}

	private IStatus statusFromException(Exception x) {
		return new Status(IStatus.ERROR, Activator.PLUGIN_ID, 
				0, x.getLocalizedMessage(), x);
	}
	
	private Element emitTag(Node parent, String tag, String contents, String classAttr) {
		Element element = doc.createElement(tag);
		if (contents != null) {
			element.appendChild(doc.createTextNode(contents));
		}
		if (classAttr != null) {
			element.setAttribute("class", classAttr);
		}
		parent.appendChild(element);
		return element;
	}
	
	private Element emitPara(String contents, String classAttr) {
		return emitTag(doc.getBody(), "p", contents, classAttr);
	}
	
	private Element startTable(String classAttr) {
		Element table = emitTag(doc.getBody(), "table", null, classAttr);
		table.setAttribute("border", "0");
		table.setAttribute("cellspacing", "0");
		table.setAttribute("cellpadding", "0");
		table.setAttribute("width", "75%");
		return table;
	}
	
	private Element startTableRow(Element table, String classAttr) {
		return emitTag(table, "tr", null, classAttr);
	}
	
	private Element emitTD(Element tableRow, String text, String classAttr) {
		return emitTag(tableRow, "td", text, classAttr);
	}
	
	private void emitException(String context, Exception x) {
		String errStr = x != null? x.getLocalizedMessage() : "Unknown error";
		String msg = context + " : " + errStr;
		emitPara(msg, null);
	}
	
	private void worked(int amount) {
		monitor.worked(amount);
		if (monitor.isCanceled()) {
			throw new CanceledException();
		}
	}
	
	/**
	 * This class implements the IBldInfViewRunnable interface
	 * provided by the epoc engine. Using this in conjunction
	 * with the {@link EpocEnginePlugin#runWithBldInfView(IPath, IViewConfiguration, IBldInfViewRunnable)}
	 * provides an easy way to access the bld.inf contents without
	 * worrying about all the underlying details.
	 */
	private class BldInfEmitter implements IBldInfViewRunnable {
		
		private final ICarbideBuildConfiguration buildConfiguration;
		CoreException loadException;
		IMakMakeReference[] makMakeRefs;
		IMakMakeReference[] testMakMakeRefs;
		
		public BldInfEmitter(ICarbideBuildConfiguration config) {
			this.buildConfiguration = config;
		}

		public void emit() {
			ICarbideProjectInfo info = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			if (info != null) {
				IPath infPath = info.getProjectRelativeBldInfPath();
				if (infPath != null) {
					emitPara(infPath.toString(), null);
					IResource resource = project.findMember(infPath);
					IPath bldInfPath = resource.getFullPath();
					IViewConfiguration viewConfig = new DefaultViewConfiguration(info, buildConfiguration);
				
					EpocEnginePlugin.runWithBldInfView(bldInfPath, viewConfig, this);
					worked(1);
					
					// Emit information for all regular mmps. Extension makefiles
					// are ignored by this example code
					for (IMakMakeReference ref : makMakeRefs) {
						if (MMP_EXTENSION.equalsIgnoreCase(ref.getPath().getFileExtension())) {
							MMPEmitter mmpEmitter = new MMPEmitter(ref.getPath(), buildConfiguration, false);
							mmpEmitter.emit();
							worked(1);
						}
					}
					
					// Emit information for all test mmps
					for (IMakMakeReference ref : testMakMakeRefs) {
						if (MMP_EXTENSION.equalsIgnoreCase(ref.getPath().getFileExtension())) {
							MMPEmitter mmpEmitter = new MMPEmitter(ref.getPath(), buildConfiguration, true);
							mmpEmitter.emit();
							worked(1);
						}
					}
				}
			}
		}

		public Object run(IBldInfView view) {
			List<String> platforms = view.getPlatforms();
			Element table = startTable("bldinf");
			Element headerRow = startTableRow(table, null);
			emitTD(headerRow, "Platforms", "header");
			int counter = 0;
			for (String platform : platforms) {
				String clsStyle = (counter & 1) != 0? "odd" : "even";
				Element row = startTableRow(table, null);
				emitTD(row, platform, clsStyle);
				++counter;
			}
			// store off mmp references for use outside the scope of runWithBldInfView
			List<IMakMakeReference> makMakeReferences = view.getMakMakeReferences();
			makMakeRefs = makMakeReferences.toArray(new IMakMakeReference[makMakeReferences.size()]);
			makMakeReferences = view.getTestMakMakeReferences();
			testMakMakeRefs = makMakeReferences.toArray(new IMakMakeReference[makMakeReferences.size()]);
			return null;
		}
		
		public Object failedLoad(CoreException x) {
			loadException = x;
			emitException("Error reading bld.inf", x);
			return null;
		}
	}
	
	/**
	 * This class implements the IMMPViewRunnable interface
	 * provided by the epoc engine. Using this in conjunction
	 * with the {@link EpocEnginePlugin#runWithMMPView(IPath, IMMPViewConfiguration, IMMPViewRunnable)}
	 * provides an easy way to access the mmp contents without
	 * worrying about all the underlying details.
	 */
	private class MMPEmitter implements IMMPViewRunnable {
		
		private final IPath mmpPath;
		private final ICarbideBuildConfiguration buildConfiguration;
		private final boolean isTestMMP;
		CoreException loadException;
		
		public MMPEmitter(IPath projectRelativeMMPPath,
				ICarbideBuildConfiguration buildConfiguration, boolean isTestMMP) {
			this.mmpPath = projectRelativeMMPPath;
			this.buildConfiguration = buildConfiguration;
			this.isTestMMP = isTestMMP;
		}

		public void emit() {
			if (isTestMMP) {
				emitTag(doc.getBody(), "h4", "Test MMP File: "+mmpPath.toString(), null);
			} else {
				emitTag(doc.getBody(), "h4", "MMP File: "+mmpPath.toString(), null);
			}
			IResource mmpResource = project.findMember(mmpPath);
			if (mmpResource != null ) {
				IMMPViewConfiguration viewConfig = new DefaultMMPViewConfiguration(project, 
						buildConfiguration, new AcceptedNodesViewFilter());
				EpocEnginePlugin.runWithMMPView(mmpResource.getFullPath(), viewConfig, this);
			} else {
				emitPara(mmpPath.toString() + " not found.", null);
			}
			worked(1);
		}

		public Object run(IMMPView view) {
			// emit table of source files
			Element table = startTable("mmpsources");
			Element row = startTableRow(table, null);
			emitTD(row, "MMP Sources", "header");
			int counter = 0;
			for (IPath path : view.getSources()) {
				String clsStyle = (counter & 1) != 0? "odd" : "even";
				row = startTableRow(table, null);
				emitTD(row, path.toString(), clsStyle);
				++counter;
			}
			emitPara(null, null);
			
			// emit table of resource files
			table = startTable("mmpresources");
			row = startTableRow(table, null);
			emitTD(row, "MMP Resources", "header");
			counter = 0;
			for (IMMPResource resource : view.getResourceBlocks()) {
				String clsStyle = (counter & 1) != 0? "odd" : "even";
				row = startTableRow(table, null);
				emitTD(row, resource.getSource().toString(), clsStyle);
				++counter;
			}
			for (IPath path : view.getUserResources()) {
				String clsStyle = (counter & 1) != 0? "odd" : "even";
				row = startTableRow(table, null);
				emitTD(row, path.toString(), clsStyle);
				++counter;
			}
			for (IPath path : view.getSystemResources()) {
				String clsStyle = (counter & 1) != 0? "odd" : "even";
				row = startTableRow(table, null);
				emitTD(row, path.toString(), clsStyle);
				++counter;
			}

			emitPara(null, null);
			return null;
		}
		
		public Object failedLoad(CoreException x) {
			loadException = x;
			emitException("Error reading "+mmpPath.toString(), x);
			return null;
		}
	}
}
