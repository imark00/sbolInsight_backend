package com.mark.sbol_insight_backend.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {

    private String id;
    private String name;
    private String URI;

    public Node() {
    }

    public Node(int id, String name) {
        this.id = "n" + id;
        this.name = name;
    }
}
