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

package com.nokia.sdt.component.symbian.sourcegen.macrohelp;

import com.nokia.sdt.component.symbian.sourcegen.ResolvedMacro;
import com.nokia.sdt.component.symbian.sourcegen.SourceGenMacroSupport;
import com.nokia.sdt.emf.component.DefineMacroType;
import com.nokia.sdt.emf.component.DocumentRoot;
import com.nokia.sdt.emf.component.MacroArgumentType;
import com.nokia.sdt.emf.component.loader.Loader;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Run this program with the name of macro *.inc file(s) as
 * arguments and it will parse the contents and emit an
 * HTML file describing each one. 
 * 
 *
 */
public class MacroHelpGenerator {
	private SourceGenMacroSupport macroSupport; 
	private Map<String, List<ResolvedMacro>> macroMap;
	private Document document;

	public static void main(String[] args) {
		MacroHelpGenerator generator = new MacroHelpGenerator();
		
		// first, parse them all
		for (String arg : args) {
			try {
				generator.parse(arg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// then, emit docs 
		for (String arg : args) {
			try {
				generator.generate(arg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public MacroHelpGenerator() {
		macroSupport = new SourceGenMacroSupport(null);
		macroMap = new HashMap<String, List<ResolvedMacro>>();
	}

	private void parse(String fName) throws Exception {
		File incFile = new File(fName);
		File tmpFile = File.createTempFile("tmpMHG", null);
		tmpFile.deleteOnExit();
		String tmpFName = tmpFile.getAbsolutePath();
		String text= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<!DOCTYPE componentDefinition [\r\n" +
				"<!ENTITY Macros SYSTEM \"" + incFile.getAbsolutePath() + "\">\r\n" + 
				"] >\r\n" +
				"<componentDefinition xmlns=\"http://www.nokia.com/sdt/emf/component\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n" +
				"<component>\r\n"+
				"<sourceGen>\r\n"+
				"&Macros;\r\n"+
				"</sourceGen>\r\n"+
				"</component>\r\n"+
				"</componentDefinition>\r\n";
		
		FileUtils.writeFileContents(tmpFile, text.toCharArray(), null);
		URI fileURI = URI.createFileURI(tmpFName);
		Loader l = new Loader();
		DocumentRoot root = l.load(fileURI);
		if (root != null) {
			
			MessageLocation messageLocation = 
				new MessageLocation(new Path(tmpFile.getAbsolutePath()));
			
			ArrayList<ResolvedMacro> fileMacros = new ArrayList<ResolvedMacro>();
			macroMap.put(fName, fileMacros);
			for (TreeIterator<EObject> iter = root.eAllContents(); iter.hasNext(); ) {
				EObject obj = iter.next();
				if (obj instanceof DefineMacroType) {
					ResolvedMacro macro = macroSupport.defineMacro(null, messageLocation, (DefineMacroType) obj);
					fileMacros.add(macro);
				}
			}
		}
		else
            throw new IllegalArgumentException("Cannot load " + fName);

	}
	private void generate(String fName) throws Exception {
		document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element html = document.createElement("html");
		document.appendChild(html);
		html.setAttribute("xmlns", "http://www.w3.org/1999/xhtml");
		Element head = document.createElement("head");
		html.appendChild(head);
		Element title = document.createElement("title");
		head.appendChild(title);
		title.setTextContent("Help for Macros in " + new File(fName).getName());
		
		Element body = document.createElement("body");
		html.appendChild(body);
		Element tocParent = document.createElement("p");
		body.appendChild(tocParent);
		Element tocTitle = document.createElement("h1");
		tocTitle.setTextContent("Table of contents");
		tocParent.appendChild(tocTitle);
		
		Element helpParent = document.createElement("p");
		body.appendChild(helpParent);

		for (ResolvedMacro macro : macroMap.get(fName)) {
			generateMacroHelp(tocParent,
					helpParent,
					macro);
			tocParent.appendChild(document.createTextNode("\n"));
			helpParent.appendChild(document.createTextNode("\n"));
		}
			
		DOMSource domSource = new DOMSource(document);
		TransformerFactory transFactory = TransformerFactory.newInstance();
		String outFileName = fName + ".html";
		FileOutputStream fileOutputStream = new FileOutputStream(
				outFileName);
		Result fileResult = new StreamResult(fileOutputStream);
		Transformer transformer = transFactory.newTransformer();
		//transformer.setParameter(Result.PI_DISABLE_OUTPUT_ESCAPING, "yes");
		try {
			//transFactory.newTransformer().transform(domSource, fileResult);
			transformer.transform(domSource, fileResult);
			System.out.println("Wrote " + outFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		fileOutputStream.close();
	}
	

	private void generateMacroHelp(
			Element tocParent,
			Element helpParent,
			ResolvedMacro macro) {
		
		String id = macro.macro.getId();
		
		// add entry to table of contents
		Element tocEntry = document.createElement("p");
		
		Element tocAddress = document.createElement("a");
		tocAddress.setAttribute("href", "#" + id);
		
		tocAddress.setTextContent(id);
		tocEntry.appendChild(tocAddress);
		
		tocParent.appendChild(tocEntry);
		
		// add entry itself
		
		Element helpAnchor = document.createElement("a");
		helpAnchor.setAttribute("name", id);
		helpParent.appendChild(helpAnchor);
		
		Element helpEntry = document.createElement("h2");
		helpEntry.setTextContent(id);
		helpParent.appendChild(helpEntry);
		
		generateMacroDescription(helpParent, macro);
	}

	private void generateMacroDescription(Element helpEntry,
			ResolvedMacro macro) {
		DefineMacroType dmt = macro.macro;
		String help = dmt.getHelp();
		
		if (help == null) {
			Element para = document.createElement("p");
			para.setTextContent("This macro is undocumented (add a 'help' attribute)");
			helpEntry.appendChild(para);
		}
		else {
			helpEntry.appendChild(encodeHTML(help));
		}
		
		Element header = document.createElement("h3");
		header.setTextContent("Arguments");
		helpEntry.appendChild(header);
		
		//Element ul = document.createElement("ul");
		generateMacroArgumentsHelp(helpEntry, macro);
		//helpEntry.appendChild(ul);
	}

	private void generateMacroArgumentsHelp(Element ul,
			ResolvedMacro macro) {

		// organize arguments by their origin: 
		// these are already ordered as <imported arguments>
		// then <custom arguments>.  We will make a linked map that
		// places custom arguments first followed by imported arguments.
		
		Map<ResolvedMacro, List<MacroArgumentType>> originsToArguments;
		originsToArguments = new LinkedHashMap<ResolvedMacro, List<MacroArgumentType>>();

		// first, custom arguments
		List<MacroArgumentType> args = new ArrayList<MacroArgumentType>();
		originsToArguments.put(macro, args);
		
		for (MacroArgumentType argument : macro.arguments.values()) {
			ResolvedMacro fromMacro = macro.argumentOrigins.get(argument.getName());
			if (fromMacro == macro) {
				args.add(argument);
			}
		}

		// now, imported argument groups
		for (MacroArgumentType argument : macro.arguments.values()) {
			ResolvedMacro fromMacro = macro.argumentOrigins.get(argument.getName());
			args = originsToArguments.get(fromMacro);
			if (args == null) {
				args = new ArrayList<MacroArgumentType>();
				originsToArguments.put(fromMacro, args);
			}
			args.add(argument);
		}
		
		// now emit the groups of arguments from outer to inner
		for (Map.Entry<ResolvedMacro, List<MacroArgumentType>> entry : originsToArguments.entrySet()) {
			emitArgumentGroup(document, ul,
					entry.getKey() != macro,
					entry.getKey(), entry.getValue());
			
		}
	}
	
	private void emitArgumentGroup(Document document,
			Element parent,
			boolean isInherited,
			ResolvedMacro fromMacro,
			List<MacroArgumentType> arguments) {
		
		if (isInherited) {
			Element info = document.createElement("p");
			info.setTextContent("Inherited from ");
			Element a = document.createElement("a");
			a.setAttribute("href", "#" + fromMacro.macro.getId());
			a.setTextContent(fromMacro.macro.getId());
			info.appendChild(a);
			info.appendChild(document.createTextNode(":"));
			parent.appendChild(info);
		}
		Element table = document.createElement("table");
		table.setAttribute("cellpadding", "3");
		parent.appendChild(table);
		
		table.setAttribute("border", "1");
		Element th = document.createElement("th");
		th.setTextContent("Argument");
		table.appendChild(th);
		
		th = document.createElement("th");
		th.setTextContent("Req'd?");
		table.appendChild(th);
		
		th = document.createElement("th");
		th.setTextContent("Default");
		table.appendChild(th);
		
		th = document.createElement("th");
		th.setTextContent("Description");
		table.appendChild(th);
		
		// sort arguments by optionality
		Collections.sort(arguments,
				new Comparator<MacroArgumentType>() {

					public int compare(MacroArgumentType o1,
							MacroArgumentType o2) {
						if (o1.isOptional() && !o2.isOptional())
							return 1;
						if (!o1.isOptional() && o2.isOptional())
							return -1;
						return 0;
					}
			
		});
		for (MacroArgumentType argument : arguments) {
			generateMacroArgumentHelp(table, fromMacro, argument);
		}

		
	}
	private void generateMacroArgumentHelp(Element table,
			ResolvedMacro macro, MacroArgumentType argument) {
		Element tr = document.createElement("tr");
		table.appendChild(tr);

		Element td;
		td = document.createElement("td");
		td.setTextContent(argument.getName());
		tr.appendChild(td);

		td = document.createElement("td");
		td.setTextContent(argument.isOptional() ? "no" : "yes");
		tr.appendChild(td);

		td = document.createElement("td");
		if (argument.getDefault() != null && argument.getDefault().length() > 0)
			td.setTextContent(argument.getDefault());
		else
			td.setTextContent("\u00A0");
		tr.appendChild(td);

		td = document.createElement("td");
		if (argument.getHelp() != null && argument.getHelp().length() > 0)
			td.appendChild(encodeHTML(argument.getHelp()));
		else
			td.setTextContent("\u00A0");
		tr.appendChild(td);
	}
	
	//static Pattern PARAGRAPH_PATTERN = Pattern.compile("(.*)(\r?\n)\\s*(\r?\n)", Pattern.MULTILINE);
	
	private Node encodeHTML(String help) {
		Node el = document.createTextNode("");
		boolean hadParagraphs = false;
		int idx = 0;
		while (idx < help.length()) {
			int para = help.indexOf("$p$", idx);
			if (para < 0)
				para = help.length();
			if (!hadParagraphs)
				el = document.createElement("p");
			Element p = document.createElement("p");
			hadParagraphs = true;
			p.setTextContent(help.substring(idx, para));
			idx = para + 3;
			el.appendChild(p);
		}
		if (!hadParagraphs) {
			el.setTextContent(help);
		}
		return el;
	}

}
