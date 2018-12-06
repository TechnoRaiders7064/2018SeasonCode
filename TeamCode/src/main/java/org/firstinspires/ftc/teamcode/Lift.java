package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Lift {
    private DcMotor leftLiftMotor = null;
    private DcMotor rightLiftMotor = null;
    private Servo clampServo = null;

    private final int LIFTMAX = 1000;

    Lift(DcMotor leftLiftMotor, DcMotor rightLiftMotor, Servo clampServo)
    {
        this.leftLiftMotor = leftLiftMotor;
        this.rightLiftMotor = rightLiftMotor;
        this.clampServo = clampServo;

        this.leftLiftMotor.setDirection(DcMotor.Direction.FORWARD);
        this.rightLiftMotor.setDirection(DcMotor.Direction.REVERSE);
    }
    public void update(Gamepad gamepad, Telemetry telemetry)
    {
        int leftPosition = leftLiftMotor.getCurrentPosition();
        int rightPosition = rightLiftMotor.getCurrentPosition();

        if(gamepad.a)
        {
            clampServo.setPosition(.5);
        }
        else if(gamepad.b)
        {
            clampServo.setPosition(0);
        }
        //&& (leftPosition <= LIFTMAX && rightPosition <= LIFTMAX)
        if(gamepad.dpad_up )
        {
            leftLiftMotor.setPower(.8);
            //leftLiftMotor.setPower(proportationalControl(leftPosition, rightPosition, 1F, 0));
            rightLiftMotor.setPower(.8);
        }
        //&& leftPosition >= 0 && rightPosition >= 0
        else if(gamepad.dpad_down )
        {
            leftLiftMotor.setPower(-.8);
            //leftLiftMotor.setPower(proportationalControl(leftPosition, rightPosition, 1F, 0));
            rightLiftMotor.setPower(-.8);
        }
//        else if(leftPosition >= rightPosition + 25 || leftPosition <= rightPosition - 25)
//        {
//            leftLiftMotor.setPower(0);
            //leftLiftMotor.setPower(proportationalControl(leftPosition, rightPosition, 1F, 0));
//            rightLiftMotor.setPower(0);
//        }
        else
        {
            leftLiftMotor.setPower(0);
            rightLiftMotor.setPower(0);
        }

        telemetry.addData("Motors", "left enc (%.2f), right enc (%.2f)", (double) leftPosition, (double) rightPosition);
    }
    private float proportationalControl(int current, int desired, float gain, float base)
    {
         return Range.clip(base + gain*(desired-current),-1.0F, 1.0F);
    }
    public void setPower(float power)
    {
        leftLiftMotor.setPower(power);
        rightLiftMotor.setPower(power);
    }
    public void openClamp()
    {
        clampServo.setPosition(0);
    }
    public void closeClamp()
    {
        clampServo.setPosition(.5);
    }
}
