
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.*
import java.lang.Math

/**
 *
 * Species Classifier/model class.  
 *
 * @param assetManager the type of a member in this group.
 * @param modelPath path to the tflite file
 * @param labelPath path to the file of strings of lables
 * @param inputSize dimension of the bitmap WxH (W=H)
 */
class SpeciesClassifier (assetManager: AssetManager, modelPath: String, labelPath: String, inputSize: Int,mName:String){

    private var MODEL_NAME: String
    private var INTERPRETER: Interpreter
    private var LABEL_LIST: List<String>
    private val INPUT_SIZE: Int = inputSize
    //rgb=3
    private val PIXEL_SIZE: Int = 3
    //no normalization ; mean=0 std=1
    private val IMAGE_MEAN = 0
    private val IMAGE_STD = 1.0f


    /**
     * Load in the model file and print a status message in the constructor
     */
    init {
        MODEL_NAME=mName
        INTERPRETER = Interpreter(loadModelFile(assetManager, modelPath))
        LABEL_LIST = loadLabelList(assetManager, labelPath)
        System.out.println("Loading model file "+modelPath+" for model "+MODEL_NAME)
        for(l in 0 until LABEL_LIST.size)
            {
                System.out.println("Model "+MODEL_NAME+", label "+l+" is "+LABEL_LIST[l])
            }
    }

    /**
     * 'getter' method for the model's name
     */
    public fun getName():String{
        return this.MODEL_NAME
    }

    /**
     * utility method to load the model file
     */
    private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    /**
     * load the lines from the label file into a list
     */
    private fun loadLabelList(assetManager: AssetManager, labelPath: String): List<String> {
        return assetManager.open(labelPath).bufferedReader().useLines { it.toList() }
    }

    /**
     * Perform classification on a bitmap
     * @param bitmap the image to run classification on
     * @return the string of the classification result ; a line from the label file
     */
    @Synchronized
    fun  recognizeImage(bitmap: Bitmap): String {
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false)
        val byteBuffer = convertBitmapToByteBuffer(scaledBitmap)
        val result = Array(1) { FloatArray(LABEL_LIST.size) }
        INTERPRETER.run(byteBuffer, result)
        val maxIdx = result[0].indexOf(result[0].max()?:-99999.999999f)
        val label=LABEL_LIST[maxIdx]
        return label
    }

    /**
     * Conver a bitmap to bytes
     * @param bitmap it will be converted to bytes
     */
    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(4 * INPUT_SIZE * INPUT_SIZE * PIXEL_SIZE)
        byteBuffer.order(ByteOrder.nativeOrder())
        val intValues = IntArray(INPUT_SIZE * INPUT_SIZE)

        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        var pixel = 0
        for (i in 0 until INPUT_SIZE) {
            for (j in 0 until INPUT_SIZE) {
                val `val` = intValues[pixel++]
                //3 putFloats for RGB
                byteBuffer.putFloat((((`val`.shr(16)  and 0xFF) - IMAGE_MEAN) / IMAGE_STD))
                byteBuffer.putFloat((((`val`.shr(8) and 0xFF) - IMAGE_MEAN) / IMAGE_STD))
                byteBuffer.putFloat((((`val` and 0xFF) - IMAGE_MEAN) / IMAGE_STD))
            }
        }
        return byteBuffer
    }



}


