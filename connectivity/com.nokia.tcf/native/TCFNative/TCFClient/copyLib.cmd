echo off
echo Copy binaries to ..\..\..\os\win32\x86
REM copy /V %1\TCFClient.dll ..\..\..\os\win32\x86 already done by project settings
copy /V %1\TCFClient.map ..\..\..\os\win32\x86
copy /V %1\TCFClient.lib ..\..\..\os\win32\x86
echo Copy binaries to ..\..\..\..\com.nokia.tcf\os\win32\x86
copy /V ..\..\..\os\win32\x86\TCFClient.dll ..\..\..\..\com.nokia.tcf\os\win32\x86
copy /V  ..\..\..\os\win32\x86\TCFClient.map ..\..\..\..\com.nokia.tcf\os\win32\x86
copy /V  ..\..\..\os\win32\x86\TCFClient.lib ..\..\..\..\com.nokia.tcf\os\win32\x86

