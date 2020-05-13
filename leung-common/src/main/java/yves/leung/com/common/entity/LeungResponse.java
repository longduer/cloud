package yves.leung.com.common.entity;

import java.util.HashMap;

public class LeungResponse extends HashMap<String, Object> {
    public LeungResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public LeungResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    @Override
    public LeungResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public String getMessage() {
        return String.valueOf(get("message"));
    }

    public Object getData() {
        return get("data");
    }
}
