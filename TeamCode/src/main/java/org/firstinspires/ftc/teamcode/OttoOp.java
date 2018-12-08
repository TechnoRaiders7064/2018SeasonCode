package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class OttoOp extends OpMode
{

    private ElapsedTime runtime = new ElapsedTime();
    private Lift lift;
    private DriveTrain driveTrain;

    @Override
    public void init()
    {
        telemetry.addData("Status", "Initializing");
        lift = new Lift(hardwareMap.get(DcMotor.class, "leftLiftMotor"), hardwareMap.get(DcMotor.class, "rightLiftMotor"), hardwareMap.get(Servo.class, "latch"));
        driveTrain = new DriveTrain(hardwareMap.get(DcMotor.class, "leftFrontMotor"), hardwareMap.get(DcMotor.class, "rightFrontMotor"), hardwareMap.get(DcMotor.class, "leftRearMotor"), hardwareMap.get(DcMotor.class, "rightRearMotor"));
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void init_loop()
    {
    }

    @Override
    public void start()
    {
        runtime.reset();
    }

    @Override
    public void loop()
    {
        lift.update(gamepad1, telemetry);
        driveTrain.update(gamepad1, telemetry);
    }

    @Override
    public void stop()
    {
    }


}
