package com.begini.androidsdkv2.workerutil

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.os.Build
import androidx.annotation.RequiresApi
import com.begini.androidsdkv2.model.devicedata.BluetoothAdapterModel
import com.begini.androidsdkv2.model.devicedata.BluetoothDeviceModel
import com.begini.androidsdkv2.model.devicedata.BluetoothModel

class BluetoothUtil {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllPairedDevicesList(bluetoothAdapter: BluetoothAdapter): BluetoothModel {
        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
        var devices: MutableList<BluetoothDeviceModel> = arrayListOf()
        pairedDevices?.forEach { device ->
            devices.add(
                BluetoothDeviceModel(
                    device.type,
                    device.name,
                    device.bluetoothClass.deviceClass,
                    device.bluetoothClass.majorDeviceClass,
                    device.address,
                    device.bondState,

                )
            )

        }
        var adapterModel= BluetoothAdapterModel(
            bluetoothAdapter.state,
            bluetoothAdapter.leMaximumAdvertisingDataLength,
            bluetoothAdapter.isLe2MPhySupported,
            bluetoothAdapter.isOffloadedScanBatchingSupported,
            bluetoothAdapter.scanMode,
            bluetoothAdapter.isLeCodedPhySupported,
            bluetoothAdapter.isMultipleAdvertisementSupported,
            bluetoothAdapter.address,
            bluetoothAdapter.isOffloadedFilteringSupported,
            bluetoothAdapter.isLePeriodicAdvertisingSupported,
            bluetoothAdapter.isLeExtendedAdvertisingSupported,
            bluetoothAdapter.name,
        )
        var bluetoothModel=BluetoothModel(adapterModel,devices)
        return bluetoothModel
    }
}