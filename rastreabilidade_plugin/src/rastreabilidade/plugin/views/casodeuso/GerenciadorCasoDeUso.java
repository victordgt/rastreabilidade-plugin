package rastreabilidade.plugin.views.casodeuso;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;

public class GerenciadorCasoDeUso {
	
	
	private Map<Annotation, List<Class<?>>> mapa = new HashMap<Annotation, List<Class<?>>>();

	public Map<Annotation, List<Class<?>>> getMapaClasses() {
		return mapa;
	}

	public void setMapaClasses(Map<Annotation, List<Class<?>>> mapaClasses) {
		this.mapa = mapaClasses;
	}

	public GerenciadorCasoDeUso() {

		List<IJavaProject> javaProjects = new ArrayList<IJavaProject>();
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();

		for (IProject project : projects) {
			try {
				project.open(null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
			IJavaProject javaProject = JavaCore.create(project);
			javaProjects.add(javaProject);

			String[] classPathEntries;
			try {
				classPathEntries = JavaRuntime
						.computeDefaultRuntimeClassPath(javaProject);
			} catch (CoreException e) {

				classPathEntries = null;
				e.printStackTrace();
			}

			List<URL> urlList = new ArrayList<URL>();
			if (classPathEntries != null) {
				for (int i = 0; i < classPathEntries.length; i++) {
					String entry = classPathEntries[i];
					IPath path = new Path(entry);
					URL url;
					try {
						url = path.toFile().toURI().toURL();
					} catch (MalformedURLException e) {
						url = null;
						e.printStackTrace();
					}
					urlList.add(url);
				}
			}

			URLClassLoader classLoader = getProjectClassLoader(project, urlList);

			
				
			try {
				
				for ( URL url: classLoader.getURLs()) {
					File arquivo = new File(url.getFile());
				
					List<String> arquivos = new ArrayList<String>();
					findFiles(arquivo, arquivos);
					
					List<String> classesClassPath = retiraCaminhoBiblioteca(classPathEntries, arquivos);
					constroiMapa(classesClassPath, classLoader);	
				}
	
			} catch (ClassNotFoundException e) {
			} 
			
		}
		
	}
	
	private List<String> retiraCaminhoBiblioteca(String[] classpathEntries, List<String> arquivos) {
		
		List<String> classes = new ArrayList<String>();
		for (String classpath : classpathEntries) {
			for (String arquivo : arquivos) {
				
				if (arquivo.indexOf(classpath) >= 0) {
					arquivo = arquivo.replaceAll(classpath, "");
					arquivo = arquivo.replaceAll("/", ".");
					if (arquivo.endsWith(".class")) {
						arquivo = arquivo.replaceAll(".class", "");
					} 
					
					if (arquivo.startsWith(".")) {
						arquivo = arquivo.substring(1, arquivo.length());
					}
					classes.add(arquivo);
				}
				
			}
		}
		
		return classes;
	}

	private URLClassLoader getProjectClassLoader(IProject project,
			List<URL> urlList) {
		ClassLoader parentClassLoader = project.getClass().getClassLoader();
		URL[] urls = (URL[]) urlList.toArray(new URL[urlList.size()]);
		URLClassLoader classLoader = new URLClassLoader(urls, parentClassLoader);

		return classLoader;
	}



	
	private void findFiles(File directory, List<String> classes)
			throws ClassNotFoundException {
		
		if (directory.isDirectory()) {		
			File[] files = directory.listFiles();
		
			for (File file : files) {
				if (file.isDirectory()) {
					findFiles(file, classes);
				} else  if (file.getName().endsWith("class")) {
					classes.add(file.toString());
				}
			} 
		} else if (directory.isFile() && directory.getAbsolutePath().endsWith("class")) {
			classes.add(directory.toString());
		}
		
	}
	
	private void  constroiMapa(List<String> classesClasspath, URLClassLoader classLoader) {
		
		for (String arquivo : classesClasspath) {
			try {
			
				Class<?> clazz = classLoader.loadClass(arquivo);
				
				if (CasoDeUsoUtil.temAnotacaoCasoDeUso(clazz)) {
					Annotation casoDeUso = CasoDeUsoUtil.getAnotacaoClasse(clazz);
					
					List<Class<?>> classes  = mapa.get(casoDeUso);
					if (classes == null) {
						classes = new ArrayList<Class<?>>();
					}
					
					classes.add(clazz);
					mapa.put(casoDeUso, classes);
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 
		}
	}
	
	public static void main(String[] args) {
		
		ThreadLocal.class.getClassLoader();
	}
	

}
