package br.com.pastelaria.domain.utils;

import net.dv8tion.jda.api.utils.FileUpload;

import java.io.InputStream;

public class FileUtils {

    public static FileUpload getFile(String url) {
        InputStream inputStream = FileUtils.class.getClassLoader().getResourceAsStream(url);

        try {
            assert inputStream != null;
            String[] partes = url.split("/");
            return FileUpload.fromData(inputStream, partes[partes.length - 1]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
