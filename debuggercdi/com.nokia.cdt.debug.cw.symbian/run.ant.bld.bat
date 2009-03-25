rem batch file to run the ant build for this plugin. this is just mean as a way to locally trouble shoot build problems, not a script to be run for autobuilds
rem com.nokia.cdt.debug.cw.symbian>perl "%ANT_HOME/bin/runant.pl" -Declipse.home=C:\eclipse -Dws=win32 -Dos=win32 -Darch=x86 -Dnl=en_US -buildfile D:/Products/Carbide/CWEclipse/eclipse/workspace/com.freescale.cdt.debug.cw.core/build.xml zip.plugin
perl "%ANT_HOME%\bin\runant.pl" -Declipse.home=C:\eclipse -Dws=win32 -Dos=win32 -Darch=x86 -Dnl=en_US -buildfile build.xml zip.plugin
