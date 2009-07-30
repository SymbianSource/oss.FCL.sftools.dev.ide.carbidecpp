#!/bin/bash

# ------------------------------------------------------------------

thisDir=`dirname $0`
[ "$thisDir" = "." ] && thisDir=`pwd`
source $thisDir/../scripts/Utils.sh

# ------------------------------------------------------------------
#
# parse command line args
#

argsText="WORKSPACE:<folder> --CARBIDE_VERSION_2DIGITS:<value> --CARBIDE_VERSION_3DIGITS:<value> [--BUILD_NUM:<value>] --HG_SFL_HOST:<value> --HG_EPL_HOST:<value> --HG_REVISION:<value> --HG_USERNAME:<value> --HG_PASSWORD:<value> --ATF_REVISION:<value> --BASE_CARBIDE:<value>"
prepare_parse_args WORKSPACE CARBIDE_VERSION_2DIGITS CARBIDE_VERSION_3DIGITS BUILD_NUM HG_SFL_HOST HG_EPL_HOST HG_REVISION HG_USERNAME HG_PASSWORD ATF_REVISION BASE_CARBIDE > /tmp/$$.parse_args
source /tmp/$$.parse_args
rm -f /tmp/$$.parse_args

# ------------------------------------------------------------------

# NOTE: BUILD_ID and BUILD_NUMBER are set by Hudson
if [ "$BUILD_NUM" = "" ]; then
    if [ "$BUILD_ID" = "" ]; then
        BUILD_NUM=v`date +%Y%m%d%H%M`
    else
        BUILD_NUM=v`echo $BUILD_ID | sed "s/[-_]//g" | cut -c1-12`
        BUILD_NUM=${BUILD_NUM}_$BUILD_NUMBER
    fi
fi
LONG_VERSION=$CARBIDE_VERSION_3DIGITS.$BUILD_NUM

# change paths to unix style so cp will work correctly
thisDir=`cygpath -m $thisDir`
WORKSPACE=`cygpath -m $WORKSPACE`
CHECKOUT_FOLDER=$WORKSPACE/co
BUILD_FOLDER=$WORKSPACE/bld
BASE_CARBIDE=`cygpath -m $BASE_CARBIDE`

echo thisDir=$thisDir
echo WORKSPACE=$WORKSPACE
echo CHECKOUT_FOLDER=$CHECKOUT_FOLDER
echo BUILD_FOLDER=$BUILD_FOLDER
echo LONG_VERSION=$LONG_VERSION
echo BASE_CARBIDE=$BASE_CARBIDE

# create files that are R/W by all
umask 0

DO_ADT_LAYOUT=yes
DO_PDT_LAYOUT=yes
DO_PDL_LAYOUT=yes
DO_PUBLIC_UPDATE_SITE=yes

# ------------------------------------------------------------------

if [ "yes" = "yes" ]; then
    if [ -d $BUILD_FOLDER ]; then
        run cmd /C rmdir /S /Q `cygpath -w $BUILD_FOLDER`
    fi
    run mkdir -p $BUILD_FOLDER/features
    run mkdir -p $BUILD_FOLDER/plugins
fi

# ------------------------------------------------------------------

run mkdir -p $CHECKOUT_FOLDER

# ------------------------------------------------------------------

if [ "yes" = "yes" ]; then
    run rm -f $CHECKOUT_FOLDER/buildtags.txt

    # dev/eclipseenv/buildlayout34 is too long; so, use a shorter path for this one
    hg_host=$HG_EPL_HOST/dev/eclipseenv
    hg_clone $CHECKOUT_FOLDER $CHECKOUT_FOLDER/buildtags.txt $HG_USERNAME $HG_PASSWORD $hg_host $HG_REVISION \
        buildlayout34

    hg_host=$HG_EPL_HOST
    hg_clone $CHECKOUT_FOLDER $CHECKOUT_FOLDER/buildtags.txt $HG_USERNAME $HG_PASSWORD $hg_host $HG_REVISION \
        dev/eclipseenv/eclipse \
        dev/ide/carbidecpp

    hg_host=$HG_SFL_HOST
    hg_clone $CHECKOUT_FOLDER $CHECKOUT_FOLDER/buildtags.txt $HG_USERNAME $HG_PASSWORD $hg_host $HG_REVISION \
        dev/ide/carbidecppplugins

    hg_host=$HG_SFL_HOST
    hg_clone $CHECKOUT_FOLDER $CHECKOUT_FOLDER/buildtags.txt $HG_USERNAME $HG_PASSWORD $hg_host default \
        ana/dynaanaapps  ana/staticanaapps  ana/staticanamdw \
        ana/compatanaapps  ana/testcreationandmgmt
