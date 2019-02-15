package br.com.wagnerrodrigues.fcmapi

import org.json.JSONObject
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


@RestController
@RequestMapping()
class NoteController {

    val userDeviceID = "cBNneB8xnkc:APA91bGM2Ibftl4UjvLFxjiaGS1sQc5_C3WYJDLpVGI_ghhspIRiWvLeNi8yYMtm7Okja0Z3lOzPqB-6QD5L9Leby-cE14pkSKVtfnvoXqr7K1lo0_79AOxu7EgzFM-zjKjazUd9XzQP"
    @PostMapping("/push")
    fun list(): String {
        pushFCMNotification(userDeviceID)
        return "OK"
    }

    // Method to send Notifications from server to client end.

    val AUTH_KEY_FCM = "AIzaSyAv-ob2Vqz-4iTYsssAHqZOtWcH47rLdVQ"
    val API_URL_FCM = "https://fcm.googleapis.com/fcm/send"

// userDeviceIdKey is the device id you will query from your database

    @Throws(Exception::class)
    fun pushFCMNotification(userDeviceIdKey: String) {

        val authKey = AUTH_KEY_FCM // You FCM AUTH key
        val FMCurl = API_URL_FCM

        val url = URL(FMCurl)
        val conn = url.openConnection() as HttpURLConnection

        conn.useCaches = false
        conn.doInput = true
        conn.doOutput = true

        conn.requestMethod = "POST"
        conn.setRequestProperty("Authorization", "key=$authKey")
        conn.setRequestProperty("Content-Type", "application/json")

        val data = JSONObject()
        data.put("id", "123")
        data.put("outroparam", "teste")


        val json = JSONObject()
        json.put("to", userDeviceIdKey.trim { it <= ' ' })
        val info = JSONObject()
        info.put("title", "Notificatoin Title") // Notification title
        info.put("body", "Hello Test notification") // Notification body
        info.put("click_action", "br.com.wagnerrodrigues.detalhe")

        json.put("notification", info)
        json.put("data", data)

        val wr = OutputStreamWriter(conn.outputStream)
        wr.write(json.toString())
        wr.flush()
        conn.inputStream

    }
}
