package rastreabilidade.plugin.util;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import rastreabilidade.plugin.anotacao.AnotacaoUtil;

public class PluginUtil {

	/**
	 * Recupera as classes e interfaces de um projeto java do Eclipse
	 * 
	 * @param project
	 * @return Lista com um conjunto de classes, pacotes e interfaces de um projeto java
	 */
	public List<ICompilationUnit> recuperaRecursosProjeto(IJavaProject project) {
		
		List<ICompilationUnit> compilaveis = new ArrayList<ICompilationUnit>();
		
		try {
			
			IJavaElement[] elementos = project.getPackageFragments();
			
			for (IJavaElement elemento : elementos) {	
				percorreElementos(elemento, compilaveis);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return compilaveis;
        	                  
	}
	
	/*
	 * Percorre os elementos de um projeto Java do Eclipse
	 */
	private void percorreElementos(IJavaElement javaElement, List<ICompilationUnit> compilaveis) {
		
		if (javaElement instanceof IPackageFragment) {
			IPackageFragment fragmento = (IPackageFragment)javaElement;
			
			try {
				for (IJavaElement elemento : fragmento.getChildren())
				
				percorreElementos(elemento, compilaveis);
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		} else if (javaElement instanceof ICompilationUnit) {
			
			ICompilationUnit compilationUnit = ((ICompilationUnit) javaElement);
			compilaveis.add(compilationUnit);
			
		}
		
	}
	
	
	/**
	 * Recupera um conjunto de um tipo espec�fico de anota��o para uma lista de compil�veis
	 * 
	 * @param classe
	 * @param compilaveis
	 * @return Elementos de um projeto java com a anota��o espec�fica
	 */
	public List<IType> recuperaElementosComAnotacao(Class<? extends Annotation> classe, List<ICompilationUnit> compilaveis) {
		List<IType> classesComAnotacao = new ArrayList<IType>();
		for (ICompilationUnit compilationUnit : compilaveis) {
			IType type = compilationUnit.findPrimaryType();
		
			if (AnotacaoUtil.temAnotacaoClasse(classe, type)) {
				classesComAnotacao.add(type);
			}
		}
		
		return classesComAnotacao;
	}
	
	
	/**
	 * Carrega as propriedades referentes aos nomes dos artefatos dos projetos
	 * 
	 * @param project Projeto do eclipse
	 */
	public void carregaPropriedadesRastreabilidade(IProject project) {
		IFile arquivo = project.getFile("./rastreabilidade.properties");
		
		Properties defaultProps = new Properties(System.getProperties());
		try {
			defaultProps.load(arquivo.getContents());
		} catch (IOException e) {
			throw new IllegalArgumentException("Arquivo de propriedades do projeto ausente ou corrompido.");
		} catch (CoreException e) {
			e.printStackTrace();
		}				
		
	
		System.setProperties(defaultProps);
	}	
	
	/**
	 * Recupera os valores das chaves de elementos de rastreabilidade previamente carregados no projeto
	 * 
	 * @param chave
	 * @return Valor a ser apresentado de um elemento de rastreabilidade
	 */
	public String recuperaPropriedadeRastreabilidade(String chave) {
		String valor = System.getProperty(chave);
		
		if (valor == null) {
			String mensagem = String.format("A chave \'%s\' � inexistente.", chave);
			throw new IllegalArgumentException(mensagem);
		}
		
		return valor;
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
