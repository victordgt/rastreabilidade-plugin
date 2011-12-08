package rastreabilidade.plugin.views;

import java.io.InputStream;

import org.eclipse.jdt.ui.ISharedImages;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

public class ViewLabelProvider extends LabelProvider {

	public String getText(Object obj) {
		return obj.toString();
	}

	public Image getImage(Object obj) {
		try {
			TreeParent treeParent = ((TreeObject) obj).getParent();

			if (treeParent.getCaminhoImagem() != null) {

				Image imagem = getImageFromFile(treeParent.getCaminhoImagem());
				return imagem;
			}
		} catch (ClassCastException e) {
			e.printStackTrace();
		}


		ISharedImages sharedImages = JavaUI.getSharedImages();
		Image imagem = sharedImages.getImage(ISharedImages.IMG_OBJS_CFILE);
		
		return imagem;
	}

	private Image getImageFromFile(String caminho) {
		InputStream is = this.getClass().getResourceAsStream(caminho);
		Image image = new Image(PlatformUI.getWorkbench().getDisplay(), is);
	
		return image;
	}
}