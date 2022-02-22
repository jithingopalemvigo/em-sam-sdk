package com.begini.androidsdkv2.workerutil

import android.content.Context
import android.media.AudioManager
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import org.json.JSONObject


class ProfileUtil {
    fun getProfile(context: Context): JSONObject {
var phoneSettingsJSON=getPhoneSettingsInformation(context)

var hardwareSettingsJSON=JSONObject()
    hardwareSettingsJSON.put("screen",getScreenInformation())
    hardwareSettingsJSON.put("volume",getVolumeInformation(context.getSystemService(Context.AUDIO_SERVICE) as AudioManager))
    hardwareSettingsJSON.put("hardware",getHardwareInformation())
        var permissionJSON=PermissionUtil().    getAllowedPermissionListForProfileUtil(context)
    var result=JSONObject()
        result.put("phone_settings",phoneSettingsJSON)
        result.put("hardware_settings",hardwareSettingsJSON)
        result.put("permission_status",permissionJSON)
        val android_id = Settings.Secure.getString(context.getContentResolver(),
            Settings.Secure.ANDROID_ID)
        result.put("android_unique_id",android_id.toString())
        return result
    }

    fun getPhoneSettingsInformation(context: Context): JSONObject {

        val information = JSONObject()

        val airplaneModeOn = getPhoneSettingsField(context, Settings.Global.AIRPLANE_MODE_ON)
        val alwaysFinishActivities =
            getPhoneSettingsField(context, Settings.Global.ALWAYS_FINISH_ACTIVITIES)
        val animatorDurationScale =
            getPhoneSettingsField(context, Settings.Global.ANIMATOR_DURATION_SCALE)
        val autoTime = getPhoneSettingsField(context, Settings.Global.AUTO_TIME)
        val autoTimeZone = getPhoneSettingsField(context, Settings.Global.AUTO_TIME_ZONE)

        val dataRoaming = getPhoneSettingsField(context, Settings.Global.DATA_ROAMING)
        val deviceProvisioned = getPhoneSettingsField(context, Settings.Global.DEVICE_PROVISIONED)
        val installNonMarketApps =
            getPhoneSettingsField(context, Settings.Global.INSTALL_NON_MARKET_APPS)
        val modeRinger = getPhoneSettingsField(context, Settings.Global.MODE_RINGER)
        val transitionAnimationScale =
            getPhoneSettingsField(context, Settings.Global.TRANSITION_ANIMATION_SCALE)

        val usbMassStorageEnabled =
            getPhoneSettingsField(context, Settings.Global.USB_MASS_STORAGE_ENABLED)
        val wifiNetworksAvailableNotificationOn =
            getPhoneSettingsField(context, Settings.Global.WIFI_NETWORKS_AVAILABLE_NOTIFICATION_ON)
        val wifiWatchdogOn = getPhoneSettingsField(context, Settings.Global.WIFI_WATCHDOG_ON)
        val adbEnabled = getPhoneSettingsField(context, Settings.Global.ADB_ENABLED)
        val wifiMaxDhcpRetryCount =
            getPhoneSettingsField(context, Settings.Global.WIFI_MAX_DHCP_RETRY_COUNT)

        val debugApp = getPhoneSettingsField(context, Settings.Global.DEBUG_APP)
        val wifiMobileDataTransitionWakelockTimeoutMs = getPhoneSettingsField(
            context,
            Settings.Global.WIFI_MOBILE_DATA_TRANSITION_WAKELOCK_TIMEOUT_MS
        )
        val wifiNetworksAvailableRepeatDelay =
            getPhoneSettingsField(context, Settings.Global.WIFI_NETWORKS_AVAILABLE_REPEAT_DELAY)
        val wifiNumOpenNetworksKept =
            getPhoneSettingsField(context, Settings.Global.WIFI_NUM_OPEN_NETWORKS_KEPT)

        information.put("airplane_mode_on", airplaneModeOn)
        information.put("always_finish_activities", alwaysFinishActivities)
        information.put("animation_duration_scale", animatorDurationScale)
        information.put("auto_time", autoTime)
        information.put("auto_time_zone", autoTimeZone)

        information.put("data_roaming", dataRoaming)
        information.put("device_provisioned", deviceProvisioned)
        information.put("install_non_market_apps", installNonMarketApps)
        information.put("mode_ringer", modeRinger)
        information.put("transition_animation_scale", transitionAnimationScale)

        information.put("usb_mass_storage_enabled", usbMassStorageEnabled)
        information.put(
            "wifi_networks_available_notification_on",
            wifiNetworksAvailableNotificationOn
        )
        information.put("wifi_watchdog_on", wifiWatchdogOn)
        information.put("adb_enabled", adbEnabled)
        information.put("wifi_max_dhcp_retry_count", wifiMaxDhcpRetryCount)

        information.put("debug_app", debugApp)
        information.put(
            "wifi_mobile_data_transition_wake_lock_timeout_ms",
            wifiMobileDataTransitionWakelockTimeoutMs
        )
        information.put("wifi_networks_available_repeat_delay", wifiNetworksAvailableRepeatDelay)
        information.put("wifi_num_open_networks_kept", wifiNumOpenNetworksKept)

        return information
    }

