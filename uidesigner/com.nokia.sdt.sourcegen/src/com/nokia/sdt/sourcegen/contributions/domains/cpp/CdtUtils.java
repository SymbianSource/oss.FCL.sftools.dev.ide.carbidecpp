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

package com.nokia.sdt.sourcegen.contributions.domains.cpp;

import org.eclipse.cdt.core.dom.ast.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 *
 */
public class CdtUtils {
    // tokens in C++
    static final Pattern cppKeywordsPattern = Pattern.compile(
"asm|_asm|__asm|__asm__|auto|break|case|catch|char|const|continue|default|delete|do|double|else|enum|extern|float|for|friend|"+ //$NON-NLS-1$
"goto|if|inline|int|long|new|private|protected|public|redeclared|register|return|short|signed|sizeof|"+ //$NON-NLS-1$
"static|struct|class|switch|template|this|try|typedef|union|unsigned|virtual|void|volatile|while|"+ //$NON-NLS-1$
"operator|true|false|throw" //$NON-NLS-1$
            );
    
    // tokens guaranteed to represent types
    static final Pattern cppSimpleTypePattern = Pattern.compile(
            "char|double|float|int|long|short" //$NON-NLS-1$
    );
    

    private static final Pattern commentPattern = Pattern.compile("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)"); //$NON-NLS-1$
    private static final Pattern argumentPattern = Pattern.compile(",", Pattern.DOTALL); //$NON-NLS-1$
    private static final Pattern tokenPattern = Pattern.compile("(?=\\W)|\\b", Pattern.DOTALL); //$NON-NLS-1$
    private static final Pattern extensionsPattern = Pattern.compile("__declspec\\([^)]*\\)|__attribute__\\(\\s*\\([^)]*\\)\\s*\\)", Pattern.DOTALL); //$NON-NLS-1$
    private static final Pattern functionPattern = Pattern.compile("((\\w|:|~)+)\\s*\\((.*)\\)", Pattern.DOTALL); //$NON-NLS-1$
    private static final Pattern scopePattern = Pattern.compile(".*::(\\w+)$"); //$NON-NLS-1$

	private static final Pattern baseClassPattern = Pattern.compile("\\s*[:,]?\\s*(public|protected|private)?\\s*((\\w|:)+)\\s*,?", Pattern.DOTALL); //$NON-NLS-1$

	private static final Pattern includeDirectivePattern = Pattern.compile("\\s*#\\s*include\\s+(?:\"|<)(.+?)(?:\"|>)\\s*"); //$NON-NLS-1$
;


    /**
     * Find all the IASTDeclaration nodes in the given scope
     * @param parent
     * @return list of nodes or null
     */
    public static IASTDeclaration[] getDeclarations(IASTNode parent) {
        IASTDeclaration[] decls = null;
        if (parent instanceof IASTTranslationUnit) {
            IASTTranslationUnit tu = (IASTTranslationUnit) parent;
            decls = tu.getDeclarations();
        } else if (parent instanceof IASTCompositeTypeSpecifier) {
            IASTCompositeTypeSpecifier c = (IASTCompositeTypeSpecifier) parent;
            decls = c.getMembers();
        } else if (parent instanceof IASTFunctionDefinition) {
            IASTFunctionDefinition def = (IASTFunctionDefinition) parent;
            IASTStatement[] stmts = null;
            IASTStatement stmt = def.getBody();
            if (stmt instanceof IASTCompoundStatement) {
                IASTCompoundStatement cstmt = (IASTCompoundStatement) stmt;
                stmts = cstmt.getStatements();
            }
            if (stmts != null) {
                List declstmts = new ArrayList();
                for (int i = 0; i < stmts.length; i++) {
                    IASTStatement statement = stmts[i];
                    if (statement instanceof IASTDeclarationStatement) {
                        declstmts.add(((IASTDeclarationStatement) statement).getDeclaration());
                    }
                }
                decls = (IASTDeclaration[]) declstmts.toArray(new IASTDeclaration[declstmts.size()]);
            }
        }
        return decls;
    }

    /**
     * @param parent
     * @param name
     * @param class1
     */
    static IASTNode findNamedNode(IASTNode parent, String name, Class class1) {
        IASTDeclaration[] decls = getDeclarations(parent);
        if (decls == null)
            return null;
        
        for (int i = 0; i < decls.length; i++) {
            IASTDeclaration decl = decls[i];
            System.out.println(decl);
            if (decl instanceof IASTSimpleDeclaration) {
                IASTSimpleDeclaration sdecl = (IASTSimpleDeclaration) decl;
                IASTDeclSpecifier declspec = sdecl.getDeclSpecifier();
                System.out.println("declspec: "+ declspec); //$NON-NLS-1$
                //IASTName nodeName = getNameIfImplementing(declspec, class1);
                //if (nodeName != null && nodeName.toString().equals(name)) {
                    //return declspec;
                //}
            }
/*            IASTNodeLocation[] locs = decl.getNodeLocations();
            for (int j = 0; j < locs.length; j++) {
                System.out.println(locs[j].getNodeOffset() + " ... +" + locs[j].getNodeLength()
                        +"\n" + code.substring(locs[j].getNodeOffset(), locs[j].getNodeLength() + locs[j].getNodeOffset()));
            }
*/
        }
        return null;
    }

