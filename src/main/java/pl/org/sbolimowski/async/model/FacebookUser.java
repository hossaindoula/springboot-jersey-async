package pl.org.sbolimowski.async.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FacebookUser {
    private final String name;
    private final String username;
    private final String locale;

    @JsonCreator
    public FacebookUser(@JsonProperty("name") String name,
                        @JsonProperty("username") String username,
                        @JsonProperty("locale") String locale) {
        this.name = name;
        this.username = username;
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getLocale() {
        return locale;
    }
}
