package net.anatolich.mahjong.game.capabilities;

import net.anatolich.mahjong.game.GameSession;

/**
 * Provides a way for objects to dynamically introduce additional capabilities.
 * <p>
 * Consider we want to extend GameSession with ability to provide hints for 
 * available move. Without Capabilities we are forced to add hint management 
 * methods to game session interface. It forces each implementor of game session 
 * to implement those new methods. 
 * It is also possible that for some game types game session will not be able to 
 * provide hints but still the methods must be implemented.</p>
 * 
 * <p>In order to avoid this problem GameSession interface should provide instance of 
 * Capabilities to it's client (in fact it does: {@link GameSession#capabilities()}
 * so the client is able to question about supported capabilities and retrieve 
 * instances of it.</p>
 * 
 * <p>So game session client that supports hints may ask current game session about 
 * supporting of hints and either use hints support if it's available or fall back 
 * to mode without hints support.</p>
 * 
 * <pre><code>
 * if(gameSession.capabilities().supports(Hints.class)){
 *      HintAction hintAction = new HintAction(gameSession.capabilities.get(Hints.class)); 
 *      JMenuItem hintItem = new JMenuItem(hintAction);
 *      menuExtras.add(hintItem);
 * }
 * </code></pre>
 * 
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
