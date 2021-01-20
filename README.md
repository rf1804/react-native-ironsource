<p align="center">
  <a href="https://www.npmjs.com/package/react-native-ironsource"><img src="https://img.shields.io/npm/dm/react-native-ironsource.svg?style=flat-square" alt="NPM downloads"></a>
  <a href="https://www.npmjs.com/package/react-native-ironsource"><img src="https://img.shields.io/npm/v/react-native-ironsource.svg?style=flat-square" alt="NPM version"></a>
  <a href="/LICENSE"><img src="https://img.shields.io/npm/l/react-native-ironsource.svg?style=flat-square" alt="License"></a>
  <a href="https://twitter.com/mraja2943"><img src="https://img.shields.io/twitter/follow/mraja2943.svg?style=social&label=Follow" alt="Follow on Twitter"></a>
</p>

# react-native-ironsource

## Getting started

`$ npm install react-native-ironsource --save`

### Mostly automatic installation

`$ react-native link react-native-ironsource`

### Manual installation

#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-ironsource` and add `RNIronsource.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNIronsource.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`

- Add `import com.ironsource.RNIronsourcePackage;` to the imports at the top of the file
- Add `new RNIronsourcePackage()` to the list returned by the `getPackages()` method

2. Append the following lines to `android/settings.gradle`:
   ```
   include ':react-native-ironsource'
   project(':react-native-ironsource').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-ironsource/android')
   ```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
   ```
     compile project(':react-native-ironsource')
   ```

## Usage

```javascript
import RNIronsource from 'react-native-ironsource';


RNIronsource.startIronSource(appKey, userId, adType);

appKey, userId, adType in String

adType options:
  Rewarded Video: "rewardedvideo",
  Interstitial Ads: "interstitial",
  Offerwall: "offerwall",
  Banner: "banner"
```
