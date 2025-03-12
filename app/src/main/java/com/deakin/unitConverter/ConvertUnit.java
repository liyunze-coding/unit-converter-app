package com.deakin.unitConverter;

import java.util.HashMap;
import java.util.Map;

// Generated using ChatGPT
public class ConvertUnit {
    // Conversion factors for length (relative to meters)
    private static final Map<String, Double> lengthFactors = new HashMap<>();
    static {
        lengthFactors.put("inch", 0.0254);
        lengthFactors.put("foot", 0.3048);
        lengthFactors.put("yard", 0.9144);
        lengthFactors.put("mile", 1609.34);
        lengthFactors.put("cm", 0.01);
        lengthFactors.put("meter", 1.0);
        lengthFactors.put("km", 1000.0);
    }

    // Conversion factors for weight (relative to kilograms)
    private static final Map<String, Double> weightFactors = new HashMap<>();
    static {
        weightFactors.put("lbs", 0.453592);
        weightFactors.put("kg", 1.0);
        weightFactors.put("ounce", 0.0283495);
        weightFactors.put("g", 0.001);
        weightFactors.put("ton", 907.185);
    }

    // Hashmap not used for temperature
    // because it involves multiplication/division and addition/subtraction
    /**
     * Converts temperature between Celsius, Fahrenheit, and Kelvin.
     */
    private static double convertTemperature(double value, String from, String to) {
        switch (from) {
            case "C":
                if (to.equals("F")) return (value * 1.8) + 32;
                if (to.equals("K")) return value + 273.15;
                break;
            case "F":
                if (to.equals("C")) return (value - 32) / 1.8;
                if (to.equals("K")) return ((value - 32) / 1.8) + 273.15;
                break;
            case "K":
                if (to.equals("C")) return value - 273.15;
                if (to.equals("F")) return ((value - 273.15) / 1.8) + 32;
                break;
        }
        return value; // If fromUnit == toUnit
    }

    /**
     * Converts between units.
     *
     * @param value      The input value.
     * @param fromUnit   The unit of the input value.
     * @param toUnit     The desired output unit.
     * @param unitType   The type of unit (LENGTH, WEIGHT, TEMPERATURE).
     * @return Converted value.
     */
    public static double convert(double value, String fromUnit, String toUnit, String unitType) {
        if (unitType.equalsIgnoreCase("length")) {
            Double fromFactor = lengthFactors.get(fromUnit);
            Double toFactor = lengthFactors.get(toUnit);

            if (fromFactor == null || toFactor == null) {
                throw new IllegalArgumentException("Invalid length unit: " + fromUnit + " or " + toUnit);
            }

            return value * fromFactor / toFactor;
        } else if (unitType.equalsIgnoreCase("weight")) {
            Double fromFactor = weightFactors.get(fromUnit);
            Double toFactor = weightFactors.get(toUnit);

            if (fromFactor == null || toFactor == null) {
                throw new IllegalArgumentException("Invalid weight unit: " + fromUnit + " or " + toUnit);
            }

            return value * fromFactor / toFactor;
        } else if (unitType.equalsIgnoreCase("temperature")) {
            return convertTemperature(value, fromUnit, toUnit);
        }

        throw new IllegalArgumentException("Invalid unit type: " + unitType);
    }
}
