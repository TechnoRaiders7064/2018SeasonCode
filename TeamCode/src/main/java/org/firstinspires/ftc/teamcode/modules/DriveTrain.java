package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DriveTrain
{
    private DcMotor frontLeftDrive;
    private DcMotor frontRightDrive;
    private DcMotor backLeftDrive;
    private DcMotor backRightDrive;

    private boolean precisionMode;
    private boolean isYReset;

    public DriveTrain(DcMotor frontLeftDrive, DcMotor frontRightDrive, DcMotor backLeftDrive, DcMotor backRightDrive)
    {
        this.frontLeftDrive = frontLeftDrive;
        this.frontRightDrive = frontRightDrive;
        this.backLeftDrive = backLeftDrive;
        this.backRightDrive = backRightDrive;

        this.frontLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        this.backLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        this.frontRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        this.backRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        precisionMode = false;
        isYReset = true;
    }

    public void update(Gamepad gamepad)
    {
        double modifier = 1.0;

        if(precisionMode)
            modifier = .25;

        double drive = gamepad.left_stick_y * modifier;
        double turn = gamepad.right_stick_x * modifier;
        double strafe = gamepad.left_stick_x * modifier;

        if(gamepad.y && isYReset)
        {
            precisionMode = !precisionMode;
            isYReset = false;
        }
        else if(!gamepad.y)
        {
            isYReset = true;
        }

        frontLeftDrive.setPower(Range.clip(drive + turn - strafe, -1.0, 1.0));
        frontRightDrive.setPower(Range.clip(drive - turn - strafe, -1.0, 1.0));
        backLeftDrive.setPower(Range.clip(drive + turn + strafe, -1.0, 1.0));
        backRightDrive.setPower(Range.clip(drive - turn + strafe, -1.0, 1.0));
    }

    public void drive(double drive, double turn, double strafe)
    {
        frontLeftDrive.setPower(Range.clip(-drive + turn - strafe, -1.0, 1.0));
        frontRightDrive.setPower(Range.clip(-drive - turn - strafe, -1.0, 1.0));
        backLeftDrive.setPower(Range.clip(-drive + turn + strafe, -1.0, 1.0));
        backRightDrive.setPower(Range.clip(-drive - turn + strafe, -1.0, 1.0));
    }

    public void encTurn(Telemetry telemetry, double speed, int distance)
    {
        int frontLeftStart = frontLeftDrive.getCurrentPosition();
        int frontRightStart = frontRightDrive.getCurrentPosition();
        int backLeftStart = backLeftDrive.getCurrentPosition();
        int backRightStart = backRightDrive.getCurrentPosition();
        drive(0, speed, 0);
        while(Math.abs(frontLeftDrive.getCurrentPosition()) - frontLeftStart < distance &&
              Math.abs(frontRightDrive.getCurrentPosition()) - frontRightStart < distance &&
              Math.abs(backLeftDrive.getCurrentPosition()) - backLeftStart < distance &&
              Math.abs(backRightDrive.getCurrentPosition()) - backRightStart < distance)
        {
            telemetry.addData("d", distance);
            telemetry.addData("fl", frontLeftDrive.getCurrentPosition() - frontLeftStart);
            telemetry.addData("fr", frontRightDrive.getCurrentPosition() - frontRightStart);
            telemetry.addData("bl", backLeftDrive.getCurrentPosition() - backLeftStart);
            telemetry.addData("br", backRightDrive.getCurrentPosition() - backRightStart);
            telemetry.update();
        }
        drive(0, 0, 0);
    }

    public void encDrive(Telemetry telemetry, double speed, int distance)
    {
        if(distance<0)
            throw new NumberFormatException("distance should always be positive");
        int frontLeftStart = frontLeftDrive.getCurrentPosition();
        int frontRightStart = frontRightDrive.getCurrentPosition();
        int backLeftStart = backLeftDrive.getCurrentPosition();
        int backRightStart = backRightDrive.getCurrentPosition();
        drive(speed, 0, 0);
        while(Math.abs(frontLeftDrive.getCurrentPosition()) - frontLeftStart < distance ||
              Math.abs(frontRightDrive.getCurrentPosition()) - frontRightStart < distance ||
              Math.abs(backLeftDrive.getCurrentPosition()) - backLeftStart < distance ||
              Math.abs(backRightDrive.getCurrentPosition()) - backRightStart < distance)
        {
            telemetry.addData("d", distance);
            telemetry.addData("fl", frontLeftDrive.getCurrentPosition() - frontLeftStart);
            telemetry.addData("fr", frontRightDrive.getCurrentPosition() - frontRightStart);
            telemetry.addData("bl", backLeftDrive.getCurrentPosition() - backLeftStart);
            telemetry.addData("br", backRightDrive.getCurrentPosition() - backRightStart);
            telemetry.update();
        }
        drive(0, 0, 0);
    }

}
