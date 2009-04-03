<!-- BEGIN EVENT formatChanged -->
		<templateGroup id="GenerateFormatChangedEvent" ifEvents="EEventFormatChanged">
				
			<template id="FormatChangedEventCase" phase="InitializeControlHandlers">
<![CDATA[
	AddEdwinEventHandlerL( 
			${this.getInstanceMemberName(instance)}, 
			EEventFormatChanged, 
			&${className}::${event.handlerName} );
]]>
			</template>
			<expandMacro name="GenerateUserEventHandlerFunction"
					FunctionLocationId="FormatChangedHandler" 
					FunctionArgs="CEikEdwin* /*aEdwin*/, TEdwinEvent /*aEventType*/"
					DeclPhase="UserHandlers"
					DefnLocation="SOURCE_FILE" />

		</templateGroup>
<!-- END EVENT formatChanged -->

<!-- BEGIN EVENT Navigation -->
		<templateGroup id="GenerateNavigationEvent" ifEvents="EEventNavigation">
				
			<template id="FormatNavigationCase" phase="InitializeControlHandlers">
<![CDATA[
	AddEdwinEventHandlerL( 
			${this.getInstanceMemberName(instance)}, 
			EEventNavigation, 
			&${className}::${event.handlerName} );
]]>
			</template>
			<expandMacro name="GenerateUserEventHandlerFunction"
					FunctionLocationId="NavigationHandler" 
					FunctionArgs="CEikEdwin* /*aEdwin*/, TEdwinEvent /*aEventType*/"
					DeclPhase="UserHandlers"
					DefnLocation="SOURCE_FILE" />

		</templateGroup>
<!-- END EVENT Navigation -->

<!-- BEGIN EVENT PrepareForSelectionDelete -->
		<templateGroup id="GeneratePrepareForSelectionDeleteEvent" ifEvents="EEventPrepareForSelectionDelete">
				
			<template id="PrepareForSelectionDeleteCase" phase="InitializeControlHandlers">
<![CDATA[
	AddEdwinEventHandlerL( 
			${this.getInstanceMemberName(instance)}, 
			EEventPrepareForSelectionDelete, 
			&${className}::${event.handlerName} );
]]>
			</template>
			<expandMacro name="GenerateUserEventHandlerFunction"
					FunctionLocationId="PrepareForSelectionDeleteHandler" 
					FunctionArgs="CEikEdwin* /*aEdwin*/, TEdwinEvent /*aEventType*/"
					DeclPhase="UserHandlers"
					DefnLocation="SOURCE_FILE" />

		</templateGroup>
<!-- END EVENT PrepareForSelectionDelete -->







<!-- BEGIN EVENT EEventStateChanged -->

			<templateGroup id="GenerateStateChangedEvent" ifEvents="EEventStateChanged" form="EventGen">
				
			<template id="StateChangedEventCase" phase="InitializeControlHandlers">
<![CDATA[
	AddControlEventHandlerL( 
			${this.getInstanceMemberName(instance)}, 
			EEventStateChanged, 
			&${className}::${event.handlerName} );
]]>
			</template>
			<expandMacro name="GenerateUserEventHandlerFunction"
					FunctionLocationId="StateChangedHandler" 
					FunctionArgs="CCoeControl* /*aControl*/, TCoeEvent /*aEvent*/"
					DeclPhase="UserHandlers"
					DefnLocation="SOURCE_FILE" />

			</templateGroup>

<!-- END EVENT EEventStateChanged -->

<!-- BEGIN EVENT EEventRequestExit -->

			<templateGroup id="GenerateRequestExitEvent" ifEvents="EEventRequestExit" form="EventGen">

			<template id="RequestExitEventCase" phase="InitializeControlHandlers">
