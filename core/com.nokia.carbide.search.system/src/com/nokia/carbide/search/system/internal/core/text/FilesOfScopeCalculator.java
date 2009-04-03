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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.filesystem.provider.FileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.search.system.core.text.TextSearchScope;

public class FilesOfScopeCalculator implements IResourceProxyVisitor {

	private static IFileSystem fileSystem = EFS.getLocalFileSystem();
	private final TextSearchScope fScope;
	private final MultiStatus fStatus;
	private final IProgressMonitor fMonitor;
	
	private HashSet<String> directoryList;
	private int foundCount = 0;

	private ArrayList fFiles;

	public FilesOfScopeCalculator(TextSearchScope scope, MultiStatus status, IProgressMonitor monitor) {
		fScope= scope;
		fStatus= status;
		fMonitor= monitor;
	}

	public boolean visit(IResourceProxy proxy) {		
		boolean inScope= fScope.contains(proxy);

		if (inScope && proxy.getType() == IResource.FILE) {
			fFiles.add(proxy.requestResource());
		}
		return inScope;
	}

	public FileStore[] process() {
		fFiles= new ArrayList();
		
		if (   (fScope.getRootFolder() != null)
			&& (fScope.getRoots() == null))
		{
			File file = new File(fScope.getRootFolder());
				
			if (file.exists() && file.isDirectory() && file.canRead()) {
				fMonitor.subTask(Messages.getString("FilesOfScopeCalculator.0FilesMatch")); //$NON-NLS-1$
				searchFromRootFolder(file);
			}

			if (fFiles.size() == 1)
				fMonitor.subTask(Messages.getString("FilesOfScopeCalculator.1FileMatches")); //$NON-NLS-1$
			else
				fMonitor.subTask(fFiles.size() + Messages.getString("FilesOfScopeCalculator.filesMatch")); //$NON-NLS-1$
	
			return (FileStore[]) fFiles.toArray(new FileStore[fFiles.size()]);
		} else {
			try {
				IResource[] roots= fScope.getRoots();
				for (int i= 0; i < roots.length; i++) {
					try {
						IResource resource= roots[i];
						if (resource.isAccessible()) {
							resource.accept(this, 0);
						}
					} catch (CoreException ex) {
						// report and ignore
						fStatus.add(ex.getStatus());
					}
				}
				return (FileStore[]) fFiles.toArray(new FileStore[fFiles.size()]);
			} finally {
				fFiles= null;
			}
		}
	}

	public void searchFromRootFolder(File file) {
		
		directoryList = new HashSet<String>();

		// exclude hidden root unless hidden is included
		// note that the root of a drive will return the hidden attribute on WinNT
		if (file.getParent() != null && !fScope.isIncludeHidden() && file.isHidden())
			return;

		int depth = fScope.isIncludeSubfolders() ? -1 : 1;

		internalSearch(file, depth);
	}

	private void internalSearch(File file, int depth)
	{
		// if the file is a directory, make sure that we have not seen its absolute path before,
		// then check its children (unless subfolders are not to be checked)
		if (file.isDirectory()) {
			String filePath;

			try
			{
				filePath = file.getCanonicalPath();
			}
			catch (IOException e)
			{
				try {
					filePath = file.getAbsolutePath();
				}
				catch (SecurityException se)
				{
					filePath = file.getPath();
				}
			}
			catch (SecurityException se)
			{
				filePath = file.getPath();
			}
			
			if (directoryList.contains(filePath))
				return;
			
			if (depth != 0) {
				directoryList.add(filePath);

				File[] children;
				
				try {
					children = file.listFiles();
				}
				catch (SecurityException se)
				{
					return;
				}
				
				if (children != null)
				{
					for (int i = 0; i < children.length && !fMonitor.isCanceled(); i++)
					{
						File child = children[i];

						if (!fScope.isIncludeHidden() && child.isHidden())
							continue;
						
						internalSearch(child, depth - 1);
					}

					return;
				}
			}
			
			return;
		}

		String fileName = file.getName();

		// base case for the recursive method call
		// if we get a match with the file name, add the file to the list
		if (doesFilePatternMatch(fileName))
		{
			try
			{
				fileName = file.getCanonicalPath();
			}
			catch (IOException e)
			{
				try {
					fileName = file.getAbsolutePath();
				}
				catch (SecurityException se)
				{
					fileName = file.getPath();
				}
			}
			catch (SecurityException se)
			{
				fileName = file.getPath();
			}

			IFileStore fileStore = fileSystem.getStore(new Path(fileName));
			fFiles.add(fileStore);

			foundCount++;
			if (   (foundCount <= 20)
				|| (foundCount <= 100 && foundCount % 10 == 0)
				|| (foundCount % 100 == 0))
				fMonitor.subTask(foundCount + Messages.getString("FilesOfScopeCalculator.filesMatch")); //$NON-NLS-1$
		}
	}

	protected boolean doesFilePatternMatch(String fileName)
	{
		return fScope.matchesFileName(fileName);
	}

}
