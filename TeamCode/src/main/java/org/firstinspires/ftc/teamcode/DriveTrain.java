package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DriveTrain {
    DcMotor frontLeftDrive = null;
    DcMotor frontRightDrive = null;
    DcMotor backLeftDrive = null;
    DcMotor backRightDrive = null;

    DriveTrain(DcMotor frontLeftDrive, DcMotor frontRightDrive, DcMotor backLeftDrive, DcMotor backRightDrive)
    {
        this.frontLeftDrive =  frontLeftDrive;
        this.frontRightDrive = frontRightDrive;
        this.backLeftDrive = backLeftDrive;
        this.backRightDrive = backRightDrive;

        this.frontLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        this.backLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        this.frontRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        this.backRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void update(Gamepad gamepad, Telemetry telemetry)
    {
        double drive = gamepad.left_stick_y;
        double turn = gamepad.right_stick_x;
        double strafe = gamepad.left_stick_x;

        frontLeftDrive.setPower(Range.clip(drive + turn - strafe, -1.0, 1.0) );
        frontRightDrive.setPower(Range.clip(drive - turn - strafe, -1.0, 1.0) );
        backLeftDrive.setPower(Range.clip(drive + turn + strafe, -1.0, 1.0) );
        backRightDrive.setPower(Range.clip(drive - turn + strafe, -1.0, 1.0) );
    }



}
