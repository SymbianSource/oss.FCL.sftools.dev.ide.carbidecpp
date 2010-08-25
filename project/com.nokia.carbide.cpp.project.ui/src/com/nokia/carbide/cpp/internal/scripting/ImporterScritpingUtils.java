package com.nokia.carbide.cpp.internal.scripting;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cpp.internal.api.sdk.BuildContextSBSv1;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildInfo;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildInfo;
import com.nokia.carbide.cpp.internal.project.utils.BldInfImportWrapper;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

public class ImporterScritpingUtils {

	/**
	 * Import a project from bld.inf
	 * 
	 * @param sdkName - The SDK to use.
	 * @param projectName - The project to import.
	 * @param projectPath - The path of the project to import.
	 * @changelog
	 * ARH 04/03/2009 Added an assert to reject a project path that is too long.
	 */
	public static IProject importINF(String projectName, String projectPath,
			List<ISymbianBuildContext> buildContexts, int rootPathIndex,
			boolean isSBSv2) {

		IProject project = null;

		IPath bldInf = new Path(projectPath);

		// AssertTestCase.assertFalse("The project: " + projectName +
		// " could not be imported from " +
		// projectPath + " because the path is " +
		// ProjectUtils.getProjectPathOverflowLength(bldInf) +
		// " too many character long.",
		// ProjectUtils.isProjectPathToLong(bldInf));

		List<IPath> projectRoots = EpocEngineHelper.getProjectRoots(bldInf,
				buildContexts, new NullProgressMonitor());
		IPath rootDirectoryPath = projectRoots.get(rootPathIndex);

		final List<String> components = new ArrayList<String>(); // should be
																	// empty
																	// list,
																	// same as
																	// all MMP
																	// components
																	// checked
		final List<String> refs = new ArrayList<String>(); // refs should be
															// empty, we'll
															// build from
															// bld.inf
		BldInfImportWrapper infWrapper = new BldInfImportWrapper(projectName,
				rootDirectoryPath, bldInf, components, refs, buildContexts);

		infWrapper.createProjectFromBldInf(isSBSv2);

		project = infWrapper.getProject();

		return project;
	}
	
	/**
	 * Validate that the project and SDK are compatible.  If the SDK and project are not on the
	 * same drive, then return false.  If they are on the same drive, then return true
	 * @param projectPath the path to the project to check
	 * @param sdk the SDK to check against the project
	 * @return true if projectPath is on the same drive as sdkName
	 */
	public static boolean checkProjectIsOnSDKDrive(String projectPath, ISymbianSDK sdk) {
		// The drive letter of the SDK
		String expectedDrive = sdk.getEPOCROOT().substring( 0, 1 );

		// The drive letter of the project
		String actualDrive = projectPath.substring( 0, 1 );
		
		// Check if drive letters are different of the SDK versus the project to import
		if ( expectedDrive.equalsIgnoreCase( actualDrive ) ) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Given an sdk and a comma deliminated list of build targets, return a List of ISymbianBuildContext
	 * An example build target string would be "WINSCW UDEB,GCCE UREL" and would return
	 * @param sdk
	 * @param buildTargets
	 * @return
	 */
	public static List<ISymbianBuildContext> getSymbianBuildContextsFromString(ISymbianSDK sdk, String buildTargets) {
		List<ISymbianBuildContext> selectedConfigs;

		// Create a selectConfigs list with all elements contained in buildTargets string
		if (buildTargets != null && buildTargets.length() > 0) {
			selectedConfigs = new ArrayList<ISymbianBuildContext>();
			StringTokenizer targetT = new StringTokenizer(buildTargets, ",");
			String buildTarget;
			String buildContext;
			while(targetT.hasMoreElements()) {
				// Get the element, separated by a comma, e.g. "GCCE UDEB".  Needs to be split further
				buildTarget = targetT.nextToken(); 
				// Within the element, get the context, which should be either "UDEB" or "UREL"
				buildContext = buildTarget.substring(buildTarget.indexOf(" ")+1);
				// Backfill with buildTarget with just the target
				buildTarget = buildTarget.substring(0, buildTarget.indexOf(" "));
				
				// Validate target and context strings against ISymbianBuildContext
//				AssertTestCase.assertTrue("!Invalid build target, should be ARMV5, GCCE, WINSCW, etc.. but was: " + buildTarget, validateBuildTarget(buildTarget));
//				AssertTestCase.assertTrue("!Invalid build context, should be UREL or UDEB, but was: " + buildContext, validateBuildContext(buildContext));
				BuildContextSBSv1 context = new BuildContextSBSv1(sdk, buildTarget, buildContext);
				selectedConfigs.add(context);		
			}
		}
		else {
			selectedConfigs = new ArrayList<ISymbianBuildContext>();
			ISBSv1BuildInfo sbsv1BuildInfo = (ISBSv1BuildInfo)sdk.getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER);
			ISBSv2BuildInfo sbsv2BuildInfo = (ISBSv2BuildInfo)sdk.getBuildInfo(ISymbianBuilderID.SBSV2_BUILDER);
			selectedConfigs.addAll(sbsv1BuildInfo.getFilteredBuildConfigurations());
			selectedConfigs.addAll(sbsv1BuildInfo.getFilteredBuildConfigurations());
		}
		return selectedConfigs;
	}
	
}
