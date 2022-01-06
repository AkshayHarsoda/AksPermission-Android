# AksPermission-Android
[![](https://jitpack.io/v/AkshayHarsoda/AksPermission-Android.svg)](https://jitpack.io/#AkshayHarsoda/AksPermission-Android)
- This Repositories will help you to get runtime permission easily.
- This project has been **written in Kotlin language**.
- This Repositories use in **Java** or **Kotlin** both language
- If any permission ***shouldShowRequestPermissionRationale*** then it will ask again automatic.

## Using `build.gradle`
###### Step 1. Add the JitPack repository to your project's `build.gradle`
```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

###### Step 2. Add the dependency to your module's `build.gradle`
```groovy
	dependencies {
	        implementation 'com.github.AkshayHarsoda:AksPermission-Android:latest_build_version'
	}
```


## How To Use this librarey

#### In Kotlin

###### Ask Single Permission:
```kotlin
        AksPermission.with(this@MainActivity)
                    .permissions(Manifest.permission.READ_CALENDAR)
                    .isShowDefaultSettingDialog(
                        // Pass false if you don't want to showing a default setting dialog if some permissions are permanently denied.
                        true // Default value is true,
                    )
                    .request { grantedList ->
                        // you have get the all granted permissions list on here
                        Log.i(TAG, "Ask Single Permission -> Granted Permission List -> $grantedList")
                        // Auto action after all permissions granted
                        //  Write Your Code Here
                    }
```

###### Ask Multiple Permission:
```kotlin
        AksPermission.with(this@MainActivity)
                    .permissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO
                    ) // You can pass N number of permission
                    .setDefaultSettingDialog(
                        // If you want to customize default dialog,
                        // which will appear after any permission is permanently denied
                        SettingDialogRequest()
                            .setDialogTitleColor(Color.YELLOW)
                            .setDialogMessageColor(Color.BLUE)
                            .setDialogPositiveColor(Color.GREEN)
                            .setDialogNegativeColor(Color.RED)
                    )
                    .skipAutoAskPermission() // If you want to stop Auto Asking Denied permission
                    .request(
                        onGrantedResult = { grantedList ->
                            Log.i(
                                TAG,
                                "Ask Multiple Permission -> Granted Permission List -> $grantedList"
                            )
                            // Auto action after all permissions granted
                            //  Write Your Code Here
                        },
                        onDeniedResult = { deniedList ->
                            Log.i(
                                TAG,
                                "Ask Multiple Permission -> Denied Permission List -> $deniedList"
                            )
                        },
                        onPermanentlyDeniedResult = { permanentlyDeniedList ->
                            Log.i(
                                TAG,
                                "Ask Multiple Permission -> PermanentlyDenied Permission List -> $permanentlyDeniedList"
                            )
                        }
                    )
```

#### In Java

###### Ask Single Permission:
```java
        AksPermission.with(MainActivity.this)
                        .permissions(Manifest.permission.READ_CALENDAR)
                        .isShowDefaultSettingDialog(
                                // Pass false if you don't want to showing a default setting dialog if some permissions are permanently denied.
                                true // Default value is true,
                        )
                        .request(new PermissionResultListener() {
                            @Override
                            public void onGrantedResult(@NotNull Set<String> fGrantedList) {
                                Log.i(TAG, "Ask Single Permission -> Granted Permission List -> " + fGrantedList);
                                // Auto action after all permissions granted
                                //  Write Your Code Here
                            }
                        });
```

###### Ask Multiple Permission:
```java
        AksPermission.with(MainActivity2.this)
                        .permissions(
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.RECORD_AUDIO
                        ) // You can pass N number of permission
                        .setDefaultSettingDialog(
                                // If you want to customize your settings dialog,
                                // which will appear after any permission is permanently denied
                                new SettingDialogRequest()
                                        .setDialogTitleColor(Color.YELLOW)
                                        .setDialogMessageColor(Color.BLUE)
                                        .setDialogPositiveColor(Color.GREEN)
                                        .setDialogNegativeColor(Color.RED)
                        )
                        .skipAutoAskPermission() // If you want to stop Auto Asking Denied permission
                        .request(new PermissionResultListener() {
                            @Override
                            public void onGrantedResult(@NotNull Set<String> fGrantedList) {
                                Log.i(TAG, "Ask Multiple Permission -> Granted Permission List -> " + fGrantedList);
                                // Auto action after all permissions granted
                                //  Write Your Code Here
                            }

                            @Override
                            public void onDeniedResult(@NotNull Set<String> fDeniedList) {
                                super.onDeniedResult(fDeniedList);
                                Log.i(TAG, "Ask Multiple Permission -> Denied Permission List -> " + fDeniedList);
                            }

                            @Override
                            public void onPermanentlyDeniedResult(@NotNull Set<String> fPermanentlyDeniedList) {
                                super.onPermanentlyDeniedResult(fPermanentlyDeniedList);
                                Log.i(TAG, "Ask Multiple Permission -> PermanentlyDenied Permission List -> " + fPermanentlyDeniedList);
                            }
                        });
```

#### Customize Settings Dialog

###### Chnage Dialog Text:
	SettingDialogRequest()
            .setDialogTitle() // Dialog Title Text
            .setDialogMessage() // Dialog Message Text
            .setDialogPositiveText() // Dialog Positive Button Text
            .setDialogNegativeText() // Dialog Negative Button Text

###### Chnage Text Color:
	SettingDialogRequest()
            .setDialogTitleColor() // Color for dialog Title Text
            .setDialogMessageColor() // Color for dialog Message Text
            .setDialogPositiveColor() // Color for dialog Positive Button Text
            .setDialogNegativeColor() // Color for dialog Negative Button Text

###### Chnage Text Font Family:
	SettingDialogRequest()
            .setDialogTitleTypeface() // Font Style for dialog Title Text
            .setDialogMessageTypeface() // Font Style for dialog Message Text
            .setDialogPositiveTypeface() // Font Style for dialog Positive Button Text
            .setDialogNegativeTypeface() // Font Style for dialog Negative Button Text

###### Click of dialog button:
    - **onPositive** button click -> pass user to **setting screen for grant denied permission.**
    - **onNegative** button click -> dismiss the dialog.

### ⭐️ If you liked it support me with your stars!

## License

	Copyright (c) 2021 Akshay Harsoda

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
	
	
	## Developed By
[Akshay Harsoda](https://github.com/AkshayHarsoda) - [akshayharsoda@gmail.com](https://mail.google.com/mail/u/0/?view=cm&fs=1&to=akshayharsoda@gmail.com&su=https://github.com/vickypathak123/Android-Ads-Helper&body=&bcc=akshayharsoda@gmail.com&tf=1)

  <a href="https://github.com/AkshayHarsoda" rel="nofollow">
  <img alt="Follow me on Google+" 
       height="50" width="50" 
       src="https://github.com/vickypathak123/Android-Ads-Helper/blob/master/social/github.png" 
       style="max-width:100%;">
  </a>

  <a href="https://www.linkedin.com/in/akshay-harsoda-b66820116" rel="nofollow">
  <img alt="Follow me on LinkedIn" 
       height="50" width="50" 
       src="https://github.com/vickypathak123/Android-Ads-Helper/blob/master/social/linkedin.png" 
       style="max-width:100%;">
  </a>

  <a href="https://twitter.com/Akshayharsoda1" rel="nofollow">
  <img alt="Follow me on Twitter" 
       height="50" width="50"
       src="https://github.com/vickypathak123/Android-Ads-Helper/blob/master/social/twitter.png" 
       style="max-width:100%;">
  </a>

  <a href="https://www.facebook.com/akshay.harsoda" rel="nofollow">
  <img alt="Follow me on Facebook" 
       height="50" width="50" 
       src="https://github.com/vickypathak123/Android-Ads-Helper/blob/master/social/facebook.png" 
       style="max-width:100%;">
  </a>
