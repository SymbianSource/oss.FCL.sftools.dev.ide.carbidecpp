		<!-- This maps the common fields of either GTXTED or RTXTED resources -->
		
		<mapSimpleMember property="numlines" member="numlines"/>
		<mapSimpleMember property="textlimit" member="textlimit"/>
		<mapBitmaskMember property="flags" member="flags">
			<mapBitmaskValue properties="EEikEdwinKeepDocument" value="EEikEdwinKeepDocument"/>
			<mapBitmaskValue properties="EEikEdwinSegmentedStorage" value="EEikEdwinSegmentedStorage"/>
			<mapBitmaskValue properties="EEikEdwinNoAutoSelection" value="EEikEdwinNoAutoSelection"/>
			<mapBitmaskValue properties="EEikEdwinJustAutoCurEnd" value="EEikEdwinJustAutoCurEnd"/> 
			<mapBitmaskValue properties="EEikEdwinNoWrap" value="EEikEdwinNoWrap"/>
			<mapBitmaskValue properties="EEikEdwinLineCursor" value="EEikEdwinLineCursor"/>
			<mapBitmaskValue properties="EEikEdwinNoHorizScrolling" value="EEikEdwinNoHorizScrolling"/>
			<mapBitmaskValue properties="EEikEdwinInclusiveSizeFixed" value="EEikEdwinInclusiveSizeFixed"/>
			<mapBitmaskValue properties="EEikEdwinDisplayOnly" value="EEikEdwinDisplayOnly"/>
			<mapBitmaskValue properties="EEikEdwinAlwaysShowSelection" value="EEikEdwinAlwaysShowSelection"/>
			<mapBitmaskValue properties="EEikEdwinReadOnly" value="EEikEdwinReadOnly"/>
			<mapBitmaskValue properties="EEikEdwinAllowPictures" value="EEikEdwinAllowPictures"/>
			<mapBitmaskValue properties="EEikEdwinAllowUndo" value="EEikEdwinAllowUndo"/>
			<mapBitmaskValue properties="EEikEdwinNoLineOrParaBreaks" value="EEikEdwinNoLineOrParaBreaks"/>
			<mapBitmaskValue properties="EEikEdwinOnlyASCIIChars" value="EEikEdwinOnlyASCIIChars"/>
			<mapBitmaskValue properties="EEikEdwinIgnoreVirtualCursor" value="EEikEdwinIgnoreVirtualCursor"/>
			<mapBitmaskValue properties="EEikEdwinAvkonDisableCursor" value="EEikEdwinAvkonDisableCursor"/>
			<!--
			<mapBitmaskValue properties="EEikEdwinAvkonNotEditable" value="EEikEdwinAvkonNotEditable"/>
			-->
			<mapBitmaskValue properties="EEikEdwinAlternativeWrapping" value="EEikEdwinAlternativeWrapping"/>
			<mapBitmaskValue properties="EEikEdwinResizable" value="EEikEdwinResizable"/>
		</mapBitmaskMember>		
	<!-- turned off because these are associated with an unavailable font selection dialog
		<mapBitmaskMember property="fontControlFlags" member="fontcontrolflags">
		# even though there is a bit for 'ALL' font controls, it is a separate bit, 
		# so we treat it as a separate value that can be used in conjunction with any others 
			<mapBitmaskValue properties="EGulFontControlAll" value="EGulFontControlAll"/>
			<mapBitmaskValue properties="EGulFontControlName" value="EGulFontControlName"/>
			<mapBitmaskValue properties="EGulFontControlSize" value="EGulFontControlSize"/>
			<mapBitmaskValue properties="EGulFontControlBold" value="EGulFontControlBold"/>
			<mapBitmaskValue properties="EGulFontControlItalic" value="EGulFontControlItalic"/>
			<mapBitmaskValue properties="EGulFontControlUnderline" value="EGulFontControlUnderline"/>
			<mapBitmaskValue properties="EGulFontControlStrikethrough" value="EGulFontControlStrikethrough"/>
			<mapBitmaskValue properties="EGulFontControlTextColor" value="EGulFontControlTextColor"/>
			<mapBitmaskValue properties="EGulFontControlPrintPos" value="EGulFontControlPrintPos"/>
		</mapBitmaskMember>		
		<mapBitmaskMember property="fontNameFlags" member="fontnameflags">
		# even though there is a bit for 'ALL' fonts, it is a separate bit, 
		# so we treat it as a separate value that can be used in conjunction with any others 
			<mapBitmaskValue properties="EGulAllFonts" value="EGulAllFonts"/>
			<mapBitmaskValue properties="EGulMonospaceFontsOnly" value="EGulMonospaceFontsOnly"/>
			<mapBitmaskValue properties="EGulNoMonospaceFonts" value="EGulNoMonospaceFonts"/>
			<mapBitmaskValue properties="EGulSymbolFontsOnly" value="EGulSymbolFontsOnly"/>
			<mapBitmaskValue properties="EGulNoSymbolFonts" value="EGulNoSymbolFonts"/>
			<mapBitmaskValue properties="EGulNoMonospaceOrSymbolFonts" value="EGulNoMonospaceOrSymbolFonts"/>
		</mapBitmaskMember>		
	-->
		<mapEnumMember property="defaultCase" member="default_case" validate="false"/>
		<mapBitmaskMember property="allowedCaseModes" member="allowed_case_modes">
			<mapBitmaskValue properties="EAknEditorUpperCase EAknEditorLowerCase EAknEditorTextCase" value="EAknEditorAllCaseModes"/>
			<mapBitmaskValue properties="EAknEditorUpperCase" value="EAknEditorUpperCase"/>
			<mapBitmaskValue properties="EAknEditorLowerCase" value="EAknEditorLowerCase"/>
			<mapBitmaskValue properties="EAknEditorTextCase" value="EAknEditorTextCase"/>
		</mapBitmaskMember>
		<!-- the default changes between 2.1 and 2.6 -->
		<mapEnumMember property="numericKeymap" member="numeric_keymap" suppressDefault="false" />
		<mapEnumMember property="defaultInputMode" member="default_input_mode" validate="false"/>
		<mapBitmaskMember property="allowedInputModes" member="allowed_input_modes">
			<!-- The value of EAknEditorAllInputModes changes between 2.1 and 2.6,
					So this can't be coded without selecting on the SDK - see uikon.hrh
			<mapBitmaskValue properties="EAknEditorTextInputMode EAknEditorNumericInputMode EAknEditorSecretAlphaInputMode EAknEditorKatakanaInputMode EAknEditorFullWidthTextInputMode EAknEditorFullWidthNumericInputMode EAknEditorFullWidthKatakanaInputMode EAknEditorHiraganaKanjiInputMode EAknEditorHalfWidthTextInputMode"
				value="EAknEditorAllInputModes"/>
			-->
			<mapBitmaskValue properties="EAknEditorTextInputMode" value="EAknEditorTextInputMode"/>
			<mapBitmaskValue properties="EAknEditorNumericInputMode" value="EAknEditorNumericInputMode"/>
			<mapBitmaskValue properties="EAknEditorSecretAlphaInputMode" value="EAknEditorSecretAlphaInputMode"/>
			<mapBitmaskValue properties="EAknEditorKatakanaInputMode" value="EAknEditorKatakanaInputMode"/>
			<mapBitmaskValue properties="EAknEditorFullWidthTextInputMode" value="EAknEditorFullWidthTextInputMode"/>
			<mapBitmaskValue properties="EAknEditorFullWidthNumericInputMode" value="EAknEditorFullWidthNumericInputMode"/>
			<mapBitmaskValue properties="EAknEditorFullWidthKatakanaInputMode" value="EAknEditorFullWidthKatakanaInputMode"/>
			<mapBitmaskValue properties="EAknEditorHiraganaKanjiInputMode" value="EAknEditorHiraganaKanjiInputMode"/>
			<mapBitmaskValue properties="EAknEditorHiraganaInputMode" value="EAknEditorHiraganaInputMode"/>
			<mapBitmaskValue properties="EAknEditorHalfWidthTextInputMode" value="EAknEditorHalfWidthTextInputMode"/>
		</mapBitmaskMember>
		<mapBitmaskMember property="avkonFlags" member="avkon_flags">
			<mapBitmaskValue properties="EAknEditorFlagFixedCase" value="EAknEditorFlagFixedCase"/>
			<mapBitmaskValue properties="EAknEditorFlagNoT9" value="EAknEditorFlagNoT9"/>
			<mapBitmaskValue properties="EAknEditorFlagNoEditIndicators" value="EAknEditorFlagNoEditIndicators"/>
			<mapBitmaskValue properties="EAknEditorFlagNoLRNavigation" value="EAknEditorFlagNoLRNavigation"/>
			<mapBitmaskValue properties="EAknEditorFlagSupressShiftMenu" value="EAknEditorFlagSupressShiftMenu"/>
			<mapBitmaskValue properties="EAknEditorFlagEnableScrollBars" value="EAknEditorFlagEnableScrollBars"/>
			<mapBitmaskValue properties="EAknEditorFlagMTAutoOverwrite" value="EAknEditorFlagMTAutoOverwrite"/>
			<mapBitmaskValue properties="EAknEditorFlagUseSCTNumericCharmap" value="EAknEditorFlagUseSCTNumericCharmap"/>
			<mapBitmaskValue properties="EAknEditorFlagLatinInputModesOnly" value="EAknEditorFlagLatinInputModesOnly"/>
			<mapBitmaskValue properties="EAknEditorFlagForceTransparentFepModes" value="EAknEditorFlagForceTransparentFepModes"/>
			<mapBitmaskValue properties="EAknEditorFlagAllowEntersWithScrollDown" value="EAknEditorFlagAllowEntersWithScrollDown"/>
			<mapBitmaskValue properties="EAknEditorFlagEnablePictographInput" value="EAknEditorFlagEnablePictographInput"/>
			<mapBitmaskValue properties="EAknEditorFlagFindPane" value="EAknEditorFlagFindPane"/>
		</mapBitmaskMember>
		<mapIdentifierMember property="specialCharacterTable" member="special_character_table"/>
		<mapSimpleMember property="maxViewHeightInLines" member="max_view_height_in_lines"/>
