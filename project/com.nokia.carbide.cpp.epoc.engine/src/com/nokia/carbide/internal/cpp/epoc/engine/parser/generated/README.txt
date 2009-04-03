
Do you need to modify the grammar?  For MMP, note that it is extensible through
the IMMPConfiguration interface used when creating a view.

The parser classes and constants files are generated from ASTParserCore.jj, etc.  
Make grammar and action changes to this file, then run JavaCC on the file.

Check out /n/sedona/com.nokia.sdt.internal.javacc to get the builder,
and import the External Tool launcher from "JavaCC Builder.launch" in
that directory.

Run the external tool launcher (Run / External Tools / JavaCC Builder)
with one of these *.jj files selected to rebuild.
