echo off
echo Copy binaries to ..\..\..\os\win32\x86
copy /V %1\TCFCommVirtualSerial.dll ..\..\..\os\win32\x86
copy /V %1\TCFCommVirtualSerial.lib ..\..\..\os\win32\x86
copy /V %1\TCFCommVirtualSerial.map ..\..\..\os\win32\x86
