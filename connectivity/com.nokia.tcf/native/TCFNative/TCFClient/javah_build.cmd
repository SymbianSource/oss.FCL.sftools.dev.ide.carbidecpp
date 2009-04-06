REM echo off

REM create API 
attrib -R TCAPIConnectionJni.h
javah -classpath ..\..\..\..\com.nokia.tcf\bin -o TCAPIConnectionJni.h -jni com.nokia.tcf.impl.TCAPIConnection
