package com.company.prop;

import com.company.anot.PropertyKey;

public class AppProperties {

    @PropertyKey("connections.limit")
    public int maxConnections;

    @PropertyKey("test")
    public String test;

    public String empty;

    @PropertyKey("version")
    public double version;

    public float empty2;

    @PropertyKey("active")
    public boolean isActive;
}
