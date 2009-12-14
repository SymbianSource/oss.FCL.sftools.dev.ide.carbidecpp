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


package com.nokia.carbide.internal.cpp.epoc.engine.model.pkg;

import com.nokia.carbide.cpp.epoc.engine.model.IData;
import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.*;
import com.nokia.carbide.internal.cpp.epoc.engine.Messages;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTUtils;
import com.nokia.carbide.internal.cpp.epoc.engine.model.*;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.IDocumentParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ParserFactory;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.pkg.IPKGParserConfiguration;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.IDocument;

import java.io.File;
import java.util.*;

public class PKGView extends ViewBase<IPKGOwnedModel> implements IPKGView {

	private static final String LANG_SOURCEFILE_NUMBER_MISMATCH = "PKGView.LanguageSourceFileNumberMismatch"; //$NON-NLS-1$

	private IASTPKGTranslationUnit tu;
	private List<EPKGLanguage> languages;
	private List<IPKGInstallFile> allInstallFiles;
	private List<IPKGInstallFile> topInstallFiles;
	private List<IPKGInstallFile> addedInstallFiles;
	private List<IPKGInstallFile> removedInstallFiles;
	private List<IMessage> messageList;
	private IPKGHeader pkgHeader;
	private IPKGHeader modifiedPkgHeader;
	private List<IPKGEmbeddedSISFile> allEmbeddedSisFiles;
	private List<IPKGEmbeddedSISFile> addedEmbeddedSisFiles;
	private List<IPKGEmbeddedSISFile> removedEmbeddedSisFiles;

