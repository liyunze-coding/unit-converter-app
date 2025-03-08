package com.deakin.unitConverter;

import java.util.HashMap;
import java.util.Map;

// generated using ChatGPT
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
        weightFactors.put("ton", 907.184);
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
            if (!lengthFactors.containsKey(fromUnit) || !lengthFactors.containsKey(toUnit)) {
                throw new IllegalArgumentException("Invalid length unit: " + fromUnit + " or " + toUnit);
            }
            return value * lengthFactors.get(fromUnit) / lengthFactors.get(toUnit);
        } else if (unitType.equalsIgnoreCase("weight")) {
            if (!weightFactors.containsKey(fromUnit) || !weightFactors.containsKey(toUnit)) {
                throw new IllegalArgumentException("Invalid weight unit: " + fromUnit + " or " + toUnit);
            }
            return value * weightFactors.get(fromUnit) / weightFactors.get(toUnit);
        } else if (unitType.equalsIgnoreCase("temperature")) {
            return convertTemperature(value, fromUnit, toUnit);
        }
        throw new IllegalArgumentException("Invalid unit type: " + unitType);
    }


    /**
     * Converts temperature between Celsius, Fahrenheit, and Kelvin.
     */
    private static double convertTemperature(double value, String from, String to) {
        if (from.equals("C")) {
            if (to.equals("F")) return (value * 9/5) + 32;
            if (to.equals("K")) return value + 273.15;
        } else if (from.equals("F")) {
            if (to.equals("C")) return (value - 32) * 5/9;
            if (to.equals("K")) return (value - 32) * 5/9 + 273.15;
        } else if (from.equals("K")) {
            if (to.equals("C")) return value - 273.15;
            if (to.equals("F")) return (value - 273.15) * 9/5 + 32;
        }
        return value; // If fromUnit == toUnit
    }
}
