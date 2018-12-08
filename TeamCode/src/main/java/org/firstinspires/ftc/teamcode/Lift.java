package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Lift
{
    private DcMotor leftLiftMotor = null;
    private DcMotor rightLiftMotor = null;
    private Servo clampServo = null;
    private boolean isClosed;

    private final int LIFTMAX = 1000;

    Lift(DcMotor leftLiftMotor, DcMotor rightLiftMotor, Servo clampServo)
    {
        this.leftLiftMotor = leftLiftMotor;
        this.rightLiftMotor = rightLiftMotor;
        this.clampServo = clampServo;
        this.leftLiftMotor.setDirection(DcMotor.Direction.FORWARD);
        this.rightLiftMotor.setDirection(DcMotor.Direction.REVERSE);
        closeClamp();
    }

    public void update(Gamepad gamepad, Telemetry telemetry)
    {
        if(gamepad.a)
        {
            closeClamp();
        }
        else if(gamepad.b)
        {
            openClamp();
        }
        if(gamepad.dpad_up && !isClosed)
        {
            leftLiftMotor.setPower(.8);
            rightLiftMotor.setPower(.8);
        }
        else if(gamepad.dpad_down)
        {
            leftLiftMotor.setPower(-.8);
            rightLiftMotor.setPower(-.8);
        }
        else
        {
            leftLiftMotor.setPower(0);
            rightLiftMotor.setPower(0);
        }

    }

    public void setPower(float power)
    {
        leftLiftMotor.setPower(power);
        rightLiftMotor.setPower(power);
    }

    public void openClamp()
    {
        clampServo.setPosition(0);
        isClosed = false;
    }

    public void closeClamp()
    {
        clampServo.setPosition(.5);
        isClosed = true;
    }
}
