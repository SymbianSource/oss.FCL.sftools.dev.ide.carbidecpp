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

package com.nokia.carbide.internal.cpp.epoc.engine.model.mmp;

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.*;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.*;
import com.nokia.carbide.internal.cpp.epoc.engine.model.*;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ITranslationUnitParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.mmp.IMMPParserConfiguration;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IConditionalBlock;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Version;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * View onto MMP contents.
 *
 */
public class MMPView extends ViewASTBase implements IMMPView {
	
	static final String SOURCE_INFO_PATTERN = "SOURCE|SOURCEPATH"; //$NON-NLS-1$

	static final String BITMAP_KEYWORD = "BITMAP"; //$NON-NLS-1$

	static final String RESOURCE_KEYWORD = "RESOURCE"; //$NON-NLS-1$
	
	private static final String DEF_FILE_EXTENSION = "def"; //$NON-NLS-1$
	private static final Pattern VERSION_PATTERN = Pattern.compile("(.*)\\{.*\\}"); //$NON-NLS-1$

	private List<IPath> sources;
	private List<IPath> documents;
	private List<IPath> userIncludes;
	private List<IPath> systemIncludes;
	private Map<EMMPStatement, List<String>> stringListArgumentSettings;
	private List<IMMPBitmap> bitmaps;
	private Set<EMMPStatement> flagSettings;
	private Map<EMMPStatement, String> singleArgumentSettings;
	private List<IMMPResource> resourceBlocks;
	private List<IMMPAIFInfo> aifs;

	private List<IPath> userResources;
	private List<IPath> systemResources;
	private List<EMMPLanguage> languages;
	private String uid2;
	private String uid3;
	private Map<String, String> options, linkerOptions, replaceOptions;
	
	IPath defFileBase;
	
	private List<IASTStatement> knownStatements;

	protected OrderedObjectMap<IMMPAIFInfo, IASTMMPAifStatement> aifToStatementMap;
	protected OrderedObjectMap<IMMPResource, IASTMMPStartBlockStatement> resourceBlockToStatementMap;
	protected OrderedObjectMap<IMMPBitmap, IASTMMPStartBlockStatement> bitmapBlockToStatementMap;
	
	/** read-only list for now */
	private Set<IPath> sourcePaths;

	public MMPView(ModelBase model, ITranslationUnitParser parser,
			IMMPViewConfiguration viewConfiguration) {
		super(model, parser, viewConfiguration);
		
		//System.out.println("Parsing view for " + model.getPath());
		
		stringListArgumentSettings = new LinkedHashMap<EMMPStatement, List<String>>();
		singleArgumentSettings = new LinkedHashMap<EMMPStatement, String>();
		flagSettings = new LinkedHashSet<EMMPStatement>();
		
		for (EMMPStatement stmt : EMMPStatement.values()) {
			if (((IMMPViewConfiguration) getViewConfiguration()).isStatementSupported(stmt)) {
				switch (stmt.getCategory()) {
				case IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT:
					if (isStringListArgumentSettingStatement(stmt)) {
						stringListArgumentSettings.put(stmt, new ArrayList<String>());
					}
					break;
				case IMMPParserConfiguration.SINGLE_ARGUMENT_STATEMENT:
					if (isSingleArgumentSettingStatement(stmt)) {
						singleArgumentSettings.put(stmt, null);
					}
					break;
				}
			} 
		}

		knownStatements = new ArrayList<IASTStatement>();

		sources = new ArrayList<IPath>();
		documents = new ArrayList<IPath>();
		
		userIncludes = new ArrayList<IPath>();
		systemIncludes = new ArrayList<IPath>();
		userResources = new ArrayList<IPath>();
		systemResources = new ArrayList<IPath>();
		resourceBlocks = new ArrayList<IMMPResource>();
		languages = new ArrayList<EMMPLanguage>();
		
		bitmaps = new ArrayList<IMMPBitmap>();
		aifs = new ArrayList<IMMPAIFInfo>();
		options = new LinkedHashMap<String, String>();
		linkerOptions = new LinkedHashMap<String, String>();
		replaceOptions = new LinkedHashMap<String, String>();
		
		defFileBase = convertModelToProjectPath(new Path("")); // //$NON-NLS-1$
		
		sourcePaths = new LinkedHashSet<IPath>();
		
		aifToStatementMap = new OrderedObjectMap<IMMPAIFInfo, IASTMMPAifStatement>();
		resourceBlockToStatementMap = new OrderedObjectMap<IMMPResource, IASTMMPStartBlockStatement>();
		bitmapBlockToStatementMap = new OrderedObjectMap<IMMPBitmap, IASTMMPStartBlockStatement>();
	}
	
	IASTMMPTranslationUnit mmp() {
		return (IASTMMPTranslationUnit) getFilteredTranslationUnit();
	}
	
	/**
	 * Identify a path which should not be converted to be project-relative.
	 * This includes actual absolute paths and special EPOCROOT\epoc32
	 * paths, which look relative but start with "+".
	 * @param mmpPath
	 * @return
	 */
	static boolean isAbsoluteLikePath(IPath mmpPath) {
		return (isAbsolutePath(mmpPath)
				|| (mmpPath.segmentCount() > 0 && mmpPath.segment(0).equals("+")));  //$NON-NLS-1$
	}
	
