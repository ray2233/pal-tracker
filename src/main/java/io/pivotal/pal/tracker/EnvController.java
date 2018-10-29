package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {

    private String PORT;
    private String MEMORY_LIMIT;
    private String CF_INSTANCE_INDEX;
    private String CF_INSTANCE_ADDR;

    public EnvController(@Value("${PORT:NOT SET}") String PORT, @Value("${MEMORY_LIMIT:NOT SET}") String MEMORY_LIMIT, @Value("${CF_INSTANCE_INDEX:NOT SET}")String CF_INSTANCE_INDEX,@Value("${CF_INSTANCE_ADDR:NOT SET}")  String CF_INSTANCE_ADDR) {
        this.PORT = PORT;
        this.MEMORY_LIMIT = MEMORY_LIMIT;
        this.CF_INSTANCE_ADDR = CF_INSTANCE_ADDR;
        this.CF_INSTANCE_INDEX = CF_INSTANCE_INDEX;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {
        Map<String,String> Output = new HashMap<String,String>();

        Output.put("PORT",PORT);
        Output.put("MEMORY_LIMIT", MEMORY_LIMIT);
        Output.put("CF_INSTANCE_INDEX", CF_INSTANCE_INDEX);
        Output.put("CF_INSTANCE_ADDR", CF_INSTANCE_ADDR);

        return Output;
    }
}
