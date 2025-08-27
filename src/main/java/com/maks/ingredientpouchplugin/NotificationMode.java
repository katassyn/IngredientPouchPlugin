package com.maks.ingredientpouchplugin;

/**
 * Enum representing the different notification modes for item pickups
 */
public enum NotificationMode {
    /**
     * Show notifications for all items
     */
    ALL(2),
    
    /**
     * Show notifications only for best/rare items
     */
    BEST(1),
    
    /**
     * Don't show any notifications
     */
    OFF(0);
    
    private final int value;
    
    NotificationMode(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    /**
     * Get NotificationMode from integer value
     * @param value The integer value (0, 1, or 2)
     * @return The corresponding NotificationMode, defaults to BEST if invalid
     */
    public static NotificationMode fromValue(int value) {
        for (NotificationMode mode : values()) {
            if (mode.getValue() == value) {
                return mode;
            }
        }
        return BEST; // Default
    }
}