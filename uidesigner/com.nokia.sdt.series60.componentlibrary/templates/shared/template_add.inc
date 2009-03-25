	<wizardPage 
		id="basics" 
		label="%basics.label">
		<description>%basics.desc</description>
		<textField 
				id="author"
				label="%basics.author.label"
				persist="true">
				<description>%basics.author.desc</description>
		</textField>
		<textField 
				id="copyright"
				label="%basics.copyright.label"
				multiline="true"
				default="Your copyright notice"
				persist="true">
				<description>%basics.copyright.desc</description>
		</textField>
	</wizardPage>

	<wizardPage 
		id="directories" 
		label="%directories.label">
		<description>%directories.desc</description>
		<filenameField 
				id="incDir"
				label="%directories.incDir.label"
		     	default="inc"
				mandatory="true"
				persist="true">
				<description>%directories.incDir.desc</description>
		</filenameField>
		<filenameField 
				id="sourceDir"
				label="%directories.sourceDir.label"
				default="src"
				mandatory="true"
				persist="true">
				<description>%directories.sourceDir.desc</description>
		</filenameField>
		<filenameField 
				id="groupDir"
				label="%directories.groupDir.label"
				default="group"
				mandatory="true"
				persist="true">
				<description>%directories.groupDir.desc</description>
		</filenameField>
		<filenameField 
				id="dataDir"
				label="%directories.dataDir.label"
				default="data"
				mandatory="true"
				persist="true">
				<description>%directories.dataDir.desc</description>
		</filenameField>
	</wizardPage>

	<process class="com.nokia.carbide.cpp.project.ui.processes.CreateSourceFolders"
		 	bundle="com.nokia.carbide.cpp.project.ui">
		<parameter name="project" projectName="$(projectName)" />
		<parameter name="folder" path="$(sourceDir)" /> 
		<parameter name="folder" path="$(dataDir)" forceUserInclude="true" /> 
		<parameter name="folder" path="$(incDir)" forceUserInclude="true" /> 
	</process>

	<process class="com.nokia.sdt.series60.viewwizard.SaveModelsProcessRunner"
				bundle="com.nokia.sdt.series60.componentlibrary">
		<parameter name="project" projectName="$(projectName)" sourceGenRootModel="false" />
	</process>
	
	<process class="com.nokia.sdt.series60.viewwizard.DisposeModelsProcessRunner"
				bundle="com.nokia.sdt.series60.componentlibrary">
		<parameter name="project" projectName="$(projectName)" />
	</process>
