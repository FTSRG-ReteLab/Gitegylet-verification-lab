package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;

import static org.mockito.Mockito.*;

public class TrainSensorTest {
	TrainSensorImpl sensor;
	TrainUser mockTU;
	TrainController mockTC;
	
    @Before
    public void before() {
    	mockTU = mock(TrainUser.class);
    	mockTC = mock(TrainController.class);
    	sensor = new TrainSensorImpl(mockTC, mockTU);
    }

    @Test
    public void CheckAlarmState_IfSpeedLimitIsBelowZero() {
    	sensor.overrideSpeedLimit(-10);
    	verify(mockTU, times(1)).setAlarmState(true);
    }
    
    @Test
    public void CheckAlarmState_IfSpeedLimitIsOver500() {
    	sensor.overrideSpeedLimit(510);
    	verify(mockTU, times(1)).setAlarmState(true);
    }
    
    @Test
    public void CheckAlarmState_IfSpeedLimitIsJustOver50PercentOfReferenceSpeed() {
        when(mockTC.getReferenceSpeed()).thenReturn(100);
        
        sensor.overrideSpeedLimit(51);
        
        verify(mockTC, times(1)).getReferenceSpeed();
        verify(mockTU, times(1)).setAlarmState(false);
    }
    
    @Test
    public void CheckAlarmState_IfSpeedLimitIsJustBelow50PercentOfReferenceSpeed() {
        when(mockTC.getReferenceSpeed()).thenReturn(100);
        
        sensor.overrideSpeedLimit(49);
        
        verify(mockTC, times(1)).getReferenceSpeed();
        verify(mockTU, times(1)).setAlarmState(true);
    }
}
