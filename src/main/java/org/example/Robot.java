package org.example;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import org.strykeforce.thirdcoast.telemetry.TelemetryService;

public class Robot extends IterativeRobot {

  private TelemetryService telemetryService;
  private CANTalon talon;

  @Override
  public void robotInit() {
    telemetryService = new TelemetryService();

    talon = new CANTalon(1);
    telemetryService.register(talon);

    telemetryService.start();
  }

}
