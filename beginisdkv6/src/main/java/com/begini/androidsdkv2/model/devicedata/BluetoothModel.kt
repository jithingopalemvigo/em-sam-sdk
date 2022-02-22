package com.begini.androidsdkv2.model.devicedata

import com.squareup.moshi.Json

data class BluetoothModel(
    @Json(name = "adapter")
    val adapter: BluetoothAdapterModel,
    @Json(name = "devices")
    val devices: MutableList<BluetoothDeviceModel>
)

data class BluetoothDeviceModel(
    @Json(name = "type")
    val type: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "device_class")
    val deviceClass: Int,
    @Json(name = "device_major_class")
    val majorDeviceClass: Int,
    @Json(name = "address")
    val address: String,
    @Json(name = "bond_state")
    val bondState: Int
)

data class BluetoothAdapterModel(
    @Json(name = "state")
    val state: Int,
    @Json(name = "le_maximum_advertising_data_length")
    val leMaximumAdvertisingDataLength: Int,
    @Json(name = "is_le_2m_phy_supported")
    val isLe2MPhySupported: Boolean,
    @Json(name = "is_offloaded_scan_batching_supported")
    val isOffloadedScanBatchingSupported: Boolean,
    @Json(name = "scan_mode")
    val scanMode: Int,
    @Json(name = "is_le_coded_phy_supported")
    val isLeCodedPhySupported: Boolean,
    @Json(name = "is_multiple_advertisement_supported")
    val isMultipleAdvertisementSupported: Boolean,
    @Json(name = "address")
    val address: String,
    @Json(name = "is_offloaded_filtering_supported")
    val isOffloadedFilteringSupported: Boolean,
    @Json(name = "is_le_periodic_advertising_supported")
    val isLePeriodicAdvertisingSupported: Boolean,
    @Json(name = "is_le_extended_advertising_supported")
    val isLeExtendedAdvertisingSupported: Boolean,
    @Json(name = "name")
    val name: String
)