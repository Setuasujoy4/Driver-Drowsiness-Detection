# Driver Drowsiness Detection System

A real-time driver drowsiness detection system built with Java and OpenCV that monitors driver alertness through facial and eye detection.

## Features

- **Real-time Detection**: Uses webcam to monitor driver's face and eyes in real-time
- **Multi-state Classification**: Detects three states:
  - 🟢 **Active**: Both eyes open and alert
  - 🟡 **Drowsy**: One eye detected or partially closed eyes
  - 🔴 **Sleeping**: No eyes detected (eyes closed)
- **Visual Feedback**: Color-coded status display with real-time alerts
- **Face Detection**: Uses Haar cascade classifiers for accurate face and eye detection

## Technology Stack

- **Java 11**: Core programming language
- **OpenCV 4.5.1**: Computer vision library for image processing
- **Maven**: Build and dependency management
- **Haar Cascade Classifiers**: Pre-trained models for face and eye detection

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- Webcam/Camera connected to your system
- Windows/Linux/macOS

## Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Driver-Drowsiness-Detection
   ```

2. **Build the project**
   ```bash
   mvn clean compile
   ```

3. **Run the application**
   ```bash
   mvn exec:java -Dexec.mainClass="com.budget.DrowsinessDetector"
   ```

## Project Structure

```
Driver-Drowsiness-Detection/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/budget/
│   │           ├── App.java
│   │           └── DrowsinessDetector.java
│   └── test/
├── haarcascade_frontalface_alt.xml    # Face detection model
├── haarcascade_eye.xml                # Eye detection model
├── pom.xml                           # Maven configuration
└── README.md
```

## How It Works

1. **Camera Initialization**: Captures video feed from the default camera (index 0)
2. **Face Detection**: Uses Haar cascade classifier to detect faces in each frame
3. **Eye Detection**: Within detected face regions, identifies eyes using eye cascade classifier
4. **State Classification**:
   - **Active**: 2+ eyes detected → Green status
   - **Drowsy**: 1 eye detected → Orange status  
   - **Sleeping**: 0 eyes detected → Red status
5. **Alert System**: Displays real-time status with color-coded warnings

## Usage

1. Run the application
2. Position yourself in front of the camera
3. The system will display a window showing:
   - Live video feed
   - Face detection rectangle (green)
   - Current alertness status
   - Color-coded warning system

**Controls:**
- Press `ESC` to exit the application

## Configuration

The detection sensitivity can be adjusted by modifying these parameters in `DrowsinessDetector.java`:

```java
// Detection thresholds
private int sleep = 0, drowsy = 0, active = 0;

// Sensitivity settings
if (active > 3)   // Active threshold
if (drowsy > 5)   // Drowsy threshold  
if (sleep > 8)    // Sleep threshold
```

## Dependencies

- **OpenCV**: `org.openpnp:opencv:4.5.1-2`
- **JUnit**: `junit:junit:3.8.1` (testing)

## Troubleshooting

**Camera not detected:**
- Ensure your webcam is connected and not being used by another application
- Try changing the camera index in `VideoCapture(0)` to `VideoCapture(1)` or higher

**Cascade files not found:**
- Verify `haarcascade_frontalface_alt.xml` and `haarcascade_eye.xml` are in the project root
- Check file paths are correct for your operating system

**Poor detection accuracy:**
- Ensure good lighting conditions
- Position face directly in front of camera
- Adjust detection parameters for your environment

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/improvement`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/improvement`)
5. Create a Pull Request

## License

This project is open source and available under the [MIT License](LICENSE).

## Future Enhancements

- [ ] Audio alerts for drowsiness detection
- [ ] Machine learning model integration
- [ ] Mobile app development
- [ ] Data logging and analytics
- [ ] Multiple camera support
- [ ] Cloud-based monitoring dashboard

## Contact

For questions or support, please open an issue in the repository.