package com.begini.androidsdkv2.model.devicedata

import com.squareup.moshi.Json

data class GalleryExifModel(

    @Json(name = "light_source")
    val lightSource:String?,
    @Json(name = "y_resolution")
    val yResolution:String?,
    @Json(name = "pixel_x_dimension")
    val pixelXDimension:String?,
    @Json(name = "compression")
    val compression:String?,
    @Json(name = "date_taken")
    val dateTaken:String?,
    @Json(name = "exposure_mode")
    val eposureMode:String?,
    @Json(name = "flash")
    val flash:String?,
    @Json(name = "flashpix_version")
    val flashpixVersion:String?,
    @Json(name = "scene_capture_type")
    val SceneCaptureType:String?,
    @Json(name = "date_time")
    val dateTime:String?,
    @Json(name = "metering_mod")
    val meteringMod:String?,
    @Json(name = "exif_version")
    val exifVersion:String?,
    @Json(name = "jpeg_interchange_format_length")
    val jpegInterchangeFormatLength:String?,
    @Json(name = "image_unique_id")
    val imageUniqueID:String?,
    @Json(name = "exposure_bias_value")
    val exposureBiasValue:String?,
    @Json(name = "maker_note")
    val makerNote:String?,
    @Json(name = "exposure_program")
    val exposureProgram:String?,
    @Json(name = "shutter_speed_value")
    val shutterSpeedValue:String?,
    @Json(name = "color_space")
    val colorSpace:String?,
    @Json(name = "filename")
    val filename:String?,
    @Json(name = "pixel_y_dimension")
    val pixelYDimension:String?,
    @Json(name = "file_size")
    val fileSize:String?,
    @Json(name = "date_time_original")
    val dateTimeOriginal:String?,
    @Json(name = "image_width")
    val imageWidth:String?,
    @Json(name = "brightness_value")
    val brightnessValue:String?,
    @Json(name = "make")
    val make:String?,
    @Json(name = "date_time_digitized")
    val dateTimeDigitized:String?,
    @Json(name = "fNumber")
    val fNumber:String?,
    @Json(name = "aperture_value")
    val apertureValue:String?,
    @Json(name = "focal_length")
    val focalLength:String?,
    @Json(name = "thumbnail_image_length")
    val thumbnailImageLength:String?,
    @Json(name = "software")
    val software:String?,
    @Json(name = "iso_speed_ratings")
    val isoSpeedRatings:String?,
    @Json(name = "model")
    val model:String?,
    @Json(name = "orientation")
    val orientation:String?,
    @Json(name = "exposure_time")
    val exposureTime:String?,
    @Json(name = "image_length")
    val image_length:String?,
    @Json(name = "max_aperture_value")
    val maxApertureValue:String?,
    @Json(name = "thumbnail_image_width")
    val thumbnailImageWidth:String?,
    @Json(name = "white_balance")
    val whiteBalance:String?,
    @Json(name = "jpeg_interchange_format")
    val jpegInterchangeFormat:String?,
    @Json(name = "y_cb_cr_positioning")
    val yCbCrPositioning:String?,
    @Json(name = "x_resolution")
    val x_resolution:String?,

)