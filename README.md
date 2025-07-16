HttpSniffer Library
📡 HttpSniffer is a lightweight Android library that monitors all HTTP requests made through OkHttpClient.
It comes with a built-in UI to display captured requests, a Response Viewer with Pretty JSON formatting, and smart status code colors.

✨ Features
✅ Automatically captures all HTTP requests sent via OkHttp
✅ Built-in UI to display captured requests
✅ Response Viewer with Pretty Print JSON
✅ Smart status code colors (200 = green, 300 = orange, 400+ = red)
✅ Shows “No HTTP requests yet” when empty
✅ Real-time callback for every captured request

📦 Installation
1️⃣ Add JitPack to your repositories in settings.gradle or top-level build.gradle:

gradle
Copy
Edit
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
2️⃣ Add the dependency in your app-level build.gradle:

gradle
Copy
Edit
dependencies {
    implementation("com.github.afiksoco:HttpSniffer:v1.1.1")
}
3️⃣ Sync Gradle ✅

🚀 Quick Start
1. Add the Interceptor to your OkHttpClient
kotlin
Copy
Edit
val client = OkHttpClient.Builder()
    .addInterceptor(HttpSnifferInterceptor())
    .build()
2. Open the Sniffer UI
kotlin
Copy
Edit
HttpSniffer.showSnifferUI(this)
For example:

kotlin
Copy
Edit
findViewById<Button>(R.id.openSnifferButton).setOnClickListener {
    HttpSniffer.showSnifferUI(this)
}
3. (Optional) Listen for Real-Time Callbacks
kotlin
Copy
Edit
HttpSniffer.registerCallback(object : SnifferCallback {
    override fun onRequestSniffed(request: SniffedRequest) {
        Log.d("Sniffer", "New request: ${request.method} ${request.url} → ${request.responseCode}")
    }
})
And don’t forget to unregister in onDestroy:

kotlin
Copy
Edit
HttpSniffer.unregisterCallback()
🖼️ Screenshots
📌 Requests List with Status Code Colors

Here the list of all HTTP requests

<img width="359" height="287" alt="image" src="https://github.com/user-attachments/assets/1891ddf1-afe6-447b-802b-b1784462f2e2" />

📌 Response Viewer (Raw)

Displays the response body as raw text.

<img width="325" height="441" alt="image" src="https://github.com/user-attachments/assets/5600723b-478b-4c85-a8e3-dd3aa2527c63" />

📌 Response Viewer (Pretty JSON)

Toggle Pretty JSON formatting for better readability.

<img width="328" height="447" alt="image" src="https://github.com/user-attachments/assets/b4ee88fb-d763-46ba-aa0a-0baaa632ff30" />


✅ Demo API
For a quick demo, you can use JSONPlaceholder:

GET https://jsonplaceholder.typicode.com/posts/1

POST https://jsonplaceholder.typicode.com/posts

PUT https://jsonplaceholder.typicode.com/posts/1

🔄 Updating the Library
Whenever you update the library:

bash
Copy
Edit
git add .
git commit -m "New features"
git tag v1.1.2
git push origin main
git push origin v1.1.2
Then consumers can update like this:

gradle
Copy
Edit
implementation("com.github.afiksoco:HttpSniffer:v1.1.2")
📜 License
MIT License – Free to use in any project.

