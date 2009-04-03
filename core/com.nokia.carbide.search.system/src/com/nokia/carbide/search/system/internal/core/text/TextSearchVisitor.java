/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation, Nokia and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.nokia.carbide.search.system.internal.core.text;

import java.io.IOException;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.IDocument;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

import org.eclipse.search.ui.NewSearchUI;

import com.nokia.carbide.search.system.core.text.TextSearchMatchAccess;
import com.nokia.carbide.search.system.core.text.TextSearchRequestor;
import com.nokia.carbide.search.system.core.text.TextSearchScope;
import com.nokia.carbide.search.system.internal.ui.Messages;
import com.nokia.carbide.search.system.internal.ui.SearchMessages;
import com.nokia.carbide.search.system.internal.ui.SearchPlugin;


/**
 * The visitor that does the actual work.
 */
public class TextSearchVisitor {
	
	public static class ReusableMatchAccess extends TextSearchMatchAccess {
		
		private int fOffset;
		private int fLength;
		private IFileStore fFile;
		private CharSequence fContent;
		
		public void initialize(IFileStore file, int offset, int length, CharSequence content) {
			fFile= file;
			fOffset= offset;
			fLength= length;
			fContent= content;
		}
				
		public IFileStore getFile() {
			return fFile;
		}
		
		public int getMatchOffset() {
			return fOffset;
		}
		
		public int getMatchLength() {
			return fLength;
		}

		public int getFileContentLength() {
			return fContent.length();
		}

		public char getFileContentChar(int offset) {
			return fContent.charAt(offset);
		}

		public String getFileContent(int offset, int length) {
			return fContent.subSequence(offset, offset + length).toString(); // must pass a copy!
		}
	}
	

	private final TextSearchRequestor fCollector;
	private final Matcher fMatcher;
	
	private Map fDocumentsInEditors;
		
	private IProgressMonitor fProgressMonitor;

	private int fNumberOfScannedFiles;
	private int fNumberOfFilesToScan;
	private IFileStore fCurrentFile;

	private final MultiStatus fStatus;
	
	private final FileCharSequenceProvider fFileCharSequenceProvider;
	
	private final ReusableMatchAccess fMatchAccess;
	
	public TextSearchVisitor(TextSearchRequestor collector, Pattern searchPattern) {
		fCollector= collector;
		fStatus= new MultiStatus(NewSearchUI.PLUGIN_ID, IStatus.OK, SearchMessages.TextSearchEngine_statusMessage, null);
		
		fMatcher= searchPattern.pattern().length() == 0 ? null : searchPattern.matcher(new String());
		
		fFileCharSequenceProvider= new FileCharSequenceProvider();
		fMatchAccess= new ReusableMatchAccess();
	}
	
	public IStatus search(IFileStore[] files, IProgressMonitor monitor) {
		fProgressMonitor= monitor == null ? new NullProgressMonitor() : monitor;
        fNumberOfScannedFiles= 0;
        fNumberOfFilesToScan= files.length;
        fCurrentFile= null;
        
        if (fProgressMonitor.isCanceled())
			return Status.OK_STATUS;
        
        Job monitorUpdateJob= new Job(SearchMessages.TextSearchVisitor_progress_updating_job) {
        	private int fLastNumberOfScannedFiles= 0;
        	
        	public IStatus run(IProgressMonitor inner) {
        		while (!inner.isCanceled()) {
        			String fileName= null;

        			if (fCurrentFile != null)
        				fileName = fCurrentFile.getName();

        			if (fileName == null)
        				fileName = ""; //$NON-NLS-1$
        			
					Object[] args= { fileName, new Integer(fNumberOfScannedFiles), new Integer(fNumberOfFilesToScan)};
					fProgressMonitor.subTask(Messages.format(SearchMessages.TextSearchVisitor_scanning, args));
					int steps= fNumberOfScannedFiles - fLastNumberOfScannedFiles;
					fProgressMonitor.worked(steps);
					fLastNumberOfScannedFiles += steps;

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						return Status.OK_STATUS;
					}
				}
    			return Status.OK_STATUS;
        	}
        };

