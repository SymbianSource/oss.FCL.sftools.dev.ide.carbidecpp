echo off
echo Copy binaries to ..\..\..\os\win32\x86
copy /V %1\TCFProtOST.dll ..\..\..\os\win32\x86
copy /V %1\TCFProtOST.lib ..\..\..\os\win32\x86
copy /V %1\TCFProtOST.map ..\..\..\os\win32\x86
