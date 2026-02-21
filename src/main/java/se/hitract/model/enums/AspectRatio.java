package se.hitract.model.enums;

/**
 * Enum representing common aspect ratios for images.
 * Provides utilities to calculate, match, and work with aspect ratios.
 */
public enum AspectRatio {
    // Landscape ratios
    ULTRA_WIDE_21_9(21, 9, "21:9 Ultra Wide"),
    WIDESCREEN_16_9(16, 9, "16:9 Widescreen"),
    CLASSIC_PHOTO_3_2(3, 2, "3:2 Classic Photo"),
    TRADITIONAL_4_3(4, 3, "4:3 Traditional"),

    // Square
    SQUARE_1_1(1, 1, "1:1 Square"),

    // Portrait ratios
    PORTRAIT_TRADITIONAL_3_4(3, 4, "3:4 Portrait Traditional"),
    PORTRAIT_PHOTO_2_3(2, 3, "2:3 Portrait Photo"),
    PORTRAIT_MOBILE_9_16(9, 16, "9:16 Portrait Mobile"),

    // Custom/Unknown
    CUSTOM(0, 0, "Custom");

    private final int width;
    private final int height;
    private final String displayName;
    private final double ratio;

    AspectRatio(int width, int height, String displayName) {
        this.width = width;
        this.height = height;
        this.displayName = displayName;
        this.ratio = (height == 0) ? 0 : (double) width / height;
    }

    /**
     * Gets the aspect ratio as a decimal value (width/height).
     */
    public double getRatio() {
        return ratio;
    }

    /**
     * Gets the width component of the aspect ratio.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height component of the aspect ratio.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the display name of this aspect ratio.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Calculates the aspect ratio from width and height.
     */
    public static double calculateRatio(int width, int height) {
        if (height == 0) {
            throw new IllegalArgumentException("Height cannot be zero");
        }
        return (double) width / height;
    }

    /**
     * Finds the closest matching aspect ratio enum for the given width and height.
     * Uses a tolerance of 0.05 to account for slight variations.
     */
    public static AspectRatio fromDimensions(int width, int height) {
        return fromRatio(calculateRatio(width, height));
    }

    /**
     * Finds the closest matching aspect ratio enum for the given ratio.
     * Uses a tolerance of 0.05 to account for slight variations.
     */
    public static AspectRatio fromRatio(double ratio) {
        return fromRatio(ratio, 0.05);
    }

    /**
     * Finds the closest matching aspect ratio enum for the given ratio with custom tolerance.
     *
     * @param ratio The aspect ratio to match
     * @param tolerance The maximum difference allowed for a match
     * @return The matching AspectRatio enum, or CUSTOM if no match found
     */
    public static AspectRatio fromRatio(double ratio, double tolerance) {
        AspectRatio closest = CUSTOM;
        double minDifference = Double.MAX_VALUE;

        for (AspectRatio ar : values()) {
            if (ar == CUSTOM) continue;

            double difference = Math.abs(ar.ratio - ratio);
            if (difference <= tolerance && difference < minDifference) {
                minDifference = difference;
                closest = ar;
            }
        }

        return closest;
    }

    /**
     * Calculates the height for a given width maintaining this aspect ratio.
     */
    public int calculateHeight(int width) {
        if (this == CUSTOM || ratio == 0) {
            throw new IllegalStateException("Cannot calculate dimensions for CUSTOM aspect ratio");
        }
        return (int) Math.round(width / ratio);
    }

    /**
     * Calculates the width for a given height maintaining this aspect ratio.
     */
    public int calculateWidth(int height) {
        if (this == CUSTOM || ratio == 0) {
            throw new IllegalStateException("Cannot calculate dimensions for CUSTOM aspect ratio");
        }
        return (int) Math.round(height * ratio);
    }

    /**
     * Checks if this is a landscape orientation (width > height).
     */
    public boolean isLandscape() {
        return ratio > 1.0;
    }

    /**
     * Checks if this is a portrait orientation (height > width).
     */
    public boolean isPortrait() {
        return ratio < 1.0;
    }

    /**
     * Checks if this is a square orientation (width == height).
     */
    public boolean isSquare() {
        return Math.abs(ratio - 1.0) < 0.001;
    }

    @Override
    public String toString() {
        return displayName + " (" + ratio + ")";
    }
}
