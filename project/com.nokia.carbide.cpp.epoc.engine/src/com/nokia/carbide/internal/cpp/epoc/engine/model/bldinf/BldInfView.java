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

package com.nokia.carbide.internal.cpp.epoc.engine.model.bldinf;

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.*;
import com.nokia.carbide.internal.cpp.epoc.engine.model.*;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ITranslationUnitParser;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IConditionalBlock;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.cdt.core.dom.ast.IASTProblemStatement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import java.util.*;

/**
 * View onto bld.inf contents.
 *
 */
public class BldInfView extends ViewASTBase implements IBldInfView {
	/**
	 * 
	 */
	public static final String EPOC32_INCLUDE = "/epoc32/include"; //$NON-NLS-1$
	public static final String ZIP_MODIFIER = ":zip"; //$NON-NLS-1$
	public static final String PRJ_EXPORTS_KEYWORD = "PRJ_EXPORTS"; //$NON-NLS-1$
	public static final String PRJ_TESTEXPORTS_KEYWORD = "PRJ_TESTEXPORTS"; //$NON-NLS-1$
	public static final String PRJ_MMPFILES_KEYWORD = "PRJ_MMPFILES"; //$NON-NLS-1$
	public static final String PRJ_TESTMMPFILES_KEYWORD = "PRJ_TESTMMPFILES"; //$NON-NLS-1$
	public static final String PRJ_PLATFORMS_KEYWORD = "PRJ_PLATFORMS"; //$NON-NLS-1$
	public static final String PRJ_EXTENSIONS_KEYWORD = "PRJ_EXTENSIONS"; //$NON-NLS-1$
	public static final String PRJ_TESTEXTENSIONS_KEYWORD = "PRJ_TESTEXTENSIONS"; //$NON-NLS-1$
	
	private List<String> platforms;
	private List<IExport> exports;
	private List<IMakMakeReference> maks;
	private List<IExport> testExports;
	private List<IMakMakeReference> testMaks;
	private List<IASTBldInfStatement> knownStatements;
	private List<IExtension> extensions;
	private List<IExtension> testExtensions;
	
	private List currentList;
	private OrderedObjectMap currentMap;
	private ModelConverter listConverter;
	public OrderedObjectMap<IMakMakeReference, IASTBldInfMakMakeStatement> makToStatementMap;
	public OrderedObjectMap<IMakMakeReference, IASTBldInfMakMakeStatement> testMakToStatementMap;
	public OrderedObjectMap<IExport, IASTBldInfExportStatement> exportToStatementMap;
	public OrderedObjectMap<IExport, IASTBldInfExportStatement> testExportToStatementMap;
	public OrderedObjectMap<IExtension, IASTBldInfExtensionBlockStatement> extensionToStatementMap;
	public OrderedObjectMap<IExtension, IASTBldInfExtensionBlockStatement> testExtensionToStatementMap;

	private List<IMessage> parseMessages;
	
	public BldInfView(ModelBase model, ITranslationUnitParser parser,
			IViewConfiguration viewConfiguration) {
		super(model, parser, viewConfiguration);
		
		knownStatements = new ArrayList<IASTBldInfStatement>();
		platforms = new ArrayList<String>();
		exports = new ArrayList<IExport>();
		testExports = new ArrayList<IExport>();
		maks = new ArrayList<IMakMakeReference>();
		testMaks = new ArrayList<IMakMakeReference>();
		extensions = new ArrayList<IExtension>();
		testExtensions = new ArrayList<IExtension>();
		
		makToStatementMap = new OrderedObjectMap<IMakMakeReference, IASTBldInfMakMakeStatement>();
		testMakToStatementMap = new OrderedObjectMap<IMakMakeReference, IASTBldInfMakMakeStatement>();
		exportToStatementMap = new OrderedObjectMap<IExport, IASTBldInfExportStatement>();
		testExportToStatementMap = new OrderedObjectMap<IExport, IASTBldInfExportStatement>();
		extensionToStatementMap = new OrderedObjectMap<IExtension, IASTBldInfExtensionBlockStatement>();
		testExtensionToStatementMap = new OrderedObjectMap<IExtension, IASTBldInfExtensionBlockStatement>();
		
		parseMessages = new ArrayList<IMessage>();
	}
	
