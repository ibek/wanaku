package org.wanaku.routers.simple;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.logging.Logger;
import org.wanaku.api.resolvers.ResourceResolver;
import org.wanaku.api.types.McpResource;
import org.wanaku.api.types.McpResourceData;
import org.wanaku.api.types.ResourceReference;

class SimpleResourceResolver implements ResourceResolver {
    private static final Logger LOG = Logger.getLogger(SimpleResourceResolver.class);
    private String resourcesPath;

    public SimpleResourceResolver(String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }

    public static List<ResourceReference> loadResources(File resourcesFile) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(resourcesFile,
                objectMapper.getTypeFactory().constructCollectionType(List.class, ResourceReference.class));
    }

    @Override
    public List<McpResource> resources() {
        LOG.info("Resolving resources");
        List<McpResource> mcpResources = new ArrayList<>();

        File resourcesFile = new File(resourcesPath, "resources.json");

        try {
            List<ResourceReference> references = loadResources(resourcesFile);

            for (ResourceReference reference : references) {
                McpResource mcpResource = new McpResource();

                mcpResource.uri = String.format("%s:%s", reference.getType(), reference.getLocation());
                mcpResource.name = reference.getName();
                mcpResource.mimeType = reference.getMimeType();
                mcpResource.description = reference.getDescription();

                mcpResources.add(mcpResource);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return mcpResources;
    }

    @Override
    public List<McpResourceData> read(String uri) {
        URI uriUri = URI.create(uri);
        String filePath = uriUri.getSchemeSpecificPart();

        File file = new File(filePath);
        try {
            McpResourceData data = new McpResourceData();
            data.uri = uri;
            data.text = Files.readString(file.toPath());
            data.mimeType = "text/plain";

            return List.of(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
