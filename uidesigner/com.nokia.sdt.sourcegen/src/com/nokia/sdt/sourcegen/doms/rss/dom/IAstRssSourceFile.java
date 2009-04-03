/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.sourcegen.doms.rss.dom;

import com.nokia.sdt.sourcegen.core.ISourceFile;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.*;

/**
 * This is a source file (header, main file, etc.) whose DOM is rewritable.
 * Meaning, this interface keeps track of the nodes in a file so that they can
 * be written to disk with as few changes as necessary from the original version
 * of the file.
 * <p>
 * The file consists of "file-level nodes", which are nodes expected
 * to be declared at the file scope level, notable entries of which are:<p>
 * <ul>
 * <li>IAstTopLevelNode
 *  <ul>
 *  <li>IAstStructDeclaration
 *  <li>IAstEnumDeclaration
 *  <li>IAstRlsString
 *  </ul>
 * <li>IAstPreprocessorNode
 *  <ul>
 *  <li>IAstPreprocessorIncludeDirective
 *  <li>IAstPreprocessorDefineDirective
 *  </ul>
 * </ul>
 * <p>
 * This interface provides convenience functions to find and gather 
 * all the specific types of file-level nodes above.
 * <p>
 * The function "add" has special meaning.  It means to place a node
 * in the "best" place.  We choose this to mean after all other entries
 * of the same type.  Furthermore, if all nodes are "added" to a file
 * (rather than appended or inserted), then these node types will be
 * emitted in source together and in this order:
 * <ul>
 * <li>system includes
 * <li>user includes
 * <li>macro defines
 * <li>rls_string entries
 * <li>top-level nodes
 * </ul>
 * 
 * 
 * The specific node-to-source mapping is accessed from IAstNode.
 * IAstNode#getSourceRange() returns non-null when the contents of the node came
 * from the on-disk version of a node, and null when a node has been newly
 * generated. A null range is also returned when a node's contributing file is
 * not an instance of this interface.
 * <p>
 * IAstNode#hasDirtySource() returns true when a node's contents have been
 * changed and its text needs to be regenerated.
 * <p>
 * In order to preserve trailing comments (e.g. for fields) or leading comments
 * (e.g. for resources), an IAstNode's source range may contain nonessential
 * leading or trailing text. It is up to the RSS parser to decide where such
 * comments are likely to exist and whether they need to be associated with a
 * node.
 * <p>
 * Thus, if a given node is not modified, we can write out the original text,
 * with any user-supplied comment unchanged. But if the node does change, the
 * entire text is regenerated and the comment may be lost, depending on the
 * parser's implementation.
 * <p>
 * Any text not represented by an IAstNode in the DOM proper is represented in
 * an IAstPreprocessorNode (either IAstPreprocessorDirective or
 * IAstPreprocessorTextNode). (N.B.: the automatic attachment of comments to
 * IAstNode as described above also has the effect of curtailing an exploding
 * number of these nodes.)
 * <p>
 * An IAstRssSourceFile provides references to all the files included via
 * #include as IAstPreprocessorIncludeDirective entries which reference
 * IAstSourceFile nodes. Over a translation unit, the same
 * ISourceFile may thus be referenced multiple times, but we expect that due to
 * include guards, most of the duplicate IAstSourceFile entries will be
 * empty.
 * 
 * 
 * 
 */
public interface IAstRssSourceFile extends IAstSourceFile {

    /**
     * Get the top-level nodes defined by this file.
     */
    public IAstTopLevelNode[] getTopLevelNodes();

    /**
     * Tell whether the file contains a given node
     */
    public boolean hasTopLevelNode(IAstTopLevelNode node);

    /**
     * Get all the preprocessor nodes structuring this file. These include
     * directives and "extra" text not included in other nodes in the DOM
     * proper.
     * <p>
     * For instance, until a "problem" node exists, unparseable text will appear
     * as IAstPreprocessorTextNode outside of an IAstNode's range, when, say, a
     * nonsense top-level declaration exists; or overlapping the range of an
     * IAstNode, when a child of a complex node is unparseable.
     */
    public IAstPreprocessorNode[] getPreprocessorNodes();

    /**
     * Get all the macro definitions in this file
     * <p>
     * Note: the #defines are also accessible by iterating
     * getPreprocessorNodes()
     * <p>You must update any translation unit, if present, to notice. 
     * @see ITranslationUnit#refresh()
     */
    public IAstPreprocessorDefineDirective[] getDefines();

    /**
     * Find a define
     */
    public IAstPreprocessorDefineDirective findDefine(String name);

    /**
     * Find an #include included in this file (non-recursive)
     */
    public IAstSourceFile findIncludeFile(ISourceFile file);

    /**
     * Find an #include included in this file (non-recursive).
     * Compares by filename.
     */
    public IAstSourceFile findIncludeFile(String string);
 
    /**
     * Find an #include included in this file (non-recursive)
     */
    public IAstPreprocessorIncludeDirective findInclude(ISourceFile file);

    /**
     * Find an #include included in this file (non-recursive)
     */
    public IAstPreprocessorIncludeDirective findInclude(String string);
 
    /**
     * Get all the includes in this file, in #include order.
     * Does not provide recursively included files.
     * This returns IAstSourceFile nodes, which reflect the
     * contents of the included files.  These may be empty
     * when the second #include of the same file occurs.
     * <p>
     * Note: the #includes are also accessible by iterating
     * #getPreprocessorNodes()
     */
    public IAstPreprocessorIncludeDirective[] getIncludeFiles();

    /**
     * Get all macro references in this file
     * <p>
     * Note: the references are also accessible by iterating
     * getPreprocessorNodes()
     */
    public IAstPreprocessorMacroExpression[] getMacroExpressions();

    /**
     * Get all the *.rls includes in this file, in #include order.
     * Does not provide recursively included files.
     * This returns IAstRlsSourceFile nodes, which reflect the
     * contents of the included files. 
     * <p>
     * Note: the #includes are also accessible by iterating
     * #getPreprocessorNodes()
     */
    public IAstRlsSourceFile[] getRlsSourceFiles();

    /**
     * Find a rls_string defined in this file (non-recursive)
     */
    public IAstRlsString findRlsString(String identifer);

    /**
     * Get all the rls_strings in this file.
     * Does not provide recursively included files.
     * <p>
     * Note: the #includes are also accessible by iterating
     * #getTopLevelNodes()
     */
    public IAstRlsString[] getRlsStrings();

	/**
	 * Find a given RLS include
	 * @param langCode
	 * @return
	 */
	public IAstRlsSourceFile findRlsFile(int langCode);

	/**
	 * Remove an RLS file
	 */
	public void removeRlsSourceFile(IAstRlsSourceFile rlsFile);
	
	/**
	 * Refresh cached tables if nodes are changed outside
	 */
	public void refresh();
    
}
