/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.remoteconnections.tests.extensions;

import com.nokia.carbide.remoteconnections.interfaces.*;

import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Version;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

public class UnknownStatusService implements IService {

	private static final String _5_0 = "5.0";
	private static final String S60 = "S60";

	private final class UnknownStatusConnectedService implements IConnectedService {
		private boolean enabled = true;

		public void addStatusChangedListener(IStatusChangedListener listener) {
		}

		public void dispose() {
		}

		public IService getService() {
			return UnknownStatusService.this;
		}

		public IStatus getStatus() {
			return new IStatus() {

				public IConnectedService getConnectedService() {
					return UnknownStatusConnectedService.this;
				}

				public EStatus getEStatus() {
					return EStatus.UNKNOWN;
				}

				public String getLongDescription() {
					return getShortDescription();
				}

				public String getShortDescription() {
					return "Unknown";
				}
				
			};
		}

		public void removeStatusChangedListener(IStatusChangedListener listener) {
		}

		public void testStatus() {
		}

		public void setDeviceOS(String familyName, Version version) {
		}

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled  = enabled;
		}
	}

	public IConnectedService createInstance(IConnection connection) {
		return new UnknownStatusConnectedService();
	}

	public String getDisplayName() {
		return "Unknown Status Service";
	}

	public String getAdditionalServiceInfo() {
		return "No information about this service";
	}
	
	public String getIdentifier() {
		return getClass().getName();
	}

	public IRemoteAgentInstallerProvider getInstallerProvider() {
		return new IRemoteAgentInstallerProvider() {
			
			public List<String> getSDKFamilyNames(IRunnableContext runnableContext) {
				return Collections.singletonList(S60);
			}
			
			public List<Version> getVersions(String familyName) {
				if (familyName.equals(S60))
					return Collections.singletonList(new Version(_5_0));
				
				return null;
			}
			
			public List<IRemoteAgentInstaller> getRemoteAgentInstallers(String familyName, Version version) {
				if (familyName.equals(S60) && version.equals(new Version(_5_0))) {
					IRemoteAgentInstaller installer = new IRemoteAgentInstaller() {

						public boolean fileSupportsInstall() {
							return false;
						}

						public Image getImage() {
							return null;
						}

						public String getInformation() {
							return null;
						}

						public String getLabel() {
							return null;
						}

						public Version getInstallerVersion() {
							return null;
						}

						public IPackageContents getPackageContents(IRunnableContext runnableContext) {
							return new IPackageContents() {

								public String getDefaultNameFileName() {
									return null;
								}

								public InputStream getInputStream() {
									return new ByteArrayInputStream("Unknown".getBytes());
								}
								
							};
						}
					};
					return Collections.singletonList(installer);
				}
				return null;
			}

			public void dispose() {
			}

			public IService getService() {
				return UnknownStatusService.this;
			}
			
		};
	}

	public boolean isTestable() {
		return false;
	}

}
