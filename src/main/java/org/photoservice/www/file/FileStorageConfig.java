package org.photoservice.www.file;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties("file-storage")
public record FileStorageConfig(String root,
                                boolean resourcesBase,
                                Map<String, String> directories) {
}
