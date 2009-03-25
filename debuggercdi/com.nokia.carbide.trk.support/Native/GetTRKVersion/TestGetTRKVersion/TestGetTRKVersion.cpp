// TestGetTRKVersion.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "GetTRKVersion.h"

int main(int argc, char* argv[])
{
	printf("Calling GetTRKVersion()\n");

	long version[3];
	DWORD error = GetTRKVersion("COM8", -1, 0, 0, 0, 0, version);
	
	if (error != 0)
		printf("Error = %d\n", error);
	else
		printf("Version ints = %d, %d, %d\n", version[0], version[1], version[2]);
	
	return 0;
}

