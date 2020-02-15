package com.es.core.model.phone;

import java.util.Objects;

public class Color {

    private Long id;

    private String code;

    public Color() {
        id = 0L;
        this.code = "code";
    }

    public Color(Long id, String code) {
        this.id = id;
        this.code = code;
    }

    public Color(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return code.equals(color.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }
}