	public PKGView(ModelBase model, IViewConfiguration configuration) {
		super(model, null, configuration);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase#getSlashFormat()
	 */
	@Override
	protected SlashFormat getSlashFormat() {
		return SlashFormat.BACKWARD;
	}
	
	public IPKGModel getPKGModel() {
		return (IPKGModel) getModel();
	}

	public IPKGInstallFile createInstallFile(IPKGStatementContainer container) {
		PKGInstallFile installFile = new PKGInstallFile(null, container);
		if (container != null)
			container.getStatements().add(installFile);
		return installFile;
	}

	public void addInstallFile(IPKGInstallFile installFile) {
		addedInstallFiles.add(installFile);
	}

	public void removeInstallFile(IPKGInstallFile installFile) {
		removedInstallFiles.add(installFile);
	}

	public IPKGInstallFile[] getAllInstallFiles() {
		return (IPKGInstallFile[]) allInstallFiles
				.toArray(new IPKGInstallFile[allInstallFiles.size()]);
	}

	public List<IPKGInstallFile> getInstallFileList() {
		return topInstallFiles;
	}

	public List<EPKGLanguage> getLanguages() {
		return languages;
	}

	@Override
	public IPath[] getReferencedFiles() {
		return new IPath[] { getModel().getPath() };
	}

	@Override
	protected void addViewSpecificMessages(List<IMessage> messageList) {
		messageList.addAll(this.messageList);
	}

	@Override
	protected void internalCommit() {
		Check.checkState(tu != null);
		for (IPKGInstallFile installFile : addedInstallFiles) {
			IASTPKGInstallFileStatement statement = createASTInstallFileStatement(installFile);
			tu.getNodes().add(statement);
		}

		// won't find matching AST object if we create one like above since the line numbers will be different
		// so we pass the AST to the constructor but it's not exposed in any API
		for (IPKGInstallFile installFile : removedInstallFiles) {
			if (installFile instanceof PKGInstallFile) {
				removePKGStatement(installFile, ((PKGInstallFile)installFile).getInstallFileStatement());
			}
		}
		
		if (modifiedPkgHeader != null && modifiedPkgHeader instanceof PKGHeader) {
			IASTPKGPackageHeaderStatement header = getASTPackageHeaderStatement(modifiedPkgHeader);
			int index = tu.getNodes().indexOf(header);
			if (index >= 0) {
				tu.getNodes().set(index, header);
			} else {
				tu.getNodes().add(header);
			}
		}
		
		for (IPKGEmbeddedSISFile embeddedSisFile : addedEmbeddedSisFiles) {
			IASTPKGEmbeddedSisStatement statement = createASTEmbeddedSisFileStatement(embeddedSisFile);
			tu.getNodes().add(statement);
		}

		// won't find matching AST object if we create one like above since the line numbers will be different
		// so we pass the AST to the constructor but it's not exposed in any API
		for (IPKGEmbeddedSISFile embeddedSisFile : removedEmbeddedSisFiles) {
			if (embeddedSisFile instanceof PKGEmbeddedSISFile) {
				removePKGStatement(embeddedSisFile, ((PKGEmbeddedSISFile)embeddedSisFile).getEmbeddedSisFileStatement());
			}
		}

		// do b0ilerplate stuffs
		boolean changed = false;
		model.lock();
		try {
			Map<File, IASTTranslationUnit> updatedFileMap = new HashMap<File, IASTTranslationUnit>();
			updatedFileMap.put(model.getPath().toFile(), tu);
			changed = model.commitDocument(null, updatedFileMap, this);
		} finally {
			model.unlock();
		}

		// start over to get fresh source locations and data, using the updated
		// documents
		doRevert(true);

		// always notify of change, or else we've destroyed the DOM and other views will fail to commit
		model.desyncOtherViews(this);
		if (changed) {
			model.fireViewChanged(this);
		}
	}
	
	private void removePKGStatement(IPKGStatement pkgStatement, IASTPKGStatement astStatement) {
		
		IPKGStatementContainer container = pkgStatement.getContainer();
		if (container != null) {
			if (container instanceof PKGConditionalContainer) {
				IASTPKGConditionalContainer astContainer = ((PKGConditionalContainer)container).getASTPKGConditionalContainer();
				astContainer.getStatements().remove(astStatement);
			}
		} else {
			tu.getNodes().remove(astStatement);
		}
	}

	@Override
	protected boolean internalHasChanges() {
		return !addedInstallFiles.isEmpty() || !removedInstallFiles.isEmpty() || modifiedPkgHeader != null ||
				!addedEmbeddedSisFiles.isEmpty() || !removedEmbeddedSisFiles.isEmpty();
	}

	@Override
	protected Map<IPath, IDocument> internalReparse(
			Map<IPath, IDocument> overrideDocumentMap) {
		refresh();
		Check.checkState(tu != null);
		return ASTUtils.getDocumentMap(tu);
	}

	private void initData() {
		languages = new ArrayList<EPKGLanguage>();
		allInstallFiles = new ArrayList<IPKGInstallFile>();
		topInstallFiles = new ArrayList<IPKGInstallFile>();
		addedInstallFiles = new ArrayList<IPKGInstallFile>();
		removedInstallFiles = new ArrayList<IPKGInstallFile>();
		messageList = new ArrayList<IMessage>();
		pkgHeader = null;
		modifiedPkgHeader = null;
		allEmbeddedSisFiles = new ArrayList<IPKGEmbeddedSISFile>();
		addedEmbeddedSisFiles = new ArrayList<IPKGEmbeddedSISFile>();
		removedEmbeddedSisFiles = new ArrayList<IPKGEmbeddedSISFile>();
	}

	protected void refresh() {
		IDocumentParser pkgParser = ParserFactory
				.createPKGParser(new IPKGParserConfiguration() {
				});
		tu = (IASTPKGTranslationUnit) pkgParser.parse(getModel().getPath(),
				getModel().getDocument());
		Check.checkState(tu != null);
		initData();

		// get top-level node data
		IASTListNode<IASTTopLevelNode> nodes = tu.getNodes();
		for (IASTTopLevelNode node : nodes) {
			if (node instanceof IASTPKGLanguageStatement) {
				processLanguageStatement(node);
			} else if (node instanceof IASTPKGInstallFileStatement) {
				processInstallFileStatement(node, null);
			} else if (node instanceof IASTPKGConditionalBlock) {
				// get nested node data
				int result = node.accept(new IASTVisitor() {

					private IPKGStatementContainer currentContainer;

					public int visit(IASTNode node) {
						if (node instanceof IASTPKGConditionalContainer) {
							currentContainer = new PKGConditionalContainer((IASTPKGConditionalContainer)node);
						} else if (node instanceof IASTPKGInstallFileStatement) {
							Check.checkState(currentContainer != null);
							processInstallFileStatement(node, currentContainer);
							return VISIT_SIBLINGS;
						} else if (node instanceof IASTPKGEmbeddedSisStatement) {
							Check.checkState(currentContainer != null);
							processEmbeddedSisFileStatement(node, currentContainer);
							return VISIT_SIBLINGS;
						}
						return VISIT_CHILDREN;
					}
				});
				Check.checkState(result != IASTVisitor.VISIT_ABORT);
			} else if (node instanceof IASTPKGPackageHeaderStatement) {
				processPackageHeaderStatement(node, null);
			} else if (node instanceof IASTPKGEmbeddedSisStatement) {
				processEmbeddedSisFileStatement(node, null);
			}
		}
		// are we still sane?
		Check.checkState(tu != null);
	}

	@Override
	protected void internalRevertChanges() {
		addedInstallFiles.clear();
		removedInstallFiles.clear();
		modifiedPkgHeader = null;
		addedEmbeddedSisFiles.clear();
		removedEmbeddedSisFiles.clear();
	}

	@Override
	public boolean merge() {
		return false;
	}

	private IASTPKGInstallFileStatement createASTInstallFileStatement(
			IPKGInstallFile installFile) {
		// create the source files
		Map<EPKGLanguage, IPath> sourceFiles = installFile.getSourceFiles();
		List<String> sourceFileStrings = new ArrayList<String>();
		if (sourceFiles.size() == 1
				&& sourceFiles.containsKey(EPKGLanguage.INDEPENDENT)) {
			// if the source file is language independent, get it using the
			// constant language
			IPath path = sourceFiles.get(EPKGLanguage.INDEPENDENT);
			sourceFileStrings.add(getASTPathString(path));
		} else {
			// for language dependent source files, get them in order of the
			// languages
			for (EPKGLanguage language : languages) {
				IPath path = sourceFiles.get(language);
				sourceFileStrings.add(getASTPathString(path));
			}
		}
		IASTListNode<IASTLiteralTextNode> languageVariants = ASTPKGFactory
				.createRawLiteralTextNodeList(sourceFileStrings);
		languageVariants.setSeparator(", "); //$NON-NLS-1$

		// create the target file
		IASTLiteralTextNode targetFile = ASTPKGFactory
				.createLiteralTextNode(getASTPathString(installFile
						.getDestintationFile()));

		// create the options
		List<String> optionStrings = installFile.getOptions();
		IASTListNode<IASTLiteralTextNode> options = ASTPKGFactory
				.createRawLiteralTextNodeList(optionStrings);

		return ASTPKGFactory.createPKGInstallFileStatement(languageVariants,
				targetFile, options);
	}

	private IASTPKGEmbeddedSisStatement createASTEmbeddedSisFileStatement(
			IPKGEmbeddedSISFile embeddedSisFile) {
		// create the source files
		Map<EPKGLanguage, IPath> sourceFiles = embeddedSisFile.getSourceFiles();
		List<String> sourceFileStrings = new ArrayList<String>();
		if (sourceFiles.size() == 1
				&& sourceFiles.containsKey(EPKGLanguage.INDEPENDENT)) {
			// if the source file is language independent, get it using the
			// constant language
			IPath path = sourceFiles.get(EPKGLanguage.INDEPENDENT);
			sourceFileStrings.add(getASTPathString(path));
		} else {
			// for language dependent source files, get them in order of the
			// languages
			for (EPKGLanguage language : languages) {
				IPath path = sourceFiles.get(language);
				sourceFileStrings.add(getASTPathString(path));
			}
		}
		IASTListNode<IASTLiteralTextNode> languageVariants = ASTPKGFactory
				.createRawLiteralTextNodeList(sourceFileStrings);
		languageVariants.setSeparator(", "); //$NON-NLS-1$

		IASTLiteralTextNode uid = ASTPKGFactory.createLiteralTextNode(embeddedSisFile.getUid());

		return ASTPKGFactory.createPKGEmbeddedSisStatement(languageVariants, uid);
	}

	private IASTPKGPackageHeaderStatement getASTPackageHeaderStatement(
			IPKGHeader header) {
		
		IASTPKGPackageHeaderStatement astHeader = ((PKGHeader)header).getASTHeader();
		if (astHeader != null) {
			astHeader.setUid(ASTPKGFactory.createLiteralTextNode(header.getUid()));
			
			IASTListNode<IASTLiteralTextNode> version = ASTPKGFactory.createRawLiteralTextNodeList(header.getVersion());
			version.setSeparator(","); //$NON-NLS-1$
			astHeader.setVersion(version);

			IASTListNode<IASTLiteralTextNode> options = ASTPKGFactory.createRawLiteralTextNodeList(header.getOptions());
			options.setSeparator(","); //$NON-NLS-1$
			astHeader.setOptions(options);
		}
		
		return astHeader;
	}

	private String getASTPathString(IPath path) {
		return '"' + pathString(path) + '"';
	}

	private void processInstallFileStatement(IASTNode node,
			IPKGStatementContainer parentNode) {
		IASTPKGInstallFileStatement installFileStmt = (IASTPKGInstallFileStatement) node;
		PKGInstallFile installFile = new PKGInstallFile(installFileStmt, parentNode);
		IASTLiteralTextNode targetFile = installFileStmt.getTargetFile();
		installFile.setDestinationFile(PathUtils.createPath(TextUtils.unquote(targetFile
				.getStringValue(), '"')));
		IASTListNode<IASTLiteralTextNode> optionNodes = installFileStmt
				.getOptions();
		if (optionNodes != null) {
			List<String> options = installFile.getOptions();
			for (IASTLiteralTextNode optionNode : optionNodes) {
				options.add(optionNode.getStringValue());
			}
		}
		IASTListNode<IASTLiteralTextNode> sourceNodes = installFileStmt
				.getLanguageVariants();
		Map<EPKGLanguage, IPath> sourceFiles = installFile.getSourceFiles();
		if (installFileStmt.getLanguageDependentSyntaxStatus()) {
			int languageIndex = 0;
			if (sourceNodes.size() != languages.size()) {
				messageList.add(new Message(IMessage.ERROR, installFileStmt
						.getMessageLocation(), LANG_SOURCEFILE_NUMBER_MISMATCH,
						Messages.getString(LANG_SOURCEFILE_NUMBER_MISMATCH)));
			} else {
				for (IASTLiteralTextNode sourceNode : sourceNodes) {
					sourceFiles.put(languages.get(languageIndex++),
							PathUtils.createPath(TextUtils.unquote(sourceNode
									.getStringValue(), '"')));
				}
			}
		} else { // language independent
			Check.checkContract(sourceNodes.size() == 1); // parser should
			// always return
			// single source
			// node
			sourceFiles.put(EPKGLanguage.INDEPENDENT, PathUtils.createPath(TextUtils
					.unquote(sourceNodes.get(0).getStringValue(), '"')));
		}
		if (parentNode == null)
			topInstallFiles.add(installFile);
		allInstallFiles.add(installFile.copy());
	}

	private void processLanguageStatement(IASTNode node) {
		IASTListNode<IASTLiteralTextNode> variants = ((IASTPKGLanguageStatement) node)
				.getLanguageVariants();
		for (IASTLiteralTextNode textNode : variants) {
			EPKGLanguage lang = EPKGLanguage.forLangCode(textNode
					.getStringValue());
			languages.add(lang);
		}
	}

	private void processPackageHeaderStatement(IASTNode node,
			IPKGStatementContainer parentNode) {

		IASTPKGPackageHeaderStatement headerStmt = (IASTPKGPackageHeaderStatement) node;
		pkgHeader = new PKGHeader(headerStmt, parentNode);

		IASTLiteralTextNode uid = headerStmt.getUid();
		pkgHeader.setUid(TextUtils.unquote(uid.getStringValue(), '"'));

		IASTListNode<IASTLiteralTextNode> versionNodes = headerStmt.getVersion();
		if (versionNodes != null) {
			List<String> version = pkgHeader.getVersion();
			for (IASTLiteralTextNode versionNode : versionNodes) {
				version.add(versionNode.getStringValue());
			}
		}
		
		IASTListNode<IASTLiteralTextNode> optionNodes = headerStmt.getOptions();
		if (optionNodes != null) {
			List<String> options = pkgHeader.getOptions();
			for (IASTLiteralTextNode optionNode : optionNodes) {
				options.add(optionNode.getStringValue());
			}
		}
	}

	private void processEmbeddedSisFileStatement(IASTNode node, IPKGStatementContainer parentNode) {
		IASTPKGEmbeddedSisStatement embeddedSisFileStmt = (IASTPKGEmbeddedSisStatement) node;
		PKGEmbeddedSISFile embeddedSisFile = new PKGEmbeddedSISFile(embeddedSisFileStmt, parentNode);

		IASTLiteralTextNode uid = embeddedSisFileStmt.getUid();
		embeddedSisFile.setUid(TextUtils.unquote(uid.getStringValue(), '"'));
		
		IASTListNode<IASTLiteralTextNode> sourceNodes = embeddedSisFileStmt.getLanguageVariants();
		Map<EPKGLanguage, IPath> sourceFiles = embeddedSisFile.getSourceFiles();
		if (embeddedSisFileStmt.getLanguageVariants().size() > 1) {
			int languageIndex = 0;
			if (sourceNodes.size() != languages.size()) {
				messageList.add(new Message(IMessage.ERROR, embeddedSisFileStmt
						.getMessageLocation(), LANG_SOURCEFILE_NUMBER_MISMATCH,
						Messages.getString(LANG_SOURCEFILE_NUMBER_MISMATCH)));
			} else {
				for (IASTLiteralTextNode sourceNode : sourceNodes) {
					// remove the leading '@'
					sourceFiles.put(languages.get(languageIndex++),
							PathUtils.createPath(TextUtils.unquote(sourceNode.getStringValue().substring(1), '"')));
				}
			}
		} else { // language independent
			Check.checkContract(sourceNodes.size() == 1); // parser should
			// always return single source node.  remove the leading '@'.
			sourceFiles.put(EPKGLanguage.INDEPENDENT, PathUtils.createPath(TextUtils
					.unquote(sourceNodes.get(0).getStringValue().substring(1), '"')));
		}
		
		allEmbeddedSisFiles.add(embeddedSisFile.copy());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#getData()
	 */
	public IData getData() {
		return null;
	}

	public IPKGHeader getPackageHeader() {
		return modifiedPkgHeader == null ? pkgHeader : modifiedPkgHeader;
	}

	public void setPackageHeader(IPKGHeader header) {
		modifiedPkgHeader = header;
	}

	public void addEmbeddedSISFile(IPKGEmbeddedSISFile embeddedSisFile) {
		addedEmbeddedSisFiles.add(embeddedSisFile);
	}
	
	public void removeEmbeddedSISFile(IPKGEmbeddedSISFile embeddedSisFile) {
		removedEmbeddedSisFiles.add(embeddedSisFile);
	}

	public IPKGEmbeddedSISFile[] getAllEmbeddedSISFiles() {
		return (IPKGEmbeddedSISFile[]) allEmbeddedSisFiles.toArray(new IPKGEmbeddedSISFile[allEmbeddedSisFiles.size()]);
	}
}
