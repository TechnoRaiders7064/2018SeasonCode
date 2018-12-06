package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class OttoAuto extends LinearOpMode {

    private Lift lift;
    private DriveTrain driveTrain;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        lift = new Lift(hardwareMap.get(DcMotor.class, "leftLiftMotor"), hardwareMap.get(DcMotor.class, "rightLiftMotor"), hardwareMap.get(Servo.class, "latch"));
        driveTrain = new DriveTrain(hardwareMap.get(DcMotor.class, "leftFrontMotor"),hardwareMap.get(DcMotor.class, "rightFrontMotor"),hardwareMap.get(DcMotor.class, "leftRearMotor"),hardwareMap.get(DcMotor.class, "rightRearMotor"));
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        waitForStart();

        if(opModeIsActive())
            lift.setPower(-.5F);
        wait(.1);
        if(opModeIsActive())
            lift.openClamp();
        if(opModeIsActive())
            lift.setPower(-.2F);
        wait(1.0);
        if(opModeIsActive())
            lift.setPower(.5F);
        wait(3.0);
        lift.setPower(0);
        if(opModeIsActive())
            driveTrain.drive(-.4, 0.0, 0.0);
        wait(.5);
        telemetry.addData("Status", "Complete");
        telemetry.update();
    }
    public void wait(double time)
    {
        runtime.reset();
        while(runtime.seconds() < time && opModeIsActive()){}
    }

}
