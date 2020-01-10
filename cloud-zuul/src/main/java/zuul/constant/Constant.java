package zuul.constant;

import java.util.HashMap;
import java.util.Map;

public class Constant {

    public static Map<String, String> getDocs() {
        Map<String, String> map = new HashMap<String, String>(6);
        map.put("案件服务", "/cloud-case/v2/api-docs");
        map.put("权限服务", "/cloud-authorize/v2/api-docs");
        map.put("后台服务", "/cloud-backend/v2/api-docs");
        return map;
    }

}
