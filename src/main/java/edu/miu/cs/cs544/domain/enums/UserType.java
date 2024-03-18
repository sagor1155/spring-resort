package edu.miu.cs.cs544.domain.enums;

public enum UserType {
    ADMIN("ADMIN"), CLIENT("CLIENT");

    private String type;

    UserType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
