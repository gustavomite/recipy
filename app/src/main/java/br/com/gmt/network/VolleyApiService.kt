package br.com.gmt.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class VolleyApiService(val context: Context, val callback: RequestCallback) {
    // Instantiate the RequestQueue.
    val queue = Volley.newRequestQueue(context)
    val url = "https://jsonplaceholder.typicode.com/posts"

    interface RequestCallback {
        fun onEnd(response: String)
    }

    // Request a string response from the provided URL.
    val stringRequest = StringRequest(
        Request.Method.GET, url,
        { response ->
            callback.onEnd("Response is: ${response.substring(0, 500)}")
        },
        {
            callback.onEnd("That didn't work!")
        })

    fun get() {
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}