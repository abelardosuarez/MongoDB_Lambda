package familiares;

import org.bson.Document;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.stream.MalformedJsonException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

public class CargaFamiliaCloud {
	/*	External Archives
	 * 	gson-2.2.2.jar
	 * 	mongo-java-driver-3.12.1.jar
	 */
	
	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;
	private MongoCollection<Document> mongoCollection;
	private static Document parse(String source) throws MalformedJsonException {
		try {
		    return Document.parse(source);
		  } catch (JsonIOException | org.bson.json.JsonParseException o_O) {
		    throw new MalformedJsonException("Error al generar JSON document", o_O);
		  }
		}
	
	public CargaFamiliaCloud() {
//		MongoClientURI uri = new MongoClientURI("mongodb+srv://usuario:password@cluster0-p4lmg.mongodb.net/");
		MongoClientURI uri = new MongoClientURI("mongodb://localhost/");
		mongoClient = new MongoClient(uri);
		mongoDatabase = mongoClient.getDatabase("shop");
		mongoCollection = mongoDatabase.getCollection("familiares");
	}// end MongoDBxAtlasExemple constructor

	public void printAllDocuments() {
		FindIterable<Document> cursor = mongoCollection.find();
		
		for(Document doc : cursor) {
			System.out.println(doc.toJson());
		}
	}
	
	public void cargaInicial () throws MalformedJsonException{

		mongoCollection.drop();

		List< Persona> personas = new ArrayList<Persona>();		
		personas.add(new Persona(new Nombre("Elena ", "Perez Castillo"),"",55,165,85,new Telefono(58,111111111)));
		personas.add(new Persona(new Nombre("Abelardo", "Solorzano Perez"),"Y1111111A",82,173,60,new Telefono(34,655905533)));
		personas.add(new Persona(new Nombre("Rocio de Fatima","Zamora de Solorzano"),"Y2222222B",67,163,55,new Telefono(34,111111111))); 
		personas.add(new Persona(new Nombre("Alberto", "Solorzano Zamora"),"Y3333333C",80,180,33,new Telefono(34,222222222))); 
		personas.add(new Persona(new Nombre("Abelardo Jose","Solorzano Zamora"),"Y4444444D",78,175,31,new Telefono(34,333333333)));
		personas.add(new Persona(new Nombre("Fabiana","Solorzano Zamora"),"",60,168,28,new Telefono(1,111111111))); 
		personas.add(new Persona(new Nombre("Salomon","Solorzano Blanco"),"",20,100,5,new Telefono(34))); 
		personas.add(new Persona(new Nombre("Antonela","Solorzano Blanco"),"",15,95,3,new Telefono(34))); 
		personas.add(new Persona(new Nombre("Amanda","Herrera Solorzano"),"",6,60,0,new Telefono(1))); 
		personas.add(new Persona(new Nombre("Omar","Herrera"),"",65,170,28,new Telefono(1,222222222))); 
		personas.add(new Persona(new Nombre("Genesis","Blanco Vera"),"Y5555555E",75,168,29,new Telefono(34,444444444))); 
		
		Gson gson = new Gson();        
    	for(Persona persona : personas) {
			Document docPersona = parse(gson.toJson(persona));
			mongoCollection.insertOne(docPersona);
		}
	}
	
	public static void main(String[] args) throws MalformedJsonException {
		CargaFamiliaCloud cargaFamiliaCloud= new CargaFamiliaCloud();
		cargaFamiliaCloud.cargaInicial();
		cargaFamiliaCloud.printAllDocuments();
		

	}// end main
	
}
