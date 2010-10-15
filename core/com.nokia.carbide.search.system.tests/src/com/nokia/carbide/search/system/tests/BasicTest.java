/**
 * 
 */
package com.nokia.carbide.search.system.tests;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.IPDOMManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkingSet;
import org.junit.Test;

import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.search.system.core.text.TextSearchEngine;
import com.nokia.carbide.search.system.core.text.TextSearchScope;
import com.nokia.carbide.search.system.internal.core.text.PatternConstructor;
import com.nokia.carbide.search.system.internal.core.text.TextSearchEngineRegistry;
import com.nokia.carbide.search.system.internal.ui.SearchMessages;
import com.nokia.carbide.search.system.internal.ui.SearchPlugin;
import com.nokia.carbide.search.system.internal.ui.text.BasicElementLabels;
import com.nokia.carbide.search.system.internal.ui.text.FileSearchQuery;
import com.nokia.carbide.search.system.internal.ui.text.FileSearchResult;
import com.nokia.carbide.search.system.ui.ISearchQuery;
import com.nokia.carbide.search.system.ui.ISearchResult;
import com.nokia.carbide.search.system.ui.text.FileTextSearchScope;
import com.nokia.carbide.search.system.ui.text.IEditorMatchAdapter;
import com.nokia.carbide.search.system.ui.text.IFileMatchAdapter;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

import junit.framework.TestCase;

/**
 *
 */
public class BasicTest extends TestCase {
	static IProject carbideProject;
	private static final String BASE_DIR = "data/TestProject/";
	private static final String CARBIDE_PROJECT_NAME = "CarbideProject";
	private static final String PROJECT_RELATIVE_BLDINF_PATH = "group/bld.inf";

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	
	@Test
	public void testTextSearchEngineRegistry() throws Exception {
		TextSearchEngine engine = TextSearchEngine.create();
		TextSearchEngineRegistry registry = SearchPlugin.getDefault().getTextSearchEngineRegistry();
		String[][] availableEngines = registry.getAvailableEngines();
	}

	@Test
	public void testBasicFileTextSearch() throws Exception {
		String folderName = TestPlugin.getPluginPath("data");
		String[] fileNamePatterns = {"*.txt"};
		boolean includeSubFolders = true;
		boolean includeHidden = true;
		boolean includeArchives = true;
		FileTextSearchScope scope = FileTextSearchScope.newSystemScope(folderName, fileNamePatterns, folderName, includeSubFolders, includeHidden, includeArchives);
		assertNotNull("Search scope not created", scope);
		assertNotNull("Scope description is null", scope.getDescription());
		assertNull("Scope working set is not null", scope.getWorkingSets());
		assertNull("Scope content types is not null", scope.getContentTypes());
		assertNotNull("Scope fileNamePatterns is null", scope.getFileNamePatterns());
		assertNotNull("Scope filterDescription is null", scope.getFilterDescription());
		assertEquals("Scope includeSubFolders not expected", includeSubFolders, scope.includeSubfolders());
		assertEquals("Scope isIncludeSubFolders not expected", includeSubFolders, scope.isIncludeSubfolders());
		assertEquals("Scope includeHidden not expected", includeHidden, scope.includeHidden());
		assertEquals("Scope isIncludeHidden not expected", includeHidden, scope.isIncludeHidden());
		assertEquals("Scope includeArchives not expected", includeArchives, scope.includeArchives());
		IResource[] res = scope.getRoots();
		scope.getRootFolder();

		String searchText = "the";
		boolean isRegEx = false;
		boolean isCaseSensitive = false;
		FileSearchQuery query = new FileSearchQuery(searchText, isRegEx, isCaseSensitive, scope);
		assertNotNull("Query not created", query);
		assertEquals("Query isRegEx not expected", isRegEx, query.isRegexSearch());
		assertEquals("Query isCaseSensitive not expected", isCaseSensitive, query.isCaseSensitive());
		assertTrue("Query canRerun not true", query.canRerun());
		assertTrue("Query canRunInBackground not true", query.canRunInBackground());
		assertNotNull("Query scope is null", query.getSearchScope());
		assertEquals("Query search label not expected", SearchMessages.FileSearchQuery_label, query.getLabel());
		assertEquals("Query search text not expected", searchText, query.getSearchString());
		
		FileSearchResult result = (FileSearchResult)query.getSearchResult();
		assertNotNull("Search results are null", result);

		IProgressMonitor monitor = new NullProgressMonitor();
		IStatus status = query.run(monitor);
		assertEquals("Status from search query should be 0", 0, status.getCode());
		result = (FileSearchResult)query.getSearchResult();
		int matchCount = result.getMatchCount();
		assertTrue("Expected number of matches not found", matchCount > 0);
		Object[] elements = result.getElements();
		assertNotNull("Result elements is null", elements);
		String toolTip = result.getTooltip();
		ImageDescriptor d = result.getImageDescriptor();
		FileSearchQuery q = (FileSearchQuery)result.getQuery();
		IFileMatchAdapter f = result.getFileMatchAdapter();
		IEditorMatchAdapter e = result.getEditorMatchAdapter();
		String l = result.getLabel();
		String rl = q.getResultLabel(1);
		TextSearchEngineRegistry registry = SearchPlugin.getDefault().getTextSearchEngineRegistry();
		String[][] availableEngines = registry.getAvailableEngines();
		
		// file search only
		query = new FileSearchQuery("", isRegEx, isCaseSensitive, scope);
		status = query.run(monitor);
		assertEquals("Status from search query should be 0", 0, status.getCode());
		result = (FileSearchResult)query.getSearchResult();
		matchCount = result.getMatchCount();
		assertTrue("Expected number of matches not found", matchCount > 0);
		toolTip = result.getTooltip();
		
	}
	@Test
	public void testPatternConstructor() throws Exception {
		Pattern pattern = PatternConstructor.createPattern("word word", true, false, true, true);
		pattern = PatternConstructor.createPattern("word word", false, false, true, true);
		pattern = PatternConstructor.createPattern("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)", true, false, true, true);
	}
	@Test
	public void testBasicElementLabels() throws Exception {
		BasicElementLabels.getFilePattern("file");
		IPath p = new Path("/os");
		BasicElementLabels.getPathLabel(p, true);
		BasicElementLabels.getPathLabel(p, false);
		BasicElementLabels.getResourceName("resource");
		BasicElementLabels.getURLPart("http://www.x.xom/s.html#1");
		BasicElementLabels.getVersionName("1.4.1");
	}
	
