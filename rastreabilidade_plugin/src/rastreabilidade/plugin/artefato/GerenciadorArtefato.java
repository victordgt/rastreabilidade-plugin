package rastreabilidade.plugin.artefato;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMemberValuePair;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import rastreabilidade.plugin.anotacao.AnotacaoUtil;
import rastreabilidade.plugin.util.PluginUtil;

/**
 * Classe respons�vel pela associa��o entre artefatos de requisitos e classes Java
 * 
 * @author Victor
 */
public abstract class GerenciadorArtefato {
	
	/** Classe da anota��o */
	protected Class<? extends Annotation> clazz;
	
	/** Mapa com as chaves dos elementos e uma lista de classes associadas*/
	protected Map<String, List<IType>> mapaArtefatos = new HashMap<String, List<IType>>();
	
	/** Campo a ser buscado na anota��o para a recupera��o do valor associado*/
	protected String campo;
	
	/** Shell associado a view do Eclipse, para possibilitar a exibi��o de mensagens de erro*/
	protected Shell shell;
	
	protected GerenciadorArtefato(Class<? extends Annotation> clazz, String campo, Shell shell) {
		this.clazz = clazz;
		this.campo = campo;
		this.shell = shell;
	}
	
	
	public Map<String, List<IType>> constroiMapa() {
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
			
			//Verifica se o projeto est� aberto para recuperar as informa��es associadas ao projeto
			if (javaProject.isOpen()) {
				//Carrega para a mem�ria as propriedades de rastreabilidade do projeto java
				try {
					pluginUtil.carregaPropriedadesRastreabilidade(project);
				} catch (IllegalArgumentException ex) {					
					MessageDialog.openError(shell, "Erro", ex.getMessage());
				}
				
	
				List<ICompilationUnit> compilaveis = pluginUtil.recuperaRecursosProjeto(javaProject);
				List<IType> classesComAnotacao = pluginUtil.recuperaElementosComAnotacao(clazz, compilaveis);
	
				filtrar(classesComAnotacao);
			}
		}		
		
		return mapaArtefatos;
	}

	
	/**
	 * FILTRA POR PADR�O UMA ANNOTATION COM UM CAMPO COM UM NOME ESPEC�FICO
	 * @param classesComAnotacao
	 */
	protected  void filtrar(List<IType> classesComAnotacao) {

		for (IType classe : classesComAnotacao) {

			IAnnotation[] anotacoes;
			try {
				anotacoes = classe.getAnnotations();

				for (IAnnotation annotation : anotacoes) {

					if (AnotacaoUtil.comparaAnotacaoClasse(clazz, annotation)) {
						try {
							IMemberValuePair[] membros = annotation.getMemberValuePairs();
							insereMembrosMapa(classe, campo, membros);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (JavaModelException e) {
							e.printStackTrace();
						}
					}
				}				

			} catch (JavaModelException e1) {
				e1.printStackTrace();
			}
		}		
	}
	
	private void insereMembrosMapa(IType classe, String campo, IMemberValuePair[] membros) {	
		AnotacaoUtil anotacaoUtil = new AnotacaoUtil();
		PluginUtil pluginUtil = new PluginUtil();
		
		String chave = (String)anotacaoUtil.recuperaMemberValuePair(campo, membros);
		
		String valor = null;
		try {
			valor = pluginUtil.recuperaPropriedadeRastreabilidade(chave);
		} catch(IllegalArgumentException ex) {
			MessageDialog.openError(shell, "Erro", ex.getMessage());
		}
		
		List<IType> listaTipos = mapaArtefatos.get(valor);

		if (listaTipos == null) {
			listaTipos = new ArrayList<IType>();
			mapaArtefatos.put(valor, listaTipos);
		}		

		listaTipos.add(classe);		
	}

}
