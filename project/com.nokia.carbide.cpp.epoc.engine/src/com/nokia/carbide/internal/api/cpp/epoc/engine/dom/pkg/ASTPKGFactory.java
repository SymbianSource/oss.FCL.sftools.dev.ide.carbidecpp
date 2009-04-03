/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStream;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg.ASTPKGBinaryConditionExpression;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg.ASTPKGCommentStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg.ASTPKGConditionPrimitiveExpression;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg.ASTPKGConditionalBlock;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg.ASTPKGElseContainer;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg.ASTPKGEmbeddedSisEntry;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg.ASTPKGEmbeddedSisStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg.ASTPKGHardwareDependencyStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg.ASTPKGIfElseifContainer;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg.ASTPKGInstallFileStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg.ASTPKGLanguageStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg.ASTPKGLogoStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg.ASTPKGOptionsListOption;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg.ASTPKGOptionsListStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg.ASTPKGPackageHeaderStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg.ASTPKGPackageSignatureStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg.ASTPKGProblemStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg.ASTPKGPropertiesOrCapabilitiesStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg.ASTPKGSoftwareDependencyOrVersionCompatibilityStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg.ASTPKGTranslationUnit;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg.ASTPKGUnaryConditionExpression;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg.ASTPKGVendorStatement;
import com.nokia.cpp.internal.api.utils.core.*;


public abstract class ASTPKGFactory extends ASTFactory {

	public static IASTListNode<IASTPKGStatement> createPKGStatementListNode() {
		return createListNode("\n"); //$NON-NLS-1$
	}

	public static IASTPKGTranslationUnit createPKGTranslationUnit() {
		return createPKGTranslationUnit(null);
	}

	public static IASTPKGTranslationUnit createPKGTranslationUnit(
			IASTListNode<IASTPKGStatement> nodes) {
		if (nodes == null)
			nodes = createTopLevelNodeListNode();
		return new ASTPKGTranslationUnit(nodes);
	}

	public static IASTListNode<IASTPKGConditionalContainer> createPKGConditionalContainerListNode() {
		return createListNode("\n"); //$NON-NLS-1$
	}

	public static IASTLiteralTextNode createLiteralTextNode(String string) {
		return ASTFactory.createRawLiteralTextNode(string);
	}
	
	/**
	 * @since 2.0
	 */
	public static IASTPKGEmbeddedSisEntry createEmbeddedSisEntry(String entry) {
		IASTPKGEmbeddedSisEntry node = new ASTPKGEmbeddedSisEntry(entry);
		return node;
	}

	public static IASTPKGProblemStatement creatPKGProblemStatement(
			IASTPreprocessorTokenStream stream, IMessage message) {
		return new ASTPKGProblemStatement(stream, message);
	}

	public static IASTPKGCommentStatement createPKGCommentStatement(
			IASTLiteralTextNode commentNode) {
		return new ASTPKGCommentStatement(commentNode);
	}

	// Test support only
	public static IASTPKGCommentStatement createPKGCommentTest(
			String stringWithoutCommentSemicolon) {
		return new ASTPKGCommentStatement(
				createLiteralTextNode(stringWithoutCommentSemicolon));
	}

	public static IASTPKGLanguageStatement createPKGLanguageStatement(
			IASTListNode<IASTLiteralTextNode> languages) {
		return new ASTPKGLanguageStatement(languages);
	}

	// Test support only
	public static IASTPKGLanguageStatement createPKGLanguageTest(
			String[] languages) {
		IASTListNode<IASTLiteralTextNode> listNode = ASTFactory
				.createListNode(","); //$NON-NLS-1$
		IASTLiteralTextNode literalNode;
		for (String language : languages) {
			literalNode = createLiteralTextNode(language);
			if (literalNode != null) {
				listNode.add(literalNode);
			}
		}
		IASTPKGLanguageStatement node = createPKGLanguageStatement(listNode);
		return node;
	}

	public static IASTPKGPackageHeaderStatement createPKGPackageHeaderStatement(
			IASTListNode<IASTLiteralTextNode> headersLocalized,
			IASTLiteralTextNode uid, IASTListNode<IASTLiteralTextNode> version,
			IASTListNode<IASTLiteralTextNode> options) {
		return new ASTPKGPackageHeaderStatement(headersLocalized, uid, version,
				options);
	}

