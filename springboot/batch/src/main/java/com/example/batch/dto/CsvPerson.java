package com.example.batch.dto;

public record CsvPerson(String firstName, String lastName) {
    @Override
    public String toString() {
        return "CsvPerson{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
