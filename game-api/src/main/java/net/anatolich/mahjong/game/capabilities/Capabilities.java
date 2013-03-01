package net.anatolich.mahjong.game.capabilities;

/**
 * Allows to retrieve additional object capabilities.
 * @author Dmytro Kovalchuk
 * @version 1.0
 * @since 1.0
 */
public interface Capabilities {

    /**
     * Get capability by it's class. 
     * @param <T>
     * @param capability
     * @return capability instance or null if capability was not registered.
     */
    <T> T get(Class<T> capability);

    /**
     * Checks whether capability of given type is supported.
     * @param capability
     * @return 
     */
    boolean supports(Class<?> capability);
    
}
