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
/**
 * 
 */
package com.nokia.sdt.sourcegen.doms.rss.impl;

import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefineFactory;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.sourcegen.*;
import com.nokia.sdt.sourcegen.core.*;
import com.nokia.sdt.sourcegen.doms.rss.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorDefineDirective;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorIncludeDirective;
import com.nokia.sdt.sourcegen.doms.rss.parser.RssCodeReaderFactory;
import com.nokia.sdt.sourcegen.doms.rss.parser.RssParser;
import com.nokia.sdt.symbian.ISymbianNameGenerator;
import com.nokia.sdt.utils.*;

import org.eclipse.cdt.core.dom.ICodeReaderFactory;
import org.eclipse.cdt.core.parser.CodeReader;
import org.eclipse.core.runtime.*;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

/**
 * Manager for RSS files associated with a model.
 * 
 *
 */
public class RssProjectFileManager implements IRssProjectFileManager, IRssProjectFileHandling {
    /** The CDT engine to read files */
    private ICodeReaderFactory codeReader;

	/**
	 * The list of ISourceFiles we know about 
	 */
	private List<ISourceFile> sourceFiles;
	/**
	 * Generated files, shared DOM elements
	 */
	private Map<ISourceFile, IAstRssSourceFile> rssFiles;

	/** Map of IRssModelProxy|String -> ITranslationUnit */
	private Map<Object, ITranslationUnit> translationUnits;
	
	private Map<IRssModelProxy, ISourceFile> proxyFiles;
	
	private ISourceGenProvider provider;

	public boolean DUMP = false;

	private INewFileContentCreator newFileContentCreator;

	/**
	 * 
	 */
	public RssProjectFileManager(ISourceGenProvider provider) {
		Check.checkArg(provider);
		this.provider = provider;
        this.sourceFiles = new ArrayList<ISourceFile>();
        this.codeReader = new RssCodeReaderFactory();
        this.rssFiles = new LinkedHashMap<ISourceFile, IAstRssSourceFile>();
        this.translationUnits = new HashMap<Object, ITranslationUnit>();
        this.proxyFiles = new HashMap<IRssModelProxy, ISourceFile>();
        
        this.newFileContentCreator = new RssSourceFileContentCreator();
	}

	public void reset() {
        sourceFiles.clear();
        rssFiles.clear();
        translationUnits.clear();
        proxyFiles.clear();
        codeReader = new RssCodeReaderFactory();
	}
	
	/**
	 * Find a registered source file
	 * @param file the file to locate
	 * @return ISourceFile or null
	 */
	public ISourceFile findSourceFile(File file) {
	    try {
	        file = file.getCanonicalFile();
	    } catch (IOException e) {
	        
	    }
	    for (Iterator iter = sourceFiles.iterator(); iter.hasNext();) {
	        ISourceFile sf = (ISourceFile) iter.next();
	        if (sf.getFile().equals(file)) 
	            return sf;
	    }
	    return null;
	}

	/**
	 * Find a registered source file
	 * @param file the file to locate
	 * @return ISourceFile or null
	 */
	public ISourceFile isSourceFileRegistered(ISourceFile file) {
	    for (Iterator iter = sourceFiles.iterator(); iter.hasNext();) {
	        ISourceFile sf = (ISourceFile) iter.next();
	        if (sf == file) 
	            return sf;
	    }
	    return null;
	}

