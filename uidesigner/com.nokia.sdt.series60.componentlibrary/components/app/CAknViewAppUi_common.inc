			<template location="HEADER_FILE">
#ifndef ${instanceName.toUpperCase()}_H
#define ${instanceName.toUpperCase()}_H

			</template>
			
			<template location="HEADER_INCLUDES"><![CDATA[
#include <aknviewappui.h>
]]>			</template>

			<template location="FORWARD_DECLARATIONS"/>
			
			<defineLocation id="CLASS" baseLocation="HEADER_FILE"
				owned="false"
				location="class(${className})">
				<template><![CDATA[
/**
 * @class	${className} ${instanceName}.h
 * @brief The AppUi class handles application-wide aspects of the user interface, including
 *        view management and the default menu, control pane, and status pane.
 */
class ${className} : public CAknViewAppUi
	{
public: 
	// constructor and destructor
	${className}();
	virtual ~${className}();
	void ConstructL();

public:
	// from CCoeAppUi
	TKeyResponse HandleKeyEventL(
				const TKeyEvent& aKeyEvent,
				TEventCode aType );

	// from CEikAppUi
	void HandleCommandL( TInt aCommand );
	void HandleResourceChangeL( TInt aType );

	// from CAknAppUi
	void HandleViewDeactivation( 
			const TVwsViewId& aViewIdToBeDeactivated, 
			const TVwsViewId& aNewlyActivatedViewId );

private:
	void InitializeContainersL();
	};
]]>
				</template>
			</defineLocation>

			
			<template location="CLASS"/>
			
			<template location="CLASS_METHODS"/>
					
			<template location="CLASS_IVARS"/>

			<template location="HEADER_FILE">
#endif // ${instanceName.toUpperCase()}_H			
			</template>
			
			<!-- most locations provided by base component -->

			<template location="MAIN_FILE"/>
			<template location="MAIN_SYSTEM_INCLUDES"/>
			<template location="MAIN_USER_INCLUDES"/>
			<template location="CONSTANTS"/>
			<template location="CONSTRUCTOR"/>
			<template location="CONSTRUCTOR_BODY"/>
			<template location="DESTRUCTOR"/>
			<template location="DESTRUCTOR_BODY"/>
			<template location="INIT_CONTAINERSL"/>
			<template location="HANDLECOMMANDL_METHOD"/>
			<template location="HANDLECOMMANDL_BODY"/>
			<template location="HANDLERESOURCECHANGEL_METHOD"/>
			<template location="HANDLERESOURCECHANGEL_BODY"/>
			<template location="CLASS_USERHANDLERS"/>
			
			<defineLocation id="KeyEventOverride_Method" baseLocation="MAIN_FILE"
				owned="false"
				location="function(${className}::HandleKeyEventL(const TKeyEvent&amp;,TEventCode))">
				<template><![CDATA[
/** 
 * Override of the HandleKeyEventL virtual function
 * @return EKeyWasConsumed if event was handled, EKeyWasNotConsumed if not
 * @param aKeyEvent 
 * @param aType 
 */
TKeyResponse ${className}::HandleKeyEventL(
		const TKeyEvent& aKeyEvent,
		TEventCode aType )
	{
	// The inherited HandleKeyEventL is private and cannot be called
	}
]]>
				</template>
			</defineLocation>

			<template location="KeyEventOverride_Method"/>
			
			<defineLocation id="KeyEventOverride_Body" baseLocation="KeyEventOverride_Method"
				location="region(Generated Contents)">
			</defineLocation>
			
			<template location="KeyEventOverride_Body"/>

			<template location="KeyEventOverride_Method">
return EKeyWasNotConsumed;
			</template>
			
			<defineLocation id="HANDLE_VIEW_DEACTIVATION" baseLocation="MAIN_FILE"
				owned="false"
				location="function(${className}::HandleViewDeactivation(const TVwsViewId&amp;,const TVwsViewId&amp;))">
				<template><![CDATA[
/** 
 * Override of the HandleViewDeactivation virtual function
 *
 * @param aViewIdToBeDeactivated 
 * @param aNewlyActivatedViewId 
 */
void ${className}::HandleViewDeactivation( 
		const TVwsViewId& aViewIdToBeDeactivated, 
		const TVwsViewId& aNewlyActivatedViewId )
	{
	CAknViewAppUi::HandleViewDeactivation( 
			aViewIdToBeDeactivated, 
			aNewlyActivatedViewId );
	}
]]>
				</template>
			</defineLocation>

			<template location="HANDLE_VIEW_DEACTIVATION"/>
			
			<defineLocation id="HANDLE_VIEW_DEACTIVATION_BODY" baseLocation="HANDLE_VIEW_DEACTIVATION"
				location="region(Generated Contents)">
			</defineLocation>
			
			<template location="HANDLE_VIEW_DEACTIVATION_BODY"/>
			
