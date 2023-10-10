package com.lynx.scoreblitz.presentation.screens

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.Analyzer
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.core.UseCaseGroup
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.navigation.fragment.findNavController
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.lynx.scoreblitz.R
import com.lynx.scoreblitz.databinding.FragmentWatchBinding
import com.lynx.scoreblitz.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import java.util.concurrent.Executors

@AndroidEntryPoint
class WatchFragment : Fragment() {
    private var _binding: FragmentWatchBinding? = null
    private val binding get() = _binding!!
    private var barcodeScanner: BarcodeScanner? = null
    private var isScanned: Boolean = false
    private val cameraExecutor = Executors.newSingleThreadExecutor()
    private val alertDialog: AlertDialog? = null

    private val cameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (!it) toast("Camera permission denied!") else setupScanner()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isScanned = true
        barcodeScanner = BarcodeScanning.getClient()
        binding.btnClose.setOnClickListener { findNavController().popBackStack() }

        val qrString = getModernTradeWaveMerchantNo("00020101021129300011097931841010111097931841015204581253031045802MM5910TestBiller6006YANGON63042015")
        binding.message.text = qrString
    }

    private fun startedScanning(){
        if (isGrantedPermission) setupScanner()
        else cameraPermission.launch(android.Manifest.permission.CAMERA)
    }

    private fun setupScanner() {
        val processCameraProvider = ProcessCameraProvider.getInstance(requireContext())
        processCameraProvider.addListener(
            {
                val cameraProvider = processCameraProvider.get()
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
                    }
                val imageAnalyzer = ImageAnalysis.Builder()
                    .build()
                    .also{
                        it.setAnalyzer(cameraExecutor,barcodeAnalyzer)
                    }

                val useCase : UseCaseGroup = UseCaseGroup.Builder()
                    .addUseCase(preview)
                    .addUseCase(imageAnalyzer)
                    .build()

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(this, cameraSelector, useCase)
                }catch (e: Exception){
                    toast(e.message.toString())
                }

            }, ContextCompat.getMainExecutor(requireContext())
        )
    }

    @ExperimentalGetImage
    private val barcodeAnalyzer = Analyzer { imgProxy ->
        if (isScanned) {
            val img = imgProxy.image
            if (img == null) {
                imgProxy.close()
                return@Analyzer
            }
            val inputImage = InputImage.fromMediaImage(img, imgProxy.imageInfo.rotationDegrees)
            val barcodeProcess = barcodeScanner?.process(inputImage)
            barcodeProcess?.addOnSuccessListener {barcode ->
                if (barcode.isEmpty()) {
                    imgProxy.close()
                    return@addOnSuccessListener
                }else{
                    val barcode = barcode.first().rawValue.toString()
                    getCodeString(barcode)
                    Log.d("barcode",barcode)
                }
            }

            barcodeProcess?.addOnCanceledListener {
                imgProxy.close()
                isScanned = true
            }
            barcodeProcess?.addOnFailureListener {
                imgProxy.close()
                isScanned = true
                toast(it.message.toString())
            }
        } else imgProxy.close()
    }

    private fun getCodeString(barcode: String){
        when{
            isWavePayTransfer(code = barcode) -> {
                toast(TYPES.WAVE_TRANSFER.name)
            }
            else -> toast(TYPES.WAVE_MERCHANT.name)
        }
    }

    private fun isWavePayTransfer(code: String) : Boolean{
        return code.startsWith("9") && code.length in 8..11 && code.isDigitsOnly()
    }

    private fun startedBarcodeAnim(){
        binding.barcodeLine.startAnimation(
            AnimationUtils.loadAnimation(requireContext(), R.anim.scanner_animation)
        )
    }

    fun getModernTradeWaveMerchantNo(encode: String?): String {
        encode ?: return ""
            val prefixString = encode.take(20)
            if (prefixString.isDigitsOnly()) {
                val length = prefixString.takeLast(2).toInt()
                val startIndex = 20
                val endIndex = (startIndex + length)
                val decode = encode.substring(startIndex, endIndex)
                return decode.trimStart('0')
            } else {
                return ""
            }
    }

    private val isGrantedPermission: Boolean get() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    override fun onStart() {
        super.onStart()
        startedBarcodeAnim()
        startedScanning()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isScanned = false
        barcodeScanner?.close()
        cameraExecutor.shutdown()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        isScanned = false
        barcodeScanner?.close()
        cameraExecutor.shutdown()
    }

    enum class TYPES(name: String) {
        WAVE_TRANSFER("WAVE TRANSFER"),
        WAVE_MERCHANT("WAVE MERCHANT")
    }

}
