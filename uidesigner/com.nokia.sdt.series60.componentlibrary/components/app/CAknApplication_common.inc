			<!-- locations provided by component -->
			<defineLocation id="MAIN_FILE" domain="cpp" dir="${src}" 
				file="${instanceName}.cpp"
				owned="false"
				location="">
			</defineLocation>

			<defineLocation id="MAIN_SYSTEM_INCLUDES" baseLocation="MAIN_FILE"
				location="region(Generated System Includes)" 
				filter="unique-includes"/>

			<template location="MAIN_SYSTEM_INCLUDES"/>

			<defineLocation id="MAIN_INCLUDES" baseLocation="MAIN_FILE"
				location="region(Generated Includes)" 
				filter="unique-includes"/>

			<template location="MAIN_INCLUDES"><![CDATA[
#include "${instanceName}.h"
]]>			</template>		
			<template location="MAIN_INCLUDES"><![CDATA[
#include "${instance.findChildOfType("com.nokia.sdt.series60.CAknDocument").name}.h"
]]>			</template>		

			<defineLocation id="HEADER_FILE" domain="cpp" dir="${inc}" 
				file="${instanceName}.h"
				owned="false"
				location="">
			</defineLocation>
			<defineLocation id="HEADER_INCLUDES" baseLocation="HEADER_FILE"
				location="region(Generated Includes)"
				filter="unique-includes">
				<template><![CDATA[
#include <aknapp.h>
]]>				</template>				
			</defineLocation>
			<defineLocation id="HEADER_CONSTANTS" baseLocation="HEADER_FILE"
				location="region(Generated Constants)">
			</defineLocation>
			<defineLocation id="CLASS" baseLocation="HEADER_FILE"
				owned="false"
				location="class(${className})">
				<template ><![CDATA[
/**
 *
 * @class	${className} ${instanceName}.h
 * @brief	A CAknApplication-derived class is required by the S60 application 
 *          framework. It is subclassed to create the application's document 
 *          object.
 */
class ${className} : public CAknApplication
	{
private:
	TUid AppDllUid() const;
	CApaDocument* CreateDocumentL();
	
	};
]]>				</template>
			</defineLocation>
						
			<!-- contributions for this file -->
			

			<template location="MAIN_FILE"><![CDATA[
/**
 * @brief Returns the application's UID (override from CApaApplication::AppDllUid())
 * @return UID for this application (KUid${instanceName})
 */
TUid ${className}::AppDllUid() const
	{
	return KUid${instanceName};
	}

/**
 * @brief Creates the application's document (override from CApaApplication::CreateDocumentL())
 * @return Pointer to the created document object (${instance.findChildOfType("com.nokia.sdt.series60.CAknDocument").className})
 */
CApaDocument* ${className}::CreateDocumentL()
	{
	return ${instance.findChildOfType("com.nokia.sdt.series60.CAknDocument").className}::NewL( *this );
	}
]]>
				</template>				

		<template location="MAIN_INCLUDES"><![CDATA[
#ifdef EKA2
#include <eikstart.h>
#endif
]]>		</template>

			
			<template location="MAIN_FILE"><![CDATA[
#ifdef EKA2

/**
 *	@brief Called by the application framework to construct the application object
 *  @return The application (${className})
 */	
LOCAL_C CApaApplication* NewApplication()
	{
	return new ${className};
	}

/**
* @brief This standard export is the entry point for all Series 60 applications
* @return error code
 */	
GLDEF_C TInt E32Main()
	{
	return EikStart::RunApplication( NewApplication );
	}
	
#else 	// Series 60 2.x main DLL program code

/**
* @brief This standard export constructs the application object.
* @return The application (${className})
*/
EXPORT_C CApaApplication* NewApplication()
	{
	return new ${className};
	}

/**
* @brief This standard export is the entry point for all Series 60 applications
* @return error code
*/
GLDEF_C TInt E32Dll(TDllReason /*reason*/)
	{
	return KErrNone;
	}

#endif // EKA2
]]>			</template>

			<template mode="upgrade(1.0,1.1)" location="MAIN_FILE">
@@@ Change use of __SERIES60_3X__ to EKA2
- #ifdef __SERIES60_3X__
- /**
+ #ifdef EKA2
+ 
+ /**
			</template>

			<template mode="upgrade(1.0,1.1)" location="MAIN_FILE">
@@@ Change use of __SERIES60_3X__ to EKA2
  	return KErrNone;
  	}
  
- #endif // __SERIES60_3X__
+ #endif // EKA2
			</template>
			
			<template location="HEADER_FILE">
#ifndef ${instanceName.toUpperCase()}_H
#define ${instanceName.toUpperCase()}_H

			</template>
						
			<template location="HEADER_INCLUDES"/>

			<template location="HEADER_CONSTANTS"><![CDATA[
const TUid KUid${instanceName} = { ${properties.uid} };
]]>			</template>				
			
			<template location="CLASS"/>
			
			<template location="HEADER_FILE">
			
#endif // ${instanceName.toUpperCase()}_H		
			</template>
			
			<!-- child contributions -->						
			<inline>
			// iterate children
			contribs.addAll(Engine.generateChildContributions(form))
			
			// fix up the phases so they point to real locations
			Engine.assignLocationsForPhase(contribs, "MainIncludes", "MAIN_INCLUDES");
			Engine.assignLocationsForPhase(contribs, "HeaderIncludes", "HEADER_INCLUDES");
			</inline>
