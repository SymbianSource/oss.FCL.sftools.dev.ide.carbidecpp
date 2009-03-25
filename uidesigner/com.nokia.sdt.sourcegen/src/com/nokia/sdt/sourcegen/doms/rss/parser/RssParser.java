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
package com.nokia.sdt.sourcegen.doms.rss.parser;

import com.nokia.carbide.cpp.epoc.engine.DocumentFactory;
import com.nokia.carbide.cpp.epoc.engine.model.IModelDocumentProvider;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.*;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.IDocumentParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ParserFactory;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.ASTPreprocessor;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.Tuple;
import com.nokia.sdt.sourcegen.IIncludeFileLocator;
import com.nokia.sdt.sourcegen.SourceGenPlugin;
import com.nokia.sdt.sourcegen.core.*;
import com.nokia.sdt.sourcegen.doms.rss.IRssProjectFileManager;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.*;
import com.nokia.sdt.sourcegen.doms.rss.impl.Utilities;
import com.nokia.sdt.sourcegen.doms.rss.parser.gen.*;
import com.nokia.sdt.utils.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class parses an RSS file into an RSS DOM, using
 * the CDT scanner underneath as a preprocessor.
 * 
 * 
 *
 */
public class RssParser implements IAugmentedPreprocessorCallbacks, IPreprocessorFilter {
    public IAstRssSourceFile rssSrcFile;
    public ISourceFile rssFile;
    
    public boolean DUMP = false;
    
    // parser state
    //String lastPath;
    /** current file */
    public ISourceFile sourceFile;
    /** current file */
    public IAstSourceFile srcFile;
    private HashMap<ISourceFile, IAstSourceFile> srcMap;
    /** Of IAstSourceFile */
    Stack<IAstSourceFile> fileStack;
    
	private IRssProjectFileManager manager;
	private IIncludeFileLocator includeHandler;
	private Collection<IDefine> macros;
	private DefaultTranslationUnitProvider tuProvider;
	private IModelDocumentProvider documentProvider;
	private com.nokia.carbide.cpp.epoc.engine.preprocessor.IIncludeFileLocator fileLocator;
	private ITranslationUnit tu;
	private ArrayList<IASTPreprocessorDefineStatement> detectedDefines;

