package ru.sb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "passengers")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean survived;
    @Enumerated(EnumType.STRING)
    @Column(name = "pclass")
    private Class pClass;
    private String name;
    private String sex;
    private float age;
    @Column(name = "siblings_spouses")
    private short siblingSpouses;
    @Column(name = "parents_children")
    private short parentsChildren;
    private double fare;

    public Passenger() {
    }

    public Passenger(Long id, boolean survived, Class pClass, String name, String sex,
                     float age, short siblingSpouses, short parentsChildren, double fare) {
        this.id = id;
        this.survived = survived;
        this.pClass = pClass;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.siblingSpouses = siblingSpouses;
        this.parentsChildren = parentsChildren;
        this.fare = fare;
    }

    public Passenger(boolean survived, Class pClass, String name, String sex,
                     float age, short siblingSpouses, short parentsChildren, double fare) {
        this.survived = survived;
        this.pClass = pClass;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.siblingSpouses = siblingSpouses;
        this.parentsChildren = parentsChildren;
        this.fare = fare;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isSurvived() {
        return survived;
    }

    public void setSurvived(boolean survived) {
        this.survived = survived;
    }

    public String getPClass() {
        return pClass.name();
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

    public float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
    }

    public short getSiblingSpouses() {
        return siblingSpouses;
    }

    public void setSiblingSpouses(short siblingSpouses) {
        this.siblingSpouses = siblingSpouses;
    }

    public short getParentsChildren() {
        return parentsChildren;
    }

    public void setParentsChildren(short parentsChildren) {
        this.parentsChildren = parentsChildren;
    }

    public double getFare() {
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
