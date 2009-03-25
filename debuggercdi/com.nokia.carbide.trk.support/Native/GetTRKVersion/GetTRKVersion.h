#include "stdafx.h"

__declspec(dllexport)
DWORD GetTRKVersion(const char* inPortName, int baudIndex, int dataBits, int parity, int stopBits, int flowControl, long version[3]);
