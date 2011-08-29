package rastreabilidade.plugin.views;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;


public class TreeObject implements IAdaptable {
	private String name;
	private TreeParent parent;
	private IResource resource;
	
	public TreeObject(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setParent(TreeParent parent) {
		this.parent = parent;
	}
	public TreeParent getParent() {
		return parent;
	}
	public String toString() {
		return getName();
	}
	public Object getAdapter(Class key) {
		return null;
	}
	
	public IResource getResource() {
		return this.resource;
	}
	
	public void setResource(IResource resource) {
		this.resource = resource;
	}
}

