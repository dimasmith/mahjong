package net.anatolich.mahjong.game.impl;

import java.util.HashMap;
import java.util.Map;
import net.anatolich.mahjong.game.capabilities.Capabilities;

/**
 * Manages additional capabilities of objects. 
 * Contains method of registering capability {@link #register }
 *
 * @author Dmytro Kovalchuk
 * @version 1.0
 * @since 1.0
 */
public class CapabilitiesImpl implements Capabilities {

    private Map<Class<?>, Object> supportedCapabilities;

    public CapabilitiesImpl() {
        supportedCapabilities = new HashMap<>();
    }

    @Override
    public <T> T get(Class<T> capability) {
        return (T) supportedCapabilities.get(capability);
    }

    @Override
    public boolean supports(Class<?> capability) {
        return supportedCapabilities.containsKey(capability);
    }

    public <T> void register(T capability) {
        supportedCapabilities.put(capability.getClass(), capability);
    }

    public void register(Object capability, Class<?> capabilityType) {
        supportedCapabilities.put(capabilityType, capability);
    }
}
