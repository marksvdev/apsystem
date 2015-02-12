package com.dtu.s113604.apsystem.ap_system.models2;

/**
 * Created by marksv on 29/01/15.
 */
public class AlgorithmData {
    private String foo = "null";
    private Long id;

    public AlgorithmData(String foo) {
        this.foo = foo;
    }

    public AlgorithmData() {
    }

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
