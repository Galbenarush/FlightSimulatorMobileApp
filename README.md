# FlightSimulatorMobileApp

## About

This project is a flight simulator mobile app, displayed by FlightGear. In this project, we were asked to build virtual Joystick that controls the simulator

We build this project based on MVVM architecture as follow:

Model - refers either to a domain model, which represents real state content. Responsible to save the data and to connect to our server – Flight Gear Simulator. Actually,  Model is the one that instructs the simulator to move.
There is one model class: Model.java

ViewModel - creates the unique model and contains the details that connects the modules - responsible for the connection between the View and the model 
There is one viewModel class: ViewModel.java

View - responsible to render our application, using as GUI
There are 2 view classes and one xml file:
- MainActivity.java – responsible for the connection part in our program
- Joystick.java – separated view class, potentially, other Joysticks can be 	  injected ( Active Object Design Pattern )
- activity_main.xml – file that designs the UI

## App Description
This app contains one window to display.
At first, you should enter IP and PORT for connection to server, and when clicking on Connect,
the application opens a connection to the serverץ
In the bottom part of the application, there are 2 seek bars:
  1. horizonal seek bar for rudder
  2. vertical seek bar for throttle
and there is a joystick that its X-axis controlls the aileron, and the Y-axis controlls the elevator.
Aileron, Elevator, Throttle, Rudder are flight control surfaces

## Instructions:

Please download FlightGear application from: https://www.flightgear.org/
Define FlightGear settings as follow: --telnet=socket,in,10,127.0.0.1,6400,tcp
Download "FlightSimulatorMobileApp" project to Android Studio (on Windows)
Press Play and enjoy :)


## Introduction Video
Link:

## Screen Shots