	private static Pattern lxxFilePattern = Pattern.compile(".*\\.(?:l|L)([0-9][0-9])"); //$NON-NLS-1$
	private static Pattern locFilePattern = Pattern.compile(".*\\.loc", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$
	public static Pattern definePattern = Pattern.compile("([A-Za-z0-9_]+)\\s+(.*)\\s*"); //$NON-NLS-1$
	private static Pattern rlsFilePattern = Pattern.compile(".*\\.rls", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$
	private static Pattern rlsLangFilePattern = Pattern.compile(".*_([0-9][0-9])\\.rls", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$
	private static Pattern definedPattern = Pattern.compile("defined\\s*\\(\\s*(\\w+)\\s*\\)"); //$NON-NLS-1$
	private static Pattern langPattern = Pattern.compile("LANGUAGE_([0-9][0-9])"); //$NON-NLS-1$
	private static Pattern viewRssFilePattern = Pattern.compile(".*\\.(rssi|ra)", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$


    /**
	 * Create an RSS parser
	 * @param file the main file
	 */
	public RssParser(IRssProjectFileManager manager, IIncludeFileLocator includeHandler) {
		this(manager, includeHandler, null);
	}

	/**
     * Create an RSS parser
     * @param fileManager
     * @param theIncludeHandler
     * @param macros prefix macros
     */
    public RssParser(IRssProjectFileManager fileManager, IIncludeFileLocator theIncludeHandler, Collection<IDefine> macros) {
        Check.checkArg(fileManager);
        Check.checkArg(theIncludeHandler);
        
        this.manager = fileManager;
        this.includeHandler = theIncludeHandler;
        this.macros = macros != null ? macros : Collections.EMPTY_LIST;
        
    	documentProvider = new IModelDocumentProvider() {

			public IDocument getDocument(File file) {
				ISourceFile sf = manager.findSourceFile(file);
				if (sf == null) {
					sf = new SourceFile(file);
					manager.registerSourceFile(sf);
				}
				manager.loadSourceFile(sf);
				return DocumentFactory.createDocument(new String(sf.getText()));
			}
    		
    	};
    	
    	tuProvider = DefaultTranslationUnitProvider.getInstance();
    	
    	fileLocator = new com.nokia.carbide.cpp.epoc.engine.preprocessor.IIncludeFileLocator() {

			private File[] paths;

			public File findIncludeFile(String file, boolean isUser,
					File currentDir) {
				return includeHandler.findIncludeFile(file, isUser, currentDir);
			}

			public File[] getSystemPaths() {
				if (paths == null) {
					String[] includePaths = includeHandler.getIncludePaths();
					paths = new File[includePaths.length];
					int idx = 0;
					for (String path : includePaths) {
						paths[idx++] = new File(path);
					}
				}
				return paths; 
			}

			public File[] getUserPaths() {
				return new File[0];
			}
    		
    	};
    	

    }
    
    /**
     * Parse a single file (and its includes) 
     * @param tu the translation unit whose source file is parsed; its DOM is established by
     * this operation
     * @param mainFile true: do top-level source reparsing to find loc/rls files
     */
    private void doParse(boolean mainFile) {
    	srcMap = new HashMap<ISourceFile, IAstSourceFile>();
    	srcMap.put(rssFile, rssSrcFile);
    	
    	if (manager.findSourceFile(rssFile.getFile()) == null) 
    		manager.registerSourceFile(rssFile);
    	
    	manager.loadSourceFile(rssSrcFile.getSourceFile());

    	if (manager.findRssFile(rssFile)== null)
    		manager.registerRssFile(rssSrcFile);
    	
    	// get the preparsed translation unit
    	IDocumentParser preParser = ParserFactory.createPreParser();
		IASTTranslationUnit ppTu = preParser.parse(new Path(rssFile.getFile().getAbsolutePath()),
				documentProvider.getDocument(rssFile.getFile()));
    	
		this.fileStack = new Stack<IAstSourceFile>();
		fileStack.push(rssSrcFile);
        this.srcFile = rssSrcFile;
        fileStack.push(rssSrcFile);

    	// preprocess the translation unit
        detectedDefines = new ArrayList<IASTPreprocessorDefineStatement>();
		IPreprocessor preprocessor = new ASTPreprocessor(tuProvider, fileLocator, documentProvider);
		preprocessor.setFilter(this);
		IPreprocessorResults preprocessorResults = preprocessor.preprocess(ppTu, new AcceptedNodesViewFilter(), macros);
		
		// 
		RssTokenManager tokenManager = new RssTokenManager(this, preprocessorResults);
		SymbianRssParser parser = new SymbianRssParser(this, tokenManager);
		
        try {
            parser.SourceFile(rssSrcFile);
        } catch (ParseException e) {
            // should not happen: should be wired to messages
            e.printStackTrace();
            Check.checkState(false);
        }


        // set entire file's source range
        ISourceFile sf = rssSrcFile.getSourceFile();
        rssSrcFile.setSourceRange(new SourceRange(sf, 0, sf,
                    sf.getText().length));    
        
        SymbianRssParser.setFileNodesSourceRange(rssSrcFile);
        
        if (mainFile)
        	updateMainFile(tu);
        
        rssSrcFile.setDirtyTree(false);
    }
    
    
    /**
     * Parse a single file (and its includes) 
     * @param tu the translation unit whose source file is parsed; its DOM is established by
     * this operation
     * @param mainFile true: do top-level source reparsing to find loc/rls files
     */
    public void reparse(ITranslationUnit tu, boolean mainFile) {
    	
    	this.tu = tu;
    	this.rssSrcFile = (IAstRssSourceFile) tu.getSourceFile();
    	this.rssFile = rssSrcFile.getSourceFile();
    	
    	doParse(mainFile);
    }
    
	public ITranslationUnit parseTu(ISourceFile rssfile, boolean mainFile) throws FileNotFoundException {
        // ensure the file is on disk
        File diskFile = rssfile.getFile();
        if (!diskFile.exists()) {
        	throw new FileNotFoundException(diskFile.toString());
        }
		
		this.rssFile = rssfile; 
        this.rssSrcFile = new AstRssSourceFile(rssFile);

	    this.tu = new TranslationUnit(rssSrcFile);
	    doParse(mainFile);
	    
	    rssSrcFile.setTranslationUnit(tu);
	    rssSrcFile.setDirty(false);
	    tu.refresh();
	    
	    IAstSourceFile includedFiles[] = tu.getIncludedFiles();
	    for (int i = 0; i < includedFiles.length; i++) {
	    	if (!includedFiles[i].isReadOnly())
	    		addTextNodes(includedFiles[i]);
	        includedFiles[i].setTranslationUnit(tu);
	        includedFiles[i].setDirtyTree(false);
	    }
	    
	    return tu;
	}

	public ITranslationUnit parseTu(ISourceFile rssFile) throws FileNotFoundException {
		return parseTu(rssFile, true);
	}

	public ITranslationUnit getCurrentTranslationUnit() { 
		return tu;
	}
	
	/**
	 * Handle top-level preprocessor nodes, such as registering
	 * macros detected by the scanner and adding top-level text
	 * nodes for contents in between top-level nodes.
	 * @param tu
	 */
	private void updateMainFile(ITranslationUnit tu) {
		// scan files for rls/lxx includes
		if (tu.getSourceFile() instanceof IAstRssSourceFile)
			reparseLocalizationFileIncludes((IAstRssSourceFile) tu.getSourceFile());
		
        // scan main file for macros 
        reparseStringMacros(tu.getSourceFile());
        
        // add top-level text nodes
        addTextNodes(tu.getSourceFile());
        
		// get a holding cell for local decls 
		IAstRssSourceFile localSource = (IAstRssSourceFile) tu.getLocalSourceFile();
        if (localSource == null) {
        	localSource = new AstRssSourceFile();
        	tu.setLocalSourceFile(localSource);
        }
        
        // now, populate detected macros into the TU's #defines where they match
        // (i.e. in editable sources where they represent zero-argument macros),
        // or shunt them the local source otherwise
        
    	for (IASTPreprocessorDefineStatement ppDefine : detectedDefines) {
    		IMacro macro = null;
    		
        	if (ppDefine.getMacroArgs() != null) {
        		macro = new com.nokia.sdt.sourcegen.doms.rss.dom.impl.FunctionStyleMacro(
        				ppDefine.getMacroName().getValue(),
        				textNodesToStrings(ppDefine.getMacroArgs()),
        				ppDefine.getMacroExpansion() != null ?
        						ppDefine.getMacroExpansion().getNewText() : "");
        	}
        	else {
        		macro = new com.nokia.sdt.sourcegen.doms.rss.dom.impl.ObjectStyleMacro(
        				ppDefine.getMacroName().getValue(),
        				ppDefine.getMacroExpansion() != null ?
        						ppDefine.getMacroExpansion().getNewText() : "");        	}
        	
        	if (macro != null) {
        		IAstPreprocessorDefineDirective define = tu.findDefine(macro.getName());
        		if (define != null)
        			define.setMacro(macro);
        		else {
        			localSource.addFileNode(new AstPreprocessorDefineDirective(macro));
        		}
        	}
    	}
    	
        // remove source ranges for system files
        // and finalize info for user files
        IAstSourceFile[] includedFiles = tu.getIncludedFiles();
        for (int i = 0; i < includedFiles.length; i++) {
			IAstSourceFile asf = includedFiles[i];
			if (asf.isReadOnly()) {
				removeSourceRanges(asf);
			} else {
				// find any macros
				reparseStringMacros(asf);
				
				// fill in extra text
				addTextNodes(asf);
			}
			asf.setDirtyTree(false);
		}
	}

	/**
	 * @param macroArgs
	 * @return
	 */
	private String[] textNodesToStrings(
			IASTListNode<IASTLiteralTextNode> macroArgs) {
		String[] strings = new String[macroArgs.size()];
		int idx = 0;
		for (IASTLiteralTextNode arg : macroArgs)
			strings[idx++] = arg.getValue();
		return strings;
	}

	/**
	 * @param asf
	 */
	private void removeSourceRanges(IAstNode node) {
		node.setSourceRange(null);
		IAstNode[] children = node.getChildren();
		for (int i = 0; i < children.length; i++) {
			removeSourceRanges(children[i]);
		}
	}

	/**
	 * Flesh out the TU for a source file by inserting IAstPreprocessorTextNode
	 * entries in the spaces between statements parsed for the RSS DOM.
	 * This ensures that every character in the file is accounted for, so that
	 * rewriting does not lose track of it.  (Usually comments are accounted
	 * for by gathering extended source ranges, but #ifdef'ed content or 
	 * blank lines would be missed otherwise.)
	 * @param sourceFile2
	 */
	private void addTextNodes(IAstSourceFile srcFile) {
        IAstListNode listNode = srcFile.getFileNodeList();
        ISourceRange range = srcFile.getSourceRange();
        if (range != null) {
	        IAstNode[] nodes = listNode.getChildren();
	        int prevEndOffset = 0; //range.getOffset();
	        char[] text = range.getFile().getText();
	        for (int i = 0; i < nodes.length; i++) {
				ISourceRange subRange = nodes[i].getExtendedRange();
				Check.checkState(subRange != null);
				if (subRange.getOffset() > prevEndOffset) {
					int start = prevEndOffset;
					int end = subRange.getOffset();
					IAstPreprocessorTextNode textNode = createTextNode(text, subRange.getFile(), start, end); 
					listNode.insertBeforeFileNode(textNode, nodes[i]);
				}
				prevEndOffset = subRange.getEndOffset();
			}
	        if (prevEndOffset < range.getEndOffset()) {
				IAstPreprocessorTextNode textNode = createTextNode(text, range.getFile(), 
						prevEndOffset, range.getEndOffset());
				listNode.addItem(textNode);
	        }
        }
		ParserBase.setFileNodesSourceRange(srcFile);
	}

	/**
	 * @param text
	 * @param subRange
	 * @param start
	 * @param end
	 * @return
	 */
	private IAstPreprocessorTextNode createTextNode(char[] text, ISourceFile sourceFile, int start, int end) {
		String otherText = new String(text, start, end - start);
		IAstPreprocessorTextNode textNode = new AstPreprocessorTextNode(otherText);
		
		textNode.setSourceRange(new SourceRange(sourceFile, start, end - start));
		return textNode;
	}

	public void setCurrentFile(IPath path) {
		File ifile = path.toFile();
        sourceFile = manager.findSourceFile(ifile);

        if (sourceFile == null) {
            sourceFile = new SourceFile(ifile);
            manager.registerSourceFile(sourceFile);
        }
        
        // ensure correct contents loaded for offset/line lookup
        manager.loadSourceFile(sourceFile);
    }
	
    public void enterInclude(IPath path, int start, int end) {
        fileStack.push(srcFile);

        setCurrentFile(path);
        
        if (sourceFile != rssFile) {

        	// directive includes eol
        	int eol = end; //ParserBase.skipWhitespaceForward(srcFile.getSourceFile(), end, srcFile.getSourceFile().getLength());
        	SourceRange includeRange = new SourceRange(srcFile.getSourceFile(), start, eol - start);
        	
        	IAstPreprocessorIncludeDirective newIncl = null;
        	IAstSourceFile newSrcFile = null;

        	boolean registerNewFile = false;
        	if (newIncl == null) {
	        	// create new #include node for a generic RSS/RH/HRH/etc file
        		if (newSrcFile == null) {
        			//newSrcFile = new AstRssSourceFile(sourceFile);
        			newSrcFile = manager.findRssFile(sourceFile);
        			if (newSrcFile == null) {
        				newSrcFile = new AstRssSourceFile(sourceFile);
        				registerNewFile = true;
        			} else {
        				newSrcFile.removeAllContents();
        			}
        		}
	        
	            // detect whether it's a user or system include
	            String line = new String(srcFile.getSourceFile().getText(), start, end - start);
	            boolean isUserPath = line.indexOf('<') < 0;
	            newIncl = new AstPreprocessorIncludeDirective(
	            		sourceFile.getFileName(), isUserPath, newSrcFile);
	            if (!isUserPath) {
	            	newSrcFile.setReadOnly(true);
	            	registerNewFile = false;
	            }
	            else 
        			newSrcFile.setTranslationUnit(srcFile.getTranslationUnit());

	            newIncl.setSourceRange(includeRange);
	            ((IAstRssSourceFile)srcFile).appendFileNode(newIncl);
        	}
        	
            srcFile = newSrcFile;

            if (registerNewFile && newSrcFile.getTranslationUnit() != null)
				manager.registerRssFile((IAstRssSourceFile) newSrcFile);

            // map of recently included files
            srcMap.put(sourceFile, srcFile);

            //System.out.println("parsing "+sourceFile+":\n"+new String(sourceFile.getText()));
            //System.out.println("srcfile ==> "+ srcFile.getSourceFile().getFile());
        }
    }

    public void exitInclude() {
    	if (srcFile != null) {
    		srcFile.setSourceRange(new SourceRange(srcFile.getSourceFile(),
					0, srcFile.getSourceFile().getLength()));
				
    		if (!srcFile.isReadOnly())
    			ParserBase.setFileNodesSourceRange(srcFile);
    	}
    	//System.out.println("exiting " + srcFile.getSourceFile().getFile());
        srcFile = (IAstSourceFile) fileStack.pop();
        sourceFile = srcFile.getSourceFile();
    }
    
    /**
     * Reparse the given file and detect #defines that
     * could be string macros (no arguments and a string expansion).
     * We explicitly IGNORE non-string macros.
	 * @param srcFile
	 */
	private void reparseStringMacros(IAstSourceFile srcFile) {
		if (DUMP) System.out.println("Reparsing #defines in " + srcFile);
		// scan the text looking for #defines
    	char[] text = srcFile.getSourceFile().getText();
    	int ptr = 0;
    	int limit = text.length;
    	IAstListNode fileNodes = srcFile.getFileNodeList();
    	while (ptr < limit) {
    		int directive;
    		if (text[ptr] == '#')
    			directive = ptr;
    		else
    			directive = ParseUtils.searchDirectiveForwards(text, ptr, limit);
    		if (directive != -1) {
				int endOffset = ParseUtils.skipToLogicalEndOfLineForward(text, directive, limit);
				endOffset += ParseUtils.newlineLength(text, endOffset);
				ptr = endOffset;
			
				Tuple dirInfo = ParseUtils.findDirectiveAndArgument("define", text, directive, limit);
    			if (dirInfo == null)
    				continue;

    			// watch out for existing nodes
    			IAstNode[] existing = srcFile.findNodesAt(directive);
    			if (existing.length > 0) {
    				IAstNode node = existing[existing.length - 1];
    				if (node instanceof IAstPreprocessorDefineDirective)
    					continue;
    				if (node instanceof IAstPreprocessorTextNode)
    					srcFile.removeFileNode(node);
    			}
    			
    			// only handle argument-less macros
    			Matcher defineMatcher = definePattern.matcher((String) dirInfo.get(0));
    			if (defineMatcher.matches()) {
    				IMacro macro = new com.nokia.sdt.sourcegen.doms.rss.dom.impl.ObjectStyleMacro(
    						defineMatcher.group(1), 
    						defineMatcher.group(2));
    				IAstPreprocessorDefineDirective define = new AstPreprocessorDefineDirective(macro);
    				define.setSourceRange(new SourceRange(srcFile.getSourceFile(), directive, endOffset - directive));
    				
    				int argStart = ((Integer) dirInfo.get(3)).intValue() + defineMatcher.start(2);
    				int argEnd = ((Integer) dirInfo.get(4)).intValue(); 
    				define.getMacroValue().setSourceRange(new SourceRange(
    						srcFile.getSourceFile(), 
    						argStart, argEnd - argStart));
    				ParserBase.setExtendedSourceRangeFromComments(define, false, true);

    				// ensure the list range includes the child
    				ISourceRange defineRange = define.getExtendedRange();
    				if (fileNodes.getSourceRange() != null) {
	    				if (defineRange.getOffset() < fileNodes.getSourceRange().getOffset())
	    					ParserBase.setSourceRangeFrom(fileNodes, defineRange.getOffset());
	    				if (defineRange.getEndOffset() > fileNodes.getSourceRange().getEndOffset())
	    					ParserBase.setSourceRangeTo(fileNodes, defineRange.getEndOffset());
    				}    				
    				srcFile.addFileNode(define);
    			}
    		} else
    			break;
    	}
		ParserBase.setFileNodesSourceRange(srcFile);
	}

	/**
     * Reparse the given file and categorize #includes to reference
     * specific file types
	 * @param srcFile
	 */
    private void reparseLocalizationFileIncludes(IAstRssSourceFile srcFile) {
    	if (DUMP) System.out.println("Reparsing localization #includes in " + srcFile);
    	File cwd = srcFile.getSourceFile().getFile().getParentFile();
    	char[] text = srcFile.getSourceFile().getText();
    	int ptr = 0;
    	int limit = text.length;
    	
    	Check.checkState(srcFile.getSourceRange() != null);
    	
    	while (ptr < limit) {
    		// look for #include first, ignoring any #if structure
    		int directive;
    		if (text[ptr] == '#')
    			directive = ptr;
    		else
    			directive = ParseUtils.searchDirectiveForwards(text, ptr, limit);
    		if (directive != -1) {
				int endOffset = ParseUtils.skipToLogicalEndOfLineForward(text, directive, limit);
				endOffset += ParseUtils.newlineLength(text, endOffset);
				ptr = endOffset;
				
    			Tuple dirInfo = ParseUtils.findDirectiveAndArgument("include", text, directive, limit);
    			if (dirInfo == null)
    				continue;
    			String arg = (String) dirInfo.get(0); 
    			if (arg.length() > 2) {
    				boolean isUser = arg.charAt(0) == '"';
    				arg = arg.substring(1, arg.length() - 1);
    				File file = includeHandler.findIncludeFile(arg, isUser, cwd);
    				//if (file == null && !isUser)
    				//	continue;	// probably CDT didn't find it either and already issued an error
    					
					IAstPreprocessorIncludeDirective incl =
						reparseLocalizationInclude(srcFile, file, directive, endOffset, arg, isUser);
					if (incl != null)
						ptr = incl.getSourceRange().getEndOffset();
    			}
    		} else
    			break;
    	}
    }

	/**
	 * Check a given include file and reparse it if it's a localization file
	 * Returns an unfinished file -- string macros and source ranges
	 * still need to be finalized 
	 * @param srcFile
	 * @param inclFile
	 * @param directive
	 * @param endOffset
	 * @return include directive (either new or existing) or null if not
	 * a localization file
	 */
	private IAstPreprocessorIncludeDirective reparseLocalizationInclude(IAstRssSourceFile srcFile, File file, int directive, int endOffset, String fileName, boolean isUser) {

		if (DUMP) System.out.println("Reparsing #include of " + file);

		ISourceFile inclFile = null;
		if (file != null) {
			inclFile = manager.findSourceFile(file);
			if (inclFile == null) {
				inclFile = new SourceFile(file);
				manager.registerSourceFile(inclFile);
			}
		}

		IAstPreprocessorIncludeDirective incl = null;
		SourceRange includeRange = new SourceRange(srcFile.getSourceFile(), directive, endOffset - directive);

		// see if the #include overlaps an existing one
		IAstPreprocessorIncludeDirective[] existingNodes = srcFile.getIncludeFiles();
		IAstPreprocessorIncludeDirective existing = null;
		for (int i = 0; i < existingNodes.length; i++) {
			// guard against missing include files
			if ((inclFile != null && existingNodes[i].getFile() != null
						? existingNodes[i].getFile().getSourceFile().equals(inclFile)
						: existingNodes[i].getFilename().equals(fileName)) &&
					existingNodes[i].getSourceRange() != null &&
					existingNodes[i].getSourceRange().intersects(includeRange)) {
				existing = existingNodes[i];
				break;
			}
		}
		
		if (inclFile == null) {
			if (existing == null) {
				// replace missing #include with plain old #include
				incl = new AstPreprocessorIncludeDirective(fileName, isUser, null);
				incl.setSourceRange(includeRange);
				srcFile.addFileNode(incl);
			} else {
				incl = existing;
			}
			return incl;
		}
		
		// check for *.loc include
		if (locFilePattern.matcher(fileName).matches()) {
			if (existing != null) {
				if (existing.getFile() instanceof IAstLocSourceFile) {
					if (existing instanceof IAstPreprocessorLocIncludeNode)
						return existing;
				}
			}

			if (DUMP) System.out.println("Reparsing as LOC file");

			IAstLocSourceFile locFile = (IAstLocSourceFile) manager.findOrReplaceTypedFile(srcFile.getTranslationUnit(),
					inclFile, AstLocSourceFile.class, null);
			locFile.removeAllContents();
			locFile.setSourceRange(new SourceRange(inclFile, 0, inclFile.getLength()));
			
			if (existing == null) {
				incl = new AstPreprocessorLocIncludeNode(fileName, isUser, locFile);
				incl.setSourceRange(includeRange);
				srcFile.appendFileNode(incl);
			} else {
				// replace incorrectly typed #include with good one
				incl = new AstPreprocessorLocIncludeNode(fileName, isUser, locFile);
				incl.setSourceRange(includeRange);
				srcFile.insertFileNode(existing, incl);
				srcFile.removeFileNode(existing);
			}
			
			// reparse to get the *.lxx includes
			reparseLocalizationFileIncludes(locFile);
			
			return incl;
		}
		
		if (rlsFilePattern.matcher(fileName).matches()) {
			// remove in case we change it into a bigger statement (with #ifdef...#endif)
			if (existing != null)
				srcFile.removeFileNode(existing);
			incl = tryParsingRlsInclude(srcFile, inclFile, directive, endOffset, isUser);
			// add back if that failed
			if (existing != null && incl == null)
				srcFile.addFileNode(existing);
		}
		if (incl != null) {
			// it was added as an #include
			IAstRlsSourceFile rlsFile = (IAstRlsSourceFile) incl.getFile();

			if (DUMP) System.out.println("Reparsing as RLS file");

			// sanity check included file to make sure it looks like a normal file
			Matcher rlsFileMatcher = rlsLangFilePattern.matcher(fileName);
			if (!rlsFileMatcher.matches() || Integer.parseInt(rlsFileMatcher.group(1)) != rlsFile.getLanguageCode()) {
				String ext = "" + (rlsFile.getLanguageCode()/10) + (rlsFile.getLanguageCode()%10);
				Messages.emit(
		                IMessage.WARNING,
		                incl.getSourceRange().createMessageLocation(), 
		                "RssParser.UnexpectedRlsFileName", //$NON-NLS-1$
		                new Object[] { fileName, ext });
			}
			
			reparseFile(rlsFile);
			return incl;
			
		}
		
		// look for #ifdef LANGUAGE/#endif
		if (srcFile instanceof IAstLocSourceFile 
				|| lxxFilePattern.matcher(fileName).matches()) {
			// remove in case we change this to a bigger range
			if (existing != null)
				srcFile.removeFileNode(existing);
			incl = tryParsingLxxInclude(srcFile, inclFile, directive, endOffset, isUser);
			// add back if that failed
			if (existing != null && incl == null)
				srcFile.addFileNode(existing);
		}
		if (incl != null) {
			// it was added as an #include
			IAstLxxSourceFile lxxFile = (IAstLxxSourceFile) incl.getFile();

			if (DUMP) System.out.println("Reparsing as LXX file");

			// sanity check included file to make sure it looks like a normal file
			Matcher lxxFileMatcher = lxxFilePattern.matcher(fileName);
			if (!lxxFileMatcher.matches() || Integer.parseInt(lxxFileMatcher.group(1)) != lxxFile.getLanguageCode()) {
				String ext = "" + (lxxFile.getLanguageCode()/10) + (lxxFile.getLanguageCode()%10);
				Messages.emit(
		                IMessage.WARNING,
		                incl.getSourceRange().createMessageLocation(), 
		                "RssParser.UnexpectedLxxFileName", //$NON-NLS-1$
		                new Object[] { fileName, ext });
			}
			
			return incl;
		} 
		
		// possible view model file with its own loc files
		if (existing != null
				&& isUser
				&& srcFile == srcFile.getTranslationUnit().getSourceFile()
				&& viewRssFilePattern.matcher(fileName).matches()
				&& existing.getFile() instanceof IAstRssSourceFile) {

			// ensure the #include is the right type
			if (!(existing instanceof IAstPreprocessorModelIncludeNode)) {
				srcFile.removeFileNode(existing);
				incl = new AstPreprocessorModelIncludeNode(existing.getFile());
				incl.setSourceRange(existing.getSourceRange());
				srcFile.addFileNode(incl);
			} else
				incl = existing;
			
			
			if (DUMP) {
				System.out.println("Recursing into model file");
				System.out.println(((IAstRssSourceFile) incl.getFile()).getFileNodeList());
			}
			
			reparseLocalizationFileIncludes((IAstRssSourceFile) incl.getFile());
			if (DUMP) {
				System.out.println("After recursing into model file");
				System.out.println(((IAstRssSourceFile) incl.getFile()).getFileNodeList());
			}
			return incl;
		}
		
		if (existing != null)
			return existing;

		return null;
	}

	/**
	 * Reparse a previously-parsed IAstSourceFile and replace its contents from the file.
	 * This maintains the existing ISourceFile and IAstSourceFile entries for the file.
	 * @param manager
	 * @param includeHandler
	 * @param file
	 * @throws IOException
	 */
	public static void reparseRssFile(IRssProjectFileManager manager, IIncludeFileLocator includeHandler, IAstSourceFile file) throws IOException {
		RssParser parser = new RssParser(manager, includeHandler, null);
		ITranslationUnit tu = parser.parseTu(file.getSourceFile(), true);
		file.removeAllContents();
		file.moveContents(tu.getSourceFile());

		file.setSourceRange(new SourceRange(file.getSourceFile(), 0, file.getSourceFile().getLength()));
		ParserBase.setFileNodesSourceRange(file);
	}
	
	/**
	 * Reparse a given file
	 * @param file
	 */
	private void reparseFile(IAstSourceFile file) {
		try {
			reparseRssFile(manager, includeHandler, file);
		} catch (IOException e) {
			SourceGenPlugin.getDefault().log(e);
		}
	}

	/**
	 * Detect whether the include is an *.lxx include by looking at
	 * the filename and adding any #ifdef/#endif to its source range.
	 * @param locFile 
	 * @param targetFile #included file, possible lxx file
	 * @param sourceFile host file containing include
	 * @param offset cursor at #include
	 * @param endOffset end of #include
	 * @return lxx source file #include with proper source range, or null
	 */
	private IAstPreprocessorIncludeDirective tryParsingLxxInclude(IAstSourceFile locFile, ISourceFile targetFile, int offset, int endOffset, boolean isUser) {
		return tryParsingIfdefInclude(locFile, targetFile, offset, endOffset, isUser,
				AstLxxSourceFile.class, AstPreprocessorLocIncludeNode.class);
	}

	/**
	 * Detect whether the include is an *.rls include by looking at
	 * the filename and adding any #ifdef/#endif to its source range.
	 * @param locFile 
	 * @param targetFile #included file, possible rls file
	 * @param sourceFile host file containing include
	 * @param offset cursor at #include
	 * @param endOffset end of #include
	 * @return rls source file #include with proper source range, or null
	 */
	private IAstPreprocessorIncludeDirective tryParsingRlsInclude(IAstSourceFile locFile, ISourceFile targetFile, int offset, int endOffset, boolean isUser) {
		return tryParsingIfdefInclude(locFile, targetFile, offset, endOffset, isUser, 
				AstRlsSourceFile.class,
				AstPreprocessorRlsIncludeNode.class);
	}

	/**
	 * Detect an #include of a file inside an #if[def]/#endif block
	 * matching the given filename [attern.
	 * <p>
	 * If matching, create a source file node of the appropriate type, create
	 * an #include node with the #ifdef/#endif in its source range.
	 * @param srcFile host file
	 * @param targetFile #included file, possible lxx file
	 * @param offset cursor at #include
	 * @param endOffset end of #include
	 * @param fileMatcher pattern to match filename
	 * @param klazz AstSourceFile type to create
	 * @param inclKlazz AstPreprocessorIncludeDirective type to create
	 * @return lxx source file #include with proper source range, or null
	 */
	private IAstPreprocessorIncludeDirective tryParsingIfdefInclude(
			IAstSourceFile srcFile, ISourceFile targetFile, int offset, int endOffset, boolean isUser,
			Class klazz, Class inclKlazz) {
		
		char[] text = srcFile.getSourceFile().getText();
		int start = ParseUtils.searchDirectiveBackwards(text, offset, 0);
		int end = ParseUtils.searchDirectiveForwards(text, endOffset-1, text.length);
	
		if (start != -1 && end != -1) {
			end = ParseUtils.skipToLogicalEndOfLineForward(text, end, text.length);
			end = ParseUtils.skipIfNewLine(text, end);
			
			String arg = null;
			Tuple dirInfo;
			dirInfo = ParseUtils.findDirectiveAndArgument("ifdef", text, start, offset);
			if (dirInfo == null) {
				dirInfo = ParseUtils.findDirectiveAndArgument("if", text, start, offset);
				if (dirInfo != null) {
					// ensure it's defined(...)
					Matcher definedMatcher = definedPattern.matcher((CharSequence) dirInfo.get(0));
					if (definedMatcher.matches()) {
						arg = definedMatcher.group(1);
					}
				}
			} else {
				arg = (String) dirInfo.get(0);
			}
			if (arg == null)
				return null;
			
			// Ensure it's LANGUAGE_xxx
			Matcher langMatcher = langPattern.matcher(arg);
			if (!langMatcher.matches())
				return null;
			
			int langMacroCode = Integer.parseInt(langMatcher.group(1));
			
			// create a file node
			IAstSourceFile targetSrcFile;
			targetSrcFile = manager.findRssFile(targetFile);
			if (!klazz.isInstance(targetSrcFile)) {
				targetSrcFile = Utilities.constructAstSourceFile(klazz, 
						new Object[] { new Integer(langMacroCode) }, targetFile);
				if (targetSrcFile == null)
					return null;
				targetSrcFile.setTranslationUnit(srcFile.getTranslationUnit());
				manager.loadSourceFile(targetFile);
		    	if (targetSrcFile.getSourceRange() == null)
		    		targetSrcFile.setSourceRange(new SourceRange(targetFile, 0, targetFile.getLength()));
		    	if (manager.findRssFile(targetFile) == null)
		    		manager.registerRssFile((IAstRssSourceFile) targetSrcFile);
		    	// reparse for statements like CHARACTER_SET
		    	reparseFile(targetSrcFile);
			} else {
				// already parsed
			}
			

	    	IAstPreprocessorIncludeDirective incl;
	    	if (IAstPreprocessorRlsIncludeNode.class.isAssignableFrom(inclKlazz))
	    		incl = new AstPreprocessorRlsIncludeNode(targetSrcFile.getSourceFile().getFileName(), 
	    				isUser, targetSrcFile, langMacroCode); 
	    	else if (IAstPreprocessorLocIncludeNode.class.isAssignableFrom(inclKlazz))
	    		incl = new AstPreprocessorLocIncludeNode(targetSrcFile.getSourceFile().getFileName(), 
	    				isUser, targetSrcFile); 
	    	else
	    		incl = new AstPreprocessorIncludeDirective(targetSrcFile.getSourceFile().getFileName(), 
	    				isUser, targetSrcFile);
	    	incl.setSourceRange(new SourceRange(srcFile.getSourceFile(), start, end - start));
			srcFile.addFileNode(incl);
			return incl;
		}
		return null;
	}


	/**
     * Get the last known AST source file for a given path.
     * @param file
     */
    public IAstSourceFile getSourceFile(IPath path) {
    	ISourceFile sf = manager.findSourceFile(path.toFile());
    	return srcMap.get(sf);
    }

    /**
     * Get the last known AST source file for a given source file.
     * @param file
     */
    public IAstSourceFile getSourceFile(ISourceFile file) {
    	return srcMap.get(file);
    }

	/**
	 * Callback from parser to add a top-level node to the current IAstSourceFile
	 * @param node
	 */
	public void addTopLevelNode(IAstTopLevelNode node) {
	    ISourceRange range = node.getSourceRange();
	    Check.checkState(range != null);
	    IAstSourceFile asf = getSourceFile(range.getFile());
	    Check.checkState(asf != null);
	    if (asf instanceof IAstRssSourceFile) {
	    	((IAstRssSourceFile)asf).appendFileNode(node);
	    }
		
	}

	/**
	 * @param type
	 * @return
	 */
	public IAstStructDeclaration findStructDeclaration(String type) {
		return tu.findStructDeclaration(type);
	}

	/**
	 * @param itoken
	 */
	/*
	public IAstSourceFile updateSourceFile(IToken itoken) {
		IDocumentTokenLocation location = (IDocumentTokenLocation) itoken.getLocation();
		setCurrentFile(location.getPath());
		return null;
	}
	*/

	public IASTPreprocessorStatement filterDirective(IASTPreprocessorStatement directive) {
		return directive;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorFilter#filterIncludeEntry(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIncludeStatement, org.eclipse.core.runtime.IPath)
	 */
	public IASTPreprocessorStatement filterIncludeEntry(
			IASTPreprocessorIncludeStatement include, IPath path) {
		return new AugmentedIncludeStatement(include, path);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorFilter#filterIncludeExit()
	 */
	public IASTPreprocessorStatement filterIncludeExit() {
		return new AugmentedIncludeExitStatement();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorCallbacks#handleDirective(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorStatement)
	 */
	public void handleDirective(IASTPreprocessorStatement directive) {
		if (directive instanceof IASTPreprocessorDefineStatement) {
			detectedDefines.add((IASTPreprocessorDefineStatement) directive);
		}
	}
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorCallbacks#handleProblem(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTProblemTopLevelNode)
	 */
	public void handleProblem(IASTProblemTopLevelNode problem) {
    	MessageReporting.emitMessage(problem.getMessage());
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorCallbacks#handleIncludeEntry(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIncludeStatement, java.io.File)
	 */
	public void handleIncludeEntry(IASTPreprocessorIncludeStatement include,
			IPath path) {
		IRegion region = include.getSourceRegion().getInclusiveHeadRegion().getRegion();
		enterInclude(path, region.getOffset(),
				region.getOffset() + region.getLength());
	}
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorCallbacks#handleIncludeExit()
	 */
	public void handleIncludeExit() {
		exitInclude();
	}
	
}
