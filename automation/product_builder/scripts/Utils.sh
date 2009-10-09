#!/bin/bash
#
# utilities used by build scripts
#
# To include these utilites, add these lines to another script in the same folder.
#
#   utilsDir=`dirname $0`
#   [ "$utilsDir" = "." ] && utilsDir=`pwd`
#   source $utilsDir/Utils.sh
#

program=$0


# ------------------------------------------------------------------
#
# prepare to parse command line args
#   usage: prepare_parse_args arg1 [arg2] ...
#
# To parse command line args, add these lines after source $utilsDir/Utils.sh
#
#   argsText="--ARG1:<varlue> --ARG2:<value>"
#   prepare_parse_args ARG1 ARG2 > /tmp/$$.parse_args
#   source /tmp/$$.parse_args
#   rm -f /tmp/$$.parse_args
#

function prepare_parse_args {
    local name
    local sedcmd='`echo $i | sed 's/[-a-zA-Z0-9_]*://'`'

    echo 'for i in $*'
    echo 'do'
    echo '  case $i in'

    for name in $*
    do
        echo "    --$name:*)"
        echo "      $name=$sedcmd"
        echo "      echo $name=\$$name"
		echo '    ;;'
    done

    echo '    --help)'
    echo '      usage $argsText'
    echo '    ;;'
    echo '    --verbose)'
    echo '      verbose=YES'
    echo '    ;;'
    echo '    *)'
    echo '    ;;'
  	echo 'esac'
    echo 'done'

}

this_step=0
total_errors=0
return_code=0

#
# count and print number of errors
#
function set_returncode {
    this_step=$(($this_step+1))
    if [ $1 -ne 0 ]; then
        total_errors=$(($total_errors+1))
    fi
    return_code=$1
    echo time = `date +%R`, step = $this_step, return code = $1, total errors = $total_errors
}

#
# print error message and exit
#
function error_exit {
    echo $program ERROR: $*
    echo BUILD FAILED
    set_returncode 1
    exit $total_errors
}

#
# print a command line
# run the command line
# remember the exit status
#
function run {
    echo $*
    $*
    set_returncode $?
}

#
# run and exit if non-zero return code
#
function must_run {
    echo $*
    $*
    return_code=$?
    set_returncode $return_code
    if [ $return_code -ne 0 ]; then
        exit $total_errors
    fi
}

#
# run the command line
# remember the exit status
#
function run_quiet {
    $*
    set_returncode $?
}

#
# show a usage message and exit
#
function usage {
    echo usage: $program $* --help --verbose
    set_returncode 1
    exit $total_errors
}

