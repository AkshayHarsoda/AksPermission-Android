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
```java
        AksPermission.with(this@MainActivity)
                    .permission(Manifest.permission.READ_CALENDAR)
                    .isShowDefaultSettingDialog(
                        // Pass false if you don't want to showing a default setting dialog if some permissions are permanently denied.
                        true // Default value is true,
                    )
                    .isShowDefaultToast(
                        // Pass true if you want to showing a default toast message after user action.
                         false // Default value is false,
                    )
                    .request { grantedList ->
                        // you have get the all granted permissions list on here
                        Log.i(TAG, "Ask Single Permission -> Granted Permission List -> $grantedList")
                        // Auto action after all permissions granted
                        //  Write Your Code Here
                    }
```

###### Ask Multiple Permission:
```java
        AksPermission.with(this@MainActivity)
                    .permissionList(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO
                    ) // You can pass N number of permission
                    .setCustomSettingDialog(
                        // If you want to customize your settings dialog,
                        // which will appear after any permission is permanently denied
                        SettingDialogRequest()
                            .setDialogTitleColor(Color.YELLOW)
                            .setDialogMessageColor(Color.BLUE)
                            .setDialogPositiveColor(Color.GREEN)
                            .setDialogNegativeColor(Color.RED)
                    )
                    .request { grantedList, deniedList, permanentlyDeniedList ->
                        // you have get the all permissions list on here
                        Log.i(TAG, "Ask Multiple Permission -> Granted Permission List -> $grantedList")
                        Log.i(TAG, "Ask Multiple Permission -> Denied Permission List -> $deniedList")
                        Log.i(TAG, "Ask Multiple Permission -> PermanentlyDenied Permission List -> $permanentlyDeniedList")
                        // Auto action after all permissions granted
                        //  Write Your Code Here
                    }
```

#### In Java

###### Ask Single Permission:
```java
        AksPermission.with(MainActivity.this)
                .permission(Manifest.permission.READ_CALENDAR)
                .isShowDefaultSettingDialog(
                        // Pass false if you don't want to showing a default setting dialog if some permissions are permanently denied.
                        true // Default value is true,
                )
                .isShowDefaultToast(
                        // Pass true if you want to showing a default toast message after user action.
                        false // Default value is false,
                )
                .request(grantedList -> {
                    // you have get the all granted permissions list on here
                    Log.i(TAG, "Ask Single Permission -> Granted Permission List -> " + grantedList);
                    // Auto action after all permissions granted
                    //  Write Your Code Here
                    return null;
                });
```

###### Ask Multiple Permission:
```java
        AksPermission.with(MainActivity.this)
                .permissionList(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO
                ) // You can pass N number of permission
                .setCustomSettingDialog(
                        // If you want to customize your settings dialog,
                        // which will appear after any permission is permanently denied
                        new SettingDialogRequest()
                                .setDialogTitleColor(Color.YELLOW)
                                .setDialogMessageColor(Color.BLUE)
                                .setDialogPositiveColor(Color.GREEN)
                                .setDialogNegativeColor(Color.RED)
                )
                .request((grantedList, deniedList, permanentlyDeniedList) -> {
                    // you have get the all permissions list on here
                    Log.i(TAG, "Ask Multiple Permission -> Granted Permission List -> " + grantedList);
                    Log.i(TAG, "Ask Multiple Permission -> Denied Permission List -> " + deniedList);
                    Log.i(TAG, "Ask Multiple Permission -> PermanentlyDenied Permission List -> " + permanentlyDeniedList);
                    // Auto action after all permissions granted
                    //  Write Your Code Here
                    return null;
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