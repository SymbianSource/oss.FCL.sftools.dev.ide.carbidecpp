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
				label="%standardDirFields.incDir.label"
		     	default="inc"
				mandatory="true"
				persist="true">
				<description>%standardDirFields.incDir.desc</description>
		</filenameField>
		<filenameField 
				id="sourceDir"
				label="%standardDirFields.sourceDir.label"
				default="src"
				mandatory="true"
				persist="true">
				<description>%standardDirFields.sourceDir.desc</description>
		</filenameField>
		<filenameField 
				id="groupDir"
				label="%standardDirFields.groupDir.label"
				default="group"
				mandatory="true"
				persist="true">
				<description>%standardDirFields.groupDir.desc</description>
		</filenameField>
		<filenameField 
				id="resDir"
				label="%standardDirFields.resDir.label"
				default="rsc"
				mandatory="true"
				persist="true">
				<description>%standardDirFields.resDir.desc</description>
		</filenameField>
	</wizardPage>

	<process class="com.nokia.carbide.cpp.project.ui.processes.CreateSourceFolders"
		 	bundle="com.nokia.carbide.cpp.project.ui">
		<parameter name="project" projectName="$(projectName)" />
		<parameter name="folder" path="$(sourceDir)" /> 
		<parameter name="folder" path="$(resDir)" forceUserInclude="true" /> 
		<parameter name="folder" path="$(incDir)" forceUserInclude="true" /> 
	</process>

	<process class="com.nokia.carbide.cpp.uiq.ui.viewwizard.SaveModelsProcessRunner"
				bundle="com.nokia.carbide.cpp.uiq.ui">
		<parameter name="project" projectName="$(projectName)" sourceGenRootModel="false" />
	</process>
	
	<process class="com.nokia.carbide.cpp.uiq.ui.viewwizard.DisposeModelsProcessRunner"
				bundle="com.nokia.carbide.cpp.uiq.ui">
		<parameter name="project" projectName="$(projectName)" />
	</process>
