package app.tuxguitar.ui.qt;

import java.net.URL;

import app.tuxguitar.ui.UIApplication;
import app.tuxguitar.ui.UIFactory;
import app.tuxguitar.ui.appearance.UIAppearance;
import app.tuxguitar.ui.qt.appearance.QTAppearance;
import io.qt.core.QUrl;
import io.qt.gui.QDesktopServices;

public class QTApplication extends QTComponent<QTApplicationHandle> implements UIApplication {

	private Thread uiThread;
	private UIFactory uiFactory;
	private UIAppearance uiAppearance;
	private QTEnvironment environment;

	public QTApplication(String name) {
		super(new QTApplicationHandle());

		this.environment = new QTEnvironment();
		this.uiFactory = new QTFactory();
		this.uiAppearance = new QTAppearance();

		this.getControl().setApplicationName(name);
	}

	public void dispose() {
		this.getControl().quit();

		super.dispose();
	}

	public UIFactory getFactory() {
		return this.uiFactory;
	}

	public UIAppearance getAppearance() {
		return this.uiAppearance;
	}

	public void openUrl(URL url) {
		QDesktopServices.openUrl(new QUrl(url.toExternalForm()));
	}

	public void runInUiThread(Runnable runnable) {
		this.getControl().invokeLater(runnable);
	}

	public boolean isInUiThread() {
		return (this.uiThread == Thread.currentThread());
	}

	public void start(Runnable runnable) {
		this.uiThread = Thread.currentThread();
		this.getControl().initialize();

		String qtStyle = this.environment.findStyle();
		if( qtStyle != null && qtStyle.length() > 0 ) {
			this.getControl().setStyle(qtStyle);
		}

		this.runInUiThread(runnable);

		this.getControl().exec();
	}
}