fi

# ------------------------------------------------------------------

if [ "yes" = "yes" ]; then
    cd $BUILD_FOLDER
    cat $thisDir/map_carbide.txt $thisDir/map_trace.txt $thisDir/map_cdk.txt $thisDir/map_adt.txt $thisDir/map_pdt.txt | grep -v "^#" | grep , | sed "s;^;run cp -pr $CHECKOUT_FOLDER/;" | sed "s;,; ;" > /tmp/$$.a    
    source /tmp/$$.a
    rm -f /tmp/$$.a
fi

# ------------------------------------------------------------------

if [ "yes" = "yes" ]; then
    unix2dos $CHECKOUT_FOLDER/buildtags.txt
    run cp -p $CHECKOUT_FOLDER/buildtags.txt $BUILD_FOLDER/plugins/com.nokia.carbide.cpp/buildtags.txt
fi

# ------------------------------------------------------------------

if [ "yes" = "yes" ]; then
    run rm -rf $BUILD_FOLDER/buildlayout34
    run cp -pr $CHECKOUT_FOLDER/buildlayout34 $BUILD_FOLDER/buildlayout34
    run cp -r $BASE_CARBIDE/plugins/com.nokia.carbide.cpp.support_* $BUILD_FOLDER/buildlayout34/carbidecpp20devenv/plugins/
    run cp -r $BASE_CARBIDE/plugins/com.freescale.* $BUILD_FOLDER/buildlayout34/carbidecpp20devenv/plugins/
    run cp -r $BASE_CARBIDE/plugins/com.nokia.carbide.cpp.x86build* $BUILD_FOLDER/buildlayout34/carbidecpp20devenv/plugins/
    run cp -r $BASE_CARBIDE/configuration/qt $BUILD_FOLDER/buildlayout34/carbidecpp20devenv/configuration/
    run cp -r $BASE_CARBIDE/features/com.trolltech.qtcpp.feature_* $BUILD_FOLDER/buildlayout34/carbidecpp20devenv/features/
    run cp -r $BASE_CARBIDE/plugins/com.trolltech.* $BUILD_FOLDER/buildlayout34/carbidecpp20devenv/plugins/
    run cp -r $BASE_CARBIDE/features/org.eclipse.cdt.gnu.build_* $BUILD_FOLDER/buildlayout34/carbidecpp20devenv/features/
    run cp -r $BASE_CARBIDE/features/org.eclipse.cdt.gnu.debug_* $BUILD_FOLDER/buildlayout34/carbidecpp20devenv/features/
    run cp -r $BASE_CARBIDE/features/org.eclipse.cdt.platform_* $BUILD_FOLDER/buildlayout34/carbidecpp20devenv/features/
    run cp -r $BASE_CARBIDE/features/org.eclipse.cdt_* $BUILD_FOLDER/buildlayout34/carbidecpp20devenv/features/
    run cp -r $BASE_CARBIDE/plugins/org.eclipse.cdt_* $BUILD_FOLDER/buildlayout34/carbidecpp20devenv/plugins/
    run cp -r $BASE_CARBIDE/plugins/org.eclipse.cdt.* $BUILD_FOLDER/buildlayout34/carbidecpp20devenv/plugins/
fi

ECLIPSE_HOME=$BUILD_FOLDER/buildlayout34/carbidecpp20devenv
[ -d "$ECLIPSE_HOME" ] || error_exit cannot find folder $ECLIPSE_HOME
ECLIPSE_BUILD_SCRIPTS=`ls -d $ECLIPSE_HOME/plugins/org.eclipse.pde.build_*/scripts`
echo ECLIPSE_HOME=$ECLIPSE_HOME

# ------------------------------------------------------------------
#
# remove update site from all feature.xml files except com.nokia.carbide.cpp/feature.xml
#

if [ "yes" = "yes" ]; then
    run cd $BUILD_FOLDER
    for name in `ls features/*/feature.xml | grep -v com.nokia.carbide.cpp/feature.xml`
    do
        remove_update_site_urls $name $BUILD_FOLDER/temp
    done
