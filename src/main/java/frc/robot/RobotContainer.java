/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ArmUpThenDownCommand;
import frc.robot.commands.LowerArmCommand;
import frc.robot.commands.RaiseArmCommand;
import frc.robot.commands.StopArmCommand;
import frc.robot.subsystems.ArmSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ArmSubsystem mArm = new ArmSubsystem();

  private final RaiseArmCommand mRaiseCommand = new RaiseArmCommand(mArm);
  private final LowerArmCommand mLowerCommand = new LowerArmCommand(mArm);
  private final StopArmCommand mStopCommand = new StopArmCommand(mArm);

  private final ArmUpThenDownCommand mUpThenDownCommand = new ArmUpThenDownCommand(mArm);

  private final Joystick mDriveJoystick = new Joystick(Constants.JOYSTICK_USB);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // You can use commands in sequence (one after the other) with one action.
    // Both chunks of code below perform the same action on different buttons.

    // One way is to create a new class that uses the commands you want to use.
    // Refer to the ArmUpThenDownCommand class for an example.
    // You usually only have to do this for more complicated compound commands.
    JoystickButton classButton = new JoystickButton(mDriveJoystick, Constants.ARM_UP_AND_DOWN_CLASS_JOYSTICK_BUTTON);
    classButton.whenPressed(mUpThenDownCommand);

    // WPILib also allows in-lining, where you don't have to make a new class for the compound command.
    // This is generally the preferred solution.
    JoystickButton inlineButton = new JoystickButton(mDriveJoystick, Constants.ARM_UP_AND_DOWN_INLINE_JOYSTICK_BUTTON);
    inlineButton.whenPressed(mRaiseCommand
        .andThen(new WaitCommand(5).andThen(mLowerCommand).andThen(new WaitCommand(5).andThen(mStopCommand))));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
