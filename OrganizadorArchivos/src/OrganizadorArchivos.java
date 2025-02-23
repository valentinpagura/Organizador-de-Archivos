
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class OrganizadorArchivos {

    // Definir las categorías y sus extensiones
    private static final Map<String, String[]> CATEGORIAS = new HashMap<>();

    static {
        CATEGORIAS.put("Imágenes", new String[]{".jpg", ".jpeg", ".png", ".gif", ".bmp"});
        CATEGORIAS.put("Documentos", new String[]{".pdf", ".docx", ".txt", ".xlsx", ".pptx"});
        CATEGORIAS.put("Música", new String[]{".mp3", ".wav", ".flac"});
        CATEGORIAS.put("Videos", new String[]{".mp4", ".mkv", ".avi"});
        CATEGORIAS.put("Otros", new String[]{});  // Para archivos sin categoría
    }

    public static void main(String[] args) {
        // Carpeta a organizar
        String carpetaPrincipal = "C:/Users/TuUsuario/Desktop/TUCarpetaDesorganizada";

        // Crear subcarpetas si no existen
        for (String categoria : CATEGORIAS.keySet()) {
            File subcarpeta = new File(carpetaPrincipal, categoria);
            if (!subcarpeta.exists()) {
                subcarpeta.mkdir();
            }
        }

        // Organizar archivos
        File carpeta = new File(carpetaPrincipal);
        File[] archivos = carpeta.listFiles();

        if (archivos != null) {
            for (File archivo : archivos) {
                // Ignorar si es una carpeta
                if (archivo.isDirectory()) {
                    continue;
                }

                // Obtener la extensión del archivo
                String nombreArchivo = archivo.getName();
                String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".")).toLowerCase();

                // Encontrar la categoría correspondiente
                String categoriaEncontrada = "Otros";  // Por defecto
                for (Map.Entry<String, String[]> entry : CATEGORIAS.entrySet()) {
                    for (String ext : entry.getValue()) {
                        if (ext.equalsIgnoreCase(extension)) {
                            categoriaEncontrada = entry.getKey();
                            break;
                        }
                    }
                }

                // Mover el archivo a la carpeta correspondiente
                Path rutaOrigen = archivo.toPath();
                Path rutaDestino = Paths.get(carpetaPrincipal, categoriaEncontrada, nombreArchivo);

                try {
                    Files.move(rutaOrigen, rutaDestino);
                    System.out.println("Movido: " + nombreArchivo + " -> " + categoriaEncontrada);
                } catch (IOException e) {
                    System.err.println("Error al mover el archivo: " + nombreArchivo);
                    e.printStackTrace();
                }
            }
        }

        System.out.println("¡Organización completada!");
    }
}