fi

# ------------------------------------------------------------------

if [ "yes" = "yes" ]; then
    run rm -rf $BUILD_FOLDER/Carbide.c

    cat $thisDir/product.xml \
        | sed "s/\${CARBIDE_VERSION_2DIGITS}/${CARBIDE_VERSION_2DIGITS}/g" \
        | sed "s/\${CARBIDE_VERSION_3DIGITS}/${CARBIDE_VERSION_3DIGITS}/g" \
        > $BUILD_FOLDER/product.xml

    must_run $ECLIPSE_HOME/eclipsec.exe -nosplash --launcher.suppressErrors -application org.eclipse.ant.core.antRunner \
        -buildfile $ECLIPSE_BUILD_SCRIPTS/productBuild/productBuild.xml \
        -Dbuilder=$thisDir/config \
        -Dproduct=$BUILD_FOLDER/product.xml \
        -DbuildDirectory=$BUILD_FOLDER \
        -DbaseLocation=$ECLIPSE_HOME \
        -DforceContextQualifier=$BUILD_NUM \
        -DrepoDirectory=$BUILD_FOLDER/layout/repository \
        -DbuildNumber=$BUILD_NUM \
        -consoleLog
fi

# ------------------------------------------------------------------
#
# extract Carbide.c from the Eclipse PDE build
#

if [ "yes" = "yes" ]; then
    run rm -rf   $BUILD_FOLDER/parts_carbide
    run mkdir -p $BUILD_FOLDER/parts_carbide

	# unzip our product and the packaged eclipse stuff into the product layout
	run /bin/unzip -o $BUILD_FOLDER/Carbide.c/Carbide.c-win32.win32.x86.zip -d $BUILD_FOLDER/parts_carbide

	# remove the configuration directory and p2 plugins (ONLY DO THIS FOR NON_P2 builds)
	rm -rf $BUILD_FOLDER/parts_carbide/eclipse/configuration
    rm -rf $BUILD_FOLDER/parts_carbide/eclipse/features/org.eclipse.equinox.p2.*
    rm -rf $BUILD_FOLDER/parts_carbide/eclipse/plugins/org.eclipse.equinox.p2.*
    rm -rf $BUILD_FOLDER/parts_carbide/eclipse/plugins/org.eclipse.ecf*
    rm -rf $BUILD_FOLDER/parts_carbide/eclipse/plugins/org.eclipse.equinox.frameworkadmin*
    rm -rf $BUILD_FOLDER/parts_carbide/eclipse/plugins/org.sat4j.*
    rm -rf $BUILD_FOLDER/parts_carbide/eclipse/plugins/org.eclipse.equinox.simpleconfigurator.manipulator*
    rm -rf $BUILD_FOLDER/parts_carbide/eclipse/dropins
    rm -rf $BUILD_FOLDER/parts_carbide/eclipse/p2
fi

# ------------------------------------------------------------------
#
# remove <includes id="<feature>.source" /> from all feature.xml files
#

if [ "yes" = "yes" ]; then
    run cd $BUILD_FOLDER/parts_carbide/eclipse/features
    for name in */feature.xml
    do
        xml2oneline $name | grep "\<includes.id=.*.source\"" > /dev/null
        if [ $? -eq 0 ]; then
            xml2oneline $name > temp
            echo grep -v "\<includes.id=.*.source\"" $name
            grep -v "\<includes.id=.*.source\"" temp > $name
            unix2dos $name
        fi
    done
    rm -f temp
    run cd $BUILD_FOLDER
fi

# ------------------------------------------------------------------

