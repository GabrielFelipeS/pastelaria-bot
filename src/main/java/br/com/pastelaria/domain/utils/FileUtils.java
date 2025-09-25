package br.com.pastelaria.domain.utils;

import net.dv8tion.jda.api.utils.FileUpload;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class FileUtils {

    public static FileUpload getFile(String url) {
        URL resource = FileUtils.class.getClassLoader().getResource(url);

        File file = null;
        try {
            assert resource != null;
            file = new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return FileUpload.fromData(file);
    }
}
