# Twitter Client Android Application

A Simple Twitter Client Application that is integrated with Fabric Twitter SDK, 
implementing MVVM Model and using a lot of Great Libraries

-----------------------------------------------------------------------------------------------------

# Application Features
1. Splash Screen with Fading in Animation
2. **Log in** by your Twitter Account (if Twitter Application exists in your mobile phone, so it will be redirected to the TwitterApplication to get Authontecation Permission otherwise A Webpage will be opened).
3. **Save login status** of the logged user.
4. **Get the user followers** in a Material Design Recycler List View with **Pull to Refresh** and **Infinite Scrolling**.
5. Followers List works in **offline mode**.
6. **Get Followers Infromation** in a greate User Experience Screen with **Sticky Headr** for Followers Background Image.
7. **Localization** (Arabic to English and vice versa).

# Libraries are used
1. [Fabric Framework (Twitter SDK)](https://docs.fabric.io/android/fabric/overview.html)

2. [Retrofit](http://androidgifts.com/retrofit-android-library-tutorial-library-6/)

3. [RxAndroid](https://github.com/ReactiveX/RxAndroid) and [RxJava Realm](https://realm.io/news/using-realm-with-rxjava/)

4. [Piccaso](http://androidgifts.com/picasso-android-library-tutorial/)

5. [Native Android Data Binding](https://developer.android.com/topic/libraries/data-binding/index.html)

6. [Parceler](https://github.com/johncarl81/parceler)

7. [Realm ORM Database](https://realm.io)

# Plugins that are used
1. [Android Drawable Importar](http://androidgifts.com/material-design-icons-android-studio-drawable-importer/)
2. [Fabric for Android Studio](https://docs.fabric.io/android/fabric/integration.html#ide-plugin)

# Software Architectural Pattern that is used
1. [MVVM (Model–View–ViewModel)](https://en.wikipedia.org/wiki/Model–view–viewmodel)

-----------------------------------------------------------------------------------------------------

# User Documentation :
1. **Splash Screen :**
This screen contains Image View and Text View with Fade in Animation. After finishing animation, the user either loggen in to Followers Screen or Login Screen.
2. **Login Screen :**
User can press on Login in button to Log in this application via your Twitter Account. If Twitter Application exists in your mobile phone, so it will be redirected to the TwitterApplication to get Authontecation Permission otherwise A Webpage will be opened.
3. **Followers Screen :**
Followers will be appeared that are realted to the logged user. Pull to Refresh and Infinite Scrolling are applied
4. **Follower Details Screen :**
Details that related to each user will be appeared with his/her recent ten tweets that is done by this specific user.
5. **Logout :**
Button above is nothing but a logout button that will appear a popup confirmation dialog. If the user presses on logout, then the application will be restarted otherwise nothing will be happened.
6. **Change Langauge:**
Application supports Arabic and English Language with Right-To-Left and Left-To-Right Layout Direction Methodology.
7. **Caching :**
Local Database is applied in these two screen Followers and Follower Detail Screens by using Realm ORM Database Library

-----------------------------------------------------------------------------------------------------

# Technical Documentation :
1.**Fabric Framework :**

Fabric is a mobile platform with modular kits you can mix and match to build the best apps. Fabric is tightly integrated into your dev environment, making adding new services a breeze. Start with what you need from Fabric today, and quickly add more kits as your needs grow.

To get up and running with Fabric, [sign up for a free account](https://fabric.io/sign_up) and follow the on-screen instructions to quickly get set up!

Once your account is configured, it’s time to onboard your first app to Fabric by installing a kit. Kits can be installed [manually](https://fabric.io/kits) or using the [Fabric IDE plugin](https://fabric.io/downloads/android) for Android Studio or IntelliJ (what i used here in this application is Fabric Android Studio IDE Plugin. it's great and works fine).

If you have an Application subclass, then you can place ```java Fabric.with()``` in the ```java onCreate()``` method. Otherwise, if you have multiple launch activities in your app, then add ```java Fabric.with()``` to each launch activity. Fabric is only initialized the first time you call start, so calling it multiple times won’t cause any issues.

[This Twitter University Official Video](https://www.youtube.com/watch?v=H9RLFoqTqOQ) illustrate the Fabric Framework, Third Party Libraries that are integrated with Frabric Framework.

2.**Retrofit :**

It is a type-safe HTTP client for Android and Java by Square. By using Retrofit in Android we can seamlessly capture JSON responses from a web API. It is different from other libraries because Retrofit gives us an easy way to use since it uses the GSON library in background to parse the responses. All we need to do is define a POJO (Plain Old Java Object) to do all the parsing.

[This tutorial is very helpful](http://androidgifts.com/retrofit-android-library-tutorial-library-6/), discussing how to deal with Retrofit Library in the best way (Your comment is more than welcomed since this tutorials is written by me.)

3.**RxAndroid :**

This module adds the minimum classes to RxJava that make writing reactive components in Android applications easy and hassle-free. More specifically, it provides a Scheduler that schedules on the main thread or any given Looper.
**So what is RxJava ?**
RxJava is a Java VM implementation of [Reactive Extensions](http://reactivex.io) (a library for composing asynchronous and event-based programs by using observable sequences.) It extends the [observer pattern](http://www.tutorialspoint.com/design_pattern/observer_pattern.htm) to support sequences of data/events and adds operators that allow you to compose sequences together declaratively while abstracting away concerns about things like low-level threading, synchronization, thread-safety and concurrent data structures.

[This tutorial is very helpful](https://medium.com/@kurtisnusbaum/rxandroid-basics-part-1-c0d5edcf6850#.b3t73pv08), showing you how RxJava and RxAndroid works

4.**Piccaso :**

Images add much-needed context and visual flair to Android applications. Picasso allows for hassle-free image loading in your application—often in one line of code!

Many common pitfalls of image loading on Android are handled automatically by Picasso:
- Handling ImageView recycling and download cancelation in an adapter.
- Complex image transformations with minimal memory use.
- Automatic memory and disk caching.

[This tutorial is very helpful](http://androidgifts.com/picasso-android-library-tutorial/), showing you how to user Piccaso Library in the best way.

5.**Native Android Data Binding :**

The Data Binding Library offers both flexibility and broad compatibility. It's a support library, so you can use it with all Android platform versions back to Android 2.1 (API level 7+).

Yigit Boyar and George Mount of Google developed Android’s Data Binding Library to allow developers to build rich & responsive user experiences with minimal effort.

All you want to know about Data Binding and its Official Talk that is done by Yigit Boyar and George Mount is [here](https://realm.io/news/data-binding-android-boyar-mount/).

6.**Parceler :**

In Android, Parcelables are a great way to serialize Java Objects between Contexts. Compared with traditional Serialization, Parcelables take on the order of 10x less time to both serialize and deserialize. There is a major flaw with Parcelables, however. Parcelables contain a ton of boilerplate code. To implement a Parcelable, you must mirror the writeToParcel() and createFromParcel() methods such that they read and write to the Parcel in the same order. Also, a Parcelable must define a public static final Parcelable.Creator CREATOR in order for the Android infrastructure to be able to leverage the serialization code.

Parceler is a code generation library that generates the Android Parcelable boilerplate source code. No longer do you have to implement the Parcelable interface, the writeToParcel() or createFromParcel() or the public static final CREATOR. You simply annotate a POJO with @Parcel and Parceler does the rest. Because Parceler uses the Java JSR-269 Annotation Processor, there is no need to run a tool manually to generate the Parcelable code. Just annotate your Java Bean, compile and you are finished. By default, Parceler will serialize the fields of your instance directly:
