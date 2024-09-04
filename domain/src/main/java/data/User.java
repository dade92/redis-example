package data;


import java.io.Serializable;

public record User(
    String name
) implements Serializable {}
