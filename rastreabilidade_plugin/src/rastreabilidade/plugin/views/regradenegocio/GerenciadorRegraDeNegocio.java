package rastreabilidade.plugin.views.regradenegocio;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

import rastreabilidade.CasoDeUso;
import rastreabilidade.RegraDeNegocio;
import rastreabilidade.plugin.anotacao.AnotacaoUtil;
import rastreabilidade.plugin.util.PluginUtil;

public class GerenciadorRegraDeNegocio {
	
	
	private Map<Annotation, List<Class<?>>> mapa = new HashMap<Annotation, List<Class<?>>>();

	public Map<Annotation, List<Class<?>>> getMapaClasses() {
		return mapa;
	}

	public void setMapaClasses(Map<Annotation, List<Class<?>>> mapaClasses) {
		this.mapa = mapaClasses;
	}

	public GerenciadorRegraDeNegocio() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		
		PluginUtil pluginUtil = new PluginUtil();

		for (IProject project : projects) {
			try {
				project.open(null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
			IJavaProject javaProject = JavaCore.create(project);

			String[] classPathEntries = pluginUtil.recuperaClassPathsProjeto(javaProject);

			List<URL> urlList = pluginUtil.recuperaUrlsClasse(classPathEntries);

			URLClassLoader classLoader = pluginUtil.getProjectClassLoader(project, urlList);
						
			constroiMapaUrls(classPathEntries, classLoader); 		
		}
		
	}

	private void constroiMapaUrls(String[] classPathEntries,
			URLClassLoader classLoader) {
		
		PluginUtil pluginUtil = new PluginUtil();
		try {
			
			for ( URL url: classLoader.getURLs()) {
				File arquivo = new File(url.getFile());
			
				List<String> arquivos = new ArrayList<String>();
				pluginUtil.findFiles(arquivo, arquivos);
				
				List<String> classesClassPath = pluginUtil.retiraCaminhoBiblioteca(classPathEntries, arquivos);
				constroiMapa(classesClassPath, classLoader);	
			}

		} catch (ClassNotFoundException e) {
		}
	}

	//TODO Listar os metodos com as regras de neg�cio
	private void  constroiMapa(List<String> classesClasspath, URLClassLoader classLoader) {
		for (String arquivo : classesClasspath) {
			try {
			
				Class<?> artefato = classLoader.loadClass(arquivo);
				
				if (AnotacaoUtil.temAnotacaoMetodo(RegraDeNegocio.class, artefato) || AnotacaoUtil.temAnotacaoClasse(RegraDeNegocio.class, artefato)) {
					Annotation regraDeNegocio = AnotacaoUtil.getAnotacaoClasse(RegraDeNegocio.class, artefato);
					
					List<Class<?>> classes  = mapa.get(regraDeNegocio);
					if (classes == null) {
						classes = new ArrayList<Class<?>>();
					}
					
					classes.add(artefato);
					mapa.put(regraDeNegocio, classes);
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 
		}
	}

}
