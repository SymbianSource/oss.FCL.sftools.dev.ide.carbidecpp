echo off
echo Copy binaries to ..\..\..\os\win32\x86
copy /V %1\TCFCommSerial.dll ..\..\..\os\win32\x86
copy /V %1\TCFCommSerial.lib ..\..\..\os\win32\x86
copy /V %1\TCFCommSerial.map ..\..\..\os\win32\x86
