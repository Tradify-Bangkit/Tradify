package com.example.tradify.ui.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tradify.databinding.FragmentCameraBinding
import com.example.tradify.ml.Model2
import com.example.tradify.ui.fooditem.FoodDetailActivity
import com.example.tradify.ui.ui.dashboard.CameraViewModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder


class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private lateinit var currentPhotoPath: String
    private var getFile: File? = null
    private val binding get() = _binding!!

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    activity,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                activity?.finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireActivity().baseContext,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val cameraViewModel =
            ViewModelProvider(this).get(CameraViewModel::class.java)

        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.apply {
            namaMakanan.isGone = true
            btnDetailMakanan.isGone = true
            cameraXButton.setOnClickListener { startTakePhoto() }
            galleryButton.setOnClickListener { startGallery() }
            btnDetailMakanan.setOnClickListener {
                cameraViewModel.productuser.observeForever {
                    cameraViewModel.getProduct()
                    val foodDetail = Intent(requireActivity(), FoodDetailActivity::class.java)
                    foodDetail.putExtra(FoodDetailActivity.EXTRA_FOOD, it[0])
                    startActivity(foodDetail)
                }
            }
        }

        return root
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)

        createTempFile(requireActivity()).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireActivity(),
                "com.example.tradify",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile

            val result = BitmapFactory.decodeFile(getFile?.path)
            binding.previewImageView.setImageBitmap(result)
            classifyImage(result)
        }
    }


    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri

            val myFile = uriToFile(selectedImg, requireActivity())
            getFile = myFile
            binding.previewImageView.setImageURI(selectedImg)
            val result = BitmapFactory.decodeFile(getFile?.path)
            classifyImage(result)
        }
    }

    private fun classifyImage(image: Bitmap) {
        try {
            val model = Model2.newInstance(requireActivity())

            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 64, 64, 3), DataType.FLOAT32)
            val byteBuffer: ByteBuffer =
                ByteBuffer.allocateDirect(4 * 64 * 64 * 3)
            byteBuffer.order(ByteOrder.nativeOrder())


            val intValues = IntArray(64 * 64)
            image.getPixels(intValues, 0, 64, 0, 0, 64, 64)
            var pixel = 0
            for (i in 0 until 64) {
                for (j in 0 until 64) {
                    val `val` = intValues[pixel++]
                    byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 64f))
                    byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 64f))
                    byteBuffer.putFloat((`val` and 0xFF) * (1f / 64f))
                }
            }
            inputFeature0.loadBuffer(byteBuffer)
            val outputs: Model2.Outputs = model.process(inputFeature0)
            val outputFeature0: TensorBuffer = outputs.outputFeature0AsTensorBuffer

            val confidences = outputFeature0.floatArray
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            val classes = arrayOf(
                "Kerak Telur",
                "Bika Ambon",
                "Rendang",
                "Lapis Talas Bogor",
                "Pie Susu",
                "Mochi",
                "Pempek",
                "Putu Ayu"
            )
            Log.e("PREDIK", classes[maxPos])
            model.close()

            binding.apply {
                namaMakanan.text = classes[maxPos]
                namaMakanan.isGone = false
                btnDetailMakanan.isGone = false
            }
        } catch (e: Exception) {
            Log.e("Camera Exception", e.toString())
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}