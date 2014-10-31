package fitedit.core;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class FiteditCore implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		FiteditCore.context = bundleContext;
	}

	public void stop(BundleContext bundleContext) throws Exception {
		FiteditCore.context = null;
	}

}
