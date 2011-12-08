package rastreabilidade.plugin.views;

import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.IType;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.part.ViewPart;

import rastreabilidade.plugin.artefato.GerenciadorCasoDeUso;


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

	/**
	 * Inicializa a view com a ‡rvore padr‹o de casos de uso
	 * 
	 * We will set up a dummy model to initialize tree heararchy. In a real
	 * code, you will connect to a real model and expose its hierarchy.
	 */
	private void initialize() {
		GerenciadorCasoDeUso gerenciador = new GerenciadorCasoDeUso(viewPart.getSite().getShell());	
		Map<String, List<IType>> mapa = gerenciador.constroiMapa();
		IGerenciadorArvore gerenciadorArvore = new ConstrutorArvoreView(mapa);
		invisibleRoot = gerenciadorArvore.constroi();			
	}
	
	public void setInvisibleRoot(TreeParent treeParent) {
		this.invisibleRoot = treeParent;
	}
}
