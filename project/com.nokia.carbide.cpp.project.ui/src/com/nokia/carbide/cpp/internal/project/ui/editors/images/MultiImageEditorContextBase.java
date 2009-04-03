/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of the License "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
* Contributors:
*
* Description: 
*
*/
package com.nokia.carbide.cpp.internal.project.ui.editors.images;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.ImageMakefileViewPathHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.image.*;
import com.nokia.carbide.cpp.epoc.engine.model.*;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.internal.project.ui.images.*;
import com.nokia.carbide.cpp.internal.project.ui.images.providers.ThumbnailImageLabelProvider;
import com.nokia.carbide.cpp.internal.ui.images.CachingImageLoader;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.ui.images.IImageLoader;
import com.nokia.carbide.cpp.ui.images.IImageModel;
import com.nokia.cpp.internal.api.utils.core.CacheMap;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.*;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;

import java.util.ArrayList;
import java.util.regex.Pattern;

public abstract class MultiImageEditorContextBase {
	private static final Pattern LEGAL_FILENAME_PATTERN = Pattern.compile("[A-Za-z0-9_]+"); //$NON-NLS-1$
	
	public static final String PREFS_PREFIX = "com.nokia.carbide.cpp.ui.images.editor."; //$NON-NLS-1$
	/** The view containing the resources to edit */
	protected IView view;
	/** The model owning the view */
	protected IModel model;
	/** The model provider providing the model */
	protected IModelProvider modelProvider;
	/** The owning project (or null) */
	protected IProject project;
	protected ICarbideProjectInfo carbideProjectInfo;
	protected ISymbianImageContainerModel containerModel;
	protected IDialogSettings dialogSettings;
	protected IAdaptable editorContext;
	protected DefaultOperationHistory operationHistory;
	protected IUndoContext undoContext;
	protected IUndoContext editorUndoContext;
	protected boolean isDirty;
	protected ArrayList<Listener> listeners;
	protected Composite parent;
	protected MultiImageListPage page;
	protected IOperationHistory editorHistory;
	protected ImageMakefileViewPathHelper imagePathHelper;
	private String targetFileName;

	final int THUMB_WIDTH = 64;
	final int THUMB_HEIGHT = 64;

	private CacheMap imageCache;

	private ThumbnailImageLabelProvider thumbnailImageLabelProvider;

	private CachingImageLoader imageLoader;

	public interface Listener {
			void changed(MultiImageEditorContextBase context);
		}

	/**
	 * 
	 */
	public MultiImageEditorContextBase() {
		super();
		
		listeners = new ArrayList<Listener>();
		editorContext = new IAdaptable() {
			
			public Object getAdapter(Class adapter) {
				return null;
			}
		};
		
		setupOperationHistory();

		this.undoContext = new IUndoContext() {

			public String getLabel() {
				return Messages.getString("MultiImageEditorContext.UndoContextLabel"); //$NON-NLS-1$
			}

			public boolean matches(IUndoContext context) {
				return context == this;
			}
			
		};
		
		if (ProjectUIPlugin.getDefault() != null) {
			dialogSettings = ProjectUIPlugin.getDefault().getDialogSettings();
		} else {
			dialogSettings = new DialogSettings(Messages.getString("MultiImageEditorContext.DialogSettingsSection")); //$NON-NLS-1$
		}
		this.imageCache = new CacheMap();
		
	}

	protected void commonInit() {
		/*
		 * Yay, we don't need this anymore, because the build tools work righr!
		if (convertedImageCache != null)
			convertedImageCache.dispose();
		convertedImageCache = new SymbianImageCache(Display.getDefault(), 
				isS60() ? new Series60ImageConverterFactory() : new SymbianImageConverterFactory());
		*/
		setupOperationHistory();
	}
	

	public void addListener(Listener listener) {
		if (!listeners.contains(listener))
			this.listeners.add(listener);
	}

	public void removeListener(Listener listener) {
		this.listeners.remove(listener);
	}

	/** Generic reaction to any change in the history. */
	protected void updateDirtyState() {
		for (Listener listener : listeners) {
			listener.changed(this);
		}
	}

	/**
	 * 
	 */
	protected void setupOperationHistory() {
		this.operationHistory = new DefaultOperationHistory();
		
		this.operationHistory.addOperationHistoryListener(new IOperationHistoryListener() {
			
			public void historyNotification(OperationHistoryEvent event) {
				if (event.getEventType() == OperationHistoryEvent.DONE
						|| event.getEventType() == OperationHistoryEvent.UNDONE
						|| event.getEventType() == OperationHistoryEvent.REDONE) {
					updateDirtyState();
				}
			}
			
		});

		
	}

	public IView getView() {
		return view;
	}
	
