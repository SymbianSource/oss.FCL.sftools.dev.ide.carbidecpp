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

package com.nokia.carbide.internal.cpp.epoc.engine.model.makefile;

import com.nokia.carbide.cpp.epoc.engine.DocumentFactory;
import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.model.IData;
import com.nokia.carbide.cpp.epoc.engine.model.IOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileViewBase;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IIncludeFileLocator;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ModelBase;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.cdt.make.core.MakeCorePlugin;
import org.eclipse.cdt.make.core.makefile.*;
import org.eclipse.cdt.make.core.makefile.gnu.IVariableDefinition;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.text.*;
import org.eclipse.text.edits.*;

import java.io.*;
import java.net.URI;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MakefileViewBase<Model extends IOwnedModel> extends ViewBase<Model> implements IMakefileViewBase {

	private IMakefile makefile;
	private Map<IPath, IDocument> shadowDocuments;
	private String eol;
	private HashMap<String, IPath> directiveFilenameToPathMap;
	private Set<IPath> referencedFiles;
	private VariableSubstitutionEngine engine;
	private IVariableLookupCallback engineSubstitutor;
	
	/**
	 * @param model
	 * @param parser
	 * @param viewConfiguration
	 */
	public MakefileViewBase(ModelBase model, IMakefileViewConfiguration viewConfiguration) {
		super(model, null, viewConfiguration);
		this.makefile = null;
		this.shadowDocuments = new HashMap<IPath, IDocument>();
		shadowDocuments.put(model.getPath(),
			DocumentFactory.createDocument(model.getDocument().get()));
		this.referencedFiles = new HashSet<IPath>();
	}
	
	private IDocument getMainShadowDocument() {
		return shadowDocuments.get(model.getPath());
	}
	
	private void refreshMakefile() {
		String text = getMainShadowDocument().get();
		
		eol = System.getProperty("line.separator"); //$NON-NLS-1$
		Pattern eolPattern = TextUtils.ANY_NEWLINE_MATCHING_PATTERN;
		Matcher matcher = eolPattern.matcher(text);
		if (matcher.find())
			eol = matcher.group(1);
	
		try {
			makefile.parse(URIUtil.toURI(getModel().getPath().toOSString()),
					makefile.getMakefileReaderProvider());
		} catch (IOException e) {
			EpocEnginePlugin.log(e);
		}
	}
		
	@Override
	public IPath[] getReferencedFiles() {
		return (IPath[]) referencedFiles.toArray(new IPath[referencedFiles.size()]);
	}
	
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase#internalReparse()
	 */
	@Override
	protected Map<IPath, IDocument> internalReparse(Map<IPath, IDocument> overrideDocumentMap) {
		IPath modelPath = getModel().getPath();
		if (makefile == null) {
			String style = ((IMakefileViewConfiguration) getViewConfiguration()).getMakefileStyle();
			boolean isGnuStyle = style == null || style.equals("GNU"); //$NON-NLS-1$
			
			directiveFilenameToPathMap = new HashMap<String, IPath>();
			
			IMakefileReaderProvider provider = createMakefileReaderProvider(); 
			IDocument document = null;
			if (overrideDocumentMap != null) {
				document = overrideDocumentMap.get(modelPath);
			}
			if (document == null) {
				document = getModel().getDocument();
			}
			
			// prefetch the main document
			referencedFiles.clear();
			referencedFiles.add(modelPath);
			directiveFilenameToPathMap.put(modelPath.toOSString(), modelPath);

			IDocument shadowDocument = DocumentFactory.createDocument(document.get());
			shadowDocuments.put(modelPath, shadowDocument);
			
			String[] includeDirs = getIncludeDirs(getViewConfiguration().getViewParserConfiguration().getIncludeFileLocator());
			makefile = MakeCorePlugin.createMakefile(
					URIUtil.toURI(modelPath.toOSString()), 
					isGnuStyle, includeDirs,
					provider);
		} else {
			referencedFiles.clear();
			referencedFiles.add(modelPath);
			
			refreshMakefile();
			
			for (Iterator<Map.Entry<IPath, IDocument>> iter = shadowDocuments.entrySet().iterator(); 
				iter.hasNext() ;) {
				Map.Entry<IPath, IDocument> entry = iter.next();
				if (!referencedFiles.contains(entry.getKey()))
					iter.remove();
			}
			
		}

		// get non-model documents
		Map<IPath, IDocument> documentMap = new HashMap<IPath, IDocument>(shadowDocuments);
		documentMap.remove(modelPath);
		
		return documentMap;
	}
	
	/**
	 * Get the provider for the contents of the makefiles being parsed.
	 * @return
	 */
	private IMakefileReaderProvider createMakefileReaderProvider() {
		return new IMakefileReaderProvider() {

			// read from disk for unknown documents, else fetch from the shadow map
			public Reader getReader(String filename) throws IOException {
				
				IPath path = directiveFilenameToPathMap.get(filename);
				if (path == null) {
					path = new Path(filename);
					if (!path.toFile().exists())
						throw new FileNotFoundException(filename);
					
					File file = path.toFile().getCanonicalFile();	// may throw
					
					// remember the canonical mapping
					path = new Path(file.getAbsolutePath());
					directiveFilenameToPathMap.put(filename, path);
				}
				
				referencedFiles.add(path);
				
				// now get the shadow document or load it for the first time
				IDocument document = shadowDocuments.get(path);
				if (document == null) {
					// see if model knows about it
					document = getModel().getDocument(path);
					
					if (document == null) {
						// get contents from disk
						char[] contents = null;
						try {
							contents = FileUtils.readFileContents(path.toFile(), null);
							document = DocumentFactory.createDocument(
									new String(contents));
							getModel().setDocument(path, document);
							
						} catch (CoreException e) {
							EpocEnginePlugin.log(e);
							throw new FileNotFoundException(e.getMessage());
						}
					}
					shadowDocuments.put(path, 
							DocumentFactory.createDocument(document.get()));
				}
				
				return new StringReader(document.get());
			}

			public Reader getReader(URI fileURI) throws IOException {
				return getReader(URIUtil.toPath(fileURI).toOSString());
			}
		};
	}

	/**
	 * Get the include paths scanned.
	 * @param includeFileLocator
	 * @return
	 */
	private String[] getIncludeDirs(IIncludeFileLocator includeFileLocator) {
		File[] systemDirs = includeFileLocator.getSystemPaths();
		if (systemDirs == null)
			systemDirs = new File[0];
		File[] userDirs = includeFileLocator.getUserPaths();
		if (userDirs == null)
			userDirs = new File[0];
		String[] strings = new String[1 + systemDirs.length + userDirs.length];
		int idx = 0;
		strings[idx++] = getModel().getPath().removeLastSegments(1).toFile().getAbsolutePath();
		for (File file : userDirs) {
			strings[idx++] = file.getAbsolutePath();
		}
		for (File file : systemDirs) {
			strings[idx++] = file.getAbsolutePath();
		}
		return strings;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase#internalRevertChanges()
	 */
	@Override
	protected void internalRevertChanges() {
		for (Map.Entry<IPath, IDocument> entry : shadowDocuments.entrySet()) {
			entry.getValue().set(getModel().getDocument(entry.getKey()).get());
		}
		refreshMakefile();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase#internalHasChanges()
	 */
	@Override
	protected boolean internalHasChanges() {
		for (Map.Entry<IPath, IDocument> entry : shadowDocuments.entrySet()) {
			IDocument origDocument = getModel().getDocument(entry.getKey());
			IDocument currDocument = entry.getValue();
			if (origDocument == null || !origDocument.get().equals(currDocument.get()))
				return true;
		}
		return false;
	}

	/**
	 */
	protected void internalCommit() {
		model.lock();
		model.lockDocuments();
		// just copy shadow document to new document; we've been updating that all along
		for (Map.Entry<IPath, IDocument> entry : shadowDocuments.entrySet()) {
			IDocument origDocument = getModel().getDocument(entry.getKey());
			IDocument currDocument = entry.getValue();
			origDocument.set(currDocument.get());
		}		
		model.unlockDocuments();
		model.unlock();
		revert();
		model.desyncOtherViews(this);
		model.fireViewChanged(this);
	}
	
	@Override
	public boolean merge() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileView#getMakefile()
	 */
	public IMakefile getMakefile() {
		return makefile;
	}

	private interface IDirectiveVisitor {
		void visit(IDirective directive);
	}
	
	private void accept(IDirective directive, IDirectiveVisitor visitor) {
		visitor.visit(directive);
		if (directive instanceof IParent) {
			IDirective[] kids = ((IParent)directive).getDirectives();
			for (IDirective kid : kids)
				accept(kid, visitor);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileView#getEOL()
	 */
	public String getEOL() {
		return eol;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileView#getAllMacroDefinitions()
	 */
	public IMacroDefinition[] getAllMacroDefinitions() {
		final List<IMacroDefinition> list = new ArrayList<IMacroDefinition>();
		accept(makefile, new IDirectiveVisitor() {

			public void visit(IDirective directive) {
				if (directive instanceof IMacroDefinition) {
					// don't include exports as they would override the real macro definition
					if (directive instanceof IVariableDefinition) {
						if (((IVariableDefinition)directive).isExport()) {
							return;
						}
					}
					list.add((IMacroDefinition) directive);
				}
			}
			
		});
		return (IMacroDefinition[]) list.toArray(new IMacroDefinition[list.size()]);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileView#getAllMacroDefinitions(java.lang.String)
	 */
	public IMacroDefinition[] getAllMacroDefinitions(final String name) {
		final List<IMacroDefinition> list = new ArrayList<IMacroDefinition>();
		accept(makefile, new IDirectiveVisitor() {

			public void visit(IDirective directive) {
				if (directive instanceof IMacroDefinition) {
					IMacroDefinition def = (IMacroDefinition) directive;
					if (def.getName().equals(name))
						list.add((IMacroDefinition) directive);
				}
			}
			
		});
		return (IMacroDefinition[]) list.toArray(new IMacroDefinition[list.size()]);
	}
	
	protected String getProgramMatcherPattern(String program) {
		String ext = ""; //$NON-NLS-1$
		program = makefile.expandString(program, true);
		if (HostOS.IS_WIN32) {
			ext = ".exe"; //$NON-NLS-1$
			if (program.length() > 4 && program.substring(program.length() - 4).equalsIgnoreCase(ext)) {
				program = program.substring(0, program.length() - 4);
			}
		}
		
		return "(\\s*|.*/|.*\\\\)" + program + "(" + ext + ")?"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileView#findCommandsInvoking(java.lang.String)
	 */
	public ICommand[] findCommandsInvoking(String program) {
		List<ICommand> commands = new ArrayList<ICommand>();
		IRule[] rules = makefile.getRules();
		
		String patternS = getProgramMatcherPattern(program);
		Pattern pattern = Pattern.compile(patternS + "(\\s+.*|$)",  //$NON-NLS-1$
				Pattern.CASE_INSENSITIVE + Pattern.DOTALL);

		for (IRule rule : rules) {
			for (ICommand command : rule.getCommands()) {
				String cmd = makefile.expandString(command.toString(), true);
				if (pattern.matcher(cmd).matches()) {
					commands.add(command);
				}
			}
		}
		return (ICommand[]) commands.toArray(new ICommand[commands.size()]);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileView#getRuleForTarget(java.lang.String,boolean)
	 */
	public ITargetRule findRuleForTarget(String target, boolean exactMatch) {
		ITargetRule match = null;
		File targetFile = new File(target);
		ITargetRule[] rules = makefile.getTargetRules();
		for (ITargetRule rule : rules) {
			File ruleFile = new File(rule.getTarget().toString());
			if (exactMatch && ruleFile.equals(targetFile)) 
				match = rule;
			else if (!exactMatch && ruleFile.getName().equalsIgnoreCase(targetFile.getName())) 
				match = rule;
		}
		return match;
	}

	protected IRegion getDirectiveRegion(IDirective directive) {
		try {
			IDocument shadowDocument = getDirectiveShadowDocument(directive);
			IRegion start = shadowDocument.getLineInformation(directive.getStartLine() - 1);
			IRegion end = shadowDocument.getLineInformation(directive.getEndLine() - 1);
			int endOffset = end.getOffset() + end.getLength();
			String eol = shadowDocument.getLineDelimiter(directive.getEndLine() - 1);
			if (eol != null)
				endOffset += eol.length();
			return new Region(start.getOffset(), endOffset - start.getOffset());
		} catch (BadLocationException e) {
			return null;
		}
	}

	/**
	 * @param directive
	 * @return
	 */
	private IDocument getDirectiveShadowDocument(IDirective directive) {
		IPath path = directiveFilenameToPathMap.get(URIUtil.toPath(directive.getMakefile().getFileURI()).toOSString());
		Check.checkState(path != null);
		
		return shadowDocuments.get(path);
	}

	/**
	 * Get the region where a target rule's target and dependencies live
	 * @param directive
	 * @param target
	 * @return region or null
	 */
	protected IRegion getTargetRegion(ITargetRule targetRule) {
		IRegion region = getDirectiveRegion(targetRule);
		if (region == null)
			return null;
		
		int idx = 0;
		String text;
		try {
			text = getDirectiveShadowDocument(targetRule).get(region.getOffset(), region.getLength());
		} catch (BadLocationException e) {
			return null;
		}
		while (idx < text.length()) {
			char ch = text.charAt(idx);
			// ignore catenated lines
			if (ch == '\\') {
				if (idx + 1 < text.length()) {
					char next = text.charAt(idx + 1);
					if (next == '\n' || next == '\r') {
						idx += 2;
						if (next == '\r' && idx < text.length() && text.charAt(idx) == '\n')
							idx++;
						continue;
					}
				}
			}
			if (ch == '\n' || ch == '\r') {
				// consume
				idx++;
				if (ch == '\r' && idx < text.length() && text.charAt(idx) == '\n')
					idx++;
				break;
			}
			idx++;
		}
		return new Region(region.getOffset(), idx);
	}
	
	private void applyEdit(IDocument document, TextEdit edit) {
		try {
			edit.apply(document, TextEdit.UPDATE_REGIONS);
		} catch (MalformedTreeException e) {
			EpocEnginePlugin.log(e);
			Check.checkState(false);
		} catch (BadLocationException e) {
			EpocEnginePlugin.log(e);
			Check.checkState(false);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileView#appendText(org.eclipse.cdt.make.core.makefile.IDirective, java.lang.String)
	 */
	public void appendText(String line) {
		insertTextBefore(line, null);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileView#insertLine(org.eclipse.cdt.make.core.makefile.IDirective, java.lang.String)
	 */
	public void insertText(IDirective directive, String line) {
		IRegion region = new Region(0, 0);
		IDocument shadowDocument = getMainShadowDocument();
		if (directive != null) {
			region = getDirectiveRegion(directive);
			shadowDocument = getDirectiveShadowDocument(directive);
		}
		Check.checkArg(region);
		TextEdit edit = new InsertEdit(region.getOffset() + region.getLength(), line);
		applyEdit(shadowDocument, edit);
		refreshMakefile();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileView#insertLineBefore(java.lang.String, org.eclipse.cdt.make.core.makefile.IDirective)
	 */
	public void insertTextBefore(String line, IDirective directive) {
		IDocument shadowDocument = getMainShadowDocument();
		IRegion region = new Region(shadowDocument.getLength(), 0);
		if (directive != null) {
			shadowDocument = getDirectiveShadowDocument(directive);
			region = getDirectiveRegion(directive);
		}

		Check.checkArg(region);
		TextEdit edit = new InsertEdit(region.getOffset(), line);
		applyEdit(shadowDocument, edit);
		refreshMakefile();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileView#deleteDirective(IDirective)
	 */
	public void deleteDirective(IDirective directive) {
		IDocument document = getDirectiveShadowDocument(directive);
		Check.checkArg(document);
		IRegion region = getDirectiveRegion(directive);
		Check.checkArg(region);
		TextEdit edit = new DeleteEdit(region.getOffset(), region.getLength());
		applyEdit(document, edit);
		refreshMakefile();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileView#replaceDirective(org.eclipse.cdt.make.core.makefile.IDirective, java.lang.String)
	 */
	public void replaceDirective(IDirective directive, String text) {
		IDocument document = getDirectiveShadowDocument(directive);
		Check.checkArg(document);
		IRegion region = getDirectiveRegion(directive);
		Check.checkArg(region);
		TextEdit edit = new ReplaceEdit(region.getOffset(), region.getLength(), text);
		applyEdit(document, edit);
		refreshMakefile();
	}
	
	protected void replaceTarget(ITargetRule targetRule, String text) {
		IDocument document = getDirectiveShadowDocument(targetRule);
		Check.checkArg(document);
		IRegion region = getTargetRegion(targetRule);
		Check.checkArg(region);
		TextEdit edit = new ReplaceEdit(region.getOffset(), region.getLength(), text);
		applyEdit(document, edit);
		refreshMakefile();
	}

	public String expandAllMacrosInString(String text) {
		Map<String, String> vars = getAllDefs();
		return substituteMacros(vars, text);
		
	}

	/**
	 * @return
	 */
	private Map<String, String> getAllDefs() {
		Map<String, String> vars = new HashMap<String, String>();
		IMacroDefinition[] macroDefs = getAllMacroDefinitions();
		for (IDirective dir : macroDefs) {
			IMacroDefinition def = (IMacroDefinition) dir;
			vars.put(def.getName(), def.getValue().toString());
		}
		return vars;
	}

	private String substituteMacros(final Map<String, String> vars, String text) {
		if (engine == null) {
			engine = new VariableSubstitutionEngine(null, null);
			engine.allowRecursion(true);
			engine.setVariableToken('(');
			
			engineSubstitutor = new IVariableLookupCallback() {
				
				public Object getValue(String var) {
					String value = vars.get(var);
					if (value == null)
						return null;
					if (HostOS.IS_UNIX)
						value = HostOS.convertPathToUnix(value);
					return value;
				}
			};
		}
		return engine.substitute(engineSubstitutor, text);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileView#unexpandMacros(java.lang.String, java.lang.String[])
	 */
	public String unexpandMacros(String text, boolean exhaustive) {
		/*for (String macroName : macroNames) {
			IDefine[] macros = getAllMacroDefinitions(macroName);
			for (IDefine macro : macros) {
				text = unexpand(text, macro);
			}
		}
		if (exhaustive) {
		*/
		IMacroDefinition[] otherDefs;
		if (exhaustive)
			otherDefs = getAllMacroDefinitions();
		else
			otherDefs = makefile.getMacroDefinitions();
		
		boolean changed;
		do {
			changed = false;
			for (IMacroDefinition def : otherDefs) {
				String newText = unexpand(text, def);
				if (!newText.equals(text)) {
					changed = true;
					text = newText;
				}
			}
		} while (changed);
		//}
		return text;
	}

	/**
	 * Get the length of text outside variables.
	 * @param text
	 * @return
	 */
	private int constantTextLength(String text) {
		text = text.replaceAll("\\$\\(.*?\\)", ""); //$NON-NLS-1$ //$NON-NLS-2$
		return text.length();

	}
	private String unexpand(String text, IMacroDefinition macro) {
		String source = Pattern.quote(makefile.expandString(macro.getValue().toString(), true));
		String replacement = Matcher.quoteReplacement("$(" + macro.getName() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
		
		// try replacing into current string
		String unexpandedText = text.replaceAll(source, replacement);
		if (constantTextLength(unexpandedText) < constantTextLength(text))
			return unexpandedText;
		
		// if not, re-expand everything and try again
		String expandedText = makefile.expandString(text, true);
		unexpandedText = expandedText.replaceAll(source, replacement);
		if (constantTextLength(unexpandedText) < constantTextLength(text))
			return unexpandedText;
		
		return text;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileViewBase#expandAllMacrosInRuleString(java.lang.String, org.eclipse.cdt.make.core.makefile.ITargetRule)
	 */
	public String expandAllMacrosInRuleString(String text, ITargetRule rule) {
		text = text.replaceAll("\\$<", Matcher.quoteReplacement(rule.getTarget().toString())); //$NON-NLS-1$
		String[] prereqs = rule.getPrerequisites();
		if (prereqs.length > 0)
			text = text.replaceAll("\\$<", prereqs[0]); //$NON-NLS-1$
		text = text.replaceAll("\\$@", Matcher.quoteReplacement(TextUtils.catenateStrings(prereqs, " "))); //$NON-NLS-1$ //$NON-NLS-2$

		Map<String, String> vars = getAllDefs();
		text = substituteMacros(vars, text);
		
		return text;
	}
	
	public String unexpandMacros(String text, String[] macroNames) {
		for (String macroName : macroNames) {
			IMacroDefinition[] macros = getMakefile().getMacroDefinitions(macroName);
			for (IMacroDefinition macro : macros) {
				text = unexpand(text, macro);
			}
		}
		return text;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase#addViewSpecificMessages(java.util.List)
	 */
	@Override
	protected void addViewSpecificMessages(List<IMessage> messageList) {
		IPath fullPath = getModel().getPath();
		IDirective[] directives = getMakefile().getDirectives();
		for (IDirective directive : directives) {
			if (directive instanceof IBadDirective) {
				String text = directive.toString();
				messageList.add(ASTFactory.createMessage(
								IMessage.ERROR,
								"MakefileViewBase.BadDirective", //$NON-NLS-1$
								new Object[] { text }, 
								new MessageLocation(fullPath, directive.getStartLine(), 0)));
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#getData()
	 */
	public IData getData() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase#convertModelToProjectPath(org.eclipse.core.runtime.IPath)
	 */
	@Override
	public IPath convertModelToProjectPath(IPath modelPath) {
		if (isAbsolutePath(modelPath) || modelPath.toString().startsWith("$")) //$NON-NLS-1$
			return modelPath;

		return super.convertModelToProjectPath(modelPath);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase#convertProjectToModelPath(org.eclipse.core.runtime.IPath)
	 */
	@Override
	public IPath convertProjectToModelPath(IPath prjPath) {
		if (isAbsolutePath(prjPath) || prjPath.toString().startsWith("$")) //$NON-NLS-1$
			return prjPath;

		return super.convertProjectToModelPath(prjPath);
	}
	
}
