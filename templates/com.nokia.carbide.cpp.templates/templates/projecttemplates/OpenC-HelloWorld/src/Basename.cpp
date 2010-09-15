/*
============================================================================
 Name        : $(baseName).cpp
 Author      : $(author)
 Copyright   : $(copyright)
 Description : Main application class
============================================================================
*/

// INCLUDE FILES
#include <stdio.h>

// This is a GCCE toolchain workaround needed when compiling with GCCE
// and using main() entry point
#ifdef __GCCE__
#include <staticlibinit_gcce.h>
#endif

int main(void)
{
	printf("Hello ANSI C!\n");
	printf("Press a character to exit!");
	int c = getchar();
	return 0;
}
