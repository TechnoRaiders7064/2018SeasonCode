package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class Arm
{
    private DcMotor armMotor;
    private Servo intakeServo;
    private Servo extenderServo;
    private ElapsedTime timer = new ElapsedTime();
    private int lastPos=0;
    public Arm(DcMotor armMotor, Servo intakeServo, Servo extenderServo)
    {
        this.armMotor = armMotor;
        this.intakeServo = intakeServo;
        this.extenderServo = extenderServo;

        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void update(Gamepad gamepad)
    {
        armMotor.setPower(Range.clip(gamepad.right_stick_y/1.5, -1.0, 1.0));
        extenderServo.setPosition(Range.clip(.5 + gamepad.right_trigger/2 - gamepad.left_trigger/2, 0, 1.0));
        intakeServo.setPosition(Range.clip(.5 + gamepad.left_stick_y/2, 0, 1.0));

        lastPos = armMotor.getCurrentPosition();
        timer.reset();
    }
}
