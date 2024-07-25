package ru.sb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "passengers")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean survived;
    @Enumerated(EnumType.STRING)
    @Column(name = "pclass")
    private Class pClass;
    private String name;
    private String sex;
    private Float age;
    @Column(name = "siblings_spouses")
    private Short siblingsSpouses;
    @Column(name = "parents_children")
    private Short parentsChildren;
    private Double fare;

    public Passenger() {
    }

    public Passenger(Long id, boolean survived, Class pClass, String name, String sex,
                     float age, short siblingsSpouses, short parentsChildren, double fare) {
        this.id = id;
        this.survived = survived;
        this.pClass = pClass;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.siblingsSpouses = siblingsSpouses;
        this.parentsChildren = parentsChildren;
        this.fare = fare;
    }

    public Passenger(boolean survived, Class pClass, String name, String sex,
                     float age, short siblingsSpouses, short parentsChildren, double fare) {
        this(null, survived, pClass, name, sex, age, siblingsSpouses, parentsChildren, fare);
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean isSurvived() {
        return survived;
    }

    public void setSurvived(boolean survived) {
        this.survived = survived;
    }

    public Class getPClass() {
        return pClass;
    }

    public void setPClass(Class pClass) {
        this.pClass = pClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
    }

    public Short getSiblingsSpouses() {
        return siblingsSpouses;
    }

    public void setSiblingsSpouses(short siblingsSpouses) {
        this.siblingsSpouses = siblingsSpouses;
    }

    public Short getParentsChildren() {
        return parentsChildren;
    }

    public void setParentsChildren(short parentsChildren) {
        this.parentsChildren = parentsChildren;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }


    public enum Class {
        First,
        Second,
        Third
    }

}