# ------------------------------------------------------------------
#
# move or copy plugins from one layout to another
# usage: move_or_copy_plugins <move_plugins | copy_plugins> [ -q] src_folder dst_folder map_file [map_file...]
#
function move_or_copy_plugins {
    if [ "$1" != "move_plugins" -a "$1" != "copy_plugins" ]; then
        echo 'usage: move_or_copy_plugins <move_plugins | copy_plugins> [ -q] src_folder dst_folder map_file [map_file...]'
        return 1
    fi

    local move_or_copy=$1
    shift

    local verbose=1
    if [ "$1" = "-q" ]; then
        verbose=0
        shift
    fi

    if [ $# -lt 3 ]; then
        echo usage: $move_or_copy [ -q] src_folder dst_folder map_file [map_file...]
        return 1
    fi

    local src_folder=$1
    shift
    local dst_folder=$1
    shift

    if [ ! -d $src_folder ]; then
        echo ERROR: $move_or_copy: cannot find $src_folder
        return 1
    fi

    [ -d $dst_folder/features ] || mkdir -p $dst_folder/features
    [ -d $dst_folder/plugins ] || mkdir -p $dst_folder/plugins

    local name
    local name2
    local have_x86_eol

    for name in $*
    do
        if [ ! -f $name ]; then
            echo ERROR: $move_or_copy: cannot find map file: $name
            return 1
        fi
    done

    for name in `cat $* | grep -v "^#" | grep , | cut -d"," -f2`
    do
        #echo $src_folder/${name} $src_folder/${name}_*
        # Except for x86_64 plugins, plugins follow the convention that they do not have underscores in the name
        # For example: org.eclipse.swt.win32.win32.x86_* matches
        #    org.eclipse.swt.win32.win32.x86_3.4.1.v3449c.jar
        #    org.eclipse.swt.win32.win32.x86_64.source_3.4.1.v3449c.jar
        #    org.eclipse.swt.win32.win32.x86_64_3.4.1.v3449c.jar
        # Check for x86_64 and handle the exception
        echo $name | grep x86$ > /dev/null 2>&1
        if [ $? -eq 0 ]; then
            have_x86_eol=1
        else
            have_x86_eol=0
        fi

        features_or_plugins=`echo $name | cut -d"/" -f1`

        # The x86 names may expand to more than one filename
        for name2 in $src_folder/${name}_*
        do
            if [ "$name2" = '$src_folder/${name}_*' ]; then
                # no match found
                [ $verbose -eq 1 ] && echo WARNING: $move_or_copy: cannot find $src_folder/${name}
            else
                echo $name2 | grep x86_64 > /dev/null 2>&1
                if [ $? -ne 0 -o $have_x86_eol -eq 0 ]; then
                    # do this if name2 does not have x86_64  OR  if pattern does end in x86
                    if [ "$move_or_copy" = "move_plugins" ]; then
                        run mv $name2 $dst_folder/$features_or_plugins
                    else
                        run cp -pr $name2 $dst_folder/$features_or_plugins
                    fi
                fi
            fi
        done
    done
}

#
# move plugins from one layout to another
# usage: move_plugins [ -q] src_folder dst_folder map_file [map_file...]
#
function move_plugins {
    move_or_copy_plugins move_plugins $*
}

#
# copy plugins from one layout to another
# usage: copy_plugins [ -q] src_folder dst_folder map_file [map_file...]
#
function copy_plugins {
    move_or_copy_plugins copy_plugins $*
}

# ------------------------------------------------------------------
#
# clone mercurial repository
# usage: hg_clone checkout_folder buildtags_file username password host revision repo [repo...]
#
function hg_clone {
    if [ $# -lt 7 ]; then
        echo usage: usage: hg_clone checkout_folder buildtags_file username password host revision repo [repo...]
        set_returncode 1
        return 1
    fi

    local checkout_folder=$1
    shift
    local buildtags_file=$1
    shift
    local username=$1
    shift
    local password=$1
    shift
    local host=$1
    shift
    local revision=$1
    shift

    if [ ! -d $checkout_folder ]; then
        echo ERROR: hg_clone: cannot find $checkout_folder
        set_returncode 1
        return 1
    fi

    local url=http://${username}:${password}@$host

    local name
    for name in $*
    do
        if [ ! -d $checkout_folder/$name ]; then
            run cd $checkout_folder
            run mkdir -p $name
            run rmdir $name
            echo hg clone http://$host/$name $name
            run_quiet hg clone $url/$name $name
        else
            run cd $checkout_folder/$name
            echo hg pull http://$host/$name
            run_quiet hg pull $url/$name
        fi
        run cd $checkout_folder/$name
        run hg update -C $revision
        echo http://$host/$name `hg identify` >> $buildtags_file
    done
}


# ------------------------------------------------------------------
#
# remove update site URLs from feature.xml
#

function remove_update_site_urls {
    local feature_xml=$1
    local temp_file=$2

    xml2oneline $feature_xml > $temp_file
    grep tools.ext.nokia.com/updates $temp_file > /dev/null
    if [ $? -eq 0 ]; then
        echo remove update site tools.ext.nokia.com/updates from $feature_xml
        grep -v tools.ext.nokia.com/updates $temp_file | grep -v \<url\> | grep -v \</url\> > $feature_xml
        unix2dos $feature_xml
    fi
    rm -f $temp_file
}
