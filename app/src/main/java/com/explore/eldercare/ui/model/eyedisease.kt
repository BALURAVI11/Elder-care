package com.explore.eldercare.ui.model

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.explore.eldercare.databinding.ActivityEyediseaseBinding
import com.explore.eldercare.ml.EyeDisFloat32
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class eyedisease : AppCompatActivity() {

    private lateinit var binding: ActivityEyediseaseBinding
    private var imageUri : Uri? = null
    lateinit var bitmap:Bitmap

    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()){
        imageUri = it
        binding.imageView.setImageURI(imageUri)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEyediseaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.setOnClickListener{
            val intent= Intent()
            intent.setAction(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(intent,100)
        }
        var imageProcessor = ImageProcessor.Builder()
           .add(NormalizeOp(floatArrayOf(0.485f,0.456f,0.406f), floatArrayOf(0.229f,0.224f,0.225f)))
            .add(CastOp(DataType.UINT8))
            .add(NormalizeOp(0f, 255f))
           .add(ResizeOp(224,224,ResizeOp.ResizeMethod.BILINEAR)).build()

        binding.button2.setOnClickListener{
            var tensorImage = TensorImage(DataType.UINT8)
            tensorImage.load(bitmap)

            tensorImage=imageProcessor.process(tensorImage)

            val model = EyeDisFloat32.newInstance(this)

// Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)

            inputFeature0.loadBuffer(tensorImage.buffer)
            Log.d("shape", inputFeature0.buffer.toString())

// Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

            var idx=0
            outputFeature0.forEachIndexed{index, fl ->
                if(outputFeature0[idx]<fl){
                    idx=index
                }
            }

            if(idx== 0){
                Toast.makeText(this,"normal",Toast.LENGTH_SHORT).show()
            }
            if(idx== 1){
                Toast.makeText(this,"diabities",Toast.LENGTH_SHORT).show()
            }
            if(idx== 2){
                Toast.makeText(this,"cataract",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"gluacoma",Toast.LENGTH_SHORT).show()
            }

// Releases model resources if no longer used.
            model.close()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==100){
            var uri = data?.data
            bitmap= MediaStore.Images.Media.getBitmap(this.contentResolver,uri)
            binding.imageView.setImageBitmap(bitmap)
        }
    }
}