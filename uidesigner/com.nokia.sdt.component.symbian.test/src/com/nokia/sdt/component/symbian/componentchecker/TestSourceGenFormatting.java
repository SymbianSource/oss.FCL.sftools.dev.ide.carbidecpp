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

package com.nokia.sdt.component.symbian.componentchecker;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.symbian.IFacetContainer;
import com.nokia.sdt.component.symbian.sourcegen.TemplateJavaScriptFormatter;
import com.nokia.sdt.emf.component.*;
import com.nokia.sdt.sourcegen.contributions.domains.cpp.CdtUtils;
import com.nokia.sdt.sourcegen.templates.frontend.*;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.*;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Test that the templates for sourcegen appear to be formatted properly
 * 
 *
 */
public class TestSourceGenFormatting extends BaseComponentTest {
	private static final String TEST_PAREN_EXPR_SPACING = 
		"sourcegen-paren-expr-spacing";
	private static final String TEST_TEMPLATE_ARG_SPACING = 
		"sourcegen-template-arg-spacing";
	private static final String TEST_ARRAY_INDEXING_SPACING = 
		"sourcegen-array-indexing-spacing";
	private static final String TEST_PTR_REF_SPACING = 
		"sourcegen-ptr-ref-spacing";
	private static final String TEST_IF_POINTER = 
		"sourcegen-if-pointer";
	private static final String TEST_CLEAR_DELETED_POINTER = 
		"sourcegen-clear-deleted-pointer";
	private static final String TEST_LONG_LINE = 
		"sourcegen-long-line";
	private static final String TEST_LONG_EXPR_LINE = 
		"sourcegen-long-expr-line";
	private static final String TEST_VIRTUAL_KEYWORD = 
		"sourcegen-virtual-keyword";
	private static final String TEST_SPACES_INSTEAD_OF_TABS = 
		"sourcegen-spaces-instead-of-tabs";
	
    private static final Pattern PAREN_EXPR_PATTERN = Pattern.compile("\\(([^;]*)\\)", Pattern.DOTALL); //$NON-NLS-1$
    private static final Pattern TEMPLATE_ARG_PATTERN = Pattern.compile("\\<(?!<-)(\\w|:)*\\>", Pattern.DOTALL); //$NON-NLS-1$
    private static final Pattern ARRAY_INDEXING_PATTERN = Pattern.compile("\\[([^]]*)\\]", Pattern.DOTALL); //$NON-NLS-1$
    private static final Pattern PTR_REF_PATTERN = Pattern.compile("\\w+(\\s*)(?:\\&(?!&)|\\*+)(\\s*)(\\w+)?", Pattern.DOTALL); //$NON-NLS-1$
	private static final String TEST_COND_STMT_BRACES = 
		"sourcegen-cond-stmt-braces";
	private static final Pattern COND_STMT_WITHOUT_BRACES_PATTERN = Pattern.compile("(?:if|while|do|switch)\\s*\\(([^{;]*)\\)\\s*\\w+", Pattern.DOTALL);
	private static final Pattern ELSE_STMT_WITHOUT_BRACES_PATTERN = Pattern.compile("(?:else)\\s*\\w+", Pattern.DOTALL);
	private static final Pattern IF_PTR_PATTERN = Pattern.compile("\\bif\\s*\\(\\s*!?\\s*(\\w+)\\s*\\)", Pattern.DOTALL);
	private static final Pattern DELETE_PTR_PATTERN = Pattern.compile("\\bdelete\\s*(\\w+)\\s*;", Pattern.DOTALL);
	private static final String INIT_PTR_PATTERN_FORMAT = "\\b{0}\\s*=\\s*NULL;";
	protected static final String EXPRESSION_SUBSTITUTION_TOKEN = "TextFromExpression";
	private static final Pattern VIRTUAL_PATTERN = Pattern.compile("\\bvirtual\\b[^;~]+\\(", Pattern.DOTALL);
	private static final String MACRO_ARG_SUBSTITUTION_TOKEN = "ArgumentsFromMacro";
	private static final Pattern SPACES_PATTERN = Pattern.compile("^(    )+(.*)$", Pattern.MULTILINE);

