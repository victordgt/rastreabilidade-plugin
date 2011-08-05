package rastreabilidade.plugin.views.casodeuso;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.part.ViewPart;

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
		GerenciadorArvoreCasoDeUso gerenciadorArvore = new GerenciadorArvoreCasoDeUso(mapa);
		
		
		invisibleRoot = gerenciadorArvore.contruirArvore();		
		
		
		/*
		TreeObject to1 = new TreeObject("Leaf 1");
		TreeObject to2 = new TreeObject("Leaf 2");
		TreeObject to3 = new TreeObject("Leaf 3");
		TreeParent p1 = new TreeParent("Parent 1");
		p1.addChild(to1);
		p1.addChild(to2);
		p1.addChild(to3);

		TreeObject to4 = new TreeObject("Leaf 4");
		TreeParent p2 = new TreeParent("Parent 2");
		p2.addChild(to4);

		TreeParent root = new TreeParent("Root");
		root.addChild(p1);
		root.addChild(p2);


		invisibleRoot = new TreeParent("");
		invisibleRoot.addChild(root);
		
				
		*/

	}
}
