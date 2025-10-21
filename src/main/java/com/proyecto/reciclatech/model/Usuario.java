package com.proyecto.reciclatech.model;

public class Usuario {
    private String id;
    private String carnet;
    private String carrera;
    private String password;

    public Usuario() {}

    public Usuario(String carnet, String carrera, String password) {
        this.carnet = carnet;
        this.carrera = carrera;
        this.password = password;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCarnet() { return carnet; }
    public void setCarnet(String carnet) { this.carnet = carnet; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
