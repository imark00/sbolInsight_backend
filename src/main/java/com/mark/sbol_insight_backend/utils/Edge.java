package com.mark.sbol_insight_backend.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Edge {

    private String id;
    private String label;
    private String source;
    private String target;


    public Edge(int id, String label, String source, String target) {
        this.id = "e" + id;
        this.label = label;
        this.source = source;
        this.target = target;
    }

    public Edge() {
    }
}
