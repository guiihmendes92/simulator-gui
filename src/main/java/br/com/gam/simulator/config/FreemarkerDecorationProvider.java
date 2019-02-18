package br.com.gam.simulator.config;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.jpos.q2.ConfigDecorationProvider;
import org.jpos.q2.Q2;
import org.jpos.util.Log;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static freemarker.template.Configuration.VERSION_2_3_28;
import static java.util.Arrays.asList;
import static java.util.Objects.isNull;

/**
 * Classe responsável por carregar os arquivos de propriedades localizados em Q2-decorator.properties.
 * Gerenciar as propriedades dinamicas dos arquivos *.xml, adicionando valores a partir dos arquivos de propriedades carregados.
 * <p>
 * <p>
 * Essa classe é um fork da classe FreemarkerDecorationProvider contendo algumas alterações:
 * <p>
 * Possibilidade de uma propriedade ser carregada a partir de uma variavel de ambiente. Ex: some_property=${from_variable_system}
 * Refatoração de todos métodos para melhor entendimento facilitando em uma possivel manutenção.
 * <p>
 * fernando.araujo
 */
public class FreemarkerDecorationProvider implements ConfigDecorationProvider {
	
	private static final String DECORATION_PROVIDER_PROPERTIES_PATH = "/META-INF/org/jpos/config/Q2-decorator.properties";
	private static final String SYSTEM_VARIABLE_REGEX               = "\\$\\{([^}]*)\\}"; // ${some_value}
	private static final String CONFIG_FILES_PROPERTY               = "config-files";
	private static final String SPLIT_COMMA_REGEX                   = ",";
	private static final String CONFIG_PATH                         = "../cfg";
	
	private File          deployDir;
	private Configuration config;
	private List<String>  configFiles;
	private Map           properties;
	final   Log           logger = Log.getLog(Q2.LOGGER_NAME, getClass().getSimpleName());
	
	@Override
	public void initialize(File deployDir) throws Exception {
		this.deployDir = deployDir;
		this.configFiles = handlerPropertyFiles();
		this.properties = loadProperties();
		this.config = createFreemarker();
	}
	
	private List<String> handlerPropertyFiles() {
		try (InputStream inputStream = getClass().getResourceAsStream(DECORATION_PROVIDER_PROPERTIES_PATH)) {
			
			final List<String> propertyFilesPath = loadPropertyFilesPath(inputStream);
			return handlerPropertyFilesPath(propertyFilesPath);
			
		} catch (IOException e) {
			logger.error(e);
			
			return new ArrayList<>();
		}
	}
	
	private List<String> loadPropertyFilesPath(final InputStream inputStream) throws IOException {
		
		final Properties properties = new Properties();
		properties.load(inputStream);
		
		String configFiles = properties.getProperty(CONFIG_FILES_PROPERTY);
		if (isNull(configFiles)) {
			return new ArrayList<>();
		}
		
		return asList(configFiles.split(SPLIT_COMMA_REGEX));
	}
	
	private List<String> handlerPropertyFilesPath(final List<String> propertyFilesPath) {
		
		final List<String> configFiles = new ArrayList<>();
		
		propertyFilesPath.forEach(filePath -> {
			
			String filePathFormated = handlerPropertyValue(filePath);
			
			System.out.println("Load properties file " + filePathFormated);
			
			configFiles.add(filePathFormated);
		});
		
		return configFiles;
	}
	
	private Configuration createFreemarker() {
		
		final Configuration configuration = new Configuration(VERSION_2_3_28);
		
		configuration.setTemplateLoader(configureTemplate());
		configuration.setTemplateUpdateDelayMilliseconds(0);
		configuration.setTagSyntax(Configuration.SQUARE_BRACKET_TAG_SYNTAX);
		
		return configuration;
	}
	
	private MultiTemplateLoader configureTemplate() {
		try {
			
			final Set<TemplateLoader> configFileLoaders = new HashSet<>();
			
			templateSearch(deployDir, configFileLoaders);
			configFileLoaders.add(new ConfigFileLoader(new File(deployDir, CONFIG_PATH)));
			
			final TemplateLoader[] templateLoaders = new TemplateLoader[configFileLoaders.size()];
			final AtomicInteger    count           = new AtomicInteger(0);
			
			configFileLoaders.forEach(templateLoader -> templateLoaders[count.getAndIncrement()] = templateLoader);
			
			return new MultiTemplateLoader(templateLoaders);
			
		} catch (IOException e) {
			
			logger.error(e);
		}
		
		return null;
	}
	
