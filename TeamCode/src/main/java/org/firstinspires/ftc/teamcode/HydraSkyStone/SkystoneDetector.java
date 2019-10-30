package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.*;
import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import java.util.ArrayList;

import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.red;



public class SkystoneDetector extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    VuforiaLocalizer vuforia;
    LinearOpMode opMode;

    public SkystoneDetector(LinearOpMode opMode) {
        this.opMode = opMode;
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters();
        params.vuforiaLicenseKey = "AQvLCbX/////AAABmTGnnsC2rUXvp1TAuiOSac0ZMvc3GKI93tFoRn4jPzB3uSMiwj75PNfUU6MaVsNZWczJYOep8LvDeM/3hf1+zO/3w31n1qJTtB2VHle8+MHWNVbNzXKLqfGSdvXK/wYAanXG2PBSKpgO1Fv5Yg27eZfIR7QOh7+J1zT1iKW/VmlsVSSaAzUSzYpfLufQDdE2wWQYrs8ObLq2kC37CeUlJ786gywyHts3Mv12fWCSdTH5oclkaEXsVC/8LxD1m+gpbRc2KC0BXnlwqwA2VqPSFU91vD8eCcD6t2WDbn0oJas31PcooBYWM6UgGm9I2plWazlIok72QG/kOYDh4yXOT4YXp1eYh864e8B7mhM3VclQ";
        params.cameraName = opMode.hardwareMap.get(WebcamName.class, "Webcam 1");
        vuforia = ClassFactory.getInstance().createVuforia(params);
        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true); //enables RGB565 format for the image
        vuforia.setFrameQueueCapacity(4); //tells VuforiaLocalizer to only store one frame at a time
    }

    public Bitmap getImage() throws InterruptedException {
        VuforiaLocalizer.CloseableFrame frame = vuforia.getFrameQueue().take();
        long numImages = frame.getNumImages();
        Image rgb = null;
        for (int i = 0; i < numImages; i++) {
            Image img = frame.getImage(i);
            int fmt = img.getFormat();
            if (fmt == PIXEL_FORMAT.RGB565) {
                rgb = frame.getImage(i);
                break;
            }
        }
        Bitmap bm = Bitmap.createBitmap(rgb.getWidth(), rgb.getHeight(), Bitmap.Config.RGB_565);
        bm.copyPixelsFromBuffer(rgb.getPixels());
        return bm;
    }

    public String getSkystonePos() throws InterruptedException {
        Bitmap rgbImage = getImage();
        // store the x values of pixels in relative thirds of the image
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> center = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();
        // (0,0) = top left
        for (int y = 0; y < rgbImage.getHeight(); y++) {
            for (int x = 0; x < rgbImage.getWidth(); x++) {
                int pixel = rgbImage.getPixel(x, y);
                // if sksytone color is detected in the pixel
                // image is flipped, so first third is the right hand side
                if (red(pixel) <= 69 && green(pixel) <= 69 && blue(pixel) <= 69) {
                    if (x < rgbImage.getWidth() / 3){
                        right.add(x);
                    }
                    else if (x < rgbImage.getWidth() * 2 / 3){
                        center.add(x);
                    }
                    else {
                        left.add(x);
                    }
                }
            }
        }
        opMode.telemetry.addData("left: ", left.size());
        opMode.telemetry.addData("center: ", center.size());
        opMode.telemetry.addData("right: ", right.size());
        opMode.telemetry.update();

        if (Math.max(left.size(), center.size()) > right.size()){
            if (left.size() > center.size()){
                return "left";
            }
            else {
                return "center";
            }
        }
        else {
            return "right";
        }
    }
}