	/**
	 */
	public void dispose() {
		if (modelProvider != null) {
			if (view != null) {
				view.dispose();
				view = null;
			}
			if (model != null) {
				modelProvider.releaseSharedModel(model);
				model = null;
			}
		}
		if (imageCache != null)
			imageCache.dispose();
		if (thumbnailImageLabelProvider != null) {
			thumbnailImageLabelProvider.dispose();
			thumbnailImageLabelProvider = null;
		}
		if (imageLoader != null) {
			imageLoader.dispose();
			imageLoader = null;
		}
	}

	public boolean isDirty() {
		return isDirty;
	}

	/**
	 * Create a path from the given filename
	 * @param filename
	 * @return
	 */
	public IPath createPath(String filename) {
		if (project != null)
			return new Path(project.getName()).append(filename);
		else if (model != null)
			return model.getPath().removeLastSegments(1).append(filename);
		else
			return new Path(filename);
	}

	/**
	 * Get the base path against which IImageSource IPaths are resolved.
	 * @return
	 */
	public IPath getBaseImagePath() {
		if (project != null) {
			IPath location = CarbideBuilderPlugin.getProjectRoot(project);
			return location;
		} else if (model != null)
			return model.getPath().removeLastSegments(1);
		else
			return new Path(""); //$NON-NLS-1$
	
	}

	/**
	 * Get the project containing the source (mmp / makefile).
	 * @return project or null
	 */
	public IProject getProject() {
		return project;
	}

	/**
	 * Get the default build context.
	 * @return build context or null
	 */
	public ICarbideBuildConfiguration getBuildContext() {
		IProject project = getProject();
		if (project == null)
			return null;
		ICarbideProjectInfo info = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		return info.getDefaultConfiguration();
	}

	/**
	 * Find the real full path for an image, which, in a MIF,
	 * might be on an access path.
	 * @param path
	 * @return full path
	 */
	public IPath lookupRealImagePath(IPath path) {
		if (path == null)
			return null;
		
		if (imagePathHelper != null) {
			path = imagePathHelper.findCandidateImagePath(path);
			if (path.isAbsolute())
				return path;
		}
		if (path.isAbsolute())
			return path;
		else
			return getBaseImagePath().append(path);
	}

	/**
	 * Get a cached image.
	 * @param cache the cache to use (determines how it's converted)
	 * @param path mbm/mif-relative path to image or mask
	 * @param size desired size, or null for natural size
	 * @return
	 */
	/*
	public Image getImage(ImageCache cache, IPath path, Point size) {
		if (path != null && !path.isAbsolute()) {
			path = getBaseImagePath().append(path);
			path = lookupRealImagePath(path);
		}
		Image image = null;
		if (path != null) {
			image = cache.getImage(path, size);
		}
		if (image == null) {
			// cache the missing images scaled to desired size
			if (size != null) {
				image = (Image) cache.get(size);
				if (image == null) {
					image = ImageUtils.scaleImage(Display.getDefault(), missingImage, size);
					cache.put(size, image);
				}
			} else {
				image = missingImage;
			}
		}
		return image;
	}*/

	/**
	 * Push and execute the given operation, if it does anything.
	 * @param operation
	 */
	public void pushAndExecute(MultiImageEditorOperation operation) {
		if (!operation.doesAnything())
			return;
		
		try {
			//System.out.println("operation: " + operation); //$NON-NLS-1$
			operation.addContext(undoContext);
			operationHistory.execute(operation, new NullProgressMonitor(), editorContext);
		} catch (ExecutionException e) {
			ProjectUIPlugin.log(e);
		}
	}

	/**
	 * Get the cache of original images.
	 * @return
	 */
	/*
	public ImageCache getImageCache() {
		return originalImageCache;
	}*/

	/**
	 * Get the drive on which the sources live.
	 * @return
	 */
	public String getDrivePath() {
		if (project != null) {
			IPath path = project.getRawLocation();
			if (path == null)
				path = project.getLocation();
			return path.getDevice() + "\\"; //$NON-NLS-1$
		} else {
			return "c:\\"; //$NON-NLS-1$
		}
	}

	public IDialogSettings getDialogSettings() {
		return dialogSettings;
	}

	public void setHelpContext(Control control, String id) {
		if (Platform.isRunning()) {
			PlatformUI.getWorkbench().getHelpSystem().setHelp(control, id);
	
		}
	}

	/**
	 * 
	 */
	public void undo() {
		try {
			operationHistory.undo(undoContext, new NullProgressMonitor(), editorContext);
		} catch (ExecutionException e) {
			ProjectUIPlugin.log(e);
		}
	}

