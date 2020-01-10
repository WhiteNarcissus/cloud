package zuul.config;



import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import zuul.constant.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Primary
public class DocumentationConfig implements SwaggerResourcesProvider {

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList();
        Map<String, String> map = Constant.getDocs();
        if(!map.keySet().isEmpty()) {
            for (String key : map.keySet()) {
                resources.add(swaggerResource(key, map.get(key), "1.0"));
            }
        }
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
