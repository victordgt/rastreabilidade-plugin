package rastreabilidade.plugin.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.launching.JavaRuntime;

public class PluginUtil {
	
	

	public List<String> retiraCaminhoBiblioteca(String[] classpathEntries,
			List<String> arquivos) {

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
	
	public String[] recuperaClassPathsProjeto(IJavaProject javaProject) {
		String[] classPathEntries;
		try {
			classPathEntries = JavaRuntime
					.computeDefaultRuntimeClassPath(javaProject);
		} catch (CoreException e) {

			classPathEntries = null;
			e.printStackTrace();
		}
		return classPathEntries;
	}
	
	public List<URL> recuperaUrlsClasse(String[] classPathEntries) {
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
		return urlList;
	}	
	
	
	public URLClassLoader getProjectClassLoader(IProject project,
			List<URL> urlList) {
		ClassLoader parentClassLoader = project.getClass().getClassLoader();
		URL[] urls = (URL[]) urlList.toArray(new URL[urlList.size()]);
		URLClassLoader classLoader = new URLClassLoader(urls, parentClassLoader);

		return classLoader;
	}
	
	/**
	 * Procura arquivos compilados do projeto
	 * 
	 * @param directory
	 * @param classes
	 * @throws ClassNotFoundException
	 */
	public void findFiles(File directory, List<String> classes)
			throws ClassNotFoundException {

		if (directory.isDirectory()) {
			File[] files = directory.listFiles();

			for (File file : files) {
				if (file.isDirectory()) {
					findFiles(file, classes);
				} else if (file.getName().endsWith("class")) {
					classes.add(file.toString());
				}
			}
		} else if (directory.isFile()
				&& directory.getAbsolutePath().endsWith("class")) {
			classes.add(directory.toString());
		}

	}	

	
	

}
