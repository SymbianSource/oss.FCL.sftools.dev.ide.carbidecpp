<!--START_USECASES: CU19 END_USECASES-->

		<!-- This maps the common fields of either GTXTED or RTXTED resources -->
		
		<mapSimpleMember property="width" member="width"/>
		<mapSimpleMember property="height" member="height"/>
		<mapSimpleMember property="numLines" member="numlines"/>
		<mapSimpleMember property="textLimit" member="textlimit"/>				

<!--		<mapBitmaskMember property="fontControlFlags" member="fontcontrolflags">
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