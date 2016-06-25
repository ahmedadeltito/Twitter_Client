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
7. [Realm Database](https://realm.io)

# Plugins that are used
1. [Android Drawable Importar](http://androidgifts.com/material-design-icons-android-studio-drawable-importer/)
2. [Fabric for Android Studio](https://docs.fabric.io/android/fabric/integration.html#ide-plugin)

# Software Architectural Pattern that is used
1. [MVVM (Model–View–ViewModel)](https://en.wikipedia.org/wiki/Model–view–viewmodel)

-----------------------------------------------------------------------------------------------------

# User Documentation :
##1. **Splash Screen :**

This screen contains Image View and Text View with Fade in Animation. After finishing animation, the user either loggen in to Followers Screen or Login Screen.

##2. **Login Screen :**

User can press on Login in button to Log in this application via your Twitter Account. If Twitter Application exists in your mobile phone, so it will be redirected to the TwitterApplication to get Authontecation Permission otherwise A Webpage will be opened.

##3. **Followers Screen :**

Followers will be appeared that are realted to the logged user. Pull to Refresh and Infinite Scrolling are applied

##4. **Follower Details Screen :**

Details that related to each user will be appeared with his/her recent ten tweets that is done by this specific user.

##5. **Logout :**

Button above is nothing but a logout button that will appear a popup confirmation dialog. If the user presses on logout, then the application will be restarted otherwise nothing will be happened.

##6. **Change Langauge:**

Application supports Arabic and English Language with Right-To-Left and Left-To-Right Layout Direction Methodology.

##7. **Caching :**

Local Database is applied in these two screen Followers and Follower Detail Screens by using Realm Database Library

-----------------------------------------------------------------------------------------------------

# Technical Documentation :
##1.**Fabric Framework :**

Fabric is a mobile platform with modular kits you can mix and match to build the best apps. Fabric is tightly integrated into your dev environment, making adding new services a breeze. Start with what you need from Fabric today, and quickly add more kits as your needs grow.

To get up and running with Fabric, [sign up for a free account](https://fabric.io/sign_up) and follow the on-screen instructions to quickly get set up!

Once your account is configured, it’s time to onboard your first app to Fabric by installing a kit. Kits can be installed [manually](https://fabric.io/kits) or using the [Fabric IDE plugin](https://fabric.io/downloads/android) for Android Studio or IntelliJ (what i used here in this application is Fabric Android Studio IDE Plugin. it's great and works fine).

If you have an Application subclass, then you can place ```java Fabric.with()``` in the ```java onCreate()``` method. Otherwise, if you have multiple launch activities in your app, then add ```java Fabric.with()``` to each launch activity. Fabric is only initialized the first time you call start, so calling it multiple times won’t cause any issues.

[This Twitter University Official Video](https://www.youtube.com/watch?v=H9RLFoqTqOQ) illustrate the Fabric Framework, Third Party Libraries that are integrated with Frabric Framework.

##2.**Retrofit :**

It is a type-safe HTTP client for Android and Java by Square. By using Retrofit in Android we can seamlessly capture JSON responses from a web API. It is different from other libraries because Retrofit gives us an easy way to use since it uses the GSON library in background to parse the responses. All we need to do is define a POJO (Plain Old Java Object) to do all the parsing.

[This tutorial is very helpful](http://androidgifts.com/retrofit-android-library-tutorial-library-6/), discussing how to deal with Retrofit Library in the best way (Your comment is more than welcomed since this tutorials is written by me.)

##3.**RxAndroid :**

This module adds the minimum classes to RxJava that make writing reactive components in Android applications easy and hassle-free. More specifically, it provides a Scheduler that schedules on the main thread or any given Looper.
**So what is RxJava ?**
RxJava is a Java VM implementation of [Reactive Extensions](http://reactivex.io) (a library for composing asynchronous and event-based programs by using observable sequences.) It extends the [observer pattern](http://www.tutorialspoint.com/design_pattern/observer_pattern.htm) to support sequences of data/events and adds operators that allow you to compose sequences together declaratively while abstracting away concerns about things like low-level threading, synchronization, thread-safety and concurrent data structures.

[This tutorial is very helpful](https://medium.com/@kurtisnusbaum/rxandroid-basics-part-1-c0d5edcf6850#.b3t73pv08), showing you how RxJava and RxAndroid works

##4.**Piccaso :**

Images add much-needed context and visual flair to Android applications. Picasso allows for hassle-free image loading in your application—often in one line of code!

Many common pitfalls of image loading on Android are handled automatically by Picasso:
- Handling ImageView recycling and download cancelation in an adapter.
- Complex image transformations with minimal memory use.
- Automatic memory and disk caching.

[This tutorial is very helpful](http://androidgifts.com/picasso-android-library-tutorial/), showing you how to user Piccaso Library in the best way.

##5.**Native Android Data Binding :**

The Data Binding Library offers both flexibility and broad compatibility. It's a support library, so you can use it with all Android platform versions back to Android 2.1 (API level 7+).

Yigit Boyar and George Mount of Google developed Android’s Data Binding Library to allow developers to build rich & responsive user experiences with minimal effort.

All you want to know about Data Binding and its Official Talk that is done by Yigit Boyar and George Mount is [here](https://realm.io/news/data-binding-android-boyar-mount/).

##6.**Parceler :**

In Android, Parcelables are a great way to serialize Java Objects between Contexts. Compared with traditional Serialization, Parcelables take on the order of 10x less time to both serialize and deserialize. There is a major flaw with Parcelables, however. Parcelables contain a ton of boilerplate code. To implement a Parcelable, you must mirror the ```java writeToParcel()``` and ```java createFromParcel()``` methods such that they read and write to the Parcel in the same order. Also, a Parcelable must define a ```java public static final Parcelable.Creator CREATOR``` in order for the Android infrastructure to be able to leverage the serialization code.

Parceler is a code generation library that generates the Android Parcelable boilerplate source code. No longer do you have to implement the Parcelable interface, the ```java writeToParcel()``` or ```java createFromParcel()``` or the ```java public static final CREATOR```. You simply annotate a POJO with ```java @Parcel``` and Parceler does the rest. Because Parceler uses the Java JSR-269 Annotation Processor, there is no need to run a tool manually to generate the Parcelable code. Just annotate your Java Bean, compile and you are finished.

[This github link](https://github.com/johncarl81/parceler) is a fully details tutorial about how it works and how you can integrate it with your application.

##7. **Realm Database :**

**Easy**
Get set up in minutes, not hours. Even porting a whole app to Realm is faster than just setting up other databases. After that, you'll have a database that works right inside your language, with features like fluent interfaces, field annotations, and more.

**Fast**
Thanks to its zero-copy design, Realm is much faster than an ORM, and often faster than raw SQLite. So chain queries as deep as you like. You won’t even have time to notice how fast they are.

**Advanced**
All this ease and speed doesn't mean you give up power. Realm has all the features you expect: encryption, change notifications, easy migrations, sane threading, and more.

[This Video](https://www.youtube.com/watch?v=x_5Ifs8kIrI) is more that awesome made by Vivz that discuss Realm Database Library with Diagrams and shows you how to integrate with it easily from A to Z.

-----------------------------------------------------------------------------------------------------

# MVVM Software Architecture Pattern :

The Data Binding library for android is something that I’ve been keen to check out for a short while. I decided to experiment with it using the Model-View-ViewModel architectural approach.

##**What is MVVM?**

Model-View-ViewModel is an architecural approach used to abstract the state and behaviour of a view, which allows us to separate the development of the UI from the business logic. This is accomplished by the introduction of a ViewModel, whos responsibility is to expose the data objects of a model and handle any of the applications logic involved in the display of a view.

This approach (MVVM) is made up of three core components, each with it’s own distinct and separate role:
- **Model** - Data model containing business and validation logic
- **View** - Defines the structure, layout and appearance of a view on screen
- **ViewModel** - Acts a link between the View and Model, dealing with any view logic

![alt tag](https://cdn-images-1.medium.com/max/1400/1*WfT-BCzN0ZAGzdE30oea1g.png)

##**The architecture for MVC is as follows:**
- **The View** sits at the top of the architure with the Controller below it, followed by the Model
- **The Controller** is aware of both the View and Model
- **The View** is aware of just the Model and is notified whenever there are changes to it

##**Overall view of MVVM Architecutre :**
- **Model Layer:** Like in MVP, DataManager holds a reference to the RestApi (like Retrofit), database (SQLite), etc. Typical scenario is that model layer gets data from the backend and saves data. The difference between MVP and MVVM from the perspective of the Model Layer is that in MVVM architecture DataManager returns response to Activity/Fragment instead to Presenter. That means that Activity/Fragment is aware of business logic (POJO).
- **View Layer**is a combination of Activity/Fragment with XML and binding. Typical scenario is that Activity requests data from the backend, gets data (POJO) and forwards it to ViewModel Layer. ViewModel Layer updates the UI with the new data.
- **ViewModel** is the middle man between the View Layer and the model (POJO). It receives data from Model Layer and updates the View Layer. Also, it manipulates the model state (fields in POJO objects) as a result from user interaction from the View Layer.

-----------------------------------------------------------------------------------------------------

# MVVM Example implemented on This Application

![alt tag](https://s31.postimg.org/ox6mx71uj/Twitter_Client.png)

**- FollowersActivity VIEW :**
Defines the structure, layout and appearance of a view on FollowersActivity screen. Data binding configuration takes place here since you make a data binding between ViewModel and its XML Layout.

**- FollowersViewModel VIEWMODEL :**
Acts a link between the FollowersActivity "View" and Followers "Model", send notification to FollowersActivity "View" and FollowersActivity "View" make a data minding commands to FollowersViewModel.

**- Followers MODEL :**
Data model containing business and validation logic, it's a POJO "JavaBean class" that is going to be managed by RestApiClient. Since it will hold the Followers retrieved data from Twitter API Webservice or from Realm Local Database.

-----------------------------------------------------------------------------------------------------

# Localization and Native RTL support

##**Localization from Arabic to English and vice verse:**
While developing your awesome application, sometimes you are required to add a feature to change the language of your app on the fly. However, Android OS does not directly support this behaviour. And therefore, you need to solve this situation in some other ways. “LocaleHelper” is the solution all you need. You just have to initialize locale on your application’s main class. After that all your language changes will persist.
if you call ```java onCreate(Context context)``` constructor it will just set default locale of your device as the default locale of your application.

if you call ```java onCreate(Context context, String defaultLanguage)``` constructor it will set the given language as the default language of your application for the first time that your application opens. After that you can change your locale by using the buttons or any other method that you provide to your users through your layout.

##**Native RTL support:**
Android 4.1 (Jelly Bean) introduced limited support for bidirectional text in TextView and EditText elements, allowing apps to display and edit text in both left-to-right (LTR) and right-to-left (RTL) scripts. Android 4.2 added full native support for RTL layouts, including layout mirroring, allowing you to deliver the same great app experience to all of your users, whether their language uses a script that reads right-to-left or one that reads left-to-right.

To take advantage of RTL layout mirroring, simply make the following changes to your app:
- Declare in your app manifest that your app supports RTL mirroring.
- Specifically, add ```java android:supportsRtl="true"``` to the ```java <application>``` element in your manifest file.
- Change all of your app's ```java"left/right"``` layout properties to new ```java"start/end" equivalents```.

If you are targeting your app to Android 4.2 (the app's targetSdkVersion or minSdkVersion is 17 or higher), then you should use “start” and “end” instead of “left” and “right”. For example, android:paddingLeft should become android:paddingStart.
If you want your app to work with versions earlier than Android 4.2 (the app's targetSdkVersion or minSdkVersion is 16 or less), then you should add “start” and end” in addition to “left” and “right”. For example, you’d use both android:paddingLeft and android:paddingStart.

-----------------------------------------------------------------------------------------------------

###**If you want to know more details about this project, please take a look on [Project Documentation](https://github.com/ahmed-adel-said/Twitter_Client/tree/master/documentation), since it is a HTML generated documentation with great user interface and contains the needed UML diagrams that you need to get the full image**