if [ "yes" = "yes" ]; then
	# copy eclipsec.exe
	run cp $ECLIPSE_HOME/eclipsec.exe $BUILD_FOLDER/parts_carbide/eclipse

	# copy carbide.c++.ini to eclipse.ini (for eclipsec.exe)
    run cp -pr $BUILD_FOLDER/parts_carbide/eclipse/Carbide.c++.${CARBIDE_VERSION_2DIGITS}.ini $BUILD_FOLDER/parts_carbide/eclipse/eclipse.ini

	# manually copy over the vista and 64 bit fragments until we figure out how to include them as part of the product build.
	run cp -pr $ECLIPSE_HOME/plugins/org.eclipse.equinox.launcher.wpf.win32.x86* $BUILD_FOLDER/parts_carbide/eclipse/plugins
	run cp $ECLIPSE_HOME/plugins/org.eclipse.swt.wpf.win32.x86_* $BUILD_FOLDER/parts_carbide/eclipse/plugins
	run cp -pr $ECLIPSE_HOME/plugins/org.eclipse.equinox.launcher.win32.win32.x86_64* $BUILD_FOLDER/parts_carbide/eclipse/plugins
	run cp $ECLIPSE_HOME/plugins/org.eclipse.swt.win32.win32.x86_64_* $BUILD_FOLDER/parts_carbide/eclipse/plugins

    run mkdir -p $BUILD_FOLDER/parts_carbide/eclipse/jre
    run cp -r $BASE_CARBIDE/jre $BUILD_FOLDER/parts_carbide/eclipse/

	# copy the Qt feature/plugins into the product layout
    run mkdir -p $BUILD_FOLDER/parts_carbide/eclipse/configuration/qt
    run cp -r $BASE_CARBIDE/configuration/qt $BUILD_FOLDER/parts_carbide/eclipse/configuration/
    run cp -r $BASE_CARBIDE/features/com.trolltech.qtcpp.feature_* $BUILD_FOLDER/parts_carbide/eclipse/features
    run cp -r $BASE_CARBIDE/plugins/com.trolltech.* $BUILD_FOLDER/parts_carbide/eclipse/plugins

	# copy the Trace plugins into the product layout
    run cp -r $BASE_CARBIDE/plugins/com.nokia.tcf_*/os $BUILD_FOLDER/parts_carbide/eclipse/plugins/com.nokia.tcf_*

	# copy the TRK plugins into the product layout
    run cp -r $BASE_CARBIDE/plugins/com.nokia.carbide.trk.support_*/os $BUILD_FOLDER/parts_carbide/eclipse/plugins/com.nokia.carbide.trk.support_*

    run mkdir -p $BUILD_FOLDER/parts_carbide/eclipse/EULA
    run cp -p $CHECKOUT_FOLDER/dev/ide/carbidecpp/core/carbide_releases/EULA/carbide_eula.txt $BUILD_FOLDER/parts_carbide/eclipse/EULA/carbide_eula.txt

    run mkdir -p $BUILD_FOLDER/parts_carbide/eclipse/readme
    run cp -p $CHECKOUT_FOLDER/dev/ide/carbidecpp/core/carbide_releases/readme/readme_sdks.html $BUILD_FOLDER/parts_carbide/eclipse/readme/readme_sdks.html
    run cp -p $CHECKOUT_FOLDER/dev/ide/carbidecpp/core/carbide_releases/readme/background_carbide.jpg $BUILD_FOLDER/parts_carbide/eclipse/readme/background_carbide.jpg

    run mkdir -p $BUILD_FOLDER/parts_carbide/eclipse/x86Build
    run cp -r $BASE_CARBIDE/x86Build/env_switch $BUILD_FOLDER/parts_carbide/eclipse/x86Build/env_switch
    run cp -r $BASE_CARBIDE/x86Build/Release_Notes $BUILD_FOLDER/parts_carbide/eclipse/x86Build/Release_Notes
    run cp -r $BASE_CARBIDE/x86Build/Symbian_Support $BUILD_FOLDER/parts_carbide/eclipse/x86Build/Symbian_Support
    run cp -r $BASE_CARBIDE/x86Build/Symbian_Tools $BUILD_FOLDER/parts_carbide/eclipse/x86Build/Symbian_Tools

    run mkdir -p $BUILD_FOLDER/parts_carbide/eclipse/configuration
    run cp -p $CHECKOUT_FOLDER/dev/ide/carbidecpp/core/carbide_releases/configuration/server.properties $BUILD_FOLDER/parts_carbide/eclipse/configuration/server.properties
    run cp -p $CHECKOUT_FOLDER/dev/ide/carbidecpp/core/carbide_releases/configuration/run_env_update.bat $BUILD_FOLDER/parts_carbide/eclipse/configuration/run_env_update.bat
    run cp -p $BUILD_FOLDER/plugins/com.nokia.carbide.cpp/config.ini $BUILD_FOLDER/parts_carbide/eclipse/configuration/config.ini

    chmod -R 777 $BUILD_FOLDER/parts_carbide
