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

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.sourcegen.doms.rss.IRssModelManipulator;
import com.nokia.sdt.sourcegen.doms.rss.IRssModelResourceHandler;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstName;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstResourceDefinition;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 *
 */
public class RssModelResourceHandler implements IRssModelResourceHandler {

	private IRssModelManipulator manipulator;
	private boolean projectUniqueResources;

	/**
	 * @param manipulator
	 */
	public RssModelResourceHandler(IRssModelManipulator manipulator) {
		this.manipulator = manipulator;
		this.projectUniqueResources = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelResourceHandler#isGeneratingProjectUniqueResources()
	 */
	public boolean isGeneratingProjectUniqueResources() {
		return projectUniqueResources;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelResourceHandler#setGeneratingProjectUniqueResources(boolean)
	 */
	public void setGeneratingProjectUniqueResources(boolean flag) {
		this.projectUniqueResources = flag;
	}
	
	/**
     * Make a name derived from the given name.
     * This name is not guaranteed to be unique.
     * @param model the model owning the instance
     * @param startName the starting name, usually instance + property
     * @return new name suitable for identifier
     */
    public String getDerivedResourceName(IDesignerDataModel model, String startName) {
        if (startName == null || startName.equals("")) //$NON-NLS-1$
            return "r_unnamed"; //$NON-NLS-1$
        
        String name;
        if (model != null && isGeneratingProjectUniqueResources()) {
        	String modelName = SymbianModelUtils.getModelBaseName(manipulator.getModelProxy().getModelSpecifier()); 
            	//manipulator.getNameGenerator().getModelBaseName(model);
            name = Utilities.getCleanIdentifierName(modelName + "_" + startName); //$NON-NLS-1$
        } else {
            name = Utilities.getCleanIdentifierName(startName); //$NON-NLS-1$
        }
        
        // replace camel-case with underscores
        Pattern pattern = Pattern.compile("((?<![A-Z_])[A-Z][0-9a-z_]+)"); //$NON-NLS-1$
        Matcher matcher = pattern.matcher(name);
        name = matcher.replaceAll("_$1"); //$NON-NLS-1$

        // now lowercase it all
        name = name.toLowerCase();

        // the regex is not precise; an initial uppercase letter will invoke an underscore
        if (Character.isLetter(startName.charAt(0)) && name.charAt(0) == '_')
            return "r" + name; //$NON-NLS-1$
        else
            return "r_" + name; //$NON-NLS-1$
    }

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelResourceHandler#findOrCreateUniqueResourceDefinition(com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit, com.nokia.sdt.sourcegen.doms.rss.dom.IAstStructDeclaration, java.lang.String)
	 */
    public IAstResourceDefinition findOrCreateUniqueResourceDefinition(
    		ITranslationUnit tu, IAstStructDeclaration decl,
    		String rsrcName) {
        IAstResourceDefinition def = tu.findResourceDefinition(rsrcName);
        if (def == null) {
            // make a new one
            def = new AstResourceDefinition(decl, new AstName(rsrcName, null));
            return def;
        } else {
            // Check that the resource is not a user resource,
            // since we don't touch them
            if (manipulator.getResourceTracker().findInfoForResource(def) == null)
                return null;

            IAstSourceFile defSf = def.getAstSourceFile();

            // check to see the existing resource is the same type;
            // and see if we can write back to the file it came from
            // (no ISourceFile means read-only)
            
            // note: this process can end up stranding resources
            // when components change type but their names stay the same --
            // a problem?
            
            if (def.getStructType() == decl 
                    && defSf instanceof IAstRssSourceFile
                    && !defSf.isReadOnly()
                    && defSf.getSourceFile() != null) {
                return def;
            } else {
                // look for another usable name
                return null;
            }
        }
    }

}