<!-- BEGIN EVENT handleKeyEvent override of CAknAppUi -->
			<templateGroup id="GenerateHandleKeyEventHandlerViewAppUi" ifEvents="handleKeyEvent">
			
				<template id="HandlerDecl" phase="UserHandlers"><![CDATA[
TKeyResponse ${event.handlerName}( const TKeyEvent& aKeyEvent, TEventCode aType );
]]>				</template>
	
				<template id="DispatchToHandler" location="KeyEventOverride_Body"><![CDATA[
if ( ${event.handlerName}( aKeyEvent, aType ) == EKeyWasConsumed )
	{
	return EKeyWasConsumed;
	}
]]>				</template>
								
				<defineLocation id="KeyEventHandler" baseLocation="MAIN_FILE"
					owned="false"
					isEventHandler="true"
					location="function(${handlerClassName}::${event.handlerName}(const TKeyEvent&amp;, TEventCode))">
					<template><![CDATA[
/** 
 * Handle the ${event.eventName} event.
 * @return EKeyWasConsumed if event was handled, EKeyWasNotConsumed if not
 */
TKeyResponse ${handlerClassName}::${event.handlerName}( const TKeyEvent& /* aKeyEvent */, TEventCode /* aType */ )
	{
	// TODO: implement ${event.eventName} event handler
	return EKeyWasNotConsumed;
	}
]]>				
					</template>
				</defineLocation>
			
				<template id="RealizeHandler" location="KeyEventHandler"/>
			</templateGroup>
<!-- END EVENT handleKeyEvent  override of CAknAppUi-->

			<!-- our contributions -->
			
			<useTemplate ids="MakeConstructL GenerateHandleCommandBody"/>			
			<useTemplateGroup ids="GenerateHandleCommandEventHandler"/>
			<useTemplateGroup ids="GenerateForegroundEventHandler GenerateSwitchOnEventHandler"/>
			<useTemplateGroup ids="GenerateSystemEventHandler GenerateApplicationSpecificEventHandler"/>
			<useTemplateGroup ids="GenerateHandleStatusPaneSizeChangeHandler GenerateViewEventHandler"/>
			<useTemplateGroup ids="GenerateInitMenuPaneHandler GenerateOfferKeyToAppHandler"/>
			<useTemplateGroup ids="GenerateHandleResourceChangeHandler"/>
						
			<inline scope="prototype">
include("../containers/containerLibrary.js")
include("../cba/cbaLibrary.js")
			</inline>			
			
			<inline scope="prototype"><![CDATA[
${jsObject}.prototype.getViewContribs = function(contribs, instance) {
			// gather contributions from views
			for (i in instance.children) {
				var view = instance.children[i];
				if (view.isInstanceOf("com.nokia.sdt.series60.AvkonViewReference")) {
					var form = "RootAppUi";
					if (instance.properties.initialDesign == view.name) {
						form += "|RootAppUi_InitialView";
						if ((findNaviTabs(instance) != null) && view.properties.inTabGroup)
							form += "|RootAppUi_SetActiveTab";
					}
					var viewContribs = Engine.generateViewContributions(view.properties.filePath, form);
					if (viewContribs != null)
						contribs.addAll(viewContribs);
				}
			}
}
]]>			</inline>

			<inline><![CDATA[
			
			// Emit the view uids.  We expect the tab group to have emitted zero
			// or more of these already.
			for (i in instance.children) {
				var view = instance.children[i];
				if (view.isInstanceOf("com.nokia.sdt.series60.AvkonViewReference")) {
					//println("got view ref: " + view.properties.filePath);
					var theEnum = findOrCreateViewUidConstant(view);
				}
			}
			
			this.getHrhFiles(contribs);
			
			this.getChildContribs(contribs, form);
			
			this.getViewContribs(contribs, instance);
						
			Engine.assignLocationsForPhase(contribs, "HandleKeyEventL", "KeyEventOverride_Body");
			Engine.assignLocationsForPhase(contribs, "HandleViewDeactivation", "HANDLE_VIEW_DEACTIVATION_BODY");
			this.finalizeContribs(contribs);
]]>			</inline>
	
			<useTemplate ids="IncludeRsgFile" />
			
			<!-- contributions as child (to CAknDocument) -->
	
			<template phase="MainUserIncludes">
#include "${instanceName}.h"
			</template>

			<template phase="CreateAppUiL">
return new ( ELeave ) ${className};
			</template>
