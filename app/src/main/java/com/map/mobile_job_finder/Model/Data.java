package com.map.mobile_job_finder.Model;

public class Data {
    String title;
    String description;
    String skills;
    String salary;

    String id;
    String date;
    String name;
    String email;



    public Data(String title, String description, String skills, String salary, String id, String date) {
        this.title = title;
        this.description = description;
        this.skills = skills;
        this.salary = salary;
        this.id = id;
        this.date = date;
        this.name=name;
        this.email=email;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {return name;}

    public void setName(String name){this.name=name;}

    public String getEmail() { return email;}

    public void setEmail( String email){this.email=email;}
}
