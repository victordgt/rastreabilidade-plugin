package rastreabilidade.plugin;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * A classe activator controla o ciclo de vida do plug-in
 */
public class Activator extends AbstractUIPlugin {

	//O identificador do plugin
	public static final String PLUGIN_ID = "rastreabilidade_plugin"; 

	// A instancia compartilhada
	private static Activator plugin;
	

	public Activator() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Retorna a inst�ncia compartilhada
	 *
	 * @return inst�ncia compartilhada
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Retorna um descritor de uma imagem para um arquivo de imagem oriundo do caminho relativo do plug-in
	 * 
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
