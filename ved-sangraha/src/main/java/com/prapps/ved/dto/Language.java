package com.prapps.ved.dto;

import java.util.Arrays;
import java.util.List;

public class Language implements DatastoreObject {
    private String code;
    private String name;

    public Language() { }
    public Language(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static final String CODE = "code";
    public static final String NAME = "name";

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public List<String> properties() {
        return Arrays.asList(CODE, NAME);
    }
}
