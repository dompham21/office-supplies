package com.luv2code.shopme.Model;

import java.io.Serializable;
import java.util.Objects;

public class District implements Serializable {
    private String code;
    private String name;
    private String nameEn;
    private String fullNameEn;
    private String fullName;
    private String codeName;
    private Province province;

    public District(String fullName) {
        this.fullName = fullName;
    }

    public District(String code, String name, String nameEn, String fullNameEn, String fullName, String codeName, Province province) {
        this.code = code;
        this.name = name;
        this.nameEn = nameEn;
        this.fullNameEn = fullNameEn;
        this.fullName = fullName;
        this.codeName = codeName;
        this.province = province;
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

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        District district = (District) o;
        return Objects.equals(code, district.code) && Objects.equals(name, district.name) && Objects.equals(nameEn, district.nameEn) && Objects.equals(fullNameEn, district.fullNameEn) && Objects.equals(fullName, district.fullName) && Objects.equals(codeName, district.codeName) && Objects.equals(province, district.province);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, nameEn, fullNameEn, fullName, codeName, province);
    }
}
