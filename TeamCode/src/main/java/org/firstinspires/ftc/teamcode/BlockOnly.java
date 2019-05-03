package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.modules.DriveTrain;
import org.firstinspires.ftc.teamcode.modules.Lift;
import org.firstinspires.ftc.teamcode.modules.Pixy;

@Autonomous
public class BlockOnly extends LinearOpMode
{

    private Lift lift;
    private DriveTrain driveTrain;
    private ElapsedTime runtime = new ElapsedTime();
    private Pixy pixy;

    @Override
    public void runOpMode()
    {
        //initialize lift and driveTrain objects
        lift = new Lift(hardwareMap.get(DcMotor.class, "leftLiftMotor"), hardwareMap.get(DcMotor.class, "rightLiftMotor"), hardwareMap.get(Servo.class, "latch"));
        driveTrain = new DriveTrain(hardwareMap.get(DcMotor.class, "leftFrontMotor"), hardwareMap.get(DcMotor.class, "rightFrontMotor"), hardwareMap.get(DcMotor.class, "leftRearMotor"), hardwareMap.get(DcMotor.class, "rightRearMotor"));
        pixy = new Pixy(hardwareMap.i2cDeviceSynch.get("pixy"));
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
            driveTrain.drive(.15,0,0);
            wait(.2);
            driveTrain.drive(0,0,0);
        }
        int pos = 0;
        if(opModeIsActive())
        {
           pos = pixy.scanPos(3F, 15);
           telemetry.addData("Driving to position", pos);
           telemetry.update();
        }
        if(opModeIsActive())
        {
            driveTrain.drive(-.25, 0, 0);
            wait(.3);
            double turnTime = .6;
            double driveTime = 2.0;
            switch(pos)
            {
                case 1:
                    driveTrain.drive(0,-.35,0);
                    wait(turnTime);
                    driveTrain.drive(-.25,0,0);
                    wait(driveTime);
                    driveTrain.drive(.25,0,0);
                    wait(driveTime);
                    driveTrain.drive(0,.35,0);
                    wait(turnTime);
                    break;
                case 2:
                    driveTrain.drive(-.25,0,0);
                    wait(driveTime);
                    driveTrain.drive(.25,0,0);
                    wait(driveTime);
                    break;
                case 3:
                    driveTrain.drive(0,.35,0);
                    wait(turnTime);
                    driveTrain.drive(-.25,0,0);
                    wait(driveTime);
                    driveTrain.drive(.25,0,0);
                    wait(driveTime);
                    driveTrain.drive(0,-.35,0);
                    wait(turnTime);
                    break;
                case 0:
            }
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