	/**
	 * @param checker
	 */
	TestSourceGenFormatting(ComponentChecker checker, IComponent component) {
		super(checker, component);
	}

    private SourceGenType getSourceGenTypeFromContainer(IFacetContainer fc) {
        EStructuralFeature sourcegenFeature = 
            ComponentPackage.eINSTANCE.getComponentType().getEStructuralFeature(
                ComponentPackage.COMPONENT_TYPE__SOURCE_GEN);

        SourceGenType srcgenObj = null;
        EObject container = fc.getEMFContainer();
        Object featureObj = container.eGet(sourcegenFeature);
        if (featureObj instanceof SourceGenType)
            srcgenObj = (SourceGenType) featureObj;
        
        return srcgenObj;
    }

	public void runTests() {
		if (!(component instanceof IFacetContainer))
			return;
		
		IFacetContainer fc = (IFacetContainer) component;
		SourceGenType sg = getSourceGenTypeFromContainer(fc);
		if (sg == null)
			return;
		
		for (Iterator iter = sg.eAllContents(); iter.hasNext();) {
			EObject obj = (EObject) iter.next();
			if (obj instanceof TemplateType) {
				checkTemplate((TemplateType) obj);
				
			}
		}
	}

	
	/**
	 * Get actual source text from a template.
	 * @param str
	 * @return
	 */
	private String getTemplateRawText(String str) {
		str = TemplateJavaScriptFormatter.clipWhitespaceFromText(str, true, true);
		final StringBuffer buffer = new StringBuffer();
		FrontEnd frontend = new FrontEnd(true);
		frontend.setSource("test", str);
		Node node = frontend.parse();
		node.traverseDeep(new IDeepNodeVisitor() {
			Stack<BlockNode> blockStack = new Stack<BlockNode>();
			public void visitTextNode(TextNode node) {
				int type = BlockNode.BLOCK_RAW;
				if (blockStack.size() > 0)
					type = blockStack.peek().getType();
				if (type == BlockNode.BLOCK_RAW)
					buffer.append(node.getText());
				else if (type == BlockNode.BLOCK_EXPR)
					buffer.append(EXPRESSION_SUBSTITUTION_TOKEN);
			}

			public void visitBlockNode(BlockNode node, boolean open) {
				if (open)
					blockStack.push(node);
				else
					blockStack.pop();
			}
			
		});
		
		String rawText = buffer.toString();
		
		// remove macro args
		rawText = rawText.replaceAll("\\$\\([^)]*\\)", MACRO_ARG_SUBSTITUTION_TOKEN);
		return rawText;
    }


	private boolean isSpacey(String str) {
		return str.startsWith(" ") && str.endsWith(" ");
	}
	
	/**
	 * Check the text in a template, excluding script/expr code
	 * @param template
	 */
	private void checkTemplate(TemplateType template) {
		String text = template.getValue();
		if (text == null)
			return;

		// remove script/expr nodes
		text = getTemplateRawText(text);

		text = CdtUtils.stripComments(text).toString();
		
		// test for line-oriented tests
		testLongLines(template, text);
		testSpacesInsteadOfTabs(template, text);

		// collapse to one line for spacing-oriented tasks
		text = text.replaceAll("\r\n|\r|\n", " ");

		// ignore directives
		if (text.trim().startsWith("#"))
			return;
		
		testParentheses(template, text); 
		testTemplateArgumentSpacing(template, text); 
		testArrayIndexing(template, text);
		testPtrRef(template, text);
		testCondStmtBraces(template, text);
		testIfPtr(template, text);
		testClearDeletedPtr(template, text);
		testVirtualKeyword(template, text);
	}

	/**
	 * @param template
	 * @param text
	 */
	private void testLongLines(TemplateType template, String text) {
		String[] lines = text.split("\r\n|\r|\n");
		// account for indentation -- just a guess
		int indent = 0;
		
		// assume inside a block which is indented
		if (template.getPhase() != null)
			indent = 4;
		
		for (String line : lines) {
			if (line.indexOf(MACRO_ARG_SUBSTITUTION_TOKEN) >= 0) {
				// ignore -- these could be anything, long or short
				continue;
			}
			
			if (line.indexOf("@@@") >= 0) {
				// description for a patch; ignore
				continue;
			}
			if (line.indexOf(EXPRESSION_SUBSTITUTION_TOKEN) >= 0) {
				// we can't be sure how long the real expr is
				if (line.length() + indent >= 90) {
					reportTemplateError(template,   
							TEST_LONG_EXPR_LINE, "possibly too-long line: " + line);
	
				}
			} else {
				if (line.length() + indent >= 78) {
					reportTemplateError(template,   
							TEST_LONG_LINE, "overly long line: " + line);
	
				}
			}
		}
	}

