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
package com.nokia.sdt.sourcegen.doms.rss;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.sourcegen.core.ISourceFile;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Manage files on a project-wide basis.  This allows
 * sharing of RSS DOMs among the various data models
 * contributing to it.
 * 
 *
 */
public interface IRssProjectFileManager {

	/**
	 * Reset knowledge of stored files.
	 *
	 */
	public void reset();
	
	/**
	 * Find a registered source file
	 * @param file the file to locate
	 * @return ISourceFile or null
	 */
	public ISourceFile findSourceFile(File file);

    /**
     * Register a new source file
     * @param file the file to add
     */
    public void registerSourceFile(ISourceFile file);

    /**
     * Find a registered source file for the proxy.
     * @param proxy
     * @return file, or null
     */
	public ISourceFile findProxySourceFile(IRssModelProxy proxy);
	
	/**
	 * Register the source file for the proxy.
	 * @param proxy
	 * @param sf the source file to use, or null to use the default
	 */
	public void registerProxySourceFile(IRssModelProxy proxy, ISourceFile sf);


	/**
	 * Find or create the main RSS file for the given proxy and
	 * file name.  This registers a default file for the proxy if needed.
	 * <p>
	 * Pass filename==null to use the default name according to 
	 * the proxy.  Otherwise, the filename is the exact name to use
	 * (directory not fixed).
	 * <p>
	 * Usually, the RSS file is an *.rss file for the
	 * root data model and an *.rssi file for view data models.
	 * <p>
	 * @param proxy the proxy
	 * @param filename an absolute filename or null for proxy-defined name 
	 * @return
	 */
	public ISourceFile getRssSourceFile(IRssModelProxy proxy, String filename);
	
	/**
	 * Find or create an RSS file for the given model and filename.
	 * This may be an included file of a project-level RSS file.
	 * @param proxy
	 * @param fileName
	 * @param allowCreate 
	 * @return
	 * @see #getRssSourceFile(IRssModelProxy, String)
	 */
	public IAstRssSourceFile findOrCreateRssFile(IRssModelProxy proxy, String fileName, boolean allowCreate);

	/**
	 * Find the translation unit for the given model and filename.
	 * This refers to the top-level RSS DOM for the root model
	 * associated with the given proxy, or the translation unit for
	 * the given file.
	 * @param proxy
	 * @param fileName
	 * @return translation unit or null
	 */
	public ITranslationUnit findTranslationUnit(IRssModelProxy proxy, String fileName);

	/**
	 * Create or load translation unit (based on provider configuration).
	 * @param proxy
	 * @param fileName
	 * @return new translation unit
	 */
	public ITranslationUnit createOrLoadTranslationUnit(IRssModelProxy proxy, String fileName);

	/**
	 * Register the translation unit for the given model and filename.
	 * It is an error to register a TU with the same IAstRssSourceFile
	 * as another.
	 * @param proxy the model 
	 * @param fileName if null, use the root model's name, else use this name   
	 */
	public void registerTranslationUnit(IRssModelProxy proxy, String fileName, ITranslationUnit tu);

	/**
	 * Remove a translation unit from the registry.  
	 * @param translationUnit
	 */
	public void removeTranslationUnit(ITranslationUnit translationUnit);

	/**
	 * Load a source file, only if it's not loaded.
	 */
	public void loadSourceFile(ISourceFile file);

	/**
	 * Reload a source file, discarding existing contents
	 * @param file
	 */
	public void reloadSourceFile(ISourceFile file);
	
	/**
	 * Get immutable list of modified two-way files.
	 * @return
	 */
	public Collection<IAstRssSourceFile> getGeneratedRssFiles();

	/**
	 * Add an RSS source file to the context, to be remembered
	 * between a load and save.
	 * It is an error to register the same ISourceFile twice. 
	 * @param rssFile
	 */
	public void registerRssFile(IAstRssSourceFile rssFile);

	/**
	 * Initialize a file once generated -- this usually
	 * sets up the "do not modify" comment.  The file does
	 * not need to be registered.
	 */
	public void setupNewRssFile(IAstRssSourceFile rssFile);

	/**
	 * Forget about an RSS file
	 * @param rssFile
	 */
	public void removeRssFile(IAstRssSourceFile rssFile);

