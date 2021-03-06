# FlightSimulatorMobileApp

## About

This project is a flight simulator mobile app, displayed by FlightGear. In this project, we were asked to build virtual Joystick that controls the simulator

We build this project based on MVVM architecture as follow:

Model - refers either to a domain model, which represents real state content. Responsible to save the data and to connect to our server – Flight Gear Simulator. Actually,  Model is the one that instructs the simulator to move.
There is one model class: Model.java

ViewModel - creates the unique model and contains the details that connects the modules - responsible for the connection between the View and the model 
There is one viewModel class: ViewModel.java

View - responsible to render our application, using as GUI <br />
There are 2 view classes and one xml file: <br />
- MainActivity.java – responsible for the connection part in our program
- Joystick.java – separated view class, potentially, other Joysticks can be injected ( Active Object Design Pattern )
- activity_main.xml – file that designs the UI

## App Description
This app contains one window to display.
At first, you should enter IP and PORT for connection to server, and when clicking on Connect,
the application opens a connection to the server <br />
In the bottom part of the application, there are 2 seek bars: <br />
  1. horizonal seek bar for rudder <br />
  2. vertical seek bar for throttle <br />
and there is a joystick that its X-axis controlls the aileron, and the Y-axis controlls the elevator. <br />
Aileron, Elevator, Throttle, Rudder are flight control surfaces <br />

## Instructions:

Please download FlightGear application from: https://www.flightgear.org/ <br />
Define FlightGear settings as follow: --telnet=socket,in,10,127.0.0.1,6400,tcp <br />
Download "FlightSimulatorMobileApp" project to Android Studio (on Windows) <br />
Press Play

1. Fill IP and Port details
2. Press connect
3. Play with the joystick as you wish for
4. Enjoy!


## Introduction Video
Link: https://youtu.be/zYt7_xnmEWo

## UML
Attached also in the main folder
![image](https://user-images.githubusercontent.com/72906989/123504889-a1873b00-d664-11eb-826c-18f6fad122eb.png)

## Screen Shots
![image](https://user-images.githubusercontent.com/72906989/123504909-c54a8100-d664-11eb-891d-339d60916b5e.png)

