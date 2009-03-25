package com.nokia.carbide.cpp.logging;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;

import com.nokia.carbide.cpp.ProductPlugin;

public class DiagnosticLogManager {

	final private static String LOG_ENABLEMENT_PREFS = "log_enablement";
	private static DiagnosticLogManager diagnosticLogManager;
	private ArrayList<DiagnosticLogGroup> logGroups = new ArrayList<DiagnosticLogGroup>();
	
	public DiagnosticLogManager() {
		super();
		
		IPath workspacePath = ResourcesPlugin.getWorkspace().getRoot().getLocation();
		
		IExtensionPoint point = Platform.getExtensionRegistry().getExtensionPoint("com.nokia.carbide.cpp", "diagnosticLogGroup");
		if (point != null) {
			IExtension[] exts = point.getExtensions();
			for (int i = 0; i < exts.length; i++) {
				IConfigurationElement[] elements = exts[i].getConfigurationElements();
				for (int j = 0; j < elements.length; j++) {
					logGroups.add(new DiagnosticLogGroup(elements[j].getAttribute("name"), elements[j].getAttribute("id")));
				}
			}
		}

		point = Platform.getExtensionRegistry().getExtensionPoint("com.nokia.carbide.cpp", "diagnosticLog");
		if (point != null) {
			IExtension[] exts = point.getExtensions();
			for (int i = 0; i < exts.length; i++) {
				IConfigurationElement[] elements = exts[i].getConfigurationElements();
				for (int j = 0; j < elements.length; j++) {
					String logGroupID = elements[j].getAttribute("group");
					for (DiagnosticLogGroup logGroup : logGroups) {
						if (logGroup.getId().equals(logGroupID)) {
							String fileName = elements[j].getAttribute("file_name");
							// configure file name
							logGroup.add(new DiagnosticLog(elements[j].getAttribute("name"), elements[j].getAttribute("id"), workspacePath.append(fileName).toOSString()));
						}
					}
				}
			}

		}
	}

	public static void loadLogSettings()
	{	
		String logPrefsRaw = ProductPlugin.getDefault().getPreferenceStore().getString(LOG_ENABLEMENT_PREFS);
		if (logPrefsRaw.length() > 0)
		{
			String[] logPrefsSplit = logPrefsRaw.split(":");
			for (int i = 0; i < logPrefsSplit.length; i++) {			
				DiagnosticLog log = getLog(logPrefsSplit[i]);
				Level logLevel = Level.parse(logPrefsSplit[++i]);
				if (log != null && logLevel != null)
				{
					log.getLogger().setLevel(logLevel);
				}
			}
		}	
	}
	
	public static void saveLogSettings()
	{
		StringBuffer logSettings = new StringBuffer();
		for (DiagnosticLogGroup logGroup : DiagnosticLogManager.getDiagnosticLogManager().getLogGroups()) {
			DiagnosticLog[] logs = logGroup.getLogs();
			for (int i = 0; i < logs.length; i++) {
				String logID = logs[i].getId();
				Logger logger = logs[i].getLogger();
				logSettings.append(logID);
				logSettings.append(':');
				logSettings.append(logger.getLevel().toString());
				logSettings.append(':');
			}
		}
		ProductPlugin.getDefault().getPreferenceStore().setValue(LOG_ENABLEMENT_PREFS, logSettings.toString());
		ProductPlugin.getDefault().savePluginPreferences();
	}
	
	public static DiagnosticLog getLog(String string) {
		for (DiagnosticLogGroup logGroup : DiagnosticLogManager.getDiagnosticLogManager().getLogGroups()) {
			DiagnosticLog[] logs = logGroup.getLogs();
			for (int i = 0; i < logs.length; i++) {
				if (logs[i].getId().equals(string))
						return logs[i];
			}
		}
		return null;
	}

	public static DiagnosticLogManager getDiagnosticLogManager() {
		if (diagnosticLogManager == null)
		{
			diagnosticLogManager = new DiagnosticLogManager();
			loadLogSettings();
		}
		return diagnosticLogManager;
	}

	public DiagnosticLogGroup[] getLogGroups() {
		return logGroups.toArray(new DiagnosticLogGroup[logGroups.size()]);
	}

}
