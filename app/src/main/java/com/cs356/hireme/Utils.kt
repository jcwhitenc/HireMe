package com.cs356.hireme

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.ImageView
import java.util.concurrent.Executors


fun loadImage(url: String, imageView: ImageButton?) {
    // Declaring executor to parse the URL
    val executor = Executors.newSingleThreadExecutor()

    // Once the executor parses the URL
    // and receives the image, handler will load it
    // in the ImageView
    val handler = Handler(Looper.getMainLooper())

    // Initializing the image
    var image: Bitmap? = null

    // Only for Background process (can take time depending on the Internet speed)
    executor.execute {
        // Tries to get the image and post it in the ImageView
        // with the help of Handler
        try {
            val `in` = java.net.URL(url).openStream()
            image = BitmapFactory.decodeStream(`in`)

            // Only for making changes in UI
            handler.post {
                imageView!!.setImageBitmap(image)
            }
        }

        // If the URL doesnot point to
        // image or any other kind of failure
        catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
