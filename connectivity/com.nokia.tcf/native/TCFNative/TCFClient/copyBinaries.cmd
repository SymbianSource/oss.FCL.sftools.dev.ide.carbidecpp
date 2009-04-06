echo off
echo Copy binaries to ..\..\..\os\win32\x86
copy /V %1\TCFClient.dll ..\..\..\os\win32\x86
copy /V %1\TCFClient.map ..\..\..\os\win32\x86
copy /V %1\TCFClient.lib ..\..\..\os\win32\x86
