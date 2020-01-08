package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {

    private String port;
    private String memory_limit;
    private String instance_index;
    private String instance_addr;

    public EnvController(@Value("${port:NOT SET}") String port,
                         @Value("${memory.limit:NOT SET}") String memory_limit,
                         @Value("${cf.instance.index:NOT SET}") String instance_index,
                         @Value("${cf.instance.addr:NOT SET}") String instance_addr)
    {
        this.port = port;
        this.memory_limit = memory_limit;
        this.instance_index = instance_index;
        this.instance_addr = instance_addr;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv(){

        Map<String, String> env = new HashMap<>();

        env.put("PORT", this.port);
        env.put("MEMORY_LIMIT", this.memory_limit);
        env.put("CF_INSTANCE_INDEX", this.instance_index);
        env.put("CF_INSTANCE_ADDR", this.instance_addr);

        return env;
    }
}
