package rastreabilidade.processor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import rastreabilidade.CasoDeUso;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("*")
public class APTTeste extends AbstractProcessor {
	

	private Messager messager;
	private Filer filer;

	@Override
	public void init(ProcessingEnvironment env) {
		messager = env.getMessager();
		filer = env.getFiler();
		
	}
	 

	@Override
	    public boolean process(Set<? extends TypeElement> annotations,
	            RoundEnvironment roundEnvironment) {
	        for (Element e : roundEnvironment.getRootElements())  {
	        	
	        	CasoDeUso casoDeUso = e.getAnnotation(CasoDeUso.class);
	        	
	 
	        	
	        	if (casoDeUso != null) {
	        		
	        		//messager.printMessage(Kind.WARNING, casoDeUso.nome(),e);
	        		validaCasoDeUso(casoDeUso.nome(), e);
	        		
	        	}
	                
	        }
	        return true;
	  
	}


	private void validaCasoDeUso(String chaveCasoDeUso, Element e) {
		Properties properties = new Properties();
		try {
			
			System.out.println(File.listRoots());

			properties.store(new FileOutputStream("rastreabilidade.properties"), null);
			
			
			FileObject file = filer.createResource(StandardLocation.SOURCE_OUTPUT, "", "rastreabilidade.properties");
			
			PrintWriter writer = new PrintWriter(file.openWriter());
			writer.print("Caso de Uso 1=blablabl");
			writer.close();
			
			//InputStream in = e.getClass().getResourceAsStream("rastreabilidade.properties");
			
		
	 
		    //properties.load(in);
		    
		  
		   // messager.printMessage(Kind.WARNING, properties.getProperty(chaveCasoDeUso),e);
		    
		    //System.out.println(properties.getProperty(chaveCasoDeUso));
		    
		} catch (IOException ex) {
		}
	}


}
