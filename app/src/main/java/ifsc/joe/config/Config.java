package ifsc.joe.config;

import java.io.InputStream;
import java.util.Properties;

public final class Config {

    private static final Properties props = new Properties();

    static {
        try (InputStream in =
                     Config.class.getClassLoader().getResourceAsStream("joe.properties")) {

            if (in == null) {
                throw new RuntimeException("Arquivo joe.properties nao encontrado no classpath!");
            }

            props.load(in);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar arquivo joe.properties", e);
        }
    }

    public static int getInt(String key) {
        String value = props.getProperty(key);
        if (value == null)
            throw new RuntimeException("Chave nao encontrada: " + key);
        return Integer.parseInt(value);
    }

    public static double getDouble(String key) {
        String value = props.getProperty(key);
        if (value == null)
            throw new RuntimeException("Chave nao encontrada: " + key);
        return Double.parseDouble(value);
    }

    public static String get(String key) {
        String value = props.getProperty(key);
        if (value == null)
            throw new RuntimeException("Chave nao encontrada: " + key);
        return value;
    }
}
