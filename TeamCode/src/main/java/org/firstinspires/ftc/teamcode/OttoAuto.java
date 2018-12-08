package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class OttoAuto extends LinearOpMode
{

    private Lift lift;
    private DriveTrain driveTrain;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode()
    {
        //initialize lift and driveTrain objects
        lift = new Lift(hardwareMap.get(DcMotor.class, "leftLiftMotor"), hardwareMap.get(DcMotor.class, "rightLiftMotor"), hardwareMap.get(Servo.class, "latch"));
        driveTrain = new DriveTrain(hardwareMap.get(DcMotor.class, "leftFrontMotor"), hardwareMap.get(DcMotor.class, "rightFrontMotor"), hardwareMap.get(DcMotor.class, "leftRearMotor"), hardwareMap.get(DcMotor.class, "rightRearMotor"));

        telemetry.addData("Status", "Waiting for start.");
        telemetry.update();

        waitForStart();

        //lifts up to release clamp
        if(opModeIsActive())
        {
            lift.setPower(-.5F);
            telemetry.addData("Status", "Lifting.");
            telemetry.update();
            wait(.1);
        }
        //Opens clamp and provides resistance so lift won't slide back down
        if(opModeIsActive())
        {
            lift.openClamp();
            lift.setPower(-.2F);
            telemetry.addData("Status", "Opening clamp.");
            telemetry.update();
            wait(1.0);
        }
        //lowers robot to ground and unhooks lift
        if(opModeIsActive())
        {
            lift.setPower(.5F);
            telemetry.addData("Status", "Lowering");
            telemetry.update();
            wait(3.0);
            lift.setPower(0);
        }
        //backs up
        if(opModeIsActive())
        {
            driveTrain.drive(-.4, 0.0, 0.0);
            telemetry.addData("Status", "Backing up");
            telemetry.update();
            wait(.5);
            driveTrain.drive(0,0,0);
        }

        telemetry.addData("Status", "Complete");
        telemetry.update();
    }

    public void wait(double time)
    {

        runtime.reset();
        while(runtime.seconds() < time && opModeIsActive()) {}
    }
    
}
