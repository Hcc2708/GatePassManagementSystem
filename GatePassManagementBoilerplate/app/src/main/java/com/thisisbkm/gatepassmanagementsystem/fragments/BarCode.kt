package com.thisisbkm.gatepassmanagementsystem.fragments

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.thisisbkm.gatepassmanagementsystem.R
import com.thisisbkm.gatepassmanagementsystem.TinyDB
import com.thisisbkm.gatepassmanagementsystem.databinding.FragmentBarCodeBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BarCode : Fragment() {
    private lateinit var codeScanner: CodeScanner
    private lateinit var binding:FragmentBarCodeBinding
    private lateinit var tg:ToneGenerator
    private lateinit var tdb:TinyDB
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentBarCodeBinding.inflate(layoutInflater, container, false)
        tg = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
        tdb = TinyDB(context)
        if(ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                requireContext() as Activity,
                arrayOf(Manifest.permission.CAMERA), 23
            )

        }
        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        val scannerView = view?.findViewById<CodeScannerView>(R.id.scanner_view)
        val activity = requireActivity()
        codeScanner = CodeScanner(activity, scannerView!!)
        codeScanner.isTouchFocusEnabled = true
        codeScanner.isFlashEnabled = true
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.decodeCallback = DecodeCallback {
            tg.startTone(ToneGenerator.TONE_CDMA_PIP, 150)
            val list = tdb.getListInt("MyList")
            val ll = tdb.getListString("MyListLogs")
            try{
                val reg = it.text.toString().toInt()

                activity.runOnUiThread {
                    if (list.contains(reg)) {
                        list.remove(reg)
                        ll.add(0, "IN : $reg checked in at " + getDateTime())
                        Toast.makeText(activity, "$reg Checked In", Toast.LENGTH_SHORT).show()
                    } else {
                        list.add(reg)
                        ll.add(0, "OUT : $reg checked out at " + getDateTime())
                        Toast.makeText(activity, "$reg Checked Out", Toast.LENGTH_SHORT).show()
                    }
                    tdb.putListInt("MyList", list)
                    tdb.putListString("MyListLogs", ll)
                }
            }
            catch(e:Exception){
                Toast.makeText(context,"Some Error Occurred", Toast.LENGTH_SHORT).show()
            }
        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
        super.onViewStateRestored(savedInstanceState)
    }

    @Deprecated("Deprecated in Java")
    override fun setUserVisibleHint(isVisibleToUser: Boolean){
        if(context!=null){
        if(isVisibleToUser)
            codeScanner.startPreview()
        else
            codeScanner.releaseResources()
        super.setUserVisibleHint(isVisibleToUser)
        }
    }
    private fun getDateTime(): String? {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(("yy-MM-dd HH:mm")))
    }
    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
//    private fun initializeDetectors(){
//        Toast.makeText(context, "Barcode Scanner Started", Toast.LENGTH_SHORT).show()
//        bd = BarcodeDetector.Builder(requireContext())
//            .setBarcodeFormats(Barcode.ALL_FORMATS)
//            .build()
//        cs = CameraSource.Builder(requireContext(), bd)
//            .setRequestedPreviewSize(1920, 1080)
//            .setAutoFocusEnabled(true)
//            .build()
//        binding.surfaceView.holder.addCallback(object : SurfaceHolder.Callback{
//            override fun surfaceCreated(holder: SurfaceHolder) {
//                try {
//                    if (ActivityCompat.checkSelfPermission(
//                            requireContext(),
//                            Manifest.permission.CAMERA
//                        ) == PackageManager.PERMISSION_GRANTED
//                    ) {
//                        cs.start(sv.holder)
//                    } else {
//                        ActivityCompat.requestPermissions(
//                            context as Activity,
//                            arrayOf(Manifest.permission.CAMERA),
//                            REQUEST_CAMERA_PERMISSION
//                        )
//                    }
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            }
//
//            override fun surfaceChanged(
//                holder: SurfaceHolder,
//                format: Int,
//                width: Int,
//                height: Int
//            ) {
//
//            }
//
//            override fun surfaceDestroyed(holder: SurfaceHolder) {
//                cs.stop()
//            }
//        })
//        bd.setProcessor(object : Detector.Processor<Barcode> {
//            override fun release() {
//                 Toast.makeText(context, "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
//            }
//
//            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
//                val barcodes = detections.detectedItems
//                if (barcodes.size() != 0) {
//                    binding.barcodeText.post {
//                        binding.barcodeText.removeCallbacks(null)
//                        data = barcodes.valueAt(0).email.address
//                        binding.barcodeText.text = data
//                        tg.startTone(ToneGenerator.TONE_CDMA_PIP, 150)
//                    }
//                }
//            }
//        })
//    }

//    override fun onPause() {
//        super.onPause()
//        cs.release()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        initializeDetectors()
//    }
}