	public static IASTPKGPackageHeaderStatement createPKGPackageHeaderTest(
			String[] headersLocalized, String uid, String[] version,
			String[] options) {
		IASTLiteralTextNode literalNode;
		IASTListNode<IASTLiteralTextNode> headersLocalizedNode = ASTFactory
				.createListNode(","); //$NON-NLS-1$;
		IASTLiteralTextNode uidNode;
		IASTListNode<IASTLiteralTextNode> versionNode = ASTFactory
				.createListNode(","); //$NON-NLS-1$;
		IASTListNode<IASTLiteralTextNode> optionsNode = ASTFactory
				.createListNode(","); //$NON-NLS-1$;
		for (String header : headersLocalized) {
			literalNode = createLiteralTextNode(header);
			if (literalNode != null) {
				headersLocalizedNode.add(literalNode);
			}
		}
		uidNode = createLiteralTextNode(uid);
		for (String versionString : version) {
			literalNode = createLiteralTextNode(versionString);
			if (literalNode != null) {
				versionNode.add(literalNode);
			}
		}
		if (options != null) {
			for (String optionString : options) {
				literalNode = createLiteralTextNode(optionString);
				if (literalNode != null) {
					optionsNode.add(literalNode);
				}
			}
		}

		return new ASTPKGPackageHeaderStatement(headersLocalizedNode, uidNode,
				versionNode, optionsNode);
	}

	public static IASTPKGVendorStatement createPKGVendorStatement(
			IASTListNode<IASTLiteralTextNode> vendorsLocalized,
			boolean languageDependentSyntaxStatus) {
		return new ASTPKGVendorStatement(vendorsLocalized,
				languageDependentSyntaxStatus);
	}

	// Test support only
	public static IASTPKGVendorStatement createPKGVendorTest(
			String[] vendorsLocalized, boolean languageDependentSyntaxStatus) {
		IASTListNode<IASTLiteralTextNode> listNode = ASTFactory
				.createListNode(","); //$NON-NLS-1$
		IASTLiteralTextNode literalNode;
		for (String vendor : vendorsLocalized) {
			literalNode = createLiteralTextNode(vendor);
			if (literalNode != null) {
				listNode.add(literalNode);
			}
		}
		IASTPKGVendorStatement node = createPKGVendorStatement(listNode,
				languageDependentSyntaxStatus);
		return node;
	}

	public static IASTPKGLogoStatement createPKGLogoStatement(
			IASTLiteralTextNode sourcePath, IASTLiteralTextNode mimeType,
			IASTLiteralTextNode destPath) {
		return new ASTPKGLogoStatement(sourcePath, mimeType, destPath);
	}

	// Test support only
	public static IASTPKGLogoStatement createPKGLogoTest(String sourcePath,
			String mimeType, String destPath) {
		return new ASTPKGLogoStatement(createLiteralTextNode(sourcePath),
				createLiteralTextNode(mimeType), destPath == null ? null
						: createLiteralTextNode(destPath));
	}

	public static IASTPKGPackageSignatureStatement createPKGPackageSignatureStatement(
			IASTLiteralTextNode key, IASTLiteralTextNode cert) {
		return new ASTPKGPackageSignatureStatement(key, cert);
	}

	// Test support only
	public static IASTPKGPackageSignatureStatement createPKGPackageSignatureTest(
			String key, String cert) {
		return new ASTPKGPackageSignatureStatement(createLiteralTextNode(key),
				createLiteralTextNode(cert));
	}

	public static IASTPKGHardwareDependencyStatement createPKGHardwareDependencyStatement(
			IASTLiteralTextNode uid, IASTListNode<IASTLiteralTextNode> version,
			IASTListNode<IASTLiteralTextNode> components) {
		return new ASTPKGHardwareDependencyStatement(uid, version, components);
	}

	// Test support only
	public static IASTPKGHardwareDependencyStatement createPKGHardwareDependencyTest(
			String uid, String[] version, String component) {
		IASTLiteralTextNode literalNode;
		IASTListNode<IASTLiteralTextNode> versionNode = ASTFactory
				.createListNode(","); //$NON-NLS-1$
		IASTListNode<IASTLiteralTextNode> componentsNode = ASTFactory
				.createListNode(","); //$NON-NLS-1$

		for (String versionString : version) {
			literalNode = createLiteralTextNode(versionString);
			if (literalNode != null) {
				versionNode.add(literalNode);
			}
		}

		componentsNode.add(createLiteralTextNode(component));

		return new ASTPKGHardwareDependencyStatement(
				createLiteralTextNode(uid), versionNode, componentsNode);
	}

