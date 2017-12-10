package org.example;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.IterativeRobot;
import org.strykeforce.thirdcoast.telemetry.TelemetryService;

public class Robot extends IterativeRobot {

  private final TelemetryService telemetryService = new TelemetryService();
  private CANTalon talon;

  @Override
  public void robotInit() {
    talon = new CANTalon(1);
    talon.changeControlMode(TalonControlMode.Voltage); // etc...
    telemetryService.register(talon);
    telemetryService.start();
  }

  @Override
  public void teleopPeriodic() {
    // do something with the Talon
  }
}
