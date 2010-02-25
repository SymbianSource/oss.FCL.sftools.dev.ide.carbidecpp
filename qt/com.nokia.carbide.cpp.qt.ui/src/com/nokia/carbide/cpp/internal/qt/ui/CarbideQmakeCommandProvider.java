package com.nokia.carbide.cpp.internal.qt.ui;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.trolltech.qtcppproject.qmake.IQtQmakeCommandProvider;

public class CarbideQmakeCommandProvider implements IQtQmakeCommandProvider {

	public static final String SYMBIAN_ABLD_SPEC = "symbian-abld"; 
	public static final String SYMBIAN_SBSV2_SPEC = "symbian-sbsv2"; 
	
	public List<String> getAdditionalArgs(IProject project) {
		
		// Do nothing....
		return null;
	}

	public String getProviderMkspec(IProject project) {
		if (CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(project)){
			return SYMBIAN_SBSV2_SPEC;
		} else if (CarbideBuilderPlugin.getBuildManager().isCarbideProject(project)) {
			return SYMBIAN_ABLD_SPEC;
		}
		
		// Project may just be being created so check the project session properties
		boolean sbsv2Project = false;
		Object property = null;
		try {
			property = project.getSessionProperty(CarbideBuilderPlugin.SBSV2_PROJECT);
			//project.setSessionProperty(key, value)
		} catch (CoreException e) {
			e.printStackTrace();
		}
		if (property != null && property instanceof Boolean) {
			sbsv2Project = ((Boolean)property).booleanValue();
		}
		
		if (sbsv2Project){
			return SYMBIAN_SBSV2_SPEC;
		} else {
			return SYMBIAN_ABLD_SPEC;
		}
		
	}

}
