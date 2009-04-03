#
# Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
# All rights reserved.
# This component and the accompanying materials are made available
# under the terms of the License "Eclipse Public License v1.0"
# which accompanies this distribution, and is available
# at the URL "http://www.eclipse.org/legal/epl-v10.html".
#
# Initial Contributors:
# Nokia Corporation - initial contribution.
#
# Contributors:
# 
# Description: 
#

use File::Basename;
use File::Spec;
use Cwd;


if (@ARGV!=2)
{
	print <<USAGE_EOF;
Usage : perl fix_dep_file_paths.pl depfile platform

Update the paths in depfile to match those in the makefile

USAGE_EOF
	exit 1;
}

my $depfile = shift;
my $plat = shift;


if (!-e $depfile)
{
	print STDERR "ERROR: $depfile does not exist\n";
	exit 1;
}

if (-z $depfile)
{
	print STDERR "ERROR: $depfile is empty\n";
	exit 1;
}

if (!($plat eq "winscw" || $plat eq "armv5" || $plat eq "gcce" || $plat eq "gcc98"|| $plat eq "cpp"))
{
	print STDERR "ERROR: Unsupported platform argument $plat\nValid options are winscw, armv5, gcce, gcc98 and cpp\n";
	exit 1;
}

open (DEPFILE, "<$depfile") || die "ERROR: Unable to open file $depfile: error $!";
my @depfile_content = <DEPFILE>;
close DEPFILE;

if ($plat eq "winscw")
{

	# replace the first line with filepath.o : \
	my $filename = $depfile;
	$filename =~ s/\.(d|dep)$/.o/;
	$depfile_content[0] = "$filename : \\\n"

}
elsif ($plat eq "armv5")
{

	# no changes required

}
elsif ($plat eq "gcce")
{

	# just replace any / or \\ with \
    foreach my $line (@depfile_content) {
        $line =~ s/\//\\/g;
        $line =~ s/\\\\/\\/g;
    }
}
elsif ($plat eq "gcc98")
{
	
	# doctor first line - remove the object file since it's not the correct path
	my @strings = split(' ', $depfile_content[0]);
	shift @strings;
	$depfile_content[0] = join(' ', @strings);

	# change bld.inf directory relative paths to absolute
	my $cwd = getcwd();
	
    foreach my $line (@depfile_content) {
		my @paths = split(' ', $line);
		foreach my $path (@paths) {
			$path = File::Spec->rel2abs($path, $cwd);
 		}
		$line = join(' ', @paths);
		$line =~ s/\s+$//;
		$line .= "\n";
    }

	# insert filepath.o : \
	my $filename = $depfile;
	$filename =~ s/\.(d|dep)$/.o/;
	$depfile_content[0] = "$filename : \\\n" . $depfile_content[0];
	
}
elsif ($plat eq "cpp")
{

	# replace the first line with DEPEND$depfile = \
	my $depfilename = basename($depfile);
	$depfile_content[0] = "DEPEND$depfilename = \\\n";

}

open (DEPFILE, ">$depfile") || die "ERROR: Unable to save file $depfile: error $!";
print DEPFILE @depfile_content;
close DEPFILE;

exit 0;
