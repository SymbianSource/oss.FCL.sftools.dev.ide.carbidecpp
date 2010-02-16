package com.nokia.cdt.internal.debug.launch.wizard;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

public class LaunchOptions {

	public IProject project;
	public String mode;
	public String configurationName;
	public boolean isEmulation; // aka isX86
	public boolean emulatorOnly; // aka useEmulationByDefault
	public IPath defaultExecutable;
	public List<IPath> exes;
	public List<IPath> mmps;

}