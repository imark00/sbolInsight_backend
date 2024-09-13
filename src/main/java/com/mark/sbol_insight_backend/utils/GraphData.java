package com.mark.sbol_insight_backend.utils;

import java.util.HashMap;


public class GraphData {

    private final Elements elements = new Elements();

    public HashMap<String, Object> getGraphData() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("elements", elements.getElements());
        result.put("style", "hi");
        return result;
    }
}