	private void testParentheses(TemplateType template, String text) {
		Matcher matcher;
		matcher = PAREN_EXPR_PATTERN.matcher(text);
		while (matcher.find()) {
			checkAllGroups('(', ')', 
					template, matcher.group(), TEST_PAREN_EXPR_SPACING, "spaces missing in parenthesized expression");
		}
	}

	private void testTemplateArgumentSpacing(TemplateType template, String text) {
		Matcher matcher;
		matcher = TEMPLATE_ARG_PATTERN.matcher(text);
		while (matcher.find()) {
			checkAllGroups('<', '>', 
					template, matcher.group(), 
					TEST_TEMPLATE_ARG_SPACING, "spaces missing in template arguments");
		}
	}
	
	private void testPtrRef(TemplateType template, String text) {
		Matcher matcher;
		matcher = PTR_REF_PATTERN.matcher(text);
		while (matcher.find()) {
			String leading = matcher.group(1);
			String trailing = matcher.group(2);
			if (leading.length() != 0 || trailing.length() == 0)
				reportTemplateError(template,   
					TEST_PTR_REF_SPACING, "incorrect formatting of pointer/reference argument: " + matcher.group());
		}
	}
	
	/**
	 * Ensure array indexing does not have spurious spaces
	 * @param template
	 * @param text
	 */
	private void testArrayIndexing(TemplateType template, String text) {
		Matcher matcher;
		matcher = ARRAY_INDEXING_PATTERN.matcher(text);
		while (matcher.find()) {
			String arglist = matcher.group(1);
			if (isSpacey(arglist))
				reportTemplateError(template,   
					TEST_ARRAY_INDEXING_SPACING, "spaces present in array index: " + matcher.group());
		}

	}

	/**
	 * Ensure conditional statements have braces
	 * @param template
	 * @param text
	 */
	private void testCondStmtBraces(TemplateType template, String text) {
		Matcher matcher;
		matcher = COND_STMT_WITHOUT_BRACES_PATTERN.matcher(text);
		while (matcher.find()) {
			reportTemplateError(template,   
					TEST_COND_STMT_BRACES, "conditional statement does not use braces: " + matcher.group());
		}
		
		matcher = ELSE_STMT_WITHOUT_BRACES_PATTERN.matcher(text);
		while (matcher.find()) {
			reportTemplateError(template,   
					TEST_COND_STMT_BRACES, "conditional statement does not use braces: " + matcher.group());
		}

	}
	
	/**
	 * Test that no "if (ptr)" tests exist -- these should explicitly use ptr != NULL
	 * @param template
	 * @param text
	 */
	private void testIfPtr(TemplateType template, String text) {
		Matcher matcher;
		matcher = IF_PTR_PATTERN.matcher(text);
		while (matcher.find()) {
			reportTemplateError(template,   
					TEST_IF_POINTER, "if() on a pointer should use != or == NULL: " + matcher.group());
		}

	}

	/**
	 * Test that indentation, if any, uses tabs instead of spaces
	 * @param template
	 * @param text
	 */
	private void testSpacesInsteadOfTabs(TemplateType template, String text) {
		Matcher matcher;
		matcher = SPACES_PATTERN.matcher(text);
		while (matcher.find()) {
			reportTemplateError(template,   
					TEST_SPACES_INSTEAD_OF_TABS, "indentation should use tabs, not spaces: " + matcher.group());
		}
		
	}

