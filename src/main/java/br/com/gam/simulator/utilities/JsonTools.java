package br.com.gam.simulator.utilities;

import br.com.gam.simulator.entities.Card;
import br.com.gam.simulator.entities.SwitchJsonCase;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedHashTreeMap;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Created by guilherme.mendes on 05/09/2016.
 */
public class JsonTools {
	
	public static Object read(final String diretory, final Class<?> classOfT) {
		
		try (Reader reader = new InputStreamReader(new FileInputStream(diretory), StandardCharsets.ISO_8859_1)) {
			
			return new GsonBuilder().create() //
			                        .fromJson(reader, classOfT);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void create(final Object json, final String fileName) {
		
		final String separator    = System.getProperty("file.separator");
		final String userDiretory = System.getProperty("user.dir");
		final String paste        = json.getClass().getSimpleName();
		
		final String fileDiretory = userDiretory + separator + paste;
		
		final File fileDir = new File(fileDiretory);
		
		final File file = Optional.of(new File(fileDiretory)) //
		                          .filter(File::mkdirs) //
		                          .filter(File::isDirectory) //
		                          .map(File::getAbsoluteFile) //
		                          .orElse(fileDir);
		
		final String name = file.getPath() + separator + fileName + ".json";
		
		try (final Writer writer = new OutputStreamWriter(new FileOutputStream(name), StandardCharsets.ISO_8859_1)) {
			
			final GsonBuilder gsonBuilder = new GsonBuilder();
			
			gsonBuilder.disableHtmlEscaping() //
			           .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES) //
			           .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES) //
			           .setPrettyPrinting() //
			           .create() //
			           .toJson(json, writer);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		
		final Card card = Card.builder() //
		                      .pan("6042202012547896") //
		                      .password("1234") //
		                      .serviceCode("601") //
		                      .build();
		
		final LinkedHashTreeMap authorization     = new LinkedHashTreeMap();
		final LinkedHashTreeMap undoAuthorization = new LinkedHashTreeMap();
		
		authorization.put("0", "0800");
		authorization.put("2", "PAN");
		authorization.put("7", "GMT_DATETIME");
		authorization.put("12", "LOCAL_TIME");
		authorization.put("13", "LOCAL_DATE");
		
		undoAuthorization.putAll(authorization);
		
		final SwitchJsonCase json = SwitchJsonCase.builder() //
		                                          .card(card) //
		                                          .authorization(authorization) //
		                                          .undoAuthorization(undoAuthorization) //
		                                          .build();
		
		create(json, "01-sipag#auth");
		create(card, "01-card");
		
		//		final String dir       = System.getProperty("user.dir");
		//		final String separator = System.getProperty("file.separator");
		//
		final String diretory = "C:\\Users\\Gui\\IdeaProjects\\simulator-gui\\SwitchJsonCase\\01-sipag#auth.json";
		
		//		System.out.println(read(diretory, SwitchJsonCase.class));
	}
}


