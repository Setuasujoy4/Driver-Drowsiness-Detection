package com.budget;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.highgui.HighGui;

public class DrowsinessDetector {
    static {
        nu.pattern.OpenCV.loadLocally();
    }

    private VideoCapture cap;
    private CascadeClassifier faceDetector;
    private CascadeClassifier eyeDetector;
    private int sleep = 0, drowsy = 0, active = 0;
    private String status = "Starting...";
    private Scalar color = new Scalar(0, 255, 0);

    public DrowsinessDetector() {
        cap = new VideoCapture(0);
        faceDetector = new CascadeClassifier("haarcascade_frontalface_alt.xml");
        eyeDetector = new CascadeClassifier("haarcascade_eye.xml");

        if (faceDetector.empty() || eyeDetector.empty()) {
            System.err.println("Could not load cascade files");
            System.exit(1);
        }
    }

    public void run() {
        Mat frame = new Mat();
        Mat gray = new Mat();

        while (true) {
            cap.read(frame);
            if (frame.empty()) break;

            Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);

            MatOfRect faces = new MatOfRect();
            faceDetector.detectMultiScale(gray, faces, 1.1, 3, 0, new Size(100, 100), new Size());

            for (Rect face : faces.toArray()) {
                Imgproc.rectangle(frame, face.tl(), face.br(), new Scalar(0, 255, 0), 2);

                Mat faceROI = new Mat(gray, face);
                MatOfRect eyes = new MatOfRect();
                eyeDetector.detectMultiScale(faceROI, eyes, 1.1, 3, 0, new Size(20, 20), new Size());

                Rect[] eyeArray = eyes.toArray();

                // Corrected logic: More eyes detected = more awake
                if (eyeArray.length >= 2) {
                    // Both eyes open - Active
                    sleep = 0;
                    drowsy = 0;
                    active++;
                    if (active > 3) {
                        status = "Active :)";
                        color = new Scalar(0, 255, 0);
                    }
                } else if (eyeArray.length == 1) {
                    // One eye detected - Drowsy
                    sleep = 0;
                    active = 0;
                    drowsy++;
                    if (drowsy > 5) {
                        status = "Drowsy !";
                        color = new Scalar(0, 165, 255);
                    }
                } else {
                    // No eyes detected - Sleeping
                    active = 0;
                    drowsy = 0;
                    sleep++;
                    if (sleep > 8) {
                        status = "SLEEPING !!!";
                        color = new Scalar(0, 0, 255);
                    }
                }
            }

            Imgproc.putText(frame, status, new Point(100, 100),
                    Imgproc.FONT_HERSHEY_SIMPLEX, 1.2, color, 3);

            HighGui.imshow("Drowsiness Detection", frame);
            if (HighGui.waitKey(1) == 27) break; // ESC key
        }

        cap.release();
        HighGui.destroyAllWindows();
    }

    public static void main(String[] args) {
        new DrowsinessDetector().run();
    }
}
