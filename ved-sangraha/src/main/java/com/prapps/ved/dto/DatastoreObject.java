package com.prapps.ved.dto;

import java.util.List;

public interface DatastoreObject {
    List<String> properties();
    String getKind();
}
