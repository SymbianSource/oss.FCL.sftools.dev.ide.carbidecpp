
TARGETPATH=\system\resources

RESOURCE:
	mifconv $(TARGETPATH)\Svgs.mif \
		/c32,8 ../aif/AppIcon.svg \
		/c8,8 ../group/qgn_note_ok.bmp \
		/c8 img.svg