	private IASTBldInfTranslationUnit bldinf() {
		return (IASTBldInfTranslationUnit) getFilteredTranslationUnit();
	}
	
	/**
	 * Convert an BldInf-relative string filename to a project-relative path.
	 * @param filename 
	 * @return
	 */
	private IPath fromBldInfToProjectPath(IPath bldinfPath) {
		IPath path;
		if (currentDirectory != null && !isAbsolutePath(bldinfPath))
			path = currentDirectory.append(bldinfPath);
		else
			path = bldinfPath;
		if (FileUtils.isPathInParent(path)) {
			path = getProjectPath().append(path);
		}
		return path;
	}

	/**
	 * Convert an BldInf-relative string filename to a project-relative path.
	 * @param filename 
	 * @return
	 */
	private IPath fromBldInfToProjectPath(String bldinfPath) {
		return fromBldInfToProjectPath(FileUtils.createPossiblyRelativePath(bldinfPath));
	}

	/**
	 * Convert an BldInf-relative string filename to a project-relative path.
	 * @param filename 
	 * @return
	 */
	IPath fromBldInfToProjectPath(IASTLiteralTextNode bldinfPath) {
		return fromBldInfToProjectPath(bldinfPath.getValue());
	}

	/**
	 * Convert an IPath to an BldInf-relative path 
	 * @param projectPath 
	 * @return BldInf-relative directory 
	 */
	IPath fromProjectToBldInfPath(IPath projectPath) {
		if (isAbsolutePath(projectPath))
			return projectPath;
		return fromProjectToRelativePath(currentDirectory, projectPath);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase#internalRevertChanges()
	 */
	@Override
	protected void internalRevertChanges() {
		updateCurrentDirectory(getModel().getPath());

		/*
		rescanPlatforms();
		rescanExports();
		rescanMmpfiles();
		*/
		rescanStatements();
	}

	interface IBldInfStatementHandler {
		void handleStatement(List modelList, IASTBldInfStatement statement);
	}
	
	private void rescanStatements() {
		exports.clear();
		testExports.clear();
		maks.clear();
		testMaks.clear();
		platforms.clear();
		extensions.clear();
		testExtensions.clear();
		
		knownStatements.clear();
		makToStatementMap.clear();
		testMakToStatementMap.clear();
		exportToStatementMap.clear();
		testExportToStatementMap.clear();
		extensionToStatementMap.clear();
		testExtensionToStatementMap.clear();
		
		parseMessages.clear();
		
		currentList = null;
		currentMap = null;
		listConverter = null;
		
		for (IASTTopLevelNode topLevelNode : bldinf().getNodes()) {
			rescanStatement(topLevelNode);
		}
	}

	private void rescanStatement(IASTTopLevelNode topLevelNode) {
		updateCurrentDirectory(topLevelNode);
		if (topLevelNode instanceof IASTBldInfPrjPlatformsBlockStatement) {
			rescanPlatformsStatement((IASTBldInfPrjPlatformsBlockStatement) topLevelNode);
			knownStatements.add((IASTBldInfStatement) topLevelNode);
		} else if (topLevelNode instanceof IASTBldInfBlockStatement
				&& !(topLevelNode instanceof IASTBldInfExtensionBlockStatement)) {
			IASTBldInfBlockStatement block = (IASTBldInfBlockStatement) topLevelNode;
			if (block.getKeywordName().equalsIgnoreCase(PRJ_MMPFILES_KEYWORD)) {
				currentList = maks;
				currentMap = makToStatementMap;
				listConverter = new MakListConverter(this, PRJ_MMPFILES_KEYWORD);
			} else if (block.getKeywordName().equalsIgnoreCase(PRJ_TESTMMPFILES_KEYWORD)) {
				currentList = testMaks;
				currentMap = testMakToStatementMap;
				listConverter = new MakListConverter(this, PRJ_TESTMMPFILES_KEYWORD);
			} else if (block.getKeywordName().equalsIgnoreCase(PRJ_EXPORTS_KEYWORD)) {
				currentList = exports;
				currentMap = exportToStatementMap;
				listConverter = new ExportListConverter(this, PRJ_EXPORTS_KEYWORD);
			} else if (block.getKeywordName().equalsIgnoreCase(PRJ_TESTEXPORTS_KEYWORD)) {
				currentList = testExports;
				currentMap = testExportToStatementMap;
				listConverter = new ExportListConverter(this, PRJ_TESTEXPORTS_KEYWORD);
			} else if (block.getKeywordName().equalsIgnoreCase(PRJ_EXTENSIONS_KEYWORD)) {
				currentList = extensions;
				currentMap = extensionToStatementMap;
				listConverter = new ExtensionListConverter(this, PRJ_EXTENSIONS_KEYWORD);
			} else if (block.getKeywordName().equalsIgnoreCase(PRJ_TESTEXTENSIONS_KEYWORD)) {
				currentList = testExtensions;
				currentMap = testExtensionToStatementMap;
				listConverter = new ExtensionListConverter(this, PRJ_TESTEXTENSIONS_KEYWORD);
			} else {
				EpocEnginePlugin.log(new IllegalArgumentException("Ignoring unknown block statement: " + topLevelNode)); //$NON-NLS-1$
				return;
			}
			knownStatements.add(block);
		} else if (topLevelNode instanceof IASTProblemTopLevelNode) {
			// ignore
		} else if (currentList == null) {
			EpocEnginePlugin.log(new IllegalArgumentException("Ignoring unknown statement: " + topLevelNode)); //$NON-NLS-1$
		} else {
			if (listConverter == null) {
				// ignored silently
				return;
			}

			// safely try to convert; if the parser is broken we'll get 
			// a statement of the wrong type for the converter and throw
			try {
				Object model = listConverter.fromNode(topLevelNode);
				if (model != null) {
					currentList.add(model);
					currentMap.put(model, topLevelNode);
					knownStatements.add((IASTBldInfStatement) topLevelNode);
				}
			} catch (ClassCastException e) {
				EpocEnginePlugin.log(new IllegalArgumentException("Unexpected statement: " + topLevelNode)); //$NON-NLS-1$
				return;
			}
		}
		recordSlashInfo(topLevelNode.getOriginalText());
	}

	
	/**
	 * Take the filename/path in an PRJ_EXPORTS target and
	 * convert it to a canonical Eclipse path.
	 * <p>
	 * In bld.inf, if the target is blank, is means \epoc32\include.
	 * If it is relative, it is relative to \epoc32\include.
	 * If it is full path, it is absolute for the target.
	 * @param sourcePath the source path, whose filename to append to implicit paths, or null
	 * @param targetPath
	 * @return eclipse path
	 */
	IPath fromBldInfExportTargetToProjectPath(IPath sourcePath, IASTLiteralTextNode targetPath) {
		IPath path;
		if (targetPath == null) {
			path = new Path(EPOC32_INCLUDE);
			if (sourcePath != null)
				path = path.append(sourcePath.lastSegment());
		} else {
			String pathName = targetPath.getValue();
			if (pathName.startsWith("|")) {
				// project-relative
				path = fromBldInfToProjectPath(pathName.substring(1));
			} else {
				path = FileUtils.createPossiblyRelativePath(pathName);
				if (!isAbsolutePath(path))
					path = new Path(EPOC32_INCLUDE).append(path);
			}
		}
		return path;
	}
	
	/**
	 * Take the path in an IExport and convert it to the
	 * filename/path in an PRJ_EXPORTS target.
	 * <p>
	 * If the path is null or equal to \epoc32\include, return null.
	 * If it is relative, it is relative to \epoc32\include, so return as-is.
	 * If it is full path, return as-is.
	 * @param projectPath
	 * @return bld.inf path
	 */
	IPath fromProjectToBldInfExportTargetPath(IPath projectPath, IPath sourcePath) {
		if (projectPath == null)
			return null;
		if (sourcePath == null) {
			if (equalPath(projectPath, new Path(EPOC32_INCLUDE)))
				return null;
		} else  {
			// remove EPOC32_INCLUDE prefix from a relative path
			if (projectPath.segmentCount() > 2
					&& projectPath.segment(0).equalsIgnoreCase("epoc32") //$NON-NLS-1$
					&& projectPath.segment(1).equalsIgnoreCase("include")) { //$NON-NLS-1$
				IPath epocIncludeRelPath = projectPath.removeFirstSegments(2);
				if (equalPath(epocIncludeRelPath, new Path(sourcePath.lastSegment()))) {
					return null;
				} else {
					return epocIncludeRelPath;
				}
			} else if (!isAbsolutePath(projectPath)) {
				// project-relative
				return new Path("|" + fromProjectToBldInfPath(projectPath).toString());
			}
		}
		return projectPath;
	}

	List<String> getStringList(IASTListNode<IASTLiteralTextNode> attributes) {
		List<String> list = new ArrayList<String>();
		for (IASTLiteralTextNode node : attributes)
			list.add(node.getValue());
		return list;
	}

	private void rescanPlatformsStatement(IASTBldInfPrjPlatformsBlockStatement platform) {
		StringListConverter converter = new StringListConverter(IASTLiteralTextNode.EStyle.PREPROCESSOR);
		IASTListNode<IASTLiteralTextNode> plats = platform.getList();
		for (IASTLiteralTextNode node : plats) {
			platforms.add((String)converter.fromNode(node));
		}
	}

	protected void internalGatherChanges(List modifications, List messages) {
		BldInfStatementUpdater updater = new BldInfStatementUpdater(this, modifications, messages);
		updater.update();
	}

	private void deleteStatement(IASTNode node) {
		((IASTListHolder) node.getParent()).getList().remove(node);
	}

	/**
	 * Remove stray PRJ_xxx section headers that we added
	 *
	 */
	protected void cleanupSections(IASTTranslationUnit tu) {
		IASTStatement lastSection = null;
		String lastSectionName = null;
		IConditionalBlock lastSectionBlock = null;
		boolean usedSection = true;
		
		IASTTopLevelNode[] nodes = (IASTTopLevelNode[]) tu.getNodes().toArray(new IASTTopLevelNode[tu.getNodes().size()]);
		for (IASTTopLevelNode node : nodes) {
			if (!(node instanceof IASTStatement))
				continue;
			IASTStatement stmt = (IASTStatement) node;
			IConditionalBlock block = getLangNodeConditionalBlock(stmt);
			if (stmt instanceof IASTBldInfBlockStatement) {
				String sectionName = ((IASTBldInfBlockStatement) stmt).getKeywordName().toUpperCase();
				if (!usedSection && lastSectionName.equals(sectionName)
				&&  (lastSectionBlock == block || (lastSectionBlock != null && lastSectionBlock.contains(block)))) {
					deleteStatement(lastSection);
				}
				lastSection = stmt;
				lastSectionName = sectionName;
				lastSectionBlock = block;
				if  (stmt instanceof IASTBldInfExtensionBlockStatement
						|| stmt instanceof IASTBldInfPrjPlatformsBlockStatement) {
					// both of these contain subnodes, not serialized nodes, so they're 'used'
					usedSection = true; 
				} else {
					usedSection = false;
				}
			}
			else {
				usedSection = true;
			}
		}
		
		if (lastSection != null && !usedSection) {
			deleteStatement(lastSection);
		}
	}
	@Override
	protected void internalFinalizePreparserTranslationUnit(IASTTranslationUnit tu) {
		//cleanupSections(tu);
	}

	enum SpacingCategory {
		STATEMENT,
	};
	
    /**
     * Return an object which is the same as all other objects in the same spacing
     * category.  Items in the same spacing category do not have additional
     * newlines added in between.  
	 * @param node node to test
	 * @return an object which is == to others in same category
	 */
	protected Object getSpacingCategory(IASTTopLevelNode node) {
		// keep all nodes in their own category
		if (node instanceof IASTBldInfPrjPlatformsBlockStatement)
			return IASTBldInfPrjPlatformsBlockStatement.class;
		if (node instanceof IASTBldInfPrjMmpfilesBlockStatement 
				|| node instanceof IASTBldInfMakMakeStatement)
			return IASTBldInfPrjMmpfilesBlockStatement.class;
		if (node instanceof IASTBldInfPrjExportsBlockStatement 
				|| node instanceof IASTBldInfExportStatement)
			return IASTBldInfPrjExportsBlockStatement.class;
		if (node instanceof IASTBldInfPrjExtensionsBlockStatement
				|| node instanceof IASTBldInfExtensionBlockStatement
				|| node instanceof IASTBldInfExtensionStatement)
			return IASTBldInfPrjExtensionsBlockStatement.class;
		if (node instanceof IASTBldInfBlockStatement)
			return node;
		return super.getSpacingCategory(node); 
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
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView#createExport()
	 */
	public IExport createExport() {
		return new Export();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView#createMMPReference()
	 */
	public IMMPReference createMMPReference() {
		return new MMPReference();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView#createMakefileReference()
	 */
	public IMakefileReference createMakefileReference() {
		return new MakefileReference();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView#getExports()
	 */
	public List<IExport> getExports() {
		return exports;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView#getMakMakeReferences()
	 */
	public List<IMakMakeReference> getMakMakeReferences() {
		return maks;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView#getPlatforms()
	 */
	public List<String> getPlatforms() {
		return platforms;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView#getTestExports()
	 */
	public List<IExport> getTestExports() {
		return testExports;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView#getTestMakMakeReferences()
	 */
	public List<IMakMakeReference> getTestMakMakeReferences() {
		return testMaks;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView#getAllMakMakeReferences()
	 */
	public IMakMakeReference[] getAllMakMakeReferences() {
		List<IMakMakeReference> makmakes = new ArrayList<IMakMakeReference>();
		makmakes.addAll(maks);
		makmakes.addAll(testMaks);
		return (IMakMakeReference[]) makmakes.toArray(new IMakMakeReference[makmakes.size()]);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView#getMakefileReferences()
	 */
	public IMakefileReference[] getAllMakefileReferences() {
		List<IMakefileReference> makefiles = new ArrayList<IMakefileReference>();
		for (IMakMakeReference mak : maks) {
			if (mak instanceof IMakefileReference)
				makefiles.add((IMakefileReference) mak);
		}
		for (IMakMakeReference mak : testMaks) {
			if (mak instanceof IMakefileReference)
				makefiles.add((IMakefileReference) mak);
		}
		return (IMakefileReference[]) makefiles.toArray(new IMakefileReference[makefiles.size()]);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView#getMMPReferences()
	 */
	public IMMPReference[] getAllMMPReferences() {
		List<IMMPReference> mmps = new ArrayList<IMMPReference>();
		for (IMakMakeReference mak : maks) {
			if (mak instanceof IMMPReference)
				mmps.add((IMMPReference) mak);
		}
		for (IMakMakeReference mak : testMaks) {
			if (mak instanceof IMMPReference)
				mmps.add((IMMPReference) mak);
		}
		return (IMMPReference[]) mmps.toArray(new IMMPReference[mmps.size()]);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase#addViewSpecificMessages(java.util.List)
	 */
	@Override
	protected void addViewSpecificMessages(List/*<IMessage>*/ messageList) {
		messageList.addAll(parseMessages);
	}

	public Collection<IASTBldInfStatement> getKnownStatements() {
		return knownStatements;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView#createExtension()
	 */
	public IExtension createExtension() {
		return new Extension();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView#getExtensions()
	 */
	public List<IExtension> getExtensions() {
		return extensions;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView#getTestExtensions()
	 */
	public List<IExtension> getTestExtensions() {
		return testExtensions;
	}

	/**
	 * Merge a list of statements, with the assumption that only one instance
	 * of a given key statement is retained (for everything 
	 * @param listNode original statements
	 * @param listNode2 ones to replace
	 */
	public void mergeStatementList(IASTListNode<IASTBldInfStatement> listNode, IASTListNode<IASTBldInfStatement> listNode2) {
		// unparent the fromStmts
		for (IASTBldInfStatement stmt : listNode2)
			stmt.setParent(null);
		
		for (ListIterator<IASTBldInfStatement> iter = listNode.listIterator(); iter.hasNext(); ) {
			IASTBldInfStatement into = iter.next();
			if (into instanceof IASTProblemStatement)
				continue;
			
			//String keyword = into.getKeywordName().toUpperCase();
			//if (keyword == null)
			//	continue;
			
			boolean handled = false;
			for (ListIterator<IASTBldInfStatement> iter2 = listNode2.listIterator(); iter2.hasNext(); ) {
				IASTBldInfStatement from = iter2.next();
				//String keyword2 = from.getKeywordName().toUpperCase();
				if (sameStatementClass(into, from)) {
					// try to merge
					if (!mergeStatements(into, from)) {
						// replace
						iter.remove();
						iter.add(from);
					}
					iter2.remove();
					handled = true;
					break;
				}
			}
			
			if (!handled) {
				// the old statement is not replaced, so it must be removed
				iter.remove();
			}
		}
		
		// now, take remaining fromStmts and add
		listNode.addAll(listNode2);
	}
	
	/**
	 * Tell if the statements are considered the same, so that one can be merged
	 * with or replace another.  Currently this is intended only for statements inside
	 * 'start extension' inside PRJ_EXTENSIONS
	 * @param into
	 * @param from
	 * @return
	 */
	private boolean sameStatementClass(IASTBldInfStatement into,
			IASTBldInfStatement from) {
		String kw1 = into.getKeywordName();
		String kw2 = from.getKeywordName();
		if (kw1 == null || kw2 == null)
			return false;
		kw1 = kw1.toUpperCase();
		kw2 = kw2.toUpperCase();
		if (!kw1.equals(kw2)) 
			return false;
		if (into instanceof IASTBldInfExtensionStatement
				&& from instanceof IASTBldInfExtensionStatement 
				&& kw1.equals("OPTION")) { //$NON-NLS-1$
			// the key of the option must match
			return ((IASTBldInfExtensionStatement) into).getArguments().get(0)
				.equalValue(((IASTBldInfExtensionStatement) from).getArguments().get(0));
		} else {
			return true;
		}
	}

	/**
	 * Take two statements of the same type and try to merge their contents.
	 * We already know that the statements are compatible, which means
	 * they have keywords that are the same.
	 * @param into
	 * @param from
	 * @return true if into was modified by merging with from, else false to use from wholesale
	 */
	private boolean mergeStatements(IASTBldInfStatement into,
			IASTBldInfStatement from) {
		if (into instanceof IASTBldInfExtensionStatement) {
			IASTBldInfExtensionStatement fromStmt = ((IASTBldInfExtensionStatement) from);
			IASTBldInfExtensionStatement intoStmt = ((IASTBldInfExtensionStatement) into);
			if (fromStmt.getArguments().equalValue(intoStmt.getArguments()))
				return true;
			fromStmt.getArguments().setParent(null);
			intoStmt.setArguments(fromStmt.getArguments());
			return true;
		} else {
			return false;
		}
	}

	protected void addErrorMessage(String msgText, IASTNode node) {
		IMessage message = new Message(IMessage.ERROR, node.getMessageLocation(),
				"BldInfView.ErrorMessage", msgText); //$NON-NLS-1$
		parseMessages.add(message);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView#getAllExtensions()
	 */
	public IExtension[] getAllExtensions() {
		List<IExtension> allExtensions = new ArrayList<IExtension>(this.extensions);
		allExtensions.addAll(testExtensions);
		return (IExtension[]) allExtensions.toArray(new IExtension[allExtensions.size()]);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#getData()
	 */
	public IBldInfData getData() {
		return new BldInfData(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IData#getModelPath()
	 */
	public IPath getModelPath() {
		return this.model.getPath();
	}
}
