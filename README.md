# DaggerHiltDemo
 DagerHiltDemo demonstrates user profile database with Hilt, Coroutines, Flow, Jetpack (Room, ViewModel) based on MVVM architecture.
 It is a user profile interface. Where we can store a user profile, and all of its details locally on our device.
 
![USER PROFILE DATABASE SAVED LOCALLY ON DEVICE](https://user-images.githubusercontent.com/87520905/226391235-7f22ac4e-fd40-469f-a43b-ac34c2c10921.png)

# Tech stack & Open-source libraries. 
- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://developer.android.com/kotlin/coroutines) + [Flow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Jetpack
  - Lifecycle: Observe Android lifecycles and handle UI states upon the lifecycle changes.
  - ViewModel: Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
  - Room: Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
  - Hilt: Dagger Hilt dependency injection library was crucial since it offers a uniform method of utilizing DI in your application by handling the containers for all Android classes in the project.
-  Architecture 
   - MVVM : MVVM stands for Model-View-ViewModel architecture. There are several advantages of using MVVM in projects also it is highly recommended by google and android developers teams to use MVVM architecture.
- [Gldie](https://github.com/bumptech/glide) : To load image into the image view. Glide is a fast and efficient open source media management and image loading framework for Android that wraps media decoding, memory and disk caching, and resource pooling into a simple and easy to use interface.







