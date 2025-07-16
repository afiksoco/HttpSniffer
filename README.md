# HttpSniffer Library

📡 **HttpSniffer** is a lightweight Android library that monitors all HTTP requests made through `OkHttpClient`.
It comes with a **built-in UI** to display captured requests, a **Response Viewer** with Pretty JSON formatting, and smart status code colors.

---

## ✨ Features

✅ Automatically captures all HTTP requests sent via OkHttp  
✅ Built-in UI to display captured requests  
✅ Response Viewer with Pretty Print JSON  
✅ Smart status code colors (**200 = green**, **300 = orange**, **400+ = red**)  
✅ Shows **“No HTTP requests yet”** when empty  
✅ Real-time callback for every captured request

---

## 📦 Installation

1️⃣ **Add JitPack to your repositories** in `settings.gradle` or top-level `build.gradle`:

```gradle
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

2️⃣ **Add the dependency** in your **app-level `build.gradle`**:

```gradle
dependencies {
    implementation("com.github.afiksoco:HttpSniffer:v1.1.1")
}
```

3️⃣ **Sync Gradle ✅**

---

## 🚀 Quick Start

### 1️⃣ Add the Interceptor to your OkHttpClient

```kotlin
val client = OkHttpClient.Builder()
    .addInterceptor(HttpSnifferInterceptor())
    .build()
```

---

### 2️⃣ Open the Sniffer UI

```kotlin
HttpSniffer.showSnifferUI(this)
```

Example usage:

```kotlin
findViewById<Button>(R.id.openSnifferButton).setOnClickListener {
    HttpSniffer.showSnifferUI(this)
}
```

---

### 3️⃣ (Optional) Listen for Real-Time Callbacks

```kotlin
HttpSniffer.registerCallback(object : SnifferCallback {
    override fun onRequestSniffed(request: SniffedRequest) {
        Log.d(
            "Sniffer",
            "New request: ${request.method} ${request.url} → ${request.responseCode}"
        )
    }
})
```

And don’t forget to **unregister in `onDestroy`**:

```kotlin
HttpSniffer.unregisterCallback()
```

---

## 🖼️ Screenshots

📌 **Requests List with Status Code Colors**  
Here the list of all HTTP requests with smart status coloring (green for 200, red for 400+).

![Requests List](https://github.com/user-attachments/assets/d29d02d5-6f67-440e-95aa-8b72db39d79b)

---

📌 **Response Viewer (Raw)**  
Displays the response body as **raw text**.

![Response Raw](https://github.com/user-attachments/assets/5600723b-478b-4c85-a8e3-dd3aa2527c63)

---

📌 **Response Viewer (Pretty JSON)**  
Toggle **Pretty JSON formatting** for better readability.

![Response Pretty JSON](https://github.com/user-attachments/assets/b4ee88fb-d763-46ba-aa0a-0baaa632ff30)

---

## ✅ Demo API

For a quick demo, you can use **[JSONPlaceholder](https://jsonplaceholder.typicode.com/)**:

- `GET https://jsonplaceholder.typicode.com/posts/1`
- `POST https://jsonplaceholder.typicode.com/posts`
- `PUT https://jsonplaceholder.typicode.com/posts/1`

---

## 🔄 Updating the Library

Whenever you update the library:

```bash
git add .
git commit -m "New features"
git tag v1.1.2
git push origin main
git push origin v1.1.2
```

Then consumers can update like this:

```gradle
implementation("com.github.afiksoco:HttpSniffer:v1.1.2")
```

---

## 📜 License

MIT License – Free to use in any project.