    /**
     * This strips comments from the text
     */
    public static CharSequence stripComments(CharSequence text) {
        // remove all the comments
    	if (text == null)
    		return null;
        Matcher commentMatcher = commentPattern.matcher(text);
        text = commentMatcher.replaceAll(""); //$NON-NLS-1$
        return text;
    }
    
    /**
     * This routine parses a contribution that should contain a prototype,
     * and canonicalizes it into a form that ignores whitespace and argument names.
     * <p>
     * Unfortunately we can't use CDT to parse this in isolation because 
     * it will probably reference types which are not declared.  
     * When CDT sees unknown identifiers, it just gives up.
     * <p>  
     * @param text
     * @return signature, or null if not a prototype
     */
    public static CharSequence getPrototypeSignature(CharSequence text) {
        // remove compiler-specific stuff
        Matcher extMatcher = extensionsPattern.matcher(text);
        text = extMatcher.replaceAll(""); //$NON-NLS-1$
        
        // Find the method name and arguments.
        // Ignore the return type, since that can't differentiate overloads anyway.
        Matcher matcher = functionPattern.matcher(text);
        if (matcher.find()) {
            // simplify method name, removing redundant class:: if present
            String name = matcher.group(1);
            Matcher scopeMatcher = scopePattern.matcher(name);
            if (scopeMatcher.matches())
                name = scopeMatcher.group(1);
            
            String paramText = matcher.group(3);
            
            String paramSig = CdtUtils.getParametersSignature(paramText);
            
            String signature = name + "(" + paramSig + ")"; //$NON-NLS-1$ //$NON-NLS-2$
            return signature;
        } else {
            return null;
        }
    }

    /**
     * Parse parameters, returning a string that represents the signature,
     * trying to ignore parameter names (assumed to begin with lowercase)
     * <p>
     * We can't really parse a prototype to remove superfluous
     * stuff without implementing a full C++ parser -- we try to
     * do the obvious things. 
     * @param paramText
     * @return parameters signature
     */
    public static String getParametersSignature(String paramText) {
        StringBuffer ret = new StringBuffer();
        
        // get each argument -- we ignore possible embedded function pointers
        String[] params = argumentPattern.split(paramText);
        for (int i = 0; i < params.length; i++) {
            if (params[i].length() == 0)
                continue;
            
            String paramSig = CdtUtils.getTypeSignature(params[i]);
            
            if (ret.length() > 0)
                ret.append(',');
            
            ret.append(paramSig);
        }
        return ret.toString();
    }

    /**
     * Parse something that should contain type information, 
     * returning a string that represents the signature,
     * trying to ignore parameter names (assumed to be lowercase words 
     * at the end)
     * <p>
     * We can't really parse a prototype to remove superfluous
     * stuff without implementing a full C++ parser -- we try to
     * do the obvious things. 
     * @param text
     * @return signature
     */
    public static String getTypeSignature(String text) {
        StringBuffer ret = new StringBuffer();
        
        // split the parameter into tokens and punctuation
        String[] tokens = tokenPattern.split(text);
        boolean sawTypeToken = false;
        int lastIndexToKeep = -1;
        int lastIndexIgnored = -1;
        for (int j = 0; j < tokens.length; j++) {
            if (tokens[j].trim().length() == 0) {
                tokens[j] = null;
                continue;
            }
            
            // stop at an initializer
            if (tokens[j].equals("=")) //$NON-NLS-1$
                break;
            
            // if at an array, clear any name
            if (tokens[j].equals("[")) { //$NON-NLS-1$
                if (lastIndexIgnored > lastIndexToKeep)
                    for (int k = lastIndexIgnored; k < j; k++)
                        tokens[k] = null;
            }
            
            // watch for some type indicator
            if (cppSimpleTypePattern.matcher(tokens[j]).matches()
                    || Character.isUpperCase(tokens[j].charAt(0))) {
                sawTypeToken = true;
                lastIndexToKeep = j;
            }
            else if (cppKeywordsPattern.matcher(tokens[j]).matches()) {
                lastIndexToKeep = j;
            }
            else // assume that words which begin with lowercase are argument names
                if (sawTypeToken && Character.isLowerCase(tokens[j].charAt(0))) {
                    if (lastIndexIgnored > lastIndexToKeep)
                        lastIndexToKeep = j;
                    else
                        lastIndexIgnored = j;
                }
                else
                    lastIndexToKeep = j;
        }
            
        for (int j = 0; j <= lastIndexToKeep; j++) {
            // add spaces between words only
            if (tokens[j] == null)
                continue;
            if (ret.length() > 0 && Character.isLetterOrDigit(ret.charAt(ret.length() - 1))
                    && tokens[j].length() > 0 && Character.isLetter(tokens[j].charAt(0)))
                ret.append(' ');
            ret.append(tokens[j]);
        }
        return ret.toString();
    }

	/**
	 * Get the base class from a base class specifier.
	 * The format may start with a colon or comma
	 * and may end with a comma.  The class name precedes
	 * the final punctuation.
	 * @param text
	 * @return the name of the class, or null
	 */
	public static String getBaseClass(CharSequence text) {
        Matcher matcher = baseClassPattern.matcher(text);
        if (matcher.find())
        	return matcher.group(2);
        return null;
	}

	/**
	 * Find the file referenced by an #include.
	 * Ignores user/system paths.
	 * @param text
	 * @return the included file, or null
	 */
	public static String getInclude(CharSequence text) {
		Matcher matcher = includeDirectivePattern.matcher(text);
        if (matcher.find())
        	return matcher.group(1);
        return null;
	}


}