<![CDATA[
	AddControlEventHandlerL( 
			${this.getInstanceMemberName(instance)}, 
			EEventRequestExit, 
			&${className}::${event.handlerName} );
]]>

			</template>
			<expandMacro name="GenerateUserEventHandlerFunction"
					FunctionLocationId="RequestExitHandler" 
					FunctionArgs="CCoeControl* /*aControl*/, TCoeEvent /*aEvent*/"
					DeclPhase="UserHandlers"
					DefnLocation="SOURCE_FILE" />

			</templateGroup>

<!-- END EVENT EEventRequestExit -->

<!-- BEGIN EVENT EEventRequestCancel -->

			<templateGroup id="GenerateRequestCancelEvent" ifEvents="EEventRequestCancel" form="EventGen">

			<template id="RequestCancelEventCase" phase="InitializeControlHandlers">
<![CDATA[
	AddControlEventHandlerL( 
			${this.getInstanceMemberName(instance)}, 
			EEventRequestCancel, 
			&${className}::${event.handlerName} );
]]>
			</template>
			<expandMacro name="GenerateUserEventHandlerFunction"
					FunctionLocationId="RequestCancelHandler" 
					FunctionArgs="CCoeControl* /*aControl*/, TCoeEvent /*aEvent*/"
					DeclPhase="UserHandlers"
					DefnLocation="SOURCE_FILE" />

			</templateGroup>

<!-- END EVENT EEventRequestCancel -->

<!-- BEGIN EVENT EEventRequestFocus -->

			<templateGroup id="GenerateRequestFocusEvent" ifEvents="EEventRequestFocus" form="EventGen">

			<template id="RequestFocusEventCase" phase="InitializeControlHandlers">
<![CDATA[
	AddControlEventHandlerL( 
			${this.getInstanceMemberName(instance)}, 
			EEventRequestFocus, 
			&${className}::${event.handlerName} );
]]>
			</template>
		
			<expandMacro name="GenerateUserEventHandlerFunction"
					FunctionLocationId="RequestFocusHandler" 
					FunctionArgs="CCoeControl* /*aControl*/, TCoeEvent /*aEvent*/"
					DeclPhase="UserHandlers"
					DefnLocation="SOURCE_FILE" />

			</templateGroup>

<!-- END EVENT EEventRequestFocus -->

<!-- BEGIN EVENT EEventPrepareFocusTransition -->

			<templateGroup id="GeneratePrepareFocusTransitionEvent" ifEvents="EEventPrepareFocusTransition" form="EventGen">

			<template id="PrepareFocusTransitionEventCase" phase="InitializeControlHandlers">
<![CDATA[
	AddControlEventHandlerL( 
			${this.getInstanceMemberName(instance)}, 
			EEventPrepareFocusTransition, 
			&${className}::${event.handlerName} );
]]>
			</template>
			<expandMacro name="GenerateUserEventHandlerFunction"
					FunctionLocationId="PrepareFocusTransitionHandler" 
					FunctionArgs="CCoeControl* /*aControl*/, TCoeEvent /*aEvent*/"
					DeclPhase="UserHandlers"
					DefnLocation="SOURCE_FILE" />

			</templateGroup>

<!-- END EVENT EEventPrepareFocusTransition -->

<!-- BEGIN EVENT EEventInteractionRefused -->

			<templateGroup id="GenerateInteractionRefusedEvent" ifEvents="EEventInteractionRefused" form="EventGen">

			<template id="InteractionRefusedEventCase" phase="InitializeControlHandlers">
<![CDATA[
	AddControlEventHandlerL( 
			${this.getInstanceMemberName(instance)}, 
			EEventInteractionRefused, 
			&${className}::${event.handlerName} );
]]>
			</template>

			<expandMacro name="GenerateUserEventHandlerFunction"
					FunctionLocationId="InteractionRefusedHandler" 
					FunctionArgs="CCoeControl* /*aControl*/, TCoeEvent /*aEvent*/"
					DeclPhase="UserHandlers"
					DefnLocation="SOURCE_FILE" />

			</templateGroup>

<!-- END EVENT EEventInteractionRefused -->


