package familiares;

import org.bson.Document;
import com.google.gson.Gson;
import com.google.gson.stream.MalformedJsonException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EjemploLambdaCloud {
	/*	External Archives
	 * 	gson-2.2.2.jar
	 * 	mongo-java-driver-3.12.1.jar
	 */
	
	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;
	private MongoCollection<Document> mongoCollection;

	//first connection
	static int codigoPais(String quePais,List<Pais> paises) {
		int queCodigo = 0;
		for (Pais pais :paises) 
			if (pais.nombre == quePais)
				queCodigo = pais.getCodigo();
		return queCodigo;
	}
	// imc Indice de Masa Corporal
	static double imc(int peso, int altura) {
		return peso*10000/(altura*altura);
	}

	public EjemploLambdaCloud() {
//		MongoClientURI uri = new MongoClientURI("mongodb+srv://usuario:password@cluster0-p4lmg.mongodb.net/");
		MongoClientURI uri = new MongoClientURI("mongodb://localhost/");
		mongoClient = new MongoClient(uri);
		mongoDatabase = mongoClient.getDatabase("shop");
		mongoCollection = mongoDatabase.getCollection("familiares");
	}
	
	public void ProcesaDatosFamilia () throws MalformedJsonException{

		List< Pais> paises = new ArrayList<Pais>();		
			paises.add(new Pais("Estados Unidos",1));
			paises.add(new Pais("Espania",34));
			paises.add(new Pais("Venezuela",58));
			
			Gson gson = new Gson();        
	    	
			System.out.println("-----collection familiares -------++++-------");
			List< Persona> personas = new ArrayList<Persona>();		
			FindIterable<Document> cursor = mongoCollection.find();
			for (Document doc : cursor) {
				personas.add( (Persona) gson.fromJson(doc.toJson(), Persona.class));
				}
			System.out.println("------------------------------");
			
			mongoClient.close();

			personas.forEach(p -> System.out.println(p));
		    
		    System.out.println();
			
		    System.out.println("suma edad:        personas.stream().mapToInt(persona->persona.getEdad()).sum())      = "+personas.stream().mapToInt(persona->persona.getEdad()).sum());
		    System.out.println("cuantas personas: personas.size()                                                    = "+personas.size());
		    System.out.println("edad promedio:    personas.stream().mapToInt(persona->persona.getEdad()).average()   = "+personas.stream().mapToInt(persona->persona.getEdad()).average());
		    System.out.println("altura promedio:  personas.stream().mapToInt(persona->persona.getAltura()).average() = "+personas.stream().mapToInt(persona->persona.getAltura()).average());
		    System.out.println("peso promedio:    personas.stream().mapToInt(persona->persona.getPeso()).average()   = "+personas.stream().mapToInt(persona->persona.getPeso()).average());
		    System.out.println("peso minimo:      personas.stream().mapToInt(persona->persona.getPeso()).min()       = "+personas.stream().mapToInt(persona->persona.getPeso()).min());
		    System.out.println("peso maximo:      personas.stream().mapToInt(persona->persona.getPeso()).max()       = "+personas.stream().mapToInt(persona->persona.getPeso()).max());
		    
		    
		    System.out.println();
		    System.out.println("cuantas sobrepeso = filter(persona->imc(persona.getPeso(),persona.getAltura())>25) "
		                       +personas.stream().filter(persona->imc(persona.getPeso(),persona.getAltura())>25).map(persona->persona.getNombre()).count());
		    
		    ArrayList<Nombre> sobrepeso = personas.stream().filter(persona->imc(persona.getPeso(),persona.getAltura())>25).map(persona->persona.getNombre())
                    .collect(Collectors.toCollection(() -> new ArrayList<Nombre>()));
            System.out.println();
        	System.out.println("personas con sobrepeso indice de masa corporal > 25");
        	System.out.println();
        	sobrepeso.forEach(p -> System.out.println(p));
        		     
                    
           System.out.println("personas adultas = filter(persona->persona.getEdad()>17) ");
		    System.out.println();

		    System.out.println("suma edad adultos:               filter(persona->persona.getEdad()>17).mapToInt(persona->persona.getEdad()).sum())        = "+personas.stream().filter(persona->persona.getEdad()>17).mapToInt(persona->persona.getEdad()).sum());
		    System.out.println("cuantas personas adultas:        filter(persona->persona.getEdad()>17).count())                                           = "+personas.stream().filter(persona->persona.getEdad()>17).count());
		    System.out.println("edad promedio adultos:           filter(persona->persona.getEdad()>17).mapToInt(persona->persona.getEdad()).average())    = "+personas.stream().filter(persona->persona.getEdad()>17).mapToInt(persona->persona.getEdad()).average());
		    System.out.println("altura promedio adultos:         filter(persona->persona.getEdad()>17).mapToInt(persona->persona.getAltura()).average())  = "+personas.stream().filter(persona->persona.getEdad()>17).mapToInt(persona->persona.getAltura()).average());
		    System.out.println("peso promedio adultos:           filter(persona->persona.getEdad()>17).mapToDouble(persona->persona.getPeso()).average()) = "+personas.stream().filter(persona->persona.getEdad()>17).mapToDouble(persona->persona.getPeso()).average());
		    System.out.println("peso minimo adultos:             filter(persona->persona.getEdad()>17).mapToInt(persona->persona.getPeso()).min())        = "+personas.stream().filter(persona->persona.getEdad()>17).mapToInt(persona->persona.getPeso()).min());
		    System.out.println("peso maximo adultos:             filter(persona->persona.getEdad()>17).mapToInt(persona->persona.getPeso()).max())        = "+personas.stream().filter(persona->persona.getEdad()>17).mapToInt(persona->persona.getPeso()).max());
		    System.out.println("Todos son adultos:               allMatch(persona->persona.getEdad()>17))                                                 = "+personas.stream().allMatch(persona->persona.getEdad()>17));
		    System.out.println("Existe alguno que no sea adulto: anyMatch(p->!(p.getEdad()>17)))                                                          = "+personas.stream().anyMatch(p->!(p.getEdad()>17)));
 		    System.out.println("Quienes viven en Estados Unidos: filter(persona->persona.getTelefono().getCodigo()==codigoPais(\"Estados Unidos\",paises))\r\n" + 
		    		"		          	.map(persona->persona.getNombre()).collect(Collectors.toCollection()))) \r\n" +
		    		"                   = "+personas.stream()
		          	.filter(persona->persona.getTelefono().getCodigo()==codigoPais("Estados Unidos",paises))
		          	.map(persona->persona.getNombre())
		          	.collect(Collectors.toCollection(() -> new ArrayList<Nombre>())));

		    System.out.println();
		    
		    ArrayList<Persona> adultos = personas.stream().filter(persona->persona.getEdad()>17).collect(
					Collectors.toCollection(() -> new ArrayList<Persona>()));
		    
		    System.out.println();
		    System.out.println("ArrayList<Persona> adultos = personas.stream().filter(persona->persona.getEdad()>17)"
		    		+ ".collect(Collectors.toCollection(() -> new ArrayList<Persona>()))");
		    System.out.println();
		    
		    adultos.forEach(p -> System.out.println(p));
		    
		    System.out.println();
		    
			System.out.println("suma edad adultos               = mapToInt(adulto->adulto.getEdad()).sum():        "+adultos.stream().mapToInt(adulto->adulto.getEdad()).sum());
		    System.out.println("cuantos adultos                 = adultos.size():                                  "+adultos.size());
		    System.out.println("edad promedio adultos           = mapToInt(adulto->adulto.getEdad()).average():    "+adultos.stream().mapToInt(adulto->adulto.getEdad()).average());
		    System.out.println("altura promedio adultos         = mapToInt(adulto->adulto.getAltura()).average():  "+adultos.stream().mapToInt(adulto->adulto.getAltura()).average());
		    System.out.println("peso promedio adultos           = mapToDouble(adulto->adulto.getPeso()).average(): "+adultos.stream().mapToDouble(adulto->adulto.getPeso()).average());
		    System.out.println("peso minimo adultos             = mapToInt(adulto->adulto.getPeso()).min():        "+adultos.stream().mapToInt(adulto->adulto.getPeso()).min());
		    System.out.println("peso maximo adultos             = mapToInt(adulto->adulto.getPeso()).max():        "+adultos.stream().mapToInt(adulto->adulto.getPeso()).max());
		    System.out.println("Todos son adultos               = allMatch(adulto->adulto.getEdad()>17)):          "+adultos.stream().allMatch(adulto->adulto.getEdad()>17));
		    System.out.println("Existe alguno que no sea adulto = anyMatch(adulto->!(adulto.getEdad()>17)):        "+adultos.stream().anyMatch(adulto->!(adulto.getEdad()>17)));
		    System.out.println("Existe alguno que no sea adulto = anyMatch(adulto->adulto.getEdad()<18)):          "+adultos.stream().anyMatch(adulto->adulto.getEdad()<18));

	    				
		}
	public static void main(String[] args) throws MalformedJsonException {
		
		EjemploLambdaCloud ejemploLambdaCloud= new EjemploLambdaCloud();
		ejemploLambdaCloud.ProcesaDatosFamilia();
		

	}// end main
	
	}