	private void templateSearch(final File file, final Set<TemplateLoader> configFileLoaders) {
		try {
			
			Optional.ofNullable(file.listFiles()) //
			        .map(Stream::of) //
			        .ifPresent(files -> files.filter(File::isDirectory) //
			                                 .forEach(subFile -> {
				                                 templateSearch(subFile, configFileLoaders);
			                                 }));
			configFileLoaders.add(new ConfigFileLoader(file));
		} catch (IOException e) {
			logger.error(e);
		}
		
	}
	
	@Override
	public String decorateFile(final File file) throws Exception {
		
		this.properties = loadProperties();
		
		final String deployDirAbsolutePath = deployDir.getAbsolutePath();
		final String filePath              = file.getAbsolutePath();
		final int    isFileinsideDeployDir = filePath.indexOf(deployDirAbsolutePath);
		
		if (isFileinsideDeployDir < 0) {
			throw new Exception("Arquivo " + filePath + " nao esta localizado no diretorio de deploy");
		}
		final String fileName = filePath.substring(deployDirAbsolutePath.length() + 1);
		
		final StringWriter outFile = new StringWriter();
		
		final Template template = config.getTemplate(fileName);
		template.process(properties, outFile);
		
		return outFile.toString();
	}
	
	private Map loadProperties() {
		
		return configFiles.stream() //
		                  .map(String::trim) //
		                  .map(File::new) //
		                  .map(this::propertiesSearch) //
		                  .reduce(new HashMap(), (hashMap, properties) -> {
			                  properties.putAll(hashMap);
			                  return properties;
		                  });
		
	}
	
	private Map propertiesSearch(final File file) {
		
		return Optional.ofNullable(file) //
		               .filter(File::exists) //
		               .map(safeFile -> {
			
			               final Map rootMap = new HashMap();
			
			               try (FileReader fileReader = new FileReader(safeFile)) {
				
				               Properties properties = new Properties();
				               properties.load(fileReader);
				
				               properties.forEach((key, value) -> {
					               String handledPropertyValue = value.toString();
					
					               handledPropertyValue = handlerPropertyValue(handledPropertyValue);
					
					               rootMap.put(key, handledPropertyValue);
				               });
				
			               } catch (IOException e) {
				               e.printStackTrace();
			               }
			
			               return rootMap;
		               }) //
		               .orElse(new HashMap());
		
	}
	
	private String handlerPropertyValue(final String data) {
		
		Optional<String> resultMatch = checkIfRegexMatches(SYSTEM_VARIABLE_REGEX, data);
		if (!resultMatch.isPresent()) {
			return data;
		}
		
		String systemVariableValue = getSystemPropertyValue(resultMatch.get());
		return replaceString(SYSTEM_VARIABLE_REGEX, data, systemVariableValue);
	}
	
	/**
	 * Verifica se a String passada contem regex informada.
	 *
	 * @param regex Regex que sera utilizado
	 * @param value String contendo o valor a ser verificado pelo regex
	 * @return Se o match for true, retorno o valor tratado da regex, se falso null
	 */
	private Optional<String> checkIfRegexMatches(String regex, String value) {
		
		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(value);
		
		if (matcher.find()) {
			return Optional.of(matcher.group(1));
		}
		
		return Optional.empty();
	}
	
	private String getSystemPropertyValue(final String systemProperty) {
		return System.getProperty(systemProperty);
	}
	
	private String replaceString(String regex, String replaceStr, String valueToReplace) {
		return replaceStr.replaceAll(regex, valueToReplace);
	}
	
	@Override
	public void uninitialize() {
	}
	
	private static class ConfigFileLoader extends FileTemplateLoader {
		public ConfigFileLoader(File file) throws IOException {
			super(file);
		}
		
		public long getLastModified(Object templateSource) {
			return System.currentTimeMillis();
		}
	}
}