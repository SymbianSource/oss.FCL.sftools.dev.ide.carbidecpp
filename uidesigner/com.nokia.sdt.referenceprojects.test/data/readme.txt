This folder contains projects and reference source. 

For each project there should be a folder containing two
subfolders. 

- The subfolder named 'project' contains
the Carbide.c++ project as a subfolder. 

- The subfolder 'reference' contains
reference files in a parallel file hierarchy to the expected
generated files.

The driver will import the project, load the models and
generated source. Then it will traverse the reference folder
looking for matching files and comparing their contents. It will
generate failures if files are missing or don't match the
reference content.

NOTE: The UI designer projects are imported and built inside the
workspace, so they will be changed any time you run a test.  Be
sure not to commit changes under 'reference' unless they're intentional!
  