	/**
	 * Convert an MMP-relative string filename to a project-relative path if possible.
	 * @param filename 
	 * @return
	 */
	IPath fromMmpToProjectPath(IPath mmpPath) {
		if (isAbsoluteLikePath(mmpPath))
			return mmpPath;
		IPath path = currentDirectory != null ? currentDirectory.append(mmpPath) : mmpPath;
		if (FileUtils.isPathInParent(path)) {
			path = getProjectPath().append(path);
		}
		return path;
	}

	/**
	 * Convert an MMP-relative string filename to a project-relative path if possible.
	 * @param filename 
	 * @return
	 */
	IPath fromMmpToProjectPath(String mmpPath) {
		return fromMmpToProjectPath(FileUtils.createPossiblyRelativePath(mmpPath));
	}

	/**
	 * Convert an MMP-relative string filename to a project-relative path
	 * if possible.
	 * @param mmpPath text node from MMP
	 * @return
	 */
	IPath fromMmpToProjectPath(IASTLiteralTextNode mmpPath) {
		return fromMmpToProjectPath(mmpPath.getValue());
	}

	/**
	 * Convert a project-relative IPath to an MMP-relative path and
	 * leave other paths alone. 
	 * @param projectPath 
	 * @return MMP-relative directory (never blank) or self if not project-relative 
	 */
	IPath fromProjectToMmpPath(IPath projectPath) {
		if (isAbsoluteLikePath(projectPath))
			return projectPath;
		IPath path = fromProjectToRelativePath(currentDirectory, projectPath);
		if (path.segmentCount() == 0)
			return new Path("."); //$NON-NLS-1$
		else
			return path;
	}


	/**
	 * Create an EMMPLanguage list from the given LANG statement.
	 * @param prevLang
	 * @return
	 */
	public List<EMMPLanguage> createLanguageList(IASTMMPListArgumentStatement stmt) {
		List<EMMPLanguage> list = new ArrayList<EMMPLanguage>();
		for (IASTLiteralTextNode node : stmt.getArguments()) {
			try {
				list.add(EMMPLanguage.fromCode(node.getValue()));
			} catch (IllegalArgumentException e) {
				// ignore unknown language... completely! (I'm tired of these bug reports)
				/*
				EpocEnginePlugin.log(
						Logging.newStatus(EpocEnginePlugin.getDefault(), 
								new IllegalArgumentException(
										"Warning, ignoring unknown language code: " + node.getValue() + " in " + stmt)
				));
				*/
			}
		}
		return list;
	}

	/**
	 * Create a LANG statement with the given languages.
	 * @param list
	 * @return
	 */
	public IASTMMPListArgumentStatement createLanguageStatement(Collection<EMMPLanguage> list) {
		IASTListNode<IASTLiteralTextNode> languages = ASTMMPFactory.createLiteralTextNodeList();
		for (EMMPLanguage lang : list)
			languages.add(ASTMMPFactory.createPreprocessorLiteralTextNode(lang.getCodeString()));
		return ASTMMPFactory.createMMPListArgumentStatement(
				ASTMMPFactory.createPreprocessorLiteralTextNode(EMMPStatement.LANG.toString()), 
				languages);
	}

	/**
	 * Tell if the statement is a list argument statement that we store
	 * in lists (rather than in some other manner).
	 * @return
	 */
	public boolean isStringListArgumentSettingStatement(EMMPStatement stmt) {
		if (stmt.getCategory() != IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT)
			return false;
		 return stmt != EMMPStatement.SOURCE
			&& stmt != EMMPStatement.USERINCLUDE && stmt != EMMPStatement.SYSTEMINCLUDE
			&& stmt != EMMPStatement.RESOURCE && stmt != EMMPStatement.SYSTEMRESOURCE
			&& stmt != EMMPStatement.LANG && stmt != EMMPStatement.DOCUMENT;
	}

	/**
	 * Tell if the statement is a single argument statement that we store
	 * in the single-argument settings (rather than in some other manner).
	 * @return true: yup
	 */
	public boolean isSingleArgumentSettingStatement(EMMPStatement stmt) {
		if (stmt.getCategory() != IMMPParserConfiguration.SINGLE_ARGUMENT_STATEMENT)
			return false;
		return stmt != EMMPStatement.SOURCEPATH;	
	}
	
	/**
	 * Tell if the statement is a flag statement that we store in the
	 * flag settings
	 */
	public boolean isFlagSettingStatement(EMMPStatement stmt) {
		return stmt.getCategory() == IMMPParserConfiguration.FLAG_STATEMENT;
	}

	/**
	 * Get the default project-relative directory for source files.
	 * @return project-relative path, maybe empty if at project
	 */
	IPath defaultProjectRelativeSourcePath() {
		return currentDirectory;
	}

	/**
	 * Return an appropriate path for a SOURCEPATH statement
	 * @param mmpSourceElement filename
	 * @return the directory of the filename, or "." if at root
	 */
	IPath getSourcePathFromSource(IPath mmpSourceElement) {
		return mmpSourceElement.segmentCount() > 1 ? mmpSourceElement.removeLastSegments(1)
				: new Path("."); //$NON-NLS-1$
	}

	static public boolean equalPath(IPath path, IPath path2) {
		if (path == null || path2 == null)
			return false;
		if (path.segmentCount() == 0 && path2.segmentCount() == 1 && path2.segment(0).equals(".")) { //$NON-NLS-1$
			return true;
		}
		if (path2.segmentCount() == 0 && path.segmentCount() == 1 && path.segment(0).equals(".")) { //$NON-NLS-1$
			return true;
		}
		//return path.toOSString().equalsIgnoreCase(path2.toOSString());
		// don't ignore capitalization, otherwise there's no way the client can use the API
		// to repair it
		return path.equals(path2);
	}

