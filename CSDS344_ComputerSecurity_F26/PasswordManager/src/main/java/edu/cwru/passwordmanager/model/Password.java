package edu.cwru.passwordmanager.model;

public class Password {
    private String label;
    private String password;

    public Password(String label, String password) {
        this.label = label;
        this.password = password;
    }

    @Override
    public String toString() {
        return this.label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
