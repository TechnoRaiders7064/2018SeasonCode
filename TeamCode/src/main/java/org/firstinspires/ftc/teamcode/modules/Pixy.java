package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Pixy
{
    private I2cDeviceSynch pixy;

    private byte[] getBlocks = new byte[]{(byte) 0xAE, (byte) 0xC1,
                                          (byte) 0x20, (byte) 0x02,
                                          (byte) 0xFF, (byte) 0x01};

    private ElapsedTime timer = new ElapsedTime();

    private int x;
    private int y;

    public Pixy(I2cDeviceSynch pixy)
    {
        this.pixy = pixy;
        pixy.setI2cAddress(I2cAddr.create7bit(0x54));
        pixy.setReadWindow(new I2cDeviceSynch.ReadWindow(1, 26, I2cDeviceSynch.ReadMode.REPEAT));
        pixy.engage();
    }

    public void getBlocks()
    {
        pixy.write(0, getBlocks);
    }

    public void update()
    {
        byte[] bytes = pixy.read(0, 18);
        if(bytes[0] == 85 && bytes[1] == -86 && bytes[2] == 85)
        {
            x = 256 * bytes[9] + (bytes[8] < 0 ? 256 + bytes[8] : bytes[8]);
            y = 256 * bytes[11] + (bytes[10] < 0 ? 256 + bytes[10] : bytes[10]);
        }
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int scanPos(float time, int minY)
    {
        timer.reset();
        int x = 0;
        int count = 0;
        while(timer.seconds() < time)
        {
            getBlocks();
            update();
            if(getY() >= minY)
            {
                x += getX();
                count++;
            }
        }
        if(count != 0)
            x /= count;
        if(x >= 0 && x < 75)
            return 1;
        if(x >= 75 && x < 225)
            return 2;
        if(x >= 225 && x < 300)
            return 3;
        return 0;
    }
}