	/**
	 * Tell whether a statement can be "seen" at a global level.
	 * This means anything at the top level and anything inside a START PLATFORM block.
	 * Stuff from other blocks are local to that block and not globally visible.
	 * @param stmt
	 * @return true if statement is global
	 */
	boolean isGloballyVisibleStatement(IASTStatement stmt) {
		if (stmt.getParent() == mmp().getNodes())
			return true;
		
		// only handle nested statements in START PLATFORM.
		IASTNode parent = stmt;
		while ((parent = parent.getParent()) != null) {
			if (parent instanceof IASTMMPStartBlockStatement) {
				String blockType = ((IASTMMPStartBlockStatement) parent).getBlockType().getValue();
				if (blockType.equals(RESOURCE_KEYWORD) || blockType.equals(BITMAP_KEYWORD))
					return false;
				return isMacroDefined(blockType.toUpperCase());
			}
		}
	
		// what is this statement?
		Check.checkState(false);
		return false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase#internalRevertChanges()
	 */
	@Override
	protected void internalRevertChanges() {
		knownStatements.clear();
		
		aifToStatementMap.clear();
		bitmapBlockToStatementMap.clear();
		resourceBlockToStatementMap.clear();
		
		MMPStatementScanner scanner = new MMPStatementScanner(this);
		
		flagSettings.clear();
		singleArgumentSettings.clear();
		stringListArgumentSettings.clear();
		for (EMMPStatement stmt : EMMPStatement.values()) {
			if (((IMMPViewConfiguration) getViewConfiguration()).isStatementSupported(stmt)) {
				switch (stmt.getCategory()) {
				case IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT:
					if (isStringListArgumentSettingStatement(stmt)) {
						stringListArgumentSettings.put(stmt, new ArrayList<String>());
					}
					break;
				case IMMPParserConfiguration.SINGLE_ARGUMENT_STATEMENT:
					if (isSingleArgumentSettingStatement(stmt)) {
						singleArgumentSettings.put(stmt, null);
					}
					break;
				}
			} 
		}		
		sources.clear();
		documents.clear();
		userIncludes.clear();
		systemIncludes.clear();
		bitmaps.clear();
		flagSettings.clear();
		resourceBlocks.clear();
		aifs.clear();
		userResources.clear();
		systemResources.clear();
		languages.clear();
		uid2 = null;
		uid3 = null;
		options.clear();
		linkerOptions.clear();
		replaceOptions.clear();
		
		defFileBase = convertModelToProjectPath(new Path("")); // //$NON-NLS-1$
		
		sourcePaths.clear();
		
		for (IASTTopLevelNode tln : mmp().getNodes()) {
			if (!(tln instanceof IASTMMPStatement))
				continue;
			IASTMMPStatement statement = (IASTMMPStatement) tln;
			scanner.scanStatement(statement);
			
			// record slash direction
			recordSlashInfo(statement.getOriginalText());
		}
		
		if (debug) {
			System.out.println("#internalRevertChanges() for " + getModel().getPath() + ":\n"+ //$NON-NLS-1$ //$NON-NLS-2$
					"Incoming filtered TU:\n"+ //$NON-NLS-1$
					getFilteredTranslationUnit().getNewText());
			dumpModelStructure();
		}
	}


	private void dumpModelStructure() {
		dumpCollection("Sources", sources); //$NON-NLS-1$
		dumpCollection("Documents", documents); //$NON-NLS-1$
		dumpCollection("User Includes", userIncludes); //$NON-NLS-1$
		dumpCollection("System Includes", userIncludes); //$NON-NLS-1$
		dumpCollection("Flags", flagSettings); //$NON-NLS-1$
		dumpCollection("Single Arguments", singleArgumentSettings); //$NON-NLS-1$
		dumpCollection("String Lists", stringListArgumentSettings); //$NON-NLS-1$
		dumpCollection("Bitmaps", bitmaps); //$NON-NLS-1$
		dumpCollection("Resource Blocks", resourceBlocks); //$NON-NLS-1$
		dumpCollection("User Resources", userResources); //$NON-NLS-1$
		dumpCollection("System Resources", systemResources); //$NON-NLS-1$
		dumpCollection("Languages", languages); //$NON-NLS-1$
		dumpCollection("AIFs", aifs); //$NON-NLS-1$
		dumpCollection("Options", options); //$NON-NLS-1$
		dumpCollection("Linker Options", linkerOptions); //$NON-NLS-1$
		dumpCollection("Replace Options", replaceOptions); //$NON-NLS-1$
		System.out.println("DEF file: " + getSingleArgumentSettings().get(EMMPStatement.DEFFILE) + " in " + defFileBase); //$NON-NLS-1$ //$NON-NLS-2$
		System.out.println("UID: " + uid2 + " " + uid3); //$NON-NLS-1$ //$NON-NLS-2$
		dumpCollection("SourcePaths", sourcePaths); //$NON-NLS-1$
	}

	private void dumpCollection(String string, Collection coll) {
		if (!coll.isEmpty()) {
			System.out.println(string + ":"); //$NON-NLS-1$
			for (Object obj : coll) {
				System.out.println("\t" + obj); //$NON-NLS-1$
			}
		}
	}

	private void dumpCollection(String string, Map map) {
		if (!map.isEmpty()) {
			System.out.println(string + ":"); //$NON-NLS-1$
			for (Map.Entry ent : (Set<Map.Entry>)map.entrySet()) {
				if (ent.getValue() != null && !(ent.getValue() instanceof Collection && ((Collection)ent.getValue()).isEmpty()))
					System.out.println("\t" + ent.getKey() + ": " + ent.getValue()); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.mmp.MMPView#internalGatherChanges(java.util.List, java.util.List)
	 */
	@Override
	protected void internalGatherChanges(List modifications, List messages) {
		if (debug) {
			System.out.println("#internalGatherChanges for " + getModel().getPath() + ":\n"); //$NON-NLS-1$ //$NON-NLS-2$
			dumpModelStructure();
		}
		
		MMPStatementUpdater updater = new MMPStatementUpdater(this, modifications, messages);
		updater.update();
		
		if (debug) {
			System.out.println(
					"New filtered TU:\n"+ //$NON-NLS-1$
					getFilteredTranslationUnit().getNewText());
					
		}
	}

	private boolean statementUsesSourcePath(IASTMMPStatement statement) {
		if (statement.getKeywordName() == null)
			return false;
		
		if (statement.getKeywordName().matches("(?i)SOURCE|RESOURCE|SYSTEMRESOURCE|DOCUMENT")) {//$NON-NLS-1$
			return true;
		}
		if (statement instanceof IASTMMPStartBlockStatement) {
			return "RESOURCE".equalsIgnoreCase(((IASTMMPStartBlockStatement) statement).getBlockType().getValue()); //$NON-NLS-1$
		}
		return false;
	}


	private void deleteStatement(IASTNode node) {
		((IASTListHolder) node.getParent()).getList().remove(node);
	}
	
	/**
	 * Remove stray SOURCEPATH statements
	 *
	 */
	private void cleanupSourcePaths(IASTTranslationUnit tu) {
		IASTMMPStatement lastSourcePath = null;
		IConditionalBlock lastSourcePathBlock = null;
		boolean usedSourcePath = true;

		IASTTopLevelNode[] nodes = (IASTTopLevelNode[]) tu.getNodes().toArray(new IASTTopLevelNode[tu.getNodes().size()]);
		for (IASTTopLevelNode node : nodes) {
			if (!(node instanceof IASTMMPStatement))
				continue;
			IASTMMPStatement stmt = (IASTMMPStatement) node;
			IConditionalBlock block = getLangNodeConditionalBlock(stmt);
			if (EMMPStatement.SOURCEPATH.matches(stmt)) {
				if (!usedSourcePath
				&&  (lastSourcePathBlock == block || (lastSourcePathBlock != null && lastSourcePathBlock.contains(block)))) {
					deleteStatement(lastSourcePath);
				}
				lastSourcePath = stmt;
				lastSourcePathBlock = block;
				usedSourcePath = false;
			}
			else if (statementUsesSourcePath(stmt)) {
				usedSourcePath = true;
			}
		}
		
		if (lastSourcePath != null && !usedSourcePath) {
			deleteStatement(lastSourcePath);
		}
	}
	
	
	private void addMissingSourcePaths(IASTTranslationUnit tu) {
		IASTMMPSingleArgumentStatement lastSourcePathStatement = null;
		updateCurrentDirectory(tu);
		IPath lastSourcePath = defaultProjectRelativeSourcePath();
		
		// scan top-level nodes (only -- not inside START or the like) to see
		// if every statement that has a reference to a SOURCEPATH is still
		// "seeing" the same node (or equivalent)
		//
		for (ListIterator<IASTTopLevelNode> iter = tu.getNodes().listIterator();
			iter.hasNext();) {
			IASTTopLevelNode tln = iter.next();
			updateCurrentDirectory(tln);
			if (!(tln instanceof IASTMMPStatement)) {
				continue;
			}
			IASTMMPStatement stmt = (IASTMMPStatement) tln;
			if (EMMPStatement.SOURCEPATH.matches(stmt)) {
				lastSourcePathStatement = (IASTMMPSingleArgumentStatement) tln;
				lastSourcePath = fromMmpToProjectPath(lastSourcePathStatement.getArgument());
			} else if (stmt.getSourcePathDependentContext() != null) {
				// see if this statement can use the last sourcepath
				IASTMMPSingleArgumentStatement reqdSourcePathStmt =
					stmt.getSourcePathDependentContext().getSourcePathStatement();
				IPath reqdSourcePath = lastSourcePath;
				
				if (reqdSourcePathStmt != null) {
					if (reqdSourcePathStmt == IMMPSourcePathDependentContext.DEFAULT_SOURCEPATH_STATEMENT)
						reqdSourcePath = defaultProjectRelativeSourcePath();
					else
						reqdSourcePath = fromMmpToProjectPath(reqdSourcePathStmt.getArgument());
				}
				
				if (!equalPath(lastSourcePath, reqdSourcePath)) {
					// we're working only inside one TU at the moment, so
					// it's fine to assume that equal IPaths means equal text,
					// so we can copy the actual node originally referenced
					iter.previous();
					if (reqdSourcePathStmt != null) {
						lastSourcePathStatement = (IASTMMPSingleArgumentStatement) reqdSourcePathStmt.copy();
					} else {
						lastSourcePathStatement = ASTMMPFactory.createMMPSingleArgumentStatement(
								EMMPStatement.SOURCEPATH.toString(),
								pathString(fromProjectToMmpPath(reqdSourcePath)));
					}
					iter.add(lastSourcePathStatement);
					iter.next();
					lastSourcePath = reqdSourcePath;
				}
			}
		}
		
	}
	
	@Override
	protected void internalFinalizePreparserTranslationUnit(IASTTranslationUnit tu) {
		addMissingSourcePaths(tu);
		cleanupSourcePaths(tu);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase#internalHasChanges()
	 */
	@Override
	protected boolean internalHasChanges() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase#internalMerge(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit)
	 */
	@Override
	protected boolean internalMerge(IASTTranslationUnit oldTu) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#createMMPBitmap()
	 */
	public IMMPBitmap createMMPBitmap() {
		return new MMPBitmap();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#createMMPAIFInfo()
	 */
	public IMMPAIFInfo createMMPAIFInfo() {
		return new MMPAIFInfo();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#createMMPResource()
	 */
	public IMMPResource createMMPResource() {
		return new MMPResource();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getASSPLibraries()
	 */
	public List<String> getASSPLibraries() {
		return stringListArgumentSettings.get(EMMPStatement.ASSPLIBRARY);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getAifInfo()
	 */
	public List<IMMPAIFInfo> getAifs() {
		return aifs;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getMultiImageSources()
	 */
	public List<IMultiImageSource> getMultiImageSources() {
		return (List) bitmaps;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getBitmaps()
	 */
	public List<IMMPBitmap> getBitmaps() {
		return bitmaps;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getDebugLibraries()
	 */
	public List<String> getDebugLibraries() {
		return stringListArgumentSettings.get(EMMPStatement.DEBUGLIBRARY);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getFlags()
	 */
	public Set<EMMPStatement> getFlags() {
		return new ValidatingSet<EMMPStatement>(flagSettings) {

			public boolean isValidEntry(EMMPStatement entry) {
				return isFlagSettingStatement(entry);
			}
			
		};
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getLanguages()
	 */
	public List<EMMPLanguage> getLanguages() {
		return languages;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getLibraries()
	 */
	public List<String> getLibraries() {
		return stringListArgumentSettings.get(EMMPStatement.LIBRARY);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getListArgumentSettings()
	 */
	public Map<EMMPStatement, List<String>> getListArgumentSettings() {
		return new ValidatingKeyMap<EMMPStatement, List<String>>(stringListArgumentSettings) {

			@Override
			public boolean isAllowedKey(EMMPStatement key) {
				return isStringListArgumentSettingStatement(key);
			}
			
		};
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getMMPModel()
	 */
	public IMMPModel getMMPModel() {
		return (IMMPModel) getModel();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getResources()
	 */
	public List<IMMPResource> getResourceBlocks() {
		return resourceBlocks;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getSingleArgumentSettings()
	 */
	public Map<EMMPStatement, String> getSingleArgumentSettings() {
		return new ValidatingKeyMap<EMMPStatement, String>(singleArgumentSettings) {

			@Override
			public boolean isAllowedKey(EMMPStatement key) {
				return isSingleArgumentSettingStatement(key);
			}
			
		};
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getEffectiveSourcePaths()
	 */
	public IPath[] getEffectiveSourcePaths() {
		Set<IPath> sourcePaths = new LinkedHashSet<IPath>();
		for (IPath source : sources) {
			sourcePaths.add(source.removeLastSegments(1));
		}
		for (IPath source : userResources) {
			sourcePaths.add(source.removeLastSegments(1));
		}
		for (IPath source : systemResources) {
			sourcePaths.add(source.removeLastSegments(1));
		}
		for (IPath source : documents) {
			sourcePaths.add(source.removeLastSegments(1));
		}
		return (IPath[]) sourcePaths.toArray(new IPath[sourcePaths.size()]);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getRealSourcePaths()
	 */
	public IPath[] getRealSourcePaths() {
		return (IPath[]) sourcePaths.toArray(new IPath[sourcePaths.size()]); 
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getSources()
	 */
	public List<IPath> getSources() {
		return sources;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getStaticLibraries()
	 */
	public List<String> getStaticLibraries() {
		return stringListArgumentSettings.get(EMMPStatement.STATICLIBRARY);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getSystemIncludes()
	 */
	public List<IPath> getSystemIncludes() {
		return systemIncludes;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getSystemResources()
	 */
	public List<IPath> getSystemResources() {
		return systemResources;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getTargetFilePath()
	 */
	public IPath getTargetFilePath() {
		String name = singleArgumentSettings.get(EMMPStatement.TARGET);
		if (name == null)
			return null;
		String dir = singleArgumentSettings.get(EMMPStatement.TARGETPATH);
		if (dir == null)
			return null;
		IPath path = PathUtils.createPath(FileUtils.createPossiblyRelativePath(dir).append(name).toOSString());
		if (FileUtils.isPathInParent(path)) {
			path = getProjectPath().append(path);
		}
		return path;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getTargetType()
	 */
	public String getTargetType() {
		return singleArgumentSettings.get(EMMPStatement.TARGETTYPE);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getUserIncludes()
	 */
	public List<IPath> getUserIncludes() {
		return userIncludes;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getUserResources()
	 */
	public List<IPath> getUserResources() {
		return userResources;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getStaticLibraries()
	 */
	public List<String> getWin32Libraries() {
		return stringListArgumentSettings.get(EMMPStatement.WIN32_LIBRARY);
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#setAifInfo(com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo)
	 */
	public void setAifs(List<IMMPAIFInfo> aifs) {
		Check.checkArg(aifs);
		this.aifs = aifs;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#setBitmaps(java.util.List)
	 */
	public void setBitmaps(List<IMMPBitmap> bitmaps) {
		Check.checkArg(bitmaps);
		this.bitmaps = bitmaps;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#setTargetFilePath(org.eclipse.core.runtime.IPath)
	 */
	public void setTargetFilePath(IPath path) {
		path = path.removeTrailingSeparator();
		Check.checkArg(path.segmentCount() > 1);
		String filename = path.lastSegment();
		IPath dir = path.removeLastSegments(1);
		singleArgumentSettings.put(EMMPStatement.TARGETPATH, pathString(dir)); 
		singleArgumentSettings.put(EMMPStatement.TARGET, filename); 
				
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getUid2()
	 */
	public String getUid2() {
		return uid2;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getUid3()
	 */
	public String getUid3() {
		return uid3;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#setUid2(java.lang.String)
	 */
	public void setUid2(String uid) {
		this.uid2 = uid;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#setUid2(int)
	 */
	public void setUid2(int value) {
		this.uid2 = "0x" + Integer.toHexString(value); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#setUid3(java.lang.String)
	 */
	public void setUid3(String uid) {
		this.uid3 = uid;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#setUid3(int)
	 */
	public void setUid3(int value) {
		this.uid3 = "0x" + Integer.toHexString(value); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getLinkerOptions()
	 */
	public Map<String, String> getLinkerOptions() {
		return linkerOptions;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getOptions()
	 */
	public Map<String, String> getOptions() {
		return options;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getReplaceOptions()
	 */
	public Map<String, String> getReplaceOptions() {
		return replaceOptions;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#setLinkerOptions(java.util.Map)
	 */
	public void setLinkerOptions(Map<String, String> options) {
		Check.checkArg(options);
		this.linkerOptions = options;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#setOptions(java.util.Map)
	 */
	public void setOptions(Map<String, String> options) {
		Check.checkArg(options);
		this.options = options;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#setReplaceOptions(java.util.Map)
	 */
	public void setReplaceOptions(Map<String, String> options) {
		Check.checkArg(options);
		this.replaceOptions = options;
	}
	
	/**
	 * Tell if the given macro is defined in the view configuration.
	 * @param macro
	 * @return
	 */
	public boolean isMacroDefined(String macro) {
		for (IDefine define : getViewConfiguration().getMacros()) {
			if (define.getName().equals(macro))
				return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getDocuments()
	 */
	public List<IPath> getDocuments() {
		return documents;
	}
	
	/**
	 * Track the statements known to the view from scanning.
	 * These will be updated when the view is updated.  Any others
	 * (i.e. illegal statements or unhandled ones) will be left alone.
	 * @return
	 */
	public Collection<IASTStatement> getKnownStatements() {
		return knownStatements;
	}

	public IPath getDefFile() {
		IMMPViewConfiguration viewConfiguration = (IMMPViewConfiguration) getViewConfiguration();
		String theDefFile = singleArgumentSettings.get(EMMPStatement.DEFFILE);;
		return convertMMPDefFileToProjectOrFullPath(
				theDefFile, isDefFileInFixedDirectory(), 
				viewConfiguration.getDefaultDefFileBase(),
				viewConfiguration.isEmulatorBuild());
	}
	
	public boolean isDefFileInFixedDirectory() {
		String theDefFile = singleArgumentSettings.get(EMMPStatement.DEFFILE);
		if (theDefFile == null)
			return false;
		return theDefFile.indexOf('/') >= 0 || theDefFile.indexOf('\\') >= 0;
	}
	
	public void setDefFile(IPath path, boolean isFixedDirectory) {
		// NOTE: we assume the new/modified statement always goes to the main file, otherwise postpone this
		//Check.checkState(ModelSynchronizer.FORCE_NEW_CONTENT_TO_MAIN_DOCUMENT);
		IMMPViewConfiguration viewConfiguration = (IMMPViewConfiguration) getViewConfiguration();
		String newDefFile = convertPhysicalFileToMMPDefFile(path, 
				viewConfiguration.getDefaultDefFileBase(),
				viewConfiguration.isEmulatorBuild(),
				isFixedDirectory);
		singleArgumentSettings.put(EMMPStatement.DEFFILE, newDefFile);
		defFileBase = convertModelToProjectPath(new Path("")); // //$NON-NLS-1$
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase#addViewSpecificMessages(java.util.List)
	 */
	@Override
	protected void addViewSpecificMessages(final List messageList) {
		// also add IASTMMPUnknownStatements as warnings
		getFilteredTranslationUnit().accept(new IASTVisitor() {

			public int visit(IASTNode node) {
				if (node instanceof IASTMMPUnknownStatement) {
					String text = node.getOriginalText();
					if (text == null)
						text = node.getNewText();
					messageList.add(ASTMMPFactory.createMessage(
									IMessage.WARNING,
									"MMPView.UnknownStatement", //$NON-NLS-1$
									new Object[] { text }, 
									node.getMessageLocation()));
					return IASTVisitor.VISIT_SIBLINGS;
				} else if (node instanceof IASTMMPStatement
						&& EMMPStatement.MESSAGE.matches((IASTMMPStatement) node)) {
					IASTListNode<IASTLiteralTextNode> message = ((IASTMMPListArgumentStatement) node).getList();
					String text = message.getOriginalText();
					if (text == null)
						text = message.getNewText();
					messageList.add(ASTMMPFactory.createMessage(
							IMessage.WARNING,
							"MMPView.MessageStatement", //$NON-NLS-1$
							new Object[] { text }, 
							node.getMessageLocation()));
					return IASTVisitor.VISIT_SIBLINGS;
				}
				return IASTVisitor.VISIT_CHILDREN;
			}
			
		});
		
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase#getSpacingCategory(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode)
	 */
	@Override
	protected Object getSpacingCategory(IASTTopLevelNode node) {
		if (!(node instanceof IASTMMPStatement) || (node instanceof IASTProblemNode))
			return null;
		try {
			EMMPStatement stmt = EMMPStatement.valueOf(
					((IASTMMPStatement) node).getKeywordName().toUpperCase());
			if (stmt == EMMPStatement.SOURCEPATH
					|| stmt == EMMPStatement.SOURCE
					|| stmt == EMMPStatement.RESOURCE
					|| stmt == EMMPStatement.SYSTEMRESOURCE
					|| stmt == EMMPStatement.DOCUMENT) {
				return EMMPStatement.SOURCEPATH.getCategory();
			}
			return stmt.getCategory();
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	/**
	 * Merge a list of statements, with the assumption that only one instance
	 * of a given statement is retained.
	 * @param intoStmts original statements
	 * @param fromStmts ones to replace
	 */
	public void mergeStatementList(IASTListNode<IASTMMPStatement> intoStmts, IASTListNode<IASTMMPStatement> fromStmts) {
		// unparent the fromStmts
		for (IASTMMPStatement stmt : fromStmts)
			stmt.setParent(null);
		
		for (ListIterator<IASTMMPStatement> iter = intoStmts.listIterator(); iter.hasNext(); ) {
			IASTMMPStatement into = iter.next();
			if (into instanceof IASTMMPProblemStatement)
				continue;
			
			EMMPStatement stmt = null;
			try {
				stmt = EMMPStatement.valueOf(into.getKeywordName().toUpperCase());
			} catch (IllegalArgumentException e) {
				// ignore
				continue;
			}
				
			boolean handled = false;
			for (ListIterator<IASTMMPStatement> iter2 = fromStmts.listIterator(); iter2.hasNext(); ) {
				IASTMMPStatement from = iter2.next();
				try {
					EMMPStatement stmt2 = EMMPStatement.valueOf(from.getKeywordName().toUpperCase());
					if (stmt == stmt2) {
						// replace
						iter.remove();
						iter.add(from);
						iter2.remove();
						handled = true;
						break;
					}
				} catch (IllegalArgumentException e2) {
					// who knows?
				}
			}
			
			if (!handled) {
				// the old statement is not replaced, so it must be removed
				iter.remove();
			}
		}
		
		// now, take remaining fromStmts and add
		intoStmts.addAll(fromStmts);
	}

	/** Helper method for now.  Returns list of unique source paths. */
	Collection<IPath> getSourcePaths() {
		return sourcePaths;
	}
	
	/**
	 * Convert the DEFFILE argument returned from IMMPView to a project-relative 
	 * or absolute path.  If the path has no directory or no filename,
	 * 'U' is added (for Unicode builds) and a platform-specific directory
	 * ("bwins" or "eabi") is added as in makmake.
	 * @param defFileSetting the DEFFILE setting from {@link IMMPView#getSingleArgumentSettings().get(EMMPStatement.DEFFILE)} -- may be null
	 * to determine default filename
	 * @param fixedDirectory 
	 * @return a path derived from defFileSetting adjusted to the location of a .def file within a project
	 * or a full path if outside the project, or null.  
	 */
	private IPath convertMMPDefFileToProjectOrFullPath(String defFileSetting, boolean fixedDirectory, String implDirectory, boolean isEmulator) {
		String ext = null;
		String baseName = null;
		IPath dirPath = null;
		
		// no def file if not required
		if (defFileSetting == null && !requiresDefFile())
			return null;
		
		IPath mmpPath = defFileSetting != null ? FileUtils.createPossiblyRelativePath(defFileSetting) : null;
		
		if (mmpPath != null && !mmpPath.hasTrailingSeparator()) {
			// filename is provided
			ext = mmpPath.getFileExtension();
			if (ext == null)
				ext = DEF_FILE_EXTENSION;
			IPath basePath = mmpPath.removeFileExtension();
			baseName = basePath.lastSegment();
			
			if (isAbsolutePath(basePath)) {
				dirPath = basePath.removeLastSegments(1);
			} else {
				dirPath = defFileBase.append(basePath.removeLastSegments(1));
			}
			//if (!dirPath.isAbsolute())
			//	dirPath = ((IModel)mmpView.getModel()).getPath().removeLastSegments(1).append(dirPath);
			
		} else {
			// no filename, so guess it from the target name
			//if (mmpPath == null || !mmpPath.isAbsolute())
			//	dirPath = ((IModel)mmpView.getModel()).getPath().removeLastSegments(1).append(mmpPath);
			//else
			if (mmpPath == null)
				dirPath = new Path(""); //$NON-NLS-1$
			else
				dirPath = mmpPath;
			dirPath = convertModelToProjectPath(dirPath);
			String targetName = getSingleArgumentSettings().get(EMMPStatement.TARGET);
			if (targetName == null)
				targetName = "unnamed"; //$NON-NLS-1$ 
			baseName = new Path(targetName).removeFileExtension().lastSegment();
			ext = DEF_FILE_EXTENSION; 
		}

		// add implicit directory if needed
		if (!fixedDirectory) {
			if (implDirectory != null) {
				dirPath = dirPath.append("..").append(implDirectory); //$NON-NLS-1$
			}
		} else {
			// replace any "/~/" sequences 
			if (dirPath.segmentCount() > 0)
				dirPath = new Path(dirPath.addTrailingSeparator().toPortableString()
						.replace("/~/", "/"+implDirectory+"/")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		
		// version identifier overrides unicode
		String versionString = getFileVersionString(); 
		if (!isEmulator && versionString != null) {
			baseName += versionString;
		}
		else if (!isNoStrictDef()) {
			// unicode is on by default unless the user wants to explicitly set the name
			baseName += "U"; //$NON-NLS-1$
		}
		
		IPath prjPath = null;
		IPath tempPath = dirPath.append(baseName + "." + ext); //$NON-NLS-1$
		if (!isAbsolutePath(tempPath)) {
			prjPath = tempPath; //convertMMPToProject(EMMPPathContext.DEFFILE, tempPath);
		} else if (mmpPath == null || !isAbsolutePath(mmpPath)) {
			/*
			IPath wsPath = epocHelper.convertFilesystemToWorkspace(tempPath);
			if (wsPath != null)
				prjPath = wsPath.removeFirstSegments(1);
			
			if (prjPath == null) {
				if (mmpView != null) {
					IPath projectRoot = mmpView.getViewConfiguration().getViewParserConfiguration().getProjectLocation();
					prjPath = FileUtils.removePrefixFromPath(projectRoot, tempPath);
				}
			}
			*/
		}
		if (prjPath == null) {
			// e.g. for full paths
			prjPath = tempPath;
		}
		return prjPath;
	}

	private String getFileVersionString() {
		List<String> versionArgs = getListArgumentSettings().get(EMMPStatement.VERSION);
		if (versionArgs == null || versionArgs.size() < 1)
			return null;
		try {
			Version version = new Version(versionArgs.get(0));
			Formatter formatter = new Formatter();
			formatter.format("{%04x%04x}", version.getMajor(), version.getMinor()); //$NON-NLS-1$
			return formatter.toString();
		} catch (IllegalArgumentException e) {
			return null;
		}
	}


	/**
	 * Convert a def file path, which may have a 'U' added for a Unicode build,
	 * and an implicit bwins or eabi directory, and remove them.  This may
	 * return null if the DEFFILE statement is not needed.
	 * <p>
	 * This does not provide a complete mapping back to the appropriate DEFFILE
	 * statement if the realPath was previously created from {@link #convertMMPDefFileToProjectOrFullPath(String)}
	 * where the DEFFILE statement was missing.  The client should determine whether
	 * a non-null result is sensible (e.g. points to a real file).
	 * <p>
	 * @param realPath the path to a real file; if relative, assumed project-relative, else assumed full path
	 * @param implDirectory the directory for platform .def files or null for unknown
	 * @param isEmulator true if an emulator build, else false: determines whether the version is added
	 * @param isFixedDirectory true if the realPath should not be converted to platform-independent
	 * @return a string adjusted to the MMP format.  It may be null.
	 */
	private String convertPhysicalFileToMMPDefFile(IPath realPath, String implDirectory, boolean isEmulator, boolean isFixedDirectory) {
		if (realPath == null)
			return null;

		if (!isAbsolutePath(realPath)) {
			//realPath = ((IModel)mmpView.getModel()).getPath().removeLastSegments(1).append(realPath);
			realPath = convertProjectToModelPath(realPath);
		}
		
		String ext = null;
		String baseName = null;
		IPath dirPath = null;
		
		ext = FileUtils.getSafeFileExtension(realPath);
		IPath basePath = realPath.removeFileExtension();
		baseName = basePath.lastSegment();
		dirPath = basePath.removeLastSegments(1);

		// remove Unicode or version suffix
		if (!isNoStrictDef() && baseName.toUpperCase().endsWith("U")) { //$NON-NLS-1$
			baseName = baseName.substring(0, baseName.length() - 1);
		} else {
			Matcher matcher = VERSION_PATTERN.matcher(baseName);
			if (matcher.matches()) {
				baseName = matcher.group(1);
			}
		}
		
		// remove implicit directory if present
		if (!isFixedDirectory && implDirectory != null && dirPath.segmentCount() > 0 && dirPath.lastSegment().equalsIgnoreCase(implDirectory)) {
			dirPath = dirPath.removeLastSegments(2);	// ".." and impl
		}

		// return string value, containing literal .\ if not generic name
		String mmpPath = pathString(dirPath.append(baseName + "." + ext)); //$NON-NLS-1$
		if (isFixedDirectory && !isAbsolutePath(dirPath) && dirPath.segmentCount() == 0)
			mmpPath = "." + pathSeparator() + mmpPath; //$NON-NLS-1$
		return mmpPath;
	}

	/**
	 * Tell whether NOSTRICTDEF was defined in the MMP view, e.g.,
	 * if the view does not change the name of DEFFILEs for Unicode
	 * builds.
	 * @return flag
	 */
	private boolean isNoStrictDef() {
		return getFlags().contains(EMMPStatement.NOSTRICTDEF);
	}

	/**
	 * Tell whether the ASSP format exports are used
	 * @return flag
	 */
	private boolean isASSP() {
		return getFlags().contains(EMMPStatement.ASSPEXPORTS);
	}

	/**
	 * Tell whether the MMP file requires a .def file, which tells whether
	 * a missing DEFFILE statement has an implicit one.
	 * @return flag
	 */
	private boolean requiresDefFile() {
		String targetType = getSingleArgumentSettings().get(EMMPStatement.TARGETTYPE);
		if (targetType == null)
			return false;
		return targetType.toUpperCase().matches("DLL|EXEDLL|EXEXP|IMPLIB|STDDLL"); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView#getData()
	 */
	public IMMPData getData() {
		return new MMPData(this);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPData#getModelPath()
	 */
	public IPath getModelPath() {
		return model.getPath();
	}
}