	/**
	 * 
	 */
	public void redo() {
		try {
			operationHistory.redo(undoContext, new NullProgressMonitor(), editorContext);
		} catch (ExecutionException e) {
			ProjectUIPlugin.log(e);
		}
	}

	/**
	 * @return
	 */
	public boolean canUndo() {
		return operationHistory.canUndo(undoContext);
	}

	/**
	 * @return
	 */
	public boolean canRedo() {
		return operationHistory.canRedo(undoContext);
	}

	/**
	 * Tell if the SDK for the project is EKA2 or not.
	 * @return
	 */
	public boolean isEKA2() {
		ISymbianBuildContext buildContext = getBuildContext();
		ISymbianSDK sdk = buildContext != null ? buildContext.getSDK() : null;
		if (sdk != null) {
			return sdk.isEKA2();
		}
		return false;
	}

	/**
	 * Tell if the SDK for the project is S60 or not.
	 * @return
	 */
	public boolean isS60() {
		ISymbianBuildContext buildContext = getBuildContext();
		ISymbianSDK sdk = buildContext != null ? buildContext.getSDK() : null;
		if (sdk != null) {
			return sdk.isS60();
		}
		return false;
		
	}

	/**
	 * @return
	 */
	public IImageResolver getImageResolver() {
		return new IImageResolver() {
			public IPath resolvePath(IPath path) {
				return lookupRealImagePath(path);
			}
		};
	}

	/**
	 * This is the file to which new files will be added
	 * @return
	 */
	public void setTargetFileName(String fileName) {
		this.targetFileName = fileName;
	}
	
	/**
	 * This is the file to which new files will be added
	 * @return
	 */
	public String getTargetFileName() {
		return targetFileName;
	}

	/**
	 * @return
	 */
	abstract public IBitmapSourceReference createBitmapSourceReference();

	/**
	 * @return
	 */
	abstract public ISVGSourceReference createSVGSourceReference();

	public boolean isValidPath(IPath path) {
		String filename = path.removeFileExtension().lastSegment();
		if (filename == null)
			return true;
		return LEGAL_FILENAME_PATTERN.matcher(filename).matches();
	}

	/**
	 * @return
	 */
	public ThumbnailImageLabelProvider getThumbnailImageLabelProvider() {
		if (thumbnailImageLabelProvider == null) {
			// cache images for any dialogs coming from this dialog,
			// but no longer
			thumbnailImageLabelProvider = new ThumbnailImageLabelProvider(
					getImageLoader(),
					new Point(THUMB_WIDTH, THUMB_HEIGHT)) {
				/* (non-Javadoc)
				 * @see com.nokia.carbide.cpp.internal.project.ui.images.providers.DelayedImageLoadingLabelProviderBase#resolvePath(org.eclipse.core.runtime.IPath)
				 */
				@Override
				protected IPath resolvePath(IPath elementPath) {
					return lookupRealImagePath(elementPath); 
				}
			};
		}
		// flush cached images so any changes are detected
		thumbnailImageLabelProvider.flush();
		return thumbnailImageLabelProvider;
	}

	/**
	 * Get a composed image from the given image source.
	 * The caller does not own this image and must copy it 
	 * for any long-term use.
	 * @param imageSourceReferenceModel the bitmap/mask or SVG to render
	 * @param imageFormat the format of the image
	 * @param size the size to scale to, or null for original size
	 * @param baseDir the base directory for resolving imageSource IPaths
	 * @return cached composed image
	 */
	public Image getComposedImage(IImageSourceReference source, ImageFormat format, Point size) throws CoreException {
		IImageModel model = wrapImageSourceReference(source, format);
		if (model != null) {
			//return cacheImage(model, size);
			return model.getImageDescriptor(size).createImage();
		}
		return null;
	}
	
	/**
	 * @return
	 */
	public ISymbianImageContainerModel getImageContainer() {
		return containerModel;
	}

	public IImageSourceReferenceModel wrapImageSourceReference(IImageSourceReference source, ImageFormat format) {
		if (imagePathHelper != null)
			return CarbideImageModelFactory.createImageSourceReferenceModel(getImageContainer(), source, format, imagePathHelper);
		else
			return CarbideImageModelFactory.createImageSourceReferenceModel(getImageContainer(), source, format);
	}
	public IImageSourceModel wrapImageSource(IImageSource source) {
		if (imagePathHelper != null)
			return CarbideImageModelFactory.createImageSourceModel(getImageContainer(), source, imagePathHelper);
		else
			return CarbideImageModelFactory.createImageSourceModel(getImageContainer(), source);
	}

	/**
	 * @return
	 */
	public IImageLoader getImageLoader() {
		if (imageLoader == null) {
			imageLoader = new CachingImageLoader();
		}
		return imageLoader;
	}


}