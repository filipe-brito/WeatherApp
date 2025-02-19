package com.abelatroz.weatherapp.view;

import java.io.InputStream;
import javafx.scene.text.Font;

public class FontLoader {

    // Caminhos das fontes no diretório /resources/fonts/
    private static final String[] FONT_FILES = {
        "/fonts/Fipps-Regular.otf",
        "/fonts/PixelifySans-Regular.ttf",
        "/fonts/PixelifySans-Medium.ttf",
        "/fonts/PixelifySans-SemiBold.ttf",
        "/fonts/VT323-Regular.ttf"
    };

    // Método para carregar todas as fontes automaticamente
    public static void loadFonts(double size) {
        for (String path : FONT_FILES) {
            try (InputStream fontStream = FontLoader.class.getResourceAsStream(path)) {
                if (fontStream != null) {
                    Font.loadFont(fontStream, size);
                } else {
                    System.err.println("⚠ Fonte não encontrada: " + path);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
