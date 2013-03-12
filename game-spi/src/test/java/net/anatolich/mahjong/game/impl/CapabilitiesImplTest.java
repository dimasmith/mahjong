package net.anatolich.mahjong.game.impl;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Dmytro Kovalchuk
 */
public class CapabilitiesImplTest {

    private CapabilitiesImpl capabilities;

    @Before
    public void createCapabilities() {
        this.capabilities = new CapabilitiesImpl();
    }

    @Test
    public void addCapabilityAndEnsureItIsAvailable() {

        SomeCapability capability = new SomeCapability();
        capabilities.register(capability);

        assertCapabilityPresent(SomeCapability.class, capability);
    }

    @Test
    public void addTwoCapabilitiesAndEnsureBothArePresent() {

        SomeCapability someCapability = new SomeCapability();
        OtherCapability otherCapability = new OtherCapability();

        capabilities.register(someCapability);
        capabilities.register(otherCapability);

        assertCapabilityPresent(SomeCapability.class, someCapability);
        assertCapabilityPresent(OtherCapability.class, otherCapability);
    }

    @Test
    public void addSomeCapabilityAndCheckForOther() {

        SomeCapability someCapability = new SomeCapability();

        capabilities.register(someCapability);

        assertCapabilityPresent(SomeCapability.class, someCapability);
        assertCapabilityMissing(OtherCapability.class);
    }

    @Test
    public void overrideCapabilityWithAnotherInstance() {

        SomeCapability someCapability = new SomeCapability();
        SomeCapability someCapability2 = new SomeCapability();

        capabilities.register(someCapability);
        capabilities.register(someCapability2);

        assertCapabilityPresent(SomeCapability.class, someCapability2);
    }

    @Test
    public void registerCapabilityByType() {
        SomeCapabilityImpl capabilityImpl = new SomeCapabilityImpl();

        capabilities.register( capabilityImpl, SomeCapability.class );

        assertCapabilityPresent( SomeCapability.class, capabilityImpl );
    }

    private <T> void assertCapabilityPresent(final Class<T> capabilityType, T capability) {
        assertTrue(String.format("%s must be present", capabilityType.getName()), hasCapability(capabilityType));
        assertEquals(String.format("%s must be present", capabilityType.getName()), capability, getCapability(capabilityType));
    }

    private <T> void assertCapabilityMissing(final Class<T> capabilityType) {
        assertFalse(String.format("%s must not be present", capabilityType.getName()), hasCapability(capabilityType));
        assertNull(getCapability(capabilityType));
    }

    private <T> boolean hasCapability(final Class<T> capabilityType) {
        return capabilities.supports(capabilityType);
    }

    private <T> T getCapability(final Class<T> capabilityType) {
        return capabilities.get(capabilityType);
    }

    private static class OtherCapability {
    }

    private static class SomeCapability {}

    private static class SomeCapabilityImpl extends SomeCapability {}
}