	// Test support only
	public static IASTPKGHardwareDependencyStatement createPKGHardwareDependencyTest(
			String uid, String[] version, String[] components) {
		IASTLiteralTextNode literalNode;
		IASTListNode<IASTLiteralTextNode> versionNode = ASTFactory
				.createListNode(","); //$NON-NLS-1$
		IASTListNode<IASTLiteralTextNode> componentsNode = ASTFactory
				.createListNode(","); //$NON-NLS-1$

		for (String versionString : version) {
			literalNode = createLiteralTextNode(versionString);
			if (literalNode != null) {
				versionNode.add(literalNode);
			}
		}

		for (String componentsString : components) {
			literalNode = createLiteralTextNode(componentsString);
			if (literalNode != null) {
				componentsNode.add(literalNode);
			}
		}

		return new ASTPKGHardwareDependencyStatement(
				createLiteralTextNode(uid), versionNode, componentsNode);
	}

	// version with range 1,2,3~1,2,*
	public static IASTPKGSoftwareDependencyOrVersionCompatibilityStatement createPKGSoftwareDependencyOrVersionCompatibilityStatement(
			IASTLiteralTextNode uid,
			IASTListNode<IASTLiteralTextNode> versionLowerBound,
			IASTListNode<IASTLiteralTextNode> versionUpperBound,
			IASTListNode<IASTLiteralTextNode> components) {
		return new ASTPKGSoftwareDependencyOrVersionCompatibilityStatement(uid,
				versionLowerBound, versionUpperBound, components);
	}

	// version without range
	public static IASTPKGSoftwareDependencyOrVersionCompatibilityStatement createPKGSoftwareDependencyOrVersionCompatibilityStatement(
			IASTLiteralTextNode uid, IASTListNode<IASTLiteralTextNode> version,
			IASTListNode<IASTLiteralTextNode> components) {
		return new ASTPKGSoftwareDependencyOrVersionCompatibilityStatement(uid,
				version, components);
	}

	// Test support only
	public static IASTPKGSoftwareDependencyOrVersionCompatibilityStatement createPKGVersionCompatibilityTest(
			String uid, String[] version, String component) {
		IASTLiteralTextNode literalNode;
		IASTListNode<IASTLiteralTextNode> versionNode = ASTFactory
				.createListNode(","); //$NON-NLS-1$
		IASTListNode<IASTLiteralTextNode> componentsNode = ASTFactory
				.createListNode(","); //$NON-NLS-1$

		for (String versionString : version) {
			literalNode = createLiteralTextNode(versionString);
			if (literalNode != null) {
				versionNode.add(literalNode);
			}
		}
		componentsNode.add(createLiteralTextNode(component));

		return new ASTPKGSoftwareDependencyOrVersionCompatibilityStatement(
				createLiteralTextNode(uid), versionNode, componentsNode);
	}

	// Test support only
	public static IASTPKGSoftwareDependencyOrVersionCompatibilityStatement createPKGSoftwareDependencyStatementTest(
			String uid, String[] versionLowerBound, String[] versionUpperBound,
			String[] components) {
		IASTLiteralTextNode literalNode;
		IASTListNode<IASTLiteralTextNode> versionLowerBoundNode = ASTFactory
				.createListNode(","); //$NON-NLS-1$
		IASTListNode<IASTLiteralTextNode> versionUpperBoundNode = ASTFactory
				.createListNode(","); //$NON-NLS-1$
		IASTListNode<IASTLiteralTextNode> componentsNode = ASTFactory
				.createListNode(","); //$NON-NLS-1$

		if (versionLowerBound != null) {
			for (String versionLowerBoundString : versionLowerBound) {
				literalNode = createLiteralTextNode(versionLowerBoundString);
				if (literalNode != null) {
					versionLowerBoundNode.add(literalNode);
				}
			}
		}

		if (versionUpperBound != null) {
			for (String versionUpperBoundString : versionUpperBound) {
				literalNode = createLiteralTextNode(versionUpperBoundString);
				if (literalNode != null) {
					versionUpperBoundNode.add(literalNode);
				}
			}
		} else {
			versionUpperBoundNode = null;
		}

		for (String componentsString : components) {
			literalNode = createLiteralTextNode(componentsString);
			if (literalNode != null) {
				componentsNode.add(literalNode);
			}
		}

		return new ASTPKGSoftwareDependencyOrVersionCompatibilityStatement(
				createLiteralTextNode(uid), versionLowerBoundNode,
				versionUpperBoundNode, componentsNode);
	}

