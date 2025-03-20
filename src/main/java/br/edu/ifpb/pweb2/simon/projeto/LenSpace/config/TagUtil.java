package br.edu.ifpb.pweb2.simon.projeto.LenSpace.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagUtil {
    public static String transformarHashtagsEmLinks(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto;
        }

        // Expressão Regular para encontrar hashtags
        Pattern pattern = Pattern.compile("#(\\w+)");
        Matcher matcher = pattern.matcher(texto);

        // Substituir hashtags por links
        StringBuffer resultado = new StringBuffer();
        while (matcher.find()) {
            String hashtag = matcher.group(1);
            String link = "<a href='/hashtag/" + hashtag + "'>#"+hashtag+"</a>";
            matcher.appendReplacement(resultado, link);
        }
        matcher.appendTail(resultado);

        return resultado.toString();
    }

}