    fun getPhoneSettingsField(context: Context, fieldName: String): String {
        return Settings.Global.getString(context.contentResolver, fieldName) ?: ""
    }

    fun getHardwareInformation(): JSONObject {
        val deviceInformation = JSONObject()
        deviceInformation.put("Board", Build.BOARD)
        deviceInformation.put("Bootloader", Build.BOOTLOADER)
        deviceInformation.put("Brand", Build.BRAND)
        deviceInformation.put("Device", Build.DEVICE)
        deviceInformation.put("Model", Build.MODEL)
        deviceInformation.put("Product", Build.PRODUCT)
        deviceInformation.put("manufacture", Build.MANUFACTURER)
        deviceInformation.put("TAGS", Build.TAGS)
        deviceInformation.put(
             "Build",
            "Release " + Build.VERSION.RELEASE + ", Inc: '" + Build.VERSION.INCREMENTAL + "'"
        )
        deviceInformation.put("Display", Build.DISPLAY)
        deviceInformation.put("Fingerprint", Build.FINGERPRINT)
        deviceInformation.put("ID", Build.ID)
        deviceInformation.put("Time", Build.TIME)
        deviceInformation.put("Type", Build.TYPE)
        deviceInformation.put("User", Build.USER)
        deviceInformation.put("Host", Build.HOST)
        return deviceInformation

    }

    fun getScreenInformation(): JSONObject {
        val screenInformation = JSONObject()
        val metrics = DisplayMetrics()
        if (metrics != null) {
            screenInformation.put("density", metrics.density)
            screenInformation.put("densityDpi", metrics.densityDpi)
            screenInformation.put("scaledDensity", metrics.scaledDensity)
            screenInformation.put("xdpi", metrics.xdpi)
            screenInformation.put("ydpi", metrics.ydpi)
            screenInformation.put("heightPixels", metrics.heightPixels)
            screenInformation.put("widthPixels", metrics.widthPixels)
        }
        screenInformation.put("DENSITY_DEFAULT", DisplayMetrics.DENSITY_DEFAULT)
        screenInformation.put("DENSITY_LOW", DisplayMetrics.DENSITY_LOW)
        screenInformation.put("DENSITY_MEDIUM", DisplayMetrics.DENSITY_MEDIUM)
        screenInformation.put("DENSITY_HIGH", DisplayMetrics.DENSITY_HIGH)
        return screenInformation
    }

    fun getVolumeInformation(audioManager: AudioManager): JSONObject {
        val volumeInformation = JSONObject()

        if (audioManager != null) {
            val streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
            val max_streamVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            val streamVolume2 = audioManager.getStreamVolume(AudioManager.STREAM_ALARM)
            val max_streamVolume2 = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM)
            val streamVolume3 = audioManager.getStreamVolume(AudioManager.STREAM_RING)
            val max_streamVolume3 = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)
            volumeInformation.put("media_current_volume", streamVolume)
            volumeInformation.put("media_max_volume", max_streamVolume)
            volumeInformation.put("alarm_current_volume", streamVolume2)
            volumeInformation.put("alarm_max_volume", max_streamVolume2)
            volumeInformation.put("ringtone_current_volume", streamVolume3)
            volumeInformation.put("ringtone_max_volume", max_streamVolume3)
        }
        return volumeInformation
    }
}