	/**
	 * Try to add several files.  Only those with unique
	 * ISourceFile are added. 
	 * @param files list of IAstSourceFile
	 */
	public void registerRssFiles(List<IAstRssSourceFile> files);

	/**
	 * Find a registered RSS source file.
	 * @param sourceFile
	 * @return
	 */
	public IAstRssSourceFile findRssFile(ISourceFile sourceFile);
	
	/**
	 * Merge a system include file into the translation unit, avoiding
	 * duplicate #includes.  This checks whether the TU already
	 * includes the file (directly or indirectly) and, if not,
	 * parses the given include file, marking it read-only.  
	 * The given file is included in the primary RSS file.
	 * @param tu the translation unit to merge into
	 * @param includeName the file to include
	 * @param file the source file to include
	 * @param context the context providing message handler and include lookup
	 * 
	 * @return 
	 */
	public IAstSourceFile mergeIncludeFile(ITranslationUnit tu, 
	        String includeName, ISourceFile file);

	/**
	 * Make sure the given header is included in the translation nit.
	 * @param tu
	 * @param header
	 * @return file or null
	 */
	public File ensureIncludedFile(ITranslationUnit tu, String header);

	/**
	 * Find or create a file whose name is derived from the given
	 * file.
	 * @param dataModel 
	 * @param baseFile the file based on which to name the derived file
	 * @param directoryId the destination directory id for { @link com.nokia.sdt.sourcegen.INameGenerator#getProjectRelativeDirectory(String) } 
	 * @param suffix 
	 * @param extension new extension (e.g. ".foo")
	 * @param context
	 * @return a source file
	 */
	public ISourceFile findOrCreateDerivedFile(
	        IDesignerDataModel dataModel, 
	        ISourceFile baseFile,
	        String directoryId, String suffix, String extension);
	
	/**
	 * Remove a registered source file
	 * @param existing
	 */
	public void removeSourceFile(ISourceFile existing);

	
	/**
	 * Find or create a source file whose name and path are derived
	 * from the given file.  This does not register the rss file.
	 * @param dataModel 
	 * @param rssFile the file on which to derive the filename
	 * @param directoryId the destination directory id for { @link com.nokia.sdt.sourcegen.INameGenerator#getProjectRelativeDirectory(String) }
	 * @param suffix the suffix to apppend before the extension, or null  
	 * @param extension new extension (e.g. ".foo")
	 * @param klazz the class that creates an IAstSourceFile instance,
	 * must take a constructor (ISourceFile file)
	 * @param constrArgs constructor args (may be null)
	 * @param includeIt if true, add an #include in the rssFile  
	 * @param context
	 * @return a source file
	 */
	public IAstSourceFile findOrCreateDerivedSourceFile(
			IDesignerDataModel dataModel, 
	       IAstRssSourceFile rssFile, String directoryId, String suffix,
	       String extension, Class klazz, Object[] constrArgs, boolean includeIt);

	/**
	 * Save modified files
	 * @param monitor
	 * @throws CoreException 
	 */
	public void saveModifiedSources(IProgressMonitor monitor) throws CoreException;

	/**
	 * Revery any changed files
	 */
	public void revert();

	/**
	 * Find or replace a given AST source file.  Use this to replace an incorrectly
	 * typed file with a correct type (i.e. ordinary RSS with a LOC/RLS file).
	 * @param sf the source file to look up
	 * @param tu the translation unit the ast file should be owned by 
	 * @param klazz AstSourceFile derived class to match
	 * @param constrArgs additional arguments after ISourceFile
	 * @return existing or newly created file
	 */
	public IAstRssSourceFile findOrReplaceTypedFile(ITranslationUnit tu, ISourceFile inclFile, Class klazz,
    		Object[] constrArgs);

	/**
	 * Synchronize all the ISourceFiles with their on-disk contents.
	 * @return true: changes detected
	 */
	public boolean synchronizeSourceFiles();

	/**
	 * Replace any registered file with this one.
	 * @param astSourceFile
	 */
	public void replaceRssFile(IAstRssSourceFile astSourceFile);

	public interface INewFileContentCreator {
		IAstNode[] createNewContent(IAstRssSourceFile rssFile);
	}
}
