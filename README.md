
# react-native-splash
Splash screen for react native, to avoid the white screen while your application is being loaded.
## Getting started

`$ npm install git+ssh://git@github.com/medlmobileenterprises/react-native-medl-splash.git --save`

### Mostly automatic installation

`$ react-native link react-native-medl-splash`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-splash` and add `RNSplash.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNSplash.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNSplashPackage;` to the imports at the top of the file
  - Add `new RNSplashPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-splash'
  	project(':react-native-splash').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-splash/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-splash')
  	```

## Usage
### iOS
1. Add the image asset to your images.xcassets
2. In your app delegate add this to show your splash screen
```
#import "RNSplash.h"      <--Add this line

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    NSURL *jsCodeLocation;

  jsCodeLocation = [[RCTBundleURLProvider sharedSettings] jsBundleURLForBundleRoot:@"index.ios" fallbackResource:nil];

  RCTRootView *rootView = [[RCTRootView alloc] initWithBundleURL:jsCodeLocation
                                                      moduleName:@""
                                               initialProperties:nil
                                                   launchOptions:launchOptions];
  rootView.backgroundColor = [[UIColor alloc] initWithRed:1.0f green:1.0f blue:1.0f alpha:1];

  self.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
  UIViewController *rootViewController = [UIViewController new];
  rootViewController.view = rootView;
  self.window.rootViewController = rootViewController;
  [self.window makeKeyAndVisible];
  [RNSplash showLoadingView:rootView andImageName:@"the name of your image asset for the splash"];      <--Add this line
  return YES;
}
```
### Android
1. Add the image asset to /main/res/drawable/
2. Add the code to your MainActivity
```
    ...
    import android.os.Bundle;
    import com.medlsplash.RNSplashModule;

    public class MainActivity extends ReactActivity {
        ...
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            RNSplashModule.showSplash(this,R.drawable.yoursplashasset);

        }
    }
```
### javascript
```
import {
  NativeModules
} from 'react-native';
const RNSplash = NativeModules.RNSplash;

//To hide the splash after your application is loaded, add the following in code in your initial component, usually your componentDidMount() function

componentDidMount() {
    RNSplash.hideLoadingViewWithDurationAndDelay(1, 1);
}

```
  
