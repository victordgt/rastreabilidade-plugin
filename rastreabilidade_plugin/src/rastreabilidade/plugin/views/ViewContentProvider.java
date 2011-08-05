package rastreabilidade.plugin.views;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.part.ViewPart;

import rastreabilidade.plugin.views.casodeuso.GerenciadorArvoreCasoDeUso;
import rastreabilidade.plugin.views.casodeuso.GerenciadorCasoDeUso;

public class ViewContentProvider implements IStructuredContentProvider, ITreeContentProvider {
	private TreeParent invisibleRoot;
	private ViewPart viewPart;
	
	public ViewContentProvider(ViewPart viewPart) {
		this.viewPart = viewPart;
	}

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}

	public void dispose() {
	}

	public Object[] getElements(Object parent) {
		if (parent.equals(viewPart.getViewSite())) {
			if (invisibleRoot == null)
				initialize();
			return getChildren(invisibleRoot);
		}
		return getChildren(parent);
	}

	public Object getParent(Object child) {
		if (child instanceof TreeObject) {
			return ((TreeObject) child).getParent();
		}
		return null;
	}

	public Object[] getChildren(Object parent) {
		if (parent instanceof TreeParent) {
			return ((TreeParent) parent).getChildren();
		}
		return new Object[0];
	}

	public boolean hasChildren(Object parent) {
		if (parent instanceof TreeParent)
			return ((TreeParent) parent).hasChildren();
		return false;
	}

	/*
	 * We will set up a dummy model to initialize tree heararchy. In a real
	 * code, you will connect to a real model and expose its hierarchy.
	 */
	private void initialize() {
		
		GerenciadorCasoDeUso gerenciador = new GerenciadorCasoDeUso();	
		Map<Annotation, List<Class<?>>> mapa = gerenciador.getMapaClasses();
		IGerenciadorArvore gerenciadorArvore = new GerenciadorArvoreCasoDeUso(mapa);
		
		invisibleRoot = gerenciadorArvore.contruirArvore();		
		
	}
	
	public void setInvisibleRoot(TreeParent treeParent) {
		this.invisibleRoot = treeParent;
	}
}
