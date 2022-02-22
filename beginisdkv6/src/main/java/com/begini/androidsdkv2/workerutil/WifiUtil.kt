package com.begini.androidsdkv2.workerutil

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.os.SystemClock
import android.text.TextUtils
import org.json.JSONArray
import org.json.JSONObject

class WifiUtil {
      @SuppressLint("MissingPermission")
      fun getConnectedWifi(wifiManager: WifiManager): JSONObject {
        val connectedWifi = JSONObject()
        val obj = JSONObject()
        val connectionInfo = wifiManager.connectionInfo
        if (connectionInfo != null) {
            val ssid = connectionInfo.ssid.replace("\"".toRegex(), "")
            obj.put("captureTime", System.currentTimeMillis())
            obj.put("ssid", ssid)
            obj.put("bssid", connectionInfo.bssid)
            obj.put("macAddress", connectionInfo.macAddress)
            obj.put("supplicantState", connectionInfo.supplicantState.ordinal.toLong())
            obj.put("rssi", connectionInfo.rssi.toLong())
            obj.put("linkSpeed", connectionInfo.linkSpeed.toLong())
            obj.put("linkSpeedUnit", "Mbps")
            obj.put("networkId", connectionInfo.networkId.toLong())
            obj.put("detailedSupplicantState", WifiInfo.getDetailedStateOf(connectionInfo.supplicantState).ordinal.toLong())
            if (Build.VERSION.SDK_INT >= 21) {
                obj.put("frequency", connectionInfo.frequency.toLong())
                obj.put("channel", ieee80211_frequency_to_channel(connectionInfo.frequency))
                obj.put("frequencyUnit", "MHz")
            }
        }
        connectedWifi.put("connectedWifi", obj)
        return connectedWifi
    }

    @SuppressLint("MissingPermission")
    private fun getConfiguredWifi(wifiManager: WifiManager): JSONObject {
        val array = JSONArray()
        val configuredWifi = JSONObject()

        val configuredNetworks = wifiManager.configuredNetworks
        val currentTimeMillis = System.currentTimeMillis()
        for (wifiConfiguration in configuredNetworks) {
            val obj = JSONObject()
            val str = wifiConfiguration.SSID.replace("\"".toRegex(), "")
            obj.put("captureTime", currentTimeMillis)
            obj.put("ssid", str)
            obj.put("bssid", wifiConfiguration.BSSID)
            obj.put("networkId", wifiConfiguration.networkId.toLong())
            if (Build.VERSION.SDK_INT >= 23) {
                obj.put("fqdn", wifiConfiguration.FQDN)
                obj.put("isPassPoint", wifiConfiguration.isPasspoint)
                obj.put("providerFriendlyName", wifiConfiguration.providerFriendlyName)
            }
            array.put(obj)
        }
        configuredWifi.put("configuredWifi", array)
        return configuredWifi
    }

      fun getScannedWifi(context: Context, wifiManager: WifiManager): JSONObject {
        val array = JSONArray()
        val scannedWifi = JSONObject()

         val scanResults = wifiManager.scanResults
            val currentTimeMillis = System.currentTimeMillis()
            for (scanResult in scanResults) {
                val obj = JSONObject()
                obj.put("captureTime", currentTimeMillis)
                obj.put("ssid", scanResult.SSID)
                obj.put("bssid", scanResult.BSSID)
                obj.put("channel", ieee80211_frequency_to_channel(scanResult.frequency).toLong())
                obj.put("level", scanResult.level.toLong())
                obj.put("frequency", scanResult.frequency.toLong())
                obj.put("capabilities", scanResult.capabilities)
                obj.put("linkSpeedUnit", "Mbps")
                if (Build.VERSION.SDK_INT >= 17) {
                    obj.put("timestamp", SystemClock.elapsedRealtime() + scanResult.timestamp / 1000)
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    obj.put("frequency0", scanResult.centerFreq0.toLong())
                    obj.put("frequency1", scanResult.centerFreq1.toLong())
                    obj.put("operatorFriendlyName", if (TextUtils.isEmpty(scanResult.operatorFriendlyName)) null else scanResult.operatorFriendlyName.toString())
                    obj.put("venueName", if (TextUtils.isEmpty(scanResult.venueName)) null else scanResult.venueName.toString())
                    obj.put("isPasspointNetwork", scanResult.isPasspointNetwork)
                    obj.put("channelWidth", scanResult.channelWidth.toLong())
                }
                array.put(obj)
            }
        
        scannedWifi.put("scannedWifi", array)
        return scannedWifi
    }

    fun getWifiInformation(context: Context): JSONArray {
        val obj = JSONArray()
             val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            if (wifiManager != null) {
                obj.put(getConnectedWifi(wifiManager))
                if (wifiManager.isWifiEnabled) {
                    obj.put(getConfiguredWifi(wifiManager))
                }
                obj.put(getScannedWifi(context, wifiManager))
            }

        return obj
    }

    private fun ieee80211_frequency_to_channel(freq: Int): Int {
        if (freq == 2484)
            return 14

        return if (freq < 2484) (freq - 2407) / 5 else freq / 5 - 1000

    }
}