        try {
        	String taskName= fMatcher == null ? SearchMessages.TextSearchVisitor_filesearch_task_label :  Messages.format(SearchMessages.TextSearchVisitor_textsearch_task_label, fMatcher.pattern().pattern());
            fProgressMonitor.beginTask(taskName, fNumberOfFilesToScan);
            monitorUpdateJob.setSystem(true);
            monitorUpdateJob.schedule();
            try {
	            fCollector.beginReporting();
	            processFiles(files);
	            return fStatus;
            } finally {
                monitorUpdateJob.cancel();
            }
        } finally {
            fProgressMonitor.done();
            fCollector.endReporting();
        }
	}
		
	public IStatus search(TextSearchScope scope, IProgressMonitor monitor) {
		fProgressMonitor= monitor == null ? new NullProgressMonitor() : monitor;
    	String taskName= fMatcher == null ? SearchMessages.TextSearchVisitor_filesearch_task_label :  Messages.format(SearchMessages.TextSearchVisitor_textsearch_task_label, fMatcher.pattern().pattern());
        fProgressMonitor.beginTask(taskName, 0);
 		return search(scope.evaluateFilesInScope(fStatus, monitor), monitor);
    }
    
	private void processFiles(IFileStore[] files) {
		fDocumentsInEditors= evalNonFileBufferDocuments();
        for (int i= 0; i < files.length; i++) {
        	fCurrentFile= files[i];
            boolean res= processFile(fCurrentFile);
            if (!res)
            	break;
		}
		fDocumentsInEditors= null;
	}
	
	/**
	 * @return returns a map from IFile to IDocument for all open, dirty editors
	 */
	private Map evalNonFileBufferDocuments() {
		Map result= new HashMap();
		IWorkbench workbench= SearchPlugin.getDefault().getWorkbench();
		IWorkbenchWindow[] windows= workbench.getWorkbenchWindows();
		for (int i= 0; i < windows.length; i++) {
			IWorkbenchPage[] pages= windows[i].getPages();
			for (int x= 0; x < pages.length; x++) {
				IEditorReference[] editorRefs= pages[x].getEditorReferences();
				for (int z= 0; z < editorRefs.length; z++) {
					IEditorPart ep= editorRefs[z].getEditor(false);
					if (ep instanceof ITextEditor && ep.isDirty()) { // only dirty editors
						evaluateTextEditor(result, ep);
					}
				}
			}
		}
		return result;
	}

	private void evaluateTextEditor(Map result, IEditorPart ep) {
		IEditorInput input= ep.getEditorInput();
		if (input instanceof IFileEditorInput) {
			IFile file= ((IFileEditorInput) input).getFile();
			if (!result.containsKey(file)) { // take the first editor found
				ITextFileBufferManager bufferManager= FileBuffers.getTextFileBufferManager();
				ITextFileBuffer textFileBuffer= bufferManager.getTextFileBuffer(file.getFullPath(), LocationKind.IFILE);
				if (textFileBuffer != null) {
					// file buffer has precedence
					result.put(file, textFileBuffer.getDocument());
				} else {
					// use document provider
					IDocument document= ((ITextEditor) ep).getDocumentProvider().getDocument(input);
					if (document != null) {
						result.put(file, document);
					}
				}
			}
		} else if (input instanceof FileStoreEditorInput) {
			FileStoreEditorInput editorInput = (FileStoreEditorInput) input;
		}
	}

	public boolean processFile(IFileStore file) {
		try {
		    if (!fCollector.acceptFile(file) || fMatcher == null) {
			       return true;
			}
		        
			IDocument document= getOpenDocument(file);		
			
			if (document != null) {
				DocumentCharSequence documentCharSequence= new DocumentCharSequence(document);
				// assume all documents are non-binary
				locateMatches(file, documentCharSequence);
			} else {
				CharSequence seq= null;
				try {
					seq= fFileCharSequenceProvider.newCharSequence(file);
					if (hasBinaryContent(seq, file) && !fCollector.reportBinaryFile(file)) {
						return true;
					}
					locateMatches(file, seq);
				} catch (FileCharSequenceProvider.FileCharSequenceException e) {
					e.throwWrappedException();
				} finally {
					if (seq != null) {
						try {
							fFileCharSequenceProvider.releaseCharSequence(seq);
						} catch (IOException e) {
							SearchPlugin.log(e);
						}
					}
				}
			}
		} catch (UnsupportedCharsetException e) {
			String fileName;
			try {
				fileName = file.toLocalFile(EFS.NONE, null).getAbsolutePath();
			} catch (CoreException e1) {
				fileName = file.getName();
			}

			String[] args= { getCharSetName(file), fileName};
			String message= Messages.format(SearchMessages.TextSearchVisitor_unsupportedcharset, args); 
			fStatus.add(new Status(IStatus.ERROR, NewSearchUI.PLUGIN_ID, IStatus.ERROR, message, e));
		} catch (IllegalCharsetNameException e) {
			String fileName;
			try {
				fileName = file.toLocalFile(EFS.NONE, null).getAbsolutePath();
			} catch (CoreException e1) {
				fileName = file.getName();
			}
			String[] args= { getCharSetName(file), fileName};
			String message= Messages.format(SearchMessages.TextSearchVisitor_illegalcharset, args);
			fStatus.add(new Status(IStatus.ERROR, NewSearchUI.PLUGIN_ID, IStatus.ERROR, message, e));
		} catch (IOException e) {
			String fileName;
			try {
				fileName = file.toLocalFile(EFS.NONE, null).getAbsolutePath();
			} catch (CoreException e1) {
				fileName = file.getName();
			}
			String[] args= { getExceptionMessage(e), fileName};
			String message= Messages.format(SearchMessages.TextSearchVisitor_error, args); 
			fStatus.add(new Status(IStatus.ERROR, NewSearchUI.PLUGIN_ID, IStatus.ERROR, message, e));
		} catch (CoreException e) {
			String fileName;
			try {
				fileName = file.toLocalFile(EFS.NONE, null).getAbsolutePath();
			} catch (CoreException e1) {
				fileName = file.getName();
			}
			String[] args= { getExceptionMessage(e), fileName};
			String message= Messages.format(SearchMessages.TextSearchVisitor_error, args); 
			fStatus.add(new Status(IStatus.ERROR, NewSearchUI.PLUGIN_ID, IStatus.ERROR, message, e));
		} catch (StackOverflowError e) {
			String message= SearchMessages.TextSearchVisitor_patterntoocomplex0;
			fStatus.add(new Status(IStatus.ERROR, NewSearchUI.PLUGIN_ID, IStatus.ERROR, message, e));
			return false;
		} finally {
			fNumberOfScannedFiles++;
//			fProgressMonitor.subTask(fNumberOfScannedFiles + " files scanned");
		}
		if (fProgressMonitor.isCanceled())
			throw new OperationCanceledException(SearchMessages.TextSearchVisitor_canceled);
		
		return true;
	}
	
	private boolean hasBinaryContent(CharSequence seq, IFileStore file) throws CoreException {
//		IContentDescription desc= file.getContentDescription();
//		if (desc != null) {
//			IContentType contentType= desc.getContentType();
//			if (contentType != null && contentType.isKindOf(Platform.getContentTypeManager().getContentType(IContentTypeManager.CT_TEXT))) {
//				return false;
//			}
//		}
		
		// avoid calling seq.length() at it runs through the complete file,
		// thus it would do so for all binary files.
		try {
			int limit= FileCharSequenceProvider.BUFFER_SIZE;
			for (int i= 0; i < limit; i++) {
				if (seq.charAt(i) == '\0') {
					return true;
				}
			}
		} catch (IndexOutOfBoundsException e) {
		}
		return false;
	}

	private void locateMatches(IFileStore file, CharSequence searchInput) throws CoreException {
		try {
			fMatcher.reset(searchInput);
			int k= 0;
			while (fMatcher.find()) {
				int start= fMatcher.start();
				int end= fMatcher.end();
				if (end != start) { // don't report 0-length matches
					fMatchAccess.initialize(file, start, end - start, searchInput);
					boolean res= fCollector.acceptPatternMatch(fMatchAccess);
					if (!res) {
						return; // no further reporting requested
					}
				}
				if (k++ == 20) {
					if (fProgressMonitor.isCanceled()) {
						throw new OperationCanceledException(SearchMessages.TextSearchVisitor_canceled);
					}
					k= 0;
				}
			}
		} finally {
			fMatchAccess.initialize(null, 0, 0, new String()); // clear references
		}
	}
	
	
	private String getExceptionMessage(Exception e) {
		String message= e.getLocalizedMessage();
		if (message == null) {
			return e.getClass().getName();
		}
		return message;
	}

	private IDocument getOpenDocument(IFileStore file) {
		IDocument document= (IDocument) fDocumentsInEditors.get(file);
		if (document == null) {
			String pathString = null;
			try {
				pathString = file.toLocalFile(EFS.NONE, null).getAbsolutePath();
			} catch (CoreException e) {
				pathString = file.getName();
			}
			Path path = new Path(pathString);
			ITextFileBufferManager bufferManager= FileBuffers.getTextFileBufferManager();
			ITextFileBuffer textFileBuffer= bufferManager.getTextFileBuffer(path, LocationKind.IFILE);
			if (textFileBuffer != null) {
				document= textFileBuffer.getDocument();
			}
		}
		return document;
	}
	
	private String getCharSetName(IFileStore file) {
//		try {
//			return file.getCharset();
//		} catch (CoreException e) {
//			return "unknown"; //$NON-NLS-1$
//		}
		return "unknown"; //$NON-NLS-1$
	}

}

