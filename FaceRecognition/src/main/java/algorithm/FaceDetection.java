package algorithm;

import constants.Constants;
import gui.ImagePanel;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

public class FaceDetection {
    private CascadeClassifier cascadeFace;
    private CascadeClassifier cascadeEyes;
    private CascadeClassifier cascadeNose;
    private CascadeClassifier cascadeMouth;

    public FaceDetection(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.cascadeFace = new CascadeClassifier(Constants.CASCADE_CLASSIFIER_FACE);
        this.cascadeEyes = new CascadeClassifier(Constants.CASCADE_CLASSIFIER_EYES);
        this.cascadeNose = new CascadeClassifier(Constants.CASCADE_CLASSIFIER_NOSE);
        this.cascadeMouth = new CascadeClassifier(Constants.CASCADE_CLASSIFIER_MOUTH);
    }

    private BufferedImage convertMatToImage(Mat mat){
        int type = BufferedImage.TYPE_BYTE_GRAY;

        if(mat.channels() > 1){
            type = BufferedImage.TYPE_3BYTE_BGR;
        }

        int bufferSize = mat.channels()*mat.cols()*mat.rows();
        byte[] bytes = new byte[bufferSize];
        mat.get(0, 0, bytes);
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        final byte[] targetPixels = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
        System.arraycopy(bytes, 0, targetPixels, 0, bytes.length);

        return image;
    }

    private void detectFeatures(Mat image, CascadeClassifier cascadeClassifier, Scalar color){
        MatOfRect features = new MatOfRect();
        cascadeClassifier.detectMultiScale(image, features, 1.2, 3, 10, new Size(30, 30), new Size(500, 500));
        for(Rect rect : features.toArray()){
            Imgproc.rectangle(image, rect.tl(), rect.br(), color, 4);
        }
    }

    public void detectFaces(File file, ImagePanel imagePanel){
        Mat image = Imgcodecs.imread(file.getAbsolutePath(), Imgcodecs.IMREAD_COLOR);

        MatOfRect faceDetections = new MatOfRect();
        cascadeFace.detectMultiScale(image, faceDetections, 1.2, 3, 10, new Size(30, 30), new Size(500, 500));

        for(Rect rect : faceDetections.toArray()){
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width ,rect.y+ rect.height), new Scalar(0, 165, 255), 4);
            Mat faceROI = new Mat(image, rect);

            detectFeatures(faceROI, cascadeEyes, new Scalar(255, 0, 0));
            detectFeatures(faceROI, cascadeNose, new Scalar(0, 255, 0));
            detectFeatures(faceROI, cascadeMouth, new Scalar(0, 0, 255));
        }

        BufferedImage bufferedImage = convertMatToImage(image);
        imagePanel.updateImage(bufferedImage);
    }

}
