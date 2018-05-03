package org.example;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import java.net.URL;
import org.strykeforce.thirdcoast.swerve.SwerveDrive;
import org.strykeforce.thirdcoast.util.ExpoScale;

public class Robot extends TimedRobot {

  private final double DEADBAND = 0.06;
  private final double EXPO = 0.25;
  private final double WIMPIFY = 0.25;

  private final XboxController controller = new XboxController(0);
  private final ExpoScale forwardExpo = new ExpoScale(DEADBAND, EXPO);
  private final ExpoScale strafeExpo = new ExpoScale(DEADBAND, EXPO);
  private final ExpoScale azimuthExpo = new ExpoScale(DEADBAND, EXPO);
  private final Trigger gyroZeroTrigger =
      new Trigger() {
        @Override
        public boolean get() {
          return controller.getStartButton() && controller.getBackButton();
        }
      };

  private SwerveDrive drive;

  @Override
  public void robotInit() {
    URL thirdCoastConfig = Robot.class.getResource("/META-INF/thirdcoast.toml");
    SingletonComponent singletonComponent =
        DaggerSingletonComponent.builder().thirdCoastConfig(thirdCoastConfig).build();
    drive = singletonComponent.swerveDrive();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void teleopInit() {
    zeroGyro();
  }

  @Override
  public void teleopPeriodic() {
    double forward = -forwardExpo.apply(controller.getY(Hand.kLeft)) * WIMPIFY;
    double strafe = strafeExpo.apply(controller.getX(Hand.kLeft)) * WIMPIFY;
    double azimuth = azimuthExpo.apply(controller.getX(Hand.kRight)) * WIMPIFY;
    drive.drive(forward, strafe, azimuth);

    if (gyroZeroTrigger.hasActivated()) zeroGyro();
  }

  private void zeroGyro() {
    AHRS gyro = drive.getGyro();
    gyro.setAngleAdjustment(0);
    double adj = gyro.getAngle() % 360;
    gyro.setAngleAdjustment(-adj);
  }
}
