echo off
echo Copy binaries to ..\..\..\os\win32\x86
copy /V %1\TCFCommTCP.dll ..\..\..\os\win32\x86
copy /V %1\TCFCommTCP.lib ..\..\..\os\win32\x86
copy /V %1\TCFCommTCP.map ..\..\..\os\win32\x86