	@Test
	public void testResourceSearch() throws Exception {
//		setUpProject();
//		tearDownProject();
	}

	private void tearDownProject() throws Exception {
		carbideProject.delete(true, true, null);
	}

	private void setUpProject() throws Exception {
		if (carbideProject == null){
			// turn off the indexer
			CCorePlugin.getIndexManager().setDefaultIndexerId(IPDOMManager.ID_NO_INDEXER);

			// turn off auto-building
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IWorkspaceDescription workspaceDesc = workspace.getDescription();
			workspaceDesc.setAutoBuilding(false);
			workspace.setDescription(workspaceDesc);

			// create the test project
			carbideProject = ProjectCorePlugin.createProject(CARBIDE_PROJECT_NAME, null);
			assertNotNull(carbideProject);

			// copy the project contents into the workspace
			File baseDir;
			if (Platform.isRunning()) {
				baseDir = FileUtils.pluginRelativeFile(TestPlugin.getDefault(), BASE_DIR);
			} else {
				baseDir = new File(BASE_DIR).getAbsoluteFile();
			}

			FileUtils.copyTreeNoParent(baseDir, carbideProject.getLocation().toFile(), new FileFilter() {
				public boolean accept(File arg0) {
					return true;
				}
			});
			
			// refresh the workspace
			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);

			List<ISymbianBuildContext> configs = getUsableBuildConfigs();
			
			ProjectCorePlugin.postProjectCreatedActions(carbideProject, PROJECT_RELATIVE_BLDINF_PATH, configs, new ArrayList<String>(), "Debug MMP", null, new NullProgressMonitor());
		}
	}

	/**
	 * Get some build configurations for the first non-empty SDK we find.
	 * @return a list of contexts, maximum 8
	 */
	private List<ISymbianBuildContext> getUsableBuildConfigs() {
		for (ISymbianSDK sdk : SDKCorePlugin.getSDKManager().getSDKList()) {
			if ((new File(sdk.getEPOCROOT()).exists())){
				// TODO: Convert to SBSv2 test
				List<ISymbianBuildContext> contexts = sdk.getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER).getAllBuildConfigurations();
				if (contexts.size() > 0) {
					return contexts.subList(0, Math.min(contexts.size(), 8));
				}
			}
		}
		TestCase.fail("No installed SDKs provide build configurations");
		return Collections.emptyList();
	}
}
