package com.example.demo;

public class Classes {
    private String className;
    private String classTime;
    private String classCoach;
    private int classRoom;
    public Classes(String className, String classTime,
                    String classCoach,int classRoom) {

       this.className=className;
       this.classTime=classTime;
       this.classCoach=classCoach;
       this.classRoom=classRoom;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public String getClassCoach() {
        return classCoach;
    }

    public void setClassCoach(String classCoach) {
        this.classCoach = classCoach;
    }

    public int getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(int classRoom) {
        this.classRoom = classRoom;
    }
}
