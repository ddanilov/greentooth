package io.github.ddanilov.greentooth;

import static android.bluetooth.BluetoothProfile.HEALTH;
import static android.bluetooth.BluetoothProfile.HEARING_AID;
import static android.bluetooth.BluetoothProfile.STATE_CONNECTED;
import static android.bluetooth.BluetoothProfile.STATE_DISCONNECTED;
import static io.github.ddanilov.greentooth.Util.isBluetoothEnabled;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BluetoothUtilUnitTests {

    @Mock
    BluetoothAdapter bluetoothAdapter;

    @Test
    public void testIsBluetoothEnabled() {
        int[] onStates = {BluetoothAdapter.STATE_ON, BluetoothAdapter.STATE_TURNING_ON};
        int[] offStates = {BluetoothAdapter.STATE_TURNING_OFF, BluetoothAdapter.STATE_OFF};
        for (int state : onStates) {
            when(bluetoothAdapter.getState()).thenReturn(state);
            assertTrue(isBluetoothEnabled(bluetoothAdapter));
        }
        for (int state : offStates) {
            when(bluetoothAdapter.getState()).thenReturn(state);
            assertFalse(isBluetoothEnabled(bluetoothAdapter));
        }
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testIsBluetoothConnectedAPI29() {
        setMockConnectionState(HEARING_AID, STATE_CONNECTED);
        assertTrue(isConnected());
        setMockConnectionState(HEARING_AID, STATE_DISCONNECTED);
        //Health profile is deprecated at this API level
        setMockConnectionState(HEALTH, STATE_CONNECTED);
        assertFalse(isConnected());
    }

    @Test
    public void testSetBluetoothProfiles() {
        int[] testArray = {1, 2, 3};
        Util.setBluetoothProfiles(testArray);
        assertEquals(testArray, Util.getBluetoothProfiles());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testGetBluetoothProfiles() {
        int[] testArray = Util.getBluetoothProfiles();
        assertThat(testArray, equalTo(new int[]{BluetoothProfile.HEADSET, BluetoothProfile.A2DP, BluetoothProfile.HEALTH}));
    }

    private void setMockConnectionState(int profile, int state) {
        when(bluetoothAdapter.getProfileConnectionState(profile)).thenReturn(state);
    }

    private boolean isConnected() {
        return Util.isBluetoothConnected(bluetoothAdapter);
    }

}
