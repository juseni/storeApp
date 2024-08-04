# Cabify Challenge - Android Technical Assignment

## 📝 Notes for interviewer

![Kotlin](https://img.shields.io/badge/kotlin-1.9.10-yellow.svg) ![Room](https://img.shields.io/badge/room-2.6.0-blue.svg) ![Mockito](https://img.shields.io/badge/mockito-4.0.0-orange.svg) ![Hilt](https://img.shields.io/badge/dagger_hilt-2.48.1-red.svg) ![JetPack Compose](https://img.shields.io/badge/compose_compiler-1.5.3-green.svg)

This is an application made by myself using the Product Cabify Api, and implementing Reactive Clean Architecture with Android Architecture Components as the core of design architecture and patterns.
- Architectural patterns used: MVVM/MVI

![](https://developer.android.com/static/topic/libraries/architecture/images/mad-arch-domain-overview.png)

# Persistence
The application stores local data by using latest technology feature offers by google as Architecture Components with Room and Flows.

# Threads
This project handle all their threads with Coroutines on an asynchronous way by using scopes and dispatchers.  

# Modules
According to Robert Cecil Martin "Uncle Bob" the best way to reach a clean programming is by splitting logic, data, and UI or frameworks in different layers or modules, in that way the developer can understand in a better manner the code and it is easier to face future changes and also gets more understandable, more reliable and more testable. 

In this application the modules of layers used are by follow:

## data Module
According to Clean Architecture, this module take care of the business logic in order to deliver data, this module doesn't know about others.
I'm working with 2 datasources (Network, Local) so my main data (source of truth) always come from the API, and then I persit it into the the database. However if the device is not connected to the internet, user can get persist data anytime if it's available.
There are two repositories here, one to handle all the products and other to handle the products added to the shopping cart, but they consume the same database

Every data source has their own model (Entity and NetworkModel) and when it will be delivered outside the module, its mapped to an external model (ProductItem) wrapped inside a FLow
### di:
Implement Dependency injection using _dagger-hilt_
### db: 
DataSource from local data - There is one table , where products are set with code as primary key
### network:
Data source for data coming from API, getting through _retrofit2_.
### repositories:
Implement the business logic in order to deliver data as requested. There are 2 repositories in order to make it clear, not too long and avoiding boilerplate (Clean code). It is also planned if in the future more features are coming, we can reuse these repositories.

These repositories can be used for domain module in order to access, store or update data.
All data is processed, thread safe and catching if any error occurs during execution

_Unit testing is implemented successfully for both repositories considering different scenarios._
_Android testing is implemented successfully for room database._

## domain Module
This module its the bridge in between data and Ui module. It contains only usecases which can be used in any situation by Ui module. Sometimes this module also can contains some business logic processing data, like when has to map the productItems with discounts.
Similar to Data module, this module has it own model to deliver data coming from data module (domain.ProductItem)

_Unit testing is implemented successfully for four usecases considering different scenarios._

## discounts Module
This module has been created as a kind of feature module, assuming this feature module can provide discounts linked to different products
it's an independent module, because in the future as it was requested in the notes, it could change anytime

## app Module
Ui module to present data to the user which is getting from domain module.
I'm using JetPack compose to make the screens, also using Navigation from Compose to navigate in between screens, and I'm using viewmodels where you can find the usecases. This data it will be presented to the screens as stateFlows awareness lifecycle,
so every time an update is made, this stateFlows, emit new data.

There are also other situations where sharedFlows are used, because I need to use cold flows in order to emit data without comparing states
### ui:
All the screens are made in compose, using Material design 3. All the screens could have their own preview without any issue
There are 3 screens, Home Screen(Main Screen), Products Screen, and Checkout Screen. All of them use common components that can be found in _ui.components package_
- There is an empty holder just in case there are not data
- There is an error Alert dialog just in case something went wrong trying to get data
- There is a loading dialog, when fetching data
### navigation:
Using _NavhostController_ to navigate in between screens
### viewmodels:
there are 3 viewModels, one for home (which validate the item quantity to show into the app bar basket), other fro products, and other to handle the checkout

_Unit testing is implemented successfully for all of three viewModels considering different scenarios._
_Android testing is implemented successfully for compose navigation in all screens._


> You can find an App video by clicking [Here](https://drive.google.com/file/d/1FO7z03T-gR4EbHXzJ565lYkLszBhzbD9/view?usp=drive_link).