fi

# ------------------------------------------------------------------

if [ "$DO_PUBLIC_UPDATE_SITE" = "yes" ]; then
	# run entire product layout through the p2 metadata generator to make the update site
	run $ECLIPSE_HOME/eclipsec.exe -nosplash --launcher.suppressErrors -application org.eclipse.equinox.p2.metadata.generator.EclipseGenerator \
		-source $BUILD_FOLDER/parts_carbide/eclipse \
		-metadataRepository file:$BUILD_FOLDER/parts_carbide/update_site -metadataRepositoryName "Carbide.c++ Metadata Repository" \
		-artifactRepository file:$BUILD_FOLDER/parts_carbide/update_site -artifactRepositoryName "Carbide.c++ Artifact Repository" \
		-compress -noDefaultIUs -publishArtifacts -root com.nokia.carbide.cpp.product -rootVersion ${CARBIDE_VERSION_3DIGITS}.$BUILD_NUM -consoleLog -vmargs -Xmx512m

    rm -rf $BUILD_FOLDER/parts_carbide/update_site/artifacts.jar
    rm -rf $BUILD_FOLDER/parts_carbide/update_site/content.jar
fi

# ------------------------------------------------------------------

if [ "yes" = "yes" ]; then
    run cd $BUILD_FOLDER
    run rm -rf $BUILD_FOLDER/parts_carbide_base
    run mv $BUILD_FOLDER/parts_carbide $BUILD_FOLDER/parts_carbide_base

    move_plugins $BUILD_FOLDER/parts_carbide_base/eclipse $BUILD_FOLDER/parts_carbide/eclipse $thisDir/map_adt.txt \
        $thisDir/map_cdk.txt $thisDir/map_pdt.txt 
    move_plugins $BUILD_FOLDER/parts_carbide_base/update_site $BUILD_FOLDER/parts_carbide/update_site $thisDir/map_adt.txt \
        $thisDir/map_cdk.txt $thisDir/map_pdt.txt
    
    run rm -rf $BUILD_FOLDER/parts_carbide_source
    run mkdir -p $BUILD_FOLDER/parts_carbide_source/eclipse/features
    run mkdir -p $BUILD_FOLDER/parts_carbide_source/eclipse/plugins
    run mkdir -p $BUILD_FOLDER/parts_carbide_source/update_site/features
    run mkdir -p $BUILD_FOLDER/parts_carbide_source/update_site/plugins
    run mv $BUILD_FOLDER/parts_carbide_base/eclipse/features/*.source_* $BUILD_FOLDER/parts_carbide_source/eclipse/features
    run mv $BUILD_FOLDER/parts_carbide_base/eclipse/plugins/*.source_* $BUILD_FOLDER/parts_carbide_source/eclipse/plugins
    if [ "$DO_PUBLIC_UPDATE_SITE" = "yes" ]; then
        run mv $BUILD_FOLDER/parts_carbide_base/update_site/features/*.source_* $BUILD_FOLDER/parts_carbide_source/update_site/features
        run mv $BUILD_FOLDER/parts_carbide_base/update_site/plugins/*.source_* $BUILD_FOLDER/parts_carbide_source/update_site/plugins
    fi
fi

# ------------------------------------------------------------------

if [ "yes" = "yes" ]; then
    run rm -rf   $BUILD_FOLDER/parts_eclispe_home
    run mkdir -p $BUILD_FOLDER/parts_eclispe_home/eclipse

    run cp -pr $CHECKOUT_FOLDER/buildlayout34/carbidecpp20devenv/features $BUILD_FOLDER/parts_eclispe_home/eclipse
    run cp -pr $CHECKOUT_FOLDER/buildlayout34/carbidecpp20devenv/plugins $BUILD_FOLDER/parts_eclispe_home/eclipse

	# remove the and p2 plugins (ONLY DO THIS FOR NON_P2 builds)
    rm -rf $BUILD_FOLDER/parts_eclispe_home/eclipse/features/org.eclipse.equinox.p2.*
    rm -rf $BUILD_FOLDER/parts_eclispe_home/eclipse/plugins/org.eclipse.equinox.p2.*
    rm -rf $BUILD_FOLDER/parts_eclispe_home/eclipse/plugins/org.eclipse.ecf*
    rm -rf $BUILD_FOLDER/parts_eclispe_home/eclipse/plugins/org.eclipse.equinox.frameworkadmin*
    rm -rf $BUILD_FOLDER/parts_eclispe_home/eclipse/plugins/org.sat4j.*
    rm -rf $BUILD_FOLDER/parts_eclispe_home/eclipse/plugins/org.eclipse.equinox.simpleconfigurator.manipulator*

	# run parts_eclispe_home layout through the p2 metadata generator to make the update site
    # only the features and plugins are required, all features and plugins are required to resolve dependencies
	run $ECLIPSE_HOME/eclipsec.exe -nosplash --launcher.suppressErrors -application org.eclipse.equinox.p2.metadata.generator.EclipseGenerator \
		-source $BUILD_FOLDER/parts_eclispe_home/eclipse \
		-metadataRepository file:$BUILD_FOLDER/parts_eclispe_home/update_site -metadataRepositoryName "Carbide.c++ Metadata Repository" \
		-artifactRepository file:$BUILD_FOLDER/parts_eclispe_home/update_site -artifactRepositoryName "Carbide.c++ Artifact Repository" \
		-compress -noDefaultIUs -publishArtifacts -root com.nokia.carbide.cpp.product -rootVersion ${CARBIDE_VERSION_3DIGITS}.$BUILD_NUM -consoleLog -vmargs -Xmx512m

    # bug 8732 - use org.junit4_4.3.1.jar from the Eclipse update site
    #run cp -p $CHECKOUT_FOLDER/internal/eclipseenv/buildlayout34/update_site/plugins/org.junit4_4.3.1.jar $BUILD_FOLDER/parts_eclispe_home/update_site/plugins

    rm -rf $BUILD_FOLDER/parts_eclispe_home/update_site/artifacts.jar
    rm -rf $BUILD_FOLDER/parts_eclispe_home/update_site/content.jar
    rm -rf $BUILD_FOLDER/parts_eclispe_home/update_site/binary
fi

# ------------------------------------------------------------------
#
# Now, we have the parts needed to make the layouts
#
# $BUILD_FOLDER/parts_carbide_base      features and plugins for all layouts
# $BUILD_FOLDER/parts_carbide_source    features and plugins for source only
# $BUILD_FOLDER/parts_carbide           features and plugins for multiple layouts, use map_adt.txt, map_cdk.txt, map_pdt.txt
# $BUILD_FOLDER/parts_eclispe_home      features and plugins for CDK, use map_dev_kit.txt

# ------------------------------------------------------------------

if [ "yes" = "yes" ]; then
    run rm -rf $BUILD_FOLDER/output_zips
    run mkdir $BUILD_FOLDER/output_zips
fi

# ------------------------------------------------------------------
#
# make the ADT layout
#

if [ "$DO_ADT_LAYOUT" = "yes" ]; then
    run rm -rf   $BUILD_FOLDER/layout_adt
    run mkdir -p $BUILD_FOLDER/layout_adt

    run cp -pr $BUILD_FOLDER/parts_carbide_base/eclipse $BUILD_FOLDER/layout_adt
    copy_plugins $BUILD_FOLDER/parts_carbide/eclipse    $BUILD_FOLDER/layout_adt/eclipse $thisDir/map_adt.txt

    run cd $BUILD_FOLDER/layout_adt/eclipse
    run /bin/zip -r $BUILD_FOLDER/output_zips/Carbide.c_ADT_layout_$LONG_VERSION.zip .eclipseproduct *
fi

# ------------------------------------------------------------------
#
# make the PDT layout
#

if [ "$DO_PDT_LAYOUT" = "yes" ]; then
    run rm -rf   $BUILD_FOLDER/layout_pdt
    run mkdir -p $BUILD_FOLDER/layout_pdt

    run cp -pr $BUILD_FOLDER/parts_carbide_base/eclipse $BUILD_FOLDER/layout_pdt
    copy_plugins $BUILD_FOLDER/parts_carbide/eclipse    $BUILD_FOLDER/layout_pdt/eclipse $thisDir/map_adt.txt $thisDir/map_pdt.txt

    run cd $BUILD_FOLDER/layout_pdt/eclipse
    run /bin/zip -r $BUILD_FOLDER/output_zips/Carbide.c_PDT_layout_$LONG_VERSION.zip .eclipseproduct *
fi

# ------------------------------------------------------------------
#
# make the PDL layout
#

if [ "$DO_PDL_LAYOUT" = "yes" ]; then
    run rm -rf   $BUILD_FOLDER/layout_pdl
    run mkdir -p $BUILD_FOLDER/layout_pdl

    # ADT
    run cp -pr $BUILD_FOLDER/parts_carbide_base/eclipse   $BUILD_FOLDER/layout_pdl
    copy_plugins $BUILD_FOLDER/parts_carbide/eclipse      $BUILD_FOLDER/layout_pdl/eclipse $thisDir/map_adt.txt

    # CDK
    run cp -pr $BUILD_FOLDER/parts_carbide_source/eclipse $BUILD_FOLDER/layout_pdl
    copy_plugins $BUILD_FOLDER/parts_carbide/eclipse      $BUILD_FOLDER/layout_pdl/eclipse $thisDir/map_cdk.txt
    copy_plugins $BUILD_FOLDER/parts_eclispe_home/eclipse $BUILD_FOLDER/layout_pdl/eclipse $thisDir/map_dev_kit.txt

    run cd $BUILD_FOLDER/layout_pdl/eclipse
    run /bin/zip -r $BUILD_FOLDER/output_zips/Carbide.c_PDL_layout_$LONG_VERSION.zip .eclipseproduct *
fi

# ------------------------------------------------------------------
#
# make the public update site
#

if [ "$DO_PUBLIC_UPDATE_SITE" = "yes" ]; then
    run rm -rf   $BUILD_FOLDER/layout_public
    run mkdir -p $BUILD_FOLDER/layout_public

    # ADT, PDT
    run cp -pr $BUILD_FOLDER/parts_carbide_base/update_site $BUILD_FOLDER/layout_public
    copy_plugins $BUILD_FOLDER/parts_carbide/update_site $BUILD_FOLDER/layout_public/update_site $thisDir/map_adt.txt $thisDir/map_pdt.txt

    # CDK
    run cp -pr $BUILD_FOLDER/parts_carbide_source/update_site $BUILD_FOLDER/layout_public
    copy_plugins $BUILD_FOLDER/parts_carbide/update_site $BUILD_FOLDER/layout_public/update_site $thisDir/map_cdk.txt
    copy_plugins $BUILD_FOLDER/parts_eclispe_home/update_site $BUILD_FOLDER/layout_public/update_site $thisDir/map_dev_kit.txt

    cat $CHECKOUT_FOLDER/dev/ide/carbidecpp/automation/product_builder/carbide.c/site.xml \
        | sed "s/\${QUALIFIER}/$BUILD_NUM/g" \
        | sed "s/\${CARBIDE_VERSION_2DIGITS}/${CARBIDE_VERSION_2DIGITS}/g" \
        | sed "s/\${CARBIDE_VERSION_3DIGITS}/${CARBIDE_VERSION_3DIGITS}/g" \
        > $BUILD_FOLDER/layout_public/update_site/site.xml

    run cd $BUILD_FOLDER/layout_public/update_site
    run /bin/zip -r $BUILD_FOLDER/output_zips/Carbide.c_update_site_$LONG_VERSION.zip site.xml features plugins
fi

# ------------------------------------------------------------------

cd $BUILD_FOLDER/output_zips

run cp -p $CHECKOUT_FOLDER/buildtags.txt buildtags.txt

cat $CHECKOUT_FOLDER/buildtags.txt >> map.txt
echo >> map.txt
cat $thisDir/map* | cut -d"," -f1 | sort | sed "s/ *//" | grep -v ^$ >> map.txt
unix2dos map.txt

ls > artifacts.txt
echo env:BUILD_NUMBER=$BUILD_NUMBER >> artifacts.txt
echo env:HOSTNAME=`hostname` >> artifacts.txt
echo env:HUDSON_URL=$HUDSON_URL >> artifacts.txt
echo env:JOB_NAME=$JOB_NAME >> artifacts.txt
echo env:PERMALINK=$HUDSON_URL/job/$JOB_NAME/$BUILD_NUMBER/ >> artifacts.txt
echo env:WORKSPACE=`cygpath -m $WORKSPACE` >> artifacts.txt
unix2dos artifacts.txt

# ------------------------------------------------------------------

echo exit $total_errors
exit $total_errors