    /**
     * Register a new source file
     * @param file the file to add
     */
    public void registerSourceFile(ISourceFile file) {
        Check.checkArg(!sourceFiles.contains(file));

        // assert that we do not have duplicate references
        // to files that do not share ISourceFile
        for (Iterator iter = sourceFiles.iterator(); iter.hasNext();) {
            ISourceFile sf = (ISourceFile) iter.next();
            Check.checkState(!sf.getFile().equals(file.getFile()));
        }
 
        sourceFiles.add(file);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelFileManager#removeSourceFile(com.nokia.sdt.sourcegen.core.ISourceFile)
     */
    public void removeSourceFile(ISourceFile existing) {
    	Check.checkArg(sourceFiles.contains(existing));
    	sourceFiles.remove(existing);
    }

	public void loadSourceFile(ISourceFile file) {
	    Check.checkArg(file);

	    if (file.getText() != null && file.getLength() > 0 && file.getSavedTimestamp() != 0)
	    	return;
	    
	    reloadSourceFile(file);
	}

	public void reloadSourceFile(ISourceFile file) {
	    Check.checkArg(file);
	    
	    // We have to do this so rewritten text buffers 
	    // are not confused with on-disk buffers as used by CDT

	    if (DUMP) System.out.println("loadSourceFile " + file);
	    // NOTE: might not be null
        CodeReader reader = codeReader.createCodeReaderForInclusion(
        		file.getFile().getAbsolutePath());
        if (reader != null) {
        	file.setText(reader.buffer);
        }
        file.setDirty(false);
        file.setSavedTimestamp(provider.getFileTracker().getModificationTime(file.getFile()));
	}

	public ISourceFile findProxySourceFile(IRssModelProxy proxy) {
		ISourceFile sf = proxyFiles.get(proxy);
		return sf;
	}
	
	public void registerProxySourceFile(IRssModelProxy proxy, ISourceFile sf) {
		Check.checkArg(proxy);
		if (sf != null) {
			Check.checkArg(sourceFiles.contains(sf));
			proxyFiles.put(proxy, sf);
		} else {
			proxyFiles.remove(proxy);
		}
	}


	private ISourceFile getRssSourceFile(IDesignerDataModel model, String fileName) {
		String dir = provider.getNameGenerator()
				.getProjectRelativeDirectory(model, ISymbianNameGenerator.RESOURCE_DIRECTORY_ID);
		File file = new File(new File(provider.getBaseDirectory(), dir), fileName);

		ISourceFile sourceFile = findSourceFile(file);
		if (sourceFile == null) {
			sourceFile = new SourceFile(file);
			registerSourceFile(sourceFile);
		}
		return sourceFile;
	}

	public ISourceFile getRssSourceFile(IRssModelProxy proxy, String filename) {
		// start from the proxy base, and base other files off its location
		ISourceFile base = findProxySourceFile(proxy);
		if (base == null) {
			base = getRssSourceFile(proxy.requestDataModel(), proxy.getRssFileName());
			registerProxySourceFile(proxy, base);
		}
		ISourceFile sf = base;
		if (filename != null) {
			File alternate = new File(sf.getFile().getParentFile(), filename);
			sf = findSourceFile(alternate);
			if (sf == null) {
				sf = new SourceFile(alternate);
				registerSourceFile(sf);
			}
		}
		return sf;
	}
	
	public Collection<IAstRssSourceFile> getGeneratedRssFiles() {
		List<IAstRssSourceFile> files = new LinkedList<IAstRssSourceFile>(rssFiles.values());
		for (Iterator iter = files.iterator(); iter.hasNext();) {
			IAstRssSourceFile rssFile = (IAstRssSourceFile) iter.next();
			if (rssFile.isReadOnly())
				iter.remove();
		}
		return files;
	}

	public IAstRssSourceFile findRssFile(ISourceFile sourceFile) {
		return rssFiles.get(sourceFile);
	}

	public void registerRssFile(IAstRssSourceFile file) {
	    Check.checkArg(sourceFiles.contains(file.getSourceFile()));
	    Check.checkArg(rssFiles.get(file.getSourceFile()) == null
	    		|| rssFiles.get(file.getSourceFile()) == file);
	    Check.checkArg(file.getTranslationUnit() != null);
	    rssFiles.put(file.getSourceFile(), file);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssProjectFileManager#removeRssFile(com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile)
	 */
	public void removeRssFile(IAstRssSourceFile rssFile) {
		rssFile.setTranslationUnit(null);
		rssFiles.remove(rssFile.getSourceFile());
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssProjectFileManager#replaceRssFile(com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile)
	 */
	public void replaceRssFile(IAstRssSourceFile rssFile) {
    	IAstRssSourceFile oldFile = findRssFile(rssFile.getSourceFile());
    	if (oldFile != rssFile) {
    		if (oldFile != null)
    			removeRssFile(oldFile);
    		registerRssFile(rssFile);
    	}
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssProjectFileManager#getRssFile(com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy, java.lang.String)
	 */
	public IAstRssSourceFile findOrCreateRssFile(IRssModelProxy proxy, String fileName, boolean allowCreate) {
		
		ISourceFile sf = getRssSourceFile(proxy, fileName);
		if (sf != null) {
			IAstRssSourceFile rssFile = findRssFile(sf);
			if (rssFile == null && allowCreate) {
				rssFile = new AstRssSourceFile(sf);
				
				boolean mustRegister = true;
				boolean createdNew = true;

				if (createdNew) {
					if (DUMP) System.out.println("Creating new " + rssFile + " (register="+mustRegister);
					setupNewRssFile(rssFile);
				}
				
				ITranslationUnit tu = findTranslationUnit(proxy, fileName);
				if (tu == null) {
					tu = new TranslationUnit(rssFile);
					registerTranslationUnit(proxy, fileName, tu);
				} else {
					// ensure there is no stray version of the rssFile
					IAstPreprocessorIncludeDirective otherInclude = ((IAstRssSourceFile) tu.getSourceFile()).findInclude(sf);
					if (otherInclude != null) {
						IAstSourceFile otherRssFile = otherInclude.getFile(); 
						if (otherRssFile != null && otherRssFile != rssFile) {
							Check.checkState(otherRssFile.isReadOnly());
							otherInclude.setFile(rssFile);
						}
					}
				}
				
				rssFile.setTranslationUnit(tu);
				tu.refresh();
				if (!createdNew)
					rssFile.setDirty(false);
				
				if (mustRegister)
					registerRssFile(rssFile);
			}
			return rssFile;
		}
		return null;
	}
	
	public void setupNewRssFile(IAstRssSourceFile rssFile) {
		// ignore existing files, which probably already have these comments
		// (also, we reparse the file and don't want to missync the
		// comments)
		boolean exists = rssFile.getSourceFile().getFile().exists();
		if (exists)
			return;
		
		if (!provider.isOneWayUpdate()) {
	    	// add comment indicating file is generated
	    	String text = Messages.getString("SourceGenContext.GeneratedFile"); //$NON-NLS-1$
	    	rssFile.addFileNode(new AstPreprocessorTextNode(text));
		}

		// add file template
		String newText = SourceGenUtils.getNewFileContents(rssFile.getSourceFile().getFile());
		if (newText.length() > 0) {
			newText = TextUtils.canonicalizeNewlines(newText, provider.getSourceFormatter().getSourceFormatting().getEOL());
			rssFile.addFileNode(new AstPreprocessorTextNode(newText));
		}
		
		// add initial contents
		INewFileContentCreator creator = newFileContentCreator;
		if (creator != null) {
			IAstNode[] nodes = creator.createNewContent(rssFile);
			for (IAstNode node : nodes) {
				rssFile.addFileNode(node);
			}
		}
	}

	public void registerRssFiles(List<IAstRssSourceFile> files) {
		for (Iterator iter = files.iterator(); iter.hasNext();) {
			IAstRssSourceFile file = (IAstRssSourceFile) iter.next();
			registerRssFile(file);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssProjectFileManager#getTranslationUnit(com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy, java.lang.String)
	 */
	public ITranslationUnit findTranslationUnit(IRssModelProxy proxy, String fileName) {
		Object key = fileName != null ? fileName : proxy.getProjectInfo().getRootModelProxy();
		return translationUnits.get(key);
	}
	
	public ITranslationUnit createOrLoadTranslationUnit(IRssModelProxy proxy, String fileName) {
		// the real root proxy, or null
		IRssRootModelProxy rootProxy = proxy.getProjectInfo().getRootModelProxy();
		
		// the proxy that owns the translation unit
		IRssModelProxy toplevelProxy;
		if (fileName == null) {
			toplevelProxy = rootProxy;
			if (toplevelProxy == null)
				toplevelProxy = proxy;
		} else {
			// secondary files are independent 
			toplevelProxy = proxy;
		}
		
		// get the top-level RSS file
		IAstRssSourceFile toplevelRssFile = findOrCreateRssFile(toplevelProxy, fileName, true);

		// get the model's RSS file
		IAstRssSourceFile modelRssFile = findOrCreateRssFile(proxy, fileName, true);

		if (!provider.isOneWayUpdate()) {
			if (DUMP) System.out.println("one way export");
			if (toplevelProxy != proxy) {
				// get a dummy root RSS file, which will not be written,
				// but which holds a reference to the current view RSSI
				// so we can find its resources
				if (DUMP) System.out.println("\tclearing out " + toplevelRssFile);
				toplevelRssFile.removeAllContents();
				toplevelRssFile.addFileNode(
						new AstPreprocessorModelIncludeNode(modelRssFile));
				toplevelRssFile.setReadOnly(true);
			}
			
			if (DUMP) System.out.println("\tclearing out " + modelRssFile);
			modelRssFile.removeAllContents();
			setupNewRssFile(modelRssFile);
			
		} else {
			if (DUMP) System.out.println("one way update");

			// do we use what we have or start over?
			boolean useExistingTu = true;
			if (toplevelRssFile.getTranslationUnit() == null) {
				new TranslationUnit(toplevelRssFile);
				useExistingTu = false;
				if (DUMP) System.out.println("\tno existing TU");
				
			} else if (toplevelRssFile.hasDirtySourceTree() || modelRssFile.hasDirtySourceTree()) {
				// The file is dirty, which we take to mean it is new (and
				// populated with default statements) or was reverted.
				// Otherwise, if the TU is actively being managed, it
				// would already be registered.
				useExistingTu = false;
				if (DUMP) System.out.println("\tdirty or new file: "+ toplevelRssFile+"="+toplevelRssFile.hasDirtySourceTree()
						+ " || " + modelRssFile+"=" + modelRssFile.hasDirtySourceTree());
			} else {
				// If we added a temporary #include of the model RSSI, we need to reparse
				IAstPreprocessorIncludeDirective include = toplevelRssFile.findInclude(modelRssFile.getSourceFile());
				if (include == null) {
					useExistingTu = false;
					if (DUMP) System.out.println("\tfound dummy #include of "+ modelRssFile + " in " + toplevelRssFile);
				} else if (include.getFile() != modelRssFile) {
					//useExistingTu = false;
					include.setFile(modelRssFile);
					if (DUMP) System.out.println("\treplaced dummy #include of "+ modelRssFile + " in " + toplevelRssFile);
				}
			}

			if (!useExistingTu) {
				// need to reparse or regenerate
				boolean parseExisting = toplevelRssFile.getSourceFile().getFile().exists();
				

				if (parseExisting) {
					if (DUMP) System.out.println("\tparsing existing " + toplevelRssFile);
					parseTranslationUnit(toplevelRssFile.getTranslationUnit());
				} else {
					if (DUMP) System.out.println("\tclearing out " + modelRssFile);
					modelRssFile.removeAllContents();
					setupNewRssFile(modelRssFile);
				}
			}
		}
		
		return toplevelRssFile.getTranslationUnit();
	}
	
	/**
	 * Parse a translation unit
	 * @param tu
	 */
	private void parseTranslationUnit(ITranslationUnit tu) {
		try {
			// ensure the file is up to date first
			tu.rewrite(provider.getSourceFormatter());

			if (DUMP) System.out.println("parsing TU: "+ tu.getSourceFile());
			RssParser parser = new RssParser(this, provider.getIncludeFileLocator(), null);
			parser.reparse(tu, true);
		} catch (Exception e) {
			Logging.log(SourceGenPlugin.getDefault(),
					Logging.newStatus(
					SourceGenPlugin.getDefault(),
					IStatus.ERROR,
					MessageFormat.format(
							"Failed to parse resources from ''{0}''",
							new Object[] { tu.getSourceFile().getSourceFile().getFile() }),
					e));
		}

	}


	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssProjectFileManager#getTranslationUnit(com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy, java.lang.String)
	 */
	public void registerTranslationUnit(IRssModelProxy proxy, String fileName, ITranslationUnit tu) {
		Check.checkArg(tu);
		Object key = fileName != null ? fileName : proxy.getProjectInfo().getRootModelProxy();
		if (translationUnits.get(key) == tu)
			return;
		/* this happens when running with a loaded model -- we hope both don't try to change the RSS!
		for (Iterator iter = translationUnits.values().iterator(); iter.hasNext();) {
			ITranslationUnit regTu = (ITranslationUnit) iter.next();
			if (regTu.getSourceFile() == tu.getSourceFile())
				Check.checkState(false);
		}*/
		translationUnits.put(key, tu);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssProjectFileManager#removeTranslationUnit(com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit)
	 */
	public void removeTranslationUnit(ITranslationUnit translationUnit) {
		translationUnits.values().remove(translationUnit);
	}
	
	public IAstSourceFile mergeIncludeFile(ITranslationUnit tu, 
	        String includeName, ISourceFile file) {
	    Check.checkArg(tu);
	    Check.checkArg(includeName);
	    Check.checkArg(tu.getSourceFile() instanceof IAstRssSourceFile);
	
	    IAstRssSourceFile mainFile = (IAstRssSourceFile) tu.getSourceFile();
	    
	    // don't waste time if the TU directly included this file
	    IAstSourceFile dupe = mainFile.findIncludeFile(file);
	    if (dupe != null) {
	        return dupe;
	    }
	
	    // get prefix macros from current TU to compile include
	    Collection<IDefine> macros = new ArrayList();
	    if (tu.getLocalSourceFile() instanceof IAstRssSourceFile) {
	        IAstPreprocessorDefineDirective[] macroList;
	        macroList = ((IAstRssSourceFile) tu.getLocalSourceFile()).getDefines();
	        for (int i = 0; i < macroList.length; i++) {
	            macros.add(DefineFactory.createDefine(
	            			macroList[i].getMacro().getName(),
	            			macroList[i].getMacro().getExpansion()));
	        }
	    }
	    
	    try {
	        // parse the new include
	        RssParser parser = new RssParser(this, provider.getIncludeFileLocator(), macros);
	        ITranslationUnit subTu = parser.parseTu(file, false);
	        IAstRssSourceFile subFile = (IAstRssSourceFile) subTu.getSourceFile();
	        
	        subFile.setReadOnlyRecursive(true);
	        
	        tu.refresh();

	        // anything this file includes, we can remove from the main TU
	        IAstPreprocessorIncludeDirective[] subIncludes = subFile.getIncludeFiles();
	        for (int i = 0; i < subIncludes.length; i++) {
	            IAstSourceFile subSrcFile = subIncludes[i].getFile();
	            if (subSrcFile != null) {
	                ISourceFile subInclude = subSrcFile.getSourceFile();
	                IAstPreprocessorIncludeDirective incl = mainFile.findInclude(subInclude);
	                if (incl != null)
	                	mainFile.removeFileNode(incl);
	            }
	        }
	        
	        // add #include to primary RSS file
	        mainFile.addFileNode(new AstPreprocessorIncludeDirective(
	                includeName, false, subFile));
	        subFile.setTranslationUnit(tu);
	        tu.refresh();
	
	        return subFile;
	    } catch (IOException e) {
	        Messages.emit(IMessage.ERROR,
	                new MessageLocation(new Path(file.getFile().getAbsolutePath())),
	                "RssEngine.CannotParseInclude", //$NON-NLS-1$
	                new Object[] { file.getFile().getAbsolutePath(), e.getLocalizedMessage() });
	        return null;
	    }
	}

	/**
	 * Make sure the given header is included in the translation nit.
	 * @param tu
	 * @param header
	 * @return file or null
	 */
	public File ensureIncludedFile(ITranslationUnit tu, String header) {
	    File ifile = provider.getIncludeFileLocator().findIncludeFile(header, false, null); 
	    if (ifile != null) {
	        if (tu.findIncludedFile(ifile.getAbsolutePath()) == null) {
	            ISourceFile file;
	            file = findSourceFile(ifile);
	            if (file == null) {
	                file = new SourceFile(ifile);
	                registerSourceFile(file);
	                loadSourceFile(file);
	            }
	            mergeIncludeFile(tu, header, file);
	        }
	    }
	    return ifile;
	}

	/**
	 * Find or create a file whose name is derived from the given
	 * file.
	 * @param baseFile the file based on which to name the derived file
	 * @param directoryId the destination directory id for { @link com.nokia.sdt.sourcegen.INameGenerator#getProjectRelativeDirectory(String) } 
	 * @param suffix 
	 * @param extension new extension (e.g. ".foo")
	 * @param context
	 * @return a source file
	 */
	public ISourceFile findOrCreateDerivedFile(
	        IDesignerDataModel dataModel, 
	        ISourceFile baseFile,
	        String directoryId, String suffix, String extension) {
	    String newName;
	    if (suffix == null)
	        suffix = ""; //$NON-NLS-1$
	    String baseName = baseFile.getFileName();
	    int ext = baseName.lastIndexOf('.');
	    if (ext >= 0)
	        newName = baseName.substring(0, ext) + suffix + extension;
	    else
	        newName = baseName + suffix + extension;
	    
	    File file = getProjectFile(dataModel, directoryId, newName); 
	            
	    ISourceFile sourceFile = findSourceFile(file);
	    if (sourceFile == null) {
	        sourceFile = new SourceFile(file);
	        registerSourceFile(sourceFile);
	    }
	    
	    return sourceFile;
	}

	/**
	 * Get the target for a file given the directory moniker and the filename.
	 * @param directoryId
	 * @param newName
	 * @return project-relative file
	 */
	private File getProjectFile(IDesignerDataModel dataModel, String directoryId, String newName) {
		File file = new File(provider.getBaseDirectory(), 
				provider.getNameGenerator().getProjectRelativeDirectory(dataModel, directoryId));
		return new File(file, newName);
	}

	public IAstSourceFile findOrCreateDerivedSourceFile(
			IDesignerDataModel dataModel, 
	       IAstRssSourceFile rssFile, String directoryId, String suffix,
	       String extension, Class klazz, Object[] args, boolean includeIt) {
	   ISourceFile file = findOrCreateDerivedFile(
	           dataModel, 
	           rssFile.getSourceFile(), directoryId, suffix, extension);
	   IAstSourceFile srcFile = null;
	   srcFile = findRssFile(file);
	   if (srcFile != null)
		   srcFile = findOrReplaceTypedFile(rssFile.getTranslationUnit(), file, klazz, args);
	   if (srcFile == null) {

		   // create a file node
		   srcFile = Utilities.constructAstSourceFile(klazz, args, file);
		   if (srcFile == null)
			   return null;
		   
		   if (file.getFile().exists()) {
			   // need to reparse existing
			   try {
				   RssParser.reparseRssFile(this, this, srcFile);
			   } catch (IOException e) {
				   SourceGenPlugin.getDefault().log(e);
				   
				   setupNewRssFile((IAstRssSourceFile) srcFile);
			   }
		   } else {
			   setupNewRssFile((IAstRssSourceFile) srcFile);
		   }
		   
	       // add #include either to main RSS or to a location where it
	       // will otherwise show up as a contribution to the TU
	       ITranslationUnit tu = rssFile.getTranslationUnit();
	       if (includeIt) {
	    	   IAstPreprocessorIncludeDirective incl = rssFile.findInclude(file.getFileName());
	    	   if (incl == null) {
		           incl = new AstPreprocessorIncludeDirective(
		                   file.getFileName(), true, srcFile);
		           rssFile.addFileNode(incl);
	    	   } else {
	    		   // replace existing include: it looks the same in source,
	    		   // but the DOM is now referring to the right child
	    		   incl.setFile(srcFile);
	    	   }
	       } else {
	    	   tu.addAuxiliarySourceFile(srcFile);
	       }
	       srcFile.setTranslationUnit(tu);
	   }
	   return srcFile;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.IIncludeFileLocator#findIncludeFile(java.lang.String, boolean, java.io.File)
	 */
	public File findIncludeFile(String file, boolean isUser, File currentDir) {
		return provider.getIncludeFileLocator().findIncludeFile(file, isUser, currentDir);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.IIncludeFileLocator#getIncludePaths()
	 */
	public String[] getIncludePaths() {
		return provider.getIncludeFileLocator().getIncludePaths();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssProjectFileManager#saveModifiedSources(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void saveModifiedSources(IProgressMonitor monitor) throws CoreException {
		IFileTracker fileTracker = provider.getFileTracker();
		Set<ITranslationUnit> touchTus = new HashSet<ITranslationUnit>();
		for (Iterator iter = rssFiles.values().iterator(); iter.hasNext();) {
			IAstRssSourceFile rssFile = (IAstRssSourceFile) iter.next();
			if (writeFile(fileTracker, rssFile)) {
				touchTus.add(rssFile.getTranslationUnit());
			}
		}

		// Bug 442 is fixed now.  We may still need it in the event that system
		// files (outside the workspace) are modified, but we cannot do this anyway.
		// (Both because we explicitly mark system files read-only, and
		// because our file saver only operates on workspace resources.)
		/*
		 // Due to bug 442, we have to manually touch all the top-level RSS files
		for (Iterator iter = touchTus.iterator(); iter.hasNext();) {
			ITranslationUnit tu = (ITranslationUnit) iter.next();
			fileTracker.touchFile(tu.getSourceFile().getSourceFile().getFile());
		}*/
	}

	/**
	 * Write a file, if necessary, and tell whether it was updated
	 * @param fileSaver
	 * @param rssFile
	 * @return
	 * @throws CoreException
	 */
    private boolean writeFile(IFileSaver fileSaver, IAstRssSourceFile rssFile) throws CoreException {
        ISourceFile sf = rssFile.getSourceFile();
        if (DUMP) System.out.print("write "+rssFile+"?");
        if (rssFile.isReadOnly()) {
        	if (DUMP) System.out.println("... read only");
        	return false;
        }
        
        boolean mustRewrite = !fileSaver.fileExists(sf.getFile());
        
    	// don't rewrite if existing unless changes...
        boolean hasChanges = sf.isDirty();
        if (rssFile.hasDirtySourceTree()) {
        	// must call this to sync the text even if we already know we're writing it
        	hasChanges |= rssFile.rewrite(provider.getSourceFormatter());
        }

        if (DUMP) System.out.println("... must="+mustRewrite+", changed="+hasChanges);

        if (mustRewrite || hasChanges) {
        	if (DUMP ) System.out.println("saving "+sf.getFile()+"\n"+new String(sf.getText()));
        	fileSaver.saveFileText(sf.getFile(), sf.getCharset(), sf.getText());
        	sf.setSavedTimestamp(provider.getFileTracker().getModificationTime(sf.getFile()));
        	return true;
        } 
    	if (DUMP) System.out.println("ignoring "+sf.getFile());
        return false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.IRssProjectFileManager#revert()
     */
    public void revert() {
    	rssFiles.clear();
    	translationUnits.clear();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.IRssProjectFileManager#findOrReplaceTypedFile(com.nokia.sdt.sourcegen.core.ISourceFile, java.lang.Class)
     */
    public IAstRssSourceFile findOrReplaceTypedFile(ITranslationUnit tu, ISourceFile inclFile, Class klazz,
    		Object[] constrArgs) {
    	Check.checkArg(AstRssSourceFile.class.isAssignableFrom(klazz));
		IAstRssSourceFile rssFile = findRssFile(inclFile);
		IAstRssSourceFile typedRssFile = null;
		if (rssFile != null) {
			if (!klazz.isInstance(rssFile)) {
				removeRssFile(rssFile);
				rssFile = null;
			} else
				typedRssFile = rssFile;
		}
		if (typedRssFile == null) {
			typedRssFile = (IAstRssSourceFile) Utilities.constructAstSourceFile(klazz, constrArgs, inclFile);
			loadSourceFile(inclFile);
			typedRssFile.setTranslationUnit(tu);
			registerRssFile(typedRssFile);
			setupNewRssFile(typedRssFile);
		}
		return typedRssFile;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.IRssProjectFileManager#synchronizeSourceFiles()
     */
    public boolean synchronizeSourceFiles() {
    	boolean anyChanges = false;
    	IFileTracker fileTracker = provider.getFileTracker();
    	List<ISourceFile> changedFiles = new ArrayList<ISourceFile>();
    	for (Iterator iter = sourceFiles.iterator(); iter.hasNext();) {
			ISourceFile sf = (ISourceFile) iter.next();
			
			if (!fileTracker.fileExists(sf.getFile())) {
				iter.remove();
				changedFiles.add(sf);
				for (Iterator iterator = proxyFiles.values().iterator(); iterator
						.hasNext();) {
					ISourceFile file = (ISourceFile) iterator.next();
					if (file == sf)
						iterator.remove();
				}
				anyChanges = true;
			} else if (fileTracker.getModificationTime(sf.getFile()) > sf.getSavedTimestamp()) {
				changedFiles.add(sf);
				sf.setDirty(true);
				reloadSourceFile(sf);
				anyChanges = true;
			}
		}

    	if (anyChanges) {
    		// clear messages previously generated
    		ParserMessages.deleteMessagesForFiles((ISourceFile[]) changedFiles.toArray(new ISourceFile[changedFiles.size()]));
    		rssFiles.clear();
    		translationUnits.clear();
    	}
    	return anyChanges;
    }
}