	/**
	 * Test that deleted pointers are cleared.
	 * @param template
	 * @param text
	 */
	private void testClearDeletedPtr(TemplateType template, String text) {
		Matcher matcher;
		matcher = DELETE_PTR_PATTERN.matcher(text);
		while (matcher.find()) {
			Pattern initPattern = Pattern.compile(
					MessageFormat.format(INIT_PTR_PATTERN_FORMAT, new Object[] { matcher.group(1) }),
					Pattern.DOTALL);
			
			Matcher initMatcher = initPattern.matcher(text);
			if (!initMatcher.find(matcher.start())) {
				reportTemplateError(template,   
					TEST_CLEAR_DELETED_POINTER, "did not find 'ptr == NULL' after 'delete ptr': " + matcher.group());
			}
		}

	}

	/**
	 * Test that "virtual" isn't used when overriding methods.
	 * We assume that any method we implement is non-virtual.
	 * Add expected failures as needed.
	 * @param template
	 * @param text
	 */
	private void testVirtualKeyword(TemplateType template, String text) {
		
		Matcher matcher;
		matcher = VIRTUAL_PATTERN.matcher(text);
		while (matcher.find()) {
			reportTemplateError(template,   
					TEST_VIRTUAL_KEYWORD, "use of 'virtual' not allowed for overrides: " + matcher.group());
		}

	}
	/**
	 * Iterate through string and find all parenthesized groups, examining them in turn
	 * @param begin 
	 * @param end
	 * @param template 
	 * @param stmt
	 * @param test_name
	 * @param test_text
	 */
	private void checkAllGroups(char begin, char end, TemplateType template, String stmt, String test_name, String test_text) {
		Stack<Integer> groupStack = new Stack<Integer>();
		int idx = stmt.indexOf(begin);
		if (idx < 0)
			return;
		
		idx++;
		groupStack.push(idx);
		while (idx < stmt.length()) {
			if (stmt.charAt(idx) == end) {
				int start = groupStack.isEmpty() ? 0 : groupStack.pop();
				String expr = stmt.substring(start, idx);
				
				// allow "()" and "(MacroArgs)" (macros likely account for spaces correctly)
				if (expr.length() > 0 && !isSpacey(expr) 
						&& !expr.equals(MACRO_ARG_SUBSTITUTION_TOKEN)) {
					reportTemplateError(template, test_name, test_text + ": "+ stmt);
				}
			} else if (stmt.charAt(idx) == begin) {
				groupStack.push(idx+1);
			}
			idx++;
		}		
	}

	/**
	 * @param template
	 * @param stmt
	 * @param test_name
	 * @param test_text
	 */
	private void reportTemplateError(TemplateType template, String test_name, String test_text) {
		check(false,
				Severity.ERROR,
				component, test_name + "+" + getTemplateId(template), 
				test_text + "\n" + getInfo(template));
	}

	/**
	 * Get info about the offending template
	 * @param template
	 * @return
	 */
	private String getInfo(TemplateType template) {
		StringBuffer buffer = new StringBuffer();
		if (template instanceof InternalEObject) {
			URI uri = ((InternalEObject) template).eProxyURI();
			buffer.append(uri.fragment());
		}
		if (template.getLocation() != null)
			buffer.append("; loc: " + template.getLocation()); 
		else if (template.getPhase() != null)
			buffer.append("; phase: " + template.getPhase());
		else if (template.getId() != null)
			buffer.append("; id: " + template.getPhase());
		else {
			String allText = template.getValue();
			if (allText.length() > 40)
				allText = allText.substring(0, 40);
			buffer.append("; text: '" + allText + "...'");
		}
		return buffer.toString();
	}

	/**
	 * Get info about the offending template
	 * @param template
	 * @return
	 */
	private String getTemplateId(TemplateType template) {
		if (template instanceof InternalEObject) {
			URI uri = ((InternalEObject) template).eProxyURI();
			return uri.fragment();
		}
		if (template.getLocation() != null)
			return "loc=" + template.getLocation(); 
		else if (template.getPhase() != null)
			return "phase=" + template.getPhase();
		else if (template.getId() != null)
			return "id=" + template.getPhase();
		return "???";
	}

	boolean templateIsInMacro(TemplateType template) {
		if (template instanceof InternalEObject) {
			URI uri = ((InternalEObject) template).eProxyURI();
			if (uri.fragment().indexOf("defineMacro") >= 0)
				return true;
		}
		return false;
		
	}
}