	// Test support only
	public static IASTPKGSoftwareDependencyOrVersionCompatibilityStatement createPKGSoftwareDependencyStatementTest(
			String uid, String[] version, String[] components) {
		IASTLiteralTextNode literalNode;
		IASTListNode<IASTLiteralTextNode> versionNode = ASTFactory
				.createListNode(","); //$NON-NLS-1$
		IASTListNode<IASTLiteralTextNode> componentsNode = ASTFactory
				.createListNode(","); //$NON-NLS-1$

		for (String versionLowerBoundString : version) {
			literalNode = createLiteralTextNode(versionLowerBoundString);
			if (literalNode != null) {
				versionNode.add(literalNode);
			}
		}

		for (String componentsString : components) {
			literalNode = createLiteralTextNode(componentsString);
			if (literalNode != null) {
				componentsNode.add(literalNode);
			}
		}

		return new ASTPKGSoftwareDependencyOrVersionCompatibilityStatement(
				createLiteralTextNode(uid), versionNode, componentsNode);
	}

	public static IASTPKGPropertiesOrCapabilitiesStatement createPKGPropertiesOrCapabilitiesStatement(
			IASTListNode<IASTLiteralTextNode> properties) {
		return new ASTPKGPropertiesOrCapabilitiesStatement(properties);
	}

	// Test support only
	public static IASTPKGPropertiesOrCapabilitiesStatement createPKGPropertiesOrCapabilitiesTest(
			String[] properties) {
		IASTListNode<IASTLiteralTextNode> listNode = ASTFactory
				.createListNode(","); //$NON-NLS-1$
		IASTLiteralTextNode literalNode;
		for (String property : properties) {
			literalNode = createLiteralTextNode(property);
			if (literalNode != null) {
				listNode.add(literalNode);
			}
		}
		IASTPKGPropertiesOrCapabilitiesStatement node = createPKGPropertiesOrCapabilitiesStatement(listNode);
		return node;
	}

	public static IASTPKGBinaryConditionExpression createPKGBinaryConditionExpression(
			boolean isAnd, IASTPKGConditionExpression leftExpression,
			IASTPKGConditionExpression rightExpression) {
		return new ASTPKGBinaryConditionExpression(isAnd, leftExpression,
				rightExpression);
	}

	public static IASTPKGUnaryConditionExpression createPKGUnaryConditionExpression(
			boolean negation, IASTPKGConditionExpression node) {
		return new ASTPKGUnaryConditionExpression(negation, node);
	}

	public static IASTPKGConditionPrimitiveExpression createPKGConditionPrimitiveExpression(
			IASTNode node) {
		return new ASTPKGConditionPrimitiveExpression(node);
	}

	public static IASTPKGConditionalBlock createPKGConditionalBlock(
			IASTListNode<IASTPKGConditionalContainer> containers) {
		return new ASTPKGConditionalBlock(containers);
	}

	public static IASTPKGIfElseifContainer createPKGIfElseifContainer(
			IASTPKGConditionExpression condition,
			IASTListNode<IASTPKGStatement> statements) {
		return new ASTPKGIfElseifContainer(condition, statements);
	}

	public static IASTPKGElseContainer createPKGElseContainer(
			IASTListNode<IASTPKGStatement> statements) {
		return new ASTPKGElseContainer(statements);
	}

	public static IASTPKGOptionsListOption createPKGOptionsListOption(
			IASTListNode<IASTLiteralTextNode> option) {
		return new ASTPKGOptionsListOption(option);
	}

	public static IASTPKGOptionsListStatement createPKGOptionsListStatement(
			IASTListNode<IASTPKGOptionsListOption> optionsList) {
		return new ASTPKGOptionsListStatement(optionsList);
	}

	/**
	 * @since 2.0
	 */
	public static IASTPKGInstallFileStatement createPKGInstallFileStatement(
			IASTListNode<IASTLiteralTextNode> languageVariants,
			IASTLiteralTextNode targetFile,
			IASTListNode<IASTLiteralTextNode> options) {
		return new ASTPKGInstallFileStatement(languageVariants, targetFile,
				options);
	}

	/**
	 * @since 2.0
	 */
	public static IASTPKGEmbeddedSisStatement createPKGEmbeddedSisStatement(
			IASTListNode<IASTLiteralTextNode> languageVariants, IASTLiteralTextNode uid) {
		return new ASTPKGEmbeddedSisStatement(languageVariants, uid);
	}
}
