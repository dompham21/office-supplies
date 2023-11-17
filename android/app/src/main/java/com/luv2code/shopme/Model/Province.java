package com.luv2code.shopme.Model;

import java.io.Serializable;
import java.util.Objects;

public class Province implements Serializable {
    private String code;
    private String name;
    private String nameEn;
    private String fullNameEn;
    private String fullName;
    private String codeName;

    public Province(String code, String name, String nameEn, String fullNameEn, String fullName, String codeName) {
        this.code = code;
        this.name = name;
        this.nameEn = nameEn;
        this.fullNameEn = fullNameEn;
        this.fullName = fullName;
        this.codeName = codeName;
    }

    public Province(String fullName) {
        this.fullName = fullName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getFullNameEn() {
        return fullNameEn;
    }

    public void setFullNameEn(String fullNameEn) {
        this.fullNameEn = fullNameEn;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Province province = (Province) o;
        return Objects.equals(code, province.code) && Objects.equals(name, province.name) && Objects.equals(nameEn, province.nameEn) && Objects.equals(fullNameEn, province.fullNameEn) && Objects.equals(fullName, province.fullName) && Objects.equals(codeName, province.codeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, nameEn, fullNameEn, fullName, codeName);
    }
}
