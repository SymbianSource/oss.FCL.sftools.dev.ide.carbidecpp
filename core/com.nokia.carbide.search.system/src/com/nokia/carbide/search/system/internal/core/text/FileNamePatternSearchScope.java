/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation, Nokia and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.nokia.carbide.search.system.internal.core.text;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;


import com.nokia.carbide.search.system.core.text.TextSearchScope;

public class FileNamePatternSearchScope extends TextSearchScope {

	/**
	 * Returns a scope for the given resources.
	 * @param description description of the scope
	 * @param resources the resources to be contained
	 * @param rootFolder root folder for the search
	 * @param includeSubfolders whether to search subfolders
	 * @param includeHidden whether to search hidden files
	 * @param includeArchives whether to search archives
	 * @return a scope for the given resources.
	 */
	public static FileNamePatternSearchScope newSearchScope(String description, IResource[] resources, boolean includeDerived,
			String rootFolder, boolean includeSubfolders, boolean includeHidden, boolean includeArchives) {
		return new FileNamePatternSearchScope(description, removeRedundantEntries(resources, includeDerived), includeDerived,
				rootFolder, includeSubfolders, includeHidden, includeArchives);
	}
	
	/**
	 * Returns a scope for the given resources.
	 * @param description description of the scope
	 * @param resources the resources to be contained
	 * @return a scope for the given resources.
	 */
	public static FileNamePatternSearchScope newSearchScope(String description, String rootFolder, boolean includeSubfolders, boolean includeHidden, boolean includeArchives) {
		return new FileNamePatternSearchScope(description, null, false,	rootFolder, includeSubfolders, includeHidden, includeArchives);
	}

	private static final boolean IS_CASE_SENSITIVE_FILESYSTEM = !new File("Temp").equals(new File("temp")); //$NON-NLS-1$ //$NON-NLS-2$
	
	private final String fDescription;
	private final IResource[] fRootElements;
	
	private final Set fFileNamePatterns;
	private Matcher fFileNameMatcher;
	
	private boolean fVisitDerived;
	
	private final String fRootFolder;
	private final boolean fIncludeSubfolders;
	private final boolean fIncludeHidden;
	private final boolean fIncludeArchives;

	private FileNamePatternSearchScope(String description, IResource[] resources, boolean visitDerived,
						String rootFolder, boolean includeSubfolders, boolean includeHidden, boolean includeArchives) {
		Assert.isNotNull(description);
		fDescription= description;
		fRootElements= resources;
		fFileNamePatterns=  new HashSet(3);
		fFileNameMatcher= null;
		fVisitDerived= visitDerived;
		fRootFolder = rootFolder;
		fIncludeSubfolders = includeSubfolders;
		fIncludeHidden = includeHidden;
		fIncludeArchives = includeArchives;
	}
	
	/**
	 * Returns the description of the scope
	 * @return the description of the scope
	 */
	public String getDescription() {
		return fDescription;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.search.system.core.text.FileSearchScope#getRoots()
	 */
	public IResource[] getRoots() {
		return fRootElements;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.search.system.core.text.FileSearchScope#contains(org.eclipse.core.resources.IResourceProxy)
	 */
	public boolean contains(IResourceProxy proxy) {
		if (!fVisitDerived && proxy.isDerived()) {
			return false; // all resources in a derived folder are considered to be derived, see bug 103576
		}
		
		if (proxy.getType() == IResource.FILE) {
			return matchesFileName(proxy.getName());
		}
		return true;
	}
		
	/**
	 * Adds an file name pattern  to the scope.
	 * @param pattern
	 */
	public void addFileNamePattern(String pattern) {
		if (fFileNamePatterns.add(pattern)) {
			fFileNameMatcher= null; // clear cache
		}
	}
	
	public void setFileNamePattern(Pattern pattern) {
		fFileNameMatcher= pattern.matcher(""); //$NON-NLS-1$
	}
	
	
	public Pattern getFileNamePattern() {
		return getFileNameMatcher().pattern();
	}
		
	/**
	 * Returns if derived resources are included in the scope.
	 * 
	 * @return if set derived resources are included in the scope.
	 */
	public boolean isIncludeDerived() {
		return fVisitDerived;
	}
	

	private Matcher getFileNameMatcher() {
		if (fFileNameMatcher == null) {
			Pattern pattern;
			if (fFileNamePatterns.isEmpty()) {
				pattern= Pattern.compile(".*"); //$NON-NLS-1$
			} else {
				String[] patternStrings= (String[]) fFileNamePatterns.toArray(new String[fFileNamePatterns.size()]);
				pattern= PatternConstructor.createPattern(patternStrings, IS_CASE_SENSITIVE_FILESYSTEM);
			}
			fFileNameMatcher= pattern.matcher(""); //$NON-NLS-1$
		}
		return fFileNameMatcher;
	}
	
	/**
	 * Tests if a file name matches to the file name patterns contained in the scope
	 * @param fileName The file name to test
	 * @return returns true if the file name is matching to a file name pattern
	 */
	public boolean matchesFileName(String fileName) {
 		return getFileNameMatcher().reset(fileName).matches();
	}
		
	/**
	 * Returns a description for the file name patterns in the scope
	 * @return the description of the scope
	 */
	public String getFileNamePatternDescription() {
		String[] ext= (String[]) fFileNamePatterns.toArray(new String[fFileNamePatterns.size()]);
		Arrays.sort(ext);
		StringBuffer buf= new StringBuffer();
		for (int i= 0; i < ext.length; i++) {
			if (i > 0) {
				buf.append(", "); //$NON-NLS-1$
			}
			buf.append(ext[i]);
		}
		return buf.toString();
	}

	
	private static IResource[] removeRedundantEntries(IResource[] elements, boolean includeDerived) {
		ArrayList res= new ArrayList();
		for (int i= 0; i < elements.length; i++) {
			IResource curr= elements[i];
			addToList(res, curr, includeDerived);
		}
		return (IResource[])res.toArray(new IResource[res.size()]);
	}
	
	private static void addToList(ArrayList res, IResource curr, boolean includeDerived) {
		if (!includeDerived && isDerived(curr)) {
			return;
		}
		IPath currPath= curr.getFullPath();
		for (int k= res.size() - 1; k >= 0 ; k--) {
			IResource other= (IResource) res.get(k);
			IPath otherPath= other.getFullPath();
			if (otherPath.isPrefixOf(currPath)) {
				return;
			}
			if (currPath.isPrefixOf(otherPath)) {
				res.remove(k);
			}
		}
		res.add(curr);
	}

	private static boolean isDerived(IResource curr) {
		do {
			if (curr.isDerived()) {
				return true;
			}
			curr= curr.getParent();
		} while (curr != null);
		return false;
	}

	@Override
	public String getRootFolder() {
		return fRootFolder;
	}

	@Override
	public boolean isIncludeSubfolders() {
		return fIncludeSubfolders;
	}

	@Override
	public boolean isIncludeHidden() {
		return fIncludeHidden;
	}
}
