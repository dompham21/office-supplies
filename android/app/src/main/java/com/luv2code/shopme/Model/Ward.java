package com.luv2code.shopme.Model;

import java.io.Serializable;
import java.util.Objects;

public class Ward implements Serializable {
    private String code;
    private String name;
    private String nameEn;
    private String fullNameEn;
    private String fullName;
    private String codeName;
    private District district;

    public Ward(String fullName) {
        this.fullName = fullName;
    }

    public Ward(String code, String name, String nameEn, String fullNameEn, String fullName, String codeName, District district) {
        this.code = code;
        this.name = name;
        this.nameEn = nameEn;
        this.fullNameEn = fullNameEn;
        this.fullName = fullName;
        this.codeName = codeName;
        this.district = district;
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

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ward ward = (Ward) o;
        return Objects.equals(code, ward.code) && Objects.equals(name, ward.name) && Objects.equals(nameEn, ward.nameEn) && Objects.equals(fullNameEn, ward.fullNameEn) && Objects.equals(fullName, ward.fullName) && Objects.equals(codeName, ward.codeName) && Objects.equals(district, ward.district);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, nameEn, fullNameEn, fullName, codeName, district);
    }
}
