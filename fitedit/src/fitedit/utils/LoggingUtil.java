package fitedit.utils;

import org.eclipse.core.runtime.Status;

import fitedit.FitEditPlugin;

public class LoggingUtil {
	public static void log(int severity, String msg) {
		FitEditPlugin.getDefault().getLog()
				.log(new Status(severity, FitEditPlugin.PLUGIN_ID, msg));
	}
}
